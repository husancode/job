package yofoto.issue.controller;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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
import yofoto.issue.util.DateUtil;
import yofoto.issue.vo.Page;

/**
 * @author husan
 * @Date 2013-11-7
 * @description
 */
public class Allissue2Action extends BaseAction{
	private static Log logger = LogFactory.getLog(Allissue2Action.class);
	@Autowired
	private TeamServiceImpl teamImpl;
	@Autowired
	private IssueServiceImpl issueImpl;
	private Team team;
	//页面数据
	private Page<Issue> page;
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		team = teamImpl.load(tid);
		Criterion[] criterion = new Criterion[3];
		criterion[0] = Restrictions.eq("status", 1);
		criterion[1] = Restrictions.eq("teamID", tid);
		criterion[2] = Restrictions.or(Restrictions.eq("secret", Boolean.FALSE),
				Restrictions.and(Restrictions.eq("secret",Boolean.TRUE), Restrictions.or(Restrictions.eq("publisher.sid", staffer.getSid()), Restrictions.eq("completer.sid", staffer.getSid()))));
		if(page==null)
			page = new Page();
		page = issueImpl.searchPage(criterion,page);
		List<Team> teams = staffer.getTeams();
		Set<Staffer> staffers = team.getStaffers();
		//
		
		List<String> months = DateUtil.getMonths(new Date().getYear());
		
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("months", months);
		request.setAttribute("staffers", staffers);
		request.setAttribute("teams", teams);
		return SUCCESS;
		
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
