/**
 * 
 */

define(['jquery','csrf'],function ($) {
	var constantMap = {};
	//var dfd = $.Deferred();
	(function(){
		$.ajax({
			url:contextPath+"/messageDefine",
			type:"post",
			dataType:"json",
			data:"",
			async:false,
			success:function(result){
				console.log("contant result："+result);
				constantMap = result;
				//申请授权
				constantMap.STATUS_WAIT = 1;
				//授权通过
				constantMap.STATUS_PASS = 2;
				//授权被拒绝
				constantMap.STATUS_REJECT = 3;
				//授权被取消
				constantMap.STATUS_CANCEL = 4;
				//dfd.resolve();
				//mess为数组
				/*constant.formatMessage=function(key,mess){
					$.each(mess, function(i, m){
						var r = new RegExp('{'+i+'}', 'i');
						key.replace(r,m);
					});
					return key;
				}*/
				//return constant;
			}
		});		
	})();
	return {
		messageMap:constantMap,
		formatMessage:function(key,formatList){
			var resultStr = "";
			try{
				if(formatList instanceof String){
					throw new Error('参数formatList不是数组');
				}
				if(key instanceof Array){
					throw new Error('参数key不是字符类型');
				}
				$.each(formatList, function(cnt, m){
					//var regStr = "\{"+cnt+"\}";
					var reg = new RegExp("\\{"+cnt+"\\}", "i");
					resultStr = key.replace(reg,m);
				});
				return resultStr;
			}catch(err){
				console.log(err.message);
			}
		}
	}
});