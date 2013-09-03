package yofoto.issue.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.Issue;
import yofoto.issue.service.impl.IssueServiceImpl;

/**
 * @author husan
 * @Date 2013-9-3
 * @description
 */
public class IndexAction extends BaseAction{
	@Autowired
	private IssueServiceImpl issueImpl;
	
	private Integer tid;
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		if(tid==null || tid==0)
			tid=1;
		Criterion[] criterions = new Criterion[3];
		criterions[0] = Restrictions.eq("status", 1);
		criterions[1] = Restrictions.eq("teamID", tid);
		
		//待评分任务
		criterions[2] = Restrictions.or(Restrictions.eq("completeStatus", 3), Restrictions.eq("completeStatus", 4));
		List<Issue> toScoreIssues = issueImpl.getIssues(1, 3, criterions);
		//待审核任务
		criterions[2] = Restrictions.eq("completeStatus", 2);
		List<Issue> toReviewIssues = issueImpl.getIssues(1, 3, criterions);
		//工作任务即将到期
		List<Issue> expireIssues = issueImpl.getIssueExpireList(2, 3);
		//优秀任务评分确认
		
		//待延期任务确认
		
		//任务待取消确认
		
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("toScoreIssues", toScoreIssues);
		request.setAttribute("toReviewIssues", toReviewIssues);
		request.setAttribute("expireIssues", expireIssues);
		return SUCCESS;
	}
	public Integer getTid() {
		return tid;
	}
	public void setTid(Integer tid) {
		this.tid = tid;
	}
	
	

}


