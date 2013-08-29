package yofoto.issue.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.Staffer;
import yofoto.issue.pojo.Team;
import yofoto.issue.service.impl.StafferServiceImpl;
import yofoto.issue.service.impl.TeamServiceImpl;
import yofoto.issue.util.AuthUtil;
import yofoto.issue.util.MyStringUtil;
import yofoto.issue.vo.StafferVO;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author husan
 * @Date 2012-10-30
 * @description
 */
public class TeammodAction extends ActionSupport{
	
	private static Log logger = LogFactory.getLog(TeammodAction.class);
	
	@Autowired
	private TeamServiceImpl teamImpl;
	@Autowired
	private StafferServiceImpl stafferImpl;
	
	public String execute() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		String ids = request.getParameter("ids");
		Team team = teamImpl.load(Integer.parseInt(ids));
		if(!AuthUtil.isTeamAdmin(staffer, team)){
			return ERROR;
		}
		
		Set<Staffer> staffers = team.getStaffers();
		//Set<Staffer> staffers = teamImpl.getStaffers(team.getTid());
	
		List<StafferVO> stafferVOs = new ArrayList<StafferVO>();
		for(Staffer aStaffer : staffers){
			StafferVO stafferVO = new StafferVO();
			PropertyUtils.copyProperties(stafferVO, aStaffer);
			if(AuthUtil.isTeamAdmin(aStaffer, team)){
				stafferVO.setAdmin(true);
			}
			if(team.getCreator().getSid()==aStaffer.getSid()){
				stafferVO.setCreator(true);
			}
			stafferVOs.add(stafferVO);
		}
		request.setAttribute("staffers", stafferVOs);
		request.setAttribute("team", team);
		return SUCCESS;
	}
	public String delStaffer() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		Integer sid = Integer.parseInt(request.getParameter("sid"));
		Integer tid = Integer.parseInt(request.getParameter("tid"));
		String result = "fail";
		if(!teamImpl.hasAuth(tid, staffer)){
			result = "auth";
		}else{
			teamImpl.delStaffer(tid, sid);
			result = "success";
		}
		logger.info(staffer.getName()+" delStaffer:"+sid);
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter pw = new PrintWriter(response.getOutputStream());
		pw.write(result);
		pw.flush();
		pw.close();
		return null;
	}
	public String addStaffer()throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		String newMembers = request.getParameter("newMembers");
		Integer tid = Integer.parseInt(request.getParameter("tid"));
		if(!teamImpl.hasAuth(tid, staffer)){
			return ERROR;
		}
		Set<String> emails = MyStringUtil.stringToEmail(newMembers.split("\r\n"));
		Team team = teamImpl.load(tid);
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
		/*List<Staffer> staffers = team.getStaffers();
		for(Staffer aStaffer : stas){
			if(aStaffer.getEmail())
		}*/
		Set<Staffer> staffers = team.getStaffers();
		for(Staffer sta : stas){
			if(!staffers.contains(sta)){
				staffers.add(sta);
			}
		}
		team.setStaffers(staffers);
		teamImpl.save(team);
		logger.info(staffer.getName()+" addStaffer:"+newMembers);
		HttpServletResponse response = ServletActionContext.getResponse();
		String forword = request.getServletPath();
		response.sendRedirect("teammod?ids="+tid);
		return null;
	}
	
	public String modName() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		String teamName = request.getParameter("teamName");
		Integer tid = Integer.parseInt(request.getParameter("tid"));
		String result = "fail";
		if(!teamImpl.hasAuth(tid, staffer)){
			result = "auth";
		}else{
			Team team = teamImpl.load(tid);
			team.setName(teamName);
			teamImpl.save(team);
			result = "success";
		}
		logger.info(staffer.getName()+" mod team name to:"+teamName);
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter pw = new PrintWriter(response.getOutputStream());
		pw.write(result);
		pw.flush();
		pw.close();
		return null;
	}
	
	public String setAdmin() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		String sid = request.getParameter("sid");
		Integer tid = Integer.parseInt(request.getParameter("tid"));
		String flag = request.getParameter("flag");
		String result = "fail";
		if(!teamImpl.hasAuth(tid, staffer)){
			result = "auth";
		}else{
			Team team = teamImpl.load(tid);
			String admin = null;
			if("checked".equals(flag)){
				admin = AuthUtil.setAuth(sid, team.getAdmin(), false);
			}else{
				admin = AuthUtil.setAuth(sid, team.getAdmin(), true);
			}
			team.setAdmin(admin);
			teamImpl.save(team);
			result = "success";
		}
		logger.info(staffer.getName()+" set team admin:"+sid);
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter pw = new PrintWriter(response.getOutputStream());
		pw.write(result);
		pw.flush();
		pw.close();
		return null;
	}
	
}
