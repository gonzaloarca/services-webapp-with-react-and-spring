<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        <spring:message code="navigation.categories" var="text"/>
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
    <link href="${pageContext.request.contextPath}/resources/css/categories.css" rel="stylesheet"/>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/icon.svg">
    <link rel="apple-touch-icon" href="${pageContext.request.contextPath}/resources/images/apple-touch-icon.png">
</head>
<body>
<jsp:include page="customNavBar.jsp">
    <jsp:param name="path" value="categories"/>
</jsp:include>
<div class="content-container mt-5">
    <h3>
        Categorías de servicios
    </h3>
    <hr>
    <div class="search-bar-container">
        <input class="category-search-bar" placeholder="Filtrar categorías por nombre"/>
        <button style="color: white" class="btn search-button hirenet-blue-btn">
            <i class="fas fa-search mr-2"></i>Buscar
        </button>
    </div>
    <div class="category-container">
        <a class="category">
            <p>
                Categoría
            </p>
            <div class="category-overlay">
            </div>
            <%--            TODO: Poner alt correcto--%>
            <img src="<c:url value="/resources/images/teaching.jpeg"/>" alt="">
        </a>
        <a class="category">
            <p>
                Categoría
            </p>
            <div class="category-overlay">
            </div>
            <%--            TODO: Poner alt correcto--%>
            <img src="<c:url value="/resources/images/teaching.jpeg"/>" alt="">
        </a>
        <a class="category">
            <p>
                Categoría
            </p>
            <div class="category-overlay">
            </div>
            <%--            TODO: Poner alt correcto--%>
            <img src="<c:url value="/resources/images/teaching.jpeg"/>" alt="">
        </a>
        <a class="category">
            <p>
                Categoría
            </p>
            <div class="category-overlay">
            </div>
            <%--            TODO: Poner alt correcto--%>
            <img src="<c:url value="/resources/images/teaching.jpeg"/>" alt="">
        </a>
    </div>


</div>
</body>
</html>
