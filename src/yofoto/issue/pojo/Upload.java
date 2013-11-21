package yofoto.issue.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * @author husan
 * @Date 2013-10-24
 * @description
 */
@Entity(name="UPLOAD")
public class Upload {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="S_UPLOAD")  
	@SequenceGenerator(name="S_UPLOAD",allocationSize=1,initialValue=1, sequenceName="S_UPLOAD") 
	private Integer id;
	
	private Integer tid;
	
	private String fileName;
	
	private String uploadName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUploadName() {
		return uploadName;
	}

	public void setUploadName(String uploadName) {
		this.uploadName = uploadName;
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

}
