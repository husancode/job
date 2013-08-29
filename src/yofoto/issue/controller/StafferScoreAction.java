package yofoto.issue.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import yofoto.issue.util.AuthUtil;
import yofoto.issue.util.DateUtil;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author husan
 * @Date 2013-5-21
 * @description
 */
public class StafferScoreAction extends ActionSupport{
	
	private static Log logger = LogFactory.getLog(StafferScoreAction.class);
	
	@Autowired
	private TeamServiceImpl teamImpl;
	@Autowired
	private StafferScoreServiceImpl stafferScoreImpl;
	
	private Integer tid;
	
	private String month;
	
	private Team team;
	
	private StafferScore stafferScore;
	
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		team = teamImpl.get(tid);
		if(!AuthUtil.isTeamAdmin(staffer, team)){
			return ERROR;
		}
		//默认返回上个月的绩效
		if(month==null||"".equals(month)){
			month = DateUtil.getPrevMonth();
		}
		List<String> months = DateUtil.getMonths(2012);
		List<Team> teams = staffer.getTeams();
		List<StafferScore> result = stafferScoreImpl.getStafferScore(tid,month);
		System.out.println(result.size());
		for(StafferScore score : result){
			System.out.println(score.getAmount());
		}
		request.setAttribute("months", months);
		request.setAttribute("teams", teams);
		request.setAttribute("result", result);
		request.setAttribute("tid", tid);
		request.setAttribute("month",month);
		return SUCCESS;
	}
	
	public String commit() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
	
		team = teamImpl.get(tid);
		
		if(!AuthUtil.isTeamAdmin(staffer, team)){
			return ERROR;
		}
		String result = "success";
		if(!checkForm(stafferScore)){
			result = "fail";
			
		}else{
			stafferScoreImpl.deal(stafferScore);
		}
		
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter pw = new PrintWriter(response.getOutputStream());
		
		pw.write(result);
		pw.flush();
		pw.close();
		return null;
	}
	
	private boolean checkForm(StafferScore stafferScore){
		
		try {
			double amount = Double.parseDouble(stafferScore.getAmount());
			double attitude = Double.parseDouble(stafferScore.getAttitude());
			double coordination = Double.parseDouble(stafferScore.getCoordination());
			double discipline = Double.parseDouble(stafferScore.getDiscipline());
			double progress = Double.parseDouble(stafferScore.getProgress());
			double wordCheck = Double.parseDouble(stafferScore.getWordCheck());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
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

	public StafferScore getStafferScore() {
		return stafferScore;
	}

	public void setStafferScore(StafferScore stafferScore) {
		this.stafferScore = stafferScore;
	}
	
	

}
