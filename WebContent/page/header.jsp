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
   <link href="<%=basePath %>Content/data/style.css" rel="stylesheet" type="text/css" />
   <link href="<%=basePath %>Content/thickbox.css" rel="stylesheet" type="text/css" />
   <script src="<%=basePath %>Scripts/jquery-1.3.2.min.js" type="text/javascript"></script>
   <script src="<%=basePath %>Scripts/common.js" type="text/javascript"></script>
   <script src="<%=basePath %>Scripts/thickbox.js" type="text/javascript"></script>
   <title>企业执行系统EES</title>
</head>
<body>
  <div class=" Wrapper">
   <div class="Top">
            <div class="left White">
    <a class="DLine light-yellow" ></a>
   欢迎<span class="username">
       <s:property value="#session.user.name"/></span>
    已加入
    <a class="DLine light-yellow" href="<%=basePath %>team"><b>
         <s:property value="#session.teams"/></b>
        个团队</a> <a class="DLine light-yellow" href="<%=basePath %>project">
            <b>
                 <s:property value="#session.departments"/></b>
            个小组</a>|<a class="DLine light-yellow" href="<%= basePath %>staffer">
                    个人资料</a>|<a class="DLine light-yellow" href="<%= basePath %>staffermanager">
                    用户管理</a>|<a class="DLine light-yellow" href="<%= basePath %>authmanager">
                    权限管理</a>|<a class="DLine light-yellow" href="<%= basePath %>login!loginOut">
                        退出</a>
    
		</div>
		<div class="right help-language">
		</div>
	</div>
</body>
	