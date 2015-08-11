<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
<title>产品样式显示</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../css/vip.css" type="text/css">
<script type="text/javascript">
	//到指定的分页页面
	function topage(page){
		var form = document.forms[0];
		form.page.value=page;
		form.submit();
	}
	
	function allselect()
	{
		var selectall = document.getElementsByName("all").item(0);	//获取得到全选按钮
		var selecteveryone = document.getElementsByName("stylesids");
		//判断全选按钮是否被选中
		//如果被选中了，那么我们用循环进行和全选复选框同步为一样的
		for(var i = 0; i < selecteveryone.length; ++i)
		{
			//吧选项一个一个地进行同步一样的
			selecteveryone[i].checked = selectall.checked;
		}
	}
	
	/**
	*这个方法是为了把这个函数提交到相应的action里面去，也就是提交表单
	*/
	function actionEvent(methodname)
	{
		var form = document.getElementById("form1");
		if(validateIsSelect(form.all, form.stylesids))
		{
			form.action="<s:url action='productstylemanage-"+ methodname +"' />";
			form.submit();
		}
		else
		{
			alert("请选择要操作的记录");
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
</script>
<SCRIPT type="text/javascript" src="../js/FoshanRen.js"></SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" marginwidth="0" marginheight="0">
<s:form id="form1" action="productstylelist" method="post">
<s:hidden name="productid" />
  <table width="98%" border="0" cellspacing="1" cellpadding="2" align="center">
    <tr>
      <td width="8%" nowrap bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">选择</font></div></td>
      <td width="8%" nowrap bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">修改</font></div></td>
      <td width="37%" bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">名称</font></div></td>
      <td width="10%" bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">在售状态</font></div></td>
	  <td width="37%" nowrap bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">产品图片</font></div></td>
    </tr>
<!---------------------------LOOP START------------------------------>
<s:iterator value="#request.styles" var="entry">
    <tr>
      <td bgcolor="f5f5f5"> 
	      <div align="center">
	      	<INPUT TYPE="checkbox" NAME="stylesids" value="<s:property value="#entry.id" />" />
	      </div>
	  </td>
      <td bgcolor="f5f5f5"> 
      	<div align="center">
      		<a href="<s:url action="edit-productstylemanage"/>?productstyleid=<s:property value="#entry.id" />">
	  			<img src="../images/edit.gif" width="15" height="16" border="0">
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
      		<s:if test="%{#entry.visible}">在售</s:if>
      		<s:if test="%{!#entry.visible}">停售</s:if>
      	</div>
      </td>
	  <td bgcolor="f5f5f5"> 
	  	<div align="center">
	  		<img src="../<s:property value="#entry.imageFullPath" />" width="50">
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
              <input type="button" class="frm_btn" 
              		onClick="javascript:window.location.href='<s:url action="add-productstylemanage"/>?productid=<s:property value="productid" />'" value="添加产品图片"> &nbsp;&nbsp;
           	  <input type="button" class="frm_btn" 
             		onClick="javascript:actionEvent('visible')" value="产品样式上架"> &nbsp;&nbsp;
              <input type="button" class="frm_btn" 
             		onClick="javascript:actionEvent('disable')" value="产品样式下架"> &nbsp;&nbsp;
            </td>
          </tr>
        </table></td>
    </tr>
  </table>
</s:form>
</body>
</html>