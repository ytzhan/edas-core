Page({
	#entityEvents(),
	onData:function(){
		return #remote("detail/find").post(this.param.id)#end;
	},
	onBackClick:function(){
		switchPage("policy/index");
	},
   	#codeTable()
});