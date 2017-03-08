Page({
	#entityEvents(),
	onData:function(){
		return #remote("first/find").get()#end;
	},
})