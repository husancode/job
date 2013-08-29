package yofoto.issue.controller;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.Department;
import yofoto.issue.pojo.Modular;
import yofoto.issue.pojo.Staffer;
import yofoto.issue.service.impl.DepartmentServiceImpl;
import yofoto.issue.service.impl.ModularServiceImpl;
import yofoto.issue.service.impl.StafferServiceImpl;
import yofoto.issue.util.AuthUtil;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author husan
 * @Date 2012-11-3
 * @description
 */
public class ModuleAction extends ActionSupport{
	
	private static Log logger = LogFactory.getLog(ModuleAction.class);
	
	@Autowired
	private DepartmentServiceImpl departmentImpl;
	@Autowired
	private ModularServiceImpl modularImpl;
	@Autowired
	private StafferServiceImpl stafferImpl;
	private String name;
	private Integer responsible;
	private Integer did;
	private Integer percentage;
	private Double score;
	private Integer mid;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return super.execute();
	}
	
	public String add() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		logger.info(staffer.getName()+"添加模块："+name);
		Department department = departmentImpl.load(did);
		if(!AuthUtil.isProjectAdmin(staffer, department)){
			return ERROR;
		}
		String result = null;
		if(name==null||"".equals(name)){
			result = "fail";
		}else{
			Modular modular = new Modular();
			modular.setName(name);
			modular.setDepartment(department);
			modular.setScore(score);
			modular.setStatus(1);
			modular.setPercentage(percentage);
			Staffer responseStaffer = stafferImpl.load(responsible);
			modular.setResponsibleStaffer(responseStaffer);
			modular.setCreateTime(new Date());
			modularImpl.save(modular);
			result = String.valueOf(modular.getId());
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter pw = new PrintWriter(response.getOutputStream());
		pw.write(result);
		pw.flush();
		pw.close();
		return null;
	}
	
	public String del() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		logger.info(staffer.getName()+"删除模块："+mid);
		Department department = departmentImpl.load(did);
		if(!AuthUtil.isProjectAdmin(staffer, department)){
			return ERROR;
		}
		modularImpl.deleteByID(mid,did);
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter pw = new PrintWriter(response.getOutputStream());
		pw.write("success");
		pw.flush();
		pw.close();
		return null;
	}
	
	public String update() throws Exception{
		//System.out.println("sdfsdf");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		logger.info(staffer.getName()+"修改模块："+mid);
		Department department = departmentImpl.load(did);
		if(!AuthUtil.isProjectAdmin(staffer, department)){
			return ERROR;
		}
		Modular modular = modularImpl.load(mid);
		modular.setName(name);
		modular.setScore(score);
		modular.setPercentage(percentage);
		Staffer responseStaffer = stafferImpl.load(responsible);
		modular.setResponsibleStaffer(responseStaffer);
		modularImpl.save(modular);
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter pw = new PrintWriter(response.getOutputStream());
		pw.write("success");
		pw.flush();
		pw.close();
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getResponsible() {
		return responsible;
	}

	public void setResponsible(Integer responsible) {
		this.responsible = responsible;
	}

	public Integer getDid() {
		return did;
	}

	public void setDid(Integer did) {
		this.did = did;
	}

	public Integer getPercentage() {
		return percentage;
	}

	public void setPercentage(Integer percentage) {
		this.percentage = percentage;
	}

	

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}
	
	
	
}
