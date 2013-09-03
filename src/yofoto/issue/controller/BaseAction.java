package yofoto.issue.controller;


import yofoto.issue.pojo.Staffer;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author husan
 * @Date 2012-10-25
 * @description
 */
public class BaseAction extends ActionSupport {
	protected Staffer staffer;

	public Staffer getStaffer() {
		return staffer;
	}

	public void setStaffer(Staffer staffer) {
		this.staffer = staffer;
	}

	
	
}
