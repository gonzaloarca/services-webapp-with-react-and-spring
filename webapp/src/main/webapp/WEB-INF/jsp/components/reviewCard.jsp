<%@ page contentType="text/html;charset=UTF-8" %>
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
    <div class="review-username">
        <h4><c:out value="${requestScope.data.client.username}"/></h4>
        <%--                                        TODO: IMPLEMENTAR FECHA EN REVIEWS--%>
        <%--                                        <h5>15/3/21</h5>--%>
    </div>
    <jsp:include page="components/rateStars.jsp">
        <jsp:param name="rate" value="${requestScope.data.rate}"/>
    </jsp:include>
</div>
<h4 class="mt-2 review-title"><c:out value="${requestScope.data.title}"/></h4>
<h5><c:out value="${requestScope.data.description}"/></h5>
<c:if test="${requestScope.withLink}">
<a href="${pageContext.request.contextPath}/job/${requestScope.data.jobPost.id}">
    <h5 class="review-link mt-2">
        <i class="bi bi-box-arrow-up-right"></i>
        <spring:message code="profile.review.link"
                        arguments="${requestScope.data.jobPost.title}"/>
    </h5>
</a>
</c:if>