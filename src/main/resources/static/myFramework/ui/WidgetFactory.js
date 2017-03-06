define(["myFramework/utils/StacheHelpers"],function(_helpers){
	/*
	 * options
	 * 	tag 组件名称
	 *  name 绑定变量名称
	 */
	function _widgetFunc(tag,template,hasError){
		
		//构建Componnet
		return new function(tag,template){
			this.context={
				tag:tag,
				tpl:template,
				helpers:_helpers,
				config:{
					hasError:true,
					hasAlign:true,
					hasLabel:true,
					extendVM:undefined
				}
			};
			
			this._buildViewModel=function(attrs,parentScope,el){
				//获取组件的辅助元素
				var _optionsJson=$(el).data("options");
				var _options={};
				if (_optionsJson){
					//data-options=["xx","xx"]时，jquery自动转换为数组
					if (can.isArray(_optionsJson))
						_options=_optionsJson;
					else{
						var func=new Function("return "+_optionsJson+";");
						_options=func();
					}
				}
				
				//获取page对象的viewModel,组合组件从上层组件获取root,顶层组件的parentScope为root
				var _root=parentScope.attr("root")==undefined?parentScope:parentScope.attr("root");
				//获取页面对象
				var _page=_root.attr("page");
				var _contextName=attrs.context||"";
				var _data=can.getObject(_contextName,parentScope.attr("data")||_root.attr("data"));
				var hide = attrs.hide == "true" ? "display:none":undefined;
				var vm= {
					_cName:el.tagName,
					id:$(el).attr("id"),
					tag:undefined,	
					name:undefined,
					context:_contextName,
					options:_options,
					_hidden:hide,
					page:_page,
					root:_root,
					data:_data,//对象,元素的值为data[name]
					parentScope:parentScope
				};	
				if (this.config.hasError)
					vm.error=new can.Map({
						flag:false,
						message:undefined
					});
				if (this.config.hasLabel)
					vm.label=undefined;
				if (this.config.hasAlign)
					vm.align=(attrs.align== undefined?"left":(attrs.align == "right" ? "flex-end" :"center"));
				if (this.config.extendVM)
					this.config.extendVM(vm,attrs,parentScope,el);
				return vm;
			};
			
			this.events=function(callback){
				if (!this.context.events)
					this.context.events={};
				if (callback)
					callback(this.context.events);
				return this;
			};
			
			this.config=function(callback){
				callback(this.context.config);
				return this;
			}
	
			this.build=function(){
				this.context.viewModel=this._buildViewModel;
				this.context.template=can.stache(this.context.tpl);
				//取消helper数据绑定
				var _removed=function(){
					var _self=this;
					if (_self.viewModel.removeHandler){
						can.each(_self.viewModel.removeHandler,function(obj,index){
							if (obj.event){
								if (_self.viewModel.data&&_self.viewModel.data[obj.name])
									_self.viewModel.data[obj.name].unbind(obj.event,obj.handler);
							}else{
								if (_self.viewModel.data&&_self.viewModel.data[obj.name])
									_self.viewModel.data.unbind(obj.name,obj.handler);
							}
						})
					}
				};
				if (this.context.events){
					var __removed=this.context.events.removed;
					if (__removed){
						this.context.events.removed=function(){
							_removed.call();
							__removed.call();
						}
					}else
						this.context.events.removed=_removed;
				}else{
					this.context.events={removed:_removed};
				}
				can.Component.extend(this.context);
				
				var _plugin=function(tag,callback){
					var _tag=tag;  
					var _callback=callback;
					var _result=function(el){
						if (el){
							return new function(el){
								this.vm=$(el).viewModel();
								_callback(this);
							}(el);
						}else{
							return new function(){
								this.vm=$(_tag).viewModel();
								_callback(this);
							}();
						}
					};
					window[tag]=_result;
				};
				return {
					_tag:this.context.tag,
					plugin:function(callback){
						return new _plugin(this._tag,callback);
					}
				}
			};
		}(tag,template);
	}
	
	function _simpleWidget(tag,template){
		var _widget=_widgetFunc(tag,template);
		return _widget;
	}
	
	return {
		widget:_simpleWidget
	};
});