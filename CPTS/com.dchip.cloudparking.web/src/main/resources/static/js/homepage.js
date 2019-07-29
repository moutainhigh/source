define(['jquery',"echarts",'async!BMap','layui','csrf'],function($,echarts){	
	if(typeof(points) == "string"){  //如果是字符串 则转成json对象
		points = $.parseJSON(points);
	}	
	var map = new BMap.Map("homepageMap");
	//当前点
	var currentPoint = points.length>0?new BMap.Point(points[0].longitude,points[0].latitude):new BMap.Point(113.604968,22.370864);
	var level = getLevel(map,points); 
	map.centerAndZoom(currentPoint,level); 
	//定位一个点
	map.panTo(currentPoint);  
	
	$.each(points, function(i, point){
		//添加标注
		var drawPoint = new BMap.Point(point.longitude,point.latitude);
		var geoc = new BMap.Geocoder(); 
		
		addMarker(map,drawPoint,geoc,point.status,point.parkName);
	});
	
	var parkingIncome = "停车场收入折线图";
	var cardIncome = "月卡收入折线图";
	var appUserRegister = "APP会员注册折线图";
	var parkingUserRegister = "停车场会员注册折线图";
	if(i18n1 != null && i18n1 == "en_US"){
		parkingIncome = "Breakdown chart of parking income";
		cardIncome = "Breakdown chart of monthly card income";
		appUserRegister = "Breakdown chart of APP user register";
		parkingUserRegister = "Breakdown chart of parking user register";
	}else if(i18n1 != null && i18n1 == "zh_TW"){
		parkingIncome = "停車場收入折線圖";
		cardIncome = "月卡收入折線圖";
		appUserRegister = "APP會員註冊折線圖";
		parkingUserRegister = "停車場會員註冊折線圖";
	}
	if(type != 1){//1-系统管理员,2-公司管理员,3-停车场管理员
		
		if(allAccountType != 4){
			$(".parkingIncomeEchart").removeClass("none");//停车场收入折线图
			$(".cardIncomeEchart").removeClass("none");//月卡收入折线图
		}
		$.ajax({
			url:contextPath+"/order/getOrderData",
			type:"post",
			dataType:"json",
			data:{
				companyId:companyId
			},
			success:function(result) {
				showChart('parkingIncome',result.data,'#47505f',parkingIncome);//浅绿色
			} 
		});
		 $.ajax({
		 	url:contextPath+"/order/getCardData",
		 	type:"post",
		 	dataType:"json",
		 	data:{
		 		companyId:companyId
		 	},
		 	success:function(result) {
		 		showChart('cardIncome',result.data,'#47505f',cardIncome);//浅绿色
		 	}
		 });
	}else{
		$(".charts").removeClass("none");
		$(".parkingMemberEchart").removeClass("none");
		//查询admin图表数据
		$.ajax({
		 	url:contextPath+"/user/getChartData",
	        type:"post",
	        dataType:"json",
	        data:{},
	        success:function(result) {
	        	showChart('userMemberEcharts',result.data,'#dccc83',appUserRegister);//青色
	        } 
	    });
		$.ajax({
		 	url:contextPath+"/parking/getChartData",
	        type:"post",
	        dataType:"json",
	        data:{},
	        success:function(result) {
	        	showChart('parkingMemberEcharts',result.data,'#3fa7dc',parkingUserRegister);//浅绿色
	        } 
	    });
	}

	map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放

	//以下为定义的一些函数
	
	//展现表格，id:html元素id , datas:数组数据 ,rgb:颜色
	function showChart(id,datas,rgb,heading){ 
		var echart = echarts.init(document.getElementById(id));
		
		//var startDate = +new Date(2018, 10, 3); //月和日从0开始
		//var oneDay = 24 * 3600 * 1000;
		var dates = new Array();//横坐标名称 时间轴
		var data = new Array();//纵坐标数据，每一个下标对应一个横坐标，其值为纵坐标的值
		
		//添加横坐标时间轴(date)与纵坐标(data)

			for (var i = 0; i <datas.length; i++) { //每循环一次为一天
			    //var now = new Date(startDate += oneDay);//毫秒		    
			    //var nowTemp = [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/');//格式化的时间 
			   var nowTemp = datas[i].date; 
			    
			    dates.push(nowTemp);	//时间轴添加一个格式化后的时间数据
		    	var v = "";//判断有没有对应的数据  
		    	var len = datas.length;
		    	for(var j = 0; j < len; j++) {
		    		if(datas[j].date == nowTemp){
				    	v = datas[j].count;	
				    	break;
				    }
		    	}
			    if(v != ""){
			    	data.push(v);
			    }else{
			    	//如果没有对应的纵坐标数据，则给0
					data.push(0);
				}
			}
		
		option = {
				title: {
					text: heading,
					fontSize: 50
				},
			    tooltip: {
			        trigger: 'axis',
			        position: function (pt) {
			            return [pt[0], '10%'];
			        }
			    },
			   /* grid: {
			    	top: '50'
			    },*/
			    dataZoom: [
			        {
			            start: 0,
			            end: 100,
			            handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
			            handleSize: '80%',
			            handleStyle: {
			                color: '#fff',
			                shadowBlur: 3,
			                shadowColor: 'rgba(0, 0, 0, 0.6)',
			                shadowOffsetX: 2,
			                shadowOffsetY: 2
			            }
			        }
			    ],
			    xAxis: {
			        type: 'category',
			        data: dates
			    },
			    yAxis: {
			        type: 'value'
			    },
			    series: [{
			        data: data,
			        type: 'line',
			        smooth: true,
			        itemStyle: {
		                color: rgb
			        }
			    }]
		};
		echart.hideLoading();
		echart.setOption(option);
        $(window).resize(function () {          //当浏览器大小变化时
            echart.resize();
        });
	} 

	
	//坐标点创建标注覆盖物   -1:停用   0：正常 
	function addMarker(map,point,geoc,state,parkname){
		var marker;// 标记点
		if(state != null){
			var myIcon;
			if(state == -1){ 
				myIcon = new BMap.Icon(contextPath+"/images/parking_icon_red.png", new BMap.Size(20,20));
			}else {
				myIcon = new BMap.Icon(contextPath+"/images/parking_icon_blue.png", new BMap.Size(20,20));
			}				
			marker = new BMap.Marker(point,{icon:myIcon});  // 有图标的标记
		}else{
			 marker = new BMap.Marker(point);// 无图标的标记
		}
		map.addOverlay(marker);              // 将标记添加到地图中
		marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动
		createMessWindow(map,point,marker,geoc,parkname);//跳到另一个方法
	}
	
	//坐标点创建信息窗口
	function createMessWindow(map,point,marker,geoc,parkname){
		marker.addEventListener("click", function(e){//标记点添加事件监听   
			var pt = point;
			geoc.getLocation(pt, function(rs){
				var addComp = rs.addressComponents;
				map.openInfoWindow(new BMap.InfoWindow("<b>地址：</b>"+addComp.province +addComp.city+addComp.district+addComp.street+addComp.streetNumber, {
					  width : 200,     // 信息窗口宽度
					  height: 80,     // 信息窗口高度
					  title : "<h4 style='margin:0 0 5px 0;padding:0.2em 0'><b>"+parkname+"</b></h4>" 
					  /*title : "<h4 style='margin:0 0 5px 0;padding:0.2em 0'><b>"+rs.business+"</b></h4>"*/ 
					}),pt); //开启信息窗口
			});        
		});
	}

	//根据原始数据计算中心坐标和缩放级别，并为地图设置中心坐标和缩放级别。  
	function getLevel(map,pointsObject){ 
		//points只保留pointsObject对象里的经纬度信息
		var points = new Array();
		var point = new Object();
		pointsObject.forEach(function(o,i){
			point.longitude = o.longitude;
			point.latitude = o.latitude;
			points.push(point);
		});
	    if(points.length>0){  
	    	 var lngArr = $.map(points, function(n){
	   		  	return n.longitude;
	    	 });
	    	 var latArr = $.map(points, function(n){
	   		  	return n.latitude;
	    	 });
	   	 
	    	var maxLng = Math.max.apply(null,lngArr);  
	        var minLng = Math.min.apply(null,lngArr);  
	        var maxLat = Math.max.apply(null,latArr);  
	        var minLat = Math.min.apply(null,latArr);
	        var cenLng =(parseFloat(maxLng)+parseFloat(minLng))/2;  
	        var cenLat = (parseFloat(maxLat)+parseFloat(minLat))/2;  
	        var zoom = getZoom(map,maxLng, minLng, maxLat, minLat);  
	        if(zoom > 15){ //设置最小缩放等级为15
	        	zoom = 15;
	        }
	        return zoom;
	        //map.centerAndZoom(new BMap.Point(cenLng,cenLat), zoom);    
	    }else{ 
	    	return 5;
	        //没有坐标，显示全中国  
	        map.centerAndZoom(new BMap.Point(103.388611,35.563611), 5);    
	    }   
	} 

	//根据经纬极值计算绽放级别。  
	function getZoom (map,maxLng, minLng, maxLat, minLat) {  
	    var zoom = ["50","100","200","500","1000","2000","5000","10000","20000","25000","50000","100000","200000","500000","1000000","2000000"]//级别18到3。  
	    var pointA = new BMap.Point(maxLng,maxLat);  // 创建点坐标A  
	    var pointB = new BMap.Point(minLng,minLat);  // 创建点坐标B  
	    var distance = map.getDistance(pointA,pointB).toFixed(1);  //获取两点距离,保留小数点后两位  
	    for (var i = 0,zoomLen = zoom.length; i < zoomLen; i++) {  
	        if(zoom[i] - distance > 0){  
	            return 18-i+3;//之所以会多3，是因为地图范围常常是比例尺距离的10倍以上。所以级别会增加3。  
	        }  
	    };  
	} 
	
	

});

