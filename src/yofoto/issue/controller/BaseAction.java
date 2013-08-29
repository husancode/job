package yofoto.issue.controller;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author husan
 * @Date 2012-10-25
 * @description
 */
public class BaseAction extends ActionSupport {
	protected Integer ids;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		
		return super.execute();
	}

	public Integer getIds() {
		return ids;
	}

	public void setIds(Integer ids) {
		this.ids = ids;
	}
	
}
