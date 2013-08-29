package yofoto.issue.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.Department;
import yofoto.issue.pojo.Modular;
import yofoto.issue.pojo.Staffer;

import yofoto.issue.service.impl.DepartmentServiceImpl;
import yofoto.issue.service.impl.ModularServiceImpl;
import yofoto.issue.service.impl.StafferServiceImpl;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author husan
 * @Date 2012-10-20
 * @description
 */
public class HibeTestAction extends ActionSupport{
	@Autowired
	DepartmentServiceImpl deImpl;
	@Autowired
	StafferServiceImpl staImpl;
	@Autowired
	ModularServiceImpl modularImpl;
	
	//@Autowired
	//AssueServiceImpl assueImpl;
	
	//@Autowired
	//AssueWorkflowServiceImpl assueWorkflowImpl;
	
	@Override
	public String execute() throws Exception {
		System.out.println("hello to ");
		Department depart = new Department();
		depart.setName("网络s部");
		depart.setStatus(2);
		//depart.setId(11);
		deImpl.save(depart);
		
		List<Department> departments = new ArrayList<Department>();
		departments.add(depart);
		for(int i=0;i<3;i++){
			Staffer staffer = new Staffer();
			staffer.setName("husan"+i);
			staffer.setPassword("123456");
			staffer.setDepartments(departments);
			staImpl.save(staffer);
		}
		
		for(int i=0; i<3; i++){
			Modular modular = new Modular();
			modular.setDepartment(depart);
			modular.setName("modular"+i);
			modularImpl.save(modular);
		}
		
		/*Assue assue =  new Assue();
		assue.setTitle(" assue title 2");
		Modular modular = new Modular();
		modular.setId(1);
		assue.setModular(modular);
		assue.setPriority(1);
		Staffer staffer = new Staffer();
		staffer.setSid(4);
		assue.setReviewer(staffer);
		assue.setExprireDate(new Date());
		AssueDetail assueDetail = new AssueDetail();
		assueDetail.setDetail("assue detail2");
		assue.setAssueDetail(assueDetail);
		
		assueImpl.insert(assue);
		*/
		/*AssueWorkflow assueWorkflow = new AssueWorkflow();
		
		assueWorkflow.setAssueId(5);
		assueWorkflow.setDealTime(new Date());
		Staffer staffer1 = new Staffer();
		staffer1.setSid(1);
		Staffer staffer2 = new Staffer();
		staffer2.setSid(2);
		assueWorkflow.setFromStaffer(staffer1);
		assueWorkflow.setToStaffer(staffer2);
		assueWorkflowImpl.insert(assueWorkflow);*/
		
		//assueImpl.delete(2);
		/*Assue assue = assueImpl.load(5);
		System.out.println(assue.getTitle());
		System.out.println(assue.getAssueDetail().getDetail());
		System.out.println(assue.getReviewer().getName());
		System.out.println(assue.getModular().getName());
		System.out.println(assue.getCompleter().getName());
		System.out.println(assue.getPublisher().getName());
		System.out.println("<<<"+assue.getAssueWorkflows().size());*/
		
		return SUCCESS;
	}
	public String newTest()throws Exception{
		System.out.println("newTest");
		return SUCCESS;
	}
}
