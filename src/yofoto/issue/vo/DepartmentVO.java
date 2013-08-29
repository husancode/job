package yofoto.issue.vo;

import java.util.Date;

/**
 * @author husan
 * @Date 2012-11-1
 * @description
 */
public class DepartmentVO {
	private Integer did;
	private String name;
	private Integer status;
	private Date createTime;
	private boolean ifAdmin;
	private boolean ifCreator;
	public Integer getDid() {
		return did;
	}
	public void setDid(Integer did) {
		this.did = did;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public boolean isIfAdmin() {
		return ifAdmin;
	}
	public void setIfAdmin(boolean ifAdmin) {
		this.ifAdmin = ifAdmin;
	}
	public boolean isIfCreator() {
		return ifCreator;
	}
	public void setIfCreator(boolean ifCreator) {
		this.ifCreator = ifCreator;
	}
	
	
	
	
}
