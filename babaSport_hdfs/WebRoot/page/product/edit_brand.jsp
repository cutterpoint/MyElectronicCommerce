<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
<title>修改品牌</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/css/vip.css" type="text/css">
<SCRIPT language=JavaScript src="/js/FoshanRen.js"></SCRIPT>
<script language="JavaScript">
function checkfm(form){
	if (trim(form.name.value)==""){
		alert("品牌名称不能为空！");
		form.name.focus();
		return false;
	}
	if (byteLength(form.note.value)>200){
		alert("图片名不能大于200字！");
		form.note.focus();
		return false;
	}	
	return true;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<s:form action="brandmanage-edit" method="post" enctype="multipart/form-data"  onsubmit="return checkfm(this)">
<s:hidden name="code" value="%{#request.code}" />
<br>
  <table width="90%" border="0" cellspacing="2" cellpadding="3" align="center">
    <tr bgcolor="6f8ac4">
    	<td colspan="2"  > <font color="#FFFFFF">修改品牌：</font></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="22%" > <div align="right">品牌名称： </div></td>
      <td width="78%"> <input type="text" name="name" value="${name }" size="50" maxlength="50" />
        <font color="#FF0000">*</font>
       </td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="22%" > 
      	<div align="right">Logo图片：</div>
      </td>
      <td width="78%"> 
      	<input type="file" name="logofile" size="50" maxlength="100" /> 
      	<s:if test="%{#request.logoimagepath != null}">
      		<img src="${logoimagepath }" width="200" />
      	</s:if>
      	<s:if test="%{#request.logoimagepath == null}">
      		NO Logo!
      	</s:if>
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