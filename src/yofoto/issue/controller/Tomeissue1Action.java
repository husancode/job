package yofoto.issue.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.Issue;
import yofoto.issue.pojo.Staffer;
import yofoto.issue.pojo.Team;
import yofoto.issue.service.impl.IssueServiceImpl;
import yofoto.issue.service.impl.TeamServiceImpl;
import yofoto.issue.vo.Page;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author husan
 * @Date 2012-12-5
 * @description
 */
public class Tomeissue1Action extends ActionSupport{
	
	
	@Autowired
	private TeamServiceImpl teamImpl;
	@Autowired
	private IssueServiceImpl issueImpl;
	
	private Integer tid;
	
	private Team team;
	
	private Page<Issue> page;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		
		team = teamImpl.load(tid);
		if(!teamImpl.isInTeam(staffer, team)){
			return ERROR;
		}
		Criterion[] criterion = new Criterion[3];
		criterion[0] = Restrictions.eq("status", 1);
		criterion[1] = Restrictions.eq("teamID", tid);
		criterion[2] = Restrictions.eq("completer.sid",staffer.getSid());
		if(page==null)
			page = new Page();
		//System.out.println("-------"+page.getDepartmentid());
		page = issueImpl.searchPage(criterion,page);
		
		List<Team> teams = staffer.getTeams();
		Set<Staffer> staffers = team.getStaffers();
		
		request.setAttribute("staffers", staffers);
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

	public Page<Issue> getPage() {
		return page;
	}

	public void setPage(Page<Issue> page) {
		this.page = page;
	}
	
	
}
