package yofoto.issue.service.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import yofoto.issue.pojo.Issue;
import yofoto.issue.pojo.Subject;
import yofoto.issue.pojo.SubjectItem;
import yofoto.issue.vo.Page;

/**
 * @author husan
 * @Date 2013-10-29
 * @description
 */
@Service
public class SubjectService  extends SimpleHibernateDao<Subject,Integer> {
	
	@Override
	@Autowired(required=true)		
	public void setSessionFactory(@Qualifier("issue") SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	} 
	
	public Subject load(Integer id){
		return (Subject)super.getSession().load(Subject.class, id);
	}
	
	public Page<Subject> searchPage(final Criterion[] criterion,final Page<Subject> page){
		Criteria criteria = super.createCriteria(criterion);
		if(page.isAutoCount()){
			long total = countCriteriaResult(criteria);
			page.setTotal(total);
		}
		criteria = setPageParater(criteria,page);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Subject> result = criteria.list();
		page.setResults(result);
		return page;
		
	}
	//项目确认，需要将项目status设为2，项目item设为3，issue设为有效
	public void confirm(Integer subjectID){
		Subject subject = load(subjectID);
		subject.setStatus(2);
		for(SubjectItem item : subject.getItems()){
			item.setStatus(3);
			for(Issue issue : item.getIssues()){
				issue.setStatus(1);
			}
		}
		super.getSession().saveOrUpdate(subject);
	}
	

}
