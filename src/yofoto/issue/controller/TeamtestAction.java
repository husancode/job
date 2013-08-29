package yofoto.issue.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.Staffer;
import yofoto.issue.pojo.Team;
import yofoto.issue.service.impl.StafferServiceImpl;
import yofoto.issue.service.impl.TeamServiceImpl;

/**
 * @author husan
 * @Date 2012-10-25
 * @description
 */
public class TeamtestAction extends BaseAction{
	@Autowired
	TeamServiceImpl  teamImpl;
	
	@Autowired
	StafferServiceImpl stafferImpl;
	
	public static int id = 1;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return super.execute();
	}
	
	public String add() throws Exception{
		Team team = new Team();
		team.setName("team"+id++);
		team.setStatus(1);
		List<Staffer> staffers = stafferImpl.loadAll();
		System.out.println(staffers.size());
		//team.setStaffers(staffers);
		teamImpl.save(team);
		return null;
	}
	
	public String show(){
	
		Team team = teamImpl.load(ids);
		System.out.println(team.getName());
		System.out.println(team.getStaffers());
		return null;
	}
	public String delete(){
		System.out.println(ids);
		teamImpl.delete(ids);
		return null;
	}
	public String add1(){
		Staffer staffer = new Staffer();
		staffer.setName("staffer"+id++);
		List<Team> teams = new ArrayList<Team>();
		Team team = teamImpl.get(ids);
		System.out.println(team.getName());
		teams.add(team);
		staffer.setTeams(teams);
		stafferImpl.save(staffer);
		return null;
	}
	
	public String show1(){
		Criterion criterion = Restrictions.eq("id",ids);
		Staffer staffer = stafferImpl.findUnique(criterion);
		System.out.println(staffer.getTeams().size());
		if(staffer.getTeams().size()>0){
			for(Team team : staffer.getTeams()){
				System.out.println(team.getName());
			}
		}
		return null;
	}

}
