package yofoto.issue.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.Department;
import yofoto.issue.pojo.Staffer;
import yofoto.issue.pojo.Team;
import yofoto.issue.service.impl.DepartmentServiceImpl;
import yofoto.issue.service.impl.StafferServiceImpl;
import yofoto.issue.service.impl.TeamServiceImpl;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author husan
 * @Date 2012-10-29
 * @description
 */
public class LoginAction extends ActionSupport{
	//
	private static Log logger = LogFactory.getLog(LoginAction.class);
	@Autowired
	private StafferServiceImpl stafferImpl;
	@Autowired
	private TeamServiceImpl teamImpl;
	@Autowired
	private DepartmentServiceImpl departImpl;
	private String email;
	private String password;
	private String rememberMe;
	private String message;
	//cookie有效时间
	public final static int SECONDS = 7*24*60*60;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return SUCCESS;
	}
	
	public String toLogin() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("login");
		Staffer staffer = stafferImpl.login(email,password);
		logger.info("login execute");
		String result = null;
		if(staffer!=null){
			HttpSession session = ServletActionContext.getRequest().getSession();
			int teamSize=0;
			int departSize=0;
			//admin管理员
			if("admin".equals(staffer.getEmail())){
				teamSize = teamImpl.loadAllSize();
				departSize = departImpl.loadAllSize();
				session.setAttribute("teams", teamSize);
				session.setAttribute("departments", departSize);
				session.setAttribute("user", staffer);
				//设置当前tid
				
			}else{
				List<Team> teams = staffer.getTeams();
				List<Department> departments = staffer.getDepartments();
				session.setAttribute("teams", teams);
				//System.out.println(teams.size());
				session.setAttribute("departments", departments);
				session.setAttribute("user", staffer);
				session.setAttribute("tid", teams.get(0).getTid());
			}
			
			String user = staffer.getSid()+"!"+session.getId();
			stafferImpl.recordLogin(user);
			if(rememberMe!=null&&"on".equals(rememberMe)){
				Cookie cookie = new Cookie("user",user);
				cookie.setPath("/");
				cookie.setMaxAge(SECONDS);
				HttpServletResponse response = ServletActionContext.getResponse();
				response.addCookie(cookie);
			}
			logger.info(staffer.getName()+" login success");
			result = "index";
		}else {
			message = "登陆失败，用户名或密码错误";
			logger.info("user "+email+" login failure");
			result = SUCCESS;
		}
		return result;
		
	}
	public String loginOut() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		logger.info(session.getId()+" loginout");
		session.invalidate();
		//清除cookie
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies){
			if(cookie.getName().equals("user")){
				cookie.setValue("");
				cookie.setMaxAge(0);
				cookie.setPath("/");
				ServletActionContext.getResponse().addCookie(cookie);
			}
		}
		return "login";
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRememberMe() {
		return rememberMe;
	}
	public void setRememberMe(String rememberMe) {
		this.rememberMe = rememberMe;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
