<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header1.jsp" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<SCRIPT type="text/javascript">
function submitForm(v){
	if(v)
		$("#completeStatus").val(v);
	
	$("#_form").submit();
}
</SCRIPT>
</head>
<body>

  <!-- start of content -->
    <div class="Content">
        <div class="Right Sub Sar">
            <div class="Bugicon Midbox">
                小组概况  <a href="<%=basePath %>issueimp?did=<s:property value="#request.department.did" />" class="button-create" style="margin-top: -10px"><span>
                    导入新任务</span></a>
            </div>
            <div class="SearchBox">
                <div class="left BiggestFont">
                    <a class="bugs-total" href="<%=basePath %>allissue?did=<s:property value="#request.department.did"/>">
                        所有任务:<span class="num"><s:property value="#request.issueCountVO.totalCount" /></span>
                    </a>
                    <img src="<%=basePath %>Content/images/line.gif" class="SLine">
                    <a class="bugs-unfix" href="javascript:submitForm(1);">
                        未完成的任务:<span class="num"><s:property value="#request.issueCountVO.unCompletedCount" /></span>
                    </a>
                    <img src="<%=basePath %>Content/images/line.gif" class="SLine">
                     <a class="bugs-unfix" href="javascript:submitForm(6);">
                        已取消的任务:<span class="num"><s:property value="#request.issueCountVO.cancleCount" /></span>
                    </a>
                    <img src="<%=basePath %>Content/images/line.gif" class="SLine">
                    <a class="bugs-approval" href="javascript:submitForm(2);">
                        待审核的任务:<span class="num"><s:property value="#request.issueCountVO.toExamineCount" /></span>
                    </a>
                    <img src="<%=basePath %>Content/images/line.gif" class="SLine">
                    <a href="javascript:submitForm(3);" class="bugs-fix">
                        已完成的任务:<span class="num"><s:property value="#request.issueCountVO.completedCount" /></span>
                    </a>
                    <img src="<%=basePath %>Content/images/line.gif" class="SLine">
                     <a href="javascript:submitForm(4);" class="bugs-approval">
                        延期完成的任务:<span class="num"><s:property value="#request.issueCountVO.overCompletedCount" /></span>
                    </a>
                    <img src="<%=basePath %>Content/images/line.gif" class="SLine">
                      <a href="javascript:submitForm(5);" class="bugs-fix">
                        已评分任务:<span class="num"><s:property value="#request.issueCountVO.scoredCount" /></span>
                    </a>
                    <img src="<%=basePath %>Content/images/line.gif" class="SLine">
                </div>
                <a href="<%=basePath %>issueadd?did=<s:property value="#request.department.did" />" class="button-create" style="margin-top: 10px;"><span>
                    提交新任务</span></a>
                <div class="clear">
                    <!-- -->
                </div>
            </div>
            <!--start of solution-->
            <div class="Solution ScaleBox left">
                <div class="gantan Urgently left">
                    <b>
                        紧急待处理的任务</b></div>
                <a href="<%=basePath %>allissue?did=<s:property value="#request.department.did"/>" class="right Blue margintop10">
                    更多...</a>
                <form method="post" id="_form" action="<%=basePath %>allissue">
                <input value="<s:property value="#request.department.did"/>" name="did" type="hidden">
                <input value="0" id="completeStatus" name="page.completeStatus" type="hidden">
                 <input value="" id="month" name="page.month" type="hidden">
                <input value="BugPriorityID desc" name="orderBy" type="hidden">
                </form>
                <br class="clear">
                <div class="BlueBorder_btm">
                    <!-- -->
                </div>
               <s:if test="#request.priorityIssues.size==0">
                 <br>
                暂无数据
               </s:if>
              	<s:else>
              	<table class="Bug box" border="0" cellpadding="10" cellspacing="0" width="100%">
                    <tbody><tr class="BlueBackground Black BugTitle TableTop">
                        <td width="67%">
                            <b>
                                标题</b>
                        </td>
                        <td>
                            <b>
                                优先级</b>
                        </td>
                        <td>
                            <b>
                                处理人</b>
                        </td>
                    </tr>
                    <s:iterator value="#request.priorityIssues" var="priorityIssue">
                       <tr class="GrayBackground">
                        <td>
                            <a class="Blue BiggerFont" href="<%=basePath %>issuehandle?id=<s:property value="#priorityIssue.id" />" target="_blank">
                                <b>
                                    <s:property value="#priorityIssue.title" /></b></a>
                        </td>
                        <s:if test="#priorityIssue.priority==5">
                        <td class="Pink">
                            严重
                        </td>
                        </s:if><s:elseif test="#priorityIssue.priority==4">
                        <td class="Red">
                            紧急
                        </td></s:elseif>
                       <s:elseif test="#priorityIssue.priority==3">
                       <td class="Orange">
                            高
                        </td></s:elseif>
                        <s:elseif test="#priorityIssue.priority==2">
					<td class="ThinGreen">
                          		  中
                        </td></s:elseif><s:else> <td class="Green">
                            低
                        </td></s:else>
                       
                            
                        <td>
                            <s:property value="#priorityIssue.completer.name"/>
                        </td>
                    </tr>
                    </s:iterator>
                    
                </tbody></table>
              	
              	</s:else>
                
            </div>
            <!--end of solution-->
            <!--start of solution-->
            <div class="Solution ScaleBox right">
                <div class="gantan Newbug left">
                    <b>
                        最新任务</b></div>
                <a href="<%=basePath %>allissue?did=<s:property value="#request.department.did"/>" class="right Blue margintop10">
                    更多...</a>
                <br class="clear">
                <div class="BlueBorder_btm">
                    <!-- -->
                </div>
                <s:if test="#request.toCompleteIssues.size==0">
                 <br>
                暂无数据
               </s:if>
               <s:else>
               	 <table class="Bug box" border="0" cellpadding="10" cellspacing="0" width="100%">
                    <tbody><tr class="BlueBackground Black BugTitle TableTop">
                        <td width="67%">
                            <b>
                                标题</b>
                        </td>
                        <td>
                            <b>
                                优先级</b>
                        </td>
                        <td>
                            <b>
                                分配人</b>
                        </td>
                    </tr>
                    <s:iterator value="#request.toCompleteIssues" var="toCompleteIssue">
                    	  <tr class="GrayBackground">
                        <td>
                            <a class="Blue BiggerFont" href="<%=basePath %>issuehandle?id=<s:property value="#toCompleteIssue.id" />" target="_blank">
                                <b><s:property value="#toCompleteIssue.title" />
                                  </b></a>
                        </td>
                        <s:if test="#toCompleteIssue.priority==5">
                        <td class="Pink">
                            严重
                        </td>
                        </s:if><s:elseif test="#toCompleteIssue.priority==4">
                        <td class="Red">
                            紧急
                        </td></s:elseif>
                       <s:elseif test="#toCompleteIssue.priority==3">
                       <td class="Orange">
                            高
                        </td></s:elseif>
                        <s:elseif test="#toCompleteIssue.priority==2">
					<td class="ThinGreen">
                          		  中
                        </td></s:elseif><s:else> <td class="Green">
                            低
                        </td></s:else>
                        <td>
                            <s:property value="#toCompleteIssue.publisher.name"/>
                        </td>
                    </tr>
                    </s:iterator>
                  
                  
                </tbody></table>
               </s:else>
            </div>
            <!--end of solution-->
        </div>
        <div class="clear">
            <!-- -->
        </div>
    </div>
</body>
</html>