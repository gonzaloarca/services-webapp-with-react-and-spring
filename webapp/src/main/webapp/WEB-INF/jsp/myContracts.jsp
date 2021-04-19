<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<html>
<head>

    <title>
        <spring:message code="mycontracts.title" var="text"/>
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
    <link href="${pageContext.request.contextPath}/resources/css/mycontracts.css" rel="stylesheet"/>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/icon.svg">
    <link rel="apple-touch-icon" href="${pageContext.request.contextPath}/resources/images/apple-touch-icon.png">
</head>
<body>
<jsp:include page="components/customNavBar.jsp">
    <jsp:param name="path" value="/my-contracts"/>
</jsp:include>
<div class="content-container-transparent">
    <jsp:include page="components/siteHeader.jsp">
        <jsp:param name="code" value="mycontracts.title"/>
    </jsp:include>
    <div class="content-container">
        <c:forEach var="contractCard" items="${contractCards}" varStatus="status">
            <c:set var="data" value="${contractCard.jobCard}" scope="request"/>
            <div class="row">
                <div class="contract-service mr-4">
                    <c:import url="components/serviceCard.jsp"/>
                </div>
                <div class="contract-buttons-card">
                    <h4 class="contract-contact-text">
                        <i class="fa fa-info-circle" aria-hidden="true"></i>
                        CONTACTO
                    </h4>
                    <c:choose>
                        <c:when test="${contractCard.review != null}">
                            <jsp:include page="components/rateStars.jsp">
                                <jsp:param name="rate" value="${contractCard.review.rate}"/>
                            </jsp:include>
                        </c:when>
                        <c:otherwise>
                            <h4 class="contract-review-text">
                                <i class="bi bi-star"></i>
                                CALIFICAR
                            </h4>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <c:if test="${status.index != contractCards.size()-1}">
                <hr class="hr1"/>
            </c:if>
        </c:forEach>
    </div>
</div>
<jsp:include page="components/footer.jsp"/>
</body>
</html>
