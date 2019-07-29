define([ 'domReady', 'jquery', 'weui',  ], function(doc, $, $_) {
	$.toast.prototype.defaults.duration = 1000; //设置提示消息为1秒，默认2秒
	var totalFee;
	var parkingInfoId;
	var parkingLen;
	//结账
	$(document).off("click", "#reckoningBtn").on("click", "#reckoningBtn", function(){
		var licensePlate = $("#licensePlateInp").val();
		if(!licensePlate){
			$.toast("车牌不能为空", "forbidden");
			return ;
		}
		var re = /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$/;
		if(licensePlate.search(re) == -1) {
			$.toast("车牌格式错误", "forbidden");
			return false;
		} else {
			rechoningOpearte(licensePlate);
		}
	});
	
	//结账按钮
	function rechoningOpearte(licensePlate){
		$.ajax({
			type: 'post',
			url: contextPath + "/rechoningOpearte",
			dataType: 'json',
			data:{
				parkingId: parkingId,
				licensePlate: licensePlate
			},
			complete:function(XHR, TS){
				var returnObj = eval('('+ XHR.responseText +')');
				if(returnObj.code != 200){
					$(".show-null-input").html(returnObj.msg).removeClass("none");
				}else{
					$(".show-null-input").addClass("none");
					$(".show-details-pay-box").removeClass("none");
					
					var showData = returnObj.data;
					$("#showTotalFeeO").html(showData.totalFee);
					$("#showParkingLenO").html(showData.showParkingLen);
					$("#showOutDateO").html(showData.outDate);
					$("#showLicensePlateO").html(showData.licensePlate);
					$("#showfreeTimeO").html(showData.freeTime + "分钟");
					if(showData.totalFee == 0){
						$("#showPayBtnBox").addClass("none");
					}else{
						$("#showPayBtnBox").removeClass("none");
					}
					if(showData.showfree == true){
						$("#showFreeOutBox").removeClass("none");
						$(".show-pay-flag-box").addClass("none");
					}else{
						$("#showFreeOutBox").addClass("none");
						$(".show-pay-flag-box").removeClass("none");
					}
					//赋值
					parkingInfoId = showData.parkingInfoId;
					totalFee = showData.totalFee;
					parkingLen = showData.parkingLen;
					
				}
			}
		});
	}
	
	 $(document).ready(function(){
	        $("#closeButton").click(function() {
	           AlipayJSBridge.call('closeWebview');
	        });
	     });

	    // 由于js的载入是异步的，所以可以通过该方法，当AlipayJSBridgeReady事件发生后，再执行callback方法
	    function ready(callback) {
	         if (window.AlipayJSBridge) {
	             callback && callback();
	         } else {
	             document.addEventListener('AlipayJSBridgeReady', callback, false);
	         }
	    }

	    function tradePay(tradeNO) {
	        ready(function(){
	             // 通过传入交易号唤起快捷调用方式(注意tradeNO大小写严格)
	             AlipayJSBridge.call("tradePay", {
	                  tradeNO: tradeNO
	             }, function (data) {
	                 log(JSON.stringify(data));
	                 if ("9000" == data.resultCode) {
	                     log("支付成功");
	                 }
	             });
	        });
	    }
	
	$("#wechatPayBtn").click(function(){
		var outTradeNo;
		if(totalFee != 0){
			$.ajax({
		        type : "post",
				url : contextPath + "/Alipay/AlipayMethod",
		        dataType : "json",
		        data : {
		        	openid: parkingInfoId,
		        	totalFee: totalFee,  
		        	//totalFee: 0.01, 
		        	parkingInfoId: parkingInfoId,
		        	parkingLen: parkingLen,
		        	authCode:authCode,
		        	body : "停车收费"
		        },
				complete : function(XHR, TS) {
					var returnObj = eval('('
							+ XHR.responseText + ')');
					/*if (returnObj.code != 0) {
						$.alert(returnObj.msg, "", function() {
							  //点击确认后的回调函数
						});
						return;
					}*/
/*	        		appId = returnObj.data.appId;
	        		paySign = returnObj.data.paySign;
	        		timeStamp = returnObj.data.timeStamp;
	        		nonceStr = returnObj.data.nonceStr;
	        		packageStr = returnObj.data.packageStr;
	        		signType = returnObj.data.signType;*/
					tradeNo = returnObj.data.msg; 
	        		tradePay("${tradeNO}");
	        		console.log(tradeNo);
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
	
	$(document).off("click","#closeWindows").on("click", "#closeWindows", function(){
		wx.closeWindow();	//关闭当前页面
	});
});