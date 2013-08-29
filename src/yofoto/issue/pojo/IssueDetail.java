package yofoto.issue.pojo;



import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;

/**
 * @author husan
 * @Date 2012-10-23
 * @description 问题明细表
 */
@Entity(name="ISSUEDETAIL")
public class IssueDetail {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="S_Issuedetail")  
	@SequenceGenerator(name="S_Issuedetail",allocationSize=1,initialValue=1, sequenceName="S_Issuedetail") 
	private Integer id;
	
	
	//内容
	@Lob
	@Basic(fetch=FetchType.LAZY)
	@Column(name="CONTENT",columnDefinition="BLOB")
	private byte[] content;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	} 
	
	
	
	
	
}
