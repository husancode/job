<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="calender/WdatePicker.js"></script>
<script type="text/javascript" src="ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="ckeditor/config.js"></script>

 <script type="text/javascript">
	
  function submit(m)
  {
   
   $("#"+m).submit();
  }
  
    </script>
</head>
<body>

    <!-- start of content -->
    <div class="Content">
        <div class="Right">
            <form id="_form" action="<%=basePath %>issuemod!commit" method="post">
            <input name="aissue.id" value="<s:property value="#request.aIssue.id" />" type="hidden">
            <div class="Bugicon Midbox">
                任务调整
            </div>
            <!--start of solution-->
            <div class="Solution Insert ">
                <p class="Gray box">
                    <label>
                        任务
                        名称<font color="red">*</font></label>
                    <input id="title" name="aissue.title" disabled="disabled" value="<s:property value="#request.aIssue.title" />" size="50" style="width:750px;" type="text">
                    <font color="red">
                        
                    </font>
                </p>
              
               
                <p class="Gray box">
                  <label>
                        有效时间</label>
                        
                       <input name="aissue.exprireDate" id="expiredate" type="text" value="<s:date name="#request.aIssue.exprireDate" format="yyyy-MM-dd"/>"
			onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" class="Wdate">
				
                        延期原因<input type="text" name="aissue.reason" value="<s:property value="#request.aIssue.reason" />"></input>
                </p>
                
                <p class="Gray box">
                  <label>
                        任务系数</label>
                       
                      <SELECT name="aissue.percentage">
                      	<option value="100"  <s:if test="#request.aIssue.percentage==100">selected='selected'</s:if> >1</option>
                      	<option value="110" <s:if test="#request.aIssue.percentage==110">selected='selected'</s:if> >1.1</option>
                      	<option value="120" <s:if test="#request.aIssue.percentage==120">selected='selected'</s:if> >1.2</option>
                      	<option value="130" <s:if test="#request.aIssue.percentage==130">selected='selected'</s:if> >1.3</option>
                      </SELECT>
                           调整原因<input type="text" name="aissue.perReason" value="<s:property value="#request.aIssue.perReason" />"></input>
                </p>
                
               
                <p class="Gray box">
                    <label>
                        &nbsp;</label>
                    <a href="javascript:submit('_form');" class=" Buttom  icon_bigest"><span>
                        提交</span></a>
                    
                    <a href="javascript:history.go(-1);" class="Buttom"><span>
                        返回</span></a>
                </p>
                <div class="clear">
                    <!-- -->
                </div>
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