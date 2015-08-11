<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
<title>上传文件显示</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/css/vip.css" type="text/css">
<script type="text/javascript">
	//到指定的分页页面
	function topage(page)
	{
		document.getElementById("page").value = page;
		var obj = document.getElementsByName("form1").item(0).submit();
	}
	
	function allselect()
	{
	
		var selectall = document.getElementsByName("all").item(0);	//获取得到全选按钮
		var selecteveryone = document.getElementsByName("fileids");
		//判断全选按钮是否被选中
		//如果被选中了，那么我们用循环进行和全选复选框同步为一样的
		for(var i = 0; i < selecteveryone.length; ++i)
		{
			//吧选项一个一个地进行同步一样的
			selecteveryone[i].checked = selectall.checked;
		}
	}
	
	function deleteFiles()	//为了把表单提交到相应的路径
	{
		var obj = document.getElementsByName("form1").item(0);
		obj.action = "delete-filemanage";
		obj.method = "post";
		obj.submit();
	}
</script>
<SCRIPT language=JavaScript src="/js/FoshanRen.js"></SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" marginwidth="0" marginheight="0">
<s:form name="form1" action="uploadfilelist" method="post">
<s:hidden id="page" name = "page" />
  <table width="98%" border="0" cellspacing="1" cellpadding="2" align="center">
    <tr ><td colspan="4" bgcolor="6f8ac4" align="right">
    	<%@ include file="../share/fenye.jsp" %>
   </td></tr>
    <tr>
      <td width="8%" bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">选择</font></div></td>
      <td width="10%" bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">代号</font></div></td>
      <td width="60%" bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">文件</font></div></td>
	  <td width="22%" nowrap bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">上传时间</font></div></td>
    </tr>
<!---------------------------LOOP START------------------------------>
<s:iterator value="#request.pageView.records" var="entry">
    <tr> 
      <td bgcolor="f5f5f5"> 
      	<div align="center">
      		<INPUT TYPE="checkbox" NAME="fileids" value="<s:property value="#entry.id" />" />
      	</div>
      </td>
      <td bgcolor="f5f5f5"> 
      	<div align="center"><s:property value="#entry.id" /></div>
      </td>
      <td bgcolor="f5f5f5"> 
      	<div align="center">
      		<a href="<s:property value="#entry.filepath" />" target="_blank"><s:property value="#entry.filepath" /></a>
      	</div>
      </td>
	  <td bgcolor="f5f5f5"> 
	  	<div align="center"><s:property value="#entry.uploadtime" /></div>
	  </td>
	</tr>
</s:iterator>
    <!----------------------LOOP END------------------------------->
    <tr>
      <td bgcolor="f5f5f5" colspan="4" align="center"><table width="100%" border="0" cellspacing="1" cellpadding="3">
          <tr> 
            <td width="10%"><INPUT TYPE="checkbox" NAME="all" onclick="javascript:allselect()">全选/反选</td>
              <td width="85%">
              <input type="button" class="frm_btn" onClick="javascript:deleteFiles()" value=" 删 除 "> &nbsp;&nbsp;
            </td>
          </tr>
        </table></td>
    </tr>
  </table>
</s:form>
</body>
</html>