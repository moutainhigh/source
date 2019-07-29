define(['jquery', 'layui', 'until'], function ($, LAYUI, until) {
	layui.use(['table', 'form'], function () {
		var table = layui.table;
		var form = layui.form;
		var cheakNameFlag = true;   //检查角色重名标志  false为重名，true 为不重名
		var oldUserName;   //保存原来的角色名称
		form.render();
		if(i18n == 'en_US'){
			table.render({
				elem: '#ParkingUserTable',
				url: contextPath + "/parkingUser/rendTable",
				method: 'post',
				cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
				skin: 'line',
				where: {
					sortName: 'nickname',
					direction: 'desc'
				},
				cols: [[
					{ field: 'realName', title: 'realName', sort: true},
					{ field: 'parkName', title: 'parkName', sort: true},
					{ field: 'userName', title: 'userName', sort: true},
					{ field: 'type', title: 'type', sort: true, templet: function(data){
						if(data.type == 1){
							return "商户";
						}else if(data.type == 2){
							return "保安";
						}
					}},
					{ field: 'status', title: 'status', sort: true, templet: function (data){
						if(data.status == 1){
							return "正常";
						}else{
							return "删除";
						}
					} },
					{ field: 'createTime', title: 'createTime', sort: true, align: 'center', templet: function (data){
						if(data.createTime == null){
							return "";
						}else{
							return until.formatDateTime(data.createTime);
						}
					} },
					
					{ fixed: 'right', title:'right', toolbar: '#UserTableBar', align: 'center', width:200}
				
				]],
				response: {
					countName: 'totalElements',  //规定数据总数的字段名称，默认：count
					dataName: 'content' //规定数据列表的字段名称，默认：data
				},
				page: true
			});
		}else{
			table.render({
				elem: '#ParkingUserTable',
				url: contextPath + "/parkingUser/rendTable",
				method: 'post',
				cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
				skin: 'line',
				where: {
					sortName: 'nickname',
					direction: 'desc'
				},
				cols: [[
					{ field: 'realName', title: '真实姓名', sort: true},
					{ field: 'parkName', title: '所属停车场', sort: true},
					{ field: 'userName', title: '登录账户', sort: true},
					{ field: 'type', title: '用户类型', sort: true, templet: function(data){
						if(data.type == 1){
							return "商户";
						}else if(data.type == 2){
							return "保安";
						}
					}},
					{ field: 'status', title: '状态', sort: true, templet: function (data){
						if(data.status == 1){
							return "正常";
						}else{
							return "删除";
						}
					} },
					{ field: 'createTime', title: '创建时间', sort: true, align: 'center', templet: function (data){
						if(data.createTime == null){
							return "";
						}else{
							return until.formatDateTime(data.createTime);
						}
					} },
					
					{ fixed: 'right', title:'操作', toolbar: '#UserTableBar', align: 'center', width:200}
				
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
			$("#paekingUserName").empty();
			$("#paekingUserName").append('UserName');
			$("#searchUserAccount").empty();
			$("#searchUserAccount").append('Search');
			$("#addUserAccount").empty();
			$("#addUserAccount").append('AddUserAccount');
			$("#userAccount").attr("placeholder","UserName");
		}
		
		/**
		 * 搜索方法
		 */
		$("#searchUserAccount").click(function() {
			table.reload('ParkingUserTable', {
				  where : { //设定异步数据接口的额外参数，任意设
					  userName: $("#userAccount").val(),
				  },
				   page : {curr: 1 }  //重新从第 1 页开始
			});
		 });
		
		//失去焦点判断重名
		$(document).off("blur", "#userName").on("blur", "#userName", function(){
			var thisName = $(this).val();
			if((thisName != oldUserName) && (thisName)){
				$.ajax({
					url: contextPath + "/parkingUser/checkUserName",
					dataType: "json",
					type: "post",
					data: {
						userName: thisName
					},
					complete: function(XHR, TS){
						var returnObj = eval('('+ XHR.responseText +')');
						if(returnObj.code != 200){
							cheakNameFlag = false;
							$("#userName").focus();
							layer.msg(returnObj.msg, {time: 1500});
						}else{
							cheakNameFlag = true;	 							 
						}
					}
				});
			}
		});
		
		
		/**
		 * 添加用户点击事件
		 */
		$("#addUserAccount").click(function () {
			editUser("add");
		});
		
		//编辑或添加用户的具体操作
		function editUser(type, data) {
			var showTitle = '编辑用户信息';
			var responseUrl = "/parkingUser/addUser";
			var userId = 0;
			if (type == "add") {
				showTitle = '添加用户';
			}else{
				userId = data.id;
			}
			var showContent = $('#showAccountContent').html();
			$('#showAccountContent').html("");

			layer.open({
				type: 1,
				id: 'showLayui',
				title: showTitle,
				content: showContent, //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
				area: ['500px','500px'],
				btn: ['确定', '取消'],
				btnAlign: 'c',
				success: function (layero, index) {

					if(parkingIdStr != null){
						$("#parkingUSerChoseParking").addClass("none");
						$("#parkingUSerGetAllParking").val(parkingIdStr);
					}
                    /**
					 * 默认选中 管理员类型相应选项
                     */
					$.ajax({
                        url: contextPath + "/parking/getAllParking",
                        dataType: 'json',
                        type: 'post',
                        async: false,
                        data: {},
                        complete: function (XHR, TS) {
                            var returnObj = eval('(' + XHR.responseText + ')');
                            $("#parkingUSerGetAllParking").empty();
                            $("#parkingUSerGetAllParking").append('<option value="" selected="selected">请选择</option>');
                            $.each(returnObj, function (i, obj) {
                                $("#parkingUSerGetAllParking").append('<option value="' + obj.id + '">' + obj.parkName + '</option>');
                            });

                        }
                    });
					
					if(data){
						oldUserName = data.userName;
						$("#parkingUSerGetAllParking").val(data.parkingId);
						$("#userName").val(data.userName);
						$("#AccountRealName").val(data.realName);
						$("#ParkingUSerPW").addClass("none");
						$("input[name='accountUserType'][value='"+ data.type +"']").prop("checked",true); 
					}

					form.render();
				}, //success结束

				yes: function (index, layero) {debugger //确定按钮
					var parkingId;
					var msgArr = [ "登录账号不能为空","真实姓名不能为空"];  //提示语集合
					var clsArr = [ "#userName","#AccountRealName"];  //id集合
					var validateFlag = until.validate(msgArr, clsArr); //非空验证 需要引入工具js文件until
					if (validateFlag) {
						layer.msg(validateFlag, { anim: 6, time: 1500 });
						return;
					};
					if(type == "add"){
						parkingId = $("#parkingUSerGetAllParking").val();
					}else{
						parkingId = data.parkingId;
					}
					if(parkingIdStr != null){
						parkingId = parkingIdStr;
					}
                    var contactPhone = $("#userName").val();
                    if(contactPhone != null && contactPhone.length > 0 && !until.isPoneAvailable(contactPhone)){
                        layer.msg("请输入正确的联系电话" , {anim: 6, time: 2500});
                        return ;
                    }

					var userName = $("#userName").val();
					var saveUserType = $('input[name="AccountUserType"]:checked').val(); //管理员类型
					
					if (!(/^[0-9-]{11}$/.test(userName))) {
						layer.msg("请填写11位的登录账号", { anim: 6, time: 1500 });
						return false;
					}
					
					
                    if(cheakNameFlag == false){
	 					layer.msg("登录账号不能重复" , {anim: 6, time: 1500});  
	 					return ;
                    }

					$.ajax({
						url: contextPath + responseUrl,
						dataType: 'json',
						type: 'post',
						async: false,
						data: {
							Id: userId,
							userName: $("#userName").val(),
							realName: $("#AccountRealName").val(),
							type: $('input[name="accountUserType"]:checked').val(), //用户类型
							parkingId:parkingId
						},
						complete: function (XHR, TS) {
							var returnObj = eval('(' + XHR.responseText + ')');
							if (returnObj.code != 200) {
								layer.msg(returnObj.msg, { icon: 2, time: 1500 });
							} else {
								layer.msg("操作成功", { icon: 1, time: 1500 });
							}
							layer.close(index);
							table.reload('ParkingUserTable');
						}
					});
				},

				end: function () { //只要层被销毁了，end都会执行
					$('#showAccountContent').html(showContent);
				}

			});
		} //编辑或添加停车场的具体操作 end
		
		 //监听行工具事件
		 table.on('tool(ShowUserAccountTable)', function(obj){
		     var data = obj.data;
		     if(obj.event === 'del'){
		         layer.confirm('确定删除该用户吗', function(index){
		        	 $.ajax({
		        		url: contextPath + "/parkingUser/delUser",
		        		dataType: 'json',
		        		type: 'post',
		        		async: false,
		        		data: {
		        			userId: data.id
		        		},
		        		complete: function(XHR, TS){
		        			var returnObj = eval('('+ XHR.responseText +')');
	 						if(returnObj.code != 200){
	 							 layer.msg(returnObj.msg, {icon: 2, time: 1500});
	 						}else{
	 							layer.msg("操作成功", {icon: 1, time: 1500});		 							 
	 						}
	 						layer.close(index); 
	 						table.reload('ParkingUserTable');
		        		}
		        		
		        	 });
		             
		         });
		       }else if(obj.event === 'reset'){
		    	   //执行重置方法
			         layer.confirm('确定重置该用户密码吗', function(index){
			        	 $.ajax({
			        		url: contextPath + "/parkingUser/resetUser",
			        		dataType: 'json',
			        		type: 'post',
			        		async: false,
			        		data: {
			        			userId: data.id
			        		},
			        		complete: function(XHR, TS){
			        			var returnObj = eval('('+ XHR.responseText +')');
		 						if(returnObj.code != 200){
		 							 layer.msg(returnObj.msg, {icon: 2, time: 1500});
		 						}else{
		 							layer.msg("操作成功", {icon: 1, time: 1500});		 							 
		 						}
		 						layer.close(index); 
		 						table.reload('ParkingUserTable');
			        		}
			        		
			        	 });
			             
			         });
		    	   
		    	   
		       }else if(obj.event === 'edit'){
		    	 //执行编辑方法
		    	 editUser("edit", data);
		     }
		  });
	});
	
	
});