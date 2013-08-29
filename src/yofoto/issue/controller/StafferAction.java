package yofoto.issue.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.Staffer;
import yofoto.issue.service.impl.StafferServiceImpl;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author husan
 * @Date 2012-11-3
 * @description
 */
public class StafferAction extends ActionSupport{
	
	private static Log logger = LogFactory.getLog(StafferAction.class);
	
	@Autowired
	private StafferServiceImpl stafferImpl;
	private Staffer staffer;
	
	private String notify;
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return SUCCESS;
	}
	
	public String mod() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer user = (Staffer)session.getAttribute("user");
		logger.info(user.getName()+"修改个人信息");
		user.setName(staffer.getName());
		if(staffer.getPassword()!=null&&!"".equals(staffer.getPassword())){
			user.setPassword(staffer.getPassword());
		}
		
		user.setTelphone(staffer.getTelphone());
		
		user.setNotify(staffer.isNotify());
		stafferImpl.save(user);
		return "index";
	}

	public Staffer getStaffer() {
		return staffer;
	}

	public void setStaffer(Staffer staffer) {
		this.staffer = staffer;
	}

	public String getNotify() {
		return notify;
	}

	public void setNotify(String notify) {
		this.notify = notify;
	}
	
	

}
