package yofoto.issue.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.Department;
import yofoto.issue.pojo.Staffer;
import yofoto.issue.service.impl.DepartmentServiceImpl;
import yofoto.issue.service.impl.IssueServiceImpl;
import yofoto.issue.util.AuthUtil;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author husan
 * @Date 2012-11-24
 * @description
 */
public class ScorestatAction extends ActionSupport{
	
	private static Log logger = LogFactory.getLog(ScorestatAction.class);

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
		logger.info(staffer.getName()+"查看评分统计:"+timeFrom+":"+timeTo);
		department = departImpl.load(did);
		if(!AuthUtil.isProjectAdmin(staffer, department)){
			return ERROR;
		}
		List<Department> departments = staffer.getDepartments();
		request.setAttribute("did", did);
		request.setAttribute("departments", departments);
		if(timeFrom==null||timeTo==null){
			request.setAttribute("flag", "nodata");
			//设定默认时间：为上个月
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			calendar.setTime(new Date());
			calendar.set(Calendar.DAY_OF_MONTH,1);
			calendar.set(Calendar.DATE,-1);
			String to = dateFormat.format(calendar.getTime());
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			String from = dateFormat.format(calendar.getTime());
			request.setAttribute("timeFrom", from);
			request.setAttribute("timeTo", to);
			return SUCCESS;
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(timeTo);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		Date to = calendar.getTime();
		String stafferScoreStat = issueImpl.getStafferScoreStat(did,timeFrom,to);
		
		request.setAttribute("timeFrom", timeFrom);
		request.setAttribute("timeTo", timeTo);
		request.setAttribute("stafferScoreStat", stafferScoreStat);
		return SUCCESS;
	}
	
	public String extExcel() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		logger.info(staffer.getName()+"导出excel文件");
		department = departImpl.load(did);
		if(!AuthUtil.isProjectAdmin(staffer, department)){
			return ERROR;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(timeTo);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		Date to = calendar.getTime();
		HSSFWorkbook wb = issueImpl.extScoreStatExcel(did,timeFrom,to);
		HttpServletResponse response = ServletActionContext.getResponse();
		
		String fileName = department.getName()+"评分统计.xls";
	    response.setHeader("Content-disposition", "attachment; filename="+new String(fileName.getBytes("UTF-8"),"ISO8859-1"));
	    
	    response.setContentType("application/msexcel");
	    wb.write(response.getOutputStream());
	    response.getOutputStream().close();
	    return null;
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
