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

    <!-- Popper.js 1.12.9 for Bootstrap -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
            integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
            crossorigin="anonymous"></script>

    <!-- Bootstrap 4.5.2 JS libraries minified -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
            integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
            crossorigin="anonymous"></script>

    <!-- FontAwesome 5 -->
    <script src="https://kit.fontawesome.com/108cc44da7.js" crossorigin="anonymous"></script>

    <%-- Animation plugin for jQuery--%>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"
            integrity="sha512-0QbL0ph8Tc8g5bLhfVzSqxe9GERORsKhIn1IrpxDAgUsbBGz/V7iSav2zzW325XGd1OMLdL4UiqRJj702IeqnQ=="
            crossorigin="anonymous"></script>

    <link href="${pageContext.request.contextPath}/resources/css/styles.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/resources/css/createjobpoststeps.css" rel="stylesheet"/>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/icon.svg">
    <link rel="apple-touch-icon" href="${pageContext.request.contextPath}/resources/images/apple-touch-icon.png">
</head>
<body>
<c:set var="zoneValues" value="${zoneValues}" scope="request"/>
<%@include file="components/customNavBar.jsp" %>

<div class="content-container-transparent mt-3">
    <h3>
        <img style="height: 30px; padding-bottom: 5px" src="<c:url value="/resources/images/add-1.svg"/>" alt="">
        Publicar un nuevo servicio
    </h3>

    <c:url value="/create-job-post" var="postPath"/>
    <form:form modelAttribute="createJobPostForm" action="${postPath}" method="post" cssClass="step-frame">
        <div class="form-error-container">
            <form:errors path="jobType" cssClass="form-error-list" element="p"/>
            <form:errors path="title" cssClass="form-error-list" element="p"/>
            <form:errors path="jobPackage.title" cssClass="form-error-list" element="p"/>
            <form:errors path="jobPackage.description" cssClass="form-error-list" element="p"/>
            <form:errors path="jobPackage.rateType" cssClass="form-error-list" element="p"/>
            <form:errors path="jobPackage.price" cssClass="form-error-list" element="p"/>
            <form:errors path="availableHours" cssClass="form-error-list" element="p"/>
            <form:errors path="zones" cssClass="form-error-list" element="p"/>
        </div>

        <div class="step-container-wrapper" id="step-wrapper1">

            <img style="height: 120px; position: absolute; top: -65px; right: -40px"
                 src="<c:url value="/resources/images/circles1-v1.svg"/>" alt="">
            <img style="height: 55px; position: absolute; top: 10px; right: 20px"
                 src="<c:url value="/resources/images/job-1.svg"/>" alt="">

            <div class="step-container">
                <h4>Datos de la publicación</h4>
                <p class="step-subtitle">Paso 1 de 6</p>
                <br>

                <div class="input-container">
                    <form:label path="jobType" class="step-header-label"
                                for="jobTypeSelect">
                        <spring:message code="jobPost.create.service.select"/>
                    </form:label>

                    <spring:message code="jobPost.create.service.type" var="serviceType"/>

                    <form:select path="jobType" class="form-control w-100" id="jobTypeSelect">
                        <form:option value="" label="${serviceType}"/>

                        <c:forEach items="${jobTypes}" var="type">
                            <form:option value="${type.value}">
                                <spring:message code="${type.stringCode}"/>
                            </form:option>
                        </c:forEach>

                    </form:select>
                    <form:errors path="jobType" class="form-error" element="p"/>
                    <div class="button-controls">
                        <button class="continue-btn btn btn-primary hirenet-blue-btn text-uppercase" type="button">
                            Siguiente
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <div class="step-container-wrapper" id="step-wrapper2">

            <img style="height: 120px; position: absolute; top: -65px; right: -40px"
                 src="<c:url value="/resources/images/circles1-v1.svg"/>" alt="">
            <img style="height: 75px; position: absolute; top: 5px; right: 10px"
                 src="<c:url value="/resources/images/title1.svg"/>" alt="">

            <div class="step-container">
                <h4>Datos de la publicación</h4>
                <p class="step-subtitle">Paso 2 de 6</p>
                <br>

                <div class="input-container">
                    <form:label path="title" for="jobTitle"
                                class="step-header-label">
                        <spring:message code="jobPost.create.publication.title"/>
                    </form:label>
                    <spring:message code="jobPost.create.publication.placeholder" var="titlePlaceholder"/>
                    <form:input path="title" id="jobTitle" type="text" class="form-control"
                                placeholder="${titlePlaceholder}" maxlength="100"/>
                    <form:errors path="title" class="form-error" element="p"/>
                    <div class="button-controls">
                        <button class="back-btn btn btn-outline-secondary hirenet-grey-outline-btn text-uppercase mr-2"
                                type="button">
                            Volver atrás
                        </button>
                        <button class="continue-btn btn btn-primary hirenet-blue-btn text-uppercase" type="button">
                            Siguiente
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <div class="step-container-wrapper" id="step-wrapper3">

            <img style="height: 120px; position: absolute; top: -65px; right: -40px"
                 src="<c:url value="/resources/images/circles1-v1.svg"/>" alt="">
            <img style="height: 85px; position: absolute; top: 0; right: 10px"
                 src="<c:url value="/resources/images/package1.svg"/>" alt="">

            <div class="step-container">
                <h4>Datos de la publicación</h4>
                <p class="step-subtitle">Paso 3 de 6</p>
                <br>

                <div class="input-container">
                    <div class="step-header-label">
                        <spring:message code="jobPost.create.package.required"/>

                        <spring:message code="jobPost.create.package.tooltipText" var="tooltipText"/>

                        <div data-toggle="tooltip" data-placement="right"
                             data-html="true" title="<p class='tooltip-text'>${tooltipText}</p>"
                             class="help-tooltip">
                            <spring:message code="jobPost.create.package.helpSymbol"/>
                        </div>
                    </div>

                    <div class="step-header-subtitle">
                        <span><i class="fas fa-caret-right"></i>
                            <spring:message code="jobPost.create.package.morePackagesHelp"/>
                        </span>
                    </div>

                    <div class="step-subtitle">
                        <spring:message code="contract.create.form.required"/>
                    </div>
                    <hr style="margin-top: 0;">

                    <div class="package-input">
                        <form:label path="jobPackage.title" for="package-title-input">
                            <spring:message code="jobPost.create.package.title"/>
                        </form:label>

                        <spring:message code="jobPost.create.package.title" var="pTitlePlaceholder"/>

                        <form:input path="jobPackage.title" id="package-title-input" type="text" class="form-control"
                                    placeholder="${pTitlePlaceholder}" maxlength="100"/>

                        <form:errors path="jobPackage.title" class="form-error" element="p"/>
                    </div>

                    <div class="package-input">
                        <form:label path="jobPackage.description"
                                    for="package-description-input">
                            <spring:message code="jobPost.create.package.description"/>
                        </form:label>

                        <spring:message code="jobPost.create.package.descriptionPlaceholder"
                                        var="descriptionPlaceholder"/>
                        <form:textarea path="jobPackage.description" id="package-description-input"
                                       class="form-control"
                                       placeholder="${descriptionPlaceholder}" maxlength="100"
                                       rows="3"/>

                        <form:errors path="jobPackage.description" class="form-error" element="p"/>
                    </div>

                    <div id="package-ratetype-input" class="package-input">
                        <form:label path="jobPackage.rateType"
                                    style="display: block; margin-bottom: 15px">
                            <spring:message code="jobPost.create.package.rateType"/>
                        </form:label>
                        <div class="center">
                            <div class="form-check form-check-inline">
                                <form:radiobutton path="jobPackage.rateType" id="hourly-radio"
                                                  class="form-check-input" name="inlineRadioOptions"
                                                  value="0"/>
                                <form:label path="jobPackage.rateType" for="hourly-radio"
                                            class="form-check-label">
                                    <spring:message code="jobPost.create.package.hourly"/>
                                </form:label>
                            </div>
                            <div class="form-check form-check-inline">
                                <form:radiobutton path="jobPackage.rateType" id="onetime-radio"
                                                  class="form-check-input" name="inlineRadioOptions"
                                                  value="1"/>
                                <form:label path="jobPackage.rateType" for="onetime-radio"
                                            class="form-check-label">
                                    <spring:message code="jobPost.create.package.oneTime"/>
                                </form:label>
                            </div>
                            <div class="form-check form-check-inline">
                                <form:radiobutton path="jobPackage.rateType" id="tbd-radio"
                                                  class="form-check-input"
                                                  name="inlineRadioOptions"
                                                  value="2"/>
                                <form:label path="jobPackage.rateType" for="tbd-radio"
                                            class="form-check-label">
                                    <spring:message code="jobPost.create.package.tbd"/>
                                </form:label>
                            </div>
                        </div>
                        <form:errors path="jobPackage.rateType" class="form-error" element="p"/>
                    </div>

                    <div class="package-input">
                        <form:label path="jobPackage.price" for="package-price-input">
                            <spring:message code="jobPost.create.package.price"/>
                        </form:label>
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text">
                                    <spring:message code="jobPost.create.package.argentinePeso"/>
                                </span>
                            </div>
                            <spring:message code="jobPost.create.package.price" var="pricePlaceholder"/>
                            <form:input path="jobPackage.price" id="package-price-input" type="number" step="any"
                                        class="form-control" min="0" max="99999999999"
                                        placeholder="${pricePlaceholder}"/>
                        </div>
                        <form:errors path="jobPackage" class="form-error" element="p"/>
                    </div>

                    <div class="button-controls">
                        <button class="back-btn btn btn-outline-secondary hirenet-grey-outline-btn text-uppercase mr-2"
                                type="button">
                            Volver atrás
                        </button>
                        <button class="continue-btn btn btn-primary hirenet-blue-btn text-uppercase" type="button">
                            Siguiente
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <div class="step-container-wrapper" id="step-wrapper4">

            <img style="height: 120px; position: absolute; top: -65px; right: -40px"
                 src="<c:url value="/resources/images/circles1-v1.svg"/>" alt="">
            <img style="height: 55px; position: absolute; top: 10px; right: 20px"
                 src="<c:url value="/resources/images/images1.svg"/>" alt="">

            <div class="step-container">
                <h4>Datos de la publicación</h4>
                <p class="step-subtitle">Paso 4 de 6</p>
                <br>

                <div class="input-container">
                    <form:label path="title" for="jobTitle"
                                class="step-header-label mb-4">
                        <spring:message code="jobPost.create.images"/>
                    </form:label>

                    <div class="file-input-container">
                        <form:input id="imageInput" type="file" path="" multiple="multiple"/>
                    </div>

                    <p class="img-upload-disclaimer mt-1">
                        <spring:message code="jobPost.create.filedisclaimer"/>
                    </p>

                    <form:errors path="" class="form-error" element="p"/>

                    <div class="button-controls">
                        <button class="back-btn btn btn-outline-secondary hirenet-grey-outline-btn text-uppercase mr-2"
                                type="button">
                            Volver atrás
                        </button>
                        <button class="continue-btn btn btn-primary hirenet-blue-btn text-uppercase" type="button">
                            Siguiente
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <div class="step-container-wrapper" id="step-wrapper5">

            <img style="height: 120px; position: absolute; top: -65px; right: -40px"
                 src="<c:url value="/resources/images/circles1-v1.svg"/>" alt="">
            <img style="height: 80px; position: absolute; top: 5px; right: 20px"
                 src="<c:url value="/resources/images/clock1.svg"/>" alt="">

            <div class="step-container">
                <h4>Datos de la publicación</h4>
                <p class="step-subtitle">Paso 5 de 6</p>
                <br>

                <div class="input-container">
                    <form:label path="availableHours" for="availableHoursInput"
                                class="step-header-label">
                        <spring:message code="jobPost.create.availableHours"/>
                    </form:label>
                    <spring:message code="jobPost.create.availableHours.placeholder" var="hoursPlaceholder"/>
                    <form:textarea path="availableHours" id="availableHoursInput" class="form-control"
                                   placeholder="${hoursPlaceholder}"
                                   rows="5" maxlength="100"/>
                    <form:errors path="availableHours" class="form-error" element="p"/>
                    <div class="button-controls">
                        <button class="back-btn btn btn-outline-secondary hirenet-grey-outline-btn text-uppercase mr-2"
                                type="button">
                            Volver atrás
                        </button>
                        <button class="continue-btn btn btn-primary hirenet-blue-btn text-uppercase" type="button">
                            Siguiente
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <div class="step-container-wrapper" id="step-wrapper6">

            <img style="height: 120px; position: absolute; top: -65px; right: -40px"
                 src="<c:url value="/resources/images/circles1-v1.svg"/>" alt="">
            <img style="height: 80px; position: absolute; top: 10px; right: 20px"
                 src="<c:url value="/resources/images/location2.svg"/>" alt="">

            <div class="step-container">
                <h4>Datos de la publicación</h4>
                <p class="step-subtitle">Paso 6 de 6</p>
                <br>

                <div class="input-container">
                    <form:label path="zones"
                                class="step-header-label">
                        <spring:message code="jobPost.create.zones"/>
                    </form:label>

                    <div class="form-group has-search">
                        <span class="fa fa-search form-control-feedback"></span>
                        <input id="locationFilter" type="text" class="form-control"
                               placeholder="<spring:message code="jobPost.create.zones.placeholder"/>"/>
                    </div>

                    <div class="list-group location-list">
                        <c:forEach items="${zoneValues}" var="zone">
                            <label class="list-group-item">
                                <form:checkbox path="zones" class="form-check-input zone-checkbox"
                                               value="${zone.value}"/>
                                <span class="location-name">
                                    <spring:message code="${zone.stringCode}"/>
                                </span>
                            </label>
                        </c:forEach>
                    </div>

                    <form:errors path="zones" class="form-error" element="p"/>
                    <div class="button-controls">
                        <button class="back-btn btn btn-outline-secondary hirenet-grey-outline-btn text-uppercase mr-2"
                                type="button">
                            Volver atrás
                        </button>
                        <button class="btn btn-primary hirenet-blue-btn text-uppercase continue-btn"
                                type="button">
                            Siguiente
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <div class="step-container-wrapper" id="overview-wrapper">

            <img style="height: 120px; position: absolute; top: -65px; right: -40px"
                 src="<c:url value="/resources/images/circles1-v1.svg"/>" alt="">

            <div class="overview-container">
                <h4>Resumen de la publicación</h4>

                <div class="overview-content mt-4">

                    <div class="row">

                        <div class="col-7 overview-field-container-wrapper">
                            <img style="height: 45px; position: absolute; right: 8px; top: -10px"
                                 src="<c:url value="/resources/images/title1.svg"/>" alt=""/>

                            <div class="overview-field-container">
                                <p class="overview-field-header">
                                    Título
                                </p>

                                <div class="overview-field-data-container">
                                    <p id="jobTitleOverview"></p>
                                </div>
                            </div>
                        </div>

                        <div class="col-5 overview-field-container-wrapper">
                            <img style="height: 40px; position: absolute; right: 5px; top: -15px"
                                 src="<c:url value="/resources/images/job-1.svg"/>" alt=""/>

                            <div class="overview-field-container">
                                <p class="overview-field-header">
                                    Tipo de servicio
                                </p>

                                <div class="overview-field-data-container">
                                    <p id="jobTypeOverview"></p>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-12 overview-field-container-wrapper">
                            <img style="height: 55px; position: absolute; right: 5px; top: -15px"
                                 src="<c:url value="/resources/images/package1.svg"/>" alt=""/>

                            <div class="overview-field-container">
                                <p class="overview-field-header">
                                    Paquetes
                                </p>

                                <div class="accordion" id="accordionPackages">

                                    <div class="card custom-card mb-3">
                                        <div class="card custom-card " id="heading">

                                            <button class="drop btn-block hirenet-white-btn collapsed" type="button"
                                                    data-toggle="collapse" data-target="#collapse"
                                                    aria-expanded="false"
                                                    aria-controls="collapse">
                                                <div class="package-info">
                                                    <i class="fa fa-chevron-down"></i>
                                                    <i class="fa fa-chevron-up"></i>
                                                    <p id="package-title-overview">

                                                    </p>
                                                    <div class="custom-row">
                                                        <div class="package-price end-items-item mr-2">
                                                            <p class="text-center mt-2">
                                                                <spring:message code="jobPost.jobs.price"/>
                                                            </p>
                                                            <div id="price-chip" class="chip">
                                                                <p id="overview-price"></p>

                                                                <p class="display-none" id="tbd-overview-text">
                                                                    <spring:message code="JobPackage.RateType.TBD"/>
                                                                </p>

                                                                <div class="display-none ml-1" id="hourly-suffix">
                                                                    <spring:message
                                                                            code="JobPackage.RateType.hourlySuffix"/>
                                                                </div>
                                                                    <%-- <spring:message code="${pack.rateType.stringCode}"--%>
                                                                    <%--  arguments=""/>--%>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </button>
                                        </div>
                                        <div id="collapse" class="collapse package-desc"
                                             aria-labelledby="heading"
                                             data-parent="#accordionPackages">
                                            <div class="card-body">
                                                <p class="package-text">
                                                    <spring:message code="jobPost.package.description"/><br/>
                                                </p>
                                                <p id="package-description-overview">

                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-12 overview-field-container-wrapper">
                            <img style="height: 45px; position: absolute; right: 5px; top: -15px"
                                 src="<c:url value="/resources/images/images1.svg"/>" alt=""/>

                            <div class="overview-field-container">
                                <p class="overview-field-header">
                                    Imágenes
                                </p>

                                <div id="imageCarousel">
                                    No se seleccionaron imágenes
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">

                        <div class="col-7 overview-field-container-wrapper">
                            <img style="height: 45px; position: absolute; right: 5px; top: -15px"
                                 src="<c:url value="/resources/images/clock1.svg"/>" alt=""/>

                            <div class="overview-field-container">
                                <p class="overview-field-header">
                                    Horarios
                                </p>

                                <div class="overview-field-data-container">
                                    <p id="availableHoursOverview"></p>
                                </div>
                            </div>
                        </div>

                        <div class="col-5 overview-field-container-wrapper">
                            <img style="height: 45px; position: absolute; right: 5px; top: -20px"
                                 src="<c:url value="/resources/images/location3.svg"/>" alt=""/>

                            <div class="overview-field-container">
                                <p class="overview-field-header">
                                    Zonas de disponibilidad
                                </p>

                                <div id="zoneContainer" class="overview-field-data-container">
                                </div>

                            </div>
                        </div>
                    </div>
                </div>

                <div class="input-container">
                    <div class="button-controls">
                        <button class="back-btn btn btn-outline-secondary hirenet-grey-outline-btn text-uppercase mr-2"
                                type="button">
                            Volver atrás
                        </button>

                        <button class="btn btn-primary hirenet-blue-btn text-uppercase"
                                type="submit">
                            Publicar
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </form:form>
</div>

<script>

    // Script para habilitar tooltips de Bootstrap
    $(document).ready(function () {
        $("body").tooltip({selector: '[data-toggle=tooltip]'});
    });

    // Script para deshabilitar el input de precio cuando esta TBD el radio
    const tbdRadio = $("#tbd-radio");

    tbdRadio.on('click', function () {
        $("#package-price-input").prop('readonly', true).val('');
    })

    if (tbdRadio.is(':checked')) {
        $("#package-price-input").prop('readonly', true);
    }

    $("#onetime-radio").on('click', function () {
        $("#package-price-input").prop('readonly', false);
    });

    $("#hourly-radio").on('click', function () {
        $("#package-price-input").prop('readonly', false);
    });

    // Script para habilitar filtro por nombre de ubicaciones

    $('#locationFilter').on('keyup', function () {
        const filter = $(this)[0].value.toUpperCase();
        const list = $('.location-list');
        const listElems = list[0].getElementsByTagName('label');

        // Iterar por la lista y esconder los elementos que no matcheen
        for (let i = 0; i < listElems.length; i++) {
            let a = listElems[i].getElementsByTagName("span")[0];
            let txtValue = a.textContent || a.innerText;
            if (txtValue.toUpperCase().indexOf(filter) > -1) {
                listElems[i].style.display = "";
            } else {
                listElems[i].style.display = "none";
            }
        }
    });

    // Script para navegar entre pasos

    let currentStep, nextStep, previousStep;
    let left, opacity, scale;
    let animating = false;

    $('.continue-btn').click(function () {
        if (animating) return false;
        animating = true;

        currentStep = $(this).closest('.step-container-wrapper');
        nextStep = currentStep.next();

        nextStep.show();

        currentStep.animate({opacity: 0}, {
            step: function (now, mx) {

                scale = 1 - (1 - now) * 0.2;

                left = (now * 50) + "%";

                opacity = 1 - now;
                currentStep.css({
                    'transform': 'scale(' + scale + ')',
                    'position': 'absolute'

                });
                nextStep.css({'left': left, 'opacity': opacity});
            },
            duration: 800,
            complete: function () {
                nextStep.css({'position': 'relative'})
                currentStep.hide();
                animating = false;
            },

            easing: 'easeInOutBack'
        });
    });

    $(".back-btn").click(function () {
        if (animating) return false;
        animating = true;

        currentStep = $(this).closest('.step-container-wrapper');
        previousStep = currentStep.prev();

        previousStep.show();

        currentStep.animate({opacity: 0}, {
            step: function (now, mx) {
                scale = 0.8 + (1 - now) * 0.2;

                left = ((1 - now) * 50) + "%";

                opacity = 1 - now;
                currentStep.css({'left': left});
                previousStep.css({'transform': 'scale(' + scale + ')', 'opacity': opacity});
            },
            duration: 800,
            complete: function () {
                previousStep.css({'position': 'relative'})
                currentStep.hide();
                animating = false;
            },

            easing: 'easeInOutBack'
        });
    });

    // Script para deshabilitar submit on enter
    $('form input').on('keypress', function (e) {
        return e.which !== 13;
    });

    // Script para actualizar en tiempo real los campos del resumen de publicación
    // Titulo de publicación
    $('#jobTitle').on('keyup', function () {
        $('#jobTitleOverview').text($(this).val());
    });

    // Horarios
    $('#availableHoursInput').on('keyup', function () {
        $('#availableHoursOverview').text($(this).val());
    });

    // Tipo de servicio
    $('#jobTypeSelect').change(function () {
        $('#jobTypeOverview').text($(this).find(':selected').text());
    })

    // Zonas
    $('.zone-checkbox').change(function () {
        let zoneString = $(this).closest('.list-group-item')[0].getElementsByTagName('span')[0].textContent;

        if ($(this)[0].checked) {
            //Lo agrego al div
            $('#zoneContainer').append(
                "<span class='zoneTag'>" + zoneString + "</span>");
        } else {
            const spanList = $('#zoneContainer').children('span');
            let index;

            //Lo busco y lo elimino del div
            for (let i = 0; i < spanList.length; i++) {
                if (spanList[i].textContent === zoneString) {
                    index = i;
                    break;
                }
            }
            spanList[index].remove();
        }
    });

    // Imágenes
    $('#imageInput').change(function () {
        const fileArray = $(this)[0].files;

        if (fileArray.length > 0) {
            let filesAmount = fileArray.length;

            $('#imageCarousel').empty();

            for (let i = 0; i < filesAmount; i++) {
                let reader = new FileReader();

                reader.onload = function (event) {
                    $($.parseHTML('<img>')).attr('src', event.target.result).addClass('carousel-img')
                        .appendTo($('#imageCarousel'));
                }

                reader.readAsDataURL(fileArray[i]);
            }
        }
    });

    // Paquete
    $('#package-title-input').on('keyup', function () {
        $('#package-title-overview').text($(this).val())
    });

    $('#package-description-input').on('keyup', function () {
        $('#package-description-overview').text($(this).val())
    });

    $('#package-description-overview').on('keyup', function () {
        $('#package-title-overview').text($(this).val())
    });

    $('#hourly-radio').change(function () {
        if ($(this)[0].checked) {
            $('#tbd-overview-text').hide();
            $('#overview-price').show();
            $('#hourly-suffix').show();
        }
    });

    $('#onetime-radio').change(function () {
        if ($(this)[0].checked) {
            $('#tbd-overview-text').hide();
            $('#overview-price').show();
            $('#hourly-suffix').hide();
        }
    });

    $('#tbd-radio').change(function () {
        if ($(this)[0].checked) {
            $('#tbd-overview-text').show();
            $('#overview-price').hide();
            $('#hourly-suffix').hide();
        }
    });

    $('#package-price-input').on('keyup', function () {
        $('#overview-price').text('$' + $(this).val());
    });

    // Eliminar div de errores si esta vacío
    let errorContainer = $('.form-error-container');
    if (errorContainer.children().length === 0) {
        errorContainer.remove();
    }


</script>
</body>
</html>
