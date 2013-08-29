package yofoto.issue.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import yofoto.issue.pojo.Department;
import yofoto.issue.pojo.Staffer;
import yofoto.issue.pojo.Team;

/**
 * @author husan
 * @Date 2012-10-30
 * @description
 */
public class AuthUtil {
	
	public static String delProjectAdmin(String admin,String[] ids){
		String[] admins = admin.split(";");
		StringBuffer sb = new StringBuffer("");
		for(String ad : admins){
			boolean isAdmin = false;
			for(String idd : ids){
				if(idd.equals(ad)){
					isAdmin = true;
					break;
				}
			}
			if(!isAdmin){
				sb.append(ad);
				sb.append(";");
			}
		}
		return sb.toString();
	}
	public static String addProjectAdmin(String admin,String[] ids){
		Set<String> result = new HashSet<String>(Arrays.asList(admin.split(";")));
		for(String id : ids){
			result.add(id);
		}
		StringBuffer sb = new StringBuffer("");
		for(String str : result){
			sb.append(str);
			sb.append(";");
		}
		return sb.toString();
	}
	public static boolean isTeamAdmin(Staffer staffer, Team team){
		String admin = team.getAdmin();
		String[] admins = admin.split(";");
		for(String ad : admins){
			if(String.valueOf(staffer.getSid()).equals(ad)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isTeamAdmin(Staffer staffer, String admin){
		String[] admins = admin.split(";");
		for(String ad : admins){
			if(String.valueOf(staffer.getSid()).equals(ad)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isProjectAdmin(Staffer staffer, Department department){
		String admin = department.getAdmin();
		String[] admins = admin.split(";");
		for(String ad : admins){
			if(String.valueOf(staffer.getSid()).equals(ad)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isProjectAdmin(Staffer staffer, String admin){
		String[] admins = admin.split(";");
		for(String ad : admins){
			if(String.valueOf(staffer.getSid()).equals(ad)){
				return true;
			}
		}
		return false;
	}
	
	public static String setAuth(String sid,String admin,boolean flag){
		
		if(flag){
			return admin+sid+";";
		}else{
			StringBuffer sb = new StringBuffer("");
			String[] admins = admin.split(";");
			for(String ad : admins){
				if(!sid.equals(ad)){
					sb.append(ad+";");
				}
			}
			return sb.toString();
		}
	}
}
