<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
	<s:iterator value="#request.viewHistory" var="vireproduct" status="statu">
		<li class="bj_blue"> 
			<a href="<s:url action="viewproduct" />?productid=<s:property value="#vireproduct.id" />" target="_blank" title="<s:property value="#vireproduct.name" />"><s:property value="#vireproduct.name" /></a>
		</li>
	</s:iterator>

	