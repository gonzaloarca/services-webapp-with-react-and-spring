<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>
        <spring:message code="navigation.index" var="text"/>
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

    <link href="${pageContext.request.contextPath}/resources/css/styles.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/resources/css/index.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/resources/css/jobcard.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/resources/css/searchBar.css" rel="stylesheet"/>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/icon.svg">
    <link rel="apple-touch-icon" href="${pageContext.request.contextPath}/resources/images/apple-touch-icon.png">
</head>
<body>
<c:set var="path" value="/" scope="request"/>
<c:set var="withoutColor" value="true" scope="request"/>
<c:set var="zoneValues" value="${zoneValues}" scope="request"/>
<c:set var="jobCardSize" value="${jobCards.size()}"/>
<%@include file="components/customNavBar.jsp" %>
<%@include file="components/searchBar.jsp" %>

<div>
    <div style="z-index: 2" class="landing-row-shadow">
        <div class="landing-row">
            <h3><spring:message code="index.jobs.title"/></h3>
            <div class="index-category-list-container">
                <c:forEach items="${categories}" var="category">
                    <a class="index-category-href" onclick="redirectCategory(${category.value})">
                        <spring:message code="${category.stringCode}" var="jobTypeName"/>
                        <img src='<c:url value="/resources/images/${category.imagePath}" />'
                             alt="<spring:message code="jobCard.jobs.imageAlt" arguments="${category}"/>">
                        <p>${jobTypeName}</p>
                    </a>
                </c:forEach>
                <div class="index-category-href">
                    <a href="${pageContext.request.contextPath}/categories">
                        <img src="<c:url value="/resources/images/morecategories1.svg"/>"
                             alt="<spring:message code="index.jobs.extraTypes"/>">
                        <p><spring:message code="index.jobs.seeMore"/><i class="fas fa-chevron-right"></i></p>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
<div style="background-color: white" class="landing-row-shadow">
    <div class="landing-row">
        <h3><spring:message code="index.services.title"/></h3>
        <div class="job-display-container">
            <c:if test="${jobCards.size() > 0}">
                <c:forEach items="${jobCards}" var="jobCard">
                    <c:set var="data" value="${jobCard}" scope="request"/>
                    <c:import url="components/jobCard.jsp"/>
                </c:forEach>
            </c:if>
            <c:if test="${jobCards.size() == 0}">
                <div class="result-div">
                    <img
                            src="<c:url value="/resources/images/unavailable-1.svg"/> "
                            alt="<spring:message code="index.jobs.noResults"/> ">
                    <p class="result-text">
                        <spring:message code="index.jobs.noResults"/>
                    </p>
                    <p class="result-sub-text">
                        <spring:message code="index.jobs.sorry"/>
                    </p>
                </div>
            </c:if>
        </div>
        <c:set var="listSize" value="${jobCardSize}" scope="request"/>
        <c:set var="maxPage" value="${maxPage}" scope="request"/>
        <c:set var="currentPages" value="${currentPages}" scope="request"/>
        <%@include file="components/bottomPaginationBar.jsp" %>
    </div>
</div>
<div class="landing-bottom landing-row-shadow"
     style="background: url('${pageContext.request.contextPath}/resources/images/publish-landing-bg-1.svg')" >
    <h3>
        <spring:message code="index.createJobPost.question"/>
        <br>
        <spring:message code="index.createJobPost.proposition"/>
    </h3>
    <a class="btn hirenet-blue-btn" href="${pageContext.request.contextPath}/create-job-post"><spring:message code="index.createJobPost.button"/></a>
    <div class="mt-5">
        <jsp:include page="components/footer.jsp"/>
    </div>
</div>

</div>
<script>
    // Para modificar el href con la ubicacion seleccionada
    function redirectCategory(category) {
        var auxZoneId = sessionStorage.getItem("pickedZoneId");
        sessionStorage.setItem("pickedCategoryId", category);
        if (auxZoneId) {
            window.location.href = "${pageContext.request.contextPath}" + '/search?zone=' + auxZoneId +
                '&query=&category=' + category;
        } else {
            window.location.href = "${pageContext.request.contextPath}" + '/search?zone=' +
                '&query=&category=' + category;
        }
    }
</script>
</body>
</html>