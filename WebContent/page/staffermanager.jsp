<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" media="screen" href="<%=basePath %>jqueryui/redmond/jquery-ui-1.10.1.custom.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%=basePath %>jqueryui/redmond/jquery-ui.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%=basePath %>jqGrid/css/ui.jqgrid.css" />

<script src="<%=basePath %>Scripts/jquery-1.5.1.min.js" type="text/javascript"></script>
<script src="<%=basePath %>Scripts/jquery-ui-1.8.14.custom.min.js" type="text/javascript"></script>	
<script src="<%=basePath %>jqGrid/js/i18n/grid.locale-cn.js" type="text/javascript"></script>
<script src="<%=basePath %>jqGrid/js/jquery.jqGrid.min.js" type="text/javascript"></script>	
<script type="text/javascript">
function initialTable(gridid,url,dataParm,editurl,pager){		
	$(gridid).jqGrid({
   		url:url,
   		loadComplete:setbackground,
   		postData:dataParm,
		datatype: "json",
		mtype:"post",
		autowidth:true,
		height: 400,
		viewrecords: true,
   		colNames:["编号","登录名","姓名","部门","职位","手机号","创建日期","操作"],
   		colModel:[
				{name:'sid',index:'sid',width:40,key:true,editable:false,search:true,searchoptions:{sopt:['eq','lt','le','gt','ge']}},
				{name:'email',index:'email',width:70,sortable:true,editable:true,editrules:{required:true}},
				{name:'name',index:'name',width:70,sortable:true,editable:true,editrules:{required:true}},
				{name:'departments',index:'departments',sortable:true,editable:true,edittype:'select',editoptions:{value:eval($("#dept").val())}},
				{name:'position',index:'position',sortable:false,editable:true},
   				{name:'telphone',index:'telphone',sortable:false,editable:true,editrules:{number:true}},
   				{name:'generaTime',index:'generaTime',sortable:true,editable:false,formatter:'date',formatoptions: {newformat:'Y-m-d'},searchoptions:{sopt:['eq','lt','le','gt','ge']}},
   				{name:'act',index:'act',sortable:false,search:false}
   				],
   		jsonReader : {   
       		root:"gridModel",   
        	records: "records",
        	repeatitems : false
    	},
    	sortname : "generaTime",
        sortorder: "asc",   
    	multiselect:false,
   		rowNum:20,
   		rowList:[10,20,30],
   		pager: pager,
   		ajaxSelectOptions:{async:false},
   		gridComplete: function(){ 
   	   		var ids = jQuery("#list2").jqGrid('getDataIDs'); 
   	   		for(var i=0;i < ids.length;i++){ 
   	   	   	var cl = ids[i]; 
   	   	   	be="";
   	   	   	se="";
   	   		
   		 		be = "<input style='height:22px;width:40px;' type='button' value='修改' onclick=\"jQuery('#list2').jqGrid('editGridRow','"+cl+"',{top:100,left:400,height:280,reloadAfterSubmit:true,closeAfterEdit:true,editCaption:'编辑用户'});\" />"; 
   				se = "<input style='height:22px;width:40px;' type='button' value='删除' onclick=\"jQuery('#list2').jqGrid('delGridRow','"+cl+"',{top:100,left:400,height:100,reloadAfterSubmit:true});\" />"; 
			
			ce = "";
			
				ce = "<input style='height:22px;width:40px;' type='button' value='授权' onclick=\"goToAuth("+cl+")\" />";
			
   	   		jQuery("#list2").jqGrid('setRowData',ids[i],{act:be+se+ce}); } },
   			editurl:editurl,
   			caption: "用户列表"
		}).jqGrid("navGrid",pager,
   		    {edit:false,add:false,del:false,refresh:false,searchtext:'查询'},{},{},{},{multipleSearch:true}
		);
 		
 		$(gridid).navButtonAdd(pager, {
			buttonicon:"ui-icon-add",
			caption : "添加用户",
			title : "添加用户",
			onClickButton : addStaffer
		});
	
}
function setbackground(){}
function addStaffer(){
	jQuery("#list2").jqGrid('editGridRow',"new",{top:100,left:400,height:280,reloadAfterSubmit:true,closeAfterAdd:true,editCaption:'添加用户'});
	
}
function Collaboration(){}

function goToAuth(id){
	var url = "auth!authAllot?sid="+id;
	window.location = url;
}

$(document).ready(function(){
	mainWidth = document.documentElement.clientWidth-50;
	initialTable("#list2","staffermanager!authStafferView",{},"stafferedit!authStafferEdit","#pager2");
});
</script>
</head>

<body>
<div class="Content" >
	<div class="Right">
      	<div class="Bugicon Midbox">用户管理</div>
            <div class="clear">
            </div>
            <input type="hidden" id="dept" value="<s:property value="#request.dept" />"></input>
            <div class="Solution">
			<table id="list2"></table>
			<div id="pager2"></div>
			</div>
		</div>
	</div>
</body>
</html>