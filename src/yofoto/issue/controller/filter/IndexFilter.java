package yofoto.issue.controller.filter;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import yofoto.issue.pojo.Department;
import yofoto.issue.pojo.Staffer;
import yofoto.issue.pojo.Team;
import yofoto.issue.service.impl.DepartmentServiceImpl;
import yofoto.issue.service.impl.StafferServiceImpl;
import yofoto.issue.service.impl.TeamServiceImpl;


/**
 * @author husan
 * @Date 2012-10-29
 * @description
 */

public class IndexFilter implements Filter{
	
	
	private StafferServiceImpl stafferImpl;
	
	private TeamServiceImpl teamImpl;
	
	private DepartmentServiceImpl departImpl;
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest)request;
		req.setCharacterEncoding("utf-8");
		//过滤静态资源
		if(!excludeStaticSource(req.getRequestURI())){
			chain.doFilter(request, response);
		}else{
			HttpSession session = req.getSession();
			if(session == null || session.getAttribute("user") == null){
				Cookie[] cookies = req.getCookies();
				if(cookies!= null){
					boolean isLogin = false;
					String user = null;
					for(Cookie cookie : cookies){
						if("user".equals(cookie.getName())){
							user = cookie.getValue();
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(new Date());
							calendar.add(Calendar.DAY_OF_YEAR, -7);
							isLogin = stafferImpl.isLogin(user, calendar.getTime());
							break;
						}
					}
					
					if(isLogin){
						//doLogin
						/*String name = user.split("!")[0];
						Staffer staffer = stafferImpl.loadByName(name);*/
						Integer sid = Integer.parseInt(user.split("!")[0]);
						Staffer staffer = stafferImpl.load(sid);
						if(staffer!=null){
							if("admin".equals(staffer.getEmail())){
								int teamSize = teamImpl.loadAllSize();
								int departSize = departImpl.loadAllSize();
								session.setAttribute("teams", teamSize);
								session.setAttribute("departments", departSize);
								session.setAttribute("user", staffer);
							}else{
								List<Team> teams = staffer.getTeams();
								List<Department> departments = staffer.getDepartments();
								session.setAttribute("teams", teams);
								//System.out.println(teams.size());
								session.setAttribute("departments", departments);
								session.setAttribute("user", staffer);
								session.setAttribute("tid", teams.get(0).getTid());
							}
						}
					}else{
						//返回登录页面
						HttpServletResponse res = (HttpServletResponse) response;
						String loginURL = req.getContextPath()+"/login";
						if(req.getRequestURI().indexOf(loginURL)==-1){
							res.sendRedirect(loginURL);
							return;
						}
						
					}
			}else{
				HttpServletResponse res = (HttpServletResponse)response;
				String loginURL = req.getContextPath()+"/login";
				if(req.getRequestURI().indexOf(loginURL)==-1){
				
					res.sendRedirect(loginURL);
					return;
				}
					
			}
			
				
			}
			chain.doFilter(request, response);
			
		}
		
	}
	
	private boolean excludeStaticSource(String source){
		Pattern pattern = Pattern.compile("\\.[a-z]*$");
		Matcher matcher = pattern.matcher(source);
		if(matcher.find()){
			return false;
		}
		return true;
	}
	
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext()
				);
		stafferImpl = (StafferServiceImpl)wac.getBean("stafferServiceImpl");
		teamImpl = (TeamServiceImpl)wac.getBean("teamServiceImpl");
		departImpl = (DepartmentServiceImpl) wac.getBean("departmentServiceImpl");
	}
	
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
}
