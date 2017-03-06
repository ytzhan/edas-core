requirejs(["text!myFramework/ui/form/Checkbox.stache","myFramework/ui/WidgetFactory"],function(tpl,widgetFactory){
	widgetFactory.widget("checkbox",tpl)
	.config(function(config){
		config.extendVM=function(vm,attrs,parentScope,el){
			
			var _selection = $(el).data("selection");
			_selection  = (new Function("return "+_selection))();
			can.each(_selection,function(val,key){
				if(key == "page"){
					if(vm.page[val]){
						if(can.isFunction(vm.page[val])){
							var result = vm.page[val]();
							if(!can.isDeferred(result)){
								vm.selection = result;
							}
						}else{
							vm.selection = vm.page[val];
						}
					}
				}else{
					vm.selection = _selection;
				}
			})       
		}
	})
	.build()
	.plugin(function(_widget){
		_widget.align=function(value){
			this.vm.attr("align",value== undefined?"left":(value == "right" ? "flex-end" :"center"));
		};
	});
});

