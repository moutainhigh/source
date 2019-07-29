define(['jquery', 'layui', 'until'], function ($, LAYUI, until) {
	layui.use(['table', 'form'], function () {
			var table = layui.table;
			var form = layui.form;
			
			$("#searchLicensePlateName").empty();
			$("#searchLicensePlateName").append('<option value="" selected="selected">请选择</option>');
			$.each(licensePlateName, function(i, obj){
				$("#searchLicensePlateName").append('<option value="'+ obj.licensePlateName +'" >'+ obj.licensePlateName +'</option>');
			});
			
			form.render();
			if(i18n == 'en_US'){
				table.render({
					elem: '#firstWhiteListTable',
					url: contextPath + "/firstWhiteList/rendTable",
				    method: 'post',
			        cellMinWidth: 80, 
					skin: 'line',
				    where: {
						 sortName: 'firstWhiteListId',
						 direction: 'desc'
					},
				    cols: [[
						{ field: 'licensePlateName', title: 'licensePlateName', sort: true, align: 'center'},
						{ field: 'type', title: '摄像机识别类型', sort: true, align: 'center', templet: function (data){
							if(data.type == 1){
								return "摄像机识别类型A";
							}else if(data.type == 2){
								return "摄像机识别类型B";
							}else{
								return "摄像机识别类型C";
							}
						  }
						},
						{ field: 'createTime', title: 'createTime', sort: true, align: 'center',templet: function (data) {
							if(data.createTime == null){
			            		 return "";
			            	 } else {
			            		 return until.formatDateTime(data.createTime);
			            	 }
						   }
						},
						{ fixed: 'right', title: 'right',align: 'center', width:150, templet: function (data) {
							var button = "";
							button += "<button lay-event='edit' class='layui-btn layui-btn-xs'>Edit</button>";
							button += "<button lay-event='del' class='layui-btn layui-btn-danger layui-btn-xs'>Del</button>";
							return button;
						  }
						}
				   ]],
				response: {
					countName: 'totalElements', 
					 dataName: 'content' 
				},
				page: true
			});
				
			}else{
				table.render({
					elem: '#firstWhiteListTable',
					url: contextPath + "/firstWhiteList/rendTable",
				    method: 'post',
			        cellMinWidth: 80, 
					skin: 'line',
				    where: {
						 sortName: 'firstWhiteListId',
						 direction: 'desc'
					},
				    cols: [[
						{ field: 'licensePlateName', title: '车牌类型名称', sort: true, align: 'center'},
						{ field: 'type', title: '摄像机识别类型', sort: true, align: 'center', templet: function (data){
							if(data.type == 1){
								return "摄像机识别类型A";
							}else if(data.type == 2){
								return "摄像机识别类型B";
							}else{
								return "摄像机识别类型C";
							}
						  }
						},
						{ field: 'createTime', title: '更新时间', sort: true, align: 'center',templet: function (data) {
							if(data.createTime == null){
			            		 return "";
			            	 } else {
			            		 return until.formatDateTime(data.createTime);
			            	 }
						   }
						},
						{ fixed: 'right', title: '操作',align: 'center', width:150, templet: function (data) {
							var button = "";
							button += "<button lay-event='edit' class='layui-btn layui-btn-xs'>编辑</button>";
							button += "<button lay-event='del' class='layui-btn layui-btn-danger layui-btn-xs'>删除</button>";
							return button;
						  }
						}
				   ]],
				response: {
					countName: 'totalElements', 
					 dataName: 'content' 
				},
				page: true
			});
			}
			
			//切换英文
			if(i18n == 'en_US'){
				$("#whiteLicensePlateName").empty();
				$("#whiteLicensePlateName").append('LicensePlate');
				$("#searchWhiteList").empty();
				$("#searchWhiteList").append('Search');
				$("#addLicensePlate").empty();
				$("#addLicensePlate").append('AddLicensePlate');
				$("#FirstWhiteType").empty();
				$("#FirstWhiteType").append('Type');
			}
			
		/**
		* 排序方法
		*/
		table.on('sort(firstWhiteListTable)', function (obj) {
			table.reload('firstWhiteListTable', {
				initSort: obj, 
				where: { 
					field: obj.field, 
					order: obj.type 
				}
			});
		});
		
		/**
		 * 搜索方法
		 */
		$("#searchWhiteList").click(function() {
			table.reload('firstWhiteListTable', {
				  where : { //设定异步数据接口的额外参数，任意设
					  searchLicensePlateName: $("#searchLicensePlateName").val()
				  },
				  page : {curr: 1 }  //重新从第 1 页开始
			});
		 });
		
		/**
		 * 添加车牌点击事件
		 */
		$("#addLicensePlate").click(function () {
			editLicensePlate("add");
		});
		
		/**
		 * 监听行工具事件
		 */
		 table.on('tool(firstWhiteListTable)', function(obj) {
		     var data = obj.data;
		     var titelDel = "真的删除吗";
	            if(i18n == 'en_US'){
	            	 titelDel = "Do you really delete it?";
	            }
		     if(obj.event === 'del'){ //执行删除方法
		         layer.confirm(titelDel, function(index){
		        	 $.ajax({
		 					  url : contextPath+"/firstWhiteList/delete",
		 				 dataType : 'json',
		 					 type : 'post',
		 					async : false,
		 					 data : {
		 						firstWhiteListId : data.firstWhiteListId
		 					  },
		 				 complete : function(XHR, TS){
	 						  var returnObj = eval('(' + XHR.responseText + ')');
	 						  if(returnObj.code != 200) {
	 							 layer.msg(returnObj.msg, {icon: 2, time: 1500});
	 						  } else {
	 							 layer.msg("操作成功", {icon: 1, time: 1500});		 							 
	 						  }
	 						 table.reload('firstWhiteListTable');
	 					  }
	 				});
		         });
		     } else if(obj.event === 'edit') {	//执行编辑方法
		    	 editLicensePlate("edit",data);
		     }
		});

		//编辑或添加停车场的具体操作
		function editLicensePlate(type,data) {
			var showTitle = '编辑';
			var responseUrl = "/firstWhiteList/edit";
			var firstWhiteListId = 0;
			var oldFlag = 0;
			if (type == "add") {
				showTitle = '添加'
				responseUrl = "/firstWhiteList/add";
			} else {
				firstWhiteListId = data.firstWhiteListId;
			};
			var showContent = $('#showFirstWhiteListContent').html();
			$('#showFirstWhiteListContent').html("");

			layer.open({
				  type : 1,
				  id : 'showLayui',
				  title : showTitle,
				  content : showContent, 
				  area : ['600px','500px'],
				  btn : ['确定', '取消'],
				  btnAlign : 'c',

				success: function (layero, index) {
					$("#licensePlateNameA").empty();
					$("#licensePlateNameA").append('<option value="" selected="selected">请选择</option>');
					$.each(licensePlateNameA, function(i, obj){
						$("#licensePlateNameA").append('<option value="'+ obj.id +'" >'+ obj.licensePlateName +'</option>');
					});
					$("#licensePlateNameB").empty();
					$("#licensePlateNameB").append('<option value="" selected="selected">请选择</option>');
					$.each(licensePlateNameB, function(i, obj){
						$("#licensePlateNameB").append('<option value="'+ obj.id +'" >'+ obj.licensePlateName +'</option>');
					});
					$("#licensePlateNameC").empty();
					$("#licensePlateNameC").append('<option value="" selected="selected">请选择</option>');
					$.each(licensePlateNameC, function(i, obj){
						$("#licensePlateNameC").append('<option value="'+ obj.id +'" >'+ obj.licensePlateName +'</option>');
					});
					layui.use(['form', 'layer'], function(){
				 		var form = layui.form;
				 		//监听选择框
				 		form.on('radio(recognition)', function(data){						
				 			if(data.value == 1){
				 				$("#typeA").removeClass("none");
				 				$("#typeB").addClass("none");
				 				$("#typeC").addClass("none");
				 			}else if(data.value == 0){
				 				$("#typeB").removeClass("none");
				 				$("#typeA").addClass("none");
				 				$("#typeC").addClass("none");
				 			}else{
				 				$("#typeC").removeClass("none");
				 				$("#typeA").addClass("none");
				 				$("#typeB").addClass("none");
				 			}
				 		}); 
				 		if(data){
				 			console.log(data)
				 			oldFlag = data.licensePlateTypeId;
					 		//赋值
				 			if(data.type == 1){
				 				$("input[name='recognition'][value=1]").attr("checked",true); 
				 				$("#licensePlateNameA").val(data.id);
				 				$("#typeA").removeClass("none");
				 				$("#typeB").addClass("none");
				 				$("#typeC").addClass("none");
				 			}else if(data.type == 2){
				 				$("input[name='recognition'][value=0]").attr("checked",true); 
				 				$("#licensePlateNameB").val(data.id);
				 				$("#typeB").removeClass("none");
				 				$("#typeA").addClass("none");
				 				$("#typeC").addClass("none");
				 			}else{
				 				$("input[name='recognition'][value=2]").attr("checked",true); 
				 				$("#licensePlateNameC").val(data.id);
				 				$("#typeC").removeClass("none");
				 				$("#typeA").addClass("none");
				 				$("#typeB").addClass("none");
				 			}
					 	}
				 		form.render();  //重新渲染表单
					});
				}, //success结束

				yes: function (index, layero) { //确定按钮
					if($('input:radio[name="recognition"]').is(':checked')==false){
						layer.msg("类型不能为空", { anim: 6, time: 2000 });
						return;
					}else{
						var recognition = $("input[name='recognition']:checked").val();
						if(recognition == 1){
							var licensePlateNameA = $("#licensePlateNameA").val();
							if(licensePlateNameA == ""){
								layer.msg("请选择摄像识别类型A", { anim: 6, time: 2000 });
								return;
							}
						}else if(recognition == 0){
							var licensePlateNameB = $("#licensePlateNameB").val();
							if(licensePlateNameB == ""){
								layer.msg("请选择摄像识别类型B", { anim: 6, time: 2000 });
								return;
							}
						}else{
							var licensePlateNameC = $("#licensePlateNameC").val();
							if(licensePlateNameC == ""){
								layer.msg("请选择摄像识别类型C", { anim: 6, time: 2000 });
								return;
							}
						}
					}
					var licensePlateTypeId = 0;
					if($("input[name='recognition']:checked").val() == 1){
						licensePlateTypeId = $("#licensePlateNameA").val();
					}else if($("input[name='recognition']:checked").val() == 0){
						licensePlateTypeId = $("#licensePlateNameB").val();
					}else{
						licensePlateTypeId = $("#licensePlateNameC").val();
					}
					if(licensePlateTypeId != oldFlag){
						$.ajax({
							url: contextPath + responseUrl,
							dataType: 'json',
							type: 'post',
							async: false,
							data: {
								firstWhiteListId: firstWhiteListId,
								licensePlateTypeId: licensePlateTypeId
							},
							complete: function (XHR, TS) {
								var returnObj = eval('(' + XHR.responseText + ')');
								if (returnObj.code != 200) {
									layer.msg(returnObj.msg, { icon: 2, time: 2000 });
								} else {
									layer.msg("操作成功", { icon: 1, time: 2000 });
								}
								layer.close(index);
								table.reload('firstWhiteListTable');
							}
						});
					}else{
						layer.msg("操作成功", { icon: 1, time: 2000 });
						layer.close(index);
						table.reload('firstWhiteListTable');
					}
				},

				end: function () { //只要层被销毁了，end都会执行
					$('#showFirstWhiteListContent').html(showContent);
				}

			});
		} //编辑或添加停车场的具体操作 end
	
	});
});