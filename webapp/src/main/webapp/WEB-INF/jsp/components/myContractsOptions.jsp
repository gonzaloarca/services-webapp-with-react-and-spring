<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<div>
    <h4 class="options-title">
        <spring:message code="contract.options.title"/>
    </h4>
    <hr class="divider-bar"/>
    <nav class="nav flex-column">
        <a class="nav-link nav-option ${param.contractState == 'active' ? 'active' : ''}"
           href="${pageContext.request.contextPath}/my-contracts/${param.contractType}/active">
            <c:choose>
                <c:when test="${param.contractState == 'active'}">
                    <span class="green-line"></span>
                </c:when>
                <c:otherwise>
                    <span class="empty-line"></span>
                </c:otherwise>
            </c:choose>
            <div class="contracts-option ${param.contractState == 'active' ? 'font-weight-bold' : ''}">
                <i class="fas fa-briefcase option-icon active-contracts-icon"></i>
                <spring:message code="contract.options.active"/>
            </div>
        </a>

        <a class="nav-link nav-option ${param.contractState == 'pending' ? 'active' : ''}"
           href="${pageContext.request.contextPath}/my-contracts/${param.contractType}/pending">
            <c:choose>
                <c:when test="${param.contractState == 'pending'}">
                    <span class="orange-line"></span>
                </c:when>
                <c:otherwise>
                    <span class="empty-line"></span>
                </c:otherwise>
            </c:choose>
            <div class="contracts-option ${param.contractState == 'pending' ? 'font-weight-bold' : ''}">
                <i class="far fa-clock option-icon pending-contracts-icon"></i>
                <spring:message code="contract.options.pending"/>
            </div>
        </a>

        <a class="nav-link nav-option ${param.contractState == 'finalized' ? 'active' : ''}"
           href="${pageContext.request.contextPath}/my-contracts/${param.contractType}/finalized">
            <c:choose>
                <c:when test="${param.contractState == 'finalized'}">
                    <span class="blue-line"></span>
                </c:when>
                <c:otherwise>
                    <span class="empty-line"></span>
                </c:otherwise>
            </c:choose>
            <div class="contracts-option ${param.contractState == 'finalized' ? 'font-weight-bold' : ''}">
                <i class="fas fa-check option-icon finalized-contracts-icon"></i>
                <spring:message code="contract.options.finalized"/>
            </div>
        </a>

    </nav>
</div>
