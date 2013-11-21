package yofoto.issue.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.service.impl.SubjectService;

/**
 * @author husan
 * @Date 2013-11-7
 * @description
 */
public class PlanAction extends BaseAction{
	@Autowired
	private SubjectService subjectService;
	
	private Integer id;
	
	public String authEdit(){
		subjectService.confirm(id);
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			response.sendRedirect("planmonth");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	

}
