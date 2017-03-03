Page({
	#entityEvents(),
	onData:function(){
		return #defaultEntity();
	},
	onAbcChanged:function(){
		//手写远程调用
		#remote("/page1/rpc").post({a:23},function(_data){
			alert("Ok");
		});#end
	},
	onEntityChanged:function(){
		#entity("page1/entity"){
			alert("ok2");
		}#end;
	}
});