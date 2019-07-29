/**
 * 无牌车入场页面
 * @returns
 */
define([ 'domReady!', 'jquery', 'weui', 'jweixin', 'csrf' ], function(doc, $, $_, wx) {
	var showSuccessBoxFlag = 0;
	$("#showParkName").html(parkName);
	$("#showFreeTimeLength").html(freeTimeLength+"分钟");
	$("#showHourCost").html(hourCost+"元");
	$("#showParkDate").html(parkDate);
	$(document).off("click","#closeWindows").on("click", "#closeWindows", function(){
		wx.closeWindow();	//关闭当前页面
	});
	
	
	$("#openBrakeBtn").click(function(){debugger
		var licensePlate = $("#licensePlateInp").val();
		$.ajax({
	        type : "post",
			url : contextPath + "/payment/openBrake",
	        dataType : "json",
	        data : {
	        	parkinginfoId: parkinginfoId,
	        	licensePlate:licensePlate
	        },
			complete : function(XHR, TS) {
				var returnObj = eval('('
						+ XHR.responseText + ')');
				document.getElementById("noPayshowBox").classList.add("none");
				document.getElementById("showSuccessContent").classList.remove("none");
        		
			}
	    });
	});
	
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