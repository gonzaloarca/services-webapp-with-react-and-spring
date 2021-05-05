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
			<!-- Navegacion vertical -->
			<div class="account-sections content-container">
				<jsp:include page="components/accountOptions.jsp">
					<jsp:param name="selected" value="1"/>
				</jsp:include>
			</div>
			<!-- Settings -->
			<div class="account-settings content-container">
				<h4 class="options-title">
					<spring:message code="account.settings.options.security"/>
				</h4>
				<hr class="divider-bar"/>
				<h5 class="options-subtitle">
					<spring:message code="account.settings.security.passTitle"/>
				</h5>
				<div class="form-section">
					<form:form modelAttribute="passChangeForm" enctype="multipart/form-data" onsubmit="disableBtn()"
							   action="${pageContext.request.contextPath}/account/security" method="post"
								class="needs-validation" novalidate="true">

						<!-- Ingreso de contraseña actual -->
						<div class="row data-row">
							<div class="col">
								<spring:message code="account.settings.security.curentPass" var="currentPassText"/>
								<form:label path="currentPass" class="form-text custom-label">
									${currentPassText}
								</form:label>
								<div class="input-group has-validation" id="show_hide_old_password">
									<form:input type="password" class="form-control custom-input custom-password"
												name="currentPass" required="true"
												placeholder="${currentPassText}" maxlength="100" path="currentPass"/>
									<span class="input-group-text password-eye" id="inputGroupPostpend">
                                    	<a href=""><i class="fa fa-eye" aria-hidden="true"></i></a>
                                	</span>
									<div class="invalid-feedback">
										<spring:message code="account.settings.security.empty"/>
									</div>
								</div>
								<form:errors path="currentPass" cssClass="form-error" element="p"/>
							</div>
							<div class="col">
							</div>
						</div>

						<div class="row data-row">
							<!-- Ingreso de nueva contreseña -->
							<div class="col">
								<spring:message code="account.settings.security.newPass" var="newPassText"/>
								<form:label path="newPass" class="form-text custom-label">
									${newPassText}
								</form:label>
								<div class="input-group has-validation" id="show_hide_password">
									<form:input type="password" class="form-control custom-input custom-password"
												name="newPass" required="true" id="newPass"
												placeholder="${newPassText}" maxlength="100" path="newPass"/>
									<span class="input-group-text password-eye" id="inputGroupPostpend">
                                    	<a href=""><i class="fa fa-eye" aria-hidden="true"></i></a>
                                	</span>
									<div class="invalid-feedback">
										<spring:message code="account.settings.security.empty"/>
									</div>
								</div>
								<form:errors path="newPass" cssClass="form-error" element="p"/>
							</div>
							<!-- Repetir nueva contraseña -->
							<div class="col">
								<spring:message code="account.settings.security.repeatNewPass" var="repeatPassText"/>
								<form:label path="repeatNewPass" class="form-text custom-label">
									${repeatPassText}
								</form:label>
								<div class="input-group has-validation" id="show_hide_password_repeat">
									<form:input type="password" class="form-control custom-input custom-password"
												name="repeatNewPass" id="newPassRepeat" required="true"
												placeholder="${repeatPassText}" maxlength="100" path="repeatNewPass"/>
									<span class="input-group-text password-eye" id="inputGroupPostpend">
                                    	<a href=""><i class="fa fa-eye" aria-hidden="true"></i></a>
                                	</span>
									<div class="invalid-feedback">
										<spring:message code="password.change.noMatch"/>
									</div>
								</div>
								<form:errors path="repeatNewPass" cssClass="form-error" element="p"/>
							</div>
						</div>

						<div class="spaced-div">
							<h5 class="success-text">
<%--								<c:if test="${success}">--%>
<%--									<spring:message code="account.settings.security.success"/>--%>
<%--								</c:if>--%>
							</h5>
							<!-- Submit Button -->
							<div>
								<button class="btn btn-primary hirenet-blue-btn" type="submit" id="submitBtn">
									<spring:message code="account.settings.security.save"/>
								</button>
							</div>
						</div>

					</form:form>
				</div>
			</div>

		</div>

	</div>

	<jsp:include page="components/footer.jsp"/>

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
    $(document).ready(function () {
        $("#show_hide_old_password a").on('click', function (event) {
            event.preventDefault();
            let passwordInput = $('#show_hide_old_password input');
            let passwordI = $('#show_hide_old_password i');
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

    // Example starter JavaScript for disabling form submissions if there are invalid fields
    (function () {
        'use strict'

        // Fetch all the forms we want to apply custom Bootstrap validation styles to
        let form = document.querySelector('.needs-validation')

        // Loop over them and prevent submission
		form.addEventListener('submit', function (event) {
			if (!form.checkValidity()) {
				event.preventDefault()
				event.stopPropagation()
			}

			form.classList.add('was-validated')
		}, false)

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
</script>
</body>
</html>
