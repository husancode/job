package yofoto.issue.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import yofoto.issue.pojo.Attachment;
import yofoto.issue.pojo.Department;
import yofoto.issue.pojo.Issue;
import yofoto.issue.pojo.IssueWorkflow;
import yofoto.issue.pojo.Modular;
import yofoto.issue.pojo.Staffer;
import yofoto.issue.util.AuthUtil;
import yofoto.issue.util.ConfigUtil;
import yofoto.issue.util.DateUtil;
import yofoto.issue.util.IssueAuth;
import yofoto.issue.util.ReflectionUtils;
import yofoto.issue.util.SendMailUtil;
import yofoto.issue.vo.IssueCountVO;
import yofoto.issue.vo.IssueScore;
import yofoto.issue.vo.IssueStatCount;
import yofoto.issue.vo.Page;

/**
 * @author husan
 * @Date 2012-11-8
 * @description
 */
@Service
public class IssueServiceImpl extends SimpleHibernateDao<Issue,Integer>{
	@Autowired
	private ModularServiceImpl modularImpl;
	@Autowired
	private StafferServiceImpl stafferImpl;
	@Autowired
	private IssueWorkflowServiceImpl isserWFImpl;
	@Override
	@Autowired(required=true)		
	public void setSessionFactory(@Qualifier("issue") SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	} 
	
	private String format(String source){
		int index = source.indexOf(".");
		if(index>0)
		return source.substring(0, index);
		else
			return source;
	}
	//2012/12/17；excel批量导入任务
	public void addBatchIssue(File source, Department department,Staffer staffer)throws Exception{
		FileInputStream is = new FileInputStream(source);
		HSSFWorkbook wb = new HSSFWorkbook(is);
		int sheetNum = wb.getNumberOfSheets();
		for(int i=0; i<sheetNum; i++){
			HSSFSheet childSheet = wb.getSheetAt(i);  
			int rowNum = childSheet.getLastRowNum(); 
			 for(int j=1; j<rowNum; j++)  
             {  
                 HSSFRow row = childSheet.getRow(j);   
                 int cellNum=row.getLastCellNum();  
                   if(cellNum<7){
                	   continue;
                   }
                 String modularName = format(row.getCell(0).toString());
                 String title = row.getCell(1).toString();
                 String content = row.getCell(2).toString();
                 String handler = format(row.getCell(3).toString());
                 String coorder = format(row.getCell(4).toString());
                 Date expireDate = row.getCell(5).getDateCellValue();
                 String prior = format(row.getCell(6).toString());
                 int priority = 1;
                 if(!"".equals(prior))
                	 priority = Integer.parseInt(prior);
                 boolean secret = false;
                 if("是".equals(row.getCell(7).toString()))
                	 secret = true;
                 Modular modular = modularImpl.loadByName(modularName);
                 Staffer completer = stafferImpl.loadByName(handler);
                 //Staffer cooder = stafferImpl.loadByName(coorder);
                 if(modular==null||completer==null)
                	 continue;
                 Date dealDate = new Date();
                 Issue issue = new Issue();
                 issue.setTitle(title);
         		 issue.setPriority(priority);
         		 issue.setCompleteStatus(1);
         		 issue.setDepartmentID(department.getDid());
         		 issue.setTeamID(department.getTeam().getTid());
         		 issue.setSecret(secret);
         		 issue.setPublisher(staffer);
        		 issue.setReviewer(staffer);
        		 issue.setPublishDate(dealDate);
        		 issue.setExprireDate(expireDate);
        		 issue.setStatus(1);
        		 issue.setModular(modular);
        		 issue.setCompleter(completer);
        		 issue.setCoordinationer(coorder);
        		 //
        		 issue.setPercentage(ConfigUtil.percentage);
        		 save(issue);
        		 
        		 IssueWorkflow issueWorkflow = new IssueWorkflow();
        		 issueWorkflow.setCompleteStatus(1);
        		 issueWorkflow.setDealTime(dealDate);
        		 issueWorkflow.setFromStaffer(staffer);
        		 issueWorkflow.setContent(content);
        		 issueWorkflow.setToStaffer(completer);
        		 issueWorkflow.setIssueId(issue.getId());
        		 isserWFImpl.save(issueWorkflow);
        			//发布新任务：发送短信
        		 if(completer.isNotify()){
           				title = issue.getPublisher().getName()+"在【"+modular.getDepartment().getName()+"】发布了新的任务";
        				content = issue.getPublisher().getName()+"发布了新的任务："+"<a href='http://ees.yofoto.com.cn/ees/issuehandle?id="+issue.getId()+"'>"+issue.getTitle()+"</a>"+"<br>"+" <br>任务发布人:"+issue.getPublisher().getName()+"<br><br> <font color='red'>任务接收人:"+issue.getCompleter().getName()+"</font><br><br><br>任务描述:"+issueWorkflow.getContent();
        				//MailSender.send(completer.getEmail(), title, content);
        				//MailAttachment.send(completer.getEmail(), title, content,attachmentList);
        				List<String> to = new ArrayList<String>();
        				to.add(completer.getEmail());
        				SendMailUtil.send(to,title,content,null);
        			}
        		 
                  
             }  
		}
		
	}
	
	public HSSFWorkbook extScoreStatExcel(Integer did , Date timeFrom , Date timeTo){
		Query query = super.getSession().createSQLQuery("select name,sid,issuecount,score from (select s2.name,s2.id as sid from j_project_staffer j2 left join staffer s2 on j2.sid=s2.id where j2.did=? and s2.status=1) t1 left join (select count(issueid)as issueCount,sum(score)as score,tostaffer_id from (select min(iw1.id) id,iw1.issueid,iw1.tostaffer_id,min((iw1.professionalscore+iw1.progressscore+m1.score)*m1.percentage/100) score from issueworkflow iw1,issue i1,modular m1 where iw1.issueid=i1.id and i1.modular_id=m1.id and iw1.completestatus=1 and i1.completestatus=5  and i1.departmentid=? and dealtime<=? and dealtime>= ? group by iw1.issueid,iw1.tostaffer_id) a1 group by a1.tostaffer_id) t2 on t1.sid=t2.tostaffer_id order by sid asc")
		.addScalar("NAME", Hibernate.STRING)
		.addScalar("SID", Hibernate.INTEGER)
		.addScalar("ISSUECOUNT", Hibernate.STRING)
		.addScalar("SCORE", Hibernate.STRING)
		.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter(0, did);
		query.setParameter(1, did);
		query.setParameter(2, timeTo);
		query.setParameter(3, timeFrom);
		List<Map> result = query.list();
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
		
		HSSFSheet sheet = wb.createSheet("评分统计");
		HSSFRow row = sheet.createRow(0);
		setCellGBKValue(row.createCell(0),"项目人员",cellStyle);
		setCellGBKValue(row.createCell(1),"参与任务数",cellStyle);
		setCellGBKValue(row.createCell(2),"得分",cellStyle);
		int rowCount = 1;
		for(Map map : result){
			HSSFRow aRow = sheet.createRow(rowCount++);
			setCellGBKValue(aRow.createCell(0),(String)map.get("NAME"),cellStyle);
			setCellGBKValue(aRow.createCell(1),(String)map.get("ISSUECOUNT"),cellStyle);
			setCellGBKValue(aRow.createCell(2),(String)map.get("SCORE"),cellStyle);
		}
		return wb;
		
	}
	private void setCellGBKValue(HSSFCell cell,String value,CellStyle cellStyle){
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(value);
		if(cellStyle!=null)
			cell.setCellStyle(cellStyle);
		
	}
	
	public String getStafferScoreStat(Integer did, Date timeFrom ,Date timeTo){
		Query query = super.getSession().createSQLQuery("select name,sid,issuecount,score from (select s2.name,s2.id as sid from j_project_staffer j2 left join staffer s2 on j2.sid=s2.id where j2.did=? and s2.status=1) t1 left join (select count(issueid)as issueCount,sum(score)as score,tostaffer_id from (select min(iw1.id) id,iw1.issueid,iw1.tostaffer_id,min((iw1.professionalscore+iw1.progressscore+m1.score)*m1.percentage/100) score from issueworkflow iw1,issue i1,modular m1 where iw1.issueid=i1.id and i1.modular_id=m1.id and iw1.completestatus=1 and i1.completestatus=5  and i1.departmentid=? and dealtime<=? and dealtime>= ? group by iw1.issueid,iw1.tostaffer_id) a1 group by a1.tostaffer_id) t2 on t1.sid=t2.tostaffer_id order by sid asc")
				.addScalar("NAME", Hibernate.STRING)
				.addScalar("SID", Hibernate.INTEGER)
				.addScalar("ISSUECOUNT", Hibernate.INTEGER)
				.addScalar("SCORE", Hibernate.STRING)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter(0, did);
		query.setParameter(1, did);
		query.setParameter(2, timeTo);
		query.setParameter(3, timeFrom);
		List<Map> result = query.list();
		StringBuilder sb = new StringBuilder("");
		for(Map map : result){
			sb.append(map.get("NAME")+";"+map.get("ISSUECOUNT")+";"+map.get("SCORE")+"\\n");
		}
		return sb.toString();
	}
	
	//2012-11-23 :评分
	public void score(String issueIds,String scores){
		Query query1 = super.getSession().createSQLQuery("update issue i1 set i1.completestatus=5 where i1.id=?");
		//Query query2 = super.getSession().createSQLQuery("update issueworkflow iw1 set iw1.score=? where iw1.id=?");
		Query query2 = super.getSession().createSQLQuery("update issueworkflow iw1 set iw1.professionalscore=?,iw1.progressscore=?,iw1.scorenote=? where iw1.id=?");
		if(issueIds!=null && !issueIds.equals("")){
			String[] ids = issueIds.split(":");
			for(String issueId : ids){
				if(!ids.equals("")){
					query1.setParameter(0, Integer.parseInt(issueId));
					query1.executeUpdate();
				}
			}
		}
		
		if(scores!=null && !scores.equals("")){
			String[] score = scores.split(";");
		
			for(String s : score){
				
				if(!s.equals("")){
					String[] ss = s.split(":");
					
					String id = ss[0];
					String s1 = ss[1];
					String s2 = ss[2];
					String note="";
					if(ss.length>3)
					 note = ss[3];
					
					query2.setParameter(0, Double.parseDouble(s1));
					query2.setParameter(1, Double.parseDouble(s2));
					query2.setParameter(2, note);
					query2.setParameter(3, Integer.parseInt(id));
					query2.executeUpdate();
				}
			}
		}

		
	}
	
	//2012.11.15 任务按处理人统计
	public String getIssueStafferStat(Integer did ,Date timeFrom ,Date timeTo){
		Query query = super.getSession().createSQLQuery("select t1.name,c1,c2,c3,c4,c5 from(" +
				"select name from j_project_staffer j2 left join staffer s2 on j2.sid=s2.id where j2.did=? and s2.status=1) t1 " +
				"left join(select s1.name,count(CASE WHEN i1.completestatus = 1 THEN 1 ELSE NULL END) c1," +
				"count(CASE WHEN i1.completestatus = 2 THEN 1 ELSE NULL END) c2,count(CASE WHEN i1.completestatus = 3 THEN 1 ELSE NULL END) c3," +
				"count(CASE WHEN i1.completestatus = 4 THEN 1 ELSE NULL END) c4,count(CASE WHEN i1.completestatus = 5 THEN 1 ELSE NULL END) c5" +
				" from j_project_staffer j1 left join staffer s1   on j1.sid = s1.id left join issue i1 on s1.id=i1.completer_id " +
				"where s1.status=1  and i1.status=1 and j1.did=? and  i1.publishdate>=? and i1.publishdate <=? and i1.departmentID=?  group by s1.name) t2 on t1.name = t2.name order by t1.name asc")
				.addScalar("NAME",Hibernate.STRING)
				.addScalar("C1", Hibernate.INTEGER)
				.addScalar("C2", Hibernate.INTEGER)
				.addScalar("C3", Hibernate.INTEGER)
				.addScalar("C4", Hibernate.INTEGER)
				.addScalar("C5", Hibernate.INTEGER)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter(0, did);
		query.setParameter(1, did);
		query.setParameter(2, timeFrom);
		query.setParameter(3, timeTo);
		query.setParameter(4, did);
		List<Map> result = query.list();
		StringBuilder sb = new StringBuilder("");
		for(Map map : result){
			sb.append(map.get("NAME")+";"+map.get("C1")+";"+map.get("C2")+";"+map.get("C3")+";"+map.get("C4")+";"+map.get("C5")+"\\n");
		}
		return sb.toString();
	}
	
	
	//2012.11.15 任务分模块统计
	public String getIssueModularStat(Integer did, Date timeFrom , Date timeTo){
		Query query = super.getSession().createSQLQuery("select t1.modular_id as mid,t1.c1,t1.c2,t1.c3,t1.c4,t1.c5,t2.name from(select i.modular_id,count(CASE WHEN i.completestatus = 1 THEN 1 ELSE NULL END) c1,count(CASE WHEN i.completestatus = 2 THEN 1 ELSE NULL END) c2,count(CASE WHEN i.completestatus = 3 THEN 1 ELSE NULL END) c3,count(CASE WHEN i.completestatus = 4 THEN 1 ELSE NULL END) c4,count(CASE WHEN i.completestatus = 5 THEN 1 ELSE NULL END) c5 from issue i where  i.status=1 and i.publishdate>=? and i.publishdate<=? and i.departmentid=? group by i.modular_id) t1 left join modular t2 on t1.modular_id=t2.id order by t1.modular_id asc")
		.addScalar("MID",Hibernate.INTEGER)
		.addScalar("C1", Hibernate.INTEGER)
		.addScalar("C2", Hibernate.INTEGER)
		.addScalar("C3", Hibernate.INTEGER)
		.addScalar("C4", Hibernate.INTEGER)
		.addScalar("C5", Hibernate.INTEGER)
		.addScalar("NAME",Hibernate.STRING)
		.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter(0, timeFrom);
		query.setParameter(1, timeTo);
		query.setParameter(2, did);
		List<Map> result = query.list();
		StringBuilder sb = new StringBuilder("");
		for(Map map : result){
			sb.append(map.get("NAME")+";"+map.get("C1")+";"+map.get("C2")+";"+map.get("C3")+";"+map.get("C4")+";"+map.get("C5")+"\\n");
		}
		return sb.toString();
	}
	
	//2012.11.15每天累计任务数量统计
	public String getIssueSumCount(Integer did,Date timeFrom ,Date timeTo){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateFrom = dateFormat.format(timeFrom);
		String dateTo = dateFormat.format(timeTo);
		Query query = super.getSession().createSQLQuery(
				" select DD, AA, CC from (select to_char(to_date(?, 'yyyy-MM-dd') + (rownum - 1),'yyyy-MM-dd') DD from dual connect by rownum <= ((to_date(?, 'yyyy-MM--dd')) - to_date(?, 'yyyy-MM-dd')) + 1) t1 left join (select max(aa) aa, cc from (select to_char(publishdate, 'yyyy-MM-dd') cc, aa from (select sum(1) over(order by id asc) aa,i.publishdate from issue i where i.status=1 and i.publishdate <= ? and i.departmentid=?) where publishdate >=?)group by cc) t2 on t1.DD = t2.CC order by t1.DD asc")
				.addScalar("DD",Hibernate.STRING)
				.addScalar("AA", Hibernate.INTEGER)
				.addScalar("CC", Hibernate.STRING)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter(0, dateFrom);
		query.setParameter(1, dateTo);
		query.setParameter(2, dateFrom);
		query.setParameter(3, timeTo);
		query.setParameter(4, did);
		query.setParameter(5, timeFrom);
		List<Map> result =  query.list();
		StringBuilder sb = new StringBuilder("<chart><graphs><graph gid=\\\"0\\\">");
		for(int i=0; i<result.size(); i++){
			Map map = result.get(i);
			if(map.get("AA")== null&&i>0){
				map.put("AA",result.get(i-1).get("AA"));
			}
			sb.append("<point x=\\\""+map.get("DD")+"\\\" y=\\\""+map.get("AA")+"\\\"></point>");
		}
		
		sb.append("</graph></graphs></chart>");
		return sb.toString();
	}
	
	//2012.11.14 每天新增任务数量统计
	public String getIssueNewCount(Integer did,Date timeFrom ,Date timeTo){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateFrom = dateFormat.format(timeFrom);
		String dateTo = dateFormat.format(timeTo);
		Query query = super.getSession().createSQLQuery(
				"select DD,AA,CC from(select to_char((to_date(?,'yyyy-MM-dd')+(rownum-1)),'yyyy-MM-dd') DD from dual  connect by rownum<= to_date(?,'yyyy-MM-dd') - to_date(?,'yyyy-MM-dd')+1 ) t1 left  join (select count(id) AA,to_char(publishdate,'yyyy-MM-dd') CC from issue i where i.status=1 and i.publishdate>=? and i.publishdate<=? and i.departmentid=? group by to_char(publishdate,'yyyy-MM-dd')) t2 on t1.DD=t2.CC order by t1.DD asc")
				.addScalar("DD",Hibernate.STRING)
				.addScalar("AA", Hibernate.INTEGER)
				.addScalar("CC", Hibernate.STRING)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		
		query.setParameter(0, dateFrom);
		query.setParameter(1, dateTo);
		query.setParameter(2, dateFrom);
		query.setParameter(3, timeFrom);
		query.setParameter(4, timeTo);
		query.setParameter(5, did);
		List<Map> result =  query.list();
		StringBuffer sb = new StringBuffer("<chart><graphs><graph gid=\\\"0\\\">");
		for(Map map : result){
			sb.append("<point x=\\\""+map.get("DD")+"\\\" y=\\\""+map.get("AA")+"\\\"></point>");
			
		}
		sb.append("</graph></graphs></chart>");
		return sb.toString();
		
	}
	
	public IssueStatCount getIssueStatCount(Integer did,Date timeFrom ,Date timeTo){
		IssueStatCount issueStatCount = new IssueStatCount();
		Query  query1 = super.getSession().createSQLQuery(
				"select count(CASE WHEN i.priority = 1 THEN 1 ELSE NULL END) P1,count(CASE WHEN i.priority = 2 THEN 1 ELSE NULL END) P2,count(CASE WHEN i.priority = 3 THEN 1 ELSE NULL END) P3,count(CASE WHEN i.priority = 4 THEN 1 ELSE NULL END) P4,count(CASE WHEN i.priority = 5 THEN 1 ELSE NULL END) P5 from issue i where i.status=1 and i.publishdate>=? and i.publishdate<=? and i.departmentID=?")
				.addScalar("P1", Hibernate.INTEGER)
				.addScalar("P2", Hibernate.INTEGER)
				.addScalar("P3", Hibernate.INTEGER)
				.addScalar("P4", Hibernate.INTEGER)
				.addScalar("P5", Hibernate.INTEGER)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query1.setParameter(0, timeFrom);
		query1.setParameter(1, timeTo);
		query1.setParameter(2, did);
		Map map = (Map)query1.uniqueResult();
		issueStatCount.setP1((Integer)map.get("P1"));
		issueStatCount.setP2((Integer)map.get("P2"));
		issueStatCount.setP3((Integer)map.get("P3"));
		issueStatCount.setP4((Integer)map.get("P4"));
		issueStatCount.setP5((Integer)map.get("P5"));
		Query query2 = super.getSession().createSQLQuery(
				"select count(CASE WHEN i.completestatus = 1 THEN 1 ELSE NULL END) C1,count(CASE WHEN i.completestatus = 2 THEN 1 ELSE NULL END) C2,count(CASE WHEN i.completestatus = 3 THEN 1 ELSE NULL END) C3,count(CASE WHEN i.completestatus = 4 THEN 1 ELSE NULL END) C4,count(CASE WHEN i.completestatus = 5 THEN 1 ELSE NULL END) C5,count(CASE WHEN i.completestatus = 6 THEN 1 ELSE NULL END) C6 from issue i where i.status=1 and i.publishdate>=? and i.publishdate<=? and i.departmentID=?")
				.addScalar("C1", Hibernate.INTEGER)
				.addScalar("C2", Hibernate.INTEGER)
				.addScalar("C3", Hibernate.INTEGER)
				.addScalar("C4", Hibernate.INTEGER)
				.addScalar("C5", Hibernate.INTEGER)
				.addScalar("C6", Hibernate.INTEGER)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query2.setParameter(0, timeFrom);
		query2.setParameter(1, timeTo);
		query2.setParameter(2, did);
		map = (Map) query2.uniqueResult();
		issueStatCount.setC1((Integer)map.get("C1"));
		issueStatCount.setC2((Integer)map.get("C2"));
		issueStatCount.setC3((Integer)map.get("C3"));
		issueStatCount.setC4((Integer)map.get("C4"));
		issueStatCount.setC5((Integer)map.get("C5"));
		issueStatCount.setC6((Integer)map.get("C6"));
		return issueStatCount;
		
	}
	
	public Issue dealEdit(final Issue issue,final Issue aissue, IssueWorkflow issueWF,String note){
		issue.setTitle(aissue.getTitle());
		issue.setPriority(aissue.getPriority());
		issue.setModular(aissue.getModular());
		
		//issue.setExprireDate(aissue.getExprireDate());
		if(aissue.getExprireDate().compareTo(issue.getExprireDate())!=0){
			issue.setOriginalExpireDate(issue.getExprireDate());
		}
		issue.setExprireDate(aissue.getExprireDate());
		issue.setReason(aissue.getReason());
		//2012-12-15
		issue.setSecret(aissue.isSecret());
		//
		
		
		if(aissue.getCompleter()!=null){
			issue.setCompleter(aissue.getCompleter());
			issue.setCompleteStatus(1);
		}
		save(issue);
		isserWFImpl.save(issueWF);
		//转发
		if(aissue.getCompleter()!=null){
			IssueWorkflow issueWorkflow = new IssueWorkflow();
			issueWorkflow.setCompleteStatus(1);
			issueWorkflow.setContent(note);
			issueWorkflow.setDealTime(new Date());
			//修改处理为发布者，不包括管理员
			issueWorkflow.setFromStaffer(issue.getPublisher());
			issueWorkflow.setToStaffer(issue.getCompleter());
			issueWorkflow.setIssueId(issue.getId());
			isserWFImpl.save(issueWorkflow);
			
		}
		return issue;
	}
	//审核：已完成
	public void dealExam(final IssueWorkflow issueWorkflow, final Issue issue){
		if(issue.isOverdue()){
			issueWorkflow.setCompleteStatus(4);
		}else issueWorkflow.setCompleteStatus(3);
		
		issue.setCompleteStatus(issueWorkflow.getCompleteStatus());
		//审核完成，处理人为最后一个处理任务的人员，而不是审核者
		//issue.setCompleter(issueWorkflow.getToStaffer());
		
		issue.setCompleter(issue.getIssueWorkflows().get(0).getFromStaffer());
		issue.setCompleteDate(issueWorkflow.getDealTime());
		int overLevel = DateUtil.getOverLevel(issue.getExprireDate(), issue.getCompleteDate());
		issue.setOverLevel(overLevel);
		isserWFImpl.save(issueWorkflow);
		super.getSession().merge(issue);
		
		String title = "【"+issue.getModular().getDepartment().getName()+"】有任务已完成";
		String content ="有任务完成："+ "<a href='http://ees.yofoto.com.cn/ees/issuehandle?id="+issue.getId()+"'>"+issue.getTitle()+"</a>"+"<br>"+" <br>任务发布人:"+issue.getPublisher().getName()+"<br><br> <font color='red'>任务接收人:"+issue.getCompleter().getName()+"</font><br><br><br>任务描述:"+issueWorkflow.getContent();
		//MailSender.send(issue.getCompleter().getEmail(), title, content);
		//准备好邮件附件清单
		String targetDirectory = ServletActionContext.getServletContext().getRealPath("/uploadfiles/");
		List<String> attachmentList = getAttachment(issueWorkflow.getAttachments() ,targetDirectory);

		List<String> to = new ArrayList<String>();
		to.add(issue.getCompleter().getEmail());
		
		

		SendMailUtil.send(to,title,content,attachmentList);
	}
	
	public void dealComit(IssueWorkflow issueWorkflow, Issue issue){
		//处理者即是发布者，无须审核
		if(issueWorkflow.getFromStaffer().getSid().equals(issueWorkflow.getToStaffer().getSid())){
			if(issue.isOverdue()){
				issueWorkflow.setCompleteStatus(4);
				
			}else{
				issueWorkflow.setCompleteStatus(3);
			}
			issue.setCompleteDate(issueWorkflow.getDealTime());
			int overLevel = DateUtil.getOverLevel(issue.getExprireDate(), issue.getCompleteDate());
			issue.setOverLevel(overLevel);
		}else{
			issueWorkflow.setCompleteStatus(2);
		}
		isserWFImpl.save(issueWorkflow);
		issue.setCompleteStatus(issueWorkflow.getCompleteStatus());
		if(issueWorkflow.getCompleteStatus()>=3)
			issue.setCompleteDate(issueWorkflow.getDealTime());
		issue.setCompleter(issueWorkflow.getToStaffer());

		super.getSession().merge(issue);
		
		//准备好邮件附件清单
		String targetDirectory = ServletActionContext.getServletContext().getRealPath("/uploadfiles/");
		List<String> attachmentList = getAttachment(issueWorkflow.getAttachments() ,targetDirectory);
		List<String> to = new ArrayList<String>();
		
		//待审核：发送短信
		//给审核人发送邮件
		if(issue.getCompleteStatus() == 2 && issue.getCompleter().isNotify()){
			String title = "【"+issue.getModular().getDepartment().getName()+"】有任务待审核";
			String content = "有任务完成："+"<a href='http://ees.yofoto.com.cn/ees/issuehandle?id="+issue.getId()+"'>"+issue.getTitle()+"</a>"+"<br>"+" <br>任务发布人:"+issue.getPublisher().getName()+"<br><br> <font color='red'>任务接收人:"+issue.getCompleter().getName()+"</font><br><br><br>任务描述:"+issueWorkflow.getContent();
			//MailSender.send(issue.getCompleter().getEmail(), title, content);
			to = new ArrayList<String>();
			//to.add(issue.getCompleter().getEmail());
			to.add(issue.getPublisher().getEmail());
			SendMailUtil.send(to,title,content,attachmentList);
		}
		
		
		if(issue.getCompleteStatus() == 3 && issue.getCompleter().isNotify()){
			String title = "【"+issue.getModular().getDepartment().getName()+"】有任务完成";
			String content = "有任务完成："+"<a href='http://ees.yofoto.com.cn/ees/issuehandle?id="+issue.getId()+"'>"+issue.getTitle()+"</a>"+"<br>"+" <br>任务发布人:"+issue.getPublisher().getName()+"<br><br> <font color='red'>任务接收人:"+issue.getCompleter().getName()+"</font><br><br><br>任务描述:"+issueWorkflow.getContent();
			//MailSender.send(issue.getCompleter().getEmail(), title, content);
			to = new ArrayList<String>();
			to.add(issue.getCompleter().getEmail());
			SendMailUtil.send(to,title,content,attachmentList);
		}
	
		/*if(issue.getCompleteStatus() == 3 && issue.getCoordinationer() != null && issue.getCoordinationer().isNotify()){
			String title = issue.getPublisher().getName()+"在【"+issue.getModular().getDepartment().getName()+"】有任务完成";
			String content = issue.getPublisher().getName()+"有任务完成："+"<a href='http://ees.yofoto.com.cn/ees/issuehandle?id="+issue.getId()+"'>"+issue.getTitle()+"</a>"+"<br>"+" <br>任务发布人:"+issue.getPublisher().getName()+"<br><br> <font color='red'>任务接收人:"+issue.getCompleter().getName()+"</font><br><br><br>任务描述:"+issueWorkflow.getContent();
			//MailSender.send(coordinationer.getEmail(), title, content);
			//SendMailUtil.send(coordinationer.getEmail(), title, content,attachmentList);
			to = new ArrayList<String>();
			to.add(issue.getCoordinationer().getEmail());
			SendMailUtil.send(to,title,content,attachmentList);
		}*/
		
		
		//抄送人发送短信
		String cctos = issue.getEmailRecivers();
		if(issue.getCompleteStatus() == 3 && cctos != null && cctos.length() > 0){
			String[] staffids = cctos.split(",");
			for(int id =0 ;id<staffids.length;id++){
				if(staffids[id].equals("")) continue;
				Integer sid = Integer.valueOf(staffids[id]);
				Staffer staff = stafferImpl.load(sid);
				if(staff.isNotify()){
					String title = "【"+issue.getModular().getDepartment().getName()+"】有任务完成";
					String content = "<a href='http://ees.yofoto.com.cn/ees/issuehandle?id="+issue.getId()+"'>"+issue.getTitle()+"</a>"+"<br>"+" <br>任务发布人:"+issue.getPublisher().getName()+"<br><br> <font color='red'>任务接收人:"+issue.getCompleter().getName()+"</font><br><br><br>任务描述:"+issueWorkflow.getContent();
//					MailSender.send(staff.getEmail(), title, content);
					to = new ArrayList<String>();
					to.add(staff.getEmail());
					SendMailUtil.send(to,title,content,attachmentList);
				}
			}
		}


	}
	
	public void dealTrans(final IssueWorkflow issueWorkflow,final Issue issue){
		isserWFImpl.save(issueWorkflow);
		issue.setCompleteStatus(issueWorkflow.getCompleteStatus());
		issue.setCompleter(issueWorkflow.getToStaffer());
		super.getSession().merge(issue);
		
		//准备好邮件附件清单
		String targetDirectory = ServletActionContext.getServletContext().getRealPath("/uploadfiles/");
		List<String> attachmentList = getAttachment(issueWorkflow.getAttachments() ,targetDirectory);
		List<String> to = new ArrayList<String>();
				
		
		//发送短信
		if(issue.getCompleter().isNotify()){
			String title = "【"+issue.getModular().getDepartment().getName()+"】有任务更新";
			String content = "<a href='http://ees.yofoto.com.cn/ees/issuehandle?id="+issue.getId()+"'>"+issue.getTitle()+"</a>"+"<br>"+" <br>任务发布人:"+issue.getPublisher().getName()+"<br><br> <font color='red'>任务接收人:"+issue.getCompleter().getName()+"</font><br><br><br>任务描述:"+issueWorkflow.getContent();
			//MailSender.send(issue.getCompleter().getEmail(), title, content);
			to = new ArrayList<String>();
			to.add(issue.getCompleter().getEmail());
			SendMailUtil.send(to,title,content,attachmentList);
		}
		
		
/*
		if(issue.getCoordinationer() != null && issue.getCoordinationer().isNotify()){
			String title = issue.getPublisher().getName()+"在【"+issue.getModular().getDepartment().getName()+"】有任务更新";
			String content = issue.getPublisher().getName()+"有任务更新："+"<a href='http://ees.yofoto.com.cn/ees/issuehandle?id="+issue.getId()+"'>"+issue.getTitle()+"</a>"+"<br>"+" <br>任务发布人:"+issue.getPublisher().getName()+"<br><br> <font color='red'>任务接收人:"+issue.getCompleter().getName()+"</font><br><br><br>任务描述:"+issueWorkflow.getContent();
			//MailSender.send(coordinationer.getEmail(), title, content);
			//SendMailUtil.send(coordinationer.getEmail(), title, content,attachmentList);
			to = new ArrayList<String>();
			to.add(issue.getCoordinationer().getEmail());
			SendMailUtil.send(to,title,content,attachmentList);
		}*/
		
		
		//抄送人发送短信
		String cctos = issue.getEmailRecivers();
		if(cctos != null && cctos.length() > 0){
			String[] staffids = cctos.split(",");
			for(int id =0 ;id<staffids.length;id++){
				if(staffids[id].equals("")) continue;
				Integer sid = Integer.valueOf(staffids[id]);
				Staffer staff = stafferImpl.load(sid);
				if(staff.isNotify()){
					String title = "【"+issue.getModular().getDepartment().getName()+"】有任务更新";
					String content = "<a href='http://ees.yofoto.com.cn/ees/issuehandle?id="+issue.getId()+"'>"+issue.getTitle()+"</a>"+"<br>"+" <br>任务发布人:"+issue.getPublisher().getName()+"<br><br> <font color='red'>任务接收人:"+issue.getCompleter().getName()+"</font><br><br><br>任务描述:"+issueWorkflow.getContent();
//					MailSender.send(staff.getEmail(), title, content);
					to = new ArrayList<String>();
					to.add(staff.getEmail());
					SendMailUtil.send(to,title,content,attachmentList);
				}
			}
		}
		
	}
	
	//issue权限判定
	public IssueAuth issueAuth(Issue issue, Staffer staffer){
		IssueAuth issueAuth = new IssueAuth();
		Query query1 = super.getSession().createSQLQuery("select 1 from issue i where i.publisher_id= ? and i.id=?");
		query1.setParameter(0, staffer.getSid());
		query1.setParameter(1, issue.getId());
		if(query1.list().size()>0){
			issueAuth.setAdmin(true);
			issueAuth.setView(true);
		}
		Query query2 = super.getSession().createSQLQuery("select 1 from issue i where i.completer_id = ? and i.id=?");
		query2.setParameter(0, staffer.getSid());
		query2.setParameter(1, issue.getId());
		if(query2.list().size()>0){
			issueAuth.setDeal(true);
			issueAuth.setView(true);
		}
		//2013-514增加权限控制，主要是延期，修改系数；评分
		//
		Query query11 = super.getSession().createSQLQuery("select p1.admin from issue i1,project p1 where i1.departmentid=p1.id and i1.id=?").addScalar("admin", Hibernate.STRING);
		
		query11.setParameter(0, issue.getId());
		String projectAdmin = (String)query11.uniqueResult();
		boolean proAdmin = AuthUtil.isProjectAdmin(staffer, projectAdmin);
		issueAuth.setProAdmin(proAdmin);
		Query query22 = super.getSession().createSQLQuery("select t1.admin from issue i1,team t1 where i1.teamid=t1.id and i1.id=?").addScalar("admin",Hibernate.STRING);
		query22.setParameter(0,issue.getId());
		String teamAdmin = (String) query22.uniqueResult();
		boolean tAdmin = AuthUtil.isTeamAdmin(staffer, teamAdmin);
		issueAuth.setTeamAdmin(tAdmin);
		if(proAdmin || tAdmin){
			//小组或部门主管都可以评分
			issueAuth.setScore(true);
		}
		if(!issueAuth.isView())	{
			Query query3 = super.getSession().createSQLQuery(
					"select 1 from issue i ,staffer st,j_project_staffer jp where i.departmentid = jp.did and jp.sid=st.id and st.id=? and i.id=? ");
			query3.setParameter(0, staffer.getSid());
			query3.setParameter(1, issue.getId());
			if(query3.list().size()>0)
				issueAuth.setView(true);
			
		}
		
		//超级管理员可查看所有团队,所有项目
		if("admin".equals(staffer.getName())){
			issueAuth.setAdmin(true);
			issueAuth.setView(true);
			issueAuth.setScore(true);
			issueAuth.setTeamAdmin(true);
			issueAuth.setProAdmin(true);
		}
		
		return issueAuth;
		
	}
	
	public Page<Issue> searchPage(final Criterion[] criterion,final Page<Issue> page){
		//System.out.println("------------");
		//ShowField.showFild(page);
		Criteria criteria = super.createCriteria(getCriterions(page));
		for(Criterion c : criterion){
			if(c!=null)
			criteria.add(c);
		}
		if(page.isAutoCount()){
			long total = countCriteriaResult(criteria);
			page.setTotal(total);
		}
		//设置分页,排序条件
		criteria = setPageParater(criteria,page);
		/*
		criteria.setFetchMode("publisher", FetchMode.SELECT);
		criteria.setFetchMode("completer", FetchMode.SELECT);
		criteria.setFetchMode("reviewer", FetchMode.SELECT);*/
		//对分页无效，查询后的去重复
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Issue> result = criteria.list();
		//System.out.println(result.size());
		page.setResults(result);
		return page;
	}
	
	public List<Issue> getIssues(int page,int pageNum,Criterion[] criterions){
		Criteria issues = super.createCriteria(criterions);
		issues.addOrder(Order.desc("id"));
		issues.setMaxResults(pageNum);
		issues.setFirstResult((page-1)*pageNum);
		issues.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return issues.list();
		
	}
	
	public IssueCountVO getIssueCount(Integer did){
		IssueCountVO issueVO = new IssueCountVO();
		Query  query1 = super.getSession().createSQLQuery(
				"select count(1) totalCount,count(CASE WHEN i.completestatus = 1 THEN 1 ELSE NULL END) unCompletedCount,count(CASE WHEN i.completestatus = 2 THEN 1 ELSE NULL END) toExamineCount,count(CASE WHEN i.completestatus = 3 THEN 1 ELSE NULL END) completedCount,count(CASE WHEN i.completestatus = 4 THEN 1 ELSE NULL END) overCompletedCount,count(CASE WHEN i.completestatus = 5 THEN 1 ELSE NULL END) scoredCount,count(CASE WHEN i.completestatus = 6 THEN 1 ELSE NULL END) cancleCount from issue i where i.status=1 and i.departmentID=?")
				.addScalar("TOTALCOUNT", Hibernate.INTEGER)
				.addScalar("UNCOMPLETEDCOUNT", Hibernate.INTEGER)
				.addScalar("TOEXAMINECOUNT", Hibernate.INTEGER)
				.addScalar("COMPLETEDCOUNT", Hibernate.INTEGER)
				.addScalar("OVERCOMPLETEDCOUNT", Hibernate.INTEGER)
				.addScalar("SCOREDCOUNT",Hibernate.INTEGER)
				.addScalar("CANCLECOUNT",Hibernate.INTEGER)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query1.setParameter(0, did);
		Map map = (Map)query1.uniqueResult();
		issueVO.setTotalCount((Integer)map.get("TOTALCOUNT"));
		issueVO.setUnCompletedCount((Integer)map.get("UNCOMPLETEDCOUNT"));
		issueVO.setToExamineCount((Integer)map.get("TOEXAMINECOUNT"));
		issueVO.setCompletedCount((Integer)map.get("COMPLETEDCOUNT"));
		issueVO.setOverCompletedCount((Integer)map.get("OVERCOMPLETEDCOUNT"));
		issueVO.setScoredCount((Integer)map.get("SCOREDCOUNT"));
		issueVO.setCancleCount((Integer)map.get("CANCLECOUNT"));
		return issueVO;
	}
	
	public void addIssue(Staffer staffer,Issue issue,Integer handler,String coord,Integer modular,IssueWorkflow issueWorkflow){
		Modular modul = modularImpl.load(modular);
		Staffer completer = stafferImpl.load(handler);
		//Staffer coordinationer = stafferImpl.load(coord);
		//System.out.println(coordinationer.getName());
		issue.setModular(modul);
		issue.setCompleter(completer);
		//2012-12-4
		issue.setCoordinationer(coord);
		save(issue);
		
		//取得issue.getId(),更新title=id#title
		//String titlebyid = issue.getId()+"#"+issue.getTitle();
		//issue.setTitle(titlebyid);
		//save(issue);
		
		issueWorkflow.setToStaffer(completer);
		issueWorkflow.setIssueId(issue.getId());
		isserWFImpl.save(issueWorkflow);
		
		//准备好邮件附件清单
		String targetDirectory = ServletActionContext.getServletContext().getRealPath("/uploadfiles/");
		List<String> attachmentList = getAttachment(issueWorkflow.getAttachments() ,targetDirectory);
		List<String> to = new ArrayList<String>();
				
		
		//发布新任务：发送email
		if(completer.isNotify()){
			String title = issue.getPublisher().getName()+"在【"+modul.getDepartment().getName()+"】发布了新的任务";
			String content = issue.getPublisher().getName()+"发布了新的任务："+"<a href='http://ees.yofoto.com.cn/ees/issuehandle?id="+issue.getId()+"'>"+issue.getTitle()+"</a>"+"<br>"+" <br>任务发布人:"+issue.getPublisher().getName()+"<br><br> <font color='red'>任务接收人:"+issue.getCompleter().getName()+"</font><br><br><br>任务描述:"+issueWorkflow.getContent();
			//MailSender.send(completer.getEmail(), title, content);
			//MailAttachment.send(completer.getEmail(), title, content,attachmentList);
			to = new ArrayList<String>();
			to.add(completer.getEmail());
			SendMailUtil.send(to,title,content,attachmentList);
		}
		
		/*if(coordinationer.isNotify()){
			String title = issue.getPublisher().getName()+"在【"+modul.getDepartment().getName()+"】发布了新的任务";
			String content = issue.getPublisher().getName()+"发布了新的任务："+"<a href='http://ees.yofoto.com.cn/ees/issuehandle?id="+issue.getId()+"'>"+issue.getTitle()+"</a>"+"<br>"+" <br>任务发布人:"+issue.getPublisher().getName()+"<br><br> <font color='red'>任务接收人:"+issue.getCompleter().getName()+"</font><br><br><br>任务描述:"+issueWorkflow.getContent();
			//MailSender.send(coordinationer.getEmail(), title, content);
			//SendMailUtil.send(coordinationer.getEmail(), title, content,attachmentList);
			to = new ArrayList<String>();
			to.add(coordinationer.getEmail());
			SendMailUtil.send(to,title,content,attachmentList);
		}*/
		
		//抄送人发送短信
		String cctos = issue.getEmailRecivers();
		if(cctos != null && cctos.length() > 0){
			String[] staffids = cctos.split(",");
			for(int id =0 ;id<staffids.length;id++){
				if(staffids[id].equals("")) continue;
				Integer sid = Integer.valueOf(staffids[id]);
				Staffer staff = stafferImpl.load(sid);
				if(staff.isNotify()){
					String title = issue.getPublisher().getName()+"在【"+modul.getDepartment().getName()+"】发布了新的任务";
					String content = issue.getPublisher().getName()+"发布了新的任务："+"<a href='http://ees.yofoto.com.cn/ees/issuehandle?id="+issue.getId()+"'>"+issue.getTitle()+"</a>"+"<br>"+" <br>任务发布人:"+issue.getPublisher().getName()+"<br><br> <font color='red'>任务接收人:"+issue.getCompleter().getName()+"</font><br><br><br>任务描述:"+issueWorkflow.getContent();
					//MailSender.send(staff.getEmail(), title, content);
					//MailAttachment.send(staff.getEmail(), title, content,attachmentList);
					to = new ArrayList<String>();
					to.add(staff.getEmail());
					SendMailUtil.send(to,title,content,attachmentList);
				}
			}
		}
	}
	
	//2013/4/24
	
	public List<Issue> getIssueAlertList(int day){
		Criterion[] criterion = new Criterion[3];
		criterion[0] = Restrictions.eq("status", 1);
		criterion[1] = Restrictions.eq("completeStatus", 1);
		
		criterion[2] = Restrictions.le("exprireDate", DateUtil.getExpireDate(day));
		Criteria issues = super.createCriteria(criterion);
		return issues.list();
		
	}
	
	public List<Issue> getIssueExpireList(int day , int num){
		Criterion[] criterion = new Criterion[3];
		criterion[0] = Restrictions.eq("status", 1);
		criterion[1] = Restrictions.eq("completeStatus", 1);
		
		criterion[2] = Restrictions.le("exprireDate", DateUtil.getExpireDate(day));
		
		Criteria issues = super.createCriteria(criterion);
		issues.setMaxResults(num);
		issues.addOrder(Order.desc("id"));
		return issues.list();
	}
	
	
	
	public List<String>  getAttachment(List<Attachment> attachments,String targetDirectory){
		if(attachments == null || attachments.size() == 0){
			return null;
		}
		List<String> attachmentList = new ArrayList<String>();
		
	    if(attachments != null && attachments.size() > 0){
        	for(int i=0;i<attachments.size();i++){
        		
        		//File target = new File(targetDirectory, attachments.get(i).getUUIDName());
        		//targetDirectory="E:\\work\\yofoto\\IT服务管理\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\IssueEasy\\uploadfiles\\";
        		attachmentList.add(targetDirectory+"/"+attachments.get(i).getUUIDName()); // 添加附件
        	}
        }
	    
	    return attachmentList;
	}
	
	
	public void save(Issue issue){
		super.save(issue);
	}
	/*public Issue get(Integer id){
		return (Issue) super.getSession().get(Issue.class, id);
	}*/
	public Issue load(Integer id){
		Issue issue = (Issue)super.getSession().load(Issue.class,id);
		return issue;
	}
	
	public void delete(Integer id){
		super.delete(id);
	}
	
	/**
	 * 执行count查询获得本次Criteria查询所能获得的对象总数.
	 */
	@SuppressWarnings("unchecked")
	protected long countCriteriaResult(final Criteria c) {
		CriteriaImpl impl = (CriteriaImpl) c;

		// 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();

		List<CriteriaImpl.OrderEntry> orderEntries = null;
		try {
			orderEntries = (List) ReflectionUtils.getFieldValue(impl, "orderEntries");
			ReflectionUtils.setFieldValue(impl, "orderEntries", new ArrayList());
		} catch (Exception e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}

		// 执行Count查询
		Long totalCountObject = ((Number) c.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		long totalCount = (totalCountObject != null) ? totalCountObject : 0;

		// 将之前的Projection,ResultTransformer和OrderBy条件重新设回去
		c.setProjection(projection);

		if (projection == null) {
			c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			c.setResultTransformer(transformer);
		}
		try {
			ReflectionUtils.setFieldValue(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}

		return totalCount;
	}
	
	private Criterion[] getCriterions(final Page<Issue> page){
		Criterion[] criterion = new Criterion[14];
		if(page.getPriority() > 0)
			criterion[0] = Restrictions.eq("priority", page.getPriority());
		if(page.getPublisherid() > 0){
			criterion[1] = Restrictions.eq("publisher.sid", page.getPublisherid());
		}
		if(page.getModularid() > 0)
			criterion[2] = Restrictions.eq("modular.id", page.getModularid());
		if(page.getCompleteStatus() > 0)
			criterion[3] = Restrictions.eq("completeStatus", page.getCompleteStatus());
		if(page.getCompleterid() > 0)
			criterion[4] = Restrictions.eq("completer.sid", page.getCompleterid());
		if(page.getKeyWord()!=null && !"".equals(page.getKeyWord()))
			criterion[5] = Restrictions.like("title", "%"+page.getKeyWord()+"%");
		if(page.getOverdue()>=0){
			if(page.getOverdue()==0)
				criterion[6] = Restrictions.eq("isOverdue", false);
			if(page.getOverdue()==1)
				criterion[6] = Restrictions.eq("isOverdue", true);
		}
		if(page.getDepartmentid()>0)
			criterion[7] = Restrictions.eq("departmentID", page.getDepartmentid());
		//增加时间段查询
		if(page.getFrom()!=null)
			criterion[8] = Restrictions.ge("publishDate", page.getFrom());
		if(page.getTo()!=null){
			// 时间止需要加1天，到0点
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(page.getTo());
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			criterion[9] = Restrictions.le("publishDate", calendar.getTime());
		}
		if(page.getCompleteDateFrom()!=null)
			criterion[10] = Restrictions.ge("completeDate", page.getCompleteDateFrom());
		if(page.getCompleteDateTo()!=null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(page.getCompleteDateTo());
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			criterion[11] = Restrictions.le("completeDate", calendar.getTime());
		}
		if(page.getMonth()!=null && !"".equals(page.getMonth())){
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			try {
				calendar.setTime(sdf.parse(page.getMonth()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			criterion[12] = Restrictions.ge("publishDate", calendar.getTime());
			calendar.add(Calendar.MONTH,1);
			criterion[13] = Restrictions.le("publishDate", calendar.getTime());
		}	
		return criterion;
	}
	
	private Criteria setPageParater(final Criteria c, final Page page){
		c.setFirstResult(page.getFirstResult()-1);
		c.setMaxResults(page.getPageNum());
		if(page.getOrderBy()!=null && !"".equals(page.getOrderBy())){
			String[] orderBy = page.getOrderBy().split(":");
			if(orderBy.length>=2){
				if(orderBy[1].equals("asc"))
					c.addOrder(Order.asc(orderBy[0]));
				if(orderBy[1].equals("desc"))
					c.addOrder(Order.desc(orderBy[0]));
			}
		}else {
			c.addOrder(Order.desc("id"));
		}
		return c;
	}
	
	//
	public void modIssue(final Issue issue, final Issue aIssue){
		if(issue.getExprireDate().compareTo(aIssue.getExprireDate())!=0 && issue.getOriginalExpireDate()==null){
			issue.setOriginalExpireDate(issue.getExprireDate());
		}
		issue.setExprireDate(aIssue.getExprireDate());
		issue.setReason(aIssue.getReason());
		if(!issue.getPercentage().equals(aIssue.getPercentage()) && issue.getOriginalPercentage()==null){
			issue.setOriginalPercentage(issue.getPercentage());
		}
		issue.setPercentage(aIssue.getPercentage());
		issue.setPerReason(aIssue.getPerReason());
		save(issue);
	}
	
	//2013-5-21
	public Map getScoreList(String month, Integer sid){
		List<IssueScore> list = new ArrayList<IssueScore>();
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		try {
			calendar.setTime(sdf.parse(month));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Query query = super.getSession().createSQLQuery(" select i1.title,iw1.scorenote,i1.id,(iw1.professionalscore + iw1.progressscore +m1.score) * i1.percentage/100 as score from issueworkflow iw1, issue i1, modular m1 where iw1.issueid = i1.id and i1.modular_id = m1.id  and iw1.completestatus = 1 and i1.completestatus = 5 and iw1.tostaffer_id=? and i1.publishdate>=? and i1.publishdate<? order by i1.id asc")
		.addScalar("TITLE", Hibernate.STRING)
				.addScalar("SCORENOTE", Hibernate.STRING)
				.addScalar("ID", Hibernate.INTEGER)
				.addScalar("SCORE", Hibernate.DOUBLE)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter(0, sid);
		
		query.setParameter(1, calendar.getTime());
		calendar.add(Calendar.MONTH, 1);
		query.setParameter(2, calendar.getTime());
		List<Map> result = query.list();
		int count = 0;
		double totalScore = 0;
		for(Map map : result){
			IssueScore issueScore = new IssueScore();
			issueScore.setNote((String)map.get("SCORENOTE"));
			issueScore.setTitle((String)map.get("TITLE"));
			double temp = (Double)map.get("SCORE");
			issueScore.setScore(String.valueOf(temp));
			count++;
			totalScore += temp;
			list.add(issueScore);
		}
		double avgScore = 0;
		if(count>0)
		avgScore = totalScore/count;
		BigDecimal bd = new BigDecimal(avgScore);
		 bd  =  bd.setScale(1,BigDecimal.ROUND_HALF_UP);
		 avgScore = bd.doubleValue();
		Map map = new HashMap();
		map.put("list", list);
		
		map.put("avgScore",avgScore);
		System.out.println(list.size());
		System.out.println(avgScore);
		return map;
	}
	
	
	public void cancle(Issue issue){
		issue.setCompleteStatus(6);
		save(issue);
	}

}
