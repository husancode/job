package yofoto.issue.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author husan
 * @Date 2013-4-26
 * @description
 */
public class DateUtil {
	
	//返回上个月
	public static String getPrevMonth(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, -1);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		return df.format(calendar.getTime());
	}
	//返回动态月份
	public static List<String> getMonths(int s_year){
		Calendar calendar = Calendar.getInstance();
		List<String> months = new ArrayList<String>();
		calendar.setTime(new Date());
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		for(int i=month ; i>=1; i--){
			if(i<10)
				months.add(String.valueOf(year)+"-0"+String.valueOf(i));
			else
				months.add(String.valueOf(year)+"-"+String.valueOf(i));
			
		}
		for(int i=year-1; i>=s_year; i--){
			months.add(String.valueOf(i)+"-"+"12");
			months.add(String.valueOf(i)+"-"+"11");
			months.add(String.valueOf(i)+"-"+"10");
			months.add(String.valueOf(i)+"-"+"09");
			months.add(String.valueOf(i)+"-"+"08");
			months.add(String.valueOf(i)+"-"+"07");
			months.add(String.valueOf(i)+"-"+"06");
			months.add(String.valueOf(i)+"-"+"05");
			months.add(String.valueOf(i)+"-"+"04");
			months.add(String.valueOf(i)+"-"+"03");
			months.add(String.valueOf(i)+"-"+"02");
			months.add(String.valueOf(i)+"-"+"01");
			
		}
		
		return months;
	}
	public static Date getExpireDate(int day){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_YEAR, day+1);
		cal.set(Calendar.HOUR_OF_DAY,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	public static int getOverLevel(Date expireDate, Date completeDate){
		int overLevel = 0;
		Calendar calendar = Calendar.getInstance();
		if(expireDate.after(completeDate)){
			overLevel = 0;
		}else{
			calendar.setTime(expireDate);
			calendar.add(Calendar.DAY_OF_YEAR, 2);
			if(calendar.after(completeDate)){
				overLevel = 1;
			}else{
				calendar.add(Calendar.DAY_OF_YEAR, 3);
				if(calendar.before(completeDate)){
					overLevel = 3;
				}else{
					overLevel = 2;
				}
			}
		}
		return overLevel;
	}
	public static void main(String[] args) {
		Date aDate = new Date();
		System.out.println(aDate);
		System.out.println(aDate.getDate());
	}

}
