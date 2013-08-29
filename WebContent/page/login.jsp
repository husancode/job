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
<title>企业执行系统EES
        登录</title>
        <link href="<%=basePath %>Content/data/style.css" rel="stylesheet" type="text/css" />
  <link href="<%=basePath %>Content/thickbox.css" rel="stylesheet" type="text/css" />
</head>
<script type="text/javascript">
function SubmitForm(){
	
	document.getElementById("form1").submit();
}
function checkForm(){
	
}
</script>
<body>
<div class="SubHeader relative">
            <span class="logo"><a href="#">
               </a> </span>
                
           
        </div>
        
    <div class="Content">
    
        <div class="Right">
            <div class="Bugicon Midbox">
               企业执行系统EES</div>
            <!--start of solution-->
            <div class="Solution Insert Sub">
                <form id="form1" method="post" action="login!toLogin">
                <div class="box ">
                    <label>
                        Email</label> 
                      
                    <input class="inputWidth" name="email" id="email"  type="text">
                   
                </div>
                <div class="box">
                    <label>
                        密码</label>
                    <input class="inputWidth" name="Password" id="pwd" type="password">
                   
                </div>
                <div class="box ">
                    <label>
                        &nbsp;</label>
                    <p class="NoneBorder Smallerbox">
                        <input name="rememberMe" type="checkbox">
                        <span class="icon_biger">
                            记住我</span>
                    </p>
                </div>
                </form>
                <div class="box ">
                    <label>
                        &nbsp;</label>
                    <a href="javascript:SubmitForm();" class="Buttom icon_bigest"><span>
                        登录</span></a> <font color="red"><s:property value="message"></s:property>
                            </font> 
                       <a href="#" title="忘记密码" class="Blue BigFont thickbox">
                        忘记密码?</a>
                    <div class="clear">
                        <!-- -->
                    </div>
                </div>
                <div class="clear">
                    <!-- -->
                </div>
            </div>
            <!--end of solution-->
        </div>
        <div class="clear">
        </div>
    </div>
   
</body>
</html>