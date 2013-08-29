<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>团队编辑</title>
 <script type="text/javascript">
 $(document).ready(function(){
	$("#button1").click(function(){
		 var tid = $("#tid").val();
		var teamName = $("#teamName").val();
		var data = "teamName="+teamName+"&tid="+tid;
		var url = "<%= basePath%>teammod!modName";
		$.post(url,data,function(data){
	         if (data=='success')
	             //GetPage(m_nPageIndex);
	             alert("修改成功");
	         else
	             alert("操作失败"); 
	     }
	    );	
		});
	 
 });
 
 function DeleteTeamMember(mid)
 {
	 var tid = $("#tid").val();
  if ( !confirm("确定删除该成员?"))
  return;
 	var url = "<%= basePath%>teammod!delStaffer?sid="+mid+"&tid="+tid;
    $.post(url,function(data){
         if (data=='success')
             //GetPage(m_nPageIndex);
             $("#tr_"+mid).remove();
         else
             alert("操作失败"); 
     }
    );
 }
 
 function AddTeamMembers(){
	if(!check()) {
			return;
		};
	 $("#form1").submit();
	 }
 function check(){
	 if(!$("#newMembers").val()){
			return false;
		 }
	 	return true;
	 }
 function SetAdmin(mid){
	 var tip = "确定？";
   var val= $("#check_"+mid).attr("checked");
   	var flag = "";
   	if(!val) flag="checked";
     if ( !confirm(tip))
     {	
         $("#check_"+mid).attr("checked",flag);
             return;
             } 
     var tid = $("#tid").val();
      $.ajax({url:"teammod!setAdmin?sid="+mid+"&tid="+tid+"&flag="+flag,
       success:function(data){ 
       }
      });
   
 }
 </script>
</head>
<body>
      <!-- start of content -->
    <div class="Content">
        <div class="Right">
            <div class="Bugicon Midbox">
                团队信息
            </div>
            <!--start of solution-->
            <div class="Solution">
                <div class="OverviewBox clear box">
                    <label>
                        团队名称：
                    </label>
                   
                    <input class="input-w160" type="text" name="teamName" id="teamName" value="<s:property value="#request.team.name"/>"/>
                    <button id="button1">保存</button>
                    </div>
                <div class="clear">
                    <!-- -->
                </div>
            </div>
            <!--end of solution-->
                  
            
                     <!--start of solution-->
            <div class="Solution">
                <span id="page1">
<div class="PageTop">
     <b class="Black">
                        团队成员</b>
</div>
<table class="Bug box" border="0" cellpadding="10" cellspacing="0" width="100%">
    <tbody><tr class="BlueBackground Black BugTitle TableTop">
        <td width="17%">
            <b>
                昵称
            </b>
        </td>
        <td width="22%">
            <b>
                邮箱
            </b>
        </td>
        <td width="16%">
            <b>
                加入日期</b>
        </td>
        
        <td>
            操作
        </td>
        <td>
            管理员
        </td>
        
    </tr>
    <s:iterator value="#request.staffers" var="staffer">
    	 <tr id="tr_<s:property value="#staffer.sid"/>" class="GrayBackground" id="tr_18623">
        <td><s:property value="#staffer.name"></s:property></td>
        <td><s:property value="#staffer.email"></s:property></td>
        <td><s:date name="#staffer.generaTime" format="yyyy-MM-dd" /></td>
        
        <td>
            <a  href="javascript:DeleteTeamMember(<s:property value="#staffer.sid"/>)">
                <img src="<%=basePath %>Content/images/delete_icon.gif"></a>
        </td>
        <td>
            
            <input id="check_<s:property value="#staffer.sid"/>" onclick="SetAdmin(<s:property value="#staffer.sid"/>);" 
            <s:if test="#staffer.admin==true">checked="checked"</s:if>
            <s:if test="#staffer.creator==true">disabled="disabled"</s:if> type="checkbox">
        </td>
        
    </tr>
    </s:iterator>
 
</tbody></table>
</span>
                <div class="OverviewBox margintop10">
                    <label>
                        <img src="<%=basePath %>Content/images/person_icon.gif">
                        添加新成员
                    </label>
                     <form id="form1" action="<%=basePath%>teammod!addStaffer" 
                method="post">
                    <div class="left">
                        <div class="left">
                         <input id="tid" type="hidden" name="tid" value="<s:property value="#request.team.tid"/>"></input>
                            <textarea cols="25" rows="6" id="newMembers" name="newMembers"></textarea>
                        </div>
                        <div class="nametip DLine ">
                            <div class="mid">
                                添加新成员(输入Email,换行隔开)
                            </div>
                            <div class="btm">
                                <!-- -->
                            </div>
                        </div>
                        <br class=" clear">
                        <br>
                        <a href="javascript:AddTeamMembers();" class="Buttom icon_bigest "><span>
                            添加</span></a> <font color="red">
                                </font>
                    </div>
                    </form>
                </div>
                <br class=" clear">
                <div class="OverviewBox margintop10">
                    <label>
                        &nbsp;</label>
                    <div class="left" style="color: Red;" id="newMailsInfo">
                    </div>
                </div>
                <div class="clear">
                    <!-- -->
                </div>
            </div>
            
        </div>
        <div class="clear">
            <!-- -->
        </div>
    </div>
    <!-- start of content -->
</body>
</html>