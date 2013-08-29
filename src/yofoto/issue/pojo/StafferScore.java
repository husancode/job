package yofoto.issue.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
 * @author husan
 * @Date 2013-5-21
 * @description
 */
@Entity(name="STAFFERSCORE")
public class StafferScore {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="S_Score")  
	@SequenceGenerator(name="S_Score",allocationSize=1,initialValue=1, sequenceName="S_Score") 
	private Integer id;
	
	private Integer tid;
	@ManyToOne
	private Staffer staffer;
	
	private String name;
	
	private String month;
	
	private String amount;
	private String attitude;
	private String coordination;
	private String discipline;
	private String progress;
	private String wordCheck;
	
	private String issueScore;
	private String score;
	private String note;
	private Integer sid;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Staffer getStaffer() {
		return staffer;
	}
	public void setStaffer(Staffer staffer) {
		this.staffer = staffer;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
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
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getAttitude() {
		return attitude;
	}
	public void setAttitude(String attitude) {
		this.attitude = attitude;
	}
	public String getCoordination() {
		return coordination;
	}
	public void setCoordination(String coordination) {
		this.coordination = coordination;
	}
	public String getDiscipline() {
		return discipline;
	}
	public void setDiscipline(String discipline) {
		this.discipline = discipline;
	}
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public String getWordCheck() {
		return wordCheck;
	}
	public void setWordCheck(String wordCheck) {
		this.wordCheck = wordCheck;
	}
	public String getIssueScore() {
		return issueScore;
	}
	public void setIssueScore(String issueScore) {
		this.issueScore = issueScore;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	
	

}
