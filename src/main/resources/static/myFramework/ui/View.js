requirejs([ "text!myFramework/ui/View.stache","myFramework/ui/WidgetFactory"],function(tpl,widgetFactory){
	widgetFactory.widget("view",tpl)
	.config(function(config){
		config.hasError=false;
		config.hasAlign=false;
		config.hasLabel=false;
		config.extendVM=function(vm,attrs,parentScope,el){
			vm.title=undefined
		}
	})
	.build()
	.plugin(function(_widget){
		
	});
});

/*
], function(tpl) {
	can.Component.extend({
		tag : "view",
		template : can.stache(tpl),
		viewModel : function(attrs,parentScope,el){
			//获取page对象的viewModel,组合组件从上层组件获取root,顶层组件的parentScope为root
			var _root=parentScope.attr("root")==undefined?parentScope:parentScope.attr("root");
			//获取页面对象
			var _page=_root.attr("page");
			//组件可以通过属性data重设数据值对象
			//组件可以通过属性data重设数据值对象
			var _contextName=attrs.context||"";
			var _data=can.getObject(_contextName,parentScope.attr("data")||_root.attr("data"));
			return {
				_cName:el.tagName,
				id:el.getAttribute("id"),
				contextName:_contextName,
				name:undefined,
				title:undefined,
				page:_page,
				data:_data,
				root:_root,
				parentScope:parentScope
			}
		}
	});
});*/