<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" buffer="64kb" %>

<%--Seteo de variable para verificar si el currentUser es el dueño del jobPost en el contract--%>
<c:set var="isOwner" value="${contractType == 'professional'}" scope="request"/>

<html>
<head>

    <title>
        <spring:message code="mycontracts.title" var="text"/>
        <spring:message code="title.name" arguments="${text}"/>
    </title>

    <%-- Bootstrap 4.5.2 CSS minified --%>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">

    <%-- jQuery 3.6.0 minified dependency for Bootstrap JS libraries --%>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"
            integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>

    <%-- Popper libraries minified for Bootstrap compatibility --%>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
            integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
            crossorigin="anonymous"></script>

    <%-- Bootstrap 4.5.2 JS libraries minified --%>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
            integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
            crossorigin="anonymous"></script>

    <%-- FontAwesome Icons--%>
    <script src="https://kit.fontawesome.com/108cc44da7.js" crossorigin="anonymous"></script>

    <!--  Bootstrap icons   -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css">

    <!-- Bootstrap Datepicker -->
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.0/moment.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.0/locale/es.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.39.0/js/tempusdominus-bootstrap-4.min.js"
            integrity="sha512-k6/Bkb8Fxf/c1Tkyl39yJwcOZ1P4cRrJu77p83zJjN2Z55prbFHxPs9vN7q3l3+tSMGPDdoH51AEU8Vgo1cgAA=="
            crossorigin="anonymous"></script>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.39.0/css/tempusdominus-bootstrap-4.min.css"
          integrity="sha512-3JRrEUwaCkFUBLK1N8HehwQgu8e23jTH4np5NHOmQOobuC4ROQxFwFgBLTnhcnQRMs84muMh0PnnwXlPq5MGjg=="
          crossorigin="anonymous"/>

    <link href="${pageContext.request.contextPath}/resources/css/styles.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/resources/css/mycontracts.css" rel="stylesheet"/>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/icon.svg">
    <link rel="apple-touch-icon" href="${pageContext.request.contextPath}/resources/images/apple-touch-icon.png">
</head>
<body>
<c:set var="path" value="/my-contracts" scope="request"/>
<c:set var="zoneValues" value="${zoneValues}" scope="request"/>
<%@include file="components/customNavBar.jsp" %>
<div class="content-container-transparent">
    <jsp:include page="components/siteHeader.jsp">
        <jsp:param name="code" value="mycontracts.title"/>
    </jsp:include>
    <div class="content-container-transparent pt-0">

        <div class="contracts-horizontal-tabs content-container">
            <hr class="divider-bar"/>
            <nav class="nav flex-row">
                <a class="nav-link nav-horizontal-option ${contractType == 'client'
                ? 'active active-yellow font-weight-bold' : ''} ${!isPro ? 'w-100' : ''}"
                   href="${pageContext.request.contextPath}/my-contracts/client/active">
                    <div class="contracts-option ${contractType == 'client'
                    ? 'font-weight-bold' : ''}">
                        <i class="fas fa-users fa-sm option-icon client-icon ml-0 mr-2"></i>
                        <spring:message code="contract.options.mine"/>
                    </div>
                </a>
                <c:if test="${isPro}">
                    <a class="nav-link nav-horizontal-option ${contractType == 'professional'
                    ? 'active active-blue font-weight-bold' : ''}"
                       href="${pageContext.request.contextPath}/my-contracts/professional/active">
                        <div class="contracts-option ${contractType == 'professional'
                    ? 'font-weight-bold' : ''}">
                            <i class="fas fa-user fa-sm option-icon pro-icon ml-0 mr-2"></i>
                            <spring:message code="contract.options.myServices"/>
                        </div>
                    </a>
                </c:if>

            </nav>
        </div>

        <div class="main-body">
            <div class="contracts-sections content-container">
                <jsp:include page="components/myContractsOptions.jsp">
                    <jsp:param name="contractState" value="${contractState}"/>
                    <jsp:param name="contractType" value="${contractType}"/>
                </jsp:include>
            </div>

            <div class="contracts-container content-container">
                <h4 class="my-4 ml-4 font-weight-bold">
                    <spring:message code="contract.options.${contractState}Contracts"/>
                </h4>
                <div class="mx-3">
                    <c:choose>
                        <c:when test="${contractCards.size() > 0}">
                            <c:forEach var="contractCardVar" items="${contractCards}" varStatus="status">
                                <c:set var="jobCard" value="${contractCardVar.jobCard}" scope="request"/>
                                <c:set var="contractState" value="${contractCardVar.jobContract.state}"/>
                                <c:set var="contractType" value="${contractType}"/>
                                <c:set var="contractCard" value="${contractCardVar}" scope="request"/>
                                <%@include file="components/contractCard.jsp" %>
                                <c:if test="${status.index != contractCards.size()-1}">
                                    <hr class="hr1"/>
                                </c:if>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${contractStateEndpoint == 'active' && !isOwner}">
                                    <c:set value="mycontracts.noActiveContractsHeader" var="noContractsHeader"/>
                                    <c:set value="mycontracts.noActiveContractsSubtitle" var="noContractsSubtitle"/>
                                </c:when>
                                <c:when test="${contractStateEndpoint == 'active' && isOwner}">
                                    <c:set value="mycontracts.noActiveContractsHeader" var="noContractsHeader"/>
                                    <c:set value="mycontracts.noActiveContractsSubtitlePro" var="noContractsSubtitle"/>
                                </c:when>
                                <c:when test="${contractStateEndpoint == 'pending' && !isOwner}">
                                    <c:set value="mycontracts.noPendingContractsHeader" var="noContractsHeader"/>
                                    <c:set value="mycontracts.noPendingContractsSubtitle" var="noContractsSubtitle"/>
                                </c:when>
                                <c:when test="${contractStateEndpoint == 'pending' && isOwner}">
                                    <c:set value="mycontracts.noPendingContractsHeader" var="noContractsHeader"/>
                                    <c:set value="mycontracts.noPendingContractsSubtitlePro" var="noContractsSubtitle"/>
                                </c:when>
                                <c:when test="${contractStateEndpoint == 'finalized'}">
                                    <c:set value="mycontracts.noFinalizedContractsHeader" var="noContractsHeader"/>
                                    <c:set value="mycontracts.noFinalizedContractsSubtitle" var="noContractsSubtitle"/>
                                </c:when>

                            </c:choose>
                            <div style="display: flex; align-items: center; flex-direction: column">
                                <img loading="lazy" style="height: 200px; width: 40%; margin: 30px 0"
                                     alt="<spring:message code="mycontracts.shakingHands"/>"
                                     src='<c:url value="/resources/images/contract1.svg"/>'/>
                                <h4 class="font-weight-bold">

                                    <spring:message code="${noContractsHeader}"/>
                                </h4>

                                <p><spring:message code="${noContractsSubtitle}"/></p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
                <c:set var="listSize" value="${contractCards.size()}" scope="request"/>
                <c:set var="maxPage" value="${maxPage}" scope="request"/>
                <c:set var="currentPages" value="${currentPages}" scope="request"/>
                <%@include file="components/bottomPaginationBar.jsp" %>
            </div>
        </div>
    </div>
</div>

<jsp:include page="components/footer.jsp"/>
<script>

    function openContactModal(name, email, phone) {
        $('#modalProfessionalName').text(name);
        $('#modalProfessionalEmail').text(email);
        $('#modalProfessionalPhone').text(phone);
        $('#contact-modal').modal('show');
    }

    function openDetailsModal(contractId) {
        const imageElem = $('#details-modal-image');
        const imageHeader = $('#details-modal-image-header');
        const imageContainer = $('#details-image-container');
        const descriptionContainer = $('#details-description-container');
        const modalDialog = $('#details-modal-dialog');
        const description = $('#details-description-text-' + contractId).text()
        const image = $('#image-source-' + contractId).text()

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

    function openRescheduleModal(contractId, dateString) {
        const dateInput = $("#date-input-" + contractId);
        dateInput.val(dateString);
        $('#reschedule-modal-' + contractId).modal('show');
    }

    function changeContractState(contractId, state, urlAppend) {
        let returnUrl = $('#return-url-' + contractId);
        let newState = $('#new-state-' + contractId);
        newState.val(state);
        returnUrl.val(returnUrl.val() + urlAppend);
    }

    function changeContractDate(contractId, state) {
        let returnUrl = $('#return-url-' + contractId);
        let newState = $('#new-state-' + contractId);
        let dateInput = $('#date-input-' + contractId);
        let dateHidden = $('#hidden-scheduled-date-' + contractId);

        dateHidden.val(dateInput.val());
        dateInput.prop('disabled', true);
        newState.val(state);
        returnUrl.val(returnUrl.val() + 'pending');
    }
</script>

</body>
</html>
