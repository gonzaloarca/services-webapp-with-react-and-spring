<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<html>
<head>

    <title>
        <spring:message code="qualifycontract.title" var="text"/>
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
    <link href="${pageContext.request.contextPath}/resources/css/qualifycontract.css" rel="stylesheet"/>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/icon.svg">
    <link rel="apple-touch-icon" href="${pageContext.request.contextPath}/resources/images/apple-touch-icon.png">
</head>
<body>
<c:set var="zoneValues" value="${zoneValues}" scope="request"/>
<%@include file="components/customNavBar.jsp" %>
<div class="content-container">
    <div class="qualify-service">
        <h2>
            <i class="bi bi-star-fill"></i>
            <spring:message code="qualifycontract.review.qualifyprofessional"
                            arguments="${jobCard.jobPost.user.username}"/>
        </h2>

        <c:set var="data" value="${jobCard}" scope="request"/>
        <div class="card custom-card">
            <div class="card-body">
                <c:import url="components/serviceCard.jsp"/>
            </div>
        </div>
    </div>
    <div class="qualify-form">
        <form:form modelAttribute="reviewForm"
                   action="${pageContext.request.contextPath}/qualify-contract/${contractId}" method="post"
                   enctype="multipart/form-data">
            <h3 class="m-0">
                <spring:message code="qualifycontract.review.pickrate"/></h3>
            <div class="qualify-rate">
                <form:radiobutton path="rate" id="star5" name="rate" value="5"/>
                <form:label path="rate" for="star5" title="text">5 stars</form:label>
                <form:radiobutton path="rate" id="star4" name="rate" value="4"/>
                <form:label path="rate" for="star4" title="text">4 stars</form:label>
                <form:radiobutton path="rate" id="star3" name="rate" value="3"/>
                <form:label path="rate" for="star3" title="text">3 stars</form:label>
                <form:radiobutton path="rate" id="star2" name="rate" value="2"/>
                <form:label path="rate" for="star2" title="text">2 stars</form:label>
                <form:radiobutton path="rate" id="star1" name="rate" value="1" checked="checked"/>
                <form:label path="rate" for="star1" title="text">1 star</form:label>
            </div>
            <form:label path="description" class="custom-label">
                <spring:message code="qualifycontract.review.description"/>
            </form:label>
            <spring:message code="qualifycontract.review.description.placeholder" var="descriptionPlaceholder"/>
            <form:textarea class="form-control text-input" rows="6" path="description" maxlength="100"
                           placeholder="${descriptionPlaceholder}"/>
            <form:errors path="description" cssClass="form-error" element="p"/>

            <form:label path="title" class="custom-label">
                <spring:message code="qualifycontract.review.title"/>
            </form:label>
            <spring:message code="qualifycontract.review.title.placeholder" var="titlePlaceholder"/>
            <form:input type="text" class="form-control custom-input" name="title"
                        placeholder="${titlePlaceholder}" maxlength="100" path="title"/>
            <form:errors path="title" cssClass="form-error" element="p"/>

            <div class="submit-button-container p-5">
                <button class="btn btn-primary hirenet-yellow-btn" type="submit">
                    <spring:message code="qualifycontract.review.submit"/>
                </button>
            </div>
        </form:form>
    </div>
</div>
<jsp:include page="components/footer.jsp"/>
</body>
</html>

