<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<div class="site-header">
    <spring:message code="${param.code}" var="titleString"/>
    <h3>
        ${titleString}
    </h3>
    <img class="header-image" src="${pageContext.request.contextPath}/resources/images/bannerart1.jpg"
         alt="${titleString}">
</div>
