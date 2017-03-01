Page({
	#foreach($function in $functions)$function:function(){
		var _deferred=can.ajax({
			url:"$page/$function",
			type:"POST",
			contentType:"application/json", 
			dataType : "json",
			data : JSON.stringify(this.data)
		});
		var _self=this;
		_deferred.then(function(data){
			_self.update(data);
		},function(){
			alert("远程调用失败");
		});
	},#end
	#if($data)onData:function(){
		return $data;
	}#end
});