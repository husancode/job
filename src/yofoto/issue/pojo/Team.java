package yofoto.issue.pojo;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * @description 团队表(部门)
 */
@Entity(name="TEAM")
public class Team {
	//主键
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="S_TEAM")  
	@SequenceGenerator(name="S_TEAM",allocationSize=1,initialValue=1, sequenceName="S_TEAM") 
	private Integer tid;
	//团队部门名
	private String name;
	
	//所属项目
	@OneToMany(cascade=CascadeType.ALL,mappedBy="team")
	@OrderBy("did asc")
	private List<Department> departments;
	//所属员工
	@ManyToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE})
	@JoinTable(name="j_team_staffer" ,joinColumns={@JoinColumn(name="tid")},inverseJoinColumns={@JoinColumn(name="sid")})
	@BatchSize(size=5)
	@OrderBy("sid asc")
	private Set<Staffer> staffers;
	
	//状态：1.有效2.停用
	private Integer status;
	//创建者
	@ManyToOne
	private Staffer creator;
	
	//管理员：staffer id ;分隔
	private String admin;
	
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	//创建时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
	//是否管理者
	@Transient
	private boolean isAuth;
	public Integer getTid() {
		return tid;
	}
	public void setTid(Integer tid) {
		this.tid = tid;
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
	public List<Department> getDepartments() {
		return departments;
	}
	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}
	
	
	
	public Set<Staffer> getStaffers() {
		return staffers;
	}
	public void setStaffers(Set<Staffer> staffers) {
		this.staffers = staffers;
	}
	public boolean isAuth() {
		return isAuth;
	}
	public void setAuth(boolean isAuth) {
		this.isAuth = isAuth;
	}
	
	public Staffer getCreator() {
		return creator;
	}
	public void setCreator(Staffer creator) {
		this.creator = creator;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
