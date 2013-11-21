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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Type;

/**
 * @author husan
 * @Date 2013-8-29
 * @description
 */
@Entity(name="SUBJECTITEM")
public class SubjectItem {
	//主键
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="S_SubjectItem")  
	@SequenceGenerator(name="S_SubjectItem",allocationSize=1,initialValue=1, sequenceName="S_SubjectItem") 
	private Integer id;
	//外键
	//private Integer subjectId;
	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="subjectid")
	private Subject subject;
	//团队id
	private Integer teamID;
	
	//标题
	private String title;
	//工作小组
	//private Integer departmentID;
	
	@ManyToOne
	private Department department;
	
	//负责人
	@ManyToOne
	private Staffer completer;
	//完成日期
	private Date completeDate;
	
	//具体任务
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="subjectItemId")
	@OrderBy("id desc")
	private List<Issue> issues;
	
	//所属模块
	@ManyToOne
	private Modular modular;
	
	//任务系数
	private Integer percentage;
	
	//优先级：1.低；2.中；3.高；4.紧急5.严重
	private Integer priority;
	
	@Lob
	@Type(type="org.springframework.orm.hibernate3.support.ClobStringType")
	@Column(length=10000)
	private String content;
	
	//状态：1待细分2待审核3已审核
	private int status;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	/*public Integer getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}*/
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public Staffer getCompleter() {
		return completer;
	}
	public void setCompleter(Staffer completer) {
		this.completer = completer;
	}
	public Date getCompleteDate() {
		return completeDate;
	}
	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}
	public List<Issue> getIssues() {
		return issues;
	}
	public void setIssues(List<Issue> issues) {
		this.issues = issues;
	}
	public Modular getModular() {
		return modular;
	}
	public void setModular(Modular modular) {
		this.modular = modular;
	}
	public Integer getPercentage() {
		return percentage;
	}
	public void setPercentage(Integer percentage) {
		this.percentage = percentage;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public Integer getTeamID() {
		return teamID;
	}
	public void setTeamID(Integer teamID) {
		this.teamID = teamID;
	}
	
	
}


