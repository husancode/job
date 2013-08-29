<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="calender/WdatePicker.js"></script>
<script type="text/javascript" src="ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="ckeditor/config.js"></script>

 <script type="text/javascript">
	Date.prototype.format = function(format)

	{ 

	var o = { 

	"M+" : this.getMonth()+1, //month 

	"d+" : this.getDate(), //day 

	"h+" : this.getHours(), //hour 

	"m+" : this.getMinutes(), //minute 

	"s+" : this.getSeconds(), //second 

	"q+" : Math.floor((this.getMonth()+3)/3), //quarter 

	"S" : this.getMilliseconds() //millisecond 

	}; 

	if(/(y+)/.test(format)) format=format.replace(RegExp.$1, 

	(this.getFullYear()+"").substr(4 - RegExp.$1.length)); 

	for(var k in o)if(new RegExp("("+ k +")").test(format)) 

	format = format.replace(RegExp.$1, 

	RegExp.$1.length==1 ? o[k] : 

	("00"+ o[k]).substr((""+ o[k]).length)); 

	return format; 

	}; 
  $(document).ready(function(){
	 
      //var now = new Date();
     // now = new Date(Date.parse(now) + (86400000 * 7)).format("yyyy-MM-dd");
	 // $("#expiredate").val(now);
      $("input[name='priority']").click(function(){
    	  var nowTime   = new Date();
          if($(this).val()>2){
        	  nowTime = new Date(Date.parse(nowTime) + (86400000 * 3)).format("yyyy-MM-dd");
        	  $("#expiredate").val(nowTime);
              }else{
            	  nowTime = new Date(Date.parse(nowTime) + (86400000 * 7)).format("yyyy-MM-dd");
            	  $("#expiredate").val(nowTime);
                  }
          });
	  });
 

  function SelectM()
  {
      var sid=$("#s_moudle  option:selected").attr("sid");
      $("#s_handler option").each(function(){
          	if($(this).val()==sid)
          		$(this).attr("selected","selected");
          });
  }

  var titlenull="标题不能为空";
  function submit(m)
  {
    if ($("#title").val().trim()=="")
    {
      alert(titlenull);
      return;
    }
   $("#"+m).submit();
  }
  function Rehandle()
  { 
    if (document.getElementById("c_rehandle").checked)
    {
     document.getElementById("s_handler").disabled=false;
     document.getElementById("a_content").style.display="block";
    }
    else
    {
    document.getElementById("s_handler").disabled=true;
     document.getElementById("a_content").style.display="none";
    }
  }
    </script>
</head>
<body>

    <!-- start of content -->
    <div class="Content">
        <div class="Right">
            <form id="_form" action="<%=basePath %>issueedit!commit" method="post">
            <input name="aissue.id" value="<s:property value="#request.aIssue.id" />" type="hidden">
            <input name="wfid" value="<s:property value="#request.issueWorkflow.id" />" type="hidden">
            <div class="Bugicon Midbox">
                任务修改
            </div>
            <!--start of solution-->
            <div class="Solution Insert ">
                <p class="Gray box">
                    <label>
                        任务
                        名称<font color="red">*</font></label>
                    <input id="title" name="aissue.title" value="<s:property value="#request.aIssue.title" />" size="50" style="width:750px;" type="text">
                    <font color="red">
                        
                    </font>
                </p>
                <p class="Gray box">
                    <label>
                       工作项目</label>
                    <select name="aissue.modular.id" id="s_moudle"   onchange="SelectM();">
                <s:iterator value="#request.modulars" var="modular">
				<option value="<s:property value="#modular.id" />" <s:if test="#request.aIssue.modular.id==#modular.id">selected="selected"</s:if> sid="<s:property value="#modular.responsibleStaffer.sid" />">
					<s:property value="#modular.name" />
				</option>        	
        		</s:iterator>
                    </select>
                </p>
                <p class="Gray box NoneBorder">
                    <label>
                        优先级</label>
                    
                    <input name="aissue.priority" <s:if test="#request.aIssue.priority==1">checked="checked"</s:if>  value="1" type="radio">
                    低
                    
                    <input name="aissue.priority" value="2" type="radio" <s:if test="#request.aIssue.priority==2">checked="checked"</s:if>>
                    中
                    
                    <input name="aissue.priority" value="3" type="radio" <s:if test="#request.aIssue.priority==3">checked="checked"</s:if>>
                    高
                    
                    <input name="aissue.priority" value="4" type="radio" <s:if test="#request.aIssue.priority==4">checked="checked"</s:if>>
                    紧急
                    
                    <input name="aissue.priority" value="5" type="radio" <s:if test="#request.aIssue.priority==5">checked="checked"</s:if>>
                    严重
                    
                </p>
               
                <p class="Gray box">
                  <label>
                        有效时间</label>
                        
                       <input name="aissue.exprireDate" id="expiredate" type="text" value="<s:date name="#request.aIssue.exprireDate" format="yyyy-MM-dd"/>"
			onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" class="Wdate">
				
                        延期原因<input type="text" name="aissue.reason" value="<s:property value="#request.aIssue.reason" />"></input>
                </p>
                
                <p class="Gray box">
                  <label>
                        是否保密</label>
                       <input name="aissue.secret" id="secret" type="checkbox" value="true" <s:if test="#request.aIssue.secret==true">checked='checked'</s:if>></input>
                </p>
                 <div class="Gray box Detail">
                    <label>
                        描述与截图
                    </label>
                    <div class="left GrayBorder MLine">
                        
                        <p class="Smallbox">
                           <textarea rows="30" cols="50" name="description" id="description"><s:property value="#request.issueWorkflow.content"></s:property></textarea>
<script type="text/javascript">CKEDITOR.replace('description',{
	//extraPlugins : 'easybug',
	toolbar : 'easybugfull',
	width:750,
	height:500
});</script>

                            
                        </p>
                    </div>
                    <div class="clear">
                        <!-- -->
                    </div>
                </div>
                 <p class="Gray box">
                    <label>
                        重新分配</label>
                    <input type="checkbox" id="c_rehandle" onclick="Rehandle();" name="if_rehandle" value="true" />
                    <select id="s_handler" disabled="disabled" name="aissue.completer.sid">
                         <s:iterator value="#request.department.staffers" var="staffer">
                       	<option <s:if test="#staffer.sid==#request.aIssue.completer.sid">selected="selected"</s:if>  value="<s:property value="#staffer.sid" />">
                       	<s:property value="#staffer.name" />
                       	</option>
                       </s:iterator> 
                    </select>
                </p>
                 <div class="clear">
                    <!-- -->
                </div>
                 <p class="Gray box" id="a_content" style="display: none;">
                    <label>
                        备注</label>
                    <textarea rows="3" name="content" id="content" cols="30"></textarea>
                </p>
                
                <p class="Gray box">
                    <label>
                        &nbsp;</label>
                    <a href="javascript:submit('_form');" class=" Buttom  icon_bigest"><span>
                        提交</span></a>
                    
                    <a href="javascript:history.go(-1);" class="Buttom"><span>
                        返回</span></a>
                </p>
                <div class="clear">
                    <!-- -->
                </div>
            </div>
            </form>
            <!--end of solution-->
        </div>
        <div class="clear">
            <!-- -->
        </div>
    </div>
</body>
</html>