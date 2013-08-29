package yofoto.issue.util;
/**
 * @author husan
 * @Date 2012-11-12
 * @description 处理Issue的权限
 */
public class IssueAuth {
	//管理：复制、修改、删除、强制关闭
	private boolean admin;
	//处理：转发、已解决、未修复
	private boolean deal;
	//查看
	private boolean view;
	//评分
	private boolean score;
	
	// 部门主管
	private boolean teamAdmin;
	// 小组主管
	private boolean proAdmin;
	
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public boolean isDeal() {
		return deal;
	}
	public void setDeal(boolean deal) {
		this.deal = deal;
	}
	public boolean isView() {
		return view;
	}
	public void setView(boolean view) {
		this.view = view;
	}
	public boolean isScore() {
		return score;
	}
	public void setScore(boolean score) {
		this.score = score;
	}
	public boolean isTeamAdmin() {
		return teamAdmin;
	}
	public void setTeamAdmin(boolean teamAdmin) {
		this.teamAdmin = teamAdmin;
	}
	public boolean isProAdmin() {
		return proAdmin;
	}
	public void setProAdmin(boolean proAdmin) {
		this.proAdmin = proAdmin;
	}
	

}
