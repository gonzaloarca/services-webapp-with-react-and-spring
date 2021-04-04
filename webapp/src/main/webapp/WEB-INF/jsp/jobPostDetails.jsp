<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><c:out value="Fontanería"/></title>
    <%--    TODO: CAMBIAR CON CATEGORIA DEL SERVICIO--%>
    <link href="${pageContext.request.contextPath}/resources/css/styles.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/resources/css/jobpostdetails.css" rel="stylesheet"/>
    <link rel="shortcut icon" href="#">
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

    <!--  Bootstrap icons   -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css">
</head>
<body>
<%@ include file="customNavBar.jsp" %>
<div class="content-container-transparent">
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb bg-white">
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">Inicio</a></li>
            <li class="breadcrumb-item active" aria-current="page">Fontaneria</li>
        </ol>
    </nav>
    <div class="card mb-4 bg-white rounded">
        <div id="carousel" class="carousel slide" data-ride="carousel">
            <ol class="carousel-indicators">
                <li data-target="#carousel" data-slide-to="0" class="active"></li>
                <li data-target="#carousel" data-slide-to="1"></li>
                <li data-target="#carousel" data-slide-to="2"></li>
            </ol>
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img class="d-block w-100 h-100"
                         src="${pageContext.request.contextPath}/resources/images/banner1.jpg"
                         alt="Primer foto">
                    <%--                TODO: CAMBIAR EL ALT A i20n
                        TODO: INCORPORAR FOTO DE TRABAJADOR, FOTO DEFAULT?? --%>
                </div>
            </div>
            <a class="carousel-control-prev" href="#carousel" role="button" data-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
            </a>
            <a class="carousel-control-next" href="#carousel" role="button" data-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="sr-only">Next</span>
            </a>
        </div>

        <div class="card-body custom-row">

            <div class="side">
            </div>

            <div class="center">

                <div class="card mb-4 bg-white rounded">
                    <div class="card-body">
                        <span class="card-title custom-row">
                            <i class="fas fa-suitcase"></i>
                            <p>
                                <c:out value="Fontanería"/>
    <%--                            TODO: CAMBIAR POR TITULO--%>
                            </p>
                        </span>


                        <%--TODO: IMPLEMENTAR CALIFICACIONES--%>
                        <%--                        <span class="custom-row rating ml-5">--%>
                        <%--                            <c:set var="rate" value="${3.7}" scope="session"/>--%>
                        <%--    &lt;%&ndash;                        TODO: CAMBIAR POR RATING VERDADERO&ndash;%&gt;--%>
                        <%--                            <c:forEach var="i" begin="1" end="5">--%>
                        <%--                                &lt;%&ndash;                            > 0.8 se rendondea para arriba, < 0.2 para abajo, el resto queda en 0.5&ndash;%&gt;--%>
                        <%--                                <c:choose>--%>
                        <%--                                    <c:when test="${i <= rate + 0.2}">--%>
                        <%--                                        <i class="bi bi-star-fill star"></i>--%>
                        <%--                                    </c:when>--%>
                        <%--                                    <c:when test="${i-rate <= 0.8 && i-rate >= 0.2}">--%>
                        <%--                                        <i class="bi bi-star-half star"></i>--%>
                        <%--                                    </c:when>--%>
                        <%--                                    <c:otherwise>--%>
                        <%--                                        <i class="bi bi-star star"></i>--%>
                        <%--                                    </c:otherwise>--%>
                        <%--                                </c:choose>--%>
                        <%--                            </c:forEach>--%>
                        <%--                            <p>--%>
                        <%--                                (43 calificaciones)--%>
                        <%--                            </p>--%>
                        <%--                        </span>--%>

                        <div class="summary custom-row">
                            <div class="summary-item">
                                <img
                                        src="${pageContext.request.contextPath}/resources/images/banner1.jpg"
                                        alt="Primer foto">
                                <p><c:out value="Sergio"/></p>
                            </div>
                            <div class="summary-item">
                                <i class="bi bi-geo-alt-fill"></i>
                                <p><c:out value="Palermo"/></p>
                            </div>
                            <div class="summary-item">
                                <i class="fas fa-check"></i>
                                <p>101 trabajos completados</p>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="card mb-4 bg-white rounded">
                    <div class="card-body">
                        <span class="card-title custom-row">
                            <i class="far fa-clock"></i>
                            <p>
                                Horarios
                            </p>
                        </span>
                        <div class="available-hours">
                            <p>
                                <c:out value="Lunes - Jueves 9:00hs a 18:00hs
                            Viernes - Sabados 10:00hs a 15:00hs"/>
                                <%--                            TODO: CAMBIAR POR HORARIOS REALES
                                                                VERIFICAR FUNCIONAMIENTO DE ENTER --%>
                            </p>
                        </div>
                    </div>
                </div>


                <div class="card mb-4 bg-white rounded">
                    <div class="card-body">
                        <span class="card-title custom-row">
                            <i class="bi bi-box-seam"></i>
                            <p>
                                Paquetes disponibles
                            </p>
                        </span>

                        <div class="accordion mx-5" id="accordionPackages">
                            <%--                        TODO: CAMBIAR POR packages VERDADEROs --%>
                            <c:forEach var="j" begin="1" end="5">
                                <div class="card mb-3">
                                    <div class="card " id="heading${j}">

                                        <button class="btn btn-block collapsed" type="button"
                                                data-toggle="collapse" data-target="#collapse${j}"
                                                aria-expanded="false"
                                                aria-controls="collapse${j}">
                                            <div class="custom-row package-info">
                                                <i class="fas fa-chevron-down"></i>
                                                <p class="package-title">
                                                    <c:out value="Instalacion de ${j} puertas"/>
                                                </p>
                                                <div class="ml-auto custom-row">
                                                    <div class="package-price">
                                                        <p class="text-center">Precio</p>
                                                        <div class="chip">
                                                            <c:out value="A acordar"/>
                                                        </div>
                                                    </div>
                                                    <div class="align-self-center ml-4 mr-4">
                                                        <a class="btn btn-primary" href="#" role="button" type="submit">
                                                            SOLICITAR
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>
                                        </button>
                                    </div>
                                    <div id="collapse${j}" class="collapse package-desc" aria-labelledby="heading${j}"
                                         data-parent="#accordionPackages">
                                        <div class="card-body">
                                            <p class="package-text">
                                                Descripcion<br/>
                                                Some placeholder content
                                                Some placeholder content for this accordion panel. This panel is hidden
                                                by
                                                default.
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>

            <div class="side"></div>
        </div>
    </div>
</div>
</body>
</html>
