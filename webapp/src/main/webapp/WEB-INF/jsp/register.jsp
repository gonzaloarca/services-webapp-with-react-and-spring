<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>
        <spring:message code="register.title" var="text"/>
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
    <link href="${pageContext.request.contextPath}/resources/css/register.css" rel="stylesheet"/>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/icon.svg">
    <link rel="apple-touch-icon" href="${pageContext.request.contextPath}/resources/images/apple-touch-icon.png">
</head>
<body>
<div class="background">
    <jsp:include page="customNavBar.jsp">
        <jsp:param name="withoutColor" value="true"/>
    </jsp:include>
    <div class="register-card">
        <h3 class="register-title">
            <i class="bi bi-person-plus"></i>
            <spring:message code="register.into.hirenet"/>
        </h3>
        <div class="card p-5">

            <form:form modelAttribute="registerForm"
                       action="${pageContext.request.contextPath}/register" method="post"
                       enctype="application/x-www-form-urlencoded">

                <div class="row">
                    <div class="col-8">
                        <form:label path="name" class="form-text custom-label">
                            <spring:message code="register.name"/>
                        </form:label>
                        <spring:message code="register.name.placeholder" var="namePlaceholder"/>
                        <form:input type="text" class="form-control custom-input" name="name"
                                    placeholder="${namePlaceholder}" maxlength="100" path="name"/>
                        <form:errors path="name" cssClass="form-error" element="p"/>
                    </div>
                    <div class="col-4">
                        <form:label path="phone" class="form-text custom-label">
                            <spring:message code="register.phone"/>
                        </form:label>
                        <spring:message code="register.phone.placeholder" var="phonePlaceholder"/>
                        <form:input type="text" class="form-control custom-input" name="phone"
                                    placeholder="${phonePlaceholder}" maxlength="100" path="phone"/>
                        <form:errors path="phone" cssClass="form-error" element="p"/>
                    </div>
                </div>

                <form:label path="email" class="form-text custom-label">
                    <spring:message code="register.email"/>
                </form:label>
                <spring:message code="register.email.placeholder" var="emailPlaceholder"/>
                <form:input type="email" class="form-control custom-input" name="email"
                            placeholder="${emailPlaceholder}" maxlength="100" path="email"/>
                <form:errors path="email" cssClass="form-error" element="p"/>

                <div class="row">
                    <div class="col">
                        <form:label path="password" class="form-text custom-label">
                            <spring:message code="register.password"/>
                        </form:label>
                        <spring:message code="register.password.placeholder" var="passwordPlaceholder"/>
                        <div class="input-group" id="show_hide_password">
                            <form:input type="password" class="form-control custom-input custom-password"
                                        name="password"
                                        placeholder="${passwordPlaceholder}" maxlength="100" path="password"/>
                            <div class="input-group-addon password-eye">
                                <a href=""><i class="fa fa-eye" aria-hidden="true"></i></a>
                            </div>
                        </div>
                        <form:errors path="password" cssClass="form-error" element="p"/>
                    </div>
                    <div class="col">
                        <form:label path="password" class="form-text custom-label">
                            <spring:message code="register.password.repeat"/>
                        </form:label>
                        <div class="input-group" id="show_hide_password_repeat">
                            <form:input type="password" class="form-control custom-input custom-password"
                                        name="repeatPassword"
                                        placeholder="${passwordPlaceholder}" maxlength="100" path="repeatPassword"/>
                            <div class="input-group-addon password-eye">
                                <a href=""><i class="fa fa-eye" aria-hidden="true"></i></a>
                            </div>
                        </div>
                        <form:errors path="repeatPassword" cssClass="form-error" element="p"/>
                    </div>
                </div>
                <div class="submit-button-container">
                    <button class="btn btn-primary hirenet-blue-btn" type="submit">
                        <spring:message code="register.submit"/>
                    </button>
                </div>
            </form:form>

            <a href="${pageContext.request.contextPath}/login">
                <h5 class="bottom-link">
                    <spring:message code="register.alreadyhasaccount"/>
                    <br/>
                    <spring:message code="register.login"/>
                </h5>
            </a>
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
        $("#show_hide_password a").on('click', function (event) {
            event.preventDefault();
            if ($('#show_hide_password input').attr("type") == "text") {
                $('#show_hide_password input').attr('type', 'password');
                $('#show_hide_password i').addClass("fa-eye");
                $('#show_hide_password i').removeClass("fa-eye-slash");
            } else if ($('#show_hide_password input').attr("type") == "password") {
                $('#show_hide_password input').attr('type', 'text');
                $('#show_hide_password i').removeClass("fa-eye");
                $('#show_hide_password i').addClass("fa-eye-slash");
            }
        });
    });
    $(document).ready(function () {
        $("#show_hide_password_repeat a").on('click', function (event) {
            event.preventDefault();
            if ($('#show_hide_password_repeat input').attr("type") == "text") {
                $('#show_hide_password_repeat input').attr('type', 'password');
                $('#show_hide_password_repeat i').addClass("fa-eye");
                $('#show_hide_password_repeat i').removeClass("fa-eye-slash");
            } else if ($('#show_hide_password_repeat input').attr("type") == "password") {
                $('#show_hide_password_repeat input').attr('type', 'text');
                $('#show_hide_password_repeat i').removeClass("fa-eye");
                $('#show_hide_password_repeat i').addClass("fa-eye-slash");
            }
        });
    });
</script>
</body>
</html>
