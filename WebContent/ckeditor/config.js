var langList = 
[
	{name:'en',	charset:'UTF-8'},
	{name:'zh-cn',	charset:'gb2312'},
	{name:'zh-tw',	charset:'GBK'}
];

var skinList = 
[
	{name:'default',	charset:'gb2312'},
	{name:'whyGreen',	charset:'gb2312'}
];

CKEDITOR.editorConfig = function( config )
{	 config.toolbar = 'easybugfull';

	config.toolbar_easybugfull = [['Bold', 'Italic', 'Underline', 'Strike',
                             '-','NumberedList', 'BulletedList', 'Outdent', 'Indent',
                            '-','TextColor','BGColor','JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock',
                            '-','Link', 'Unlink',
                            '-','Image', 'Table', 'Smiley',
                            '-','Paste', 'PasteText', 'PasteFromWord', 'Source','-','StartCapture','CaptureFromClipboard']];
	config.toolbar = 'easybugbase';
	config.toolbar_easybugbase = [['Smiley', 'Image', 'TextColor', 'BGColor', 'Bold', 'Italic', 'Underline', 'Link', 'Unlink', '-', 'StartCapture', 'CaptureFromClipboard']];
	};
