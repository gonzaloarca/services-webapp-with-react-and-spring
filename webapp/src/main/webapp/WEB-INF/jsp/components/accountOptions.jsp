<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<div>
	<h4 class="options-title">
		<spring:message code="account.settings.account"/>
	</h4>
	<hr class="divider-bar"/>
	<nav class="nav flex-column">
		<a class="nav-link nav-option ${param.selected == '0' ? 'active' : ''}"
		   href="${pageContext.request.contextPath}/account/details">
			<c:choose>
				<c:when test="${param.selected == '0'}">
					<span class="orange-line"></span>
				</c:when>
				<c:otherwise>
					<span class="empty-line"></span>
				</c:otherwise>
			</c:choose>
			<div class="account-option">
				<i class="fas fa-user fa-sm option-icon user-icon"></i>
				<spring:message code="account.settings.options.data"/>
			</div>
		</a>
		<a class="nav-link nav-option ${param.selected == '1' ? 'active' : ''}"
		   href="${pageContext.request.contextPath}/account/security">
			<c:choose>
				<c:when test="${param.selected == '1'}">
					<span class="blue-line"></span>
				</c:when>
				<c:otherwise>
					<span class="empty-line"></span>
				</c:otherwise>
			</c:choose>
			<div class="account-option">
				<i class="fas fa-lock fa-sm option-icon security-icon"></i>
				<spring:message code="account.settings.options.security"/>
			</div>
		</a>
		<a class="nav-link nav-option"
		   href="${pageContext.request.contextPath}/logout">
			<span class="empty-line"></span>
			<div class="account-option">
				<i class="fas fa-sign-out-alt option-icon logout-icon"></i>
				<spring:message code="account.settings.options.logout"/>
			</div>
		</a>
	</nav>
</div>
