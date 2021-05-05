<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        <spring:message code="jobPost.create.success.title" var="text"/>
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
    <link href="${pageContext.request.contextPath}/resources/css/createjobpoststeps.css" rel="stylesheet"/>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/icon.svg">
    <link rel="apple-touch-icon" href="${pageContext.request.contextPath}/resources/images/apple-touch-icon.png">
</head>
<body>
<c:set var="path" value="/create-job-post" scope="request"/>
<c:set var="zoneValues" value="${zoneValues}" scope="request"/>
<%@include file="components/customNavBar.jsp" %>

<div class="content-container-transparent mt-3">
    <h3>
        <img style="height: 30px; padding-bottom: 5px" src="<c:url value="/resources/images/add-1.svg"/>"
             alt="<spring:message code="post.create.plus"/>">
        <spring:message code="jobPost.create.tileExtended"/>
    </h3>

    <div class="step-frame">
        <div style="padding: 40px; display: flex; flex-direction: column; align-items: center; justify-content: center"
             class="step-container">
            <img class="mb-4" style="height: 150px" src="<c:url value="/resources/images/success1.svg"/>"
                 alt="<spring:message code="post.create.success.alt"/>"/>
            <h4 class="mb-4">
                <spring:message code="post.create.success"/>
            </h4>

            <c:url value="/job/${postId}" var="getJobPath"/>
            <a href='${getJobPath}' class="btn btn-primary text-uppercase">
                <spring:message code="post.create.redirect"/>
            </a>
        </div>

        <div class="add-package-reminder">
            <div class="d-flex justify-content-center align-items-center">
                <div>
                    <img src="<c:url value="/resources/images/package1.svg"/>"
                         alt="<spring:message code="post.create.image"/>"/>
                </div>
                <div class="ml-3 d-flex flex-column align-items-center">
                    <p>
                        <spring:message code="post.create.more"/>
                    </p>

                    <c:url value="/job/${postId}/packages" var="getPackagesPath"/>
                    <a href="${getPackagesPath}" class="btn btn-primary text-uppercase">
                        <spring:message code="post.create.add"/>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
