<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        <spring:message code="contract.success.page.title"/>
    </title>
    <link href="${pageContext.request.contextPath}/resources/css/styles.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/resources/css/contractSubmitted.css" rel="stylesheet"/>

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
</head>
<body>
    <%@ include file="customNavBar.jsp" %>
    <div class="content-container-transparent page">
        <div class="message">
            <img src='<c:url value="${pageContext.request.contextPath}/resources/images/thumbs.svg" />'
                 alt="<spring:message code="contract.success.image"/>"
            class="image"/>
            <h1 class="title">
                <spring:message code="contract.success.message"/>
            </h1>
            <p class="text">
                <spring:message code="contract.success.text"/>
            </p>
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/" role="button" type="submit">
                <spring:message code="button.return"/>
            </a>
        </div>
    </div>
</body>
</html>
