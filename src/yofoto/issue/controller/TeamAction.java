package yofoto.issue.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.Department;
import yofoto.issue.pojo.Staffer;
import yofoto.issue.pojo.Team;
import yofoto.issue.service.impl.StafferServiceImpl;
import yofoto.issue.service.impl.TeamServiceImpl;
import yofoto.issue.util.AuthUtil;
import yofoto.issue.util.MyStringUtil;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author husan
 * @Date 2012-10-30
 * @description
 */
@Results({
	@Result(name="projectAdd",location="projectAdd.jsp")
})
public class TeamAction extends ActionSupport{
	
	private static Log logger = LogFactory.getLog(TeamAction.class);
	
	@Autowired
	private TeamServiceImpl teamImpl;
	@Autowired
	private StafferServiceImpl stafferImpl;
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		logger.info(staffer.getName()+"显示项目列表");
		List<Team> teams = null;
		//管理员
		if("admin".equals(staffer.getEmail())){
			teams = teamImpl.loadAll();
		}else{
			teams =  staffer.getTeams();
		}
		
		String sid = String.valueOf(staffer.getSid());
		for(Team team : teams){
			String admin = team.getAdmin();
			String[] admins = admin.split(";");
			
			for(String ad : admins){
				if(sid.equals(ad)){
					team.setAuth(true);
				}
			}
		}
		request.setAttribute("teams", teams);
		return SUCCESS;
	}
	public String add() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer creator = (Staffer) session.getAttribute("user");
		
		if(!creator.getEmail().equals("admin")){
			return "error";
		}
		String teamName = request.getParameter("teamName");
		String newMembers = request.getParameter("newMembers");
		Set<String> emails = MyStringUtil.stringToEmail(newMembers.split("\r\n"));
		Team team = new Team();
		team.setCreator(creator);
		team.setAdmin(creator.getSid()+";");
		team.setName(teamName);
		team.setCreateTime(new Date());
		team.setStatus(1);
		Set<Staffer> stas = new HashSet<Staffer>();
		for(String email : emails){
			Staffer sta = new Staffer();
			sta.setEmail(email);
			sta.setName(email.substring(0, email.indexOf("@")));
			sta.setGeneraTime(new Date());
			sta.setPassword(email);
			sta.setNotify(true);
			sta.setStatus(1);
			stas.add(sta);
		}
		stas = stafferImpl.batchSave(stas);
		team.setStaffers(stas);
		teamImpl.save(team);
		logger.info(creator.getName()+"创建团队："+teamName);
		//更新session
		Integer teamCount = (Integer)session.getAttribute("teams");
		session.setAttribute("teams", teamCount+1);
		return execute();
	}
	
	public String del() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		
		Integer tid = Integer.parseInt(request.getParameter("tid"));
		Team team = teamImpl.load(tid);
		if(!AuthUtil.isTeamAdmin(staffer, team)){
			return ERROR;
		}
		teamImpl.delete(team);
		Integer teamCount = (Integer)session.getAttribute("teams");
		session.setAttribute("teams", teamCount-1);
		logger.info(staffer.getName()+"删除团队："+tid);
		return execute();
	}
	
	public String exit() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		Integer tid = Integer.parseInt(request.getParameter("tid"));
		List<Team> teams = staffer.getTeams();
		List<Department> departments = staffer.getDepartments();
		int count = 0;
		for(Team team : teams){
			if(team.getTid().equals(tid)){
				break;
			}else{
				count++;
			}
		}
		int departRemoveCount = 0;
		int length = departments.size();
		for(int i=length-1;i>=0;i--){
			
			if(departments.get(i).getTeam().getTid().equals(tid)){
				departRemoveCount++;
				departments.remove(i);
			}
		}
		//System.out.println(teams.size());
		teams.remove(count);
		//System.out.println(teams.size());
		staffer.setTeams(teams);
		//session.setAttribute("user", staffer);
		teamImpl.delStaffer(tid, staffer.getSid());
		Integer projectCount = (Integer)session.getAttribute("departments");
		session.setAttribute("departments", projectCount-departRemoveCount);
		Integer teamCount = (Integer)session.getAttribute("teams");
		session.setAttribute("teams", teamCount-1);
		logger.info(staffer.getName()+"退出团队："+tid);
		return execute();
	}
	
	public String addProject()throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		Integer tid = Integer.parseInt(request.getParameter("tid"));
		Team team = teamImpl.load(tid);
		if(!AuthUtil.isTeamAdmin(staffer, team)){
			return ERROR;
		}
		request.setAttribute("team", team);
		
		return "projectAdd";
	}
	
	
	
}
