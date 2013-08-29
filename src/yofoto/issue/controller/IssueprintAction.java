package yofoto.issue.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.Issue;
import yofoto.issue.pojo.Staffer;
import yofoto.issue.pojo.Team;
import yofoto.issue.service.impl.IssueServiceImpl;
import yofoto.issue.service.impl.TeamServiceImpl;
import yofoto.issue.util.IssueAuth;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author husan
 * @Date 2013-5-21
 * @description
 */
public class IssueprintAction extends ActionSupport{
	private static Log logger = LogFactory.getLog(IssueprintAction.class);
	
	@Autowired
	private IssueServiceImpl issueImpl;
	@Autowired
	private TeamServiceImpl teamImpl;
	
	private Integer id;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		Issue issue = issueImpl.get(id);
		IssueAuth issueAuth = issueImpl.issueAuth(issue,staffer);
		if(!issueAuth.isView()){
			return ERROR;
		}
		logger.info(staffer.getName()+"查看任务单:"+issue.getId());
		Team team = teamImpl.get(issue.getTeamID());
		request.setAttribute("issue", issue);
		request.setAttribute("team", team);
		return SUCCESS;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	

}
