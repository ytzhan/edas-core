Page({
	#entityEvents(),
	onData:function(){
		return #remote("detail/find").post(this.param.id)#end;
	},
	onBackClick:function(){
		switchPage("policy/index");
	},
	chose123:{
		zh:[{key:"香洲",value:"xz"},{key:"金湾",value:"jw"}],
	 		sz:[{key:"南山",value:"ns"},{key:"福田",value:"ft"},{key:"罗湖",value:"lh"}]
   	}
});