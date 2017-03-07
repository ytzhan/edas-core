define([],function () {
	
	function _selectWidget(_self,el){
		return new function (_self,el,url){
			var _setparentSelection = function(_self,el){
				_self.attr("parentSelection",_self.selection);
				var val = _self.data.attr(_self.parentName);
				var _options  = _self.attr("parentSelection");
				_self.attr("selection",_options.attr(val));
				el.value = _self.data.attr(_self.name);

			};
			var _refreshMobi = function(_self){
				
				if(_self.mobi){
					_self.mobi.refresh();
					_self.mobi.setVal(_self.data.attr(_self.name),true);
				}
			};
			var _initValue = function(_self,el){
				var type =el.getAttribute("type");
				if( !(type == "radio" && type == "checkbox") ){
					el.value  = _self.data.attr(_self.name);
				}
			}
			this._getSelectionType  = function(_self){

				var name  = _self.attr("selection");
				can.each(name,function(val ,key){
					if(key == "page"){
						if(_self.page[val]){
						
							if(can.isFunction(_self.page[val])){//是个deferred对象
								var result = _self.page[val]();
								if(can.isDeferred(result)){
									result.then(function(ssuccess){
										var _selection =  (new Function("return " +ssuccess))();
											_selection = _selection.data;
											_self.attr("selection",_selection);
										if(_self.parentName){
											_setparentSelection(_self,el);
										}
										_refreshMobi(_self);
										_initValue(_self,el);
									},function(reason){
										exports.Mask.toast(reason);
									});
								}else{
									_self.attr("selection",result);//返回一个对象
									if(_self.parentName){
										_setparentSelection(_self,el);
									}
									_refreshMobi(_self);
									_initValue(_self,el);
								}
							}else{
								_self.attr("selection",_self.page[val]);//result是个对象
								if(_self.parentName){
									_setparentSelection(_self,el);
								}
								_refreshMobi(_self);
								_initValue(_self,el);
							}
						}
					}
				});
			};
		}(_self,el); 
	}
	function _simpleSelect(_self,el){
		var _widget = _selectWidget(_self,el);
		return _widget;
	}
	return {
		selectWidget:_simpleSelect
	}
})