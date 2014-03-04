<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="headernew.jsp" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="css/page.css" type="text/css" rel="stylesheet" />
<script type="text/javascript">
function GO(page) {
    document.getElementById("pageIndex").value = page;
    document.getElementById("_form").submit();
}
</script>
</head>
<body>
<div class="mina_nav">
    <div id="div1" class="mina_nav_bm">
      <div class="mina_nav_bm2"><a href="index">温馨提醒</a></div>
      <div class="mina_nav_bm1"><a href="planadd">工作计划</a></div>
      <div class="mina_nav_bm2"><a href="myissue2">我的任务</a></div>
      <div class="mina_nav_bm2"><a href="allissue2">所有任务</a></div>
      <div class="mina_nav_bm2"><a href="departscore">任务评分</a></div>
      <div class="mina_nav_bm2"><a href="#">任务统计</a></div>
      <div class="mina_nav_bm2"><a href="综合汇总_绩效评分(给主管打分1.2).html">综合汇总</a></div>
    </div>
  </div>
  <div class="summary">
    <div class="summary_title">
      <div class="summary_title_name2"><a href="planadd">提交项目</a></div>
      <div class="summary_title_name1"><a href="issuesegment">任务细分</a></div>
    </div>
    <div class="summary_mina">
      <div class="summary_mina_con"> <br />
        <div class="taskcode">
        <form id="_form" action="issuesegment" method="post">
        	 <input value="<s:property value="#request.page.orderBy" />" name="page.orderBy" id="OrderBy" type="hidden">
        	 <input id="pageIndex" value="<s:property value="#request.page.pageIndex" />" name="page.pageIndex" type="hidden">
        </form>
          <div class="taskcode_table">
            <table  class="style_table"  cellpadding="0" cellspacing="0"  >
              <tr class="style_table_tr1">
                <th width="10%">序号</th>
                <th width="25%">工作项目</th>
                <th width="10%">工作小组</th>
                <th width="25%">任务名称</th>
                <th width="10%">责任人</th>
                <th width="10%">完成时间</th>
                <th width="10%">操作</th>
              </tr>
              <s:iterator value="#request.page.results" var="item" status="status">
              <tr class="style_table_tr5">
                <td width="10%"><s:property value="#status.index+1"/></td>
                <td width="25%"><s:property value="#item.subject.title"/></td>
                <td width="10%"><s:property value="#item.department.name"/></td>
                <td width="25%"><s:property value="#item.title"/></td>
                <td width="10%"><s:property value="#item.completer.name"/></td>
                <td width="10%"><s:date name="#item.completeDate" format="yyyy-MM-dd"></s:date></td>
                <s:if test="#item.status==1">
                 <td width="10%"><a href="subjectitem?id=<s:property value="#item.id"/>">任务细分</a></td>
                </s:if>
                <s:elseif test="#item.status==2">
                <td width="10%"><a href="subjectitem?id=<s:property value="#item.id"/>">审核中</a></td>
                </s:elseif>
                <s:else>
                <td width="10%"><a href="subjectitem?id=<s:property value="#item.id"/>">已审核</a></td>
                </s:else>
               
              </tr>
              </s:iterator>
              
            </table>
          </div>
          <div  class="page_div"> 
          <table class="Tableborder5" border="0" cellpadding="0" cellspacing="1">                <tbody>                    <tr align="center">                        <td class="tablebody1">                            &nbsp;<a href="javascript:GO(1)">刷新</a>&nbsp;</td>
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
                    </tr>                </tbody>            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>