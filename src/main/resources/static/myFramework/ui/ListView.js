requirejs(["text!myFramework/ui/ListView.stache","myFramework/ui/WidgetFactory"],function(tpl,widgetFactory){
	widgetFactory.widget("listview",tpl)
	.config(function(config){
		config.extendVM=function(vm,attrs,parentScope,el){
			var _titles=$(el).data("titles");
			vm.titles=_titles;
		}
	})
	.build()
	.plugin(function(_widget){
		_widget.align=function(value){
			this.vm.attr("align",value== undefined?"left":(value == "right" ? "flex-end" :"center"));
		};
	});
});

/*
,"myFramework/utils/StacheHelpers"],function(tpl,stacheHelpers){
	can.Component.extend({
		tag:"list",
		template:can.stache(tpl),
		helpers:stacheHelpers,
		viewModel:function(attrs,parentScope,el){
			var _titles=$(el).data("titles");
			
			//获取page对象的viewModel,组合组件从上层组件获取root,顶层组件的parentScope为root
			var _root=parentScope.attr("root")==undefined?parentScope:parentScope.attr("root");
			//获取页面对象
			var _page=_root.attr("page");
			//组件可以通过属性data重设数据值对象
			var _contextName=attrs.context||"";
			var _title = attrs.rows||"";
			var _data=can.getObject(_contextName,parentScope.attr("data")||_root.attr("data"));

			return {
				id:el.getAttribute("id"),
				contextName:_contextName,
				align:"left",
				titles:_titles,
				page:_page,
				data:_data,
				root:_root,
				aling:"left",
				style:"table bordered",
				titleColor:"primary",
				active:"",
				_myParent:parentScope,
				error:new can.Map({
					flag:false,
					message:undefined
				})
			}
		}
	});
});
*/