define(["text"],function(textLoader) {
	// 加载基础JS框架
	var _script;//App.js
	function _loadLibrary(req,config,cb){
		req([ "mzui"],function() {
			req([ "canjs" ],function() {
				req(["mobiscroll","canjsStache","canjsValidation"],function(mobiscroll) {
					if (config.isBuild){
						
						cb();
					}else{	
						window.mobiscroll=mobiscroll;
						cb();
					};
				});
			});
		});
	};
	function _loadMyFramework(req,config,cb){
		req(["myFramework/MyExports","myFramework/AppObject","myFramework/PageObject","text!myFramework/errorsDialog.stache"],
			function(exports,App,Page,tpl){
			if (config.isBuild){
				cb();
			}else{	
				exports.tools.App=App;
				exports.tools.Page=Page;
				//exports.tools.Remote=Remote;
				exports.tools.validate = function(data,callback){
					var errors  = data.errors();
					if(errors!=undefined){
						var _page = new Page({
							dialog:true,
							title:"错误信息",
							top:'20%',
							left:'10%',
							height:"300px",
							width:'80%',
							opcity:true,
							onSureClick:function(){
								_page.hide();
							}
						});
					_page.setStache(tpl);
					_page.show(errors);
					}else{
						callback();
					}
				};
				cb();
			}
		});
	};
	
	function _loadAppAndPages(req,config,cb){
		req(["myFramework/MyExports","app!App"],function(exports,app) {
			if (config.isBuild){
				req(["myFramework/MyExports","text!App.stache","myFramework/AppObject"],function(exports,tpl,_APP) {
					cb();
				});
			}else{
				//exports.App=app;
				// 启动应用对象
				req(["myFramework/MyExports","text!App.stache","myFramework/AppObject"],function(exports,tpl,_APP) {
					var func=new Function("App","return "+_script+";");
					var _app=func(_APP.App);
					_app.setTemplate(tpl);
					cb(_app);
				});
			}
		});
	}
	function _loadDefaultImports(req,cb){
		/*req(["myFramework/ui/TitleBar","myFramework/ui/TabBar","myFramework/ui/View",
			"myFramework/ui/form/Checkbox",	"myFramework/ui/form/Command",	"myFramework/ui/form/Date",	
			"myFramework/ui/form/Dropdown",	"myFramework/ui/form/Groupopt","myFramework/ui/form/Multitext",
			"myFramework/ui/form/Radio","myFramework/ui/form/Search","myFramework/ui/form/Switch",
			"myFramework/ui/form/Text",	"myFramework/ui/form/Vcode","myFramework/ui/form/Dropdown_mobi"
			],function(){
			cb();
		});*/
		cb();
	}
	
	return {
		load : function(name, req, onload,config) {
			textLoader.get(req.toUrl("App.js"), function(t) {
				_script=t;
				_loadLibrary(req,config,function(){
					_loadMyFramework(req,config,function(){
						_loadDefaultImports(req,function(){
							_loadAppAndPages(req,config,function(app){
								if (config.isBuild){
									onload(app);
								}else{
						
									onload(app);
								}
							});
						});
					});
				});
			});
		},
		normalize:function(name,normalize){
			return normalize(name);
		},
		write:function(plugName,moduleName,write){
			write(
				'define("' + plugName + '!' + moduleName + '",'+'["mobiscroll","myFramework/MyExports","myFramework/AppObject","myFramework/PageObject","app!App","text!App.stache","text!myFramework/errorsDialog.stache"],function(mobiscroll,exports,_APP,Page,options,tpl,errorsDialogTpl){'+
					'exports.tools.App=_APP;'+
					'exports.tools.Page=Page;'+
					//'exports.tools.Remote=Remote;'+
					'exports.tools.validate = function(data,callback){'+
						'var errors  = data.errors();'+
						'if(errors!=undefined){'+
							'var _page = new Page({'+
								'dialog:true,'+
								'title:"错误信息",'+
								'top:"20%",'+
								'left:"10%",'+
								'height:"300px",'+
								'width:"80%",'+
								'opcity:true,'+
								'onSureClick:function(){'+
									'_page.hide();'+
									'}'+
								'});'+
							'_page.setStache(errorsDialogTpl);'+
							'_page.show(errors);'+
						'}else{'+
							'callback();'+
						'}'+
					'};'+
					'exports.App=options;'+
					'var App=_APP.App;'+
					'var _app='+_script+';'+
					'_app.setTemplate(tpl);'+
					'window.mobiscroll=mobiscroll;'+
					'return _app;'+
				'});'
			);
		}
	};
});