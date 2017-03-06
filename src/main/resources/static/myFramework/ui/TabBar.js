requirejs(["text!myFramework/ui/TabBar.stache","myFramework/utils/Navigator"],function(tpl,Navigator){
	can.Component.extend({
		tag:"tabbar",
		template:can.stache(tpl),
		viewModel:function(attrs,parentScope,el){
			return{
				visible:false,
				tabs:new can.List([])
			}
		},
		events:{
			"nav a click":function(el){
				Navigator.switchPage(el.attr("data-url"));
			}
		}
	});
	
	//如果应用则初始化body
	var _body = $("body");
	_body.addClass("with-nav-bottom");
	
	//tabBar为单实例
	window.tabBar={
		addTab:function(page,text){
			var el=$("tabbar");
			if (el.length>0){
				var vm=can.viewModel(el[0]);
				vm.tabs.push({page:page,text:text});
				vm.attr("visible",vm.tabs.length);
			}
		}
	}
});