{
	isBuild:true,
	// baseUrl : ".",
	paths : {
		mzui : "../../lib/mzui.min",
		canjs : "../../lib/can.zepto.min",
		canjsStache : "../../lib/can.stache",
		canjsValidation:"../../lib/can.map.validations",
		mobiscroll: "../../lib/mobiscroll.custom-3.0.0-beta6.min",
		myFramework : "../../myFramework",
		text : "../../requirejs/text",// 文本加载器
		app : "../../myFramework/loader/AppLoader", // 自定义加载器
		html : "../../myFramework/loader/HtmlPageLoader" ,// 自定义加载器
		stache : "../../myFramework/loader/StachePageLoader", // 自定义加载器
		boot : "../../myFramework/Booter" // 自定义加载器
	},
	config : {
		text : {

		},
		app : {

		},
		html : {

		},
		stache : {

		},
		boot : {

		}
	},
	stubModules : ['text','stache','boot','html','app'],
	wrap: {
		startFile: "../../requirejs/require.js"
	},
	preserveLicenseComments: false,
	name:"index.js",
	out:"tpframework.js"
}