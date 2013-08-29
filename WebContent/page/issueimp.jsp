<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="header1.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

</head>
<body>

    <!-- start of content -->
    <div class="Content">
        <div class="Right">
            <form id="_form" action="<%=basePath %>issueimp!commit" method="post" enctype="multipart/form-data">
            <input name="did" value="<s:property value="#request.department.did" />" type="hidden">
          
            <div class="Bugicon Midbox">
                导入新任务
            </div>
            <!--start of solution-->
            <div class="Solution Insert ">
        		<s:file name ="source"  /> 
        		  <s:submit value="导入"/> 
            </div>
            </form>
            <!--end of solution-->
        </div>
        <div class="clear">
            <!-- -->
        </div>
    </div>
</body>
</html>