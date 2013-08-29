<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="/struts-tags" prefix="s"%>
    <%@ include file="header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

</head>
<SCRIPT type="text/javascript">
	function submit(){
		 var name = $("#name").val().trim();
		 if(name==""){
			 alert("名称不能为空");
			 $("#name").focus();
			 return;
			 }
		 var tel = $("#tel").val().trim();
		 if(tel!= ""){
			 if(!chMobilePhone(tel)){
					alert("手机号码格式不对，请重新输入！");
					$("#tel").focus();
					return;
				 }
			 }
		 var pwd = $("#password").val();
		 var npwd = $("#againpwd").val();
		 if(pwd!=npwd){
			 alert("密码输入不一致，请重新输入");
			 $("#againpwd").val("");
			 $("#againpwd").focus();
			 return;
			 }
		 $("#form1").submit();
		}
function chMobilePhone(src){
		
		return (src.match(/^13\d{9}$/g)||(src.match(/^18[0,5,6,7,8,9]\d{8}$/g))||src.match(/^15\d{9}$/g));
	  
}
	
</SCRIPT>
<body>
  <!-- start of content -->
    <div class="Content">
        <div class="Right">
            <div class="Bugicon Midbox">
                个人资料</div>
            <!--start of solution-->
            <div class="Solution Insert Sub">
            <form id="form1" name="form1" action="<%=basePath %>staffer!mod" method="post">
                <div class="box">
                    <label>
                        Email</label>
                   		<input type="text" id="email" name="email" value="<s:property value="#session.user.email" />" disabled="disabled"/>
                </div>
                <div class="box">
                    <label>
                        昵称</label>
                    <input type="text" id="name" name="staffer.name" value="<s:property value="#session.user.name" />"/>
                </div>
                <div class="box">
                    <label>
                        手机号码</label>
                    <input type="text" id="tel" name="staffer.telphone" maxlength="11" value="<s:property value="#session.user.telphone" />"/>
                </div>
               <hr/>
                <div class="box ">
                    <label>
                        新密码</label>
                    <input name="staffer.password" id="password" type="password">
                </div>
                <div class="box ">
                    <label>
                        确认密码</label>
                    <input name="againpwd" id="againpwd" type="password">
                </div>
            <hr />
                <div class="box ">
                    <label>
                        接收通知邮件</label>
                    <input <s:if test="#session.user.notify==true">checked="checked"</s:if> id="notify" name="staffer.notify" value="true" type="checkbox">
                </div>
                <label>
                    &nbsp;</label>
                <a href="javascript:submit();" class="Buttom Small_Btm">
                    <span>
                        提交</span> </a>
               
                <div class="clear">
                    <!-- -->
                </div>
                </form>
            </div>
            <!--end of solution-->
        </div>
        <div class="clear">
            <!-- -->
        </div>
    </div>
     
    </div>
    <div class="Footer">
    <span class="left copyright"></span>
</div>
</body>
</html>