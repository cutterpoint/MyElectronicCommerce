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
<form id="form11" name="form1" action="producttypelist" method="post">
<s:hidden id="page" name="page" />
<s:hidden name="parentid" />
<s:hidden name="query" />
<s:hidden name="name" />
  <table width="98%" border="0" cellspacing="1" cellpadding="2" align="center">
    <tr ><td colspan="6"  bgcolor="6f8ac4" align="right" >
   	<%@ include file="../share/fenye.jsp" %>
    </td></tr>
    <tr>
      <td width="8%" bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">代号</font></div></td>
      <td width="5%" nowrap bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">修改</font></div></td>
      <td width="20%" bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">产品类别名称</font></div></td>
	  <td width="10%" nowrap bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">创建下级类别</font></div></td>
	  <td width="15%" bgcolor="6f8ac4"><div align="center"><font color="#FFFFFF">所属父类</font></div></td>
	  <td nowrap bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">备注</font></div></td>
    </tr>
<!---------------------------LOOP START------------------------------>
<s:iterator value="#request.pageView.records" var="productType">
    <tr>
      <td bgcolor="f5f5f5"> 
      	<div align="center"><s:property value="#productType.typeid"/> </div>
      </td>
      <td bgcolor="f5f5f5"> 
      	<div align="center">
      		<a href="<s:url action="edit-producttypemanage" />?typeid=<s:property value="#productType.typeid"/>">
	  			<img src="../images/edit.gif" width="15" height="16" border="0">
	  		</a>
	  	</div>
	  </td>
      <td bgcolor="f5f5f5"> 
      	<div align="center">
      		<a href="producttypelist?parentid=<s:property value="#productType.typeid"/>">
      			<s:property value="#productType.name"/>
      			<font color="red">
	      			<s:if test="#productType.childtypes.size > 0">
	      				（有<s:property value="#productType.childtypes.size" />个子类）
	      			</s:if>
      			</font>
      		</a> 
      	</div>
      </td>
	  <td bgcolor="f5f5f5"> 
	  	<div align="center">
	  		<a href="<s:url action="add-producttypemanage" />?parentid=<s:property value="#productType.typeid"/>">创建子类别</a>
	  	</div>
	  </td>
	  <td bgcolor="f5f5f5" align="center">
	  	<s:if test="%{#productType.parent != null}"><s:property value="#productType.parent.name"/></s:if> 
	  </td>
	  <td bgcolor="f5f5f5">
	  	<s:property value="#productType.note"/>
	  </td>
	</tr>
</s:iterator>
    <!----------------------LOOP END------------------------------->
    <tr> 
      <td bgcolor="f5f5f5" colspan="6" align="center"><table width="100%" border="0" cellspacing="1" cellpadding="3">
          <tr> 
            <td width="5%"></td>
              <td width="85%">
              <input name="AddDic" type="button" class="frm_btn" id="AddDic" onClick="javascript:window.location.href='<s:url action="add-producttypemanage" />?parentid=${param.parentid}'" value="添加类别"> &nbsp;&nbsp;
			  <input name="query" type="button" class="frm_btn" id="query" onClick="javascript:window.location.href='<s:url action="query-producttypemanage" />'" value=" 查 询 "> &nbsp;&nbsp;
            </td>
          </tr>
        </table></td>
    </tr>
  </table>
  </form>
</body>
</html>