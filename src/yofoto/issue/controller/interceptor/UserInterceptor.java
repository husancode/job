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
 * @description 取得用户信息和当前部门信息
 */
public class UserInterceptor extends AbstractInterceptor{
	
	@Override
	public String intercept(ActionInvocation ac) throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		if(staffer!=null)
		ac.getStack().setValue("staffer", staffer);
		//
		Integer tid = 0;
		try {
			 tid = (Integer)session.getAttribute("tid");
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(tid!=null && tid>0)
		ac.getStack().setValue("tid", tid);
		return ac.invoke();
	}

}


