package yofoto.issue.service.impl;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import yofoto.issue.pojo.Upload;

/**
 * @author husan
 * @Date 2013-10-24
 * @description
 */
@Service
public class UploadService  extends SimpleHibernateDao<Upload,Integer>{
	@Override
	@Autowired(required=true)		
	public void setSessionFactory(@Qualifier("issue") SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	} 
	
	public Upload getNewestUpload(Integer tid){
		Criterion[] criterion = new Criterion[1];
		criterion[0] = Restrictions.eq("tid",tid);
		Criteria criteria = super.createCriteria(criterion);
		criteria.addOrder(Order.desc("id"));
		criteria.setMaxResults(1);
		return (Upload)criteria.uniqueResult();
	}
	
	

}
