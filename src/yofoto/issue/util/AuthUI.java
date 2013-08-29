package yofoto.issue.util;

import java.util.HashSet;
import java.util.List;

/**
 * @author husan
 * @Date 2013-2-27
 * @description
 */
public class AuthUI {
	
	private List<Auth> auths;
	
	private HashSet<String> filter = new HashSet<String>();
	
	private Res res;
	
	public void initFilter(){
		if(auths==null)
			return ;
		filter.clear();
		for(Auth auth : auths){
			filter.add(auth.getAction());
		}
	}
	
	public boolean hasAuth(String method){
		return filter.contains(method);
	}

	public List<Auth> getAuths() {
		return auths;
	}

	public void setAuths(List<Auth> auths) {
		this.auths = auths;
	}

	public HashSet<String> getFilter() {
		return filter;
	}

	public void setFilter(HashSet<String> filter) {
		this.filter = filter;
	}
	
	public Res getRes() {
		return res;
	}
	
	public void setRes(Res res) {
		this.res = res;
	}

}
