<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ar.edu.itba.paw.models.JobPackage.RateType" %>
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
    <form class="home-search-form">
        <input type="search" placeholder="Buscar un servicio..." class="home-search-bar home-search-bar-row">
        <div class="dropdown home-search-location">
            <button class="btn btn-light btn-block rounded-pill dropdown-toggle home-search-bar-row"
                    type="button"
                    id="dropdownMenuButton"
                    data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                Ubicacion
            </button>
            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                <a class="dropdown-item" href="#">Action</a>
                <a class="dropdown-item" href="#">Another action</a>
                <a class="dropdown-item" href="#">Something else here</a>
            </div>
        </div>
        <button class="btn btn-warning btn-circle btn-l home-search-button home-search-bar-row">
            <i class="fas fa-search"></i>
        </button>
    </form>
    <%--TODO: Poner alt correcto--%>
    <img class="home-banner-img" alt=""
         src='<c:url value="${pageContext.request.contextPath}/resources/images/banner1.jpg" />'/>
</div>
<div class="content-container">
    <h3>Servicios destacados</h3>
    <hr class="hr1"/>
    <div class="job-display-container">
        <c:forEach items="${jobCards}" var="jobCard" varStatus="status">
            <div class="card job-card" style="width: 18rem;">
                    <%-- TODO: Poner alt correcto e imagen de usuario--%>
                <img class="card-img-top job-card-img"
                     src='<c:url value="${pageContext.request.contextPath}/resources/images/service-default.jpg" />'
                     alt="">
                <div class="card-body">
                    <h5 class="card-title job-card-title">${jobCard.title}</h5>
                    <h6 class="card-text job-card-type capitalizeFirstLetter">${jobCard.jobType}</h6>
                    <div class="job-card-price-container">
                        <p class="job-card-price">
                                <%--   TODO: ARREGLAR COMPARACION HARDCODEADA--%>
                            <c:choose>
                                <c:when test="${packages[status.index].rateType == 'HOURLY'}">
                                    <c:out value="$${packages[status.index].price}/hora"/>
                                </c:when>
                                <c:when test="${packages[status.index].rateType == 'ONE_TIME'}">
                                    <c:out value="$${packages[status.index].price}"/>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="A acordar"/>
                                </c:otherwise>
                            </c:choose>
                        </p>
                    </div>
                </div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item job-card-detail">
                        <i class="fas fa-map-marker-alt job-card-detail" style="font-size: 25px; color: gray"></i>
                        <c:set var="zonesSize" value="${jobCard.zones.size()}"/>
                        <p class="job-card-detail capitalizeFirstLetter">${jobCard.zones[0]}
                            <c:if test="${zonesSize > 1}">
                                y ${zonesSize -1} + más
                            </c:if>
                        </p>
                    </li>
                    <li class="list-group-item job-card-detail">
                        <i class="fas fa-check job-card-detail" style="font-size: 25px; color: gray"></i>
                        <p class="job-card-detail">101 completados</p>
                    </li>

                </ul>
                <div class="card-body" style="display: flex; justify-content: center; align-items: center">
                    <a href="/job/${jobCard.id}" class="btn btn-outline-primary text-uppercase">Ver detalles</a>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>