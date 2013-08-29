package yofoto.issue.vo;

import java.util.Date;



/**
 * @author husan
 * @Date 2012-11-10
 * @description
 */
public class IssueVO {
	private Integer id;
	private String title;
	private Integer modularid;
	private Integer priority;
	private Integer completeStatus;
	private Integer publisherid;
	private Integer completerid;
	private boolean isOverdue;
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
	public Integer getModularid() {
		return modularid;
	}
	public void setModularid(Integer modularid) {
		this.modularid = modularid;
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
	
	public boolean isOverdue() {
		return isOverdue;
	}
	public void setOverdue(boolean isOverdue) {
		this.isOverdue = isOverdue;
	}
	public Integer getPublisherid() {
		return publisherid;
	}
	public void setPublisherid(Integer publisherid) {
		this.publisherid = publisherid;
	}
	public Integer getCompleterid() {
		return completerid;
	}
	public void setCompleterid(Integer completerid) {
		this.completerid = completerid;
	}
	
	

}
