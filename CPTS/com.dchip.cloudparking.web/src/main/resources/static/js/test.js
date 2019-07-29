define(['jquery', 'layui'], function($){
	//先引用，并初始化
	layui.use(['layer','form'], function(){
		var layer = layui.layer; //layui初始化，初始化之后才可以调用
		var form = layui.form;
	});
	
	//普通消息层 就只是修改msg里面内容
	$("#showMsg").click(function(){
		layer.msg("我是提示层");
	});
	
	//消息层 
	$("#showSuccessMsg").click(function(){
		layer.msg("操作成功", {icon: 1});
	});
	
	//消息层 
	$("#showErrorMsg").click(function(){
		layer.msg("操作失败", {icon: 2});
	});
	
	//提示框
	$("#showAlert").click(function(){
		layer.alert('有了回调', function(index){
			  //关闭以后，回调执行的方法
			layer.close(index);
		});  
	});
	
	//询问框
	$("#showComfirm").click(function(){
		layer.confirm('是否删除', {icon: 3, title:'标题'}, function(index){
			  //do something
			  
			layer.close(index);
		}); 
	});
	
	//自定义弹出框
	$("#showCustomContent").click(function(){
		var showContent = $('#showUserContent').html();
		$('#showUserContent').html("");
		layer.open({
			type: 1,
			id: 'showLayui',
			title: '标题',
			content: showContent, //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
			area: '800px',
			zIndex: 19931014,
			btn: ['确定', '取消'],
			btnAlign: 'c',
			success: function(layero, index){//打开层之后可执行的回调
				
				layui.use('form', function(){
					var form = layui.form;
					form.render();
				});
			},
			yes: function(index, layero){ //确定按钮
			    //do something
			    layer.close(index); //如果设定了yes回调，需进行手工关闭
			},
			end: function(){ //只要层被销毁了，end都会执行
				$('#showUserContent').html(showContent);
			}
			
		});
	});
	
});