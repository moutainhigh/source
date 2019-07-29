define(['jquery', 'layui', 'until'],function($, LAYUI, until){
	
	layui.use(['table','form'],function(){
		var form = layui.form;
		var table = layui.table;
		$(".show-out-roadway-box").html("");
		var addHtml = '';
		$.each(roadwayList,function(i, obj){
			addHtml += '<input type="checkbox" name="roadway" value="'+ obj.id +'" lay-skin="primary" title="'+ obj.road_name +'" checked="">';
		});
		$(".show-out-roadway-box").html(addHtml);
		
		form.render();
		
		//监听车道选择
		form.on('checkbox()', function(data){
			getNewData();		    
		}); 
		/**
		 * 刷新列表数据
		 */		
//		var check_val = [];
		function getNewData(){
			//获取选中的车道
			var obj = document.getElementsByName("roadway");
		    var check_val = [];
		    for(k in obj){
		        if(obj[k].checked)
		            check_val.push(obj[k].value);
		    }
		    console.log(check_val);
		    $("#manualRefresh").click(function(){
				 //根据选中的车道刷新列表
			    table.reload('cashTable', {
					  where : { //设定异步数据接口的额外参数，任意设
						  searchRoadWayId: JSON.stringify(check_val),
					  },
					   page : {curr: 1 }  //重新从第 1 页开始
				});
			});
		}
		
		if(i18n == "en_US"){
			table.render({
				elem: '#cashTable',
				url: contextPath + "/cash/rendTable",
				method: 'post',
				cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
				skin: 'line',
				where: {
					sortName: 'outDate',
					direction: 'desc'
				},
				cols: [[
					{ field: 'piId', title: 'piId', sort: true, hide: true },
					{ field: 'parkName', title: 'parkName', sort: false },
					{ field: 'licensePlate', title: 'licensePlate' },
					{ field: 'parkDate', title: 'parkDate', sort: true, templet: function(data){
						if(data.parkDate){
							return until.formatDateTimeString(data.parkDate);
						}else{
							return "无";
						}
					}},
					{ field: 'roadName', title: 'roadName' },
					{ field: 'outDate', title: 'outDate', sort: true, templet: function(data){
						if(data.outDate){
							return until.formatDateTimeString(data.outDate);
						}else{
							return "无";
						}
					}},
					
					{ fixed: 'right',field: 'status', title: 'right', align: 'center',width:250, templet: function (data) {
							var button = "";
							button += "<button lay-event='editBar' class='layui-btn layui-btn-xs'>Settlement</button>";
							return button;	
						}
					}
				]],
				response: {
					countName: 'totalElements',  //规定数据总数的字段名称，默认：count
					dataName: 'content' //规定数据列表的字段名称，默认：data
				},
				page: true
			});
		}else{
			table.render({
				elem: '#cashTable',
				url: contextPath + "/cash/rendTable",
				method: 'post',
				cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
				skin: 'line',
				where: {
					sortName: 'outDate',
					direction: 'desc'
				},
				cols: [[
					{ field: 'piId', title: 'piId', sort: true, hide: true },
					{ field: 'parkName', title: '停车场', sort: false },
					{ field: 'licensePlate', title: '车牌号码' },
					{ field: 'parkDate', title: '入场时间', sort: true, templet: function(data){
						if(data.parkDate){
							return until.formatDateTimeString(data.parkDate);
						}else{
							return "无";
						}
					}},
					{ field: 'roadName', title: '出场车道' },
					{ field: 'outDate', title: '出场时间', sort: true, templet: function(data){
						if(data.outDate){
							return until.formatDateTimeString(data.outDate);
						}else{
							return "无";
						}
					}},
					
					{ fixed: 'right',field: 'status', title: '操作', align: 'center',width:250, templet: function (data) {
							var button = "";
							button += "<button lay-event='editBar' class='layui-btn layui-btn-xs'>结算</button>";
							return button;	
						}
					}
				]],
				response: {
					countName: 'totalElements',  //规定数据总数的字段名称，默认：count
					dataName: 'content' //规定数据列表的字段名称，默认：data
				},
				page: true
			});
		}
		
		//切换英文
		if(i18n == 'en_US'){
			$("#manualRefresh").empty();
			$("#manualRefresh").append('Refresh');
			$("#choseRoadway").empty();
			$("#choseRoadway").append('Roadway');
		}
		

		table.on('sort(cashTable)', function (obj) {
			table.reload('cashTable', {
				initSort: obj, //记录初始排序，如果不设的话，将无法标记表头的排序状态。 layui 2.1.1 新增参数
				where: { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
					field: obj.field, //排序字段
					order: obj.type //排序方式
				}
			});
		});
		
		 //监听行工具事件
		 table.on('tool(cashTable)', function(obj){
		     var data = obj.data;
		     console.log(data)
		     if(obj.event === 'editBar'){
		    	 //执行编辑方法
		    	var showContent = $('#showCashTableContent').html();
		 		$('#showCashTableContent').html("");
		 		layer.open({
		 			type: 1,
		 			id: 'showLayui',
		 			title: '现金收费管理',
		 			content: showContent, //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
		 			area: '600px',
		 			btn: ['确定', '取消'],
		 			btnAlign: 'c',
		 			success: function(layero, index){//打开层之后可执行的回调
		 				layui.use(['form', 'layer'], function(){
		 					var form = layui.form;
		 					var layer = layui.layer;
		 					//赋值
		 					$("#parkName").html(data.parkName);
		 					$("#licensePlate").html(data.licensePlate);
		 					$("#roadName").html(data.roadName);
		 					console.log(data)
		 					$.ajax({	//结算费用
				 				url : contextPath + "/cash/settleCost",
				 				dataType : 'json',
				 				type : 'post',
				 				async : false,
				 				data : {
				 					parkDate: until.formatDateTime(data.parkDate),
				 					outDate: until.formatDateTime(data.outDate),
				 					licensePlate: data.licensePlate, 
				 					parkingId: data.parkingId
				 					 
				 				},
				 				complete : function(XHR, TS) {
				 					 console.log(XHR);
				 					 var returnObj = eval('(' + XHR.responseText + ')');
			 						 console.log(returnObj);
			 						 if(returnObj.code != 200) {
			 							 layer.msg(returnObj.msg, {icon: 2, time: 2500});
			 						 } else {
			 							var date = returnObj.data;
			 							console.log(date);
			 							$("#parkingTime").html(date.parkingTime);
			 							$("#fee").html(date.Fee);
			 						 }
			 					 }
				 		    });
		 					form.render();  //重新渲染表单
		 				});
		 			},
		 			yes: function(index, layero){ //确定按钮
		 				$.ajax({
		 					  url: contextPath+"/cash/generateOrder",
		 					  dataType:'json',
		 					  type:'post',
		 					  async: false,
		 					  data : {
		 						  Fee: $("#fee").html(),
		 						  parkingTime: $("#parkingTime").html(),
		 						  parkingInfoId: data.piId,
		 						 licensePlate:data.licensePlate
		 					  },
		 					  complete: function(XHR, TS){
		 						 var returnObj = eval('(' + XHR.responseText + ')');
		 						 if(returnObj.code != 200){
		 							 layer.msg(returnObj.msg, {icon: 2, time: 1500});
		 						 }else{
		 							 layer.msg(returnObj.msg, {icon: 1, time: 1500});		 							 
		 						 }
		 						 layer.close(index); 
		 						 table.reload('cashTable');
		 						 
		 					  }
		 				});
		 			    
		 			},
		 			end: function(){ //只要层被销毁了，end都会执行
		 				$('#showCashTableContent').html(showContent);
		 			}
		 			
		 		});
		    }
		  });
		
		
		/**
		 * websocket操作
		 */
		var websocket = null;		 
	    //判断当前浏览器是否支持WebSocket
	    if('WebSocket' in window){
	        websocket = new WebSocket("ws://"+ document.domain +":9002/cloudParkingWeb/ws/myWebSocket/*"+ parkingId +"*"+ allAccountId);
	    }
	    else{
	        alert('Not support websocket')
	    }
	 
	    //连接发生错误的回调方法
	    websocket.onerror = function(){
	    	console.log("error");
	    };
	 
	    //连接成功建立的回调方法
	    websocket.onopen = function(event){
	        console.log("open");
	    }
	 
	    //接收到消息的回调方法
	    websocket.onmessage = function(event){
	    	setTimeout(function(){//延迟一秒显示出来
	    		getNewData();
	    	},1000);	    	
	    }
	 
	    //连接关闭的回调方法
	    websocket.onclose = function(){
	    	console.log("close");
	    }
	 
	    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
	    window.onbeforeunload = function(){
	        websocket.close();
	    }
	 
	    //关闭连接
	    function closeWebSocket(){
	        websocket.close();
	    }
		
	});
	
	
	
});