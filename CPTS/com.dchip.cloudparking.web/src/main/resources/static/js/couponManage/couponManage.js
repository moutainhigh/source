define(['jquery', 'layui','until'], function($,LAYUI,until){
	layui.use(['table','form','laydate'], function(){
		var table = layui.table;
		 var form = layui.form;
	  var laydate = layui.laydate;
	  if(i18n == 'en_US'){
		  table.render({
				elem : '#couponManageTable',
				 url : contextPath+"/couponManage/rendering",
			  method : 'post',
		cellMinWidth : 150 ,//全局定义常规单元格的最小宽度，layui 2.2.1 新增
				skin : 'line',
			   where : {
					sortName: 'createTime',
					direction: 'desc'
			   },
			    cols : [[
                  { field:'couponId',hide:true,sort:true },
                  { field:'count', title: 'count', align: 'center',sort:true},
                  { field:'couponRemark',title:'couponRemark',align: 'center',sort:true},
                  // {
                  // 	field:'deductionType', title: '抵扣类型', align: 'center',templet: function(data){
                  //         if(data.deductionType == 1){
                  //             return "全抵扣";
                  //         } else if(data.deductionType == 2){
                  //             return "分次抵扣";
                  //         }else{
                  //             return data.deductionType != undefined ? data.deductionType : "";
                  //         }
                  //     }
                  //  },
                  // { field:'partDeduction', title: '每次抵扣数', align: 'center'},
                  // {
					{	field:'deductionType', title: 'deductionType', align: 'center',sort:true,templet: function(data){
                          if(data.deductionType == 1){
                              return "全抵扣";
                          } else if(data.deductionType == 2){
                              return "分次抵扣"+data.partDeduction+"元";
                          }else{
                              return data.deductionType != undefined ? data.deductionType : "";
                          }
                      }
                   },
                  { field:'memberIds',hide:true,sort:true },
                  { field:'memberIdNames', title: 'memberIdNames', align: 'center',sort:true },
                  // { field:'endNum', title: '截止数', align: 'center'},
			        // {
			        // 	field:'endType', title: '截止日期类型', align: 'center', templet: function(data){
					// 		 if(data.endType == 1){
					// 			 return "日";
					// 		 } else if(data.endType == 2){
					// 			 return "月";
					// 		 }else if(data.endType == 3){
					// 			 return "年";
					// 		 }else{
					// 			return data.endType != undefined ? data.endType : "";
					// 		 }
			        // 	}
			        //  },
                  // {
                  // 	field:'endTime',title:'截止时间',align: 'center', templet: function(data){
                  //         if(data.endTime == null){
                  //             return "";
                  //         }else{
                  //             return until.formatDateTime(data.endTime);
                  //         }
					//     }
					// },
                  {
                  	field:'createTime',title:'createTime',align: 'center',sort:"desc",sort:true,templet: function(data){
							   if(data.createTime == null ) {
								  return "";
							   }else{
                                 return until.formatDateTime(data.createTime);
                             }
                  	}
                  },
					{	field:'endTime',title:'endTime',align: 'center',sort:true,templet: function(data){
                          if(data.endType == 1 ) {
                              return data.endNum + "日";
                          } else if(data.endType == 2){
                              return data.endNum + "月";
                          }else if(data.endType == 3){
                              return data.endNum + "年";
                          }else{
                              return until.formatDateTime(data.endTime);
                          }
                      }
                  },
                  {
                  	field:'status', title: 'status',align: 'center',sort:true,templet: function(data){
                          if(data.status == 0){
                              return "<p style='color:green'>正常</p>";
                          }else if(data.status == 1){
                              return "<p style='color:red'>禁用</p>";
                          }
                  	}
                  },
			        {  fixed: 'right', title:'right', align: 'center', width:180, templet: function (data) {
                          var button = "";
                          if (data.status == 1) {//1：正常
                              button += "<button lay-event='changeStatus' class='layui-btn layui-btn-xs'>Open</button>";
                          } else if(data.status == 0){//0：禁用
                              button += "<button lay-event='changeStatus' class='layui-btn layui-btn-warm layui-btn-xs'>forbidden</button>";
                          }
                          button += "<button lay-event='edit' class='layui-btn layui-btn-xs'>Edit</button>";
                          if(data.status == 1){//在优惠券在被禁用情况下，可以删除
                          	 button += "<button lay-event='del' class='layui-btn layui-btn-danger layui-btn-xs'>Del</button>";
                          }
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
				elem : '#couponManageTable',
				 url : contextPath+"/couponManage/rendering",
			  method : 'post',
		cellMinWidth : 150 ,//全局定义常规单元格的最小宽度，layui 2.2.1 新增
				skin : 'line',
			   where : {
					sortName: 'createTime',
					direction: 'desc'
			   },
			    cols : [[
                  { field:'couponId',hide:true,sort:true },
                  { field:'count', title: '优惠券面值', align: 'center',sort:true},
                  { field:'couponRemark',title:'优惠券说明',align: 'center',sort:true},
                  // {
                  // 	field:'deductionType', title: '抵扣类型', align: 'center',templet: function(data){
                  //         if(data.deductionType == 1){
                  //             return "全抵扣";
                  //         } else if(data.deductionType == 2){
                  //             return "分次抵扣";
                  //         }else{
                  //             return data.deductionType != undefined ? data.deductionType : "";
                  //         }
                  //     }
                  //  },
                  // { field:'partDeduction', title: '每次抵扣数', align: 'center'},
                  // {
					{	field:'deductionType', title: '抵扣描述', align: 'center',sort:true,templet: function(data){
                          if(data.deductionType == 1){
                              return "全抵扣";
                          } else if(data.deductionType == 2){
                              return "分次抵扣"+data.partDeduction+"元";
                          }else{
                              return data.deductionType != undefined ? data.deductionType : "";
                          }
                      }
                   },
                  { field:'memberIds',hide:true,sort:true },
                  { field:'memberIdNames', title: '授权会员等级', align: 'center',sort:true },
                  // { field:'endNum', title: '截止数', align: 'center'},
			        // {
			        // 	field:'endType', title: '截止日期类型', align: 'center', templet: function(data){
					// 		 if(data.endType == 1){
					// 			 return "日";
					// 		 } else if(data.endType == 2){
					// 			 return "月";
					// 		 }else if(data.endType == 3){
					// 			 return "年";
					// 		 }else{
					// 			return data.endType != undefined ? data.endType : "";
					// 		 }
			        // 	}
			        //  },
                  // {
                  // 	field:'endTime',title:'截止时间',align: 'center', templet: function(data){
                  //         if(data.endTime == null){
                  //             return "";
                  //         }else{
                  //             return until.formatDateTime(data.endTime);
                  //         }
					//     }
					// },
                  {
                  	field:'createTime',title:'创建时间',align: 'center',sort:"desc",sort:true,templet: function(data){
							   if(data.createTime == null ) {
								  return "";
							   }else{
                                 return until.formatDateTime(data.createTime);
                             }
                  	}
                  },
					{	field:'endTime',title:'截止时间/截止时长',align: 'center',sort:true,templet: function(data){
                          if(data.endType == 1 ) {
                              return data.endNum + "日";
                          } else if(data.endType == 2){
                              return data.endNum + "月";
                          }else if(data.endType == 3){
                              return data.endNum + "年";
                          }else{
                              return until.formatDateTime(data.endTime);
                          }
                      }
                  },
                  {
                  	field:'status', title: '优惠券状态',align: 'center',sort:true,templet: function(data){
                          if(data.status == 0){
                              return "<p style='color:green'>正常</p>";
                          }else if(data.status == 1){
                              return "<p style='color:red'>禁用</p>";
                          }
                  	}
                  },
			        {  fixed: 'right', title:'操作', align: 'center', width:180, templet: function (data) {
                          var button = "";
                          if (data.status == 1) {//1：正常
                              button += "<button lay-event='changeStatus' class='layui-btn layui-btn-xs'>解禁</button>";
                          } else if(data.status == 0){//0：禁用
                              button += "<button lay-event='changeStatus' class='layui-btn layui-btn-warm layui-btn-xs'>禁用</button>";
                          }
                          button += "<button lay-event='edit' class='layui-btn layui-btn-xs'>编辑</button>";
                          if(data.status == 1){//在优惠券在被禁用情况下，可以删除
                          	 button += "<button lay-event='del' class='layui-btn layui-btn-danger layui-btn-xs'>删除</button>";
                          }
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
			$("#addCoupon").empty();
			$("#addCoupon").append('AddCoupon');
			$("#couponTotal").empty();
			$("#couponTotal").append('Total');
			$("#couponType").empty();
			$("#couponType").append('Type');
			$("#endType").empty();
			$("#endType").append('EndType');
			$("#writeEndtime").empty();
			$("#writeEndtime").append('Endtime');
			$("#choseMember").empty();
			$("#choseMember").append('MemberLevel');
		}
	
			/**
			 * 监听行工具事件
			 */
			 table.on('tool(couponManageTable)', function(obj) {
				 var data = obj.data;
				 if(obj.event === 'del'){ //执行删除方法
					 layer.confirm('真的删除么', function(index){
						 $.ajax({
								  url : contextPath+"/couponManage/delete",
							 dataType : 'json',
								 type : 'post',
								async : false,
								 data : {
									couponId : data.couponId
								  },
							 complete : function(XHR, TS){
								  var returnObj = eval('(' + XHR.responseText + ')');
								  if(returnObj.code != 200) {
									 layer.msg(returnObj.msg, {icon: 2, time: 1500});
								  } else {
									 layer.msg("操作成功", {icon: 1, time: 1500});
								  }
								 table.reload('couponManageTable');
							  }
						});
					 });
				 } else if(obj.event === 'edit') {	//执行编辑方法
					 editCoupon("edit",data);
				 }else if(obj.event === 'changeStatus'){
					 console.log(data);
					 var titel = "是否禁用该优惠券？";
					 if(data.status == 1){
						 titel = "是否启用该优惠券？";
						 if(i18n == 'en_US'){
							 titel = "Do you really open it?";
						 }
					 }
					 if(i18n == 'en_US' && data.status == 0){
						 titel = "Do you really forbidden it?";
					 }
                     layer.confirm(titel,
						 function (index) {
                         $.ajax({
                             url: contextPath + "/couponManage/changeCouponStatus",
                             dataType: 'json',
                             type: 'post',
                             data: {
                                 couponId:data.couponId,
                                 couponStatus:data.status
                             },
                             complete: function (XHR, TS) {
                                 var returnObj = eval('(' + XHR.responseText + ')');
                                 if (returnObj.code != 200) {
                                     layer.msg(returnObj.msg, { icon: 2, time: 1500 });
                                 }
                                 layer.close(index);
                                 table.reload('couponManageTable');
                             }
                         });
                     });
				 }
			  });
			
			/**
			  * 添加优惠券按钮点击事件
			  */
			 $("#addCoupon").click(function() {
				 editCoupon("add");
			 });
			 
			//编辑或添加优惠券的具体操作
			 function editCoupon(type,data) {
				  var showTitle = '编辑优惠券';
				var responseUrl = "/couponManage/edit";
					if(type == "add"){
						  showTitle = '添加优惠券';
						responseUrl = "/couponManage/add";
					}
				var showContent = $('#showCouponContent').html();
				$('#showCouponContent').html("");
				
				layer.open({
				 		type : 1,
				 		  id : 'showLayui',
				 	   title : showTitle,
				 	 content : showContent, //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
				 		area : ['500px', '500px'],
				 		 btn : ['确定', '取消'],
				 	btnAlign : 'c',
				 	
				 	 success : function(layero, index) {
				 			layui.use(['form', 'layer','laydate'], function(){
								var form = layui.form;
								var layer = layui.layer;
								var laydate = layui.laydate;

                                $.ajax({
                                    url : contextPath+"/couponManage/queryMemberRule",
                                    dataType : 'json',
                                    type : 'post',
                                    async : false,
                                    data : {
                                    },
                                    complete : function(XHR, TS){
                                        var returnObj = eval('(' + XHR.responseText + ')');
                                        if(returnObj.code != 200) {
                                            $("#memberIdsDiv").html("");
                                            layer.msg(returnObj.msg, {icon: 2, time: 1500});
                                        } else {
                                            $("#memberIdsDiv").html("");
                                            var arr = returnObj.data.content;
                                            var content="";
                                            for(var i in arr){
                                                content+="<input type='checkbox' name='memberIds' value='"+(arr[i].id)+"' lay-skin='primary' title='"+(arr[i].gradeDescription)+"'/>";
                                            }
                                            $("#memberIdsDiv").append(content);
                                        }
                                    }
                                });

								//时间插件
                                var minTime = until.formatDateTime(new Date());
								laydate.render({
									elem: '#endTime',
									type: 'datetime',
									min: minTime
								});
                                $("#endTime").val(until.formatDateTime(new Date()));
                                $("#setEndTime").css("display","");
                                $("#setEndNum").css("display","none");

                                //抵扣类型的选择监听事件
                                form.on('radio(deductionTypeFilter)', function(data){
                                    console.log(data.value); //得到被选中的值
                                    if(data.value == "2"){
                                        $("#setPartDeduction").css("display","");
                                        $("#partDeduction").css("display","");
                                    }else{
                                        $("#setPartDeduction").css("display","none");
                                        $("#partDeduction").css("display","none");
                                    }
                                });
                                //设置截止类型 显示 和 隐藏
                                form.on('radio(endDateTypeFilter)', function(data){
                                    console.log(data.value); //得到被选中的值
                                    if(data.value == "1"){//选中 统一结束
                                        $("#setEndTime").css("display","");
                                        $("#setEndNum").css("display","none");
                                        $("#endNum").val("");
                                        $("#endTime").val(until.formatDateTime(new Date()));
                                    }else if(data.value == "2"){//选中 领券后计时
                                        $("#setEndTime").css("display","none");
                                        $("#setEndNum").css("display","");
                                        $("#endTime").val("");
                                    }
                                });
                                form.on('select(endTypeFilter)', function(data){
                                    console.log(data.value); //得到被选中的值
                                });
				 					
								if(type == "edit"){//编辑框赋值
										 form.val('couponFrom',//给表单里面的字段赋值  通过name属性
											{
											  "count": data.count,
											  "couponRemark": data.couponRemark,
                                              "endType": (function (endType) {
                                                  if(endType == undefined || endType ==""){//领券后计时
                                                      return 1
                                                  }else{
                                                      return data.endType;//统一结束
                                                  }
                                              })(data.endType),
                                              "endNum": data.endNum,
											  "endTime": (function (endTime) {
												  if(endTime == undefined || endTime ==""){//领券后计时
												  	return ""
												  }else{
												  	return until.formatDateTime(data.endTime)//统一结束
												  }
                                              })(data.endTime)
											}
										 );
                                        //------ 抵扣类型 start ------
                                        $("input:radio[name='deductionType'][value='"+(data.deductionType)+"']").attr('checked',true);
                                        if(data.deductionType == "2"){//分次抵扣
                                            $("#setPartDeduction").css("display","");
                                            $("#partDeduction").css("display","");
                                            $("#partDeduction").val(data.partDeduction);
                                        }else{
                                            $("#setPartDeduction").css("display","none");
                                            $("#partDeduction").css("display","none");
                                            $("#partDeduction").val("");
                                        }
                                        //------ 抵扣类型 end ------

                                        //------ 设置截止时间 start ------
									    var endDateTypeValue =(
                                            function (endTime) {
                                                if(endTime == undefined || endTime ==""){//领券后计时
                                                    return 2;
                                                }else{
                                                    return 1;//统一结束
                                                }
                                            }
                                        )(data.endTime);
                                        $("input:radio[name='endDateType'][value='"+(endDateTypeValue)+"']").attr('checked',true);
										if(endDateTypeValue == "1" || endDateTypeValue == 1 ){//选中 统一结束
											$("#setEndTime").css("display","");
											$("#setEndNum").css("display","none");
										}else if(endDateTypeValue == "2" || endDateTypeValue == 2){//选中 领券后计时
											$("#setEndTime").css("display","none");
											$("#setEndNum").css("display","");
										}
                                        //------ 设置截止时间 end ------

									    //------ 抵扣类型 start ------
                                        $("input:checkbox[name='memberIds']:checked").each(function(i){
                                            $("input:checkbox[name='memberIds'][value='"+($(this).val())+"']").attr('checked',false);
                                        });
									    //------ 抵扣类型 end ------

                                        //------ 会员等级 start ------
										$("input:checkbox[name='memberIds']:checked").each(function(i){
                                            $("input:checkbox[name='memberIds'][value='"+($(this).val())+"']").attr('checked',false);
										});
										if(data.memberIds != undefined && data.memberIds != ""){
											var memberIdsArr = data.memberIds.split(",");
											memberIdsArr.forEach(function (elem, index) {
												$("input:checkbox[name='memberIds'][value='"+(elem)+"']").attr('checked',true);
											})
										}
                                        //------ 会员等级 end ------
								  }
								 form.render();  //重新渲染表单
							});
						},
				 			
				 	  yes: function(index, layero){ //确定按钮
						  	    var msgArr = ["优惠券面值不能为空"]; // 提示语集合
							    var clsArr = ["#count"]; // id集合
								if($("#setPartDeduction").css("display") != "none"  && $("#partDeduction").val() == "" ){
                                    msgArr.push("分次抵扣面值不能为空");
                                    clsArr.push("#partDeduction");
								}
								if($("#setEndTime").css("display") != "none" && $("#endTime").val() == ""){
                                    msgArr.push("截止时间不能为空");
                                    clsArr.push("#endTime");
								}
							    if($("#setEndNum").css("display") != "none" && $("#endNum").val() == ""){
								    msgArr.push("截止数不能为空");
								    clsArr.push("#endNum");
							    }
				 				var validateFlag = until.validate(msgArr, clsArr); //非空验证 需要引入工具js文件until
				 				if(validateFlag) {
				 					layer.msg(validateFlag , {anim: 6, time: 2500});  
				 					return ;
				 				};

                                  var memberIdsArr = new Array();
								  $("input:checkbox[name='memberIds']:checked").each(function(i){
									  memberIdsArr[i] = $(this).val();
								  });
								  var memberIds = memberIdsArr.join(",");
								  console.log(memberIds)
								  if(memberIds == ""){
								  	layer.msg("至少授权一个会员等级" , {anim: 6, time: 2500});
								  	return;
                                  }

				 				 //------------  拼接备注 start ------------
						         var formatCreateTime = until.formatDateTime(new Date());
						         var deductionType = $("input:radio[name='deductionType']:checked").val();
						         var count = $("#count").val();
						         var partDeduction = $("#partDeduction").val();
                                 var remarks=(
                                     deductionType == "1" ?
										("结算时一次性抵扣"+(count)+"元")
										:
										("面值"+(count)+"元，每次折扣"+(partDeduction)+"元")
								 );
                                 //------------  拼接备注 end ------------
				 				$.ajax({
				 					    url : contextPath + responseUrl,
				 				   dataType : 'json',
				 					   type : 'post',
				 					  async : false,
				 					   data :
                                       type == "add" ?
										   {
				 						      remark: remarks,
				 						      count: count,
										      status: 0,
									          memberIds: memberIds,
											  endNum: $("#endNum").val(),
											  endType: $("#endType").val(),
											  endTime: $("#endTime").val(),
											  deductionType: $("input:radio[name='deductionType']:checked").val(),
											  partDeduction: partDeduction,
                                              createTime: formatCreateTime
				 					       }:
                                          {
                                          	  couponId:data.couponId,
                                          	  remark: remarks,
                                          	  count: count,
                                              status: data.status,
                                              memberIds: memberIds,
                                              endNum: $("#endNum").val(),
                                              endType: $("#endType").val(),
                                              endTime: $("#endTime").val(),
                                              deductionType: $("input:radio[name='deductionType']:checked").val(),
                                              partDeduction: partDeduction,
                                              createTime: formatCreateTime
                                          },
				 					complete : function(XHR, TS) {
				 						  var returnObj = eval('(' + XHR.responseText + ')');
				 						  if(returnObj.code != 200) {
				 							 layer.msg(returnObj.msg, {icon: 2, time: 1500});
				 						  } else {
				 							 layer.msg("操作成功", {icon: 1, time: 1500});		 							 
				 						  }
				 						 layer.close(index); 
				 						 table.reload('couponManageTable');
				 					  }
				 				});
				 			},
				 			
				 	end: function(){ //只要层被销毁了，end都会执行
				 			$('#showCouponContent').html(showContent);
				 		}
				 			
				});
			} //编辑或添加优惠券的具体操作 end
			 
		
	});
});