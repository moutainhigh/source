define(['jquery', 'layui', 'until','async!BMap'], function($, LAY, until, BMap1) {
	layui.use(['table','form','laydate'], function() {
		var roadways;//选择车道集合
		var roadwaysData = new Array();//车道的数据集合
		var table = layui.table;
		var form = layui.form;
		var oldParkName;
		if(i18n == 'en_US'){
			table.render({
				 elem : '#parkingSpaceTable',
				 url : contextPath+"/parkingSpace/rendering",
			     method : 'post',
		         cellMinWidth : 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
				 skin : 'line',
				 where : { //默认排序字段
						sortName: 'id',
					    direction: 'desc'
					},
			     cols : [[

				    	 { field: 'id', title:'停车场ID',align: 'center',sort: true, hide: true},
				    	 { field: 'companyName',title: 'companyName', sort: true},
				    	 { field: 'parkName', title: 'parkName', sort: true, align: 'center'},
					     { field: 'locations', title: 'locations', align: 'center',width: 300},
					     { field: 'spaceCount', title: 'spaceCount', sort: true, align: 'center',hide: true},
					     { field: 'comcatName', title: 'comcatName',  sort: true, align: 'center'},
					     { field: 'comcatPhone', title: 'comcatPhone', sort: true, align: 'center'},
					     { field: 'managerName', title: 'managerName', sort: true, align: 'center'},
					     { field: 'managerPhone', title: 'managerPhone', sort: true, align: 'center'},
					     { field: 'calculateDay',  title: 'calculateDay', sort: true, align: 'center'},
					     { field: 'status', title: 'status', sort: true, align: 'center',templet: function(data){
					    	 if(data.status == 0){
					    		 return '<font color="green">启用</font>';
					    	 }else if(data.status == -2){
		                         return '<font color="red">已删除</font>';
		                     }else {
		                         return '<font color="red">禁用</font>';
		                     }
					       }
					     },
					     /*{ field: 'freeTimeStatus', title: '免费时长是否加入收费', align: 'center', sort: true, templet: function(data){
								if(data.freeTimeStatus == 0){
									return "否";
								}else if(data.freeTimeStatus == 1){
									return "是";
								}else{
									return "";
								}
							}},*/
					     { fixed: 'right', title:'right', toolbar: '#parkingSpaceTableBarEnglish',width:roleType==1?300:220,align: 'center'}
				     
			     ]],
			 response : {
			        countName: 'totalElements', //规定数据总数的字段名称，默认：count
			         dataName: 'content' //规定数据列表的字段名称，默认：data
			     }, 
				 page : true
			}); 
		}else{
			table.render({
				 elem : '#parkingSpaceTable',
				 url : contextPath+"/parkingSpace/rendering",
			     method : 'post',
		        cellMinWidth : 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
				 skin : 'line',
				 where : { //默认排序字段
						sortName: 'id',
					    direction: 'desc'
					},
			     cols : [[
		
				    	 { field: 'id', title:'停车场ID',align: 'center',sort: true, hide: true},
				    	 { field: 'companyName',title: '公司名称', sort: true},
				    	 { field: 'parkName', title: '停车场名称', sort: true, align: 'center'},
					     { field: 'locations', title: '地址', align: 'center',width: 300},
					     { field: 'spaceCount', title: '车位数量', sort: true, align: 'center',hide: true},
					     { field: 'comcatName', title: '联系人',  sort: true, align: 'center'},
					     { field: 'comcatPhone', title: '联系电话', sort: true, align: 'center'},
					     { field: 'managerName', title: '管理员', sort: true, align: 'center'},
					     { field: 'managerPhone', title: '管理服务中心电话', sort: true, align: 'center'},
					     { field: 'calculateDay',  title: '月结日', sort: true, align: 'center'},
					     { field: 'status', title: '状态', sort: true, align: 'center',templet: function(data){
					    	 if(data.status == 0){
					    		 return '<font color="green">启用</font>';
					    	 }else if(data.status == -2){
		                        return '<font color="red">已删除</font>';
		                    }else {
		                        return '<font color="red">禁用</font>';
		                    }
					       }
					     },
					     /*{ field: 'freeTimeStatus', title: '免费时长是否加入收费', align: 'center', sort: true, templet: function(data){
								if(data.freeTimeStatus == 0){
									return "否";
								}else if(data.freeTimeStatus == 1){
									return "是";
								}else{
									return "";
								}
							}},*/
					     { fixed: 'right', title:'操作', toolbar: '#parkingSpaceTableBar',width:roleType==1?300:220,align: 'center'}
				     
			     ]],
			 response : {
			        countName: 'totalElements', //规定数据总数的字段名称，默认：count
			         dataName: 'content' //规定数据列表的字段名称，默认：data
			     }, 
				 page : true
			}); 
		}
		
		//切换英文
		if(i18n == 'en_US'){
			$("#companyName").empty();
			$("#companyName").append('Name');
			$("#parkName").empty();
			$("#parkName").append('ParkName');
			$("#searchParkingSpace").empty();
			$("#searchParkingSpace").append('Search');
			$("#addParkingSpace").empty();
			$("#addParkingSpace").append('AddParking');
			$("#PSCompanyName").empty();
			$("#PSCompanyName").append('Company');
			$("#PSParkName").empty();
			$("#PSParkName").append('ParkName');
			$("#PSLocation").empty();
			$("#PSLocation").append('Location');
			$("#getCity").empty();
			$("#getCity").append('Automatic Acquisition province/city/area');
			$("#PSLongitudeAndLatitude").empty();
			$("#PSLongitudeAndLatitude").append('Longitude');
			$("#PSLongitudeAndLatitude").after("<label>Latitude</label>");
			$("#getLongitudeAndLatitude").empty();
			$("#getLongitudeAndLatitude").append('Automatic Acquisition LongitudeAndLatitude');
			$("#PSCityName").empty();
			$("#PSCityName").append('CityName');
			$("#getNewCityName").empty();
			$("#getNewCityName").append('Location');
			$("#LocationRemark").empty();
			$("#LocationRemark").append('Locate the map according to the city name');
			$("#FeeStandard").empty();
			$("#FeeStandard").append('FeeStandard');
			$("#FreeTime").empty();
			$("#FreeTime").append('FreeTime');
			$("#minute").empty();
			$("#minute").append('minute');
			$("#HourlyCharge").empty();
			$("#HourlyCharge").append('HourlyCharge');
			$("#hourYuan").empty();
			$("#hourYuan").append('Yuan/Hour');
			$("#MaximumDailyCharge").empty();
			$("#MaximumDailyCharge").append('MaximumDailyCharge');
			$("#Yuan").empty();
			$("#Yuan").append('Yuan(Re-start billing at 0:00 a.m.)');
			$("#FreeTimeJoinOrNot").empty();
			$("#FreeTimeJoinOrNot").append('Whether to add fees for free duration');
			$("#Contacts").empty();
			$("#Contacts").append('Contacts');
			$("#contactNumber").empty();
			$("#contactNumber").append('ContactNumber');
			$("#Administrator").empty();
			$("#Administrator").append('Administrator');
			$("#CenterTelephone").empty();
			$("#CenterTelephone").append('CenterTelephone');
			$("#monthlySettlementDate").empty();
			$("#monthlySettlementDate").append('monthlySettlementDate');
			$("#NumberOfParking").empty();
			$("#NumberOfParking").append('NumberOfParking');
			$("#MonthCardFees").empty();
			$("#MonthCardFees").append('MonthCardFees');
			$("#searchCompany").attr("placeholder","");
			$("#searchCompany").attr("placeholder","CompanyName");
			$("#searchParking").attr("placeholder","");
			$("#searchParking").attr("placeholder","ParkingName");
		}
		
		/**
		 * 排序方法
		 */
		table.on('sort(parkingSpaceTable)', function(obj) {
			table.reload('parkingSpaceTable', {
			    initSort : obj , //记录初始排序，如果不设的话，将无法标记表头的排序状态
			       where : {     //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
			    	 sortName: obj.field, //排序字段
			        direction: obj.type //排序方式
			       }
			});
		});
		
		/**
		 * 搜索方法
		 */
		$("#searchParkingSpace").click(function() {
			table.reload('parkingSpaceTable', {
				  where : { //设定异步数据接口的额外参数，任意设
					searchParking: $("#searchParking").val(),
					searchCompany: $("#searchCompany").val()
				  },
				   page : {curr: 1 }  //重新从第 1 页开始
			});
		 });
	
		/**
		 * 监听行工具事件
		 */
		 table.on('tool(parkingSpaceTable)', function(obj) {
		     var data = obj.data;
		     var titelDel = "请确认是否删除";
		     if(i18n == 'en_US'){
		    	 var titelDel = "Please confirm whether to delete";
			 }
		     if (obj.event === 'del') { //执行删除方法
					layer.confirm(titelDel, function(index) {
						$.ajax({
							url : contextPath + "/parkingSpace/delete",
							dataType : 'json',
							type : 'post',
							async : false,
							data : {
								parkingId : data.id,
							},
							 complete : function(XHR, TS) {
								var returnObj = eval('(' + XHR.responseText + ')');
								if (returnObj.code != 200) {
									layer.msg(returnObj.msg, {
										icon : 2,
										time : 2500
									});
								} else {
									layer.msg(returnObj.msg, {
										icon : 1,
										time : 2500
									});
								}
								table.reload('parkingSpaceTable');
							}
						});
					});
			 } else if(obj.event === 'set'){ //执行规则设置方法
		         setParkingRule(data);
		     } else if(obj.event === 'edit') {	//执行编辑方法
		    	 editParking("edit",data);
		    	 $("#parkingCreateTime").removeClass("none");
		     }else if(obj.event === 'disable'){ //执行禁用方法
		    	 ableOrDisable("disable",data);
		     }else if(obj.event === 'able'){ //执行启用方法
		    	 ableOrDisable("able",data);
		     }else if(obj.event === 'openGate'){
		    	 longRangeOpenGate(data);
		     }
		     form.render();
		  });
		 
		 /**
		  * 禁用或启用
		  */
		 function ableOrDisable(type,data) {
			 var titel = "真的禁用吗？";
			 var statu = "-1";
			 if(type == "able"){
				 titel = "真的启用吗？";
				 statu = "0";
				 if(i18n == 'en_US'){
					 var titel = "Is it really open?"
				 }
			 }
			 if(i18n == 'en_US' && type != "able"){
				 var titel = "Is it really forbidden?"
			 }
			 layer.confirm(titel, function(index){
	        	 $.ajax({
	 					  url : contextPath+"/parkingSpace/changestatus",
	 				 dataType : 'json',
	 					 type : 'post',
	 					async : false,
	 					 data : {
	 						 parkingId : data.id,
	 						    status : statu
	 					  },
	 				 complete : function(XHR, TS){
 						  var returnObj = eval('(' + XHR.responseText + ')');
 						  if(returnObj.code != 200) {
 							 layer.msg(returnObj.msg, {icon: 2, time: 1500});
 						  } else {
 							 layer.msg("操作成功", {icon: 1, time: 1500});		 							 
 						  }
 						 table.reload('parkingSpaceTable');
 					  }
 				});
	         });
		 };
		 
		 /**
		  * 添加停车场点击事件
		  */
		 $("#addParkingSpace").click(function() {
			 editParking("add");
		 });

		 /*
		 //公司名称 与 管理员联动
        $('select[name="companyName"]').bind('change',function () {
        	return;
            $.ajax({
                url: contextPath + "/parkingSpace/getManagerByCompanyId",
                dataType: 'json',
                type: 'post',
                data: {
                    // companyId:
                },
                complete: function (XHR, TS) {
                    var returnObj = eval('(' + XHR.responseText + ')');
                    if (returnObj.code != 200) {
                        layer.msg(returnObj.msg, { icon: 2, time: 2500 });
                        return false;
                    }else{
                        $("#manager").empty();
                        $.each(returnObj, function(i, obj){
                            $("#manager").append('<option value="'+ obj.id +'" >'+ obj.name +'</option>');
                        });
                        return false;
                    }
                }
            });
        });
        //*/
//        form.on('select(selectCompanyName)', function(data){
//            console.log(data.value);
//        });

         //编辑或添加停车场的具体操作
		 function editParking(type,data) {
			 var showTitle = '编辑停车场';
			 var responseUrl = "/parkingSpace/edit";
			 var parkingId = 0;
			 var companyid;
			 var freeStandardId = 0;
			 var managerPhoneFlag;
			 if(type == "add"){
				  showTitle = '添加停车场'
				responseUrl = "/parkingSpace/add";
			 }else {
				  parkingId = data.id;
				  companyid = data.companyId;
				  freeStandardId = data.freeStandardId;
				  oldParkName = data.parkName;
			 };		
			 
			 var showContent = $('#showParkingSpaceContent').html();
			 $('#showParkingSpaceContent').html("");

			layer.open({
			 		type : 1,
			 		  id : 'showLayui',
			 	   title : showTitle,
			 	 content : showContent, //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
			 		area : ['900px','700px'],
			 		 btn : ['确定', '取消'],
			 	btnAlign : 'c',
			 	
			 	success : function(layero, index) {
                    var myCity = new BMap.LocalCity();
					myCity.get(myFun);
					oldParkingName = "";
					
			 		if(type == "edit"){
			 			$("#parkingCreateTime").removeClass("none");;
			 		}
			 		// 百度地图API功能
			 		var map = new BMap.Map("showParkingMap");
			 		var point = new BMap.Point(116.331398, 39.897445);//地图中心点
			 		map.centerAndZoom(point, 12); //缩放层级
			 		map.enableScrollWheelZoom(); // 启用滚轮放大缩小，默认禁用
			 		var geocoder = new BMap.Geocoder(); //计算
			 		//"定位"按钮
			 		$("#getNewCityName").click(function(){
			 			var cityName = $("#showMapCityName").val();
			 			if(cityName != ""){
			 				map.centerAndZoom(cityName,11);      // 用城市名设置地图中心点
			 			}
			 		});
			 		
			 		map.addEventListener("click", showInfo);
			 		
			 		if($(".show-parking-area-box").html()==""){
		    			$("#big-area-box").removeClass("none");
		    			var areaObj = {"id": 0, "areaName": "", "longitude": longitudeOverAll, "latitude": latitudeOverAll};
		    			addAreaHtmlFunction(0, areaObj);
		    		}
			 		
			 		if(type == "add"){
			 			function myFun(result){
			 				var cityName = result.name;
			 				$("#showMapCityName").val(cityName);
			 				map.setCenter(cityName);
			 			}
			 		}else {
			 			$("#showMapCityName").val(data.cityName);
		 	    		var new_point = new BMap.Point(data.longitude,data.latitude)
			 			var marker = new BMap.Marker(new_point)
		 	    		map.addOverlay(marker);
		 	    		map.centerAndZoom(new_point,15);
			 		}
			 		
			 		
					var myCity = new BMap.LocalCity();
					myCity.get(myFun);
					oldParkingName = "";
			 		//鼠标点击地图，增加点
			 		function showInfo(e){
			 			map.clearOverlays(); 
			 			var marker = new BMap.Marker(e.point);
			 			map.addOverlay(marker);
			 			geocoder.getLocation(e.point,function(rs){
			 				console.log(rs);
			 				var provincesOverAll = rs.addressComponents.province;
			 				var cityOverAll = rs.addressComponents.city;
			 				var countyOverAll = rs.addressComponents.district;
			 				var detailsOverAll = rs.addressComponents.street;
			 				longitudeOverAll = e.point.lng;
			 				latitudeOverAll = e.point.lat;
			 				
			 	            $("#provinceName").val(provincesOverAll); //获取省份
			 	            $("#provinceName").css("color","gray");
			 	            
			 	            $("#cityName").val(cityOverAll); //获取城市
			 	            $("#cityName").css("color","gray");
			 	            
			 	            $("#areaName").val(countyOverAll); //获取区
			 	            $("#areaName").css("color","gray");
			 	            
			 	            $("#location").val(detailsOverAll); //获取街道
			 	            
			 	            $("#inpLongitudeNum").val(longitudeOverAll); //填入经度
			 	            $("#inpLongitudeNum").css("color","gray");
			 	            
			 	    		$("#inpLatitudeNum").val(latitudeOverAll);  //填入纬度
			 	    		$("#inpLatitudeNum").css("color","gray");
			 	    		
			 			});
			 		};

                    var form = layui.form;
                    var layer = layui.layer;
                    var laydate = layui.laydate;
                    laydate.render({
                    	elem: '#startWorkTime', 
                    	type: 'time'
                    		});
                    laydate.render({
                    	elem: '#endWorkTime', 
                    	type: 'time'
                    		});
                    $("#selectCompanyName").empty();
                    $("#selectCompanyName").append('<option value="" selected="selected">请选择所属公司</option>');
                    $.each(companyList,function(i,obj){
                        $("#selectCompanyName").append('<option value="'+obj.id+'">'+obj.name+'</option>');
                    });
                    if(type == "edit"){
                        //赋值
                        form.val('parkingSpaceFrom', { //给表单里面的字段赋值  通过name属性
                            "selectCompanyName": data.companyId,
                            "parkSpaceName": data.parkName,
                            "provinceName": data.provinceName,
                            "cityName": data.cityName,
                            "areaName": data.areaName,
                            "location": data.location,
                            "inpLongitudeNum": data.longitude,
                            "inpLatitudeNum": data.latitude,
                            "freetime": data.freetime,
                            "hourCost": data.hourCost,
                            "mostCost": data.mostCost,
                            "contact": data.comcatName,
                            "contactPhone": data.comcatPhone,
                            "manager": data.managerName,
                            "managerPhone": data.managerPhone,
                            "monthlyKnot": data.calculateDay,
                            "createtime": until.formatDateTime(data.createTime),
                            "spaceNum": data.spaceCount,
                            "monthfree": data.monthFree,
                            
//                            "freeTimePayFlag":date.freeTimePayFlag
                        });
                        $("#provinceName").css("color","gray");
                        $("#cityName").css("color","gray");
                        $("#areaName").css("color","gray");
                        $("#inpLongitudeNum").css("color","gray");
                        $("#inpLatitudeNum").css("color","gray");
                        $("input[name='freeTimeStatus'][value="+data.freeTimeStatus+"]").attr("checked",true);
                        if(allAccountType === 2){
                        	$("#noneParkingforCompanySel").addClass("none");
                        }
                        
                    }
                    form.render();  //重新渲染表单
                    
			        $("#parkSpaceName").blur(function(){
			        		var selectCompanyId = $("#selectCompanyName").val();
			        		var thisName = $(this).val();
	                    	if(selectCompanyId != ""){
	                    		if((thisName != oldParkName) && (thisName)){
	                    			$.ajax({
										url: contextPath + "/parkingSpace/checkRepeat",
										dataType: 'json',
										type: 'post',
										async: false,
										data: {
											companyId: $("#selectCompanyName").val(),
											parkSpaceName: parkSpaceName
										},
										complete: function (XHR, TS) {
											var returnObj = eval('(' + XHR.responseText + ')');
											if (returnObj.code != 200) {
												layer.msg(returnObj.msg, { icon: 2, time: 2500 });
												$("#parkSpaceName").val("");
												return false;
											}
										}
									});
	                    		}
	                    	}else{
	                    		layer.msg("请先选择所属公司" , {anim: 6, time: 2500});
	                    		$("#parkSpaceName").val("");
	                    	}
					});        
			        
					$("#contactPhone").change(function(){
						var str =$("#contactPhone").val();
						if(!isPhone(str)){
							layer.msg("请输入正确的手机号码");
							var result='';
							for(var i=0;i<str.length;i++){
								if(isSettleAccountsDay(str.charAt(i))){
									result+=str.charAt(i);
								}
							}
							$("#contactPhone").val(result);
							return;
						}
					});
					
					$("#managerPhone").change(function(){
						var str =$("#managerPhone").val();
						if(!managePhone(str) && !isPhone(str)){
							layer.msg("请输入正确的管理服务中心电话");
							var result='';
							for(var i=0;i<str.length;i++){
								if(isSettleAccountsDay(str.charAt(i))){
									result+=str.charAt(i);
								}
							}
							managerPhoneFlag = false;
							$("#managerPhone").val(result);
							return;
						}else{
							managerPhoneFlag = true;
						}
					});

			        $("#monthlyKnot").bind("input propertychange",function(){
						var str = $("#monthlyKnot").val();
						if(!isSettleAccountsDay(str)){
							if(str=='0' || str == 0){
								layer.msg("请输入1-28的数字");
							}else{
								layer.msg("请输入数字");
							}
							var result='';
							for(var i=0;i<str.length;i++){
								if(isSettleAccountsDay(str.charAt(i))){
									result+=str.charAt(i);
								}
							}
							$("#monthlyKnot").val(result);
						}
						if(parseInt(str)>28){
							layer.msg("月结日不能大于28");
							$("#monthlyKnot").val(28);
						}
					});

	            }, //success结束
			 			
			 	yes: function(index, layero){  //确定按钮
			 		  		companyid = $("#selectCompanyName").val();
			 				var msgArr = ["公司名称不能为空","停车场名称不能为空","省不能为空","市不能为空",
			 					          "县/区不能为空","详细地址不能为空","经度不能为空","纬度不能为空",
			 					          "免费时长不能为空","每小时收费不能为空","每日最高收费不能为空",
			 					          "管理员不能为空","管理服务中心电话不能为空","月结日不能为空","车位数量不能为空"];  //提示语集合
			 				var clsArr = ["#selectCompanyName","#parkSpaceName","#provinceName","#cityName",
			 					          "#areaName","#location","#inpLongitudeNum","#inpLatitudeNum",
			 					          "#freetime","#hourCost","#mostCost","#manager","#managerPhone",
			 					          "#monthlyKnot","#spaceNum"];  //id集合
                            var validateFlag = until.validate(msgArr, clsArr); //非空验证 需要引入工具js文件until
			 				if(validateFlag) {
			 					layer.msg(validateFlag , {anim: 6, time: 2500});  
			 					return ;
			 				};

                           var contactPhone = $("#contactPhone").val();
                           if(contactPhone != null && contactPhone.length > 0 && !until.isPoneAvailable(contactPhone)){
                               layer.msg("请输入正确的联系电话" , {anim: 6, time: 2500});
                               return ;
                           }
                           
                           if(managerPhoneFlag == false){
                               layer.msg("请输入正确的管理服务中心电话" , {anim: 6, time: 2500});
                               return ;
                           }

                            // var managerPhone =$("#managerPhone").val();
                            // var re = /^\d+(?=\.{0,1}\d+$|$)/;
                            // if (!re.test(managerPhone)) {
				 			// 	layer.msg("请输入正确的服务中心电话号码" , {anim: 6, time: 2500});
				 			// 	return;
				 			// }
//                            var managerPhone =$("#managerPhone").val();
//                            if(contactPhone != null && contactPhone.length > 0 && !until.isPoneAvailable(contactPhone)){
//                                layer.msg("请输入正确的联系电话" , {anim: 6, time: 2500});
//                                return ;
//                            }
                            var freeTimeStatus = $('input:radio[name="freeTimeStatus"]:checked').val();
                            console.log(freeTimeStatus)

							$.ajax({
			 					    url : contextPath + responseUrl,
			 					    dataType : 'json',
			 					    type : 'post',
			 					    async : false,
			 					    data : {
			 						    parkingId: parkingId,
			 						    companyId: companyid,
			 						    freeStandardId: freeStandardId,
			 						    parkSpaceName: $("#parkSpaceName").val(),
			 						    provinceName: $("#provinceName").val(),
			 						    cityName: $("#cityName").val(),
			 						    areaName: $("#areaName").val(),
			 						    location: $("#location").val(),
			 						    longitude: $("#inpLongitudeNum").val(),
			 						    latitude: $("#inpLatitudeNum").val(),
			 						    freetime: $("#freetime").val(),
			 						    hourCost: $("#hourCost").val(),
			 						    mostCost: $("#mostCost").val(),
			 						    comcatName: $("#contact").val(),
			 						    comcatPhone: $("#contactPhone").val(),
			 						    managerName: $("#manager").val(),
			 						    managerPhone: $("#managerPhone").val(),
			 						    calculateDay: $("#monthlyKnot").val(),
			 						    spaceCount: $("#spaceNum").val(),
			 						    monthFree: $("#monthfree").val(),
			 						    freeTimePayFlag:freeTimeStatus
//						 					       ruleType: $('input[name="inAndOut"]:checked').val(), //出入场类型
//						 					         inRule: $('input[name="inrule"]:checked') == true?inRule:outrule
			 					  },
			 					complete : function(XHR, TS) {
			 						  var returnObj = eval('(' + XHR.responseText + ')');
			 						  if(returnObj.code != 200) {
			 							 layer.msg(returnObj.msg, {icon: 2, time: 1500});
			 						  } else {
			 							 layer.msg("操作成功", {icon: 1, time: 1500});		 							 
			 						  }
			 						 layer.close(index); 
			 						 table.reload('parkingSpaceTable');
			 					}
			 				});
			 	},
			 	end: function(){ //只要层被销毁了，end都会执行
			 			$('#showParkingSpaceContent').html(showContent);
			 	}
			});
		}; //编辑或添加停车场的具体操作 end
		
		/**
		 * 停车场规则设置
		 */
		function setParkingRule(data){
			 var showContent = $('#showParkingRule').html();
			 $('#showParkingRule').html("");
			 
			 layer.open({
				 type : 1,
				 id : 'showLayui',
				 title : data.parkName+'--出入场规则设置',
				 content : showContent,
				 area : ['1000px','600px'],
				 btn : ['确定', '取消'],
				 btnAlign : 'c',
				 
				 success: function(layero, index){
						layui.use(['form', 'layer','laydate'], function(){
							var form = layui.form;
					 		var layer = layui.layer;
					 		var laydate = layui.laydate;
					 		laydate.render({
					 			elem: '#startWorkTime',
					 			type: 'time',
					 			format: 'HH:mm'
					 		});
					 		laydate.render({
					 			elem: '#endWorkTime',
					 			type: 'time',
					 			format: 'HH:mm'
					 		});
					 		//监听选择框
					 		form.on('radio(outrule)', function(data){						
					 			$("#selectTime").removeClass("none");
					 			$("#monthlyCard").removeClass("none");
					 			if(data.value == 2){
					 				$("#startWorkDay").removeClass("none");
					 				$("#endWorkDay").removeClass("none");
					 				$("#startWorkTiming").addClass("none");
					 				$("#endWorkTiming").addClass("none");
					 				$("#startWorkTiming").val();
					 				$("#endWorkTiming").val();
					 			}else if(data.value == 3){
					 				$("#startWorkDay").removeClass("none");
					 				$("#endWorkDay").removeClass("none");
					 				$("#startWorkTiming").removeClass("none");
					 				$("#endWorkTiming").removeClass("none");
					 			}else{
					 				$("#selectTime").addClass("none");
					 			}
					 		}); 		
					 		
							$.ajax({
								url: contextPath + "/parkingSpace/getSettingRuleData",
								dataType: 'json',
								type: 'post',
								async: false,
								data: {
									parkingId: data.id
								},
								complete: function (XHR, TS) {
									var returnObj = eval('(' + XHR.responseText + ')');
									if (returnObj.code != 200) {
										layer.msg(returnObj.msg, { icon: 2, time: 2000 });
									} else {
										var result = returnObj.data;
										var outRuleId = result[0].id;
										var inRuleId = result[1].id;
										if (result.length > 0) {
											var outRule = result[0].inRule;
											if(outRule == 2){
												$("#monthlyCard").removeClass("none");
												$("#selectTime").removeClass("none");
												$("#startWorkDay").removeClass("none");
												$("#endWorkDay").removeClass("none");
												$("#startWorkTiming").addClass("none");
												$("#endWorkTiming").addClass("none");
											}else if(outRule == 3){
												$("#monthlyCard").removeClass("none");
												$("#selectTime").removeClass("none");
												$("#startWorkDay").removeClass("none");
												$("#endWorkDay").removeClass("none");
												$("#startWorkTiming").removeClass("none");
												$("#endWorkTiming").removeClass("none");
											}
											//赋值
											$("#outHideId").val(outRuleId);
											$("input[name='outrule'][value='"+ result[0].inRule +"']").prop("checked",true); 
											$("input[name='inrule'][value='"+ result[1].inRule +"']").prop("checked",true);
											$("#startWorkingDay").val(result[0].startDay);
									 		$("#endWorkingDay").val(result[0].endDay);
									 		$("#startWorkTime").val(result[0].startTime);
									 		$("#endWorkTime").val(result[0].endTime);
									 		$("#hideId").val(inRuleId);
										} else {
											$('#showParkingRule').html("");
										}
									}
								}
							});
					 		//赋值
					 		$("#max").val(data.max);
					 		$("input[name='yesOrNo'][value='"+ data.isSupportCard +"']").prop("checked",true);
					 		laydate.render({
					 			elem: '#startWorkTime',
					 			type: 'time',
					 			format: 'HH:mm'
					 		});
					 		laydate.render({
					 			elem: '#endWorkTime',
					 			type: 'time',
					 			format: 'HH:mm'
					 		});
					 		form.render();  //重新渲染表单
						});
				 },
				 yes: function(index, layero){ //确定按钮
					 var msgArr = [];  //提示语集合
					 var clsArr = [];  //id集合
					 var validateFlag = until.validate(msgArr, clsArr); //非空验证 需要引入工具js文件until
					 if(validateFlag) {
						 layer.msg(validateFlag , {anim: 6, time: 2500});  
						 return ;
					 };
		 			 var outrule = $('input[name="outrule"]:checked').val(); //出场规则
		 			 var inrule = $('input[name="inrule"]:checked').val(); //入场规则
		 			 var startWorkingDay = $("#startWorkingDay").val();
		 			 var endWorkingDay = $("#endWorkingDay").val();
		 			 var startWorkTime = $("#startWorkTime").val();
		 			 var endWorkTime = $("#endWorkTime").val();
		 			 if(outrule == 2){
		 				$("#startWorkTime").val(null);
						$("#endWorkTime").val(null);
						if(startWorkingDay == ""){
							layer.msg("工作日不能为空" , {anim: 6, time: 2000}); 
							return false;
						}
						if(endWorkingDay == ""){
							layer.msg("工作日不能为空" , {anim: 6, time: 2000});
							return false;
						}
		 			 }else if(outrule == 3){
		 				if(startWorkingDay == ""||endWorkingDay == ""){
							layer.msg("工作日不能为空" , {anim: 6, time: 2000}); 
							return false;
						}
		 				if(startWorkTime == "" || endWorkTime == ""){
		 					layer.msg("工作时不能为空" , {anim: 6, time: 2000}); 
							return false;
		 				}
		 			 }else if(outrule == 1){
		 				 $("#startWorkingDay").val('');
			 			 $("#endWorkingDay").val('');
			 			 $("#startWorkTime").val('');
			 			 $("#endWorkTime").val('');
		 			 }
		 			 var max = $("#max").val();
		 			 if(max == ""){
		 				$("#max").val(1);
		 			 }
		 			 $.ajax({
		 				url : contextPath + "/parkingSpace/saveSettingRuleData",
		 				dataType : 'json',
		 				type : 'post',
		 				async : false,
		 				data : {
		 					parkingId: data.id,
		 					inRuleId: $("#hideId").val(),
		 					outRuleId: $("#outHideId").val(),
		 					inrule: inrule,
		 					outrule: outrule,
		 					startDay: $("#startWorkingDay").val(),
		 					endDay: $("#endWorkingDay").val(),
		 					startTime: $("#startWorkTime").val(),
		 					endTime: $("#endWorkTime").val(),
		 					isSupportCard: $('input[name="yesOrNo"]:checked').val(),
		 					max: $("#max").val()
		 				},
		 				complete : function(XHR, TS) { 
	 						 var returnObj = eval('(' + XHR.responseText + ')');

	 						 if(returnObj.code != 200) {
	 							 layer.msg(returnObj.msg, {icon: 2, time: 2500});
	 						 } else {
	 							 layer.msg("操作成功", {icon: 1, time: 2500});		 							 
	 						 }
	 						 layer.close(index); 
	 						 table.reload('parkingSpaceTable');
	 					 }
		 			 });
				 },
				 end: function(){
					 $('#showParkingRule').html(showContent);
				 }
			 });
		};
		
		/**
		 * 远程开闸
		 */
		function longRangeOpenGate(data) {
			var showTitle = '远程开闸';
			var showContent = $('#showOpenGateBlock').html();
			$('#showOpenGateBlock').html("");
			var mainControlId = data.mainControlId;
			layer.open({ 
				type : 1,
				id : 'showLayui',
				title : showTitle,
				content : showContent, //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
				area : ['500px','300px'],
				btn : ['开闸', '关闭'],
				btnAlign : 'c',
				
				success : function(layero, index) {//mainControlId
					layui.use(['form'], function(){
						var form = layui.form;
						$.ajax({
							url: contextPath + "/equipment/getEntryRoadways",
							dataType: 'json',
							type: 'post',
							async: false,
							data: {
								mainControlId: mainControlId
							},
							complete : function(XHR, TS) {
								var returnObj = eval('(' + XHR.responseText + ')');
							if(returnObj.code != 200) {
								layer.msg(returnObj.msg, {icon: 2, time: 2500});
							} else {
								//在这里渲染弹窗
								var data = returnObj.data;
								console.log(data)
								for(var i = 0; i < data.length; i++){
									//确定这个主控板绑定了几个车道,设置每个车道的id
									if (i == 0) {
										//渲染第一个车道
										roadways = '<input type="radio" name="roadway" lay-filter="roadway" id = "'+data[i].id+'" value="'+data[i].cameraMac+'" title="'+data[i].roadName+'" >';
									} else {
										roadways = '<input type="radio" name="roadway" lay-filter="roadway" id = "'+data[i].id+'" value="'+data[i].cameraMac+'" title="'+data[i].roadName+'" >';
									}
									//动态添加车道
									$("#entryRoadways").append(roadways);
								}
							  }
							form.render("");
						   }
						});
						$.ajax({
							url: contextPath + "/equipment/getExitRoadways",
							dataType: 'json',
							type: 'post',
							async: false,
							data: {
								mainControlId: mainControlId
							},
							complete : function(XHR, TS) {
								var returnObj = eval('(' + XHR.responseText + ')');
							if(returnObj.code != 200) {
								layer.msg(returnObj.msg, {icon: 2, time: 2500});
							} else {
								//在这里渲染弹窗
								var data = returnObj.data;
								console.log(data)
								for(var i = 0; i < data.length; i++){
									//确定这个主控板绑定了几个车道,设置每个车道的id
									if (i == 0) {
										//渲染第一个车道
										roadways = '<input type="radio" name="roadway" lay-filter="roadway" id = "'+data[i].id+'" value="'+data[i].cameraMac+'" title="'+data[i].roadName+'" >';
									} else {
										roadways = '<input type="radio" name="roadway" lay-filter="roadway" id = "'+data[i].id+'" value="'+data[i].cameraMac+'" title="'+data[i].roadName+'" >';
									}
									//动态添加车道
									$("#exitRoadways").append(roadways);
								}
							}
							form.render("");
							}
						});
						//监听选择框
				 		form.on('select(entryAndExit)', function(data){						
				 			if(data.value == 1){
				 				$("#entry").removeClass("none");
				 				$("#exit").addClass("none");
				 			}else if(data.value == 2){
				 				$("#exit").removeClass("none");
				 				$("#entry").addClass("none");
				 			}
				 		}); 	
						
				 		/*form.on('radio(freeTimeStatus)', function(data){
				 			roadwaysData[currentRoadwayIndex].cameraType = data.value;
						  }); */
					   //赋值
				   });
				},
				yes: function(index, layero){  //确定按钮
					var msgArr = [ ];
					var clsArr = [ ];
					var validateFlag = until.validate(msgArr, clsArr); //非空验证 需要引入工具js文件until
					if(validateFlag) {
						layer.msg(validateFlag , {anim: 6, time: 2500});  
						return ;
					};
					console.log(data.controlMac)
					var cameraMac = $('input:radio[name="roadway"]:checked').val();
					$.ajax({
 					    url : contextPath + "/parkingSpace/openGate",
 					    dataType : 'json',
 					    type : 'post',
 					    async : false,
 					    data : {
 					    	controlMac: data.controlMac,
 					    	cameraMac: cameraMac
 					    },
	 					complete : function(XHR, TS) {
	 						  var returnObj = eval('(' + XHR.responseText + ')');
	 						  if(returnObj.code != 200) {
	 							 layer.msg(returnObj.msg, {icon: 2, time: 1500});
	 						  } else {
	 							 layer.msg(returnObj.msg, {icon: 1, time: 1500});		 							 
	 						  }
	 						 //layer.close(index); 
	 						 //table.reload('parkingSpaceTable');
	 					}
	 				});
				},
				end: function(){ //只要层被销毁了，end都会执行
		 			$('#showOpenGateBlock').html(showContent);
			 	}
		   });
		};//远程开闸事件结束
		
	});
	
	/**
	 * 判断是否是结算日，1-28号 true:是   false:不是
	 */
	function isSettleAccountsDay(str){
	    	var reg = new RegExp("^[1-9]+[0-9]*$");
	    	if(!reg.test(str)){
	    		return false;
	    	}
		    return true;
	}
	function managePhone(str){
		var reg = new RegExp(/^([0-9]{3,4}-)?[0-9]{7,8}$/);		
		if(!reg.test(str)){
			return false;
		}
		return true;
	}
	function isPhone(str){
		var reg = new RegExp(/^1(3|4|5|7|8)\d{9}$/);		
		if(!reg.test(str)){
			return false;
		}
		return true;
	}
	function check(e) {
	    var re = /^\d+(?=\.{0,1}\d+$|$)/
	    if (e.value != "") {
	        if (!re.test(e.value)) {
	            alert("请输入正确的数字");
	            e.value = "";
	            e.focus();
	        }
	    }
	}
	
});