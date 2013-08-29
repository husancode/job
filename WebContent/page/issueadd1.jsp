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
                提交任务</div>
            <!--start of solution-->
            <div class="Solution ">
            <form id="_form" action="<%=basePath%>issueadd2"   method="post">
            	<input type="hidden" name="tid" value="<s:property value="#request.tid"/>">
            	  <table>
                    <tbody><tr>
                        <td>
                            <b class="BiggestFont Black">
                                工作小组:</b>
                        </td>
                        <td>
                             <select name="did" onchange="SubForm();" class="SearchSelect left">
                             <option value='0'>选择小组</option>
                        <s:iterator value="#request.team.departments" var="department">
                        	<option value="<s:property value="#department.did" />" >
                               <s:property value="#department.name" /></option>
                        </s:iterator>
                        </select>
                        </td>
                        <td><input id="did" value="<s:property value="#request.department.did"/>" type="hidden"></input></td>
                       
                    </tr>
                </tbody></table>
            </form>
              
            </div>
            <!--end of solution-->
        
            <!--start of solution-->
         
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