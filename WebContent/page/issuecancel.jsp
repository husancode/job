<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="headernew.jsp" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script type="text/javascript" src="calender/WdatePicker.js"></script>
<link href="css/pop.css" type="text/css" rel="stylesheet" />
<script src="js/change_button.js" type="text/javascript"></script>
<script type="text/javascript">
function sqyq(){
	document.getElementById("_form").submit();
}
function reset(){
	window.close();
}
</script>

</head>
<body>
 <div class="taskno">
    <div class="taskno_mina">
      <div class="taskno_mina_con">
        <p>任务取消</p>
      
        <div class="taskno_mina_con_table">
        <form id="_form" action="issuecancel!cancel" method="post" >
        <input type="hidden" name="aissue.id" value="<s:property value="#request.issue.id"/>">
          <table cellpadding="0" cellspacing="0" width="100%">
            <tr>
              <th width="20%">任务名称</th>
               <th width="20%">责任人</th>
              <th width="20%">取消原因</th>
            </tr>
            <tr>
           	 <td><s:property value="#request.issue.title"/></td>
              <td><s:property value="#request.issue.completer.name"/></td>
              <td><input name="aissue.cancelReason"></input></td>
            </tr>
          </table>
        </form>
        
        </div>
        <div class="taskno_mina_con_button">
          <button onmouseover="msover();" onmouseout="msout();" class="taskno_mina_con_button_bg1" type="submit"
		   onclick="sqyq()">确定</button>
          <button onmouseover="msover();" onmouseout="msout();" class="taskno_mina_con_button_bg2" type="submit"
		  onclick="reset()">取消</button>
        </div>
      </div> 
   
    </div>
  </div>
</body>
</html>