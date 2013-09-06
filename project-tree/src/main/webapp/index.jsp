<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="keywords" content="jquery,ui,easy,easyui,web">
<meta name="description"
	content="easyui help you build your web page easily!">
<title>jQuery EasyUI Demo</title>
<link rel="stylesheet" type="text/css" href="themes/metro/easyui.css">
<link rel="stylesheet" type="text/css" href="themes/icon.css">
<script type="text/javascript" src="script/jquery-1.6.1.min.js"></script>
<script type="text/javascript" src="script/jquery.easyui.min.js"></script>
<script type="text/javascript">
	$(function() {
		$('#tt')
				.tree(
						{
							animate : true,
							url : 'treeServlet.do?action=root',
							onBeforeExpand : function(node, param) {
								$('#tt').tree('options').url = "treeServlet.do?action=child&id="
										+ node.id;// change the url                       
							},
							onContextMenu : function(e, node) {
								e.preventDefault();
								$('#tt').tree('select', node.target);
								$('#mm').menu('show', {
									left : e.pageX,
									top : e.pageY
								});
								var node = $('#tt').tree('getSelected');
								// 展开子节点
								if (node) {
									$("#tt").tree("expand", node.target);
								}
							}
						});
		$("#node_add").click(function() {
			$("#win_node_add").window({
				closed : false
			});
		});

		$("#node_update").click(function() {
			var node = $('#tt').tree('getSelected');
			$("#node_id").val(node.id);
			$("#update_node_name").val(node.text);

			$("#win_node_update").window({
				closed : false
			});
		});

		$("#update_btn").click(function() {
			$.post("treeServlet.do?action=update", {
				"id" : $("#node_id").val(),
				"text" : $("#update_node_name").val()
			}, function() {
				var node = $('#tt').tree('getSelected');
				if (node) {
					$('#tt').tree('update', {
						target : node.target,
						text : $("#update_node_name").val()
					});
				}
			}, "json");
			$("#win_node_update").window({
				closed : true
			});
		});

		$("#btn").click(function() {
			var node = $('#tt').tree('getSelected');
			var text = $("#insert_node_name").val();
			$.post("treeServlet.do?action=insert", {
				"pId" : node.id,
				"text" : text
			}, function(data) {
				$('#tt').tree('append', {
					parent : (node ? node.target : null),
					data : [ {
						text : text,
						id : data.msgs.id,
						checked : true
					} ]
				});

				$("#win_node_add").window({
					closed : true
				});
			}, "json");
		});

		$("#node_remove").click(function() {
			var node = $('#tt').tree('getSelected');
			$.post("treeServlet.do?action=delete", {
				"id" : node.id
			}, function(data) {
				$('#tt').tree('remove', node.target);
			}, "json");
		});

	});
</script>
</head>
<body>
	<div style="width: 150px;">
		<ul id="tt" class="easyui-tree"></ul>
	</div>
	<div id="mm" class="easyui-menu" style="width: 120px;">
		<div id="node_add" iconcls="icon-add">添加节点</div>
		<div id="node_remove" iconcls="icon-remove">删除节点</div>

		<div id="node_update" iconcls="icon-edit">修改节点</div>
	</div>

	<div id="win_node_add" class="easyui-window" title="添加子节点"
		style="width: 500px; height: 150px; padding: 10px; background: #fafafa;"
		data-options="iconCls:'icon-save',closable:true,maximizable:false,minimizable:false,collapsible:false,shadow:true,
                closed:true,modal:true">
		节点名称:<input type="text" id="insert_node_name" /> <a id="btn"
			class="easyui-linkbutton" href="#">ok</a>
	</div>


	<div id="win_node_update" class="easyui-window" title="添加子节点"
		style="width: 500px; height: 150px; padding: 10px; background: #fafafa;"
		data-options="iconCls:'icon-save',closable:true,maximizable:false,minimizable:false,collapsible:false,shadow:true,
                closed:true,modal:true">
		节点名称:<input type="text" id="update_node_name" /> <input type="hidden"
			id="node_id" value="" /> <a id="update_btn"
			class="easyui-linkbutton" href="#">ok</a>
	</div>
</body>
</html>
