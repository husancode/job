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
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Type;
import org.hibernate.mapping.Collection;

/**
 * @author husan
 * @Date 2012-10-23
 * @description 处理流程表
 */
@Entity(name="ISSUEWORKFLOW")
public class IssueWorkflow {
	//主键
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="S_IssueWorkflow")  
	@SequenceGenerator(name="S_IssueWorkflow",allocationSize=1,initialValue=1, sequenceName="S_IssueWorkflow") 
	private Integer id;
	//外键assue
	private Integer issueId;
	//上传文件
	@OneToMany(cascade=CascadeType.ALL,mappedBy="issueworkflow",fetch=FetchType.LAZY)
	private List<Attachment> attachments;

	public List<Attachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
	//处理人
	@OneToOne
	private Staffer fromStaffer;
	//转发人
	@OneToOne
	private Staffer toStaffer;
	//流程状态：1未修复2.待审核3.已修复
	private Integer completeStatus;
	//流程是否终止：1.未终止2.终止
	private Integer isStop;
	/*//备注
	@Lob
	@Basic(fetch=FetchType.LAZY)
	@Column(name="note",columnDefinition="BLOB")
	private byte[] note;*/
	@Lob
	@Type(type="org.springframework.orm.hibernate3.support.ClobStringType")
	@Column(length=10000)
	private String content;
	//处理时间
	private Date dealTime;
	
	//该流程评分
	private double score;
	
	//该流程是否计分
	private boolean isScored;
	
	//2012-12-5 
	//进度评分
	private double progressScore;
	
	//专业评分
	private double professionalScore;
	
	private Boolean progressFlag;
	
	private Boolean professionalFlag;
	
	//评分批注
	private String scoreNote;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIssueId() {
		return issueId;
	}
	public void setIssueId(Integer issueId) {
		this.issueId = issueId;
	}
	public Staffer getFromStaffer() {
		return fromStaffer;
	}
	public void setFromStaffer(Staffer fromStaffer) {
		this.fromStaffer = fromStaffer;
	}
	public Staffer getToStaffer() {
		return toStaffer;
	}
	public void setToStaffer(Staffer toStaffer) {
		this.toStaffer = toStaffer;
	}
	public Integer getCompleteStatus() {
		return completeStatus;
	}
	public void setCompleteStatus(Integer completeStatus) {
		this.completeStatus = completeStatus;
	}
	public Integer getIsStop() {
		return isStop;
	}
	public void setIsStop(Integer isStop) {
		this.isStop = isStop;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDealTime() {
		return dealTime;
	}
	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public boolean isScored() {
		return isScored;
	}
	public void setScored(boolean isScored) {
		this.isScored = isScored;
	}
	public double getProgressScore() {
		return progressScore;
	}
	public void setProgressScore(double progressScore) {
		this.progressScore = progressScore;
	}
	public double getProfessionalScore() {
		return professionalScore;
	}
	public void setProfessionalScore(double professionalScore) {
		this.professionalScore = professionalScore;
	}
	public Boolean getProgressFlag() {
		return progressFlag;
	}
	public void setProgressFlag(Boolean progressFlag) {
		this.progressFlag = progressFlag;
	}
	public Boolean getProfessionalFlag() {
		return professionalFlag;
	}
	public void setProfessionalFlag(Boolean professionalFlag) {
		this.professionalFlag = professionalFlag;
	}
	public String getScoreNote() {
		return scoreNote;
	}
	public void setScoreNote(String scoreNote) {
		this.scoreNote = scoreNote;
	}
	
}
