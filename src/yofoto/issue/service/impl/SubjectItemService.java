package yofoto.issue.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import yofoto.issue.pojo.Issue;
import yofoto.issue.pojo.Staffer;
import yofoto.issue.pojo.SubjectItem;
import yofoto.issue.util.ReflectionUtils;
import yofoto.issue.vo.Page;

/**
 * @author husan
 * @Date 2013-10-30
 * @description
 */
@Service
public class SubjectItemService extends SimpleHibernateDao<SubjectItem,Integer>{
	
	@Override
	@Autowired(required=true)		
	public void setSessionFactory(@Qualifier("issue") SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	} 
	
	public List<SubjectItem> getSubjectItems(Staffer completer){
		Criterion[] criterion = new Criterion[1];
		criterion[0] = Restrictions.eq("completer", completer);
		Criteria criteria = super.createCriteria(criterion);
		criteria.addOrder(Order.asc("status"));
		criteria.addOrder(Order.desc("id"));
		return criteria.list();
	}
	
	public Page<SubjectItem> searchPage(final Criterion[] criterion,final Page<SubjectItem> page){
		Criteria criteria = super.createCriteria(criterion);
		if(page.isAutoCount()){
			long total = countCriteriaResult(criteria);
			page.setTotal(total);
		}
		criteria = setPageParater(criteria,page);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<SubjectItem> result = criteria.list();
		page.setResults(result);
		return page;
		
	}
	
	
	
	@SuppressWarnings("unchecked")
	protected long countCriteriaResult(final Criteria c) {
		CriteriaImpl impl = (CriteriaImpl) c;

		// 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();

		List<CriteriaImpl.OrderEntry> orderEntries = null;
		try {
			orderEntries = (List) ReflectionUtils.getFieldValue(impl, "orderEntries");
			ReflectionUtils.setFieldValue(impl, "orderEntries", new ArrayList());
		} catch (Exception e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}

		// 执行Count查询
		Long totalCountObject = ((Number) c.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		long totalCount = (totalCountObject != null) ? totalCountObject : 0;

		// 将之前的Projection,ResultTransformer和OrderBy条件重新设回去
		c.setProjection(projection);

		if (projection == null) {
			c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			c.setResultTransformer(transformer);
		}
		try {
			ReflectionUtils.setFieldValue(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}

		return totalCount;
	}
	
}
