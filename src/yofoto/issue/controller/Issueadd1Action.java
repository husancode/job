package yofoto.issue.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.Staffer;
import yofoto.issue.pojo.Team;
import yofoto.issue.service.impl.DepartmentServiceImpl;
import yofoto.issue.service.impl.IssueServiceImpl;
import yofoto.issue.service.impl.IssueWorkflowServiceImpl;
import yofoto.issue.service.impl.TeamServiceImpl;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author husan
 * @Date 2013-5-12
 * @description  团队管理员权限，批量提交页面
 */
public class Issueadd1Action extends ActionSupport{
	
	private static Log logger = LogFactory.getLog(Issueadd1Action.class);
	
	@Autowired
	private DepartmentServiceImpl departImpl;
	@Autowired
	private IssueServiceImpl issueImpl;
	@Autowired
	private IssueWorkflowServiceImpl issueWorkflowImpl;
	@Autowired
	private TeamServiceImpl teamServiceImpl;
	
	private Integer tid;
	

	
	private Team team;
	
	@Override
	public String execute() throws Exception {
		
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		if(!teamServiceImpl.hasAuth(tid, staffer)){
			return ERROR;
		}
	
		team = teamServiceImpl.load(tid);
	
		List<Team> teams = staffer.getTeams();
		
		request.setAttribute("teams", teams);
		return SUCCESS;
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}


	

}
