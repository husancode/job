package yofoto.issue.vo;

import java.util.Date;
import java.util.List;

/**
 * @author husan
 * @Date 2012-11-10
 * @description 页面显示分页封装
 */
public class Page<T> {
	public static final int PAGESIZE =10;
	//页码
	private int pageIndex = 1;
	//每页显示条数
	private int pageNum = PAGESIZE;
	//排序
	private String orderBy;
	//结果总条数
	private long total;
	//结果列表
	private List<T> results;
	
	//是否计算总数
	private boolean isAutoCount = true;
	
	//搜索条件
	private int priority;
	
	private int publisherid;
	
	private int modularid;
	
	private int completeStatus;
	
	private int completerid;
	
	private int overdue = -1;
	
	private String keyWord;
	
	//2012-12-5
	private int departmentid;
	
	private Date from;
	
	private Date to;
	
	private Date completeDateFrom;
	
	private Date completeDateTo;
	
	//
	private String month;
	
	public int getOverdue() {
		return overdue;
	}
	public void setOverdue(int overdue) {
		this.overdue = overdue;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageNum() {
		
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<T> getResults() {
		return results;
	}
	public void setResults(List<T> results) {
		this.results = results;
	}
	
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public int getPublisherid() {
		return publisherid;
	}
	public void setPublisherid(int publisherid) {
		this.publisherid = publisherid;
	}
	public int getModularid() {
		return modularid;
	}
	public void setModularid(int modularid) {
		this.modularid = modularid;
	}
	public int getCompleteStatus() {
		return completeStatus;
	}
	public void setCompleteStatus(int completeStatus) {
		this.completeStatus = completeStatus;
	}
	public int getCompleterid() {
		return completerid;
	}
	public void setCompleterid(int completerid) {
		this.completerid = completerid;
	}
	
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	
	public boolean isAutoCount() {
		return isAutoCount;
	}
	public void setAutoCount(boolean isAutoCount) {
		this.isAutoCount = isAutoCount;
	}
	public long getTotalPage(){
		if(total<0)return -1;
		long count = total/pageNum;
		if(total%pageNum > 0)
			count++;
		return count;
	}
	//显示从1开始，数据库查找从0开始
	public int getFirstResult(){
		return (pageIndex-1)*pageNum + 1;
	}
	public int getLastResult(){
		return pageIndex*pageNum;
	}
	public int getDepartmentid() {
		return departmentid;
	}
	public void setDepartmentid(int departmentid) {
		this.departmentid = departmentid;
	}
	public Date getFrom() {
		return from;
	}
	public void setFrom(Date from) {
		this.from = from;
	}
	public Date getTo() {
		return to;
	}
	public void setTo(Date to) {
		this.to = to;
	}
	public Date getCompleteDateFrom() {
		return completeDateFrom;
	}
	public void setCompleteDateFrom(Date completeDateFrom) {
		this.completeDateFrom = completeDateFrom;
	}
	public Date getCompleteDateTo() {
		return completeDateTo;
	}
	public void setCompleteDateTo(Date completeDateTo) {
		this.completeDateTo = completeDateTo;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	
}
