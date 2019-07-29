define(['jquery', 'layui', 'until'], function ($, LAYUI, until) {
	layui.use(['table', 'form'], function () {
			var table = layui.table;
			var form = layui.form;
			var cheakNameFlag = true;
			var oldLicensePlateName;
			table.render({
				elem : '#secondWhiteListTable',
				 url : contextPath + "/parkingWhiteList/rendTable",
			  method : 'post',
		cellMinWidth : 80, 
				skin : 'line',
			   where : {
					 sortName: 'createTime',
					direction: 'desc'
				},
			    cols : [[
					{ field: 'parkName', title: '停车场名称', sort: true, align: 'center' },
					{ field: 'licensePlate', title: '车牌号码', sort: true, align: 'center'},
					{ field: 'createTime', title: '创建时间', sort: true, align: 'center',templet: function (data) {
							if(data.createTime == null){
			            		 return "";
			            	 } else {
			            		 return until.formatDateTime(data.createTime);
			            	 }
						}
					},
					{ field: 'status', title: '状态', sort: true, align: 'center',templet: function (data){
						if(data.status == 1){
							return '<font color="green">启用</font>';
						}else {
							return '<font color="red">禁用</font>';
						}
					  }
					},
					{ fixed: 'right', title:'操作', toolbar: '#secondWhiteListTableBar', align: 'center'}
			   ]],
			response: {
				countName: 'totalElements', 
				 dataName: 'content' 
			},
			page: true
		});
			
			
		/**
		* 排序方法
		*/
		table.on('sort(secondWhiteListTable)', function (obj) {
			table.reload('secondWhiteListTable', {
				initSort: obj, 
				where: { 
					field: obj.field, 
					order: obj.type 
				}
			});
		});
		
		
		/**
		 * 添加车牌点击事件
		 */
		$("#addParkingLicensePlate").click(function () {
			editLicensePlate("add");
		});
		
		/**
		 * 监听行工具事件
		 */
		 table.on('tool(secondWhiteListTable)', function(obj) {
		     var data = obj.data;
		     if(obj.event === 'del'){ //执行删除方法
		         layer.confirm('真的删除么', function(index){
		        	 $.ajax({
		 					  url : contextPath+"/parkingWhiteList/delete",
		 				 dataType : 'json',
		 					 type : 'post',
		 					async : false,
		 					 data : {
		 						secondWhiteListId : data.secondWhiteListId
		 					  },
		 				 complete : function(XHR, TS){
	 						  var returnObj = eval('(' + XHR.responseText + ')');
	 						  if(returnObj.code != 200) {
	 							 layer.msg(returnObj.msg, {icon: 2, time: 1500});
	 						  } else {
	 							 layer.msg("操作成功", {icon: 1, time: 1500});		 							 
	 						  }
	 						 table.reload('secondWhiteListTable');
	 					  }
	 				});
		         });
		     } else if(obj.event === 'edit') {	//执行编辑方法
		    	 editLicensePlate("edit",data);
		     }else if(obj.event === 'disableWhiteList'){ //执行禁用方法
		    	 ableOrDisable("disable",data);
		     }else if(obj.event === 'ableWhiteList'){ //执行启用方法
		    	 ableOrDisable("able",data);
		     }
		     
		  });
		 
		 /**
		  * 禁用或启用
		  */
		 function ableOrDisable(type,data) {
			 var titel = "真的禁用吗？";
			 var statu = "0";
			 if(type == "able"){
				 titel = "真的启用吗？";
				 statu = "1";
			 }
			 layer.confirm(titel, function(index){
	        	 $.ajax({
	 					  url : contextPath+"/parkingWhiteList/changestatus",
	 				 dataType : 'json',
	 					 type : 'post',
	 					async : false,
	 					 data : {
	 						secondWhiteListId : data.secondWhiteListId,
	 						           status : statu
	 					  },
	 				 complete : function(XHR, TS){
 						  var returnObj = eval('(' + XHR.responseText + ')');
 						  if(returnObj.code != 200) {
 							 layer.msg(returnObj.msg, {icon: 2, time: 1500});
 						  } else {
 							 layer.msg("操作成功", {icon: 1, time: 1500});		 							 
 						  }
 						 table.reload('secondWhiteListTable');
 						 form.render();  //重新渲染表单
 					  }
 				});
	         });
		 };

		//编辑或添加停车场的具体操作
		function editLicensePlate(type,data) {
			var showTitle = '编辑';
			var responseUrl = "/parkingWhiteList/edit";
			var secondWhiteListIds = 0;
			if (type == "add") {
				showTitle = '添加'
				responseUrl = "/parkingWhiteList/add";
			} else {
				secondWhiteListIds = data.secondWhiteListId;
			};
			var showContent = $('#showSecondWhiteListContent').html();
			$('#showSecondWhiteListContent').html("");

			layer.open({
				type : 1,
				  id : 'showLayui',
			   title : showTitle,
			 content : showContent, 
				area : ['500px','250px'],
				 btn : ['确定', '取消'],
			btnAlign : 'c',

				success: function (layero, index) {
					layui.use(['form', 'layer'], function(){
				 		var form = layui.form;
				 		var layer = layui.layer;
				 		$("#parkName").empty();
						$("#parkName").append('<option value="" selected="selected">请选择</option>');
						$.ajax ({
						 	url : contextPath + "/parkingWhiteList/getParking",
						 	dataType : 'json',
						 	type : 'post',
						 	async : false,
						 	data : {},
						 	complete : function(XHR, TS) {
						 		var returnObj = eval('(' + XHR.responseText + ')');
						 		$.each(returnObj,function(i,obj){
						 			$("#parkName").append('<option value="'+obj.id+'">'+obj.park_name+'</option>');
						 		});
						 		form.render();  //重新渲染表单
						 	}
					     });
						
				 		if(type == "edit"){
					 		//赋值
				 			oldLicensePlateName = data.licensePlate;
						 	form.val('secondWhiteListFrom', { //给表单里面的字段赋值  通过name属性
						 		"parkingLicensePlate": data.licensePlate,
						 		           "parkName": data.parkingId
						 	});
					 	}
				 		form.render();  //重新渲染表单
						$("#parkingLicensePlate").change(function() {
							var str = $("#parkingLicensePlate").val();
							if(!isLicensePlate(str)){
								layer.msg("请输入正确车牌号");
								 cheakNameFlag = false;
								return;
							}else{
								cheakNameFlag = true;
								return;
							}
						});
						
					});
				}, //success结束

				yes: function (index, layero) { //确定按钮
					var msgArr = ["停车场名称不能为空","车牌号码不能为空"];  //提示语集合
					var clsArr = ["#parkName","#parkingLicensePlate"];  //id集合
					var validateFlag = until.validate(msgArr, clsArr); //非空验证 需要引入工具js文件until
					if (validateFlag) {
						layer.msg(validateFlag, { anim: 6, time: 2500 });
						return;
					};
	 				if(cheakNameFlag == false){
	 					layer.msg("请输入正确的车牌号" , {anim: 6, time: 1500});  
	 					return ;
	 				}
	 				var licensePlate = $("#parkingLicensePlate").val();
					var parkingId = $("#parkName").val();
					if(parkingId == ""){
						layer.msg("请先选择停车场", { anim: 6, time: 2500 });
						$("#parkName").focus();
					}else{
						$.ajax ({
							url : contextPath + "/parkingWhiteList/checkRepeat",
							dataType : 'json',
							type : 'post',
							async : false,
							data : {
								licensePlate: licensePlate,
								parkingId: parkingId
							},
							complete : function(XHR, TS) {
								var returnObj = eval('(' + XHR.responseText + ')');
								if(returnObj.code != 200 && licensePlate != oldLicensePlateName){
									layer.msg("该车牌号码已存在,请重新输入新的车牌号码", { anim: 6, time: 2500 });
									return;
								}else{
									$.ajax({
										   url: contextPath + responseUrl,
									  dataType: 'json',
										  type: 'post',
										 async: false,
										  data: {
											   parkingId: $("#parkName").val(),
											licensePlate: $("#parkingLicensePlate").val(),
									   secondWhiteListId: secondWhiteListIds
										},
										complete: function (XHR, TS) {
											var returnObj = eval('(' + XHR.responseText + ')');
											if (returnObj.code != 200) {
												layer.msg(returnObj.msg, { icon: 2, time: 1500 });
											} else {
												layer.msg("操作成功", { icon: 1, time: 1500 });
											}
											layer.close(index);
											table.reload('secondWhiteListTable');
										}
									});
								}
							}
					});
					
				}
			},

				end: function () { //只要层被销毁了，end都会执行
					$('#showSecondWhiteListContent').html(showContent);
				}

			});
		} //编辑或添加停车场的具体操作 end
	
	});
	function isSettleAccountsDay(str){
    	var reg = new RegExp("^[1-9]+[0-9]*$");
    	if(!reg.test(str)){
    		return false;
    	}
	    return true;
		}
	function isLicensePlate(str){
		var reg = /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$/;	
		if(!reg.test(str)){
			return false;
		}
		return true;
	}
});