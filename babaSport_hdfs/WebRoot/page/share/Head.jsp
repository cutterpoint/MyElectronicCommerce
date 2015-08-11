<%@ page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div id="Head">
  <div id="HeadTop">
    <div id="Logo"><a href="http://www.taobao.com/" target=_top>
    	<img alt="中国最大、最安全的信管1201网上交易平台！" src="images/global/logo.gif" border=0 /></a> 
    </div>
    <div id="HeadNavBar">
      <ul>
        <li class="NoSep"><a id="MyBuyCar"  href="shopping-cart" ><font color="blue"><Strong>购物车</Strong></font></a> </li>
        <%-- <li><a href="/user/reg.do?method=regUI" >新用户注册</a> </li>
        <li><a href="/user/logon.do" >用户登录</a> </li>
         <c:if test="${!empty user}"> <li><a href="/user/logout.do" >退出登录</a> </li></c:if> --%>
         <li> <a href="<s:url action="center-main" namespace="/control" />">进入后台</a>  </li> 
        <li><a href="http://www.baidu.com/">帮助中心</a> </li>
        <li class="phone">服务热线：110</li>
      </ul>
    </div>
  </div>
  <div id="ChannelMenu">
	<UL id="ChannelMenuItems">
		<LI id="MenuHome"><a href="http://www.babasport.com"><span>首页</span></a></LI>
		<LI id="ProducType1Home"><a href="frontProductlist?typeid=116"><span>日常生活用品</span></a></LI>
		<LI id="ProducType2Home"><a href="frontProductlist?typeid=112"><span>大家电</span></a></LI>
		<LI id="ProducType3Home"><a href="frontProductlist?typeid=111"><span>珠宝饰品</span></a></LI>
		<LI id="ProducType4Home"><a href="frontProductlist?typeid=110"><span>鞋包</span></a></LI>
		<LI id="ProducType5Home"><a href="frontProductlist?typeid=105"><span>电子数码产品</span></a></LI>
		<LI id="ProducType6Home"><a href="frontProductlist?typeid=82"><span>男装</span></a></LI>
		<LI id="ProducType8Home"><a href="frontProductlist?typeid=81"><span>女装</span></a></LI>
		<LI id="MyAccountHome"><a href="/"><span>我的账户(有待完善)</span></a></LI>
	</UL>
<!--  SearchBox -->
<div id="SearchBox">
	  <div id="SearchBoxTop">
		  <div id="SearchForm">
			<form action="##" method="post" name="search" id="search">

			 <span class="name">商品搜索: </span><input id="word" name="word" accesskey="s" size="100" maxlength="100" value="有待完善"/>

			  <input type="submit" value="搜 索" id="DoSearch"/>
			</form>
		  </div>
	  </div>
      <div id="HotKeywords">
			<ul>
				<li><span>
					<input type="date" id="time" />&nbsp;&nbsp;
您好<SCRIPT type="text/javascript" src="js/welcome.js"></SCRIPT>，欢迎来到信管1201！</span></li>
				<!-- <li>热门搜索：</li>
				
<li><a href="/q?word=%E5%B8%90%E7%AF%B7">帐篷</a></li>
<li><a href="/q?word=%E7%91%9C%E4%BC%BD%E7%90%83">瑜伽球</a></li>
<li><a href="/q?word=%E8%BF%9C%E9%98%B3%E7%91%9C%E4%BC%BD%E6%9C%8D">远阳瑜伽服</a></li> -->

			</ul>
      </div>
   </div>
</div><!-- End SearchBox -->
</div>
<!-- Head End -->