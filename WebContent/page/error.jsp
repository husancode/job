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
<title>EES项目</title>
        <link href="<%=basePath %>Content/data/style.css" rel="stylesheet" type="text/css" />
  <link href="<%=basePath %>Content/thickbox.css" rel="stylesheet" type="text/css" />
</head>
<script type="text/javascript">

</script>
<body>
<div class="SubHeader relative">
            <span class="logo"><a href="#">
               </a> </span>
                
           
        </div>
        
    <div class="Content">
        <div class="Right">
            <div class="error-word">
                Error</div>
            <div class="error-reason">
                 <h3>抱歉，无法找到该页面</h3><p>您可以：</p><p>1.检查网址是否正确</p><p>2.<a href="<%=basePath %>/">返回首页</a></p>
                <p>
                    Error message:
                    </p>
            </div>
        </div>
        <div class="clear">
            <!-- -->
        </div>
    </div>
   
</body>
</html>