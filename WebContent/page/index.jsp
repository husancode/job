<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="headernew.jsp" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>温馨提醒</title>

 <script src="<%=basePath %>Scripts/common.js" type="text/javascript"></script>
 <script src="<%=basePath %>Scripts/jquery-1.3.2.min.js" type="text/javascript"></script>
 <link href="<%=basePath %>css/header.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>css/wxtx.css" rel="stylesheet" type="text/css" />
 <script type="text/javascript">
 
 </script>
</head>
<body>
<div id="header1">
  <div id="yofo"></div>
  <div id="qiye">| &nbsp;企业执行系统</div>
  <div id="huanying">欢迎你，<font color="#0033CC">yotofo</font><a href="">退出</a> </div>
</div>
<div id="header2">
  <li style="background: #666666; margin-left:30px;">企划部</li>
  <li>计划部</li>
  <li>采购部</li>
  <li>生产部</li>
  <li>研发部</li>
  <li style="width:200px;">网络信息部</li>
</div>
<div id="header4">
  <li style="width:120px;background: #B9B9B9; margin-left:30px;"><a href="#">温馨提醒</a></li>
  <li><strong><a href="工作计划.html">工作计划</a></strong></li>
  <li><a href="我的任务.html">我的任务</a></li>
  <li><a href="所有任务.html">所有任务</a></li>
  <li><a href="任务评分.html">任务评分</a></li>
  <li><a href="任务统计.html">任务统计</a></li>
  <li><a href="综合汇总_员工版1_2_.html">综合汇总</a></li>
</div>
<div id="main">
  <div id="part">
    <div id="part1"><strong>请相互提醒！</strong></div>
    <div id="part2">
      <li>未评分工作项目: &nbsp;<s:iterator value="#request.toScoreIssues" var="issue">
       <a target="_blank" href="<%=basePath %>issuehandle?id=<s:property value="#issue.id" />" ><b><s:property value="#issue.title" />&nbsp;|</b></a>
      </s:iterator>
      
      </li>
      <li>待审核工作任务: &nbsp;<s:iterator value="#request.toReviewIssues" var="issue">
       <a target="_blank" href="<%=basePath %>issuehandle?id=<s:property value="#issue.id" />" ><b><s:property value="#issue.title" />&nbsp;|</b></a>
      </s:iterator>
      </li>
      <li>工作任务即将到期: &nbsp;
      <s:iterator value="#request.expireIssues" var="issue">
       <a target="_blank" href="<%=basePath %>issuehandle?id=<s:property value="#issue.id" />" ><b><s:property value="#issue.title" />&nbsp;|</b></a>
      </s:iterator>
	   </li>
      <li>新工作计划表下载: &nbsp;<a href="提交任务.html">市场发展规划宣导大会（项目表） <font color="#FF0000"><strong>NEW</strong></font></a><input name="" type="button" value="上传计划表"  style="width:95px; height:25px; margin-left:30px;"/> </li>
      <li>优秀任务评分确认: &nbsp;<a href="领导确认评分">市场发展规划宣导大会 <font color="#FF0000"><strong>NEW</strong></font></a> </li>
	  <li>任务延期确认: &nbsp;<a href="领导确认评分">市场发展规划宣导大会 <font color="#FF0000"><strong>NEW</strong></font></a> </li>
	  <li>任务取消确认: &nbsp;<a href="领导确认评分">市场发展规划宣导大会 <font color="#FF0000"><strong>NEW</strong></font></a> </li>
    </div>
    <div id="part3"></div>
    <div id="part4">企业执行系统使用说明 </div>
    <div id="part5">目标说明：</div>
    <div id="part6">
      <li>1、每月1日至10日为当月填写绩效考核日；并且为上月目标填写成效并为自己评分；为上月的行为才能进行自我评分； 
        若上级是经理及以上级别的，要为上级评分。 </li>
      <li>2、上级为下级的上月绩效及行为评分并为当月新目标进行审核（审核可以去下属历史目标审核）行为才能说明。 </li>
    </div>
  </div>
</div>
</body>
</html>