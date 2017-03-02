Page({
	#entityEvents(),
	onData:function(){
		return #defaultEntity();
	},
	onAbcChanged:function(){
		//手写远程调用
		#json("/page1/rpc"){
			alert("Ok");
		}#end
	}
});