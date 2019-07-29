define(['jquery', 'layui', 'until' , 'ztree'], function ($, LAYUI, until , ztree) {
	var sysTreeObj;
	var parkingTreeObj;
	var cheakNameFlag = true;   //检查角色重名标志  false为重名，true 为不重名
	var oldRoleName;   //保存原来的角色名称
//    $("#RoleName").attr("autocomplete","off").append('<input type="password" style="display:none" /><input type="password">');
	layui.use(['table', 'form'], function () {
		var table = layui.table;
		var form = layui.form;
		if(i18n == 'en_US'){
			table.render({
				elem: '#sysRoleTable',
				url: contextPath + "/sysRole/rendTable",
				method: 'post',
				cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
				skin: 'line',
				where: {
					 sortName : 'createTime',
					 direction : 'desc'
				},
				cols: [[
					{ field: 'roleId', title: '角色ID', sort: true, align: 'center', hide: true},
					{ field: 'roleName', title: 'roleName', sort: true,align: 'center' },
					{ field: 'roleStatus', title: 'roleStatus', sort: true,align: 'center', hide: true, templet: function (data) {
							if (data.roleStatus == 1) {
								return "正常";
							} else {
								return "<span style='color: red;'>删除</span>";
							}
						}
					},
					{ field: 'roleType', title: 'roleType', sort: true,align: 'center', templet: function (data) {
							if (data.roleType == 1) {
								return "系统管理员";
							}else if(data.roleType == 2) {
								return "公司管理员";
							}else {
								return "停车场管理员";
							}
						}
					},
					{ field: 'createTime', title: 'createTime', sort: true,align: 'center', templet: function (data) {
							if(data.createTime == null){
			            		 return "";
			            	 } else {
			            		 return until.formatDateTime(data.createTime);
			            	 }
					    }
					},
					{ field: 'roleRemark', title: 'roleRemark', sort: true,align: 'center', templet: function (data) {
						if (!data.roleRemark) {
							return "无";
						} else {
							return data.roleRemark;
						}
					  }
					},
					{ fixed: 'right', title:'right', toolbar: '#sysRoleTableBarEnglish', align: 'center', width:150}
				]],
				response: {
					countName: 'totalElements',  //规定数据总数的字段名称，默认：count
					 dataName: 'content' //规定数据列表的字段名称，默认：data
				},
				page: true
			});
		}else{
			table.render({
				elem: '#sysRoleTable',
				url: contextPath + "/sysRole/rendTable",
				method: 'post',
				cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
				skin: 'line',
				where: {
					 sortName : 'createTime',
					 direction : 'desc'
				},
				cols: [[
					{ field: 'roleId', title: '角色ID', sort: true, align: 'center', hide: true},
					{ field: 'roleName', title: '角色名称', sort: true,align: 'center' },
					{ field: 'roleStatus', title: '状态', sort: true,align: 'center', hide: true, templet: function (data) {
							if (data.roleStatus == 1) {
								return "正常";
							} else {
								return "<span style='color: red;'>删除</span>";
							}
						}
					},
					{ field: 'roleType', title: '角色类型', sort: true,align: 'center', templet: function (data) {
							if (data.roleType == 1) {
								return "系统管理员";
							}else if(data.roleType == 2) {
								return "公司管理员";
							}else {
								return "停车场管理员";
							}
						}
					},
					{ field: 'createTime', title: '创建时间', sort: true,align: 'center', templet: function (data) {
							if(data.createTime == null){
			            		 return "";
			            	 } else {
			            		 return until.formatDateTime(data.createTime);
			            	 }
					    }
					},
					{ field: 'roleRemark', title: '备注', sort: true,align: 'center', templet: function (data) {
						if (!data.roleRemark) {
							return "无";
						} else {
							return data.roleRemark;
						}
					  }
					},
					{ fixed: 'right', title:'操作', toolbar: '#sysRoleTableBar', align: 'center', width:150}
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
			$("#roleName").empty();
			$("#roleName").append('RoleName');
			$("#searchRoleName").empty();
			$("#searchRoleName").append('Search');
			$("#addSysRole").empty();
			$("#addSysRole").append('AddSysRole');
			$("#writeName").empty();
			$("#writeName").append('Name');
			$("#roleRemark").empty();
			$("#roleRemark").append('roleRemark');
			$("#choseRoleType").empty();
			$("#choseRoleType").append('Type');
			$("#addMenu").empty();
			$("#addMenu").append('Menu');
			$("#editMenu").empty();
			$("#editMenu").append('Menu');
			$("#RoleName").attr("placeholder","RoleName");
		}
		
		/**
		 * 搜索方法
		 */
		$("#searchRoleName").click(function() {
			table.reload('sysRoleTable', {
				  where : { //设定异步数据接口的额外参数，任意设
					searchRole: $("#RoleName").val(),
				  },
				   page : {curr: 1 }  //重新从第 1 页开始
			});
		 });
		
		//添加角色
		$(document).off("click", "#addSysRole").on("click", "#addSysRole", function(){
			editSysRole("add");
		});
		
		//失去焦点判断重名
		$(document).off("blur", "#roleNameInp").on("blur", "#roleNameInp", function(){
			var thisName = $(this).val();
			if((thisName != oldRoleName) && (thisName)){
				$.ajax({
					url: contextPath + "/sysRole/checkRoleName",
					dataType: "json",
					type: "post",
					data: {
						roleName: thisName
					},
					complete: function(XHR, TS){
						var returnObj = eval('('+ XHR.responseText +')');
						if(returnObj.code != 200){
							cheakNameFlag = false;
							$("#roleNameInp").focus();
							layer.msg(returnObj.msg, {time: 1500});
						}else{
							cheakNameFlag = true;	 							 
						}
					}
				});
			}
		});
		
		 //监听行工具事件
		 table.on('tool(sysRoleTable)', function(obj){
		     var data = obj.data;
		     var data = obj.data;
	            var titelDel = "真的删除吗";
	            if(i18n == 'en_US'){
	            	 titelDel = "Do you really delete it?";
	            }
		     if(obj.event === 'del'){
		         layer.confirm(titelDel, function(index){
		        	 $.ajax({
		        		url: contextPath + "/sysRole/delRole",
		        		dataType: 'json',
		        		type: 'post',
		        		async: false,
		        		data: {
		        			roleId: data.roleId
		        		},
		        		complete: function(XHR, TS){
		        			var returnObj = eval('('+ XHR.responseText +')');
	 						if(returnObj.code != 200){
	 							 layer.msg(returnObj.msg, {icon: 2, time: 1500});
	 						}else{
	 							layer.msg("操作成功", {icon: 1, time: 1500});		 							 
	 						}
	 						layer.close(index); 
	 						table.reload('sysRoleTable');
		        		}
		        		
		        	 });
		             
		         });
		       } else if(obj.event === 'edit'){
		    	 //执行编辑方法
		    	 editSysRole("edit", data);
		     }
		  });
		 
		 /**
		  * 添加/编辑角色
		  */
		 function editSysRole(type, obj){
			 var showTitle = "编辑角色";
			 var responseUrl = "/sysRole/addRole";
			 if(type == "add"){
				 showTitle = "添加角色";
			 }else{
				 responseUrl = "/sysRole/editRole";
			 }
			 var saveNodes;  //菜单树
			 var saveRoleId = 0;  //id
			 var saveRemark;  //备注
			 var saveRoleName; //名称
			 var saveRoleType; //类型
			 var saveMenuIdList = new Array();
			 var showContent = $('#showSysRoleContent').html();
	 		 $('#showSysRoleContent').html("");
	 		 layer.open({
	 			 type: 1,
	 			 id: 'showLayui',
	 			 title: showTitle,
	 			 content: showContent, //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
	 			 area: ['600px','600px'],
	 			 btn: ['确定', '取消'],
	 			 btnAlign: 'c',
	 			 success: function(layero, index){  //打开层之后可执行的回调
	 				 var sysMenuData = JSON.parse(JSON.stringify(initSysMenuData));
	 				 var parkingMenuData = JSON.parse(JSON.stringify(initParkingMenuData));
	 				 var sysSetting = { //系统菜单树设置
 						 check: {
 							 enable: true,
 							 chkboxType: { "Y" : "ps", "N" : "ps" }
 						 },
 						 data: {
 							 simpleData: {
 								 enable: true
 							 }
 						 },
 						 callback: {
 							 onCheck: sysTreeOnCheck,
 							 onClick: sysTreeOnClick
 						 }
 					 };
	 				 
					 var parkingSetting = { // 停车场菜单树设置
						check : {
							enable : true,
							chkboxType : {
								"Y" : "ps",
								"N" : "ps"
							}
						},
						data : {
							simpleData : {
								enable : true
							}
						},
						callback : {
							onCheck : parkingTreeOnCheck,
							onClick : parkingTreeOnClick
						}
					};
	 				
	 				//点击Check节点打开菜单
	 				function sysTreeOnCheck(event, treeId, treeNode) {
	 					sysTreeObj.expandNode(treeNode, true, true, true);
	 				};	 				
	 				function parkingTreeOnCheck(event, treeId, treeNode) {
	 					parkingTreeObj.expandNode(treeNode, true, true, true);
	 				};
	 				
	 				//点击Click节点打开菜单
	 				function sysTreeOnClick(event, treeId, treeNode) {
	 					sysTreeObj.expandNode(treeNode);
	 				};	 				
	 				function parkingTreeOnClick(event, treeId, treeNode) {
	 					parkingTreeObj.expandNode(treeNode);
	 				};
	 				
	 				if(type == "edit"){
	 					form.val('sysRoleFrom', { //给表单里面的字段赋值  通过name属性
	 					    "roleNameInp": obj.roleName // "name": "value"
	 					    ,"remarkInp": obj.roleRemark
	 					});
	 					saveRoleId = obj.roleId;
	 					oldRoleName = obj.roleName;
//	 					$("input[name='roleType'][value='"+ obj.roleType +"']").prop("checked",true); 
	 					$("input[name='roleType'][value='"+ obj.roleType +"']").prop("checked",true); 
	 					//将菜单选中并打开
		 				$.ajax({
		 					url: contextPath + "/sysRole/getMenuIdByRoleId",
		 					dataType: 'json',
		 					type: 'post',
		 					data: {roleId: obj.roleId},
		 					async: false,
		 					complete: function(XHR, TS){
		 						var returnObj = eval('(' + XHR.responseText + ')');
		 						if(obj.roleType == 1){
		 							$.each(sysMenuData, function(i,dataObj){		 							
			 							$.each(returnObj, function(j, checkObj){
			 								if(dataObj.id == checkObj.menuId){
			 									sysMenuData[i].open = true;
			 									sysMenuData[i].checked = true;
			 									return false;
			 								}
			 							});
			 							
			 						});
		 						}else{
		 							$.each(parkingMenuData, function(i,dataObj){		 							
			 							$.each(returnObj, function(j, checkObj){
			 								if(dataObj.id == checkObj.menuId){
			 									parkingMenuData[i].open = true;
			 									parkingMenuData[i].checked = true;
			 									return false;
			 								}
			 							});
		 							});
		 						} 								 							 						
		 					}
		 				});
	 				}
	 				
	 				sysTreeObj = $.fn.zTree.init($("#sysMenutree"), sysSetting, sysMenuData);
	 				parkingTreeObj = $.fn.zTree.init($("#parkingMenutree"), parkingSetting, parkingMenuData);
	 				if($('input[name="roleType"]:checked').val() == 1){
						$("#showSysTreeBox").removeClass("none");
						$("#showParkingTreeBox").addClass("none");
					}else{
						$("#showSysTreeBox").addClass("none");
						$("#showParkingTreeBox").removeClass("none");
					}
	 				 layui.use(['form', 'layer'], function(){
	 					 var form = layui.form;
	 					 var layer = layui.layer;
	 					//监听开关
	 					form.on('radio(roleIdentity)', function(data){
	 						if(data.value == 1){
	 							$("#showSysTreeBox").removeClass("none");
	 							$("#showParkingTreeBox").addClass("none");
	 						}else{
	 							$("#showSysTreeBox").addClass("none");
	 							$("#showParkingTreeBox").removeClass("none");
	 						}
	 					});
	 					
	 					form.render();  //重新渲染表单
	 				});
	 			},
	 			yes: function(index, layero){ //确定按钮
	 				var sysNodes = sysTreeObj.getCheckedNodes(true);
	 				var parkingNodes = parkingTreeObj.getCheckedNodes(true);
	 				saveRoleType = $('input[name="roleType"]:checked').val(); //角色类型
	 				if(saveRoleType == 1){ 
	 					saveNodes = sysNodes;
	 				}else{
	 					saveNodes = parkingNodes;
	 				}
	 				saveRemark = $("#remarkInp").val();
	 				saveRoleName = $("#roleNameInp").val();
	 				var msgArr = ["角色名称不能为空"];  //提示语集合
	 				var clsArr = ["#roleNameInp"];  //id集合
	 				var validateFlag = until.validate(msgArr, clsArr); //非空验证 需要引入工具js文件until
	 				if(validateFlag){
	 					layer.msg(validateFlag , {anim: 6, time: 1500});  
	 					return ;
	 				};
	 				if(saveNodes.length == 0){
	 					layer.msg("请至少选择一项菜单" , {anim: 6, time: 1500});  
	 					return ;
	 				}
	 				if(cheakNameFlag == false){
	 					layer.msg("角色名称不能重复" , {anim: 6, time: 1500});  
	 					return ;
	 				}
	 				$.each(saveNodes,function(i, nodeObj){
	 					saveMenuIdList.push(nodeObj.id);
	 				});
	 				$.ajax({
	 					url: contextPath + responseUrl,
	 					dataType: 'json',
	 					type: 'post',
	 					data: {
	 						saveRoleId: saveRoleId,
	 						saveRoleName: saveRoleName,
	 						saveRemark: saveRemark,
	 						saveRoleType: saveRoleType,
	 						saveMenuIdList: saveMenuIdList.toString()
	 					},
	 					complete: function(XHR, TS){
	 						var returnObj = eval('('+ XHR.responseText +')');
	 						if(returnObj.code != 200){
	 							 layer.msg(returnObj.msg, {icon: 2, time: 1500});
	 						  }else{
	 							 layer.msg("操作成功", {icon: 1, time: 1500});		 							 
	 						  }
	 						 layer.close(index); 
	 						 table.reload('sysRoleTable');
	 					}
	 				});
	 			    
	 			},
	 			end: function(){ //只要层被销毁了，end都会执行
	 				$('#showSysRoleContent').html(showContent);
	 			}
	 			
	 		});
		 }
	});
});