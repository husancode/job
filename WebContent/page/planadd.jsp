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
</SCRIPT>
</head>
<body>
<div class="mina_nav">
    <div id="div1" class="mina_nav_bm">
      <div class="mina_nav_bm2"><a href="index">温馨提醒</a></div>
      <div class="mina_nav_bm1"><a href="planadd">工作计划</a></div>
      <div class="mina_nav_bm2"><a href="myissue2">我的任务</a></div>
      <div class="mina_nav_bm2"><a href="allissue2">所有任务</a></div>
      <div class="mina_nav_bm2"><a href="departscore">任务评分</a></div>
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
          <div class="xiangmu_show"><a href="planadd">新增项目</a></div>
          <div class="xiangmu_show2"><a href="planmonth">当月项目</a></div>
          <div class="xiangmu_show2"><a href="planhistory">历史项目</a></div>
        </div>
        <form action="planadd!authAdd" method="post">
        <div class="renwu_info">
          <div style="height:25px; margin:10px;">
            <div id="tab_show1" onclick="showhidetable('tab_show1','renweu1')" style=" margin-top:5px; width:24px; height:24px;background-image:url(images/icon_show.png); float:left; cursor:pointer;" ></div>
            <div style="float:left; padding-left:10px; line-height:20px; font-size:12px;">
              <div style="float:left;">工作项目：
                <input name="subjects[0].title" value=""  style="width:280px; height:28px;"/>
                &nbsp;&nbsp;&nbsp;&nbsp;责任人： </div>
              <div class="style_select">
                <select name="subjects[0].completer.sid" id="completer">
                <s:iterator value="#request.staffers" var="staffer">
                  <option value="<s:property value="#staffer.sid"/>"><s:property value="#staffer.name"/></option>
                  </s:iterator>
                </select>
              </div>
            </div>
            <div style="float:right; "><a href="javascript:void(0)" style="color:#3c9dc4; font-size:12px;" onclick="adddiv2(this)">添加新项目</a></div>
          </div>
        </div>
		<div id="renweu1" class="renwu" >
		 <div id="it0" class="renwu_con" >
        <div class="renwu_one">
        	标题：<input type="text" name="subjects[0].items[0].title"></input>
        	工作小组：<select name="subjects[0].items[0].department.did">
        		<s:iterator value="#request.departments" var="department">
        			<option value="<s:property value="#department.did"/>"><s:property value="#department.name"/></option>
        		</s:iterator>
        	</select>
        	负责人： <select name="subjects[0].items[0].completer.sid">
        		 <s:iterator value="#request.staffers" var="staffer">
                  <option value="<s:property value="#staffer.sid"/>"><s:property value="#staffer.name"/></option>
                  </s:iterator>
        	</select>
        	完成日期：
        	<input name="subjects[0].items[0].completeDate" id="expiredate" type="text" value=""
			onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  style="width:140px; height:25px;" />
        	<a href="javascript:void(0)" style="color:#3c9dc4; font-size:12px;" onclick="addIssue(0)">添加任务</a>
        </div>
         
        </div>
     
		</div>
        <div id="xiangmu_info2" class="renwu_info" style="display:none;">
          <div style="height:24px; margin:10px; ">
            <div id="tab_show2" onclick="showhidetable('tab_show2','renweu2')" style=" margin-top:5px; width:24px; height:24px;background-image:url(images/icon_show.png); float:left; cursor:pointer;" ></div>
            <div style="float:left; padding-left:10px; line-height:20px; font-size:12px;">
              <div style="float:left;">工作项目：
                <input name="subjects[1].title" value=""  style="width:280px; height:28px;"/>
                &nbsp;&nbsp;&nbsp;&nbsp;责任人： </div>
              <div class="style_select">
                <select name="subjects[1].completer.sid" id="completer">
                <s:iterator value="#request.staffers" var="staffer">
                  <option value="<s:property value="#staffer.sid"/>"><s:property value="#staffer.name"/></option>
                  </s:iterator>
                </select>
              </div>
            </div>
            <div style="float:right; "><a href="javascript:void(0)" style="color:#3c9dc4; font-size:12px;" onclick="adddiv2(this)">添加新项目</a></div>
          </div>
        </div>
        
        <div id="renweu2" class="renwu" >
		 <div id="it1" class="renwu_con" >
         <div class="renwu_one">
        	标题：<input type="text" name="subjects[1].items[0].title"></input>
        	工作小组：<select name="subjects[1].items[0].department.did">
        		<s:iterator value="#request.departments" var="department">
        			<option value="<s:property value="#department.did"/>"><s:property value="#department.name"/></option>
        		</s:iterator>
        	</select>
        	负责人： <select name="subjects[1].items[0].completer.sid">
        		 <s:iterator value="#request.staffers" var="staffer">
                  <option value="<s:property value="#staffer.sid"/>"><s:property value="#staffer.name"/></option>
                  </s:iterator>
        	</select>
        	完成日期：
        	<input name="subjects[1].items[0].completeDate" id="expiredate" type="text" value=""
			onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  style="width:140px; height:25px;" />
        	<a href="javascript:void(0)" style="color:#3c9dc4; font-size:12px;" onclick="addIssue(1)">添加任务</a>
        </div>
         
        </div>
     
		</div>
       
        <div id="xiangmu_info3" class="renwu_info" style="display:none;">
          <div style="height:24px; margin:10px; ">
            <div id="tab_show3" onclick="showhidetable('tab_show3','renweu3')" style=" margin-top:5px; width:24px; height:24px;background-image:url(images/icon_show.png); float:left; cursor:pointer;" ></div>
             <div style="float:left; padding-left:10px; line-height:20px; font-size:12px;">
              <div style="float:left;">工作项目：
                <input name="subjects[2].title" value=""  style="width:280px; height:28px;"/>
                &nbsp;&nbsp;&nbsp;&nbsp;责任人： </div>
              <div class="style_select">
                <select name="subjects[2].completer.sid" id="completer">
                <s:iterator value="#request.staffers" var="staffer">
                  <option value="<s:property value="#staffer.sid"/>"><s:property value="#staffer.name"/></option>
                  </s:iterator>
                </select>
              </div>
            </div>
            <div style="float:right; "><a href="javascript:void(0)" style="color:#3c9dc4; font-size:12px;" onclick="adddiv2(this)">添加新项目</a></div>
          </div>
        </div>
     
        <div id="renweu3" class="renwu" >
		 <div id="it2" class="renwu_con" >
         <div class="renwu_one">
        	标题：<input type="text" name="subjects[2].items[0].title"></input>
        	工作小组：<select name="subjects[2].items[0].department.did">
        		<s:iterator value="#request.departments" var="department">
        			<option value="<s:property value="#department.did"/>"><s:property value="#department.name"/></option>
        		</s:iterator>
        	</select>
        	负责人： <select name="subjects[2].items[0].completer.sid">
        		 <s:iterator value="#request.staffers" var="staffer">
                  <option value="<s:property value="#staffer.sid"/>"><s:property value="#staffer.name"/></option>
                  </s:iterator>
        	</select>
        	完成日期：
        	<input name="subjects[2].items[0].completeDate" id="expiredate" type="text" value=""
			onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  style="width:140px; height:25px;" />
        	<a href="javascript:void(0)" style="color:#3c9dc4; font-size:12px;" onclick="addIssue(2)">添加任务</a>
        </div>
         
        </div>
     
		</div>
       
        <div id="xiangmu_info4" class="renwu_info" style="display:none;">
          <div style="height:24px; margin:10px; ">
            <div id="tab_show4" onclick="showhidetable('tab_show4','renweu4')" style=" margin-top:5px; width:24px; height:24px;background-image:url(images/icon_show.png); float:left; cursor:pointer;" ></div>
             <div style="float:left; padding-left:10px; line-height:20px; font-size:12px;">
              <div style="float:left;">工作项目：
                <input name="subjects[3].title" value=""  style="width:280px; height:28px;"/>
                &nbsp;&nbsp;&nbsp;&nbsp;责任人： </div>
              <div class="style_select">
                <select name="subjects[3].completer.sid" id="completer">
                <s:iterator value="#request.staffers" var="staffer">
                  <option value="<s:property value="#staffer.sid"/>"><s:property value="#staffer.name"/></option>
                  </s:iterator>
                </select>
              </div>
            </div>
            <div style="float:right; "><a href="javascript:void(0)" style="color:#3c9dc4; font-size:12px;" onclick="adddiv2(this)">添加新项目</a></div>
          </div>
        </div>
    
        <div id="renweu4" class="renwu" >
		 <div id="it3" class="renwu_con" >
        <div class="renwu_one">
        	标题：<input type="text" name="subjects[3].items[0].title"></input>
        	工作小组：<select name="subjects[3].items[0].department.did">
        		<s:iterator value="#request.departments" var="department">
        			<option value="<s:property value="#department.did"/>"><s:property value="#department.name"/></option>
        		</s:iterator>
        	</select>
        	负责人： <select name="subjects[3].items[0].completer.sid">
        		 <s:iterator value="#request.staffers" var="staffer">
                  <option value="<s:property value="#staffer.sid"/>"><s:property value="#staffer.name"/></option>
                  </s:iterator>
        	</select>
        	完成日期：
        	<input name="subjects[3].items[0].completeDate" id="expiredate" type="text" value=""
			onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  style="width:140px; height:25px;" />
        	<a href="javascript:void(0)" style="color:#3c9dc4; font-size:12px;" onclick="addIssue(3)">添加任务</a>
        </div>
        </div>
     
		</div>
        
        <div style=" width:100%; height:60px;background-color:#FFFFFF; padding-top:20px;">
          <div class="style_button2" >
            <button type="submit">提交</button>
          </div>
          <div class="style_button3">
            <button type="reset">取消</button>
          </div>
        </div>
        </form>
      </div>
    </div>
  </div>
</body>
</html>