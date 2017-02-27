<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div>
	<ul id="contentCategory" class="easyui-tree">
	</ul>
</div>
<div id="contentCategoryMenu" class="easyui-menu" style="width: 120px;"
	data-options="onClick:menuHandler">
	<div data-options="iconCls:'icon-add',name:'add'">添加</div>
	<div data-options="iconCls:'icon-remove',name:'rename'">重命名</div>
	<div class="menu-sep"></div>
	<!-- div 右键显示的分隔符 -->
	<div data-options="iconCls:'icon-remove',name:'delete'">删除</div>
</div>
<script type="text/javascript">
	$ (function () {
	    $ ("#contentCategory").tree ({
	        url : '/rest/content/category',
	        animate : true,
	        method : "GET",
	        onContextMenu : function (e, node) {
		        e.preventDefault (); // 阻止浏览器默认的右键事件
		        $ (this).tree ('select', node.target); // 选中当前右键的节点
		        $ ('#contentCategoryMenu').menu ('show', {
		            left : e.pageX, // 右击事件的显示菜单的位置
		            top : e.pageY
		        });
	        },
	        onAfterEdit : function (node) { // 回车编辑新增的节点之调用的方法请求后台处理
		        var _tree = $ (this);
		        if (node.id == 0){ // 判断是否是新增节点，如果是新增节点修改节点ID
			        // 新增节点
			        $.post ("/rest/content/category", {
			            parentId : node.parentId,
			            name : node.text
			        }, function (data) {
				        _tree.tree ("update", {
				            target : node.target,
				            id : data.id
				        });
			        });
		        }
		        else{ // 如果不是新增的节点就修改节点内容
			        $.ajax ({
			            type : "PUT", // 修改
			            url : "/rest/content/category",
			            data : {
			                id : node.id,
			                name : node.text
			            },
			            success : function (msg) {
				            //$.messager.alert('提示','新增商品成功!');
			            },
			            error : function () {
				            $.messager.alert ('提示', '重命名失败!');
			            }
			        });
		        }
	        }
	    });
    });
    function menuHandler (item) { // 菜单选项点击事件传入菜单的选项对象
	    var tree = $ ("#contentCategory");
	    var node = tree.tree ("getSelected"); // 获取当前右键的节点作为父节点
	    if (item.name === "add"){
		    tree.tree ('append', {
		        parent : (node ? node.target : null), // 如果父节点为false把null作为父节点
		        data : [
			        { // 子节点的数据
			            text : '新建分类', // 默认子节点的名称
			            id : 0, // 新增初始化ID都为0,新增成功后才修改
			            parentId : node.id
			        }
		        ]
		    });
		    var _node = tree.tree ('find', 0); // 通过节点ID查找节点ID为0的节点对象
		    tree.tree ("select", _node.target).tree ('beginEdit', _node.target); // 选中新增的节点开始编辑状态
	    }
	    else if (item.name === "rename"){
		    tree.tree ('beginEdit', node.target);
	    }
	    else if (item.name === "delete"){
		    $.messager.confirm ('确认', '确定删除名为 ' + node.text + ' 的分类吗？', function (r) {
			    if (r){
				    $.ajax ({
				        type : "POST",
				        url : "/rest/content/category",
				        data : {
				            parentId : node.parentId,
				            id : node.id,
				            "_method" : "DELETE"
				        },
				        success : function (msg) {
					        //$.messager.alert('提示','新增商品成功!');
					        tree.tree ("remove", node.target);
				        },
				        error : function () {
					        $.messager.alert ('提示', '删除失败!');
				        }
				    });
			    }
		    });
	    }
    }
</script>