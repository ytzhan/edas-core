define(["myFramework/utils/Template"],function(TemplateTools) {
	// 考虑不污染全局变量，建立唯一的命名空间
	var _app = undefined;
	
	function _App(properties) {
		// 属性
		this.template = "";
		
		//复制属性和方法
		var _self = this;
		can.each(properties, function(value, key) {
			_self[key] = value;
		});

		// App事件
		// this.onLaungh = properties.onLaungh || undefined;
		// this.onError = properties.onError || undefined;

		this.setTemplate=function(tpl){
			this.template=tpl;
		}
		
		
		this.laungh = function(cb) {
			self = this;
			if (self.onLaungh){
				self.onLaungh();
			}
			var _view = new TemplateTools.Stache(this.template);
			var _body = $("body");
			_view.appendTo(_body);
			$(function() {
				cb(_view);
			});
		};
		
		this.$laungh =function(){
			self = this;
			if (self.onLaungh){
				self.onLaungh();
			}
			var _view = new TemplateTools.Stache(self.template);
			var _body = $("body");
			_view.appendTo(_body);
			if (self.onShow)
				self.onShow(_view);
		}
		
		//以下为App的一些工具方法
		this.pushBackList=function(page){
			if (titleBar)
				titleBar.pushBackList(page);
		}
		this.popBackList=function(){
			if (titleBar)
				titleBar.popBackList();
		}
	}

	// 以下为全局方法
	function AppFunc(properties) {
		// new app，调用 laungh 将赋值到 _app
		var _props=properties||{};
		_app = new _App(_props);
		return _app;
	};

	function getAppFunc() {
		if (!_app)
			_app = new _App({});
		return _app;
	}
	;

	return {
		App : AppFunc,
		getApp : getAppFunc,
		//Popup : _Popup //调整到Mask
		//defaultImports : _defaultImports
	};
});