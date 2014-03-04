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
<script src="js/showdiv.js" type="text/javascript"></script>
<style type="text/css">
.item{
}
.shanchu{
}
</style>
</head>
<script type="text/javascript" src="calender/WdatePicker.js"></script>
<script type="text/javascript" src="ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="ckeditor/config.js"></script>
<SCRIPT type="text/javascript">
function init(){
	if($("#stat").val()=='3'){
		$("#tijiao").hide();
		$("#jxtj").hide();
		$("div.shanchu").hide();
		$("input").attr("disabled","disabled");
		}
}
function subm(){
	var index = $(".item").length;
	for(var i=0;i<index;i++){
		//alert($("#more"+i).find("input[type='file']").length);
		var count = 0;
		$.each($("#more"+i).find("input[type='file']"),function(){
				if($(this).val()){
					count++;
					}
			});
		
		$("#filecount"+i).val(count);
		}	
	var form = document.getElementById("form1");
	
	form.submit();
}
function adddiv1(){
	var div = $("#moban").clone(true);
	var index = $(".item").length;
	$(div).find("input[name='t1']").attr("name","subjectItem.issues["+index+"].title");
	$(div).find("select[name='t2']").attr("name","subjectItem.issues["+index+"].modular.id");
	$(div).find("select[name='t3']").attr("name","subjectItem.issues["+index+"].completer.sid");
	$(div).find("input[name='t4']").attr("name","subjectItem.issues["+index+"].exprireDate");
	$(div).find("select[name='t5']").attr("name","subjectItem.issues["+index+"].percentage");
	$(div).find("input[name='t6']").attr("name","subjectItem.issues["+index+"].priority");
	$(div).find("input[name='t7']").attr("name","");
	$(div).find("input[name='t8']").attr("name","subjectItem.issues["+index+"].secret");
	$(div).find("textarea[name='t9']").attr("id","description"+index).attr("name","description["+index+"]");
	$(div).find("#more").attr("id","more"+index);
	$(div).find("input[name='t10']").attr("name","filecount["+index+"]").attr("id","filecount"+index);
	
	$(div).addClass("item");
	$("#main").append(div);
	$(div).show();
	//编辑框初始化
	ckeditInit("description"+index);
}
function ckeditInit(id){
	CKEDITOR.replace(id,{
		//extraPlugins : 'easybug',
		toolbar : 'easybugfull',
		width:750,
		height:500
	});
	
}
function delDiv(obj){
	$(obj).parent().parent().parent().parent().remove();
	var index = $(".item").length;
	
}
function compute(obj){
	alert(obj.value);
}
function addMore(obj)
{

	var td = document.getElementById($(obj).parent().attr("id"));
	
	var br = document.createElement("br");
	var input = document.createElement("input");
	var button = document.createElement("input");
	
	input.type = "file";
	input.name = "upload";
	input.onchange="compute(this)";
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
</SCRIPT>
<body onload="init();">
<div class="mina_nav">
    <div id="div1" class="mina_nav_bm">
      <div class="mina_nav_bm2"><a href="index">温馨提醒</a></div>
      <div class="mina_nav_bm1"><a href="planadd">工作计划</a></div>
      <div class="mina_nav_bm2"><a href="myissue">我的任务</a></div>
      <div class="mina_nav_bm2"><a href="allissue">所有任务</a></div>
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
    <form id="form1" action="subjectitem!submit" method="post" enctype="multipart/form-data">
      <div class="summary_mina_con" id="main">
        <div ><a href="issuesegment" style="color:#3c9dc4;">< 返回</a></div>
        <div id="head" style=" height:40px; line-height:40px; font-size:14px; border-bottom:1px dashed #eaeaea;">
         <input type="hidden"  name="subjectItem.id" value="<s:property value="#request.subjectItem.id"/>"></input>
          <input type="hidden" id="stat" value="<s:property value="#request.subjectItem.status"/>"></input>
          <div style="float:left;"><font style="color:#838383;">工作项目：</font><s:property value="#request.subjectItem.subject.title"/></div>
          <div style="float:left; margin-left:50px;"><font style="color:#838383;">任务名称：</font> <s:property value="#request.subjectItem.title"/></div>
          <div style="float:left; margin-left:50px;"><font style="color:#838383;">计划完成时间：</font><s:date name="#request.subjectItem.completeDate" format="yyyy-MM-dd"></s:date></div>
        </div>
        <s:iterator value="#request.subjectItem.issues" var="issue" status="st">
        <div class="item" id="item<s:property value="#st.index"/>">
          <div class="renwu_info">
          <div style="height:20px; padding:10px;background-color:#ededed;">
            <div id="tab_show1" onclick="showtable(this)" style=" width:24px; height:24px;background-image:url(images/icon_show.png); float:left; cursor:pointer;" ></div>
            <div style="float:left; padding-left:10px;"><s:property value="#issue.title"/></div>
            <div class="shanchu" style="float:right; "><a href="javascript:void(0);" onclick="delDiv(this)" style="color:#3c9dc4; font-size:12px;">删除</a></div>
          </div>
        </div>
          <div id="table_show1" style="font-size:12px; color:#838383; margin:20px; display:none; ">
          <table cellpadding="5px" cellspacing="5px">
          <tr>
              <td></td>
              <td></td>
              <td><input type="hidden" name="subjectItem.issues[<s:property value="#st.index"/>].id" value="<s:property value="#issue.id"/>"/></td>
            </tr>
            <tr>
              <td><span style="color:#ed3535;">*</span></td>
              <td>任务名称：</td>
              <td><input name="subjectItem.issues[<s:property value="#st.index"/>].title"  style="width:360px; height:30px;" value="<s:property value="#issue.title"/>"/></td>
            </tr>
            <tr>
              <td><span style="color:#ed3535;">*</span></td>
              <td>工作模块：</td>
              <td><div class="style_select">
                  <select name="subjectItem.issues[<s:property value="#st.index"/>].modular.id" id="s_moudle" >
                <s:iterator value="#request.modulars" var="modular">
               
				<option  value="<s:property value="#modular.id" />"    <s:if test="#modular.id==#issue.modular.id">selected="selected"</s:if>>
					<s:property value="#modular.name" />
				</option>        	
        		</s:iterator>
                    </select>
                </div></td>
            </tr>
            <tr>
              <td><span style="color:#ed3535;">*</span></td>
              <td>责任人：</td>
              <td><div class="style_select">
                  <select name="subjectItem.issues[<s:property value="#st.index"/>].completer.sid" id="s_handler">
                       <s:iterator value="#request.department.staffers" var="staffer">
                       	<option value="<s:property value="#staffer.sid" />" <s:if test="#staffer.sid==#issue.completer.sid">selected="selected"</s:if>>
                       	<s:property value="#staffer.name" />
                       	</option>
                       </s:iterator> 
                    </select>
                </div></td>
            </tr>
            <tr>
              <td><span style="color:#ed3535;">*</span></td>
              <td>完成时间：</td>
              <td><input name="subjectItem.issues[<s:property value="#st.index"/>].exprireDate" id="expiredate" type="text" value="<s:date name="#issue.exprireDate" format="yyyy-MM-dd"/>"
			onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  style="width:140px; height:25px;" /></td>
            </tr>
            <tr>
              <td></td>
              <td>任务系数：</td>
              <td><div class="style_select">
                  <select name="subjectItem.issues[<s:property value="#st.index"/>].percentage">
                    <option value="100" <s:if test="#issue.percentage==100">selected="selected"</s:if>>1</option>
                    <option value="110" <s:if test="#issue.percentage==110">selected="selected"</s:if>>1.1</option>
                    <option value="130" <s:if test="#issue.percentage==130">selected="selected"</s:if>>1.3</option>
                  </select>
                </div></td>
            </tr>
            <tr>
              <td></td>
              <td>优先级别：</td>
              <td>
              <input name="subjectItem.issues[<s:property value="#st.index"/>].priority" <s:if test="#issue.priority==1">checked="checked"</s:if><s:elseif test="!#issue">checked="checked"</s:elseif> value="1" type="radio">
                    低
                    
                    <input name="subjectItem.issues[<s:property value="#st.index"/>].priority" value="2" type="radio" <s:if test="#issue.priority==2">checked="checked"</s:if>>
                    中
                    
                    <input name="subjectItem.issues[<s:property value="#st.index"/>].priority" value="3" type="radio" <s:if test="#issue.priority==3">checked="checked"</s:if>>
                    高
                    
                    <input name="subjectItem.issues[<s:property value="#st.index"/>].priority" value="4" type="radio" <s:if test="#issue.priority==4">checked="checked"</s:if>>
                    紧急
                    
                    <input name="subjectItem.issues[<s:property value="#st.index"/>].priority" value="5" type="radio" <s:if test="#issue.priority==5">checked="checked"</s:if>>
                    严重
              </td>
            </tr>
            <tr>
              <td></td>
              <td>抄送：</td>
              <td><input type="checkbox" />
                施光辉
                <input type="checkbox" />
                唐俊杰
                <input type="checkbox" />
                章建成</td>
            </tr>
            <tr>
              <td></td>
              <td>是否保密： </td>
              <td>  <input name="subjectItem.issues[<s:property value="#st.index"/>].secret" id="secret" type="checkbox" value="true" <s:if test="#issue.secret==true">checked="checked"</s:if>></input></td>
            </tr>
            <tr>
              <td><span style="color:#ed3535;">*</span></td>
              <td>具体内容：</td>
              <td>  <div class="left GrayBorder MLine">
                          <a id="href_client" style="display: none;" href="<%=basePath %>client/setup.exe" class="Blue" onclick="javascript:AddHit();">
                            下载新版截图插件，支持剪切板粘贴</a>
                        <p class="Smallbox">
                     
                           <textarea rows="30" cols="50" name="description[<s:property value="#st.index"/>]" id="description[<s:property value="#st.index"/>]"><s:property value="#issue.issueWorkflows[0].content" /></textarea>
<script type="text/javascript">CKEDITOR.replace('description[<s:property value="#st.index"/>]',{
	//extraPlugins : 'easybug',
	toolbar : 'easybugfull',
	width:750,
	height:500
});</script>

                            
                        </p>
                    </div></td>
            </tr>
            <s:if test="#issue.issueWorkflows[0].attachments.size()>0">
            <tr>
            <td></td>
            <td>已上传文件：</td>
             <td>  <s:iterator value="#issue.issueWorkflows[0].attachments" var="attachment" status="at">
									
										<a href="<%=basePath %>issuehandle!getDownloadFile?UUIDName=${attachment.UUIDName }&realname=${attachment.realName }"><img src="<%=basePath %>Content/images/attachment.jpg"><font color=#42426F>${attachment.realName}</font></a>
									
									</s:iterator>
			</td>
            </tr>
            </s:if>
            <tr>
              <td><input type="hidden" id="filecount<s:property value="#st.index"/>" name="filecount[<s:property value="#st.index"/>]" ></input></td>
              <td>上传文件：</td>
              <td><table align="left" width="50%" border="0">
						<tr>
							<td>
								
							</td>
							<td id="more<s:property value="#st.index"/>" >
								<s:file name="upload" width="120" ></s:file>
								<input type="button" value="上传更多..." onclick="addMore(this)">
							</td>
						</tr>
					</table></td>
            </tr>
          </table>
        </div>
        </div>
        </s:iterator>
     
      	<div id="moban"  style="display:none;">
        <div  class="renwu_info" >
          <div style="height:20px; padding:10px;background-color:#ededed;">
            <div id="tab_show2" onclick="showtable(this)" style=" width:24px; height:24px;background-image:url(images/icon_show.png); float:left; cursor:pointer;" ></div>
            <div style="float:left; padding-left:10px;">新任务</div>
            <div class="shanchu" style="float:right; "><a href="javascript:void(0);" onclick="delDiv(this)" style="color:#3c9dc4; font-size:12px;">删除</a></div>
          </div>
        </div>
        <div style="font-size:12px; color:#838383; margin:20px; display:none; ">
         <table cellpadding="5px" cellspacing="5px">
            <tr>
              <td><span style="color:#ed3535;">*</span></td>
              <td>任务名称：</td>
              <td><input name="t1" style="width:360px; height:30px;" /></td>
            </tr>
            <tr>
              <td><span style="color:#ed3535;">*</span></td>
              <td>工作模块：</td>
              <td><div class="style_select">
                  <select name="t2" >
                <s:iterator value="#request.modulars" var="modular">
               
				<option  value="<s:property value="#modular.id" />"  >
					<s:property value="#modular.name" />
				</option>        	
        		</s:iterator>
                    </select>
                </div></td>
            </tr>
            <tr>
              <td><span style="color:#ed3535;">*</span></td>
              <td>责任人：</td>
              <td><div class="style_select">
                  <select name="t3">
                       <s:iterator value="#request.department.staffers" var="staffer">
                       	<option value="<s:property value="#staffer.sid" />" >
                       	<s:property value="#staffer.name" />
                       	</option>
                       </s:iterator> 
                    </select>
                </div></td>
            </tr>
            <tr>
              <td><span style="color:#ed3535;">*</span></td>
              <td>完成时间：</td>
              <td><input name="t4" type="text" value=""
			onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  style="width:140px; height:25px;" /></td>
            </tr>
            <tr>
              <td></td>
              <td>任务系数：</td>
              <td><div class="style_select">
                  <select name="t5" >
                    <option value="100" >1</option>
                    <option value="110" >1.1</option>
                    <option value="130" >1.3</option>
                  </select>
                </div></td>
            </tr>
            <tr>
              <td></td>
              <td>优先级别：</td>
              <td>
              <input name="t6"  value="1" type="radio">
                    低
                    
                    <input name="t6" value="2" type="radio" >
                    中
                    
                    <input name="t6" value="3" type="radio" >
                    高
                    
                    <input name="t6" value="4" type="radio" >
                    紧急
                    
                    <input name="t6" value="5" type="radio" >
                    严重
              </td>
            </tr>
            <tr>
              <td></td>
              <td>抄送：</td>
              <td><input name="t7" type="checkbox" />
                施光辉
                <input name="t7" type="checkbox" />
                唐俊杰
                <input name="t7" type="checkbox" />
                章建成</td>
            </tr>
            <tr>
              <td></td>
              <td>是否保密： </td>
              <td>  <input name="t8" id="secret" type="checkbox" value="true" ></input></td>
            </tr>
            <tr>
              <td><span style="color:#ed3535;">*</span></td>
              <td>具体内容：</td>
              <td>  <div class="left GrayBorder MLine">
                          <a id="href_client" style="display: none;" href="<%=basePath %>client/setup.exe" class="Blue" onclick="javascript:AddHit();">
                            下载新版截图插件，支持剪切板粘贴</a>
                        <p class="Smallbox">
                           <textarea rows="30" cols="50" name="t9" id=""><s:property value="#issue.issueWorkflow.content" /></textarea>
<script type="text/javascript"></script>

                            
                        </p>
                    </div></td>
            </tr>
            <tr>
              <td><input type="hidden"  name="t10"></input></td>
              <td>上传文件：</td>
              <td><table align="left" width="50%" border="0">
						<tr>
							<td>
								
							</td>
							<td id="more">
								<s:file name="upload" width="120" ></s:file>
								<input type="button" value="上传更多..." onclick="addMore(this)">
							</td>
						</tr>
					</table></td>
            </tr>
          </table>
        </div>
       </div>
      </div>
      
        <div id="jxtj" style=" width:100%; height:50px; line-height:50px; padding-left:10px;"><a href="#" style="color:#3c9dc4; font-size:12px;" onclick="adddiv1()">继续添加任务</a></div>
        <div id="tijiao" style=" width:100%; height:60px;background-color:#FFFFFF">
          <div class="style_button2" >
            <a href="javascript:void(0);" onclick="subm()">提交</a>
          </div>
          <div class="style_button3">
            <a href="subjectitem?id=<s:property value="#request.subjectItem.id"/>">取消</a>
          </div>
        </div>
     </form>
    </div>
  </div>
</body>
</html>