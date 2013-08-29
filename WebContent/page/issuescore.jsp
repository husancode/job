<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<script type="text/javascript" src="ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="ckeditor/config.js"></script>
		<script type="text/javascript">

<!--addMore函数可以提供上传多个文件上传-->

function addMore()
{

	var td = document.getElementById("more");
	
	var br = document.createElement("br");
	var input = document.createElement("input");
	var button = document.createElement("input");
	
	input.type = "file";
	input.name = "upload";
	
	button.type = "button";
	button.value = "   删除    ";
	
	button.onclick = function()
	{
		td.removeChild(br);
		td.removeChild(input);
		td.removeChild(button);
	}
	
	td.appendChild(br);
	td.appendChild(input);
	td.appendChild(button);
}

</script>
<SCRIPT type="text/javascript">

function score(){
	
	var scores = "";
	
	$("tr[name='tr11']").each(function(){
		scores = scores + $(this).find("input[name='wid']").val()
		+':'+$(this).find("select[name='s1']").val()+":"
		+$(this).find("select[name='s2']").val()+":"
		+$(this).find("input[name='note']").val()+";";
		});
	
	$("#scores").val(scores);
	document.getElementById('form1').submit();
}
</SCRIPT>
</head>
<body>

    <form id="_form" action="issuescore!score" method="post"  enctype="multipart/form-data">
    <input name="id" id="id"  value="<s:property value="#request.aIssue.id" />" type="hidden">
    <!-- start of content -->
    <div class="Content">
        <div class="Right">
            <div class="Bugicon Midbox left">
                任务
                处理</div>
            <div class="clear">
            </div>
            <!--start of solution-->
            <div class="Solution Insert">
                
                <p class="createEdit">
                	
                     
                </p>
                
                    <p class="Gray box">
                        <label>
                           工作小组
                        </label>
                        <b>
                           <s:property value="#request.department.name" /></b></p>
                             <p class="Gray box">
                        <label>
                            工作项目</label>
                        <s:property value="#request.aIssue.modular.name" />
                    </p>
                    <p class="Gray box">
                        <label>
                            任务名称
                        </label>
                       <s:property value="#request.aIssue.title" /></p>
                    <p class="Gray box">
                        <label>
                            创建时间</label>
                       <s:date name="#request.aIssue.publishDate" format="yyyy-MM-dd HH:mm:ss"></s:date></p>
                         <p class="Gray box">
                        <label>
                            有效时间</label>
                        <s:date name="#request.aIssue.exprireDate" format="yyyy-MM-dd HH:mm:ss"></s:date></p>
                  <p class="Gray box" >
                        <label>
                            完成时间</label>
                        <s:if test="#request.aIssue.completeDate==null"><br></s:if>
                       		<s:else><s:date name="#request.aIssue.completeDate" format="yyyy-MM-dd HH:mm:ss"></s:date></s:else>
                     <p class="Gray box">
                        <label>
                            分配人</label>
                         <s:property value="#request.aIssue.publisher.name" />
                    </p>
                    <p class="Gray box">
                        <label>
                            处理人</label>
                        <s:property value="#request.aIssue.completer.name" />
                    </p>
                
                     <p class="Gray box">
                        <label>
                            协调人</label>
                        <s:if test="#request.aIssue.coordinationer==null">
                             <br>
                            </s:if>
                            <s:else> <s:property  value="#request.aIssue.coordinationer" /></s:else>
                    </p>
                    <p class="Gray box">
                        <label>
                            优先级</label>
                       <s:if test="#request.aIssue.priority==5">严重</s:if>
                       <s:elseif test="#request.aIssue.priority==4">紧急</s:elseif>
                        <s:elseif test="#request.aIssue.priority==3">高</s:elseif>
                         <s:elseif test="#request.aIssue.priority==2">中</s:elseif>
                         <s:else>低</s:else>
                    </p>
                    <p class="Gray box">
                        <label>
                            状态</label>
                        <s:if test="#request.aIssue.completeStatus==1">未完成</s:if>
                       <s:elseif test="#request.aIssue.completeStatus==2">待审核</s:elseif>
                        <s:elseif test="#request.aIssue.completeStatus==3">已完成</s:elseif>
                         <s:elseif test="#request.aIssue.completeStatus==4">延期完成</s:elseif>
                         <s:else>已评分</s:else>     
                        </p>
                 
                     <p class="Gray box">
                        <label>
                            是否保密</label>
                       <s:if test="#request.aIssue.secret==true">是</s:if>
                       <s:else>否</s:else>
                    </p>
                    <p class="Gray box">
                    
                    <div class="Gray box Detail clear">
                        <label>
                            得分</label>
                        <!--start of solution-->
                        <div class="Solution left">
                            
                            <table class="bugdetail box" border="0" cellpadding="10" cellspacing="0" width="100%">
                                
                                <tbody>
                                <s:iterator value="#request.issueWorkflows" var="issueWF" status="wf">
                                	<s:if test="#issueWF.completeStatus==1">
                                		<tr name="tr11" class="title">
                                    <td width="12%">
                                        <span class="BlueBox">
                                        <input name="wid"  type="hidden" value="<s:property value="#issueWF.id"/>"></input>
                                          <s:property value="#issueWF.toStaffer.name"/>
                                        </span>
                                    </td>
                                    <td width="20%">
                                      专业得分：<select name="s1">
                     					<option value="-1">-1</option>
                     					<option value="-0.5">-0.5</option>
                     					<option value="-0.25">-0.25</option>
                     					<option value="0" selected="selected">0</option>
                     					<option value="0.3">0.3</option>
                     					<option value="0.5">0.5</option>
                     					<option value="0.75">0.75</option>
                     				</select>
                                    </td>
                                    <td class="">
                                 
                                      进度得分：<select name="s2">
                                      	<option value="0"   <s:if test="#request.aIssue.overLevel==0">selected="selected"</s:if> >0</option>
                                      	<option value="-0.25"   <s:if test="#request.aIssue.overLevel==1">selected="selected"</s:if> >-0.25</option>
                                      	<option value="-0.5"   <s:if test="#request.aIssue.overLevel==2">selected="selected"</s:if> >-0.5</option>
                     					<option value="-1"   <s:if test="#request.aIssue.overLevel==3">selected="selected"</s:if> >-1</option>
                     					<option value="-1.5">-1.5</option>
                     				</select>
                                    </td>
                                    <td width="34%">
                                       备注： <input type="text" name="note" width="100px"></input>
                                    </td>
                                </tr>
                                	</s:if>
                                 
                                </s:iterator>
                               
                                
                            </tbody></table>
                        </div>
                        <!--end of solution-->
                        <div>
<p class="Gray box">
	                        <label>&nbsp;</label>
 						</p>
					
                        <a href="javascript:score();" class=" Buttom  icon_bigest"><span>
                            打分</span></a> 

              </div>
                    </div>
                    	
                    <div class="clear">
                        <!-- -->
                    </div>
                    <br>
                    
                    <div class="clear">
                        <!-- -->
                    </div>
            </div>
            <!--end of solution-->
            <div class="left margintop10">
                <a href="javascript:window.close();" class=" Blue">
                    关闭窗口</a>
            </div>
        </div>
        <div class="clear">
            <!-- -->
        </div>
    </div>
    <!-- start of content -->
    <div class="clear">
        <!-- -->
    </div>
    <br>
    </form>
    <form id="form1" action="<%=basePath %>issuescore!score" method="post">
                		<input id="did" name="did" value="<s:property value="#request.department.did" />" type="hidden"></input>
                		<input id="issueIds" name="issueIds" type="hidden" value="<s:property value='#request.aIssue.id' />"></input>
                		<input id="scores" name="scores" type="hidden"></input>
                	</form>
</body>
</html>