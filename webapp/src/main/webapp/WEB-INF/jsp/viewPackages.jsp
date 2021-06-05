<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        <spring:message code="jobPost.packages.manage.title" var="text"/>
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
    <link href="${pageContext.request.contextPath}/resources/css/packages.css" rel="stylesheet"/>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/icon.svg">
    <link rel="apple-touch-icon" href="${pageContext.request.contextPath}/resources/images/apple-touch-icon.png">
</head>
<body>

<c:set var="path" value="/create-job-post" scope="request"/>
<c:set var="zoneValues" value="${zoneValues}" scope="request"/>
<%@include file="components/customNavBar.jsp" %>

<div class="content-container-transparent mt-3">
    <h3>
        <i class="mr-2 fas fa-cube"></i>
        <spring:message code="jobPost.packages.title"/>
    </h3>

    <div class="packages-frame">
        <div class="packages-container-wrapper">
            <img loading="lazy" style="height: 120px; position: absolute; top: -65px; right: -40px"
                 src="<c:url value="/resources/images/circles1-v1.svg"/>" alt="">
            <img loading="lazy" style="height: 85px; position: absolute; top: 0; right: 10px"
                 src="<c:url value="/resources/images/package1.svg"/>" alt="">
            <div class="packages-container">
                <h4 class="font-weight-bold">
                    <spring:message code="packages.view.title"/>
                </h4>

                <a class="mt-4 ml-4" href="<c:url value="/job/${jobPost.id}"/>">
                    <i class="mr-2 fas fa-external-link-alt"></i>
                    <spring:message htmlEscape="true" code="jobPost.packages.manage.postTitle"
                                    arguments="${jobPost.title}"/>
                </a>
                <br>

                <a href="<c:url value="/job/${jobPost.id}/packages/add"/>"
                   style="margin-top: 40px"
                   class="btn btn-block btn-light add-package-btn text-uppercase">
                    <i style="font-size: 1.5rem" class="fas fa-plus-square text-primary mr-1"></i>
                    <span>
                        <spring:message code="packages.view.add"/>
                    </span>
                </a>

                <div style="margin-top: 40px" class="accordion" id="accordionPackages">
                    <c:forEach items="${packages}" var="pack" varStatus="status">
                        <hr style="border-top: 2px solid rgba(0,0,0,0.1)">
                        <div class="editable-package-container">
                            <div class="card custom-card flex-grow-1 mb-3 border-radius-package">
                                <div class="card custom-card border-radius-package"
                                     id="heading${status.index}">

                                    <button class="drop btn-block hirenet-blue-btn collapsed border-radius-package"
                                            type="button"
                                            data-toggle="collapse" data-target="#collapse${status.index}"
                                            aria-expanded="false"
                                            aria-controls="collapse${status.index}">
                                        <div class="package-info">
                                            <i class="fa fa-chevron-down text-white"></i>
                                            <i class="fa fa-chevron-up text-white"></i>
                                            <p class="package-title">
                                                <c:out value="${pack.title}"/>
                                            </p>
                                            <div class="custom-row">
                                                <div class="package-price end-items-item mr-2">
                                                    <p class="text-center mt-2">
                                                        <spring:message code="jobPost.jobs.price"/>
                                                    </p>
                                                    <div class="chip">
                                                        <spring:message htmlEscape="true" code="${pack.rateType.stringCode}"
                                                                        arguments="${pack.price}"/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </button>
                                </div>
                                <div id="collapse${status.index}" class="collapse package-desc"
                                     aria-labelledby="heading${status.index}"
                                     data-parent="#accordionPackages">
                                    <div class="card-body">
                                        <p class="package-text">
                                            <spring:message code="jobPost.package.description"/><br/>
                                        </p>
                                        <p>
                                            <c:out value="${pack.description}"/>
                                        </p>
                                    </div>
                                </div>
                            </div>
                            <div class="package-controls-container">
                                <a href="<c:url value="/job/${jobPost.id}/packages/${pack.id}/edit"/>"
                                   class="btn package-control-edit btn-link text-uppercase">
                                    <i class="fas fa-edit"></i>
                                    <spring:message code="edit"/>
                                </a>
                                <c:if test="${packages.size() > 1}">
                                    <c:url value="/job/${jobPost.id}/packages" var="postPath"/>
                                    <form:form modelAttribute="deletePackageForm" action="${postPath}" method="post"
                                               cssStyle="margin-bottom: 0">
                                        <button type="submit" class="btn package-control-delete text-uppercase">
                                            <i class="fas fa-trash-alt"></i>
                                            <spring:message code="delete"/>
                                        </button>

                                        <form:hidden path="id" value="${pack.id}"/>
                                    </form:form>
                                </c:if>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="components/footer.jsp"/>
</div>
</body>
</html>
