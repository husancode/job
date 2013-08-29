package yofoto.issue.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.Attachment;
import yofoto.issue.pojo.Department;
import yofoto.issue.pojo.Issue;
import yofoto.issue.pojo.IssueWorkflow;
import yofoto.issue.pojo.Staffer;
import yofoto.issue.service.impl.DepartmentServiceImpl;
import yofoto.issue.service.impl.IssueServiceImpl;
import yofoto.issue.service.impl.IssueWorkflowServiceImpl;
import yofoto.issue.service.impl.StafferServiceImpl;
import yofoto.issue.util.IssueAuth;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author husan
 * @Date 2012-11-12
 * @description: issue流程处理
 */
public class IssuehandleAction extends ActionSupport{
	
	private static Log logger = LogFactory.getLog(IssuehandleAction.class);
	public List<Integer> getListCheck() {
		return listCheck;
	}

	public void setListCheck(List<Integer> listCheck) {
		this.listCheck = listCheck;
	}
	private List<Integer> listCheck;
	private File[] upload;
	private String[] uploadContentType; 
	private String[] uploadFileName; 
	private List<Attachment> attachments = new ArrayList<Attachment>();
	public File[] getUpload()
	{
		return upload;
	}

	public void setUpload(File[] upload)
	{
		this.upload = upload;
	}

	public String[] getUploadContentType()
	{
		return uploadContentType;
	}

	public void setUploadContentType(String[] uploadContentType)
	{
		this.uploadContentType = uploadContentType;
	}

	public String[] getUploadFileName()
	{
		return uploadFileName;
	}

	public void setUploadFileName(String[] uploadFileName)
	{
		this.uploadFileName = uploadFileName;
	}

	@Autowired
	private IssueServiceImpl issueImpl;
	@Autowired
	private IssueWorkflowServiceImpl issueWorkflowImpl;
	@Autowired
	private DepartmentServiceImpl departmentImpl;
	@Autowired
	private StafferServiceImpl stafferImpl;
	
	private Integer id;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		logger.info(staffer.getName()+" 查看任务："+id);
		Issue aIssue = null;
		try{
			aIssue = issueImpl.get(id);
		}catch(Exception ex){
			return ERROR;
		}
		//System.out.println(aIssue.getPublishDate());
		//System.out.println(aIssue.getCompleter().getName());
		IssueAuth issueAuth = issueImpl.issueAuth(aIssue,staffer);
		if(!issueAuth.isView()){
			return ERROR;
		}
		List<IssueWorkflow> issueWorkflows = issueWorkflowImpl.getByIssue(aIssue);
		Department department = departmentImpl.load(aIssue.getDepartmentID());
		//System.out.println(department.getName());
		//System.out.println(department.getStaffers().size());
		request.setAttribute("department", department);
		request.setAttribute("aIssue", aIssue);
		request.setAttribute("issueAuth", issueAuth);
		request.setAttribute("issueWorkflows", issueWorkflows);
		return SUCCESS;
	}
	//流程处理：
	public String dealIssue() throws Exception{
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		logger.info(staffer.getName()+"处理任务:"+ id);
		
		Issue aIssue = null;
		try{
			aIssue = issueImpl.get(id);
		}catch(Exception ex){
			return ERROR;
		}
		IssueAuth issueAuth = issueImpl.issueAuth(aIssue,staffer);
		
		//设定抄送人
		String ccto = "";
		if(listCheck != null && listCheck.size() > 0){
			for(int c =0 ;c<listCheck.size();c++){
				ccto = ccto+","+listCheck.get(c).intValue();
			}
			aIssue.setEmailRecivers(ccto);
		}
		//
		
		//这个会造成提交后再次人工刷新时出错
//		if(!issueAuth.isDeal()){
//			return ERROR;
//		}
		
		if(issueAuth.isDeal()){
		
		String content = null;
		
		if(null==request.getParameter("contentType")||"1".equals(request.getParameter("contentType"))) 
			content = request.getParameter("content");
		else content = request.getParameter("handleHtml");
		//System.out.println(content+">>>>");
		int status = Integer.parseInt(request.getParameter("status"));
		
		if(aIssue.getCompleteStatus()==1){
			//转发:status=1
			if(status == 1){
				IssueWorkflow issueWorkflow = new IssueWorkflow();
				issueWorkflow.setFromStaffer(staffer);
				Integer toSid = Integer.parseInt(request.getParameter("m_person"));
				Staffer toStaffer = stafferImpl.load(toSid);
				issueWorkflow.setToStaffer(toStaffer);
				issueWorkflow.setCompleteStatus(1);
				issueWorkflow.setContent(content);
				issueWorkflow.setDealTime(new Date());
				issueWorkflow.setIssueId(aIssue.getId());
				
				//上传文件到流程
				uploadAttachments(issueWorkflow);
				
				issueImpl.dealTrans(issueWorkflow,aIssue);
				//未完成-》待审核
			}else if(status == 2 ){
				IssueWorkflow issueWorkflow = new IssueWorkflow();
				issueWorkflow.setFromStaffer(staffer);
				issueWorkflow.setToStaffer(aIssue.getPublisher());
				
				issueWorkflow.setContent(content);
				issueWorkflow.setDealTime(new Date());
				issueWorkflow.setIssueId(aIssue.getId());
				
				//上传文件到流程
				uploadAttachments(issueWorkflow);
				
				issueImpl.dealComit(issueWorkflow,aIssue);
				
				//
			}
			//审核
		}else if(aIssue.getCompleteStatus()==2){
			//未修复：驳回
			if(status == 0){
				IssueWorkflow issueWorkflow = new IssueWorkflow();
				issueWorkflow.setFromStaffer(staffer);
				Staffer toStaffer = issueWorkflowImpl.getToStaffer(id);
				issueWorkflow.setToStaffer(toStaffer);
				issueWorkflow.setContent(content);
				issueWorkflow.setCompleteStatus(1);
				issueWorkflow.setDealTime(new Date());
				issueWorkflow.setIssueId(aIssue.getId());
				
				//上传文件到流程
				uploadAttachments(issueWorkflow);
				
				issueImpl.dealTrans(issueWorkflow,aIssue);
			//未修复转发	
			}else if(status == 1){
				IssueWorkflow issueWorkflow = new IssueWorkflow();
				issueWorkflow.setFromStaffer(staffer);
				Integer toSid = Integer.parseInt(request.getParameter("m_person"));
				Staffer toStaffer = stafferImpl.load(toSid);
				issueWorkflow.setToStaffer(toStaffer);
				issueWorkflow.setContent(content);
				issueWorkflow.setCompleteStatus(1);
				issueWorkflow.setDealTime(new Date());
				issueWorkflow.setIssueId(aIssue.getId());
				
				//上传文件到流程
				uploadAttachments(issueWorkflow);
				
				issueImpl.dealTrans(issueWorkflow,aIssue);
			//已完成	
			}else if(status == 3){
				IssueWorkflow issueWorkflow = new IssueWorkflow();
				issueWorkflow.setFromStaffer(staffer);
				issueWorkflow.setToStaffer(aIssue.getPublisher());
				issueWorkflow.setContent(content);
				issueWorkflow.setDealTime(new Date());
				issueWorkflow.setIssueId(aIssue.getId());
				//计算完成过期时间
				
				//上传文件到流程
				uploadAttachments(issueWorkflow);
				
				issueImpl.dealExam(issueWorkflow,aIssue);
			}
		}
		
		}
		
		return execute();
	}
	
	
	public void uploadAttachments(IssueWorkflow issueWorkflow) throws IOException{
		//上传文件
		String targetDirectory = ServletActionContext.getServletContext().getRealPath("/uploadfiles/");
		if(upload != null) {
			for (int i = 0; i < upload.length; i++) {
				String fileName = uploadFileName[i];
				//String type = uploadContentType[i];
				String uploadUUIDName = UUID.randomUUID().toString() + "."+fileName;//+getExt(fileName);
	
				File target = new File(targetDirectory, uploadUUIDName);
				FileUtils.copyFile(upload[i], target);
				Attachment a = new Attachment();
				a.setUUIDName(uploadUUIDName);
				a.setRealName(fileName);
				a.setIssueworkflow(issueWorkflow);
				
				attachments.add(a);
				
			}
			issueWorkflow.setAttachments(attachments);
		}
		//

	}
	public String closeIssue()throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		logger.info(staffer.getName()+" 关闭了任务:"+id);
		Issue aIssue = issueImpl.get(id);
		System.out.println(aIssue.getTitle());
		IssueAuth issueAuth = issueImpl.issueAuth(aIssue,staffer);
		if(!issueAuth.isAdmin()){
			return null;
		}
		if(aIssue.getCompleteStatus()<3){
			IssueWorkflow issueWorkflow = new IssueWorkflow();
			issueWorkflow.setFromStaffer(staffer);
			issueWorkflow.setToStaffer(staffer);
			issueWorkflow.setContent(staffer.getName()+"强制关闭任务");
			issueWorkflow.setDealTime(new Date());
			issueWorkflow.setIssueId(aIssue.getId());
			issueImpl.dealExam(issueWorkflow,aIssue);
		}
		return null;
	}
	
	public String deleteIssue() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		logger.info(staffer.getName()+"删除了任务："+id);
		Issue aIssue = issueImpl.get(id);
	
		IssueAuth issueAuth = issueImpl.issueAuth(aIssue,staffer);
		if(!issueAuth.isAdmin()){
			return null;
		}
		if(aIssue.getCompleteStatus()<3){
			aIssue.setStatus(2);
			issueImpl.save(aIssue);
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter pw = new PrintWriter(response.getOutputStream());
		pw.write("true");
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


	//文件下载
	private String realname;
	private String UUIDName;
	public void setFileName()
	{
		UUIDName = ServletActionContext.getRequest().getParameter("UUIDName");
		realname = ServletActionContext.getRequest().getParameter("realname");
		try
		{
			UUIDName = new String(UUIDName.getBytes("ISO-8859-1"), "UTF-8");
			realname= new String(realname.getBytes("ISO-8859-1"), "UTF-8");
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		System.out.println(realname);
		System.out.println(UUIDName);
	}


	public HttpServletResponse getDownloadFile() throws IOException {
		this.setFileName();
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		   String filepath = request.getRealPath("/uploadfiles");
		   File f = new File(filepath+"/"+UUIDName);
		   Long filelength = f.length();
		   int cacheTime = 10;
		  
		   response.setContentType("application/octet-stream");
		   response.setHeader("Location",realname);
		   response.setHeader("Cache-Control", "max-age=" + cacheTime);
		  
		   response.setContentType("application/octet-stream");
		   realname = new String(realname.getBytes(), "ISO-8859-1"); 
		   response.setHeader("Content-Disposition", "attachment;filename=" + realname);
		   response.setContentLength(filelength.intValue());
		   OutputStream outputStream = response.getOutputStream();
		   InputStream inputStream = new FileInputStream(f);
		   byte[] buffer = new byte[1024];
		   int i = -1;
		   while ((i = inputStream.read(buffer)) != -1) {
		    outputStream.write(buffer, 0, i);
		   }
		  
		   outputStream.flush();
		   outputStream.close();
		   inputStream.close();
		  
		   return null ;

	}


}
