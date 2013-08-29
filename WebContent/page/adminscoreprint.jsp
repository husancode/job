<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
  <%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/"; 
  %>
<html>
<head>

<title>企划部月度个人绩效评分表</title>
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
		window.opener.location.href="goToPrint.action";
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
		<h2 style="font-size: 23; font-family: 黑体; letter-spacing: 4px">企划部月度个人绩效评分表（主管版1.2）
		</h2>
		</td>
	</tr>
</table>
<div style="position: absolute; z-index: -11;">
<div style="position: absolute; left: 68px; top: -33px;"></div>
</div>

<table align="center" width="624px" border="0" cellspacing="0">
	<tr>
		<td align="left" style="font-size: 16; font-family: 黑体; padding-left: 10px;"><s:property value="#request.team.name" /></td>
		<td align="right" style="font-size: 16; font-family: 黑体; padding-right: 20px;">姓名：<s:property value="#request.staffer.name" /></td>
	</tr>
</table>

<table width="624px" class="center" id="main1" align="center"
	cellpadding="0" cellspacing="0" border="1"
	style="empty-cells: show; border-collapse:collapse;
	 table-layout: fixed;border-spacing:0.5px;border: thick solid #000000 ;border-width: 2px;">
	
	<tr height="40px" style="font-size: 15; ">
		<td colspan="24" style="font-size: 16; font-family: 黑体; " align="left">任务分</td>
	
	</tr>
	<tr height="40px" style="font-size: 15; ">
		<td colspan="2">序号</td>
		<td colspan="10">任务名称</td>
		<td colspan="2">单项任务得分</td>
		<td colspan="10">备注</td>
	</tr>
	<s:iterator value="#request.result.list" var="iss" status="i1">
	<tr height="40px" style="font-size: 15; ">
		<td colspan="2"><s:property value="#i1.index"/></td>
		<td colspan="10"><s:property value="#iss.title"/></td>
		<td colspan="2"><s:property value="#iss.score"/></td>
		<td colspan="10"><s:property value="#iss.note"/></td>
		</tr>
	</s:iterator>
	<tr height="40px" style="font-size: 15; ">
		<td colspan="12">个人月度任务平均分</td>
		<td colspan="12"><s:property value="#request.result.avgScore"/></td>
	</tr>
	<tr height="40px" style="font-size: 15; ">
		<td  colspan="24" style="font-size: 16;font-family: 黑体; " align="left">管理分</td>
	</tr>
	<tr height="40px" style="font-size: 15; ">
		<td colspan="5">工作计划安排(0.2)</td>
		<td colspan="5">任务完成情况(0.1)</td>
		<td colspan="5">任务完成品质(0.2)</td>
		<td colspan="5">劳动纪律(0.1)</td>
		<td colspan="4">总分</td>
	
	</tr>
	<tr height="40px" style="font-size: 15; ">
		<td colspan="5"></td>
		<td colspan="5"></td>
		<td colspan="5"></td>
		<td colspan="5"></td>
		<td colspan="4"></td>
	</tr>
	<tr height="40px" style="font-size: 15; ">
		<td colspan="4">总分</td>
		<td colspan="20"> </td>
		
	</tr>
	<tr height="40px" style="font-size: 15; ">
		<td colspan="4">经理签字</td>
		<td colspan="8"></td>
		<td colspan="4">副总签字</td>
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