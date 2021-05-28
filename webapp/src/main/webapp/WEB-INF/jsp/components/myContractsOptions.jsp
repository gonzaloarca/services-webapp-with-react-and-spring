<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<div>
    <h4 class="options-title">
        <spring:message code="contract.options.title"/>
    </h4>
    <hr class="divider-bar"/>
    <nav class="nav flex-column">
        <a class="nav-link nav-option ${param.selected == '0' ? 'active' : ''}"
           href="">
            <c:choose>
                <c:when test="${param.selected == '0'}">
                    <span class="green-line"></span>
                </c:when>
                <c:otherwise>
                    <span class="empty-line"></span>
                </c:otherwise>
            </c:choose>
            <div class="contracts-option ${param.selected == '0' ? 'font-weight-bold' : ''}">
                <i class="fas fa-briefcase option-icon active-contracts-icon"></i>
                <spring:message code="contract.options.active"/>
            </div>
        </a>

        <a class="nav-link nav-option ${param.selected == '1' ? 'active' : ''}"
           href="${pageContext.request.contextPath}/my-contracts/professional">
            <c:choose>
                <c:when test="${param.selected == '1'}">
                    <span class="orange-line"></span>
                </c:when>
                <c:otherwise>
                    <span class="empty-line"></span>
                </c:otherwise>
            </c:choose>
            <div class="contracts-option ${param.selected == '1' ? 'font-weight-bold' : ''}">
                <i class="far fa-clock option-icon pending-contracts-icon"></i>
                <spring:message code="contract.options.pending"/>
            </div>
        </a>

        <a class="nav-link nav-option ${param.selected == '2' ? 'active' : ''}"
           href="${pageContext.request.contextPath}/my-contracts/professional">
            <c:choose>
                <c:when test="${param.selected == '2'}">
                    <span class="blue-line"></span>
                </c:when>
                <c:otherwise>
                    <span class="empty-line"></span>
                </c:otherwise>
            </c:choose>
            <div class="contracts-option ${param.selected == '2' ? 'font-weight-bold' : ''}">
                <i class="fas fa-check option-icon finalized-contracts-icon"></i>
                <spring:message code="contract.options.completed"/>
            </div>
        </a>

    </nav>
</div>
