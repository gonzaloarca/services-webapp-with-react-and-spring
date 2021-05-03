<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<div>
    <h4 class="options-title">
        Contratos
    </h4>
    <hr class="divider-bar"/>
    <nav class="nav flex-column">
        <a class="nav-link nav-option ${param.selected == '0' ? 'active' : ''}"
           href="${pageContext.request.contextPath}/my-contracts/client">
            <c:choose>
                <c:when test="${param.selected == '0'}">
                    <span class="yellow-line"></span>
                </c:when>
                <c:otherwise>
                    <span class="empty-line"></span>
                </c:otherwise>
            </c:choose>
            <div class="contracts-option ${param.selected == '0' ? 'font-weight-bold' : ''}">
                <i class="fas fa-users fa-sm option-icon client-icon"></i>
                Servicios que yo contraté
            </div>
        </a>
        <a class="nav-link nav-option ${param.selected == '1' ? 'active' : ''}"
           href="${pageContext.request.contextPath}/my-contracts/professional">
            <c:choose>
                <c:when test="${param.selected == '1'}">
                    <span class="blue-line"></span>
                </c:when>
                <c:otherwise>
                    <span class="empty-line"></span>
                </c:otherwise>
            </c:choose>
            <div class="contracts-option ${param.selected == '1' ? 'font-weight-bold' : ''}">
                <i class="fas fa-user fa-sm option-icon pro-icon"></i>
                Mis servicios
            </div>
        </a>

    </nav>
</div>
