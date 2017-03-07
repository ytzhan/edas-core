requirejs(["text!myFramework/ui/form/Readtext.stache","myFramework/ui/WidgetFactory"],function(tpl,widgetFactory){
	widgetFactory.widget("readtext",tpl)
	.config(function(config){
		
	})
	.build()
	.plugin(function(_widget){
		_widget.align=function(value){
			this.vm.attr("align",value== undefined?"left":(value == "right" ? "flex-end" :"center"));
		};
	});
});