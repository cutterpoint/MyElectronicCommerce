<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
<title>文件上传</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../../css/vip.css" type="text/css">
<SCRIPT type="text/javascript" src="../../js/FoshanRen.js"></SCRIPT>
<script type="text/javascript">
function checkfm(form)
{	
	//这个是在客户端判断是不是需求的文件格式
	var uploadfile = document.getElementsByName("uploadfile").item(0).value;	//获取到这个属性的值
	//去掉空格
	while(uploadfile.indexOf(" ") != -1)
	{
		uploadfile = uploadfile.replace(" ", "");//吧" "替换为""
	}
	
	uploadfile = uploadfile.toLowerCase();
	//只要文件名不是空的
	if(uploadfile != "")
	{
		var types = ["jpg","gif","bmp","png","exe","doc","pdf","txt","xls","ppt","swf","docx"];
		//var ext = uploadfile.substr(uploadfile.lastIndexOf("."));	
		var two = uploadfile.split(".");
		//后缀
		var ext = two[two.length - 1];
		var sing = false;	//判断是否成功
		for(var i = 0; i < types.length; ++i)
		{
			if(ext == types[i])
			{
				sing = true;
			}
		}
		if(!sing)
		{
			alert("只允许上传图片/flash动画/word文件/exe文件/pdf文件/TxT文件/xls文件/ppt文件");
		}
	}
	return true;
	
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<s:form action="uploadHdfs-add" method="post" enctype="multipart/form-data" >
<s:hidden name="filePath" />
  <table width="90%" border="0" cellspacing="2" cellpadding="3" align="center">
    <tr bgcolor="6f8ac4"><td colspan="2"  > <font color="#FFFFFF">上传文件：</font></td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="22%" > <div align="right">文件：</div></td>
      <td width="78%"> <input type="file" name="uploadfile" size="50"><br/></td>
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