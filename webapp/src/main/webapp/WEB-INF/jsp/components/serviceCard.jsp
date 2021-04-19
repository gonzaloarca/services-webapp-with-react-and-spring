<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<a class="service-link" href="${pageContext.request.contextPath}/job/${requestScope.data.jobPost.id}">
    <div class="row no-gutters">
        <div class="col-md-3">
            <%--                                        TODO: LEVANTAR IMAGEN DE SERVICE--%>
            <img class="card-image-top service-img"
                 src='<c:url value="/resources/images/cleaning.png" />'
                 alt="<spring:message code="profile.service.image"/>">
        </div>
        <div class="col-md-9 px-3">
            <h4 class="service-title">
                <c:out value="${requestScope.data.jobPost.title}"/>
            </h4>
            <div class="justify-content-between custom-row">
                <h6 class="service-subtitle"><spring:message code="${requestScope.data.jobPost.jobType.stringCode}"/></h6>
                <div class="custom-row">
                    <jsp:include page="rateStars.jsp">
                        <jsp:param name="rate" value="${requestScope.data.jobPost.rating}"/>
                    </jsp:include>
                    <h6 class="ml-3 service-subtitle">
                        (${requestScope.data.reviewsCount})
                    </h6>
                </div>
            </div>

            <div class="d-flex mt-2">
                <div class="price-container">
                    <p class="price">
                        <spring:message code="${requestScope.data.rateType.stringCode}"
                                        arguments="${requestScope.data.price}"/>
                    </p>
                </div>
            </div>

            <div class="d-flex">
                <div class="custom-row service-contracted-times gray-chip">
                    <i class="fas fa-check mr-2"></i>
                    <h6 class="m-0">
                        <spring:message code="profile.service.contract.quantity"
                                        arguments="${requestScope.data.contractsCompleted}"/>
                    </h6>
                </div>
            </div>

        </div>
    </div>
</a>