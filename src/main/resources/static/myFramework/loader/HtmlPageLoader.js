define(["text" ], function(text) {
	var buildMap={};
	return {
		load : function(name, req, onload, config) {
			'use strict';
			var jsName=name.indexOf(".html")>0?name.substring(0,name.length-5):name;
			text.get(req.toUrl(jsName + ".js"), function(text) {
				buildMap[name] = text;
				req(["myFramework/MyExports","myFramework/AppObject","myFramework/PageObject","text!"+name,"myFramework/data/Remote"], function(exports,_App,_Page,text,_Remote) {
					if (config.isBuild){
						onload();
					}else{
						var _page=undefined;
						var func=new Function("Remote","getApp","Page",buildMap[name]);
						func(_Remote,_App.getApp,function(options){_page=_Page(options)});
						_page.setHtml(text);
						_page.name=name;
						// 保存页面对象到全局变量MyExports
						exports.Pages.push(_page);
						onload();
					}
				});
			});
		},
		write : function(plugName, moduleName, write) {
			write('define("' + plugName + '!' + moduleName+'",["myFramework/MyExports","myFramework/AppObject","myFramework/PageObject","text!'+moduleName+'","myFramework/data/Remote"],function(exports,_App,_Page,tpl,Remote){'+
					'var _page=undefined;'+
					'var getApp=_App.getApp;'+
					'var Page=function(options){_page=_Page(options)};'+
					buildMap[moduleName]+";"+ 
					'_page.setHtml(tpl);'+
					'_page.name="'+moduleName+'";'+
					'exports.Pages.push(_page);' + 
				'});');
		}
	};
});