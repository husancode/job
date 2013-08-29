package yofoto.issue.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import yofoto.issue.util.ReflectionUtils;

/**
 * 封装Hibernate原生API的DAO泛型基类.
 * 
 * 可在Service层直接使用, 也可以扩展泛型DAO子类使用, 见两个构造函数的注释.
 * 参考Spring2.5自带的Petlinc例子, 取消了HibernateTemplate, 直接使用Hibernate原生API.
 * 
 * @param <T> DAO操作的对象类型
 * @param <PK> 主键类型
 * 
 * @author calvin
 */
@SuppressWarnings("unchecked")
@Transactional(propagation=Propagation.REQUIRED)
public class SimpleHibernateDao<T, PK extends Serializable>{

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected SessionFactory sessionFactory;

	protected Class<T> entityClass;

	/**
	 * 用于Dao层子类使用的构造函数.
	 * 通过子类的泛型定义取得对象类型Class.
	 * eg.
	 * public class UserDao extends SimpleHibernateDao<User, Long>
	 */
	public SimpleHibernateDao() {
		this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
	}

	/**
	 * 用于用于省略Dao层, 在Service层直接使用通用SimpleHibernateDao的构造函数.
	 * 在构造函数中定义对象类型Class.
	 * eg.
	 * SimpleHibernateDao<User, Long> userDao = new SimpleHibernateDao<User, Long>(sessionFactory, User.class);
	 */
	public SimpleHibernateDao(final SessionFactory sessionFactory, final Class<T> entityClass) {
		this.sessionFactory = sessionFactory;
		this.entityClass = entityClass;
	}

	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#getSessionFactory()
	 */
	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#getSessionFactory()
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#setSessionFactory(org.hibernate.SessionFactory)
	 */
	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#setSessionFactory(org.hibernate.SessionFactory)
	 */
	//@Autowired
	public void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#getSession()
	 */
	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#getSession()
	 */
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#save(T)
	 */
	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#save(T)
	 */
	public void save(final T entity) {
		Assert.notNull(entity, "entity不能为空");
		getSession().saveOrUpdate(entity);
		logger.debug("save entity: {}", entity);
	}

	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#delete(T)
	 */
	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#delete(T)
	 */
	public void delete(final T entity) {
		Assert.notNull(entity, "entity不能为空");
		getSession().delete(entity);
		logger.debug("delete entity: {}", entity);
	}

	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#delete(PK)
	 */
	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#delete(PK)
	 */
	public void delete(final PK id) {
		Assert.notNull(id, "id不能为空");
		delete(get(id));
		logger.debug("delete entity {},id is {}", entityClass.getSimpleName(), id);
	}

	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#get(PK)
	 */
	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#get(PK)
	 */
	public T get(final PK id) {
		Assert.notNull(id, "id不能为空");
		return (T) getSession().load(entityClass, id);
	}

	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#get(java.util.Collection)
	 */
	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#get(java.util.Collection)
	 */
	public List<T> get(final Collection<PK> ids) {
		return find(Restrictions.in(getIdName(), ids));
	}

	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#getAll()
	 */
	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#getAll()
	 */
	public List<T> getAll() {
		return find();
	}

	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#getAll(java.lang.String, boolean)
	 */
	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#getAll(java.lang.String, boolean)
	 */
	public List<T> getAll(String orderByProperty, boolean isAsc) {
		Criteria c = createCriteria();
		if (isAsc) {
			c.addOrder(Order.asc(orderByProperty));
		} else {
			c.addOrder(Order.desc(orderByProperty));
		}
		return c.list();
	}

	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#findBy(java.lang.String, java.lang.Object)
	 */
	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#findBy(java.lang.String, java.lang.Object)
	 */
	public List<T> findBy(final String propertyName, final Object value) {
		Assert.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return find(criterion);
	}

	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#findUniqueBy(java.lang.String, java.lang.Object)
	 */
	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#findUniqueBy(java.lang.String, java.lang.Object)
	 */
	public T findUniqueBy(final String propertyName, final Object value) {
		Assert.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return (T) createCriteria(criterion).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#find(java.lang.String, java.lang.Object)
	 */
	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#find(java.lang.String, java.lang.Object)
	 */
	public <X> List<X> find(final String hql, final Object... values) {
		return createQuery(hql, values).list();
	}

	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#find(java.lang.String, java.util.Map)
	 */
	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#find(java.lang.String, java.util.Map)
	 */
	public <X> List<X> find(final String hql, final Map<String, ?> values) {
		return createQuery(hql, values).list();
	}

	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#findUnique(java.lang.String, java.lang.Object)
	 */
	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#findUnique(java.lang.String, java.lang.Object)
	 */
	public <X> X findUnique(final String hql, final Object... values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#findUnique(java.lang.String, java.util.Map)
	 */
	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#findUnique(java.lang.String, java.util.Map)
	 */
	public <X> X findUnique(final String hql, final Map<String, ?> values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#batchExecute(java.lang.String, java.lang.Object)
	 */
	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#batchExecute(java.lang.String, java.lang.Object)
	 */
	public int batchExecute(final String hql, final Object... values) {
		return createQuery(hql, values).executeUpdate();
	}

	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#batchExecute(java.lang.String, java.util.Map)
	 */
	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#batchExecute(java.lang.String, java.util.Map)
	 */
	public int batchExecute(final String hql, final Map<String, ?> values) {
		return createQuery(hql, values).executeUpdate();
	}

	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#createQuery(java.lang.String, java.lang.Object)
	 */
	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#createQuery(java.lang.String, java.lang.Object)
	 */
	public Query createQuery(final String queryString, final Object... values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#createQuery(java.lang.String, java.util.Map)
	 */
	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#createQuery(java.lang.String, java.util.Map)
	 */
	public Query createQuery(final String queryString, final Map<String, ?> values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}

	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#find(org.hibernate.criterion.Criterion)
	 */
	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#find(org.hibernate.criterion.Criterion)
	 */
	public List<T> find(final Criterion... criterions) {
		return createCriteria(criterions).list();
	}

	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#findUnique(org.hibernate.criterion.Criterion)
	 */
	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#findUnique(org.hibernate.criterion.Criterion)
	 */
	public T findUnique(final Criterion... criterions) {
		return (T) createCriteria(criterions).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#createCriteria(org.hibernate.criterion.Criterion)
	 */
	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#createCriteria(org.hibernate.criterion.Criterion)
	 */
	public Criteria createCriteria(final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			if(c!=null)
			criteria.add(c);
		}
		return criteria;
	}

	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#initProxyObject(java.lang.Object)
	 */
	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#initProxyObject(java.lang.Object)
	 */
	public void initProxyObject(Object proxy) {
		Hibernate.initialize(proxy);
	}

	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#flush()
	 */
	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#flush()
	 */
	public void flush() {
		getSession().flush();
	}

	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#distinct(org.hibernate.Query)
	 */
	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#distinct(org.hibernate.Query)
	 */
	public Query distinct(Query query) {
		query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return query;
	}

	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#distinct(org.hibernate.Criteria)
	 */
	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#distinct(org.hibernate.Criteria)
	 */
	public Criteria distinct(Criteria criteria) {
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria;
	}

	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#getIdName()
	 */
	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#getIdName()
	 */
	public String getIdName() {
		ClassMetadata meta = getSessionFactory().getClassMetadata(entityClass);
		return meta.getIdentifierPropertyName();
	}

	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#isPropertyUnique(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	/* (non-Javadoc)
	 * @see com.cndcsoft.dao.impl.ISimpleHibernateDao#isPropertyUnique(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	public boolean isPropertyUnique(final String propertyName, final Object newValue, final Object oldValue) {
		if (newValue == null || newValue.equals(oldValue)) {
			return true;
		}
		Object object = findUniqueBy(propertyName, newValue);
		return (object == null);
	}
}