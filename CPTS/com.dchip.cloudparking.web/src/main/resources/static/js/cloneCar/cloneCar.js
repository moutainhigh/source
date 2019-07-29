define(['jquery', 'layui','until'], function($,LAYUI,until){
	layui.use(['table','form'], function(){
		 var table = layui.table;
		 var form = layui.form;
		 form.render();
		 if(i18n == 'en_US'){
			 table.render({
					elem : '#CloneCarTable',
					 url : contextPath+"/cloneCar/rendering",
				  method : 'post',
			cellMinWidth : 150 ,//全局定义常规单元格的最小宽度，layui 2.2.1 新增
					skin : 'line',
				   where : { 
						 sortName: 'createTime',
						direction: 'desc'
					},
				    cols : [[
				         { field:'licensePlate', title: 'cloneLicensePlate', sort: true, align: 'center'},
				         { field:'createTime', title: 'Time', sort: true, align: 'center',templet: function(data) {
				    	     if(data.createTime==null){
			            		 return "";
			            	 } else {
			            		 return until.formatDateTime(data.createTime);
			            	 }
				           }
				         },
				         /*{ field:'status', title: '状态', sort: true, align: 'center',templet: function(data) {
				    	      if(data.status == 0) {
				    	    	  return '<span style="color:red;">未处理</span>';
				    	      } else if(data.status == 1) {
				    	    	  return '<span style="color:green;">已处理</span>';
				    	      } else {
				    	    	  return "";
				    	      }
				            }
				         },*/
				         { fixed: 'right', title:'right', toolbar: '#cloneCarBarEnglish', align: 'center'}
				        ]],
				 response : {
				        countName: 'totalElements', //规定数据总数的字段名称，默认：count
				        dataName: 'content' //规定数据列表的字段名称，默认：data
				      }, 
					 page : true
				});
		 }else{
			 table.render({
					elem : '#CloneCarTable',
					 url : contextPath+"/cloneCar/rendering",
				  method : 'post',
			cellMinWidth : 150 ,//全局定义常规单元格的最小宽度，layui 2.2.1 新增
					skin : 'line',
				   where : { 
						 sortName: 'createTime',
						direction: 'desc'
					},
				    cols : [[
				         { field:'licensePlate', title: '车牌号码(套牌)', sort: true, align: 'center'},
				         { field:'createTime', title: '记录时间', sort: true, align: 'center',templet: function(data) {
				    	     if(data.createTime==null){
			            		 return "";
			            	 } else {
			            		 return until.formatDateTime(data.createTime);
			            	 }
				           }
				         },
				         /*{ field:'status', title: '状态', sort: true, align: 'center',templet: function(data) {
				    	      if(data.status == 0) {
				    	    	  return '<span style="color:red;">未处理</span>';
				    	      } else if(data.status == 1) {
				    	    	  return '<span style="color:green;">已处理</span>';
				    	      } else {
				    	    	  return "";
				    	      }
				            }
				         },*/
				         { fixed: 'right', title:'操作', toolbar: '#cloneCarBar', align: 'center'}
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
				$("#cloneLicensePlate").empty();
				$("#cloneLicensePlate").append('LicensePlate');
				$("#cloneTime").empty();
				$("#cloneTime").append('cloneTime');
				$("#searchParkingSpace").empty();
				$("#searchParkingSpace").append('Search');
				$("#searchLicensePlate").attr("placeholder","LicensePlate");
				$("#searchStartTime").attr("placeholder","StartTime");
				$("#searchEndTime").attr("placeholder","EndTime");
			}
			
		/**
		 * 排序方法
		 */
		table.on('sort(CloneCarTable)', function(obj){
			  table.reload('CloneCarTable', {
			       initSort : obj ,
			          where : {
			               field: obj.field, //排序字段
			               order: obj.type //排序方式
			          }
			     });
		});


        layui.use('laydate', function(){
            var laydate = layui.laydate;
            var endDate= laydate.render({
                elem: '#searchEndTime',//选择器结束时间
                type: 'datetime',
                min:"1970-1-1",//设置min默认最小值
                done: function(value,date){
                    startDate.config.max={
                        year:date.year,
                        month:date.month-1,//关键
                        date: date.date,
                        hours: 0,
                        minutes: 0,
                        seconds : 0
                    }
                }
            });
            //日期范围
            var startDate=laydate.render({
                elem: '#searchStartTime',
                type: 'datetime',
                max:"2099-12-31",//设置一个默认最大值
                done: function(value, date){
                    endDate.config.min ={
                        year:date.year,
                        month:date.month-1, //关键
                        date: date.date,
                        hours: 0,
                        minutes: 0,
                        seconds : 0
                    };
                }
            });
        });
		/**
		 * 搜索方法
		 */
		$("#searchParkingSpace").click(function(){
			  table.reload('CloneCarTable', {
				     where : { 
					     searchLicensePlate: $("#searchLicensePlate").val(),
                         searchStartTime: $("#searchStartTime").val(),
                         searchEndTime: $("#searchEndTime").val(),
                         searchStatus: $("#searchStatus").val()
				      },
				      page : {
				           curr: 1 //重新从第 1 页开始
				      }
				});
		});
		
		 /**
		  * 监听行工具事件
		  */
		 table.on('tool(CloneCarTable)', function(obj) {
		     var data = obj.data;
		     var titelDel = "真的删除吗";
	            if(i18n == 'en_US'){
	            	 titelDel = "Do you really delete it?";
	            }
		     if(obj.event === 'del') { //执行删除方法
		         layer.confirm(titelDel, function(index) {
		        	 $.ajax({
	 					  url : contextPath+"/cloneCar/delete",
	 				 dataType : 'json',
	 					 type : 'post',
	 					async : false,
	 					 data : {
	 						 cloneCarId: data.cloneCarId,
	 					  },
	 				 complete : function(XHR, TS) {
	 						  var returnObj = eval('(' + XHR.responseText + ')');
	 						  if(returnObj.code != 200) {
	 							 layer.msg(returnObj.msg, {icon: 2, time: 1500});
	 						  } else {
	 							 layer.msg("操作成功", {icon: 1, time: 1500});		 							 
	 						  }
	 						 table.reload('CloneCarTable');
	 					  }
	 				});
		         });
		     } else if(obj.event === 'viewDetail') {//执行编辑方法
					var showContent = $('#showCloneCarDetail').html();
					$('#showCloneCarDetail').html("");
					layer.open({
						type: 1,
						id: 'showLayui',
						title: '<h3>套牌车出入记录详情</h3>',
						content: showContent, //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
						area: ['600px', '500px'],
						btnAlign: 'c',
						offset: 'auto',
						success: function (layero, index) {//打开层之后可执行的回调
							$.ajax({
								url: contextPath + "/cloneCar/getCloneCarDetail",
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
										$("#showCloneCarDetail").html('');
										var result = returnObj.data;
										console.log(result)
										if (result.length > 0) {
											for (let i = 0; i < result.length; i++) {
												var content = (
													"<li class='layui-timeline-item'>" +
														"<i class='layui-icon layui-timeline-axis'></i>" +
														"<div class='layui-timeline-content layui-text'>" +
															"<h3 class='layui-timeline-title'>入场时间：" + (result[i].parkDate == null?"无":until.formatDateTime(result[i].parkDate)) + "</h3>" +
															"<p>出场时间：" + (result[i].outDate == null?"无":until.formatDateTime(result[i].outDate))+ "</p>" +
															"<p>停车状态："+ (result[i].status == 1?'<font color="green">停车中</font>':'<font color="red">已结束停车</font>') + "</p>" +
															"<p>车牌号：" + (result[i].licensePlate == null?"无":result[i].licensePlate) + "</p>" +
															"<p>停车场名称：" + (result[i].parkName == null?"无":result[i].parkName) + "</p>" +
															"<p>停车场地址：" + (result[i].locations == null?"无":result[i].locations) + "</p>" +
															"<p>停车费用： " + (result[i].fee == null?"0":result[i].fee)+"元" + "</p>" +
															/*"<p>停车时长： " + (result[i].parkingTime == null?"无":result[i].parkingTime) + "</p>" +*/
														"</div>" +
													"</li>"
												);
												$("#cloneCarContent").append(content);
											}
										} else {
											$("#cloneCarContent").append("<p>暂无停车记录</p>");
										}
									}
								}
							});
						},
						end: function () { //只要层被销毁了，end都会执行
							$('#showCloneCarDetail').html(showContent);
						}
					});
		     }
		  });
		 
	});
});