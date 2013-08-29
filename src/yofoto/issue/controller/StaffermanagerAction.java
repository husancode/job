package yofoto.issue.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.Staffer;
import yofoto.issue.service.impl.DepartmentServiceImpl;
import yofoto.issue.service.impl.StafferServiceImpl;
import yofoto.issue.util.AuthUI;
import yofoto.issue.util.Page;

/**
 * @author husan
 * @Date 2013-7-29
 * @description
 */
public class StaffermanagerAction extends JqGridBaseAction<Staffer>{

	private static Log logger = LogFactory.getLog(StaffermanagerAction.class);
	
	@Autowired
	
	private StafferServiceImpl stafferService;
	@Autowired
	private DepartmentServiceImpl  departmentService;
	
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		/*HttpServletRequest request = ServletActionContext.getRequest();
		Res res = ((AuthUI)request.getSession().getAttribute("auth")).getRes();
		if(res!=null){
			String selDepts = GridConvert.gridShow(res.getDepartment());
			request.setAttribute("dept", selDepts);
		}*/
		
		return SUCCESS;
	}
	
	@Override
	public Page<Staffer> listByPage(Page<Staffer> pages) {
		// TODO Auto-generated method stub
		//资源限制
		HttpServletRequest request = ServletActionContext.getRequest();
		AuthUI auth = (AuthUI) request.getSession().getAttribute("auth");
		return stafferService.listByPage(pages,auth);
		
	}
	@Override
	public Page<Staffer> searchPage(Page<Staffer> pages,String filter) {
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		AuthUI auth = (AuthUI) request.getSession().getAttribute("auth");
		return stafferService.searchPage(pages,auth,filter);
	}
	
	public String authStafferView() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("logInfo", "查看用户信息");
		return super.refreshGridModel();
		
	}

	
}
