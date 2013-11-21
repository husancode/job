<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="headernew.jsp" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
</script>
</head>
<script type="text/javascript" src="calender/WdatePicker.js"></script>
 <link href="<%=basePath %>Content/data/style.css" rel="stylesheet" type="text/css" />
   <link href="<%=basePath %>Content/thickbox.css" rel="stylesheet" type="text/css" />
   <script src="<%=basePath %>Scripts/common.js" type="text/javascript"></script>
   <script src="<%=basePath %>Scripts/thickbox.js" type="text/javascript"></script>
<body>
<!-- start of content -->
    <div class="Content">
        <div class="Right">
            <div class="Bugicon Midbox">
                所有任务</div>
            <div class="relative CenterBox Sub">
                <form id="_form" action="<%=basePath %>allissue2" method="post">
                <input value="<s:property value="#request.page.orderBy" />" name="page.orderBy" id="OrderBy" type="hidden">
                <input value="<s:property value="#request.team.tid" />" name="tid" type="hidden">
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
                    </span><span class="icon_bigest  left">
                        <label>
                            分配人</label>
                        <select  name="page.publisherid" onchange="SubForm();" class="SearchSelect left">
                        	<option <s:if test="#request.page.publisherid==0">selected="selected"</s:if> value="0">全部</option>
                        	<s:iterator value="#request.staffers" var="staffer">
                        		<option value="<s:property value="#staffer.sid" />" <s:if test="#request.page.publisherid==#staffer.sid">selected="selected"</s:if> ><s:property value="#staffer.name" /></option>
                        	</s:iterator>
                        </select>
                    </span><span class="icon_bigest left">
                        <label>
                           小组</label>
                        <select name="page.departmentid" onchange="SubForm();" class="SearchSelect left">
                        <option <s:if test="#request.page.departmentid==0">selected="selected"</s:if> value="0">
                                全部</option>
                        <s:iterator value="#request.team.departments" var="department">
                        	<option value="<s:property value="#department.did" />" <s:if test="#request.page.departmentid==#department.did">selected="selected"</s:if>>
                               <s:property value="#department.name" /></option>
                        </s:iterator>
                        </select>
                    </span>
                     <span class="left">
                        <label>
                            发布日期</label>
                           
                        <input name="page.from" value="<s:date name="#request.page.from" format="yyyy-MM-dd"/>" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" type="text">
                        至
                        <input name="page.to" value="<s:date name="#request.page.to" format="yyyy-MM-dd"/>" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" type="text">
                    </span>
                    <p class="clear box">
                        <!-- -->
                    </p>
                    <span class="icon_bigest left">
                        <label>
                            状态</label>
                        <select name="page.completeStatus" onchange="SubForm();" class="SearchSelect1 left">
                           
                            <option <s:if test="#request.page.completeStatus==0">selected="selected"</s:if> value="0">
                                全部</option>
                            
                            <option <s:if test="#request.page.completeStatus==1">selected="selected"</s:if> value="1">
                                未完成</option>
                            
                            <option <s:if test="#request.page.completeStatus==2">selected="selected"</s:if> value="2">
                                待审核</option>
                            
                            <option <s:if test="#request.page.completeStatus==3">selected="selected"</s:if> value="3">
                                已完成</option>
                                <option <s:if test="#request.page.completeStatus==4">selected="selected"</s:if> value="4">
                               延期完成</option>
                            <option <s:if test="#request.page.completeStatus==5">selected="selected"</s:if> value="5">
                               已评分</option>
                                <option <s:if test="#request.page.completeStatus==6">selected="selected"</s:if> value="6">
                               已取消</option>
                        </select>
                    </span><span class="icon_bigest  left">
                        <label>
                            处理人</label>
                        <select name="page.completerid" onchange="SubForm();" class="SearchSelect left">
                         <option <s:if test="#request.page.completerid==0">selected="selected"</s:if> value="0">
                                全部</option>
                      	<s:iterator value="#request.staffers" var="staffer">
                        		<option value="<s:property value="#staffer.sid" />" <s:if test="#request.page.completerid==#staffer.sid">selected="selected"</s:if> ><s:property value="#staffer.name" /></option>
                        	</s:iterator>
                        </select>
                    </span> <span class="icon_bigest left">
                        <label>
                            是否超期</label>
                        <select  name="page.overdue" onchange="SubForm();" class="SearchSelect left">
                        	<option <s:if test="#request.page.overdue==-1">selected="selected"</s:if> value="-1">全部</option>
                        	<option <s:if test="#request.page.overdue==0">selected="selected"</s:if> value="0">没有超期</option>
                        	<option <s:if test="#request.page.overdue==1">selected="selected"</s:if> value="1">已超期</option>
                        </select>
                    </span>
                    <span class="icon_bigest left">
                        <label>
                            月度</label>
                        <select  name="page.month" onchange="SubForm();" class="SearchSelect left">
                        	<option <s:if test="#request.page.month==''">selected="selected"</s:if> value="">全部</option>
                        	<s:iterator value="#request.months" var="month">
                        		<option value="<s:property value="#month" />" <s:if test="#request.page.month==#month">selected="selected"</s:if>><s:property value="#month" /></option>
                        	</s:iterator>
              
                        </select>
                    </span>
                    <span class="icon_bigest left">
                        <label>
                            关键字</label>
                        <input name="page.keyWord" value="<s:property value="#request.page.keyWord" />" class="SearchSelect" type="text"></span>
                    <a href="javascript:SubmitForm('_form');" class=" Buttom "><span>
                        搜索</span></a>
                    
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
                <a href="<%=basePath %>issueadd1?tid=<s:property value="#request.team.tid" />" class="button-create"><span>
                    提交任务</span></a>
                <div class="PageTop">
                    <div class="Page">            <table class="Tableborder5" border="0" cellpadding="0" cellspacing="1">                <tbody>                    <tr align="center">                        <td class="tablebody1">                            &nbsp;<a href="javascript:GO(1)">刷新</a>&nbsp;</td>
                       <s:if test="#request.page.totalPage>1">
                   		<td class="tablebody1">&nbsp;|&nbsp;显示&nbsp;<s:property value="#request.page.firstResult" />-<s:property value="#request.page.lastResult" />&nbsp;总记录数&nbsp;<s:property value="#request.page.total" />&nbsp;</td>
                   		<td class="tablebody1">&nbsp;|&nbsp;分页:</td>
                   		<s:if test="#request.page.pageIndex>1">
                   		<td class="tablebody1">&nbsp;<a href="javascript:GO(1)">首页</a>&nbsp;</td>
                   		<td class="tablebody1">&nbsp;<a href="javascript:GO(<s:property value="#request.page.pageIndex-1" />)">上一页</a>&nbsp;</td>
                   		</s:if>
                   		<s:if test="#request.page.pageIndex-5>1">
                   		<s:set name="first" value="#request.page.pageIndex-5"></s:set>
                   		</s:if>
                   		<s:else>
                   		<s:set name="first" value="1"></s:set>
                   		</s:else>
                   		<s:if test="#request.page.pageIndex+5<#request.page.totalPage">
                   		<s:set name="last" value="#request.page.pageIndex+5"></s:set>
                   		</s:if>
                   		<s:else>
                   		<s:set name="last" value="#request.page.totalPage"></s:set>
                   		</s:else>
                   		<s:if test="#last-#first<10">
                   			<s:if test="#first==1">
                   				<s:if test="#request.page.totalPage>#last">
                   					<s:if test="#request.page.totalPage>10">
                   						<s:set name="last" value="10"></s:set>
                   					</s:if>
                   					<s:else>
                   						<s:set name="last" value="#request.page.totalPage"></s:set>
                   					</s:else>
                   				</s:if>
                   			</s:if>
                   			<s:if test="#last==#request.page.totalPage">
                   				<s:if test="#last-10>1"><s:set name="first" value="#last-10"></s:set></s:if>
                   				<s:else></s:else>
                   			</s:if>
                   		</s:if>
                   		<s:bean name="org.apache.struts2.util.Counter" id="counter">
                   		
                   		 <s:param name="first" value="#first" />
                   		 <s:param name="last" value="#last" />
                   		 	<s:iterator status="ss">
                   		 	<s:if test="#request.page.pageIndex==(#ss.index+#first)">
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
                     <!--<td width="10%">
                            <a title="" href="javascript:OrderByThis('modular');"><b>
                                    工作项目</b>
                                <img src="<%=basePath%>Content/images/grayarrow.gif"></a>
                        </td>
                        --><td width="40%">
                            <b>
                                任务名称</b>
                        </td>
                        <td width="10%">
                            <a title="" href="javascript:OrderByThis('priority');"><b>
                                    优先级</b>
                                <img src="<%=basePath%>Content/images/grayarrow.gif"></a>
                        </td>
                        <td width="10%">
                            <a title="" href="javascript:OrderByThis('completeStatus');"><b>
                                    状态</b>
                                <img src="<%=basePath%>Content/images/grayarrow.gif"></a>
                        </td>
                        <td width="10%">
                            <a title="" href="javascript:OrderByThis('completer');"><b>
                                    处理人</b>
                                <img src="<%=basePath%>Content/images/grayarrow.gif"></a>
                        </td>
                       
                        <td width="10%">
                            <a title="" href="javascript:OrderByThis('exprireDate');"><b>
                                    有效日期</b>
                                <img src="<%=basePath%>Content/images/grayarrow.gif"></a>
                        </td>
                        <td width="10%">
                            <a title="" href="javascript:OrderByThis('publishDate');"><b>
                                   发布 日期</b>
                                <img src="<%=basePath%>Content/images/grayarrow.gif"></a>
                        </td>
                    </tr>
                    <s:iterator value="#request.page.results" var="issue">
                    	 <tr class="GrayBackground"><!--
                    	  <td>
                            <s:property value="#issue.modular.name"/>
                        </td>
                        --><td>
                            <a target="_blank" href="<%=basePath %>issuehandle?id=<s:property value="#issue.id" />" class="Blue BiggerFont">
                                <b><s:property value="#issue.title" /></b></a>
                        </td>
                         <s:if test="#issue.priority==5">
                        <td class="Pink">
                            严重
                        </td>
                        </s:if><s:elseif test="#issue.priority==4">
                        <td class="Red">
                            紧急
                        </td></s:elseif>
                       <s:elseif test="#issue.priority==3">
                       <td class="Orange">
                            高
                        </td></s:elseif>
                        <s:elseif test="#issue.priority==2">
					<td class="ThinGreen">
                          		  中
                        </td></s:elseif><s:else> <td class="Green">
                            低
                        </td></s:else>
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
                       <s:elseif test="#issue.completeStatus==6">
                       	<td><span class="RedBox	">
                               已取消</span> </td>
                       </s:elseif>
                       <s:else>
                       	<td><span class="RedBox GreenBox">
                               已评分</span> </td>
                       </s:else>
                       
                        <td>
                         <s:property value="#issue.completer.name"/>
                        </td>
                      
                        <td>
                             <s:date name="#issue.exprireDate" format="yyyy-MM-dd"/>
                        </td>
                        <td>
                             <s:date name="#issue.publishDate" format="yyyy-MM-dd"/>
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
                   		<s:if test="#request.page.pageIndex-5>1">
                   		<s:set name="first" value="#request.page.pageIndex-5"></s:set>
                   		</s:if>
                   		<s:else>
                   		<s:set name="first" value="1"></s:set>
                   		</s:else>
                   		<s:if test="#request.page.pageIndex+5<#request.page.totalPage">
                   		<s:set name="last" value="#request.page.pageIndex+5"></s:set>
                   		</s:if>
                   		<s:else>
                   		<s:set name="last" value="#request.page.totalPage"></s:set>
                   		</s:else>
                   		<s:if test="#last-#first<10">
                   			<s:if test="#first==1">
                   				<s:if test="#request.page.totalPage>#last">
                   					<s:if test="#request.page.totalPage>10">
                   						<s:set name="last" value="10"></s:set>
                   					</s:if>
                   					<s:else>
                   						<s:set name="last" value="#request.page.totalPage"></s:set>
                   					</s:else>
                   				</s:if>
                   			</s:if>
                   			<s:if test="#last==#request.page.totalPage">
                   				<s:if test="#last-10>1"><s:set name="first" value="#last-10"></s:set></s:if>
                   				<s:else></s:else>
                   			</s:if>
                   		</s:if>
                   		<s:bean name="org.apache.struts2.util.Counter" id="counter">
                   		
                   		 <s:param name="first" value="#first" />
                   		 <s:param name="last" value="#last" />
                   		 	<s:iterator status="ss">
                   		 	<s:if test="#request.page.pageIndex==(#ss.index+#first)">
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