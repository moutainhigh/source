define(['jquery', 'layui', 'until'], function ($, LAYUI, until) {
	layui.use(['table', 'form'], function () {
		var table = layui.table;
		var form = layui.form;
		form.render();
		table.render({
			elem: '#financeTable',
			url: contextPath + "/finance/rendTable",
			method: 'post',
			cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
			skin: 'line',
			where: {
				sortName: 'parkName',
				direction: 'desc'
			},
			cols: [[
				{
					field: 'financeType', title: '类型', sort: true, templet: function (data) {
						if (data.financeType == 1) {
							return "停车消费";
						} else if (data.financeType == 2) {
							return "月卡缴费";
						} else {
							return "未知";
						}
					}
				},
				{ field: 'parkName', title: '停车场名称', sort: true,align: 'center'},
				{ field: 'chargeTime', title: '年月', sort: true,align: 'center'},
				{ field: 'name', title: '公司名称', sort: true,align: 'center' , templet: function(data){
					if(data.name){
						return data.name;
					}else{
						return "无";
					}
				}},
//				{ field: 'location', title: '地址', sort: true },
				{ field: 'bankCard', title: '入账银行卡号', sort: true,align: 'center' , templet: function(data){
					if(data.bankCard){
						return data.bankCard;
					}else{
						return "无";
					}
				}},
				{ field: 'bankHolder', title: '入账银行持卡人', sort: true,align: 'center', templet: function(data){
					if(data.bankHolder){
						return data.bankHolder;
					}else{
						return "无";
					}
				}},
				{ field: 'bankName', title: '入账银行名称', sort: true,align: 'center' , templet: function(data){
					if(data.bankName){
						return data.bankName;
					}else{
						return "无";
					}
				}},
				{ field: 'bankNum', title: '入账银行编号', sort: true,align: 'center' , templet: function(data){
					if(data.bankNum){
						return data.bankNum;
					}else{
						return "无";
					}
				}},
				{ field: 'totalAmount', title: '总金额', sort: true,align: 'center' },
				{ field: 'calculateDay', title: '月结日', sort: true,align: 'center' },
				{
					field: 'status', title: '结算状态', sort: true,align: 'center', templet: function (data) {
						// 1-成功 0-失败 2-待处理
						if (data.status == 1) {
							return "成功";
						} else if (data.status == 0) {
							return "失败";
						} else {
							return "待处理";
						}
					}
				},
				{
					field: 'status', title: '结算状态', sort: true,align: 'center', templet: function (data) {
						// 1-成功 0-失败 2-待处理
						if (data.status == 1) {
							return "<button class='layui-btn layui-btn-disabled layui-btn-xs'>已结算</button>";
						} else {
							return "<button lay-event='edit' class='layui-btn layui-btn-danger layui-btn-xs'>结算</button>";
						}
					}
				}
			]],
			response: {
				countName: 'totalElements',  //规定数据总数的字段名称，默认：count
				dataName: 'content' //规定数据列表的字段名称，默认：data
			},
			page: true
		});


		table.on('sort(financeTable)', function (obj) {
			//注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
			//console.log(obj.field); //当前排序的字段名
			//console.log(obj.type); //当前排序类型：desc（降序）、asc（升序）、null（空对象，默认排序）
			//console.log(this); //当前排序的 th 对象

			//尽管我们的 table 自带排序功能，但并没有请求服务端。
			//有些时候，你可能需要根据当前排序的字段，重新向服务端发送请求，从而实现服务端排序，如：
			table.reload('financeTable', {
				initSort: obj, //记录初始排序，如果不设的话，将无法标记表头的排序状态。 layui 2.1.1 新增参数
				where: { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
					field: obj.field, //排序字段
					order: obj.type //排序方式
				}
			});
		});

		$("#financeSearch").click(function () {
			table.reload('financeTable', {
				where: { //设定异步数据接口的额外参数，任意设
					financeType: $("#financeType").val(),
					searchParking: $("#searchFinanceParking").val(),
					searchCompany: $("#searchFinanceCompany").val()
				},
				page: {
					curr: 1 //重新从第 1 页开始
				}
			});
		});

		$("#financeExcel").click(function () {
			window.location.href = contextPath + "/finance/financeExcel?financeType="
				+ $("#financeType").val() + "&searchParking=" + $("#searchFinanceParking").val() + "&searchCompany=" + $("#searchFinanceCompany").val();
		});

		//监听行工具事件
		table.on('tool(financeTable)', function (obj) {
			var data = obj.data;
			if (obj.event === 'edit') {
				var showContent = $('#showClearContent').html();
				$('#showClearContent').html("");
				layer.open({
					type: 1,
					id: 'showLayui',
					title: "是否结算",
					content: showContent,
					area: '600px',
					btn: ['确定', '取消'],
					btnAlign: 'c',

					success: function (layero, index) {
					},

					yes: function (index, layero) { // 确定按钮
						var msgArr = ["结算方不能为空", "结算人不能为空", "结算账户不能为空"]; // 提示语集合
						var clsArr = ["#name", "#person", "#account"]; // id集合
						var validateFlag = until.validate(msgArr, clsArr); // 非空验证,需要引入工具js文件until
						if (validateFlag) {
							layer.msg(validateFlag, {
								anim: 6,
								time: 2500
							});
							return;
						};
						layer.confirm('是否结算', function (index1) {
							$.ajax({
								url: contextPath + "/finance/changeFinanceStatus",
								dataType: 'json',
								type: 'post',
								data: {
									financeId: data.financeId,
									name: $("#name").val(),
									person: $("#person").val(),
									account: $("#account").val()
								},
								complete: function (XHR, TS) {
									var returnObj = eval('(' + XHR.responseText + ')');
									if (returnObj.code != 200) {
									}
									layer.close(index1);
								}
							});
							layer.close(index);
							table.reload('financeTable');
						});
					},

					end: function () { // 只要层被销毁了，end都会执行
						$('#showClearContent').html(showContent);
					}

				});
			}
		});
	});
});