<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header2.jsp" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
function OrderByThis(a) {
    var order = $("#OrderBy").val();
    if (order.indexOf(a)<0)
        $("#OrderBy").val(a + ":asc");
    else if(order.indexOf("asc")>0)
    	$("#OrderBy").val(a + ":desc");
    else   $("#OrderBy").val(a+":asc");
    document.getElementById('_form').submit();
}
function SubForm() {
    document.getElementById('_form').submit();
}
</script>
</head>
<script type="text/javascript" src="calender/WdatePicker.js"></script>
<body>
<!-- start of content -->
   <div class="Content">
        <div class="Right">
            <div class="Bugicon Midbox">
                月度绩效</div>
            <!--start of solution-->
            <div class="Solution ">
            <form id="_form" action="<%=basePath%>issue_stat1"   method="post">
            	<input type="hidden" name="tid" value="<s:property value="#request.tid"/>">
            	  <table>
                    <tbody><tr>
                        <td>
                            <b class="BiggestFont Black">
                                月度:</b>
                        </td>
                        <td>
                             <select name="month" onchange="SubForm();" class="SearchSelect left">
                        <s:iterator value="#request.months" var="month">
                     
                        	<option value="<s:property value="#month" />"    <s:if test="#request.month==#month">selected="selected"</s:if>>
                               <s:property value="#month" /></option>
                        </s:iterator>
                        </select>
                        </td>
                       <td> <a href="<%=basePath %>staffer_score?tid=<s:property value="#request.tid"/>" class="Buttom  "><span>
                    <img src="<%=basePath %>Content/images/add_icon.gif">
                    月度考核</span></a></td>
                    </tr>
                </tbody></table>
            </form>
              
            </div>
            <!--end of solution-->
        
            <!--start of solution-->
         	  <div class="Solution">
                <div class="BlueBorder_btm">
                    <!-- -->
                </div>
                <table id="ul_moudle" class="Bug box" border="0" cellpadding="10" cellspacing="0" width="100%">
                    <tbody><tr class="BlueBackground Black BugTitle TableTop">
                        <td>
                            <b>
                                序号</b>
                        </td>
                        <td>
                            <b>
                              姓名</b>
                        </td>
                      
                        <td>工作量</td>
                        <td>
                         	工作态度
                        </td>
                       	<td>团队协作</td>
                       	<td>劳动纪律</td>
                       	<td>工作进度</td>
                       	<td>文字校对</td>
                       	<td>备注</td>
                       	<td>任务分</td>
                       	<td>最后得分</td>
                    </tr>
                    <s:iterator value="#request.result" var="item"  status="it">
                    <tr>
                    	<td><s:property value="#it.index"/></td>
                    	  <td><s:property value="#item.staffer.name"/></td>
                    	  
                    	    <td><s:property value="#item.amount"/></td>
                    	     <td><s:property value="#item.attitude"/></td>
                    	      <td><s:property value="#item.coordination"/></td>
                    	       <td><s:property value="#item.discipline"/></td>
                    	        <td><s:property value="#item.progress"/></td>
                    	         <td><s:property value="#item.wordCheck"/></td>
                    	          <td><s:property value="#item.note"/></td>
                    	           <td><s:property value="#item.issueScore"/></td>
                    	            <td><s:property value="#item.score"/></td>
                    </tr>
                    	 
                    </s:iterator>
                    <!-- <s:iterator value="#request.department.modualrs" var="modular">
                      <tr class="GrayBackground" id="li_m_<s:property value="#modular.id"/>">
                        <td><s:property value="#modular.name"/></td>
                        <td><s:property value="#modular.responsibleStaffer.name"/></td>
                        <td><s:property value="#modular.percentage"/></td>
                        <td><s:property value="#modular.score"/></td>
                        <td>
                            <a href="javascript:EditMD(<s:property value="#modular.id"/>)" class="Blue">
                                <img src="<%=basePath %>Content/images/pencil.gif"></a>
                        </td>
                       
                    </tr>
                    </s:iterator> -->
                    
                   
                    
                </tbody></table>
               
            </div>
            <!--end of solution-->
            <div class="clear">
            </div>
          
        </div>
        <div class="clear">
            <!-- -->
        </div>
    </div>
</body>
</html>