package yofoto.issue.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.Issue;
import yofoto.issue.service.impl.IssueServiceImpl;

/**
 * @author husan
 * @Date 2013-11-7
 * @description
 */
@Results({
	@Result(name="yqqr",location="yqqr.jsp")
})
public class IssueexpireAction extends BaseAction{
	
	@Autowired
	private IssueServiceImpl issueService;
	
	private Integer id;
	
	private Issue aissue;
	public Issue getAissue() {
		return aissue;
	}


	public void setAissue(Issue aissue) {
		this.aissue = aissue;
	}


	@Override
	public String execute() throws Exception {
		Issue issue = issueService.load(id);
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("issue", issue);
		// TODO Auto-generated method stub
		return SUCCESS;
	}
	
	
	public String sqyy() throws Exception{
		
		issueService.sqyy(aissue);
		return SUCCESS;
	}
	
	public String yqqr() throws Exception{
		Issue issue = issueService.load(id);
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("issue", issue);
		return "yqqr";
	}
	
	public String tyyq() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		String flag = request.getParameter("flag");
	
		int result = issueService.tyyq(id,flag);
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



	
	

}
