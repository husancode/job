package yofoto.issue.pojo;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;

/**
 * @author husan
 * @Date 2012-10-25
 * @description 上传附件文件
 */
@Entity(name="ATTACHMENT")
public class Attachment {
	//主键
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="S_ATTACHMENT")  
	@SequenceGenerator(name="S_ATTACHMENT",allocationSize=1,initialValue=1, sequenceName="S_ATTACHMENT") 
	private Integer fid;
	
	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="issueworkflowid")
	private IssueWorkflow issueworkflow;
	
	public IssueWorkflow getIssueworkflow() {
		return issueworkflow;
	}

	public void setIssueworkflow(IssueWorkflow issueworkflow) {
		this.issueworkflow = issueworkflow;
	}

	public Integer getFid() {
		return fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getUUIDName() {
		return UUIDName;
	}

	public void setUUIDName(String uUIDName) {
		UUIDName = uUIDName;
	}

	private String realName;

	//文件名称 UUID
	private String UUIDName;
	

}
