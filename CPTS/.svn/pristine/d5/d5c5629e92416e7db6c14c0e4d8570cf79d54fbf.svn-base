/**
 * 微信端load页面
 */

function LoadPageFront(url,paramJson) {
	$.showLoading('加载中'); //显示Loading
	$("#showLoadPageBox").load(url, paramJson||{},function(data){
		
		//传入url在session中
		$.post(contextPath+"/setLinkUrl", { linkUrl:url} );
		
		setTimeout(function() {
			  $.hideLoading();
			  $(".weui-toast.weui_loading_toast").remove();
		}, 100); //关闭Loading
		
		//汉堡键里面按钮控制
		if(url.indexOf("showParkingMapIndex") > -1){  //是首页，显示扫码
			$(".show-scan-box").removeClass("none");
			$(".show-home-box").addClass("none");
		}else{  //不是首页，隐藏扫码
			$(".show-scan-box").addClass("none");
			$(".show-home-box").removeClass("none");			
		}
		
		//汉堡键隐藏显示
		if((url.indexOf("/phoneBinding?openid") > -1)||
				(url.indexOf("/parkingInfo?userId") > -1)){ //绑定手机号码、停车详情、选择车位、欠费页面
			$(".show-hamburger-box").addClass("none");
		}else{
			$(".show-hamburger-box").removeClass("none");
		}
		
    	if(isJSON(data)||Object.prototype.toString.call(data)  === '[object Object]'){
    		var obj = $.parseJSON(data);
    		if(!obj.success){
    			if(obj.code == 403){
    				$.get(contextPath+"/error/"+obj.code+".html", function(data){
    					$("#showLoadPageBox").load($(data).find("#butnDiv").remove().find("body").html());
    				});
    			}else{
    				$("#showLoadPageBox").load(contextPath+"/error/"+obj.code+".html");
    			}
    		}
    	}
 	});
}

function isJSON(str) {
    if (typeof str == 'string') {
        try {
            var obj=JSON.parse(str);
            if(typeof obj == 'object' && obj ){
                return true;
            }else{
                return false;
            }

        } catch(e) {
            //console.log('error：'+str+'!!!'+e);  因一直console页面出来，故注释    --leif    2018/06/25
            return false;
        }
    }
    console.log('It is not a string!')
}
