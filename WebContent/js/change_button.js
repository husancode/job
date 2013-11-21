
function msover(){
	event.srcElement.style.color="#ffffff";
	event.srcElement.style.background="url(images/approve_bg.png)";
	//event.srcElement.style.cursor = "hand";
	}
	
function msout(){
	event.srcElement.style.color="#303030";
	event.srcElement.style.background="url(images/disagree_bg.png)";
	//event.srcElement.style.cursor = "auto";
	}
	


$(document).ready(function () {

	$(' #dialog-box1').click(function () {		
		//$('#dialog-overlay1, #dialog-box1').hide();	
		//$('#dialog-overlay2, #dialog-box2').hide();
		  document.getElementById("dialog-overlay1").style.display="none";
		  document.getElementById("dialog-overlay2").style.display="none";
		  document.getElementById("dialog-box1").style.display="none";
		  document.getElementById("dialog-box2").style.display="none";
		return false;
	});
	$(' #dialog-box2').click(function () {		
		  document.getElementById("dialog-overlay1").style.display="none";
		  document.getElementById("dialog-overlay2").style.display="none";
		  document.getElementById("dialog-box1").style.display="none";
		  document.getElementById("dialog-box2").style.display="none";
		return false;
	});
	
	//$(window).resize(function () {
	//	if (!$('#dialog-box1').is(':hidden')) popup();		
	//	if (!$('#dialog-box2').is(':hidden')) popup();	
	//});	
	
	
});

//Popup dialog
function popup(message1,flag) {  
	if(flag){
		 document.getElementById("dialog-overlay1").style.display="block";
		 document.getElementById("dialog-box1").style.display="block";
		 $('#dialog-message1').html(message1);
	}else{
		document.getElementById("dialog-overlay2").style.display="block";
		document.getElementById("dialog-box2").style.display="block";
		$('#dialog-message2').html(message1);
	}
	
			
}