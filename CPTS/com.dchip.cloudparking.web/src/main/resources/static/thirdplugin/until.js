/**
 * 
 */
define(["jquery"],function($){
	 return {
        isVehicleNumber:function(vehicleNumber){
            var result = false;
            if (vehicleNumber.length == 7){
                var express = /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$/;
                result = express.test(vehicleNumber);
            }
            return result;
        },
	 	getBankNameByNum: function(num){
            var json = {
                "SRCB": "深圳农村商业银行",
                "BGB": "广西北部湾银行",
                "SHRCB": "上海农村商业银行",
                "BJBANK": "北京银行",
                "WHCCB": "威海市商业银行",
                "BOZK": "周口银行",
                "KORLABANK": "库尔勒市商业银行",
                "SPABANK": "平安银行",
                "SDEB": "顺德农商银行",
                "HURCB": "湖北省农村信用社",
                "WRCB": "无锡农村商业银行",
                "BOCY": "朝阳银行",
                "CZBANK": "浙商银行",
                "HDBANK": "邯郸银行",
                "BOC": "中国银行",
                "BOD": "东莞银行",
                "CCB": "中国建设银行",
                "ZYCBANK": "遵义市商业银行",
                "SXCB": "绍兴银行",
                "GZRCU": "贵州省农村信用社",
                "ZJKCCB": "张家口市商业银行",
                "BOJZ": "锦州银行",
                "BOP": "平顶山银行",
                "HKB": "汉口银行",
                "SPDB": "上海浦东发展银行",
                "NXRCU": "宁夏黄河农村商业银行",
                "NYNB": "广东南粤银行",
                "GRCB": "广州农商银行",
                "BOSZ": "苏州银行",
                "HZCB": "杭州银行",
                "HSBK": "衡水银行",
                "HBC": "湖北银行",
                "JXBANK": "嘉兴银行",
                "HRXJB": "华融湘江银行",
                "BODD": "丹东银行",
                "AYCB": "安阳银行",
                "EGBANK": "恒丰银行",
                "CDB": "国家开发银行",
                "TCRCB": "江苏太仓农村商业银行",
                "NJCB": "南京银行",
                "ZZBANK": "郑州银行",
                "DYCB": "德阳商业银行",
                "YBCCB": "宜宾市商业银行",
                "SCRCU": "四川省农村信用",
                "KLB": "昆仑银行",
                "LSBANK": "莱商银行",
                "YDRCB": "尧都农商行",
                "CCQTGB": "重庆三峡银行",
                "FDB": "富滇银行",
                "JSRCU": "江苏省农村信用联合社",
                "JNBANK": "济宁银行",
                "CMB": "招商银行",
                "JINCHB": "晋城银行JCBANK",
                "FXCB": "阜新银行",
                "WHRCB": "武汉农村商业银行",
                "HBYCBANK": "湖北银行宜昌分行",
                "TZCB": "台州银行",
                "TACCB": "泰安市商业银行",
                "XCYH": "许昌银行",
                "CEB": "中国光大银行",
                "NXBANK": "宁夏银行",
                "HSBANK": "徽商银行",
                "JJBANK": "九江银行",
                "NHQS": "农信银清算中心",
                "MTBANK": "浙江民泰商业银行",
                "LANGFB": "廊坊银行",
                "ASCB": "鞍山银行",
                "KSRB": "昆山农村商业银行",
                "YXCCB": "玉溪市商业银行",
                "DLB": "大连银行",
                "DRCBCL": "东莞农村商业银行",
                "GCB": "广州银行",
                "NBBANK": "宁波银行",
                "BOYK": "营口银行",
                "SXRCCU": "陕西信合",
                "GLBANK": "桂林银行",
                "BOQH": "青海银行",
                "CDRCB": "成都农商银行",
                "QDCCB": "青岛银行",
                "HKBEA": "东亚银行",
                "HBHSBANK": "湖北银行黄石分行",
                "WZCB": "温州银行",
                "TRCB": "天津农商银行",
                "QLBANK": "齐鲁银行",
                "GDRCC": "广东省农村信用社联合社",
                "ZJTLCB": "浙江泰隆商业银行",
                "GZB": "赣州银行",
                "GYCB": "贵阳市商业银行",
                "CQBANK": "重庆银行",
                "DAQINGB": "龙江银行",
                "CGNB": "南充市商业银行",
                "SCCB": "三门峡银行",
                "CSRCB": "常熟农村商业银行",
                "SHBANK": "上海银行",
                "JLBANK": "吉林银行",
                "CZRCB": "常州农村信用联社",
                "BANKWF": "潍坊银行",
                "ZRCBANK": "张家港农村商业银行",
                "FJHXBC": "福建海峡银行",
                "ZJNX": "浙江省农村信用社联合社",
                "LZYH": "兰州银行",
                "JSB": "晋商银行",
                "BOHAIB": "渤海银行",
                "CZCB": "浙江稠州商业银行",
                "YQCCB": "阳泉银行",
                "SJBANK": "盛京银行",
                "XABANK": "西安银行",
                "BSB": "包商银行",
                "JSBANK": "江苏银行",
                "FSCB": "抚顺银行",
                "HNRCU": "河南省农村信用",
                "COMM": "交通银行",
                "XTB": "邢台银行",
                "CITIC": "中信银行",
                "HXBANK": "华夏银行",
                "HNRCC": "湖南省农村信用社",
                "DYCCB": "东营市商业银行",
                "ORBANK": "鄂尔多斯银行",
                "BJRCB": "北京农村商业银行",
                "XYBANK": "信阳银行",
                "ZGCCB": "自贡市商业银行",
                "CDCB": "成都银行",
                "HANABANK": "韩亚银行",
                "CMBC": "中国民生银行",
                "LYBANK": "洛阳银行",
                "GDB": "广东发展银行",
                "ZBCB": "齐商银行",
                "CBKF": "开封市商业银行",
                "H3CB": "内蒙古银行",
                "CIB": "兴业银行",
                "CRCBANK": "重庆农村商业银行",
                "SZSBK": "石嘴山银行",
                "DZBANK": "德州银行",
                "SRBANK": "上饶银行",
                "LSCCB": "乐山市商业银行",
                "JXRCU": "江西省农村信用",
                "ICBC": "中国工商银行",
                "JZBANK": "晋中市商业银行",
                "HZCCB": "湖州市商业银行",
                "NHB": "南海农村信用联社",
                "XXBANK": "新乡银行",
                "JRCB": "江苏江阴农村商业银行",
                "YNRCC": "云南省农村信用社",
                "ABC": "中国农业银行",
                "GXRCU": "广西省农村信用",
                "PSBC": "中国邮政储蓄银行",
                "BZMD": "驻马店银行",
                "ARCU": "安徽省农村信用社",
                "GSRCU": "甘肃省农村信用",
                "LYCB": "辽阳市商业银行",
                "JLRCU": "吉林农信",
                "URMQCCB": "乌鲁木齐市商业银行",
                "XLBANK": "中山小榄村镇银行",
                "CSCB": "长沙银行",
                "JHBANK": "金华银行",
                "BHB": "河北银行",
                "NBYZ": "鄞州银行",
                "LSBC": "临商银行",
                "BOCD": "承德银行",
                "SDRCU": "山东农信",
                "NCB": "南昌银行",
                "TCCB": "天津银行",
                "WJRCB": "吴江农商银行",
                "CBBQS": "城市商业银行资金清算中心",
                "HBRCU": "河北省农村信用社"
            };
            return json[num];
		},

         dateAdd:function(interval, number, date){
             switch (interval) {
                 case "y ": {
                     date.setFullYear(date.getFullYear() + number);
                     return date;
                     break;
                 }
                 case "q ": {
                     date.setMonth(date.getMonth() + number * 3);
                     return date;
                     break;
                 }
                 case "M ": {
                     date.setMonth(date.getMonth() + number);
                     return date;
                     break;
                 }
                 case "w ": {
                     date.setDate(date.getDate() + number * 7);
                     return date;
                     break;
                 }
                 case "d ": {
                     date.setDate(date.getDate() + number);
                     return date;
                     break;
                 }
                 case "h ": {
                     date.setHours(date.getHours() + number);
                     return date;
                     break;
                 }
                 case "m ": {
                     date.setMinutes(date.getMinutes() + number);
                     return date;
                     break;
                 }
                 case "s ": {
                     date.setSeconds(date.getSeconds() + number);
                     return date;
                     break;
                 }
                 default: {
                     date.setDate(d.getDate() + number);
                     return date;
                     break;
                 }
             }
         },

		 isPoneAvailable:function(str){
			 var myreg=/^[1][3,4,5,7,8][0-9]{9}$/;  
	          if (!myreg.test(str)) {  
	              return false;  
	          } else {  
	              return true;  
	          }  
		 },

         /**
          * 时间戳转成yyyy年MM月dd日 HH时mm分ss秒
          * @param obj
          * @returns  yyyy年MM月dd日 HH时mm分ss秒
          */
         formatDateTimeString: function(inputTime){
             var date = new Date(inputTime);
             var y = date.getFullYear();
             var m = date.getMonth() + 1;
             m = m < 10 ? ('0' + m) : m;
             var d = date.getDate();
             d = d < 10 ? ('0' + d) : d;
             var h = date.getHours();
             h = h < 10 ? ('0' + h) : h;
             var minute = date.getMinutes();
             var second = date.getSeconds();
             minute = minute < 10 ? ('0' + minute) : minute;
             second = second < 10 ? ('0' + second) : second;
             return y + '年' + m + '月' + d+'日 '+h+'时'+minute+'分'+second+'秒';
         },

         /**
          * 时间戳转成yyyy-MM-dd
          * @param obj
          * @returns  yyyy-MM-dd
          */
         formatDate: function(inputTime){
             var date = new Date(inputTime);
             var y = date.getFullYear();
             var m = date.getMonth() + 1;
             m = m < 10 ? ('0' + m) : m;
             var d = date.getDate();
             d = d < 10 ? ('0' + d) : d;
             return y + '-' + m + '-' + d;
         },
		 
	 	/**
	 	 * 时间戳转成yyyy-MM-dd HH-mm-ss
	 	 * @param obj
	 	 * @returns  yyyy-MM-dd HH-mm-ss
	 	 */
	 	formatDateTime: function(inputTime){
	 		var date = new Date(inputTime);  
	 	    var y = date.getFullYear();    
	 	    var m = date.getMonth() + 1;    
	 	    m = m < 10 ? ('0' + m) : m;    
	 	    var d = date.getDate();    
	 	    d = d < 10 ? ('0' + d) : d;    
	 	    var h = date.getHours();  
	 	    h = h < 10 ? ('0' + h) : h;  
	 	    var minute = date.getMinutes();  
	 	    var second = date.getSeconds();  
	 	    minute = minute < 10 ? ('0' + minute) : minute;    
	 	    second = second < 10 ? ('0' + second) : second;   
	 	    return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;   
	 	},
	 	
	 	/**
	 	 * 时间戳转成 HH-mm-ss
	 	 * @param obj
	 	 * @returns HH-mm-ss
	 	 */
	 	formatHourTime: function(inputTime){
	 		var str = inputTime;
	 		var st = str.substring(9);
	 		if(st == "下午"){
	 			var hour = str.substring(0,2)*1 + 12;
	 			st = hour + str.substring(2,8);
	 		}else{
	 			st = str.substring(0,8);
	 		}
	 	    return st;
	 	},
	 	
	 	/**
	 	 * 分钟数转成时分
	 	 * @param minute  分钟数
	 	 * @returns  XX小时XX分
	 	 */
	 	minuteFormat: function(minute){
	 		var time='';
	 		var d=0;
	 		var m=0;
	 		var h=0;
	 		d=Math.floor(minute/(60*24));
	 		h=Math.floor(minute/60);
	 		h=h-d*24;
	 		m=minute%60;
	 		if(d!=0){
	 			time=d+"天";
	 		}
	 		time+=h+'小时'+m+'分';
	 		return time;
	 	},
	 	
	 	/**
	 	 * 计算相差多少天多少小时多少分钟多少秒
	 	 * @param startDate timestamp
	 	 * @param endDate date
	 	 * @returns
	 	 */
	 	diffTime:function(startDate,endDate){	 	
	 		var start=new Date(startDate);
	 		var end=new Date(endDate);
	 		var diff=end.getTime() - start.getTime();//时间差的毫秒数  
	 		  
	 	    //计算出相差天数  
	 	    var days=Math.floor(diff/(24*3600*1000));  
	 	       
	 	    //计算出小时数  
	 	    var leave1=diff%(24*3600*1000);    //计算天数后剩余的毫秒数  
	 	    var hours=Math.floor(leave1/(3600*1000));  
	 	    //计算相差分钟数  
	 	    var leave2=leave1%(3600*1000);        //计算小时数后剩余的毫秒数  
	 	    var minutes=Math.floor(leave2/(60*1000));  
	 	       
	 	    //计算相差秒数  
	 	    var leave3=leave2%(60*1000);      //计算分钟数后剩余的毫秒数  
	 	    var seconds=Math.round(leave3/1000);  
	 	      
//	 	    var returnStr = seconds + "秒";
	 	    var returnStr = ""; 
	 	    if(minutes>0) {  
	 	        returnStr = minutes + "分" + returnStr;  
	 	    }  
	 	    if(hours>0) {  
	 	        returnStr = hours + "小时" + returnStr;  
	 	    }  
	 	    if(days>0) {  
	 	        returnStr = days + "天" + returnStr;  
	 	    }  
	 	    return returnStr;  
	 	},
	 	
	 	/**
	 	 * 时间戳转成yyyy-MM-dd HH-mm
	 	 * @param obj
	 	 * @returns  yyyy-MM-dd HH-mm
	 	 */
	 	formatDate: function (inputTime) {
	 		var date = new Date(inputTime);  
	 	    var y = date.getFullYear();    
	 	    var m = date.getMonth() + 1;    
	 	    m = m < 10 ? ('0' + m) : m;    
	 	    var d = date.getDate();    
	 	    d = d < 10 ? ('0' + d) : d;    
	 	    var h = date.getHours();  
	 	    h = h < 10 ? ('0' + h) : h;  
	 	    var minute = date.getMinutes();  
	 	    minute = minute < 10 ? ('0' + minute) : minute;   
	 	    return y + '-' + m + '-' + d + ' ' + h + ':' + minute;  
		} ,
		
		/**
		 * 非空验证，mymsg为提示信息数组,clsArr为验证的类名数组
		 */
		validate: function(msgArr, clsArr){
			var msg = [];
		    var index = 0;
		    for (var i = 0; i < clsArr.length; i++) {
		        if ($(clsArr[i]).length == 0) { continue; }
		        var val = $(clsArr[i]).val();
		        if(typeof val == 'string'){
                    val = val.trim();
                }
		        if (!val) {
		            msg = msgArr[i];
		            $(clsArr[i]).focus();
		            break;
		        }
		    }
		    if (msg != "") {
		        return msg;
		    } else {
		        return false;
		    }
		},
		
	 	/**
	 	 * ms:毫秒
	 	 */
	 	sleep:function(ms){
	 		for(var t = Date.now();Date.now() - t <= ms;);
	 	}
		
	 }
});