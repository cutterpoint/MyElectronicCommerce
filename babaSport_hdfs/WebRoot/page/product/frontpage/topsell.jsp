<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<UL>
	<s:iterator value="#request.topsellproducts" var="topsellproduct" status="statu">
		<li class="bx"><s:property value="#statu.count"/>.<a href="<s:url action="viewproduct" />?productid=<s:property value="#topsellproduct.id" />" target="_blank"><s:property value="#topsellproduct.name"/></a></li>
	</s:iterator>
</UL>