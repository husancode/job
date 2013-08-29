<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header3.jsp" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>


<head>
<script type="text/javascript" src="calender/WdatePicker.js"></script>
<script type="text/javascript">
var did = <s:property value="#request.department.did"/>;

var SaveSuccessfully="保存成功"; 
var SaveFirst='请先保存'; 
var CheckInput='不能有特殊字符';
var AreYouSure='确定??';
var Loading='加载中...... ';
var Save="保存";
var CannotDelete='不能删除';
var Success="成功";
var Failure="失败";
var Cancel="取消";

function OrderByThis(a) {
    var order = $("#OrderBy").val();
    if (order.indexOf(a)<0)
        $("#OrderBy").val(a + ":asc");
    else if(order.indexOf("asc")>0)
    	$("#OrderBy").val(a + ":desc");
    else   $("#OrderBy").val(a+":asc");
    document.getElementById('_form').submit();
}
function SubForm(obj) {
	if($(obj).val() == 0){
		alert("请选择小组");
		return false;
		}
    document.getElementById('_form').submit();
}


function AddIssue()
{ 
  if (document.getElementById("input_m"))
  {
    alert(SaveFirst);
    return;
   }
  if ( document.getElementById("add_li2")) return; 
  		var  selModular = "<select id='modular' name='modular'>";
  		selModular = selModular + $("#modulars").html();
  		selModular = selModular +  "</select>";
  		
  		var completer = "<select id='completer' name='completer'>";
  		completer = completer + $("#staffer").html();
  		completer = completer + "</select>";
  		var coord = "<input id='coord' name='coord' type='text' size='12'>";
  		coord = coord + "</input>";
       $("#ul_moudle").append("<tr id='add_li2'><td> "+selModular+"</td><td><input size='15' id='subject' type='text' /></td><td><input size='20' id='title' type='text' /></td><td><textarea  size='30' id='content' ></textarea></td><td> "+coord+"</td><td> "+completer+"</td><td><input name='expiredate' id='expiredate' type='text' size='20'  onFocus=\"WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})\" ></input></td><td><a class='Blue' href='javascript:AddP();'> "+Save+"</a> <a class='Blue' href='javascript:Cancle_AddP();'>"+Cancel+"</a></td></tr>");
       //var modularName = $("#add_li2").prev().children("td").eq(0).html();
        
}
function Cancle_AddP()
{
 $("#add_li2").remove();
}
function AddP()
{
  var modular=$("#modular").val();
  var title = $("#title").val().trim();
  var content = $("#content").val();
 
  var completer = $("#completer").val();
  var coord = $("#coord").val();
  var expireDate = $("#expiredate").val();
  var subject = $("#subject").val();	
  var s1= document.getElementById("add_li2").getElementsByTagName("select")[0];
  var modularName= s1.options[s1.selectedIndex].text;    
  var s2 = document.getElementById("add_li2").getElementsByTagName("select")[1];
  var completerName= s2.options[s2.selectedIndex].text;
 
  var coordName=coord;
  
  if (title=="") return;
  $.ajax({
       url:"issueadd2!add", 
       type:"POST",
       data:"modular="+modular+"&title="+title+"&completer="+completer+"&coord="+coord+"&expireDate="+expireDate+"&did="+did+"&subject="+subject+"&content="+content,
       success:function(data){
           var id=data; 
            $("#add_li2").remove();
            var s="<tr id='li_m_"+id+"' class='GrayBackground'><td>"+modularName+"</td><td>"+subject+"</td><td>"+title+"</td><td>"+content+"</td><td>"+coordName+"</td><td>"+completerName+"</td><td>"+expireDate+"</td></tr>";
            //更新mudular的select
            var options = $("#modulars").children("option");
            var newSel = "";
           	for(var i=0;i<options.length;i++){
               	var option = options.eq(i);
               	if(option.text().trim()==modularName){
               		newSel=newSel+"<option value='"+option.attr("value")+"' selected='selected'>"+option.text().trim()+"</option>"
                   	//$(option).attr("selected","selected");
                	
                   	}else{
                   		//$(option).removeAttr("selected");
                   		newSel=newSel+"<option value='"+option.attr("value")+"'>"+option.text().trim()+"</option>"
                   		
                       	}
               	}
           	
           	$("#modulars").html(newSel);
            $("#ul_moudle").append(s);
         }, error:function(){ alert(CheckInput);} 
       });
   }
</script>
</head>
<script type="text/javascript" src="calender/WdatePicker.js"></script>
<body>
<!-- start of content -->
   <div class="Content">
        <div class="Right">
            <div class="Bugicon Midbox">
                提交任务</div>
            <!--start of solution-->
            <div class="Solution ">
          
             <select id="modulars" style="display:none">
            	<s:iterator value="#request.modulars" var="modular">
            		
                        	<option value="<s:property value="#modular.id"  />" >
                               <s:property value="#modular.name" /></option>
            	</s:iterator>
            </select>
             <select id="staffer" style="display:none">
            	<s:iterator value="#request.department.staffers" var="staffer">
            		
                        	<option value="<s:property value="#staffer.sid"  />" >
                               <s:property value="#staffer.name" /></option>
            	</s:iterator>
            </select>
            <form id="_form" action="<%=basePath %>issueadd2"   method="post">
            <input type="hidden" name="tid" value="<s:property value="#request.tid"/>">
           
            
            	  <table>
                    <tbody><tr>
                        <td>
                            <b class="BiggestFont Black">
                                工作小组:</b>
                        </td>
                        <td>
                             <select name="did" onchange="SubForm(this);" class="SearchSelect left">
                             <option value='0'>选择小组</option>
                        <s:iterator value="#request.team.departments" var="department">
                       
                        	<option value="<s:property value="#department.did"  />" <s:if test="#request.department.did==#department.did">selected='seleted'</s:if>>
                               <s:property value="#department.name" /></option>
                        </s:iterator>
                        </select>
                        </td>
                        <td><input id="did" value="<s:property value="#request.department.did"/>" type="hidden"></input></td>
                       
                    </tr>
                </tbody></table>
            </form>
              
            </div>
            <!--end of solution-->
        
            <!--start of solution-->
            <div class="Solution">
                <div class="BlueBorder_btm">
                    <!-- -->
                </div>
                <table id="ul_moudle" class="Bug box" border="0" cellpadding="10" cellspacing="0" width="100%">
                    <tbody><tr class="BlueBackground Black BugTitle TableTop">
                        <td>
                            <b>
                                模块</b>
                        </td>
                         <td>
                            <b>
                                工作项目</b>
                        </td>
                        <td>
                            <b>
                                标题</b>
                        </td>
                        <td>
                            <b>
                              具体要求</b>
                        </td>
                        <td>协调人</td>
                        <td>责任人</td>
                        <td>
                         	计划完成时间
                        </td>
                       	<td></td>
                    </tr>
                    <!-- <s:iterator value="#request.department.modualrs" var="modular">
                      <tr class="GrayBackground" id="li_m_<s:property value="#modular.id"/>">
                        <td><s:property value="#modular.name"/></td>
                        <td><s:property value="#modular.responsibleStaffer.name"/></td>
                        <td><s:property value="#modular.percentage"/></td>
                        <td><s:property value="#modular.score"/></td>
                        <td>
                            <a href="javascript:EditMD(<s:property value="#modular.id"/>)" class="Blue">
                                <img src="<%=basePath %>Content/images/pencil.gif"></a>
                        </td>
                       
                    </tr>
                    </s:iterator> -->
                   
                    
                </tbody></table>
                <a href="javascript:AddIssue();" class="Buttom  "><span>
                    <img src="<%=basePath %>Content/images/add_icon.gif">
                    添加</span></a>
            </div>
            <!--end of solution-->
            <div class="clear">
            </div>
          
        </div>
        <div class="clear">
            <!-- -->
        </div>
    </div>
</body>
</html>