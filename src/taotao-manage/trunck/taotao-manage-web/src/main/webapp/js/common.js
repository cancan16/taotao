Date.prototype.format = function (format) {
	
	var o = {
	    "M+" : this.getMonth () + 1, // month
	    "d+" : this.getDate (), // day
	    "h+" : this.getHours (), // hour
	    "m+" : this.getMinutes (), // minute
	    "s+" : this.getSeconds (), // second
	    "q+" : Math.floor ((this.getMonth () + 3) / 3), // quarter
	    "S" : this.getMilliseconds ()
	// millisecond
	};
	if (/(y+)/.test (format)){
		format = format.replace (RegExp.$1, (this.getFullYear () + "").substr (4 - RegExp.$1.length));
	}
	for ( var k in o){
		if (new RegExp ("(" + k + ")").test (format)){
			format = format.replace (RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr (("" + o[k]).length));
		}
	}
	return format;
};

var TT = TAOTAO = {
    // 编辑器参数
    kingEditorParams : {
        filePostName : "uploadFile", // 上传表单的名称
        uploadJson : '/rest/pic/upload', // 上传地址
        dir : "image" // 类型
    },
    // 格式化时间
    formatDateTime : function (val, row) {
	    var now = new Date (val);
	    return now.format ("yyyy-MM-dd hh:mm:ss");
    },
    // 格式化连接
    formatUrl : function (val, row) {
	    if (val){
		    return "<a href='" + val + "' target='_blank'>查看</a>";
	    }
	    return "";
    },
    // 格式化价格
    formatPrice : function (val, row) {
	    return (val / 1000).toFixed (2);
    },
    // 格式化商品的状态
    formatItemStatus : function formatStatus (val, row) {
	    if (val == 1){
		    return '正常';
	    }
	    else if (val == 2){
		    return '<span style="color:red;">下架</span>';
	    }
	    else{
		    return '未知';
	    }
    },
    
    init : function (data) {
	    this.initPicUpload (data);
	    this.initItemCat (data);
    },
    // 初始化图片上传组件
    initPicUpload : function (data) {
	    $ (".picFileUpload").each (function (i, e) {
		    var _ele = $ (e);
		    // siblings方法:查找同级元素对象中的div对象中的pics
		    // 先清空标签的内容再添加图片标签
		    _ele.siblings ("div.pics").remove ();
		    // 在js中一个字符串没有结束可以用'\'来表示
		    _ele.after ('\
    			<div class="pics">\
        			<ul></ul>\
        		</div>');
		    // 回显图片
		    if (data && data.pics){
			    var imgs = data.pics.split (",");
			    for ( var i in imgs){
				    if ($.trim (imgs[i]).length > 0){
					    _ele.siblings (".pics").find ("ul").append ("<li><a href='" + imgs[i] + "' target='_blank'><img src='" + imgs[i] + "' width='80' height='50' /></a></li>");
				    }
			    }
		    }
		    $ (e).click (function () {
			    /*
				 * 第一行代码的意义就是找到按钮对象最近的form父标签对象
				 * this代表当前的按钮对象，转成jquery对象调用parentUntil方法，
				 * parentUntil：找到所有父标签匹配form元素的下一个标签对象(其实就是table对象)，然后再获取table对象的父标签是form的标签的form对象
				 */
			    var form = $ (this).parentsUntil ("form").parent ("form");
			    KindEditor.editor (TT.kingEditorParams).loadPlugin ('multiimage', function () {
				    var editor = this;
				    editor.plugin.multiImageDialog ({
					    clickFn : function (urlList) { // 这个方法是点击全部插入的时候执行
						    var imgArray = [];
						    KindEditor.each (urlList, function (i, data) {
							    imgArray.push (data.url);
							    form.find (".pics ul").append ("<li><a href='" + data.url + "' target='_blank'><img src='" + data.url + "' width='80' height='50' /></a></li>");
						    });
						    form.find ("[name=image]").val (imgArray.join (","));
						    editor.hideDialog ();
					    }
				    });
			    });
		    });
	    });
    },
    
    // 初始化选择类目组件
    initItemCat : function (data) {
	    // i,e代表索引和元素
	    $ (".selectItemCat").each (function (i, e) {
		    var _ele = $ (e);
		    if (data && data.cid){
			    _ele.after ("<span style='margin-left:10px;'>" + data.cid + "</span>");
		    }
		    else{
			    _ele.after ("<span style='margin-left:10px;'></span>");
		    }
		    // 先解绑再绑定，为了防止对象多次绑定时间
		    _ele.unbind ('click').click (function () {
			    // 创建一个div并添加样式
			    $ ("<div>").css ({
				    padding : "5px"
			    }).html ("<ul>").window ({
			        width : '500',
			        height : "300",
			        modal : true,
			        closed : true,
			        iconCls : 'icon-save',
			        title : '选择类目',
			        // 执行打开窗口函数
			        onOpen : function () {
				        var _win = this; // 创建的div
				        // _win代表一个范围，在创建的div中查找ul标签
				        $ ("ul", _win).tree ({
				            url : '/rest/item/cat',
				            animate : true,
				            method : 'GET',
				            onClick : function (node) {
					            if ($ (this).tree ("isLeaf", node.target)){
						            // 填写到cid中
						            // node为选中的对象，node.id就是选中的商品ID
						            _ele.parent ().find ("[name=cid]").val (node.id);
						            _ele.next ().text (node.text).attr ("cid", node.id);
						            $ (_win).window ('close');
						            if (data && data.fun){
							            /*
										 * 传入函数做回调 通过.call执行function,
										 * 第一个参数必须传入一般都为this，从第二个参数开始是function的参数
										 */
							            data.fun.call (this, node);
						            }
					            }
				            }
				        });
			        },
			        onClose : function () {
				        $ (this).window ("destroy");
			        }
			    }).window ('open');
		    });
	    });
    },
    
    createEditor : function (select) {
	    // 参数1.多行文本选择器2.富文本编辑器所需参数
	    return KindEditor.create (select, TT.kingEditorParams);
    },
    
    /**
	 * 创建一个窗口，关闭窗口后销毁该窗口对象。<br/>
	 * 
	 * 默认：<br/> width : 80% <br/> height : 80% <br/> title : (空字符串) <br/>
	 * 
	 * 参数：<br/> width : <br/> height : <br/> title : <br/> url : 必填参数 <br/>
	 * onLoad : function 加载完窗口内容后执行<br/>
	 * 
	 * 
	 */
    createWindow : function (params) {
	    $ ("<div>").css ({
		    padding : "5px"
	    }).window ({
	        width : params.width ? params.width : "80%",
	        height : params.height ? params.height : "80%",
	        modal : true,
	        title : params.title ? params.title : " ",
	        href : params.url,
	        onClose : function () {
		        $ (this).window ("destroy");
	        },
	        onLoad : function () {
		        if (params.onLoad){
			        params.onLoad.call (this);
		        }
	        }
	    }).window ("open");
    },
    
    closeCurrentWindow : function () {
	    $ (".panel-tool-close").click ();
    },
    
    changeItemParam : function (node, formId) {
	    // 新增商品模板时查找是否存在模板
	    $.ajax ({
	        type : "GET",
	        url : "/rest/item/param/" + node.id,
	        statusCode : {
	            200 : function (data) {
		            $ ("#" + formId + " .params").show ();
		            var paramData = JSON.parse (data.paramData);
		            var html = "<ul>";
		            for ( var i in paramData){
			            var pd = paramData[i];
			            html += "<li><table>";
			            html += "<tr><td colspan=\"2\" class=\"group\">" + pd.group + "</td></tr>";
			            for ( var j in pd.params){
				            var ps = pd.params[j];
				            html += "<tr><td class=\"param\"><span>" + ps + "</span>: </td><td><input autocomplete=\"off\" type=\"text\"/></td></tr>";
			            }
			            html += "</li></table>";
		            }
		            html += "</ul>";
		            $ ("#" + formId + " .params td").eq (1).html (html);
	            },
	            404 : function () { // 不存在就添加
		            $ ("#" + formId + " .params").hide ();
		            $ ("#" + formId + " .params td").eq (1).empty ();
	            },
	            500 : function () { // 错误弹出框出错
		            alert ("error");
	            }
	        }
	    });
    },
    getSelectionsIds : function (select) {
	    var list = $ (select);
	    var sels = list.datagrid ("getSelections");
	    var ids = [];
	    for ( var i in sels){
		    ids.push (sels[i].id);
	    }
	    ids = ids.join (",");
	    return ids;
    },
    
    /**
	 * 初始化单图片上传组件 <br/> 选择器为：.onePicUpload <br/> 上传完成后会设置input内容以及在input后面追加<img>
	 */
    initOnePicUpload : function () {
	    $ (".onePicUpload").click (function () {
		    var _self = $ (this);
		    KindEditor.editor (TT.kingEditorParams).loadPlugin ('image', function () {
			    this.plugin.imageDialog ({
			        showRemote : false,
			        clickFn : function (url, title, width, height, border, align) {
				        var input = _self.siblings ("input");
				        input.parent ().find ("img").remove ();
				        input.val (url);
				        input.after ("<a href='" + url + "' target='_blank'><img src='" + url + "' width='80' height='50'/></a>");
				        this.hideDialog ();
			        }
			    });
		    });
	    });
    }
};
