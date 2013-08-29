package yofoto.issue.controller;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.Department;
import yofoto.issue.pojo.Staffer;
import yofoto.issue.service.impl.DepartmentServiceImpl;
import yofoto.issue.util.AuthUtil;
import yofoto.issue.util.MyStringUtil;
import yofoto.issue.vo.DepartmentVO;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author husan
 * @Date 2012-10-30
 * @description
 */

public class ProjectAction extends ActionSupport{
	
	private static Log logger = LogFactory.getLog(ProjectAction.class);
	
	@Autowired
	private DepartmentServiceImpl departmentImpl;
	
	private Integer tid;
	
	private Integer did;
	
	private List<DepartmentVO> departmentVOs;
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		logger.info(staffer.getName()+"打开项目列表");
		List<Department> departments = null;
		if(staffer.getEmail().equals("admin")){
			departments = departmentImpl.loadAllDepartments();
		}else
			//当通过sql删除一个项目，页面刷新无更新，访问缓存未访问数据库
		departments = staffer.getDepartments();
		departmentVOs = new ArrayList<DepartmentVO>();
		for(Department de : departments){
			DepartmentVO departVO = new DepartmentVO();
			PropertyUtils.copyProperties(departVO, de);
			if(de.getCreator().getSid().equals(staffer.getSid())){
				departVO.setIfCreator(true);
			}
			if(AuthUtil.isProjectAdmin(staffer, de)){
				departVO.setIfAdmin(true);
			}
			departmentVOs.add(departVO);
		}
		request.setAttribute("departments", departments);
		return SUCCESS;
	}
	
	public String showByTeam() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		logger.info(staffer.getName()+"打开团队下的项目列表");
		List<Department> departments = null;
		if("admin".equals(staffer.getEmail())){
			departments = departmentImpl.loadDepartmentsByTeam(tid);
		}else{
			departments = departmentImpl.loadDeparmentsByStaffer(staffer);
		}
		departmentVOs = new ArrayList<DepartmentVO>();
		for(Department de : departments){
			
			if(de.getTeam().getTid().equals(tid)){
				
				DepartmentVO departVO = new DepartmentVO();
				PropertyUtils.copyProperties(departVO, de);
				if(de.getCreator().getSid().equals(staffer.getSid())){
					departVO.setIfCreator(true);
				}
				if(AuthUtil.isProjectAdmin(staffer, de)){
					departVO.setIfAdmin(true);
				}
				departmentVOs.add(departVO);
			}
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
		pw.write(MyStringUtil.toHtml(departmentVOs,tid,staffer));
		pw.flush();
		pw.close();
		return null;
	}
	
	public String exit() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		logger.info(staffer.getName()+"退出项目:"+did);
		departmentImpl.exitProject(staffer,did);
		Integer projectCount = (Integer)session.getAttribute("departments");
		session.setAttribute("departments", projectCount-1);
		return null;
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public List<DepartmentVO> getDepartmentVOs() {
		return departmentVOs;
	}

	public void setDepartmentVOs(List<DepartmentVO> departmentVOs) {
		this.departmentVOs = departmentVOs;
	}

	public Integer getDid() {
		return did;
	}

	public void setDid(Integer did) {
		this.did = did;
	}
	
	
	
	

}
