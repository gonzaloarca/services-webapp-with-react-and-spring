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
	<jsp:include page="components/customNavBar.jsp"/>

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
					<form:form modelAttribute="passChangeForm" enctype="multipart/form-data"
							   action="${pageContext.request.contextPath}/account/security" method="post">

						<!-- Ingreso de contraseña actual -->
						<div class="row data-row">
							<div class="col">
								<spring:message code="account.settings.security.curentPass" var="currentPassText"/>
								<form:label path="currentPass" class="form-text custom-label">
									${currentPassText}
								</form:label>
								<div class="input-group" id="show_hide_old_password">
									<form:input type="password" class="form-control custom-input custom-password"
												name="currentPass"
												placeholder="${currentPassText}" maxlength="100" path="currentPass"/>
									<div class="input-group-addon password-eye">
										<a href=""><i class="fa fa-eye" aria-hidden="true"></i></a>
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
								<div class="input-group" id="show_hide_password">
									<form:input type="password" class="form-control custom-input custom-password"
												name="newPass"
												placeholder="${newPassText}" maxlength="100" path="newPass"/>
									<div class="input-group-addon password-eye">
										<a href=""><i class="fa fa-eye" aria-hidden="true"></i></a>
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
								<div class="input-group" id="show_hide_password_repeat">
									<form:input type="password" class="form-control custom-input custom-password"
												name="repeatNewPass"
												placeholder="${repeatPassText}" maxlength="100" path="repeatNewPass"/>
									<div class="input-group-addon password-eye">
										<a href=""><i class="fa fa-eye" aria-hidden="true"></i></a>
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
								<button class="btn btn-primary" type="submit">
									<spring:message code="account.settings.security.save"/>
								</button>
							</div>
						</div>

					</form:form>

					<!-- TODO cambio de email probablemente en otra vista-->
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

    //TODO client side validation?
</script>
</body>
</html>
