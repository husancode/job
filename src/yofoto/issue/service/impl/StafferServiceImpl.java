package yofoto.issue.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import yofoto.issue.pojo.Department;
import yofoto.issue.pojo.Login;
import yofoto.issue.pojo.Staffer;
import yofoto.issue.util.AuthUI;
import yofoto.issue.util.AuthUtil;
import yofoto.issue.util.Page;

/**
 * @author husan
 * @Date 2012-10-20
 * @description
 */
@Service
public class StafferServiceImpl extends SimpleHibernateDao<Staffer,Integer>{
	@Override
	@Autowired(required=true)		
	public void setSessionFactory(@Qualifier("issue") SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	public Set<Staffer> addProjectNewMember(Department department ,Set<String> emails){
		Set<Staffer> stas = new HashSet<Staffer>();
		for(String email : emails){
			Staffer sta = new Staffer();
			sta.setEmail(email);
			sta.setName(email.substring(0, email.indexOf("@")));
			sta.setGeneraTime(new Date());
			sta.setPassword(email);
			sta.setNotify(true);
			sta.setStatus(1);
			stas.add(sta);
		}
		stas = batchSave(stas);
		Integer tid = department.getTeam().getTid();
		
		Query query1 = super.getSession().createSQLQuery("insert into j_project_staffer(sid, did)values(?, ?)");
		Query query2 = super.getSession().createSQLQuery("insert into j_team_staffer(tid, sid)values(?, ?)");
		Query query3 = super.getSession().createSQLQuery("select 1 from j_project_staffer where sid=? and did=?");
		Query query4 = super.getSession().createSQLQuery("select 1 from j_team_staffer where sid=? and tid=?");
		
		for(Staffer staffer : stas){
			query3.setParameter(0, staffer.getSid());
			query3.setParameter(1, department.getDid());
			if(query3.list().size()<1){
				query1.setParameter(0, staffer.getSid());
				query1.setParameter(1, department.getDid());
				query1.executeUpdate();
			}
			query4.setParameter(0, staffer.getSid());
			query4.setParameter(1, tid);
			if(query4.list().size()<1){
				query2.setParameter(0, tid);
				query2.setParameter(1, staffer.getSid());
				query2.executeUpdate();
			}
			
		}
		return stas;
	}
	public void delProjectAdmin(String ids, Integer did){
		Query query0 = super.getSession().createSQLQuery("select p.admin from project p where p.id=?");
		query0.setParameter(0, did);
		String admin = (String)query0.uniqueResult();
		admin = AuthUtil.delProjectAdmin(admin, ids.split(","));
		Query query1 = super.getSession().createSQLQuery("update project set admin=? where id=?");
		query1.setParameter(0, admin);
		query1.setParameter(1, did);
		query1.executeUpdate();
	}
	public void addProjectAdmin(String ids,Integer did){
		Query query0 = super.getSession().createSQLQuery("select p.admin from project p where p.id=?");
		query0.setParameter(0, did);
		String admin = (String)query0.uniqueResult();
		
		admin = AuthUtil.addProjectAdmin(admin, ids.split(","));
		Query query1 = super.getSession().createSQLQuery("update project set admin=? where id=?");
		query1.setParameter(0, admin);
		query1.setParameter(1, did);
		query1.executeUpdate();
		
	}
	
	//判断是不明当前登录用户是当前项目的管理员
	public boolean isProjectAdmin(Integer userid,Integer did){
		Query query0 = super.getSession().createSQLQuery("select p.admin from project p where p.id=?");
		query0.setParameter(0, did);
		String adminIds = (String)query0.uniqueResult();
		String[] ss = adminIds.split(";");
		if(ss == null || ss.length == 0){
			return false;
		}
		
		for(int i=0;i< ss.length;i++){
			if(ss[i].equals(userid.toString())){
				return true;
			}
		}
		
		return false;
	}
	
	public void delProjectMember(String ids,Integer did){
		Query query0 = super.getSession().createSQLQuery("select p.admin from project p where p.id=?");
		query0.setParameter(0, did);
		String admin = (String)query0.uniqueResult();
		//System.out.println(admin);
		admin = AuthUtil.delProjectAdmin(admin, ids.split(","));
		Query query1 = super.getSession().createSQLQuery("update project set admin=? where id=?");
		query1.setParameter(0, admin);
		query1.setParameter(1, did);
		query1.executeUpdate();
		Query query2 = super.getSession().createSQLQuery("delete from j_project_staffer where did=? and sid in("+ids+")");
		query2.setParameter(0, did);
		query2.executeUpdate();
	}
	
	public List<String> addProjectMember(String[] ids,Integer did){
		Query query1 = super.getSession().createSQLQuery("select 1 from j_project_staffer jp where jp.sid=? and jp.did=?");
		Query query2 = super.getSession().createSQLQuery("select 1 from j_team_staffer jts where  jts.sid=? and jts.tid= (select p.teamid from project p where p.id=?)");
		Query query3 = super.getSession().createSQLQuery("insert into j_project_staffer(sid, did)values(?, ?)");
		List<String> results = new ArrayList<String>();
		for(String sid : ids){
			query1.setParameter(0, sid);
			query1.setParameter(1, did);
			if(query1.list()==null||query1.list().size()<1){
				query2.setParameter(0, sid);
				query2.setParameter(1, did);
				if(query2.list().size()>=1){
					results.add(sid);
					query3.setParameter(0, sid);
					query3.setParameter(1, did);
					query3.executeUpdate();
				}
			}
		}
		return results;
		
	}
	
	public List<Staffer> getStafferByTeamID(Integer tid){
		Query query = super.getSession().createSQLQuery
		("select distinct s.* from staffer s ,j_team_staffer jts " +
				"where s.id = jts.sid and s.status=1 and jts.tid=? order by id asc").addEntity(Staffer.class);
		query.setParameter(0, tid);
		return query.list();
	}
	
	public Staffer login(String email,String password){
		/*Criterion[] criterions = new Criterion[3];
		criterions[0] = Restrictions.eq("email", email);
		criterions[1] = Restrictions.eq("password", password);
		criterions[2] = Restrictions.eq("status", 1);
		Staffer staff = (Staffer)super.createCriteria(criterions).uniqueResult();
		return staff;*/
		return (Staffer)super.getSession().createCriteria(Staffer.class).
		add(Restrictions.eq("email", email)).
		add(Restrictions.eq("password", password)).
		add( Restrictions.eq("status", 1)).
		uniqueResult();
		
	}
	public void recordLogin(String user){
		Login login = new Login();
		login.setUserName(user);
		login.setLastLogin(new Date());
		super.getSession().save(login);
	}
	public boolean isLogin(String user,Date date){
		Query query = super.getSession().createQuery("from LOGIN l where l.userName=? and l.lastLogin>?");
		query.setParameter(0, user);
		query.setParameter(1, date);
		query.setMaxResults(1);
		Login login = (Login)query.uniqueResult();
		if(login!=null){
			//System.out.println(login.getUserName());
			login.setLastLogin(new Date());
			super.getSession().saveOrUpdate(login);
			return true;
		}
		return false;
		
	}
	public Staffer loadByEmail(String email){
		/*Query query = super.getSession().createQuery("from Staffer staffer where staffer.email=? and staffer.status=1");
		query.setParameter(0, email);
		Staffer staffer = (Staffer)query.uniqueResult();
		return staffer;*/
		Criterion[] criterions = new Criterion[2];
		criterions[0] = Restrictions.eq("email",email);
		criterions[1] = Restrictions.eq("status", 1);
		Staffer staffer = (Staffer) super.createCriteria(criterions).uniqueResult();
		return staffer;
	}
	public Staffer loadByName(String name){
		Criterion[] criterions = new Criterion[2];
		criterions[0] = Restrictions.eq("name",name);
		criterions[1] = Restrictions.eq("status", 1);
		Staffer staffer = (Staffer) super.createCriteria(criterions).uniqueResult();
		return staffer;
	}
	public Set<Staffer> batchSave(Set<Staffer> staffers){
		int i=0;
		Set<Staffer> results = new HashSet<Staffer>();
		for(Staffer sta : staffers){
			Staffer staffer = loadByEmail(sta.getEmail());
			if(staffer==null)
			{
				save(sta);
				results.add(sta);
			}else{
				results.add(staffer);
			}
			
			i++;
			if(i%10 == 0){
				super.flush();
			}
		}
		if(i%10 != 0){
			super.flush();
		}
		return results;
	}
	public void save(Staffer staffer){
		
		super.save(staffer);
	}
	
	public void delete(Integer id){
		super.delete(id);
	}
	public void delete (Staffer staffer){
		super.delete(staffer);
	}
	public Staffer load(Integer id){
		return (Staffer)super.getSession().load(Staffer.class, id);
	}
	
	public List<Staffer> loadAll(){
	
		return  super.getSession().createCriteria(Staffer.class).list();
	}
	
	//
	
	public Page<Staffer> listByPage(final Page<Staffer> page, final AuthUI auth){
		Criteria criteria = super.getSession().createCriteria(Staffer.class);
		if(page.isAutoCount()){
			int total = criteria.list().size();
			page.setTotalCount(total);
		}
		System.out.println(page.getFirst());
		System.out.println(page.getPageSize());
		criteria.setFirstResult(page.getFirst());
		criteria.setMaxResults(page.getPageSize());
		
		List<Staffer> result = criteria.list();
		for(Staffer s: result){
			System.out.println(s.getName());
		}
		page.setResult(result);
		return page;
	}
	
	public Page<Staffer> searchPage(final Page<Staffer> page,final AuthUI auth, final String filter){
		return null;
	}

	 
}
