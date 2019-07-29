define(['jquery', 'layui', 'until', 'thirdplugin/laydate/laydate'], function ($, LAYUI, until) {

    layui.use(['table', 'form'], function () {
        var table = layui.table;
        var form = layui.form;

        var laydate = layui.laydate;
        laydate.render({
            elem: '#searchExpiryTimeString',
            type: 'datetime',
            format: 'yyyy-MM-dd HH:mm:ss'
        });

        if(i18n == 'en_US'){
        	 table.render({
                 elem: '#monthlyCardTable',
                 url: contextPath + "/monthlyCard/rendTable",
                 method: 'post',
                 cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
                 skin: 'line',
                 where: {
                     sortName: 'parkName',
                     direction: 'desc'
                 },
                 cols: [[
                     { field: 'cardId', sort: true, hide: true},
                     { field: 'parkingId', hide: true},
                     { field: 'parkName', title: 'parkName', sort: true},
                     { field: 'phone', title: 'num', sort: true, align: 'center'},
                     { field: 'licensePlate', title: 'licensePlate', sort: true, align: 'center'},
                     { field: 'carOwnerName', title: 'carOwnerName', sort: true, align: 'center'},
                     {
                         field: 'expiryTime', title: 'expiryTime', sort: true, align: 'center', templet: function (data) {
                             if (data.expiryTime == null) {
                                 return "无";
                             } else {
                                 return until.formatDateTime(data.expiryTime);
                             }
                         }
                     },
                     {
                         field: 'ifExpiry', title: 'ifExpiry', sort: true, align: 'center', templet: function (data) {
                             if (ifExpiry(data.expiryTime)) {
                                 return '<span style="color:red;">是</span>';
                             } else {
                                 return '<span style="color:green">否</span>';
                             }
                         }
                     },
                     { field: 'phone', title: 'phone', sort: true, align: 'center'},
                     { field: 'address', title: 'address', sort: true, align: 'center'},
                     {
                         field: 'type', title: 'type', sort: true, align: 'center', templet: function (data) {
                             if (data.type == 1) {
                                 return "月卡";
                             } else if (data.type == 2) {
                                 return "季卡";
                             } else if (data.type == 3) {
                                 return "年卡";
                             } else {
                                 return "固定卡";
                             }
                         }
                     },
                     {
                         field: 'isFixedSpace', title: 'isFixedSpace', sort: true, align: 'center', templet: function (data) {
                             if (data.isFixedSpace == 1) {
                                 return "是";
                             } else {
                                 return "否";
                             }
                         }
                     },
                     { 
                     	field: 'remark', title: 'remark', sort: true, align: 'center',templet: function (data){
                     		if (data.remark) {
                     		    return data.remark;
                     		}else{
                     			return "无";
                     		}
                     	
                     	}
                     },
                     { fixed: 'right', title: 'right', toolbar: '#monthlyCardBarEnglish', align: 'center', width:150}
                 ]],
                 response: {
                     countName: 'totalElements',  //规定数据总数的字段名称，默认：count
                     dataName: 'content' //规定数据列表的字段名称，默认：data
                 },
                 page: true
             });
        }else{
        	 table.render({
                 elem: '#monthlyCardTable',
                 url: contextPath + "/monthlyCard/rendTable",
                 method: 'post',
                 cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
                 skin: 'line',
                 where: {
                     sortName: 'parkName',
                     direction: 'desc'
                 },
                 cols: [[
                     { field: 'cardId', sort: true, hide: true},
                     { field: 'parkingId', hide: true},
                     { field: 'parkName', title: '停车场名称', sort: true},
                     { field: 'phone', title: '卡编号', sort: true, align: 'center'},
                     { field: 'licensePlate', title: '车牌号', sort: true, align: 'center'},
                     { field: 'carOwnerName', title: '车主姓名', sort: true, align: 'center'},
                     {
                         field: 'expiryTime', title: '到期时间', sort: true, align: 'center', templet: function (data) {
                             if (data.expiryTime == null) {
                                 return "无";
                             } else {
                                 return until.formatDateTime(data.expiryTime);
                             }
                         }
                     },
                     {
                         field: 'ifExpiry', title: '是否到期', sort: true, align: 'center', templet: function (data) {
                             if (ifExpiry(data.expiryTime)) {
                                 return '<span style="color:red;">是</span>';
                             } else {
                                 return '<span style="color:green">否</span>';
                             }
                         }
                     },
                     { field: 'phone', title: '电话号码', sort: true, align: 'center'},
                     { field: 'address', title: '地址', sort: true, align: 'center'},
                     {
                         field: 'type', title: '月卡类型', sort: true, align: 'center', templet: function (data) {
                             if (data.type == 1) {
                                 return "月卡";
                             } else if (data.type == 2) {
                                 return "季卡";
                             } else if (data.type == 3) {
                                 return "年卡";
                             } else {
                                 return "固定卡";
                             }
                         }
                     },
                     {
                         field: 'isFixedSpace', title: '是否固定车位', sort: true, align: 'center', templet: function (data) {
                             if (data.isFixedSpace == 1) {
                                 return "是";
                             } else {
                                 return "否";
                             }
                         }
                     },
                     { 
                     	field: 'remark', title: '备注', sort: true, align: 'center',templet: function (data){
                     		if (data.remark) {
                     		    return data.remark;
                     		}else{
                     			return "无";
                     		}
                     	
                     	}
                     },
                     { fixed: 'right', title: '操作', toolbar: '#monthlyCardBar', align: 'center', width:150}
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
			$("#importShow").empty();
			$("#importShow").append('ImportShow');
			$("#download").empty();
			$("#download").append('DownloadMsg');
			$("#addMonthlyCard").empty();
			$("#addMonthlyCard").append('AddMonthlyCard');
			$("#MonthlyParkingName").empty();
			$("#MonthlyParkingName").append('ParkingName');
			$("#monthlyOwnName").empty();
			$("#monthlyOwnName").append('CardOwnName');
			$("#monthlyNum").empty();
			$("#monthlyNum").append('CardCode');
			$("#monthlyLicensePlate").empty();
			$("#monthlyLicensePlate").append('LicensePlate');
			$("#monthlyEndDate").empty();
			$("#monthlyEndDate").append('EndDate');
			$("#monthlyCardParkingName").empty();
			$("#monthlyCardParkingName").append('ParkingName');
			$("#monthlyCardIsMoreCar").empty();
			$("#monthlyCardIsMoreCar").append('OneOrMore');
			$("#writeMonthlyCardLicensePlate").empty();
			$("#writeMonthlyCardLicensePlate").append('LicensePlate');
			$("#SecondCar").empty();
			$("#SecondCar").append('SecondCar');
			$("#ThirdCar").empty();
			$("#ThirdCar").append('ThirdCar');
			$("#writeCarOwnerName").empty();
			$("#writeCarOwnerName").append('OwnerName');
			$("#writePhoneNum").empty();
			$("#writePhoneNum").append('PhoneNum');
			$("#writeLocation").empty();
			$("#writeLocation").append('Location');
			$("#choseCardType").empty();
			$("#choseCardType").append('Type');
			$("#choseEndTime").empty();
			$("#choseEndTime").append('EndTime');
			$("#choseEndTime").empty();
			$("#choseEndTime").append('EndTime');
			$("#choseIsMainCar").empty();
			$("#choseIsMainCar").append('IsMainCar');
			$("#writeMaxCar").empty();
			$("#writeMaxCar").append('Max');
			$("#choseFixedSpace").empty();
			$("#choseFixedSpace").append('FixedSpace');
			$("#writeRemark").empty();
			$("#writeRemark").append('Remark');
			$("#cardListSearch").empty();
			$("#cardListSearch").append('Search');
			$("#searchParkingName").attr("placeholder","ParkingName");
			$("#searchUserName").attr("placeholder","CardOwnName");
			$("#searchCardCode").attr("placeholder","CardCode");
			$("#searchMonthlyCardLicensePlate").attr("placeholder","LicensePlate");
		}
       

        function ifExpiry( time ){
            var nowTime = new Date();
            var expiryTime = new Date(Date.parse(time));
            return expiryTime <= nowTime;
        }

        table.on('sort(monthlyCardTable)', function (obj) {
            //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            //console.log(obj.field); //当前排序的字段名
            //console.log(obj.type); //当前排序类型：desc（降序）、asc（升序）、null（空对象，默认排序）
            //console.log(this); //当前排序的 th 对象

            //尽管我们的 table 自带排序功能，但并没有请求服务端。
            //有些时候，你可能需要根据当前排序的字段，重新向服务端发送请求，从而实现服务端排序，如：
            table.reload('monthlyCardTable', {
                initSort: obj, //记录初始排序，如果不设的话，将无法标记表头的排序状态。 layui 2.1.1 新增参数
                where: { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
                    sortName: obj.field, //排序字段
                    direction: obj.type //排序方式
                }
            });
        });

        /**
         * 监听行工具事件
         */
        table.on('tool(monthlyCardTable)', function (obj) {
            var data = obj.data;
            var titelDel = "真的删除吗";
            if(i18n == 'en_US'){
            	 titelDel = "Do you really delete it?";
            }
            if (obj.event === 'del') { //执行删除方法
                layer.confirm(titelDel, function (index) {
                    $.ajax({
                        url: contextPath + "/monthlyCard/delete",
                        dataType: 'json',
                        type: 'post',
                        async: false,
                        data: {
                            cardId: data.cardId,
                            licensePlate: data.licensePlate
                        },
                        complete: function (XHR, TS) {
                            var returnObj = eval('(' + XHR.responseText + ')');
                            if (returnObj.code != 200) {
                                layer.msg(returnObj.msg, {icon: 2, time: 1500});
                            } else {
                                layer.msg("操作成功", {icon: 1, time: 1500});
                            }
                            table.reload('monthlyCardTable');
                        }
                    });
                });
            }

            if (obj.event === 'edit') {
                editMonthlyCard('edit', data);
            }
        });

        /**
         * 导入月卡信息点击事件
         */
        $("#importShow").click(function () {
            var showContent = $('#showImportContent').html();
            $('#showImportContent').html("");
            layer.open({
                type: 1,
                id: 'showLayui',
                title: '导入月卡信息',
                content: showContent, //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                area: ['500px', '300px'],
                btn: ['确定', '取消'],
                btnAlign: 'c',
                success: function (layero, index) {
                    $("#parkName").empty();
                    $("#parkName").append('<option value="" selected="selected">请选择</option>');
                    $.ajax({
                        url: contextPath + "/parking/getAllParking",
                        dataType: 'json',
                        type: 'post',
                        async: false,
                        data: {},
                        complete: function (XHR, TS) {
                            var returnObj = eval('(' + XHR.responseText + ')');
                            $.each(returnObj, function (i, obj) {
                                $("#parkName").append('<option value="' + obj.id + '">' + obj.parkName + '</option>');
                            });
                            form.render();  //重新渲染表单
                        }
                    });
                }, // success结束
                yes: function (index, layero) {
                    var msgArr = ["停车场名称不能为空"];  //提示语集合
                    var clsArr = ["#parkName"];  //id集合
                    var validateFlag = until.validate(msgArr, clsArr); //非空验证 需要引入工具js文件until
                    if (validateFlag) {
                        layer.msg(validateFlag, {anim: 6, time: 2500});
                        return;
                    }
                    if ($("#fileFlag").val() == "") {
                        layer.msg("文件不能为空", {anim: 6, time: 2500});
                        return;
                    }
                    layer.close(index);
                    var fd = new FormData($('#excelPost')[0]);
                    //加载层-风格3
                    layer.load(2);
                    $.ajax({
                        url: contextPath + "/monthlyCard/cardImport",
                        dataType: 'json',
                        type: 'post',
                        data: fd,
                        processData: false,
                        contentType: false,
                        complete: function (XHR, TS) {
                            var returnObj = eval('(' + XHR.responseText + ')');
                            if (returnObj.code != 200) {
                                layer.closeAll('loading');
                                layer.msg(returnObj.msg, {icon: 2, time: 3000});
                            }
                            setTimeout(function(){
                                layer.closeAll('loading');
                                table.reload('monthlyCardTable');
                            }, 1000);
                        }
                    });
                }, // yes结束
                end: function () { //只要层被销毁了，end都会执行
                    $('#showImportContent').html(showContent);
                }  // end结束
            });
        });

        laydate.render({
            elem: '#searchExpiryTimeString',
            type: 'datetime',
            format: 'yyyy-MM-dd HH:mm:ss'
        });
        
        $("#cardListSearch").click(function () {
            table.reload('monthlyCardTable', {
                where: { //设定异步数据接口的额外参数，任意设
                    searchParkingName: $("#searchParkingName").val(),
                    searchCardCode: $("#searchCardCode").val(),
                    searchUserName: $("#searchUserName").val(),
                    searchLicensePlate: $("#searchMonthlyCardLicensePlate").val(),
                    searchExpiryTimeString: $("#searchExpiryTimeString").val(),
                },
                page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
        });

        /**
         * 添加月卡信息点击事件
         */
        $("#addMonthlyCard").click(function () {
            editMonthlyCard("add");
        });

        //编辑或添加月卡信息的具体操作
        function editMonthlyCard(type, data) {
        	var phoneFlag = true;
            var showTitle = '编辑月卡信息';
            var responseUrl = "/monthlyCard/addCardInfo";
            var oldLicensePlate;
            var head =  $("input[name='head'][checked]").val();
            if (type == "add") {
                showTitle = '添加月卡'
            	$("#licensePlateHead").text(head);
            } else if (type == "edit") {
                showTitle = '编辑月卡';
                responseUrl = "/monthlyCard/editCardInfo";
                oldLicensePlate = data.licensePlate;
            }
            var showContent = $('#addMonthlyCardContent').html();
            $('#addMonthlyCardContent').html("");

            layer.open({
                type: 1,
                id: 'showLayui',
                title: showTitle,
                content: showContent, //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                area: ['30%', '65%'],
                btn: ['确定', '取消'],
                btnAlign: 'c',
                success: function (layero, index) {
                	$("#licensePlateHead").click(function(){
        	        	var showbox = $('#choseLicencePlateHead').html();
        	    		$('#choseLicencePlateHead').html("");
        	    		layer.open({
        	    			type: 1,
        	    			offset:'c',
        	                id: 'licencePlateBox',
        	                title: "请选择",
        	                content: showbox, 
        	                area: ['20%', '30%'],
        	                btn: ['确定', '取消'],
        	                btnAlign: 'c',
        	                success: function (layero, index){
        	                	if(type == "edit"){
        	                		 var licensePlateNum = data.licensePlate;
        	                		 var separateHead = licensePlateNum.slice(0,1);
        	                		 $("input:radio[name='head'][value='"+(separateHead)+"']").attr("checked",true)
        	                	}
        	                	form.render();
        	                },//success结束
            	    		yes:function(index, layero){//确定按钮
            	    			 head = $(".layui-unselect.layui-form-radio.layui-form-radioed div").text();
            	    			 $("#licensePlateHead").text(head);
            	    			 layer.close(index);
            	    		},
            	    		 end: function () { //只要层被销毁了，end都会执行
            	                 $('#choseLicencePlateHead').html(showbox);
            	             }
            	    	});
                	});
                	
                    $.ajax({
                        url: contextPath + "/parking/getAllParking",
                        dataType: 'json',
                        type: 'post',
                        async: false,
                        data: {},
                        complete: function (XHR, TS) {
                            var returnObj = eval('(' + XHR.responseText + ')');
                            $("#monthlyCardName").empty();
                            $("#monthlyCardName").append('<option value="" selected="selected">请选择</option>');
                            $.each(returnObj, function (i, obj) {
                                $("#monthlyCardName").append('<option value="' + obj.id + '">' + obj.parkName + '</option>');
                            });

                        }
                    });

                    laydate.render({
                        elem: '#cardExpiryTime',
                        type: 'datetime',
                        format: 'yyyy年MM月dd日 HH时mm分ss秒'
                    });

                    var cardTypes = [
                    	{ id: 1,cardTypeName: "月卡"}, 
                    	{ id: 2,cardTypeName: "季卡"}, 
                    	{ id: 3,cardTypeName: "年卡"},
                    	{ id: 4,cardTypeName: "固定卡"}
                    ];
                    $("#cardType").empty();
                    $("#cardType").append('<option value="" selected="selected">请选择</option>');
                    $.each(cardTypes, function (i, obj) {
                        $("#cardType").append('<option value="' + obj.id + '">' + obj.cardTypeName + '</option>');
                    });
                    if (data) {
                    	console.log(data.moreCarLisencePlate);
                        $("#cardId").val(data.cardId);
                        $("#monthlyCardName").val(data.parkingId);
                        $("#monthlyCardName").siblings("div.layui-form-select").find('dl dd[lay-value=' + data.parkingId + ']').click();

                        $("#monthlyCardCode").val(data.cardCode);
                        $("#monthlyCardCode").attr("readonly","true");
                        $("#monthlyCardCode").css("color","gray");

                        var licensePlateNum = data.licensePlate;
                        var separateHead = licensePlateNum.slice(0,1);
                        var separateEnd = licensePlateNum.slice(1,8);
                        head = separateHead;
                        $("#licensePlateHead").text(separateHead);
                        $("#licensePlateHead").attr("disabled",true);
                        $("#monthCardLicensePlate").val(separateEnd);
                        $("#monthCardLicensePlate").attr("readonly","true");
                        $("#monthCardLicensePlate").css("color","gray");

                        $("#carOwnerName").val(data.carOwnerName);
                        $("#cardExpiryTime").val((function (d) {
                            if (d.expiryTime == null) {
                                return "";
                            } else {
                                return until.formatDateTimeString(d.expiryTime);
                            }
                        })(data));
                        if(data.isMain == 1){
                            $('#isMain').attr("checked", "checked");
                            $('#isMainHidden').val(1);
                        }else{
                            $('#isMain').remove("checked");
                            $('#isMainHidden').val(0);
                        }
                        if(data.isFixedSpace == 1){
                            $('#isFixedSpace').attr("checked", "checked");
                            $('#isFixedSpaceHidden').val(1);
                        }else{
                            $('#isFixedSpace').remove("checked");
                            $('#isFixedSpaceHidden').val(0);
                        }
                        $("#supportMax").val(data.max);
                        $("#cardType").val(data.type);

                        $("#cardRemark").val(data.remark);
                        $("#monthlyCardPhone").val(data.phone);
                        $("#monthlyCardAddress").val(data.address);
                        
                        
                        //------ 一卡多车选择 start ------
                        /*$("input:radio[name='isMoreCar'][value='"+(data.moreCarType)+"']").attr('checked',true);*/
                        if(data.moreCarType == 2){
                        	$('#isMoreCar').attr("checked", "checked");
                        	$('#isMoreCarHidden').val(2);
                        }else{
                        	$('#isMoreCar').attr("checked");
                        	$('#isMoreCarHidden').val(1);
                        }
                        if(data.moreCarType == "2"){//一卡多车
                        	var moreCar = data.moreCarLisencePlate;
                        	if(moreCar != null && moreCar != "" && moreCar != undefined){
                        		secondLicensePlate = moreCar.slice(0,7);
                        	}
                        	if(moreCar != null && moreCar != "" && moreCar != undefined){
                        		thirdLicensePlate = moreCar.slice(9,16);
                        	}
                        	if(moreCar != null && moreCar != "" && moreCar != undefined){
                            	$("#secondLicensePlate").val(secondLicensePlate);
                        	}else{
                        		$("#secondLicensePlate").val("");
                        	}
                        	if(moreCar != null && moreCar != "" && moreCar != undefined){
                            	$("#thirdLicensePlate").val(thirdLicensePlate);
                        	}else{
                        		$("#thirdLicensePlate").val("");
                        	}
                            $("#theSecondLicensePlate").css("display","");
                            $("#secondLicensePlate").css("display","");
                            $("#theThirdLicensePlate").css("display","");
                            $("#thirdLicensePlate").css("display","");
                        }else{
                        	 $("#theSecondLicensePlate").css("display","none");
                             $("#secondLicensePlate").css("display","none");
                             $("#secondLicensePlate").val("");
                             $("#theThirdLicensePlate").css("display","none");
                             $("#thirdLicensePlate").css("display","none");
                             $("#thirdLicensePlate").val("");

                        }
                        //------ 一卡多车选择 end ------
                    }
                        

                    //form监听事件
                    form.on('switch(isMoreCarFilter)', function(data){
                    	if(data.elem.checked){
                            $("#isMoreCarHidden").val(2);
                        }else {
                            $("#isMoreCarHidden").val(1);
                        }
                    	if( $("#isMoreCarHidden").val() == "2"){
                            $("#theSecondLicensePlate").css("display","");
                            $("#theThirdLicensePlate").css("display","");
                            $("#secondLicensePlate").css("display","");
                            $("#thirdLicensePlate").css("display","");
                        }else{
                        	$("#theSecondLicensePlate").css("display","none");
                        	$("#theThirdLicensePlate").css("display","none");
                            $("#secondLicensePlate").css("display","none");
                            $("#thirdLicensePlate").css("display","none");
                            $("#secondLicensePlate").val("");
                            $("#thirdLicensePlate").val("");
                        }
                    });
                    form.on('switch(isMainFilter)', function(data){
                        // console.log(data.elem); //得到checkbox原始DOM对象
                        // console.log(data.elem.checked); //开关是否开启，true或者false
                        // console.log(data.value); //开关value值，也可以通过data.elem.value得到
                        // console.log(data.othis); //得到美化后的DOM对象
                        if(data.elem.checked){
                            $("#isMainHidden").val(1);
                        }else {
                            $("#isMainHidden").val(0);
                        }
                    });
                    form.on('switch(isFixedSpaceFilter)', function(data){
                        // console.log(data.elem); //得到checkbox原始DOM对象
                        // console.log(data.elem.checked); //开关是否开启，true或者false
                        // console.log(data.value); //开关value值，也可以通过data.elem.value得到
                        // console.log(data.othis); //得到美化后的DOM对象
                        if(data.elem.checked){
                            $("#isFixedSpaceHidden").val(1);
                        }else {
                            $("#isFixedSpaceHidden").val(0);
                        }
                    });

                    form.on('select(cardTypeFilter)', function(data){
                    	if(data.value == 4){  //4为固定卡，隐藏选择过期时间选项
                    		$("#showEndTimeBox").addClass("none");
                    	}else{  //其他月卡类型显示选择过期时间选项
                    		$("#showEndTimeBox").removeClass("none");
                    	}
                    }); 
                    
                    $("#monthlyCardPhone").change(function(){
						var str =$("#monthlyCardPhone").val();
						if(!isPhone(str)){
							layer.msg("请输入正确的手机号码");
							phoneFlag = false ;
							return;
						}else{
							phoneFlag = true ;
						}
					});
                    
                    $("#monthCardLicensePlate").blur(function() {
                    	var thisName = head + $(this).val().toUpperCase();
                        var checkPwd = /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$/;
                        if(thisName != "" && thisName != oldLicensePlate){
                        	if(!checkPwd.test(thisName)){
                        		layer.msg("车牌号码格式错误，请重新输入！", {icon: 2, time: 2500});
                        		return;
                        	}else{
                        		$.ajax({
                                    url: contextPath + "/monthlyCard/checkRepeat",
                                    dataType: 'json',
                                    type: 'post',
                                    async: false,
                                    data: {
                                        parkingId: $("#monthlyCardName").val(),
                                        licensePlate: thisName
                                    },
                                    complete: function (XHR, TS) {
                                        var returnObj = eval('(' + XHR.responseText + ')');
                                        if (returnObj.code != 200) {
                                            layer.msg(returnObj.msg, {icon: 2, time: 2500});
                                        }
                                    }
                                });
                        	}  
                        };
                   });
                   form.render();
                }, //success结束

                yes: function (index, layero) {//确定按钮
                	var LicensePlate = head + $("#monthCardLicensePlate").val();
                	var secondLicensePlate = $("#secondLicensePlate").val();
                	var thirdLicensePlate = $("#thirdLicensePlate").val();
                    var cardExpiryTimeInp = $("#cardExpiryTime").val();
                    var cardTypeInp = $("#cardType").val();
                    if(cardTypeInp == 4){
                    	cardExpiryTimeInp = "2099年01月01日 00时00分00秒";
                    	$("#cardExpiryTime").val(cardExpiryTimeInp);
                    }
                    var msgArr = ["停车场名称不能为空","车牌不能为空","车主名称不能为空","手机号码不能为空","住址不能为空","过期时间不能为空","最大数不能为空","卡类型不能为空"]; // 提示语集合
                    var clsArr = ["#monthlyCardName","#monthCardLicensePlate","#carOwnerName","#monthlyCardPhone","#monthlyCardAddress","#cardExpiryTime","#supportMax","#cardType"]; // id集合
                    var validateFlag = until.validate(msgArr, clsArr); // 非空验证,需要引入工具js文件until
                    if (validateFlag) {
                        layer.msg(validateFlag, {
                            anim : 6,
                            time : 2500
                        });
                        return;
                    };

                    if(!isLicensePlate(LicensePlate.toUpperCase())){
                        layer.msg("请输入正确的车牌号", {anim: 6, time: 2500});
                        return;
                    }
                    if(secondLicensePlate !=null && secondLicensePlate != "" ){
                    	if(!isLicensePlate(secondLicensePlate.toUpperCase())){
                    		layer.msg("请输入正确的副车牌一", {anim: 6, time: 2500});
                    		return;
                    	}
                    }
                    
                    if(thirdLicensePlate !=null && thirdLicensePlate != ""){
                    	if(!isLicensePlate(thirdLicensePlate.toUpperCase())){
                    		layer.msg("请输入正确的副车牌二", {anim: 6, time: 2500});
                    		return;
                    	}
                    }
                    
                    if(phoneFlag == false){
                        layer.msg("请输入正确的手机号码", {anim: 6, time: 2500});
                        return;
                    }
                    
                    var cardExpiryTimeInp = $("#cardExpiryTime").val();
                    var cardTypeInp = $("#cardType").val();
                    if(cardTypeInp == 4){
                    	cardExpiryTimeInp = "2099年01月01日 00时00分00秒";
                    }
                    $.ajax({
                        url: contextPath + responseUrl,
                        dataType: 'json',
                        type: 'post',
                        async: false,
                        data: {
                            parkingId: $("#monthlyCardName").val(),
                            cardId: (function () {
                                if(data){//若为编辑
                                    return data.cardId;
                                }
                                return '';
                            })(),
                            cardCode:$("#monthlyCardPhone").val(),
                            licensePlate:LicensePlate,
                            carOwnerName: $("#carOwnerName").val(),
                            expiryTimeString: cardExpiryTimeInp,
                            endTimeString: $("#endTime").val(),
                            isMain: $("#isMainHidden").val(),
                            isFixedSpace: $("#isFixedSpaceHidden").val(),
                            max: $("#supportMax").val(),
                            type: cardTypeInp,
                            remark: $("#cardRemark").val(),
                            phone: $("#monthlyCardPhone").val(),
                            address: $("#monthlyCardAddress").val(),
                            secondLicensePlate : secondLicensePlate,
                            thirdLicensePlate : thirdLicensePlate,
                            moreCarType : $("#isMoreCarHidden").val()
                        },
                        complete: function (XHR, TS) {
                            var returnObj = eval('(' + XHR.responseText + ')');
                            if (returnObj.code != 200) {
                                layer.msg(returnObj.msg, {icon: 2, time: 1500});
                            } else {
                                layer.msg("操作成功", {icon: 1, time: 1500});
                            }
                            layer.close(index);
                            table.reload('monthlyCardTable');
                        }
                    });
                },

                end: function () { //只要层被销毁了，end都会执行
                    $('#addMonthlyCardContent').html(showContent);
                }
            });
        } //编辑或添加月卡信息的具体操作 end
    });
    
    function isPhone(str){
		var reg = new RegExp(/^1(3|4|5|7|8)\d{9}$/);		
		if(!reg.test(str)){
			return false;
		}
		return true;
	}
    
    function isLicensePlate(str){
    	 var result = false;
         if (str.length == 7){
             var express = /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$/;
             result = express.test(str);
         }
         return result;
	}
});