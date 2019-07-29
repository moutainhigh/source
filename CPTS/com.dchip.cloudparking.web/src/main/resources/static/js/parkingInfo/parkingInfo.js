define(['jquery', 'layui', 'until'], function($, LAY, until) {
	layui.use(['table','form'], function() {
		var table = layui.table;
		var form = layui.form;
		if(i18n == 'en_US'){
			table.render({ 
				 elem : '#parkingInfoTable',
				  url : contextPath+"/parkingInfo/rendering",
			   method : 'post',
		 cellMinWidth : 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
				 skin : 'line',
				where : { //默认排序字段
						sortName: 'parkDate',
					   direction: 'desc'
					},
			     cols : [[
	                 { field: 'parkingInfoId',hide:true},
			    	 { field: 'licensePlate', title: 'licensePlate', sort: true, align: 'center', templet: function(data){
							if(data.licensePlate){
								return data.licensePlate;
							}else{
								return "无数据";
							}
						}
			    	 },
			    	 { field: 'userPhone', title: 'userPhone', sort: true, align: 'center', templet: function(data){
							if(data.userPhone){
								return data.userPhone;
							}else{
								return "无数据";
							}
						}
			    	 },
			    	 { field: 'parkName', title: 'parkName', sort: true, align: 'center', templet: function(data){
							if(data.parkName){
								return data.parkName;
							}else{
								return "无数据";
							}
						}
			    	 },
				     { field: 'locations', title: 'locations', align: 'center',width: 350, templet: function(data){
				    	    if(data.locations){
				    	    	return data.locations;
				    	    }else{
				    	    	return "无数据";
				    	    }
				     	}
			    	 },
				     { field: 'parkDate', title: 'parkDate',  sort: true, align: 'center',templet: function(data){
				    	 if(data.parkDate == null){
		            		 return "";
		            	 } else {
		            		 return until.formatDateTime(data.parkDate);
		            	 }
				       }
				     },
				     { field: 'outDate', title: 'outDate', sort: true, align: 'center',templet: function(data){
				    	 if(data.outDate == null){
		            		 return '<font color="green">停车中</font>';
		            	 } else {
		            		 return until.formatDateTime(data.outDate);
		            	 }
				       }
				     },
				     { fixed: 'right', title:'right', toolbar: '#parkingInfoTableBarEnglish', align: 'center',width:180}
			     ]],
			 response : {
			        countName: 'totalElements', //规定数据总数的字段名称，默认：count
			         dataName: 'content' //规定数据列表的字段名称，默认：data
			     }, 
				 page : true
			});
		}else{
			table.render({ 
				 elem : '#parkingInfoTable',
				  url : contextPath+"/parkingInfo/rendering",
			   method : 'post',
		 cellMinWidth : 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
				 skin : 'line',
				where : { //默认排序字段
						sortName: 'parkDate',
					   direction: 'desc'
					},
			     cols : [[
	                 { field: 'parkingInfoId',hide:true},
			    	 { field: 'licensePlate', title: '车牌号码', sort: true, align: 'center', templet: function(data){
							if(data.licensePlate){
								return data.licensePlate;
							}else{
								return "无数据";
							}
						}
			    	 },
			    	 { field: 'userPhone', title: '手机号码', sort: true, align: 'center', templet: function(data){
							if(data.userPhone){
								return data.userPhone;
							}else{
								return "无数据";
							}
						}
			    	 },
			    	 { field: 'parkName', title: '停车场名称', sort: true, align: 'center', templet: function(data){
							if(data.parkName){
								return data.parkName;
							}else{
								return "无数据";
							}
						}
			    	 },
				     { field: 'locations', title: '地址', align: 'center',width: 350, templet: function(data){
				    	    if(data.locations){
				    	    	return data.locations;
				    	    }else{
				    	    	return "无数据";
				    	    }
				     	}
			    	 },
				     { field: 'parkDate', title: '入场时间',  sort: true, align: 'center',templet: function(data){
				    	 if(data.parkDate == null){
		            		 return "";
		            	 } else {
		            		 return until.formatDateTime(data.parkDate);
		            	 }
				       }
				     },
				     { field: 'outDate', title: '出场时间', sort: true, align: 'center',templet: function(data){
				    	 if(data.outDate == null){
		            		 return '<font color="green">停车中</font>';
		            	 } else {
		            		 return until.formatDateTime(data.outDate);
		            	 }
				       }
				     },
				     { fixed: 'right', title:'操作', toolbar: '#parkingInfoTableBar', align: 'center',width:180}
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
			$("#parkingName").empty();
			$("#parkingName").append('ParkingName');
			$("#licensePlateInfo").empty();
			$("#licensePlateInfo").append('LicensePlate');
			$("#infoPhone").empty();
			$("#infoPhone").append('Phone');
			$("#searchPark").empty();
			$("#searchPark").append('Search');
			$("#searchParkName").attr("placeholder","ParkingName");
			$("#searchParkingLicensePlate").attr("placeholder","LicensePlate");
			$("#searchUserPhone").attr("placeholder","PhoneNum");
			
		}

		
		/**
		 * 排序方法
		 */
		table.on('sort(parkingInfoTable)', function(obj) {
			table.reload('parkingInfoTable', {
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
		$("#searchPark").click(function() {
			table.reload('parkingInfoTable', {
				  where : { //设定异步数据接口的额外参数，任意设
					  searchParkName: $("#searchParkName").val(),
					  searchParkingLicensePlate: $("#searchParkingLicensePlate").val(),
					  searchUserPhone: $("#searchUserPhone").val()
				  },
				   page : {curr: 1 }  //重新从第 1 页开始
			});
		 });
		
		//监听行工具事件
		table.on('tool(parkingInfoTable)', function (obj) {
			var data = obj.data;
			if (obj.event === 'viewDetail') {
				var showContent = $('#showParkingInfoContent').html();
				$('#showParkingInfoContent').html("");
				layer.open({
					type: 1,
					id: 'showLayui',
					title: '车辆出入记录详情',
					content: showContent, //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
					area: ['600px', '500px'],
					btnAlign: 'c',
					offset: 'auto',
					success: function (layero, index) {//打开层之后可执行的回调
						$.ajax({
							url: contextPath + "/parkingInfo/getParkingInfoDetail",
							dataType: 'json',
							type: 'post',
							async: false,
							data: {
								licensePlate: data.licensePlate
							},
							complete: function (XHR, TS) {
								var returnObj = eval('(' + XHR.responseText + ')');
								if (returnObj.code != 200) {
									layer.msg(returnObj.msg, { icon: 2, time: 1500 });
								} else {
									$("#carRecordContent").html('');
									var result = returnObj.data;
									if (result.length > 0) {
										for (let i = 0; i < result.length; i++) {
											var content = (
												"<li class='layui-timeline-item'>" +
													"<i class='layui-icon layui-timeline-axis'></i>" +
													"<div class='layui-timeline-content layui-text'>" +
														"<h3 class='layui-timeline-title'>入场时间：" + (result[i].parkDate == null?"无":until.formatDateTime(result[i].parkDate)) + "</h3>" +
														"<p>车牌号：" + (result[i].licensePlate == null?"无":result[i].licensePlate) + "</p>" +
														"<p>停车场名称：" + (result[i].parkName == null?"无":result[i].parkName) + "</p>" +
														"<p>停车场地址：" + (result[i].locations == null?"无":result[i].locations) + "</p>" +
														(result[i].outDate == null? '<h3 style="margin-top:7px;">停车状态：<font color="green">停车中</font></h3>' :'<h3 style="margin-top:7px;">出场时间：' + until.formatDateTime(result[i].outDate) + '</h3>') +
													"</div>" +
												"</li>"
											);
											$("#carRecordContent").append(content);
										}
									} else {
										$("#carRecordContent").append("<p>暂无停车记录</p>");
									}
								}
							}
						});
					},
					end: function () { //只要层被销毁了，end都会执行
						$('#showParkingInfoContent').html(showContent);
					}
				});
			}else if(obj.event === 'manualSettlement'){
				manualSettlement(data);
			}
		});
		
		function manualSettlement(data){
			var showContent = $('#showManualSettlement').html();
			 $('#showManualSettlement').html("");
			 
			 layer.open({
				 type : 1,
				 id : 'showLayui',
				 title : data.parkName+'--人工结算',
				 content : showContent,
				 area : ['500px'],
				 btn : ['确定', '取消'],
				 btnAlign : 'c',
				 
				 success: function(layero, index){
						layui.use(['form', 'layer','laydate'], function(){
							var form = layui.form;
					 		var layer = layui.layer;
					 		var laydate = layui.laydate;
					 		
							//赋值
							$("#licensePlate").val(data.licensePlate);
							$("#inTime").val(until.formatDateTime(data.parkDate));
							$("#outTime").val(until.formatDateTime(new Date()));
							$.ajax({
					 				url : contextPath + "/parkingInfo/getRoadName",
					 				dataType : 'json',
					 				type : 'post',
					 				async : false,
					 				data : {
					 					parkingId: data.parkingId
					 				},
					 				complete : function(XHR, TS) { 
				 						 var returnObj = eval('(' + XHR.responseText + ')');
				 						 console.log(returnObj)
				 						 if(returnObj.code != 200) {
				 							 layer.msg(returnObj.msg, {icon: 2, time: 2500});
				 						 } else {
				 							var data = returnObj.data;
				 							$("#roadWay").empty();
											var addRoadWay = '<option value="">请选择出口车道</option>';
											$.each(returnObj.data,function(i,obj){
												 addRoadWay += '<option value="'+obj.id+'">'+ obj.road_name +'</option>';
											});
											$("#roadWay").append(addRoadWay);
				 						 }
				 					}
					 		});
					 		$.ajax({	//结算费用
				 				url : contextPath + "/parkingInfo/settleCost",
				 				dataType : 'json',
				 				type : 'post',
				 				async : false,
				 				data : {
				 					intime: until.formatDateTime(data.parkDate),
				 					outtime: $("#outTime").val(),
				 					licensePlate: data.licensePlate,
				 					parkingInfoId: data.parkingInfoId,
				 				},
				 				complete : function(XHR, TS) {
				 					console.log(XHR);
				 					 var returnObj = eval('(' + XHR.responseText + ')');
			 						 console.log(returnObj);
			 						 if(returnObj.code != 200) {
			 							 layer.msg(returnObj.msg, {icon: 2, time: 2500});
			 						 } else {
										$("#cost").val(returnObj.data.cost);
			 						 }
			 					 }
				 		    });
					 		laydate.render({
					 			elem: '#outTime',
					 			type: 'datetime'
					 		});
					 		form.render();  //重新渲染表单
						});
				 },
				 yes: function(index, layero){ //确定按钮
					 var msgArr = ["出场时间不能为空","出口车道不能为空","费用不能为空"];  //提示语集合
					 var clsArr = ["#outTime","#roadWay","#cost"];  //id集合
					 var validateFlag = until.validate(msgArr, clsArr); //非空验证 需要引入工具js文件until
					 if(validateFlag) {
						 layer.msg(validateFlag , {anim: 6, time: 2500});  
						 return ;
					 };
		 			 $.ajax({
		 				url : contextPath + "/parkingInfo/saveManualSettleAction",
		 				dataType : 'json',
		 				type : 'post',
		 				async : false,
		 				data : {
		 					parkingInfoId: data.parkingInfoId,
		 					outTime: $("#outTime").val(),
		 					roadWayId: $("#roadWay").val(),
		 					cost: $("#cost").val()
		 				},
		 				complete : function(XHR, TS) { 
	 						 var returnObj = eval('(' + XHR.responseText + ')');

	 						 if(returnObj.code != 200) {
	 							 layer.msg(returnObj.msg, {icon: 2, time: 2500});
	 						 } else {
	 							 layer.msg("操作成功", {icon: 1, time: 2500});		 							 
	 						 }
	 						 layer.close(index); 
	 						 table.reload('parkingInfoTable');
	 					 }
		 			 });
				 },
				 end: function(){
					 $('#showManualSettlement').html(showContent);
				 }
			 });
			
		};
		
	});
	
});