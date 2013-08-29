package yofoto.issue.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.Department;
import yofoto.issue.pojo.Issue;
import yofoto.issue.pojo.IssueWorkflow;
import yofoto.issue.pojo.Staffer;
import yofoto.issue.service.impl.DepartmentServiceImpl;
import yofoto.issue.service.impl.IssueServiceImpl;
import yofoto.issue.service.impl.StafferServiceImpl;
import yofoto.issue.util.DateUtil;
import yofoto.issue.vo.Page;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author husan
 * @Date 2012-11-10
 * @description
 */
public class AllissueAction extends ActionSupport{
	private static Log logger = LogFactory.getLog(AllissueAction.class);
	@Autowired
	private DepartmentServiceImpl departImpl;
	@Autowired
	private IssueServiceImpl issueImpl;
	
	@Autowired
	private StafferServiceImpl staffImpl;
	
	private Integer did;
	
	private Department department;
	
	private Page<Issue> page;
	

	@Override
	public String execute() throws Exception{
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Staffer staffer = (Staffer) session.getAttribute("user");
		//System.out.println(issue==null);
		department = departImpl.load(did);
		
		//admin用户,若是,则显示
		if(!"admin".equals(staffer.getName()) && !departImpl.isInDepart(staffer,department)){
			return ERROR;
		}
		Criterion[] criterion = new Criterion[3];
		criterion[0] = Restrictions.eq("status", 1);
		criterion[1] = Restrictions.eq("departmentID", did);
		//保密任务
		criterion[2] = Restrictions.or(Restrictions.eq("secret", Boolean.FALSE),
				Restrictions.and(Restrictions.eq("secret",Boolean.TRUE), Restrictions.or(Restrictions.eq("publisher.sid", staffer.getSid()), Restrictions.eq("completer.sid", staffer.getSid()))));

		//判断是不明当前登录用户是当前项目的管理员或者是admin用户,若是,则显示保密的工作清单
		boolean isProjectAdmin = staffImpl.isProjectAdmin(staffer.getSid(), did);
		if(isProjectAdmin || "admin".equals(staffer.getName())){
			criterion[2] = null;
		}
		//////////
		
		if(page==null)
			page = new Page();
		String exportFlag = request.getParameter("exportFlag");
		if("1".equals(exportFlag)){
			logger.info(staffer.getName()+" 导出excel");
			page.setPageNum(1000);
			page.setPageIndex(1);
			page = issueImpl.searchPage(criterion,page);
			HSSFWorkbook wb = createWB(page);
			HttpServletResponse response = ServletActionContext.getResponse();
			
			String fileName = department.getName()+"导出任务.xls";
		    response.setHeader("Content-disposition", "attachment; filename="+new String(fileName.getBytes("UTF-8"),"ISO8859-1"));
		    
		    response.setContentType("application/msexcel");
		    wb.write(response.getOutputStream());
		    response.getOutputStream().close();
		    return null;
		}else{
			//System.out.println("<<<:"+page.getPageIndex());
			logger.info(staffer.getName()+" 查找任务");
			page = issueImpl.searchPage(criterion,page);
			List<Department> departments = staffer.getDepartments();
			
			//admin用户,若是,则显示
			if("admin".equals(staffer.getName())){
				departments = departImpl.getAll();
			}
			List<String> months = DateUtil.getMonths(2013);
			request.setAttribute("months", months);
			Set<Staffer> staffers = department.getStaffers();
			//System.out.println(staffers.size());
			request.setAttribute("staffers", staffers);
			
			request.setAttribute("departments", departments);
			return SUCCESS;
		}
		
	}
	private HSSFWorkbook createWB(Page<Issue> page){
		HSSFWorkbook wb = new HSSFWorkbook();
		CellStyle cellStyle = wb.createCellStyle();
		Font headerFont = wb.createFont(); // 字体
		headerFont.setFontHeightInPoints((short)12);
		headerFont.setFontName("宋体");
		cellStyle.setFont(headerFont);
		cellStyle.setBorderBottom((short)1);
		cellStyle.setBorderLeft((short)1);
		cellStyle.setBorderRight((short)1);
		cellStyle.setBorderTop((short)1);
		cellStyle.setWrapText(true);
		HSSFSheet sheet = wb.createSheet("导出任务");
		HSSFRow row = sheet.createRow(0);
		setCellGBKValue(row.createCell(0),"工作项目",cellStyle);
		setCellGBKValue(row.createCell(1),"任务名称",cellStyle);
		setCellGBKValue(row.createCell(2),"任务要求",cellStyle);
		setCellGBKValue(row.createCell(3),"处理人",cellStyle);
		setCellGBKValue(row.createCell(4),"协调人",cellStyle);
		setCellGBKValue(row.createCell(5),"有效时间",cellStyle);
		setCellGBKValue(row.createCell(6),"优先级",cellStyle);
		setCellGBKValue(row.createCell(7),"是否保密",cellStyle);
		setCellGBKValue(row.createCell(8),"任务状态",cellStyle);
		setCellGBKValue(row.createCell(9),"发布时间",cellStyle);
		setCellGBKValue(row.createCell(10),"完成时间",cellStyle);
		int rowCount = 1;
		for(Issue iss : page.getResults()){
			HSSFRow aRow = sheet.createRow(rowCount++);
			setCellGBKValue(aRow.createCell(0),iss.getModular().getName(),cellStyle);
			setCellGBKValue(aRow.createCell(1),iss.getTitle(),cellStyle);
			List<IssueWorkflow> issueWFs = iss.getIssueWorkflows();
			IssueWorkflow issueWF = issueWFs.get(issueWFs.size()-1);
			setCellGBKValue(aRow.createCell(2),issueWF.getContent(),cellStyle);
			setCellGBKValue(aRow.createCell(3),iss.getCompleter().getName(),cellStyle);
			setCellGBKValue(aRow.createCell(4),iss.getCoordinationer(),cellStyle);
			setCellGBKValue(aRow.createCell(5),iss.getExprireDate(),cellStyle);
			setCellGBKValue(aRow.createCell(6),iss.getPriority(),cellStyle);
			setCellGBKValue(aRow.createCell(7),iss.isSecret(),cellStyle);
			setComplete(aRow.createCell(8),iss.getCompleteStatus(),cellStyle);
			setCellGBKValue(aRow.createCell(9),iss.getPublishDate(),cellStyle);
			setCellGBKValue(aRow.createCell(10),iss.getCompleteDate(),cellStyle);
		}
		return wb;
	}
	private void setComplete(HSSFCell cell,int completeStatus,CellStyle cellStyle){
		if(completeStatus == 1){
			setCellGBKValue(cell,"未完成",cellStyle);
		}else if(completeStatus == 2){
			setCellGBKValue(cell,"待审核",cellStyle);
		}else if(completeStatus == 3){
			setCellGBKValue(cell,"已完成",cellStyle);
		}else if(completeStatus == 4){
			setCellGBKValue(cell,"延期完成",cellStyle);
		}else if(completeStatus == 5){
			setCellGBKValue(cell,"已评分",cellStyle);
		}
	}
	
	private void setCellGBKValue(HSSFCell cell,int value,CellStyle cellStyle){
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(value);
		if(cellStyle!=null)
			cell.setCellStyle(cellStyle);
		
	}
	private void setCellGBKValue(HSSFCell cell,Boolean value,CellStyle cellStyle){
		cell.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
		cell.setCellValue(value);
		if(cellStyle!=null)
			cell.setCellStyle(cellStyle);
		
	}
	private void setCellGBKValue(HSSFCell cell,Date value,CellStyle cellStyle){
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		if(value==null)
			setCellGBKValue(cell,"",cellStyle);
		else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			String value1 = sdf.format(value);
			cell.setCellValue(value1);
			if(cellStyle!=null)
				cell.setCellStyle(cellStyle);
		}
		
	}
	private void setCellGBKValue(HSSFCell cell,String value,CellStyle cellStyle){
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(value);
		if(cellStyle!=null)
			cell.setCellStyle(cellStyle);
		
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

	public Page<Issue> getPage() {
		return page;
	}

	public void setPage(Page<Issue> page) {
		this.page = page;
	}


}
