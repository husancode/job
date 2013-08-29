package yofoto.issue.vo;
/**
 * @author husan
 * @Date 2012-11-9
 * @description
 */
public class IssueCountVO {
	
	private Integer totalCount;
	private Integer unCompletedCount;
	private Integer toExamineCount;
	private Integer completedCount;
	private Integer overCompletedCount;
	private Integer scoredCount;
	//
	private Integer cancleCount;
	
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Integer getUnCompletedCount() {
		return unCompletedCount;
	}
	public void setUnCompletedCount(Integer unCompletedCount) {
		this.unCompletedCount = unCompletedCount;
	}
	public Integer getToExamineCount() {
		return toExamineCount;
	}
	public void setToExamineCount(Integer toExamineCount) {
		this.toExamineCount = toExamineCount;
	}
	
	public Integer getCompletedCount() {
		return completedCount;
	}
	public void setCompletedCount(Integer completedCount) {
		this.completedCount = completedCount;
	}
	public Integer getOverCompletedCount() {
		return overCompletedCount;
	}
	public void setOverCompletedCount(Integer overCompletedCount) {
		this.overCompletedCount = overCompletedCount;
	}
	public Integer getScoredCount() {
		return scoredCount;
	}
	public void setScoredCount(Integer scoredCount) {
		this.scoredCount = scoredCount;
	}
	public Integer getCancleCount() {
		return cancleCount;
	}
	public void setCancleCount(Integer cancleCount) {
		this.cancleCount = cancleCount;
	}
	
}
