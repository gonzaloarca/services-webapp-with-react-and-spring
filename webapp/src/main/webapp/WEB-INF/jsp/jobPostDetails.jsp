<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>
        <spring:message code="title.name" arguments="${jobPost.title}"/>
    </title>

    <link href="${pageContext.request.contextPath}/resources/css/styles.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/resources/css/jobpostdetails.css" rel="stylesheet"/>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/icon.svg">
    <link rel="apple-touch-icon" href="${pageContext.request.contextPath}/resources/images/apple-touch-icon.png">

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
<c:set var="zoneValues" value="${zoneValues}" scope="request"/>
<%@include file="components/customNavBar.jsp" %>
<div class="content-container-transparent">
    <%--    <nav aria-label="breadcrumb">--%>
    <%--        <ol class="breadcrumb bg-white">--%>
    <%--            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">--%>
    <%--                <spring:message code="navigation.index"/>--%>
    <%--            </a></li>--%>
    <%--            <li class="breadcrumb-item active" aria-current="page">--%>
    <%--                <p class="capitalize-first-letter">--%>
    <%--                    <spring:message code="${jobPost.jobType.stringCode}"/>--%>
    <%--                </p>--%>
    <%--            </li>--%>
    <%--        </ol>--%>
    <%--    </nav>--%>
    <%--        TODO: IMPLEMENTAR EDICION DE JOBPOST--%>
    <c:if test="${isOwner}">
        <div class="flex custom-row justify-content-end align-items-center">
            <a class="edit-button text-uppercase align-items-center my-2"
               href="${pageContext.request.contextPath}/job/${jobPost.id}/edit">
                <div class="custom-row"><i class="fas fa-edit"></i>
                    <p class="mb-0 ml-2">Editar publicacion</p>
                </div>
            </a>
            <div class="vl align-items-center my-2 owner-options-vl"></div>
            <a class="delete-button text-uppercase align-items-center my-2"
               href="${pageContext.request.contextPath}/job/${jobPost.id}/delete">
                <div class="custom-row"><i class="fas fa-trash-alt"></i>
                    <p class="mb-0 ml-2">Eliminar publicacion</p>
                </div>
            </a>
        </div>
    </c:if>
    <div class="card custom-card mb-4 bg-white rounded">
        <div id="carousel" class="carousel slide" data-ride="carousel">
            <c:choose>
                <c:when test="${imageList.size() == 0}">
                    <div class="carousel-inner">
                        <div class="carousel-item active">
                            <spring:message code="${jobPost.jobType.stringCode}" var="jobTypeName"/>
                            <img class="d-block w-100 h-100"
                                 src='<c:url value="/resources/images/${jobPost.jobType.imagePath}" />'
                                 alt="<spring:message code="jobCard.jobs.imageAlt" arguments="${jobTypeName}"/>">
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:if test="${imageList.size() > 1}">
                        <ol class="carousel-indicators">
                            <c:forEach items="${imageList}" varStatus="status">
                                <li data-target="#carousel" data-slide-to="${status.index}"
                                    class="${status.index == 0 ? 'active' : ''}"></li>
                            </c:forEach>
                        </ol>
                        <a class="carousel-control-prev" href="#carousel" role="button" data-slide="prev">
                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span class="sr-only">Previous</span>
                        </a>
                        <a class="carousel-control-next" href="#carousel" role="button" data-slide="next">
                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                            <span class="sr-only">Next</span>
                        </a>
                    </c:if>
                    <div class="carousel-inner">
                        <c:forEach items="${imageList}" varStatus="status" var="postImage">
                            <div class="carousel-item ${status.index == 0 ? 'active' : ''}">
                                <spring:message code="${jobPost.jobType.stringCode}" var="jobTypeName"/>
                                <img class="d-block w-100 h-100"
                                     src='data:${postImage.image.type};base64,${postImage.image.string}'
                                     alt="<spring:message code="jobCard.jobs.imageAlt" arguments="${jobTypeName}"/>">
                            </div>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <div class="card-body custom-row mt-2 p-0">
        <div class="center">
            <div class="card custom-card mb-4 bg-white rounded">
                <div class="card-body">
                        <span class="card-title custom-row">
                            <i class="fas fa-briefcase"></i>
                            <p><c:out value="${jobPost.title}"/></p>
                        </span>

                    <div class="summary custom-row">
                        <a href="${pageContext.request.contextPath}/profile/${jobPost.user.id}/services"
                           class="summary-item profile-item align-items-center">
                            <c:choose>
                                <c:when test="${jobPost.user.image.string == null}">
                                    <img src="${pageContext.request.contextPath}/resources/images/defaultavatar.svg"
                                         alt="avatar">
                                </c:when>
                                <c:otherwise>
                                    <img src="data:${jobPost.user.image.type};base64,${jobPost.user.image.string}"
                                         alt="avatar">
                                </c:otherwise>
                            </c:choose>
                            <p><c:out value="${jobPost.user.username}"/></p>
                            <i class="bi bi-chevron-compact-right"></i>
                        </a>
                        <div class="summary-item rate-item">
                            <p><spring:message code="jobPost.jobs.avgRate"/></p>
                            <span class="custom-row align-items-center">
                                    <h2>
                                        ${avgRate}
                                    </h2>
                                    <jsp:include page="components/rateStars.jsp">
                                        <jsp:param name="rate" value="${avgRate}"/>
                                    </jsp:include>
                                    <h5 class="ml-1 review-count">
                                        (${totalReviewsSize})
                                    </h5>
                                </span>
                            <c:if test="${totalReviewsSize > 0}">
                                <a class="mt-0 opinion" id="seeReviews">
                                    <spring:message code="jobPost.jobs.seeReviews"/>
                                </a>
                            </c:if>
                        </div>
                        <div class="summary-item contracts-item">
                            <p class="mb-0 ml-3"><spring:message code="highlight.completed.works"/></p>
                            <div class="profile-completed-works-outline">
                                <div class="profile-completed-works">${totalContractsCompleted}</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="two-column">
                <div class="card custom-card mb-4 bg-white rounded two-column-item">
                    <div class="card-body">
                            <span class="card-title custom-row">
                                <i class="far fa-clock"></i>
                                <p>
                                    <spring:message code="jobPost.jobs.hours"/>
                                </p>
                            </span>
                        <div class="available-hours">
                            <p>
                                <c:out value="${jobPost.availableHours}"/>
                            </p>
                        </div>
                    </div>
                </div>

                <div class="card custom-card mb-4 bg-white rounded two-column-item">
                    <div class="card-body">
                            <span class="card-title custom-row">
                                <i class="fas fa-map-marker-alt"></i>
                                <p>
                                    <spring:message code="jobPost.jobs.zones"/>
                                </p>
                            </span>
                        <div class="custom-row zones">
                            <c:forEach items="${jobPost.zones}" var="zone">
                                <p class="capitalize-first-letter m-1">
                                    <spring:message code="${zone.stringCode}"/>
                                </p>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>

            <div class="card custom-card mb-4 bg-white rounded">
                <div class="card-body">
                    <div class="card-title custom-row align-items-center justify-content-between">
                        <div class="custom-row"><i class="bi bi-box-seam"></i>
                            <p>
                                <c:choose>
                                    <c:when test="${packages.size() == 1}">
                                        <spring:message code="jobPost.jobs.packages.onlyOne"/>
                                    </c:when>
                                    <c:otherwise>
                                        <spring:message code="jobPost.jobs.packages"/>
                                    </c:otherwise>
                                </c:choose>
                            </p>
                        </div>
                        <c:if test="${isOwner}">
                            <a class="custom-row edit-button text-uppercase align-items-center"
                               href="${pageContext.request.contextPath}/job/${jobPost.id}/packages">
                                <i class="fas fa-cube m-0"></i>
                                <p class="ml-2 font-weight-normal">Administrar paquetes</p>
                            </a>
                        </c:if>
                    </div>

                    <div class="accordion mx-5" id="accordionPackages">
                        <c:forEach items="${packages}" var="pack" varStatus="status">
                            <div class="card custom-card mb-3">
                                <div class="card custom-card " id="heading${status.index}">

                                    <button class="drop btn-block hirenet-blue-btn collapsed" type="button"
                                            data-toggle="collapse" data-target="#collapse${status.index}"
                                            aria-expanded="false"
                                            aria-controls="collapse${status.index}">
                                        <div class="package-info">
                                            <i class="fa fa-chevron-down text-white"></i>
                                            <i class="fa fa-chevron-up text-white"></i>
                                            <p class="package-title">
                                                <c:out value="${pack.title}"/>
                                            </p>
                                            <div class="custom-row end-items">
                                                <div class="package-price end-items-item">
                                                    <p class="text-center mt-2">
                                                        <spring:message code="jobPost.jobs.price"/>
                                                    </p>
                                                    <div class="chip">
                                                        <spring:message htmlEscape="true"
                                                                        code="${pack.rateType.stringCode}"
                                                                        arguments="${pack.price}"/>
                                                    </div>
                                                </div>
                                                <c:if test="${!isOwner}">
                                                    <div class="align-self-center ml-4 mr-4 requestServiceBtn end-items-item">
                                                        <a class="btn"
                                                           href="${pageContext.request.contextPath}/contract/package/${pack.id}"
                                                           role="button" type="submit">
                                                            <spring:message code="jobPost.jobs.submit"/>
                                                        </a>
                                                    </div>
                                                </c:if>
                                            </div>
                                        </div>
                                    </button>
                                </div>
                                <div id="collapse${status.index}" class="collapse package-desc"
                                     aria-labelledby="heading${status.index}"
                                     data-parent="#accordionPackages">
                                    <div class="card-body">
                                        <p class="package-text">
                                            <spring:message code="jobPost.package.description"/><br/>
                                            <c:out value="${pack.description}"/>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>

            <c:if test="${totalReviewsSize > 0}">
                <div class="card custom-card mb-4 bg-white rounded" id="jobPostReviews">
                    <div class="card-body">
                    <span class="card-title custom-row">
                        <i class="bi bi-chat-left-fill"></i>
                        <p>
                            <spring:message code="jobPost.job.opinions"/>
                        </p>
                    </span>
                        <hr class="hr1"/>
                        <c:forEach var="review" items="${reviews}" varStatus="status">
                            <c:set var="data" value="${review}" scope="request"/>
                            <%@include file="components/reviewCard.jsp" %>

                            <c:if test="${status.index != reviews.size()-1}">
                                <hr class="hr1"/>
                            </c:if>
                        </c:forEach>
                        <c:set var="listSize" value="${reviews.size()}" scope="request"/>
                        <c:set var="maxPage" value="${maxPage}" scope="request"/>
                        <c:set var="currentPages" value="${currentPages}" scope="request"/>
                        <%@include file="components/bottomPaginationBar.jsp" %>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</div>
<jsp:include page="components/footer.jsp"/>
<script>
    $('.requestServiceBtn').on('click', function (e) {
        e.stopPropagation();
    });
    $('#seeReviews').on('click', function () {
        $('html,body').animate({
            scrollTop: $('#jobPostReviews').offset().top
        }, 'slow');
    });
</script>
</body>
</html>
