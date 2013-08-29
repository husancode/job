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



/**
 * @author husan
 * @Date 2012-10-19
 * @description 部门表
 */
@Entity(name="PROJECT")
public class Department {
	// 主键id
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="S_Depart")  
	@SequenceGenerator(name="S_Depart",allocationSize=1,initialValue=1, sequenceName="S_Depart") 
	private Integer did;
	//部门名
	@Column(name="NAME")
	private String name;
	//有效状态：1有效2.无效
	@Column(name="STATUS",length=2)
	private Integer status;
	
	//所属员工
	@ManyToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE},fetch=FetchType.LAZY)
	@JoinTable(name="j_project_staffer" ,joinColumns={@JoinColumn(name="did")},inverseJoinColumns={@JoinColumn(name="sid")})
	@OrderBy("generaTime asc")
	private Set<Staffer> staffers;
	
	//所属模块
	@OneToMany(cascade=CascadeType.ALL,mappedBy="department",fetch=FetchType.LAZY)
	@OrderBy("id asc")
	private List<Modular> modualrs;
	
	//所属团队
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="teamid")
	private Team team;
	//创建者
	@ManyToOne
	private Staffer creator;
	
	//管理员：staffer id ;分隔
	private String admin;
	
	//创建时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
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
	

	public Set<Staffer> getStaffers() {
		return staffers;
	}
	public void setStaffers(Set<Staffer> staffers) {
		this.staffers = staffers;
	}
	public List<Modular> getModualrs() {
		return modualrs;
	}
	public void setModualrs(List<Modular> modualrs) {
		this.modualrs = modualrs;
	}
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	public Staffer getCreator() {
		return creator;
	}
	public void setCreator(Staffer creator) {
		this.creator = creator;
	}
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
