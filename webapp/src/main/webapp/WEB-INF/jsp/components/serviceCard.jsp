<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<a class="service-link" href="${pageContext.request.contextPath}/job/${requestScope.data.jobPost.id}">
    <div style="display: flex; justify-content: space-between">
        <div>
            <c:choose>
                <c:when test="${requestScope.data.postImage == null}">
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
                <p class="service-subtitle"><spring:message code="${requestScope.data.jobPost.jobType.stringCode}"/></p>
                <div class="custom-row">
                    <jsp:include page="components/rateStars.jsp">
                        <jsp:param name="rate" value="${requestScope.data.jobPost.rating}"/>
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
                        <spring:message code="${requestScope.data.rateType.stringCode}"
                                        arguments="${requestScope.data.price}"/>
                    </p>
                </div>
            </div>

            <div class="d-flex">
                <div class="custom-row service-contracted-times gray-chip">
                    <i class="fas fa-check mr-2"></i>
                    <p class="m-0">
                        <spring:message code="profile.service.contract.quantity"
                                        arguments="${requestScope.data.contractsCompleted}"/>
                    </p>
                </div>
            </div>
        </div>
    </div>
</a>