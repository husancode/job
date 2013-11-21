package yofoto.issue.controller;

import java.util.Date;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.Subject;
import yofoto.issue.service.impl.SubjectService;
import yofoto.issue.util.DateUtil;
import yofoto.issue.vo.Page;

/**
 * @author husan
 * @Date 2013-11-7
 * @description 当月项目
 */
public class PlanmonthAction extends BaseAction{
	@Autowired
	private SubjectService subjectService;
	
	private Page<Subject> page;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		Criterion[] criterions = new Criterion[2];
		criterions[0] = Restrictions.eq("teamID", tid);
		criterions[1] = Restrictions.eq("month", DateUtil.getMonth(new Date()));
		if(page==null)
			page = new Page<Subject>();
		page = subjectService.searchPage(criterions, page);
		System.out.println(page.getResults().size());
		return SUCCESS;
	}

	public Page<Subject> getPage() {
		return page;
	}

	public void setPage(Page<Subject> page) {
		this.page = page;
	}
	
	
}
