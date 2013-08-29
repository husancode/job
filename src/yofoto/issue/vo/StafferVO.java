package yofoto.issue.vo;

import java.util.Date;

/**
 * @author husan
 * @Date 2012-10-31
 * @description
 */
public class StafferVO {
	private Integer sid;
	private String name;
	private String password;
	private String email;
	private Integer status;
	private Date generaTime;
	private String telphone;
	private boolean isAdmin;
	private boolean isCreator;
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getGeneraTime() {
		return generaTime;
	}
	public void setGeneraTime(Date generaTime) {
		this.generaTime = generaTime;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public boolean isCreator() {
		return isCreator;
	}
	
	public void setCreator(boolean isCreator) {
		this.isCreator = isCreator;
	}
	
}
