package yofoto.issue.service.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import yofoto.issue.pojo.Staffer;
import yofoto.issue.pojo.StafferScore;
import yofoto.issue.util.ShowField;

/**
 * @author husan
 * @Date 2013-5-21
 * @description
 */
@Service
public class StafferScoreServiceImpl extends SimpleHibernateDao<StafferScore,Integer>{
	
	@Autowired
	private StafferServiceImpl stafferImpl;
	@Autowired
	private IssueServiceImpl issueImpl;
	
	@Override
	@Autowired(required=true)		
	public void setSessionFactory(@Qualifier("issue") SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	} 
	
	public List<StafferScore> getStafferScoreList(Integer tid, String month){
		Criterion[] criterion = new Criterion[3];
		criterion[0] = Restrictions.eq("month", month);
		criterion[1] = Restrictions.eq("tid", tid);
		
		//criterion[2] = Restrictions.eq("staffer.projectAdmin", false);
		Criteria scores = super.createCriteria(criterion);
		return scores.list();
	}
	
	public List<StafferScore> getStafferScore(Integer tid , String month){
		Query query = super.getSession().createSQLQuery("select t1.name as name1,t1.id as sid,st1.* from(select s1.id,s1.name from staffer s1,j_team_staffer j1 where s1.id=j1.sid and j1.tid=? and s1.status=1) t1 left join STAFFERSCORE st1 on t1.id=st1.staffer_id and st1.month=? order by t1.id asc")
		.addScalar("NAME1", Hibernate.STRING)
		.addScalar("ID", Hibernate.INTEGER)
		.addScalar("TID", Hibernate.INTEGER)
		.addScalar("STAFFER_ID", Hibernate.INTEGER)
		.addScalar("NOTE", Hibernate.STRING)
		.addScalar("AMOUNT", Hibernate.STRING)
		.addScalar("MONTH", Hibernate.STRING)
		.addScalar("ATTITUDE", Hibernate.STRING)
		.addScalar("COORDINATION", Hibernate.STRING)
		.addScalar("DISCIPLINE", Hibernate.STRING)
		.addScalar("PROGRESS", Hibernate.STRING)
		.addScalar("WORDCHECK", Hibernate.STRING)
		.addScalar("ISSUESCORE", Hibernate.STRING)
		.addScalar("SCORE", Hibernate.STRING)
		.addScalar("SID",Hibernate.INTEGER)
		.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		
		query.setParameter(0, tid);
		query.setParameter(1, month);
		List<Map> result = query.list();
	
		List<StafferScore> scores = new ArrayList<StafferScore>();
		for(Map map : result){
			
			StafferScore stafferScore = new StafferScore();
			stafferScore.setId((Integer)map.get("ID"));
			stafferScore.setName((String)map.get("NAME1"));
			stafferScore.setTid((Integer)map.get("TID"));
			stafferScore.setSid((Integer)map.get("SID"));
			stafferScore.setAmount((String)map.get("AMOUNT"));
			stafferScore.setAttitude((String)map.get("ATTITUDE"));
			stafferScore.setCoordination((String)map.get("COORDINATION"));
			stafferScore.setDiscipline((String)map.get("DISCIPLINE"));
			stafferScore.setProgress((String)map.get("PROGRESS"));
			stafferScore.setWordCheck((String)map.get("WORDCHECK"));
			stafferScore.setIssueScore((String)map.get("ISSUESCORE"));
			stafferScore.setScore((String)map.get("SCORE"));
			stafferScore.setNote((String)map.get("NOTE"));
			
			scores.add(stafferScore);
		}
		return scores;
		
	}
	
	
	public void deal(StafferScore stafferScore){
		if(stafferScore==null)return;
		Staffer staffer = stafferImpl.loadByName(stafferScore.getName());
		Map map = issueImpl.getScoreList(stafferScore.getMonth(),staffer.getSid());
		
		double avgScore = Double.parseDouble(String.valueOf(map.get("avgScore")));
	
	
		double amount = Double.parseDouble(stafferScore.getAmount());
		double attitude = Double.parseDouble(stafferScore.getAttitude());
		double coordination = Double.parseDouble(stafferScore.getCoordination());
		double discipline = Double.parseDouble(stafferScore.getDiscipline());
		double progress = Double.parseDouble(stafferScore.getProgress());
		double wordCheck = Double.parseDouble(stafferScore.getWordCheck());
	
		double score = avgScore+amount+attitude+coordination+discipline+progress+wordCheck;
		BigDecimal bd = new BigDecimal(score);
		 bd  =  bd.setScale(1,BigDecimal.ROUND_HALF_UP);
		 score = bd.doubleValue();
		stafferScore.setStaffer(staffer);
		stafferScore.setIssueScore(String.valueOf(avgScore));
		stafferScore.setScore(String.valueOf(score));
		
		
		super.getSession().saveOrUpdate(stafferScore);
	}

}
