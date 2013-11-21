// JavaScript Document
//��ʾ��������table
function showhidetable(id1,id2){
var id_show=document.getElementById(id1);
var sbtitle=document.getElementById(id2);
if(sbtitle){
   if(sbtitle.style.display=='block'){
   sbtitle.style.display='none';
   id_show.style.backgroundImage='url(images/icon_show.png)';
   }else{
   sbtitle.style.display='block';
    id_show.style.backgroundImage='url(images/icon_hidden.png)';
   }
}
}

// 表格显示
function showtable(obj){
	var div = $(obj).parent().parent().next();
	if(div){
		if($(div).css("display") == 'block'){
			$(div).css("display","none");
			$(obj).css("backgroundImage","url(images/icon_show.png)");
			
			}else{
				$(div).css("display","block");
				$(obj).css("backgroundImage","url(images/icon_hidden.png)");
				}
		}
}
//���div
function adddiv(){
var id_show2=document.getElementById('renwu_info2');
var id_show3=document.getElementById('renwu_info3');
var id_show4=document.getElementById('renwu_info4');
if(id_show2){
   if(id_show2.style.display=='none'){
   id_show2.style.display='block';
   }
   else if(id_show3.style.display=='none'){
   id_show3.style.display='block';
   }
    else if(id_show4.style.display=='none'){
   id_show4.style.display='block';
   }
}
}


//���div
function adddiv2(id){
var id_show2=document.getElementById('xiangmu_info2');
var id_show3=document.getElementById('xiangmu_info3');
var id_show4=document.getElementById('xiangmu_info4');
if(id_show2){
   if(id_show2.style.display=='none'){
   id_show2.style.display='block';
   }
   else if(id_show3.style.display=='none'){
   id_show3.style.display='block';
   }
    else if(id_show4.style.display=='none'){
   id_show4.style.display='block';
   }
}
}