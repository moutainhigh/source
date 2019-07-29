define(['jquery', 'layui','until'], function($,LAYUI,until){
	layui.use(['table','form','laydate'], function(){
		var table = layui.table;
		 var form = layui.form;
	  var laydate = layui.laydate;
	  if(i18n == 'en_US'){
		  table.render({
				elem : '#mainApkVersionRuleTable',
				 url : contextPath+"/mainapkversion/rendering",
			  method : 'post',
		cellMinWidth : 150 ,//全局定义常规单元格的最小宽度，layui 2.2.1 新增
				skin : 'line',
			   where : {  //默认排序字段
				   sortName: 'id',
					direction: 'desc'
			   },
			    cols : [[
			         { field:'id',hide:true },
			         { field:'mac', title: 'mainMac', align: 'center'},
			         { field:'currentVersion', title: 'currentVersion', align: 'center'},
			         { field:'currentVersion', title: 'LatestVersion', align: 'center',templet: function(data){
			        	 if(data.type == 100){
                           return versionCode;
                       }else{
                           return "";
                       }
			           }
			         },
			         { field:'installFailReasion', title: 'installFailReasion', align: 'center'},
			         { field:'createTime', title: 'updateTime', align: 'center',templet: function(data){
			        	 if(data.createTime == null){
                           return "";
                       }else{
                           return until.formatDateTime(data.createTime);
                       }
			           }
			         },
			         { fixed: 'right', title:'right', align: 'center', width:150, templet: function (data) {
                           var button = "";
                           button += "<button lay-event='del' class='layui-btn layui-btn-danger layui-btn-xs'>Del</button>";
                           if(data.currentVersion != versionCode){
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
				elem : '#mainApkVersionRuleTable',
				 url : contextPath+"/mainapkversion/rendering",
			  method : 'post',
		cellMinWidth : 150 ,//全局定义常规单元格的最小宽度，layui 2.2.1 新增
				skin : 'line',
			   where : {  //默认排序字段
				   sortName: 'id',
					direction: 'desc'
			   },
			    cols : [[
			         { field:'id',hide:true },
			         { field:'mac', title: '主控板Mac', align: 'center'},
			         { field:'currentVersion', title: '当前版本', align: 'center'},
			         { field:'currentVersion', title: '最新版本', align: 'center',templet: function(data){
			        	 if(data.type == 100){
                           return versionCode;
                       }else{
                           return "";
                       }
			           }
			         },
			         { field:'installFailReasion', title: '安装失败原因', align: 'center'},
			         { field:'createTime', title: '更新时间', align: 'center',templet: function(data){
			        	 if(data.createTime == null){
                           return "";
                       }else{
                           return until.formatDateTime(data.createTime);
                       }
			           }
			         },
			         { fixed: 'right', title:'操作', align: 'center', width:150, templet: function (data) {
                           var button = "";
                           button += "<button lay-event='del' class='layui-btn layui-btn-danger layui-btn-xs'>删除</button>";
                           if(data.currentVersion != versionCode){
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
			$("#mainMACName").empty();
			$("#mainMACName").append('MacName');
			$("#search").empty();
			$("#search").append('Search');
			$("#addCompany").empty();
			$("#addCompany").append('AddCompany');
			$("#searchMAC").attr("placeholder","MAC");
		}
	
	/**
	 * 监听行工具事件
	 */
	 table.on('tool(mainApkVersionRuleTable)', function(obj) {
	     var data = obj.data;
	     if(obj.event === 'del'){ //执行删除方法
	    	 var titelDel = "真的删除吗";
	            if(i18n == 'en_US'){
	            	 titelDel = "Do you really delete it?";
	            }
	         layer.confirm(titelDel, function(index){
	        	 $.ajax({
	 					  url : contextPath+"/mainapkversion/delete",
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
 						 table.reload('mainApkVersionRuleTable');
 					  }
 				});
	         });
	     }
	     if(obj.event === 'notify'){ //执行通知更新方法
	         layer.confirm('确定提示该设备'+data.mac+'更新？', function(index){
	        	 $.ajax({
	 					  url : contextPath+"/mainapkversion/notifyToUpdate",
	 				 dataType : 'json',
	 					 type : 'post',
	 					async : false,
	 					 data : {
	 						mac : data.mac,
	 						type: data.type
	 					  },
	 				 complete : function(XHR, TS){
 						  var returnObj = eval('(' + XHR.responseText + ')');
 						  if(returnObj.code != 200) {
 							 layer.msg(returnObj.msg, {icon: 2, time: 1500});
 						  } else {
 							 layer.msg("操作成功", {icon: 1, time: 1500});		 							 
 						  }
 						 table.reload('mainApkVersionRuleTable');
 					  }
 				});
	         });
	     }
	  });
	 
	 $("#search").click(function(){
		 table.reload('mainApkVersionRuleTable',{
			 where : { //设定异步数据接口的额外参数，任意设
					searchMAC : $("#searchMAC").val()
				},
				page : {
					curr : 1
				//重新从第 1 页开始
				}
		 });
	 });
			
	});
});