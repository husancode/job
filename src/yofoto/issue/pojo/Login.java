package yofoto.issue.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author husan
 * @Date 2012-10-29
 * @description
 */
@Entity(name="LOGIN")
public class Login {
	//主键id
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="S_Login")  
	@SequenceGenerator(name="S_Login",allocationSize=1,initialValue=1, sequenceName="S_Login") 
	private Integer id;
	
	
	private String userName;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLogin;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	
	
	
}
