#Page(){
	onData:function(){
		return this.rpcs.find.post(this.param.id);
	},
	onBackClick:function(){
		switchPage("policy/index");
	},
	onSearchChanged:function(name,val,vm){
		alert(name+":"+val);
	},
	onCitysChanged:function(){
		var def = can.Deferred();
		def.resolve('{data:[{"label": "金湾", "value": "jw"},{"label": "香洲", "value": "xz"}]}');
		window[this.cmp]("#area").setSelection(def);
		
	},
	onChose1Changed:function(){
		var def = can.Deferred();
		def.resolve('{data:[{"label": "金湾", "value": "jw"},{"label": "香洲", "value": "xz"}]}');
		var _self  = window[this.cmp]("#chose2");
		window[this.cmp]("#chose2").setSelection(def);
		if($("#chose2").viewModel().mobi){
			$("#chose2").viewModel().mobi.init();
		}
	}
}#end
