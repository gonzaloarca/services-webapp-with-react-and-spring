<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
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
            <%--            TODO: Agregar alt--%>
            <img class="login-icon" src="${pageContext.request.contextPath}/resources/images/log-in.svg" alt="">
            <spring:message code="login.into.hirenet"/>
        </h3>
        <div class="card p-5">

            <form action="${pageContext.request.contextPath}/login" method="post"
                  enctype="application/x-www-form-urlencoded">

                <div class="input-container">
                    <label for="email" class="form-text custom-label">
                        <spring:message code="login.email"/>
                    </label>
                    <spring:message code="login.email.placeholder" var="emailPlaceholder"/>
                    <input type="email" class="form-control custom-input" id="email" name="email"
                           placeholder="${emailPlaceholder}" maxlength="100"/>
                </div>

                <label for="password" class="form-text custom-label">
                    <spring:message code="login.password"/>
                </label>
                <spring:message code="login.password.placeholder" var="passwordPlaceholder"/>
                <div class="input-group" id="show_hide_password">
                    <input type="password" class="form-control custom-input custom-password" name="password"
                           placeholder="${passwordPlaceholder}" maxlength="100" id="password"/>
                    <div class="input-group-addon password-eye">
                        <a href=""><i class="fa fa-eye" aria-hidden="true"></i></a>
                    </div>
                </div>
                <div class="login-error">
                    <c:if test="${param.error != null}">
                        <p class="form-error"><spring:message code="login.badcredentials"/></p>
                    </c:if>
                </div>
                <div class="submit-button-container">
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="" id="rememberMeCheck">
                        <label class="form-check-label" for="rememberMeCheck">
                            <spring:message code="login.rememberme"/>
                        </label>
                    </div>
                    <button class="btn btn-primary hirenet-blue-btn" type="submit">
                        <spring:message code="login.submit"/>
                    </button>
                </div>
            </form>
            <p class="bottom-link-label">
                <spring:message code="login.hasaccount.question"/>
            </p>
            <a class="bottom-link" href="${pageContext.request.contextPath}/register">
                <spring:message code="login.getaccount"/>
            </a>
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
        $("#show_hide_password a").on('click', function (event) {
            event.preventDefault();
            let input = $('#show_hide_password input');
            let icon = $('#show_hide_password i')
            if (input.attr("type") === "text") {
                input.attr('type', 'password');
                icon.addClass("fa-eye");
                icon.removeClass("fa-eye-slash");
            } else if (input.attr("type") === "password") {
                input.attr('type', 'text');
                icon.removeClass("fa-eye");
                icon.addClass("fa-eye-slash");
            }
        });
    });
</script>
</body>
</html>
