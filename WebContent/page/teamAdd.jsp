<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>创建团队</title>
 <script src="<%=basePath %>Scripts/common.js" type="text/javascript"></script>
 <script src="<%=basePath %>Scripts/jquery-1.3.2.min.js" type="text/javascript"></script>
 <script type="text/javascript">
 function submit()
 {
   
   if ($("#teamName").val().trim()=="") 
    {
    alert("团队名称不能为空");
    return;
    }
    
    $("#form1").submit();
 }


 </script>
</head>
<body>
      <!-- start of content -->
    <div class="Content">
        <div class="Right">
            <div class="Bugicon Midbox">
                创建新团队
            </div>
            <!--start of solution-->
            <div class="Solution">
                
                <form id="form1" action="<%=basePath %>team!add" 
                method="post">
               
                <div class="OverviewBox clear box">
                    <label>
                        团队名称<font color="red">*</font>
                    </label>
                    <input class="input-w160" type="text" name="teamName" id="teamName" />
                    <font color="red">
                        </font></div>
                <div class="OverviewBox clear box">
                    <label>
                        添加新成员</label>
                    <div class="left">
                        <p class="Smallbox Black">
                             添加新成员(输入Email,换行隔开)</p>
                        <div class="left">
                            <textarea cols="25" rows="8" name="newMembers"></textarea>
                        </div>
                         <br class="clear" />
                        <br />

                        <a href="javascript:submit();" class="Buttom icon_bigest"><span>
                            创建</span></a> <a href="javascript:history.go(-1);"
                                class="Buttom icon_bigest"><span>
                                    返回</span></a>
                    </div>
                    <!-- 
                    <div class="left Two-way">
                        <a href="javascript:SelectAdd();" class="Buttom box"><span>
                            添加
                            </span></a>
                        <div class="clear">
                            
                        </div>
                        <a href="javascript:SelectDel();" class="Buttom"><span><
                            删除</span></a>
                    </div>
                    <div class="left">
                        <p class="Smallbox Green">
                            已选成员</p>
                        <select id="sr" class="input-w160" name="selectMembers" multiple="multiple" style="height: 150px;">
                            <option value="18436" disabled="disabled">胡卡杰
                            </option>
                        </select>
                    </div>
                      -->
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
    <!-- start of content -->
</body>
</html>