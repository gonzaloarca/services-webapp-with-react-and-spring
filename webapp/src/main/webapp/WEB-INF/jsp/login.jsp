<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>
        <spring:message code="login.title" var="text"/>
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
<body>
<div class="background">
    <jsp:include page="customNavBar.jsp">
        <jsp:param name="withoutColor" value="true"/>
    </jsp:include>
    <div class="login-card">
        <h3 class="login-title">
            <i class="bi bi-box-arrow-in-right"></i>
            <spring:message code="login.into.hirenet"/>
        </h3>
        <div class="card p-5">

            <form:form modelAttribute="loginForm"
                       action="${pageContext.request.contextPath}/login" method="post"
                       enctype="application/x-www-form-urlencoded">

                <form:label path="email" class="form-text custom-label">
                    <spring:message code="login.email"/>
                </form:label>
                <spring:message code="login.email.placeholder" var="emailPlaceholder"/>
                <form:input type="email" class="form-control custom-input" name="email"
                            placeholder="${emailPlaceholder}" maxlength="100" path="email"/>
                <form:errors path="email" cssClass="form-error" element="p"/>

                <form:label path="password" class="form-text custom-label">
                    <spring:message code="login.password"/>
                </form:label>
                <spring:message code="login.password.placeholder" var="passwordPlaceholder"/>
                <div class="input-group" id="show_hide_password">
                    <form:input type="password" class="form-control custom-input custom-password" name="password"
                                placeholder="${passwordPlaceholder}" maxlength="100" path="password"/>
                    <div class="input-group-addon password-eye">
                        <a href=""><i class="fa fa-eye" aria-hidden="true"></i></a>
                    </div>
                </div>
                <form:errors path="password" cssClass="form-error" element="p"/>

                <div class="submit-button-container">
                    <button class="btn btn-primary hirenet-blue-btn" type="submit">
                        <spring:message code="login.submit"/>
                    </button>
                </div>
            </form:form>

            <a href="${pageContext.request.contextPath}/register">
                <h5 class="bottom-link">
                    <spring:message code="login.hasaccount.question"/>
                    <br/>
                    <spring:message code="login.getaccount"/>
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
</script>
</body>
</html>
