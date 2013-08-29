package yofoto.issue.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.Department;
import yofoto.issue.pojo.Staffer;
import yofoto.issue.service.impl.DepartmentServiceImpl;
import yofoto.issue.service.impl.IssueServiceImpl;
import yofoto.issue.util.AuthUtil;
import yofoto.issue.vo.IssueStatCount;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author husan
 * @Date 2012-11-14
 * @description :图表统计
 */
public class IssueStatAction extends ActionSupport{
	
	private static Log logger = LogFactory.getLog(IssueStatAction.class);
	
	@Autowired
	private DepartmentServiceImpl departImpl;
	@Autowired
	private IssueServiceImpl issueImpl;

	private Integer did;
	
	private Department department;
	
	private Date timeFrom;
	
	private Date timeTo;
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		logger.info(staffer.getName()+"查询统计报表："+timeFrom+":"+timeTo);
		department = departImpl.load(did);
		if(!departImpl.isInDepart(staffer,department)){
			return ERROR;
		}
		//

		if(AuthUtil.isProjectAdmin(staffer, department)){
			request.setAttribute("admin", "admin");
		}
		List<Department> departments = staffer.getDepartments();
		request.setAttribute("did", did);
		request.setAttribute("departments", departments);
		
		if(timeFrom==null||timeTo==null){
			request.setAttribute("flag", "nodata");
			//设定默认时间：为最近一个月
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			calendar.setTime(new Date());
			String to = dateFormat.format(calendar.getTime());
			calendar.add(Calendar.MONTH, -1);
			String from = dateFormat.format(calendar.getTime());
			request.setAttribute("timeFrom", from);
			request.setAttribute("timeTo", to);
			return SUCCESS;
		}
		
		//System.out.println(timeFrom);
		//System.out.println(timeTo);
		//timeTo需要加一天到晚上24:00
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(timeTo);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		Date to = calendar.getTime();
		IssueStatCount issueCount = issueImpl.getIssueStatCount(did, timeFrom, calendar.getTime());
		String issueSumCount = issueImpl.getIssueSumCount(did,timeFrom,to);
		String issueNewCount = issueImpl.getIssueNewCount(did,timeFrom,to);
		String issueModularStat = issueImpl.getIssueModularStat(did, timeFrom, to);
		String issueStafferStat = issueImpl.getIssueStafferStat(did, timeFrom, to);
		request.setAttribute("issueStafferStat", issueStafferStat);
		request.setAttribute("issueModularStat",issueModularStat);
		request.setAttribute("issueNewCount", issueNewCount);
		request.setAttribute("issueSumCount", issueSumCount);
		request.setAttribute("timeFrom", timeFrom);
		request.setAttribute("timeTo", timeTo);
		request.setAttribute("issueCount", issueCount);
		
		
		return SUCCESS;
	}

	public Integer getDid() {
		return did;
	}

	public void setDid(Integer did) {
		this.did = did;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Date getTimeFrom() {
		return timeFrom;
	}

	public void setTimeFrom(Date timeFrom) {
		this.timeFrom = timeFrom;
	}

	public Date getTimeTo() {
		return timeTo;
	}

	public void setTimeTo(Date timeTo) {
		this.timeTo = timeTo;
	}
	
	
	

}
