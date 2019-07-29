// jQuery zepto vue angular 等库皆有 progress 的实现 以jQuery为例：
define(['jquery', 'layui','sparkMd5'], function($,sparkMd555){
    var $key = $('#key');  // file name    eg: the file is image.jpg,but $key='a.jpg', you will upload the file named 'a.jpg'
    var $userfile = $('#userfile');  // the file you selected

    // upload info
    var $selectedFile = $('.selected-file');
    var $progress = $(".progress");
    var $progressLabel = $('.progress-label');

    //限制上传文件的类型
    $(document).off("change","#userfile").on("change","#userfile",function(){
    	var fileVal = $("#userfile").val();
    	console.log(fileVal)
    	//var indexTar = fileVal.indexOf(".tar.bz2");
    	var index = fileVal.indexOf(".apk");
    	if(index == -1){
    		index = fileVal.indexOf(".tar.bz2");
    	}
    	fileVal = fileVal.substring(index);
    	console.log(fileVal)
    	if (fileVal != ".apk" && fileVal != ".tar.bz2"){
    		layer.msg("只允许上传后缀名为.apk或.tar.bz2的文件！",{icon: 2, time: 3000});
			return;
    	}
        var selectedFile = $userfile.val();
        var file = this.files[0];
        var md5 ='';
        var finalFileName = "";//最后上传的名称
        if (file) {
            // randomly generate the final file name 随机生成最终文件名
        	//var ramdomName = Math.random().toString(36).substr(2) + fileVal;
            var ramdomName = Math.random().toString(36).substr(2) + fileVal.match(/\.?[^.\/]+$/);
            finalFileName = ramdomName;
            $("#address").val(file.name);
        } else {
            return false;
        }
        var f = new FormData(document.getElementById("Form"));
        f.append("token",token);
        f.append("key",finalFileName);
        f.append("file",file);
        $.ajax({
            url: bucketZone,
            type: 'POST',
            data: f,
            processData: false,
            contentType: false,
            xhr: function(){
                myXhr = $.ajaxSettings.xhr();  
                if(myXhr.upload){
                    myXhr.upload.addEventListener('progress',function(e) {
                    	$("#progressFlag").show();
                        if (e.lengthComputable) {
                            var percent = e.loaded/e.total*100;
                            $progress.html('<div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: '
                            		+ percent.toFixed(2) +'%;"></div>');
                            $progressLabel.html(percent.toFixed(2)+'% 完成');
                            $("#prog").html(percent.toFixed(2)+'%')
                        }
                    }, false);
                }
                return myXhr;
            },
            success: function(res) {
                console.log("成功：" + JSON.stringify(res));
                $("#submitAddress").val(res.key);
            },
            error: function(res) {  
                console.log("失败:" +  JSON.stringify(res));
                alert("上传失败:" +  JSON.stringify(res));
            }
        });
        return false;
    });
    
    
    function get_filemd5sum(ofile) {
        var file = ofile;
        var tmp_md5;
        var blobSlice = File.prototype.slice || File.prototype.mozSlice || File.prototype.webkitSlice,
            // file = this.files[0],
            chunkSize = 8097152, // Read in chunks of 2MB
            chunks = Math.ceil(file.size / chunkSize),
            currentChunk = 0,
            spark = new ArrayBuffer(),
            fileReader = new FileReader();

        fileReader.onload = function(e) {
            // console.log('read chunk nr', currentChunk + 1, 'of', chunks);
            spark.append(e.target.result); // Append array buffer
            currentChunk++;
            var md5_progress = Math.floor((currentChunk / chunks) * 100);

            console.log(file.name + "  正在处理，请稍等," + "已完成" + md5_progress + "%");
            var handler_info = document.getElementById("handler_info");
            var progressbar = document.getElementsByClassName("progressbar")[0];
            handler_info.value=file.name + "  正在处理，请稍等," + "已完成" + md5_progress + "%"
            progressbar.value =md5_progress;
            if (currentChunk < chunks) {
                loadNext();
            } else {
                tmp_md5 = spark.end();
                console.log(tmp_md5)
                handler_info.value = tmp_md5;
            }
        };

        fileReader.onerror = function() {
            console.warn('oops, something went wrong.');
        };

        function loadNext() {
            var start = currentChunk * chunkSize,
                end = ((start + chunkSize) >= file.size) ? file.size : start + chunkSize;
            fileReader.readAsArrayBuffer(blobSlice.call(file, start, end));
        }
        loadNext();
    }
});


