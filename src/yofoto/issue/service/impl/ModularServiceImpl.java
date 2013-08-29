package yofoto.issue.service.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import yofoto.issue.pojo.Modular;

/**
 * @author husan
 * @Date 2012-10-20
 * @description
 */
@Service
public class ModularServiceImpl extends SimpleHibernateDao<Modular,Integer> {
	@Override
	@Autowired(required=true)		
	public void setSessionFactory(@Qualifier("issue") SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	} 
	
	public Modular loadByName(String name){
		Criterion[] criterion = new Criterion[1];
		criterion[0] = Restrictions.eq("name", name);
		return (Modular)super.createCriteria(criterion).uniqueResult();
	}
	
	public void deleteByID(Integer mid,Integer did){
		Query query = super.getSession().createSQLQuery("delete from modular m where m.departmentid=? and m.id=?");
		query.setParameter(0, did);
		query.setParameter(1, mid);
		query.executeUpdate();
	}
	public void merge(Modular modular){
		super.getSession().merge(modular);
	}
	public void save(Modular modular){
		super.save(modular);
	}
	public void delete(Integer id){
		super.delete(id);
	}
	public void delete(Modular modular){
		super.delete(modular);
	}
	public Modular load(Integer id){
		return (Modular)super.getSession().load(Modular.class, id);
	}
}
