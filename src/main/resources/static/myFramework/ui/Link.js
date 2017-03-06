requirejs(["text!myFramework/ui/Link.stache","myFramework/ui/WidgetFactory"],function(tpl,widgetFactory){
	widgetFactory.widget("linkpage",tpl)
	.config(function(config){
		config.extendVM=function(vm,attrs,parentScope,el){
			vm.text="详细信息";
			vm.linkto="";
			vm.image="";
		}
	})
	.events(function(_events){
		_events["a click"]=function(){
			var vm=this.viewModel;
			if (vm.linkto)
				switchPage(vm.linkto,undefined,true);
		}
	})
	.build()
	.plugin(function(_widget){
	});
});