<%@ page import="ar.edu.itba.paw.models.JobContract" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%--Seteo de variable para formateo de fechas--%>
<c:set var="dateStringAux" value="${contractCard.jobContract.creationDate.toLocalDate()}"/>
<fmt:parseDate type="date" value="${dateStringAux}" var="theDate"
               pattern="yyyy-MM-dd"/>
<spring:message code="date.format" var="dateFormat"/>
<fmt:formatDate value="${theDate}" pattern="${dateFormat}" var="dateFormatted"/>

<%--Seteo de variables para enum de States--%>
<c:set var="APPROVED" value="<%=JobContract.ContractState.APPROVED%>"/>
<c:set var="PENDING_APPROVAL" value="<%=JobContract.ContractState.PENDING_APPROVAL%>"/>
<c:set var="PRO_MODIFIED" value="<%=JobContract.ContractState.PRO_MODIFIED%>"/>
<c:set var="CLIENT_MODIFIED" value="<%=JobContract.ContractState.CLIENT_MODIFIED%>"/>
<c:set var="COMPLETED" value="<%=JobContract.ContractState.COMPLETED%>"/>
<c:set var="CLIENT_REJECTED" value="<%=JobContract.ContractState.CLIENT_REJECTED%>"/>
<c:set var="PRO_REJECTED" value="<%=JobContract.ContractState.PRO_REJECTED%>"/>
<c:set var="CLIENT_CANCELLED" value="<%=JobContract.ContractState.CLIENT_CANCELLED%>"/>
<c:set var="PRO_CANCELLED" value="<%=JobContract.ContractState.PRO_CANCELLED%>"/>

<%--Macro para identificar si el contrato debería tener controles para aceptar/rechazar--%>
<c:set var="isApprovable" value="${((contractCard.jobContract.state == PENDING_APPROVAL || contractCard.jobContract.state == CLIENT_MODIFIED) && isOwner)
|| (contractCard.jobContract.state == PRO_MODIFIED && !isOwner)}"/>


<%--Seteo de variable para la imagen y texto a mostrar en el usuario que contrato/el dueño del servicio dependiendo del caso --%>
<c:choose>
    <c:when test="${isOwner}">
        <c:set value="${contractCard.jobContract.client}" var="user"/>
        <c:set value="mycontracts.hiredBy" var="hireDataMessageCode"/>
        <c:set value="mycontracts.reviewed" var="reviewCaption"/>
        <c:set value="${contractCard.jobContract.client.username}" var="cardUser"/>
    </c:when>
    <c:otherwise>
        <c:set value="${contractCard.jobCard.jobPost.user}" var="user"/>
        <c:set value="mycontracts.hiredPro" var="hireDataMessageCode"/>
        <c:set value="mycontracts.yourReview" var="reviewCaption"/>
        <c:set value="${contractCard.jobContract.professional.username}" var="cardUser"/>
    </c:otherwise>
</c:choose>

<div>

    <c:choose>
        <c:when test="${contractState == CLIENT_CANCELLED && !isOwner}">
            <c:set value="cancelled-contract" var="stateBar"/>
            <c:set value="mycontracts.youCancelled" var="stateMessage"/>
        </c:when>
        <c:when test="${contractState == CLIENT_CANCELLED && isOwner}">
            <c:set value="cancelled-contract" var="stateBar"/>
            <c:set value="mycontracts.clientCancelled" var="stateMessage"/>
        </c:when>
        <c:when test="${contractState == PRO_CANCELLED && isOwner}">
            <c:set value="cancelled-contract" var="stateBar"/>
            <c:set value="mycontracts.youCancelled" var="stateMessage"/>
        </c:when>
        <c:when test="${contractState == PRO_CANCELLED && !isOwner}">
            <c:set value="cancelled-contract" var="stateBar"/>
            <c:set value="mycontracts.proCancelled" var="stateMessage"/>
        </c:when>
        <c:when test="${contractState == PRO_REJECTED && !isOwner}">
            <c:set value="cancelled-contract" var="stateBar"/>
            <c:set value="mycontracts.proRejected" var="stateMessage"/>
        </c:when>
        <c:when test="${contractState == PRO_REJECTED && isOwner}">
            <c:set value="cancelled-contract" var="stateBar"/>
            <c:set value="mycontracts.youRejected" var="stateMessage"/>
        </c:when>
        <c:when test="${contractState == COMPLETED}">
            <c:set value="finalized-contract" var="stateBar"/>
            <c:set value="mycontracts.finalized" var="stateMessage"/>
        </c:when>
    </c:choose>

    <c:if test="${contractState == CLIENT_CANCELLED || contractState == PRO_CANCELLED
    || contractState == PRO_REJECTED || contractState == COMPLETED}">
        <div class="${stateBar}">
            <spring:message code="${stateMessage}"/>
        </div>
    </c:if>
    <div class="hire-details-container">
        <div class="hire-user-container">
            <img class="user-avatar" src="<c:url value="/image/user/${user.id}"/>" loading="lazy"
                 alt="<spring:message code="user.avatar"/>">
            <p><spring:message htmlEscape="true" code="${hireDataMessageCode}"
                               arguments="${cardUser}"/></p>
        </div>

        <div class="hire-date-container">
            <p><spring:message code="mycontracts.creationDate" arguments="${dateFormatted}"/></p>
        </div>

    </div>

    <c:if test="${!contractCard.jobCard.jobPost.active}">
        <div class="removed-post-disclaimer">
            <i class="fas fa-exclamation-triangle"></i>
            <spring:message code="jobPost.inactive"/>
        </div>
    </c:if>
    <div style="display: flex; justify-content: space-between; padding: 20px; flex-wrap: wrap">
        <div style="display: flex; justify-content: start; flex-grow: 5">
            <div>
                <c:choose>
                    <c:when test="${requestScope.jobCard.postImageId == null}">
                        <c:url value="/resources/images/${requestScope.jobCard.jobPost.jobType.imagePath}"
                               var="imageSrc"/>
                    </c:when>
                    <c:otherwise>
                        <c:url value="/image/post/${requestScope.jobCard.postImageId}" var="imageSrc"/>
                    </c:otherwise>
                </c:choose>
                <a href="${pageContext.request.contextPath}/job/${requestScope.jobCard.jobPost.id}">
                    <img class="card-image-top service-img"
                         src='${imageSrc}'
                         alt="<spring:message code="profile.service.image"/>">
                </a>
            </div>
            <div class="service-info px-3">
                <a class="service-title service-link mb-2"
                   href="${pageContext.request.contextPath}/job/${requestScope.jobCard.jobPost.id}">
                    <c:out value="${requestScope.jobCard.jobPost.title}"/>
                </a>
                <div class="justify-content-between custom-row">
                    <p class="service-subtitle"><spring:message
                            code="${requestScope.jobCard.jobPost.jobType.stringCode}"/></p>
                    <div class="custom-row">
                        <jsp:include page="components/rateStars.jsp">
                            <jsp:param name="rate" value="${requestScope.jobCard.rating}"/>
                        </jsp:include>
                        <p class="ml-1 service-subtitle">
                            (${requestScope.jobCard.reviewsCount})
                        </p>
                    </div>
                </div>

                <div class="d-flex mt-2">
                    <div class="price-container">
                        <i class="fas fa-tag mr-2 text-white"></i>
                        <p class="price">
                            <spring:message htmlEscape="true" code="${requestScope.jobCard.rateType.stringCode}"
                                            arguments="${requestScope.jobCard.price}"/>
                        </p>
                    </div>
                </div>

                <div class="d-flex">
                    <div class="custom-row service-contracted-times gray-chip">
                        <i class="fas fa-check mr-2"></i>
                        <p class="m-0">
                            <spring:message code="profile.service.contract.quantity"
                                            arguments="${requestScope.jobCard.contractsCompleted}"/>
                        </p>
                    </div>
                </div>
            </div>
        </div>
        <div class="service-controls-container">

            <spring:message htmlEscape="true" code="mycontracts.contact.name"
                            arguments="${contractCard.jobCard.jobPost.user.username}" var="name"/>
            <spring:message htmlEscape="true" code="mycontracts.contact.email"
                            arguments="${contractCard.jobCard.jobPost.user.email}" var="email"/>
            <spring:message htmlEscape="true" code="mycontracts.contact.phone"
                            arguments="${contractCard.jobCard.jobPost.user.phone}" var="phone"/>

            <c:set value="" var="imageSrc"/>
            <c:if test="${contractCard.jobContract.image != null}">
                <c:url value="/image/contract/${contractCard.jobContract.id}" var="imageSrc"/>
            </c:if>

            <%--@elvariable id="changeContractStateForm" type="ar.edu.itba.paw.webapp.form.ChangeContractStateForm"--%>
            <form:form cssClass="w-100 mb-1" action="/contract/state/update" method="post"
                       modelAttribute="changeContractStateForm">
                <c:if test="${isApprovable}">

                    <button class="btn contract-control-accept text-uppercase mb-1" type="submit"
                            onclick="changeContractState(${APPROVED.ordinal()}, 'active')">
                        <i class="fas fa-check mr-1"></i>
                        <spring:message code="mycontracts.accept"/>
                    </button>

                    <button class="btn contract-control-reject text-uppercase" type="submit"
                            onclick="changeContractState(${contractType == 'professional' ? PRO_REJECTED.ordinal()
                                    : CLIENT_REJECTED.ordinal()}, 'finalized')">
                        <i class="fas fa-times mr-1"></i>
                        <spring:message code="mycontracts.reject"/>
                    </button>
                    <hr class="divider-bar-thick">
                </c:if>
                <c:if test="${contractState == APPROVED}">
                    <button class="btn contract-control-accept text-uppercase mb-1" type="submit"
                            onclick="changeContractState(${COMPLETED.ordinal()}, 'finalized')">
                        <i class="fas fa-check mr-1"></i>
                        <spring:message code="mycontracts.finalize"/>
                    </button>

                    <button class="btn contract-control-reject text-uppercase" type="submit"
                            onclick="changeContractState(${contractType == 'professional' ? PRO_CANCELLED.ordinal()
                                    : CLIENT_CANCELLED.ordinal()}, 'finalized')">
                        <i class="fas fa-times mr-1"></i>
                        <spring:message code="mycontracts.cancel"/>
                    </button>
                    <hr class="divider-bar-thick">
                </c:if>
                <form:hidden path="id" value="${contractCard.jobContract.id}"/>
                <form:hidden id="new-state" path="newState"/>
                <form:hidden id="return-url" path="returnURL"
                             value="${pageContext.request.contextPath}/my-contracts/${contractType}/"/>

            </form:form>


            <a class="btn contract-control-details btn-link text-uppercase"
               onclick='openDetailsModal("${contractCard.jobContract.description}", "${imageSrc}")'>
                <i class="fa fa-clipboard-list mr-1" aria-hidden="true"></i>
                <p>
                    <spring:message code="mycontract.details"/>
                </p>
            </a>
            <hr class="divider-bar-thick">

            <a class="btn contract-control-contact btn-link text-uppercase"
               onclick='openContactModal("${name}", "${email}", "${phone}")'>
                <i class="fa fa-info-circle mr-1" aria-hidden="true"></i>
                <p><spring:message code="mycontracts.contact"/></p>
            </a>

            <c:choose>
                <c:when test="${contractCard.review != null}">
                    <div style="color: #787878; width: 100% ">
                        <hr class="divider-bar-thick">
                        <p class="my-1 mx-auto text-center"><spring:message code="${reviewCaption}"/></p>
                        <div class="gray-chip mx-auto">
                            <jsp:include page="components/rateStars.jsp">
                                <jsp:param name="rate" value="${contractCard.review.rate}"/>
                            </jsp:include>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:if test="${contractCard.jobCard.jobPost.active && !isOwner}">
                        <a class="contract-control-rate text-uppercase"
                           href="${pageContext.request.contextPath}/rate-contract/${contractCard.jobContract.id}">
                            <i class="bi bi-star mr-1"></i>
                            <p><spring:message code="mycontracts.ratecontract"/></p>
                        </a>
                    </c:if>
                    <c:if test="${isOwner}">
                        <hr class="divider-bar-thick">
                        <p class="text-black-50" style="margin: 0 auto"><spring:message code="mycontracts.unrated"/></p>
                    </c:if>
                </c:otherwise>
            </c:choose>

        </div>

        <%--Modal de contacto--%>
        <div class="modal fade" tabindex="-1" id="contact-modal" aria-labelledby="modal" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title font-weight-bold">
                            <spring:message code="mycontracts.contact.title"/></h5>
                    </div>
                    <div class="modal-body">
                        <div class="contact-modal-row d-flex align-items-center">
                            <i class="fas fa-user mr-2"></i>
                            <p class="m-0" id="modalProfessionalName"></p>
                        </div>
                        <div class="contact-modal-row d-flex align-items-center">
                            <i class="fas fa-envelope mr-2"></i>
                            <p class="m-0" id="modalProfessionalEmail"></p>
                        </div>
                        <div class="contact-modal-row d-flex align-items-center">
                            <i class="fas fa-phone mr-2"></i>
                            <p class="m-0" id="modalProfessionalPhone"></p>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">
                            <spring:message code="mycontracts.contact.close"/></button>
                    </div>
                </div>
            </div>
        </div>
        <%--        Modal de detalles de contrato--%>

        <div class="modal fade" tabindex="-1" id="details-modal"
             aria-labelledby="modal" aria-hidden="true">
            <div id="details-modal-dialog" class="modal-dialog modal-lg modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title font-weight-bold">
                            <spring:message code="mycontracts.details.title"/></h5>
                    </div>
                    <div class="modal-body p-4 d-flex justify-content-around">
                        <div id="details-image-container">
                            <p id="details-modal-image-header" class="font-weight-bold">
                                <spring:message code="mycontracts.modal.image"/>
                            </p>
                            <img id="details-modal-image" src=""
                                 alt="<spring:message code="mycontracts.modal.image.alt"/>">
                        </div>
                        <div id="details-description-container">
                            <p class="font-weight-bold">
                                <spring:message code="mycontracts.modal.description"/>
                            </p>
                            <p id="details-modal-description"></p>
                        </div>

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">
                            <spring:message code="mycontracts.contact.close"/></button>
                    </div>
                </div>
            </div>
        </div>

        <script>
            function openContactModal(name, email, phone) {
                $('#modalProfessionalName').text(name);
                $('#modalProfessionalEmail').text(email);
                $('#modalProfessionalPhone').text(phone);
                $('#contact-modal').modal('show');
            }

            function openDetailsModal(description, image) {
                const imageElem = $('#details-modal-image');
                const imageHeader = $('#details-modal-image-header');
                const imageContainer = $('#details-image-container');
                const descriptionContainer = $('#details-description-container');
                const modalDialog = $('#details-modal-dialog');

                if (image === "") {
                    imageContainer.hide();
                    imageElem.hide();
                    imageHeader.hide();
                    descriptionContainer.css('width', '100%');
                    modalDialog.removeClass('modal-lg');
                } else {
                    imageElem.attr('src', image);
                    imageContainer.show();
                    imageElem.show();
                    imageHeader.show();
                    descriptionContainer.css('width', '45%');
                    modalDialog.addClass('modal-lg');
                }


                $('#details-modal-description').text(description);
                $('#details-modal').modal('show');
            }
        </script>

    </div>
    <div style="color: #485696; margin: 0 30px; padding: 10px 0" class="d-flex justify-content-start">
        <p style=" margin: 0 10px;"><i class="fas fa-cube mr-2"></i>
            <spring:message code="mycontracts.card.hired"/>
        </p>
        <p class="hired-package-title"><c:out value="${contractCard.jobContract.jobPackage.title}"/></p>
    </div>
</div>

<script>
    function changeContractState(state, urlAppend) {
        let returnUrl = $('#return-url');
        let newState = $('#new-state');
        newState.val(state);
        returnUrl.val(returnUrl.val() + urlAppend);
    }

</script>
