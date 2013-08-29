<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="header1.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="calender/WdatePicker.js"></script>
<script type="text/javascript" src="ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="ckeditor/config.js"></script>

		<script type="text/javascript">

<!--addMore函数可以提供上传多个文件上传-->

function addMore()
{

	var td = document.getElementById("more");
	
	var br = document.createElement("br");
	var input = document.createElement("input");
	var button = document.createElement("input");
	
	input.type = "file";
	input.name = "upload";
	
	button.type = "button";
	button.value = "   删除    ";
	
	button.onclick = function()
	{
		td.removeChild(br);
		td.removeChild(input);
		td.removeChild(button);
	}
	
	td.appendChild(br);
	td.appendChild(input);
	td.appendChild(button);
}

</script>
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
	  if(!$("#id").val()){
		  var sid=$("#s_moudle  option:selected").attr("sid");
		  $("#s_handler option").each(function(){
	        	if($(this).val()==sid)
	        		$(this).attr("selected","selected");
	        });
		  }
	 
      if(!$("#expiredate").val()){
    	  var now = new Date();
      now = new Date(Date.parse(now) + (86400000 * 7)).format("yyyy-MM-dd");
	  $("#expiredate").val(now);
          }
     
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
    </script>
</head>
<body>

    <!-- start of content -->
    <div class="Content">
        <div class="Right">
            <form id="_form" action="<%=basePath %>issueadd!add" method="post" enctype="multipart/form-data">
            <input name="did" value="<s:property value="#request.department.did" />" type="hidden">
            <input id="id" value="<s:property value="#request.issue.id" />" type="hidden">
            <div class="Bugicon Midbox">
                新任务
            </div>
            <!--start of solution-->
            <div class="Solution Insert ">
                <p class="Gray box">
                    <label>
                        工作模块</label>
                    <select name="module" id="s_moudle" onchange="SelectM();">
                <s:iterator value="#request.modulars" var="modular">
               
				<option  value="<s:property value="#modular.id" />"   sid="<s:property value="#modular.responsibleStaffer.sid" />"  <s:if test="#modular.id==#request.issue.modular.id">selected="selected"</s:if>>
					<s:property value="#modular.name" />
				</option>        	
        		</s:iterator>
                    </select>
                </p>
                  <p class="Gray box">
                    <label>
                        工作项目</label>
                    <input name="subject" id="subject" value="<s:property value="#request.issue.subject" />"  type="text"></input>
                    <span id="info" style="color: Red"></span>
                </p>
                <p class="Gray box">
                    <label>
                        任务
                        名称<font color="red">*</font></label>
                    <input id="title" name="title" value="<s:property value="#request.issue.title" />" size="50" style="width:750px;" type="text">
                    <font color="red">
                        
                    </font>
                </p>
            
                <p class="Gray box NoneBorder">
                    <label>
                        优先级</label>
                     
                    <input name="priority" <s:if test="#request.issue.priority==1">checked="checked"</s:if><s:elseif test="!#request.issue">checked="checked"</s:elseif> value="1" type="radio">
                    低
                    
                    <input name="priority" value="2" type="radio" <s:if test="#request.issue.priority==2">checked="checked"</s:if>>
                    中
                    
                    <input name="priority" value="3" type="radio" <s:if test="#request.issue.priority==3">checked="checked"</s:if>>
                    高
                    
                    <input name="priority" value="4" type="radio" <s:if test="#request.issue.priority==4">checked="checked"</s:if>>
                    紧急
                    
                    <input name="priority" value="5" type="radio" <s:if test="#request.issue.priority==5">checked="checked"</s:if>>
                    严重
                    
                </p>
               
                <p class="Gray box">
                  <label>
                        有效时间</label>
                       <input name="expiredate" id="expiredate" type="text" value="<s:date name="#request.issue.exprireDate" format="yyyy-MM-dd"/>"
			onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" ></input>
                </p>
                 <p class="Gray box">
                    <label>
                        处理人</label>
                    
                    <select name="handler" id="s_handler">
                       <s:iterator value="#request.department.staffers" var="staffer">
                       	<option value="<s:property value="#staffer.sid" />" <s:if test="#staffer.sid==#request.issue.completer.sid">selected="selected"</s:if>>
                       	<s:property value="#staffer.name" />
                       	</option>
                       </s:iterator> 
                    </select>
                    
                    <span id="info" style="color: Red"></span>
                </p>
                


                 <p class="Gray box">
                    <label>
                        协调人</label>
                    <input name="coord" id="coord" type="text"></input>
                    <span id="info" style="color: Red"></span>
                </p>
				<p class="Gray box">
                  <label>
                        任务类型</label>
                       <select name="issueType">
                       	<option value="2" <s:if test="#request.issue.issueType==2">selected="selected"</s:if> >新增任务</option>
                       	<option value="1" <s:if test="#request.issue.issueType==1">selected="selected"</s:if> >计划任务</option>
                       </select>
                </p>

                            
                 <p class="Gray box">
                  <label>
                        是否保密</label>
                       <input name="secret" id="secret" type="checkbox" value="1"></input>
                </p>
                 <div class="Gray box Detail">
                    <label>
                        描述与截图
                    </label>
                    <div class="left GrayBorder MLine">
                          <a id="href_client" style="display: none;" href="<%=basePath %>client/setup.exe" class="Blue" onclick="javascript:AddHit();">
                            下载新版截图插件，支持剪切板粘贴</a>
                        <p class="Smallbox">
                           <textarea rows="30" cols="50" name="description" id="description"><s:property value="#request.issueWorkflow.content" /></textarea>
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
	                        <label>上传附件(<100M)</label>

					
  					<table align="left" width="50%" border="0">
						<tr>
							<td>
								
							</td>
							<td id="more" >
								<s:file name="upload" width="120"></s:file>
								<input type="button" value="上传更多..." onclick="addMore()">
							</td>
						</tr>
					</table>

                   		 </p>
			
			<br><br><br><br>
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