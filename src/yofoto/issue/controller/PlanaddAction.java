package yofoto.issue.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.Department;
import yofoto.issue.pojo.Staffer;
import yofoto.issue.pojo.Subject;
import yofoto.issue.pojo.SubjectItem;
import yofoto.issue.service.impl.DepartmentServiceImpl;
import yofoto.issue.service.impl.StafferServiceImpl;
import yofoto.issue.service.impl.SubjectService;
import yofoto.issue.util.DateUtil;

/**
 * @author husan
 * @Date 2013-10-24
 * @description：新增项目
 */
public class PlanaddAction extends BaseAction{
	
	private List<Subject> subjects;
	
	@Autowired
	private StafferServiceImpl stafferService;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private DepartmentServiceImpl departmentService;
	
	@Override
	public String execute(){
		// TODO Auto-generated method stub
	
		List<Staffer> staffers = stafferService.getStafferByTeamID(tid);
		System.out.println(staffers.size());
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("staffers",staffers);
		
		//工作小组列表
		List<Department> departments = departmentService.loadDepartmentsByTeam(tid);
		request.setAttribute("departments", departments);
		
		return SUCCESS;
	}
	
	
	
	//新增项目
	public String authAdd(){
		if(subjects!=null){
			for(Subject subject : subjects){
				if(subject.getTitle()!=null && !"".equals(subject.getTitle())){
					List<SubjectItem> items = subject.getItems();
					for(int i=0;i<items.size();i++){
						SubjectItem item = items.get(i);
						if(item.getTitle()==null || "".equals(item.getTitle())){
							items.remove(item);
						}
						item.setStatus(1);
						item.setSubject(subject);
					}
					subject.setItems(items);
					subject.setTeamID(tid);
					subject.setStatus(1);
					Date today  = new Date();
					subject.setPublishDate(today);
					subject.setMonth(DateUtil.getMonth(today));
					subjectService.save(subject);
				}
				
			}
		}
		return execute();
	}
	
	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

}
