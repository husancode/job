package yofoto.issue.controller;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.Subject;
import yofoto.issue.service.impl.SubjectService;
import yofoto.issue.vo.Page;

/**
 * @author husan
 * @Date 2013-11-7
 * @description
 */
public class PlanhistoryAction extends BaseAction{
	@Autowired
	private SubjectService subjectService;
	
	private Page<Subject> page;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		Criterion[] criterions = new Criterion[1];
		criterions[0] = Restrictions.eq("teamID", tid);
		if(page==null)
			page = new Page<Subject>();
		page = subjectService.searchPage(criterions, page);
		//System.out.println(page.getResults().size());
		return SUCCESS;
	}

	public Page<Subject> getPage() {
		return page;
	}

	public void setPage(Page<Subject> page) {
		this.page = page;
	}
	
	

}
