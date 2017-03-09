define(["myFramework/utils/Template","myFramework/AppObject","myFramework/ui/Dialog"],function(TemplateTools,App) {
	function setControl(dom, events) {
		if (dom.getData("control"))
			return;
		var _Control = can.Control.extend(events);
		var _control = new _Control(dom.$dom);
		dom.setData("control", _control);
		// 绑定事件到_control
		_control.addEvent = function(event, func) {
			var _strs = event.split(" ");
			// 拆分为selector,event
			if (_strs.length > 0) {
				var _selector = "";
				var _event = "";
				for (var i = 0; i < _strs.length - 1; i++)
					if (i == 0)
						_selector = _strs[i];
					else
						_selector = _selector + " " + _strs[i];
				_event = _strs[_strs.length - 1];
				_control.on(_selector, _event, func);
			}
		};

		_control.addEvents = function(events) {
			var _self = this;
			can.each(events, function(func, event) {
				_self.on(event, func);
			});
		};
	}
	;

	function PageObject(options) {
		
		this._dom = undefined;
		this.template = "";

		// 复制其它方法到当前对象,构建viewModel
		this.isStache = true;
		this.helpers = {};
		this.dialog = false;
		this.onLoad = undefined;// parameter:page
		this.onShow = undefined;// parameter:page,dom
		this.onHide = undefined// parameter:page,dom
		this.events = undefined;
		this.name = undefined;
		var _self = this;
		can.each(options, function(value, key) {
			_self[key] = value;
		});
		
		// 增加组件注入的事件列表
		this.elementEvents = [];
		this.addEvent = function(event) {
			this.elementEvents.push(event);
			var _self=this;
			if (event.handler) {
				var $el = $(event.el);
				$el.off(event.handler);
				$el.on(event.type, can.proxy(event.handler,{page:_self,el:$el,cmp:window[event.viewModel._cName.toLowerCase()](event.viewModel)}));
			}
		}

		this._appendTo = function($el,__data) {
			var _data=undefined;
			if (__data)
				_data=__data;
			if (_data==undefined){
				if (this.onData){
					_data=this.onData();
				}else if (this.data)
					_data=this.data;
			}
			if (_data==undefined)
				_data={};
			//重新更新设置page的ViewModel，考虑不清除，暂时屏蔽下列代码
			/*
			if (_data==undefined)
				this.viewModel=new can.Map({});
			else
				this.viewModel=can.isMapLike(_data)?_data:new can.Map(_data);
			if (this.isStache)
				this._dom = new TemplateTools.Stache(this.template, {data:this.viewModel,page:this},
							this.helpers);
			*/
			var __render=function(_self,___data){
				if (_self.isStache){
					_self.data=can.isMapLike(___data)?___data:new can.Map(___data);
					_self._dom = new TemplateTools.Stache(_self.template, {data:_self.data,page:_self},
						_self.helpers);
				}else
					_self._dom=new TemplateTools.Html(_self.template);
				if (!can.isEmptyObject(_self.events))
					setControl(_self._dom, _self.events);
				if (_self.onLoad) {
					_self.onLoad(_self);
				}
				_self._dom.appendTo($el);
				if (_self.onShow)
					_self.onShow(_self);
			}
			var self=this;
			if (can.isDeferred(_data)){
				_data.then(function(___data){
					if (___data.status=="SUCC")
						__render(self,___data.data);
					else{
						var _errors="";
						can.each(___data.errors,function(v,k){
							_errors=_errors+v.errMsg;
						});
						alert(_errors);
						__render(self,self.data);
					}
				},function(){
					alert("数据调用错误！");
				});
			}
			else{
				__render(self,_data);
			}
		}

		this._remove = function() {
			var _result = true;
			if (this.onHide) {
				_result = this.onHide(this);
				if (_result == undefined)
					_result = true;
			}
			if (_result) {
				if (this._dom){
					this._dom.remove();		
					this.elementEvents=[];
					this._dom = undefined;
					if (this.onData==undefined)
						this.data=undefined;
				}
			}
		}

		this.setStache = function(template) {
		
			this.template = this.dialog ? "<dialog>" + template
					+ "</dialog>" : template;
			this._dom = undefined;
			this.isStache = true;
		}

		this.setHtml = function(html) {
			if (this.dialog) {
				this.setStache(html);
			} else {
				this.template=html;
				this._dom = undefined;
				this.isStache = false;
			}
		}

		this.getDom = function() {
			return this._dom;
		}

		this.getControl = function() {
			return this._dom.getData("control");
		}

		this.show = function(data) {
			var _page = $("#page");
			var _self=this;
			if (this.dialog) {
				this._appendTo($("body"),data);
				//删除滚动条
				/*$("html,body").on("touchmove",function(ev){
					ev.preventDefault();
				})*/
			} else {
				if (_page) {
					if (_page.attr("data-page") != this.name) {
						can.data(_page,"pageObject",_self);
						_page.attr("data-page", _self.name);
						_page.attr("data-page", _self.name);
						_self._appendTo(_page,data);
					}
				} else {
					alert("错误：没有对应的页面节点<div id='page'>...");
				}
			}
		};
		this.update=function(data){
			if (this._dom){
				this._dom.remove();					
				this._dom = undefined;
				var _page = $("#page");
				if (_page.attr("data-page") == this.name) {
					_page.attr("data-page", "");
					can.data(_page,"pageObject",undefined);
				}
			}
			this.show(data);
		};
		
		this.backPageHide = function() {
			var _page = $("#page");
			if (_page) {
				_page.attr("data-page", "");
				can.data(_page,"pageObject",undefined);
			}
			this._remove();
		};
		this.hide = function() {
			if (this.dialog) {
			 	this._remove();
				//增加滚动条
				/*$("html,body").off("touchmove");*/
			} else {
				this.backPageHide();
				App.getApp().pushBackList(this);
			}
		};
		this.getViewModel = function(selector) {
			if (selector == undefined || selector == "")
				return this.viewModel;
			var _$dom = this.getDom().$dom;
			var _els = _$dom.find(selector);
			var result = [];
			_els.each(function(index, el) {
				result.push($(el).viewModel());
			});
			return result;
		}
	}

	function _pageFunc(options) {
		var _pageObject = new PageObject(options || {
			cache : true
		});
		return _pageObject;
	}

	return _pageFunc;
});
