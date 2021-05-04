<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div>
    <c:set value="${contractCard.jobContract.encodedImage}" var="encodedImage"/>
    <c:if test="${!contractCard.jobCard.jobPost.active}">
        <div class="removed-post-disclaimer">
            <i class="fas fa-exclamation-triangle"></i>
            La publicación ha sido eliminada
        </div>
    </c:if>
    <div style="display: flex; justify-content: space-between; padding: 20px">
        <div style="display: flex; justify-content: start; width: 83%">
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
                <a href="${pageContext.request.contextPath}/job/${requestScope.data.jobPost.id}">
                    <img class="card-image-top service-img"
                         src='${imageSrc}'
                         alt="<spring:message code="profile.service.image"/>">
                </a>
            </div>
            <div class="service-info px-3">
                <a class="service-title service-link mb-2"
                   href="${pageContext.request.contextPath}/job/${requestScope.data.jobPost.id}">
                    <c:out value="${requestScope.data.jobPost.title}"/>
                </a>
                <div class="justify-content-between custom-row">
                    <p class="service-subtitle"><spring:message
                            code="${requestScope.data.jobPost.jobType.stringCode}"/></p>
                    <div class="custom-row">
                        <jsp:include page="components/rateStars.jsp">
                            <jsp:param name="rate" value="${requestScope.data.jobPost.rating}"/>
                        </jsp:include>
                        <p class="ml-1 service-subtitle">
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
                            <spring:message code="profile.service.contract.quantity"
                                            arguments="${requestScope.data.contractsCompleted}"/>
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

            <a class="btn contract-control-details btn-link text-uppercase"
               onclick='openDetailsModal("${contractCard.jobContract.description}", "${encodedImage.type}",
                       "${encodedImage.string}")'>
                <i class="fa fa-clipboard-list mr-1" aria-hidden="true"></i>
                <p>Detalles</p>
            </a>

            <a class="btn contract-control-contact btn-link text-uppercase"
               onclick='openContactModal("${name}", "${email}", "${phone}")'>
                <i class="fa fa-info-circle mr-1" aria-hidden="true"></i>
                <p><spring:message code="mycontracts.contact"/></p>
            </a>

            <c:choose>
                <c:when test="${contractCard.review != null}">
                    <div style="color: #787878; margin: 0 5px;">
                        <hr class="divider-bar-thick">
                        <spring:message code="mycontracts.yourReview"/>
                        <div class="gray-chip">
                            <jsp:include page="components/rateStars.jsp">
                                <jsp:param name="rate" value="${contractCard.review.rate}"/>
                            </jsp:include>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:if test="${contractCard.jobCard.jobPost.active}">
                        <a class="contract-control-rate text-uppercase"
                           href="${pageContext.request.contextPath}/rate-contract/${contractCard.jobContract.id}">
                            <i class="bi bi-star mr-1"></i>
                            <p><spring:message code="mycontracts.ratecontract"/></p>
                        </a>
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
                    <div class="modal-body d-flex">
                        <%--                            TODO: Poner alt valido--%>
                        <img id="details-modal-image" src="">
                        <p id="details-modal-description"></p>

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

            function openDetailsModal(description, imageType, image) {
                const imageElem = $('#details-modal-image');
                const modalDialog = $('#details-modal-dialog');
                imageElem.attr('src', 'data:' + imageType + ';base64,' + image);
                if (image === "") {
                    imageElem.hide();
                    modalDialog.removeClass('modal-lg');
                } else {
                    imageElem.show();
                    modalDialog.addClass('modal-lg');
                }


                $('#details-modal-description').text(description);
                $('#details-modal').modal('show');
            }
        </script>

    </div>
</div>
