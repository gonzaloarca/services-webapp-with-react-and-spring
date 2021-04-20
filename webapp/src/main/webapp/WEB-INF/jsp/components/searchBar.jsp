<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<div class="home-banner-container">
    <%--@elvariable id="searchForm" type=""--%>
    <div class="landing-content-container">
        <div class="title-and-form">
            <h1 class="landing-title">Encuentre y contrate el servicio que necesita</h1>
            <form:form action="${pageContext.request.contextPath}/search" method="get"
                       modelAttribute="searchForm"
                       class="home-search-form"
                       acceptCharset="utf-8">
                <div class="search-inputs">
                    <div class="home-search-location">
                        <form:select path="zone" class="custom-select w-100">
                            <spring:message code="index.search.location.placeholder" var="locationPlaceholder"/>
                            <form:option value="" label="${locationPlaceholder}"/>
                            <c:forEach items="${zoneValues}" var="zone">
                                <form:option value="${zone.value}">
                                    <spring:message code="${zone.stringCode}"/>
                                </form:option>
                            </c:forEach>
                        </form:select>
                        <form:errors path="zone" cssClass="search-form-error" element="p"/>
                    </div>

                    <div class="home-search-bar-container home-search-bar-row">
                        <spring:message code="index.search.jobType.placeholder" var="typePlaceholder"/>
                        <form:input path="query" type="search" class="home-search-bar w-100 h-100"
                                    placeholder="${typePlaceholder}" maxlength="100"/>
                        <form:errors path="query" cssClass="search-form-error" element="p"/>
                    </div>
                    <button class="btn btn-warning btn-circle btn-l home-search-button home-search-bar-row">
                        <i class="fas fa-search"></i>
                    </button>
                </div>
            </form:form>
        </div>

        <div class="landing-instructions-container">
            <div class="landing-instruction-outer-border">
                <div class="landing-instruction-inner">
                    <h3>1</h3>
                    <%--                TODO: Alt valido--%>
                    <img src="<c:url value="/resources/images/location-1.svg"/>" alt="">
                    <p>Seleccione su ubicación</p>
                </div>
            </div>
            <div class="landing-instruction-outer-border">
                <div class="landing-instruction-inner">
                    <h3>2</h3>
                    <%--                TODO: Alt valido--%>
                    <img src="<c:url value="/resources/images/search1.svg"/>" alt="">
                    <p>Busque el servicio que necesita</p>
                </div>
            </div>
            <div class="landing-instruction-outer-border">
                <div class="landing-instruction-inner">
                    <h3>3</h3>
                    <%--                TODO: Alt valido--%>
                    <img src="<c:url value="/resources/images/hire-1.svg"/>" alt="">
                    <p>Contrate el servicio</p>
                </div>
            </div>
        </div>
    </div>


    <img class="home-banner-img" alt="<spring:message code="index.home.banner"/>"
         src='<c:url value="/resources/images/landingbg1.svg" />'/>
</div>
