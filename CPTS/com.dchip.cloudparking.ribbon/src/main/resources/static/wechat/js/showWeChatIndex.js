define(['jquery', 'csrf', 'jweixin', 'weui', 'LoadPageFront'],function($, csrf, wx, $_, LoadPageFront){
	var linkUrl;
	var indexUrlParma;
	$.post(contextPath+"/getLinkUrl",{},function(url){
		linkUrl = url;
		if(linkUrl){
			LoadPageFront(linkUrl);
		}else if(showFlag){  //初始化跳转标志
			showFlag = parseInt(showFlag);
			 switch(showFlag)
			 {
				 case 1:  //无牌入场
					 indexUrlParma = "&roadWayId=" + roadWayId;
					 LoadPageFront(contextPath + "/unlicensedEntry?openid=" + openid + indexUrlParma);
				   break;
				 case 2:   //无牌出场
					 indexUrlParma = "&roadWayId=" + roadWayId;
					 LoadPageFront(contextPath + "/unlicensedOut?openid=" + openid + indexUrlParma);
				   break;
				 case 3:   //有牌出场
					 indexUrlParma = "&parkingInfoId=" + parkingInfoId + "&type=" + type;
					 LoadPageFront(contextPath + "/haveACardPayPage?openid=" + openid + indexUrlParma);
				   break;
				 case 4:   //微信扫码出场
					 indexUrlParma = "&parkingId=" + parkingId;
					 LoadPageFront(contextPath + "/openByLicensePlate?openid=" + openid + indexUrlParma);
				   break;
				 case 5:   //支付宝扫码出场
					 indexUrlParma = "&parkingId=" + parkingId;
					 LoadPageFront(contextPath + "/selectPaymentMethod?openid=" + openid + indexUrlParma);
				   break;
				 default:  //其他处理
					 indexUrlParma = "&parkingInfoId=" + parkingInfoId + "&type=" + type;
					 LoadPageFront(contextPath + "/selectPaymentMethod?openid=" + openid + indexUrlParma);
			 }
		}
		
	});
	
	//微信接口配置
	/*wx.config({
		//debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		appId : 'wxc84679c3bcc8d70f',
		timestamp : parseInt(mapInfo.timestamp, 10), // 必填，生成签名的时间戳
		nonceStr : mapInfo.nonceStr, // 必填，生成签名的随机串
		signature : mapInfo.signature, // 必填，签名
		jsApiList : ['hideAllNonBaseMenuItem', 'scanQRCode', 'openLocation', 'getLocation']
	// 必填，需要使用的JS接口列表
	});
	
	wx.ready(function() {
		//隐藏微信分享菜单
		wx.hideAllNonBaseMenuItem();
	});
	
	wx.error(function(res) {
		console.log("调用微信jsapi返回的状态:" + res.errMsg);
		$.alert("发生未知错误，请重新进入",function(){
			wx.closeWindow();	//关闭当前页面
		});
	});*/
	
	
});