<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags"  prefix="s" %>
<html>
<head>
<title>产品类别管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../css/vip.css" type="text/css">
<script type="text/javascript">

	//到指定的分页页面
	function topage(page)
	{
		document.getElementById("page").value = page;
		//alert(document.getElementById("page").value);
		//document.form1.method = 'post';
		//document.form1.submit();
		var obj = document.getElementsByName("form1").item(0).submit();

	}

</script>
<SCRIPT language=JavaScript src="../js/FoshanRen.js"></SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" marginwidth="0" marginheight="0">
<form id="form11" name="form1" action="brandlist" method="post">
<s:hidden id="page" name="page" />
<s:hidden name="query" />
<s:hidden name="name" />
  <table width="98%" border="0" cellspacing="1" cellpadding="2" align="center">
    <tr ><td colspan="4"  bgcolor="6f8ac4" align="right" >
   	<%@ include file="../share/fenye.jsp" %>
    </td></tr>
    <tr>
      <td width="20%" bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">代号</font></div></td>
      <td width="5%" nowrap bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">修改</font></div></td>
      <td width="45%" bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">名称</font></div></td>
	  <td width="30%" nowrap bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">Logo</font></div></td>
    </tr>
<!---------------------------LOOP START------------------------------>
<s:iterator value="#request.pageView.records" var="entry">
    <tr>
      <td bgcolor="f5f5f5"> 
      	<div align="center"><s:property value="#entry.code"/> </div>
      </td>
      <td bgcolor="f5f5f5"> 
      	<div align="center">
      		<a href="<s:url action="edit-brandmanage" />?code=<s:property value="#entry.code"/>">
	  			<img src="../images/edit.gif" width="15" height="16" border="0">
	  		</a>
	  	</div>
	  </td>
	  <td bgcolor="f5f5f5" align="center">
	  	<s:property value="#entry.name"/>
	  </td>
	  <td bgcolor="f5f5f5">
	  	<s:if test="#entry.logopath == null">没有Logo</s:if>
	  	<s:if test="#entry.logopath != null"><img width="100" src="${entry.logopath }"> </s:if>
	  </td>
	</tr>
</s:iterator>
    <!----------------------LOOP END------------------------------->
    <tr> 
      <td bgcolor="f5f5f5" colspan="4" align="center"><table width="100%" border="0" cellspacing="1" cellpadding="3">
          <tr> 
            <td width="5%"></td>
              <td width="85%">
              <input name="AddDic" type="button" class="frm_btn" id="AddDic" onClick="javascript:window.location.href='<s:url action="add-brandmanage" />'" value="添加品牌"> &nbsp;&nbsp;
			  <input name="query" type="button" class="frm_btn" id="query" onClick="javascript:window.location.href='<s:url action="query-brandmanage" />'" value=" 查 询 "> &nbsp;&nbsp;
            </td>
          </tr>
        </table></td>
    </tr>
  </table>
  </form>
</body>
</html>