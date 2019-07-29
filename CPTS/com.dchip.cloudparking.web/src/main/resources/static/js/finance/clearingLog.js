define(['jquery', 'layui', 'until'], function ($, LAYUI, until) {
	$("#clearingLogChargeTimeBefore").attr("autocomplete","off").prepend('<input type="password" class="none" />');
	layui.use(['table', 'form', 'laydate'], function () {
		var table = layui.table;
		var form = layui.form;
		var laydate = layui.laydate;
		form.render();

		laydate.render({
			elem: '#clearingLogChargeTimeAfter',
			type: 'month'
		});
		
		laydate.render({
			elem: '#clearingLogChargeTimeBefore',
			type: 'month'
		});

		if(i18n == 'en_US'){
			table.render({
				elem: '#clearingLogTable',
				url: contextPath + "/finance/rendClearingLog",
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
					{ field: 'parkName', title: 'parkName', sort: true },
					{ field: 'chargeTime', title: 'chargeTime', sort: true},
					{ field: 'calculateAccount', title: 'calculateAccount', sort: true },
					{ field: 'calculateName', title: 'calculateName', sort: true },
					{ field: 'calculatePerson', title: 'calculatePerson', sort: true },
					{ field: 'totalAmount', title: 'totalAmount', sort: true },
					{ field: 'payMoney', title: 'payMoney', sort: true },
					{
						field: 'payTime', title: 'payTime', sort: true, templet: function (data) {
							if (data.payTime == null) {
								return "";
							} else {
								return until.formatDateTime(data.payTime);
							}
						}
					},
					{ field: 'bankCard', title: 'bankCard', sort: true },
					{ field: 'bankHolder', title: 'bankHolder', sort: true },
					{ field: 'bankName', title: 'bankName', sort: true },
					{ field: 'bankNum', title: 'bankNum', sort: true }
				]],
				response: {
					countName: 'totalElements',  //规定数据总数的字段名称，默认：count
					dataName: 'content' //规定数据列表的字段名称，默认：data
				},
				page: true
			});
		}else{
			table.render({
				elem: '#clearingLogTable',
				url: contextPath + "/finance/rendClearingLog",
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
					{ field: 'parkName', title: '停车场名称', sort: true },
					{ field: 'chargeTime', title: '年月', sort: true},
					{ field: 'calculateAccount', title: '结算账户', sort: true },
					{ field: 'calculateName', title: '结算方', sort: true },
					{ field: 'calculatePerson', title: '结算人', sort: true },
					{ field: 'totalAmount', title: '总金额', sort: true },
					{ field: 'payMoney', title: '已付金额', sort: true },
					{
						field: 'payTime', title: '付款时间', sort: true, templet: function (data) {
							if (data.payTime == null) {
								return "";
							} else {
								return until.formatDateTime(data.payTime);
							}
						}
					},
					{ field: 'bankCard', title: '入账银行卡号', sort: true },
					{ field: 'bankHolder', title: '入账银行持卡人', sort: true },
					{ field: 'bankName', title: '入账银行名称', sort: true },
					{ field: 'bankNum', title: '入账银行编号', sort: true }
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
			$("#clearingLogParkingName").empty();
			$("#clearingLogParkingName").append('ParkingName');
			$("#clearingLogDate").empty();
			$("#clearingLogDate").append('Date');
			$("#clearingLogSearch").empty();
			$("#clearingLogSearch").append('Search');
			$("#clearingLogExport").empty();
			$("#clearingLogExport").append('LogExport');
			$("#searchClearingLogParking").attr("placeholder","ParkingName");
		}


		$("#clearingLogSearch").click(function () {
			table.reload('clearingLogTable', {
				where: { //设定异步数据接口的额外参数，任意设
					financeType: $("#clearingLogType").val(),
					searchParking: $("#searchClearingLogParking").val(),
					clearingLogChargeTimeAfter: $("#clearingLogChargeTimeAfter").val(),
					clearingLogChargeTimeBefore: $("#clearingLogChargeTimeBefore").val()
				},
				page: {
					curr: 1 //重新从第 1 页开始
				}
			});
		});

		$("#clearingLogExport").click(function () {
			window.location.href = contextPath + "/finance/clearingLogExport?financeType="
				+ $("#clearingLogType").val() + "&searchParking=" + $("#searchClearingLogParking").val() + "&searchChargeTime=" + $("#searchClearingLogChargeTime").val();
		});

		table.on('sort(clearingLogTable)', function (obj) {
			//注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
			//console.log(obj.field); //当前排序的字段名
			//console.log(obj.type); //当前排序类型：desc（降序）、asc（升序）、null（空对象，默认排序）
			//console.log(this); //当前排序的 th 对象

			//尽管我们的 table 自带排序功能，但并没有请求服务端。
			//有些时候，你可能需要根据当前排序的字段，重新向服务端发送请求，从而实现服务端排序，如：
			table.reload('clearingLogTable', {
				initSort: obj, //记录初始排序，如果不设的话，将无法标记表头的排序状态。 layui 2.1.1 新增参数
				where: { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
					field: obj.field, //排序字段
					order: obj.type //排序方式
				}
			});
		});
	});
});