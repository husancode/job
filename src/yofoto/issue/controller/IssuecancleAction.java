package yofoto.issue.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.Issue;
import yofoto.issue.pojo.Staffer;
import yofoto.issue.service.impl.IssueServiceImpl;
import yofoto.issue.util.IssueAuth;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.logging.Logger;

/**
 * @author husan
 * @Date 2013-5-29
 * @description
 */
public class IssuecancleAction extends ActionSupport{
	
	private static Log logger = LogFactory.getLog(IssuecancleAction.class);
	
	private Integer id;
	

	@Autowired
	private IssueServiceImpl issueImpl;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
	
		Issue aIssue = null;
		try{
			aIssue = issueImpl.get(id);
		}catch(Exception ex){
			return ERROR;
		}
		if(aIssue.getCompleteStatus() >= 3){
			return ERROR;
		}
		
		IssueAuth issueAuth = issueImpl.issueAuth(aIssue,staffer);
		if(!issueAuth.isProAdmin() && !issueAuth.isTeamAdmin()){
			return ERROR;
		}
		logger.info(staffer.getName()+"取消任务:"+aIssue.getId());
		issueImpl.cancle(aIssue);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter pw = new PrintWriter(response.getOutputStream());
		pw.write("true");
		pw.flush();
		pw.close();
		return null;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	

}
