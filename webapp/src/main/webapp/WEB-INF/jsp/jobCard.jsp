<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<html>
<body>
<div class="card job-card">
    <%-- TODO: Poner alt correcto e imagen de usuario--%>
    <spring:message code="${requestScope.data.jobPost.jobType.stringCode}" var="jobTypeName"/>
    <img class="card-img-top job-card-img"
         src='<c:url value="/resources/images/${requestScope.data.jobPost.jobType.imagePath}" />'
         alt="<spring:message code="jobCard.jobs.imageAlt" arguments="${jobTypeName}"/>">
    <div class="card-body">
        <h5 class="card-title job-card-title"><c:out value="${requestScope.data.jobPost.title}"/></h5>
        <h6 class="card-text job-card-type capitalize-first-letter">
            <c:out value="${jobTypeName}"/>
        </h6>
        <div class="job-card-price-container">
            <p class="job-card-price">
                <spring:message code="${requestScope.data.rateType.stringCode}"
                                arguments="${requestScope.data.price}"/>
            </p>
        </div>
    </div>
    <ul class="list-group list-group-flush">
        <li class="list-group-item job-card-detail">
            <i class="fas fa-map-marker-alt job-card-detail"></i>
            <p class="job-card-detail capitalize-first-letter">
                <spring:message code="${requestScope.data.jobPost.zones[0].stringCode}"/>
                <c:if test="${requestScope.data.jobPost.zones.size() > 1}">
                    <spring:message code="jobCard.jobs.extraZones" arguments="${requestScope.data.jobPost.zones.size() -1}"/>
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
