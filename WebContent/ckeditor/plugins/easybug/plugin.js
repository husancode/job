/*
Copyright (c) 2003-2011, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

/**
* @fileOverview The "easybug" plugin.
*
*/

(function () {
    CKEDITOR.plugins.add('easybug',
	{

	    requires: ['dialog'],
	    lang: ['zh-cn', 'en'],
	    init: function (editor) {
	        var lang = editor.lang.easybug;

	        editor.addCommand('capturefromclipboard',
            {
                exec: function (editor) {
                    window.parent.UploadClipboadImage();
                },
                editorFocus: true
            });

	        editor.addCommand('startcapture',
			{
			    exec: function (editor) {
			        window.parent.StartCameraScreen();

			    },
			    editorFocus: true
			});

	        editor.ui.addButton('CaptureFromClipboard',
			{
			    label: lang.CaptureFromClipboard,
			    command: 'capturefromclipboard',
			    icon: this.path + 'CaptureFromClipboard.gif'
			});

	        editor.ui.addButton('StartCapture',
			{
			    label: lang.StartCapture,
			    command: 'startcapture',
			    icon: this.path + 'ProjectGroupCapture.gif'
			});


	        //CKEDITOR.dialog.add('capturefromclipboard', this.path + 'dialogs/easybug.js');
	        //CKEDITOR.dialog.add('startcapture', this.path + 'dialogs/easybug.js');
	    },

	    afterInit: function (editor) {
	        var dataProcessor = editor.dataProcessor,
				dataFilter = dataProcessor && dataProcessor.dataFilter,
				htmlFilter = dataProcessor && dataProcessor.htmlFilter;

	    }
	});
})();

CKEDITOR.plugins.easybug =
{
    capturefromclipboard: function (editor, oldElement, text, isGet) {
        alert('capturefromclipboard');
        var element = new CKEDITOR.dom.element('span', editor.document);
        element.setAttributes(
			{
			    contentEditable: 'false',
			    'data-cke-placeholder': 1,
			    'class': 'cke_placeholder'
			}
		);

        text && element.setText(text);

        if (isGet)
            return element.getOuterHtml();

        if (oldElement) {
            if (CKEDITOR.env.ie) {
                element.insertAfter(oldElement);
                // Some time is required for IE before the element is removed.
                setTimeout(function () {
                    oldElement.remove();
                    element.focus();
                }, 10);
            }
            else
                element.replace(oldElement);
        }
        else
            editor.insertElement(element);

        return null;
    },

    startcapture: function (editor) {
        alert('startcapture');
        var range = editor.getSelection().getRanges()[0];
        range.shrink(CKEDITOR.SHRINK_TEXT);
        var node = range.startContainer;
        while (node && !(node.type == CKEDITOR.NODE_ELEMENT && node.data('cke-placeholder')))
            node = node.getParent();
        return node;
    }
};
