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

</script>
<body>
 <!-- start of subheader-->
<div class="SubHeader">
    <span class="left">
        
        <img src="<%=basePath %>Content/images/yft_logo.jpg" class="BlueBorder icon_biger user-logo-define">
        
        <select id="s_prj" class="Select" onchange="ChangePrj();">
            <s:iterator value="#request.departments" var="de">
            	 <option value="<s:property value="#de.did"/>" <s:if test="#de.did==#request.department.did">selected="selected"</s:if>>
                <s:property value="#de.name"/></option>
            </s:iterator>
        </select>
    </span>

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
    function ChangePrj()
    { 
      var url=window.location.toString();
      
      if (url.toLowerCase().indexOf("did=")>0)
      {
        url=url.substring(0,url.lastIndexOf("did="));  
        window.location= url+"did="+document.getElementById('s_prj').value;
      
      }
     
    }

    function SaveBaseInfo()
    {
      
       var name=$("#pname_input").val().trim();
        if (name.trim()=="")
       {  
        $("#baseinfo").html("小组名称不能为空");
        return;
       } 
       $("#baseinfo").html(Loading);
       $.ajax({
                   url:"projectmod!modName", 
                   type:"POST", 
                   data:"projectName="+name+"&did="+did,
                   success:function(data){
                   $("#baseinfo").html(SaveSuccessfully);
                   }
              });
                    
    }
     //-------------Select-------------- 
   
    
    function SelectAdd()
    {    
    	
        var ns=new Array(); 
         $("#sl option").each(function(){
            
              if ($(this).attr("selected"))
               {
                var v=$(this).val(); 
               
                if (!IsAdd(v))ns.push(v);
                }            
         });
         if(ns.length>0)
         {  
             $("#m_info").html(Loading);
             $.ajax({
                        url:"projectmember!add", 
                        type:"POST", 
                        data:"ids="+ns+"&did="+did,
                        dataType:"json",
                        success:function(data){ 
                 			var len = data.result.length;
                          if(len>0){
                             	
                              	for(var i=0;i<len;i++){
                                  	var name = $("#sl option[value='"+data.result[i]+"']").text();
                                  	$("#sr").append("<option value='"+data.result[i]+"'>"+name+"</option>");
                                  	}
                              	
                              	$("#m_info").html(Success);
                              } 
                           else $("#m_info").html(Failure);
                           } 
                        });
         }
    }
     
    function SelectDel()
    {
      
        var ns=new Array(); 
         $("#sr option").each(function(i){  
             if ( $(this).attr("selected") )
             { 
               var v=$(this).val();
              ns.push(v);  
             }
         })
       
       if(ns.length>0)
         {  
             $("#m_info").html(Loading);
             $.ajax({
                        url:"projectmember!del", 
                        type:"POST", 
                        cache:false,
                        data:"ids="+ns+"&did="+did,
                       
                        success:function(data){
            	 		if(data=='success'){
                	 		//var nn = new Array();
            	 			 $("#sr option").each(function(i){  
            	 	             if ( $(this).attr("selected") )
            	 	             { 
            	 	            	var dd= $(this).val();
            	 	            	$(this).remove();
            	 	            	$("#sr2 option").each(function(i){
                	 	            	
                	 	            		if(dd==$(this).val())
                    	 	            		$(this).remove();
                	 	            	});
            	 	            	
            	 	             }
            	 	         });
            	 			
            	 			$("#m_info").html(Success);
                	 		}
                         else $("#m_info").html(Failure);
                           } 
                        });
         }
    }


    function AdminAdd()
    {    
    	
        var ns=new Array(); 
         $("#sr option").each(function(){
              if ($(this).attr("selected"))
               {
                var v=$(this).val(); 
                if (!IsAdd(v,"sr2"))ns.push(v);
                }            
         });
         if(ns.length>0)
         {  
             $("#m_info2").html(Loading);
             $.ajax({
                        url:"projectmember!addAdmin", 
                        type:"POST", 
                        data:"ids="+ns+"&did="+did,
                        success:function(data){
            	 $("#sr option").each(function(i){  
	 	             if ( $(this).attr("selected") )
	 	             { 
		 	            var v = $(this).val();
	 	            	if(!IsAdd(v,"sr2")) 
		 	            	$("#sr2").append("<option value='"+v+"'>"+$(this).text()+"</option>");
	 	             }
	 	         });
            	 		$("#m_info2").html(Success);
                           } 
                        });
         }
    }

    function AdminDel()
    {   
    	
        var ns=new Array(); 
         $("#sr2 option").each(function(i){  
             if ( $(this).attr("selected") )
             { 
               var v=$(this).val();
               ns.push(v);  
             }
         });
       if(ns.length>0)
         {  
             $("#m_info2").html(Loading);
             $.ajax({
                        url:"projectmember!delAdmin", 
                        type:"POST", 
                        cache:false,
                        data:"ids="+ns+"&did="+did,
                        success:function(data){  
            	 		$("#sr2 option").each(function(i){  
	 	             		if ( $(this).attr("selected") )
	 	            			$(this).remove();
                           });
            	 		$("#m_info2").html(Success);
             }
                        });
         }
    }
    
      function IsAdd(value,sr)
    { 
    if (sr==null || sr==undefined) sr="sr";
      var flag=false;
      $("#" + sr + " option").each(function(){ if ($(this).val()==value) flag=true; });
      return flag;
    }
    
    function SelectItem(id,value)
    {
        var temp=document.getElementById(id);
        var len=temp.options.length;
        for (var i=0;i<len;i++)
        {
        if (temp.options[i].value==value)
        { temp.options[i].selected=true; break;}
        }
    }

    //
     function AddMemberToProject()
    { 
    	
        var emails=$("#newMembers").val().replace(/\n/g,','); 
        if (emails.trim()=="")
         { 
           $("#newMailsInfo").html(isnull);
           return;
         } 
         $("#newMailsInfo").html(Loading);
          $.ajax({
              url:"projectmember!addNewMember",type:"post",data:"did="+did+"&newMembers="+emails,dataType:"json",
              success:function(data){   
                  var len = data.staffers.length;
                  for(var i=0;i<len;i++){
                        var a = data.staffers[i].sid;
                        var b = data.staffers[i].name;
                        if(!IsAdd(a,"sl")) 
		 	            	$("#sl").append("<option value='"+a+"'>"+b+"</option>");
                        if(!IsAdd(a,"sr"))
                        	$("#sr").append("<option value='"+a+"'>"+b+"</option>");
                      }
                  $("#newMailsInfo").html(Success);
                  $("#newMembers").val("");
              }
             }); 
    }

     //------------------------Moudle-----------------  
     function AddMoudle()
     { 
       if (document.getElementById("input_m"))
       {
         alert(SaveFirst);
         return;
        }
       if ( document.getElementById("add_li2")) return; 
          	var selOption = "<select id='m_person' name='m_person'>";
              $("#sr option").each(function(){
            	  selOption = selOption+"<option value='"+$(this).val()+"'>"+$(this).text()+"</option>";
                  });
              selOption = selOption+ "</select>";
            $("#ul_moudle").append("<tr id='add_li2'><td><input size='8' id='m_name' type='text' /></td><td> "+selOption+"</td><td><input size='4' id='m_percentage' value='100' type='text' /></td><td><input size='4' id='m_score' value='5' type='text' /></td><td colspan='2'><a class='Blue' href='javascript:AddP();'> "+Save+"</a> <a class='Blue' href='javascript:Cancle_AddP();'>"+Cancel+"</a></td></tr>");
             
     }
     function Cancle_AddP()
     {
      $("#add_li2").remove();
     }
     function DelMD(id)
     {
       if (!confirm(AreYouSure))
         return; 
          $.ajax({
            url:"module!del?mid="+id+"&did="+did,
            success:function(data){
                if (data.toLowerCase()=='success')
                $("#li_m_"+id+"").remove();
                else
                  alert(CannotDelete);
              } 
            }); 
     }
     var tempmd;
     function EditMD(id)
     {
      if (document.getElementById("input_m")||document.getElementById("m_name"))
       {
         alert(SaveFirst);
         return;
        }
        tempmd=$("#li_m_"+id+"").html();  
        var mid=$("#li_m_"+id+" td:eq(1)").html().trim(); 
        var name= $("#li_m_"+id+" td:first").html().trim();
        var percentage=$("#li_m_"+id+" td:eq(2)").html().trim();
        var score=$("#li_m_"+id+" td:eq(3)").html().trim(); 
        $("#li_m_"+id+" td:first").html("<input size='8' id='input_m' value='"+name+"' type='text' />");
        var selOption = "<select id='m_person' name='m_person'>";
        $("#sr option").each(function(){
      	  selOption = selOption+"<option value='"+$(this).val()+"'>"+$(this).text()+"</option>";
            });
        selOption = selOption+ "</select>";
        $("#li_m_"+id+" td:eq(1)").html(selOption);
        $("#li_m_"+id+" td:eq(2)").html("<input size='4' id='m_percentage' value='"+percentage+"' type='text' />");
        $("#li_m_"+id+" td:eq(3)").html("<input size='4' id='m_score' value='"+score+"' type='text' /><a class='Blue' href='javascript:UpdateMD("+id+");'>  "+Save+"</a>  <a class='Blue' href='javascript:Cancle_UpdateMD("+id+");' > "+Cancel+"</a>");
        $("#li_m_"+id+"  option").each(function(i){ 
          ($(this).html().trim()==mid)?$(this).attr("selected","selected"):"";}
        );
  
     }
     function Cancle_UpdateMD(id)
     {
       $("#li_m_"+id+"").html(tempmd);
     }
     
     function UpdateMD(id)
     {
       var newName=$("#input_m").val().trim();
       if (newName=="")
        return;
       var responsible=$("#m_person").val();
        var temp= document.getElementById("m_person");
        var percentage = $("#m_percentage").val().trim();
        var score = $("#m_score").val().trim();
       var mpt= temp.options[temp.selectedIndex].text;  
       $.ajax({
            url:"module!update", 
            type:"POST",
            data:"responsible="+responsible+"&name="+newName+"&mid="+id+"&percentage="+percentage+"&score="+score+"&did="+did,
            success:function(data){
              if (data=='success') 
                {
                  $("#li_m_"+id+" td:first").html(newName);
                  $("#li_m_"+id+" td:eq(1)").html(mpt); 
                  $("#li_m_"+id+" td:eq(2)").html(percentage);
                  $("#li_m_"+id+" td:eq(3)").html(score);
                }
             } , error:function(){ alert(CheckInput);}
            });                   
     }
     function AddP()
     {
       
       var name=$("#m_name").val().trim(); 
       var mid=$("#m_person").val(); 
       var percentage = $("#m_percentage").val();
       var score = $("#m_score").val();
       var temp= document.getElementById("add_li2").getElementsByTagName("select")[0];
       var mpt= temp.options[temp.selectedIndex].text;     
       if (name=="") return;
       $.ajax({
            url:"module!add", 
            type:"POST",
            data:"name="+name+"&responsible="+mid+"&did="+did+"&percentage="+percentage+"&score="+score,
            success:function(data){
                var id=data; 
                 $("#add_li2").remove();
                 var s="<tr id='li_m_"+id+"' class='GrayBackground'><td>"+name+"</td><td>"+mpt+"</td><td>"+percentage+"</td><td>"+score+"</td>";
                 s+="<td><a  href='javascript:EditMD("+id+")' ><img src='<%=basePath %>Content/images/pencil.gif'></a></td><td><a  href='javascript:DelMD("+id+")'> <img src='<%=basePath %>Content/images/delete_icon.gif'></a> </td></tr>";
                 $("#ul_moudle").append(s);
              }, error:function(){ alert(CheckInput);} 
            });
        }
     
    </script>

    <ul class="Menu">
        
        <li><a href="<%=basePath %>projectinfo?did=<s:property value="#request.department.did"/>" class="menu1 ">
            小组概况</a></li>
        <li><a href="<%=basePath %>tomeissue?did=<s:property value="#request.department.did"/>" class="menu2 ">
            分配给我的任务</a></li>
        <li><a href="<%=basePath %>myissue?did=<s:property value="#request.department.did"/>" class="menu3 ">
            我创建的任务</a></li>
        <li><a href="<%=basePath %>allissue?did=<s:property value="#request.department.did"/>" class="menu4 ">
            所有任务</a></li>
        <li><a href="<%=basePath %>issue_stat?did=<s:property value="#request.department.did"/>" class="menu5 ">
            任务统计</a></li>
        
    </ul>
</div>
<!-- end of subheader-->

   <!-- start of content -->
    <div class="Content">
        <div class="Right">
            <div class="Bugicon Midbox">
                小组信息</div>
            <!--start of solution-->
            <div class="Solution ">
                <table>
                    <tbody><tr>
                        <td>
                            <b class="BiggestFont Black">
                                小组名称:</b>
                        </td>
                        <td>
                            <input id="pname_input" value="<s:property value="#request.department.name"/>" style="width: 250px" type="text">
                        </td>
                        <td><input id="did" value="<s:property value="#request.department.did"/>" type="hidden"></input></td>
                         <td style="text-align: center">
                            <a href="javascript:SaveBaseInfo();" class="Buttom"><span>
                                保存</span></a> <span style="color: Red" id="baseinfo">
                                </span>
                        </td>
                    </tr>
                </tbody></table>
            </div>
            <!--end of solution-->
        
            <!--start of solution-->
            <div class="Solution ScaleBox left">
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
                                处理人</b>
                        </td>
                        <td>权重(%)</td>
                        <td>评分</td>
                        <td>
                            修改
                        </td>
                        <td>
                            删除
                        </td>
                    </tr>
                    <s:iterator value="#request.department.modualrs" var="modular">
                      <tr class="GrayBackground" id="li_m_<s:property value="#modular.id"/>">
                        <td><s:property value="#modular.name"/></td>
                        <td><s:property value="#modular.responsibleStaffer.name"/></td>
                        <td><s:property value="#modular.percentage"/></td>
                        <td><s:property value="#modular.score"/></td>
                        <td>
                            <a href="javascript:EditMD(<s:property value="#modular.id"/>)" class="Blue">
                                <img src="<%=basePath %>Content/images/pencil.gif"></a>
                        </td>
                        <td>
                            <a href="javascript:DelMD(<s:property value="#modular.id"/>)" class=" Blue">
                                <img src="<%=basePath %>Content/images/delete_icon.gif"></a>
                        </td>
                    </tr>
                    </s:iterator>
        
                    
                    
                </tbody></table>
                <a href="javascript:AddMoudle();" class="Buttom  "><span>
                    <img src="<%=basePath %>Content/images/add_icon.gif">
                    添加</span></a>
            </div>
            <!--end of solution-->
            <div class="clear">
            </div>
            <!--start of solution-->
            <div class="Solution ">
                <div class="BlueBackground MLine Negative">
                    <b class="Black">
                        小组人员</b></div>
                <span id="page1">
<div class="left">
    <p class="Smallbox Black">
        团队成员</p>
    <select id="sl" class="input-w160" multiple="multiple" style="height: 150px;">
        <s:iterator value="#request.teamStaffers" var="tStaffer">
        	 <option value="<s:property value="#tStaffer.sid"/>">
           <s:property value="#tStaffer.name"/>
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
        小组成员</p>
    <select id="sr" class="input-w160" name="selectMembers" multiple="multiple" style="height: 150px;">
        <s:iterator value="#request.stafferVOs" var="staffer">
        	<option value="<s:property value="#staffer.sid"/>" <s:if test="#staffer.creator==true">disabled="disabled"</s:if>>
            <s:property value="#staffer.name"/>
        </option>
        </s:iterator>
    </select>
    <br>
    <span id="m_info" style="color: Red"></span>
</div>


<div class="left Two-way">
    <a href="javascript:AdminAdd();" class="Buttom box"><span>
        添加
        &gt;</span></a>
    <div class="clear">
        <!-- -->
    </div>
    <a href="javascript:AdminDel();" class="Buttom"><span>&lt;
        删除</span></a>
</div>
<div class="left">
    <p class="Smallbox Green">
        小组主管</p>
    <select id="sr2" class="input-w160" name="selectMembers" multiple="multiple" style="height: 150px;">
        <s:iterator value="#request.stafferVOs" var="staffer">
        	<s:if test="#staffer.admin==true"><option value="<s:property value="#staffer.sid"/>" <s:if test="#staffer.creator==true">disabled="disabled"</s:if>>
            <s:property value="#staffer.name"/>
        </option></s:if>
        </s:iterator>
    </select>
    <br>
    <span id="m_info2" style="color: Red"></span>
</div>
</span>
                <br class="clear">
                <div class="OverviewBox margintop10">
                    <img src="<%=basePath %>Content/images/person_icon.gif">
                    添加小组成员
                    <p>
                    </p>
                    <div class="left">
                        <div class="left">
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
                        <a href="javascript:AddMemberToProject();" class="Buttom icon_bigest "><span>
                            添加</span></a> <a class="Buttom icon_bigest" href="javascript:history.go(-1);">
                                <span>
                                    返回</span></a> <font color="red">
                                        </font>
                    </div>
                </div>
                <br class=" clear">
                <div class="OverviewBox margintop10">
                    <div class="left" style="color: Red;" id="newMailsInfo">
                    </div>
                </div>
                <!--end of page -->
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
    <!-- start of content --> 
    <div class="clear">
        <!-- -->
    </div>
    
</body>

</html>