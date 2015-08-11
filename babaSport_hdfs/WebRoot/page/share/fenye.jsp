<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
	总记录数：${pageView.totalrecords }　|　每页显示:${pageView.maxresult }条　|　总页数:${pageView.totalpage }　
	<s:iterator begin="%{#request.pageView.pageindex.startpage}" end="%{#request.pageView.pageindex.endpage}" var="index">
		<s:if test="#request.pageView.currentpage == #index"><b>第${index}页　</b></s:if>
		<s:else><a href="#" onclick="topage(${index })" class="a03">第${index}页　</a></s:else>
	</s:iterator>
<%-- 		
		<s:bean name="org.apache.struts2.util.Counter" id="nunm">
			<s:param name="first" value="#request.pageindex.startpage" />
			<s:param name="last" value="#request.pageindex.endpage" />
			<s:iterator>
				第<s:property />页　
			</s:iterator>
		</s:bean> 
--%>

