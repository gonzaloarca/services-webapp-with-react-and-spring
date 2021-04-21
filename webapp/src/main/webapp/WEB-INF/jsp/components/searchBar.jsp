<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<div class="home-banner-container">
    <%--@elvariable id="searchForm" type=""--%>
    <div class="landing-content-container">
        <div class="title-and-form">
            <h1 class="landing-title"><spring:message code="navigation.index"/></h1>
            <form:form action="${pageContext.request.contextPath}/search" method="get"
                       modelAttribute="searchForm"
                       class="home-search-form"
                       acceptCharset="utf-8">
                <div class="search-inputs">
                    <div class="home-search-location">
                        <form:select path="zone" class="custom-select w-100" id="homeSelect">
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
                    <button class="btn btn-warning btn-circle btn-l home-search-button home-search-bar-row" id="homeSearchButton">
                        <i class="fas fa-search"></i>
                    </button>
                </div>
                <form:hidden path="category" id="categoryForm"/>
            </form:form>
        </div>

        <div class="landing-instructions-container">
            <div class="landing-instruction-outer-border">
                <div class="landing-instruction-inner">
                    <h3>1</h3>
                    <img src="<c:url value="/resources/images/location-1.svg"/>"
                         alt="<spring:message code="index.search.location.icon"/>">
                    <p><spring:message code="index.search.location"/></p>
                </div>
            </div>
            <div class="landing-instruction-outer-border">
                <div class="landing-instruction-inner">
                    <h3>2</h3>
                    <img src="<c:url value="/resources/images/search1.svg"/>"
                         alt="<spring:message code="index.search.service.icon"/>">
                    <p><spring:message code="index.search.service"/></p>
                </div>
            </div>
            <div class="landing-instruction-outer-border">
                <div class="landing-instruction-inner">
                    <h3>3</h3>
                    <img src="<c:url value="/resources/images/hire-1.svg"/>"
                         alt="<spring:message code="index.search.contract.icon"/>">
                    <p><spring:message code="index.search.contract"/></p>
                </div>
            </div>
        </div>
    </div>
    <img class="home-banner-img" alt="<spring:message code="index.home.banner"/>"
         src='<c:url value="/resources/images/landingbg1.svg" />'/>
</div>
<script>
    // Cuando se hace el submit guardo las cookies
    var homeSelect = $('#homeSelect');
    $('#homeSearchButton').on('click', function () {
        sessionStorage.setItem("pickedZoneId", homeSelect[0].value);
        sessionStorage.setItem("pickedZoneString", homeSelect[0].selectedOptions[0].label);
    })

    // Para levantar la ubicacion en las cookies, en caso de existir
    var auxZoneId = sessionStorage.getItem("pickedZoneId");
    if (auxZoneId) {
        homeSelect[0].selectedIndex = parseInt(auxZoneId)+1;
    }
    // Para levantar, en caso de existir, la categoria seleccionada y meterla al form
    var auxCategoryId = sessionStorage.getItem("pickedCategoryId");
    if (auxCategoryId) {
        $('#categoryForm')[0].value = auxCategoryId;
    }
</script>
