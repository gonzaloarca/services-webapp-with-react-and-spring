<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<html>
<body>
<div class="card job-card">
    <spring:message code="${requestScope.data.jobPost.jobType.stringCode}" var="jobTypeName"/>

    <c:choose>
        <c:when test="${requestScope.data.postImageId == null}">
            <c:url value="/resources/images/${requestScope.data.jobPost.jobType.imagePath}" var="imageSrc"/>
        </c:when>
        <c:otherwise>
            <c:url value="/image/post/${requestScope.data.postImageId}" var="imageSrc"/>
        </c:otherwise>
    </c:choose>

    <img loading="lazy" class="card-img-top job-card-img" src="${imageSrc}"
         alt="<spring:message code="jobCard.jobs.imageAlt" arguments="${jobTypeName}"/>">
    <div class="card-body">
        <h5 class="card-title job-card-title"><c:out value="${requestScope.data.jobPost.title}"/></h5>
        <h6 class="card-text job-card-type capitalize-first-letter">
            <c:out value="${jobTypeName}"/>
        </h6>
        <c:choose>
            <c:when test="${requestScope.data.reviewsCount == 0}">
                <div class="gray-chip job-card-no-reviews">
                    <i class="bi bi-star-fill star"></i>
                    <p>
                        <spring:message code="jobCard.jobs.noReviews"/>
                    </p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="custom-row">
                    <jsp:include page="rateStars.jsp">
                        <jsp:param name="rate" value="${requestScope.data.rating}"/>
                    </jsp:include>
                    <p class="ml-3 service-subtitle">
                        (${requestScope.data.reviewsCount})
                    </p>
                </div>
            </c:otherwise>
        </c:choose>
        <div>
            <div class="price-container mx-4">
                <p class="price">
                    <spring:message htmlEscape="true" code="${requestScope.data.rateType.stringCode}"
                                    arguments="${requestScope.data.price}"/>
                </p>
            </div>
        </div>
    </div>
    <ul class="list-group list-group-flush">
        <li class="list-group-item job-card-detail">
            <i class="fas fa-map-marker-alt job-card-detail"></i>
            <p class="job-card-detail capitalize-first-letter">
                <spring:message code="${requestScope.data.jobPost.zones[0].stringCode}"/>
                <c:if test="${requestScope.data.jobPost.zones.size() > 1}">
                    <spring:message code="jobCard.jobs.extraZones"
                                    arguments="${requestScope.data.jobPost.zones.size() -1}"/>
                </c:if>
            </p>
        </li>
        <li class="list-group-item job-card-detail">
            <i class="fas fa-check job-card-detail"></i>
            <p class="job-card-detail">
                <c:choose>
                    <c:when test="${requestScope.data.contractsCompleted == 1}">
                        <spring:message code="jobPost.jobs.completed.onlyOne"/>
                    </c:when>
                    <c:otherwise>
                        <spring:message code="jobPost.jobs.completed"
                                        arguments="${requestScope.data.contractsCompleted}"/>
                    </c:otherwise>
                </c:choose>
            </p>
        </li>

    </ul>
    <div class="card-body job-card-button">
        <a href="${pageContext.request.contextPath}/job/${requestScope.data.jobPost.id}"
           class="btn btn-outline-primary text-uppercase stretched-link">
            <spring:message code="jobCard.jobs.details"/>
        </a>
    </div>
</div>
</body>
</html>
