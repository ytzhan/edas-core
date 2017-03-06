requirejs.config({
	// baseUrl : ".",
	paths : {
		mzui : "../lib/mzui.min",
		canjs : "../lib/can.zepto.min",
		canjsStache : "../lib/can.stache",
		canjsValidation:"../lib/can.map.validations",
		mobiscroll: "../lib/mobiscroll.custom-3.0.0-beta6.min",
		myFramework : "../myFramework",
		text : "../requirejs/text",// 文本加载器
		app : "../myFramework/loader/AppLoader", // 自定义加载器
		html : "../myFramework/loader/HtmlPageLoader" ,// 自定义加载器
		stache : "../myFramework/loader/StachePageLoader", // 自定义加载器
		boot : "../myFramework/Booter" // 自定义加载器
	},
	config : {
		text : {

		},
		app : {

		},
		html : {

		},
		stache : {

		},
		boot : {

		}
	}
});

requirejs(["boot!App","myFramework/MyExports"],function(AppObject,exports){
	//window.App=AppObject;
	window.exports=exports;
	window.Pages=exports.Pages;
	window.getPage=exports.Navigator.getPage;
	window.getApp=exports.tools.App.getApp;
	window.remote=exports.Navigator.remote;
	window.Mask=exports.Mask;
	window.Navigator=exports.Navigator;
	window.getCurrentPage=exports.Navigator.getCurrentPage;
	window.switchPage=exports.Navigator.switchPage;
	window.runApp=exports.Navigator.runApp;
	window._Page=exports.tools.Page;
	window.showPage=exports.Navigator.showPage;
	window.ajax=exports.Navigator.ajax;
	
	$(function(){
		var _app=getApp();
		if (options)
			can.each(options,function(value,key){
				_app[key]=value;
			});
		_app.$laungh();
	});
});

