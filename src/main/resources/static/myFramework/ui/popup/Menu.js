define(["text!myFramework/ui/popup/Menu.stache","myFramework/ui/popup/Mask"],function(tmpl,Mask){
	function show(data,placement){
		
		if (Mask.Popup.isShow){
			Mask.Popup.hide();
		}
		if (!data.position)
			data.position="bottom";

		Mask.Popup.show({
			content:function(options){
				var _frag=can.stache(options.stache);
				var _dom=$("<div/>");
				_dom.append(_frag(options.viewModel));
				return _dom.html();
			},
			stache:tmpl,
			viewModel:data,
			data:data,
			placement:data.position,
			targetDismiss : false,//是否点击目标自动隐藏
			display:"modal",
			autoHide:false,
			animate:0,//不执行动画
			duration:0,//不执行动画
			preventDefault:true,
			stopPropagation:true,
			hidden:function(){
				//恢复触摸事件
				var mask = $("body").find("div[type='modal']");
				mask.off("touchmove");
			},
			shown:function(options){//绑定事件
				var _options=options;
				//阻止触摸事件
				var _el=_options.$target.find("#menu a");
				var mask = $("body").find("div[type='modal']");
				mask.on("touchmove",function(ev){
					ev.preventDefault();
				});
				if (_el.length){
					$(_el).on("click",function(ev){
						var _index=$(ev.target).attr("data-menuid");
						var _action=_options.data.list[_index].action;
						if (_action)
							_action();
						hide();
						
					});
				}
			}
		});
	};
	function hide(){
		Mask.Popup.hide();
	}
	
	return {
		show : show,
		hide : hide
	}
});