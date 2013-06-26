<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>upload</title>
<link type="text/css" href="jqueryui/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
<script type="text/javascript" src="jqueryui/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="jqueryui/js/jquery-ui-1.8.11.custom.min.js"></script>
<link type="text/css" href="jqueryui/development-bundle/demos/demos.css" rel="stylesheet" />
<style>
	.ui-progressbar-value { background-image: url(images/pbar-ani.gif); }
</style>
<script type="text/javascript">
	var interval ;
	$(function(){
		$("form").bind("submit",function(){
			dialog.dialog( "open") ;
			interval = setInterval(requestProgress, 500) ;
			//绑定iframe加载(load)事件
			$("#uploadMsg").load(function(){
		        var content = document.getElementById("uploadMsg").contentWindow.document.body.innerHTML;
		        //alert(content) ;
		        content = createXml(content);
		        var msg = $(content).find("msg").eq(0);
		        //alert(msg.text()) ;
		        judgeResult(msg.text()) ;
		        $("#uploadMsg").unbind("load");
		    });
		}) ;
		// 初始化button
		var subbtn = $("form input:submit").button() ;
		subbtn.button() ;
		// 初始化 dialog
		var dialog = $( "#dialog-modal" ).dialog({
			autoOpen: false,
			height: 120,
			width: 660,
			modal: true
		});
		// 初始化progressbar
		$( "#progressbar" ).progressbar({
			value: 0
		});
	}) ;
	
	// judge the result
	function judgeResult(msg){
		if(msg==undefined){
			alert("参数错误") ;
		}else if(msg=="ok"){
			alert("上传成功!") ;
		}else if(msg=="unAllowedExt"){
			alert("非法后缀名!") ;
		}else if(msg =="unSizeLimit"){
			alert("上传文件过大!") ;
		}else if(msg =="unSupportedEncoding"){
			alert("不支持的编码!") ;
		}
	}
	
	// html to xml
	function createXml(str){
	    if (document.all) {
	        var xmlDom = new ActiveXObject("Microsoft.XMLDOM");
	        xmlDom.loadXML(str);
	        return xmlDom;
	    }
	    else {
	        return new DOMParser().parseFromString(str, "text/xml");
	    }
	}
	
	// 对上传进度进行请求
	function requestProgress(){
		$.post("uploadServlet.do",{"action":"progress"},function(data){
			var val =  $(data).find("percent").eq(0).text() ;
			$("#currVal").html(val) ;
			// 通过xml取得的数字值为string型，要调用parseInt对其进行转型
			$("#progressbar").progressbar("option", "value", parseInt(val));
			//alert("当前值是: " + $("#progressbar").progressbar("option", "value"));
			if(val==100){
				// 清除请求函数
				clearInterval(interval);
				$( "#dialog-modal" ).dialog( "close" ) ;
			}
		},"xml") ;
	}
</script>
</head>
<body>
	<form action="uploadServlet.do?action=upload" method="post" enctype="multipart/form-data" target="uploadMsg">
		<label>图片：</label>
		<input type="file" name="uploadFile" /><br />
		<input type="submit" value="上传"  />
	</form>
	<br />
	<!-- fileName值根据实际需要进行传入 -->
	<a href="uploadServlet.do?action=download&fileName=20111119205946649776.txt">download</a>
	
	<div id="dialog-modal" title="上传进度">
		<!--  -->
		<div id="progressbar"></div>
		<div><span id="currVal"></span><span>%</span></div>
	</div>
	<iframe id="uploadMsg" name="uploadMsg" frameborder="0" width="0" height="0"></iframe>
</body> 
</html>