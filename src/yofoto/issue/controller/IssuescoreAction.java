package yofoto.issue.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.Department;
import yofoto.issue.pojo.Issue;
import yofoto.issue.pojo.IssueWorkflow;
import yofoto.issue.pojo.Staffer;
import yofoto.issue.service.impl.DepartmentServiceImpl;
import yofoto.issue.service.impl.IssueServiceImpl;
import yofoto.issue.service.impl.IssueWorkflowServiceImpl;
import yofoto.issue.util.AuthUtil;
import yofoto.issue.util.IssueAuth;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author husan
 * @Date 2013-5-15
 * @description 单个任务评分
 */
public class IssuescoreAction extends ActionSupport{
	
	private static Log logger = LogFactory.getLog(IssuescoreAction.class);
	
	private Integer id;
	
	@Autowired
	private IssueServiceImpl issueImpl;
	@Autowired
	private IssueWorkflowServiceImpl issueWorkflowImpl;
	@Autowired
	private DepartmentServiceImpl departmentImpl;
	
	private Integer did;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		Issue aIssue = null;
		try{
			aIssue = issueImpl.get(id);
		}catch(Exception ex){
			return ERROR;
		}
		if(aIssue.getCompleteStatus()!=3&&aIssue.getCompleteStatus()!=4){
			return ERROR;
		}
		IssueAuth issueAuth = issueImpl.issueAuth(aIssue,staffer);
		if(!issueAuth.isScore()){
			return ERROR;
		}
		List<IssueWorkflow> issueWorkflows = issueWorkflowImpl.getByIssue(aIssue);
		Department department = departmentImpl.load(aIssue.getDepartmentID());
		
		request.setAttribute("department", department);
		request.setAttribute("aIssue", aIssue);
		request.setAttribute("issueAuth", issueAuth);
		request.setAttribute("issueWorkflows", issueWorkflows);
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
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.sendRedirect("allissue1?tid="+department.getTeam().getTid());
		return null;
		
	}
	
	

	
	public Integer getDid() {
		return did;
	}

	public void setDid(Integer did) {
		this.did = did;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	

}
