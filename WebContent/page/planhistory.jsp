<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="headernew.jsp" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/page.css" type="text/css" rel="stylesheet" />

<script src="js/showdiv.js" type="text/javascript"></script>
<script type="text/javascript" src="calender/WdatePicker.js"></script>
<title></title>
<SCRIPT type="text/javascript">
function addIssue(item){
	
	var index = $("#it"+item).children().length;
	
	var html="<div  class=\"renwu_one\">标题：<input type=\"text\" name=\"subjects["+item+"].items["+index+"].title\"></input>工作小组：<select name='subjects["+item+"].items["+index+"].department.did'><s:iterator value='#request.departments' var='department'><option value=\"<s:property value='#department.did'/>\"><s:property value='#department.name'/></option></s:iterator></select>负责人： <select name=\"subjects["+item+"].items["+index+"].completer.sid\"><s:iterator value='#request.staffers' var='staffer'><option value='<s:property value='#staffer.sid'/>'><s:property value='#staffer.name'/></option></s:iterator></select>完成日期：<input name=\"subjects["+item+"].items["+index+"].completeDate\" id=\"expiredate\" type=\"text\" value=\"\" onFocus=\"WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})\"  style=\"width:140px; height:25px;\" /></div>";
	
	$("#it"+item).append(html);
	
}

function planCon(id){
	
	var url = "plan!authEdit?id="+id;
	
	window.location.href=url;
}
</SCRIPT>
</head>
<body>
<div class="mina_nav">
    <div id="div1" class="mina_nav_bm">
      <div class="mina_nav_bm2"><a href="index">温馨提醒</a></div>
      <div class="mina_nav_bm1"><a href="planadd">工作计划</a></div>
      <div class="mina_nav_bm2"><a href="myissue">我的任务</a></div>
      <div class="mina_nav_bm2"><a href="allissue">所有任务</a></div>
      <div class="mina_nav_bm2"><a href="#">任务评分</a></div>
      <div class="mina_nav_bm2"><a href="#">任务统计</a></div>
      <div class="mina_nav_bm2"><a href="综合汇总_绩效评分(给主管打分1.2).html">综合汇总</a></div>
    </div>
  </div>
  <div class="summary">
    <div class="summary_title">
      <div class="summary_title_name1"><a href="planadd">提交项目</a></div>
      <div class="summary_title_name2"><a href="issuesegment">任务细分</a></div>
    </div>
    <div class="summary_mina">
      <div class="summary_mina_con">
        <div  class="tijiaoadd">
          <div class="xiangmu_show2"><a href="planadd">新增项目</a></div>
          <div class="xiangmu_show2"><a href="planmonth">当月项目</a></div>
          <div class="xiangmu_show"><a href="planhistory">历史项目</a></div>
        </div>
        <div class="renwu_info">
            <table>
            <tr><td width="80px">序号</td><td width="280px">工作项目</td><td width="180px">责任人</td><td width="180px">实际完成时间</td></tr>
            <s:iterator value="#request.page.results" var="item" status="st">
            	<tr><td><s:property value="#st.index+1"/></td><td><s:property value="#item.title"/></td><td><s:property value="#item.completer.name"/></td><td><s:date name="#item.completeDate" format="yyyy-MM-dd"></s:date></td><</tr>
            </s:iterator>
            </table>
        
        </div>
      </div>
    </div>
  </div>
</body>
</html>