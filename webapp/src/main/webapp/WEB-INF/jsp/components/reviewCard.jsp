<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="dateStringAux" value="${requestScope.data.creationDate}" />
<fmt:parseDate value="${dateStringAux}" var="theDate"
               pattern="yyyy-MM-dd'T'HH:mm:ss.S" />
<spring:message code="date.format" var="dateFormat"/>
<fmt:formatDate value="${theDate}" pattern="${dateFormat}" var="dateFormatted"/>

<div class="custom-row justify-content-between">
    <c:choose>
        <c:when test="${requestScope.data.client.image.string != null}">
            <c:set var="profilePic"
                   value="data:${requestScope.data.client.image.type};base64,${requestScope.data.client.image.string}"/>
        </c:when>
        <c:otherwise>
            <c:url var="profilePic" value="/resources/images/defaultavatar.svg"/>
        </c:otherwise>
    </c:choose>
    <img class="review-img" src='${profilePic}'
         alt="<spring:message code="profile.image"/>">
    <div class="review-header">
        <p class="mb-0 font-weight-bold"><c:out value="${requestScope.data.client.username}"/></p>
        <p class="mb-0 text-black-50">
            <c:out value="${dateFormatted}" />
        </p>
    </div>
    <jsp:include page="components/rateStars.jsp">
        <jsp:param name="rate" value="${requestScope.data.rate}"/>
    </jsp:include>
</div>
<p class="mt-2 mb-0 review-title"><c:out value="${requestScope.data.title}"/></p>
<p><c:out value="${requestScope.data.description}"/></p>
<c:if test="${requestScope.withLink}">
    <a href="${pageContext.request.contextPath}/job/${requestScope.data.jobPost.id}">
        <p class="review-link mt-2">
            <i class="bi bi-box-arrow-up-right"></i>
            <spring:message htmlEscape="true" code="profile.review.link"
                            arguments="${requestScope.data.jobPost.title}"/>
        </p>
    </a>
</c:if>