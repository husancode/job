package yofoto.issue.controller;


import yofoto.issue.pojo.Staffer;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author husan
 * @Date 2012-10-25
 * @description基础类：注入当前的环境信息
 */
public class BaseAction extends ActionSupport {
	protected Staffer staffer;
	
	protected Integer tid;
	
	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public Staffer getStaffer() {
		return staffer;
	}

	public void setStaffer(Staffer staffer) {
		this.staffer = staffer;
	}

}
