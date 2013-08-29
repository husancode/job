package yofoto.issue.pojo;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

/**
 * @author husan
 * @Date 2012-10-23
 * @description 问题表
 */

@Entity(name="ISSUE")
public class Issue {
	//主键
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="S_Issue")  
	@SequenceGenerator(name="S_Issue",allocationSize=1,initialValue=1, sequenceName="S_Issue") 
	private Integer id;
	//标题
	private String title;
	//所属模块
	@ManyToOne
	private Modular modular;
	//优先级：1.低；2.中；3.高；4.紧急5.严重
	private Integer priority;
	//处理状态：1.未完成 2.待审核 3.已解决4.延期完成5.已评分6.已取消
	private Integer completeStatus;
	//发布者
	@ManyToOne
	private Staffer publisher;
	//处理人
	@ManyToOne
	private Staffer completer;
	
	
	
	//审核人，默认为发布者
	@ManyToOne
	private Staffer reviewer;
	//发布时间
	private Date publishDate;
	//有效时间
	private Date exprireDate;
	//完成时间
	private Date completeDate;
	//状态 1.有效2无效
	private Integer status;
	/*//问题明细
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="issueId",unique=true)
	private IssueDetail issueDetail;*/
	
	//处理流程表
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="issueId")
	@OrderBy("id desc")
	private List<IssueWorkflow> issueWorkflows;
	
	//邮件接受者
	private  String emailRecivers;
	
	private Integer departmentID;
	
	private boolean isOverdue;
	
	//2012-12-04 协调人:自己填写
	
	private String coordinationer;
	
	//团队id ： 跨项目按团队查看任务
	private Integer teamID;
	
	//2012-12-15 :保密功能
	//是否保密：任务接收者才能查看
	private boolean secret;
	
	//申请延期原因
	private String reason;
	
	//任务系数
	private Integer percentage;
	
	private String perReason;
	
	private Integer originalPercentage;
	
	private Date originalExpireDate;
	
	//
	private Integer overLevel;
	
	private String subject;
	//1计划任务 2新增任务
	private Integer issueType;
	
	public Integer getTeamID() {
		return teamID;
	}

	public void setTeamID(Integer teamID) {
		this.teamID = teamID;
	}

	public String getCoordinationer() {
		return coordinationer;
	}

	public void setCoordinationer(String coordinationer) {
		this.coordinationer = coordinationer;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Modular getModular() {
		return modular;
	}

	public void setModular(Modular modular) {
		this.modular = modular;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getCompleteStatus() {
		return completeStatus;
	}

	public void setCompleteStatus(Integer completeStatus) {
		this.completeStatus = completeStatus;
	}

	public Staffer getPublisher() {
		return publisher;
	}

	public void setPublisher(Staffer publisher) {
		this.publisher = publisher;
	}

	public Staffer getCompleter() {
		return completer;
	}

	public void setCompleter(Staffer completer) {
		this.completer = completer;
	}

	public Staffer getReviewer() {
		return reviewer;
	}

	public void setReviewer(Staffer reviewer) {
		this.reviewer = reviewer;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Date getExprireDate() {
		return exprireDate;
	}

	public void setExprireDate(Date exprireDate) {
		this.exprireDate = exprireDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
/*
	public IssueDetail getIssueDetail() {
		return issueDetail;
	}

	public void setIssueDetail(IssueDetail issueDetail) {
		this.issueDetail = issueDetail;
	}
*/
	

	public List<IssueWorkflow> getIssueWorkflows() {
		return issueWorkflows;
	}

	public void setIssueWorkflows(List<IssueWorkflow> issueWorkflows) {
		this.issueWorkflows = issueWorkflows;
	}

	public String getEmailRecivers() {
		return emailRecivers;
	}

	public void setEmailRecivers(String emailRecivers) {
		this.emailRecivers = emailRecivers;
	}
	
	public Integer getDepartmentID() {
		return departmentID;
	}
	
	public void setDepartmentID(Integer departmentID) {
		this.departmentID = departmentID;
	}

	public boolean isOverdue() {
		return isOverdue;
	}

	public void setOverdue(boolean isOverdue) {
		this.isOverdue = isOverdue;
	}

	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	public boolean isSecret() {
		return secret;
	}

	public void setSecret(boolean secret) {
		this.secret = secret;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getPercentage() {
		return percentage;
	}

	public void setPercentage(Integer percentage) {
		this.percentage = percentage;
	}
	

	public String getPerReason() {
		return perReason;
	}

	public void setPerReason(String perReason) {
		this.perReason = perReason;
	}

	public Integer getOriginalPercentage() {
		return originalPercentage;
	}

	public void setOriginalPercentage(Integer originalPercentage) {
		this.originalPercentage = originalPercentage;
	}

	public Date getOriginalExpireDate() {
		return originalExpireDate;
	}

	public void setOriginalExpireDate(Date originalExpireDate) {
		this.originalExpireDate = originalExpireDate;
	}

	public Integer getOverLevel() {
		return overLevel;
	}

	public void setOverLevel(Integer overLevel) {
		this.overLevel = overLevel;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Integer getIssueType() {
		return issueType;
	}

	public void setIssueType(Integer issueType) {
		this.issueType = issueType;
	}
	
	
	
}
