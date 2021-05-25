<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<div style="display: flex; justify-content: space-between">
    <a style="display: flex; justify-content: start; width: 83%"
       class="service-link"
       href="${pageContext.request.contextPath}/job/${requestScope.data.jobPost.id}">

        <div>
            <c:choose>
                <c:when test="${requestScope.data.postImage.image.string == null}">
                    <c:url value="/resources/images/${requestScope.data.jobPost.jobType.imagePath}" var="imageSrc"/>
                </c:when>
                <c:otherwise>
                    <c:set value="data:${requestScope.data.postImage.image.type};base64,${requestScope.data.postImage.image.string}"
                           var="imageSrc"/>
                </c:otherwise>
            </c:choose>
            <img class="card-image-top service-img"
                 src='${imageSrc}'
                 alt="<spring:message code="profile.service.image"/>">
        </div>
        <div class="service-info px-3">
            <h5 class="service-title">
                <c:out value="${requestScope.data.jobPost.title}"/>
            </h5>
            <div class="justify-content-between custom-row">
                <p class="service-subtitle"><spring:message
                        code="${requestScope.data.jobPost.jobType.stringCode}"/></p>
                <div class="custom-row">
                    <jsp:include page="components/rateStars.jsp">
                        <jsp:param name="rate" value="${requestScope.data.rating}"/>
                    </jsp:include>
                    <p class="ml-3 service-subtitle">
                        (${requestScope.data.reviewsCount})
                    </p>
                </div>
            </div>

            <div class="d-flex mt-2">
                <div class="price-container">
                    <i class="fas fa-tag mr-2 text-white"></i>
                    <p class="price">
                        <spring:message htmlEscape="true" code="${requestScope.data.rateType.stringCode}"
                                        arguments="${requestScope.data.price}"/>
                    </p>
                </div>
            </div>

            <div class="d-flex">
                <div class="custom-row service-contracted-times gray-chip">
                    <i class="fas fa-check mr-2"></i>
                    <p class="m-0">
                        <c:choose>
                            <c:when test="${requestScope.data.contractsCompleted == 1}">
                                <spring:message code="profile.service.contract.quantity.onlyOne"/>
                            </c:when>
                            <c:otherwise>
                                <spring:message code="profile.service.contract.quantity"
                                                arguments="${requestScope.data.contractsCompleted}"/>
                            </c:otherwise>
                        </c:choose>
                    </p>
                </div>
            </div>
        </div>
    </a>
    <sec:authorize url="/job/${requestScope.data.jobPost.id}/edit">
        <div class="service-controls-container">
            <a href="<c:url value="/job/${requestScope.data.jobPost.id}/edit"/>"
               class="btn service-control-edit btn-link text-uppercase">
                <i class="fas fa-edit"></i>
                <spring:message code="edit"/>
            </a>

                <%--@elvariable id="deleteJobPostForm" type="ar.edu.itba.paw.webapp.form.DeleteItemForm"--%>
            <form:form modelAttribute="deleteJobPostForm" cssClass="w-100" action="/job/delete" method="post"
                       cssStyle="margin-bottom: 0">
                <button type="submit" class="btn service-control-delete text-uppercase w-100">
                    <i class="fas fa-trash-alt"></i>
                    <spring:message code="delete"/>
                </button>

                <form:hidden path="id" value="${requestScope.data.jobPost.id}"/>
                <form:hidden path="returnURL" value="${requestScope.returnURL}"/>
            </form:form>
        </div>
    </sec:authorize>


</div>
