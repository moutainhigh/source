/**
 * 新的login操作 --leif 
 * --2018/10/19
 * @param $
 * @returns
 */
define(['jquery','Base64','csrf'], function($){	
	
	$(function(){
	    // do something
	    var script=document.createElement("script");
	    script.type="text/javascript";
	    script.src="js/translate.js";
	    document.getElementsByTagName('head')[0].appendChild(script);
	 
	    var value = sessionStorage.getItem("language");
	    document.onreadystatechange = function () {
	        if (document.readyState == 'complete') {
	            if(value==="1"){
	                Microsoft.Translator.Widget.Translate('zh-CHS', 'en', onProgress, onError, onComplete, onRestoreOriginal, 2000);
	            }
	        }
	    }
	    function onProgress(value) {
	    }
	    function onError(error) {
	    }
	    function onComplete() {
	        $("#WidgetFloaterPanels").hide();
	    }
	    function onRestoreOriginal() {
	    }
	});
	
	//判断是否有，有的话，取第一个
	if(window.localStorage){
		if(window.localStorage.getItem("checkFlag") == "true"){
			$("#userName").val(Base64.decode(window.localStorage.getItem(Base64.encode("dchipUserName"))));
			console.log(Base64.encode("dchipUserName"))
			$("#passWord").val(Base64.decode(window.localStorage.getItem(Base64.encode("dchipPWD"))));
			console.log(Base64.encode("dchipPWD"))
			$("#userNameIcon").addClass("ec-user-blue");
			$("#passwordIcon").addClass("ec-locked-blue");
		}else{
			$("#rememberPWD").prop('checked',false);
		}
	}
	
	//登录按钮操作
	$(document).off("click", "#loginSubmit").on("click", "#loginSubmit", function(){
		submitData();
	});
	
	//“记住密码”监听事件
	$("#rememberPWD").change(function() {
		//判断是否勾选
		var checkFlag = $(this).prop('checked');
		if(checkFlag){ //勾选状态下
			if(!window.localStorage){ //浏览器不支持记住密码这个功能，弹出提示信息，清除勾选状态
				$(".custom-layer-box").removeClass("none");
				$(this).prop('checked',false);
				setTimeout(function(){
					$(".custom-layer-box").addClass("none");
				},1500);
			}
		}
	});
	
	$("#change").click(function(){debugger
		translate();
	});
	
	function translate(){debugger
	    var value = sessionStorage.getItem("language");
	    if(value==="1"){
	        sessionStorage.setItem("language", "0");
	    }else{
	        sessionStorage.setItem("language", "1");
	    }
	    window.location.reload();//刷新当前页面.
	}
	
	$("#userName").bind('input propertychange',function(){
		//隐藏错误提示
		OperateLoginError();
		var checkFlag = $("#rememberPWD").prop('checked');
		if(window.localStorage){
			if((window.localStorage.getItem("checkFlag") == "true") && checkFlag){
				if($(this).val() == (Base64.decode(window.localStorage.getItem(Base64.encode("dchipUserName"))))){
					$("#passWord").val(Base64.decode(window.localStorage.getItem(Base64.encode("dchipPWD"))));
				}else{
					$("#passWord").val("");
				}				
			}else{
				$("#passWord").val("");
			}
		}else{
			$("#passWord").val("");
		}
		if($("#passWord").val() != null && $("#passWord").val() !='' && $("#userName").val() != null && $("#userName").val() !='' ){
			$("#loginSubmit").attr("class","login-submit login-submit-blue zoomIn delay10");
		}else{
			$("#loginSubmit").attr("class","login-submit zoomIn delay10");
		}
	});
	
	$("#passWord").bind('input propertychange',function(){
		//隐藏错误提示
		OperateLoginError();
		
		if($("#passWord").val() != null && $("#passWord").val() !='' && $("#userName").val() != null && $("#userName").val() !='' ){
			$("#loginSubmit").attr("class","login-submit login-submit-blue zoomIn delay10");
		}else{
			$("#loginSubmit").attr("class","login-submit zoomIn delay10");
		}
	});
	
	//回车登录
	$("#passWord").on('keyup',function(e){
		if(e.keyCode == 13){
			submitData();
		}
	});
	
	//提交数据，登录
	function submitData(){
		var userName = $("#userName").val();
		var passWord = $("#passWord").val();
		$(".custom-mask-box").removeClass("none");
		if(checkInput(userName,passWord) && $("#showLoginError").hasClass("visibility-hidden")){
			$.ajax({
				  url: contextPath+"/loginIn",
				  dataType:'json',
				  type:'post',
				  data : {
					  userName : userName,
					  pwd: passWord
					},
				  complete: function(XHR, TS){
					  var returnObj = eval('(' + XHR.responseText + ')');
					  if(returnObj.code != 200){
						  OperateLoginError(returnObj.msg);
						  $("#passWord").val("");
						  $(".custom-mask-box").addClass("none");
						  return ;
					  }
					  var checkFlag = $("#rememberPWD").prop('checked');
					  if(checkFlag){//勾选状态下，需要修改或新增
						  localStorage.setItem("checkFlag", "true");
						  localStorage.setItem(Base64.encode("dchipUserName"), Base64.encode(userName));
						  localStorage.setItem(Base64.encode("dchipPWD"), Base64.encode(passWord));
					  }else{
						  if(localStorage.getItem(Base64.encode("dchipUserName"))){
							  localStorage.setItem("checkFlag", "false");
							  localStorage.removeItem(Base64.encode("dchipUserName"));
							  localStorage.removeItem(Base64.encode("dchipPWD"));
						  }
					  }
					  console.log(i18n)
					  if(i18n != null && i18n == "en_US"){
						  location.href = contextPath+"/index?lang=en_US";
					  }else if(i18n != null && i18n == "zh_TW"){
						  location.href = contextPath+"/index?lang=zh_TW";
					  }else{
						  location.href = contextPath+"/index";
					  }
				  }
			});
		}else{
			$(".custom-mask-box").addClass("none")
		}
	}
	
	//检查用户名密码是否为空
	function checkInput(userName, passWord){
		if(!userName){
			OperateLoginError("用户名不能为空");
			$("#userName").focus();
			return false;
		}
		if(!passWord){
			OperateLoginError("密码不能为空");
			$("#passWord").focus();
			return false;
		}
		return true;
	}
	
	//操作错误信息的方法
	function OperateLoginError(errorText){
		if(errorText){
			$("#showLoginError").removeClass("visibility-hidden").addClass("visibility-visible");
			$("#showLoginError").html(errorText);
		}else{
			$("#showLoginError").removeClass("visibility-visible").addClass("visibility-hidden");
		}
	}
	
});