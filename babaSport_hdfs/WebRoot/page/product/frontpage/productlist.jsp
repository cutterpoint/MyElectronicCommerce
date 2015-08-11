<%@ page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head><title>${producttype.name} 巴巴运动网</title>    
	<link href="css/global/header01.css" rel="stylesheet" type="text/css" />
	<link href="css/product/list.css" rel="stylesheet" type="text/css" />	
	<link href="css/global/topsell.css" rel="stylesheet" type="text/css" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="Keywords" content="${producttype.name}" />
	<META name="description" content="${producttype.note}" />
<SCRIPT type="text/javascript" src="js/xmlhttp.js"></SCRIPT>
<SCRIPT type="text/javascript" >
	
	function getTopSell(typeid)
	{
		var salespromotion = document.getElementById('salespromotion');	
		if(salespromotion && typeid!="")	//判断是否存在
		{
			
			salespromotion.innerHTML= "数据正在加载...";
			//异步请求
			send_request(function(value){ salespromotion.innerHTML=value;},
					 "<s:url action='switch-topsell'/>?&typeid="+ typeid, true);
		}
	}
	function getViewHistory()
	{
		var viewHistoryUI = document.getElementById('viewHistory');		
		if(viewHistoryUI)
		{
			viewHistoryUI.innerHTML= "数据正在加载...";
			send_request(function(value){ viewHistoryUI.innerHTML=value;},
					 "<s:url action='switch-viewHistory'/>", true);
		}
	}
	function pageInit()	//用来初始化页面
	{
		getTopSell("${producttype.typeid}");
		getViewHistory();
		getTime()
	}
	
	function getTime()
	{
		var inputtime = document.getElementById("time");
		var myDate = new Date();
		myDate.getFullYear();  //获取完整的年份(4位,1970-????)
  		myDate.getMonth();      //获取当前月份(0-11,0代表1月)
   		myDate.getDate();      //获取当前日(1-31)
		var time = myDate.toLocaleString();
		inputtime.value = time;
	}
</SCRIPT>
</head>

<body class="ProducTypeHome2" onload="javascript:pageInit()">
	<jsp:include page="/page/share/Head.jsp"/>
	<s:set name="out" value='' />  <!-- 这个是定义了一个变量 -->
	<s:iterator value="#request.types" var="type"> 
		<s:set name="out" var="out" value="#out+'&laquo;'+#type.name+'</a>'" />
	</s:iterator>
    <div id="position">您现在的位置: <a href="/" name="linkHome">巴巴运动网</a> 
    	<s:property value="#out" escapeHtml="false" />（<s:property value="#request.pageView.totalrecords" />个）
	</div>

    <!--页面左侧分类浏览部分-->
    <div class="browse_left">
         <div class="browse">
            <div class="browse_t">${producttype.name}</div>
				<h2><span class="gray">浏览下级分类</span></h2>
				<ul>
					<s:iterator value="#request.producttype.childtypes" var="childtype">
						<li class='bj_blue'> 
							<a href="<s:url action="frontProductlist"/>?typeid=<s:property value="#childtype.typeid" />"><s:property value="#childtype.name" /></a>
						</li>
					</s:iterator>
				</ul>
	     </div>
<DIV id="sy_biankuang">
        <DIV class="lanmu_font">最畅销${producttype.name}</DIV>
        <DIV style="PADDING-LEFT: 10px; COLOR: #333333" id="salespromotion">
        </DIV>
</DIV>
		 <br/>
		 <div class="browse">
	          <div class="browse_t">您浏览过的商品</div>
			  <ul id="viewHistory">
			  </ul>
	     </div>
    </div>
    <!--页面右侧分类列表部分开始-->
    <div class="browse_right">
         <div class="select_reorder">
              <div class="reorder_l">请选择排序方式： 
              <s:if test="%{'selldesc' == pf.sort}"><strong><em>销量多到少</em></strong></s:if>
              <s:if test="%{'selldesc' != pf.sort}">
              	<a title='按销量降序' href="<s:url action="frontProductlist" />?sort=selldesc&
              										typeid=<s:property value="#parameters.typeid" />&
              										showstyle=<s:property value="#parameters.showstyle" />">销量多到少</a>
              </s:if>
			  | 
			  <s:if test="%{'sellpricedesc' == pf.sort}"><strong><em>价格高到低</em></strong></s:if>
              <s:if test="%{'sellpricedesc' != pf.sort}">
              	<a title='按销量降序' href="<s:url action="frontProductlist" />?sort=sellpricedesc&
              										typeid=<s:property value="#parameters.typeid" />&
              										showstyle=<s:property value="#parameters.showstyle" />">价格高到低</a>
              </s:if>
			  | 
			  <s:if test="%{'sellpriceasc' == pf.sort}"><strong><em>价格低到低</em></strong></s:if>
			  <s:if test="%{'sellpriceasc' != pf.sort}">
			  	<a title='价格低到高' href="<s:url action="frontProductlist" />?sort=sellpriceasc&
			  										typeid=<s:property value="#parameters.typeid" />&
			  										showstyle=<s:property value="#parameters.showstyle" />">价格低到高</a>
			  </s:if>
			  | 
			  <s:if test="%{pf.sort == ''}"><strong><em>最近上架时间</em></strong></s:if>
			  <s:if test="%{pf.sort != ''}">
			  	<a title='价格低到高' href="<s:url action="frontProductlist" />?sort=&
			  										typeid=<s:property value="#parameters.typeid" />&
			  										showstyle=<s:property value="#parameters.showstyle" />">最近上架时间</a>
			  </s:if> 
		 </div>
              
		      <div class="reorder_r">显示方式：
		    <%--   <s:if test="showstyle=='imagetext'">
		      	test
		      </s:if> --%>
			      <s:if test="showstyle=='imagetext'">
			      	<strong><em>图文版</em></strong>
			      </s:if>
			      <s:if test="showstyle!='imagetext'">
			      	<a href="<s:url action="frontProductlist"/>?sort=<s:property value="#request.sort" />&
								typeid=<s:property value="#parameters.typeid" />&
								sex=<s:property value="#parameters.sex" />&
								brandid=<s:property value="#brand.code" />&
								showstyle=imagetext">
						图文版
					</a>
			      </s:if> |
			      <s:if test="showstyle!='image'">
			      	<a href="<s:url action="frontProductlist"/>?sort=<s:property value="#request.sort" />&
								typeid=<s:property value="#parameters.typeid" />&
								sex=<s:property value="#parameters.sex" />&
								brandid=<s:property value="#brand.code" />&
								showstyle=image">
						图片版
					</a>
			      </s:if>
			      <s:if test="showstyle=='image'">
			      	<strong><em>图片版</em></strong>
			      </s:if>
		      </div>
			<div class="emptybox"></div>
			 <div class="brand">
				<div class="FindByHint">按<strong>品牌</strong>选择：</div>
				<ul class="CategoryListTableLevel1">
					<s:iterator value="#request.brands" var="brand"> 
						<li>
							<a href="<s:url action="frontProductlist" />?sort=<s:property value="#request.sort" />
												&brandid=<s:property value="#brand.code" />
												&typeid=<s:property value="#parameters.typeid" />
												&sex=<s:property value="#parameters.sex" />&
												showstyle=<s:property value="#parameters.showstyle" />">
												<s:property value="#brand.name" />
							</a>
						</li>
					</s:iterator>
				</ul>
			 </div>
			 <div class="SubCategoryBox">
				<div class="FindByHint">按<strong>男女款</strong>选择：</div>
				<ul class="CategoryListTableLevel1">
				<li>
					<a  href="<s:url action="frontProductlist"/>?sort=sellpriceasc&
								typeid=<s:property value="#parameters.typeid" />&
								sex=MAN&brandid=<s:property value="#brand.code" />&
								showstyle=<s:property value="#parameters.showstyle" />">男款</a>
				</li>
				<li>
					<a  href="<s:url action="frontProductlist"/>?sort=sellpriceasc&
								typeid=<s:property value="#parameters.typeid" />&
								sex=WOMEN&brandid=<s:property value="#brand.code" />&
								showstyle=<s:property value="#parameters.showstyle" />">女款</a>
				</li>
				<li>
					<a  href="<s:url action="frontProductlist"/>?sort=sellpriceasc&typeid=<s:property value="#parameters.typeid" />&
								sex=NONE&brandid=<s:property value="#brand.code" />&
								showstyle=<s:property value="#parameters.showstyle" />">男女均可</a>
				</li>
				<li>
					<a class="red" href="<s:url action="frontProductlist"/>?sort=sellpriceasc&typeid=<s:property value="#parameters.typeid" />&showstyle=<s:property value="#parameters.showstyle" />">全部</a>
				</li>
				</ul>
			 </div>
		</div>
	     <div id="divNaviTop" class="number"> 
	          <div class="number_l">以下<span class='number_white'><s:property value="#request.pageView.totalrecords" /></span>条结果按<span class="number_white">
				<s:if test="%{'selldesc' == #request.sort}">
					销量多到少
				</s:if>
				<s:elseif test="%{'sellpricedesc' == #request.sort}">
					价格高到低
				</s:elseif>
				<s:elseif test="%{'sellpriceasc' == #request.sort}">
					价格低到高
				</s:elseif>
				<s:else>
					最近上架时间
				</s:else>
			  </span>
			 	 排列，显示方式是
				  <span class="number_white"><s:if test="#parameters.showstyle=='imagetext'">图文版</s:if><s:if test="#parameters.showstyle=='image'">图片版</s:if>
				  </span>　
				  每页显示
			  	<span class="number_white">${pageView.maxresult}</span>
				  条
			  </div>
		      <div class="turnpage">
                <div><em>第${pageView.currentpage}页</em></div>
		      </div>
	     </div>

	<div class='goods_pic'>
<!---------------------------LOOP START------------------------------>
<s:iterator value="#request.pageView.records" var="entry">
	<div class="detail">   
        <div class="goods" style="cursor:hand;background:url(<s:iterator value="#entry.styles" var="pic"><s:property value="#pic.image140FullPath" /> </s:iterator>) center center no-repeat">
	        <a href="<s:url action="viewproduct" />?productid=<s:property value="#entry.id" />" target="_blank">
	         	<img src="images/global/product_blank.gif" alt="<s:property value="#entry.name" />" width="140" height="168"  border="0"/>
	         </a>
         </div>
        <h2>
        	<a href="<s:url action="viewproduct" />?productid=<s:property value="#entry.id" />" target="_blank" title="<s:property value="#entry.name" />"><s:property value="#entry.name" /></a>
        </h2>
        <div class="save_number">
			<s>￥<s:property value="#entry.marketprice" /></s>　
			<strong><em>￥<s:property value="#entry.sellprice" /></em></strong>　节省：<s:property value="#entry.savedPrice" />
        </div>
        <div class="an_img" align="center">
        	<a href="<s:url action="viewproduct" />?productid=<s:property value="#entry.id" />">
        		<img src='images/sale.gif' width='84' height='24' border='0' class='a_1' />
        	</a>
        </div>
     </div>
</s:iterator>
<!----------------------LOOP END------------------------------->
		<div class='emptybox'></div>
	</div>
	     <div id="divNaviBottom" class="page_number">
	     <div class="turnpage turnpage_bottom">	
		<s:iterator begin="%{#request.pageView.pageindex.startpage}" end="%{#request.pageView.pageindex.endpage}" var="index">
			<s:if test="#request.pageView.currentpage == #index"><div class='red'>第${index}页　</div></s:if>
			<s:else>
				<div class="page">
					<a href="<s:url action="frontProductlist" />?page=${index }&
												typeid=<s:property value="#parameters.typeid" />&
												sort=<s:property value="#parameters.sort" />&
												brandid=<s:property value="#brand.code" />&
												sex=<s:property value="#parameters.sex" />&
												showstyle=<s:property value="#parameters.showstyle" />" onclick="topage(${index })" class="a03">第${index}页　</a>
				</div>
			</s:else>
		</s:iterator>
		<div>&nbsp;&nbsp;</div>
		跳转到第
		<select name="selectPage" class="kuang" onchange="javascript:topage(this.value)">
			<s:iterator begin="1" end="#request.pageView.totalpage" var="index"> 
				<option value="${index }" <s:if test="%{#request.pageView.currentpage == #index}" >selected="selected"</s:if> >
					<s:property value="#index" />
				</option>
			</s:iterator>
		</Select> 
		页
		<input type="hidden" id="typeid" value="<s:property value="#parameters.typeid" />" />
		<input type="hidden" id="sort" value="<s:property value="#parameters.sort" />" />
		<input type="hidden" id="brandid" value="<s:property value="#brand.code" />" />
		<input type="hidden" id="sex" value="<s:property value="#parameters.sex" />" />
		<input type="hidden" id="showstyle" value="<s:property value="#parameters.showstyle" />" />
		<SCRIPT type="text/javascript">
			
			function topage(pagenum)
			{
				var typeid=document.getElementById("typeid").value;
				var sort=document.getElementById("sort").value;
				var brandid=document.getElementById("brandid").value;
				var sex=document.getElementById("sex").value;
				var showstyle=document.getElementById("showstyle").value;
				window.location.href="<s:url action='frontProductlist' />?sort="
										+ sort +"&page="+ pagenum +"&typeid="+ typeid
										+ "&brandid=" + brandid + "&sex=" + sex 
										+ "&showstyle=" + showstyle;
			}
			
		</SCRIPT>
         </div>
	     </div>
    </div>
	<jsp:include page="/page/share/Foot.jsp" />
</body>
</html>