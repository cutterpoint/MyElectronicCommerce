<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" %>
<%@  taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<title>产品列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../css/vip.css" type="text/css">
<script type="text/javascript">
	
	//到指定的分页页面
	function topage(page)
	{
		document.getElementById("page").value = page;
		var obj = document.getElementsByName("form1").item(0).submit();
	}
	
	/**
	*这个方法是为了把这个函数提交到相应的action里面去，也就是提交表单
	*/
	function actionEvent(methodname)
	{
		var form = document.getElementById("form1");
		if(validateIsSelect(form.all, form.productids))
		{
			form.action="<s:url action='productmanage-"+ methodname +"' />";
			form.submit();
		}
		else
		{
			alert("请选择要操作的记录");
		}
	}
	
	function allselect()
	{
		var selectall = document.getElementsByName("all").item(0);	//获取得到全选按钮
		var selecteveryone = document.getElementsByName("productids");
		//判断全选按钮是否被选中
		//如果被选中了，那么我们用循环进行和全选复选框同步为一样的
		for(var i = 0; i < selecteveryone.length; ++i)
		{
			//吧选项一个一个地进行同步一样的
			selecteveryone[i].checked = selectall.checked;
		}
	}
	
	/*
	 * 判断是否选择了记录
     */
	function validateIsSelect(allobj,items){
	    var state = allobj.checked;
	    if(items.length){
	    	for(var i=0;i<items.length;i++){
	    		if(items[i].checked) return true;
	    	}
	    }else{
	    	if(items.checked) return true;
	    }
	    return false;
	}
//-->
</script>
<SCRIPT type="text/javascript" src="../js/FoshanRen.js"></SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" marginwidth="0" marginheight="0">
<s:form name="form1" id="form1" action="productlist" method="post">
<s:hidden id="page" name="page"/>
<s:hidden name="query"/>
<s:hidden name="name"/>
<s:hidden name="startbaseprice"/>
<s:hidden name="endbaseprice"/>
<s:hidden name="startsellprice"/>
<s:hidden name="endsellprice"/>
<s:hidden name="code"/>
<s:hidden name="brandid"/>
  <table width="98%" border="0" cellspacing="1" cellpadding="2" align="center">
    <tr ><td colspan="10"  bgcolor="6f8ac4" align="right">
    	<%@ include file="../share/fenye.jsp" %>
   </td></tr>
    <tr>
      <td width="5%" bgcolor="6f8ac4"><div align="center"><font color="#FFFFFF">产品ID</font></div></td>
      <td width="10%" bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">货号</font></div></td>
      <td width="5%" nowrap bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">修改</font></div></td>
      <td width="30%" bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">产品名称</font></div></td>
	  <td width="10%" nowrap bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">所属分类</font></div></td>
	  <td width="5%" bgcolor="6f8ac4"><div align="center"><font color="#FFFFFF">底价</font></div></td>
	  <td width="5%" bgcolor="6f8ac4"><div align="center"><font color="#FFFFFF">销售价</font></div></td>
	  <td width="10%" bgcolor="6f8ac4"><div align="center"><font color="#FFFFFF">在售</font></div></td>
	  <td width="10%" bgcolor="6f8ac4"><div align="center"><font color="#FFFFFF">推荐</font></div></td>
	  <td width="10%" bgcolor="6f8ac4"><div align="center"><font color="#FFFFFF">产品图片</font></div></td>
    </tr>
<!---------------------------LOOP START------------------------------>
<s:iterator value="#request.pageView.records" var="entry">
    <tr>
      <td bgcolor="f5f5f5"> 
      	<div align="center">
      		<INPUT TYPE="checkbox" NAME="productids" value="<s:property value="#entry.id" />">
      		<s:property value="#entry.id" />
      	</div>
      </td>
      <td bgcolor="f5f5f5"> 
      		<div align="center">
      			<s:property value="#entry.code" />
      		</div>
      </td>
      <td bgcolor="f5f5f5"> 
      		<div align="center">
      			<a href="edit-productmanage?productid=<s:property value="#entry.id"/>">
	  				<img src="../images/edit.gif" width="15" height="16" border="0" />
	  			</a>
	  		</div>
	  </td>
	  <td bgcolor="f5f5f5"> 
	  		<div align="center">
	  			<s:property value="#entry.name" />
	  		</div>
	  </td>
	  <td bgcolor="f5f5f5"> 
			<div align="center">
				<s:property value="#entry.type.name" />
			</div>
	  </td>
	  <td bgcolor="f5f5f5"> 
	  		<div align="center">
	  			<s:property value="#entry.baseprice" />
	  		</div>
	  </td>
	  <td bgcolor="f5f5f5"> 
	  		<div align="center">
	  			<s:property value="#entry.sellprice" />
	  		</div>
	  </td>
	  <td bgcolor="f5f5f5" align="center">
	  		<s:if test="#entry.visible">在售</s:if>
	  		<s:if test="!#entry.visible">停售</s:if>
	  </td>
	  <td bgcolor="f5f5f5" align="center">
	  		<s:if test="#entry.commend">推荐</s:if>
	  		<s:if test="!#entry.commend">--</s:if>
	  </td>
	   <td bgcolor="f5f5f5"> 
	   		<div align="center">
	   			<a href="productstylelist?productid=<s:property value="#entry.id"/>">
	   				产品图片管理
	   			</a>
	   		</div>
	   </td>
	</tr>
</s:iterator>
    <!----------------------LOOP END------------------------------->
    <tr> 
      <td bgcolor="f5f5f5" colspan="10" align="center"><table width="100%" border="0" cellspacing="1" cellpadding="3">
          <tr> 
            <td width="8%"><INPUT TYPE="checkbox" NAME="all" <s:if test="%{#request.pageView.records.size < 1}">disabled="disabled"</s:if>
             onclick="javascript:allselect()">全选</td>
              <td width="85%">
              <input type="button" class="frm_btn" onClick="javascript:window.location.href='<s:url action="add-productmanage"/>'" value="添加产品"> &nbsp;&nbsp;
			  <input name="query" type="button" class="frm_btn" id="query" onClick="javascript:window.location.href='<s:url action="query-productmanage"/>'" value=" 查 询 "> &nbsp;&nbsp;
              <input name="visible" type="button" class="frm_btn" 
              	<s:if test="%{#request.pageView.records.size < 1}">disabled="disabled"</s:if>
               onClick="javascript:actionEvent('visible')" value=" 上 架 "> &nbsp;&nbsp;
              <input name="disable" type="button" class="frm_btn"
              	<s:if test="%{#request.pageView.records.size < 1}">disabled="disabled"</s:if>
               onClick="javascript:actionEvent('disable')" value=" 下 架 "> &nbsp;&nbsp;
              <input name="commend" type="button" class="frm_btn"
              	<s:if test="%{#request.pageView.records.size < 1}">disabled="disabled"</s:if>
               onClick="javascript:actionEvent('commend')" value=" 推 荐 "> &nbsp;&nbsp;
              <input name="uncommend" type="button" class="frm_btn"
              	<s:if test="%{#request.pageView.records.size < 1}">disabled="disabled"</s:if>
               onClick="javascript:actionEvent('uncommend')" value=" 不推荐 " /> &nbsp;&nbsp;
            </td>
          </tr>
        </table></td>
    </tr>
  </table>
</s:form>
</body>
</html>