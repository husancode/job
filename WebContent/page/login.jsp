<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>企业执行系统EES
        登录</title>
   <style type="text/css">
img { border:0;}
  html, body {
	margin:0;
	padding:0; background:url(images/login_bg.png) repeat;
	height:100%;
	}
	#form1 {
		height:100%
	}
	.login{
	width:550px; height:374px; margin-left:400px; margin-top:150px;
	}
	.login_log{
	width:213px; height:145px;
	}
	.login_line{
	width:7px; height:351px;
	}
	.login_info{
	width:270px; height:260px; padding-top:70px;
	}
    </style>
</head>
<script type="text/javascript">
function SubmitForm(){
	
	var email = document.getElementById("email");
	var pwd = document.getElementById("pwd");
	if(email==""){
		alert("账号不能为空!");
		return;
		}
	if(pwd==""){
		alert("密码不能为空");
		return;
		}
	document.getElementById("form1").submit();
}

</script>
<body>
<form id="form1" method="post" action="login!toLogin">
  <table class="login" cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">
    <tr>
      <td width="250px;"><table class="login_log" cellpadding="0" cellspacing="0" border="0" height="100%">
          <tr>
            <td><img src="images/login_log.png" /></td>
          </tr>
          <tr>
            <td style="font-size:30px; font-family:微软雅黑; color:#303030; padding-top:20px; padding-left:15px;"> 企业执行系统</td>
          </tr>
          <tr>
            <td style="font-size:13px; font-family:微软雅黑; color:#8a8a8a; padding-top:10px; padding-left:15px; letter-spacing:1px;"> 三生 (中国) 健康产业有限公司 </td>
          </tr>
        </table></td>
      <td  width="45px;"><img src="images/login_line.png" /></td>
      <td  width="250px;"><table class="login_info" cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">
        <tr><td ><label style="width:50px;">帐号：</label></td><td ><input style="width:188px; height:30px;" type="text" name="email" id="email"/></td></tr>
		   <tr><td ><label>密码：</label></td><td ><input name="Password" id="pwd" type="password" style="width:188px; height:30px;" /></td></tr>
		   <tr><td></td><td><input name="rememberMe" type="checkbox">
                        <span class="icon_biger">
                            记住我</span></td></tr>
		   <tr  style="height:100px;"><td ></td><td><a href="javascript:void(0)" onclick="SubmitForm()"><img src="images/login_button.png" /></a></td></tr>
		</table></td>
    </tr>
  </table>
</form>
</body>
</html>