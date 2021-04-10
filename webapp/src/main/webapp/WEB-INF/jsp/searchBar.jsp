<div class="home-banner-container">
    <%--@elvariable id="searchForm" type=""--%>
    <form:form action="${pageContext.request.contextPath}/search" method="get"
               modelAttribute="searchForm"
               class="home-search-form"
               acceptCharset="utf-8" >
        <div class="search-instructions">
            <div class="search-instruction-step">
                <div class="blue-circle">
                    <p class="circle-text">1</p>
                </div>
                <p class="search-instructions-text">
                    <spring:message code="index.search.location"/>
                </p>
            </div>
            <div class="search-instruction-step">
                <div class="blue-circle">
                    <p class="circle-text">2</p>
                </div>
                <p class="search-instructions-text">
                    <spring:message code="index.search.jobType"/>
                </p>
            </div>
            <div class="search-instruction-step">
                <div class="blue-circle">
                    <p class="circle-text">3</p>
                </div>
                <p class="search-instructions-text">
                    <spring:message code="index.search.submit"/>
                </p>
            </div>
        </div>

        <div class="search-inputs">
            <div class="home-search-location">
                <form:select path="zone" class="custom-select w-100">
                    <spring:message code="index.search.location.placeholder" var="locationPlaceholder"/>
                    <form:option value="" label="${locationPlaceholder}"/>
                    <c:forEach items="${zones}" var="zone">
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
                            placeholder="${typePlaceholder}" />
                <form:errors path="query" cssClass="search-form-error" element="p"/>
            </div>

            <button class="btn btn-warning btn-circle btn-l home-search-button home-search-bar-row">
                <i class="fas fa-search"></i>
            </button>
        </div>
    </form:form>

    <img class="home-banner-img" alt="<spring:message code="index.home.banner"/>"
         src='<c:url value="/resources/images/banner1.jpg" />'/>
</div>
