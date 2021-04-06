<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Inicio</title>

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
    <link href="${pageContext.request.contextPath}/resources/css/index.css" rel="stylesheet"/>
    <link rel="shortcut icon" href="#">
</head>
<body>
<%@ include file="customNavBar.jsp" %>
<div class="home-banner-container">
    <form:form action="/" method="post" modelAttribute="searchForm" class="home-search-form">
        <div class="search-instructions">
            <div class="search-instruction-step">
                <div class="blue-circle">
                    <p class="circle-text">1</p>
                </div>
                <p class="search-instructions-text">Elija su ubicación</p>
            </div>
            <div class="search-instruction-step">
                <div class="blue-circle">
                    <p class="circle-text">2</p>
                </div>
                <p class="search-instructions-text">Introduzca el servicio que necesita</p>
            </div>
            <div class="search-instruction-step">
                <div class="blue-circle">
                    <p class="circle-text">3</p>
                </div>
                <p class="search-instructions-text">¡Buscar!</p>
            </div>
        </div>

        <div class="home-search-location">
            <form:select path="zone" class="custom-select w-100">
                <form:option value="" label="Ubicación"/>
                <form:options items="${zones}" itemValue="value" itemLabel="message"/>
            </form:select>
            <form:errors path="zone" cssClass="search-form-error" element="p"/>
        </div>

        <div class="home-search-bar-container home-search-bar-row">
            <form:input path="query" type="search" class="home-search-bar w-100 h-100"
                        placeholder="Buscar un servicio..."/>
            <form:errors path="query" cssClass="search-form-error" element="p"/>
        </div>

        <button class="btn btn-warning btn-circle btn-l home-search-button home-search-bar-row">
            <i class="fas fa-search"></i>
        </button>
    </form:form>
    <%--TODO: Poner alt correcto--%>
    <img class="home-banner-img" alt=""
         src='<c:url value="${pageContext.request.contextPath}/resources/images/banner1.jpg" />'/>
</div>
<div class="content-container">
    <h3>Servicios destacados</h3>
    <hr class="hr1"/>
    <div class="job-display-container">
        <c:if test="${jobCards.size() > 0}">
            <c:forEach items="${jobCards}" var="jobCard" varStatus="status">
                <div class="card job-card" style="width: 18rem;">
                        <%-- TODO: Poner alt correcto e imagen de usuario--%>
                    <img class="card-img-top job-card-img"
                         src='<c:url value="${pageContext.request.contextPath}/resources/images/service-default.jpg" />'
                         alt="">
                    <div class="card-body">
                        <h5 class="card-title job-card-title"><c:out value="${jobCard.key.title}"/></h5>
                        <h6 class="card-text job-card-type capitalize-first-letter"><c:out
                                value="${jobCard.key.jobType}"/></h6>
                        <div class="job-card-price-container">
                            <p class="job-card-price">
                                <c:out value="${jobCard.value[0]}"/>
                            </p>
                        </div>
                    </div>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item job-card-detail">
                            <i class="fas fa-map-marker-alt job-card-detail" style="font-size: 25px; color: gray"></i>
                            <c:set var="zonesSize" value="${jobCard.key.zones.size()}"/>
                            <p class="job-card-detail capitalize-first-letter">${jobCard.key.zones[0]}
                                <c:if test="${zonesSize > 1}">
                                    y ${zonesSize -1} más
                                </c:if>
                            </p>
                        </li>
                        <li class="list-group-item job-card-detail">
                            <i class="fas fa-check job-card-detail" style="font-size: 25px; color: gray"></i>
                            <p class="job-card-detail">${jobCard.value[1]} completados</p>
                        </li>

                    </ul>
                    <div class="card-body" style="display: flex; justify-content: center; align-items: center">
                        <a href="${pageContext.request.contextPath}/job/${jobCard.key.id}" class="btn btn-outline-primary text-uppercase">Ver detalles</a>
                    </div>
                </div>
            </c:forEach>
        </c:if>
        <c:if test="${jobCards.size() == 0}">
            <div style="text-align: center; width: 100%; margin: 50px 0">
                <i class="fas fa-cogs mb-4" style="font-size: 10rem;"></i>
                <p style="font-size: 1.5rem; font-weight: bold; margin: 0">No hay servicios disponibles en este
                    momento</p>
                <p style="font-size: 1.3rem">Disculpas por las molestias</p>
            </div>
        </c:if>
    </div>
</div>
</body>
</html>