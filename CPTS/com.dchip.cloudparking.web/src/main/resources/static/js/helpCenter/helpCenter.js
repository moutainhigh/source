define(['jquery', 'layui', 'until'], function($, LAY, until) {
	var layEditFlag;
	layui.use(['table','form','layedit'], function() {
		var table = layui.table;
		var form = layui.form;
		var layedit = layui.layedit,
        $ = layui.jquery;
		if(i18n == 'en_US'){
			table.render({ 
				 elem : '#HelpCenterTable',
				  url : contextPath+"/helpCenter/rendering",
			   method : 'post',
		 cellMinWidth : 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
				 skin : 'line',
				where : { //默认排序字段
						sortName: 'helpId',
					   direction: 'desc'
					},
			     cols : [[
			    	 { field: 'power', title: 'power', sort: true, align: 'center'},
			    	 { field: 'title', title: 'title', sort: true, align: 'center'},
				     { field: 'content', title: 'content', align: 'center'},
				     { field: 'type', title: 'type', sort: true, align: 'center',templet: function(data) {
				    	 if(data.type == 1){
				    		 return "积分";
				    	 }else{
				    		 return "使用帮助";
				    	 }
				       }
				     },
				     { field: 'uploadTime', title: 'uploadTime',  sort: true, align: 'center',templet: function(data) {
				    	 if(data.uploadTime == null){
				    		 return "";
				    	 }else {
				    		 return until.formatDateTime(data.uploadTime);
				    	 }
				       }
				     },
				     { fixed: 'right', title:'right', toolbar: '#HelpCenterBarEnglish', align: 'center', width:150}
			     ]],
			 response : {
			        countName: 'totalElements', //规定数据总数的字段名称，默认：count
			         dataName: 'content' //规定数据列表的字段名称，默认：data
			     }, 
				 page : true
			});
		}else{
			table.render({ 
				 elem : '#HelpCenterTable',
				  url : contextPath+"/helpCenter/rendering",
			   method : 'post',
		 cellMinWidth : 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
				 skin : 'line',
				where : { //默认排序字段
						sortName: 'helpId',
					   direction: 'desc'
					},
			     cols : [[
			    	 { field: 'power', title: '权重', sort: true, align: 'center'},
			    	 { field: 'title', title: '标题', sort: true, align: 'center'},
				     { field: 'content', title: '内容', align: 'center'},
				     { field: 'type', title: '类型', sort: true, align: 'center',templet: function(data) {
				    	 if(data.type == 1){
				    		 return "积分";
				    	 }else{
				    		 return "使用帮助";
				    	 }
				       }
				     },
				     { field: 'uploadTime', title: '更新时间',  sort: true, align: 'center',templet: function(data) {
				    	 if(data.uploadTime == null){
				    		 return "";
				    	 }else {
				    		 return until.formatDateTime(data.uploadTime);
				    	 }
				       }
				     },
				     { fixed: 'right', title:'操作', toolbar: '#HelpCenterBar', align: 'center', width:150}
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
			$("#addHelpCenter").empty();
			$("#addHelpCenter").append('AddHelpCenter');
			$("#helpType").empty();
			$("#helpType").append('Type');
			$("#helpeWeight").empty();
			$("#helpeWeight").append('Weight');
			$("#helpTitle").empty();
			$("#helpTitle").append('helpTitle');
			$("#helpDetail").empty();
			$("#helpDetail").append('helpDetail');
		}
		
		
		/**
		  * 添加点击事件
		  */
		 $("#addHelpCenter").click(function() {
			 editHelpCenter("add");
		 });
		 
		 /**
		  * 监听行工具事件
		  */
		 table.on('tool(HelpCenterTable)', function(obj) {
		     var data = obj.data;
		     var titelDel = "真的删除吗";
	            if(i18n == 'en_US'){
	            	 titelDel = "Do you really delete it?";
	            }
		     if(obj.event === 'del') { //执行删除方法
		         layer.confirm(titelDel, function(index) {
		        	 $.ajax({
	 					  url : contextPath+"/helpCenter/delete",
	 				 dataType : 'json',
	 					 type : 'post',
	 					async : false,
	 					 data : {
	 						helpId: data.helpId,
	 					  },
	 				 complete : function(XHR, TS) {
	 						  var returnObj = eval('(' + XHR.responseText + ')');
	 						  if(returnObj.code != 200) {
	 							 layer.msg(returnObj.msg, {icon: 2, time: 1500});
	 						  } else {
	 							 layer.msg("操作成功", {icon: 1, time: 1500});		 							 
	 						  }
	 						 table.reload('HelpCenterTable');
	 					  }
	 				});
		         });
		     }else if(obj.event === 'edit'){
		    	 editHelpCenter('edit',data);
		     }else if(obj.event === 'add'){
		    	 editHelpCenter('add');
		     }
		 });
		 
		 function editHelpCenter(type,data) {
			 var showTitle = '编辑';
			 var responseUrl = "/helpCenter/edit";
			 var helpId = 0;
			 if(type == "add"){
				  showTitle = '添加'
				responseUrl = "/helpCenter/add";
			 } else {
				 helpId = data.helpId;
			 };	
			 var showContent = $('#showHelpCenter').html();
			 $('#showHelpCenter').html("");
			 
			 layer.open({
			 		type : 1,
			 		  id : 'showLayui',
			 	   title : showTitle,
			 	 content : showContent, //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
			 		area : ['800px','650px'],
			 		 btn : ['确定', '取消'],
			 	btnAlign : 'c',
			 	
			 	success : function(layero, index) {
			 		layui.use(['form', 'layer'], function(){
			 			var form = layui.form;
				 		var layer = layui.layer;
				 		if(type == "add"){
	 						
					 	} else {
					 		//赋值
					 		$("input[name='type'][value="+data.type+"]").attr("checked",true);
						 	form.val('helpCenterFrom', { //给表单里面的字段赋值  通过name属性
						 		"power": data.power,
						 		"title": data.title,
						 		"content": data.content,
						 		"uploadTime": data.uploadTime
						 	});
					 	}
				 		form.render();  //重新渲染表单
			 		});
			 		
			 		//先赋值再构建编辑器（异步问题）
			 		layui.use(['layer','layedit','laypage','element'],function() {
			 			var layer = layui.layer,
			 			layedit = layui.layedit;
			 			//构建一个默认的编辑器
			 			layEditFlag = layedit.build('content');
			 		});
			 	},//success结束
			 	
			 	
			 	yes: function (index, layero) {//debugger //确定按钮
					var msgArr = ["标题不能为空"];  //提示语集合
					var clsArr = ["#title"];  //id集合
					var validateFlag = until.validate(msgArr, clsArr); //非空验证 需要引入工具js文件until
					if (validateFlag) {
						layer.msg(validateFlag, { anim: 6, time: 3500 });
						return;
					};
					var content = layedit.getContent(layEditFlag);
					if(content != null){
						content=content.trim();
					}
					if(content === ''|| content === '<p><br></p>'){
						return layer.msg("正文不能为空", { anim: 6, time: 3500 });
					}
					$.ajax({
						url: contextPath + responseUrl,
						dataType: 'json',
						type: 'post',
						async: false,
						data: {
							helpId: helpId,
							power: $('#power').val(),
							title: $('#title').val(),
						    content: layedit.getContent(layEditFlag),
							type: $("input[name='type']:checked").val()
						},
						complete: function (XHR, TS) {
							var returnObj = eval('(' + XHR.responseText + ')');
							if (returnObj.code != 200) {
								layer.msg(returnObj.msg, { icon: 2, time: 1500 });
							} else {
								layer.msg("操作成功", { icon: 1, time: 1500 });
							}
							layer.close(index);
							table.reload('HelpCenterTable');
						}
					});
				},//yes结束

				end: function () { //只要层被销毁了，end都会执行
					$('#showHelpCenter').html(showContent);
				}//end结束
				
			 });
		 };
		     
	});
	
});