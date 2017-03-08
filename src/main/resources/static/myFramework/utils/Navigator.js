define(["myFramework/MyExports","myFramework/ui/popup/Mask"], function(exports,Mask) {
	//var _pages = [];

	function getPages() {
		return exports.Pages;
	}

	function getPage(name) {
		var _ps = getPages();
		for (_p in _ps) {
			if (_ps[_p].name == name)
				return _ps[_p];
		}
		return undefined;
	}

	function navigateTo(name,data) {
		var __page=name;
		var iPos=__page.indexOf("?");
		var _param;
		if (iPos>0){
			_param=can.deparam(__page.substring(iPos+1));
			__page=__page.substring(0,iPos);
		}
		var _p = getCurrentPage();
		if (_p && _p.name == __page)
			return;
		var _newPage = getPage(__page);
		if(_newPage==undefined){
			Mask.show();
			_showPage(name,data,true,function(_page){
				if (!_page.dialog)
					if (_p)
						_p.hide();
				Mask.hide();
			});
		}else{
			Mask.show();
			if (!_newPage.dialog) {
				if (_p)
					_p.hide();
			};
			_newPage.param=_param;
			_newPage.show(data);
			Mask.hide();
		}
	};

	function getCurrentPage() {
		var $p = $("#page");
		var _name = $p.attr("data-page");
		if (_name)
			return can.data($p,"pageObject");//getPage(_name);
		else
			return undefined;
	}

	function redirectTo(url) {
		location.href = url;
	}
	
	var endWith=function(text,str){
		var pos=text.indexOf(str);
		if (pos>=0){
			return text.length==pos+str.length;
		}
		return false;
	}
	
	function _showPage(__page,data,_stored,callback){
		var iPos=__page.indexOf("?");
		var _param;
		if (iPos>0){
			_param=can.deparam(__page.substring(iPos+1));
			__page=__page.substring(0,iPos);
		}
		var _pageObject=getPage(__page);
		if (_pageObject){
			_pageObject.param=_param;
			_pageObject.show(data);
		}
		var _isStache=endWith(__page,".stache");
		var page=__page;
		if (_isStache)
			page=__page.substring(0,__page.length-7);
		else
			if (endWith(page,".html"))
				page=page.substring(0,page.length-5);
			else if (endWith(page,".htm"))
				page=page.substring(0,page.length-4);
		var pageDeferred=can.ajax({
			url:__page,
			type : "GET",
			dataType : "text/plain"
		});
		var jsDeferred=can.ajax({
			url:page+".js",
			type : "GET",
			dataType : "text/plain"
		});
		can.when(pageDeferred,jsDeferred).then(function(text,js){
			var _pageFunc=new Function("Page",js);
			var _page;
			_pageFunc(function(options){
				_page=_Page(options);
			});
			_page.param=_param;
			_page.name=__page;
			if (_isStache)
				_page.setStache(text);
			else 
				_page.setHtml(text);
			if (_stored)
				Pages.push(_page);
			if (callback)
				callback(_page);
			_page.show(data);
		},function(){
			alert("加载远程页面失败");
		});
	};
	
	function ajax(url,_data,callback){
		var _sendData=_data;
		if (can.isMapLike(_sendData))
			_sendData=_sendData.serialize();
		if (_sendData)
			_sendData=JSON.stringify(_sendData);
		var _deferred=can.ajax({
			url:url,
			type:"POST",
			contentType:"application/json", 
			dataType : "json",
			data : _sendData
		});
		
		if (callback){
			var _self=this;
			_deferred.then(function(_data){
					callback.call(_self,_data);
				},function(){
					alert("远程调用 "+url+" 失败..");
			});
		}else
			return _deferred;
	}
	
	function remote(url){
		return new function(_url){
			this.url=_url;
			this.post=function(_data,callback){
				var _sendData=_data;
				if (can.isMapLike(_sendData))
					_sendData=_sendData.serialize();
				if (_sendData)
					_sendData=JSON.stringify(_sendData);
				else
					_sendData=JSON.stringify({});
				var _deferred=can.ajax({
					url:this.url,
					type:"POST",
					contentType:"application/json", 
					dataType : "json",
					data : _sendData
				});
				
				if (callback){
					var _self=this;
					_deferred.then(function(_data){
							if (___data.status=="SUCC")
								callback.call(_self,_data);
							else{
								var _errors="";
								can.each(___data.errors,function(v,k){
									_errors=_errors+v.errMsg;
								});
								alert(_errors);
							}
						},function(){
							alert("远程调用 "+url+" 失败..");
					});
				}else
					return _deferred;
			};
			this.get=function(callback){
				window._self=this;
				var _deferred=can.ajax({
					url:this.url,
					type:"GET",
					contentType:"application/json", 
					dataType : "json"
				});
				
				if (callback){
					var _self=this;
					_deferred.then(function(_data){
							if (___data.status=="SUCC")
								callback.call(_self,_data);
							else{
								var _errors="";
								can.each(___data.errors,function(v,k){
									_errors=_errors+v.errMsg;
								});
								alert(_errors);
							}
						},function(){
							alert("远程调用 "+url+" 失败..");
					});
				}else
					return _deferred;
			};
		}(url);
	}
	
	exports.Navigator.getPages=getPages;
	exports.Navigator.getPage = getPage;
	exports.Navigator.getCurrentPage = getCurrentPage;
	exports.Navigator.switchPage = navigateTo;
	exports.Navigator.runApp = redirectTo;
	exports.Navigator.showPage = function(name,data){
		_showPage(name,data,false);
	};
	exports.Navigator.ajax=ajax;
	exports.Navigator.remote=remote;
	
	return exports.Navigator;
});