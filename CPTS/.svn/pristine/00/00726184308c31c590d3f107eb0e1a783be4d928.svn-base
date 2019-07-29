define(['jquery', 'layui', 'until'], function ($, LAYUI, until) {
    var table;
    var form;
    layui.use(['table', 'form', 'laydate'], function () {
        table = layui.table;
        form = layui.form;
        if(i18n == 'en_US'){
        	table.render({
                elem: '#parkingLotModelTable',
                url: contextPath + "/parkingLot/rendering",
                method: 'post',
                cellMinWidth: 150,//全局定义常规单元格的最小宽度，layui 2.2.1 新增
                skin: 'line',
                where: {
                    sortName: 'id',
                    direction: 'desc'
                },
                cols: [[
                    {field: 'mainId', title: 'mainId', align: 'center'},
                    {field: 'uid', title: 'uid', align: 'center'},
                    {field: 'version', title: 'version', align: 'center'},
                    {field: 'online', title: 'online', align: 'center'},
                    {field: 'initState', title: 'initState', align: 'center'},
                    {field: 'smartCustom', title: 'smartCustom', align: 'center'},
                    {field: 'areaId', title: 'areaId', align: 'center'},
                    {field: 'upHeight', title: 'upHeight', align: 'center'},
                    {field: 'downHeight', title: 'downHeight', align: 'center'},
                    {field: 'number', title: 'number', align: 'center'},
                    {field: 'status', title: 'status', align: 'center'},
                    {field: 'state', title: 'state', align: 'center'}
                ]],
                response: {
                    countName: 'totalElements', //规定数据总数的字段名称，默认：count
                    dataName: 'content' //规定数据列表的字段名称，默认：data
                },
                page: true
            });
        }else{
        	table.render({
                elem: '#parkingLotModelTable',
                url: contextPath + "/parkingLot/rendering",
                method: 'post',
                cellMinWidth: 150,//全局定义常规单元格的最小宽度，layui 2.2.1 新增
                skin: 'line',
                where: {
                    sortName: 'id',
                    direction: 'desc'
                },
                cols: [[
                    {field: 'mainId', title: '主控板id', align: 'center'},
                    {field: 'uid', title: '主控板id', align: 'center'},
                    {field: 'version', title: '版本号', align: 'center'},
                    {field: 'online', title: '在线状态', align: 'center'},
                    {field: 'initState', title: '出始状态', align: 'center'},
                    {field: 'smartCustom', title: '灵敏度设置', align: 'center'},
                    {field: 'areaId', title: '区域id', align: 'center'},
                    {field: 'upHeight', title: '上行高度', align: 'center'},
                    {field: 'downHeight', title: '下行高度', align: 'center'},
                    {field: 'number', title: '车位编号', align: 'center'},
                    {field: 'status', title: '隐藏/开放', align: 'center'},
                    {field: 'state', title: '有无车', align: 'center'}
                ]],
                response: {
                    countName: 'totalElements', //规定数据总数的字段名称，默认：count
                    dataName: 'content' //规定数据列表的字段名称，默认：data
                },
                page: true
            });
        }


        /**
         * 添加抵扣券按钮点击事件
         */
        $("#addParkingLotModel").click(function () {

        });

    });

});