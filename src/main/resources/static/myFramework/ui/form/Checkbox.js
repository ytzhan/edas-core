requirejs(["text!myFramework/ui/form/Checkbox.stache","myFramework/ui/WidgetFactory"],function(tpl,widgetFactory){
	widgetFactory.widget("checkbox",tpl)
	.config(function(config){
		config.extendVM=function(vm,attrs,parentScope,el){
			var _selection = $(el).data("selection");
			_selection  = (new Function("page","return "+_selection))(vm.page); 
			vm.selection = _selection; 
		}
	})
	.build()
	.plugin(function(_widget){
		_widget.align=function(value){
			this.vm.attr("align",value== undefined?"left":(value == "right" ? "flex-end" :"center"));
		};
	});
});

