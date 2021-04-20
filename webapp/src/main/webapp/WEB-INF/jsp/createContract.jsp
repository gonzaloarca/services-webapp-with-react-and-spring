<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <!-- TODO: poner titulo correcto -->
    <title>
        <spring:message code="contract.create.page.title" var="text"/>
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
    <script src="https://kit.fontawesome.com/108cc44da7.js" crossorigin="anonymous"></script>

    <link href="${pageContext.request.contextPath}/resources/css/styles.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/resources/css/createcontract.css" rel="stylesheet"/>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/icon.svg">
    <link rel="apple-touch-icon" href="${pageContext.request.contextPath}/resources/images/apple-touch-icon.png">

</head>
<body class="body">
<c:set var="zoneValues" value="${zoneValues}" scope="request"/>
<%@include file="components/customNavBar.jsp" %>
<div class="content-container-transparent">

    <!-- Navigation -->
    <div class="row">
        <nav aria-label="breadcrumb" style="width: 100%">
            <ol class="breadcrumb bg-white">
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">
                    <spring:message code="navigation.index"/>
                </a></li>
                <li class="breadcrumb-item active" aria-current="page">
                    <p class="capitalize-first-letter">
                        <spring:message code="${jobPost.jobType.stringCode}"/>
                    </p>
                </li>
            </ol>
        </nav>
    </div>

    <!-- Title Header -->
    <div class="row top-row">
        <div class="header-container">
            <!--div class="header-back-button">
                <a class="header-back-icon">
                    <i class="fas fa-arrow-left fa-2x"></i>
                </a>
            </div-->
            <h2 class="header-title">
                <spring:message code="contract.create.page.title"/>
            </h2>
        </div>
        <c:choose>
            <c:when test="${imageList.size() == 0}">
                <c:url value="/resources/images/${jobPost.jobType.imagePath}" var="imageSrc"/>
            </c:when>
            <c:otherwise>
                <c:set value="data:${imageList[0].image.type};base64,${imageList[0].image.string}"
                       var="imageSrc"/>
            </c:otherwise>
        </c:choose>
        <spring:message code="${jobPost.jobType.stringCode}" var="jobTypeName"/>
        <img class="header-img"
             src='${imageSrc}'
             alt="<spring:message code="jobCard.jobs.imageAlt" arguments="${jobTypeName}"/>">
    </div>

    <div class="row bottom-row">

        <!-- Contract Form -->
        <div class="first-col">
            <div class="contract-form">
                <!-- Title -->
                <h3 style="font-weight: bold">
                    <spring:message code="contract.create.form.title"/>
                </h3>
                <p style="margin: 0">
                    <spring:message code="contract.create.form.required"/>
                </p>
                <hr class="divider-bar"/>
                <!-- Form Entries -->
                <c:url value="/contract/package/${packId}" var="postUrl"/>
                <form:form class="contract-input" modelAttribute="contractForm"
                           action="${postUrl}" method="post"
                           enctype="multipart/form-data">

                    <!-- Description -->
                    <div class="form-row">
                        <div class="blue-circle">
                            <p class="circle-text">1</p>
                        </div>
                        <div class="col-10 label-and-input">
                            <form:label path="description" class="form-text">
                                <spring:message code="contract.create.form.description"/>
                            </form:label>
                            <spring:message code="contract.create.form.description.placeholder" var="descPlaceholder"/>
                            <form:textarea class="form-control text-input" rows="6" path="description" maxlength="100"
                                           placeholder="${descPlaceholder}"/>
                            <form:errors path="description" cssClass="form-error" element="p"/>
                        </div>
                    </div>

                    <!-- Image -->
                    <div class="form-row">
                        <div class="yellow-circle">
                            <p class="circle-text">2</p>
                        </div>
                        <div class="col-10 label-and-input">
                            <form:label path="image" class="form-text">
                                <spring:message code="contract.create.form.image"/>
                            </form:label>
                            <form:input type="file" path="image"/>
                            <form:errors path="image" cssClass="form-error" element="p"/>
                        </div>
                    </div>

                    <!-- Submit Button -->
                    <div class="submit-button">
                        <button class="btn btn-primary" type="submit">
                            <spring:message code="contract.create.form.submit"/>
                        </button>
                    </div>

                </form:form>
            </div>
        </div>

        <!-- Job Detail -->
        <div class="second-col">
            <div class="job-info">
                <h5 class="info-title">
                    <spring:message code="contract.create.detail.title"/>
                </h5>
                <img class="info-img"
                     src='${imageSrc}'
                     alt="<spring:message code="jobCard.jobs.imageAlt" arguments="${jobTypeName}"/>">
                <div class="container">
                    <!-- Job Title -->
                    <div class="row info-row">
                        <div class="info-left-col">
                            <i class="fas fa-briefcase fa-2x"></i>
                        </div>
                        <p class="info-right-col">
                            <c:out value="${jobPost.title}"/>
                        </p>
                    </div>
                    <hr class="divider-bar-info"/>
                    <!-- Package Name -->
                    <div class="row info-row">
                        <div class="info-left-col">
                            <i class="fas fa-box-open fa-2x"></i>
                        </div>
                        <p class="info-right-col">
                            <c:out value="${jobPack.title}"/>
                        </p>
                    </div>
                    <hr class="divider-bar-info"/>
                    <!-- Location -->
                    <div class="row info-row">
                        <div class="info-left-col">
                            <i class="fas fa-map-marker-alt fa-2x"></i>
                        </div>
                        <div class="info-right-col" style="display: flex">
                            <c:forEach items="${jobPost.zones}" var="zone" varStatus="status">
                                <p class="capitalize-first-letter">
                                    <spring:message code="${zone.stringCode}"/><c:if
                                        test="${status.index != jobPost.zones.size()-1}">,&nbsp</c:if>
                                </p>
                            </c:forEach>
                        </div>
                    </div>
                    <hr class="divider-bar-info"/>
                    <!-- Professional -->
                    <div class="row info-row">
                        <div class="info-left-col">
                            <!-- TODO: cambiar este icono por imagen de perfil -->
                            <i class="fas fa-user fa-2x"></i>
                        </div>
                        <p class="info-right-col">
                            <c:out value="${jobPost.user.username}"/>
                        </p>
                    </div>
                    <hr class="divider-bar-info"/>
                    <!-- Hours -->
                    <div class="row info-row">
                        <div class="info-left-col">
                            <i class="far fa-clock fa-2x"></i>
                        </div>
                        <p class="info-right-col">
                            <c:out value="${jobPost.availableHours}"/>
                        </p>
                    </div>
                    <hr class="divider-bar-info"/>
                    <!-- Price -->
                    <div class="row info-row">
                        <div class="info-left-col">
                            <i class="fas fa-dollar-sign fa-2x"></i>
                        </div>
                        <div class="info-right-col">
                            <p class="price-tag">
                                <spring:message code="${jobPack.rateType.stringCode}"
                                                arguments="${jobPack.price}"/>
                            </p>
                        </div>
                    </div>
                </div>
            </div>

        </div>

    </div>
</div>
<jsp:include page="components/footer.jsp"/>
</body>
</html>
