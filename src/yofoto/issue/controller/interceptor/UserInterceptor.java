package yofoto.issue.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import yofoto.issue.pojo.Staffer;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * @author husan
 * @Date 2013-9-3
 * @description
 */
public class UserInterceptor extends AbstractInterceptor{
	
	@Override
	public String intercept(ActionInvocation ac) throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		ac.getStack().setValue("staffer", staffer);
		return ac.invoke();
	}

}


