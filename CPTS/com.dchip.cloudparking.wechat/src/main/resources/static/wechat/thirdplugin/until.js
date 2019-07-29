/**
 * 
 */
define(function(){
	 return {
		 isPoneAvailable:function(str){
			 var myreg=/^[1][3,4,5,7,8][0-9]{9}$/;  
	          if (!myreg.test(str)) {  
	              return false;  
	          } else {  
	              return true;  
	          }  
		 }
	 	/**
	 	 * 时间戳转成yyyy-MM-dd HH-mm-ss
	 	 * @param obj
	 	 * @returns  yyyy-MM-dd HH-mm-ss
	 	 */
	 	,formatDateTime:function(inputTime){
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
	 	}
	 	/**
	 	 * 计算相差多少天多少小时多少分钟多少秒
	 	 * @param startDate timestamp
	 	 * @param endDate date
	 	 * @returns
	 	 */
	 	,diffTime:function(startDate,endDate){
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
	 	}
	 	/**
	 	 * 时间戳转成yyyy-MM-dd HH-mm
	 	 * @param obj
	 	 * @returns  yyyy-MM-dd HH-mm
	 	 */
	 	,formatDate: function (inputTime) {
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
		}
		/**
	 	 * ms:毫秒
	 	 */
	 	,sleep:function(ms){
	 		for(var t = Date.now();Date.now() - t <= ms;);
	 	}
	 }
});