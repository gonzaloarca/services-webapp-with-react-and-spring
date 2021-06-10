<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>
        <spring:message code="recover.title" var="text"/>
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
    <link href="${pageContext.request.contextPath}/resources/css/login.css" rel="stylesheet"/>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/icon.svg">
    <link rel="apple-touch-icon" href="${pageContext.request.contextPath}/resources/images/apple-touch-icon.png">
</head>
<body style="background: url('${pageContext.request.contextPath}/resources/images/background.jpg')" >
<c:set var="withoutColor" value="true" scope="request"/>
<c:set var="zoneValues" value="${zoneValues}" scope="request"/>
<c:set var="path" value="/login" scope="request"/>
<%@include file="components/customNavBar.jsp" %>
<div class="background" >

    <div class="login-card">
        <div class="login-title-container">
            <h3 class="login-title">
                <img loading="lazy" class="login-icon" src="${pageContext.request.contextPath}/resources/images/log-in.svg"
                     alt="<spring:message code="login.symbol" />">
                <spring:message code="recover.title"/>
            </h3>
        </div>
        <div class="card p-5">


                <c:choose>
                    <c:when test="${expired}">
                        <h2 style="font-weight: bold">
                            <spring:message code="token.view.code.expired"/>
                        </h2>
                        <p class="text">
                            <spring:message code="token.view.code.invalid"/><br/>
                        </p>
                        <a type="button" class="btn btn-primary" style="display: block;margin: 10px auto; width: 100px"
                           href="${pageContext.request.contextPath}/"><spring:message code="navigation.index"/>
                        </a>
                    </c:when>
                    <c:when test="${success}">
                        <h2 style="font-weight: bold">
                            <spring:message code="recover.success.title"/>
                        </h2>
                        <p class="text">
                            <spring:message code="recover.success.info"/>
                        </p>
                        <a type="button" class="btn btn-primary" style="display: block;margin: 10px auto; width: 100px"
                           href="${pageContext.request.contextPath}/login"><spring:message code="navigation.login"/>
                        </a>
                    </c:when>
                    <c:otherwise>

                        <c:url var="postUrl" value="/change_password?user_id=${user_id}&token=${token}"/>
                        <form:form action="${postUrl}" method="post" id="form" onsubmit="disableBtn()"
                                   enctype="multipart/form-data" class="needs-validation" novalidate="true"
                                   modelAttribute="passwordChangeForm">

                        <div class="input-container">
                            <label for="newPass" class="form-text custom-label">
                                <spring:message code="recover.new.password"/>
                            </label>
                            <spring:message code="register.password" var="passPlaceholder"/>
                            <div class="input-group has-validation" id="show_hide_password">
                                <form:input type="password" class="form-control custom-input custom-password"
                                            name="newPass" required="true" id="newPass" minlength="8"
                                            placeholder="${passPlaceholder}" maxlength="100" path="newPass"/>
                                <span class="input-group-text password-eye" id="inputGroupPostpend">
                                    	<a href=""><i class="fa fa-eye" aria-hidden="true"></i></a>
                                </span>
                                <div class="invalid-feedback">
                                    <spring:message code="register.password.invalid"/>
                                </div>
                            </div>
                            <form:errors path="newPass" cssClass="form-error" element="p"/>
                        </div>

                        <div class="input-container">
                            <label for="repeatNewPass" class="form-text custom-label">
                                <spring:message code="recover.new.password.repeat"/>
                            </label>
                            <div class="input-group has-validation" id="show_hide_password_repeat">
                                <form:input type="password" class="form-control custom-input custom-password"
                                            name="repeatNewPass" id="newPassRepeat" required="true"
                                            placeholder="${passPlaceholder}" maxlength="100" path="repeatNewPass"/>
                                <span class="input-group-text password-eye" id="inputGroupPostpend">
                                    	<a href=""><i class="fa fa-eye" aria-hidden="true"></i></a>
                                </span>
                                <div class="invalid-feedback">
                                    <spring:message code="password.change.noMatch"/>
                                </div>
                            </div>
                            <form:errors path="repeatNewPass" cssClass="form-error" element="p"/>
                        </div>

                        <div class="submit-button-container">
                            <button class="btn btn-primary hirenet-blue-btn" type="submit" id="submitBtn">
                                <spring:message code="recover.btn"/>
                            </button>
                        </div>

                        </form:form>

                        <script>
                            // Example starter JavaScript for disabling form submissions if there are invalid fields
                            (function () {
                                'use strict'

                                // Fetch all the forms we want to apply custom Bootstrap validation styles to
                                var forms = document.querySelectorAll('.needs-validation')

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

                                form.addEventListener('input', function () {
                                    let passInput = document.querySelector('#newPass');
                                    let passRepeatInput = document.querySelector('#newPassRepeat');
                                    let message = '';

                                    if (passInput.value !== passRepeatInput.value)
                                        message = 'Passwords do not match';     //Mensaje Default

                                    passRepeatInput.setCustomValidity(message);
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

                            // Script for hiding/showing password on input
                            $(document).ready(function () {
                                $("#show_hide_password a").on('click', function (event) {
                                    event.preventDefault();
                                    let passwordInput = $('#show_hide_password input');
                                    let passwordI = $('#show_hide_password i');
                                    if (passwordInput.attr("type") === "text") {
                                        passwordInput.attr('type', 'password');
                                        passwordI.addClass("fa-eye").removeClass("fa-eye-slash");
                                    } else if (passwordInput.attr("type") === "password") {
                                        passwordInput.attr('type', 'text');
                                        passwordI.removeClass("fa-eye");
                                        passwordI.addClass("fa-eye-slash");
                                    }
                                });
                            });
                            $(document).ready(function () {
                                $("#show_hide_password_repeat a").on('click', function (event) {
                                    event.preventDefault();
                                    let repeatInput = $('#show_hide_password_repeat input');
                                    let repeatI = $('#show_hide_password_repeat i');
                                    if (repeatInput.attr("type") === "text") {
                                        repeatInput.attr('type', 'password');
                                        repeatI.addClass("fa-eye");
                                        repeatI.removeClass("fa-eye-slash");
                                    } else if (repeatInput.attr("type") === "password") {
                                        repeatInput.attr('type', 'text');
                                        repeatI.removeClass("fa-eye");
                                        repeatI.addClass("fa-eye-slash");
                                    }
                                });
                            });
                        </script>

                    </c:otherwise>
                </c:choose>

        </div>
    </div>
</div>
</body>
</html>
