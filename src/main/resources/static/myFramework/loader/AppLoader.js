define(["text" ], function(textLoader) {
	var _buildMap = {};
	var _pages=[];
	
	function _executeLoads(_options,req, onload, config){
		// 从App.json中imports加载canjs组件
		var _imports=_options.imports;
		if (_imports==undefined)
			_imports=[];
		// 加载页面对象
		_pages = _options.pages;
		if (_pages == undefined) {
			_pages = [];
		}
		for ( var indx in _pages) {
			var pos = _pages[indx].indexOf(".html");
			if (pos <= 0) {
				// 默认为stache模版
				_pages[indx] = "stache!"+ _pages[indx];
			} else {
				_pages[indx] = "html!"+ _pages[indx];
			}
		}
		if (_imports.length>0){
			req(_imports,function(){
				req(_pages,function() {
					// todo:清除export.templates的内存变量
					if (config.isBuild) {// 编译时
						onload(_options);
					} else {// 浏览器运行时
						onload(_options);
					};
				});
			});
		}else{
			req(_pages,function() {
				// todo:清除export.templates的内存变量
				if (config.isBuild) {// 编译时
					onload(_options);
				} else {// 浏览器运行时
					onload(_options);
				};
			});
		}
	}
	return {
		load : function(name, req, onload, config) {
			'use strict';
			textLoader.get(req.toUrl(name+".json"), function(text) {
				// 建立App函数，获取App的定义
				var _options = new Function('return '+text+';')();
				_buildMap[name]=text;
				_executeLoads(_options,req, onload, config);
			});
		},
		write : function(plugName, moduleName, write) {
			var _content = _buildMap[moduleName];
			var _p = '"myFramework/MyExports"';
			for (var indx in _pages){
				_p = _p + ',"' + _pages[indx]+'"'; 
			}
			write(
					'define("'+plugName + '!' + moduleName+'",['+_p+'],function(){'+
						'return '+_content+';'+
					'});'
			);
		}
	};
});