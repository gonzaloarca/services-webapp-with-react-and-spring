<%@ page import="ar.edu.itba.paw.models.JobContract" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%--Seteo de variable para formateo de fechas--%>
<spring:message code="date.format" var="dateFormat"/>
<spring:message code="time.format" var="timeFormat"/>
<spring:message code="dateTime.format" var="dateTimeFormat"/>

<c:set var="creationDateStringAux" value="${requestScope.contractCard.jobContract.creationDate.toLocalDate()}"/>
<fmt:parseDate type="date" value="${creationDateStringAux}" var="creationDate"
               pattern="yyyy-MM-dd"/>
<fmt:formatDate value="${creationDate}" pattern="${dateFormat}" var="creationDateFormatted"/>

<c:set var="scheduledDateStringAux" value="${requestScope.contractCard.jobContract.scheduledDate.toLocalDate()}"/>
<fmt:parseDate type="date" value="${scheduledDateStringAux}" var="scheduledDate"
               pattern="yyyy-MM-dd"/>
<fmt:formatDate value="${scheduledDate}" pattern="${dateFormat}" var="scheduledDateFormatted"/>

<c:set var="scheduledTimeStringAux" value="${requestScope.contractCard.jobContract.scheduledDate.toLocalTime()}"/>
<fmt:parseDate type="time" value="${scheduledTimeStringAux}" var="scheduledTime"
               pattern="HH:mm"/>
<fmt:formatDate value="${scheduledTime}" pattern="${timeFormat}" var="scheduledTimeFormatted"/>

<c:set var="lastModifiedDateStringAux" value="${requestScope.contractCard.jobContract.lastModifiedDate.toLocalDate()}"/>
<fmt:parseDate type="date" value="${lastModifiedDateStringAux}" var="lastModifiedDate"
               pattern="yyyy-MM-dd"/>
<fmt:formatDate value="${lastModifiedDate}" pattern="${dateFormat}" var="lastModifiedDateFormatted"/>

<c:set var="lastModifiedTimeStringAux" value="${requestScope.contractCard.jobContract.lastModifiedDate.toLocalTime()}"/>
<fmt:parseDate type="time" value="${lastModifiedTimeStringAux}" var="lastModifiedTime"
               pattern="HH:mm"/>
<fmt:formatDate value="${lastModifiedTime}" pattern="${timeFormat}" var="lastModifiedTimeFormatted"/>

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
<c:set var="isApprovable" value="${((contractState == PENDING_APPROVAL || contractState == CLIENT_MODIFIED) && isOwner)
|| (contractState == PRO_MODIFIED && !isOwner)}"/>

<c:set var="isReschedulable" value="${contractState != COMPLETED && contractState != CLIENT_REJECTED
&& contractState != PRO_REJECTED && contractState != CLIENT_CANCELLED && contractState != PRO_CANCELLED}"/>

<c:set var="hasStateBar" value="${contractState == CLIENT_CANCELLED || contractState == PRO_CANCELLED
    || contractState == PRO_REJECTED || contractState == COMPLETED || contractState == PRO_MODIFIED || contractState == CLIENT_MODIFIED}"/>


<%--Seteo de variable para la imagen y texto a mostrar en el usuario que contrato/el dueño del servicio dependiendo del caso --%>
<c:choose>
    <c:when test="${isOwner}">
        <c:set value="${requestScope.contractCard.jobContract.client}" var="user"/>
        <c:set value="mycontracts.hiredBy" var="hireDataMessageCode"/>
        <c:set value="mycontracts.reviewed" var="reviewCaption"/>
        <c:set value="${requestScope.contractCard.jobContract.client.username}" var="cardUser"/>
    </c:when>
    <c:otherwise>
        <c:set value="${requestScope.contractCard.jobCard.jobPost.user}" var="user"/>
        <c:set value="mycontracts.hiredPro" var="hireDataMessageCode"/>
        <c:set value="mycontracts.yourReview" var="reviewCaption"/>
        <c:set value="${requestScope.contractCard.jobContract.professional.username}" var="cardUser"/>
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
        <c:when test="${contractState == PRO_MODIFIED && !isOwner}">
            <c:set value="rescheduled-contract" var="stateBar"/>
            <c:set value="mycontracts.proRescheduled" var="stateMessage"/>
        </c:when>
        <c:when test="${contractState == PRO_MODIFIED && isOwner}">
            <c:set value="rescheduled-contract" var="stateBar"/>
            <c:set value="mycontracts.youRescheduled" var="stateMessage"/>
        </c:when>
        <c:when test="${contractState == CLIENT_MODIFIED && !isOwner}">
            <c:set value="rescheduled-contract" var="stateBar"/>
            <c:set value="mycontracts.youRescheduled" var="stateMessage"/>
        </c:when>
        <c:when test="${contractState == CLIENT_MODIFIED && isOwner}">
            <c:set value="rescheduled-contract" var="stateBar"/>
            <c:set value="mycontracts.clientRescheduled" var="stateMessage"/>
        </c:when>
    </c:choose>

    <c:if test="${hasStateBar}">
        <div class="${stateBar}">
            <spring:message code="${stateMessage}"/>
            <c:if test="${contractState != PRO_MODIFIED && contractState != CLIENT_MODIFIED}">
                - (<spring:message htmlEscape="true" code="dateTime.formatted"
                                   arguments="${lastModifiedDateFormatted}, ${lastModifiedTimeFormatted} "/>)
            </c:if>

        </div>
    </c:if>
    <div class="hire-details-container ${hasStateBar ? 'hire-details-container-with-state' : ''}">
        <div class="hire-user-container">
            <img loading="lazy" class="user-avatar" src="<c:url value="/image/user/${user.id}"/>"
                 alt="<spring:message code="user.avatar"/>">
            <p><spring:message htmlEscape="true" code="${hireDataMessageCode}"
                               arguments="${cardUser}"/></p>
        </div>

        <div class="hire-date-container">
            <p><spring:message code="mycontracts.creationDate" arguments="${creationDateFormatted}"/></p>
        </div>

    </div>

    <c:if test="${!requestScope.contractCard.jobCard.jobPost.active}">
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
                    <div class="service-image-container">
                        <img loading="lazy" class="card-image-top service-img"
                             src='${imageSrc}'
                             alt="<spring:message code="profile.service.image"/>">
                        <div class="scheduled-container ${(contractState == PRO_MODIFIED || contractState == CLIENT_MODIFIED) ? 'rescheduled-container' : ''}">
                            <p style="color: black; margin: 0">
                                <c:choose>
                                    <c:when test="${contractState == PRO_MODIFIED || contractState == CLIENT_MODIFIED}">
                                        <spring:message code="mycontracts.rescheduledDate"/>
                                    </c:when>
                                    <c:otherwise>
                                        <spring:message code="mycontracts.scheduledDate"/>
                                    </c:otherwise>
                                </c:choose>

                            </p>
                            <div class="scheduled-date-container">
                                <p class="m-0"><c:out value="${scheduledDateFormatted}"/></p>
                                <p class="m-0"><spring:message code="time.formatted"
                                                               arguments="${scheduledTimeFormatted}"/></p>
                            </div>
                        </div>
                    </div>
                </a>
            </div>
            <div class="service-info px-3">
                <div>
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
                            arguments="${requestScope.contractCard.jobCard.jobPost.user.username}" var="name"/>
            <spring:message htmlEscape="true" code="mycontracts.contact.email"
                            arguments="${requestScope.contractCard.jobCard.jobPost.user.email}" var="email"/>
            <spring:message htmlEscape="true" code="mycontracts.contact.phone"
                            arguments="${requestScope.contractCard.jobCard.jobPost.user.phone}" var="phone"/>

            <c:set value="" var="imageSrc"/>
            <c:if test="${requestScope.contractCard.jobContract.imageType != null}">
                <c:url value="/image/contract/${requestScope.contractCard.jobContract.id}" var="imageSrc"/>
            </c:if>

            <%--@elvariable id="updateContractForm" type="ar.edu.itba.paw.webapp.form.UpdateContractForm"--%>
            <form:form cssClass="w-100 mb-1"
                       action="${pageContext.request.contextPath}/my-contracts/${contractType}/${contractStateEndpoint}/${requestScope.contractCard.jobContract.id}/update"
                       method="post"
                       modelAttribute="updateContractForm"
                       autocomplete="off">
                <c:if test="${isApprovable}">

                    <button class="btn contract-control-accept text-uppercase mb-1" type="submit"
                            onclick="changeContractState(${requestScope.contractCard.jobContract.id}, ${APPROVED.ordinal()}, 'active')">
                        <i class="fas fa-check mr-1"></i>
                        <spring:message code="mycontracts.accept"/>
                    </button>

                    <button class="btn contract-control-reject text-uppercase" type="submit"
                            onclick="changeContractState(${requestScope.contractCard.jobContract.id}, ${contractType == 'professional' ? PRO_REJECTED.ordinal()
                                    : CLIENT_REJECTED.ordinal()}, 'finalized')">
                        <i class="fas fa-times mr-1"></i>
                        <spring:message code="mycontracts.reject"/>
                    </button>

                    <hr class="divider-bar-thick">
                </c:if>

                <c:if test="${contractState == APPROVED}">
                    <button class="btn contract-control-accept text-uppercase mb-1" type="submit"
                            onclick="changeContractState(${requestScope.contractCard.jobContract.id}, ${COMPLETED.ordinal()}, 'finalized')">
                        <i class="fas fa-check mr-1"></i>
                        <spring:message code="mycontracts.finalize"/>
                    </button>

                    <button class="btn contract-control-reject text-uppercase" type="submit"
                            onclick="changeContractState(${requestScope.contractCard.jobContract.id}, ${contractType == 'professional' ? PRO_CANCELLED.ordinal()
                                    : CLIENT_CANCELLED.ordinal()}, 'finalized')">
                        <i class="fas fa-times mr-1"></i>
                        <spring:message code="mycontracts.cancel"/>
                    </button>
                    <hr class="divider-bar-thick">
                </c:if>

                <c:if test="${isReschedulable}">
                    <button class="btn contract-control-reschedule text-uppercase" type="button"
                            onclick="openRescheduleModal(${requestScope.contractCard.jobContract.id}, '${requestScope.contractCard.scheduledDateStr}')">
                        <i class="far fa-calendar-alt mr-1"></i>
                        <spring:message code="mycontracts.reschedule"/>
                    </button>
                    <hr class="divider-bar-thick">

                    <div class="modal fade" tabindex="-1"
                         id="reschedule-modal-${requestScope.contractCard.jobContract.id}"
                         aria-labelledby="modal" aria-hidden="true">
                        <div id="reschedule-modal-dialog" class="modal-dialog modal-dialog-centered">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title font-weight-bold">
                                        <form:label path="newScheduledDate">
                                            <spring:message code="mycontracts.reschedule.title"/>
                                        </form:label>
                                    </h5>

                                </div>
                                <div class="modal-body p-4 d-flex justify-content-around">

                                    <spring:message code="contract.create.form.date.placeholder" var="datePlaceholder"/>
                                    <div class="input-group date has-validation" id="datetimepicker1"
                                         data-target-input="nearest">
                                        <input id="date-input-${requestScope.contractCard.jobContract.id}"
                                               type="text"
                                               class="form-control datetimepicker-input"
                                               data-target="#datetimepicker1" placeholder="${datePlaceholder}"
                                               data-toggle="datetimepicker"
                                               value="${requestScope.contractCard.scheduledDateStr}"/>
                                        <div class="input-group-append" data-target="#datetimepicker1"
                                             data-toggle="datetimepicker">
                                            <div class="input-group-text"
                                                 style="background-color: #485696; color: white">
                                                <i class="far fa-calendar-alt"></i>
                                            </div>
                                        </div>
                                        <div class="invalid-feedback" style="background-color: white; margin: 0">
                                            <spring:message code="contract.create.invalid.date"/>
                                        </div>
                                    </div>
                                    <c:set var="localeCode" value="${pageContext.response.locale}"/>
                                    <script type="text/javascript">
                                        $(function () {
                                            $('#datetimepicker1').datetimepicker({
                                                locale: '${localeCode}',
                                                minDate: moment(),
                                            });
                                        });
                                    </script>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                        <spring:message code="mycontracts.contact.close"/>
                                    </button>
                                    <button type="submit"
                                            onclick="changeContractDate(${requestScope.contractCard.jobContract.id}, ${isOwner ? PRO_MODIFIED.ordinal() : CLIENT_MODIFIED.ordinal()})"
                                            class="btn reschedule-submit-btn">
                                        <spring:message code="mycontracts.reschedule.submit"/>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>
                <form:hidden id="new-state-${requestScope.contractCard.jobContract.id}" path="newState"/>
                <form:hidden id="return-url-${requestScope.contractCard.jobContract.id}" path="returnURL"
                             value="/my-contracts/${contractType}/"/>
                <form:hidden id="hidden-scheduled-date-${requestScope.contractCard.jobContract.id}"
                             path="newScheduledDate"
                             value="${requestScope.contractCard.scheduledDateStr}"/>

            </form:form>

            <p id="details-description-text-${requestScope.contractCard.jobContract.id}" style="display: none"><c:out
                    value="${requestScope.contractCard.jobContract.description}"/></p>
            <p id="image-source-${requestScope.contractCard.jobContract.id}" style="display: none"><c:out
                    value="${imageSrc}"/></p>
            <a class="btn contract-control-details btn-link text-uppercase"
               onclick='openDetailsModal(${requestScope.contractCard.jobContract.id})'>
                <i class="fa fa-clipboard-list mr-1" aria-hidden="true"></i>
                <p>
                    <spring:message code="mycontract.details"/>
                </p>
            </a>
            <hr class="divider-bar-thick">

            <a class="btn contract-control-contact btn-link text-uppercase"
               onclick='openContactModal("<c:out value="${name}"/>", "<c:out value="${email}"/>", "<c:out
                       value="${phone}"/>")'>
                <i class="fa fa-info-circle mr-1" aria-hidden="true"></i>
                <p><spring:message code="mycontracts.contact"/></p>
            </a>

            <c:choose>
                <c:when test="${requestScope.contractCard.review != null}">
                    <div style="color: #787878; width: 100% ">
                        <hr class="divider-bar-thick">
                        <p class="my-1 mx-auto text-center"><spring:message code="${reviewCaption}"/></p>
                        <div class="gray-chip mx-auto">
                            <jsp:include page="components/rateStars.jsp">
                                <jsp:param name="rate" value="${requestScope.contractCard.review.rate}"/>
                            </jsp:include>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:if test="${requestScope.contractCard.jobCard.jobPost.active && !isOwner && contractState == COMPLETED}">
                        <a class="contract-control-rate text-uppercase"
                           href="${pageContext.request.contextPath}/rate-contract/${requestScope.contractCard.jobContract.id}">
                            <i class="bi bi-star mr-1"></i>
                            <p><spring:message code="mycontracts.ratecontract"/></p>
                        </a>
                    </c:if>
                    <c:if test="${isOwner && contractState == COMPLETED}">
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
        <p id="details-description-text" hidden><c:out
                value="${requestScope.contractCard.jobContract.description}"/></p>
        <p id="image-source" hidden><c:out value="${imageSrc}"/></p>
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
                            <img loading="lazy" id="details-modal-image" src=""
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
    </div>
    <div style="color: #485696; margin: 0 30px; padding: 10px 0" class="d-flex justify-content-start">
        <p style=" margin: 0 10px;"><i class="fas fa-cube mr-2"></i>
            <spring:message code="mycontracts.card.hired"/>
        </p>
        <p class="hired-package-title"><c:out value="${requestScope.contractCard.jobContract.jobPackage.title}"/></p>
    </div>
</div>
