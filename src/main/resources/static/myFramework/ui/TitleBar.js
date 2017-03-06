requirejs([ "text!myFramework/ui/TitleBar.stache","myFramework/ui/popup/Menu","myFramework/utils/Navigator"], function(tpl,Menu,Navigator) {
	var _backList=undefined;
	var _menu={
		list:[]
	}
	can.Component.extend({
		tag : "titlebar",
		template : can.stache(tpl),
		viewModel:{
			position:"affix",
			left_button:false,
			title:"undefined",
			menu_button:false
		},
		events:{
			"#menu click":function(){
				if (_menu.list.length>0)
					Menu.show(_menu);
			},
			"#back click":function(){
				if (_backList){
					var _back=_backList.pop();
					if (_back){
						this.viewModel.attr("left_button",_backList.length>0);
						var _page=Navigator.getCurrentPage();
						_page.backPageHide();	
						_back.show();
					}
				}
			}
		}
	});
	
	//如果应用则初始化body
	var _body = $("body");
	_body.addClass("with-heading-top");
	//titleBar为单实例
	window.titleBar={
		viewModel:function(){
			var $el=$("titlebar");
			if ($el.length>0){
				return can.viewModel($el[0]);
			}
			return undefined;
		},
		pushBackList:function(page){
			var vm=this.viewModel();
			if (vm){
				if (_backList){
					_backList.push(page);
					vm.attr("left_button",_backList.length>0);
				};
			};
		},
		popBackList:function(){
			var vm=this.viewModel();
			if (vm){
				if (_backList){
					var _page=_backList.pop();
					vm.left_button=_backList.length>0;
					return _page;
				}
			};
			return undefined;
		},
		showLeftButton:function(value){
			var vm=this.viewModel();
			if (vm){
				if (value==undefined)
					return vm.left_button;
				else
					if (value){
						if (_backList==undefined)
							_backList=[];
						vm.attr("left_button",_backList.length>0);
					}else{
						vm.attr("left_button",false);
						_backList=undefined;
					}
			};
			return false;
		},
		showMenuButton:function(value){

			var vm=this.viewModel();
			if (vm){
				if (value==undefined)
					return vm.menu_button;
				else{
					vm.attr("menu_button",value);
				}
			}
			return false;
		},
		addMenu:function(text,icon,callback){
			_menu.list.push({text:text,icon:icon,action:callback});
		}
	};
});