package yofoto.issue.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.Attachment;
import yofoto.issue.pojo.Department;
import yofoto.issue.pojo.Issue;
import yofoto.issue.pojo.IssueWorkflow;
import yofoto.issue.pojo.Modular;
import yofoto.issue.pojo.SubjectItem;
import yofoto.issue.service.impl.SubjectItemService;

/**
 * @author husan
 * @Date 2013-10-30
 * @description 任务细分，添加修改删除
 */
public class SubjectitemAction extends BaseAction{
	
	private final String directory = ServletActionContext.getServletContext().getRealPath("/uploadfiles/");
	
	//subjectitem的id
	private Integer id; 
	
	private SubjectItem subjectItem;
	
	private List<String> description;
	//每个子任务对应的上传文件数目
	private List<Integer> filecount;
	
	private File[] upload;
	private String[] uploadContentType; 
	private String[] uploadFileName;  
	
	private List<String> secret;
	@Autowired
	private SubjectItemService subjectItemService;
	
	@Override
	public String execute(){
		// TODO Auto-generated method stub
		subjectItem = subjectItemService.get(id);
		System.out.println(subjectItem.getTitle());
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("subjectItem", subjectItem);
		//获取小组信息
		Department department = subjectItem.getDepartment();
		List<Modular> modulars = department.getModualrs();
		request.setAttribute("modulars", modulars);
		request.setAttribute("department", department);
		return SUCCESS;
	}
	
	//little fat,fit it later
	public String submit() throws Exception{
		SubjectItem subjectIt = null;
		
		if(subjectItem!=null){
			int fileIndex = 0;
			 subjectIt = subjectItemService.get(subjectItem.getId());
			List<Issue> issues = subjectIt.getIssues();
			if(issues==null)
				issues = new ArrayList<Issue>();
			List<Issue> newIssues = new ArrayList<Issue>();
			int length = subjectItem.getIssues().size();
			for(int i=0;i< length;i++){
				Integer fileCount = filecount.get(i);
				Issue issue = subjectItem.getIssues().get(i);
				//被删除的任务节点
				if(issue==null){
					continue;
				}
				//
				Issue oginalIssue = find(issue,issues);
				//更新操作
				if(oginalIssue!=null){	
					oginalIssue.setTitle(issue.getTitle());
					oginalIssue.setModular(issue.getModular());
					oginalIssue.setCompleter(issue.getCompleter());
					oginalIssue.setExprireDate(issue.getExprireDate());
					oginalIssue.setPercentage(issue.getPercentage());
					oginalIssue.setPriority(issue.getPriority());
					oginalIssue.setEmailRecivers(issue.getEmailRecivers());
					
					oginalIssue.setSecret(issue.isSecret());
					oginalIssue.getIssueWorkflows().get(0).setContent(description.get(i));
					//文件上传
					fileIndex = uploadFile(fileCount, oginalIssue.getIssueWorkflows().get(0), fileIndex);
					newIssues.add(oginalIssue);
				}else{
					issue.setCompleteStatus(1);
					issue.setStatus(0);
					issue.setDepartmentID(subjectIt.getDepartment().getDid());
					issue.setTeamID(subjectIt.getDepartment().getTeam().getTid());
					issue.setPublisher(staffer);
					//issue.setReviewer(staffer);
					issue.setPublishDate(new Date());
					IssueWorkflow issueWorkflow = new IssueWorkflow();
					issueWorkflow.setCompleteStatus(1);
					issueWorkflow.setDealTime(new Date());
					issueWorkflow.setFromStaffer(staffer);
					issueWorkflow.setToStaffer(issue.getCompleter());
					issueWorkflow.setContent(description.get(i));
					//文件上传
					fileIndex = uploadFile(fileCount, issueWorkflow, fileIndex);
					List<IssueWorkflow> issueWorkflows = new ArrayList<IssueWorkflow>();
					issueWorkflows.add(issueWorkflow);
					issue.setIssueWorkflows(issueWorkflows);
					newIssues.add(issue);
				}
				
			}
			subjectIt.setIssues(newIssues);
			//状态转为待审核
			subjectIt.setStatus(2);
			subjectItemService.save(subjectIt);
		}
		String url = "subjectitem?id="+subjectIt.getId();
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private int uploadFile(Integer fileCount,IssueWorkflow iwf,int fileIndex){
		if(fileCount!=null && fileCount>0){
			List<Attachment> attachments = new ArrayList<Attachment>();
			for(int y=0;y<fileCount;y++){
				File file = upload[fileIndex];
				String fileName = uploadFileName[fileIndex];
				String uploadUUIDName = UUID.randomUUID().toString() + "."+fileName;
				File target = new File(directory, uploadUUIDName);
				try {
					FileUtils.copyFile(file, target);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Attachment a = new Attachment();
				a.setUUIDName(uploadUUIDName);
				a.setRealName(fileName);
				a.setIssueworkflow(iwf);
				attachments.add(a);
				fileIndex++;
			}
			if(iwf.getAttachments()!=null){
				iwf.getAttachments().addAll(attachments);
			}else
			iwf.setAttachments(attachments);
			
		}
		return fileIndex;
	}
	
	
	private Issue find(Issue issue , List<Issue> issues){
		if(issue.getId()==null)
			return null;
		if(issues!=null){
			for(Issue iss : issues){
				if(iss.getId().equals(issue.getId()))
					return iss;
			}
		}
		return null;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public SubjectItem getSubjectItem() {
		return subjectItem;
	}


	public void setSubjectItem(SubjectItem subjectItem) {
		this.subjectItem = subjectItem;
	}


	public SubjectItemService getSubjectItemService() {
		return subjectItemService;
	}


	public void setSubjectItemService(SubjectItemService subjectItemService) {
		this.subjectItemService = subjectItemService;
	}

	public List<String> getDescription() {
		return description;
	}

	public void setDescription(List<String> description) {
		this.description = description;
	}

	public List<String> getSecret() {
		return secret;
	}

	public void setSecret(List<String> secret) {
		this.secret = secret;
	}

	public List<Integer> getFilecount() {
		return filecount;
	}

	public void setFilecount(List<Integer> filecount) {
		this.filecount = filecount;
	}

	public File[] getUpload() {
		return upload;
	}

	public void setUpload(File[] upload) {
		this.upload = upload;
	}

	public String[] getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String[] uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String[] getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String[] uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

}
