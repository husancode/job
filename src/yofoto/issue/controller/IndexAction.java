package yofoto.issue.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.Issue;
import yofoto.issue.pojo.Upload;
import yofoto.issue.service.impl.IssueServiceImpl;
import yofoto.issue.service.impl.UploadService;

/**
 * @author husan
 * @Date 2013-9-3
 * @description
 */

public class IndexAction extends BaseAction{
	
	@Autowired
	private IssueServiceImpl issueImpl;
	
	@Autowired
	private UploadService uploadService;

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
		
		//
		Upload upload = uploadService.getNewestUpload(tid);
		
		//优秀任务评分确认
		criterions[2] = Restrictions.eq("scoreFlag", 1);
		List<Issue> excellentIssues = issueImpl.getIssues(1, 3, criterions);
		//待延期任务确认
		Criterion[] criterion = new Criterion[3];
		criterion[0] = Restrictions.eq("expireFlag", 1);
		criterion[1] = Restrictions.eq("status", 1);
		criterion[2] = Restrictions.eq("teamID", tid);
		List<Issue> yqIssues = issueImpl.getIssues(1,3,criterion);
		//任务待取消确认
		criterion[0] = Restrictions.eq("cancelFlag", 1);
		List<Issue> cancelIssues = issueImpl.getIssues(1, 3, criterion);
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("toScoreIssues", toScoreIssues);
		request.setAttribute("toReviewIssues", toReviewIssues);
		request.setAttribute("expireIssues", expireIssues);
		request.setAttribute("tid",tid);
		request.setAttribute("upload", upload);
		request.setAttribute("yqIssues", yqIssues);
		request.setAttribute("cancelIssues", cancelIssues);
		request.setAttribute("excellentIssues", excellentIssues);
		return SUCCESS;
	}
	
	
}


