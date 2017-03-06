Page({
	#entityEvents(),
	onData:function(){
		return #remote("detail/find").post(this.param.id)#end;
	},
	onBackClick:function(){
		switchPage("policy/index");
	},
	codeTable:{
		citys:{zh:[{label:"香洲",value:"xz"},{label:"金湾",value:"jw"}],
	 		sz:[{label:"南山",value:"ns"},{label:"福田",value:"ft"},{label:"罗湖",value:"lh"}]}
   	},
});