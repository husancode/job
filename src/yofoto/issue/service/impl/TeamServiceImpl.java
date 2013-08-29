package yofoto.issue.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.type.IntegerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import yofoto.issue.pojo.Staffer;
import yofoto.issue.pojo.Team;
import yofoto.issue.util.AuthUtil;

/**
 * @author husan
 * @Date 2012-10-25
 * @description
 */
@Service
public class TeamServiceImpl extends SimpleHibernateDao<Team,Integer>{
	@Override
	@Autowired(required=true)		
	public void setSessionFactory(@Qualifier("issue") SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	} 
	
	public boolean isInTeam(Staffer staffer, Team team){
		Query query = super.getSession().createSQLQuery("select 1 from j_team_staffer t where t.sid =? and t.tid=?");
		query.setParameter(0, staffer.getSid());
		query.setParameter(1, team.getTid());
		
		int size = query.list().size();
		if(size>0)return true;
		return false;
	} 
	
	public boolean hasAuth(Integer id,Staffer staffer){
		Team team = load(id);
		return AuthUtil.isTeamAdmin(staffer, team);
	}
	//
	public Set<Staffer> getStaffers(Integer tid){
		Query query = super.getSession().createSQLQuery("select s.* from j_team_staffer jt  left join staffer s on jt.sid=s.id where  s.status=1 and jt.tid=? order by s.id desc").addEntity(Staffer.class);
		query.setParameter(0, tid);
		return new HashSet<Staffer>(query.list());
		
	}
	public void delStaffer(Integer tid,Integer sid){
		SQLQuery query = super.getSession().createSQLQuery("delete from j_team_staffer where sid=? and tid=?");
		query.setParameter(0, sid);
		query.setParameter(1, tid);
		
		SQLQuery query2 = super.getSession().createSQLQuery("delete from j_project_staffer j where j.sid=? and j.did in(select id from project p where p.teamid=?)");
		query2.setParameter(0, sid);
		query2.setParameter(1, tid);
		
		query.executeUpdate();
		query2.executeUpdate();
	}
	
	public List<Team> loadTeamsByStaffer(Staffer staffer){
		List<Team> teams = null;
		return teams;
	}
	
	public List<Team> loadAll(){
		SQLQuery query = super.getSession().createSQLQuery("select * from team where status=1 order by id asc");
		query.addEntity(Team.class);
		
		List<Team> result = query.list();
		return result;
	}
	
	public int loadAllSize(){
		SQLQuery query = super.getSession().createSQLQuery("select count(1) as AA from team where status=1");
		query.addScalar("AA",new IntegerType());
		Integer result = (Integer)query.uniqueResult();
		return result;
	}
	
	public void save(Team team){
		super.save(team);
	}
	
	public Team load(Integer id){
		return (Team) super.getSession().load(Team.class, id);
	}
	public void delete(Integer id){
		super.delete(id);
	}
	public void delete(Team team){
		super.delete(team);
	}
}
