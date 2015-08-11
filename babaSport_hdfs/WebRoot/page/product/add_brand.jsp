<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="/struts-tags"  prefix="s"%>
<html>
<head>
<title>添加类别</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/css/vip.css" type="text/css">
<SCRIPT language=JavaScript src="/js/FoshanRen.js"></SCRIPT>
<script language="JavaScript">
function checkfm(form)
{	
	var logofile = document.getElementsByName("logofile").item(0).value;
	var name = document.getElementsByName("name").item(0).value;
	
	while(name.indexOf(" ") != -1)
	{
		name = name.replace(" ", "");	//全部替换为没有空格的	
	}
	
	if (name=="")
	{
		alert("品牌名称不能为空！");
		form.name.focus();
		return false;
	}
	
	if(name != "")	//如果名字不是空的
	{
		var ext = logofile.substring(logofile.length - 3).toLowerCase();	//变为小写
		if(ext != "jpg" && ext != "gif" && ext != "bmp" && ext != "png")
		{
			alert("只准上传gif,png,jpg,bmp格式的照片");
			return false;
		}	
	}
	return true;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<s:form action="brandmanage-add" method="post" enctype="multipart/form-data"  onsubmit="return checkfm(this)">
<br>
  <table width="90%" border="0" cellspacing="2" cellpadding="3" align="center">
    <tr bgcolor="6f8ac4">
    	<td colspan="2"  > 
    		<font color="#FFFFFF">添加品牌：</font>
    	</td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="22%" > 
      	<div align="right">品牌名称：</div>
      </td>
      <td width="78%"> 
      	<input name="name" size="50" maxlength="40" />
        <font color="#FF0000">*</font>
      </td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="22%"> <div align="right">Logo图片：</div></td>
      <td width="78%"> 
      	 <input name="logofile" type="file" size="50" maxlength="100" /> 
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