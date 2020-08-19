<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>学生列表</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/css/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/js/validateExtends.js"></script>
	<script type="text/javascript">
	var clazzList = ${clazzListJson};
	$(function() {	
		var table;
		
		//datagrid初始化 
	    $('#dataList').datagrid({ 
	        title:'学生列表', 
	        iconCls:'icon-more',//图标 
	        border: true, 
	        collapsible:false,//是否可折叠的 
	        fit: true,//自动大小 
	        method: "post",
	        url:"get_list?t="+new Date().getTime(),
	        idField:'id', 
	        singleSelect:false,//是否单选 
	        pagination:true,//分页控件 
	        rownumbers:true,//行号 
	        sortName:'id',
	        sortOrder:'DESC', 
	        remoteSort: false,
	        columns: [[  
				{field:'chk',checkbox: true,width:50},
 		        {field:'id',title:'ID',width:50, sortable: true},    
 		        {field:'username',title:'姓名',width:150, sortable: true},
 		        {field:'sn',title:'学号',width:150, sortable: true},
 		        {field:'sex',title:'性别',width:150, sortable: true},
 		        {field:'clazzId',title:'所属班级',width:150, sortable: true,
 		        	formatter:function(value,index,row){//formatter 属于列参数，表示对于当前列的数据进行格式化操作，它是一个函数，有三个参数，分别是value，row,index
 		        		for(var i=0;i<clazzList.length;i++){//value：表示当前单元格中的值 	row：表示当前行 index：表示当前行的下标 
 		        			if(clazzList[i].id == value){//可以使用return返回想要的数据显示在单元格中
 		        				return clazzList[i].name;
 		        			}
 		        		}
 		        		return value;
 		    	   }
 		        },
 		        
 		        {field:'password',title:'密码',width:150},
	 		]], 
	        toolbar: "#toolbar"
	    }); 
	    //设置分页控件 
	    var p = $('#dataList').datagrid('getPager'); 
	    $(p).pagination({ 
	        pageSize: 10,//每页显示的记录条数，默认为10 
	        pageList: [10,20,30,50,100],//可以设置每页记录条数的列表 
	        beforePageText: '第',//页数文本框前显示的汉字 
	        afterPageText: '页    共 {pages} 页', 
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
	    }); 
	    //设置工具类按钮
	    $("#add").click(function(){
	    	table = $("#addTable");
	    	$("#addDialog").dialog("open");
	    });
	    //修改
	    $("#edit").click(function(){
	    	table = $("#editTable");
	    	var selectRows = $("#dataList").datagrid("getSelections");
        	if(selectRows.length != 1){
            	$.messager.alert("消息提醒", "请选择一条数据进行操作!", "warning");
            } else{
		    	$("#editDialog").dialog("open");
            }
	    });
	    //删除
	    $("#delete").click(function(){
	    	var selectRows = $("#dataList").datagrid("getSelections");
        	var selectLength = selectRows.length;
        	if(selectLength == 0){
            	$.messager.alert("消息提醒", "请选择数据进行删除!", "warning");
            } else{
            	var ids = [];
            	$(selectRows).each(function(i, row){
            		ids[i] = row.id;
            	});
            	$.messager.confirm("消息提醒", "如果学生下存在学生信息则无法删除，须先删除学生下属的学生信息？", function(r){
            		if(r){
            			$.ajax({
							type: "post",
							url: "delete",
							data: {ids: ids},
							dataType:'json',
							success: function(data){
								if(data.type == "success"){
									$.messager.alert("消息提醒","删除成功!","info");
									//刷新表格
									$("#dataList").datagrid("reload");
									$("#dataList").datagrid("uncheckAll");
								} else{
									$.messager.alert("消息提醒",data.msg,"warning");
									return;
								}
							}
						});
            		}
            	});
            }
	    });
	    
	  	//设置添加窗口
	    $("#addDialog").dialog({
	    	title: "添加学生",
	    	width: 500,
	    	height: 650,
	    	iconCls: "icon-add",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    	buttons: [
	    		{
					text:'添加',
					plain: true,
					iconCls:'icon-user_add',
					handler:function(){
						var validate = $("#addForm").form("validate");
						if(!validate){
							$.messager.alert("消息提醒","请检查你输入的数据!","warning");
							return;
						} else{
							var data = $("#addForm").serialize();
							$.ajax({
								type: "post",
								url: "add",
								data: data,
								dataType:'json',
								success: function(data){
									if(data.type == "success"){
										$.messager.alert("消息提醒","添加成功!","info");
										//关闭窗口
										$("#addDialog").dialog("close");
										//清空原表格数据
										$("#add_name").textbox('setValue', "");
										$("#add_remark").textbox('setValue', "");
										//重新刷新页面数据
							  			$('#dataList').datagrid("reload");
										
									} else{
										$.messager.alert("消息提醒",data.msg,"warning");
										return;
									}
								}
							});
						}
					}
				},
			],
			onClose: function(){
				$("#add_name").textbox('setValue', "");
				$("#add_remark").textbox('setValue', "");
			}
	    });
	  	
	  	//编辑学生信息
	  	$("#editDialog").dialog({
	  		title: "修改学生信息",
	    	width: 450,
	    	height: 400,
	    	iconCls: "icon-edit",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    	buttons: [
	    		{
					text:'提交',
					plain: true,
					iconCls:'icon-edit',
					handler:function(){
						var validate = $("#editForm").form("validate");
						if(!validate){
							$.messager.alert("消息提醒","请检查你输入的数据!","warning");
							return;
						} else{
							
							var data = $("#editForm").serialize();
							
							$.ajax({
								type: "post",
								url: "edit",
								data: data,
								dataType:'json',
								success: function(data){
									if(data.type == "success"){
										$.messager.alert("消息提醒","修改成功!","info");
										//关闭窗口
										$("#editDialog").dialog("close");
										
										//重新刷新页面数据
							  			$('#dataList').datagrid("reload");
							  			$('#dataList').datagrid("uncheckAll");
										
									} else{
										$.messager.alert("消息提醒",data.msg,"warning");
										return;
									}
								}
							});
						}
					}
				},
			],
			onBeforeOpen: function(){
				var selectRow = $("#dataList").datagrid("getSelected");
				//设置值
				$("#edit-id").val(selectRow.id);
				$("#edit_name").textbox('setValue', selectRow.name);
				$("#edit_gradeId").combobox('setValue', selectRow.gradeId);
				$("#edit_remark").textbox('setValue', selectRow.remark);
			}
	    });
	   	
	  	//下拉框通用属性
	  	$("#gradeId").combobox({
	  		width: "200",
	  		height: "30",
	  		valueField: "id",
	  		textField: "name",
	  		multiple: false, //可多选
	  		editable: false, //不可编辑
	  	});
	  	
	  	//搜索按钮
	  	$("#search-btn").click(function(){
	  		$('#dataList').datagrid('reload',{
	  			name:$("#search-name").textbox('getValue'),
	  			gradeId:$("#search-grade-id").combobox('getValue')
	  		});
	  	});
		//上传图片按钮
	  	$("#upload-btn").click(function(){
	  		if($("#add-upload-photo").filebox("getValue") == ''){
	  			$.messager.alert("消息提醒","请选择图片文件!","warning");
	  			return;
	  		}
	  		$("#photoForm").submit();
	  	});
	});
	</script>
</head>
<body>
	<!-- 数据列表 -->
	<table id="dataList" cellspacing="0" cellpadding="0"> 
	    
	</table> 
	<!-- 工具栏 -->
	<div id="toolbar">
	<div id="toolbar">
		<div style="float: left;"><a id="add" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="float: left;"><a id="edit" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">修改</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		<div>
			<a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">删除</a>
			学生名称：<input id="search-name" class="easyui-textbox" />
			所属年级：
			<select id="search-grade-id" class="easyui-combobox" style="width: 150px;">
				<option value="">全部</option>
				<c:forEach items="${ gradeList}" var="grade">
	    			<option value="${grade.id }">${grade.name }</option>
	    		</c:forEach>
			</select>
			<a id="search-btn" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">搜索</a>
		</div>
	</div>
	</div>
	
	<!-- 添加窗口 -->
	<div id="addDialog" style="padding: 10px;">  
	  <form id="photoForm" method="post" method="post" enctype="multipart/form-data" action="upload_photo" target="photo_target">
	    	<table id="photoTable" cellpadding="8">
	        <tr >
	    			<td>预览头像:</td>
	    			<td>
	    			    <img id="photo-preview" alt="照片" style="max-width: 120px; max-height: 120px;" title="照片" src="/Student/photo/default.png" />
	    			</td>
	    		</tr>
	    		<tr >
	    			<td>学生头像:</td>
	    			<td>
	    				<input id="add-upload-photo" class="easyui-filebox" name="photo" data-options="prompt:'选择照片'" style="width:200px;">
	    				<a id="upload-btn" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">上传图片</a>
	    			</td>
	    		</tr>
	       </table>
	  </form>  	
   		<form id="addForm" method="post">
	    	<table id="addTable" cellpadding="8">
	    	  <input id="add_photo" type="hidden" name="photo" value="/Student/photo/default.png">
	    		<tr >
	    			<td>学生姓名:</td>
	    			<td>
	    				<input id="add_name"  class="easyui-textbox" style="width: 200px; height: 30px;" type="text" name="username" data-options="required:true, missingMessage:'请填写学生姓名'"  />
	    			</td>
	    		</tr>
	    		<tr >
	    			<td>登录密码:</td>
	    			<td>
	    				<input id="add_password"  class="easyui-textbox" style="width: 200px; height: 30px;" type="password" name="password" data-options="required:true, missingMessage:'请填写登录密码'"  />
	    			</td>
	    		</tr>
	    		<tr >
	    			<td>所属班级:</td>
	    			<td>
	    				<select id="add_clazzId"  class="easyui-combobox" style="width: 200px;" name="clazzId" data-options="required:true, missingMessage:'请选择所属班级'">
	    					<c:forEach items="${clazzList}" var="clazz">
	    						<option value="${clazz.id}">${clazz.name}</option>
	    					</c:forEach>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr >
	    			<td>学生性别:</td>
	    			<td>
	    			   <select id="add_sex"  class="easyui-combobox" style="width: 200px;" name="sex" data-options="required:true, missingMessage:'请选择性别'">
	    					<option value=男>男</option>
	    					<option value=女>女</option>
	    				</select>	
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>备注:</td>
	    			<td><input id="add_remark" style="width: 256px; height: 180px;" class="easyui-textbox" type="text" name="remark" data-options="multiline:true"  /></td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	
	
	<!-- 修改窗口 -->
	<div id="editDialog" style="padding: 10px">
    	<form id="editForm" method="post">
    		<input type="hidden" name="id" id="edit-id">
	    	<table id="editTable" border=0 cellpadding="8" >
	    		<tr >
	    			<td>学生名:</td>
	    			<td>
	    				<input id="edit_name"  class="easyui-textbox" style="width: 200px; height: 30px;" type="text" name="name" data-options="required:true, missingMessage:'请填写学生名'"  />
	    			</td>
	    		</tr>
	    		<tr >
	    			<td>所属年级:</td>
	    			<td>
	    				<select id="edit_gradeId"  class="easyui-combobox" style="width: 200px;" name="gradeId" data-options="required:true, missingMessage:'请选择所属年级'">
	    					<c:forEach items="${gradeList}" var="grade">
	    						<option value="${grade.id}">${grade.name}</option>
	    					</c:forEach>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>备注:</td>
	    			<td><input id="edit_remark" style="width: 256px; height: 180px;" class="easyui-textbox" type="text" name="remark" data-options="multiline:true"  /></td>
	    		</tr>
	    	</table>
	    </form>
	</div>
		
		<!-- 提交表单处理iframe框架 隐藏用户无感知 -->
	<iframe id="photo_target" name="photo_target"></iframe>    
	    
</body>
</html>