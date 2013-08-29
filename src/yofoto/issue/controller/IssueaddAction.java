package yofoto.issue.controller;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;

import yofoto.issue.pojo.Attachment;
import yofoto.issue.pojo.Department;
import yofoto.issue.pojo.Issue;
import yofoto.issue.pojo.IssueWorkflow;
import yofoto.issue.pojo.Modular;
import yofoto.issue.pojo.Staffer;
import yofoto.issue.service.impl.DepartmentServiceImpl;
import yofoto.issue.service.impl.IssueServiceImpl;
import yofoto.issue.service.impl.IssueWorkflowServiceImpl;
import yofoto.issue.service.impl.ModularServiceImpl;
import yofoto.issue.service.impl.StafferServiceImpl;
import yofoto.issue.util.ConfigUtil;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author husan
 * @Date 2012-11-8
 * @description
 */
public class IssueaddAction extends ActionSupport{
	
	private static Log logger = LogFactory.getLog(IssueaddAction.class);
	
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

	public static String getExt(String fileName)
	{
		return fileName.substring(fileName.lastIndexOf("."));
	}
	@Autowired
	private DepartmentServiceImpl departImpl;
	@Autowired
	private IssueServiceImpl issueImpl;
	@Autowired
	private IssueWorkflowServiceImpl issueWorkflowImpl;
	
	private Integer did;
	
	private Department department;
	
	private String title;
	
	private Integer module;
	
	private Integer priority;
	
	private Integer handler;
	
	private Date expiredate;

	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		department = departImpl.load(did);
		if(!departImpl.isInDepart(staffer,department)){
			return ERROR;
		}
		//复制任务并创建
		if(request.getParameter("id")!=null){
			Integer id = Integer.parseInt(request.getParameter("id"));
			Issue issue = issueImpl.load(id);
			request.setAttribute("issue", issue);
			IssueWorkflow issueWorkflow = issueWorkflowImpl.getContent(issue.getId());
			request.setAttribute("issueWorkflow", issueWorkflow);
		}
		List<Department> departments = staffer.getDepartments();
		List<Modular> modulars = department.getModualrs();
		//System.out.println(modulars.size());
		
		request.setAttribute("departments", departments);
		request.setAttribute("modulars", modulars);
		

		return SUCCESS;
	}
	
	public String add() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		//System.out.println(request.getParameter("description"));
		try{
		department = departImpl.load(did);
		}catch(Exception ex){
			return ERROR;
		}
		if(!departImpl.isInDepart(staffer,department)){
			return ERROR;
		}
		String content = request.getParameter("description");
		//System.out.println(content);
		String coordinationer = request.getParameter("coord");
		String subject = request.getParameter("subject");
		String issueType = request.getParameter("issueType");
		//默认作为新增任务
		int iType = 2;
		try {
			iType = Integer.parseInt(issueType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		Issue issue = new Issue();
		issue.setTitle(title);
		issue.setPriority(priority);
		issue.setCompleteStatus(1);
		//工作项目
		issue.setSubject(subject);
		issue.setDepartmentID(department.getDid());
		//默认作为新增任务
		issue.setIssueType(iType);
		//2012-12-4 
		issue.setTeamID(department.getTeam().getTid());
		//2012-12-15
		if(request.getParameter("secret")!=null)
			issue.setSecret(true);
		issue.setPublisher(staffer);
		issue.setReviewer(staffer);
		issue.setPublishDate(new Date());
		
		//expiredate with 23:59:59
		DateFormat formatWithTime = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");  
		SimpleDateFormat   sDateFormate=new SimpleDateFormat("yyyy/MM/dd");
		java.util.Date newExpireTime = formatWithTime.parse(sDateFormate.format(expiredate)+" 23:59:59");  
		logger.info(formatWithTime.format(newExpireTime));
		
		issue.setExprireDate(newExpireTime);
		issue.setStatus(1);
		//
		issue.setPercentage(ConfigUtil.percentage);
		
		//设定抄送人
		String ccto = "";
		if(listCheck != null && listCheck.size() > 0){
			for(int c =0 ;c<listCheck.size();c++){
				ccto = ccto+","+listCheck.get(c).intValue();
			}
			issue.setEmailRecivers(ccto);
		}
		//
		
		
		
		IssueWorkflow issueWorkflow = new IssueWorkflow();
		issueWorkflow.setCompleteStatus(1);
		issueWorkflow.setDealTime(new Date());
		issueWorkflow.setFromStaffer(staffer);
		issueWorkflow.setContent(content);
		
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
		
		issueImpl.addIssue(staffer, issue, handler,coordinationer, module,issueWorkflow);
		//发送短信
		logger.info(staffer.getName()+"发布新任务："+issue.getTitle());
		HttpServletResponse response = ServletActionContext.getResponse();
		response.sendRedirect("myissue?did="+did);
		return null;
	}

	public Integer getDid() {
		return did;
	}

	public void setDid(Integer did) {
		this.did = did;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getModule() {
		return module;
	}

	public void setModule(Integer module) {
		this.module = module;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getHandler() {
		return handler;
	}

	public void setHandler(Integer handler) {
		this.handler = handler;
	}

	public Date getExpiredate() {
		return expiredate;
	}

	public void setExpiredate(Date expiredate) {
		this.expiredate = expiredate;
	}

	
	
	
}
