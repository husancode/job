var objEasybug;
var flag = false;
var cType = 0;
var hasInit = false;
var EasybugEditorIntance = null;
var latestVersion = 1.17;
function initCamera(editor) {
    if (editor != null && editor != undefined) {
        EasybugEditorIntance = editor;
    }    
        
        if (document.all) {
            document.write('<OBJECT ID="oEA" CLASSID="CLSID:A6BBC994-C147-4C0E-9D1B-8D058059E883" ></OBJECT>');
            objEasybug = document.getElementById('oEA');
            if (objEasybug.LastFileName == undefined || objEasybug.GetVersion < latestVersion) {
                $("#href_client").text('下载新版截图插件v' + latestVersion + '，支持剪切板粘贴和 Firefox10.0 以上版本.').show();
                flag = true;
            }
            objEasybug.attachEvent("OnUploadProcessing", OnUploadProcessing);
            objEasybug.attachEvent("OnCameraComplete", OnCameraComplete);            
            hasInit = true;
        }
        else {
            document.write('<embed id="oEA"  type="application/Easybug-Plugin" style1="display:none;" width="1" height="1" >');
            objEasybug = document.getElementById('oEA');
            if (objEasybug.CameraScreen == undefined || objEasybug.GetVersion < 1.15) {
                $("#href_client").show();
                flag = true;
            }
            objEasybug.OnUploadProcessing = "OnUploadProcessing";
            objEasybug.OnCameraComplete = "OnCameraComplete";
            hasInit = true;
        }

//        if (!flag) {
//            setTimeout("bindPaste()", 500);
//        }
        
}

function bindPaste() {
    //alert("paste");
    try {
        var oEditor = CKEDITOR.instances.description;
        oEditor.on('paste', UploadClipboadImage)
    } catch (ex) {
        setTimeout("bindPaste()",500);
    }    
}


function StartCameraScreen() {
    if (flag) {
        alert(lan == "cn" ? "请先安装截图插件" : "please install the screenshot  plugin first");
        return;
    }
    cType = 0;
    objEasybug.ShowLanguage = lan;
    html = "";
    objEasybug.CameraScreen();
}

function UploadClipboadImage(e) {    
    if (flag) {
        alert(lan == "cn" ? "请先安装截图插件" : "please install the screenshot  plugin first");
        return true;
    }            

    cType = 1;
    objEasybug.ShowLanguage = lan;
    html = "";
    var bResult = false;
    $("#info").html('uploading......');
    
    try {
        bResult = objEasybug.UploadClipboadImage();
    } catch (ex) {
        //alert("Upload image error.");
        $("#info").html('');
        return true;
    }

    
    if (bResult == "true" || bResult) {        
        return false;
    }
    else {
        $("#info").html('');
        return true;
    }
}

var m_strFileName;
function OnCameraComplete(strFileName) {
    m_strFileName = strFileName;
    $("#info").html('');
    try { window.focus(); }
    catch (err) { }
}

var html;
function OnUploadProcessing(nPercent) {
    $("#info").html('uploading...... ' + nPercent + "%");
    if (nPercent >= 100) {
        $("#info").html("");

        html = "http://localhost:8080/IssueEasy/uploadfiles/" + m_strFileName;
        var oEditor = CKEDITOR.instances.description;
        if (oEditor == undefined) oEditor = EasybugEditorIntance;
        //console.info(oEditor);
        //var oEditor = CKEDITOR.currentInstance; //FCKeditorAPI.GetInstance(intanceName);
        
        oEditor.insertHtml('<img src="' + html + '" />');
        //if (cType == 0) {
            //oEditor.Commands.GetCommand('InsertLink').Execute();
        //}
        //else {
        //    oEditor.InsertHtml('<img src="' + html + '" />');
        //}
    }
}

if (window.Event) {
    window.onbeforeunload = function (event) {
        CloseCamera();
    }
} else {
    window.onbeforeunload = function () {
        CloseCamera();
    }
}

function CloseCamera() {
    try {
        objEasybug.CancelCamera();
    }
    catch (err)
       { }
}
function AddHit() {
    $.ajax({ url: "/Bug/AddHit", data: "", type: "post" });
}

