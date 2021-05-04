<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>
        <spring:message code="token.view.title" var="text"/>
        <spring:message code="title.name" arguments="${text}"/>
    </title>

    <!-- Bootstrap 4.5.2 CSS minified -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <!-- jQuery 3.6.0 minified dependency for Bootstrap JS libraries -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"
            integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <!-- Bootstrap 4.5.2 JS libraries minified -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
            integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
            crossorigin="anonymous"></script>
    <script src="https://kit.fontawesome.com/108cc44da7.js" crossorigin="anonymous"></script>

    <link href="${pageContext.request.contextPath}/resources/css/styles.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/resources/css/contractsubmitted.css" rel="stylesheet"/>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/icon.svg">
    <link rel="apple-touch-icon" href="${pageContext.request.contextPath}/resources/images/apple-touch-icon.png">
</head>
<body>
<c:set var="zoneValues" value="${zoneValues}" scope="request"/>
<%@include file="components/customNavBar.jsp" %>
<div class="content-container-transparent page">
    <div class="message">
        <c:choose>
            <c:when test="${success}">
                <img src='<c:url value="/resources/images/emailconfirmed.svg" />'
                     alt="<spring:message code="token.view.email.alt"/>"
                     class="image"/>
                <h1 class="title">
                    <spring:message code="token.view.email.success"/>
                </h1>
                <p class="text">
                    <spring:message code="token.view.email.text"/>
                </p>
                <a type="button" class="btn btn-primary" style="display: block;margin: 10px auto; width: 100px"
                   href="${pageContext.request.contextPath}/login"><spring:message
                        code="navigation.login"/></a>
            </c:when>
            <c:when test="${send}">
                <img src='<c:url value="/resources/images/emailnotification.svg" />'
                     alt="<spring:message code="token.view.email.alt2"/>"
                     class="image"/>
                <h1 class="title">
                    <spring:message code="token.view.register.success"/>
                </h1>
                <p class="text">
                    <spring:message code="token.view.email.sent"/>
                </p>
            </c:when>
            <c:when test="${resend}">
                <img src='<c:url value="/resources/images/emailnotification.svg" />'
                     alt="<spring:message code="token.view.email.alt2"/>"
                     class="image"/>
                <h1 class="title">
                    <spring:message code="token.view.user.notverified"/>
                </h1>
                <p class="text">
                    <spring:message code="token.view.email.registered"/><br/>
                    <spring:message code="token.view.email.sent"/>
                </p>
            </c:when>
            <c:when test="${expired}">
                <img src='<c:url value="/resources/images/warning-sign.svg" />'
                     alt="<spring:message code="token.view.code.warning"/>"
                     class="image"/>
                <h1 class="title">
                    <spring:message code="token.view.code.expired"/>
                </h1>
                <p class="text">
                    <spring:message code="token.view.code.invalid"/><br/>
                    <spring:message code="token.view.code.again"/>
                </p>
            </c:when>
        </c:choose>
        <a type="submit" class="btn btn-primary" href="${pageContext.request.contextPath}/" role="button">
            <spring:message code="button.return"/></a>
    </div>
</div>
<jsp:include page="components/footer.jsp"/>
</body>
</html>
