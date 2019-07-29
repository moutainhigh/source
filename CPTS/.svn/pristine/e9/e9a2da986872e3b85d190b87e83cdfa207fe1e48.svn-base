define(['jquery', 'layui','until'], function($,LAYUI,until){
	layui.use(['table','form','laydate'], function(){
		var table = layui.table;
		var form = layui.form;
	    var laydate = layui.laydate;
	    var oldActivityName;			//保存原来的角色名称
	    var cheakNameFlag = true;   //检查角色重名标志  false为重名，true 为不重名
	    if(i18n == 'en_US'){
			table.render({
				elem : '#activityManageTable',
				url : contextPath+"/activityManage/rendering",
			    method : 'post',
		        cellMinWidth : 150 ,//全局定义常规单元格的最小宽度，layui 2.2.1 新增
				skin : 'line',
			    where : { //默认排序字段
					sortName: 'createTime',
					direction: 'desc'
				},
			    cols : [[
			         { field:'activityId',hide:true },
			         { field:'activityRemark', title: 'activityName', sort:true, align: 'center'},
			         { 
			        	 field:'effectiveType', title: 'effectiveType', sort:true, align: 'center',templet: function(data){
				    	     if(data.effectiveType == 1){
			            		 return "长期有效";
			            	 } else {
			            		 return "时间内有效";
			            	 }
				         }
			         },
	                 { 
			        	 field:'effectiveTime', title:'effectiveTime', sort:true, align: 'center', templet: function(data){
	                        if(data.effectiveTime ){
	                            return until.formatDateTime(data.effectiveTime);
	                        }else{
	                            return "无";
	                        }
			        	 }
			         },
	                 { 
			        	field:'createTime', title:'createTime', sort:true, align: 'center', templet: function(data){
	                        if(data.createTime == null){
	                            return "";
	                        }else{
	                            return until.formatDateTime(data.createTime);
	                        }
			        	}
			         },
	                 { field:'couponId', hide:true },
					 { field:'couponRemark', title:'couponRemark', sort:true, align: 'center'},
	                // {
	                //     field:'activityStatus',title:'活动状态',align: 'center',templet: function(data){
	                //         if(data.activityStatus == 0){
	                //             return "<p style='color:green'>正常</p>";
	                //         }else if(data.activityStatus == 1){
	                //             return "<p style='color:red'>禁用</p>";
	                //         }else{
	                //             return data.activityStatus != undefined ? data.activityStatus : "";
	                //         }
	                //     }
	                // },
	                 { field:'couponCount',title:'couponNum', sort:true, align: 'center'},
	                 { field:'couponStatus',title:'couponStatus', sort:true, align: 'center',templet: function(data){
	                        if(data.couponStatus == 0){
	                            return "<p style='color:green'>正常</p>";
	                        }else if(data.couponStatus == 1){
	                            return "<p style='color:red'>禁用</p>";
	                        }else{
	                        	return data.couponStatus != undefined ? data.couponStatus : "";
							}
	                    }
	                 },
			         { fixed: 'right', title:'right', width:150, align: 'center', templet: function (data) {
	                         var button = "";
	                         // if (data.status == 1) {
	                         //     button += "<button lay-event='changeStatus' class='layui-btn layui-btn-xs'>解禁</button>";
	                         // } else if(data.status == 0){
	                         //     button += "<button lay-event='changeStatus' class='layui-btn layui-btn-warm layui-btn-xs'>禁用</button>";
	                         // }
	                         button += "<button lay-event='edit' class='layui-btn layui-btn-xs'>Edit</button>";
	                         button += "<button lay-event='del' class='layui-btn layui-btn-danger layui-btn-xs'>Del</button>";
	                         return button;
	                     }
	                 }
			        ]],
			    response : {
			        countName: 'totalElements', //规定数据总数的字段名称，默认：count
			        dataName: 'content' //规定数据列表的字段名称，默认：data
			      }, 
				page : true
			}); 
	    }else{
			table.render({
				elem : '#activityManageTable',
				url : contextPath+"/activityManage/rendering",
			    method : 'post',
		        cellMinWidth : 150 ,//全局定义常规单元格的最小宽度，layui 2.2.1 新增
				skin : 'line',
			    where : { //默认排序字段
					sortName: 'createTime',
					direction: 'desc'
				},
			    cols : [[
			         { field:'activityId',hide:true },
			         { field:'activityRemark', title: '活动名称', sort:true, align: 'center'},
			         { 
			        	 field:'effectiveType', title: '活动有效类型', sort:true, align: 'center',templet: function(data){
				    	     if(data.effectiveType == 1){
			            		 return "长期有效";
			            	 } else {
			            		 return "时间内有效";
			            	 }
				         }
			         },
	                 { 
			        	 field:'effectiveTime', title:'活动有效时间', sort:true, align: 'center', templet: function(data){
	                        if(data.effectiveTime ){
	                            return until.formatDateTime(data.effectiveTime);
	                        }else{
	                            return "无";
	                        }
			        	 }
			         },
	                 { 
			        	field:'createTime', title:'活动创建时间', sort:true, align: 'center', templet: function(data){
	                        if(data.createTime == null){
	                            return "";
	                        }else{
	                            return until.formatDateTime(data.createTime);
	                        }
			        	}
			         },
	                 { field:'couponId', hide:true },
					 { field:'couponRemark', title:'优惠券说明', sort:true, align: 'center'},
	                // {
	                //     field:'activityStatus',title:'活动状态',align: 'center',templet: function(data){
	                //         if(data.activityStatus == 0){
	                //             return "<p style='color:green'>正常</p>";
	                //         }else if(data.activityStatus == 1){
	                //             return "<p style='color:red'>禁用</p>";
	                //         }else{
	                //             return data.activityStatus != undefined ? data.activityStatus : "";
	                //         }
	                //     }
	                // },
	                 { field:'couponCount',title:'优惠券发放数量', sort:true, align: 'center'},
	                 { field:'couponStatus',title:'优惠券状态', sort:true, align: 'center',templet: function(data){
	                        if(data.couponStatus == 0){
	                            return "<p style='color:green'>正常</p>";
	                        }else if(data.couponStatus == 1){
	                            return "<p style='color:red'>禁用</p>";
	                        }else{
	                        	return data.couponStatus != undefined ? data.couponStatus : "";
							}
	                    }
	                 },
			         { fixed: 'right', title:'操作', width:150, align: 'center', templet: function (data) {
	                         var button = "";
	                         // if (data.status == 1) {
	                         //     button += "<button lay-event='changeStatus' class='layui-btn layui-btn-xs'>解禁</button>";
	                         // } else if(data.status == 0){
	                         //     button += "<button lay-event='changeStatus' class='layui-btn layui-btn-warm layui-btn-xs'>禁用</button>";
	                         // }
	                         button += "<button lay-event='edit' class='layui-btn layui-btn-xs'>编辑</button>";
	                         button += "<button lay-event='del' class='layui-btn layui-btn-danger layui-btn-xs'>删除</button>";
	                         return button;
	                     }
	                 }
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
			$("#addActivity").empty();
			$("#addActivity").append('AddActivity');
			$("#activityName").empty();
			$("#activityName").append('Name');
			$("#EffectiveType").empty();
			$("#EffectiveType").append('Effective');
			$("#EffectiveTime").empty();
			$("#EffectiveTime").append('Time');
			$("#couponType").empty();
			$("#couponType").append('Type');
			$("#couponNum").empty();
			$("#couponNum").append('CouponNum');
		}
	    
		/**
		 * 排序方法
		 */
		table.on('sort(activityManageTable)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
			table.reload('activityManageTable', {
			    initSort: obj, //记录初始排序，如果不设的话，将无法标记表头的排序状态
			    where: { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
			    	sortName: obj.field, //排序字段
			        direction: obj.type //排序方式
			    }
			});
		});
		
		/**
		 * 监听行工具事件
		 */
		 table.on('tool(activityManageTable)', function(obj) {
		     var data = obj.data;
		     var titelDel = "真的删除吗";
	            if(i18n == 'en_US'){
	            	 titelDel = "Do you really delete it?";
	            }
		     if(obj.event === 'del'){ //执行删除方法
		         layer.confirm(titelDel, function(index){
		        	 $.ajax({
		 					  url : contextPath+"/activityManage/delete",
		 				 dataType : 'json',
		 					 type : 'post',
		 					async : false,
		 					 data : {
		 						activityId : data.activityId
		 					  },
		 				 complete : function(XHR, TS){
	 						  var returnObj = eval('(' + XHR.responseText + ')');
	 						  if(returnObj.code != 200) {
	 							 layer.msg(returnObj.msg, {icon: 2, time: 1500});
	 						  } else {
	 							 layer.msg("操作成功", {icon: 1, time: 1500});		 							 
	 						  }
	 						 table.reload('activityManageTable');
	 					  }
	 				});
		         });
		     } else if(obj.event === 'edit') {	//执行编辑方法
	             editActivity("edit",data);
	         }else if(obj.event === 'changeStatus') {	//执行编辑方法
	             layer.confirm('是否'+(function(status){if(status == 1){return "解禁";}else {return "禁用";}})(data.status)+'该优惠券',
	                 function (index) {
	                     $.ajax({
	                         url: contextPath + "/activityManage/changeActivityStatus",
	                         dataType: 'json',
	                         type: 'post',
	                         data: {
	                             activityId:data.activityId,
	                             activityStatus:data.status
	                         },
	                         complete: function (XHR, TS) {
	                             var returnObj = eval('(' + XHR.responseText + ')');
	                             if (returnObj.code != 200) {
	                                 layer.msg(returnObj.msg, { icon: 2, time: 1500 });
	                             }
	                             layer.close(index);
	                             table.reload('activityManageTable');
	                         }
	                     });
	             });
	         }
		  });
	 
		    //失去焦点判断重名
			$(document).off("blur", "#activityRemark").on("blur", "#activityRemark", function(){
				var thisName = $(this).val();
				if((thisName != oldActivityName) && (thisName)){
					$.ajax({
						url: contextPath + "/activityManage/checkActivityName",
						dataType: "json",
						type: "post",
						data: {
							activityName: thisName
						},
						complete: function(XHR, TS){
							var returnObj = eval('('+ XHR.responseText +')');
							if(returnObj.code != 200){
								cheakNameFlag = false;
								$("#activityRemark").focus();
								layer.msg(returnObj.msg, {time: 1500});
							}else{
								cheakNameFlag = true;	 							 
							}
						}
					});
				}
			});
	 
			
			/**
			  * 添加活动按钮点击事件
			  */
			 $("#addActivity").click(function() {
				 editActivity("add");
			 });
			 
			//编辑或添加停车场的具体操作
			 function editActivity(type,data) {
				var showTitle = '编辑活动';
				var responseUrl = "/activityManage/edit";
				var activityId = 0;
				if(type == "add"){
					 showTitle = '添加活动';
					 responseUrl = "/activityManage/add";
				} else {
					 activityId = data.activityId;  //编辑页面获取的活动ID
				};
				var showContent = $('#showActivityContent').html();
				$('#showActivityContent').html("");
				
				layer.open({
				 		type : 1,
				 		  id : 'showLayui',
				 	   title : showTitle,
				 	 content : showContent, //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
				 		area : ['600px','400px'],
				 		 btn : ['确定', '取消'],
				 	btnAlign : 'c',
				 	
				 	 success : function(layero, index) {
				 			layui.use(['form', 'layer','laydate'], function(){
				 					 var form = layui.form;
				 					var layer = layui.layer;
				 					var laydate = layui.laydate;

				 					//时间插件
				 					laydate.render({
				 				        elem: '#effectiveTime', //指定元素
				 				        type: 'datetime'
				 				     });
				 					
				 					//活动有效类型的监听事件
				 					form.on('select(effectiveType)', function(data){
				 						if(data.value == 1){
											$("#setEffectiveTime").css("display","none");
											$("#effectiveTime").val("");
										}else{
                                            $("#setEffectiveTime").css("display","");
										}
				 					});

									$("#couponId").empty();
									// $("#couponId").append('<option value="" selected="selected">请选择</option>');
									$.ajax({
										url: contextPath + "/couponManage/queryAll",
										dataType: 'json',
										type: 'post',
										async: false,
										data: {},
										complete: function (XHR, TS) {
											var returnObj = eval('(' + XHR.responseText + ')');
											var objs = returnObj.data.content;
//											var reArrRemark = new Array();
//											var  arrRemark = new Array();
//											for(var i = 0;i<objs.length;i++){
//												reArrRemark.push(objs[i].couponRemark);
//											}
//											reArrRemark.sort();
//											for(var i = 0; i<reArrRemark.length;i++){
//												if(reArrRemark[i-1] != reArrRemark[i]){
//													arrRemark.push(reArrRemark[i]);
//												}
//											}
											for(var i in objs){
                                                if(objs[i].status == 0){
                                                    $("#couponId").append('<option value="' + objs[i].couponId + '">' + objs[i].couponRemark + '</option>');
												}
											}
											form.render(); //重新渲染表单
										}
									});

				 					if(type == "edit"){
				 						oldActivityName = data.activityRemark;
				 					    //赋值
					 					form.val('activityFrom', { //给表单里面的字段赋值  通过name属性
					 					    "activityRemark": data.activityRemark,
					 					     "effectiveType": data.effectiveType,
					 						 "effectiveTime": until.formatDateTime(data.effectiveTime),
					 						      "couponId": data.couponId,
					 						  "couponRemark": data.couponRemark,
					 						  "couponCount":  data.couponCount
					 					 });
                                        if(data.effectiveType == 2){
                                            $("#setEffectiveTime").css("display","");
                                        }
				 					}
				 					form.render();  //重新渲染表单
				 				});
				 			},
				 			
				 	  yes: function(index, layero){ //确定按钮
				 				var msgArr = ["活动名称不能为空","优惠券发放数量不能为空"];  //提示语集合
				 				var clsArr = ["#activityRemark","#couponCount"];  //id集合
						        if($("#effectiveType option:selected").val() == 2){
						        	msgArr.push("有效时间不能为空");
									clsArr.push("#effectiveTime");
								}
								msgArr.push("优惠券类型");
                                clsArr.push("#couponId");
				 				var validateFlag = until.validate(msgArr, clsArr); //非空验证 需要引入工具js文件until
				 				if(validateFlag) {
				 					layer.msg(validateFlag , {anim: 6, time: 2500});  
				 					return ;
				 				};
				 				if(cheakNameFlag == false){
				 					layer.msg("活动名称不能重复" , {anim: 6, time: 1500});  
				 					return ;
			                    }
				 				console.log($("#effectiveType").val())
				 				$.ajax ({
				 					    url : contextPath + responseUrl,
				 				   dataType : 'json',
				 					   type : 'post',
				 					  async : false,
				 					   data : {
				 						    activityId: activityId,
								        activityRemark: $("#activityRemark").val(),
                                         effectiveType: $("#effectiveType").val(),
							       effectiveTimeString: $("#effectiveTime").val(),
                                              couponId: $("#couponId").val(),
                                           couponCount: $("#couponCount").val()
				 					  },
				 					complete : function(XHR, TS) {
				 						  var returnObj = eval('(' + XHR.responseText + ')');
				 						  if(returnObj.code != 200) {
				 							 layer.msg(returnObj.msg, {icon: 2, time: 1500});
				 						  } else {
				 							 layer.msg("操作成功", {icon: 1, time: 1500});		 							 
				 						  }
				 						 layer.close(index); 
				 						 table.reload('activityManageTable');
				 					  }
				 				});
				 			},
				 			
				 	end: function(){ //只要层被销毁了，end都会执行
				 			$('#showActivityContent').html(showContent);
				 		}
				 			
				});
			} //编辑或添加停车场的具体操作 end
			 
		
	});
});