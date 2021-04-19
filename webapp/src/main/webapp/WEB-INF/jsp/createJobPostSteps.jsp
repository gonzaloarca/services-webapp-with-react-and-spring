<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>
        <spring:message code="jobPost.create.title" var="text"/>
        <spring:message code="title.name" arguments="${text}"/>
    </title>

    <!-- Bootstrap 4.5.2 CSS minified -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">

    <!-- jQuery 3.6.0 minified dependency for Bootstrap JS libraries -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"
            integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>

    <!-- Bootstrap 4.5.2 JS libraries minified -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
            integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
            crossorigin="anonymous"></script>

    <!-- FontAwesome 5 -->
    <script src="https://kit.fontawesome.com/108cc44da7.js" crossorigin="anonymous"></script>

    <link href="${pageContext.request.contextPath}/resources/css/styles.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/resources/css/createjobpoststeps.css" rel="stylesheet"/>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/icon.svg">
    <link rel="apple-touch-icon" href="${pageContext.request.contextPath}/resources/images/apple-touch-icon.png">
</head>
<body>
<jsp:include page="customNavBar.jsp">
    <jsp:param name="path" value="/create-job-post"/>
</jsp:include>
<div class="content-container-transparent mt-3">
    <h3>
        <img style="height: 30px; padding-bottom: 5px" src="<c:url value="/resources/images/add-1.svg"/>" alt="">
        Publicar un nuevo servicio
    </h3>

    <c:url value="/create-job-post" var="postPath"/>
    <form:form modelAttribute="createJobPostForm" action="${postPath}" method="post">

        <div class="step-container-wrapper">

            <img style="height: 120px; position: absolute; top: -65px; right: -40px"
                 src="<c:url value="/resources/images/circles1-v1.svg"/>" alt="">
            <img style="height: 55px; position: absolute; top: 10px; right: 20px"
                 src="<c:url value="/resources/images/job-1.svg"/>" alt="">

            <div class="step-container">
                <h4>Datos de la publicación</h4>
                <p class="step-subtitle">Paso 1 de 6</p>
                <br>

                <div class="input-container">
                    <form:label path="jobType" class="input-label"
                                for="serviceTypeSelect">
                        <spring:message code="jobPost.create.service.select"/>
                    </form:label>

                    <spring:message code="jobPost.create.service.type" var="serviceType"/>

                    <form:select path="jobType" class="form-control w-100" id="serviceTypeSelect">
                        <form:option value="" label="${serviceType}"/>

                        <c:forEach items="${jobTypes}" var="type">
                            <form:option value="${type.value}">
                                <spring:message code="${type.stringCode}"/>
                            </form:option>
                        </c:forEach>

                    </form:select>
                    <form:errors path="jobType" class="form-error" element="p"/>
                    <div class="button-controls">
                        <button class="btn btn-outline-secondary hirenet-grey-outline-btn text-uppercase mr-2"
                                type="button">
                            Volver atrás
                        </button>
                        <button class="btn btn-primary hirenet-blue-btn text-uppercase" type="button">
                            Siguiente
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </form:form>
</div>
</body>
</html>
