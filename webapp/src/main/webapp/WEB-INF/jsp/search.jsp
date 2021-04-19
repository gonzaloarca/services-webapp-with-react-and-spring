<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>
        <spring:message code="search.title" var="text"/>
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
    <link href="${pageContext.request.contextPath}/resources/css/search.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/resources/css/jobcard.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/resources/css/searchBar.css" rel="stylesheet"/>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/icon.svg">
    <link rel="apple-touch-icon" href="${pageContext.request.contextPath}/resources/images/apple-touch-icon.png">
</head>
<body>
<jsp:include page="components/customNavBar.jsp"/>
<%@ include file="components/searchBar.jsp" %>

<div class="content-container d-flex">
    <div class="custom-card filter-card">
        <h3>
            <spring:message code="search.filters"/>
        </h3>
        <hr class="hr1"/>
        <h4>
            <spring:message code="search.categories"/>
        </h4>
        <c:forEach items="${categories}" var="categorie" varStatus="status">
            <span class="mb-1 custom-row align-items-center">
                <c:if test="${pickedCategory == status.index}">
                    <a href="${pageContext.request.contextPath}/search?zone=${pickedZone.ordinal()}&query=${query}&category=-1">
                        <i class="fa fa-times unselect-category"></i>
                    </a>
                </c:if>
                <p class="capitalize-first-letter">
                    <a class="category ${pickedCategory == status.index? 'pickedCategory':''}"
                       href="${pageContext.request.contextPath}/search?zone=${pickedZone.ordinal()}&query=${query}&category=${status.index}">
                        <spring:message code="${categorie.stringCode}"/>
                    </a>
                </p>
            </span>
        </c:forEach>
    </div>
    <div class="custom-row">
        <c:if test="${pickedZone != null}">
            <div class="search-title">
                <h3>
                    <c:if test="${query.length() == 0}">
                        <spring:message code="search.noquery.results"/>
                    </c:if>
                    <c:if test="${!(query.length() == 0)}">
                        <spring:message code="search.results" arguments="${query}"/>
                    </c:if>
                    <spring:message code="${pickedZone.stringCode}"/>
                    <c:if test="${pickedZone == null}">
                        <spring:message code="search.badQuery"/>
                    </c:if>
                </h3>
            </div>
            <hr class="hr1"/>
            <div class="job-display-container">
                <c:if test="${jobCards.size() > 0}">
                    <c:forEach items="${jobCards}" var="jobCard" varStatus="status">
                        <c:set var="data" value="${jobCard}" scope="request"/>
                        <c:import url="components/jobCard.jsp"/>
                    </c:forEach>
                </c:if>
                <c:if test="${jobCards.size() == 0}">
                    <div class="result-div">
                        <i class="fas fa-search mb-4"></i>
                        <p class="result-text">
                            <spring:message code="search.jobs.noResults"/>
                        </p>
                        <p class="result-sub-text">
                            <spring:message code="index.jobs.sorry"/>
                        </p>
                    </div>
                </c:if>
            </div>
        </c:if>

        <c:if test="${pickedZone == null}">
            <div class="search-title " style="width: 100%; justify-content: center">
                <h3>
                    <spring:message code="search.badQuery"/>
                </h3>
            </div>
            <hr class="hr1"/>
            <div class="result-div">
                <i class="fas fa-search mb-4"></i>
                <p class="result-text">
                    <spring:message code="search.jobs.badSearch"/>
                </p>
            </div>
        </c:if>
    </div>
</div>
<jsp:include page="components/footer.jsp"/>
</body>
</html>
