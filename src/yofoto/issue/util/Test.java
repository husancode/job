package yofoto.issue.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author husan
 * @Date 2012-11-14
 * @description
 */
public class Test {
	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int s_year = 2011;
		int s_month = 1 ;
		List<String> months = new ArrayList<String>();
		for(int i=s_year; i<year; i++){
			months.add(String.valueOf(i)+"-"+"01");
			months.add(String.valueOf(i)+"-"+"02");
			months.add(String.valueOf(i)+"-"+"03");
			months.add(String.valueOf(i)+"-"+"04");
			months.add(String.valueOf(i)+"-"+"05");
			months.add(String.valueOf(i)+"-"+"06");
			months.add(String.valueOf(i)+"-"+"07");
			months.add(String.valueOf(i)+"-"+"08");
			months.add(String.valueOf(i)+"-"+"09");
			months.add(String.valueOf(i)+"-"+"10");
			months.add(String.valueOf(i)+"-"+"11");
			months.add(String.valueOf(i)+"-"+"12");
			
		}
		for(int i=1 ; i<=month; i++){
			if(i<10)
				months.add(String.valueOf(year)+"-0"+String.valueOf(i));
			else
				months.add(String.valueOf(year)+"-"+String.valueOf(i));
			
		}
		for(String s : months){
			System.out.println(s);
		}
	}
	private static boolean excludeStaticSource(String source){
		Pattern pattern = Pattern.compile("\\.[a-z]*$");
		Matcher matcher = pattern.matcher(source);
		if(matcher.find()){
			return false;
		}
		return true;
	}
}
