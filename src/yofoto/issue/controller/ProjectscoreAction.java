package yofoto.issue.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.Department;
import yofoto.issue.pojo.Issue;
import yofoto.issue.pojo.Staffer;
import yofoto.issue.service.impl.DepartmentServiceImpl;
import yofoto.issue.service.impl.IssueServiceImpl;
import yofoto.issue.service.impl.StafferServiceImpl;
import yofoto.issue.util.AuthUtil;
import yofoto.issue.vo.Page;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author husan
 * @Date 2012-11-23
 * @description : 添加任务评分功能。权限：项目管理员
 */
@Results({
	@Result(name="scoreShow",location="scoreShow.jsp")
})
public class ProjectscoreAction extends ActionSupport{
	
	private static Log logger = LogFactory.getLog(ProjectscoreAction.class);
	
	@Autowired
	private DepartmentServiceImpl departmentImpl;
	@Autowired
	private IssueServiceImpl issueImpl;
	
	private Integer did;
	
	private Page<Issue> page;
	@Override
	public String execute() throws Exception {
		
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		logger.info(staffer.getName()+"进入项目评分页面");
		Department department = departmentImpl.load(did);
		if(!AuthUtil.isProjectAdmin(staffer, department)){
			return ERROR;
		}
		Criterion[] criterion = new Criterion[3];
		criterion[0] = Restrictions.eq("status", 1);
		criterion[1] = Restrictions.eq("departmentID", did);
		criterion[2] = Restrictions.or(Restrictions.eq("completeStatus", 3), Restrictions.eq("completeStatus", 4));
		if(page==null)
			page = new Page();
		page = issueImpl.searchPage(criterion,page);
		List<Department> departments = departmentImpl.getAdminDepartByStaffer(staffer);
		request.setAttribute("departments", departments);
		request.setAttribute("department", department);
		return SUCCESS;
	}
	
	public String score() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		
		Department department = departmentImpl.load(did);
		if(!AuthUtil.isProjectAdmin(staffer, department)){
			return ERROR;
		}
		String issueIds = request.getParameter("issueIds");
		String scores = request.getParameter("scores");
		//评分修改2012-12-5
		issueImpl.score(issueIds,scores);
		logger.info(staffer.getName()+"评分："+issueIds);
		String flag = request.getParameter("flag");
		if(null!=flag&&"scoreShow".equals(flag))
			return scoreShow();
		return execute();
	}
	
	public  String scoreShow() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		logger.info(staffer.getName()+"进入已经评分页面");
		Department department = departmentImpl.load(did);
		if(!AuthUtil.isProjectAdmin(staffer, department)){
			return ERROR;
		}
		Criterion[] criterion = new Criterion[3];
		criterion[0] = Restrictions.eq("status", 1);
		criterion[1] = Restrictions.eq("departmentID", did);
		criterion[2] = Restrictions.eq("completeStatus",5);
		if(page==null)
			page = new Page();
		page = issueImpl.searchPage(criterion,page);
		List<Department> departments = departmentImpl.getAdminDepartByStaffer(staffer);
		request.setAttribute("departments", departments);
		request.setAttribute("department", department);
		return "scoreShow";
	}
	

	public Integer getDid() {
		return did;
	}

	public void setDid(Integer did) {
		this.did = did;
	}

	public Page<Issue> getPage() {
		return page;
	}

	public void setPage(Page<Issue> page) {
		this.page = page;
	}
	
	

}
