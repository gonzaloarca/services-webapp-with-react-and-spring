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
<c:set var="zoneValues" value="${zoneValues}" scope="request"/>
<c:set var="path" value="/categories" scope="request"/>
<%@include file="components/customNavBar.jsp" %>
<div class="content-container mt-5">
    <h3>
        <spring:message code="categories.search.title"/>
    </h3>
    <hr>
    <div class="search-bar-container">
        <spring:message code="categories.search.placeholder" var="searchPlaceholder"/>
        <input class="category-search-bar" id="categoryFilter" placeholder="${searchPlaceholder}"/>
    </div>
    <div class="category-container">
        <c:forEach items="${categories}" var="category">
            <a class="category-href" onclick="redirectCategory(${category.value})" >
                <p>
                    <spring:message code="${category.stringCode}"/>
                </p>
                <div class="category-overlay">
                </div>
                <spring:message code="${category.stringCode}" var="jobTypeName"/>
                <img src='<c:url value="/resources/images/${category.imagePath}" />'
                     alt="<spring:message code="jobCard.jobs.imageAlt" arguments="${category}"/>">
            </a>
        </c:forEach>
    </div>
</div>
<jsp:include page="components/footer.jsp"/>
<script>
    // Para buscar una categoria
    $('#categoryFilter').on('keyup', function () {
        const filter = $(this)[0].value.toUpperCase();
        const list = $('.category-container');
        const listElems = list[0].getElementsByTagName('a');

        // Iterar por la lista y esconder los elementos que no matcheen
        for (let i = 0; i < listElems.length; i++) {
            let a = listElems[i].getElementsByTagName("p")[0];
            let txtValue = a.textContent || a.innerText;
            if (txtValue.toUpperCase().startsWith(filter.trim())) {
                listElems[i].style.display = "";
            } else {
                listElems[i].style.display = "none";
            }
        }
    });
    // Para modificar el href con la ubicacion seleccionada
    function redirectCategory(category) {
        var auxZoneId = sessionStorage.getItem("pickedZoneId");
        if (!auxZoneId) {
            $('#zonesModal').modal('show');
            $('#categoryForm')[0].value = category;
            return false;
        }
        if (auxZoneId) {
            window.location.href = "${pageContext.request.contextPath}" + '/search?zone=' + auxZoneId +
                '&query=&category=' + category;
        }else {
            window.location.href = "${pageContext.request.contextPath}" + '/search?zone=' +
                '&query=&category=' + category;
        }
    }
</script>
</body>
</html>
