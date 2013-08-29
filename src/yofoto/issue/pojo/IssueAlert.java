package yofoto.issue.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * @author husan
 * @Date 2013-4-26
 * @description
 */
@Entity(name="ISSUEALERT")
public class IssueAlert {
	@Id
	private int id;
	
	private String info;
	
	private boolean emailFlag;
	
	@OneToOne
	private Staffer completer;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public boolean isEmailFlag() {
		return emailFlag;
	}

	public void setEmailFlag(boolean emailFlag) {
		this.emailFlag = emailFlag;
	}

	public Staffer getCompleter() {
		return completer;
	}

	public void setCompleter(Staffer completer) {
		this.completer = completer;
	}

}
