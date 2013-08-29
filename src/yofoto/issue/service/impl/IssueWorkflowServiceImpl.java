package yofoto.issue.service.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import yofoto.issue.pojo.Issue;
import yofoto.issue.pojo.IssueWorkflow;
import yofoto.issue.pojo.Staffer;

/**
 * @author husan
 * @Date 2012-11-8
 * @description
 */
@Service
public class IssueWorkflowServiceImpl extends SimpleHibernateDao<IssueWorkflow,Integer>{
	@Override
	@Autowired(required=true)		
	public void setSessionFactory(@Qualifier("issue") SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	} 
	public Staffer getToStaffer(Integer id){
		Criteria criteria = super.getSession().createCriteria(IssueWorkflow.class);
		criteria.add(Restrictions.eq("issueId",id));
		criteria.addOrder(Order.desc("id"));
		criteria.setMaxResults(1);
		IssueWorkflow issueWF = (IssueWorkflow) criteria.uniqueResult();
		return issueWF.getFromStaffer();
	}
	public IssueWorkflow getContent(Integer id){
		Criteria criteria = super.getSession().createCriteria(IssueWorkflow.class);
		criteria.add(Restrictions.eq("issueId",id));
		criteria.addOrder(Order.asc("id"));
		criteria.setMaxResults(1);
		return (IssueWorkflow) criteria.uniqueResult();
	}
	public List<IssueWorkflow> getByIssue(Issue issue){
		Criteria criteria = super.getSession().createCriteria(IssueWorkflow.class);
		criteria.add(Restrictions.eq("issueId",issue.getId()));
		criteria.addOrder(Order.asc("id"));
		return criteria.list();
	}
	public void save(IssueWorkflow issueWorkflow){
		super.save(issueWorkflow);
	}
	
	public void delete(Integer id){
		super.delete(id);
	}
		
}
