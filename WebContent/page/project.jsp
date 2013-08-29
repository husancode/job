<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title></title>
 <script type="text/javascript">
 function QuitProject(did){
	 $.post('project!exit',{did:did},function(d)
	           {	
		 		window.location.reload();
	           });
	 }
 </script>
</head>
<body>
    <div class="Content" >
        <div class="Right">
            <div class="Bugicon Midbox">
                小组信息
            </div>
            <div class="clear">
                <!-- -->
            </div>
            <!--start of solution-->
            <div class="Solution">
                
                <h3>
                    所有小组</h3>
                <table style="text-align: center" class="Bug box" border="0" cellpadding="10" cellspacing="0" width="100%">
                    <tbody><tr class="BlueBackground Black BugTitle TableTop">
                        <td align="left" width="28%">
                            小组名称
                        </td>
                        <td width="15%">
                            加入日期
                        </td>
                        <td width="15%">
           	是否创建者
                        </td>
                        <td width="15%">
                            是否管理员
                        </td>
                        <td align="left">
                            操作
                        </td>
                    </tr>
                    <s:iterator value="#request.departmentVOs" var="department">
                     <tr class="GrayBackground" id="tr_5553">
                        <td align="left">
                            <a class="Blue" href="<%=basePath %>projectinfo?did=<s:property value="#department.did"/>"><s:property value="#department.name"/>
                                </a>
                        </td>
                        <td>
                         <s:date name="#department.createTime" format="yyyy-MM-dd" />
                        </td>
                        <td>
                           <s:if test="#department.ifCreator==true">是</s:if>
                           <s:else>否</s:else>
                        </td>
                          <td>
                        	<s:if test="#department.ifAdmin==true">是</s:if>
                        	<s:else>否</s:else>
                        </td>
                        <td align="left">
                            [<a class="Blue" href="projectinfo?did=<s:property value="#department.did"/>">进入</a>]
                            <s:if test="#department.ifCreator==false">[<a class="Blue" href="javascript:QuitProject(<s:property value="#department.did"/>);" onclick="return confirm('确定退出这个小组吗??');">退出小组</a>]</s:if>
                           	<s:if test="#department.ifAdmin==true">[<a class="Blue" href="projectedit?did=<s:property value="#department.did"/>">编辑</a>][<a class="Blue" href="projectscore?did=<s:property value="#department.did"/>">评分</a>]</s:if>
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
</body>
</html>