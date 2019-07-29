define(['jquery', 'layui', 'until'], function ($, LAYUI, until) {
    var table;
    var form;
    var roadways;//选择车道集合
    var currentRoadwayIndex = 0;//当前的车道数组索引
    var roadwaysData = new Array();//车道的数据集合
    var redioElem;  //车道redio当前选择对象
    var oldCameraMacName;//旧摄像机名字
    var cheakNameFlag = true;//判断重复
    var key;
    layui.use(['table', 'form'], function () {
        table = layui.table;
        form = layui.form;
        if(i18n == 'en_US'){
            table.render({
                elem: '#equipmentTable',
                url: contextPath + "/equipment/rendering",
                method: 'post',
                cellMinWidth: 150,// 全局定义常规单元格的最小宽度，layui 2.2.1 新增
                skin: 'line',
                where: {
                    sortName: 'createTime',
                    direction: 'desc'
                },
                cols: [[
                    {field: 'mac', title: 'MAC', sort: true, align: 'center'},
                    {field: 'parkName', title: 'parkName', sort: true, align: 'center'},
                    {field: 'roadWayNum', title: 'roadWayNum', sort: true, align: 'center'},
                    {field: 'location', title: 'location', sort: true, align: 'center'},
                    {
                        field: 'createTime', width: 250, title: 'createTime', sort: true, align: 'center',
                        templet: function (data) {
                            if (data.createTime == null) {
                                return "";
                            } else {
                                return until.formatDateTime(data.createTime);
                            }
                        }
                    },
                    {
                        field: 'online', title: 'onlineOrNot', sort: true, align: 'center',
                        templet: function (data) {
                        	console.log(data.online);
                            if (data.online == true) {
                                return '<span style="color:green;">在线</span>';
                            } else if(data.online == undefined){
                            	return '<span style="color:green;">在线</span>';
                            } else if(data.online == false){
                            	 return '<span style="color: red">离线</span>';
                            }
                        }
                    },
                    {fixed: 'right', title: 'right', toolbar: '#equipmentBarEnglish', align: 'center', width: 150}
                ]],
                response: {
                    countName: 'totalElements', // 规定数据总数的字段名称，默认：count
                    dataName: 'content' // 规定数据列表的字段名称，默认：data
                },
                page: true
            });
        }else{
            table.render({
                elem: '#equipmentTable',
                url: contextPath + "/equipment/rendering",
                method: 'post',
                cellMinWidth: 150,// 全局定义常规单元格的最小宽度，layui 2.2.1 新增
                skin: 'line',
                where: {
                    sortName: 'createTime',
                    direction: 'desc'
                },
                cols: [[
                    {field: 'mac', title: '主控板MAC', sort: true, align: 'center'},
                    {field: 'parkName', title: '绑定停车场', sort: true, align: 'center'},
                    {field: 'roadWayNum', title: '车道数量', sort: true, align: 'center'},
                    {field: 'location', title: '安装位置', sort: true, align: 'center'},
                    {
                        field: 'createTime', width: 250, title: '安装时间', sort: true, align: 'center',
                        templet: function (data) {
                            if (data.createTime == null) {
                                return "";
                            } else {
                                return until.formatDateTime(data.createTime);
                            }
                        }
                    },
                    {
                        field: 'online', title: '是否在线', sort: true, align: 'center',
                        templet: function (data) {
                        	console.log(data.online);
                            if (data.online == true) {
                                return '<span style="color:green;">在线</span>';
                            } else if(data.online == undefined){
                            	return '<span style="color:green;">在线</span>';
                            } else if(data.online == false){
                            	 return '<span style="color: red">离线</span>';
                            }
                        }
                    },
                    {fixed: 'right', title: '操作', toolbar: '#equipmentBar', align: 'center', width: 150}
                ]],
                response: {
                    countName: 'totalElements', // 规定数据总数的字段名称，默认：count
                    dataName: 'content' // 规定数据列表的字段名称，默认：data
                },
                page: true
            });
        }
        
      //切换英文
		if(i18n == 'en_US'){
			$("#macName").empty();
			$("#macName").append('MAC');
			$("#searchEquipment").empty();
			$("#searchEquipment").append('Search');
			$("#mainControllerParking").empty();
			$("#mainControllerParking").append('Parking');
			$("#EquipmentChose").empty();
			$("#EquipmentChose").append('Equipment');
			$("#addRoadway").empty();
			$("#addRoadway").append('AddRoadway');
			$("#deleteRoadway").empty();
			$("#deleteRoadway").append('DeleRoadway');
			$("#choseRoway").empty();
			$("#choseRoway").append('ChoseRoway');
			$("#choseRoway").empty();
			$("#choseRoway").append('Roway');
			$("#RowayName").empty();
			$("#RowayName").append('RowayName');
			$("#RowayNum").empty();
			$("#RowayNum").append('RowayNum');
			$("#RowayLocation").empty();
			$("#RowayLocation").append('Location');
			$("#vidiconIP").empty();
			$("#vidiconIP").append('IP');
			$("#vidiconMac").empty();
			$("#vidiconMac").append('Mac');
			$("#inOut").empty();
			$("#inOut").append('InOut');
			$("#CameraModel").empty();
			$("#CameraModel").append('Type');
			$("#searchMAC").attr("placeholder","MAC");
		}
        
        //失去焦点判断重名
        $(document).off("blur", "#cameraMac").on("blur", "#cameraMac", function () {
            var cameraMac = $("#cameraMac").val();
            var thisName = $(this).val();
            if (cameraMac != "") {
                if ((thisName != oldCameraMacName) && (thisName)) {
                    $.ajax({
                        url: contextPath + "/equipment/checkCameraMac",
                        dataType: 'json',
                        type: 'post',
                        data: {
                            cameraMac: cameraMac
                        },
                        complete: function (XHR, TS) {
                            var returnObj = eval('(' + XHR.responseText + ')');
                            if (returnObj.code != 200) {
                                cheakNameFlag = false;
                                key = $(".layui-unselect.layui-form-radio.layui-form-radioed div").html() + "摄像机mac已存在";
                                layer.msg(key, {anim: 6, time: 2500});
                            }
                            else {
                                cheakNameFlag = true;
                            }
                        }
                    });
                } else {
                    cheakNameFlag = true;
                }
            }
        });

        table.on('sort(equipmentTable)', function (obj) {
            table.reload('equipmentTable', {
                initSort: obj,
                where: {
                    field: obj.field, //排序字段
                    order: obj.type
                    //排序方式
                }
            });
        });


        //监听行工具事件
        table.on('tool(equipmentTable)', function (obj) {
            var data = obj.data;
            var titelDel = "真的删除吗";
            if(i18n == 'en_US'){
            	 titelDel = "Do you really delete it?";
            }
            if (obj.event === 'del') { //执行删除方法
                layer.confirm(titelDel, function (index) {
                    $.ajax({
                        url: contextPath + "/equipment/delete",
                        dataType: 'json',
                        type: 'post',
                        async: false,
                        data: {
                            mainControlId: data.id,
                        },
                        complete: function (XHR, TS) {
                            var returnObj = eval('(' + XHR.responseText + ')');
                            if (returnObj.code != 200) {
                                layer.msg(returnObj.msg, {
                                    icon: 2,
                                    time: 1500
                                });
                            } else {
                                layer.msg(returnObj.msg, {
                                    icon: 1,
                                    time: 1500
                                });
                            }
                            table.reload('equipmentTable');
                        }
                    });
                });
            } else if (obj.event === 'edit') {
                var showContent = $('#mainControllerEdit').html();

                $('#mainControllerEdit').html("");
                layer.open({
                    type: 1,
                    id: 'showLayui',
                    title: '编辑主控板',
                    content: showContent, //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    area: ['800px', '600px'],
                    btn: ['确定', '取消'],
                    btnAlign: 'c',
                    success: function (layero, index) {//打开层之后可执行的回调

                        //获取停车场信息
                        $.ajax({
                            url: contextPath + "/parking/getAllParking",
                            dataType: 'json',
                            type: 'post',
                            async: false,
                            data: {},
                            complete: function (XHR, TS) {
                                var data = eval('(' + XHR.responseText + ')');

                                $("#selMCParking").empty();
                                var addSelHtml = '<option value="">请选择停车场</option>';
                                for (var i = 0; i < data.length; i++) {
                                    addSelHtml += '<option value="' + data[i].id + '">' + data[i].parkName + '</option>';
                                }
                                $("#selMCParking").append(addSelHtml);
                                form.render();
                            }//complete
                        });//ajax

                        if(data.actTo != undefined && data.actTo != ""){
							var actionTypeArr = data.actTo.split(",");
							actionTypeArr.forEach(function (elem, index) {
								$("input:checkbox[name='actionType'][value='"+(elem)+"']").attr('checked',true);
							})
						}
                        
                        //获取这个主控板的车道信息，然后渲染弹窗
                        $.ajax({
                            url: contextPath + "/equipment/getRoadways",
                            dataType: 'json',
                            type: 'post',
                            async: false,
                            data: {
                                mainControlId: data.id,
                            },
                            complete: function (XHR, TS) {
                                var returnObj = eval('(' + XHR.responseText + ')');
                                if (returnObj.code != 200) {
                                    layer.msg(returnObj.msg, {
                                        icon: 2,
                                        time: 1500
                                    });
                                } else {
                                    //在这里渲染弹窗
                                    var data = returnObj.data;
                                    roadwaysData = new Array();
                                    if (data.length != 0) {
                                        $("#selMCParking").val(data[0].parkingId);
                                    } else if (obj.data.parkingId) {
                                        $("#selMCParking").val(obj.data.parkingId);
                                    }
                                    /*车道对象里面的status属性：
                                        add是新增到数据库的车道，
                                        delete是要在数据库中删除的车道，
                                        update是修改数据库中的车道，
                                        discard是丢弃的对象（在内存已经删除的对象，不需要为其修改数据库）
                                    */
                                    for (var i = 0; i < data.length; i++) {
                                        //一个车道数据的对象
                                        var roadwayObject = new Object();
                                        //车道id
                                        roadwayObject.id = data[i].id;
                                        //停车场id
                                        roadwayObject.parkingId = data[i].parkingId;
                                        //车道名称
                                        roadwayObject.roadName = data[i].roadName;
                                        //闸机机号
                                        roadwayObject.gateNumber = data[i].gateNumber;
                                        //闸机位置
                                        roadwayObject.address = data[i].address;
                                        //摄像机ip
                                        roadwayObject.cameraIp = data[i].cameraIp;
                                        //摄像机mac
                                        roadwayObject.cameraMac = data[i].cameraMac;
                                        //原始mac
                                        roadwayObject.initCameraMac = data[i].cameraMac;
                                        //出入口标记
                                        roadwayObject.inOutMarker = data[i].inOutMarker;
                                        //摄像机型号
                                        roadwayObject.cameraType = data[i].cameraType;
                                        //车道状态,从数据库里查出来的都是update
                                        roadwayObject.status = 'update';
                                        //给全局车道数据集合赋值
                                        roadwaysData.push(roadwayObject);

                                        //确定这个主控板绑定了几个车道,设置每个车道的id
                                        if (i == 0) {
                                            //渲染第一个车道
                                            renderingRoadway(roadwaysData[0]);
                                            roadways = '<input type="radio" name="roadway" lay-filter="roadway" id = "' + data[i].id + '" value="' + i + '" title="' + data[i].roadName + '" checked>';
                                        } else {
                                            roadways = '<input type="radio" name="roadway" lay-filter="roadway" id = "' + data[i].id + '" value="' + i + '" title="' + data[i].roadName + '" >';
                                        }
                                        //动态添加车道
                                        $("#roadways").append(roadways);

                                    }//for

                                    redioElem = $("#roadways input").first()[0];
                                }
                            }//complete
                        });//ajax

                        layui.use('form', function () {
                            form.render();
                            //绑定事件
                            $(document).off("click", "#addRoadway").on("click", "#addRoadway", function () {
                                addRoadway();
                            });
                            $(document).off("click", "#deleteRoadway").on("click", "#deleteRoadway", function () {
                                layer.confirm('真的删除么', function () {
                                    layer.closeAll('dialog');
                                    deleteRoadway();
                                });
//								table.reload('equipmentTable');
                            });

                        });
                    },//success
                    yes: function (index, layero) {//确定按钮,保存信息
                        var parkingId = $("#selMCParking").val();
                        var msgArr = ["所属停车场不能为空"];  //提示语集合
                        var clsArr = ["#selMCParking"];  //id集合
                        var validateFlag = until.validate(msgArr, clsArr); //非空验证 需要引入工具js文件until
                        if (validateFlag) {
                            layer.msg(validateFlag, {anim: 6, time: 1500});
                            return;
                        }
                        
                        var actionTypeArr = new Array();
						$("input:checkbox[name='actionType']:checked").each(function(i){
							actionTypeArr[i] = $(this).val();
						});
						var actionType = actionTypeArr.join(",");
						if(actionType == ""){
						  	layer.msg("至少选择一个作用类型" , {anim: 6, time: 2500});
						  	return;
                        }
						  
                        //摄像机mac非空验证
                        var idArr = new Array();
                        var cameraArr = new Array();
                        var initCameraArr = new Array();
                        for (var i = 0; i < roadwaysData.length; i++){
                        	idArr.push(roadwaysData[i].id);
                        	cameraArr.push(roadwaysData[i].cameraMac);
                        	initCameraArr.push(roadwaysData[i].initCameraMac);
                        }
                        cameraArr = $.grep(cameraArr, function (x) { return $.trim(x).length > 0; });//去除空值
                        initCameraArr = $.grep(initCameraArr, function (x) { return $.trim(x).length > 0; });//去除空值
                        if(cameraArr.length != idArr.length && initCameraArr.length != idArr.lengt){
                        	layer.msg("摄像机mac不能为空", {anim: 6, time: 1500});
                            return;
                        }
                        

                        //摄像机mac检查通过
                        var camerNum = new Array();
                        for (var i = 0; i < roadwaysData.length; i++) {
                        	if(roadwaysData[i].cameraMac !== roadwaysData[i].initCameraMac){
                            camerNum.push(roadwaysData[i].cameraMac);
                        	}
                        }
                        
                        var RepeatArr = camerNum.sort();
                        var noRepeatArr = new Array();
                        for(var i = 0; i < RepeatArr.length; i++){
                        	if( RepeatArr[i] !== RepeatArr[i+1]){
                        		noRepeatArr.push(RepeatArr[i]);
                        	}
                        }
                        
                        //检查摄像机mac
                        $.ajax({
                            url: contextPath + "/equipment/checkCameraMac",
                            dataType: 'json',
                            type: 'post',
                            async: true,
                            data: {
                                cameraMacs: JSON.stringify(camerNum)
                            },
                            complete: function (XHR, TS) {
                                var returnObj = eval('(' + XHR.responseText + ')');
                                if (returnObj.code != 200) {
                                    layer.msg(key, {
                                    	anim: 6,
                                        time: 1500
                                    });
                                } else {
                                    if (camerNum.length == noRepeatArr.length) {
                                        $.ajax({
                                            url: contextPath + "/equipment/binding",
                                            dataType: 'json',
                                            type: 'post',
                                            async: true,
                                            data: {
                                                mainControlId: data.id,
	                                            roadwaysData: JSON.stringify(roadwaysData),
                                                parkingId: parkingId,
                                                actionType: actionType
                                            },
                                            complete: function (XHR, TS) {
                                                var returnObj = eval('(' + XHR.responseText + ')');
                                                if (returnObj.code != 200) {
                                                    layer.msg(returnObj.msg, {
                                                        icon: 2,
                                                        time: 1500
                                                    });
                                                } else {
                                                    layer.msg(returnObj.msg, {
                                                        icon: 1,
                                                        time: 1500
                                                    });
                                                }
                                                layer.close(index); //如果设定了yes回调，需进行手工关闭
                                                table.reload('equipmentTable');
                                            }
                                        });
                                    }
                                    else {
                                        layer.msg("摄像机mac不能相同", {
                                            anim: 6,
                                            time: 1500
                                        });
                                    }
                                }
                            }
                        });


                    },//确定按钮
                    end: function () { //只要层被销毁了，end都会执行
                        $('#mainControllerEdit').html(showContent);
                    }

                });//layer.open end
            }
        });

        //单选框改变事件
        form.on('radio(roadway)', function (data) {
            redioElem = data.elem;
//			var index = $(obj).attr("title").substring(2);
            var index = data.value;
            //渲染选中的车道
            renderingRoadway(roadwaysData[index]);
            //更新全局数据索引
            currentRoadwayIndex = index;

        });
        form.on('radio(port)', function (data) {
            //更新全局车道数据
            roadwaysData[currentRoadwayIndex].inOutMarker = data.value;
        });
        form.on('radio(model)', function (data) {
            roadwaysData[currentRoadwayIndex].cameraType = data.value;
        });

    });//layui.use end

    //输入框改变事件
    $(document).off("input propertychange", ".listened-input").on("input propertychange", ".listened-input", function () {
        var id = $(this).attr("id");

        switch (id) {
            case 'roadwayName':
                //让车道
                $('input[name="roadway"]:checked').attr("title", $(this).val());
                $("#roadways .layui-form-radio.layui-form-radioed div").html($(this).val());
                roadwaysData[currentRoadwayIndex].roadName = $(this).val();
                break;
            case 'gateNumber':
                roadwaysData[currentRoadwayIndex].gateNumber = $(this).val();
                break;
            case 'address':
                roadwaysData[currentRoadwayIndex].address = $(this).val();
                break;
            case 'cameraIp':
                roadwaysData[currentRoadwayIndex].cameraIp = $(this).val();
                break;
            case 'cameraMac':
                roadwaysData[currentRoadwayIndex].cameraMac = $(this).val();
                break;
        }

    });


    $("#searchEquipment").click(function () {
        table.reload('equipmentTable', {
            where: { //设定异步数据接口的额外参数，任意设
                searchMAC: $("#searchMAC").val()
            },
            page: {
                curr: 1
                //重新从第 1 页开始
            }
        });
    });


    //添加车道
    var roadwayIndex = 1;

    function addRoadway() {
        //新增的车道没有value值，value值为数据库中的车道id
        if (roadwayIndex == 0) {
            roadways = '<input type="radio" name="roadway" lay-filter="roadway" value="' + roadwaysData.length + '" title="新增车道' + roadwayIndex + '" checked>';
        } else {
            roadways = '<input type="radio" name="roadway" lay-filter="roadway" value="' + roadwaysData.length + '" title="新增车道' + roadwayIndex + '" >';
        }
        //给全局roadwaysData添加一个车道对象
        var object = new Object();
        object.roadName = '新增车道' + roadwayIndex;
        object.status = 'add';
        roadwaysData.push(object);
        roadwayIndex++;
        $("#roadways").append(roadways);
        form.render('radio');
    }

    //删除车道
    function deleteRoadway() {
        $(redioElem).next().remove();
        $(redioElem).remove();
        //在全局roadwaysData数组中删除对应的车道对象
        if (roadwaysData[currentRoadwayIndex].id == undefined ||
            roadwaysData[currentRoadwayIndex].status == null ||
            roadwaysData[currentRoadwayIndex].status == '') {
            roadwaysData[currentRoadwayIndex].status = 'discard';
        } else {
            roadwaysData[currentRoadwayIndex].status = 'delete';
        }

        form.render('radio');
    }

    //渲染车道  data：一个车道的数据
    function renderingRoadway(data) {
        if (data == '' || data == undefined || data == null) {
            return;
        }

        //展示对应车道的信息
        //单选框
        if (data.inOutMarker == undefined) {
            $("input[name='port']").removeAttr("checked");
        } else {
            $("input[name='port'][value='" + data.inOutMarker + "']").prop("checked", true);
        }
        if (data.cameraType == undefined) {
            $("input[name='cameraModel']").removeAttr("checked");
        } else {
            $("input[name='cameraModel'][value='" + data.cameraType + "']").prop("checked", true);
        }
        //输入框
        //车道名称
        $("#roadwayName").val(data.roadName);
        //闸机机号
        $("#gateNumber").val(data.gateNumber);
        //闸机位置
        $("#address").val(data.address);
        //摄像机ip
        $("#cameraIp").val(data.cameraIp);
        //摄像机mac
        $("#cameraMac").val(data.cameraMac);
        oldCameraMacName = data.initCameraMac;
        form.render();
    }

});