/**
 * Load页面的方法
 * @param boxId load进去的divId
 * @param url  load的Url
 * @param paramJson
 * @returns
 */
function LoadPage(boxId, url, paramJson) {
	$(".layui-table-tips").remove();
	var returnFlag = 0;
	$.ajax({
		url: contextPath + "/setLinkUrl",
		dataType: 'json',// 数据类型
		type: "POST",
		data: { linkUrl:url },
		complete: function (XHR, TS) {
			if (XHR.responseText != '') {
				location.href = contextPath + "/login";  //解决登录信息过期，load登录页到框架中
				returnFlag = 1 ;
			}
		}
	});
	if(returnFlag == 1){
		return false;
	}
	$(".load-tab-box").html("");
	$("#" + boxId).load(url, paramJson||{},function(data){
    	if(isJSON(data)||Object.prototype.toString.call(data)  === '[object Object]'){
    		var obj = $.parseJSON(data);
    		if(!obj.success){
    			location.href = contextPath + "/login";
    		}
    	}
 	});
}

function isJSON(str) {
    if (typeof str == 'string') {
        try {
            var obj=JSON.parse(str);
            if(typeof obj == 'object' && obj ){
                return true;
            }else{
                return false;
            }

        } catch(e) {
            //console.log('error：'+str+'!!!'+e);  //因一直console页面出来，故注释    --leif    2018/06/25
            return false;
        }
    }
    console.log('It is not a string!')
}
