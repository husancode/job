 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
  
   <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

  <%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/"; 
  %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <link href="css/page.css" type="text/css" rel="stylesheet" />
   <script src="js/jquery.js" type="text/javascript"></script>
   <title>企业执行系统EES</title>
      <script type="text/javascript">
	function ChangePrj(tid){
		 var sourceUrl=window.location.toString();
	     var url = "team_change?tid="+tid+"&source="+sourceUrl;
	     window.location=url;
		}
	
</script>
</head>

<body>

<div class="mina">
  <div class="mina_log">
    <div class="mina_log_log"><img src="images/login_log.png" /></div>
    <div  class="mina_log_line"><img  src="images/login_log_ling.png" /></div>
    <div class="mina_log_name"><strong>企业执行系统</strong></div>
    <div class="mina_log_user"> <span style=" color:#838383;">欢迎您，</span><span style="color:#3c9dc4;"><strong>${user.name}</strong></span> &nbsp; <a href="#"><span style="color:#303030;"><strong>退出</strong></span></a> </div>
  </div>
  <div class="mina_menu">
    <div class="mina_menu_bm">
    <s:iterator value="#session.teams" var="team">
    	<s:if test="#session.tid==#team.tid"><div class="mina_menu_bm1"><a href="javascript:void(0)" style="color:#ffffff; text-decoration:none;" onclick="ChangePrj(<s:property value="#team.tid" />)"><s:property value="#team.name" /></a></div></s:if>
    	<s:else><div class="mina_menu_bm2"><a href="javascript:void(0)" style="color:#666666; text-decoration:none;" onclick="ChangePrj(<s:property value="#team.tid" />)"><s:property value="#team.name" /></a></div></s:else>
    </s:iterator>
    
    </div>
  </div>
  
</body>
	