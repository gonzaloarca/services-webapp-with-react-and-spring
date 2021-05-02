<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<html>
<body>
<div class="card job-card">
    <spring:message code="${requestScope.data.jobPost.jobType.stringCode}" var="jobTypeName"/>

    <c:choose>
        <c:when test="${requestScope.data.postImage == null}">
            <c:url value="/resources/images/${requestScope.data.jobPost.jobType.imagePath}" var="imageSrc"/>
        </c:when>
        <c:otherwise>
            <c:set value="data:${requestScope.data.postImage.image.type};base64,${requestScope.data.postImage.image.string}"
                   var="imageSrc"/>
        </c:otherwise>
    </c:choose>

    <img class="card-img-top job-card-img" src="${imageSrc}"
         alt="<spring:message code="jobCard.jobs.imageAlt" arguments="${jobTypeName}"/>">
    <div class="card-body">
        <h5 class="card-title job-card-title"><c:out value="${requestScope.data.jobPost.title}"/></h5>
        <h6 class="card-text job-card-type capitalize-first-letter">
            <c:out value="${jobTypeName}"/>
        </h6>
        <div>
            <div class="price-container mt-4 mx-4">
                <p class="price">
                    <spring:message code="${requestScope.data.rateType.stringCode}"
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
                <spring:message code="jobCard.jobs.completed" arguments="${requestScope.data.contractsCompleted}"/>
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
