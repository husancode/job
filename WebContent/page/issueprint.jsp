<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
  <%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/"; 
  %>
<html>
<head>

<title>任务单</title>
<link href="css/main.css" rel="stylesheet" type="text/css" >
 <script src="<%=basePath %>Scripts/jquery-1.3.2.min.js" type="text/javascript"></script>
<style media="print">
.noprint {
	display: none

}
#t1 .cell{
	width:24px;
	
}	
.mytb td {
	background-color: #ffffff;
}

</style>
<STYLE type="text/css">

#t1 .cell{
	width:24px;
	
}	

</STYLE>
<script type="text/javascript">
	
	function preview() {
		try {
			wb.execwb(7, 1);
		} catch (e) {
			alert("浏览器不支持此功能");
		}

	}
	function reback() {
		
		window.close();
		//window.opener.location.reload();
		//window.opener.location.href="goToPrint.action";
	}
	function doPrint() {
		var bdhtml = window.document.body.innerHTML;

		var startstr = "<!-- startprint -->";
		var endstr = "<!-- endprint -->";
		printhtml = bdhtml.substr(bdhtml.indexOf(startstr));

		printhtml = printhtml.substr(0, printhtml.indexOf(endstr));

		window.document.body.innerHTML = printhtml;

		try {
			window.print();
		} catch (e) {
			alert("打印出错");
		}

		

	}
	
</script>
</head>
<body>

<OBJECT id=wb height=0 width=0
	classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 name=wb></OBJECT>
<!-- startprint -->
<table align="center">
	<tr>
		<td align="center">
		<h2 style="font-size: 23; font-family: 黑体; letter-spacing: 4px">企划部单项任务单(1.4版)
		</h2>
		</td>
	</tr>
</table>
<div style="position: absolute; z-index: -11;">
<div style="position: absolute; left: 68px; top: -33px;"></div>
</div>
<table align="center" width="624px">
	<tr>
		<td ></td>
		<td align="right" style="padding-right: 2px"><font style="font-size: 13"> </font><font
			style="font-size: 13; ">No：<s:property
			value="#request.issue.id"></s:property></font></td>
	</tr>
</table>

<table align="center" width="624px" border="0" cellspacing="0">
	<tr>
		<td align="left" style="font-size: 16; font-family: 黑体; padding-left: 20px;"><s:property value="#request.team.name" /></td>
		<td align="right" style="font-size: 16; font-family: 黑体; padding-right: 20px;">任务系数：<s:if test="#request.issue.originalPercentage==null"><s:property value="%{#request.issue.percentage/100.0}"  /></s:if><s:else><s:property value="%{#request.issue.originalPercentage/100.0}"  /></s:else></td>
	</tr>
</table>

<table width="624px" class="center" id="main1" align="center"
	cellpadding="0" cellspacing="0" border="1"
	style="empty-cells: show; border-collapse:collapse;
	 table-layout: fixed;border-spacing:0.5px;border: thick solid #000000 ;border-width: 2px;">
	
	<tr height="40px" style="font-size: 15; ">
		<td colspan="5">工作事项名称</td>
		<td colspan="21"><s:property value="#request.issue.title"></s:property></td>
	</tr>
	<tr height="80px" style="font-size: 15; ">
		<td colspan="5">要求</td>
		<td colspan="21"></td>
	</tr>
	<tr height="40px" style="font-size: 15; ">
		<td colspan="5">协作小组</td>
		<td colspan="8">协作内容</td>
		<td colspan="5">责任人</td>
		<td colspan="4">要求完成时间</td>
		<td colspan="4">实际完成时间</td>
	</tr>
	<tr height="40px" style="font-size: 15; ">
		<td colspan="5"></td>
		<td colspan="8"></td>
		<td colspan="5"></td>
		<td colspan="4"></td>
		<td colspan="4"></td>
	</tr>
	<tr height="40px" style="font-size: 15; ">
		<td colspan="5"></td>
		<td colspan="8"></td>
		<td colspan="5"></td>
		<td colspan="4"></td>
		<td colspan="4"></td>
	</tr>
	<tr height="40px" style="font-size: 15; ">
		<td colspan="5">要求完成时间</td>
		<td colspan="8"> <s:if test="#request.issue.originalExpireDate==null"><s:date name="#request.issue.exprireDate" format="yyyy-MM-dd"></s:date></s:if><s:else><s:date name="#request.issue.originalExpireDate" format="yyyy-MM-dd"></s:date></s:else></td>
		<td colspan="5">实际完成时间</td>
		<td colspan="8"> <s:date name="#request.issue.completeDate" format="yyyy-MM-dd"></s:date></td>
	</tr>
	<tr height="40px" style="font-size: 15; ">
		<td colspan="5">任务接收人</td>
		<td colspan="8"> <s:property value="#request.issue.completer.name" /></td>
		<td colspan="5">任务接收时间</td>
		<td colspan="8"> <s:date name="#request.issue.publishDate" format="yyyy-MM-dd"></s:date></td>
	</tr>
	<tr height="40px" style="font-size: 15; ">
		<td colspan="5">延期调整至:</td>
		<td colspan="8"> <s:if test="#request.issue.originalExpireDate!=null"><s:date name="#request.issue.exprireDate" format="yyyy-MM-dd"></s:date></s:if></td>
		<td colspan="5">延期原因</td>
		<td colspan="8"> <s:property value="#request.issue.reason" /></td>
	</tr>
	<tr height="40px" style="font-size: 15; ">
		<td colspan="5">主管确认</td>
		<td colspan="8"> </td>
		<td colspan="5">副总/经理审核</td>
		<td colspan="8"> </td>
	</tr>
	<tr height="40px" style="font-size: 15; ">
		<td colspan="5">申请系数调整:</td>
		<td colspan="8"> <s:if test="#request.issue.originalPercentage!=null"><s:property value="%{#request.issue.percentage/100.0}"  /></s:if></td>
		<td colspan="5">调整原因</td>
		<td colspan="8"> <s:property value="#request.issue.perReason" /></td>
	</tr>
	<tr height="40px" style="font-size: 15; ">
		<td colspan="5">主管确认</td>
		<td colspan="8"> </td>
		<td colspan="5">副总/经理审核</td>
		<td colspan="8"> </td>
	</tr>
	
</table>
<div style="position: absolute;z-index: -11;">
<div style="position: absolute; left: 490px; top: -93px;"></div>
</div>
<table align="center" width="624px">
	<tr style="font-size: 13;font-style: italic">
		<td align="left"></td>
		<td width="16%"></td>
	</tr>
</table>

<!-- endprint -->
<table class="noprint" align="center" style="margin-top: 50px">
	<tr>
		<td>
		<button id="btn1" onclick="doPrint();">打印</button>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<button id="btn2" onclick="reback();">取消</button>
		</td>
	</tr>
</table>
</body>
</html>