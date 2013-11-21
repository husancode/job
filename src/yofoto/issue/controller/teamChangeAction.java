package yofoto.issue.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

/**
 * @author husan
 * @Date 2013-10-24
 * @description
 */
public class teamChangeAction extends BaseAction{
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		String tid = ServletActionContext.getRequest().getParameter("tid");
		if(tid!=null && !"".equals(tid)){
			HttpSession session = ServletActionContext.getRequest().getSession();
			session.setAttribute("tid", Integer.parseInt(tid));
		}
		String source = ServletActionContext.getRequest().getParameter("source");
		HttpServletResponse response = ServletActionContext.getResponse();
		response.sendRedirect(source);
		return null;
	}

}
