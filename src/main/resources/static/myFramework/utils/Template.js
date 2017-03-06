//类函数,调用需要使用new
define(["myFramework/MyExports","myFramework/utils/StacheHelpers"],function(exports,stacheHelpers){
	function Stache(template,scope,helpers){
		var _view=can.stache(template);
		var _dom=$("<div/>");
		var _helpers={};
		_helpers=can.extend(_helpers,stacheHelpers);
		if (helpers)
			_helpers=can.extend(_helpers,helpers);
		_dom.append(_view(scope,_helpers));
		
		this.appendTo=function($el){
			$el.append(_dom);
		};
		this.detach=function(){
			_dom.detach();
		};
		this.remove=function(){
			_dom.remove();
			_dom=undefined;
		}
		this.$dom=_dom;
		this.setData=function(key,value){
			_dom.data(key,value);
		}
		this.getData=function(key){
			return _dom.data(key);
		}
	};
	
	function Html(html){
		var _dom=$("<div>"+html+"</div>");
		
		this.appendTo=function($el){
			$el.append(_dom);
		};
		this.detach=function(){
			_dom.detach();
		};
		this.remove=function(){
			_dom.remove();
			_dom=undefined;
		}
		this.$dom=_dom;
		this.setData=function(key,value){
			_dom.data(key,value);
		}
		this.getData=function(key){
			return _dom.data(key);
		}
	};
	
	// 解决异步加载，通过回调处理机制
	function StacheWithFile(file,scope,helpers,cb){
		var _file="text!"+file+".stache";
		requirejs([file],function(tpl){
			var _obj=new Stache(tpl,scope,helpers);
			if (cb)
				cb(_obj);
		});
	}
	
	// 解决异步加载，通过回调处理机制
	function HtmlWithFile(file,cb){
		var _file="text!"+file+".html";
		requirejs([file],function(tpl){
			var _obj=new Html(tpl);
			if (cb)
				cb(_obj);
		});
	}
	
	return {
		Stache : Stache,
		StacheWithFile : StacheWithFile,
		Html : Html,
		HtmlWithFile : HtmlWithFile
	}
});