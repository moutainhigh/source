define(['jquery', 'layui', 'until'], function ($, LAYUI, until) {
	layui.use(['table', 'form','laydate'], function () {
		var table = layui.table;
		var form = layui.form;
		form.render();
		table.render({
			elem: '#applyCashListTable',
			url: contextPath + "/order/rendable",
			method: 'post',
			cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
			skin: 'line',
			where: {
				sortName: 'parkName',
				direction: 'desc'
			},
			cols: [[
				{ field: 'companyName', title: '公司名称', sort: true},
				{ field: 'parkName', title: '停车场名称', sort: true, align: 'center' },
				{ field: 'licensePlate', title: '车牌号码', sort: true, align: 'center' },
				{ field: 'finalFee', title: '支付金额', sort: true, align: 'center' },
				{ field: 'payTime', title: '支付时间', sort: true, align: 'center',templet:function(data){
					if(data.payTime == null){
						return "";
					}else {
						return until.formatDateTime(data.payTime);
					}
				  } 
				},
				{ field: 'type', title: '支付类型', sort: true, align: 'center', templet:function(data){
					if(data.type == 1){
						return "支付宝";
					}else if(data.type == 2){
						return "微信";
					}else {
						return "余额";
					}
				  } 
				},
				{ fixed: 'right', field: 'status', title: '支付状态', sort: true, align: 'center', width:180,
					templet:function(data){
						if(data.status == 0){
							return "未支付";
						}else if(data.status == 1){
							return "已支付";
						}else {
							return "支付失败";
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


		table.on('sort(applyCashListTable)', function (obj) {
			table.reload('applyCashListTable', {
				initSort: obj, //记录初始排序，如果不设的话，将无法标记表头的排序状态。 layui 2.1.1 新增参数
				where: { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
					field: obj.field, //排序字段
					order: obj.type //排序方式
				}
			});
		});

	});
});