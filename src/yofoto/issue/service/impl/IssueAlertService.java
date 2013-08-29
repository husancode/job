package yofoto.issue.service.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import yofoto.issue.pojo.Issue;
import yofoto.issue.pojo.IssueAlert;

/**
 * @author husan
 * @Date 2013-4-27
 * @description
 */
@Service
public class IssueAlertService extends SimpleHibernateDao<IssueAlert,Integer>{
	@Autowired
	private IssueServiceImpl issueService;
	@Override
	@Autowired(required=true)		
	public void setSessionFactory(@Qualifier("issue") SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	} 
	
	public void updateIssueAlert(int day){
		Query query = super.getSession().createSQLQuery("insert into issuealert(id, emailflag, info, completer_id)values(?, ?, ?, ?)");
		List<Issue> issues = issueService.getIssueAlertList(day);
		for(Issue issue: issues){
			/*IssueAlert issueAlert = new IssueAlert();
			issueAlert.setId(issue.getId());
			issueAlert.setCompleter(issue.getCompleter());
			issueAlert.setEmailFlag(false);
			String info = "你在ees有任务急需完成："+issue.getTitle();
			issueAlert.setInfo(info);*/
			String info = "你在ees有任务急需完成："+issue.getTitle();
			query.setParameter(0, issue.getId());
			query.setParameter(1, 0);
			query.setParameter(2, info);
			query.setParameter(3, issue.getCompleter().getSid());
			try {
				//捕获不到异常，我去
				//super.getSession().save(issueAlert);
				query.executeUpdate();
			} catch (HibernateException e) {
				// TODO Auto-generated catch block
			  //System.err.println("ssdfsdfdf");
			}
		}
	}
	
	public List<IssueAlert> getEmailList(){
		Criterion[] criterion = new Criterion[1];
		criterion[0] = Restrictions.eq("emailFlag", false);
		Criteria issueAlerts = super.createCriteria(criterion);
		return issueAlerts.list();
	}
	public void emailFlagUpdate(String ids){
		Query query = super.getSession().createSQLQuery("update issueAlert a1 set a1.emailflag=1 where a1.id in("+ids+")");
		query.executeUpdate();
	}

}
