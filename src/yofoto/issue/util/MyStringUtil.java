package yofoto.issue.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import yofoto.issue.pojo.Staffer;
import yofoto.issue.vo.DepartmentVO;

/**
 * @author husan
 * @Date 2012-10-30
 * @description
 */
public class MyStringUtil {
	public static Set<String> stringToEmail(String[] emails){
		Set<String> result = new HashSet<String>();
		//String[] emails = source.split("\r\n");
		for(String email : emails){
			if(isEmail(email)){
				result.add(email);
			}
		}
		return result;
	}
	public static boolean isEmail(String email){
		 if (null==email || "".equals(email)) return false;    
		 //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配  
         Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配  
         Matcher m = p.matcher(email);  
         return m.matches();  
	}
	
	public static String toHtml(List<DepartmentVO> departmentVOs,Integer tid,Staffer staffer){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer sb = new StringBuffer("<tr style=\"display: table-row;\" class=\"expand-project\" id=\"tr_app_");
		sb.append(tid);
		sb.append("\"><td colspan=\"5\"><table class=\"project-list\" cellpadding=\"5\" cellspacing=\"0\"><tbody><tr class=\"title\"><td width=\"30%\">小组</td><td width=\"20%\">创建日期</td><td width=\"35%\">是否创建者</td><td>操作</td></tr>");
		for(DepartmentVO depart : departmentVOs){
			sb.append("<tr><td><a class=\"Blue\" href=\"projectinfo?did=");
			sb.append(depart.getDid());
			sb.append("\">");
			sb.append(depart.getName());
			sb.append("</a></td><td>");
			sb.append(df.format(depart.getCreateTime()));
			sb.append("</td><td>");
			if(depart.isIfCreator()){
				sb.append("是");
			}else sb.append("否");
			sb.append("</td><td>[<a class=\"Blue\" href=\"projectinfo?did=");
			sb.append(depart.getDid());
			sb.append("\">进入</a>]");
			if(!depart.isIfCreator()&&!"admin".equals(staffer.getEmail())){
				sb.append("[<a class=\"Blue\" href=\"javascript:QuitProject("+tid+","+depart.getDid());
				
				sb.append(");\" onclick=\"return confirm('确定退出这个小组吗??');\">退出小组</a>]");
			}
			if(depart.isIfAdmin()){
				sb.append("[<a class=\"Blue\" href=\"projectedit?did=");
				sb.append(depart.getDid());
				sb.append("\">编辑</a>]");
			}
			sb.append("</td></tr>");
		}
		return sb.toString();
	}
	
	/**
	 * 格式化HTML文本
	 * @param content
	 * @return
	 */
	public static String html(String content) {
		if(content==null) return "";        
		String html = content;
		html = StringUtils.replace(html, "'", "&apos;");
		html = StringUtils.replace(html, "\"", "&quot;");
		html = StringUtils.replace(html, "\t", "&nbsp;&nbsp;");// 替换跳格
		//html = StringUtils.replace(html, " ", "&nbsp;");// 替换空格
		html = StringUtils.replace(html, "<", "&lt;");
		html = StringUtils.replace(html, ">", "&gt;");
		return html;
	}

}
