package yofoto.issue.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.Department;
import yofoto.issue.pojo.Staffer;
import yofoto.issue.service.impl.DepartmentServiceImpl;
import yofoto.issue.service.impl.StafferServiceImpl;
import yofoto.issue.util.AuthUtil;
import yofoto.issue.vo.StafferVO;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author husan
 * @Date 2012-11-1
 * @description
 */
public class ProjecteditAction extends ActionSupport{
	
	@Autowired
	private DepartmentServiceImpl departmentImpl;
	@Autowired
	private StafferServiceImpl stafferImpl;
	private Integer did;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		
		Department department = departmentImpl.load(did);
		if(!AuthUtil.isProjectAdmin(staffer, department)){
			//System.out.println("222222");
			return ERROR;
		}
		Set<Staffer> dStaffer =  department.getStaffers();
		List<StafferVO> stafferVOs = new ArrayList<StafferVO>();
		for(Staffer sta : dStaffer){
			StafferVO stafferVO = new StafferVO();
			PropertyUtils.copyProperties(stafferVO, sta);
			if(AuthUtil.isProjectAdmin(sta, department)){
				stafferVO.setAdmin(true);
			}
			if(department.getCreator().getSid() == sta.getSid()){
				stafferVO.setCreator(true);
			}
			stafferVOs.add(stafferVO);
		}
		List<Department> departments = departmentImpl.getAdminDepartByStaffer(staffer);
		List<Staffer> teamStaffers = stafferImpl.getStafferByTeamID(department.getTeam().getTid());
		request.setAttribute("teamStaffers", teamStaffers);
		request.setAttribute("department", department);
		request.setAttribute("stafferVOs", stafferVOs);
		request.setAttribute("departments", departments);
		return SUCCESS;
		
	}

	public Integer getDid() {
		return did;
	}

	public void setDid(Integer did) {
		this.did = did;
	}
	
	

}
