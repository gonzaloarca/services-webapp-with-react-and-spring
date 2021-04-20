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
							Cliente
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
					Editar datos
				</h4>
				<div style="padding: 0 20px 0 20px">
				<form:form modelAttribute="accountChangeForm"
						   action="${pageContext.request.contextPath}/account/details" method="post"
						   enctype="multipart/form-data">
					<div class="row">
						<div class="col-7">
							<form:label path="name" class="form-text custom-label">
								<spring:message code="register.name"/>
							</form:label>
							<spring:message code="register.name" var="namePlaceholder"/>
							<form:input type="text" class="form-control custom-input" name="name"
										placeholder="${namePlaceholder}" maxlength="100" path="name"/>
							<form:errors path="name" cssClass="form-error" element="p"/>
						</div>
						<div class="col-5">
							<form:label path="phone" class="form-text custom-label">
								<spring:message code="register.phone"/>
							</form:label>
							<spring:message code="register.phone" var="phonePlaceholder"/>
							<form:input type="text" class="form-control custom-input" name="phone"
										placeholder="${phonePlaceholder}" maxlength="100" path="phone"/>
							<form:errors path="phone" cssClass="form-error" element="p"/>
						</div>
					</div>
					<!-- Cambio de imagen -->
					<div class="row" style="margin: 5px">
						<form:label path="avatar" class="form-text custom-label">
							<spring:message code="account.settings.info.changeimage"/>
						</form:label>
					</div>
					<div class="row">
						<div class="col-3 img-preview-container">
								<%--                        TODO: Alt correcto--%>
							<img id="img-preview"	class="profile-img"
								 src="${pageContext.request.contextPath}/resources/images/defaultavatar.svg" alt="">
							<div class="font-weight-bold">
								<spring:message code="register.imagepreview"/>
							</div>
						</div>
						<div class="col-8 image-input">
							<div class="file-input-container">
								<form:input type="file" path="avatar" onchange="readURL(this);"/>
							</div>
							<form:errors path="avatar" cssClass="form-error" element="p"/>
						</div>
					</div>
					<!-- Submit Button -->
					<div class="spaced-div">
						<h5 class="success-text">
							<c:if test="${success}">
								<spring:message code="account.settings.info.success"/>
							</c:if>
						</h5>
						<div style="display: flex">
							<button class="btn btn-primary" type="submit">
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
    // Script para ver vista previa de imagen subida
    function readURL(input) {
        if (input.files && input.files[0]) {
            let reader = new FileReader();

            reader.onload = function (e) {
                $('#img-preview')
                    .attr('src', e.target.result)
            };

            reader.readAsDataURL(input.files[0]);
        }
    }
</script>
</body>
</html>
