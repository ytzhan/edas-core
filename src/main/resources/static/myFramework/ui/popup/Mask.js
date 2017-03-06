define(["myFramework/MyExports"],function(exports){
	// 注入mzui
	var _Popup = new $.Display({});
	function show() {	
		_Popup.show({
			content : "<div class=\"loading loading-light gray\">加载中</div>",
			placement : "center",
			backdrop : true,
			display : "modal",
			backdropDismiss : false,
			autoHide : false,
			//preventDefault:true,
			//stopPropagation:true,
			animate:0,// 不执行动画
			duration:0	// 不执行动画

		});
	};

	function toast(message) {

		_Popup.show({
			content : message,
			placement : "center",
			backdrop : true,
			display : "modal",
			backdropDismiss : true,
			preventDefault:true,
			stopPropagation:true,
			autoHide : 1000,
			animate:100,// 执行动画
			duration:100,// 执行动画
			targetClass:"activeClass flex justify-center align-middle"
		});
	};

	function hide() {
		_Popup.hide();
	}

	exports.Mask.show=show;
	exports.Mask.toast=toast;
	exports.Mask.hide=hide;
	exports.Mask.Popup=_Popup;
	
	return exports.Mask;
});