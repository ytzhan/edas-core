define([ "myFramework/MyExports","myFramework/ui/SelectFactory" ],function(exports,selectFactory) {
	
	function camelString(_name) {
		var result = "";
		var nameLength = _name ? _name.length : 0;
		if (nameLength > 0)
			result = _name.substring(0, 1).toUpperCase();
		if (nameLength > 1)
			result = result + _name.substring(1, nameLength);
		var pos = _name.indexOf(".");
		if (pos > 0)
			result = result.substring(0, pos) + camelString(result.substring(pos + 1));
		return result;
	};
	
	function getFullPath(scope,parentScope){
		var _result="";
		var _cName=scope.attr("_cName");
		if (_cName)
			_cName=_cName.toUpperCase();
		var _isDataComponent=(_cName=="VIEW"||_cName=="SCROLLVIEW"||_cName=="LISTITEM"||_cName=="LISTITEMVIEW");
		var _name=scope.attr("context")||(_isDataComponent?undefined:scope.attr("name"));
		if (parentScope){
			var _scope=parentScope;
			var _index=_scope.attr("index");
			//数组ITEM组件
			if (_index){
				var __scope=_scope.attr("parentScope");
				var _value=getFullPath(__scope,__scope.attr("parentScope"));
				if (_value.length>0)
					_result=_result+_value+"[]";
				else
					_result=_result+"[]";
			}else{
				var _value=getFullPath(_scope,_scope.attr("parentScope"));
				if (_value)
					_result=_result+_value;
			}
		}
		if (_name)
			return  _result.length>0?_result+"."+_name:_name;
		else
			return _result;
	}

	function getFullArray(scope,parentScope){
		var _result="";
		if (parentScope){
			var _scope=parentScope;
			var _index=_scope.attr("index");
			//数组ITEM组件
			if (_index){
				var __scope=_scope.attr("parentScope");
				var _value=getFullArray(__scope,__scope.attr("parentScope"));
				if (_value.length>0)
					_result=_result+_value+","+_index;
				else
					_result=_result+_index;
			}else{
				var _value=getFullArray(_scope,_scope.attr("parentScope"));
				if (_value.length>0)
					_result=_result+_value;
			}
		}
		return new Function("return ["+_result+"]")();
	}
	
	// 绑定事件，{{event type}}
	function event(type) {
		var _page = this.page;
		var _id = this.id;
		if (_id == undefined||_id=="")
			_id = this.name || "";
		var _name = this.name || "";
		var _self = this;
		return function(el) {
			var eventName = "on";
			eventName = eventName + camelString(_id);
			var typeLength = type ? type.length : 0;
			if (typeLength > 0) {
				/*
				 * eventName = eventName + type.substring(0,
				 * 1).toUpperCase(); if (typeLength>1) eventName =
				 * eventName + type.substring(1, typeLength);
				 */
				eventName = eventName + camelString(type);
				// 构建事件列表 元素/事件名称/响应函数
				if (_page) {
					var _handler = _page[eventName];
					if (exports.debug || _handler)
						_page.addEvent({
							el : el,
							name : eventName,
							type : type,
							handler : _handler,
							viewModel : _self
						});
				}
			};
		}
	};
	/*function getSelection(_self,el,url){
		var _select = _self.selection;
		var re = can.ajax({
    		url:url
    	});
    	re.then(function(ssuccess){
    		_select = (new Function("return"+ssuccess))();
    		_self.attr("selection",_select.data);
    		el.value = _self.data.attr(_self.name);
    		if(_self.parentName){
    			_setParentOption(_self,el);
			}else{
				refreshMobi(_self);
			}
    	},function(reason){
    		exports.Mask.toast(reason);
    	});
	};
	function _setParentOption(_self,el){
		_self.attr("parentSelection",_self.selection);
		var _options = _self.data.attr(_self.parentName);
		_self.attr("selection",_self.parentSelection.attr(_options));
		el.value = _self.data.attr(_self.name);
		refreshMobi(_self);
	};
	function refreshMobi(_self){
		if(_self.mobi){
			_self.mobi.refresh();
			_self.mobi.setVal(_self.data.attr(_self.name),true);
		}
	};*/
	//值绑定
	function readOnlyValue(){
		var _data = this.data;
		var _page = this.page;
		var _root = this.root;
		var _id = this.id;
		if (_id == undefined)
			_id = this.name || "";
		var _name = this.name || "";
		var _parentScope = this.parentScope;
        var _index=this.index;
		var _self=this;
		return function(el) {
			var _bindFunc=function(ev, newVal, oldVal) {
				if (newVal!=oldVal)
					el.value=newVal;
			};
			if (!_self.removeHandler){
				_self.removeHandler=[];
			}
			//在component remove时取消绑定
			_self.removeHandler.push({name:_name,handler:_bindFunc});
			if (_data)
				_data.bind(_name, _bindFunc);
			el.value = _data.attr(_name);
		};
	};
	
	function inputValue(){
		var _data = this.data;
		var _page = this.page;
		var _root = this.root;
		var _id = this.id;
		if (_id == undefined)
			_id = this.name || "";
		var _name = this.name || "";
		var _parentScope = this.parentScope;
        var _index=this.index;
        var _context=this.context;
        var _options =  this.options;
        var _self = this;
     
		
		return function(el) {
			var _bindFunc=function(ev, newVal, oldVal) {
				if (newVal!=oldVal)
					el.value=newVal;
				var _error = _root.attr("data").errors(
						getFullPath(_self,_self.attr("parentScope")),
						{options:getFullArray(_self,_self.attr("parentScope"))});
				if(_error){
					can.each(_error,function(key,val){
						can.each(key,function(el,index){
							_self.error.attr("flag",true);
							_self.error.attr("message",el);
						})
					})
				}else{
					_self.error.attr("flag",false);
					_self.error.attr("message",undefined);
				}
				var _eventName=camelString(getFullPath(_self,_self.attr("parentScope"))).replace(new RegExp("\\.","gm"),"");
				_eventName="on"+_eventName+"Changed";
				var _pe=_page[_eventName];
				if (_pe)
					_pe(_name,newVal,_self,_data);
			};
			if (_data)
				_data.bind(_name, _bindFunc);
			if (!_self.removeHandler){
				_self.removeHandler=[];
			}
			//在component remove时取消绑定
			_self.removeHandler.push({name:_name,handler:_bindFunc});
			el.onchange = function() {
				_data.attr(_name, this.value);
			};
			el.value = _data.attr(_name);
		};
	}; 
	function selectValue(){
		var _data = this.data;
		var _page = this.page;
		var _root = this.root;
		var _id = this.id;
		if (_id == undefined)
			_id = this.name || "";
		var _name = this.name || "";
		var _parentScope = this.parentScope;
        var _index=this.index;
		var _self=this;
	
		return function(el) {

			selectFactory.selectWidget(_self,el)._getSelectionType(_self);
			
			var _bindFunc=function(ev, newVal, oldVal) {
				if (newVal==oldVal)
					return;
				if (newVal!=oldVal)
					if (el.value!=newVal)
						el.value=newVal;
				if (_self.mobi){
					_self.mobi.refresh();
	    			_self.mobi.setVal(_self.data.attr(_self.name),true);
				}
				var _error = _root.attr("data").errors(
						getFullPath(_self,_self.attr("parentScope")),
						{options:getFullArray(_self,_self.attr("parentScope"))});
				if(_error){
					can.each(_error,function(key,val){
						can.each(key,function(el,index){
							_self.error.attr("flag",true);
							_self.error.attr("message",el);
						})
					})
				}else{
					_self.error.attr("flag",false);
					_self.error.attr("message",undefined);
				}
				var _eventName=camelString(getFullPath(_self,_self.attr("parentScope"))).replace(new RegExp("\\.","gm"),"");
				_eventName="on"+_eventName+"Changed";
				var _pe=_page[_eventName];
				if (_pe)
					_pe(_name,newVal,_self,_data);
			};
			if (_data)
				_data.bind(_name, _bindFunc);
			if (!_self.removeHandler){
				_self.removeHandler=[];
			}
			//在component remove时取消绑定
			_self.removeHandler.push({name:_name,handler:_bindFunc});
			//随动事件绑定
			var _parentName=_self.parentName;
			if (_parentName){
				var _bindFunc2=function(ev, newVal, oldVal) {
					if (newVal!=oldVal){
						var _vm=_self;
						var _options = _vm.attr("parentSelection");
							_options = _options[_vm.data.attr(_parentName)]
						_vm.attr("selection",_options);
						var _firstValue=undefined;
						can.each(_options,function(v,k){
							if (_firstValue==undefined)
								_firstValue=v.value;
						});
						if (_firstValue)
							_vm.data.attr(_vm.name,_firstValue);
						if (_vm.mobi){
							_self.mobi.refresh();
	    					_self.mobi.setVal(_self.data.attr(_self.name),true);
						}
					}
				};
				if (_data)
					_data.bind(_parentName,_bindFunc2);
				_self.removeHandler.push({name:_name,handler:_bindFunc2});
			}
			
			el.onchange = function() {window._el=this;
				_data.attr(_name, this.value);
			};
			//联动则赋予默认值
			var _v=_data.attr(_name);
			if (!_v){
				if (_self.selection){
					can.each(_self.selection,function(v,k){
						if (!_v)
							_v=v.value;
					});
					_data.attr(_name,_v);
				}
			}
			el.value = _data.attr(_name);
		};
	};
	
	function checkboxValue(){
		var _data = this.data;
		var _page = this.page;
		var _root = this.root;
		var _id = this.id;
		if (_id == undefined)
			_id = this.name || "";
		var _name = this.name || "";
		var _parentScope = this.parentScope;
        var _index=this.index;
		var _self=this;
		return function(el) {
			selectFactory.selectWidget(_self,el)._getSelectionType(_self);
			var _checkValue=$(el).data("value");
			var _bindFunc=function(ev, removedElements, index) {
				var _inputs=$(el).parent().parent().find("input");
				can.each(_inputs,function(_input,index){
					_input.checked=false;
					can.each(_data[_name],function(_c,index){
						if ($(_input).data("value")==_c)
							_input.checked=true;
					});
				});
				var _eventName=camelString(getFullPath(_self,_self.attr("parentScope"))).replace(new RegExp("\\.","gm"),"");
				_eventName="on"+_eventName+"Changed";
				var _pe=_page[_eventName];
				if (_pe)
					_pe(_name,newVal,_self,_data);
			};
			if (_data&&_data[_name])
				_data[_name].bind('length', _bindFunc);
			if (!_self.removeHandler){
				_self.removeHandler=[];
			}
			//在component remove时取消绑定
			_self.removeHandler.push({name:_name,handler:_bindFunc,event:"length"});
			el.onclick = function() {
				var _list=_data.attr(_name);
				//el.checked
				var _index=-1;
				if (_list){
					can.each(_list,function(value,index){
						if (value==_checkValue)
							_index=index;
					});
				}
				if (el.checked)
					if (_index=-1)//说明是最新勾选的
						_list.push(_checkValue);
				if (!el.checked)
					_list.splice(_index,1);
			};
			var _list=_data.attr(_name);
			el.checked=false;
			if (_list){
				can.each(_list,function(value,index){
					if (value==_checkValue)
						el.checked=true;
				});
			}
		};
	};
	
	
	function radioValue(){
		var _data = this.data;
		var _page = this.page;
		var _root = this.root;
		var _id = this.id;
		if (_id == undefined)
			_id = this.name || "";
		var _name = this.name || "";
		var _parentScope = this.parentScope;
        var _index=this.index;
		var _self=this;
		return function(el) {
		selectFactory.selectWidget(_self,el)._getSelectionType(_self);

			var _bindFunc=function(ev, newVal, oldVal) {
				if (newVal!=oldVal){
					el.checked = _data.attr(_name) == el.getAttribute("value")?true:false;
					var _eventName=camelString(getFullPath(_self,_self.attr("parentScope"))).replace(new RegExp("\\.","gm"),"");
					_eventName="on"+_eventName+"Changed";
					var _pe=_page[_eventName];
					if (_pe)
						_pe(_name,newVal,_self,_data);
				}
			};
			if (_data)
				_data.bind(_name, _bindFunc);
			if (!_self.removeHandler){
				_self.removeHandler=[];
			}
			//在component remove时取消绑定
			_self.removeHandler.push({name:_name,handler:_bindFunc});
			el.onclick = function() {
				_data.attr(_name, this.value);
			};
			el.checked = _data.attr(_name) == el.getAttribute("value")?true:false;
		};
	};
	
	function switchboxValue(){
		var _data = this.data;
		var _page = this.page;
		var _root = this.root;
		var _id = this.id;
		if (_id == undefined)
			_id = this.name || "";
		var _name = this.name || "";
		var _parentScope = this.parentScope;
        var _index=this.index;
		var _self=this;
		var _options = this._options||[0,1];
		return function(el) {
			if (_data)
				_data.bind(_name,function(ev, newVal, oldVal){
					if (newVal==oldVal)
						return;
					el.checked=newVal==_options[1];
					var _eventName=camelString(getFullPath(_self,_self.attr("parentScope"))).replace(new RegExp("\\.","gm"),"");
					_eventName="on"+_eventName+"Changed";
					var _pe=_page[_eventName];
					if (_pe)
						_pe(_name,newVal,_self,_data);
				});
			el.checked = _data.attr(_name)==_options[1];
			el.onclick=function(){
				_data.attr(_name,_options[this.checked?1:0]);
			};
		};
	};
	
	//todo--考虑数据双向绑定时，切换页面需要取消数据的绑定。
	/*function scrollValue(){
		var _data = this.data;
		var _page = this.page;
		var _name  = this.context;
		var  _pageNumber = 1;
		this.pageNumber = _pageNumber;
		return function(el){			
			_page.data.bind(_name,function(ev,newVal,oldVal){
				var oldLength = oldVal.length,
					newVal = newVal.slice(oldLength,newVal.length);
				can.each(newVal,function(element,index){
					_data.push(element);
				});
				_pageNumber++;
				$(el).parent().viewModel().pageNumber = _pageNumber;
			});
		};
	};*/
	return {
		event:event,
		readOnlyValue:readOnlyValue,
		inputValue:inputValue,
		selectValue:selectValue,
		checkboxValue:checkboxValue,
		radioValue:radioValue,
		switchboxValue:switchboxValue,
	}
	
});