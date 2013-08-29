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
<body>
   <!-- start of content -->
    <div class="Content">
        <div class="Right">
            <div class="Bugicon Midbox">
                任务统计</div>
            <div class="relative CenterBox Sub">
                <form method="post" id="_form" action="<%=basePath %>issue_stat?did=<s:property value="#request.did" />">
               
                <div class="SearchBox left">
                    <span class="left icon_biger">
                        <label>
                            发布日期</label>
                        <input name="timeFrom" value="<s:property value="#request.timeFrom" />" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" type="text">
                        至
                        <input name="timeTo" value="<s:property value="#request.timeTo" />" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" type="text">
                    </span><a href="javascript:SubmitForm('_form');" class=" Buttom  "><span>
                        提交</span></a>
                </div>
                </form>
                <s:if test="#request.admin=='admin'"> <a href="<%=basePath %>scorestat?did=<s:property value="#request.did" />" class="button-create"><span>
                    评分统计</span></a></s:if>
               
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
                    Bug
                    分布比例图</div>
                <br>
                <div class="ScaleBox icon_bigest left">
                    <div class="scalemenu box">
                        <span>
                            优先级</span></div>
                    <div class="clear box">
                        <!-- -->
                    </div>
                    <div id="flashcontent"><embed type="application/x-shockwave-flash" src="stat_files/ampie_002.swf" style="undefined" id="ampie" name="ampie" bgcolor="#ffffff" quality="high" flashvars="path=/amcharts/ampie/&amp;settings_file=%2Famcharts%2Fampie%2Fampie_settings.xml&amp;chart_data=%E4%BD%8E%3B243%3Bfalse%3BABDD79%0A%E4%B8%AD%3B61%3Bfalse%3B92C1D6%0A%E9%AB%98%3B31%3Bfalse%3BFCE66C%0A%E7%B4%A7%E6%80%A5%3B5%3Bfalse%3BE74862%0A%E4%B8%A5%E9%87%8D%3B2%3Bfalse%3BFEC514&amp;preloader_color=#000000" height="300" width="400"></div>

                    <script type="text/javascript">
		// <![CDATA[		
		var so = new SWFObject("<%=basePath %>amcharts/ampie/ampie.swf?cache=0", "ampie", "400", "300", "8", "#ffffff");
		so.addVariable("path", "<%=basePath %>amcharts/ampie/");
		so.addVariable("settings_file", encodeURIComponent("<%=basePath %>amcharts/ampie/ampie_settings.xml"));
		so.addVariable("chart_data",encodeURIComponent("低;<s:property value="#request.issueCount.p1" />;false;ABDD79\n中;<s:property value="#request.issueCount.p2" />;false;92C1D6\n高;<s:property value="#request.issueCount.p3" />;false;FCE66C\n紧急;<s:property value="#request.issueCount.p4" />;false;E74862\n严重;<s:property value="#request.issueCount.p5" />;false;FEC514"));
		so.addVariable("preloader_color", "#000000");
		so.write("flashcontent");
		// ]]>
                    </script>

                    <!-- end of ampie script -->
                </div>
                <div class="ScaleBox right">
                    <div class="scalemenu box">
                        <span>
                            状态</span></div>
                    <div class="clear box">
                        <!-- -->
                    </div>
                   
                    <div id="flashcontent2"></div>

                    <script type="text/javascript">
		// <![CDATA[		
		var so = new SWFObject("<%=basePath %>amcharts/ampie/ampie.swf", "ampie", "400", "300", "8", "#ffffff");
		so.addVariable("path", "<%=basePath %>amcharts/ampie/");
		so.addVariable("settings_file", encodeURIComponent("<%=basePath %>amcharts/ampie/ampie_settings.xml"));
		so.addVariable("chart_data",encodeURIComponent("未完成;<s:property value="#request.issueCount.c1" />;false;E74862\n待审核;<s:property value="#request.issueCount.c2" />;false;AD81D9\n已取消;<s:property value="#request.issueCount.c6" />;false;AE81AC\n已解决;<s:property value="#request.issueCount.c3" />;false;ADD981\n延期完成;<s:property value="#request.issueCount.c4" />;false;FEC514\n已评分;<s:property value="#request.issueCount.c5" />;false;92C1D6\n"));
		so.addVariable("preloader_color", "#000000");
		so.write("flashcontent2");
		// ]]>
                    </script>

                </div>
                <div class="clear box">
                    <!-- -->
                </div>
                <div class="PageTop BiggestFont Black">
                    任务
                    走势图</div>
                <br>
                <div class="scalemenu box">
                    <span>
                        任务总量走势图
                    </span>
                </div>
                <div class="clear box">
                    <!-- -->
                </div>
                <div id="flashcontent3"><embed type="application/x-shockwave-flash" src="stat_files/amxy.swf" style="undefined" id="amxy" name="amxy" bgcolor="#FFFFFF" quality="high" flashvars="path=/amcharts/amxy/&amp;settings_file=%2Famcharts%2Famxy%2Famxy_settings.xml&amp;chart_data=%3Cchart%3E%3Cgraphs%3E%3Cgraph%20gid%3D%220%22%3E%3Cpoint%20x%3D%222012-10-14%22%20y%3D%22414%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-10-15%22%20y%3D%22423%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-10-16%22%20y%3D%22425%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-10-17%22%20y%3D%22439%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-10-18%22%20y%3D%22444%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-10-19%22%20y%3D%22447%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-10-20%22%20y%3D%22457%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-10-21%22%20y%3D%22457%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-10-22%22%20y%3D%22464%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-10-23%22%20y%3D%22477%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-10-24%22%20y%3D%22509%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-10-25%22%20y%3D%22531%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-10-26%22%20y%3D%22539%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-10-27%22%20y%3D%22539%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-10-28%22%20y%3D%22539%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-10-29%22%20y%3D%22583%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-10-30%22%20y%3D%22596%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-10-31%22%20y%3D%22616%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-11-01%22%20y%3D%22637%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-11-02%22%20y%3D%22644%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-11-03%22%20y%3D%22653%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-11-04%22%20y%3D%22653%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-11-05%22%20y%3D%22663%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-11-06%22%20y%3D%22666%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-11-07%22%20y%3D%22670%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-11-08%22%20y%3D%22689%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-11-09%22%20y%3D%22703%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-11-10%22%20y%3D%22703%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-11-11%22%20y%3D%22711%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-11-12%22%20y%3D%22734%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-11-13%22%20y%3D%22753%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-11-14%22%20y%3D%22756%22%3E%3C%2Fpoint%3E%3C%2Fgraph%3E%3C%2Fgraphs%3E%3C%2Fchart%3E" height="300" width="800"></div>

                <script type="text/javascript"> 
		var so = new SWFObject("<%=basePath %>amcharts/amxy/amxy.swf", "amxy", "800", "300", "8", "#FFFFFF");
		so.addVariable("path", "<%=basePath %>amcharts/amxy/");
		so.addVariable("settings_file", encodeURIComponent("<%=basePath %>amcharts/amxy/amxy_settings.xml")); 
		so.addVariable("chart_data", encodeURIComponent("<s:property value="#request.issueSumCount" escape="false" />")); 
		so.write("flashcontent3"); 
                </script>

                <div class="clear box">
                    <!-- -->
                </div>
                <br>
                <br>
                <div class="ScaleBox icon_bigest left">
                    <div class="scalemenu box">
                        <span>
                            新任务数量走势图</span></div>
                    <div class="clear box">
                        <!-- -->
                    </div>
                    <div id="flashcontent4"><embed type="application/x-shockwave-flash" src="stat_files/amxy.swf" style="undefined" id="amxy" name="amxy" bgcolor="#FFFFFF" quality="high" flashvars="path=/amcharts/amxy/&amp;settings_file=%2Famcharts%2Famxy%2Famxy_settings.xml&amp;chart_data=%3Cchart%3E%3Cgraphs%3E%3Cgraph%20gid%3D%220%22%3E%3Cpoint%20x%3D%222012-10-14%22%20y%3D%220%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-10-15%22%20y%3D%229%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-10-16%22%20y%3D%222%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-10-17%22%20y%3D%2214%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-10-18%22%20y%3D%225%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-10-19%22%20y%3D%223%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-10-20%22%20y%3D%2210%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-10-21%22%20y%3D%220%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-10-22%22%20y%3D%227%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-10-23%22%20y%3D%2213%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-10-24%22%20y%3D%2232%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-10-25%22%20y%3D%2222%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-10-26%22%20y%3D%228%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-10-27%22%20y%3D%220%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-10-28%22%20y%3D%220%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-10-29%22%20y%3D%2244%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-10-30%22%20y%3D%2213%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-10-31%22%20y%3D%2220%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-11-01%22%20y%3D%2221%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-11-02%22%20y%3D%227%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-11-03%22%20y%3D%229%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-11-04%22%20y%3D%220%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-11-05%22%20y%3D%2210%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-11-06%22%20y%3D%223%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-11-07%22%20y%3D%224%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-11-08%22%20y%3D%2219%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-11-09%22%20y%3D%2214%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-11-10%22%20y%3D%220%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-11-11%22%20y%3D%228%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-11-12%22%20y%3D%2223%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-11-13%22%20y%3D%2219%22%3E%3C%2Fpoint%3E%3Cpoint%20x%3D%222012-11-14%22%20y%3D%223%22%3E%3C%2Fpoint%3E%3C%2Fgraph%3E%3C%2Fgraphs%3E%3C%2Fchart%3E" height="300" width="800"></div>

                    <script type="text/javascript"> 
		var so = new SWFObject("<%=basePath %>amcharts/amxy/amxy.swf", "amxy", "800", "300", "8", "#FFFFFF");
		so.addVariable("path", "<%=basePath %>amcharts/amxy/");
		so.addVariable("settings_file", encodeURIComponent("<%=basePath %>amcharts/amxy/amxy_settings.xml")); 
		so.addVariable("chart_data", encodeURIComponent("<s:property value="#request.issueNewCount" escape="false" />")); 
		so.write("flashcontent4"); 
                    </script>

                </div>
                
                <div class="clear box">
                    <!-- -->
                </div>
                <br>
                <br>
                <div class="PageTop BiggestFont Black">
                    各模块任务分布比例图
                </div>
                <br>
                <div id="flashcontent6"><embed type="application/x-shockwave-flash" src="stat_files/amcolumn.swf" style="undefined" id="amcolumn" name="amcolumn" bgcolor="#FFFFFF" quality="high" flashvars="path=/amcharts/amcolumn/&amp;chart_settings=%3Csettings%3E%3Ctype%3Ebar%3C%2Ftype%3E%3Cdata_type%3Ecsv%3C%2Fdata_type%3E%3Cfont%3ETahoma%3C%2Ffont%3E%3Cdepth%3E10%3C%2Fdepth%3E%3Cangle%3E45%3C%2Fangle%3E%3Ccolumn%3E%3Ctype%3Estacked%3C%2Ftype%3E%3Cwidth%3E50%3C%2Fwidth%3E%3Cspacing%3E0%3C%2Fspacing%3E%3Cgrow_time%3E1%3C%2Fgrow_time%3E%3Cgrow_effect%3Eregular%3C%2Fgrow_effect%3E%3Cballoon_text%3E%3C!%5BCDATA%5B%7Btitle%7D%3A%7Bvalue%7D%5D%5D%3E%3C%2Fballoon_text%3E%3C%2Fcolumn%3E%3Cgraphs%3E%3Cgraph%20gid%3D%220%22%3E%3Ctitle%3E%E5%B7%B2%E8%A7%A3%E5%86%B3%3C%2Ftitle%3E%3Ccolor%3E%23ADD981%3C%2Fcolor%3E%3C%2Fgraph%3E%3Cgraph%20gid%3D%221%22%3E%3Ctitle%3E%E5%BE%85%E5%AE%A1%E6%A0%B8%3C%2Ftitle%3E%3Ccolor%3E%23AD81D9%3C%2Fcolor%3E%3C%2Fgraph%3E%3Cgraph%20gid%3D%222%22%3E%3Ctitle%3E%E6%9C%AA%E4%BF%AE%E5%A4%8D%3C%2Ftitle%3E%3Ccolor%3E%23FEC514%3C%2Fcolor%3E%3C%2Fgraph%3E%3C%2Fgraphs%3E%3C%2Fsettings%3E&amp;chart_data=%E7%BB%A9%E6%95%88%E7%AE%A1%E7%90%86%E7%B3%BB%E7%BB%9F%3B12%3B0%3B0%0AEAS%E9%9C%80%E6%B1%82%3B9%3B0%3B0%0A%E6%A1%8C%E9%9D%A2%E6%94%AF%E6%8C%81%3B77%3B2%3B2%0AEAS%E5%AE%A2%E6%88%B7%E7%AB%AF%3B3%3B0%3B0%0AOA%E5%8A%9E%E5%85%AC%E7%B3%BB%E7%BB%9F%3B4%3B0%3B1%0A%E8%B4%A2%E6%99%BA%E5%AE%9D%E7%B3%BB%E7%BB%9F%3B13%3B0%3B0%0A%E7%BD%91%E6%8E%A7%E5%AE%9D%E7%B3%BB%E7%BB%9F%3B1%3B0%3B0%0A%E6%98%93%E9%82%AE%E7%B3%BB%E7%BB%9F%3B4%3B0%3B0%0A%E7%BD%91%E7%BB%9C%E6%95%85%E9%9A%9C%3B13%3B0%3B3%0A%E6%9C%8D%E5%8A%A1%E5%99%A8%E6%95%85%E9%9A%9C%3B9%3B0%3B1%0A%E8%AF%9D%E5%8A%A1%E7%B3%BB%E7%BB%9F%3B3%3B0%3B0%0A%E5%8A%9E%E5%85%AC%E8%AE%BE%E5%A4%87%3B41%3B0%3B0%0A%E7%94%B5%E8%AF%9D%E6%9C%8D%E5%8A%A1%3B8%3B0%3B1%0A%E4%B8%80%E5%8D%A1%E9%80%9A%3B3%3B0%3B0%0A%E7%9B%91%E6%8E%A7%E3%80%81%E5%BC%B1%E7%94%B5%3B23%3B0%3B6%0A%E5%8A%A0%E5%AF%86%E7%B3%BB%E7%BB%9F%3B0%3B0%3B0%0A%E8%A7%86%E9%AB%98%E8%A7%86%E9%A2%91%E4%BC%9A%E8%AE%AE%E7%B3%BB%E7%BB%9F%3B0%3B0%3B0%0A%E7%BB%BC%E5%90%88%E4%B8%9A%E5%8A%A1%3B42%3B0%3B4%0A%E5%AE%89%E5%8D%93App%3B32%3B0%3B9%0AEES%E9%A1%B9%E7%9B%AE%3B14%3B0%3B2&amp;preloader_color=#999999" height="500" width="600"></div>

                <script type="text/javascript">
		 		
		var so = new SWFObject("<%=basePath %>amcharts/amcolumn/amcolumn.swf", "amcolumn", "600", "500", "8", "#FFFFFF");
		so.addVariable("path", "<%=basePath %>amcharts/amcolumn/");
		so.addVariable("chart_settings", encodeURIComponent("<settings><type>bar</type><data_type>csv</data_type><font>Tahoma</font><depth>10</depth><angle>45</angle><column><type>stacked</type><width>50</width><spacing>0</spacing><grow_time>1</grow_time><grow_effect>regular</grow_effect><balloon_text><![CDATA[{title}:{value}]]></balloon_text></column><graphs><graph gid=\"0\"><title>未完成</title><color>#E74862</color></graph><graph gid=\"1\"><title>待审核</title><color>#AD81D9</color></graph><graph gid=\"2\"><title>已完成</title><color>#ADD981</color></graph><graph gid=\"3\"><title>延期完成</title><color>#FEC514</color></graph><graph gid=\"4\"><title>已评分</title><color>#92C1D6</color></graph></graphs></settings>"));
		so.addVariable("chart_data", encodeURIComponent("<s:property value="#request.issueModularStat" escape="false" />"));
		so.addVariable("preloader_color", "#999999");
		so.write("flashcontent6"); 
                </script>

                <div class="clear box">
                    <!-- -->
                </div>
                <br>
                <br>
                <div class="PageTop BiggestFont Black">
                    小组人员任务分配情况
                </div>
                <br>
                <div id="flashcontent7"><embed type="application/x-shockwave-flash" src="stat_files/amcolumn.swf" style="undefined" id="amcolumn" name="amcolumn" bgcolor="#FFFFFF" quality="high" flashvars="path=/amcharts/amcolumn/&amp;chart_settings=%3Csettings%3E%3Ctype%3Ebar%3C%2Ftype%3E%3Cdata_type%3Ecsv%3C%2Fdata_type%3E%3Cfont%3ETahoma%3C%2Ffont%3E%3Cdepth%3E10%3C%2Fdepth%3E%3Cangle%3E45%3C%2Fangle%3E%3Ccolumn%3E%3Ctype%3Estacked%3C%2Ftype%3E%3Cwidth%3E50%3C%2Fwidth%3E%3Cspacing%3E0%3C%2Fspacing%3E%3Cgrow_time%3E1%3C%2Fgrow_time%3E%3Cgrow_effect%3Eregular%3C%2Fgrow_effect%3E%3Cballoon_text%3E%3C!%5BCDATA%5B%7Btitle%7D%3A%7Bvalue%7D%5D%5D%3E%3C%2Fballoon_text%3E%3C%2Fcolumn%3E%3Cgraphs%3E%3Cgraph%20gid%3D%220%22%3E%3Ctitle%3E%E5%B7%B2%E8%A7%A3%E5%86%B3%3C%2Ftitle%3E%3Ccolor%3E%23ADD981%3C%2Fcolor%3E%3C%2Fgraph%3E%3Cgraph%20gid%3D%221%22%3E%3Ctitle%3E%E5%BE%85%E5%AE%A1%E6%A0%B8%3C%2Ftitle%3E%3Ccolor%3E%23AD81D9%3C%2Fcolor%3E%3C%2Fgraph%3E%3Cgraph%20gid%3D%222%22%3E%3Ctitle%3E%E6%9C%AA%E4%BF%AE%E5%A4%8D%3C%2Ftitle%3E%3Ccolor%3E%23FEC514%3C%2Fcolor%3E%3C%2Fgraph%3E%3C%2Fgraphs%3E%3C%2Fsettings%3E&amp;chart_data=%E9%99%88%E4%BD%B3%E6%B3%A2%3B4%3B0%3B5%0A%E8%91%A3%E5%8A%A0%E8%8C%82%3B52%3B0%3B0%0A%E8%83%A1%E5%8D%A1%E6%9D%B0%3B10%3B0%3B1%0A%E9%BB%84%E6%98%8E%3B15%3B2%3B6%0A%E6%9D%8E%E5%9D%9A%3B6%3B0%3B0%0A%E6%A2%81%E6%B0%B8%E6%B3%B0%3B17%3B0%3B1%0A%E5%88%98%E6%AD%A6%3B67%3B0%3B2%0A%E7%BC%AA%E6%88%90%3B27%3B0%3B2%0A%E7%A7%A6%E6%99%93%E9%98%B3%3B10%3B0%3B0%0A%E5%8F%B2%E6%95%88%E6%97%AD%3B0%3B0%3B0%0A%E7%8E%8B%E6%98%8E%E6%98%8E%3B21%3B0%3B1%0A%E5%BE%90%E7%9B%8A%E5%86%9B%3B9%3B0%3B1%0A%E6%9D%A8%E7%AB%8B%E7%BA%A2%3B0%3B0%3B0%0A%E5%8F%B6%E6%B6%9B%3B7%3B0%3B6%0A%E5%BA%94%E5%AE%87%E5%AE%BE%3B7%3B0%3B0%0A%E5%BC%A0%E6%B0%B8%E8%8A%B3%3B4%3B0%3B0%0A%E8%B5%B5%E5%BC%BA%3B50%3B0%3B1&amp;preloader_color=#999999" height="500" width="600"></div>

                <script type="text/javascript">
		 		
		var so = new SWFObject("<%=basePath %>amcharts/amcolumn/amcolumn.swf", "amcolumn", "600", "500", "8", "#FFFFFF");
		so.addVariable("path", "<%=basePath %>amcharts/amcolumn/");
		so.addVariable("chart_settings", encodeURIComponent("<settings><type>bar</type><data_type>csv</data_type><font>Tahoma</font><depth>10</depth><angle>45</angle><column><type>stacked</type><width>50</width><spacing>0</spacing><grow_time>1</grow_time><grow_effect>regular</grow_effect><balloon_text><![CDATA[{title}:{value}]]></balloon_text></column><graphs><graph gid=\"0\"><title>未完成</title><color>#E74862</color></graph><graph gid=\"1\"><title>待审核</title><color>#AD81D9</color></graph><graph gid=\"2\"><title>已完成</title><color>#ADD981</color></graph><graph gid=\"3\"><title>延期完成</title><color>#FEC514</color></graph><graph gid=\"4\"><title>已评分</title><color>#92C1D6</color></graph></graphs></settings>"));
		so.addVariable("chart_data", encodeURIComponent("<s:property value="#request.issueStafferStat" escape="false" />"));
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