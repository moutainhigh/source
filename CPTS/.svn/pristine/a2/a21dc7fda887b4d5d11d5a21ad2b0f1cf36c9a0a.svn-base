define(['jquery', 'layui','until'], function($,LAYUI,until){
	$("#userCouponCreateTime").attr("autocomplete","off").prepend('<input type="password" class="none" />');
	layui.use(['table','form','laydate'], function(){
		var table = layui.table;
		var form = layui.form;
		
	    var laydate = layui.laydate;
		// laydate.render({
	    //       elem: '#userCouponCreateTime',
	    //       type: 'datetime',
	    //       format: 'yyyy-MM-dd HH:mm:ss'
	    // });

        layui.use('laydate', function(){
            var laydate = layui.laydate;
            var endDate= laydate.render({
                elem: '#end_time',//选择器结束时间
                // type: 'datetime',
                type: 'date',
                min:"1970-1-1",//设置min默认最小值
                done: function(value,date){
                    startDate.config.max={
                        year:date.year,
                        month:date.month-1,//关键
                        date: date.date,
                        // hours: 0,
                        // minutes: 0,
                        // seconds : 0
                    }
                }
            });
            //日期范围
            var startDate=laydate.render({
                elem: '#start_time',
                // type: 'datetime',
                type: 'date',
                max:"2099-12-31",//设置一个默认最大值
                done: function(value, date){
                    endDate.config.min ={
                        year:date.year,
                        month:date.month-1, //关键
                        date: date.date,
                        // hours: 0,
                        // minutes: 0,
                        // seconds : 0
                    };
                }
            });
        });


        var oldActivityName;			//保存原来的角色名称
	    var cheakNameFlag = true;   //检查角色重名标志  false为重名，true 为不重名
	    if(i18n == 'en_US'){
	    	table.render({
				elem : '#activityRecordTable',
				 url : contextPath+"/activityManage/recordList",
			  method : 'post',
		cellMinWidth : 150 ,//全局定义常规单元格的最小宽度，layui 2.2.1 新增
				skin : 'line',
			   where : {
                   sortName: 'createTime',
                   direction: 'desc'
               },
			    cols : [[
			         { field:'id',hide:true },
			         { field:'userId',hide:true },
			         { field:'userName', title: 'userName', sort:true, align: 'center' },
                     { field:'createTime', title: 'Time', align: 'center', sort:true,templet: function (data){
                             if(data.createTime == null){
                                 return "";
                             }else{
                                 return until.formatDateTime(data.createTime);
                             }
                         }
                     },
			         { field:'couponCount', title: 'couponCount', sort:true, align: 'center'},
			         { field:'activityRemark', title: 'activityRemark', sort:true, align: 'center'},
                     { field:'useTime', title: 'useTime', align: 'center', sort:true,templet: function (data){
                             if(data.useTime == null){
                                 return '<font color="green">未使用</font>';
                             }else{
                                 return until.formatDateTime(data.useTime);
                             }
                         }
                     }
//			         { fixed: 'right', title:'操作', width:150, align: 'center', templet: function (data) {
//                             var button = "";
//                             // button += "<button lay-event='edit' class='layui-btn layui-btn-xs'>编辑</button>";
//                             // button += "<button lay-event='del' class='layui-btn layui-btn-danger layui-btn-xs'>删除</button>";
//                             return button;
//                         }
//                     },
			        ]],
			 response : {
			        countName: 'totalElements', //规定数据总数的字段名称，默认：count
			        dataName: 'content' //规定数据列表的字段名称，默认：data
			      }, 
				 page : true
			}); 
	    }else{
	    	table.render({
				elem : '#activityRecordTable',
				 url : contextPath+"/activityManage/recordList",
			  method : 'post',
		cellMinWidth : 150 ,//全局定义常规单元格的最小宽度，layui 2.2.1 新增
				skin : 'line',
			   where : {
                   sortName: 'createTime',
                   direction: 'desc'
               },
			    cols : [[
			         { field:'id',hide:true },
			         { field:'userId',hide:true },
			         { field:'userName', title: '用户名', sort:true, align: 'center' },
                     { field:'createTime', title: '日志时间', align: 'center', sort:true,templet: function (data){
                             if(data.createTime == null){
                                 return "";
                             }else{
                                 return until.formatDateTime(data.createTime);
                             }
                         }
                     },
			         { field:'couponCount', title: '面值', sort:true, align: 'center'},
			         { field:'activityRemark', title: '活动名称', sort:true, align: 'center'},
                     { field:'useTime', title: '使用时间', align: 'center', sort:true,templet: function (data){
                             if(data.useTime == null){
                                 return '<font color="green">未使用</font>';
                             }else{
                                 return until.formatDateTime(data.useTime);
                             }
                         }
                     }
//			         { fixed: 'right', title:'操作', width:150, align: 'center', templet: function (data) {
//                             var button = "";
//                             // button += "<button lay-event='edit' class='layui-btn layui-btn-xs'>编辑</button>";
//                             // button += "<button lay-event='del' class='layui-btn layui-btn-danger layui-btn-xs'>删除</button>";
//                             return button;
//                         }
//                     },
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
			$("#recordUserName").empty();
			$("#recordUserName").append('UserName');
			$("#recordTime").empty();
			$("#recordTime").append('Time');
			$("#userCouponSearch").empty();
			$("#userCouponSearch").append('Search');
			$("#userCouponUserName").attr("placeholder","UserName");
			$("#start_time").attr("placeholder","StartTime");
			$("#end_time").attr("placeholder","EndTime");
		}
			
			/**
			 * 排序方法
			 */
			table.on('sort(activityRecordTable)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
				table.reload('activityRecordTable', {
				    initSort: obj, //记录初始排序，如果不设的话，将无法标记表头的排序状态
				    where: { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
				    	sortName: obj.field, //排序字段
				        direction: obj.type //排序方式
				    }
				});
			});
	
			$("#userCouponSearch").click(function () {
	            table.reload('activityRecordTable', {
	                where: {
						startTime:$("#start_time").val(),
						endTime:$("#end_time").val(),
	                    userName: $("#userCouponUserName").val()
	                },
	                page: {
	                    curr: 1 //重新从第 1 页开始
	                }
	            });
	        });
			
		/**
		 * 监听行工具事件
		 */
		 table.on('tool(activityRecordTable)', function(obj) {

		  });
	 

	});
});