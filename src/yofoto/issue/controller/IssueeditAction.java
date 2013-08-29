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
import yofoto.issue.pojo.Modular;
import yofoto.issue.pojo.Staffer;
import yofoto.issue.service.impl.DepartmentServiceImpl;
import yofoto.issue.service.impl.IssueServiceImpl;
import yofoto.issue.service.impl.IssueWorkflowServiceImpl;
import yofoto.issue.util.IssueAuth;
import yofoto.issue.util.ShowField;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author husan
 * @Date 2012-11-13
 * @description
 */
public class IssueeditAction extends ActionSupport{
	
	private static Log logger = LogFactory.getLog(IssueeditAction.class);
	
	@Autowired
	private DepartmentServiceImpl departImpl;
	@Autowired
	private IssueServiceImpl issueImpl;
	@Autowired
	private IssueWorkflowServiceImpl issueWorkflowImpl;
	private Integer id;
	
	private Issue aissue;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		Issue aIssue = issueImpl.get(id);
		//System.out.println(aIssue.getTitle());
		IssueAuth issueAuth = issueImpl.issueAuth(aIssue,staffer);
		if(!issueAuth.isAdmin()){
			return ERROR;
		}
		Department department = departImpl.load(aIssue.getDepartmentID());
		IssueWorkflow issueWorkflow = issueWorkflowImpl.getContent(aIssue.getId());
		List<Modular> modulars = department.getModualrs();
		request.setAttribute("department", department);
		request.setAttribute("modulars", modulars);
		request.setAttribute("aIssue", aIssue);
		request.setAttribute("issueWorkflow", issueWorkflow);
		return SUCCESS;
	}
	
	public String commit() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		Issue aIssue = issueImpl.get(aissue.getId());
		IssueAuth issueAuth = issueImpl.issueAuth(aIssue,staffer);
		if(!issueAuth.isAdmin()){
			return ERROR;
		}
		logger.info(staffer.getName()+" 修改了任务:"+aIssue.getTitle());
		String content = request.getParameter("description");
		Integer issueWFID = Integer.parseInt(request.getParameter("wfid"));
		
		IssueWorkflow issueWF = issueWorkflowImpl.get(issueWFID);
		issueWF.setContent(content);
		//重新分配
		String note =  null;
		if(aissue.getCompleter() != null){
			note = request.getParameter("content");
		}
		if(aIssue.getCompleteStatus()<3){
			issueImpl.dealEdit(aIssue,aissue,issueWF,note);
			if(aissue.getCompleter()!=null){
				//发送短信
			}
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.sendRedirect("issuehandle?id="+aIssue.getId());
		return null;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Issue getAissue() {
		return aissue;
	}

	
	public void setAissue(Issue aissue) {
		this.aissue = aissue;
	}

	
	
}
