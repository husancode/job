<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="headernew.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
  <link href="<%=basePath %>Content/data/style.css" rel="stylesheet" type="text/css" />
   <link href="<%=basePath %>Content/thickbox.css" rel="stylesheet" type="text/css" />
   <script src="<%=basePath %>Scripts/common.js" type="text/javascript"></script>
   <script src="<%=basePath %>Scripts/thickbox.js" type="text/javascript"></script>
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
<SCRIPT type="text/javascript">
function GetMember(){
    if (document.getElementById("m_person"))
    { 
      if ($("#m_person").css("display")=="none")
         $("#m_person").show(); 
      return;
    }
 

 $("#input_changTo").html("加载中...... ");
 var sel  = "<select id='m_person' name='m_person'>";
 <s:iterator value="#request.department.staffers" var="staffer">
	sel = sel+ " <option <s:if test="#staffer.sid==#request.aIssue.completer.sid">selected='selected'</s:if>  value='<s:property value="#staffer.sid" />'><s:property value="#staffer.name" /></option>";
 </s:iterator>
	 sel=sel+"</select>";
	 $("#input_changTo").html(sel);
	
}

function  HideMember(){ 
  if (document.getElementById("m_person")) 
    $("#m_person").hide();     
}

function SubmitForm2(f){
 try{
      var url=window.opener.location.toString();
      if (url.toLowerCase().indexOf("myissue")>-1)
        window.opener.GetPageDelay(1);
 }catch(err){};
 SubmitForm(f); 
} 

var bLoadEditor=false;
function swichEditor(nType)
{
    $('#contentType').val(nType);
    if(nType==2)
    {
        $('#contentType2').addClass('current');
        $('#contentType1').removeClass('current');
        if (!bLoadEditor)
        {                
            editor1=CKEDITOR.replace( 'handleHtml',
            {
	            //extraPlugins : 'easybug',
	            toolbar : 'easybugbase',
	            width:530,
	            height:230
            }); 
            EasybugEditorIntance=editor1;
            bLoadEditor=true;
        }
        //if(!document.getElementById('handleHtml___Frame')) $('#handleHtml').after('<iframe id="handleHtml___Frame" src="/FCKeditor/editor/fckeditor.html?InstanceName=handleHtml&amp;Toolbar=ProjectGroupWinform" width="530" height="230" frameborder="no" scrolling="no"></iframe>');
        
        $('#handleContent').hide();
        $('#cke_handleHtml').show();
    }
    else
    {
        $('#contentType1').addClass('current');
        $('#contentType2').removeClass('current');
        $('#handleContent').show();
        $('#cke_handleHtml').hide();
    }
}
function CloseBug(){
var a="确定要强制关闭任务??";
if (!confirm(a))
 return; 
var id= $("#id").val();
 $.ajax({
    url:"issuehandle!closeIssue",
    data:{"id":id},
    success:function(data){
      window.location=window.location;
    
        } 
      }); 
}
function DeleteBug(id){
    $.post('issuehandle!deleteIssue',{id:id},function(d)
    {
        if (d=="true") {
            window.close();
        }
    }
    );
}
function cancleIssue(id){
	 $.post('issuecancle',{id:id},function(d)
			    {
			        if (d=="true") {
			            window.close();
			        }
			    }
			    );
}
</SCRIPT>
</head>
<body>

    <form id="_form" action="issuehandle!dealIssue" method="post"  enctype="multipart/form-data">
    <input name="id" id="id"  value="<s:property value="#request.aIssue.id" />" type="hidden">
    <!-- start of content -->
    <div class="Content">
        <div class="Right">
            <div class="Bugicon Midbox left">
                任务
                处理</div>
            <div class="clear">
            </div>
            <!--start of solution-->
            <div class="Solution Insert">
                
                <p class="createEdit">
                <a class="Blue edit" target="_blank" href="<%=basePath %>issueexpire?id=<s:property value="#request.aIssue.id" />">
                        延期</a>
                <a class="Blue edit" href="<%=basePath %>issueprint?id=<s:property value="#request.aIssue.id" />">
                        查看任务单</a>
                	<a class="Blue edit" href="<%=basePath %>issueadd?did=<s:property value="#request.department.did" />&id=<s:property value="#request.aIssue.id" />">
                        复制创建新任务</a>
                         <s:if test="#request.issueAuth.teamAdmin==true||#request.issueAuth.proAdmin==true">
                        <s:if test="#request.aIssue.completeStatus<3">
                        	<a class="Blue del" href="javascript:cancleIssue(<s:property value="#request.aIssue.id" />);" onclick="return confirm('确定要取消该任务?');">
                                取消</a>
                        </s:if>
                        
                </s:if>
                        <s:if test="#request.issueAuth.score==true">
                        <s:if test="#request.aIssue.completeStatus==3||#request.aIssue.completeStatus==4">
                        	 <a class="Blue edit" href="<%=basePath %>issuescore?id=<s:property value="#request.aIssue.id" />">
                            评分</a> 
                        </s:if>
                        
                </s:if>
                         <s:if test="#request.issueAuth.teamAdmin==true">
                        <s:if test="#request.aIssue.completeStatus<=2">
                        	 <a class="Blue edit" href="<%=basePath %>issuemod?id=<s:property value="#request.aIssue.id" />">
                            系数/日期调整</a> 
                        </s:if>
                        
                </s:if>
                        <s:if test="#request.issueAuth.admin==true">
                        <s:if test="#request.aIssue.completeStatus<=2">
                        	 <a class="Blue edit" href="<%=basePath %>issueedit?id=<s:property value="#request.aIssue.id" />">
                            修改</a> <a class="Blue del" href="javascript:DeleteBug(<s:property value="#request.aIssue.id" />);" onclick="return confirm('确定要删除该任务?');">
                                删除</a>
                        </s:if>
                        
                </s:if>
                    
                </p>
                
                    <p class="Gray box">
                        <label>
                           工作小组
                        </label>
                        <b>
                           <s:property value="#request.department.name" /></b></p>
                             <p class="Gray box">
                        <label>
                            工作模块</label>
                        <s:property value="#request.aIssue.modular.name" />
                    </p>
                    <p class="Gray box">
                        <label>
                            任务名称
                        </label>
                       <s:property value="#request.aIssue.title" /></p>
                    <p class="Gray box">
                        <label>
                            创建时间</label>
                       <s:date name="#request.aIssue.publishDate" format="yyyy-MM-dd HH:mm:ss"></s:date></p>
                         <p class="Gray box">
                        <label>
                            有效时间</label>
                        <s:date name="#request.aIssue.exprireDate" format="yyyy-MM-dd HH:mm:ss"></s:date></p>
                  <p class="Gray box" >
                        <label>
                            完成时间</label>
                       <s:if test="#request.aIssue.completeDate==null"><br></s:if>
                       		<s:else><s:date name="#request.aIssue.completeDate" format="yyyy-MM-dd HH:mm:ss"></s:date></s:else>
                       </p>
                      
                     <p class="Gray box">
                        <label>
                            分配人</label>
                         <s:property value="#request.aIssue.publisher.name" />
                    </p>
                    <p class="Gray box">
                        <label>
                            处理人</label>
                        <s:property value="#request.aIssue.completer.name" />
                    </p>
                
                     <p class="Gray box">
                        <label>
                            协调人</label>
                            <s:if test="#request.aIssue.coordinationer==null">
                             <br>
                            </s:if>
                            <s:else> <s:property  value="#request.aIssue.coordinationer" /></s:else>
                     
                    </p>
                       
                    <p class="Gray box">
                        <label>
                            优先级</label>
                       <s:if test="#request.aIssue.priority==5">严重</s:if>
                       <s:elseif test="#request.aIssue.priority==4">紧急</s:elseif>
                        <s:elseif test="#request.aIssue.priority==3">高</s:elseif>
                         <s:elseif test="#request.aIssue.priority==2">中</s:elseif>
                         <s:else>低</s:else>
                    </p>
                    <p class="Gray box">
                        <label>
                            状态</label>
                        <s:if test="#request.aIssue.completeStatus==1">未完成</s:if>
                       <s:elseif test="#request.aIssue.completeStatus==2">待审核</s:elseif>
                        <s:elseif test="#request.aIssue.completeStatus==3">已完成</s:elseif>
                         <s:elseif test="#request.aIssue.completeStatus==4">延期完成</s:elseif>
                             <s:elseif test="#request.aIssue.completeStatus==6">已取消</s:elseif>
                         <s:else>已评分</s:else>     
                        </p>
                 
                     <p class="Gray box">
                        <label>
                            是否保密</label>
                       <s:if test="#request.aIssue.secret==true">是</s:if>
                       <s:else>否</s:else>
                    </p>
                    <p class="Gray box">
                        <label>
                            描述与截图</label>
                        <span class="left">
                            </span>
                            <s:property escape="false" value="#request.issueWorkflows[0].content" />
                           
                        <br>
                    <p></p>
                    <div class="Gray box Detail clear">
                        <label>
                            处理过程</label>
                        <!--start of solution-->
                        <div class="Solution left">
                            
                            <table class="bugdetail box" border="0" cellpadding="10" cellspacing="0" width="100%">
                                
                                <tbody>
                                <s:iterator value="#request.issueWorkflows" var="issueWF" status="wf">
                                	<s:if test="#wf.index==0">
                                	 <tr class="title">
                                    <td width="12%">
                                        <span class="BlueBox">
                                            已分配
                                        </span>
                                    </td>
                                    <td width="20%">
                                        <s:property value="#issueWF.fromStaffer.name"/>
                                        →
                                        <s:property value="#issueWF.toStaffer.name"/>
                                    </td>
                                    <td class="">
                                      
                                    </td>
                                    <td width="14%">
                                       <s:date name="#issueWF.dealTime" format="yyyy-MM-dd HH:mm:ss"></s:date>
                                    </td>
                                </tr>
                                <s:if test="#issueWF.attachments.size()>0">
	                                <s:iterator value="#issueWF.attachments" var="attachment" status="at">
									<tr>
										<td></td><td colspan="3"><a href="<%=basePath %>issuehandle!getDownloadFile?UUIDName=${attachment.UUIDName }&realname=${attachment.realName }"><img src="<%=basePath %>Content/images/attachment.jpg"><font color=#42426F>${attachment.realName}</font></a></td>
									</tr>
									</s:iterator>
								</s:if>
								
                                 <tr class="arrow">
                                    <td colspan="4">
                                    </td>
                                </tr>
                                	</s:if>
                                	<s:else>
                                	<s:if test="#wf.last&&#issueWF.completeStatus>=3"><tr class="last"></s:if>
                                	<s:else><tr class=""></s:else>
                                    <td width="12%">
                                    <s:if test="#issueWF.completeStatus==1">
                                    	<span class="RedBox">
                                            未完成
                                        </span>
                                    </s:if>
                                    <s:elseif test="#issueWF.completeStatus==2">
                                    <span class="OrangeBox RedBox">
                                            待审核
                                        </span>
                                    </s:elseif>
                                     <s:elseif test="#issueWF.completeStatus==3">
                                     <span class="RedBox GreenBox">
                                            已完成
                                        </span>
                                     </s:elseif> 
                                     <s:elseif test="#issueWF.completeStatus==4">
                                      <span class="OrangeBox RedBox">
                                          延期完成
                                        </span>
                                     </s:elseif>  
                                    </td>
                                    <td width="20%">
                                    	<s:if test="#issueWF.completeStatus>=3">
                                    	<s:property value="#issueWF.fromStaffer.name"/>
                                    	</s:if>
                                    	<s:else>
                                    	 <s:property value="#issueWF.fromStaffer.name"/>
                                        →
                                        <s:property value="#issueWF.toStaffer.name"/>
                                    	</s:else>
                                       
                                    </td>
                                    <td class="reply">
                                       <s:property escape="false" value="#issueWF.content"/>
                                    </td>
                                    <td width="14%">
                                         <s:date name="#issueWF.dealTime" format="yyyy-MM-dd HH:mm:ss"></s:date>
                                    </td>
                               
                                
                                <s:if test="#issueWF.attachments.size()>0">
	                                <s:iterator value="#issueWF.attachments" var="attachment" status="at">
									<tr>
										<td></td><td colspan="2"><a href="<%=basePath %>issuehandle!getDownloadFile?UUIDName=${attachment.UUIDName }&realname=${attachment.realName }"><img src="<%=basePath %>Content/images/attachment.jpg"><font color=#42426F>${attachment.realName}</font></a></td>
									</tr>
									</s:iterator>
								</s:if>
                                
                                
                               <s:if test="!#wf.last">
                               	<tr class="arrow">
                                    <td colspan="4">
                                    </td>
                                </tr>
                               </s:if>
                               <s:elseif test="#issueWF.completeStatus<3">
                               	<tr class="arrow">
                                    <td colspan="4">
                                    </td>
                                </tr>
                               </s:elseif>
                                
                                	</s:else>
                                </s:iterator>
                               	<s:if test="#request.aIssue.completeStatus==1">
                               		   <tr class="last">
                                    <td>
                                    <span class="RedBox">未完成</span>
                                    </td>
                                    <td>
                                       <s:property value="#request.aIssue.completer.name"/>
                                    </td>
                                    <td class=""></td>
                                    <td></td>
                                </tr>
                               	</s:if>
                               	<s:elseif test="#request.aIssue.completeStatus==2">
                               		   <tr class="last">
                                    <td>
                                    <span class="OrangeBox RedBox">待审核</span>
                                    </td>
                                    <td>
                                       <s:property value="#request.aIssue.completer.name"/>
                                    </td>
                                    <td class=""></td>
                                    <td></td>
                                </tr>
                               	</s:elseif>
                             
                                
                                
                            </tbody></table>
                        </div>
                        <!--end of solution-->
                    </div>
                    <div class="clear">
                        <!-- -->
                    </div>
                    <br>
                    
  
  
  
                    <s:if test="#request.issueAuth.deal==true">
                    	<s:if test="#request.aIssue.completeStatus==1">
                    		  <p class="Gray box NoneBorder">
                        <label>状态</label>
                        <span class="icon_bigest">
                            <input onclick="HideMember();" name="status" checked="checked" value="2" type="radio">已解决
                        </span>
                        <input onclick="GetMember();" name="status" value="1" type="radio">转发
                        <span id="input_changTo"></span>
                    </p>
                    <div class="transform">
                        <ul>
                            <li id="contentType1" class="current"><a href="javascript:swichEditor(1);">
                                文本</a></li>
                            <li id="contentType2"><a href="javascript:swichEditor(2);">
                                HTML</a></li>
                            <li><span id="info" style="color: Red"></span></li>
                          
                        </ul>
                        <input id="contentType" name="contentType" value="1" type="hidden">
                        <p class="Gray box" style="margin-top: 1px;">
                            <label>
                                备注</label>
                            <textarea id="handleContent" style="height: 230px; width: 530px;" rows="3" name="content" cols="30"></textarea>                            
                        </p>
                        <p class="Gray box" style="margin-top: 1px; margin-left:110px;">
                         <textarea id="handleHtml" style="display: none;" rows="3" name="handleHtml" cols="30"></textarea>
                         </p>
                    </div>
                    	</s:if>
                  	<s:if test="#request.aIssue.completeStatus==2">
                  	<p class="Gray box NoneBorder">
                        <label>
                            状态</label>
                        <span class="icon_bigest">
                            <input checked="checked" name="status" value="3" onclick="HideMember();" type="radio">
                            已解决</span> <span class="icon_bigest">
                                <input name="status" value="0" onclick="HideMember();" type="radio">
                                未完成
                            </span>
                        <input onclick="GetMember();" name="status" value="1" type="radio">未修复并转发
                        <span id="input_changTo"></span>
                    </p>
                     <p class="Gray box">
                        <label>
                            备注</label>
                        <textarea rows="3" name="content" id="content" cols="30"></textarea>
                    </p>
                  	</s:if>
                    <s:if test="#request.aIssue.completeStatus<=2">
                   <!-- 
                   <p class="Gray box">
                    <label>
                        抄送人</label> </p>   
                  <div>
                  <table  width="80%" border="0.8px" height="15px"><tr><td>
 
					    <s:iterator value="#request.department.staffers" var="st" status="statusst">
							&nbsp;&nbsp;<s:property value="#st.name" /><input type="checkbox" name="listCheck" value="${st.sid}" />
						<s:if test="#statusst.count % 12== 0">
						<br>
						</s:if>	
                    	</s:iterator>
						</td></tr>
				   </table>
                    </div> 
                    --> 
                  
                    
                       <p class="Gray box">
	                        <label>上传附件(<100M)</label>
 						</p>

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
					
                    
					<br>
                    <span id="info" style="color: Red"></span>
 

                   		<br><br><br><br>
                   		
    			<div>
<p class="Gray box">
	                        <label>&nbsp;</label>
 						</p>

                        <a href="javascript:SubmitForm2('_form');" class=" Buttom  icon_bigest"><span>
                            提交</span></a> 

              </div>
                    </s:if>
                  
                    </s:if>
                    
                    
                    
                    

			
                    <s:elseif test="#request.issueAuth.admin==true">
                    <p class="Gray box right">
                        <label>
                            &nbsp;</label>
                        <a href="javascript:CloseBug();" class="Buttom"><span>
                            强制关闭该任务
                        </span></a>
                    </p>
                    </s:elseif>
                    
                    
                    <div class="clear">
                        <!-- -->
                    </div>
            </div>
            <!--end of solution-->
            <div class="left margintop10">
                <a href="javascript:window.close();" class=" Blue">
                    关闭窗口</a>
            </div>
        </div>
        <div class="clear">
            <!-- -->
        </div>
    </div>
    <!-- start of content -->
    <div class="clear">
        <!-- -->
    </div>
    <br>
    </form>
</body>
</html>