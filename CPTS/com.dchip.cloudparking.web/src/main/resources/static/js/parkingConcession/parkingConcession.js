define(['jquery', 'layui','until'], function($,LAYUI,until){
	$("#searchConcessionParking").attr("autocomplete","off").prepend('<input type="password" class="none" />');
	layui.use(['table','form','laydate'], function(){
		 var table = layui.table;
		 var form = layui.form;
	     var laydate = layui.laydate;
	     if(i18n == 'en_US'){
	    	 table.render({
					elem : '#parkingConcessionTable',
					 url : contextPath+"/parkingConcession/rendering",
				  method : 'post',
			cellMinWidth : 150 ,//全局定义常规单元格的最小宽度，layui 2.2.1 新增
					skin : 'line',
				   where : { },
				    cols : [[
	                    { field:'parkingConcessionId',hide:true },
	                    { field:'parkingId', hide:true },
	                    { field:'parkName', title: 'parkName', align: 'center', sort:true},
	                    { field:'licensePlate', title: 'licensePlate', align: 'center', sort:true},
	                    { field:'spaceNo', title: 'spaceNo', align: 'center', sort:true},
	                    { field:'effectDuring', title: 'effectDuring', align: 'center', sort:true, templet: function(data){
	                          return (data.effectDuringTime != undefined?data.effectDuringTime :"")+ " "+(data.effectDuringDate!= undefined?data.effectDuringDate :"");
	                       }
	                    },
	                    { field:'phone', title: 'phone', align: 'center', sort:true},
	                    { field:'userName', title: 'userName', align: 'center', sort:true},
	                    // { field:'tenantId', hide:true, title: '承租人id'},
	                    // { field:'tenantName', title: '承租人姓名', align: 'center'},
	                    { field:'cost', title: '费用', align: 'center', sort:true, templet: function(data){
	                            return data.cost+"元";
	                       }
	                    },
	                    { field:'rentTime', title: 'rentTime', align: 'center', sort:true, templet: function(data){
	                            if(data.rentTime == null){
	                                return "";
	                            }else{
	                                return until.formatDateTime(data.rentTime);
	                            }
	                       }
	                    },
	                    { field:'publishTime', title: 'publishTime', align: 'center', sort:true, templet: function(data){
	                            if(data.publishTime == null){
	                                return "";
	                            }else{
	                                return until.formatDateTime(data.publishTime);
	                            }
	                        }
	                    },
	                    {
	                    	field:'status', title: 'status',align: 'center', sort:true, templet: function(data){
	                            if(data.status == -3){
	                                return "<p style='color:green'>待审核</p>";
	                            }else if(data.status == -4){
	                                return "<p style='color:red'>不通过</p>";
	                            }else if(data.status == 0){
	                                return "<p style='color:green'>未领用</p>";
	                            }else if(data.status == 1){
	                                return "<p style='color:red'>已领用</p>";
	                            }else if(data.status == 2){
	                                return "<p style='color:red'>已过期</p>";
	                            }else if(data.status == -1){
	                                return "<p style='color:red'>禁用</p>";
	                            }else if(data.status == -2){
	                                return "<p style='color:red'>软删</p>";
	                            }else{
	                            	return data.status != undefined ? data.status:"";
								}
	                    	}
	                    },
	                    // { fixed: 'right', title: '操作', toolbar: '#parkingConcessionBar', align: 'center', width:150 },
	                    { fixed: 'right', title: 'right', align: 'center', width:180, templet: function(data){
	                            var button = "";
	                            if (data.status == -3) {
	                                button += '<a class="layui-btn layui-btn-xs" lay-event="examine">ToExamine</a>';
	                            }
	                            button += '<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="Del">Del</a>';
	                            return button;
	                        }
	                     },
				        ]],
				 response : {
				        countName: 'totalElements', //规定数据总数的字段名称，默认：count
				        dataName: 'content' //规定数据列表的字段名称，默认：data
				      }, 
					 page : true
				}); 
	     }else{
	    	 table.render({
					elem : '#parkingConcessionTable',
					 url : contextPath+"/parkingConcession/rendering",
				  method : 'post',
			cellMinWidth : 150 ,//全局定义常规单元格的最小宽度，layui 2.2.1 新增
					skin : 'line',
				   where : { },
				    cols : [[
	                    { field:'parkingConcessionId',hide:true },
	                    { field:'parkingId', hide:true },
	                    { field:'parkName', title: '停车场名字', align: 'center', sort:true},
	                    { field:'licensePlate', title: '车牌号', align: 'center', sort:true},
	                    { field:'spaceNo', title: '车位号', align: 'center', sort:true},
	                    { field:'effectDuring', title: '有效时间段', align: 'center', sort:true, templet: function(data){
	                          return (data.effectDuringTime != undefined?data.effectDuringTime :"")+ " "+(data.effectDuringDate!= undefined?data.effectDuringDate :"");
	                       }
	                    },
	                    { field:'phone', title: '电话号码', align: 'center', sort:true},
	                    { field:'userName', title: '出租人姓名', align: 'center', sort:true},
	                    // { field:'tenantId', hide:true, title: '承租人id'},
	                    // { field:'tenantName', title: '承租人姓名', align: 'center'},
	                    { field:'cost', title: '费用', align: 'center', sort:true, templet: function(data){
	                            return data.cost+"元";
	                       }
	                    },
	                    { field:'rentTime', title: '租赁成功时间', align: 'center', sort:true, templet: function(data){
	                            if(data.rentTime == null){
	                                return "";
	                            }else{
	                                return until.formatDateTime(data.rentTime);
	                            }
	                       }
	                    },
	                    { field:'publishTime', title: '租赁信息发布时间', align: 'center', sort:true, templet: function(data){
	                            if(data.publishTime == null){
	                                return "";
	                            }else{
	                                return until.formatDateTime(data.publishTime);
	                            }
	                        }
	                    },
	                    {
	                    	field:'status', title: '租赁信息状态',align: 'center', sort:true, templet: function(data){
	                            if(data.status == -3){
	                                return "<p style='color:green'>待审核</p>";
	                            }else if(data.status == -4){
	                                return "<p style='color:red'>不通过</p>";
	                            }else if(data.status == 0){
	                                return "<p style='color:green'>未领用</p>";
	                            }else if(data.status == 1){
	                                return "<p style='color:red'>已领用</p>";
	                            }else if(data.status == 2){
	                                return "<p style='color:red'>已过期</p>";
	                            }else if(data.status == -1){
	                                return "<p style='color:red'>禁用</p>";
	                            }else if(data.status == -2){
	                                return "<p style='color:red'>软删</p>";
	                            }else{
	                            	return data.status != undefined ? data.status:"";
								}
	                    	}
	                    },
	                    // { fixed: 'right', title: '操作', toolbar: '#parkingConcessionBar', align: 'center', width:150 },
	                    { fixed: 'right', title: '操作', align: 'center', width:180, templet: function(data){
	                            var button = "";
	                            if (data.status == -3) {
	                                button += '<a class="layui-btn layui-btn-xs" lay-event="examine">审核</a>';
	                            }
	                            button += '<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>';
	                            return button;
	                        }
	                     },
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
				$("#searchParkingName").empty();
				$("#searchParkingName").append('Search');
				$("#searchConcessionParking").attr("placeholder","ParkingName");
			}
			
			/**
			 * 搜索方法
			 */
			$("#searchParkingName").click(function() {
				table.reload('parkingConcessionTable', {
					  where : { //设定异步数据接口的额外参数，任意设
						searchParking: $("#searchConcessionParking").val(),
					  },
					  page : {curr: 1 }  //重新从第 1 页开始
				});
			 });
	
			/**
			 * 监听行工具事件
			 */
			 table.on('tool(parkingConcessionTable)', function(obj) {
				 var data = obj.data;
				 if(obj.event === 'del'){ //执行删除方法
					 layer.confirm('真的删除么', function(index){
			        	 $.ajax({
			 					  url : contextPath+"/parkingConcession/delete",
			 				 dataType : 'json',
			 					 type : 'post',
			 					async : false,
			 					 data : {
			 						parkingConcessionId : data.parkingConcessionId
			 					  },
			 				 complete : function(XHR, TS){
		 						  var returnObj = eval('(' + XHR.responseText + ')');
		 						  if(returnObj.code != 200) {
		 							 layer.msg(returnObj.msg, {icon: 2, time: 1500});
		 						  } else {
		 							 layer.msg("操作成功", {icon: 1, time: 1500});		 							 
		 						  }
		 						 table.reload('parkingConcessionTable');
		 					  }
		 				});
			         });
				 }else if(obj.event == 'examine'){
                     examine(data);
				 }
			});

			 //审核 start
        function examine(data) {
            var showTitle = '审核';
            var responseUrl = "/parkingConcession/pass";
            var showContent = $('#examineContent').html();
            $('#examineContent').html("");

            layer.open({
                type: 1,
                id: 'showLayui',
                title: showTitle,
                content: showContent, //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                area: ['20%'],
                btn: ['确定', '取消'],
                btnAlign: 'c',
                success: function (layero, index) {
                    form.on('switch(isPassFilter)', function(data){
                        if(data.elem.checked){
                            $("#isPassHidden").val(1)
                            console.log(1)
                        }else {
                            $("#isPassHidden").val(0)
                            console.log(1)
                        }
                    });
                    form.render();
                }, //success结束
                yes: function (index, layero) { //确定按钮
                    if($("#isPassHidden").val() == 0){
                        responseUrl = "/parkingConcession/notPass";
                    }
                    $.ajax({
                        url: contextPath + responseUrl,
                        dataType: 'json',
                        type: 'post',
                        async: false,
                        data: {
                            parkingConcessionId:data.parkingConcessionId
                        },
                        complete: function (XHR, TS) {
                            var returnObj = eval('(' + XHR.responseText + ')');
                            if (returnObj.code != 200) {
                                layer.msg(returnObj.msg, {icon: 2, time: 1500});
                            } else {
                                layer.msg("操作成功", {icon: 1, time: 1500});
                            }
                            layer.close(index);
                            table.reload('parkingConcessionTable');
                        }
                    });
                },
                end: function () { //只要层被销毁了，end都会执行
                    $('#examineContent').html(showContent);
                }
            });
        } //审核 end
	});
});