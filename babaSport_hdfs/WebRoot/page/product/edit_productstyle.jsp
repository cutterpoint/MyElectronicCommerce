<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
<title>修改产品样式</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/css/vip.css" type="text/css">
<SCRIPT type="text/javascript" src="/js/FoshanRen.js"></SCRIPT>
<script type="text/javascript">
function checkfm(form){
	if (trim(form.stylename.value)==""){
		alert("样式名称不能为空！");
		form.stylename.focus();
		return false;
	}
	var imagefile = form.imagefile.value;
	if(trim(imagefile)!=""){
		var ext = imagefile.substring(imagefile.length-3).toLowerCase();
		if (ext!="jpg" && ext!="gif" && ext!="bmp" && ext!="png"){
			alert("只允许上传gif、jpg、bmp、png！");
			return false; 
		}
	}
	return true;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<s:form action="productstylemanage-edit" method="post" enctype="multipart/form-data" onsubmit="return checkfm(this)">
<s:hidden name="productstyleid"/>
  <table width="90%" border="0" cellspacing="2" cellpadding="3" align="center">
    <tr bgcolor="6f8ac4"><td colspan="2"  > <font color="#FFFFFF">修改产品图片：</font></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="22%" > <div align="right">样式名称：</div></td> 
      <td width="78%"> <input type="text" size="50" maxlength="40" name="stylename" value="<s:property value="stylename" />" /> 
        <font color="#FF0000">*</font></td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="22%" > <div align="right">产品图片：</div></td>
      <td width="78%"> <input type="file" name="imagefile" size="50"><br>
      	<img src="<s:property value="#request.imageshowpath" />" width="100"> 
      </td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td colspan="2"> <div align="center"> 
          <input type="submit" value=" 确 定 " class="frm_btn">
        </div></td>
    </tr>
  </table>
</s:form>
<br>
</body>
</html>