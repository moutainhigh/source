define(['jquery', 'layui', 'until'], function($, LAY, until){
	layui.use(['table', 'form', 'layer'], function(){
		var table = layui.table;
		var form = layui.form;
		var layer = layui.layer;
		if(i18n == 'en_US'){
			table.render({
				elem: '#memberRuleTable',
				url: contextPath+"/memberRule/rendering",
				method: 'post',
				cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
				skin: 'line',
				where: { //默认排序字段
					sortName: 'id',
					direction: 'asc'
				},
			    cols: [[
			      { field:'id', title: 'id', sort: true, align: 'center', hide: true},
			      { field:'grade', title: 'grade', sort: true, align: 'center'},
			      { field:'gradeDescription', title: 'gradeDescription', sort: true, align: 'center'},
			      { field:'addWay', title: 'addWay',  sort: true, align: 'center', templet:function(data){
			    	  if(data.addWay == 1){
			    		  return "充值";
			    	  }else if(data.addWay == 2){
			    		  return "消费";
			    	  }else{
			    		  return data.addWay != undefined ? data.addWay : "";
			    	  }
			        }
			      }, 
			      { field:'money', title: 'money', sort: true, align: 'center'},
			      { field:'maximumTimes', title: 'maximumTimes', sort: true, align: 'center'},
			      { field:'maximumArrears', title: 'maximumArrears', sort: true, align: 'center'},
			      { fixed: 'right', title:'right', toolbar: '#memberRuleTableBarEnglish', align: 'center',width:100}
			    ]],
			    response: {
			        countName: 'totalElements', //规定数据总数的字段名称，默认：count
			        dataName: 'content' //规定数据列表的字段名称，默认：data
			      }, 
				page: false
			});
		}else{
			table.render({
				elem: '#memberRuleTable',
				url: contextPath+"/memberRule/rendering",
				method: 'post',
				cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
				skin: 'line',
				where: { //默认排序字段
					sortName: 'id',
					direction: 'asc'
				},
			    cols: [[
			      { field:'id', title: 'id', sort: true, align: 'center', hide: true},
			      { field:'grade', title: '会员等级', sort: true, align: 'center'},
			      { field:'gradeDescription', title: '等级描述', sort: true, align: 'center'},
			      { field:'addWay', title: '获取方式',  sort: true, align: 'center', templet:function(data){
			    	  if(data.addWay == 1){
			    		  return "充值";
			    	  }else if(data.addWay == 2){
			    		  return "消费";
			    	  }else{
			    		  return data.addWay != undefined ? data.addWay : "";
			    	  }
			        }
			      }, 
			      { field:'money', title: '金额标准', sort: true, align: 'center'},
			      { field:'maximumTimes', title: '最大欠费次数', sort: true, align: 'center'},
			      { field:'maximumArrears', title: '最大欠费金额', sort: true, align: 'center'},
			      { fixed: 'right', title:'操作', toolbar: '#memberRuleTableBar', align: 'center',width:100}
			    ]],
			    response: {
			        countName: 'totalElements', //规定数据总数的字段名称，默认：count
			        dataName: 'content' //规定数据列表的字段名称，默认：data
			      }, 
				page: false
			});
		}
		
		if(i18n == 'en_US'){
			$("#writMemberName").empty();
			$("#writMemberName").append('MemberName');
			$("#AcquisitionMethod").empty();
			$("#AcquisitionMethod").append('Method');
			$("#memberGrade").empty();
			$("#memberGrade").append('Grade');
			$("#AmountStandard").empty();
			$("#AmountStandard").append('AmountStandard');
			$("#MaxNumberOfArrears").empty();
			$("#MaxNumberOfArrears").append('MaxArrears');
			$("#MaxNumberOfArrears").empty();
			$("#MaxNumberOfArrears").append('MaxArrearsNum');
			$("#MaxAmountDue").empty();
			$("#MaxAmountDue").append('MaxArrears');
			
		}
		
		/**
		 * 排序方法
		 */
		table.on('sort(memberRuleTable)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
			table.reload('memberRuleTable', {
			    initSort: obj //记录初始排序，如果不设的话，将无法标记表头的排序状态
			    ,where: { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
			    	sortName: obj.field //排序字段
			      ,direction: obj.type //排序方式
			    }
			});
		});
		
		 //监听行工具事件
		 table.on('tool(memberRuleTable)', function(obj){
		     var data = obj.data;
		     //console.log(obj)
		     if(obj.event === 'edit'){
		    	 //执行编辑方法
		    	var showContent = $('#showMemberContent').html();
		 		$('#showMemberContent').html("");
		 		layer.open({
		 			type: 1,
		 			id: 'showLayui',
		 			title: '编辑会员等级规则',
		 			content: showContent, //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
		 			area: '600px',
		 			btn: ['确定', '取消'],
		 			btnAlign: 'c',
		 			success: function(layero, index){//打开层之后可执行的回调
		 				layui.use(['form', 'layer'], function(){
		 					var form = layui.form;
		 					var layer = layui.layer;
		 					//赋值
		 					$("#gradeShow").html(data.grade);
		 					var showAddWay;
		 					if(data.addWay == 1){
		 						showAddWay = "充值";
		 			    	  }else if(data.addWay == 2){
		 			    		 showAddWay = "消费";
		 			    	  }else{
		 			    		 showAddWay = data.addWay;
		 			    	  }
		 					$("#addWayShow").html(showAddWay);
		 					form.val('memberFrom', { //给表单里面的字段赋值  通过name属性
		 					    "moneyInp": data.money // "name": "value"
		 					    ,"gradeDescriptionInp": data.gradeDescription
		 					    ,"maximumTimesInp": data.maximumTimes
		 					    ,"maximumArrearsInp": data.maximumArrears
		 					});
		 					
		 					form.render();  //重新渲染表单
		 				});
		 			},
		 			yes: function(index, layero){ //确定按钮
		 				var msgArr = ["金额描述不能为空", "金额标准不能为空", "最大欠费次数不能为空", "最大欠费金额不能为空"];  //提示语集合
		 				var clsArr = ["#gradeDescriptionInp", "#moneyInp", "#maximumTimesInp", "#maximumArrearsInp"];  //id集合
		 				var validateFlag = until.validate(msgArr, clsArr); //非空验证 需要引入工具js文件until
		 				if(validateFlag){
		 					layer.msg(validateFlag , {anim: 6, time: 1500});  
		 					return ;
		 				};
		 				$.ajax({
		 					  url: contextPath+"/memberRule/save",
		 					  dataType:'json',
		 					  type:'post',
		 					  async: false,
		 					  data : {
		 						  mrId: data.id,
		 						  gradeDescriptionInp: $("#gradeDescriptionInp").val(),
		 						  moneyInp : $("#moneyInp").val(),
		 						  maximumTimesInp: $("#maximumTimesInp").val(),
		 						  maximumArrearsInp: $("#maximumArrearsInp").val()
		 					  },
		 					  complete: function(XHR, TS){
		 						  var returnObj = eval('(' + XHR.responseText + ')');
		 						  if(returnObj.code != 200){
		 							 layer.msg(returnObj.msg, {icon: 2, time: 1500});
		 						  }else{
		 							 layer.msg("操作成功", {icon: 1, time: 1500});		 							 
		 						  }
		 						 layer.close(index); 
		 						 table.reload('memberRuleTable');
		 						 
		 					  }
		 				});
		 			    
		 			},
		 			end: function(){ //只要层被销毁了，end都会执行
		 				$('#showMemberContent').html(showContent);
		 			}
		 			
		 		});
		    }
		  });
		
		
		 
	});
	
	
});