requirejs(["text!myFramework/ui/Dialog.stache","myFramework/utils/Navigator"],function(tpl,Navigator){
	can.Component.extend({
		tag:"dialog",
		template:can.stache(tpl),
		viewModel:function(attrs,parentScope,el){	
			var _scope=parentScope;
			while (_scope.scope){
				_scope=_scope.scope;
			};
			var page = _scope.attr("page");
			var bg = page.opcity;
			var bg = bg == true ? "background:rgba(0,0,0,0.5)" : undefined;
			var	closebtn = page.closeBtn == false ? "none":undefined;
			return {
				page:page,
				title:page.title,
				close :closebtn,
				height:page.height,
				width:page.width,
				top:page.top,
				left:page.left,
				opcity:bg
			};
		},
		events:{
			"#_closeBtn_ click":function(ev){
				this.viewModel.attr("page").hide();
			},
			"inserted" : function(el, ev){
				$(el).css("display","inline-block");
			}
		}
	});
});