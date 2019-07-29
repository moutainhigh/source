/**
 * 
 */

requirejs.config({
	baseUrl:contextPath,
    paths: {
        jquery: 'thirdplugin/jquery.min',
        domReady: 'thirdplugin/domReady',
        csrf: 'thirdplugin/csrf',
        constant: 'thirdplugin/messageUtil',
        until:'thirdplugin/until',
        jqueryUi:'thirdplugin/jquery-ui',
		async:'thirdplugin/async',
		BMap: 'http://api.map.baidu.com/api?v=2.0&ak=FGPuiHQ9DBEluA5xrqrtuQobSMoZmocp',
		echarts:'thirdplugin/echarts.min',
		table2excel:'thirdplugin/jquery.table2excel',
		qiniuUpload:'thirdplugin/qiniuUpload',
		sparkMd5:'thirdplugin/spark-md5',
		Base64: 'thirdplugin/base64',
		layui: 'thirdplugin/layui/layui',
		loadPage:'thirdplugin/LoadPage',
		ztree: 'thirdplugin/zTree_v3/js/jquery.ztree.all',
		translate: 'thirdplugin/translate'
	},
	shim:{
		 csrf:{
			 deps:['jquery'],
			 exports:"Csrf"
		 }, 
	    jqueryUi: {
	        deps: ['jquery'],
	        exports: 'JqueryUi'
	    },
	    BMap: {
	        deps: ['jquery'],
	        exports: 'BMap'
	    },
	    sparkMd5: {
	        deps: ['jquery'],
	        exports: 'sparkMd5'
	    },
	    qiniuUpload: {
	        deps: ['sparkMd5'],
	        exports: 'qiniuUpload'
	    },
	    ztree: {
	    	deps: ['jquery'],
	        exports: 'ztree'
	    }
	   
	},
	waitSeconds:0,
	urlArgs: "v=" +  (new Date()).getTime()
});