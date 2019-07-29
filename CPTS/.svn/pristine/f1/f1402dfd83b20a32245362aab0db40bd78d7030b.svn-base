/**
 * 
 */

requirejs.config({
	baseUrl:contextPath,
    paths: {
        jquery: 'wechat/thirdplugin/jquery.min',
        weui: 'wechat/thirdplugin/jquery-weui.min',
        domReady: 'wechat/thirdplugin/domReady',
        csrf: 'wechat/thirdplugin/csrf',
        constant: 'wechat/thirdplugin/messageUtil',
        until:'wechat/thirdplugin/until',
        async:'wechat/thirdplugin/async',
		jweixin: 'wechat/thirdplugin/jweixin-1.2.0',
		BMap: 'http://api.map.baidu.com/api?v=2.0&ak=FGPuiHQ9DBEluA5xrqrtuQobSMoZmocp',
		LoadPageFront:'../wechat/thirdplugin/LoadPageFront'
	},
	shim:{
		 csrf:{
			 deps:['jquery'],
			 exports:"Csrf"
		 },
		 BMap: {
		     deps: ['jquery'],
		     exports: 'BMap'
		 },
		 weui: {
		     deps: ['jquery'],
		     exports: 'jquery-weui.min'
		 },
		 LoadPageFront: {
		     deps: ['jquery','weui'],
		     exports: 'LoadPageFront'
		 }
		 
	},
	waitSeconds:0,
	urlArgs: "v=" +  (new Date()).getTime()
});
