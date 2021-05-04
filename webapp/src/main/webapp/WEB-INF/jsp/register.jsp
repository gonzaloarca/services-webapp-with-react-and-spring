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

    <%-- Animation plugin for jQuery--%>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"
            integrity="sha512-0QbL0ph8Tc8g5bLhfVzSqxe9GERORsKhIn1IrpxDAgUsbBGz/V7iSav2zzW325XGd1OMLdL4UiqRJj702IeqnQ=="
            crossorigin="anonymous"></script>

    <!--  Bootstrap icons   -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css">

    <link href="${pageContext.request.contextPath}/resources/css/styles.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/resources/css/login.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/resources/css/register.css" rel="stylesheet"/>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/icon.svg">
    <link rel="apple-touch-icon" href="${pageContext.request.contextPath}/resources/images/apple-touch-icon.png">
</head>
<body style="background: url('${pageContext.request.contextPath}/resources/images/background.jpg')">
<c:set var="withoutColor" value="true" scope="request"/>
<c:set var="zoneValues" value="${zoneValues}" scope="request"/>
<c:set var="path" value="/register" scope="request"/>
<%@include file="components/customNavBar.jsp" %>
<div class="register-card">
    <div class="register-title-container">
        <h3 class="register-title">
            <img class="login-icon mb-2" src="${pageContext.request.contextPath}/resources/images/adduser.svg"
                 alt="<spring:message code="add.user"/>">
            <spring:message code="register.into.hirenet"/>
        </h3>
    </div>


    <form:form modelAttribute="registerForm" class="needs-validation" novalidate="true"
               action="${pageContext.request.contextPath}/register" method="post"
               enctype="multipart/form-data" id="register-form">
        <div class="card p-5">
            <div style="width: 600px; margin: 0 auto; position: relative">
                <fieldset id="register-step-1">
                    <div class="row input-container">
                        <div class="col-7">
                            <form:label path="name" class="form-text custom-label">
                                <spring:message code="register.name"/>
                            </form:label>
                            <spring:message code="register.name" var="namePlaceholder"/>
                            <div class="input-group has-validation">
                                <form:input type="text" class="form-control custom-input" name="name" required="true"
                                            placeholder="${namePlaceholder}" maxlength="100" path="name"/>
                                <form:errors path="name" cssClass="form-error" element="p"/>
                                <div class="invalid-feedback">
                                    <spring:message code="field.string.notEmpty"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-5">
                            <form:label path="phone" class="form-text custom-label">
                                <spring:message code="register.phone"/>
                            </form:label>
                            <div class="input-group has-validation">
                                <spring:message code="register.phone" var="phonePlaceholder"/>
                                <form:input type="text" class="form-control custom-input" name="phone" required="true"
                                            placeholder="${phonePlaceholder}" pattern="^\+?[0-9- ]{7,50}" path="phone"/>
                                <form:errors path="phone" cssClass="form-error" element="p"/>
                                <div class="invalid-feedback">
                                    <spring:message code="Pattern.registerForm.phone"/>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="input-container">
                        <form:label path="email" class="form-text custom-label">
                            <spring:message code="register.email"/>
                        </form:label>
                        <div class="input-group has-validation">
                            <spring:message code="register.email" var="emailPlaceholder"/>
                            <form:input type="email" class="form-control custom-input" name="email" required="true"
                                        placeholder="${emailPlaceholder}" maxlength="100" path="email"/>
                            <div class="invalid-feedback">
                                <spring:message code="Email"/>
                            </div>
                        </div>
                        <form:errors path="email" cssClass="form-error" element="p"/>
                    </div>

                    <div class="row">
                        <div class="col">
                            <form:label path="password" class="form-text custom-label">
                                <spring:message code="register.password"/>
                            </form:label>
                            <spring:message code="register.password" var="passwordPlaceholder"/>
                            <div class="input-group has-validation" id="show_hide_password">
                                <form:input type="password" class="form-control custom-input custom-password"
                                            name="password" required="true" minlength="8" maxlength="100"
                                            id="password" placeholder="${passwordPlaceholder}" path="password"/>
                                <span class="input-group-text password-eye" id="inputGroupPostpend">
                                    <a href=""><i class="fa fa-eye" aria-hidden="true"></i></a>
                                </span>
                                <div class="invalid-feedback">
                                    <spring:message code="register.password.invalid"/>
                                </div>
                            </div>
                            <form:errors path="password" cssClass="form-error" element="p"/>
                        </div>
                        <div class="col">
                            <form:label path="password" class="form-text custom-label">
                                <spring:message code="register.password.repeat"/>
                            </form:label>
                            <div class="input-group has-validation" id="show_hide_password_repeat">
                                <form:input type="password" class="form-control custom-input custom-password"
                                            name="repeatPassword" id="repeatPassword"
                                            placeholder="${passwordPlaceholder}" path="repeatPassword"/>
                                <span class="input-group-text password-eye" id="inputGroupPostpend">
                                    <a href=""><i class="fa fa-eye" aria-hidden="true"></i></a>
                                </span>
                                <div class="invalid-feedback">
                                    <spring:message code="register.password.notMatch"/>
                                </div>
                            </div>
                            <form:errors path="repeatPassword" cssClass="form-error" element="p"/>
                        </div>
                    </div>

                    <div class="register-button-container">
                        <button class="btn btn-primary hirenet-blue-btn continue-btn" type="button">
                            <spring:message code="register.continue"/>
                        </button>
                    </div>

                    <p class="bottom-link-label">
                        <spring:message code="register.alreadyhasaccount"/>
                    </p>
                    <div style="width: 100%; display: flex; justify-content: center">
                        <a class="bottom-link"
                           href="${pageContext.request.contextPath}/login">
                            <spring:message code="register.login"/>
                        </a>
                    </div>
                </fieldset>
                <fieldset id="register-step-2" disabled>
                    <h5 class="form-step-title">
                        <spring:message code="register.selectimage"/>
                    </h5>

                    <div class="img-preview-container">
                        <img id="img-preview"
                             src="${pageContext.request.contextPath}/resources/images/defaultavatar.svg"
                             alt="<spring:message code="user.avatar"/>">
                        <p class="font-weight-bold">
                            <spring:message code="register.imagepreview"/>
                        </p>
                    </div>
                    <div class="file-input-container input-group has-validation">
                        <form:input type="file" path="avatar" onchange="readURL(this);" id="imageInput"/>
                        <button class="btn btn-outline-secondary cancel-btn" id="clear_image" type="button">
                            <spring:message code="image.clear"/>
                        </button>
                        <div class="invalid-feedback" style="background-color: white; margin: 0">
                            <spring:message code="image.invalid"/>
                        </div>
                    </div>

                    <p class="img-upload-disclaimer">
                        <spring:message code="register.filedisclaimer"/>
                    </p>

                    <form:errors path="avatar" cssClass="form-error" element="p"/>
                    <div class="register-button-container">
                        <button class="btn btn-outline-secondary back-btn" type="button">
                            <spring:message code="register.goback"/>
                        </button>

                        <button class="btn btn-primary hirenet-blue-btn submit-btn" type="submit" id="submitBtn">
                            <spring:message code="register.submit"/>
                        </button>
                    </div>
                </fieldset>
            </div>

        </div>
    </form:form>

</div>

<script>
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

    //Script for form step wizard
    let registerStep1, registerStep2;
    let left, opacity, scale; //fieldset properties which we will animate
    let animating = false; //flag to prevent quick multi-click glitches

    $('.continue-btn').click(function () {
        if (animating) return false;

        //Validacion Client-Side:
        let form = document.querySelector('#register-form');

        if (!form.checkValidity()) {
            document.querySelector('#register-step-1').classList.add('was-validated');
            return false;
        }

        animating = true;

        registerStep1 = $('#register-step-1');
        registerStep2 = $('#register-step-2');

        //show the next fieldset
        registerStep2.show();
        registerStep2.attr("disabled", false);
        //hide the current fieldset with style
        registerStep1.animate({opacity: 0}, {
            step: function (now, mx) {
                //as the opacity of current_fs reduces to 0 - stored in "now"
                //1. scale current_fs down to 80%
                scale = 1 - (1 - now) * 0.2;
                //2. bring next_fs from the right(50%)
                left = (now * 50) + "%";
                //3. increase opacity of next_fs to 1 as it moves in
                opacity = 1 - now;
                registerStep1.css({
                    'transform': 'scale(' + scale + ')',
                    'position': 'absolute'

                });
                registerStep2.css({'left': left, 'opacity': opacity});
            },
            duration: 800,
            complete: function () {
                registerStep1.hide();
                animating = false;
            },
            //this comes from the custom easing plugin
            easing: 'easeInOutBack'
        });
    });

    $(".back-btn").click(function () {
        if (animating) return false;
        animating = true;

        registerStep1 = $('#register-step-1');
        registerStep2 = $('#register-step-2');

        //show the previous fieldset
        registerStep1.show();
        //hide the current fieldset with style
        registerStep2.animate({opacity: 0}, {
            step: function (now, mx) {
                //as the opacity of current_fs reduces to 0 - stored in "now"
                //1. scale previous_fs from 80% to 100%
                scale = 0.8 + (1 - now) * 0.2;
                //2. take current_fs to the right(50%) - from 0%
                left = ((1 - now) * 50) + "%";
                //3. increase opacity of previous_fs to 1 as it moves in
                opacity = 1 - now;
                registerStep2.css({'left': left});
                registerStep1.css({'transform': 'scale(' + scale + ')', 'opacity': opacity});
            },
            duration: 800,
            complete: function () {
                registerStep1.css({'position': 'relative'})
                registerStep2.attr("disabled", true);
                registerStep2.hide();
                animating = false;
            },
            //this comes from the custom easing plugin
            easing: 'easeInOutBack'
        });
    });

    // Disable submit on enter keypress while input is focused
    $('form input').on('keypress', function (e) {
        return e.which !== 13;
    });

    // Script para ver vista previa de imagen subida
    function readURL(input) {
        if (input.files && input.files[0]) {
            document.querySelector('#register-step-2').classList.add('was-validated');

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

    //Agrego la validacion al hacer submit y desactivo el boton si está validado
    (function () {
        let form = document.querySelector('#register-form');
        let is_valid = true;

        form.addEventListener('submit', function (event) {
            if (!form.checkValidity()) {
                is_valid = false;
                event.preventDefault()
                event.stopPropagation()
            }

            form.classList.add('was-validated');
            $("#submitBtn").attr("disabled", is_valid);
        }, false)

        form.addEventListener('input', function () {
            let passInput = document.querySelector('#password');
            let passRepeatInput = document.querySelector('#repeatPassword');
            let message = '';

            if (passInput.value !== passRepeatInput.value)
                message = 'Passwords do not match';     //Mensaje Default

            passRepeatInput.setCustomValidity(message);
        })

        //Limpiar imagen
        let clearBtn = document.querySelector('#clear_image');
        clearBtn.addEventListener('click', function () {
            let imageInput = document.querySelector('#imageInput');
            imageInput.value = '';
            readURL(imageInput);
        })
    })()
</script>
</body>
</html>
