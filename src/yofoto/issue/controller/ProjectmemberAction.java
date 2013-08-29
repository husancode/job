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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.Department;
import yofoto.issue.pojo.Staffer;
import yofoto.issue.service.impl.DepartmentServiceImpl;
import yofoto.issue.service.impl.StafferServiceImpl;
import yofoto.issue.util.AuthUtil;
import yofoto.issue.util.MyStringUtil;
import yofoto.issue.util.SendMailUtil;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author husan
 * @Date 2012-11-2
 * @description
 */
public class ProjectmemberAction extends ActionSupport{
	
	private static Log logger = LogFactory.getLog(ProjectmemberAction.class);
	
	@Autowired
	private DepartmentServiceImpl departmentImpl;
	@Autowired
	private StafferServiceImpl stafferImpl;
	
	private String ids;
	private Integer did;
	
	private String newMembers;
	
	private List<String> result;
	
	private Set<Staffer> staffers;
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return super.execute();
	}
	
	public String add() throws Exception{
		String[] sids = ids.split(",");
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		logger.info(staffer.getName()+"增加项目成员："+ids);
		Department department = departmentImpl.load(did);
		if(!AuthUtil.isProjectAdmin(staffer, department)){
			return ERROR;
		}
		result = stafferImpl.addProjectMember(sids,did);
		return "json";
	}
	
	public String del() throws Exception{
		//System.out.println(ids);
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		logger.info(staffer.getName()+"删除项目成员："+ids);
		Department department = departmentImpl.load(did);
		if(!AuthUtil.isProjectAdmin(staffer, department)){
			return ERROR;
		}
		stafferImpl.delProjectMember(ids,did);
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter pw = new PrintWriter(response.getOutputStream());
		pw.write("success");
		pw.flush();
		pw.close();
		return null;
	}
	
	public String addAdmin() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		logger.info(staffer.getName()+"增加管理员:"+ids);
		Department department = departmentImpl.load(did);
		if(!AuthUtil.isProjectAdmin(staffer, department)){
			return ERROR;
		}
		stafferImpl.addProjectAdmin(ids,did);
		return null;
	}
	
	public String delAdmin() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		logger.info(staffer.getName()+"取消管理员："+ids);
		Department department = departmentImpl.load(did);
		if(!AuthUtil.isProjectAdmin(staffer, department)){
			return ERROR;
		}
		stafferImpl.delProjectAdmin(ids,did);
		return null;
	}
	
	public String addNewMember() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		logger.info(staffer.getName()+"增加新项目成员："+newMembers);
		Department department = departmentImpl.load(did);
		if(!AuthUtil.isProjectAdmin(staffer, department)){
			return ERROR;
		}
		//System.out.println(newMembers);
		if(null!=newMembers&&!"".equals(newMembers)){
			System.out.println(newMembers);
			Set<String> emails = MyStringUtil.stringToEmail(newMembers.split(","));
			staffers = stafferImpl.addProjectNewMember(department,emails);
			
			//发送邮件通知
			List<String> to = new ArrayList<String>();
			//添加多个成员无法发邮件
			//to.add(newMembers);
			to.addAll(emails);
			SendMailUtil.send(to,"请登录企业执行系统EES","你的用户名和初始密码都是你的QQ邮箱,登录地址<a href='http://ees.yofoto.com.cn/ees' >企业执行系统</a>",null);
		}
		
		return "json";
	}
	

	public List<String> getResult() {
		return result;
	}

	public void setResult(List<String> result) {
		this.result = result;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer getDid() {
		return did;
	}

	public void setDid(Integer did) {
		this.did = did;
	}

	public String getNewMembers() {
		return newMembers;
	}

	public void setNewMembers(String newMembers) {
		this.newMembers = newMembers;
	}

	public Set<Staffer> getStaffers() {
		return staffers;
	}

	public void setStaffers(Set<Staffer> staffers) {
		this.staffers = staffers;
	}

	
}
