requirejs(["text!myFramework/ui/form/Dropdown_mobi.stache","myFramework/ui/WidgetFactory"],function(tpl,widgetFactory){
	widgetFactory.widget("dropdown_mobi",tpl)
	.config(function(config){
		config.extendVM=function(vm,attrs,parentScope,el){
			var _selection = $(el).data("selection");
			_selection  = (new Function("page","return "+_selection))(vm.page);
			vm.selection = _selection;
			var _parentName=$(el).data("parent");
			vm.parentName = _parentName;
			if(_parentName){
				if(vm.data[_parentName]){
					_selection = vm.selection[vm.data[_parentName]];
				}else{
					_selection = new can.List([]);
				}
				vm.parentName = _parentName;
				vm.parentSelection = vm.selection;
				vm.selection = _selection;
			}

			
			/*var _options;
			if (_parentName){
				if (vm.data[_parentName]){
					_options=vm.options[vm.data[_parentName]];
				}else
					_options=new can.List([]);
				//var _data=can.getObject(vm.context,parentScope.attr("data")||vm.root.attr("data"));
				_data.bind(_parentName,function(ev, newVal, oldVal) {
					if (newVal!=oldVal){
						var _vm=$(el).viewModel();
						var _options=_vm.parentOptions[_vm.data.attr(_parentName)];
						_vm.attr("options",_options);
						var _firstValue=undefined;
						can.each(_options,function(v,k){
							if (_firstValue==undefined)
								_firstValue=v;
						});
						_vm.mobi.clear();
						if (_firstValue)
							_vm.data.attr(_vm.name,_firstValue);
					}
				});
				vm.parentName=_parentName;
				vm.parentOptions=vm.options;
				vm.options=_options;
			}else{
				vm.parentOptions={};
			}*/
		}
	})
	.events(function(events){
		events.inserted=function(el, ev) {
			var instance = mobiscroll.select(el.find("select"),{
	       		theme: 'mobiscroll',  
		        lang: 'zh',          
		        display: 'bottom',
		        dateFormat:"yy-mm-dd ",
		         	        
        	});
		    el.viewModel().mobi=instance;        	
	    }
	})
	.build()
	.plugin(function(_widget){
		_widget.align=function(value){
			this.vm.attr("align",value== undefined?"left":(value == "right" ? "flex-end" :"center"));
		};
	});
});

/*,"myFramework/utils/StacheHelpers"],function(tpl,stacheHelpers){
	can.Component.extend({
		tag:"dropdown-mobi",
		template:can.stache(tpl),
		helpers:stacheHelpers,
		viewModel:function(attrs,parentScope,el){
			var _optionsJson=$(el).data("options");
			var _parentOptions={};
			if (_optionsJson){
				var func=new Function("return "+_optionsJson+";");
				_parentOptions=func();
			};			
			//获取page对象的viewModel,组合组件从上层组件获取root,顶层组件的parentScope为root
			var _root=parentScope.attr("root")==undefined?parentScope:parentScope.attr("root");
			//获取页面对象
			var _page=_root.attr("page");
			//组件可以通过属性data重设数据值对象
			var _contextName=attrs.context||"";
			var _data=can.getObject(_contextName,parentScope.attr("data")||_root.attr("data"));
			var txt_align = attrs.align;
			var _align = txt_align == undefined?"":(txt_align == "right" ? "mobi-right" :"mobi-center");
			//级联随动
			var _parentName=$(el).data("parent");
			var _options;
			if (_parentName){
				if (_data[_parentName]){
					_options=_parentOptions[_data[_parentName]];
				}else
					_options=new can.List([]);
				_data.bind(_parentName,function(ev, newVal, oldVal) {
					var vm=$(el).viewModel();
					var _options=vm._parentOptions[vm.data[_parentName]];
					$(el).viewModel().attr("_options",_options);
					$(el).viewModel().data.attr(name,"");
					vm.mobi.clear();
					vm.mobi.init();
				});
			}else{
				_options=_parentOptions;
				_parentOptions={};
			}
			return {
				id:el.getAttribute("id"),
				contextName:_contextName,
				name:undefined,
				mobi:undefined,
				_parentName:_parentName,
				_options:_options,
				_parentOptions:_parentOptions,
				key:"",
				label:"",
				_align:_align,
				page:_page,
				data:_data,
				root:_root,
				_myParent:parentScope,
				error:new can.Map({
					flag:false,
					message:undefined
				})
			}
		},
		events: {

	        inserted: function(el, ev) {
			    var instance = mobiscroll.select(el.find("select"),{
	        		theme: 'mobiscroll',  
			        lang: 'zh',           
			        display: 'bottom',
			        dateFormat:"yy-mm-dd "		        
	        	});
				
			    el.viewModel().mobi = instance;        	
	        }
	    }
		
		
	});
});*/