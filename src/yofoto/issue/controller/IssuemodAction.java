package yofoto.issue.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.Issue;
import yofoto.issue.pojo.Staffer;
import yofoto.issue.service.impl.IssueServiceImpl;
import yofoto.issue.util.IssueAuth;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author husan
 * @Date 2013-5-14
 * @description
 */
public class IssuemodAction extends ActionSupport{
	
	private static Log logger = LogFactory.getLog(IssueeditAction.class);
	
	@Autowired
	private IssueServiceImpl issueImpl;
	
	private Integer id;
	
	private Issue aissue;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		Issue aIssue = issueImpl.get(id);
		IssueAuth issueAuth = issueImpl.issueAuth(aIssue,staffer);
		if(!issueAuth.isTeamAdmin()){
			return ERROR;
		}
		
		request.setAttribute("aIssue", aIssue);
		
		
		return SUCCESS;
	}
	
	public String commit() throws Exception{
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		Issue aIssue = issueImpl.get(aissue.getId());
		IssueAuth issueAuth = issueImpl.issueAuth(aIssue,staffer);
		if(!issueAuth.isTeamAdmin()){
			return ERROR;
		}
		logger.info(staffer.getName()+" 调整了任务:"+aIssue.getTitle());
		issueImpl.modIssue(aIssue, aissue);
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
