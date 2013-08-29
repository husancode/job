package yofoto.issue.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.Department;
import yofoto.issue.pojo.Staffer;
import yofoto.issue.service.impl.DepartmentServiceImpl;
import yofoto.issue.service.impl.IssueServiceImpl;
import yofoto.issue.service.impl.IssueWorkflowServiceImpl;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author husan
 * @Date 2012-12-16
 * @description: 任务批量导入
 */
public class IssueimpAction extends ActionSupport{
	
	private static Log logger = LogFactory.getLog(IssueimpAction.class);
	
	@Autowired
	private DepartmentServiceImpl departImpl;
	@Autowired
	private IssueServiceImpl issueImpl;
	@Autowired
	private IssueWorkflowServiceImpl issueWorkflowImpl;
	
	private Integer did;
	
	private Department department;
	
	private File source;
	
	private String contentType;
	
	private String fileName;
	
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
		request.setAttribute("departments", departments);
		return SUCCESS;
	}
	
	public String commit() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		logger.info(staffer.getName()+"批量导入任务。");
		department = departImpl.load(did);
		if(!departImpl.isInDepart(staffer,department)){
			return ERROR;
		}
		
		issueImpl.addBatchIssue(source, department, staffer);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.sendRedirect("myissue?did="+did);
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

	public File getSource() {
		return source;
	}

	public void setSource(File source) {
		this.source = source;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	


}
