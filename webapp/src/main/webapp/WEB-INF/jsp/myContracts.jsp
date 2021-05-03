<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<%@ page buffer="256kb" %>
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
    <div class="content-container">
        <c:choose>
            <c:when test="${contractCards.size() > 0}">
                <c:forEach var="contractCard" items="${contractCards}" varStatus="status">
                    <c:set var="data" value="${contractCard.jobCard}" scope="request"/>
                    <div class="row">
                        <div class="contract-service mr-4">
                            <%@include file="components/serviceCard.jsp" %>
                        </div>
                        <div class="contract-buttons-card">
                            <spring:message htmlEscape="true" code="mycontracts.contact.name"
                                            arguments="${contractCard.jobCard.jobPost.user.username}" var="name"/>
                            <spring:message htmlEscape="true" code="mycontracts.contact.email"
                                            arguments="${contractCard.jobCard.jobPost.user.email}" var="email"/>
                            <spring:message htmlEscape="true" code="mycontracts.contact.phone"
                                            arguments="${contractCard.jobCard.jobPost.user.phone}" var="phone"/>
                            <a class="contract-contact-text"
                               onclick='openContactModal("${name}", "${email}", "${phone}")'>
                                <h5 class="mb-3">
                                    <i class="fa fa-info-circle" aria-hidden="true"></i>
                                    <spring:message code="mycontracts.contact"/>
                                </h5>
                            </a>
                            <c:choose>
                                <c:when test="${contractCard.review != null}">
                                    <h7><spring:message code="mycontracts.yourReview"/></h7>
                                    <div class="gray-chip">
                                        <jsp:include page="components/rateStars.jsp">
                                            <jsp:param name="rate" value="${contractCard.review.rate}"/>
                                        </jsp:include>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <a class="contract-review-text"
                                       href="${pageContext.request.contextPath}/rate-contract/${contractCard.jobContract.id}">
                                        <h4 class="mb-0">
                                            <i class="bi bi-star"></i>
                                            <spring:message code="mycontracts.qualifycontract"/>
                                        </h4>
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <c:if test="${status.index != contractCards.size()-1}">
                        <hr class="hr1"/>
                    </c:if>
                </c:forEach>
            </c:when>
            <c:otherwise>
<%--                TODO: Poner alt valido--%>
                <div style="display: flex; height: 50%; align-items: center; flex-direction: column">
                    <img style="height: 60%; width: 60%; margin: 30px 0" src='<c:url value="/resources/images/contract1.svg"/>'/>
                    <h4 class="font-weight-bold">No hay contratos para mostrar</h4>
                    <p>Para tener contratos, primero solicite algún servicio</p>
                </div>
            </c:otherwise>
        </c:choose>
        <c:set var="listSize" value="${contractCards.size()}" scope="request"/>
        <c:set var="maxPage" value="${maxPage}" scope="request"/>
        <c:set var="currentPages" value="${currentPages}" scope="request"/>
        <%@include file="components/bottomPaginationBar.jsp" %>
    </div>
</div>

<jsp:include page="components/footer.jsp"/>

<%--Modal de contacto--%>
<div class="modal fade" tabindex="-1" id="modal" aria-labelledby="modal" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title">
                    <spring:message code="mycontracts.contact.title"/></h3>
            </div>
            <div class="modal-body">
                <h5 id="modalProfessionalName"></h5>
                <h5 id="modalProfessionalEmail"></h5>
                <h5 id="modalProfessionalPhone"></h5>
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
        $('#modal').modal('show');
    }
</script>
</body>
</html>
