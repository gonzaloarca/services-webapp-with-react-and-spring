<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<c:set var="zoneValues" value="${zoneValues}" scope="request"/>
<%@include file="components/customNavBar.jsp" %>

<div class="content-container-transparent mt-3 mb-3 pb-2">
    <h3>
        <i class="mr-2 fas fa-cube"></i>
        <spring:message code="jobPost.packages.title"/>
    </h3>

    <div class="packages-frame">
        <div class="packages-container-wrapper">
            <div class="packages-container">
                <img loading="lazy" style="height: 120px; position: absolute; top: -65px; right: -40px"
                     src="<c:url value="/resources/images/circles1-v1.svg"/>" alt="">
                <img loading="lazy" style="height: 85px; position: absolute; top: 0; right: 10px"
                     src="<c:url value="/resources/images/package1.svg"/>" alt="">

                <h4 class="font-weight-bold">
                    <c:if test="${existing == true}">
                        <spring:message code="jobPost.packages.edit.title"/>
                    </c:if>
                    <c:if test="${existing != true}">
                        <spring:message code="jobPost.packages.add.title"/>
                    </c:if>
                </h4>

                <c:if test="${existing == true}">
                    <c:url value="/job/${postId}/packages/${packageId}/edit" var="postPath"/>
                </c:if>
                <c:if test="${existing != true}">
                    <c:url value="/job/${postId}/packages/add" var="postPath"/>
                </c:if>

                <form:form modelAttribute="editPackageForm" method="post" action="${postPath}" id="pack-form"
                           cssClass="mt-4" class="needs-validation" novalidate="true">
                    <div class="package-input">
                        <form:label path="title" for="package-title-input">
                            <spring:message code="jobPost.create.package.title"/>
                        </form:label>

                        <spring:message code="jobPost.create.package.title" var="pTitlePlaceholder"/>

                        <span class="input-group has-validation">
                                <form:input path="title" id="package-title-input" type="text"
                                            class="form-control" required="true"
                                            placeholder="${pTitlePlaceholder}" maxlength="100"/>
                                <div class="invalid-feedback">
                                    <spring:message code="field.string.notEmpty"/>
                                </div>
                        </span>
                        <form:errors path="title" class="form-error" element="p"/>
                    </div>

                    <div class="package-input">
                        <form:label path="description"
                                    for="package-description-input">
                            <spring:message code="jobPost.create.package.description"/>
                        </form:label>

                        <spring:message code="jobPost.create.package.descriptionPlaceholder"
                                        var="descriptionPlaceholder"/>
                        <span class="input-group has-validation">
                                <form:textarea path="description" id="package-description-input"
                                               class="form-control" required="true"
                                               placeholder="${descriptionPlaceholder}" maxlength="100"
                                               rows="3"/>
                                <div class="invalid-feedback">
                                    <spring:message code="field.string.notEmpty"/>
                                </div>
                        </span>

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
                                                  value="0" onclick="checkRadio()"/>
                                <form:label path="rateType" for="hourly-radio"
                                            class="form-check-label radio-label">
                                    <spring:message code="jobPost.create.package.hourly"/>
                                </form:label>
                            </div>
                            <div class="form-check form-check-inline">
                                <form:radiobutton path="rateType" id="onetime-radio"
                                                  class="form-check-input" name="inlineRadioOptions"
                                                  value="1" onclick="checkRadio()"/>
                                <form:label path="rateType" for="onetime-radio"
                                            class="form-check-label radio-label">
                                    <spring:message code="jobPost.create.package.oneTime"/>
                                </form:label>
                            </div>
                            <div class="form-check form-check-inline">
                                <form:radiobutton path="rateType" id="tbd-radio"
                                                  class="form-check-input"
                                                  name="inlineRadioOptions"
                                                  value="2" onclick="checkRadio()"/>
                                <form:label path="rateType" for="tbd-radio"
                                            class="form-check-label radio-label">
                                    <spring:message code="jobPost.create.package.tbd"/>
                                </form:label>
                            </div>
                        </div>
                        <div class="invalid-feedback" id="radioFeedback">
                            <spring:message code="NotNull.createJobPostForm.jobPackage.rateType"/>
                        </div>
                        <form:errors path="rateType" class="form-error" element="p"/>
                    </div>

                    <div class="package-input">
                        <form:label path="price" for="package-price-input">
                            <spring:message code="jobPost.create.package.price"/>
                        </form:label>
                        <div class="input-group has-validation">
                            <div class="input-group-prepend">
                                <span class="input-group-text">
                                    <spring:message code="jobPost.create.package.argentinePeso"/>
                                </span>
                            </div>
                            <spring:message code="jobPost.create.package.price" var="pricePlaceholder"/>
                            <form:input path="price" id="package-price-input" type="number" step="any"
                                        class="form-control" min="0" maxlength="15"
                                        placeholder="${pricePlaceholder}" onkeyup="checkRadio()"/>
                            <div class="invalid-feedback">
                                <spring:message code="jobPost.create.price.invalid"/>
                            </div>
                        </div>
                        <form:errors path="price" class="form-error" element="p"/>
                    </div>

                    <button class="btn btn-block btn-primary text-uppercase" style="margin: 40px 0 !important;"
                            type="submit" id="submitBtn">
                        <spring:message code="jobPost.package.edit.confirm"/>
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


    let form = document.querySelector('#pack-form');
    let validated = false;

    form.addEventListener('submit', function (event) {
        let is_valid = form.checkValidity();

        validated = true;
        checkRadio();
        if (!is_valid) {
            event.preventDefault();
            event.stopPropagation();
        }

        form.classList.add('was-validated');
        $("#submitBtn").attr("disabled", is_valid);
    })

    function checkRadio() {
        let message = "";
        let feedback = document.querySelector('#radioFeedback');
        let priceInput = document.querySelector('#package-price-input');
        if(!hourlyRadio[0].checked && !onetimeRadio[0].checked && !tbdRadio[0].checked){
            message = "No radio selected";    //Mensaje default
            if (validated)
                feedback.style.setProperty("display", "flex");
        } else {
            feedback.style.setProperty("display", "none");
        }
        hourlyRadio[0].setCustomValidity(message);
        onetimeRadio[0].setCustomValidity(message);
        tbdRadio[0].setCustomValidity(message);
        if(!tbdRadio[0].checked && priceInput.value === "")
            priceInput.setCustomValidity("Price is obligatory");    //Mensaje Default
        else
            priceInput.setCustomValidity("");
    }
</script>
</body>
</html>
