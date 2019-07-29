define(['jquery', 'layui', 'until'], function ($, LAYUI, until) {
	layui.use(['table', 'form'], function () {
		var table = layui.table;
		var form = layui.form;
		form.render();
		if(i18n == 'en_US'){
			table.render({
				elem: '#userTable',
				url: contextPath + "/user/rendTable",
				method: 'post',
				cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
				skin: 'line',
				where: {
					sortName: 'nickname',
					direction: 'desc'
				},
				cols: [[
					//{ field: 'nickname', title: '用户名', sort: true },
					{ field: 'licensePlat', title: 'licensePlat', sort: true },
					{ field: 'phone', title: 'phone', sort: true, align: 'center' },
					{ field: 'gradeDescription', title: 'gradeDescription', sort: true, align: 'center' , templet: function(data){
						if(data.gradeDescription){
							return data.gradeDescription;
						}else{
							return "无";
						}
					  }
					},
					{ field: 'balance', title: 'balance', sort: true, align: 'center' },
					{ field: 'scores', title: 'scores', sort: true, align: 'center' },
					{ field: 'amount', title: 'amount', sort: true, align: 'center' },
					{ field: 'fee', title: 'fee', sort: true, align: 'center' },
					{ field: 'falseReportNumber', title: 'falseReportNumber', sort: true, align: 'center' },
					{
						field: 'state', title: '账号状态', sort: true, align: 'center', templet: function (data) {
							// console.log((data.licensePlat +" : " + data.state));
							if (data.state == 0) {
								return "正常";
							} else {
								return "<span style='color: red;'>禁用</span>";
							}
						}
					},
					{
						field: 'isBlack', title: 'isBlack', sort: true, align: 'center', templet: function (data) {
							if (data.isBlack == 0) {
								return "否";
							} else {
								return "<span style='color: red;'>是</span>";
							}
						}
					},
					{ title: 'oweTheSituation', toolbar: '#arrearageBarEnglish', align: 'center' },
					{ 
						fixed: 'right', field: 'state', title: 'right', width:80, templet: function (data) {
							if (data.state == 1) {
								return "<button lay-event='operateBar' class='layui-btn layui-btn-xs'>Open</button>";
							} else {
								return "<button lay-event='operateBar' class='layui-btn layui-btn-danger layui-btn-xs'>Forbidden</button>";
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
		}else{
			table.render({
				elem: '#userTable',
				url: contextPath + "/user/rendTable",
				method: 'post',
				cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
				skin: 'line',
				where: {
					sortName: 'nickname',
					direction: 'desc'
				},
				cols: [[
					//{ field: 'nickname', title: '用户名', sort: true },
					{ field: 'licensePlat', title: '车牌', sort: true },
					{ field: 'phone', title: '手机号', sort: true, align: 'center' },
					{ field: 'gradeDescription', title: '会员等级', sort: true, align: 'center' , templet: function(data){
						if(data.gradeDescription){
							return data.gradeDescription;
						}else{
							return "无";
						}
					  }
					},
					{ field: 'balance', title: '当前余额', sort: true, align: 'center' },
					{ field: 'scores', title: '当前积分', sort: true, align: 'center' },
					{ field: 'amount', title: '总充值金额', sort: true, align: 'center' },
					{ field: 'fee', title: '总消费金额', sort: true, align: 'center' },
					{ field: 'falseReportNumber', title: '谎报次数', sort: true, align: 'center' },
					{
						field: 'state', title: '账号状态', sort: true, align: 'center', templet: function (data) {
							// console.log((data.licensePlat +" : " + data.state));
							if (data.state == 0) {
								return "正常";
							} else {
								return "<span style='color: red;'>禁用</span>";
							}
						}
					},
					{
						field: 'isBlack', title: '是否为黑名单', sort: true, align: 'center', templet: function (data) {
							if (data.isBlack == 0) {
								return "否";
							} else {
								return "<span style='color: red;'>是</span>";
							}
						}
					},
					{ title: '欠费情况', toolbar: '#arrearageBar', align: 'center' },
					{ 
						fixed: 'right', field: 'state', title: '操作', width:80, templet: function (data) {
							if (data.state == 1) {
								return "<button lay-event='operateBar' class='layui-btn layui-btn-xs'>解禁</button>";
							} else {
								return "<button lay-event='operateBar' class='layui-btn layui-btn-danger layui-btn-xs'>禁用</button>";
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
		}
		
		//切换英文
		if(i18n == 'en_US'){
			$("#phoneNum").empty();
			$("#phoneNum").append('PhoneNum');
			$("#licensePlat").empty();
			$("#licensePlat").append('LicensePlat');
			$("#userSearch").empty();
			$("#userSearch").append('Search');
			$("#searchPhone").attr("placeholder","PhoneNum");
			$("#searchLicence").attr("placeholder","LicensePlat");
		}

		table.on('sort(userTable)', function (obj) {
			//注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
			//console.log(obj.field); //当前排序的字段名
			//console.log(obj.type); //当前排序类型：desc（降序）、asc（升序）、null（空对象，默认排序）
			//console.log(this); //当前排序的 th 对象

			//尽管我们的 table 自带排序功能，但并没有请求服务端。
			//有些时候，你可能需要根据当前排序的字段，重新向服务端发送请求，从而实现服务端排序，如：
			table.reload('userTable', {
				initSort: obj, //记录初始排序，如果不设的话，将无法标记表头的排序状态。 layui 2.1.1 新增参数
				where: { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
					field: obj.field, //排序字段
					order: obj.type //排序方式
				}
			});
		});

		$("#userSearch").click(function () {
			table.reload('userTable', {
				where: { //设定异步数据接口的额外参数，任意设
					searchPhone: $("#searchPhone").val(),
					searchLicence: $("#searchLicence").val(),
					blackSelect: $("#blackSelect").val()
				},
				page: {
					curr: 1 //重新从第 1 页开始
				}
			});
		});

		//监听行工具事件
		table.on('tool(userTable)', function (obj) {
			var data = obj.data;
			// console.log(obj)
			if (obj.event === 'arrearageBar') {
				var showContent = $('#showUserContent').html();
				$('#showUserContent').html("");
				layer.open({
					type: 1,
					id: 'showLayui',
					title: (data.licensePlat==null?"":data.licensePlat)+' 用户详情',
					content: showContent, //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
					area: ['600px', '500px'],
					btnAlign: 'c',
					offset: 'auto',
					success: function (layero, index) {//打开层之后可执行的回调
						$.ajax({
							url: contextPath + "/user/getArrearagelist",
							dataType: 'json',
							type: 'post',
							async: false,
							data: {
								userId: data.userId
							},
							complete: function (XHR, TS) {
								var returnObj = eval('(' + XHR.responseText + ')');
								if (returnObj.code != 200) {
									layer.msg(returnObj.msg, { icon: 2, time: 1500 });
								} else {
									$("#arrearageContent").html('');
									var result = returnObj.data;
									if (result.length > 0) {
										for (let i = 0; i < result.length; i++) {
											var content = (
												"<li class='layui-timeline-item'>" +
													"<i class='layui-icon layui-timeline-axis'></i>" +
													"<div class='layui-timeline-content layui-text'>" +
														"<h3 class='layui-timeline-title'>入场时间：" + (result[i].parkDate == null?"":until.formatDateTime(result[i].parkDate)) + "</h3>" +
														"<p>出场时间：" + (result[i].outDate == null?"":until.formatDateTime(result[i].outDate))+ "</p>" +
														"<p>车牌号：" + (result[i].licensePlate == null?"无":result[i].licensePlate) + "</p>" +
														"<p>停车场名称：" + (result[i].parkName == null?"无":result[i].parkName) + "</p>" +
														"<p>停车场地址：" + (result[i].provinceName==null?"":result[i].provinceName) + (result[i].cityName==null?"":result[i].cityName) + (result[i].areaName==null?"":result[i].areaName) + (result[i].location==null?"":result[i].location) + "</p>" +
														"<p>订单状态："+ (result[i].orderStatus == null?"无":(result[i].orderStatus==0?"未支付":"支付失败")) + "</p>" +
														"<p>停车费用：" + (result[i].fee == null?"0":result[i].fee) + "元" + "</p>" +
														"<p>停车时长：" + (result[i].parkingTime == null?"无":result[i].parkingTime+"分钟") + "</p>" +
													"</div>" +
												"</li>"
											);
											$("#arrearageContent").append(content);
										}
									} else {
										$("#arrearageContent").append("<p>暂无欠费记录</p>");
									}
								}
							}
						});
					},
					end: function () { //只要层被销毁了，end都会执行
						$('#showUserContent').html(showContent);
					}
				});
			}
			if (obj.event === 'operateBar') {
				var titel = "是否禁用该账号？";
				 if(data.state == 1){
					 titel = "是否启用该账号？";
					 if(i18n == 'en_US'){
						 titel = "Do you really open it?";
					 }
				 }
				 if(i18n == 'en_US' && data.state == 0){
					 titel = "Do you really forbidden it?";
				 }
				layer.confirm(titel, function (index) {
					console.log(obj);
					$.ajax({
						url: contextPath + "/user/changeUserStatus",
						dataType: 'json',
						type: 'post',
						data: {
							userId: data.userId,
							state: data.state
						},
						complete: function (XHR, TS) {
							var returnObj = eval('(' + XHR.responseText + ')');
							if (returnObj.code != 200) {
							}
							layer.close(index);
							table.reload('userTable');
						}
					});
				});
			}
		});
	});
});