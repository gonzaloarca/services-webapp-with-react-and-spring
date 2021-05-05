<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>
        <spring:message code="error.403" var="text"/>
        <spring:message code="title.name" arguments="${text}"/>
    </title>

    <%-- Bootstrap 4.5.2 CSS minified --%>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">

    <%-- jQuery 3.6.0 minified dependency for Bootstrap JS libraries --%>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"
            integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>

    <%-- Popper libraries minified for Bootstrap compatibility --%>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
            integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
            crossorigin="anonymous"></script>

    <%-- Bootstrap 4.5.2 JS libraries minified --%>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
            integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
            crossorigin="anonymous"></script>

    <%-- FontAwesome Icons--%>
    <script src="https://kit.fontawesome.com/108cc44da7.js" crossorigin="anonymous"></script>

    <link href="${pageContext.request.contextPath}/resources/css/styles.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/resources/css/error-404.css" rel="stylesheet"/>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/icon.svg">
    <link rel="apple-touch-icon" href="${pageContext.request.contextPath}/resources/images/apple-touch-icon.png">
</head>
<body>
<c:set var="zoneValues" value="${zoneValues}" scope="request"/>
<c:set var="path" value="/error" scope="request"/>
<%@ include file="../components/customNavBar.jsp" %>
<div class="error-page">
    <div class="error-container">
        <svg id="Capa_1" enable-background="new 0 0 511.997 511.997" height="256" viewBox="0 0 511.997 511.997"
             width="256" xmlns="http://www.w3.org/2000/svg">
            <g>
                <path d="m233.823 354.221h44.35v157.776h-44.35z" fill="#838383"></path>
                <path d="m257.581 354.221h20.592v157.776h-20.592z" fill="#6b6b6b"></path>
                <circle cx="255.998" cy="184.795" fill="#ee6161" r="184.795"></circle>
                <path d="m440.793 184.799c0 102.056-82.74 184.795-184.795 184.795-29.19 0-56.794-6.765-81.34-18.821 19.903 7.331 41.421 11.336 63.877 11.336 102.055 0 184.795-82.74 184.795-184.795 0-72.876-42.183-135.889-103.456-165.974 70.582 25.997 120.919 93.849 120.919 173.459z"
                      fill="#e94444"></path>
                <circle cx="255.998" cy="184.795" fill="#8ac9fe" r="149.062"></circle>
                <path d="m405.056 184.799c0 82.318-66.729 149.057-149.057 149.057-24.917 0-48.402-6.106-69.036-16.917 15.66 5.601 32.546 8.649 50.132 8.649 82.328 0 149.057-66.74 149.057-149.068 0-57.411-32.453-107.234-80.022-132.141 57.669 20.583 98.926 75.678 98.926 140.42z"
                      fill="#60b7ff"></path>
                <path d="m234.632 28.358h36.278v323.636h-36.278z" fill="#ee6161"
                      transform="matrix(.707 -.707 .707 .707 -60.44 234.437)"></path>
            </g>
        </svg>

        <p class="error-title">
            <spring:message code="error.403"/>
        </p>
        <p class="error-description">
            <spring:message code="error.403.message"/>
        </p>
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/" role="button" type="submit">
            <spring:message code="button.return"/>
        </a>
    </div>
</div>
</body>
</html>
