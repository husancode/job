package yofoto.issue.controller;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import yofoto.issue.pojo.Team;
import yofoto.issue.service.impl.DepartmentServiceImpl;
import yofoto.issue.service.impl.IssueServiceImpl;
import yofoto.issue.service.impl.IssueWorkflowServiceImpl;
import yofoto.issue.service.impl.TeamServiceImpl;
import yofoto.issue.util.ConfigUtil;

import com.opensymphony.xwork2.ActionSupport;


/**
 * @author husan
 * @Date 2013-5-13
 * @description
 */
public class Issueadd2Action extends ActionSupport{
	
	private static Log logger = LogFactory.getLog(Issueadd2Action.class);
	
	@Autowired
	private DepartmentServiceImpl departImpl;
	@Autowired
	private IssueServiceImpl issueImpl;
	@Autowired
	private IssueWorkflowServiceImpl issueWorkflowImpl;
	@Autowired
	private TeamServiceImpl teamServiceImpl;
	
	private Integer did;
	
	private Department department;
	
	private Integer tid;
	
	private Team team;
	
	private Integer modular;
	
	private String title;
	
	private Integer completer;
	
	private String coord;
	
	private String subject;
	
	private Date expireDate;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		department = departImpl.load(did);
		if(!departImpl.isInDepart(staffer,department)){
			return ERROR;
		}
		List<Department> departments = staffer.getDepartments();
		List<Modular> modulars = department.getModualrs();
		//System.out.println(modulars.size());
		
		request.setAttribute("departments", departments);
		request.setAttribute("modulars", modulars);
		team = teamServiceImpl.load(tid);
		List<Team> teams = staffer.getTeams();
		
		request.setAttribute("teams", teams);
		return SUCCESS;
	}
	
	public String add() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		//System.out.println(request.getParameter("description"));
		try{
		department = departImpl.load(did);
		}catch(Exception ex){
			return ERROR;
		}
		if(!departImpl.isInDepart(staffer,department)){
			return ERROR;
		}
		Issue issue = new Issue();
		issue.setTitle(title);
		issue.setPriority(1);
		issue.setCompleteStatus(1);
		issue.setDepartmentID(department.getDid());
		//2012-12-4 
		issue.setTeamID(department.getTeam().getTid());
		//2012-12-15
		if(request.getParameter("secret")!=null)
			issue.setSecret(true);
		//默认作为计划任务
		issue.setIssueType(1);
		issue.setPublisher(staffer);
		issue.setReviewer(staffer);
		issue.setPublishDate(new Date());
		issue.setSubject(subject);
		//expiredate with 23:59:59
		DateFormat formatWithTime = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");  
		SimpleDateFormat   sDateFormate=new SimpleDateFormat("yyyy/MM/dd");
		java.util.Date newExpireTime = formatWithTime.parse(sDateFormate.format(expireDate)+" 23:59:59");
		issue.setExprireDate(newExpireTime);
		issue.setStatus(1);
		// 设置任务系数 ，默认1
		issue.setPercentage(ConfigUtil.percentage);
		IssueWorkflow issueWorkflow = new IssueWorkflow();
		issueWorkflow.setCompleteStatus(1);
		issueWorkflow.setDealTime(new Date());
		issueWorkflow.setFromStaffer(staffer);
		String content = request.getParameter("content");
		issueWorkflow.setContent(content);
		issueImpl.addIssue(staffer, issue, completer,coord, modular,issueWorkflow);
		//发送短信
		logger.info(staffer.getName()+"发布新任务："+issue.getTitle());
		String result = String.valueOf(issue.getId());
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter pw = new PrintWriter(response.getOutputStream());
		
		pw.write(result);
		pw.flush();
		pw.close();
		return null;
	}

	public Integer getDid() {
		return did;
	}

	public void setDid(Integer did) {
		this.did = did;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
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

	public Integer getModular() {
		return modular;
	}

	public void setModular(Integer modular) {
		this.modular = modular;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getCompleter() {
		return completer;
	}

	public void setCompleter(Integer completer) {
		this.completer = completer;
	}

	public String getCoord() {
		return coord;
	}

	public void setCoord(String coord) {
		this.coord = coord;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
}
