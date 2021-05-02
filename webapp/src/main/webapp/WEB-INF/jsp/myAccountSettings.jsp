<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>
        <spring:message code="account.settings.title" var="text"/>
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
    <link href="${pageContext.request.contextPath}/resources/css/myaccount.css" rel="stylesheet"/>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/icon.svg">
    <link rel="apple-touch-icon" href="${pageContext.request.contextPath}/resources/images/apple-touch-icon.png">
</head>
<body class="body">
<c:set var="zoneValues" value="${zoneValues}" scope="request"/>
<%@include file="components/customNavBar.jsp" %>

<div class="content-container-transparent">
    <jsp:include page="components/siteHeader.jsp">
        <jsp:param name="code" value="account.settings.title"/>
    </jsp:include>

    <div class="main-body">

        <div class="account-sections content-container">
            <jsp:include page="components/accountOptions.jsp">
                <jsp:param name="selected" value="0"/>
            </jsp:include>
        </div>

        <div class="account-settings content-container">
            <h4 class="options-title">
                <spring:message code="account.settings.options.data"/>
            </h4>
            <hr class="divider-bar"/>
            <div class="spaced-div" style="margin: 30px 50px 30px 60px">
                <!-- informacion actual -->
                <div class="spaced-div">
                    <c:choose>
                        <c:when test="${user.image.string != null}">
                            <c:set var="profilePic" value="data:${user.image.type};base64,${user.image.string}"/>
                        </c:when>
                        <c:otherwise>
                            <c:url var="profilePic" value="/resources/images/defaultavatar.svg"/>
                        </c:otherwise>
                    </c:choose>
                    <img class="profile-img"
                         src='${profilePic}'
                         alt="<spring:message code="profile.image"/>">
                    <div class="centered-div">
                        <h4 style="font-weight: bold">
                            <c:out value="${user.username}"/>
                        </h4>
                        <c:choose>
                            <c:when test="${isPro}">
                                <spring:message code="account.settings.info.professional"/>
                            </c:when>
                            <c:otherwise>
                                <spring:message code="account.settings.info.client"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <!-- TODO logica para convertirme en profesional -->
                <%--					<a href="#" class="centered-div">--%>
                <%--						<button class="btn btn-pro">--%>
                <%--							Convertirme en Profesional--%>
                <%--						</button>--%>
                <%--					</a>--%>
            </div>
            <hr class="divider-bar"/>
            <!-- Editar Informacion -->
            <h4 class="options-title">
                <spring:message code="account.settings.info.subtitle"/>
            </h4>
            <div style="padding: 0 20px 0 20px">
                <form:form modelAttribute="accountChangeForm" class="needs-validation" novalidate="true"
                           action="${pageContext.request.contextPath}/account/details" method="post"
                           enctype="multipart/form-data" onsubmit="disableBtn()">
                    <div class="row p-4">
                        <div class="col-7 needs-validation">
                            <form:label path="name" class="form-text custom-label">
                                <spring:message code="register.name"/>
                            </form:label>
                            <form:input type="text" class="form-control custom-input" name="name" required="true"
                                        placeholder="${namePlaceholder}" maxlength="100" path="name"/>
                            <div class="invalid-feedback">
                                <spring:message code="register.field.notEmpty"/>
                            </div>
                            <form:errors path="name" cssClass="form-error" element="p"/>
                        </div>
                        <div class="col-5 needs-validation">
                            <form:label path="phone" class="form-text custom-label">
                                <spring:message code="register.phone"/>
                            </form:label>
                            <form:input type="text" class="form-control custom-input" name="phone" required="true"
                                        placeholder="${phonePlaceholder}" pattern="^\+?[0-9- ]{7,50}" path="phone"/>
                            <div class="invalid-feedback">
                                <spring:message code="Pattern.registerForm.phone"/>
                            </div>
                            <form:errors path="phone" cssClass="form-error" element="p"/>
                        </div>
                    </div>

                    <hr>
                    <!-- Cambio de imagen -->
                    <div class="px-3">
                        <div class="row" style="margin: 5px">
                            <form:label path="avatar" class="form-text custom-label">
                                <spring:message code="account.settings.info.changeimage"/>
                            </form:label>
                        </div>
                        <div class="row my-4" style="justify-content: center; align-items: center">
                            <div class="col-3 img-preview-container">
                                <img id="img-preview" class="profile-img"
                                     alt="<spring:message code="account.settings.info.previewalt"/>"
                                     src="${pageContext.request.contextPath}/resources/images/defaultavatar.svg">
                                <div style="color: #7e7e7e" class="text-center mt-2">
                                    <spring:message code="register.imagepreview"/>
                                </div>
                            </div>
                            <div class="col-8 image-input">
                                <div class="file-input-container input-group has-validation">
                                    <form:input type="file" path="avatar" onchange="readURL(this);" id="imageInput"/>
                                    <button class="btn btn-outline-secondary cancel-btn" id="clear_image" type="button">
                                        <spring:message code="image.clear"/>
                                    </button>
                                    <div class="invalid-feedback" style="background-color: white">
                                        <spring:message code="image.invalid"/>
                                    </div>
                                </div>
                                <form:errors path="avatar" cssClass="form-error" element="p"/>
                            </div>
                        </div>
                    </div>
                    <!-- Submit Button -->
                    <div class="spaced-div">
                        <h5 class="success-text">
                            <c:if test="${success}">
                                <spring:message code="account.settings.info.success"/>
                            </c:if>
                        </h5>

                        <button class="btn btn-primary hirenet-blue-btn" type="submit" id="submitBtn"">
                            <spring:message code="account.settings.security.save"/>
                        </button>

                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
<jsp:include page="components/footer.jsp"/>
<script>
    // Script para ver vista previa de imagen subida
    function readURL(input) {
        if (input.files && input.files[0]) {

            //Validacion de imagen
            let fileSize = input.files[0].size;
            if(fileSize > 2 * 1024 * 1024) {
                input.setCustomValidity('Max File Size Exceeded');
                setDefaultAvatar();
                return;
            }
            let fileType = input.files[0].type;
            const validTypes = ["image/jpg", "image/jpeg", "image/png"];
            if (!validTypes.includes(fileType)) {
                input.setCustomValidity('File Type not Supported');
                setDefaultAvatar();
                return;
            }
            input.setCustomValidity('');

            let reader = new FileReader();

            reader.onload = function (e) {
                $('#img-preview')
                    .attr('src', e.target.result)
            };

            reader.readAsDataURL(input.files[0]);
        } else {
            setDefaultAvatar();
        }
    }

    function setDefaultAvatar() {
        $('#img-preview')
            .attr('src', "${pageContext.request.contextPath}/resources/images/defaultavatar.svg");
    }

    // Example starter JavaScript for disabling form submissions if there are invalid fields
    (function () {
        'use strict'

        let form = document.querySelector('.needs-validation')

        form.addEventListener('submit', function (event) {
            if (!form.checkValidity()) {
                event.preventDefault()
                event.stopPropagation()
            }

            form.classList.add('was-validated')
        })

        let clearBtn = document.querySelector('#clear_image');
        clearBtn.addEventListener('click', function () {
            let imageInput = document.querySelector('#imageInput');
            imageInput.value = '';
            readURL(imageInput);
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
