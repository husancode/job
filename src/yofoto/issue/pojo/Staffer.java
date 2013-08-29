package yofoto.issue.pojo;

import java.io.Serializable;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.BatchSize;

/**
 * @author husan
 * @Date 2012-10-20
 * @description  员工表
 */
@Entity(name="STAFFER")
public class Staffer implements Serializable{
	//主键id
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="S_Staffer")  
	@SequenceGenerator(name="S_Staffer",allocationSize=1,initialValue=1, sequenceName="S_Staffer") 
	private Integer sid;
	
	//员工名
	@Column(name="name")
	private String name;
	
	//登陆密码
	@Column(name="password")
	private String password;
	
	//email（使用email登陆）
	@Column(name="email",unique=true)
	private String email;
	
	//状态：1有效2无效
	@Column(name="status")
	private Integer status;
	
	//是否邮件通知
	@Column(name="notify")
	private boolean notify;
	
	//所在部门
	@ManyToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE},fetch=FetchType.LAZY) 
	@JoinTable(name="j_project_staffer" ,joinColumns={@JoinColumn(name="sid")},inverseJoinColumns={@JoinColumn(name="did")})
	@OrderBy("did asc")
	private  List<Department> departments;
	
	//加入时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="generaTime")
	private Date generaTime;
	
	//角色：1 系统管理员 2 部门管理员 3员工
	
	private Integer role;
	
	//权限：所管辖部门id 加 逗号分隔
	private String auth;
	
	//所属团队
	@ManyToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE},fetch=FetchType.LAZY) 
	@JoinTable(name="j_team_staffer" ,joinColumns={@JoinColumn(name="sid")},inverseJoinColumns={@JoinColumn(name="tid")})
	@OrderBy("tid asc")
	private List<Team> teams;
	
	//联系电话
	private String telphone;
	
	//是否主管
	private boolean projectAdmin;
	
	
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
	
	
	public boolean isNotify() {
		return notify;
	}
	public void setNotify(boolean notify) {
		this.notify = notify;
	}
	
	public Date getGeneraTime() {
		return generaTime;
	}
	public void setGeneraTime(Date generaTime) {
		this.generaTime = generaTime;
	}
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	@JSON(serialize=false)
	public List<Department> getDepartments() {
		return departments;
	}
	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}
	@JSON(serialize=false)
	public List<Team> getTeams() {
		return teams;
	}
	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}
	public boolean isProjectAdmin() {
		return projectAdmin;
	}
	public void setProjectAdmin(boolean projectAdmin) {
		this.projectAdmin = projectAdmin;
	}
	
	
}
