<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
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
    <!-- Bootstrap Datepicker -->
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.0/moment.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.0/locale/es.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.39.0/js/tempusdominus-bootstrap-4.min.js"
            integrity="sha512-k6/Bkb8Fxf/c1Tkyl39yJwcOZ1P4cRrJu77p83zJjN2Z55prbFHxPs9vN7q3l3+tSMGPDdoH51AEU8Vgo1cgAA=="
            crossorigin="anonymous"></script>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.39.0/css/tempusdominus-bootstrap-4.min.css"
          integrity="sha512-3JRrEUwaCkFUBLK1N8HehwQgu8e23jTH4np5NHOmQOobuC4ROQxFwFgBLTnhcnQRMs84muMh0PnnwXlPq5MGjg=="
          crossorigin="anonymous"/>

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
                <c:url value="/image/post/${imageList[0]}" var="imageSrc"/>
            </c:otherwise>
        </c:choose>
        <spring:message code="${jobPost.jobType.stringCode}" var="jobTypeName"/>
        <img loading="lazy" class="header-img"
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
                <c:url value="/hire/package/${packId}" var="postUrl"/>
                <form:form class="contract-input needs-validation" modelAttribute="contractForm" novalidate="true"
                           action="${postUrl}" method="post" id="contract-form" onsubmit="disableBtn()"
                           enctype="multipart/form-data" autocomplete="off">

                    <!-- Description -->
                    <div class="form-row">
                        <div class="blue-circle">
                            <p class="circle-text">1</p>
                        </div>
                        <div class="col-10 label-and-input">
                            <form:label path="description" class="form-text">
                                <spring:message code="contract.create.form.description"/>
                            </form:label>
                            <div class="input-group has-validation">
                                <spring:message code="contract.create.form.description.placeholder"
                                                var="descPlaceholder"/>
                                <form:textarea class="form-control text-input" rows="6" path="description"
                                               maxlength="100"
                                               placeholder="${descPlaceholder}" required="true"/>
                                <div class="invalid-feedback">
                                    <spring:message code="contract.create.invalid.description"/>
                                </div>
                            </div>
                            <form:errors path="description" cssClass="invalid-feedback" element="p"/>
                        </div>
                    </div>

                    <!-- Scheduled date for service delivery -->

                    <div class="form-row">
                        <div class="orange-circle">
                            <p class="circle-text">2</p>
                        </div>
                        <div class="col-10 label-and-input">
                            <form:label path="scheduledDate" class="form-text">
                                <spring:message code="contract.create.form.date"/>
                            </form:label>

                            <spring:message code="contract.create.form.date.placeholder" var="datePlaceholder"/>
                            <div class="input-group date" id="datetimepicker1" data-target-input="nearest">
                                <form:input path="scheduledDate" id="time-input" type="text"
                                            class="form-control datetimepicker-input"
                                            data-target="#datetimepicker1" placeholder="${datePlaceholder}" data-toggle="datetimepicker"/>
                                <div class="input-group-append" data-target="#datetimepicker1"
                                     data-toggle="datetimepicker">
                                    <div class="input-group-text" style="background-color: #485696; color: white">
                                        <i class="far fa-calendar-alt"></i>
                                    </div>
                                </div>
                            </div>
                            <c:set var="localeCode" value="${pageContext.response.locale}"/>
                            <script type="text/javascript">
                                $(function () {
                                    $('#datetimepicker1').datetimepicker({
                                        locale: '${localeCode}',
                                        minDate: moment(),
                                    });
                                });
                            </script>
                            <p style="font-size: 0.9rem; color: #6e6e6e">
                                <spring:message code="contract.create.form.date.disclaimer"/>
                            </p>
                            <form:errors path="scheduledDate" cssClass="invalid-feedback" element="p"/>
                        </div>
                    </div>

                    <!-- Image -->
                    <div class="form-row">
                        <div class="yellow-circle">
                            <p class="circle-text">3</p>
                        </div>
                        <div class="col-10 label-and-input">
                            <form:label path="image" class="form-text">
                                <spring:message code="contract.create.form.image"/>
                            </form:label>
                            <div class="input-group has-validation file-input-container" id="image-container">
                                <form:input type="file" path="image" id="imageInput" onchange="validateImage(this);"/>
                                <button class="btn btn-outline-secondary cancel-btn" id="clear_image" type="button">
                                    <spring:message code="image.clear"/>
                                </button>
                                <div class="invalid-feedback" style="background-color: white; margin: 0">
                                    <spring:message code="image.invalid"/>
                                </div>
                            </div>
                            <p style="font-size: 0.9rem; color: #6e6e6e">
                            <spring:message code="register.filedisclaimer"/>
                            </p>
                            <form:errors path="image" cssClass="invalid-feedback" element="p"/>
                        </div>
                    </div>

                    <!-- Submit Button -->
                    <div class="submit-button">
                        <button class="btn btn-primary" type="submit" id="submitBtn">
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
                <img loading="lazy" class="info-img"
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
                                <spring:message code="${zone.stringCode}"/><c:if
                                    test="${status.index != jobPost.zones.size()-1}">,&nbsp;</c:if>
                            </c:forEach>
                        </div>
                    </div>
                    <hr class="divider-bar-info"/>
                    <!-- Professional -->
                    <div class="row info-row">
                        <div class="info-left-col">
                            <img loading="lazy" class="avatar-pic"
                                 src="<c:url value="/image/user/${jobPost.user.id}"/>"
                                 alt="avatar">
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
                                <spring:message htmlEscape="true" code="${jobPack.rateType.stringCode}"
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
<script>
    // Example starter JavaScript for disabling form submissions if there are invalid fields
    (function () {
        'use strict'

        // Fetch all the forms we want to apply custom Bootstrap validation styles to
        var forms = document.querySelectorAll('.needs-validation');

        // Loop over them and prevent submission
        Array.prototype.slice.call(forms)
            .forEach(function (form) {
                form.addEventListener('submit', function (event) {
                    if (!form.checkValidity()) {
                        event.preventDefault()
                        event.stopPropagation()
                    }

                    form.classList.add('was-validated')
                }, false)
            })

        //Limpiar imagen
        let clearBtn = document.querySelector('#clear_image');
        let imageInput = document.querySelector('#imageInput');
        clearBtn.addEventListener('click', function () {
            imageInput.value = '';
            validateImage(imageInput);
        })
    })()

    //Desabilitiar boton de submit cuando el form es valido (agregarlo a Form onsubmit)
    function disableBtn() {
        var forms = document.querySelectorAll('.needs-validation');
        var is_valid = true;
        Array.prototype.slice.call(forms)
            .forEach(function (form) {
                if (!form.checkValidity()) {
                    is_valid = false;
                }
            })
        $("#submitBtn").attr("disabled", is_valid);
    }

    function validateImage(input) {
        document.querySelector("#image-container").classList.add('was-validated');
        if (input.files && input.files[0]) {
            //Validacion de imagen
            let fileSize = input.files[0].size;
            if (fileSize > 2 * 1024 * 1024) {
                input.setCustomValidity('Max File Size Exceeded');
                return;
            }
            let fileType = input.files[0].type;
            const validTypes = ["image/jpg", "image/jpeg", "image/png"];
            if (!validTypes.includes(fileType)) {
                input.setCustomValidity('File Type not Supported');
                return;
            }
            input.setCustomValidity('');
        }
        input.setCustomValidity('');
    }
</script>
</body>
</html>
