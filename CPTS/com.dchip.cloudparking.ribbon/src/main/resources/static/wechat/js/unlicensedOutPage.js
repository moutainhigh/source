/**
 * 出场微信支付页面
 * @returns
 */
define([ 'domReady!', 'jquery', 'weui', 'jweixin', 'csrf' ], function(doc, $, $_, wx) {
	var showSuccessBoxFlag = 0;
	$("#showTotalFee").html(totalFee +"元");
	$("#showParkName").html(parkName);
	$("#showParkingLen").html(showParkingLen);
	$("#showOutDate").html(outDate);
	$("#showParkDate").html(parkDate);
	$("#showParkName").html(parkName);
	$(document).off("click","#closeWindows").on("click", "#closeWindows", function(){
		wx.closeWindow();	//关闭当前页面
	});
	
	
	$("#openBrakeBtn").click(function(){
		$.ajax({
	        type : "post",
			url : contextPath + "/payment/openBrake",
	        dataType : "json",
	        data : {
	        	parkingInfoId: parkingInfoId
	        },
			complete : function(XHR, TS) {
				var returnObj = eval('('
						+ XHR.responseText + ')');
				document.getElementById("noPayshowBox").classList.add("none");
				document.getElementById("showSuccessContent").classList.remove("none");
        		
			}
	    });
	});
	
	$("#wechatPayBtn").click(function(){
		if(totalFee != 0){
			$.ajax({
		        type : "post",
				url : contextPath + "/payment/jspay",
		        dataType : "json",
		        data : {
		        	openid: openid,
		        	//totalFee: totalFee,  
		        	totalFee: 0.01, 
		        	parkingInfoId: parkingInfoId,
		        	parkingLen: parkingLen,
		        	body : "停车收费"
		        },
				complete : function(XHR, TS) {
					var returnObj = eval('('
							+ XHR.responseText + ')');
					if (returnObj.code != 0) {
						$.alert(returnObj.msg, "", function() {
							  //点击确认后的回调函数
						});
						return;
					}
	        		appId = returnObj.data.appId;
	        		paySign = returnObj.data.paySign;
	        		timeStamp = returnObj.data.timeStamp;
	        		nonceStr = returnObj.data.nonceStr;
	        		packageStr = returnObj.data.packageStr;
	        		signType = returnObj.data.signType;
	        		outTradeNo = returnObj.outTradeNo; 
	                callpay();
				}
		    });
		}
	});
	
	function onBridgeReady() {
		WeixinJSBridge.invoke('getBrandWCPayRequest', {
			"appId" : appId, // 公众号名称，由商户传入
			"paySign" : paySign, // 微信签名
			"timeStamp" : timeStamp, // 时间戳，自1970年以来的秒数
			"nonceStr" : nonceStr, // 随机串
			"package" : packageStr, // 预支付交易会话标识
			"signType" : signType   // 微信签名方式
		}, function(res) {
			if (res.err_msg == "get_brand_wcpay_request:ok") {
				$.toast("支付成功");
				//支付成功，调用开闸，并显示成功页面
				setTimeout(function(){
					$(".show-pay-box").addClass("none");
	                $("#showSuccessContent").removeClass("none");
				},1000);
			} else {
				if (res.err_msg == "get_brand_wcpay_request:cancel") {
					$.toast("支付取消", "cancel");
				} else if (res.err_msg == "get_brand_wcpay_request:fail") {
					$.toast("支付失败", "cancel");
				} else {
					$.toast("支付失败", "cancel");
				}
                /*$.ajax({
        	        type : "post",
        			url : contextPath + "/payment/delPayLog",
        	        dataType : "json",
        	        data : {
        	        	outTradeNo : outTradeNo
        	        },
        			complete : function(XHR, TS) {
        				var returnObj = eval('('
        						+ XHR.responseText + ')');
        				if (returnObj.code != 200) {
        				}
        				return;
        			}
        	    });*/
                
			}
			
			
			
		});
	}
	
	function callpay() {
        if (typeof WeixinJSBridge == "undefined") {
            if (document.addEventListener){
                document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
            } else if (document.attachEvent){
                document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
                document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
            }
        } else {
            onBridgeReady();
        }
    }
	
});