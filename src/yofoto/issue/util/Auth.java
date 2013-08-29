package yofoto.issue.util;

import java.io.Serializable;

/**
 * @author husan
 * @Date 2013-2-20
 * @description
 */
public class Auth implements Serializable{
	private int id;
	private String name;
	private String action;
	private String descriptions;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj instanceof Auth){
			return ((Auth)obj).getAction().equals(this.action);
		}
		return false;	
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return action.hashCode();
	}
	
	

}
