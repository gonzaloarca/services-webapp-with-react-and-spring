<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>
        <spring:message code="jobPost.edit.title" var="text"/>
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


<c:set var="path" value="/create-job-post" scope="request"/>
<c:set var="zoneValues" value="${zoneValues}" scope="request"/>
<%@include file="components/customNavBar.jsp" %>

<div class="content-container-transparent mt-3">
    <h3>
        <img style="height: 30px; padding-bottom: 5px; margin-right: 5px" src="<c:url value="/resources/images/edit-icon.svg"/>" alt="">
        <spring:message code="jobPost.edit.title"/>
    </h3>

        <c:url value="/job/${id}/edit" var="postPath"/>
    <form:form modelAttribute="editJobPostForm" action="${postPath}" method="post" cssClass="step-frame" novalidate="true"
               enctype="multipart/form-data" class="needs-validation" id="edit-post-form" onsubmit="disableBtn()">
        <div class="form-error-container">
            <form:errors path="jobType" cssClass="form-error-list" element="p"/>
            <form:errors path="title" cssClass="form-error-list" element="p"/>
            <form:errors path="availableHours" cssClass="form-error-list" element="p"/>
            <form:errors path="zones" cssClass="form-error-list" element="p"/>
        </div>

        <div class="step-container-wrapper" id="step-wrapper1">
            <fieldset class="fieldset-step" id="fieldset-step-1">
            <img style="height: 120px; position: absolute; top: -65px; right: -40px"
                 src="<c:url value="/resources/images/circles1-v1.svg"/>" alt="">
            <img style="height: 55px; position: absolute; top: 10px; right: 20px"
                 src="<c:url value="/resources/images/job-1.svg"/>" alt="">

            <div class="step-container">
                <h4>
                    <spring:message code="jobPost.create.postData"/>
                </h4>
                <p class="step-subtitle"><spring:message code="jobPost.create.stepSubtitle" arguments="1, 4"/></p>
                <br>

                <div class="input-container">
                    <form:label path="jobType" class="step-header-label"
                                for="jobTypeSelect">
                        <spring:message code="jobPost.create.service.select"/>
                    </form:label>

                    <spring:message code="jobPost.create.service.type" var="serviceType"/>

                    <span class="input-group has-validation">
                            <form:select path="jobType" class="form-control w-100" id="jobTypeSelect" required="true">
                                <form:option value="" label="${serviceType}"/>

                                <c:forEach items="${jobTypes}" var="type">
                                    <form:option value="${type.value}">
                                        <spring:message code="${type.stringCode}"/>
                                    </form:option>
                                </c:forEach>

                            </form:select>
                            <div class="invalid-feedback">
                                    <spring:message code="NotNull.editJobPostForm.jobType"/>
                            </div>
                        </span>
                    <form:errors path="jobType" class="form-error" element="p"/>
                    <div class="button-controls">
                        <button class="continue-btn btn btn-primary hirenet-blue-btn text-uppercase" type="button">
                            <spring:message code="jobPost.create.next"/>
                        </button>
                    </div>
                </div>
            </div>
            </fieldset>
        </div>

        <div class="step-container-wrapper" id="step-wrapper2">
            <fieldset class="fieldset-step" id="fieldset-step-2" disabled>
            <img style="height: 120px; position: absolute; top: -65px; right: -40px"
                 src="<c:url value="/resources/images/circles1-v1.svg"/>" alt="">
            <img style="height: 75px; position: absolute; top: 5px; right: 10px"
                 src="<c:url value="/resources/images/title1.svg"/>" alt="">

            <div class="step-container">
                <h4><spring:message code="jobPost.create.postData"/></h4>
                <p class="step-subtitle"><spring:message code="jobPost.create.stepSubtitle" arguments="2, 4"/></p>
                <br>

                <div class="input-container">
                    <form:label path="title" for="jobTitle"
                                class="step-header-label">
                        <spring:message code="jobPost.create.publication.title"/>
                    </form:label>
                    <spring:message code="jobPost.create.publication.placeholder" var="titlePlaceholder"/>
                    <span class="input-group has-validation">
                            <spring:message code="jobPost.create.publication.placeholder" var="titlePlaceholder"/>
                            <form:input path="title" id="jobTitle" type="text" class="form-control" required="true"
                                        placeholder="${titlePlaceholder}" maxlength="100"/>
                            <form:errors path="title" class="form-error" element="p"/>
                            <div class="invalid-feedback">
                                <spring:message code="field.string.notEmpty"/>
                            </div>
                        </span>
                    <form:errors path="title" class="form-error" element="p"/>
                    <div class="button-controls">
                        <button class="back-btn btn btn-outline-secondary hirenet-grey-outline-btn text-uppercase mr-2"
                                type="button">
                            <spring:message code="jobPost.create.goBack"/>
                        </button>
                        <button class="continue-btn btn btn-primary hirenet-blue-btn text-uppercase" type="button">
                            <spring:message code="jobPost.create.next"/>
                        </button>
                    </div>
                </div>
            </div>
            </fieldset>
        </div>


        <div class="step-container-wrapper" id="step-wrapper5">
            <fieldset class="fieldset-step" id="fieldset-step-5" disabled>
            <img style="height: 120px; position: absolute; top: -65px; right: -40px"
                 src="<c:url value="/resources/images/circles1-v1.svg"/>" alt="">
            <img style="height: 80px; position: absolute; top: 5px; right: 20px"
                 src="<c:url value="/resources/images/clock1.svg"/>" alt="">

            <div class="step-container">
                <h4><spring:message code="jobPost.create.postData"/></h4>
                <p class="step-subtitle"><spring:message code="jobPost.create.stepSubtitle" arguments="3, 4"/></p>
                <br>

                <div class="input-container">
                    <form:label path="availableHours" for="availableHoursInput"
                                class="step-header-label">
                        <spring:message code="jobPost.create.availableHours"/>
                    </form:label>
                    <spring:message code="jobPost.create.availableHours.placeholder" var="hoursPlaceholder"/>
                    <span class="input-group has-validation">
                            <form:textarea path="availableHours" id="availableHoursInput" class="form-control"
                                           placeholder="${hoursPlaceholder}" required="true"
                                           rows="5" maxlength="100"/>
                            <div class="invalid-feedback">
                                <spring:message code="field.string.notEmpty"/>
                            </div>
                        </span>
                    <form:errors path="availableHours" class="form-error" element="p"/>
                    <div class="button-controls">
                        <button class="back-btn btn btn-outline-secondary hirenet-grey-outline-btn text-uppercase mr-2"
                                type="button">
                            <spring:message code="jobPost.create.goBack"/>
                        </button>
                        <button class="continue-btn btn btn-primary hirenet-blue-btn text-uppercase" type="button">
                            <spring:message code="jobPost.create.next"/>
                        </button>
                    </div>
                </div>
            </div>
            </fieldset>
        </div>

        <div class="step-container-wrapper" id="step-wrapper6">
            <fieldset class="fieldset-step" id="fieldset-step-6" disabled>
            <img style="height: 120px; position: absolute; top: -65px; right: -40px"
                 src="<c:url value="/resources/images/circles1-v1.svg"/>" alt="">
            <img style="height: 80px; position: absolute; top: 10px; right: 20px"
                 src="<c:url value="/resources/images/location2.svg"/>" alt="">

            <div class="step-container">
                <h4><spring:message code="jobPost.create.postData"/></h4>
                <p class="step-subtitle"><spring:message code="jobPost.create.stepSubtitle" arguments="4, 4"/></p>
                <br>

                <div class="input-container">
                    <form:label path="zones"
                                class="step-header-label">
                        <spring:message code="jobPost.create.zones"/>
                    </form:label>

                    <div class="form-group has-search">
                        <span class="fa fa-search form-control-feedback"></span>
                        <input id="locationFilter" type="text" class="form-control zone-search"
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

                    <div class="invalid-feedback" id="zones-feedback">
                        <spring:message code="jobPost.create.zones.invalid"/>
                    </div>
                    <form:errors path="zones" class="form-error" element="p"/>
                    <div class="button-controls">
                        <button class="back-btn btn btn-outline-secondary hirenet-grey-outline-btn text-uppercase mr-2"
                                type="button">
                            <spring:message code="jobPost.create.goBack"/>
                        </button>
                        <button class="btn btn-primary hirenet-blue-btn text-uppercase continue-btn"
                                type="button" onclick="checkZones()">
                            <spring:message code="jobPost.create.next"/>
                        </button>
                    </div>
                </div>
            </div>
            </fieldset>
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
                                type="submit" id="submitBtn">
                            Confirmar
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

    // Eliminar div de errores si esta vacío
    let errorContainer = $('.form-error-container');
    if (errorContainer.children().length === 0) {
        errorContainer.remove();
    }


    // Script para deshabilitar el input de precio cuando esta TBD el radio
    const tbdRadio = $("#tbd-radio");

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

        //Validacion Client-Side:
        let form = document.querySelector('#edit-post-form');

        if (!form.checkValidity()) {
            this.closest('.fieldset-step').classList.add('was-validated');
            return false;
        }

        animating = true;

        currentStep = $(this).closest('.step-container-wrapper');
        nextStep = currentStep.next();

        nextStep.show();
        nextStep[0].children[0].removeAttribute("disabled");

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
                currentStep[0].children[0].setAttribute("disabled", "");
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


    const checkboxList = $('.zone-checkbox');

    for (let i = 0; i < checkboxList.length; i++) {
        // console.log(checkboxList[i])

        let zoneString = checkboxList[i].closest('.list-group-item').getElementsByTagName('span')[0].textContent;
        // console.log("Afuera")
        // console.log(checkboxList[i])

        if (checkboxList[i].checked) {
            // console.log("Adentro")

//Lo agrego al div
            $('#zoneContainer').append(
                "<span class='zoneTag'>" + zoneString + "</span>");
        }
    };

    $('#jobTitleOverview').text($('#jobTitle').val());

    $('#availableHoursOverview').text($('#availableHoursInput').val());

    $('#jobTypeOverview').text($("#jobTypeSelect").find(':selected').text());

    //Desabilitiar boton de submit cuando el form es valido (agregarlo a Form onsubmit)
    function disableBtn() {
        let form = document.querySelector('#edit-post-form');
        let is_valid = true;

        if (!form.checkValidity()) {
            is_valid = false;
        }

        form.classList.add('was-validated');
        $("#submitBtn").attr("disabled", is_valid);
    }

    function checkZones() {
        let zones = document.querySelectorAll(".zone-checkbox");
        let is_valid = false;
        let message = '';
        zones.forEach(function (zone) {
            if (zone.checked === true)
                is_valid = true;
        });
        if (!is_valid) {
            message = 'Please choose a zone';       //Mensaje default
            document.querySelector('#zones-feedback').style.setProperty("display", "flex");
        } else {
            document.querySelector('#zones-feedback').style.setProperty("display", "none");
        }
        zones.forEach(function (zone) {
            zone.setCustomValidity(message);
        });
    }

</script>
</body>
</html>
