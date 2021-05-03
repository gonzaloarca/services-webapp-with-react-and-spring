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
<c:set var="zoneValues" value="${zoneValues}" scope="request"/>
<%@include file="components/customNavBar.jsp" %>
<div class="content-container-transparent">
    <div class="row">
        <div class="d-block col-3">
            <div class="card custom-card">
                <div class="card-body p-0">
                    <div class="profile-image-container">
                        <c:choose>
                            <c:when test="${user.image.string != null}">
                                <c:set var="profilePic" value="data:${user.image.type};base64,${user.image.string}"/>
                            </c:when>
                            <c:otherwise>
                                <c:url var="profilePic" value="/resources/images/defaultavatar.svg"/>
                            </c:otherwise>
                        </c:choose>
                        <img class="profile-img"
                             src='${profilePic}'
                             alt="<spring:message code="profile.image"/>">
                    </div>
                    <h4 class="card-title profile-title"><c:out value="${user.username}"/></h4>
                    <p class="profile-subtitle ml-3 mb-2">
                        <c:choose>
                            <c:when test="${isPro}">
                                <spring:message code="account.settings.info.professional"/>
                            </c:when>
                            <c:otherwise>
                                <spring:message code="account.settings.info.client"/>
                            </c:otherwise>
                        </c:choose>
                    </p>
                </div>
            </div>

            <div class="mt-3">
                <jsp:include page="components/avgRateAndCompletedContracts.jsp">
                    <jsp:param name="avgRate" value="${avgRate}"/>
                    <jsp:param name="totalContractsCompleted" value="${totalContractsCompleted}"/>
                    <jsp:param name="totalReviewsSize" value="${totalReviewsSize}"/>
                </jsp:include>
            </div>
        </div>

        <div class="col-9">
            <div class="card custom-card">
                <div class="card-body">
                    <div class="ml-1 mb-4 card-title row">
                        <a type="button" class="btn profile-list-selector ${(withServices)? 'disabled' : ''}"
                           aria-disabled="${(withServices)? 'true' : 'false'}"
                           href="${pageContext.request.contextPath}/profile/${user.id}/services">
                            <div><h5 class="mb-0"><spring:message code="profile.selector.services"/></h5></div>
                            <div class="chip mb-0"><p class="mb-0">${servicesSize}</p></div>
                        </a>
                        &nbsp;
                        <a type="button" class="btn profile-list-selector ${(!withServices)? 'disabled' : ''}"
                           aria-disabled="${(!withServices)? 'true' : 'false'}"
                           href="${pageContext.request.contextPath}/profile/${user.id}/reviews">
                            <div><h5 class="mb-0"><spring:message code="profile.selector.reviews"/></h5></div>
                            <div class="chip mb-0"><p class="mb-0">${totalReviewsSize}</p></div>
                        </a>
                    </div>

                    <c:if test="${withServices}">
                        <c:forEach var="jobCard" items="${jobCards}" varStatus="status">
                            <c:set var="data" value="${jobCard}" scope="request"/>
                            <%@include file="components/serviceCard.jsp" %>
                            <c:if test="${status.index != jobCards.size()-1}">
                                <hr class="hr1"/>
                            </c:if>
                        </c:forEach>
                        <c:set var="listSize" value="${jobCards.size()}" scope="request"/>
                        <c:set var="maxPage" value="${maxPage}" scope="request"/>
                        <c:set var="currentPages" value="${currentPages}" scope="request"/>
                        <%@include file="components/bottomPaginationBar.jsp" %>
                    </c:if>

                    <c:if test="${!withServices}">
                        <div class="row">
                            <div class="col d-flex flex-column align-items-end">
                                <p class="reviews-rate">
                                        ${avgRate}
                                </p>
                                <span class="reviews-rate-stars">
                                    <jsp:include page="components/rateStars.jsp">
                                        <jsp:param name="rate" value="${avgRate}"/>
                                    </jsp:include>
                                </span>
                                <h5 class="reviews-summary-gray-text mt-3"><spring:message code="profile.review.average"
                                                                                           arguments="${totalReviewsSize}"/>
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
                                                     style="width: ${reviewsByPoints[5-i]*100/reviews.size()};"
                                                     aria-valuenow="${reviewsByPoints.get(5-i)*100/reviews.size()}"
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
                            <c:set var="data" value="${review}" scope="request"/>
                            <c:set var="withLink" value="${true}" scope="request"/>
                            <%@include file="components/reviewCard.jsp" %>

                            <c:if test="${status.index != reviews.size()-1}">
                                <hr class="hr1"/>
                            </c:if>
                        </c:forEach>
                        <c:set var="listSize" value="${reviews.size()}" scope="request"/>
                        <c:set var="maxPage" value="${maxPage}" scope="request"/>
                        <c:set var="currentPages" value="${currentPages}" scope="request"/>
                        <%@include file="components/bottomPaginationBar.jsp" %>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="components/footer.jsp"/>
</body>
</html>
