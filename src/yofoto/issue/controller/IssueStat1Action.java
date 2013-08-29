package yofoto.issue.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.Staffer;
import yofoto.issue.pojo.StafferScore;
import yofoto.issue.pojo.Team;
import yofoto.issue.service.impl.StafferScoreServiceImpl;
import yofoto.issue.service.impl.TeamServiceImpl;
import yofoto.issue.util.DateUtil;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author husan
 * @Date 2013-5-21
 * @description
 */
public class IssueStat1Action extends ActionSupport{
	
	private static Log logger = LogFactory.getLog(IssueStat1Action.class);
	@Autowired
	private TeamServiceImpl teamImpl;
	@Autowired
	private StafferScoreServiceImpl stafferScoreImpl;
	
	private Integer tid;
	
	private String month;
	
	private Team team;
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		team = teamImpl.get(tid);
		if(!teamImpl.isInTeam(staffer, team)){
			return ERROR;
		}
		//默认返回上个月的绩效
		if(month==null||"".equals(month)){
			month = DateUtil.getPrevMonth();
		}
		List<String> months = DateUtil.getMonths(2012);
		List<Team> teams = staffer.getTeams();
		List<StafferScore> result = stafferScoreImpl.getStafferScoreList(tid,month);
		request.setAttribute("months", months);
		request.setAttribute("teams", teams);
		request.setAttribute("result", result);
		return SUCCESS;
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
	
	
	
	

}
