<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header1.jsp" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="<%=basePath %>amcharts/ampie/swfobject.js"></script>
<script type="text/javascript" src="calender/WdatePicker.js"></script>
</head>
<script type="text/javascript">
function commit(){
	var  form = document.getElementById("_form");
	form.action='<%=basePath %>scorestat?did=<s:property value="#request.did" />';
	form.submit();
}
function excel(){
	var form = document.getElementById("_form");
	form.action='<%=basePath %>scorestat!extExcel?did=<s:property value="#request.did" />';
	form.submit();
}
</script>
<body>
   <!-- start of content -->
    <div class="Content">
        <div class="Right">
            <div class="Bugicon Midbox">
                评分统计</div>
            <div class="relative CenterBox Sub">
                <form method="post" id="_form" action="<%=basePath %>scorestat?did=<s:property value="#request.did" />">
               
                <div class="SearchBox left">
                    <span class="left icon_biger">
                        <label>
                            完成日期</label>
                        <input name="timeFrom" value="<s:property value="#request.timeFrom" />" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" type="text">
                        至
                        <input name="timeTo" value="<s:property value="#request.timeTo" />" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" type="text">
                    </span><a href="javascript:commit();" class=" Buttom  " style="margin-left: 20px"><span>
                        提交</span></a><a href="javascript:excel()" class=" Buttom  " style="margin-left: 20px"><span>
                       导出excel</span></a>
                </div>
                </form>
               
                <div class="clear">
                    <!-- -->
                </div>
            </div>
            <p class="clear">
            <!-- -->
            </p>
            <s:if test="!#request.flag">
            	        <!--start of solution-->
            <div class="Solution">
            
                <div class="PageTop BiggestFont Black">
                    小组人员任务评分情况
                </div>
                <br>
                <div id="flashcontent7"><embed type="application/x-shockwave-flash" src="stat_files/amcolumn.swf" style="undefined" id="amcolumn" name="amcolumn" bgcolor="#FFFFFF" quality="high" flashvars="path=/amcharts/amcolumn/&amp;chart_settings=%3Csettings%3E%3Ctype%3Ebar%3C%2Ftype%3E%3Cdata_type%3Ecsv%3C%2Fdata_type%3E%3Cfont%3ETahoma%3C%2Ffont%3E%3Cdepth%3E10%3C%2Fdepth%3E%3Cangle%3E45%3C%2Fangle%3E%3Ccolumn%3E%3Ctype%3Estacked%3C%2Ftype%3E%3Cwidth%3E50%3C%2Fwidth%3E%3Cspacing%3E0%3C%2Fspacing%3E%3Cgrow_time%3E1%3C%2Fgrow_time%3E%3Cgrow_effect%3Eregular%3C%2Fgrow_effect%3E%3Cballoon_text%3E%3C!%5BCDATA%5B%7Btitle%7D%3A%7Bvalue%7D%5D%5D%3E%3C%2Fballoon_text%3E%3C%2Fcolumn%3E%3Cgraphs%3E%3Cgraph%20gid%3D%220%22%3E%3Ctitle%3E%E5%B7%B2%E8%A7%A3%E5%86%B3%3C%2Ftitle%3E%3Ccolor%3E%23ADD981%3C%2Fcolor%3E%3C%2Fgraph%3E%3Cgraph%20gid%3D%221%22%3E%3Ctitle%3E%E5%BE%85%E5%AE%A1%E6%A0%B8%3C%2Ftitle%3E%3Ccolor%3E%23AD81D9%3C%2Fcolor%3E%3C%2Fgraph%3E%3Cgraph%20gid%3D%222%22%3E%3Ctitle%3E%E6%9C%AA%E4%BF%AE%E5%A4%8D%3C%2Ftitle%3E%3Ccolor%3E%23FEC514%3C%2Fcolor%3E%3C%2Fgraph%3E%3C%2Fgraphs%3E%3C%2Fsettings%3E&amp;chart_data=%E9%99%88%E4%BD%B3%E6%B3%A2%3B4%3B0%3B5%0A%E8%91%A3%E5%8A%A0%E8%8C%82%3B52%3B0%3B0%0A%E8%83%A1%E5%8D%A1%E6%9D%B0%3B10%3B0%3B1%0A%E9%BB%84%E6%98%8E%3B15%3B2%3B6%0A%E6%9D%8E%E5%9D%9A%3B6%3B0%3B0%0A%E6%A2%81%E6%B0%B8%E6%B3%B0%3B17%3B0%3B1%0A%E5%88%98%E6%AD%A6%3B67%3B0%3B2%0A%E7%BC%AA%E6%88%90%3B27%3B0%3B2%0A%E7%A7%A6%E6%99%93%E9%98%B3%3B10%3B0%3B0%0A%E5%8F%B2%E6%95%88%E6%97%AD%3B0%3B0%3B0%0A%E7%8E%8B%E6%98%8E%E6%98%8E%3B21%3B0%3B1%0A%E5%BE%90%E7%9B%8A%E5%86%9B%3B9%3B0%3B1%0A%E6%9D%A8%E7%AB%8B%E7%BA%A2%3B0%3B0%3B0%0A%E5%8F%B6%E6%B6%9B%3B7%3B0%3B6%0A%E5%BA%94%E5%AE%87%E5%AE%BE%3B7%3B0%3B0%0A%E5%BC%A0%E6%B0%B8%E8%8A%B3%3B4%3B0%3B0%0A%E8%B5%B5%E5%BC%BA%3B50%3B0%3B1&amp;preloader_color=#999999" height="500" width="600"></div>

                <script type="text/javascript">
		 		
		var so = new SWFObject("<%=basePath %>amcharts/amcolumn/amcolumn.swf", "amcolumn", "600", "600", "8", "#FFFFFF");
		so.addVariable("path", "<%=basePath %>amcharts/amcolumn/");
		so.addVariable("chart_settings", encodeURIComponent("<settings><type>bar</type><data_type>csv</data_type><font>Tahoma</font><depth>10</depth><angle>45</angle><column><type>3d column</type><width>50</width><spacing>0</spacing><grow_time>1</grow_time><grow_effect>regular</grow_effect><balloon_text><![CDATA[{title}:{value}]]></balloon_text></column><graphs><graph gid=\"0\"><title>参与任务数</title><color>#FEC514</color></graph><graph gid=\"1\"><title>评分</title><color>#ADD981</color></graph></graphs></settings>"));
		so.addVariable("chart_data", encodeURIComponent("<s:property value="#request.stafferScoreStat" escape="false" />"));
		so.addVariable("preloader_color", "#999999");
		so.write("flashcontent7"); 
                </script>

                <div class="clear box">
                    <!-- -->
                </div>
                <br>
                <br>
            </div>
            
            <!--end of solution-->
            </s:if>
    
        </div>
        <div class="clear">
            <!-- -->
        </div>
    </div>
</body>
</html>