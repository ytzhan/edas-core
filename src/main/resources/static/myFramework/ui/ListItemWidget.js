requirejs(["myFramework/ui/WidgetFactory"],function(widgetFactory){
	widgetFactory.widget("listitem","<div><content/></div>")
	.config(function(config){
		config.hasError=false;
		config.hasAlign=false;
		config.hasLabel=false;
		config.extendVM=function(vm,attrs,parentScope,el){
			//vm 的 data赋值到对应的index
			var _index=attrs.index;
			
			vm.index=_index;
			vm.data=vm.data[_index];
		}
	})
	.build()
	.plugin(function(_widget){
		_widget.align=function(value){
			this.vm.attr("align",value== undefined?"left":(value == "right" ? "flex-end" :"center"));
		};
	});
});