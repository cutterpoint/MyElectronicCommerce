<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
<title>添加产品</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="../css/vip.css" type="text/css">
<SCRIPT type="text/javascript" src="../js/FoshanRen.js"></SCRIPT>
<%-- <script type="text/javascript" src="../js/jscripts/tiny_mce/tiny_mce.js"></script> --%>
<script type="text/javascript" charset="gbk" src="../ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="gbk" src="../ueditor/ueditor.all.min.js"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="gbk" src="../ueditor/lang/zh-cn/zh-cn.js"></script>

<style type="text/css">
   div{
       width:100%;
   }
</style>


<script type="text/javascript">

    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');


    function isFocus(e){
        alert(UE.getEditor('editor').isFocus());
        UE.dom.domUtils.preventDefault(e);
    }
    function setblur(e){
        UE.getEditor('editor').blur();
        UE.dom.domUtils.preventDefault(e);
    }
    function insertHtml() {
        var value = prompt('插入html代码', '');
        UE.getEditor('editor').execCommand('insertHtml', value);
    }
    function createEditor() {
        enableBtn();
        UE.getEditor('editor');
    }
    function getAllHtml() {
        alert(UE.getEditor('editor').getAllHtml());
    }
    function getContent() {
        var arr = [];
        arr.push("使用editor.getContent()方法可以获得编辑器的内容");
        arr.push("内容为：");
        arr.push(UE.getEditor('editor').getContent());
        alert(arr.join("\n"));
    }
    function getPlainTxt() {
        var arr = [];
        arr.push("使用editor.getPlainTxt()方法可以获得编辑器的带格式的纯文本内容");
        arr.push("内容为：");
        arr.push(UE.getEditor('editor').getPlainTxt());
        alert(arr.join('\n'));
    }
    function setContent(isAppendTo) {
        var arr = [];
        arr.push("使用editor.setContent('欢迎使用ueditor')方法可以设置编辑器的内容");
        UE.getEditor('editor').setContent('欢迎使用ueditor', isAppendTo);
        alert(arr.join("\n"));
    }
    function setDisabled() {
        UE.getEditor('editor').setDisabled('fullscreen');
        disableBtn("enable");
    }

    function setEnabled() {
        UE.getEditor('editor').setEnabled();
        enableBtn();
    }

    function getText() {
        //当你点击按钮时编辑区域已经失去了焦点，如果直接用getText将不会得到内容，所以要在选回来，然后取得内容
        var range = UE.getEditor('editor').selection.getRange();
        range.select();
        var txt = UE.getEditor('editor').selection.getText();
        alert(txt);
    }

    function getContentTxt() {
        var arr = [];
        arr.push("使用editor.getContentTxt()方法可以获得编辑器的纯文本内容");
        arr.push("编辑器的纯文本内容为：");
        arr.push(UE.getEditor('editor').getContentTxt());
        alert(arr.join("\n"));
    }
    function hasContent() {
        var arr = [];
        arr.push("使用editor.hasContents()方法判断编辑器里是否有内容");
        arr.push("判断结果为：");
        arr.push(UE.getEditor('editor').hasContents());
        alert(arr.join("\n"));
    }
    function setFocus() {
        UE.getEditor('editor').focus();
    }
    function deleteEditor() {
        disableBtn();
        UE.getEditor('editor').destroy();
    }
    function disableBtn(str) {
        var div = document.getElementById('btns');
        var btns = UE.dom.domUtils.getElementsByTagName(div, "button");
        for (var i = 0, btn; btn = btns[i++];) {
            if (btn.id == str) {
                UE.dom.domUtils.removeAttributes(btn, ["disabled"]);
            } else {
                btn.setAttribute("disabled", "true");
            }
        }
    }
    function enableBtn() {
        var div = document.getElementById('btns');
        var btns = UE.dom.domUtils.getElementsByTagName(div, "button");
        for (var i = 0, btn; btn = btns[i++];) {
            UE.dom.domUtils.removeAttributes(btn, ["disabled"]);
        }
    }

    function getLocalData () {
        alert(UE.getEditor('editor').execCommand( "getlocaldata" ));
    }

    function clearLocalData () {
        UE.getEditor('editor').execCommand( "clearlocaldata" );
        alert("已清空草稿箱");
    }

	function Formfield(name, label)
	{
		this.name=name;
		this.label=label;
	}
	
	function verifyForm(objForm)
	{
		//手动把 文本域的值赋给textarea表单元素
		var des = document.getElementById("editor");
		if(UE.getEditor('editor').hasContents() == true) 	//如果文本域里面有内容的话
		{
			des.value = UE.getEditor('editor').getPlainTxt();
		}
		//alert(des.value);
		
		var list  = new Array(new Formfield("name", "产品名称"),new Formfield("typeid", "产品类型"),
		new Formfield("baseprice", "产品底价"),new Formfield("marketprice", "产品市场价")
		,new Formfield("sellprice", "产品销售价"),new Formfield("description", "产品描述"),
		new Formfield("stylename", "产品图片的样式"),new Formfield("imagefile", "产品图片"));
		for(var i=0;i<list.length;i++)
		{
			var objfield = eval("objForm."+ list[i].name);
			if(trim(objfield.value)=="")
			{
				alert(list[i].label+ "不能为空");
				if(objfield.type!="hidden" && objfield.focus()) objfield.focus();
				return false;
			}
		}
		var imagefile = objForm.imagefile.value;
		var ext = imagefile.substring(imagefile.length-3).toLowerCase();
		if (ext!="jpg" && ext!="gif" && ext!="bmp" && ext!="png")
		{
			alert("只允许上传gif、jpg、bmp、png！");
			return false; 
		}
	    return true;
	}
	

	function SureSubmit()
	{
		var objForm = document.getElementById("form1");
		if (verifyForm(objForm)) 
			objForm.submit();
	} 

</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<s:form id="form1" action="productmanage-add" enctype="multipart/form-data" method="post">
<s:hidden name="typeid"/>
  <table width="98%" border="0" cellspacing="1" cellpadding="3" align="center">
    <tr bgcolor="6f8ac4"> 
      <td colspan="2" ><font color="#FFFFFF">添加产品：</font></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">产品名称  ：</div></td>
      <td width="75%"> <input type="text" name="name" size="50" maxlength="40" /><font color="#FF0000">*</font></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">产品类别<font color="#FF0000">*</font>  ：</div></td>
      <td width="75%"> <input type="text" name="v_type_name" disabled="true" size="30"/> 
        <input type="button" name="select" value="选择..." onClick="javaScript:winOpen('<s:url action="select-productmanage"/>','列表',600,400)">(<a href="<s:url action='/control/product/type/manage'/>?method=addUI">添加产品类别</a>)
      </td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">底(采购)价 ：</div></td>
      <td width="75%"> <input type="text" name="baseprice" size="10" maxlength="10" onclick="javascript:InputLongNumberCheck()" />元 <font color="#FF0000">*</font></td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">市场价 ：</div></td>
      <td width="75%"> <input type="text" name="marketprice" size="10" maxlength="10" onclick="javascript:InputLongNumberCheck()" />元 <font color="#FF0000">*</font></td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">销售价 ：</div></td>
      <td width="75%"> <input type="text" name="sellprice" size="10" maxlength="10" onclick="javascript:InputLongNumberCheck()" />元 <font color="#FF0000">*</font></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">货号 ：</div></td>
      <td width="75%"> <input type="text" name="code" size="20" maxlength="30" onclick="javascript:InputLongNumberCheck()" />(注:供货商提供的便于产品查找的编号)</td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">产品图片 (不同颜色/样式)：</div></td>
      <td width="75%"> 样式名称：<input name="stylename" type="text" size="10">样式图片<input type="file" name="imagefile" size="30"></td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="25%"> 
      	<div align="right">品牌 ：</div>
      </td>
      <td width="75%"> 
		<select name="brandid">
			<option value="">***无***</option>
			<s:iterator value="#request.brands" var="brand">
				<option value="<s:property value="#brand.code" />"><s:property value="#brand.name" /> </option>
			</s:iterator>
		</select>
        (<a href="##">添加品牌</a>)
      </td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="25%"> 
      		<div align="right">适用性别 ：</div>
      </td>
      <td width="75%">
      	<select name="sex">
      		<option value="NONE">男女不限</option>
      		<option value="MAN">男士</option>
      		<option value="WOMEN">女士</option>
      	</select>
	  </td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">型号 ：</div></td>
      <td width="75%"> <input type="text" name="model" size="35" maxlength="30" /></td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">重量 ：</div></td>
      <td width="75%"> <input type="text" name="weight" size="10" maxlength="10" onclick="" />克</td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">购买说明 ：</div></td>
      <td width="75%"> <input type="text" name="buyexplain" size="35" maxlength="30" /></td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="25%" valign="top"> <div align="right">产品简介<font color="#FF0000">*</font> ：</div></td>
      <td width="75%"> 
      	<textarea id="editor" name="description" style="width:1024px;height:500px;" ></textarea>
      </td>
	</tr>
    <tr bgcolor="f5f5f5"> 
      <td colspan="2"> <div align="center"> 
          <input type="button" name="Add" value=" 确 认 " class="frm_btn" onClick="javascript:SureSubmit()" />
          &nbsp;&nbsp;<input type="reset" name="Button" value=" 清　空 " class="frm_btn" onclick="" />
        </div></td>
    </tr>
  </table>
</s:form>
<br>
</body>
</html>