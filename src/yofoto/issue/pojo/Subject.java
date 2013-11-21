package yofoto.issue.pojo;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;

/**
 * @author husan
 * @Date 2013-8-29
 * @description :工作项目表
 */
@Entity(name="SUBJECT")
public class Subject{
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="S_Subject")  
	@SequenceGenerator(name="S_Subject",allocationSize=1,initialValue=1, sequenceName="S_Subject") 
	private Integer id;
	//主题
	private String title;
	
	//子任务
	@OneToMany(cascade=CascadeType.ALL,mappedBy="subject",fetch=FetchType.LAZY)
	@OrderBy("id desc")
	private List<SubjectItem> items;
	
	private Integer teamID;
	
	//负责人
	@ManyToOne
	private Staffer completer;
	
	//完成时间
	private Date completeDate;
	//发布时间
	private Date publishDate;
	//所属月份
	private String month;
	
	//状态：1未确认 2 已确认
	private int status;
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<SubjectItem> getItems() {
		return items;
	}

	public void setItems(List<SubjectItem> items) {
		this.items = items;
	}

	public Staffer getCompleter() {
		return completer;
	}

	public void setCompleter(Staffer completer) {
		this.completer = completer;
	}

	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	public Integer getTeamID() {
		return teamID;
	}

	public void setTeamID(Integer teamID) {
		this.teamID = teamID;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

}


