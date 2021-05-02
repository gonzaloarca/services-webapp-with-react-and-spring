<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page buffer="64kb" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        <spring:message code="jobPost.packages.edit.title" var="text"/>
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
    <link href="${pageContext.request.contextPath}/resources/css/addpackage.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/resources/css/packages.css" rel="stylesheet"/>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/icon.svg">
    <link rel="apple-touch-icon" href="${pageContext.request.contextPath}/resources/images/apple-touch-icon.png">
</head>
<body>
<c:set var="path" value="/create-job-post" scope="request"/>
<c:set var="zoneValues" value="${zoneValues}" scope="request"/>
<%@include file="components/customNavBar.jsp" %>

<div class="content-container-transparent mt-3 mb-3 pb-2">
    <h3>
        <i class="mr-2 fas fa-cube"></i>
        Administrar paquetes
    </h3>

    <div class="packages-frame">
        <div class="packages-container-wrapper">
            <div class="packages-container">
                <img style="height: 120px; position: absolute; top: -65px; right: -40px"
                     src="<c:url value="/resources/images/circles1-v1.svg"/>" alt="">
                <img style="height: 85px; position: absolute; top: 0; right: 10px"
                     src="<c:url value="/resources/images/package1.svg"/>" alt="">

                <h4 class="font-weight-bold">Editar paquete</h4>


                <c:url value="/job/${postId}/packages/${packageId}/edit" var="postPath"/>
                <form:form modelAttribute="editPackageForm" method="post" action="${postPath}"
                           cssClass="mt-4">
                    <div class="package-input">
                        <form:label path="title" for="package-title-input">
                            <spring:message code="jobPost.create.package.title"/>
                        </form:label>

                        <spring:message code="jobPost.create.package.title" var="pTitlePlaceholder"/>

                        <form:input path="title" id="package-title-input" type="text" class="form-control"
                                    placeholder="${pTitlePlaceholder}" maxlength="100"/>

                        <form:errors path="title" class="form-error" element="p"/>
                    </div>

                    <div class="package-input">
                        <form:label path="description"
                                    for="package-description-input">
                            <spring:message code="jobPost.create.package.description"/>
                        </form:label>

                        <spring:message code="jobPost.create.package.descriptionPlaceholder"
                                        var="descriptionPlaceholder"/>
                        <form:textarea path="description" id="package-description-input"
                                       class="form-control"
                                       placeholder="${descriptionPlaceholder}" maxlength="100"
                                       rows="3"/>

                        <form:errors path="description" class="form-error" element="p"/>
                    </div>

                    <div id="package-ratetype-input" class="package-input">
                        <form:label path="rateType"
                                    style="display: block; margin-bottom: 15px">
                            <spring:message code="jobPost.create.package.rateType"/>
                        </form:label>
                        <div class="center">
                            <div class="form-check form-check-inline">
                                <form:radiobutton path="rateType" id="hourly-radio"
                                                  class="form-check-input" name="inlineRadioOptions"
                                                  value="0"/>
                                <form:label path="rateType" for="hourly-radio"
                                            class="form-check-label">
                                    <spring:message code="jobPost.create.package.hourly"/>
                                </form:label>
                            </div>
                            <div class="form-check form-check-inline">
                                <form:radiobutton path="rateType" id="onetime-radio"
                                                  class="form-check-input" name="inlineRadioOptions"
                                                  value="1"/>
                                <form:label path="rateType" for="onetime-radio"
                                            class="form-check-label">
                                    <spring:message code="jobPost.create.package.oneTime"/>
                                </form:label>
                            </div>
                            <div class="form-check form-check-inline">
                                <form:radiobutton path="rateType" id="tbd-radio"
                                                  class="form-check-input"
                                                  name="inlineRadioOptions"
                                                  value="2"/>
                                <form:label path="rateType" for="tbd-radio"
                                            class="form-check-label">
                                    <spring:message code="jobPost.create.package.tbd"/>
                                </form:label>
                            </div>
                        </div>
                        <form:errors path="rateType" class="form-error" element="p"/>
                    </div>

                    <div class="package-input">
                        <form:label path="price" for="package-price-input">
                            <spring:message code="jobPost.create.package.price"/>
                        </form:label>
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text">
                                    <spring:message code="jobPost.create.package.argentinePeso"/>
                                </span>
                            </div>
                            <spring:message code="jobPost.create.package.price" var="pricePlaceholder"/>
                            <form:input path="price" id="package-price-input" type="number" step="any"
                                        class="form-control" min="0" max="99999999999"
                                        placeholder="${pricePlaceholder}"/>
                        </div>
                        <form:errors path="price" class="form-error" element="p"/>
                    </div>

                    <button class="btn btn-block btn-primary text-uppercase" style="margin: 40px 0 !important;"
                            type="submit">
                        Confirmar
                    </button>
                </form:form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="components/footer.jsp"/>

<script>
    // Script para deshabilitar el input de precio cuando esta TBD el radio
    const tbdRadio = $("#tbd-radio");
    const onetimeRadio = $("#onetime-radio");
    const hourlyRadio = $("#hourly-radio");
    const priceInput = $("#package-price-input");

    tbdRadio.on('click', function () {
        priceInput.prop('readonly', true).val('');
    })

    if (tbdRadio.is(':checked')) {
        priceInput.prop('readonly', true);
    }

    onetimeRadio.on('click', function () {
        priceInput.prop('readonly', false);
    });

    hourlyRadio.on('click', function () {
        priceInput.prop('readonly', false);
    });

    // Script para vaciar el input de precio en caso de que el precio sea a acordar
    if (onetimeRadio.checked && priceInput.val() !== "")
        priceInput.val("");

</script>
</body>
</html>
