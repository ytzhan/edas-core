requirejs(["text!myFramework/ui/form/Search.stache","myFramework/ui/WidgetFactory"],function(tpl,widgetFactory){
	widgetFactory.widget("search",tpl)
	.config(function(config){
		
	})
	.build()
	.plugin(function(_widget){
		_widget.align=function(value){
			this.vm.attr("align",value== undefined?"left":(value == "right" ? "flex-end" :"center"));
		};
	});
});