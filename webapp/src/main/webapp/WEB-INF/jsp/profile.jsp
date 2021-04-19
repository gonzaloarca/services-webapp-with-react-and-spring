<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>
        <spring:message code="profile.title" var="text"/>
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

    <!--  Bootstrap icons   -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css">

    <link href="${pageContext.request.contextPath}/resources/css/styles.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/resources/css/profile.css" rel="stylesheet"/>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/icon.svg">
    <link rel="apple-touch-icon" href="${pageContext.request.contextPath}/resources/images/apple-touch-icon.png">
</head>
<body>
<jsp:include page="customNavBar.jsp"/>
<div class="content-container-transparent">
    <div class="row">
        <div class="d-block col-4">
            <div class="card custom-card">
                <div class="card-body">
                    <c:choose>
                        <c:when test="${user.image.string != null}">
                            <c:set var="profilePic" value="data:${user.image.type};base64,${user.image.string}"/>
                        </c:when>
                        <c:otherwise>
                            <c:url var="profilePic" value="/resources/images/defaultavatar.svg"/>
                        </c:otherwise>
                    </c:choose>
                    <img class="card-image-top profile-img"
                         src='${profilePic}'
                         alt="<spring:message code="profile.image"/>">
                    <h3 class="card-title mt-2 profile-title"><c:out value="${user.username}"/></h3>
                    <h5 class="profile-subtitle"><spring:message code="profile.professional"/></h5>
                </div>
            </div>
            <div class="card custom-card mt-3">
                <div class="card-body">
                    <h5 class="profile-subtitle"><spring:message code="profile.reviews.average"/></h5>
                    <span class="custom-row rating align-items-center">
                        <h1 class="mr-3">
                            ${avgRate}
                        </h1>
                        <jsp:include page="rateStars.jsp">
                            <jsp:param name="rate" value="${avgRate}"/>
                        </jsp:include>
                        <h5 class="ml-3 mb-0">
                            (${reviews.size()})
                        </h5>
                    </span>
                </div>
            </div>
            <div class="card custom-card mt-3">
                <div class="card-body">
                    <div class="row align-items-center justify-content-center">
                        <div class="profile-completed-works-outline">
                            <div class="profile-completed-works">${totalContractsCompleted}</div>
                        </div>
                        <h4 class="mb-0 ml-3"><spring:message code="profile.completed.works"/></h4>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-8">
            <div class="card custom-card">
                <div class="card-body">
                    <div class="card-title row ml-3">
                        <a type="button" class="btn profile-list-selector ${(withServices)? 'disabled' : ''}"
                           aria-disabled="${(withServices)? 'true' : 'false'}"
                           href="${pageContext.request.contextPath}/profile/${user.id}/services">
                            <div><h4 class="mb-0"><spring:message code="profile.selector.services"/></h4></div>
                            <div class="chip mb-0"><h5 class="mb-0">${services.size()}</h5></div>
                        </a>
                        &nbsp;
                        <a type="button" class="btn profile-list-selector ${(!withServices)? 'disabled' : ''}"
                           aria-disabled="${(!withServices)? 'true' : 'false'}"
                           href="${pageContext.request.contextPath}/profile/${user.id}/reviews">
                            <div><h4 class="mb-0"><spring:message code="profile.selector.reviews"/></h4></div>
                            <div class="chip mb-0"><h5 class="mb-0">${reviews.size()}</h5></div>
                        </a>
                    </div>

                    <c:if test="${withServices}">
                        <c:forEach var="jobCard" items="${jobCards}" varStatus="status">
                            <a href="${pageContext.request.contextPath}/job/${jobCard.jobPost.id}">
                            <div class="row no-gutters">
                                <div class="col-md-3">
                                    <c:choose>
                                        <c:when test="${jobCard.postImage == null}">
                                            <c:url value="/resources/images/${jobCard.jobPost.jobType.imagePath}" var="imageSrc"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set value="data:${jobCard.postImage.image.type};base64,${jobCard.postImage.image.string}"
                                                   var="imageSrc"/>
                                        </c:otherwise>
                                    </c:choose>
                                    <img class="card-image-top service-img"
                                         src='${imageSrc}'
                                         alt="<spring:message code="profile.service.image"/>">
                                </div>
                                <div class="col-md-9 px-3">
                                    <h4 class="service-title">
                                        <c:out value="${jobCard.jobPost.title}"/>
                                    </h4>
                                    <div class="justify-content-between custom-row">
                                        <h6 class="service-subtitle"><spring:message
                                                code="${jobCard.jobPost.jobType.stringCode}"/></h6>

                                        <jsp:include page="rateStars.jsp">
                                            <jsp:param name="rate" value="${jobCard.jobPost.rating}"/>
                                        </jsp:include>
                                        <h6 class="ml-3 mb-0">
                                            (${jobCard.reviewsCount})
                                        </h6>
                                    </div>

                                    <br/>
                                    <div class="d-flex">
                                        <div class="price-container">
                                            <p class="price">
                                                <spring:message code="${jobCard.rateType.stringCode}"
                                                                arguments="${jobCard.price}"/>
                                            </p>
                                        </div>
                                    </div>

                                    <div class="custom-row service-contracted-times">
                                        <i class="fas fa-check"></i>
                                        <h6 class="ml-2">
                                            <spring:message code="profile.service.contract.quantity"
                                                            arguments="${jobCard.contractsCompleted}"/>
                                        </h6>
                                    </div>

                                </div>
                            </div>
                            </a>

                            <c:if test="${status.index != jobCards.size()-1}">
                                <hr class="hr1"/>
                            </c:if>
                        </c:forEach>
                    </c:if>

                    <c:if test="${!withServices}">
                        <div class="row">
                            <div class="col d-flex flex-column align-items-end">
                                <p class="reviews-rate">
                                        ${avgRate}
                                </p>
                                <span class="reviews-rate-stars">
                                    <jsp:include page="rateStars.jsp">
                                        <jsp:param name="rate" value="${avgRate}"/>
                                    </jsp:include>
                                </span>
                                <h5 class="reviews-summary-gray-text mt-3"><spring:message code="profile.review.average"
                                                                                           arguments="${reviews.size()}"/>
                                </h5>
                            </div>
                            <div class="col align-self-center">
                                <c:if test="${reviews.size() >0}">
                                    <c:forEach begin="1" end="5" var="i">
                                        <div class="row mb-1 align-items-center justify-content-center">
                                            <p class="reviews-summary-gray-text"><spring:message
                                                    code="profile.review.stars"
                                                    arguments="${6-i}"/></p>
                                            <div class="progress">
                                                <div class="progress-bar bg-warning" role="progressbar"
                                                    <%--                                                 TODO:DINAMIZAR--%>
                                                     style="width: ${Integer.valueOf(reviewsByPoints[5-i]*100/reviews.size()) };"
                                                     aria-valuenow="${Integer.valueOf(reviewsByPoints.get(5-i)*100/reviews.size())}"
                                                     aria-valuemin="0" aria-valuemax="100"></div>
                                            </div>
                                            <p class="reviews-summary-gray-text">${reviewsByPoints.get(5-i)}</p>
                                        </div>
                                    </c:forEach>
                                </c:if>
                            </div>
                        </div>
                        <hr class="hr1"/>

                        <c:forEach var="review" items="${reviews}" varStatus="status">
                            <jsp:include page="rateStars.jsp">
                                <jsp:param name="rate" value="${review.rate}"/>
                            </jsp:include>
                            <h4 class="mt-2 review-title">${review.title}</h4>
                            <h5>${review.description}</h5>

                            <h6>${review.client.username}</h6>
                            <a href="${pageContext.request.contextPath}/job/${review.jobPost.id}">
                                <h5 class="review-link mt-2">
                                    <i class="bi bi-box-arrow-up-right"></i>
                                    <spring:message code="profile.review.link"
                                                    arguments="${review.jobPost.title}"/>
                                </h5>
                            </a>

                            <c:if test="${status.index != reviews.size()-1}">
                                <hr class="hr1"/>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>
