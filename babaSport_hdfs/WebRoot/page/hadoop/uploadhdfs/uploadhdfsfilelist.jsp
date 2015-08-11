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
	
	function uploadFiles()	//为了把表单提交到相应的路径
	{
		var obj = document.getElementsByName("form1").item(0);
		//得到当前文件夹的路径
		//var filepath = document.getElementByName("filePath");
		//跳转的时候获取到相应的路径
		obj.action = "add-uploadHdfs";
		obj.method = "post";
		obj.submit();
	}
	
</script>
<SCRIPT language=JavaScript src="/js/FoshanRen.js"></SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" marginwidth="0" marginheight="0">
<s:form name="form1" action="uploadHDFSlist" method="post">
<s:hidden name="filePath" />
  <table width="98%" border="0" cellspacing="1" cellpadding="2" align="center">
    <tr >
    	<td colspan="4" bgcolor="6f8ac4" align="right">
    	<%-- <%@ include file="../../share/fenye.jsp" %> --%>
   		</td>
   </tr>
    <tr>
      <td width="40%" bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">文件名/文件夹</font></div></td>
      <td width="10%" bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">类型</font></div></td>
      <td width="30%" bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">大小</font></div></td>
      <td width="10%" bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">删除</font></div></td>
      <td width="10%" bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">操作</font></div></td>
    </tr>
<!---------------------------LOOP START------------------------------>
<s:iterator value="#request.hfss" var="entry">
    <tr> 
      <td bgcolor="f5f5f5"> 
      	<div align="center">
      		<s:if test="%{#entry.dir == true}">
      			<a href="uploadHDFSlist?filePath=<s:property value="#entry.path" />" >
      				<s:property value="#entry.filename" />
      			</a>
      		</s:if>
      		<s:if test="%{#entry.dir == false}">
      			<div align="center"><s:property value="#entry.filename" /></div>
      		</s:if>
      	</div>
      </td>
      <td bgcolor="f5f5f5"> 
      	<div align="center">
      		<s:if test="%{#entry.dir == true}">
      			<div align="center">目录</div>
      		</s:if>
      		<s:if test="%{#entry.dir == false}">
      			<div align="center">文件</div>
      		</s:if>
      	</div>
      </td>
	  <td bgcolor="f5f5f5"> 
	  	<div align="center"><s:property value="#entry.length" />　字节</div>
	  </td>
	  <td bgcolor="f5f5f5"> 
	  	<div align="center">
	  		<a href="uploadHdfs-delete?filePath=<s:property value="#entry.path" />" target="_blank">
	  			删除
	  		</a>
	  	</div>
	  </td>
	  <td bgcolor="f5f5f5"> 
	  	<div align="center">
	  		<a href="uploadHdfs-download?filePath=<s:property value="#entry.path" />" target="_blank">
	  			下载
	  		</a>
	  	</div>
	  </td>
	</tr>
</s:iterator>
    <!----------------------LOOP END------------------------------->
    <tr>
      <td bgcolor="f5f5f5" colspan="4" align="center">
      	<table width="100%" border="0" cellspacing="1" cellpadding="3">
          <tr> 
             <td align="center" >
              <input type="button" class="frm_btn" onClick="javascript:uploadFiles()" value=" 上传到当前文件夹 "> &nbsp;&nbsp;
            </td>
          </tr>
        </table>
       </td>
    </tr>
  </table>
</s:form>
</body>
</html>