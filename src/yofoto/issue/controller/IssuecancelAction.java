package yofoto.issue.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.Issue;
import yofoto.issue.service.impl.IssueServiceImpl;

/**
 * @author husan
 * @Date 2013-11-11
 * @description 任务取消
 */
@Results({
	@Result(name="cancelqr",location="cancelqr.jsp")
})
public class IssuecancelAction extends BaseAction{
	
	private static Log logger = LogFactory.getLog(IssuecancelAction.class);
	
	@Autowired
	private IssueServiceImpl issueService;
	
	private Integer id;
	
	private Issue aissue;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		Issue issue = issueService.load(id);
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("issue", issue);
		// TODO Auto-generated method stub
		return SUCCESS;
	}
	
	//申请取消任务
	public String cancel() throws Exception{
		issueService.cancel(aissue);
		return SUCCESS;
	}
	// 取消确认页面
	public String cancelqr() throws Exception{
		Issue issue = issueService.load(id);
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("issue", issue);
		return "cancelqr";
	}
	//任务取消操作
	public String docancelqr() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		String flag = request.getParameter("flag");
	
		int result = issueService.docancel(id,flag);
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter pw = new PrintWriter(response.getOutputStream());
		pw.write(String.valueOf(result));
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

	public Issue getAissue() {
		return aissue;
	}

	public void setAissue(Issue aissue) {
		this.aissue = aissue;
	}

}
