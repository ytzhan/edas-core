requirejs(["text!myFramework/ui/form/Dropdown.stache","myFramework/ui/WidgetFactory"],function(tpl,widgetFactory){
	widgetFactory.widget("dropdown",tpl)
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
				vm.parentName=_parentName;
				vm.parentOptions=vm.options;
				vm.options=_options;
			}else{
				vm.parentOptions={};
			}*/
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
		tag:"dropdown",
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
			var _align = txt_align == undefined?"":(txt_align == "right" ? "rtl" :"");
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
				});
			}else{
				_options=_parentOptions;
				_parentOptions={};
			}
			return {
				id:el.getAttribute("id"),
				contextName:_contextName,
				name:undefined,
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
		}
	});
	
});*/