/**
 * 显示首页地图
 */
define(['domReady!', 'jquery', 'jweixin','weui'],function(doc, $, wx,$_){

	
	
	//进入页面就启用的回调函数
	wx.ready(function() {
		
		/*//获取微信定位
		wx.getLocation({
			type : 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
			success : function(res) {
				latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
				longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
				var speed = res.speed; // 速度，以米/每秒计
				var accuracy = res.accuracy; // 位置精度
				//GPS坐标
				var x = longitude;
				var y = latitude;
				var ggPoint = new BMap.Point(x, y);
				positionFlfg = 1;
				
				//坐标转换完之后的回调函数
				translateCallback = function(data) {
					if (data.status === 0) {
						nowMarker = new BMap.Marker(data.points[0]);
						latitude = data.points[0].lat;
						longitude = data.points[0].lng;
						++parkingNum;
						var nowPosition = new BMap.Point(longitude, latitude);
						pointArray[parkingNum] = nowPosition;
						map.centerAndZoom(nowPosition, 18);
						//map.setViewport(pointArray);
						map.addOverlay(nowMarker);
						var newGeoc = new BMap.Geocoder();
						map.setCenter(data.points[0]);
						newGeoc.getLocation(nowPosition, function (rs) {   //getLocation函数用来解析地址信息，分别返回省市区街等 r.point里有经纬度  
			                var addComp = rs.addressComponents;  
			                 city = addComp.city;//获取城市  
			                 var getParkingListUrl = contextPath+"/getParkingListByCityName?cityName=" + city;
								$.get(getParkingListUrl, function(data) {
									parkingList = data;
									showParkingListMap();
								});
			            }); 

					}
				}

				setTimeout(function() {
					var convertor = new BMap.Convertor();
					var pointArr = [];
					pointArr.push(ggPoint);
					convertor.translate(pointArr, 1, 5, translateCallback);
				}, 500);
			},
			cancel : function(res) {
				console.log('用户拒绝授权获取地理位置');
				$.alert("请重新进入页面，获取当前位置");
				$("#showReturn").css("display", "none");
			},
			complete : function() {

			}
		});*/
		
	
		
		
	});
	
	
	
	//微信发生错误回调函数
	wx.error(function(res) {
		console.log("调用微信jsapi返回的状态:" + res.errMsg);
		$.alert("发生未知错误，请重新进入",function(){
			wx.closeWindow();	//关闭当前页面
		});
	});
	
	
	
	//地图点击事件，隐藏停车场信息
	map.addEventListener("click", function(){
		$(".parking-details-box").addClass("none");
		$(".init-parking-details-box").html(initContext);
		$(".show-disable-parking-box").addClass("none");
	});
	
	//页面点击事件，隐藏停车场信息
	$("body").click(function(){
		$(".parking-details-box").addClass("none");
		$(".init-parking-details-box").html(initContext);
		$(".show-disable-parking-box").addClass("none");
	});
	
	
	//暂停使用停车场div块点击事件
	$(".show-disable-parking-box").click(function(){
		$(this).addClass("none");
	});
	
	
	
	//阻止用户双击使屏幕上滑
	var agent = navigator.userAgent.toLowerCase();        //检测是否是ios
	var iLastTouch = null;                                //缓存上一次tap的时间
	if (agent.indexOf('iphone') >= 0 || agent.indexOf('ipad') >= 0)
	{
	    document.body.addEventListener('touchend', function(event)
	    {
	        var iNow = new Date()
	            .getTime();
	        iLastTouch = iLastTouch || iNow + 1 /** 第一次时将iLastTouch设为当前时间+1 */ ;
	        var delta = iNow - iLastTouch;
	        if (delta < 500 && delta > 0)
	        {
	            event.preventDefault();
	            return false;
	        }
	        iLastTouch = iNow;
	    }, false);
	}
	
	//导航按钮事件
	$(document).off("click", ".show-location-map").on("click", ".show-location-map", function () {
		showLocation(this);
	});
	
	//调用扫一扫
	$(document).off("click", ".show-scan-box").on("click",".show-scan-box", function(){
		var scanFlag = 0;
		$(".show-hamburger-content-box").addClass("fadeOutDownBig animated  infinite");
		setTimeout(function() {
			$(".show-hamburger-content-box").removeClass("fadeOutDownBig animated  infinite").addClass("none");
		}, 400);
		wx.scanQRCode({
			needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
			scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
			success: function (res) {
				scanFlag = 1;
				var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
				if(result.indexOf("/parking/userAccredit") > -1){
					if(showNoOrderFlag == 1){
						LoadPageFront(contextPath + "/account?showAlert=1&openid=" + openid);  
					}else{
						var AreaIdAttr = result.split("=");
						var newArea = AreaIdAttr[1];
						LoadPageFront(contextPath + "/parkPlaceInfo?areaId=" + newArea + "&userId=" + userId);
					}
					
				}else{
					$.alert("请扫正确的停车场二维码");
				}
			}
		});
		
		
	});
	
	
	
	//打开微信内置地图
	function showLocation(obj) {
		
		var lat = obj.getAttribute("data-lat");
		var lng = obj.getAttribute("data-lng");
		var parkingName = obj.getAttribute("data-pname");
		var showPoint = new BMap.Point(lng, lat);
		//转换坐标
		BD09TransformationGCJ02(parkingName, showPoint);
		
	}
	
	//百度地图坐标转换为微信内置地图坐标
	function BD09TransformationGCJ02(parkingName, showPoint){
		var showLocationFlag = 0; //打开地图标记
		var bd_lon = showPoint.lng;
		var bd_lat = showPoint.lat;
		//转换百度坐标为腾讯坐标
	    qq.maps.convertor.translate(new qq.maps.LatLng(bd_lat,bd_lon), 3, function(res){
	        var latlng = res[0];
	        showGJCPoint = new BMap.Point(latlng.lng, latlng.lat);
	        setTimeout(function() {
				//使用微信内置地图查看位置接口  
				wx.openLocation({
					latitude : showGJCPoint.lat, // 纬度，浮点数，范围为90 ~ -90  
					longitude : showGJCPoint.lng, // 经度，浮点数，范围为180 ~ -180。  
					name : parkingName, // 位置名  
					address : '点击右侧图标，打开相应地图App，实时导航', // 地址详情说明  
					scale : 15,
					success : function(){
						showLocationFlag = 1;
					}
				// 地图缩放级别,整形值,范围从1~28。默认为最大  
				});
			}, 500);
			
			//判断是否成功打开扫一扫，并弹出提示
			setTimeout(function() {
				if(showLocationFlag == 0){
					$.alert("打开地图失败，请打开微信定位权限");
				}
			},1500);
	    });
	}
	
});

	