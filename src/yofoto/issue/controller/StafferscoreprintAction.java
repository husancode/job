package yofoto.issue.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.Staffer;
import yofoto.issue.pojo.Team;
import yofoto.issue.service.impl.IssueServiceImpl;
import yofoto.issue.service.impl.StafferServiceImpl;
import yofoto.issue.service.impl.TeamServiceImpl;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author husan
 * @Date 2013-5-21
 * @description
 */
@Results({
	@Result(name="adminscoreprint",location="adminscoreprint.jsp")
})
public class StafferscoreprintAction extends ActionSupport{
	
	private static Log logger = LogFactory.getLog(StafferscoreprintAction.class);
	
	@Autowired
	private TeamServiceImpl teamImpl;
	@Autowired
	private IssueServiceImpl issueImpl;
	@Autowired
	private StafferServiceImpl stafferServiceImpl;
	
	private Integer sid;
	
	private Integer tid;
	
	private String month;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		Team team = teamImpl.load(tid);
		if(!teamImpl.isInTeam(staffer, team)){
			return ERROR;
		}
		Staffer user = stafferServiceImpl.get(sid);
		Map result = issueImpl.getScoreList(month,sid);
		request.setAttribute("staffer", user);
		request.setAttribute("team", team);
		request.setAttribute("result", result);
		if(user.isProjectAdmin())
		return "adminscoreprint";
		else
		return SUCCESS;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

}
