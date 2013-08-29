<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="header.jsp" %>
	<script>
		function del(tid){
			 if(!confirm("确定删除？")){
				 return;
				 }
			 var url = "<%=basePath%>team!del?tid="+tid;
			 window.location=url;
			}
		function exit(tid){
			if(!confirm("确定退出团队？")){
				 return;
				 }
			var url = "<%=basePath%>team!exit?tid="+tid;
			window.location=url;
			}
	
        
        function GetProject(tid,bFocusLoad)
        {
       
        if ( document.getElementById("tr_app_"+tid) && !bFocusLoad)
        {
         $("#tr_app_"+tid).show();  
         } else {
          
             var url = "<%=basePath %>project!showByTeam?tid="+tid;
             $.ajax({url:url, cache:false,
             success:function(data){
              
              if (bFocusLoad && document.getElementById("tr_app_"+tid)) $("#tr_app_"+tid).remove();
              $(data).insertAfter("#tr_"+tid);
             }});
           }
             
          $("#a_"+tid).attr("href","javascript:Close("+tid+")").attr("class","hide").attr("title","收起");
        }
        function Close(tid)
        {
         $("#tr_app_"+tid).hide();$("#a_"+tid).attr("class","expand").attr("href","javascript:GetProject("+tid+")").attr("title","展开");
        }
         
         function QuitProject(tid,did)
         {
           $.post('project!exit',{did:did},function(d)
           {	
             
               GetProject(tid,true);
           });
               
         }
     
	</script>
    <div class="Content" >
        <div class="Right">
            <div class="Bugicon Midbox">
                团队信息<s:if test="#session.user.email=='admin'"><a href="<%=basePath %>page/teamAdd.jsp" class="button-create"><span class="right">
                        创建团队</span> </a></s:if>
            </div>
            <div class="clear">
                <!-- -->
            </div>
            <!--start of solution-->
            <div class="Solution">
                
                <h3>
                    所有团队</h3>
                <table class="Bug box" width="100%" border="0" cellpadding="10" cellspacing="0">
                    <tbody><tr class="BlueBackground Black BugTitle TableTop">
                        <td width="25%">
                            团队名称
                        </td>
                        <td width="15%">
                            创建日期
                        </td>
                        <td width="15%">
           
                        </td>
                        <td width="15%">
                            是否管理员
                        </td>
                        <td width="10%"></td>
                        <td align="left">
                            操作
                        </td>
                    </tr>
                    <s:iterator value="#request.teams" var="team">
                     <tr class="GrayBackground" id="tr_<s:property value="#team.tid"/>">
                        <td>
                        <a class="expand" title="展开" id="a_<s:property value="#team.tid"/>" href="javascript:GetProject(<s:property value="#team.tid"/>)"><a class="Blue" href="<%=basePath %>allissue1?tid=<s:property value="#team.tid"/>"> <s:property value="#team.name"/></a>
                        </td>
                        <td>
                            <s:date name="#team.createTime" format="yyyy-MM-dd" />
                        </td>
                        <td>
                           
                        </td>
                        <td>
                        <s:if test="#team.auth==true">是</s:if>
                        <s:else>否</s:else>
                        </td>
                        <td width="10%"></td>
                        <td align="left">
                        <s:if test="#team.auth==true">
                        	 [<a class="Blue" href="<%=basePath%>team!addProject?tid=<s:property value="#team.tid"/>">创建小组</a>]
                            [<a class="Blue" href="<%=basePath%>teammod?ids=<s:property value="#team.tid"/>">编辑</a>]
                           
                        </s:if>
                           <s:if test="#team.creator.sid!=#session.user.sid"> [<a class="Blue" href="#" onclick="exit(<s:property value="#team.tid"/>)">退出团队</a>]</s:if>
                           <s:else> [<a class="Blue" href="#" onclick="del(<s:property value="#team.tid"/>)">删除</a>]</s:else>
                        </td>
                    </tr>
                    
                    </s:iterator>
                   
                </tbody></table>
                
                <div class="clear">
                    <!-- -->
                </div>
            </div>
            <!--end of solution-->
        </div>
    </div>
   </div>
</body>

