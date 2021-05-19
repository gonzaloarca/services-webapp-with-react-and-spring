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
                <img class="login-icon" src="${pageContext.request.contextPath}/resources/images/log-in.svg"
                     alt="<spring:message code="login.symbol" />">
                <spring:message code="recover.title"/>
            </h3>
        </div>
        <div class="card p-5">

            <form:form action="${pageContext.request.contextPath}/recover" method="post" id="form" onsubmit="disableBtn()"
                  enctype="application/x-www-form-urlencoded" class="needs-validation" novalidate="true"
                  modelAttribute="recoverForm">

                <div class="input-container">
                    <label for="email" class="form-text custom-label">
                        <spring:message code="recover.instructions"/>
                    </label>
                    <spring:message code="login.email.placeholder" var="emailPlaceholder"/>
                    <div class="input-group has-validation">
                        <input type="email" class="form-control custom-input" id="email" name="email"
                               placeholder="${emailPlaceholder}" maxlength="100" required="required"/>
                        <div class="invalid-feedback">
                            <spring:message code="login.input.email"/>
                        </div>
                    </div>
                </div>
                <c:choose>
                    <c:when test="${confirmed}">
                        <div style="color: green">
                            <spring:message code="recover.confirmed"/>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div>
                            <spring:message code="recover.explained"/>
                        </div>
                    </c:otherwise>
                </c:choose>

                <div class="submit-button-container">
                    <button class="btn btn-primary hirenet-blue-btn" type="submit" id="submitBtn">
                        <spring:message code="recover.btn"/>
                    </button>
                </div>
            </form:form>
        </div>
    </div>
</div>
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
</script>
</body>
</html>
