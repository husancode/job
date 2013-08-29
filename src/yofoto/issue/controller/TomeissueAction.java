package yofoto.issue.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.Department;
import yofoto.issue.pojo.Issue;
import yofoto.issue.pojo.Staffer;
import yofoto.issue.service.impl.DepartmentServiceImpl;
import yofoto.issue.service.impl.IssueServiceImpl;
import yofoto.issue.vo.Page;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author husan
 * @Date 2012-11-12
 * @description
 */
public class TomeissueAction extends ActionSupport{
	@Autowired
	private DepartmentServiceImpl departImpl;
	@Autowired
	private IssueServiceImpl issueImpl;
	
	private Integer did;
	
	private Department department;
	
	private Page<Issue> page;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		department = departImpl.load(did);
		if(!departImpl.isInDepart(staffer,department)){
			return ERROR;
		}
		Criterion[] criterion = new Criterion[3];
		criterion[0] = Restrictions.eq("status", 1);
		criterion[1] = Restrictions.eq("departmentID", did);
		criterion[2] = Restrictions.eq("completer.sid",staffer.getSid());
		if(page == null)
			page = new Page();
		page = issueImpl.searchPage(criterion,page);
		List<Department> departments = staffer.getDepartments();
		request.setAttribute("departments", departments);
		return SUCCESS;
	}

	public Integer getDid() {
		return did;
	}

	public void setDid(Integer did) {
		this.did = did;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Page<Issue> getPage() {
		return page;
	}

	public void setPage(Page<Issue> page) {
		this.page = page;
	}
	
	

}
