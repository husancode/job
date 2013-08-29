String.prototype.trim = function () {
    return this.replace(/(^[\s]*)|([\s]*$)/g, "");
};
function testMail(idstr) {
    var myReg = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
    return myReg.test(idstr);
}
function GO(page) {
    document.getElementById("pageIndex").value = page;
    document.getElementById("_form").submit();
}
function copyToClipboard(txt) {
    if (window.clipboardData) {
        window.clipboardData.clearData();
        window.clipboardData.setData("Text", txt);
        alert("Success");
    } else if (navigator.userAgent.indexOf("Opera") != -1) {
        window.location = txt;
    } else if (window.netscape) {
        try {
            netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
        } catch (e) {
            alert("被浏览器拒绝！\n请在浏览器地址栏输入'about:config'并回车\n然后将 'signed.applets.codebase_principal_support'设置为'true'");
        }
        var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
        if (!clip)
            return;
        var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
        if (!trans)
            return;
        trans.addDataFlavor('text/unicode');
        var str = new Object();
        var len = new Object();
        var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
        var copytext = txt;
        str.data = copytext;
        trans.setTransferData("text/unicode", str, copytext.length * 2);
        var clipid = Components.interfaces.nsIClipboard;
        if (!clip)
            return false;
        clip.setData(trans, null, clipid.kGlobalClipboard);
        alert("Success");
    }
}

function SubmitForm(id) {
    document.getElementById(id).submit();
}
function toggle_visibility(id) {
    var e = document.getElementById(id);
    if (e.style.display == 'block')
        e.style.display = 'none';
    else
        e.style.display = 'block';
}
//屏蔽js错误
function ResumeError() {
    return true;
}
//window.onerror = ResumeError;
function SetLanguage(lan) {
    document.getElementById("hidden_lan").value = lan;
    document.getElementById("lan_form").submit();
}
function onFocus(entity, m) {
    if (!document.all) {
        document.captureEvents(Event.KEYPRESS);
    }
    entity.onkeypress = function (e) {
        var key = document.all ? window.event.keyCode : e.which;
        if (key == 13)
            document.getElementById(m).submit();
    }
}

function Click(a) {
    window.location = "#";
    if (document.all) {
        document.getElementById(a).click();
    }
    else {
        var evt = document.createEvent("MouseEvents");
        evt.initEvent("click", true, true);
        document.getElementById(a).dispatchEvent(evt);
    }
}

String.prototype.replaceAll = function (s1, s2) {
    return this.replace(new RegExp(s1, "gm"), s2);
}    
