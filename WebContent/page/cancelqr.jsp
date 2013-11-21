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
function cancelqr(flag){
	var url = "issuecancel!docancelqr";
	var id = $("#issueid").val();
	var fflag = 0;
	var message;
	if(flag){
		fflag=1;
		}
	$.ajax({
		type:'post',
		url:url,
		data:'id='+id+'&flag='+fflag,
		dataType:'text',
		success:function(msg){
		
			if('2'==msg){
				message='任务取消成功！';
				 popup(message,true);
				}else{
					message='任务取消未通过！';
					 popup(message,false);
					}
			
			
		}
		});
}
</script>

</head>
<body>
 <div class="taskno">
    <div class="taskno_mina">
      <div class="taskno_mina_con">
        <p>任务取消</p>
         <span>责任人：<s:property value="#request.issue.completer.name"/></span>
        <div class="taskno_mina_con_table">
     
        <input type="hidden" id="issueid"  value="<s:property  value="#request.issue.id"/>">
          <table cellpadding="0" cellspacing="0" width="100%">
            <tr>
              <th width="20%">任务名称</th>
               <th width="20%">责任人</th>
              <th width="20%">取消原因</th>
            </tr>
            <tr>
           	 <td><s:property value="#request.issue.title"/></td>
              <td><s:property value="#request.issue.completer.name"/></td>
              <td><s:property value="#request.issue.cancelReason"/></input></td>
            </tr>
          </table>
      
        
        </div>
        <div class="taskno_mina_con_button">
          <button onmouseover="msover();" onmouseout="msout();" class="taskno_mina_con_button_bg1" type="submit"
		   onclick="cancelqr(true)">同意取消</button>
          <button onmouseover="msover();" onmouseout="msout();" class="taskno_mina_con_button_bg2" type="submit"
		  onclick="cancelqr(false)">不同意取消</button>
        </div>
      </div> 
   
    </div>
  </div>
  <div id="dialog-overlay1" class="dialog-overlay"></div>
<div id="dialog-box1" class="dialog-box1">
  <div id="dialog-content1" class="dialog-content">
    <div id="dialog-icon1" class="dialog-icon"><img src="images/icon_yes.png"></div>
    <div id="dialog-message1"  class="dialog-message1"><a href="#"></a></div>
  </div>
</div>
<div id="dialog-overlay2" class="dialog-overlay"></div>
<div id="dialog-box2" class="dialog-box2">
  <div id="dialog-content2" class="dialog-content">
    <div id="dialog-icon2" class="dialog-icon"><img src="images/icon_no.png"></div>
    <div id="dialog-message2" class="dialog-message2" ><a href="#"></a></div>
  </div>
</div>
</body>
</html>