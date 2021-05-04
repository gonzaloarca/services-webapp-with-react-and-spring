<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<%@ page buffer="128kb" %>
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
    <div class="content-container-transparent pt-0">

        <div class="main-body">
            <div class="contracts-sections content-container">
                <jsp:include page="components/myContractsOptions.jsp">
                    <jsp:param name="selected" value="${contractType}"/>
                </jsp:include>
            </div>

            <div class="contracts-container content-container">
                <c:choose>
                    <c:when test="${contractCards.size() > 0}">
                        <c:forEach var="contractCard" items="${contractCards}" varStatus="status">
                            <c:set var="data" value="${contractCard.jobCard}" scope="request"/>
                            <%@include file="components/contractCard.jsp" %>
                            <c:if test="${status.index != contractCards.size()-1}">
                                <hr class="hr1"/>
                            </c:if>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <%--                TODO: Poner alt valido--%>
                        <div style="display: flex; align-items: center; flex-direction: column">
                            <img style="height: 200px; width: 40%; margin: 30px 0"
                                 src='<c:url value="/resources/images/contract1.svg"/>'/>
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
    </div>
</div>

<jsp:include page="components/footer.jsp"/>


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
