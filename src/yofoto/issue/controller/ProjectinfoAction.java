package yofoto.issue.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.Department;
import yofoto.issue.pojo.Issue;
import yofoto.issue.pojo.Staffer;
import yofoto.issue.service.impl.DepartmentServiceImpl;
import yofoto.issue.service.impl.IssueServiceImpl;
import yofoto.issue.service.impl.StafferServiceImpl;
import yofoto.issue.util.DateUtil;
import yofoto.issue.vo.IssueCountVO;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author husan
 * @Date 2012-11-9
 * @description
 */
public class ProjectinfoAction extends ActionSupport{
	
	private static Log logger = LogFactory.getLog(ProjectinfoAction.class);
	
	@Autowired
	private DepartmentServiceImpl departImpl;
	@Autowired
	private IssueServiceImpl issueImpl;
	
	@Autowired
	private StafferServiceImpl staffImpl;
	
	private Integer did;
	
	private Department department;
	
	private int page;
	
	private int pageNum;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		try{
		department = departImpl.load(did);
		}catch(Exception ex){
			ex.printStackTrace();
			return ERROR;
		}
		
		//admin用户,若是,则显示
		if(!"admin".equals(staffer.getName()) && !departImpl.isInDepart(staffer,department)){
			return ERROR;
		}
		logger.info(staffer.getName()+"访问项目信息:"+did);
		List<Department> departments = staffer.getDepartments();
		
		//admin用户,若是,则显示
		if("admin".equals(staffer.getName())){
			departments = departImpl.getAll();
		}
		
		IssueCountVO issueCountVO = issueImpl.getIssueCount(did);
		//ShowField.showFild(issueCountVO);
		Criterion[] criterions = new Criterion[5];
		criterions[0] = Restrictions.eq("status", 1);
		criterions[1] = Restrictions.eq("departmentID", did);
		criterions[2] = Restrictions.eq("completeStatus", 1);
		criterions[3] = Restrictions.ge("priority", 3);
		criterions[4] = Restrictions.or(Restrictions.eq("secret", Boolean.FALSE),
				Restrictions.and(Restrictions.eq("secret",Boolean.TRUE), Restrictions.or(Restrictions.eq("publisher.sid", staffer.getSid()), Restrictions.eq("completer.sid", staffer.getSid()))));
		page = 1;
		pageNum = 8;
		List<Issue> priorityIssues = issueImpl.getIssues(page, pageNum, criterions);
		//System.out.println(priorityIssues.size());
		criterions[2] = null;
		criterions[3] = null;
		List<Issue> toCompleteIssues = issueImpl.getIssues(page, pageNum, criterions);
		//System.out.println(toCompleteIssues.size());
		
		//判断是不明当前登录用户是当前项目的管理员或者是admin用户,若是,则显示保密的工作清单
		boolean isProjectAdmin = staffImpl.isProjectAdmin(staffer.getSid(), did);
		if(isProjectAdmin || "admin".equals(staffer.getName())){
			criterions[0] = Restrictions.eq("status", 1);
			criterions[1] = Restrictions.eq("departmentID", did);
			criterions[2] = Restrictions.eq("completeStatus", 1);
			criterions[3] = Restrictions.ge("priority", 3);
			criterions[4] = Restrictions.or(Restrictions.eq("secret", Boolean.FALSE),
					Restrictions.and(Restrictions.eq("secret",Boolean.TRUE), Restrictions.or(Restrictions.eq("publisher.sid", staffer.getSid()), Restrictions.eq("completer.sid", staffer.getSid()))));
			criterions[4] = null;
			priorityIssues = issueImpl.getIssues(page, pageNum, criterions);
			//System.out.println(priorityIssues.size());
			criterions[2] = null;
			criterions[3] = null;
			toCompleteIssues = issueImpl.getIssues(page, pageNum, criterions);
		}
		//////////
		List<String> months = DateUtil.getMonths(2013);
		request.setAttribute("months", months);
		
		request.setAttribute("departments", departments);
		request.setAttribute("issueCountVO", issueCountVO);
		request.setAttribute("priorityIssues", priorityIssues);
		request.setAttribute("toCompleteIssues", toCompleteIssues);
		return SUCCESS;
	}

	public Integer getDid() {
		return did;
	}

	public void setDid(Integer did) {
		this.did = did;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
	
}
