<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>

</head>
<script type="text/javascript">
function SelectAll()
{
   var sl=document.getElementById('sr');
   var l=sl.options.length;
   for(var i=0;i<l;i++)
   {
       sl.options[i].selected=true;   
   }
  
}

function SelectAdd()
{ 
  var sl=document.getElementById("sl");
  var l=sl.options.length;
   for(var i=0;i<l;i++)
   { 
     if (sl.options[i].selected)
     {  
       var temp=sl.options[i]; 
        if (!IsAdd(temp.value))
       document.getElementById("sr").options.add(new Option(temp.text,temp.value)); 
     }
   }
}

function IsAdd(value)
{
  var sr=document.getElementById("sr");
   var l=sr.options.length;
   for(var i=0;i<l;i++)
   {
     if (sr.options[i].value==value)
      return true;
   }
   return false;
}

function SelectDel()
{ 
$("#sr option").each(function(i){if ($(this).attr("selected") ) $(this).remove(); })
}
var titlenull="小组名称不能为空";
function SubmitForm2(m)
{
   
  if ($("#projectName").val().trim()=="") 
   {
   alert(titlenull);
   return;
   }
   SelectAll();
   $("#"+m).submit();
}
</script>
<body>
   <!-- start of content -->
    <div class="Content">
        <div class="Right">
            <div class="Bugicon Midbox">
                创建新小组
            </div>
            <!--start of solution-->
            <div class="Solution">
                
                <form id="_form" action="<%=basePath %>projectmod!add"  method="post">
                <input value="<s:property value="#request.team.tid"></s:property>" name="tid" id="tid" type="hidden">
                <br>
                <div class="OverviewBox clear box">
                    <label>
                        当前团队</label>
                    <input id="t_name" name="t_name" value="<s:property value="#request.team.name"></s:property>" disabled="disabled"></input>
                </div>
                <div class="OverviewBox clear box">
                    <label>
                        小组名称<font color="red">*</font>
                    </label>
                    <input class="input-w160" name="projectName" id="projectName" type="text">
                    <font color="red">
                        </font></div>
                <div class="OverviewBox clear box">
                    <label>
                        从现有人员选择</label>
                    <div class="left">
                        <p class="Smallbox Black">
                            团队成员</p>
                        <select id="sl" class="input-w160" multiple="multiple" style="height: 150px;">
                           <s:iterator value="#request.team.staffers" var="staffer">
                            <option value="<s:property value="#staffer.sid"></s:property>">
                                <s:property value="#staffer.name"></s:property>
                            </option>
                            </s:iterator>
                        </select>
                    </div>
                    <div class="left Two-way">
                        <a href="javascript:SelectAdd();" class="Buttom box"><span>
                            添加
                            &gt;</span></a>
                        <div class="clear">
                            <!-- -->
                        </div>
                        <a href="javascript:SelectDel();" class="Buttom"><span>&lt;
                            删除</span></a>
                    </div>
                    <div class="left">
                        <p class="Smallbox Green">
                            已选成员</p>
                        <select id="sr" class="input-w160" name="selectMembers" multiple="multiple" style="height: 150px;">
                            <option value="<s:property value="#session.user.sid"></s:property>" disabled="disabled">
                                <s:property value="#session.user.name"></s:property>
                            </option>
                        </select>
                    </div>
                </div>
                <div class="OverviewBox clear box">
                    <label>
                        添加新成员</label>
                    <div class="left">
                        <div class="left">
                            <textarea cols="25" rows="6" name="newMembers"></textarea>
                        </div>
                        <div class="nametip DLine ">
                            <div class="mid">
                                添加新成员(输入Email,换行隔开)
                            </div>
                            <div class="btm">
                                <!-- -->
                            </div>
                        </div>
                        <br class="clear">
                        <br>

                        <a href="javascript:SubmitForm2('_form');" class="Buttom icon_bigest"><span>
                            创建</span></a> <a href="javascript:history.go(-1);" class="Buttom icon_bigest"><span>
                                    返回</span></a>
                    </div>
                </div>
                </form>
                
                <div class="clear">
                    <!-- -->
                </div>
            </div>
            <!--end of solution-->
        </div>
        <div class="clear">
            <!-- -->
        </div>
    </div>
</body>
</html>