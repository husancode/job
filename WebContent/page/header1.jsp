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
   <script type="text/javascript">
	function ChangePrj(){
		 var url=window.location.toString();
	      if (url.toLowerCase().indexOf("did=")>0)
	      {
	        url=url.substring(0,url.lastIndexOf("did="));  
	        window.location= url+"did="+document.getElementById('s_prj').value;
	      
	      }else{
		      if(url.toLowerCase().indexOf("?")<0){
			      window.location = url+"?did="+document.getElementById('s_prj').value;
			      }
		      }
		}

</script>
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
                    个人资料</a>|<a class="DLine light-yellow" href="<%= basePath %>login!loginOut">
                        退出</a>
    
		</div>
		<div class="right help-language">
		</div>
	</div>
	 <!-- start of subheader-->
<div class="SubHeader">
    <span class="left">
        
        <img src="<%=basePath %>Content/images/yft_logo.jpg" class="BlueBorder icon_biger user-logo-define">
        
        <select id="s_prj" class="Select" onchange="ChangePrj();">
            <s:iterator value="#request.departments" var="de">
            	 <option value="<s:property value="#de.did"/>" <s:if test="#de.did==#request.department.did">selected="selected"</s:if>>
                <s:property value="#de.name"/></option>
            </s:iterator>
        </select>
    </span>

    <ul class="Menu">
        
        <li><a href="<%=basePath %>projectinfo?did=<s:property value="#request.department.did"/>" class="menu1 ">
            小组概况</a></li>
        <li><a href="<%=basePath %>tomeissue?did=<s:property value="#request.department.did"/>" class="menu2 ">
            分配给我的任务</a></li>
        <li><a href="<%=basePath %>myissue?did=<s:property value="#request.department.did"/>" class="menu3 ">
            我创建的任务</a></li>
        <li><a href="<%=basePath %>allissue?did=<s:property value="#request.department.did"/>" class="menu4 ">
            所有任务</a></li>
        <li><a href="<%=basePath %>issue_stat?did=<s:property value="#request.department.did"/>" class="menu5 ">
            任务统计</a></li>
        
    </ul>
</div>
<!-- end of subheader-->
</body>
	