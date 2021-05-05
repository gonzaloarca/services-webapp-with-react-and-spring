<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<div class="home-banner-container">
    <%--@elvariable id="searchForm" type=""--%>
    <div class="landing-content-container">
        <div class="title-and-form">
            <h1 class="landing-title"><spring:message code="index.searchBar.title"/></h1>
            <form:form action="${pageContext.request.contextPath}/search" method="get"
                       modelAttribute="searchForm" id="search-form"
                       class="home-search-form" novalidate="true"
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
                        <p class="search-form-error" id="zoneError" style="display: none">
                            <spring:message code="search.zones.invalid"/>
                        </p>
                    </div>

                    <div class="home-search-bar-container home-search-bar-row">
                        <spring:message code="index.search.jobType.placeholder" var="typePlaceholder"/>
                        <form:input path="query" type="search" class="home-search-bar w-100 h-100" id="queryInput"
                                    placeholder="${typePlaceholder}" maxlength="100" required="true" onkeyup="hideErrorMsg()"/>
                        <p class="search-form-error" id="queryError" style="display: none">
                            <spring:message code="search.query.invalid"/>
                        </p>
                        <form:errors path="query" cssClass="search-form-error" element="p"/>
                    </div>
                    <button type="submit" class="btn btn-warning btn-circle btn-l home-search-button home-search-bar-row"
                            id="submitBtn">
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

    let homeSelect = $('#homeSelect')[0];
    // Para levantar la ubicacion en las cookies, en caso de existir
    const auxZoneId = sessionStorage.getItem("pickedZoneId");
    if (auxZoneId) {
        homeSelect.selectedIndex = parseInt(auxZoneId) + 1;
    }
    // Cuando se hace el submit chequeo que se haya seleccionado una zona y de ser asi guardo las cookies
    let form = document.querySelector('#search-form');
    form.addEventListener('submit', function (event) {
        if(homeSelect.value === "")  {
            $('#zoneError')[0].style.display = 'inherit';
            homeSelect.setCustomValidity("error");
            event.preventDefault();
            event.stopPropagation();
        }
        else {
            $('#zoneError')[0].style.display = 'none';
            homeSelect.setCustomValidity("");
            sessionStorage.setItem("pickedZoneId", homeSelect.value);
            sessionStorage.setItem("pickedZoneString", homeSelect.selectedOptions[0].label);
        }

        let querySearch = $('#queryInput');
        if(querySearch[0].value === "") {
            $('#queryError')[0].style.display = 'inherit';
            querySearch[0].setCustomValidity("error");
            event.preventDefault();
            event.stopPropagation();
        } else {
            $('#queryError')[0].style.display = 'none';
            querySearch[0].setCustomValidity("");
        }

        $("#submitBtn").attr("disabled", is_valid);
    })

    // Saco el mensaje de error si es que existia
    $('.home-search-location').on('click', function () {
        $('#zoneError')[0].style.display = 'none';
    })

    function hideErrorMsg() {
        $('#queryError')[0].style.display = 'none';
    }
</script>
