<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header2.jsp" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
function OrderByThis(a) {
    var order = $("#OrderBy").val();
    if (order.indexOf(a)<0)
        $("#OrderBy").val(a + ":asc");
    else if(order.indexOf("asc")>0)
    	$("#OrderBy").val(a + ":desc");
    else   $("#OrderBy").val(a+":asc");
    document.getElementById('_form').submit();
}
function SubForm() {
    document.getElementById('_form').submit();
}
function save1(index){
	var tid = $("#tid").val();
	var month = $("#month").val();
	var id = $("#id_"+index).val();
	var name = $("#name_"+index).val();
	var amount= $("#amount_"+index).val();
	var attitude = $("#attitude_"+index).val();
	var coordination = $("#coordination_"+index).val();
	var discipline= $("#discipline_"+index).val();
	var progress = $("#progress_"+index).val();
	var wordCheck = $("#wordCheck_"+index).val();
	var note = $("#note_"+index).val();
	
	var url = "<%=basePath %>staffer_score!commit";
    $.post(url,{'tid':tid,'stafferScore.tid':tid,
        'stafferScore.id':id,'stafferScore.month':month,'stafferScore.name':name,'stafferScore.amount':amount,'stafferScore.attitude':attitude,
        'stafferScore.coordination':coordination,'stafferScore.discipline':discipline,'stafferScore.progress':progress,'stafferScore.wordCheck':wordCheck,'stafferScore.note':note}
    ,function(data){
         if (data=='success'){
             var url1 = "<%=basePath %>staffer_score?tid="+tid+"&month="+month;
             window.location=url1;
             }
            
         else
             alert("操作失败"); 
     }
    );
}
</script>
</head>
<script type="text/javascript" src="calender/WdatePicker.js"></script>
<body>
<!-- start of content -->
   <div class="Content">
        <div class="Right">
            <div class="Bugicon Midbox">
                月度绩效</div>
            <!--start of solution-->
            <div class="Solution ">
            <form id="_form" action="<%=basePath%>staffer_score"   method="post">
            	<input type="hidden" id="tid" name="tid" value="<s:property value="#request.tid"/>">
            	  <table>
                    <tbody><tr>
                        <td>
                            <b class="BiggestFont Black">
                                月度:</b>
                        </td>
                        <td>
                             <select id="month" name="month" onchange="SubForm();" class="SearchSelect left">
                        <s:iterator value="#request.months" var="month">
                     
                        	<option value="<s:property value="#month" />"    <s:if test="#request.month==#month">selected="selected"</s:if>>
                               <s:property value="#month" /></option>
                        </s:iterator>
                        </select>
                        </td>
                       <td></td>
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
                    <td></td>
                        <td>
                            <b>
                                序号</b>
                        </td>
                        <td>
                            <b>
                              姓名</b>
                        </td>
                       
                        <td>工作量</td>
                        <td>
                         	工作态度
                        </td>
                       	<td>团队协作</td>
                       	<td>劳动纪律</td>
                       	<td>工作进度</td>
                       	<td>文字校对</td>
                       	<td>备注</td>
                       	<td>任务分</td>
                       	<td>最后得分</td>
                       	<td>操作</td>
                    </tr>
                    
                    <s:iterator value="#request.result" var="sta"  status="it">
                  
                     	<tr>
                     	<td><input type="hidden" id="id_<s:property value="#it.index"/>" name="stafferScore.id" value="<s:property value="#sta.id"/>" size="1"></input>
                     
                    	 <td><s:property value="#it.index"/></td>
                    	  <td><input type="text" size="6" disabled="disabled" id="name_<s:property value="#it.index"/>"   value="<s:property value="#sta.name"/>"></input></td>
                    	
                    	    <td><select id="amount_<s:property value="#it.index"/>">
                    	    	<option value="0" <s:if test="#sta.amount==0">selected='selected'</s:if> >0</option>
                    	    	<option value="0.1" <s:if test="#sta.amount==0.1">selected='selected'</s:if> >0.1</option>
                    	    	<option value="-0.1" <s:if test="#sta.amount==-0.1">selected='selected'</s:if> >-0.1</option>
                    	    </select></td>
                    	     <td><select id="attitude_<s:property value="#it.index"/>">
                    	    	<option value="0" <s:if test="#sta.attitude==0">selected='selected'</s:if> >0</option>
                    	    	<option value="0.2" <s:if test="#sta.attitude==0.2">selected='selected'</s:if> >0.2</option>
                    	    	<option value="-0.2" <s:if test="#sta.attitude==-0.2">selected='selected'</s:if> >-0.2</option>
                    	    </select></td>
                    	      <td><select id="coordination_<s:property value="#it.index"/>">
                    	    	<option value="0" <s:if test="#sta.coordination==0">selected='selected'</s:if> >0</option>
                    	    	<option value="0.1" <s:if test="#sta.coordination==0.1">selected='selected'</s:if> >0.1</option>
                    	    	<option value="-0.1" <s:if test="#sta.coordination==-0.1">selected='selected'</s:if> >-0.1</option>
                    	    </select></td>
                    	       <td><select id="discipline_<s:property value="#it.index"/>">
                    	    	<option value="0" <s:if test="#sta.discipline==0">selected='selected'</s:if> >0</option>
                    	    	<option value="0.1" <s:if test="#sta.discipline==0.1">selected='selected'</s:if> >0.1</option>
                    	    	<option value="-0.1" <s:if test="#sta.discipline==-0.1">selected='selected'</s:if> >-0.1</option>
                    	    </select></td>
                    	        <td><select id="progress_<s:property value="#it.index"/>">
                    	    	<option value="0" <s:if test="#sta.progress==0">selected='selected'</s:if> >0</option>
                    	    	<option value="0.1" <s:if test="#sta.progress==0.1">selected='selected'</s:if> >0.1</option>
                    	    	<option value="-0.1" <s:if test="#sta.progress==-0.1">selected='selected'</s:if> >-0.1</option>
                    	    </select></td>
                    	         <td><select id="wordCheck_<s:property value="#it.index"/>">
                    	    	<option value="0" <s:if test="#sta.wordCheck==0">selected='selected'</s:if> >0</option>
                    	    	<option value="0.1" <s:if test="#sta.wordCheck==0.1">selected='selected'</s:if> >0.1</option>
                    	    	<option value="-0.1" <s:if test="#sta.wordCheck==-0.1">selected='selected'</s:if> >-0.1</option>
                    	    </select></td>
                    	          <td><input type="text" size="4"  id="note_<s:property value="#it.index"/>" name="stafferScore.note" value="<s:property value="#sta.note"/>"></input></td>
                    	           <td><s:property value="#sta.issueScore"/></td>
                    	            <td><s:property value="#sta.score"/></td>
                    	            <td> <a href="javascript:save1(<s:property value="#it.index"/>);" class="Buttom  "><span>
                    <img src="<%=basePath %>Content/images/add_icon.gif">
                    保存</span></a><a target="_blank" href="<%=basePath%>stafferscoreprint?tid=<s:property value="#request.tid"/>&sid=<s:property value="#sta.sid"/>&month=<s:property value="#request.month"/>" class="Buttom  "><span>
                    <img src="<%=basePath %>Content/images/add_icon.gif">
                    查看</span></a></td>
                    	            </tr>
                    	           
                    </s:iterator>
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