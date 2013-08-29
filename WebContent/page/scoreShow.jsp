<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header1.jsp" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="calender/WdatePicker.js"></script>
<script type="text/javascript">
function OrderByThis(a) {
    var order = $("#OrderBy").val();
    if (order.indexOf(a)<0)
        $("#OrderBy").val(a + ":asc");
    else if(order.indexOf("asc")>0)
    	$("#OrderBy").val(a + ":desc");
    else   $("#OrderBy").val(a+":asc");
    document.getElementById('_form').submit();
}
function SubForm() {
    document.getElementById('_form').submit();
}
function selectAll(a){
	if($(a).attr("checked")){
			$("input[name='score']").attr("checked","checked");
		}else{
			$("input[name='score']").attr("checked","");
			}
}
/*
function edit(){
	
	var issueIds = "";
	var scores = "";
	var count=0;
	$("input[name='score']").each(function(){
			if($(this).attr("checked")){
				count++;
				issueIds = issueIds + $(this).parent().attr("id")+":";
				$(this).parent().prev().find("input").each(function(){
						//alert($(this).attr("id")+":"+$(this).val());
					scores = scores + $(this).attr("id")+":"+$(this).val()+";";
					});
				}
		});
	if(count==0){
		alert("请选择要修改的任务记录！");
		return;
		}
	//alert(issueIds);
	//alert(scores);
	$("#issueIds").val(issueIds);
	$("#scores").val(scores);
	$("#flag").val("scoreShow");
	document.getElementById('form1').submit();
}
*/
function edit(){
	var issueIds = "";
	var scores = "";
	var count=0;
	$("input[name='score']").each(function(){
		if($(this).attr("checked")){
			count++;
			issueIds = issueIds + $(this).parent().attr("id")+":";
			$(this).parent().prev().find("span[name='span1']").each(function(){
				//alert($(this).find("input[name='wid']").val());
				scores = scores + $(this).find("input[name='wid']").val()
				+':'+$(this).find("select[name='s1']").val()+":"
				+$(this).find("select[name='s2']").val()+":"
				+$(this).find("input[name='note']").val()+";";
				});
			}
		});
	if(count==0){
		alert("请选择要修改的任务记录！");
		return;
		}
	
	$("#issueIds").val(issueIds);
	$("#scores").val(scores);
	$("#flag").val("scoreShow");
	document.getElementById('form1').submit();
}
</script>
</head>
<body>
 <div class="Content">
        <div class="Right">
            <div class="Bugicon Midbox">
                任务评分</div>
            <div class="relative CenterBox Sub">
                <form id="_form" action="<%=basePath %>projectscore!scoreShow" method="post">
                <input value="<s:property value="#request.page.orderBy" />" name="page.orderBy" id="OrderBy" type="hidden">
                <input value="<s:property value="#request.department.did" />" name="did" type="hidden">
                <input id="pageIndex" value="<s:property value="#request.page.pageIndex" />" name="page.pageIndex" type="hidden">
                <div id="SearchBox" class="SearchBox left">
                    <span class="icon_bigest left">
                        <label>
                            优先级</label>
                        <select name="page.priority" onchange="SubForm();" class="SearchSelect1 left">
                        <s:if test="#request.page.priority==0">selected="selected"</s:if>
                            <option <s:if test="#request.page.priority==0">selected="selected"</s:if> value="0">
                                全部</option>
                            
                            <option <s:if test="#request.page.priority==1">selected="selected"</s:if> value="1">
                                低</option>
                            
                            <option <s:if test="#request.page.priority==2">selected="selected"</s:if> value="2">
                                中</option>
                            
                            <option <s:if test="#request.page.priority==3">selected="selected"</s:if> value="3">
                                高</option>
                            
                            <option <s:if test="#request.page.priority==4">selected="selected"</s:if> value="4">
                                紧急</option>
                            
                            <option <s:if test="#request.page.priority==5">selected="selected"</s:if> value="5">
                                严重</option>
                            
                        </select>
                    </span><span class="icon_bigest left">
                        <label>
                            模块</label>
                        <select name="page.modularid" onchange="SubForm();" class="SearchSelect left">
                        <option <s:if test="#request.page.modularid==0">selected="selected"</s:if> value="0">
                                全部</option>
                        <s:iterator value="#request.department.modualrs" var="modular">
                        	<option value="<s:property value="#modular.id" />" <s:if test="#request.page.modularid==#modular.id">selected="selected"</s:if>>
                               <s:property value="#modular.name" /></option>
                        </s:iterator>
                        </select>
                    </span>
                
                    <span class="left">
                        <label>
                            关键字</label>
                        <input name="page.keyWord" value="<s:property value="#request.page.keyWord" />" class="SearchSelect" type="text"></span>
                    <p class="clear box">
                        <!-- -->
                    </p>
                    <span class="icon_bigest  left">
                        <label>
                            处理人</label>
                        <select name="page.completerid" onchange="SubForm();" class="SearchSelect1 left">
                         <option <s:if test="#request.page.completerid==0">selected="selected"</s:if> value="0">
                                全部</option>
                      	<s:iterator value="#request.department.staffers" var="staffer">
                        		<option value="<s:property value="#staffer.sid" />" <s:if test="#request.page.completerid==#staffer.sid">selected="selected"</s:if> ><s:property value="#staffer.name" /></option>
                        	</s:iterator>
                        </select>
                    </span>
                     <span class="icon_bigest left">
                        <label>
                            完成日期</label>
                           
                        <input name="page.completeDateFrom" value="<s:date name="#request.page.completeDateFrom" format="yyyy-MM-dd"/>" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" type="text">
                        至
                        <input name="page.completeDateTo" value="<s:date name="#request.page.completeDateTo" format="yyyy-MM-dd"/>" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" type="text">
                    </span>
                  	  <a href="javascript:SubmitForm('_form');" class=" Buttom "><span>
                        搜索</span></a>
                          <a href="<%=basePath %>projectscore?did=<s:property value="#request.department.did" />" class="button-create"><span>
                   去评分</span></a>
                    <p class="clear box">
                        <!-- -->
                    </p>
                  
                </div>
                </form>
                <div class="clear">
                    <!-- -->
                </div>
            </div>
            <p class="clear">
            <!-- -->
            </p>
            <!--start of solution-->
            <div class="Solution">
                <a href="#" onclick="edit();" class="button-create"><span>
                    修改</span></a>
                <div class="PageTop">
                	<form id="form1" action="<%=basePath %>projectscore!score" method="post">
                		<input id="did" name="did" value="<s:property value="#request.department.did" />" type="hidden"></input>
                		<input id="issueIds" name="issueIds" type="hidden"></input>
                		<input id="scores" name="scores" type="hidden"></input>
                		<input id="flag" name="flag"  type="hidden"></input>
                	</form>
                    <div class="Page">            <table class="Tableborder5" border="0" cellpadding="0" cellspacing="1">                <tbody>                    <tr align="center">                        <td class="tablebody1">                            &nbsp;<a href="javascript:GO(1)">刷新</a>&nbsp;</td>
                    <s:if test="#request.page.totalPage>1">
                   		<td class="tablebody1">&nbsp;|&nbsp;显示&nbsp;<s:property value="#request.page.firstResult" />-<s:property value="#request.page.lastResult" />&nbsp;总记录数&nbsp;<s:property value="#request.page.total" />&nbsp;</td>
                   		<td class="tablebody1">&nbsp;|&nbsp;分页:</td>
                   		<s:if test="#request.page.pageIndex>1">
                   		<td class="tablebody1">&nbsp;<a href="javascript:GO(1)">首页</a>&nbsp;</td>
                   		<td class="tablebody1">&nbsp;<a href="javascript:GO(<s:property value="#request.page.pageIndex-1" />)">上一页</a>&nbsp;</td>
                   		</s:if>
                   		<s:bean name="org.apache.struts2.util.Counter" id="counter">
                   		 <s:param name="first" value="1" />
                   		 <s:param name="last" value="#request.page.totalPage" />
                   		 	<s:iterator status="ss">
                   		 	<s:if test="#request.page.pageIndex==#ss.index+1">
                   		 		<td class="tablebody1" style="font-weight:bold;">&nbsp;<span style="text-decoration:underline;"><s:property/></span>&nbsp;</td>
                   		 	</s:if>
                   		 	<s:else>
                   		 		<td class="tablebody1">&nbsp;<a href="javascript:GO(<s:property/>)"><s:property/></a>&nbsp;</td>
                   		 	</s:else>
                   		 		
                   		 	</s:iterator>
                   		 </s:bean>
                   		<s:if test="#request.page.pageIndex<#request.page.totalPage">
                   		<td class="tablebody1">&nbsp;<a href="javascript:GO(<s:property value="#request.page.pageIndex+1" />)">下一页</a>&nbsp;</td>
                   		<td class="tablebody1">&nbsp;<a href="javascript:GO(<s:property value="#request.page.totalPage" />)">尾页</a>&nbsp;</td>
                   		</s:if>
                   </s:if>
                    </tr>                </tbody>            </table>        </div>
                </div>
                <table class="Bug box" border="0" cellpadding="10" cellspacing="0" width="100%">
                    <tbody><tr class="BlueBackground Black BugTitle TableTop">
                         <td width="27%">
                            <b>
                                任务名称</b>
                        </td>
                         <!--  <td width="10%">
                            <a title="" href="javascript:OrderByThis('priority');"><b>
                                    优先级</b>
                                <img src="<%=basePath%>Content/images/grayarrow.gif"></a>
                        </td> -->
                         <td width="8%">
                            <a title="" href="javascript:OrderByThis('completeStatus');"><b>
                                    状态</b>
                                <img src="<%=basePath%>Content/images/grayarrow.gif"></a>
                        </td>
                          <td width="10%">
                            <a title="" href="javascript:OrderByThis('modular');"><b>
                                    模块</b>
                                <img src="<%=basePath%>Content/images/grayarrow.gif"></a>
                        </td>
                        <td width="8%">
                            <a title="" href="javascript:OrderByThis('completeDate');"><b>
                                    完成日期</b>
                                <img src="<%=basePath%>Content/images/grayarrow.gif"></a>
                        </td>
                         <td width="8%">
                            <a title="" href="javascript:OrderByThis('exprireDate');"><b>
                                    有效日期</b>
                                <img src="<%=basePath%>Content/images/grayarrow.gif"></a>
                        </td>
                        <td width="30%">
                            <a><b>
                                    处理人 :  专业得分： 进度得分</b>
                                <img src="<%=basePath%>Content/images/grayarrow.gif"></a>
                                <b>
                                  <a>评分批注</a></b>
                        </td>
                         
                      	<td width="9%" >
                      		<input type="checkbox" onclick="selectAll(this);"></input>&nbsp;<b style="color: blue">
                                    全选</b>
                    </tr>
                    <s:iterator value="#request.page.results" var="issue">
                    	 <tr class="GrayBackground">
                        <td>
                            <a target="_blank" href="<%=basePath %>issuehandle?id=<s:property value="#issue.id" />" class="Blue BiggerFont">
                                <b><s:property value="#issue.title" /></b></a>
                        </td>
               
                       <s:if test="#issue.completeStatus==1">
                        <td>
                            <span class="RedBox">
                                未完成</span>
                        </td>
                       </s:if>
                       <s:elseif test="#issue.completeStatus==2">
                       <td><span class="OrangeBox RedBox">
                                待审核</span> </td>
                       </s:elseif>
                       <s:elseif test="#issue.completeStatus==3">
                       	<td><span class="RedBox GreenBox">
                                已完成</span> </td>
                       </s:elseif>
                       <s:elseif test="#issue.completeStatus==4">
                       	<td><span class="OrangeBox GreenBox">
                               延期完成</span> </td>
                       </s:elseif>
                       <s:else>
                       	<td><span class="RedBox GreenBox">
                               已评分</span> </td>
                       </s:else>
                        <td>
                            <s:property value="#issue.modular.name"/>
                        </td>
                         <td>
                             <s:date name="#issue.completeDate" format="yyyy-MM-dd"/>
                        </td>
                        <td>
                             <s:date name="#issue.exprireDate" format="yyyy-MM-dd"/>
                        </td>
                        <td>
                        
                        	<s:iterator value="#issue.issueWorkflows" var="workflow">
                        		<s:if test="#workflow.completeStatus==1">
                        			<s:property value="#workflow.toStaffer.name"/><b>:</b>
                        			<span name='span1'>
                     					<input name="wid"  type="hidden" value="<s:property value="#workflow.id"/>"></input>
                     				<select name="s1">
                     					<option value="-1"  <s:if test="#workflow.professionalScore==-1">selected="selected"</s:if>>-1</option>
                     					<option value="-0.5" <s:if test="#workflow.professionalScore==-0.5">selected="selected"</s:if>>-0.5</option>
                     					<option value="-0.25" <s:if test="#workflow.professionalScore==-0.25">selected="selected"</s:if>>-0.25</option>
                     					<option value="0" <s:if test="#workflow.professionalScore==0">selected="selected"</s:if>>0</option>
                     					<option value="0.3" <s:if test="#workflow.professionalScore==0.3">selected="selected"</s:if>>0.3</option>
                     					<option value="0.5" <s:if test="#workflow.professionalScore==0.5">selected="selected"</s:if>>0.5</option>
                     					<option value="0.75" <s:if test="#workflow.professionalScore==0.75">selected="selected"</s:if>>0.75</option>
                     				</select>
                     				<select name="s2">
                     					<option value="1.5" <s:if test="#workflow.progressScore==1.5">selected="selected"</s:if>>1.5</option>
                     					<option value="1" <s:if test="#workflow.progressScore==1">selected="selected"</s:if>>1</option>
                     					<option value="0.5" <s:if test="#workflow.progressScore==0.5">selected="selected"</s:if>>0.5</option>
                     					<option value="0.25" <s:if test="#workflow.progressScore==0.25">selected="selected"</s:if>>0.25</option>
                     					<option value="0" <s:if test="#workflow.progressScore==0">selected="selected"</s:if>>0</option>
                     				</select>
                        			 <input type="text" name="note" value="<s:property value="#workflow.scoreNote" />"></input>
                     				</span>
                        		</s:if>
                        	</s:iterator>
                        </td>
                       	<td id="<s:property value="#issue.id" />">
                       		<input type="checkbox" name="score" ></input>
                       	</td>
                    </tr>
                    </s:iterator>
                   
                </tbody></table>
                <!--start of page -->
                <div class="PageTop PageBtm">
                    <div class="Page">            
                    <table class="Tableborder5" border="0" cellpadding="0" cellspacing="1">
                    <tbody><tr align="center"><td class="tablebody1">&nbsp;<a href="javascript:GO(1)">刷新</a>&nbsp;</td>
                   
                   <s:if test="#request.page.totalPage>1">
                   		<td class="tablebody1">&nbsp;|&nbsp;显示&nbsp;<s:property value="#request.page.firstResult" />-<s:property value="#request.page.lastResult" />&nbsp;总记录数&nbsp;<s:property value="#request.page.total" />&nbsp;</td>
                   		<td class="tablebody1">&nbsp;|&nbsp;分页:</td>
                   		<s:if test="#request.page.pageIndex>1">
                   		<td class="tablebody1">&nbsp;<a href="javascript:GO(1)">首页</a>&nbsp;</td>
                   		<td class="tablebody1">&nbsp;<a href="javascript:GO(<s:property value="#request.page.pageIndex-1" />)">上一页</a>&nbsp;</td>
                   		</s:if>
                   		<s:bean name="org.apache.struts2.util.Counter" id="counter">
                   		 <s:param name="first" value="1" />
                   		 <s:param name="last" value="#request.page.totalPage" />
                   		 	<s:iterator status="ss">
                   		 	<s:if test="#request.page.pageIndex==#ss.index+1">
                   		 		<td class="tablebody1" style="font-weight:bold;">&nbsp;<span style="text-decoration:underline;"><s:property/></span>&nbsp;</td>
                   		 	</s:if>
                   		 	<s:else>
                   		 		<td class="tablebody1">&nbsp;<a href="javascript:GO(<s:property/>)"><s:property/></a>&nbsp;</td>
                   		 	</s:else>
                   		 		
                   		 	</s:iterator>
                   		 </s:bean>
                   		<s:if test="#request.page.pageIndex<#request.page.totalPage">
                   		<td class="tablebody1">&nbsp;<a href="javascript:GO(<s:property value="#request.page.pageIndex+1" />)">下一页</a>&nbsp;</td>
                   		<td class="tablebody1">&nbsp;<a href="javascript:GO(<s:property value="#request.page.totalPage" />)">尾页</a>&nbsp;</td>
                   		</s:if>
                   </s:if>
                </div>
                <!--end of page -->
            </div>
            <!--end of solution-->
        </div>
        <div class="clear">
            <!-- -->
        </div>
    </div>
   
</body>
</html>