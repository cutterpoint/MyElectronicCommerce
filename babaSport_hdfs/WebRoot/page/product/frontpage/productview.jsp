<%@ page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/global/header01.css" rel="stylesheet" type="text/css" />
<link href="css/product/product.css" rel="stylesheet" type="text/css" />
<link href="css/global/topcommend.css" rel="stylesheet" type="text/css" />
<SCRIPT type="text/javascript" src="js/FoshanRen.js" ></SCRIPT>
<SCRIPT type="text/javascript" src="js/xmlhttp.js" ></SCRIPT>
<title>${product.name}-巴巴运动网</title>
<meta name="Keywords" content="${product.name}" />
<META name="description" content="" /> 
<SCRIPT type="text/javascript">
<!--

	//初始化界面
	function InitPage()
	{
		giveGood();
		historyaccess();
	}
	
	//用ajax进行一步提交,为了显示浏览历史
	function historyaccess()
	{
		var historyaccess = document.getElementById("historyaccess");	//获取要显示的地方的标签id名
		if(historyaccess) 	//只要获取的标签存在，那么我们就进行一步请求
		{
			historyaccess.innerHTML="数据正在加载...";
			//开始异步请求
			send_request(
							function(value)		//异步请求要执行的操作
							{
								historyaccess.innerHTML=value;
							},
							"<s:url action='switch-viewHistory' />",	//异步请求的路径
							true  		//是否返回数据
						);
		}
	}
	
	//ajax进行精品推荐的功能
	function giveGood()
	{
		var form = document.getElementById("cart");
		var typeid = form.typeid.value;
		var giveGood = document.getElementById("giveGood"); 		//获取要进行异步请求的对象
		if(giveGood && typeid!="")  //只要获取的标签存在，那么我们就进行一步请求
		{
			giveGood.innerHTML="卧槽！！数据被我吃了！！！";
			//开始异步请求
			send_request(
							function(value)	  //异步请求的要执行的操作函数
							{
								giveGood.innerHTML=value;
							},
							"<s:url action='switch-topsell'/>?&typeid="+ typeid,
							true
						);
		}
	}

	function styleEvent(styleid)
	{
		
		var productImage = document.getElementById("productImage_"+styleid);
		if(productImage)
		{
			var main_image = document.getElementById("main_image");
			main_image.style.background="url("+productImage.value+") center center no-repeat";
		}
	}
	function bigImageBrowse()
	{
		var form = document.getElementById("cart");
		var styleid = form.styleid.value;
		var productPrototypeImage = document.getElementById('productPrototypeImage_'+ styleid);		
		if(productPrototypeImage)
		{
			var path = "<s:url action="switch-showimage" />?path="+ productPrototypeImage.value;			
			window.open (path ,"显示图片");
		}
	}
//-->
</SCRIPT>
</head>
<body onload="javascript:InitPage()">

	<jsp:include page="/page/share/Head.jsp"/>
	<div id="ContentBody"><!-- 页面主体 --> 
		<s:set name="out" value=''  />  <!-- 这个是定义了一个变量 -->
		<s:iterator value="#request.types" var="type"> 
			<s:set name="out" var="out" value="#out+'&gt;&gt;'+#type.name+'</a>'" />
		</s:iterator>
		 <div id="position"> 您现在的位置：<a href="/" name="linkHome">信管1201</a> 
			 <span id="uc_cat_spnPath">
				 <s:property value="#out" escapeHtml="false" />
			 </span>
		 </div>
	 <div class="browse_left"><!-- 页面主体 左边 --> 
	        <!-- 浏览过的商品 -->
		 <div class="browse">
		      <div class="browse_t">您浏览过的商品</div>
		      <ul id="historyaccess"></ul>
		 </div>
		<!--精品推荐 start --> 
		<DIV id="topcommend" align="left">
		       <DIV id="newtop"><IMG height="13" src="images/global/sy2.gif" width="192" /></DIV>
		       <DIV id="newlist">
			  <DIV id="newmore">
			    <DIV class="title">精品推荐</DIV>
			  </DIV>
				<span id="commenddetail">
				</span>
				<ul id="giveGood"></ul>
			</DIV>
		</DIV>
	</div><!-- 页面主体 左边end -->
		
	<div id="Right" ><!-- 页面主体 右边 -->
	<form action="<s:url action="shopping-cart" />" method="post" name="cart" id="cart">
		<INPUT type="hidden" name="productid" value="<s:property value="#request.product.id" />" />
		<INPUT type="hidden" name="typeid" value="<s:property value="#request.product.type.typeid" />" />
		<div id="browse_left">
			<s:set name="currentimage" />
			<s:set var="imagecount" value="0"/>
			<s:iterator value="#request.product.styles" var="style" >
				<s:if test="#style.visible==true">
					<s:set name="currentimage" value="#style" />
					<s:set name="imagecount" var="imagecount" value="#imagecount+1" />
				</s:if>
			</s:iterator>
			<div class="right_left">
				<div id="main_image" onclick="JavaScript:bigImageBrowse(<s:property value="#currentimage.id" />)" 
							style="cursor:hand;background:url(<s:property value="#currentimage.image140FullPath" />) center center no-repeat">
					<img src="images/global/product_blank.gif" width="200" height="240"/>
				</div>
				<!-- 查看大图 -->
				<img src="images/global/zoom+.gif" onclick="JavaScript:bigImageBrowse(<s:property value="#currentimage.id" />)" style="cursor:hand;"/>
			</div>
		
			<div class="right_right">									
				<div class="right_title">
					<b>${product.name}</b>
				</div>
				<div class="right_desc">
					<ul>
						<li class="li2">商品编号：A${product.id}<font color="#CC0000">（电话订购专用）</font></li>
						<s:if test="#request.product.brand!=null"><li>品牌：${product.brand.name} </li>	</s:if>																																		
					</ul>
				</div>
				<div class="right_desc">
					<s:if test="#imagecount==1">
						<INPUT type="hidden" name="styleid" value="<s:property value="#currentimage.id" />" />
						<li>颜色：<s:property value="#currentimage.name" /></li>
						<INPUT type="hidden" id="productImage_<s:property value="#currentimage.id" />" value="<s:property value="#currentimage.image140FullPath" />" />
						<INPUT type="hidden" id="productPrototypeImage_<s:property value="#currentimage.id" />" value="<s:property value="#currentimage.imageFullPath" />" />
					 </s:if>
					<s:if test="#imagecount>1">
						<img src="images/global/init.gif" width="0" height="0" />
						<li>颜色：
							<select name="styleid" onchange="javascript:styleEvent(this.value)">
								<s:iterator value="#request.product.styles" var="style">
									<s:if test="#style.visible==true">
										<option value="<s:property value="#style.id" />" <s:if test="#style.id == #currentimage.id">selected="selected"</s:if>> 
											<s:property value="#style.name" />
										</option>
									</s:if>
								</s:iterator>
							</SELECT>
						</li>
						<s:iterator value="#request.product.styles" var="style">
							<s:if test="#style.visible==true">
								<INPUT type="hidden" id="productImage_<s:property value="#style.id" />" value="<s:property value="#style.image140FullPath" />" />
								<INPUT type="hidden" id="productPrototypeImage_<s:property value="#style.id" />" value="<s:property value="#style.imageFullPath" />" />
							</s:if>
						</s:iterator>
					 </s:if>
				</div>
				<ul>
					<li>市场价：
						<s>${product.marketprice}</s> 元 
						<font color='#ff6f02'>本站价：
							<b>${product.sellprice} 元</b>
						</font> 节省：<font color='#ff6f02'>${product.savedPrice }</font> 元										
					</li>
				  	<li class="right_img">
				  	<!-- 购买按钮 -->
				  		<INPUT type="image" src="images/global/sale.gif" />
				  	</li>
					<li class="guopiprice">[ 
						<IMG src="images/global/2j4.gif" border="0" />&nbsp;
						<A href="http://www.babasport.com/cache/news/6/9.shtml" target="_blank">配送说明</A> 
						]&nbsp;&nbsp;&nbsp;&nbsp;[ 
						<IMG src="images/global/2j4.gif" border="0" />&nbsp;
						<A href="http://www.babasport.com/cache/news/4/24.shtml" target="_blank">付款方式</A> 
					]</li>
				</ul>									
			</div>
		</div>
		<div id="browse_right">
			<div id="sy_biankuang">
				<div class="sy_xinpintuijian_font">本站尚未开张</div>
				<div class="sy_dianhua" style="line-height:150%">
					<font color="#FF0000">
					全国：010-6466 3070
					</font>
					<br>MSN在线客服：babasport@sohu.com</br>	
					<font color="#3A8FAF">QQ在线客服：523429525</font>
				</div>
			</div>
		</div>
	</form>
	<div class='right_blank'></div>
	<div class='right_title1'>商品说明</div>
	<div class='right_content'>${product.description}</div>
	</div><!-- 页面主体 右边 end -->
	</div><!-- 页面主体 end -->
	<jsp:include page="/page/share/Foot.jsp" />
</body>
</html>