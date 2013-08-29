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
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.Department;
import yofoto.issue.pojo.Staffer;
import yofoto.issue.pojo.Team;
import yofoto.issue.service.impl.DepartmentServiceImpl;
import yofoto.issue.service.impl.StafferServiceImpl;
import yofoto.issue.service.impl.TeamServiceImpl;
import yofoto.issue.util.AuthUtil;
import yofoto.issue.util.MyStringUtil;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author husan
 * @Date 2012-11-1
 * @description
 */
public class ProjectmodAction extends ActionSupport{
	
	private static Log logger = LogFactory.getLog(ProjectmodAction.class);
	
	@Autowired
	private TeamServiceImpl teamImpl;
	@Autowired
	private StafferServiceImpl stafferImpl;
	@Autowired
	private DepartmentServiceImpl departmentImpl;
	private String tid;
	private String projectName;
	private List<String> selectMembers;
	private String newMembers;
	private Integer did;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return super.execute();
	}
	
	public String add() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer creator = (Staffer) session.getAttribute("user");
		logger.info(creator.getName()+"增加新项目："+projectName);
		Team team = teamImpl.load(Integer.parseInt(tid));
		//System.out.println(selectMembers.size());
		if(!AuthUtil.isTeamAdmin(creator, team)){
			return ERROR;
		}
		Department department = new Department();
		department.setName(projectName);
		department.setStatus(1);
		department.setCreator(creator);
		department.setAdmin(team.getAdmin());
		department.setCreateTime(new Date());
		department.setTeam(team);
		Set<Staffer> stas = new HashSet<Staffer>();
		if(newMembers!=null&&!"".equals(newMembers)){
			Set<String> emails = MyStringUtil.stringToEmail(newMembers.split("\r\n"));
			
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
			team.getStaffers().addAll(stas);
			teamImpl.save(team);
		}
		
		
		Set<Staffer> tStaffer = new HashSet<Staffer>();
		if(selectMembers!=null){
			for(String sid : selectMembers){
				Staffer st = stafferImpl.load(Integer.parseInt(sid));
				tStaffer.add(st);
			}
		}
		
		tStaffer.addAll(stas);
		if(!"admin".equals(creator.getEmail()))
		tStaffer.add(creator);
		department.setStaffers(tStaffer);
		departmentImpl.save(department);
		if(!creator.getEmail().equals("admin"))
		creator.getDepartments().add(department);
		Integer projectCount = (Integer)session.getAttribute("departments");
		session.setAttribute("departments", projectCount+1);
		return "index";
	}
	
	public String modName()throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		logger.info(staffer.getName()+"修改项目名："+projectName);
		Department department = departmentImpl.load(did);
		if(!AuthUtil.isProjectAdmin(staffer, department)){
			return ERROR;
		}
		department.setName(projectName);
		departmentImpl.save(department);
		return null;
	}
	
	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public List<String> getSelectMembers() {
		return selectMembers;
	}

	public void setSelectMembers(List<String> selectMembers) {
		this.selectMembers = selectMembers;
	}

	public String getNewMembers() {
		return newMembers;
	}

	public void setNewMembers(String newMembers) {
		this.newMembers = newMembers;
	}

	public Integer getDid() {
		return did;
	}

	public void setDid(Integer did) {
		this.did = did;
	}
	
}
