define(['jquery', 'layui','until'], function($,LAYUI,until){
	layui.use(['table','form','laydate'], function(){
		var table = layui.table;
		 var form = layui.form;
	  var laydate = layui.laydate;
	  if(i18n == 'en_US'){
		  table.render({
				elem : '#versionRuleTable',
				 url : contextPath+"/version/rendering",
			  method : 'post',
		cellMinWidth : 150 ,//全局定义常规单元格的最小宽度，layui 2.2.1 新增
				skin : 'line',
			   where : {  //默认排序字段
				   sortName: 'createTime',
					direction: 'desc'
			   },
			    cols : [[
			         { field:'id',hide:true },
			         { field:'versionCode', title: 'versionCode', align: 'center'},
			         { field:'type',title:'type',align: 'center',templet: function(data){
			        	 if(data.type == 100){
			        		 return "普通道闸";
			        	 }else{
			        		 return until.formatDateTime(data.effectiveTime);
			        	 }
			           }
			         },
			         { field:'updateType',title:'updateType',align: 'center',templet: function(data){
			        	 if(data.updateType == 1){
			        		 return "立即更新";
			        	 }else{
			        		 return "凌晨更新";
			        	 }
			           }
			         },
			         { field:'createTime', title: 'createTime', align: 'center',templet: function(data){
			        	 if(data.createTime == null){
			        		 return "";
			        	 }else{
			        		 return until.formatDateTime(data.createTime);
			        	 }
			           }
			         },
			         { field:'remark', title: 'remark', align: 'center'},
			         { fixed: 'right', title:'right', align: 'center',width:180, templet: function (data) {
                           var button = "";
                           button += "<button lay-event='edit' class='layui-btn layui-btn-xs'>Edit</button>";
                           button += "<button lay-event='del' class='layui-btn layui-btn-danger layui-btn-xs'>Del</button>";
                           if(data.versionCode == versionCode100){
                          	 button += "<button lay-event='notify' class='layui-btn layui-btn-warm layui-btn-xs'>PromptUpdate</button>";
                           }
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
				elem : '#versionRuleTable',
				 url : contextPath+"/version/rendering",
			  method : 'post',
		cellMinWidth : 150 ,//全局定义常规单元格的最小宽度，layui 2.2.1 新增
				skin : 'line',
			   where : {  //默认排序字段
				   sortName: 'createTime',
					direction: 'desc'
			   },
			    cols : [[
			         { field:'id',hide:true },
			         { field:'versionCode', title: '版本号', align: 'center'},
			         { field:'type',title:'终端类型',align: 'center',templet: function(data){
			        	 if(data.type == 100){
			        		 return "普通道闸";
			        	 }else{
			        		 return until.formatDateTime(data.effectiveTime);
			        	 }
			           }
			         },
			         { field:'updateType',title:'下载更新时间',align: 'center',templet: function(data){
			        	 if(data.updateType == 1){
			        		 return "立即更新";
			        	 }else{
			        		 return "凌晨更新";
			        	 }
			           }
			         },
			         { field:'createTime', title: '添加时间', align: 'center',templet: function(data){
			        	 if(data.createTime == null){
			        		 return "";
			        	 }else{
			        		 return until.formatDateTime(data.createTime);
			        	 }
			           }
			         },
			         { field:'remark', title: '备注', align: 'center'},
			         { fixed: 'right', fixed: 'right', title:'操作', align: 'center',width:180, templet: function (data) {
                           var button = "";
                           button += "<button lay-event='edit' class='layui-btn layui-btn-xs'>编辑</button>";
                           button += "<button lay-event='del' class='layui-btn layui-btn-danger layui-btn-xs'>删除</button>";
                           if(data.versionCode == versionCode100){
                          	 button += "<button lay-event='notify' class='layui-btn layui-btn-warm layui-btn-xs'>提示更新</button>";
                           }
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
			$("#searchVersion").empty();
			$("#searchVersion").append('Search');
			$("#addVersion").empty();
			$("#addVersion").append('AddVersion');
			$("#versionNumber").empty();
			$("#versionNumber").append('versionNumber');
			$("#versionType").empty();
			$("#versionType").append('Type');
			$("#updateTime").empty();
			$("#updateTime").append('UpdateTime');
			$("#versionRemark").empty();
			$("#versionRemark").append('Remark');
			$("#document").empty();
			$("#document").append('Document');
			$("#VersionNum").attr("placeholder","VersionCode");
		}
			
			$("#searchVersion").click(function() {
				  table.reload('versionRuleTable', {
					     where : { VersionNum: $("#VersionNum").val()},
					});
				  page : {curr: 1 }//重新从第 1 页开始
			});
	
	/**
	 * 监听行工具事件
	 */
	 table.on('tool(versionRuleTable)', function(obj) {
	     var data = obj.data;
	     var titelDel = "真的删除吗";
         if(i18n == 'en_US'){
         	 titelDel = "Do you really delete it?";
         }
	     if(obj.event === 'del'){ //执行删除方法
	         layer.confirm(titelDel, function(index){
	        	 $.ajax({
	 					  url : contextPath+"/version/delete",
	 				 dataType : 'json',
	 					 type : 'post',
	 					async : false,
	 					 data : {
	 						id : data.id
	 					  },
	 				 complete : function(XHR, TS){
 						  var returnObj = eval('(' + XHR.responseText + ')');
 						  if(returnObj.code != 200) {
 							 layer.msg(returnObj.msg, {icon: 2, time: 2500});
 						  } else {
 							 layer.msg("操作成功", {icon: 1, time: 2500});		 							 
 						  }
 						 table.reload('versionRuleTable');
 					  }
 				});
	         });
	     } else if(obj.event === 'edit') {	//执行编辑方法
	    	 editVersion("edit",data);
         }else if(obj.event ==='notify'){
        	   layer.confirm('确定提示该类型设备更新？', function(index){
  	        	 $.ajax({
  	 					  url : contextPath+"/mainapkversion/notifyToUpdateByType",
  	 				 dataType : 'json',
  	 					 type : 'post',
  	 					async : false,
  	 					 data : {
  	 						type: data.type
  	 					  },
  	 				 complete : function(XHR, TS){
   						  var returnObj = eval('(' + XHR.responseText + ')');
   						  if(returnObj.code != 200) {
   							 layer.msg(returnObj.msg, {icon: 2, time: 2500});
   						  } else {
   							 layer.msg("操作成功", {icon: 1, time: 2500});		 							 
   						  }
   						 table.reload('mainApkVersionRuleTable');
   					  }
   				});
  	         });
         }
	  });
			
			/**
			  * 添加活动按钮点击事件
			  */
			 $("#addVersion").click(function() {
				 editVersion("add");
			 });
			 
			//编辑或添加停车场的具体操作
			 function editVersion(type,data) {
				var showTitle = '编辑版本';
				var responseUrl;
				var id = 0;
				if(type == "add"){
					showTitle = '添加版本';
					responseUrl = "/version/addVersion";
				} else {
					id = data.id;  //编辑页面获取的活动ID
					responseUrl = "/version/editVersion";
				};
				var showContent = $('#showVersionContent').html();
				$('#showVersionContent').html("");
				
				layer.open({
				 		type : 1,
				 		  id : 'showLayui',
				 	   title : showTitle,
				 	 content : showContent, //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
				 		area : ['600px','600px'],
				 		 btn : ['确定', '取消'],
				 	btnAlign : 'c',
				 	
				 	 success : function(layero, index) {
				 			layui.use(['form', 'layer','laydate'], function(){
				 					 var form = layui.form;
				 					var layer = layui.layer;
				 					var laydate = layui.laydate;

				 					if(type == "edit"){
				 					    //赋值
					 					form.val('versionFrom', { //给表单里面的字段赋值  通过name属性
					 					    "versionCode": data.versionCode,
					 					     "remark": data.remark,
					 						 "type": data.type,
					 						 "updateType":$('input[name="updateType"]:checked').val(),
					 						 "address":data.address,
					 						 "submitAddress":data.address
					 					 });
					 					$("input[name='updateType'][value='"+ data.updateType +"']").prop("checked",true);
				 					}
				 					
				 					form.render();  //重新渲染表单
				 				});
				 			},
				 			
				 	  yes: function(index, layero){ //确定按钮
				 				var msgArr = ["版本号不能为空","终端类型不能为空","文件不能为空"];  //提示语集合
				 				var clsArr = ["#versionCode","#updateType","#submitAddress"];  //id集合
				 				var validateFlag = until.validate(msgArr, clsArr); //非空验证 需要引入工具js文件until
				 				if(validateFlag) {
				 					layer.msg(validateFlag , {anim: 6, time: 2500});  
				 					return ;
				 				};

				 				$.ajax ({
				 					    url : contextPath + responseUrl,
				 				   dataType : 'json',
				 					   type : 'post',
				 					  async : false,
				 					   data : {
					 						    id: id,
									   versionCode: $("#versionCode").val(),
	                                          type: $("#type").val(),
								        updateType: $('input[name="updateType"]:checked').val(),
                                            remark: $("#remark").val(),
                                               md5: $("#md5").val(),
                                           address:$("#submitAddress").val()
				 					  },
				 					complete : function(XHR, TS) {
				 						  var returnObj = eval('(' + XHR.responseText + ')');
				 						  if(returnObj.code != 200) {
				 							 layer.msg(returnObj.msg, {icon: 2, time: 2500});
				 						  } else {
				 							 layer.msg("操作成功", {icon: 1, time: 2500});		 							 
				 						  }
				 						 layer.close(index); 
				 						 table.reload('versionRuleTable');
				 					  }
				 				});
				 			},
				 			
				 	end: function(){ //只要层被销毁了，end都会执行
				 			$('#showVersionContent').html(showContent);
				 		}
				 			
				});
			} //编辑或添加停车场的具体操作 end
			 
		
	});
});