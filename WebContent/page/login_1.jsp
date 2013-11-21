<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
    <%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/"; 
  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link href="<%=basePath %>css/login.css" rel="stylesheet" type="text/css" />
<title>企业执行系统EES
        登录</title>
      
</head>
<script type="text/javascript">
function SubmitForm(){
	document.getElementById("form1").submit();
}
function checkForm(){
	
}
</script>
<body>
<div id="main">
  <div id="nav_left">
    <ul>
      <li style="background:url(img/u10_normal.png) no-repeat center; width:165px; height:40px; line-height:40px;"></li>
      <li style="width:165px; height:35px; line-height:40px;font-family:微软雅黑;font-size:21px;font-weight:normal; margin-left:10px;">企业执行系统 </li>
      <li style="width:165px; height:35px; line-height:40px;font-family:微软雅黑;font-size:12px;font-weight:normal; margin-left:10px;">三生(中国)健康产业有限公司 </li>
    </ul>
  </div>
  <form id="form1" method="post" action="login!toLogin">
  <div id="nav_right"><ul>
      <li style="width:300px; height:40px; line-height:40px; font-size:16px;"><label>账号 ：  <input name="email" id="email"  type="text" style="width:175px; height:20px; "/></label></li>
      <li style="width:300px; height:40px; line-height:40px; font-size:16px;"><label>密码 ：  <input  name="Password" id="pwd" type="password" style="width:175px; height:20px;"/></label></li>
      <li style="width:300px; height:40px; line-height:40px; margin-left:65px; margin-top:30px;"><input name="" type="button" value="登录" style="width:180px; height:30px;" onclick="javascript:SubmitForm();"/></li>
      <li><font color="red"><s:property value="message"></s:property>
                            </font> </li>
    </ul></div>
    </form>
</div>
   
</body>
</html>