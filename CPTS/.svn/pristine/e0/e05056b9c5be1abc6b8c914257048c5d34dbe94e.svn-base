define(['jquery', 'layui', 'until'], function ($, LAYUI, until) {
    var table;
    var form;
    layui.use(['table', 'form', 'laydate'], function () {
        table = layui.table;
        form = layui.form;
        form.render();
        table.render({
            elem: '#deductionManageTable',
            url: contextPath + "/deductionManage/rendering",
            method: 'post',
            cellMinWidth: 150,//全局定义常规单元格的最小宽度，layui 2.2.1 新增
            skin: 'line',
            where: {
                sortName: 'receiveTime',
                direction: 'desc'
            },
            cols: [[
                {field: 'deductionId', hide: true},//抵扣券id
                {field: 'parkingId', hide: true},//停车id
                {field: 'realName', title: '发券商户', align: 'center', sort: true},
                {field: 'parkName', title: '停车场名称', align: 'center', sort: true, hide: type == 3 ? true : false},
                {field: 'licensePlat', title: '车牌号码', align: 'center', sort: true},
                {field: 'hourNum', title: '抵扣小时数', align: 'center', sort: true},
                {
                    field: 'deductionStatus', title: '使用状态', align: 'center', sort: true, templet: function (data) {
                        if (data.deductionStatus == 0) {
                            return "<span style='color: green;'>未使用</span>";
                        } else if (data.deductionStatus == 1) {
                            return "<span style='color: red;'>已使用</span>";
                        } else if (data.deductionStatus == 2) {
                            return "<span style='color: red;'>已过期</span>";
                        } else {
                            return data.deductionStatus != undefined ? data.deductionStatus : "";
                        }
                    }
                },
                {
                    field: 'receiveTime', title: '领用时间', align: 'center', sort: true, templet: function (data) {
                        if (data.receiveTime == null) {
                            return "";
                        } else {
                            return until.formatDateTime(data.receiveTime);
                        }
                    }
                },
                {
                    field: 'useTime', title: '使用时间', align: 'center', sort: true, templet: function (data) {
                        if (data.useTime == null) {
                            return "";
                        } else {
                            return until.formatDateTime(data.useTime);
                        }
                    }
                },
                {
                    field: 'dueTime', title: '到期时间', align: 'center', sort: true, templet: function (data) {
                        if (data.dueTime == null) {
                            return "";
                        } else {
                            return until.formatDateTime(data.dueTime);
                        }
                    }
                },
                {field: 'userId', title: '用户id', hide: true},
                // { field:'consumptionUrl',title:'消费图片',align: 'center', templet: function(data){
                //        if(data.consumptionUrl == null || data.consumptionUrl == ""){
                //            return "";
                //        }
                //         return '<img style="width:50px;height:50px" src="'+(domainUrl + "/" + data.consumptionUrl)+'"/>';
                //     }
                // },

                {field: 'deductioinCode', title: '抵扣券码', sort: true, align: 'center'}
                // {
                // 	field:'createTime',title:'创建时间',align: 'center',sort:"desc",templet: function(data){
                //         if(data.createTime == null){
                //             return "";
                //         }else{
                //             return until.formatDateTime(data.createTime);
                //         }
                // 	}
                // },

//					{
//                    	fixed: 'right', title: '操作', align: 'center', width:180, templet: function(data){
//                            var button = "";
//                            if (data.deductionStatus == 0) {
//                                button += "<button lay-event='changeStatus' class='layui-btn layui-btn-xs'>使过期</button>";
//                            }
//                            button += "<button lay-event='edit' class='layui-btn layui-btn-xs'>编辑</button>";
//                            button += "<button lay-event='del' class='layui-btn layui-btn-danger layui-btn-xs'>删除</button>";
//                            return button;
//                        }
//                    }
            ]],
            response: {
                countName: 'totalElements', //规定数据总数的字段名称，默认：count
                dataName: 'content' //规定数据列表的字段名称，默认：data
            },
            page: true
        });

        /**
         * 排序方法
         */
        table.on('sort(deductionManageTable)', function (obj) {
            table.reload('deductionManageTable', {
                initSort: obj,
                where: {
                    sortName: obj.field, //排序字段
                    direction: obj.type //排序方式
                }
            });
        });

        /**
         * 监听行工具事件
         */
        table.on('tool(deductionManageTable)', function (obj) {
            var data = obj.data;
            if (obj.event === 'del') { //执行删除方法
                layer.confirm('真的删除么', function (index) {
                    $.ajax({
                        url: contextPath + "/deductionManage/delete",
                        dataType: 'json',
                        type: 'post',
                        async: false,
                        data: {
                            deductionId: data.deductionId
                        },
                        complete: function (XHR, TS) {
                            var returnObj = eval('(' + XHR.responseText + ')');
                            if (returnObj.code != 200) {
                                layer.msg(returnObj.msg, {icon: 2, time: 1500});
                            } else {
                                layer.msg("操作成功", {icon: 1, time: 1500});
                            }
                            table.reload('deductionManageTable');
                        }
                    });
                });
            } else if (obj.event === 'edit') {	//执行编辑方法
                editDeduction("edit", data);
            } else if (obj.event === 'changeStatus') {
                layer.confirm(
                    // '是否'+(function(status){if(status == 1){return "解禁";}else {return "禁用";}})(data.status)+'该抵扣券',
                    '是否让该抵扣券过期',
                    function (index) {
                        $.ajax({
                            url: contextPath + "/deductionManage/changeStatus",
                            dataType: 'json',
                            type: 'post',
                            data: {
                                deductionId: data.deductionId,
                                deductionStatus: data.deductionStatus
                            },
                            complete: function (XHR, TS) {
                                var returnObj = eval('(' + XHR.responseText + ')');
                                if (returnObj.code != 200) {
                                    layer.msg(returnObj.msg, {icon: 2, time: 1500});
                                }
                                layer.close(index);
                                table.reload('deductionManageTable');
                            }
                        });
                    });
            }
        });

        /**
         * 搜索按钮
         */
        $("#search").click(function () {
            reloadData();
        });


        function reloadData() {
            table.reload('deductionManageTable', {
                where: {
                    status: $("#status").val(),
                    licensePlate: $("#licensePlate").val(),
                    realName: $("#realName").val()
                },
                page: {
                    curr: 1 //重新从第1页开始
                }
            });
        }


        /**
         * 导出excel
         */
        $("#export").click(function () {
            window.location.href = contextPath + "/deductionManage/export?status=" + $("#status").val()
                + "&licensePlate=" + $("#licensePlate").val();
        });


        //编辑或添加抵扣券的具体操作
        function editDeduction(type, data) {
            var showTitle = '编辑用户抵扣券';
            var responseUrl = "/deductionManage/edit";
            if (type == "add") {
                showTitle = '添加用户抵扣券';
                responseUrl = "/deductionManage/add";
            } else {
                $("#generateCountDiv").css("display", "none");
            }
            var showContent = $('#showDeductionContent').html();
            $('#showDeductionContent').html("");

            $("#qiniuCloudRelativeUrl").val("");
            layui.use(['upload'], function () {
                var upload = layui.upload;
                //普通图片上传
                var uploadInst = upload.render({
                    elem: '#upload_btn',
                    url: contextPath + '/deductionManage/upload',
                    accept: 'file',
                    before: function (obj) {
                        //预读本地文件示例，不支持ie8
                        obj.preview(function (index, file, result) {
                            $('#consumptionUrl').attr('src', result); //图片链接（base64）
                        });
                    }
                    , done: function (res) {
                        //上传成功
                        if (res.code === 200) {
                            $("#qiniuCloudRelativeUrl").val(res.data);
                            // return layer.msg('文件上传成功!');
                            return;
                        }
                        /*
                        //如果上传失败
                        if (res.code === 2) {
                            return layer.msg('不支持该上传类型');
                        }
                        if (res.code === 3) {
                            return layer.msg('文件为空');
                        }else{
                            return layer.msg('文件上传异常');
                        }
                        */
                    }
                    , error: function () {
                        //演示失败状态，并实现重传
                        var demoText = $('#demoText');
                        demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
                        demoText.find('.demo-reload').on('click', function () {
                            uploadInst.upload();
                        });
                    }
                });
            });

            layer.open({
                type: 1,
                id: 'showLayui',
                title: showTitle,
                content: showContent, //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                area: ['500px', '500px'],
                btn: ['确定', '取消'],
                btnAlign: 'c',

                success: function (layero, index) {
                    layui.use(['form', 'layer', 'laydate'], function () {
                        var form = layui.form;
                        var layer = layui.layer;
                        var laydate = layui.laydate;

                        //时间插件
                        laydate.render({
                            elem: '#dueTime',
                            type: 'datetime'
                        });
//                                $("#dueTime").val(until.formatDateTime(new Date()));
                        $("#consumptionUrl").attr('src', "");//打开的时候清空src

                        if (type == "edit") {//编辑框赋值
                            form.val('commitFormFilter',//给表单里面的字段赋值  通过name属性
                                {
                                    "deductionId": data.deductionId,
                                    // "parkingId" : data.parkingId,
                                    "hourNum": data.hourNum,
                                    "dueTime": until.formatDate(data.dueTime),
                                    "consumptionUrl": data.consumptionUrl,
                                    // "userId" : data.userId,
                                    // "useTime" : until.formatDateTime(data.useTime),
                                    // "receiveTime" : until.formatDateTime(data.receiveTime),
                                    // "deductioinCode" : data.deductioinCode,
                                    // "status" : data.status,
                                }
                            );
                            $("#consumptionUrl").attr('src', (domainUrl + "/" + data.consumptionUrl));
                        }
                        form.render();  //重新渲染表单
                    });
                },

                yes: function (index, layero) { //确定按钮
                    var msgArr = ["抵扣券名称不能为空", "抵扣小时数不能为空"]; // 提示语集合
                    var clsArr = ["#name", "#hourNum"]; // id集合
                    var validateFlag = until.validate(msgArr, clsArr); //非空验证 需要引入工具js文件until

                    if (validateFlag) {
                        layer.msg(validateFlag, {anim: 6, time: 2500});
                        return;
                    }
                    ;

                    var voDataValue =
                        (type == 'add') ?
                            {
                                name: $("#name").val(),
                                hourNum: $("#hourNum").val(),
                                dueTime: until.formatDateTime($("#dueTime").val()),
                                status: $('input:radio[name="status"]:checked').val(),
                                qiniuCloudRelativeUrl: $("#qiniuCloudRelativeUrl").val()
                            } :
                            {
                                deductionId: data.deductionId,
                                hourNum: $("#hourNum").val(),
                                dueTime: until.formatDateTime($("#dueTime").val()),
                                status: $('input:radio[name="status"]:checked').val()
                            };

                    if (voDataValue.hourNum < 0) {
                        layer.msg("抵扣小时数不能小于0");
                        return;
                    } else if (voDataValue.hourNum > 72) {
                        layer.msg("抵扣小时数不能高于72");
                        return;
                    }
                    if ((type == 'add')) {
                        if (voDataValue.generateCount < 0) {
                            layer.msg("抵扣券数不能小于0");
                            return;
                        }
                        // if(voDataValue.qiniuCloudRelativeUrl == undefined || voDataValue.qiniuCloudRelativeUrl == ""){
                        // 	layer.msg("消费图片不能为空");
                        //     return;
                        // }
                    }
                    $.ajax({
                        url: contextPath + responseUrl,
                        dataType: 'json',
                        type: 'post',
                        async: false,
                        data: {
                            voData: JSON.stringify(voDataValue)
                        },
                        complete: function (XHR, TS) {
                            var returnObj = eval('(' + XHR.responseText + ')');
                            if (returnObj.code != 200) {
                                layer.msg(returnObj.msg, {icon: 2, time: 1500});
                            } else {
                                layer.msg("操作成功", {icon: 1, time: 1500});
                            }
                            layer.close(index);
                            table.reload('deductionManageTable');
                        }
                    });
                },

                end: function () { //只要层被销毁了，end都会执行
                    $('#showDeductionContent').html(showContent);
                }

            });
        } //编辑或添加抵扣券的具体操作 end
    });

});