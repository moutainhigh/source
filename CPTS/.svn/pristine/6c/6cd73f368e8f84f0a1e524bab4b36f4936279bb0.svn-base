define(['jquery', 'layui', 'until'], function($, LAY, until){
	layui.use(['table', 'form', 'layer'], function(){
		var table = layui.table;
		var form = layui.form;
		var layer = layui.layer;
		if(i18n == "en_US"){
			table.render({
				elem: '#cashTable',
				url: contextPath+"/cash/rendering",
				method: 'post',
				cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
				skin: 'line',
				where: { //默认排序字段
					sortName: 'outDate',
					direction: 'desc'
				},
			    cols: [[
			      { field: 'parkName', title: 'parkName', sort: true },
			      { field:'staffName', title: 'staffName',  sort: true, align: 'center'}, 
			      { field:'fee', title: 'fee', sort: true, align: 'center'},
			      { field:'finalFee', title: 'finalFee', sort: true, align: 'center'},
			      { field: 'status', field: 'status', title: 'status', sort: true, align: 'center',templet:function(data){
							if(data.status == 0){
								return "未支付";
							}else if(data.status == 1){
								return "已支付";
							}else {
								return "支付失败";
							}
					    }
					},
			      { field:'parkingTime', title: 'parkingTime', sort: true, align: 'center'},
			      { field: 'licensePlate', title: 'licensePlate', sort: true, align: 'center', templet: function(data){
						if(data.licensePlate){
							return data.licensePlate;
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
				     { field:'userphone', title: 'userphone', sort: true, align: 'center',templet: function(data){
				    	 if(data.userphone){
								return data.userphone;
							}else{
								return "无数据";
							}
				     }
				    	 }
			    ]],
			    response: {
			        countName: 'totalElements', //规定数据总数的字段名称，默认：count
			        dataName: 'content' //规定数据列表的字段名称，默认：data
			      }, 
				page: true
			});
		}else{
			table.render({
				elem: '#cashTable',
				url: contextPath+"/cash/rendering",
				method: 'post',
				cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
				skin: 'line',
				where: { //默认排序字段
					sortName: 'outDate',
					direction: 'desc'
				},
			    cols: [[
			      { field: 'parkName', title: '停车场', sort: true },
			      { field:'staffName', title: '保安',  sort: true, align: 'center'}, 
			      { field:'fee', title: '应付金额', sort: true, align: 'center'},
			      { field:'finalFee', title: '实付金额', sort: true, align: 'center'},
			      { field: 'status', field: 'status', title: '支付状态', sort: true, align: 'center',templet:function(data){
							if(data.status == 0){
								return "未支付";
							}else if(data.status == 1){
								return "已支付";
							}else {
								return "支付失败";
							}
					    }
					},
			      { field:'parkingTime', title: '停车时间/分钟', sort: true, align: 'center'},
			      { field: 'licensePlate', title: '车牌号码', sort: true, align: 'center', templet: function(data){
						if(data.licensePlate){
							return data.licensePlate;
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
				     { field:'userphone', title: '用户手机号码', sort: true, align: 'center',templet: function(data){
				    	 if(data.userphone){
								return data.userphone;
							}else{
								return "无数据";
							}
				     }
				    	 }
			    ]],
			    response: {
			        countName: 'totalElements', //规定数据总数的字段名称，默认：count
			        dataName: 'content' //规定数据列表的字段名称，默认：data
			      }, 
				page: true
			});
		}
		 
		
		/**
		 * 排序方法
		 */
		table.on('sort(cashTable)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
			table.reload('cashTable', {
			    initSort: obj //记录初始排序，如果不设的话，将无法标记表头的排序状态
			    ,where: { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
			    	sortName: obj.field //排序字段
			      ,direction: obj.type //排序方式
			    }
			});
		});
		
		/*var websocket = null;
		 
	    //判断当前浏览器是否支持WebSocket
	    if('WebSocket' in window){
	        websocket = new WebSocket("ws://localhost:9002/cloudParkingWeb/ws/myWebSocket/*"+ parkingId +"*"+ allAccountId);
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
	    	setTimeout(function(){
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
	    }*/
		
		
		 
	});
	
	
});