package yofoto.issue.util;

import java.lang.reflect.Method;



/**
 * @author husan
 * @Date 2010-11-1
 * @description
 */
public class ShowField {
	public static void showFild(Object targetObject){
		try {
			Class targetClass = targetObject.getClass();
			
			Method  methods[] = targetClass.getDeclaredMethods();
			for(int i=0;i<methods.length;i++){
				String methodName = methods[i].getName();
				if(methodName.startsWith("get")){
					String fieldValue = (String)methods[i].invoke(targetObject, null);
					System.out.println(methodName+"="+fieldValue);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
		
	}
	public static void main(String[] args){
		
	}
}
