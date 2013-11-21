<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="headernew.jsp" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>温馨提醒</title>
<style type="text/css">

div.fileinputs {
	position: relative;
	float:left;
	width: 150px;
}

div.fakefile {
	position: absolute;
	top: 0px;
	left: 0px;
	z-index: 1;
}

input.file {
	position: relative;
	text-align: right;
	widows:150px;
	-moz-opacity:0 ;
	filter:alpha(opacity: 0);
	opacity: 0;
	z-index: 2;
}

.ff{
float:right;
position: relative;
left: 100px;
display: none;
}


</style>
<SCRIPT type="text/javascript">
function sselect(){
	$(".ff").show();
}
function submit(){
	var form = document.getElementById("form1");
	form.submit();
}
function downLoad(aa){
	var url = "file_up!downLoad?id="+aa;
	window.location= url;
}
</SCRIPT>
</head>
<body>
<div class="mina_nav">
    <div id="div1" class="mina_nav_bm">
      <div class="mina_nav_bm1"><a href="index">温馨提醒</a></div>
      <div class="mina_nav_bm2"><a href="planadd">工作计划</a></div>
      <div class="mina_nav_bm2"><a href="myissue2">我的任务</a></div>
      <div class="mina_nav_bm2"><a href="allissue2">所有任务</a></div>
      <div class="mina_nav_bm2"><a href="#">任务评分</a></div>
      <div class="mina_nav_bm2"><a href="#">任务统计</a></div>
      <div class="mina_nav_bm2"><a href="综合汇总_绩效评分(给主管打分1.2).html">综合汇总</a></div>
    </div>
  </div>
  <div class="wenxing">
    <div class="wenxing_mina">
      <div class="wenxing_mina_con">
        <div class="wenxing_mina_point">
          <div class="wenxing_mina_point_pg"></div>
          <div class="wenxing_mina_point_btn">
          <!-- 文件上传样式修改 -->
          <form id="form1" action="file_up" enctype="multipart/form-data" method="post">
           <div class="fileinputs"><input type="file" id="upload" name="upload" class="file" onchange="sselect();"/>
			<div class="fakefile"><img src="images/upload_btn.png" /></div>
		  </div>
		  <div class="ff"><button onclick="submit();">提交</button></div>
		 </form>
        
		</div>
        </div>
        <div class="wenxing_mina_del">
          <div class="wenxing_mina_del_name">1.  未评分工作项目：</div>
          <div class="wenxing_mina_del_info">
          <s:iterator value="#request.toScoreIssues" var="issue">
          	<a href="#"><s:property value="#issue.title" /></a> |
          </s:iterator>
       ...... </div>
        </div>
        <div class="wenxing_mina_del">
          <div class="wenxing_mina_del_name">2.  待审核工作任务：</div>
          <div class="wenxing_mina_del_info"> 
          <s:iterator value="#request.toReviewIssues" var="issue">
          	<a href="#"><s:property value="#issue.title" /></a> |
          </s:iterator> ...... </div>
        </div>
        <div class="wenxing_mina_del">
          <div class="wenxing_mina_del_name">3.  工作任务即将到期：</div>
          <div class="wenxing_mina_del_info">
          <s:iterator value="#request.expireIssues" var="issue">
          	<a href="#"><s:property value="#issue.title" /></a> |
          </s:iterator> ...... </div>
        </div>
        <div class="wenxing_mina_del">
          <div class="wenxing_mina_del_name">4.  新工作计划表下载：</div>
          <div class="wenxing_mina_del_info"><a href="javascript:void(0)" onclick="downLoad('<s:property value="#request.upload.id"/>')"><s:property value="#request.upload.fileName"/></a> </div>
        </div>
        <div class="wenxing_mina_del">
          <div class="wenxing_mina_del_name">5.  优秀任务评分确认：</div>
          <div class="wenxing_mina_del_info"><a href="温馨提示_优秀任务评分确认.html">市场发展规划宣导大会</a> </div>
        </div>
        <div class="wenxing_mina_del">
          <div class="wenxing_mina_del_name">6.  任务延期确认：</div>
          <div class="wenxing_mina_del_info">  <s:iterator value="#request.yqIssues" var="issue">
          	<a target="_blank" href="issueexpire!yqqr?id=<s:property value="#issue.id" />"><s:property value="#issue.title" /></a> |
          </s:iterator> </div>
        </div>
        <div class="wenxing_mina_del">
          <div class="wenxing_mina_del_name">7.  任务取消确认：</div>
          <div class="wenxing_mina_del_info"> <s:iterator value="#request.cancelIssues" var="issue">
          	<a target="_blank" href="issuecancel!cancelqr?id=<s:property value="#issue.id" />"><s:property value="#issue.title" /></a> |
          </s:iterator> </div>
        </div>
      </div>
	  <div class="wenxing_mina_ex">
	  <p >企业执行系统使用说明 </p>
	  <div  class="wenxing_mina_ex_del">
	 <span> 目标说明：</span> <br /> 
1、每月1日至10日为当月填写绩效考核日；并且为上月目标填写成效并为自己评分；为上月的行为才能进行自我评分；<br /> 
    &nbsp;&nbsp; &nbsp; 若上级是经理及以上级别的，要为上级评分。 <br /> 
2、上级为下级的上月绩效及行为评分并为当月新目标进行审核（审核可以去下属历史目标审核）行为才能说明。
	  </div>
	  </div>
    </div>
  </div>
</div>
</body>
</html>