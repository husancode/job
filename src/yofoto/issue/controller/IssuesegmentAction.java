package yofoto.issue.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.SubjectItem;
import yofoto.issue.service.impl.SubjectItemService;
import yofoto.issue.vo.Page;

/**
 * @author husan
 * @Date 2013-10-30
 * @description 任务细分
 */
public class IssuesegmentAction extends BaseAction{
	
	@Autowired
	private SubjectItemService subjectItemService;
	
	private Page<SubjectItem> page;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		//List<SubjectItem> items = subjectItemService.getSubjectItems(staffer);
		Criterion[] criterions = new Criterion[2];
		criterions[0] = Restrictions.eq("completer", staffer);
		//criterions[1] = Restrictions.in("department",staffer.getDepartments());
		if(page==null){
			page = new Page();
		}
		
		page = subjectItemService.searchPage(criterions,page);
		HttpServletRequest request = ServletActionContext.getRequest();
	
		return SUCCESS;
	}

	public Page<SubjectItem> getPage() {
		return page;
	}

	public void setPage(Page<SubjectItem> page) {
		this.page = page;
	}

}
