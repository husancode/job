package yofoto.issue.pojo;


import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author husan
 * @Date 2012-10-20
 * @description  模块表
 */

@Entity(name="MODULAR")
public class Modular {
	//主键id
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="S_Modular")  
	@SequenceGenerator(name="S_Modular",allocationSize=1,initialValue=1, sequenceName="S_Modular") 
	private Integer id;
	
	//模块名
	private String name;
	
	//处理人
	@OneToOne(fetch=FetchType.LAZY)
	private Staffer responsibleStaffer;
	
	//所在部门
	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="departmentid")
	private Department department;
	
	//状态:1.有效 2.停用
	private Integer status;
	
	//权重（百分比）
	private Integer percentage;
	
	//评分:基准分
	private double score;
	//创建时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Staffer getResponsibleStaffer() {
		return responsibleStaffer;
	}
	public void setResponsibleStaffer(Staffer responsibleStaffer) {
		this.responsibleStaffer = responsibleStaffer;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getPercentage() {
		return percentage;
	}
	public void setPercentage(Integer percentage) {
		this.percentage = percentage;
	}
	
	
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	

}
