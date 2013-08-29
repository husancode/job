package yofoto.issue.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.type.IntegerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import yofoto.issue.pojo.Department;
import yofoto.issue.pojo.Staffer;
import yofoto.issue.util.AuthUtil;

/**
 * @author husan
 * @Date 2012-10-20
 * @description
 */
@Service
public class DepartmentServiceImpl extends SimpleHibernateDao<Department,Integer>{
	@Override
	@Autowired(required=true)		
	public void setSessionFactory(@Qualifier("issue") SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	} 
	
	public boolean isInDepart(Staffer staffer,Department department){
	
		Query query = super.getSession().createSQLQuery("select 1 from j_project_staffer t where t.sid =? and t.did=?");
		query.setParameter(0, staffer.getSid());
		query.setParameter(1, department.getDid());
		int size = query.list().size();
		
		if(size>0)return true;
		return false;
	}
	//缓存
	/*public void exitProject(Staffer staffer, Integer did){
		Integer sid = staffer.getSid();
		Query query1 = super.getSession().createSQLQuery("select 1 from project p where p.creator_id=? and p.id=?");
		query1.setParameter(0, sid);
		query1.setParameter(1, did);
		if(query1.list().size()<1){
			Query query2 = super.getSession().createSQLQuery("delete from j_project_staffer jp where jp.sid=? and jp.did=?");
			query2.setParameter(0, sid);
			query2.setParameter(1, did);
			query2.executeUpdate();
		}
		
	}*/
	public void exitProject(Staffer staffer, Integer did){
		Integer sid = staffer.getSid();
		Query query1 = super.getSession().createSQLQuery("select 1 from project p where p.creator_id=? and p.id=?");
		query1.setParameter(0, sid);
		query1.setParameter(1, did);
		if(query1.list().size()<1){
			Query query2 = super.getSession().createSQLQuery("delete from j_project_staffer jp where jp.sid=? and jp.did=?");
			query2.setParameter(0, sid);
			query2.setParameter(1, did);
			query2.executeUpdate();
			for(int i=0; i<staffer.getDepartments().size(); i++){
				if(staffer.getDepartments().get(i).getDid().equals(did)){
					System.out.println("---"+did);
					staffer.getDepartments().remove(i);
				}
			}
		}
		
	}
	
	public List<Department> loadAllDepartments(){
		Query query = super.getSession().createSQLQuery("select * from project p where p.status=1 order by id asc").addEntity(Department.class);
		return query.list();
	}
	
	public List<Department> loadDeparmentsByStaffer(Staffer staffer){
		Query query = super.getSession().createSQLQuery("select  p.* from j_project_staffer jp,project p where jp.did=p.id and jp.sid = ? and p.status=1 order by p.id asc").addEntity(Department.class);
		query.setParameter(0, staffer.getSid());
		return query.list();
	}
	
	public List<Department> getAdminDepartByStaffer(Staffer staffer){
		List<Department> result = null;
		if("admin".equals(staffer.getEmail())){
			Query query = super.getSession().createSQLQuery("select p.* from project p where status=1").addEntity(Department.class);
			result = query.list();
		}else{
			result = new ArrayList<Department>();
		 List<Department> departments = staffer.getDepartments();
		 for(Department de : departments){
			 if(AuthUtil.isProjectAdmin(staffer, de)){
				 result.add(de);
			 }
		 }
		}
		return result;
	}
	
	public List<Department> loadDepartmentsByTeam(Integer tid){
		Query query = super.getSession().
		createSQLQuery("select p.* from project p where p.status=1 and p.teamid=? order by p.id asc")
		.addEntity(Department.class);
		query.setParameter(0, tid);
		return query.list();
	}
	
	
	public int loadAllSize(){
		SQLQuery query = super.getSession().createSQLQuery("select count(1) as AA from project where status=1");
		query.addScalar("AA",new IntegerType());
		Integer result = (Integer)query.uniqueResult();
		return result;
	}
	
	public void save(Department department){
		super.save(department);
	}
	public void delete(Integer id){
		super.delete(id);
	}
	public void delete(Department department){
		super.delete(department);
	}
	public Department get(Integer id){
		return (Department)super.getSession().get(Department.class, id);
	}
	public Department load(Integer id){
		return (Department)super.getSession().load(Department.class, id);
	}
}
