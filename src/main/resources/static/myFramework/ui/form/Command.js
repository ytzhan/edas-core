requirejs(["text!myFramework/ui/form/Command.stache","myFramework/ui/WidgetFactory"],function(tpl,widgetFactory){
	widgetFactory.widget("command",tpl)
	.config(function(config){
		
	})
	.build()
	.plugin(function(_widget){
		
	});
});