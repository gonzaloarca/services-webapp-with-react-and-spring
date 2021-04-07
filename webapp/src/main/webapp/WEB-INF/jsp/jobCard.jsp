<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>

<div class="card job-card" style="width: 18rem;">
    <%-- TODO: Poner alt correcto e imagen de usuario--%>
    <img class="card-img-top job-card-img"
         src='<c:url value="${pageContext.request.contextPath}/resources/images/service-default.jpg" />'
         alt="">
    <div class="card-body">
        <h5 class="card-title job-card-title"><c:out value="${requestScope.data.jobPost.title}"/></h5>
        <h6 class="card-text job-card-type capitalize-first-letter">
            <c:out value="${requestScope.data.jobPost.jobType}"/></h6>
        <div class="job-card-price-container">
            <p class="job-card-price">
                <spring:message code="${requestScope.data.rateType.toString()}"
                                arguments="${requestScope.data.price}"/>
            </p>
        </div>
    </div>
    <ul class="list-group list-group-flush">
        <li class="list-group-item job-card-detail">
            <i class="fas fa-map-marker-alt job-card-detail" style="font-size: 25px; color: gray"></i>
            <p class="job-card-detail capitalize-first-letter">${requestScope.data.jobPost.zones[0].message}
                <c:if test="${requestScope.data.jobPost.zones.size() > 1}">
                    y ${requestScope.data.jobPost.zones.size() -1} más
                </c:if>
            </p>
        </li>
        <li class="list-group-item job-card-detail">
            <i class="fas fa-check job-card-detail" style="font-size: 25px; color: gray"></i>
            <p class="job-card-detail">${requestScope.data.contractsCompleted} completados</p>
        </li>

    </ul>
    <div class="card-body" style="display: flex; justify-content: center; align-items: center">
        <a href="${pageContext.request.contextPath}/job/${requestScope.data.jobPost.id}"
           class="btn btn-outline-primary text-uppercase stretched-link">Ver detalles</a>
    </div>
</div>
</body>
</html>
