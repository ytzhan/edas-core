Page({
	#foreach($function in $functions)$function:function(){
		this.update(ajax("$page/$function",this.data));
	},#end
	#if($data)onData:function(){
		return $data;
	}#end
});