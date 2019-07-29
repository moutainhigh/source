define(['jquery', 'layui','until'], function($,LAYUI,until){
	var table ;
	var form ;
	layui.use(['table','form','laydate'], function(){
			table = layui.table;
			form = layui.form;
			table.render({
				elem : '#deductionModelTable',
				 url : contextPath+"/deductionModel/rendering",
			  method : 'post',
		cellMinWidth : 150 ,//全局定义常规单元格的最小宽度，layui 2.2.1 新增
				skin : 'line',
			   where : { },
			    cols : [[
                    { field:'id',hide:true },//抵扣券id
                    { field:'parkingId',hide:true},//停车id
                    { field:'parkName',title:'停车场名称',align: 'center', hide:roleType==3?true:false},
                    { field:'name',title:'抵扣券名称',align: 'center'},
                    { field:'hourNum',title:'抵扣小时数',align: 'center'},
                    { field:'dueTime', title: '到期时间', align: 'center', templet: function(data){
                            if(data.dueTime == null){
                                return "";
                            }else{
                                return until.formatDateTime(data.dueTime);
                            }
                        }
                     },
                    {
                        field:'status', title: '状态', align: 'center',templet: function(data){
                            if (data.status == 0) {
                                if( data.dueTime != null && data.dueTime != '' && data.dueTime != undefined){
                                    var curTime = new Date();
                                    var dueTime = new Date(Date.parse(data.dueTime));
                                    if( curTime >= dueTime ){
                                        return "<span style='color: red;'>已过期</span>";
                                    }
								}
                                return "<span style='color: green;'>可用</span>";
                            } else if(data.status == 1) {
                                return "<span style='color: red;'>不可用</span>";
                            }else if(data.status == -2) {
                                return "<span style='color: red;'>删除</span>";
                            }else{
                                return data.status != undefined ? data.status : "";
                            }
                        }
                    },
					{
                    	fixed: 'right', title: '操作', align: 'center', width:180, templet: function(data){
                            var button = "";
                            if (data.deductionStatus == 0) {
                                button += "<button lay-event='changeStatus' class='layui-btn layui-btn-xs'>禁用</button>";
                            }
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
	
			/**
			 * 监听行工具事件
			 */
			 table.on('tool(deductionModelTable)', function(obj) {
				 var data = obj.data;
				 if(obj.event === 'del'){ //执行删除方法
					 layer.confirm('真的删除么', function(index){
						 $.ajax({
								  url : contextPath+"/deductionModel/delete",
							 dataType : 'json',
								 type : 'post',
								async : false,
								 data : {
									id : data.id
								  },
							 complete : function(XHR, TS){
								  var returnObj = eval('(' + XHR.responseText + ')');
								  if(returnObj.code != 200) {
									 layer.msg(returnObj.msg, {icon: 2, time: 1500});
								  } else {
									 layer.msg("操作成功", {icon: 1, time: 1500});
								  }
								 table.reload('deductionModelTable');
							  }
						});
					 });
				 } else if(obj.event === 'edit') {	//执行编辑方法
					 editDeductionModel("edit",data);
				 }else if(obj.event === 'changeStatus'){
                     layer.confirm(
                     	// '是否'+(function(status){if(status == 1){return "解禁";}else {return "禁用";}})(data.status)+'该抵扣券',
                     	 '是否让该抵扣券过期',
						 function (index) {
                         $.ajax({
                             url: contextPath + "/deductionModel/changeStatus",
                             dataType: 'json',
                             type: 'post',
                             data: {
                                 deductionId:data.deductionId,
                                 deductionStatus:data.deductionStatus
                             },
                             complete: function (XHR, TS) {
                                 var returnObj = eval('(' + XHR.responseText + ')');
                                 if (returnObj.code != 200) {
                                     layer.msg(returnObj.msg, { icon: 2, time: 1500 });
                                 }
                                 layer.close(index);
                                 table.reload('deductionModelTable');
                             }
                         });
                     });
				 }
			  });
			
			/**
			  * 添加抵扣券按钮点击事件
			  */
			 $("#addDeductionModel").click(function() {
				 if(parkingId == -1){
					layer.msg("停车场管理员账号才能添加抵扣券" , {anim: 6, time: 2500});
					return;
				 }
				 editDeductionModel("add");
			 });
			 
			
			 /**
			  * 状态切换事件
			  */
			 form.on('radio(status)', function(data){
				 if(data.value == 0){
					 isOnlyOne(parkingId);
				 }
			  }); 
			
			 //编辑或添加抵扣券的具体操作
			 function editDeductionModel(type,data) {
				var showContent = $('#showDeductionContent').html();
				$('#showDeductionContent').html("");
				
				var showTitle = '编辑抵扣券';
				var responseUrl = "/deductionModel/edit";
				if(type == "add"){
					showTitle = '添加抵扣券';
					responseUrl = "/deductionModel/add";
				}else{
					$("#generateCountDiv").css("display","none");
				}
				
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
								console.log(data);

								$("#parkName").empty();
			                    $("#parkName").append('<option value="" selected="selected">请选择</option>');
			                    $.ajax({
			                        url: contextPath + "/parking/getAllParking",
			                        dataType: 'json',
			                        type: 'post',
			                        async: false,
			                        data: {},
			                        complete: function (XHR, TS) {
			                            var returnObj = eval('(' + XHR.responseText + ')');
			                            $.each(returnObj, function (i, obj) {
			                                $("#parkName").append('<option value="' + obj.id + '">' + obj.parkName + '</option>');
			                            });
			                            form.render();  //重新渲染表单
			                        }
			                    });
			                    if(roleType == 3){
			                    	$("#deductionParkName").addClass("none");
			                    }
								if((type == "edit") && (data.dueTime)){
									console.log(data.dueTime)
									console.log(until.formatDateTime(data.dueTime))
									laydate.render({
										elem: '#dueTime',
										type: 'datetime',
										value: until.formatDateTime(data.dueTime)
									});
								}else{
								//时间插件
									laydate.render({
										elem: '#dueTime',
										type: 'datetime'
									});
								}

								if(type == "edit"){//编辑框赋值
									var dueTime = '';
									if(data.dueTime){
										dueTime = until.formatDate(data.dueTime);
									}
									form.val('commitFormFilter',//给表单里面的字段赋值  通过name属性
										{
                                               "id" : data.id,
                                               "name" :data.name,
                                               "parkingId" : data.parkingId,
                                               "hourNum" : data.hourNum,
                                               "parkName" : data.parkingId
                                         }
									);
									$("input[name='status'][value="+data.status+"]").attr("checked",true);
								  }
								 form.render();  //重新渲染表单
							});
						},
				 			
				 	  yes: function(index, layero){ //确定按钮
						  	    var msgArr = ["抵扣券名称不能为空","抵扣小时数不能为空"]; // 提示语集合
							    var clsArr = ["#name","#hourNum"]; // id集合
				 				var validateFlag = until.validate(msgArr, clsArr); //非空验证 需要引入工具js文件until
                                
				 				if(validateFlag) {
				 					layer.msg(validateFlag , {anim: 6, time: 2500});  
				 					return ;
				 				};
				 				
				 				var dueTime ='';
				 				if($("#dueTime").val() != ''){
				 					dueTime = until.formatDateTime($("#dueTime").val());
				 				}
				 				var selectPark = $("#parkName").val();
				 				var voDataValue =
                                    (type == 'add') ?
                                        {
				 							name:$("#name").val(),
                                            hourNum:$("#hourNum").val(),
                                            dueTime:dueTime,
                                            status:$('input:radio[name="status"]:checked').val(),
                                            parkingId:selectPark==""?parkingId:selectPark
                                        }:
                                        {
                                        	id:data.id,
                                        	name:$("#name").val(),
                                            hourNum:$("#hourNum").val(),
                                            dueTime:dueTime,
                                            status:$('input:radio[name="status"]:checked').val(),
                                            parkingId:data.parkingId
                                        };

				 				if(voDataValue.hourNum < 0 ){
                                    layer.msg("抵扣小时数不能小于0");
                                    return ;
								}else if(voDataValue.hourNum > 72){
                                    layer.msg("抵扣小时数不能高于72");
                                    return;
								}
								if( (type == 'add')  ){
									if(voDataValue.generateCount < 0){
                                        layer.msg("抵扣券数不能小于0");
                                        return;
									}
								}
				 				$.ajax({
				 					    url : contextPath + responseUrl,
				 				   dataType : 'json',
				 					   type : 'post',
                                    async : false,
                                    data :{
                                        voData:JSON.stringify(voDataValue)
									},
                                    complete : function(XHR, TS) {
				 						  var returnObj = eval('(' + XHR.responseText + ')');
				 						  if(returnObj.code != 200) {
				 							 layer.msg(returnObj.msg, {icon: 2, time: 1500});
				 						  }
				 						 layer.close(index);
				 						 table.reload('deductionModelTable');
				 					  }
                                });
				 			},
				 			
				 	end: function(){ //只要层被销毁了，end都会执行
				 			$('#showDeductionContent').html(showContent);
				 		}
				 			
				});
			} //编辑或添加抵扣券的具体操作 end
			 
			//判断是否只有一个抵扣券模板可用
			function isOnlyOne(parkingId){
				$.ajax({
						url : contextPath+"/deductionModel/isOnlyOne",
				   dataType : 'json',
					   type : 'post',
                async : false,
                data :{
                	parkingId:parkingId
				},
                complete : function(XHR, TS) {
						  var returnObj = eval('(' + XHR.responseText + ')');
						  if(returnObj.code != 200) {
			 				$("input[name='status']").removeAttr("checked");
			 				$("input[name='status'][value='1']").prop("checked",true); 
			 				form.render();  //重新渲染表单
							layer.msg(returnObj.msg, {icon: 2, time: 1500});
						  }
					  }
            });
			}
	});

});