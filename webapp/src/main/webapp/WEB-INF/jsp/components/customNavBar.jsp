<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/resources/css/customnavbar.css" rel="stylesheet"/>
</head>
<body>
<!-- Sacado de https://getbootstrap.com/docs/4.6/components/navbar/ -->
<nav class="navbar navbar-expand-lg navbar-dark ${requestScope.withoutColor? 'transparent-navbar':'navbar-color'}">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/">
        <img style="height: 90%" src="${pageContext.request.contextPath}/resources/images/hirenet-logo-nav-1.svg"
             alt="<spring:message code="navigation.logo"/>"/>
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav w-100 align-items-center">
            <li class="nav-item ${requestScope.path == "/" ? 'active': ''}">
                <a class="nav-link" href="${pageContext.request.contextPath}/">
                    <spring:message code="navigation.index"/>
                </a>
            </li>
            <li class="nav-item ${requestScope.path == "/create-job-post" ? 'active': ''}">
                <a class="nav-link" href="${pageContext.request.contextPath}/create-job-post">
                    <spring:message code="navigation.publish"/>
                </a>
            </li>
            <li class="nav-item ${requestScope.path == "/categories" ? 'active': ''}">
                <a class="nav-link" href="${pageContext.request.contextPath}/categories">
                    <spring:message code="navigation.categories"/>
                </a>
            </li>

            <sec:authorize access="isAuthenticated()">
                <li class="nav-item ${requestScope.path == "/my-contracts/client" || "/my-contracts/professional" ? 'active': ''}">
                    <a class="nav-link"
                       href="${pageContext.request.contextPath}/my-contracts/client/active">
                        <spring:message code="navigation.mycontracts"/>
                    </a>
                </li>
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_PROFESSIONAL')">
                <li class="nav-item ${requestScope.path == "/analytics" ? 'active': ''}">
                    <a class="nav-link"
                       href="${pageContext.request.contextPath}/analytics">
                        <spring:message code="navigation.analytics"/>
                    </a>
                </li>
            </sec:authorize>

            <script>let search = false;</script>
            <c:if test="${requestScope.path != '/' && requestScope.path != '/login' && requestScope.path != '/register'
            && requestScope.path != '/error'}">
                <script>search = true;</script>
                <%--@elvariable id="searchForm" type="ar.edu.itba.paw.webapp.form.SearchForm"--%>
                <form:form class="form-inline ml-4 my-auto flex-grow-1 justify-content-between" id="searchNavForm"
                           action="${pageContext.request.contextPath}/search" novalidate="true"
                           method="get" modelAttribute="searchForm" acceptCharset="utf-8">

                    <spring:message code="navigation.search" var="queryPlaceholder"/>

                    <div style="position: relative; width: 45%">
                        <form:input maxlength="100" class="form-control mr-sm-2 w-100" type="search" path="query"
                                    placeholder="${queryPlaceholder}" aria-label="Search" id="search-input"
                                    required="true" onkeyup="hideNavErrorMsg()"/>
                        <button class="" type="submit" id="search-button">
                            <i class="fas fa-search"></i>
                        </button>
                        <p class="search-form-error" id="queryNavError" style="display: none">
                            <spring:message code="search.query.invalid"/>
                        </p>
                    </div>

                    <form:errors path="query" cssClass="search-form-error" cssStyle="top: 50px" element="p"/>

                    <a type="button" class="btn btn-link navbar-link-button ml-auto" data-toggle="modal"
                       data-target="#zonesModal">
                        <i class="fas fa-map-marker-alt fa-lg mr-2"></i>
                        <span id="zoneString"></span>
                        <form:errors path="zone" cssClass="search-form-error" element="p"/>
                    </a>

                    <%--Modal de locaciones--%>
                    <div class="modal fade" tabindex="-1" id="zonesModal" aria-labelledby="zonesModal"
                         aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered">
                            <div class="modal-content">
                                <div class="modal-body">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                    <div class="navbar-modal-container">
                                        <img class="navbar-modal-icon"
                                             src="${pageContext.request.contextPath}/resources/images/location2.svg"
                                             alt="<spring:message code="navigation.modal.locationicon"/>"/>
                                        <h5 class="my-4 font-weight-bold">
                                            <spring:message code="navigation.modal.title"/>
                                        </h5>
                                        <div class="navbar-has-search">
                                            <span class="fa fa-search navbar-form-control-feedback"></span>
                                            <input id="locationFilter" type="text"
                                                   class="form-control navbar-form-control"
                                                   placeholder="<spring:message code="jobPost.create.zones.placeholder"/>"/>
                                        </div>
                                        <div class="list-group navbar-location-list-group">
                                            <div id="no-results-location-modal"
                                                 class="p-4" >
                                                <p class="text-black-50">
                                                    <spring:message code="navigation.modal.noResults"/>
                                                </p>
                                            </div>
                                            <c:forEach items="${requestScope.zoneValues}" var="zone">
                                                <label class="list-group-item navbar-location-list-group-item navbar-modal-zone">
                                                    <form:radiobutton path="zone"
                                                                      value="${zone.value}"/>
                                                    <span class="location-name ml-2"><spring:message
                                                            code="${zone.stringCode}"/></span>
                                                </label>
                                            </c:forEach>
                                        </div>
                                    </div>
                                    <div class="d-flex">
                                        <button type="button" class="btn btn-danger ml-auto mr-2 text-uppercase"
                                                data-dismiss="modal">
                                            <spring:message code="navigation.modal.close"/></button>
                                        <button class="btn btn-success text-uppercase" id="pickLocationButton"
                                                type="submit">
                                            <spring:message code="navigation.modal.confirm"/></button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <form:hidden path="category" id="categoryForm"/>
                </form:form>
            </c:if>
            <c:if test="${requestScope.path == '/' || requestScope.path == '/login' || requestScope.path == '/register'
            || requestScope.path == '/error'}">
                <span class="ml-auto"></span>
            </c:if>

            <div class="vl"></div>

            <sec:authorize access="isAnonymous()">
                <a type="button" class="btn btn-link navbar-link-button"
                   href="${pageContext.request.contextPath}/login"><spring:message
                        code="navigation.login"/></a>
                <a type="button" class="btn btn-light"
                   href="${pageContext.request.contextPath}/register"><spring:message
                        code="navigation.register"/></a>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <button type="button" class="btn dropdown-toggle navbar-dropdown-toggle" data-toggle="dropdown"
                        aria-haspopup="true"
                        aria-expanded="false" id="navbarDropdown">
                    <c:choose>
                        <c:when test="${currentUser.image.string == null}">
                            <img class="navbar-user-img"
                                 src="${pageContext.request.contextPath}/resources/images/defaultavatar.svg"
                                 alt="avatar" id="navbar-avatar">
                        </c:when>
                        <c:otherwise>
                            <img class="navbar-user-img"
                                 src="data:${currentUser.image.type};base64,${currentUser.image.string}" alt="avatar"
                                 id="navbar-avatar">
                        </c:otherwise>
                    </c:choose>
                </button>
                <div class="dropdown-menu navbar-dropdown" aria-labelledby="navbarDropdown">
                    <div class="navbar-dropdown-details">
                        <c:choose>
                            <c:when test="${currentUser.image.string == null}">
                                <img class="navbar-user-img"
                                     src="${pageContext.request.contextPath}/resources/images/defaultavatar.svg"
                                     alt="avatar">
                            </c:when>
                            <c:otherwise>
                                <img class="navbar-user-img"
                                     src="data:${currentUser.image.type};base64,${currentUser.image.string}"
                                     alt="avatar">
                            </c:otherwise>
                        </c:choose>
                        <div>
                            <p class="navbar-dropdown-name">
                                <c:out value="${currentUser.username}"/>
                            </p>
                            <p class="navbar-dropdown-email">
                                <c:out value="${currentUser.email}"/>
                            </p>
                        </div>
                    </div>
                    <sec:authorize access="hasRole('ROLE_PROFESSIONAL')">
                        <a class="dropdown-item"
                           href="${pageContext.request.contextPath}/profile/${currentUser.id}/services">
                            <i class="fas fa-user navbar-dropdown-icon"></i><spring:message
                                code="navigation.dropdowon.myprofile"/></a>
                    </sec:authorize>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/account/details">
                        <i class="fas fa-user-cog navbar-dropdown-icon"></i><spring:message
                            code="navigation.dropdowon.myaccount"/></a>
                    <a class="dropdown-item mt-1" href="${pageContext.request.contextPath}/logout">
                        <div class="ml-2"><spring:message
                                code="navigation.dropdowon.logout"/></div>
                    </a>
                </div>
            </sec:authorize>
        </ul>
    </div>
</nav>
<script>
    // Para buscar una ubicación
    const noResultDivChild= $('#no-results-location-modal *');
    const noResultDiv= $('#no-results-location-modal');
    noResultDiv.hide();
    noResultDivChild.hide();


    $('#locationFilter').on('keyup', function () {
        const filter = $(this)[0].value.toUpperCase();
        const list = $('.navbar-location-list-group');
        const listElems = list[0].getElementsByTagName('label');
        let isNotEmpty = false;

        noResultDiv.hide();
        noResultDivChild.hide();

        // Iterar por la lista y esconder los elementos que no matcheen
        for (let i = 0; i < listElems.length; i++) {
            let a = listElems[i].getElementsByTagName("span")[0];
            let txtValue = a.textContent || a.innerText;
            if (txtValue.toUpperCase().startsWith(filter.trim())) {
                listElems[i].style.display = "";
                isNotEmpty = true;
            } else {
                listElems[i].style.display = "none";
            }
        }

        if (!isNotEmpty) {
            noResultDiv.show();
            noResultDivChild.show();
        }
    });

    let zonePicked = false;
    // Cuando se hace el submit guardo las cookies
    $('#pickLocationButton').on('click', function () {
        const zonesInput = $('.navbar-location-list-group')[0].getElementsByTagName('input');
        const zonesString = $('.navbar-location-list-group')[0].getElementsByTagName('span');
        let found = false;
        for (let i = 0; i < zonesInput.length && !found; i++) {
            if (zonesInput[i].checked) {
                sessionStorage.setItem("pickedZoneId", i);
                sessionStorage.setItem("pickedZoneString", zonesString[i].innerText);
                found = true;
                zonePicked = true;
                $('#zoneString')[0].innerText = zonesString[i].innerText;
                break;
            }
        }

        $('#zonesModal').modal('hide');
    })

    $('.navbar-location-list-group-item').on('click', function () {
        $('#pickLocationButton').attr("disabled", false);
    })

    // Para levantar la ubicacion en las cookies, en caso de existir
    if (search) {
        let auxZoneString = sessionStorage.getItem("pickedZoneString");
        if (auxZoneString) {
            $('#zoneString')[0].innerText = auxZoneString;
            $('#zone' + (parseInt(sessionStorage.getItem('pickedZoneId')) + 1)).prop("checked", true);
            zonePicked = true;
        } else {
            $('#zoneString')[0].innerText = '<spring:message code="navigation.picklocation"/>'
            $('#pickLocationButton').attr("disabled", true);
        }

        let searchForm = document.querySelector('#searchNavForm');
        searchForm.addEventListener('submit', function (event) {
            let querySearch = $('#search-input')[0];

            if (!zonePicked) {
                $('#zonesModal').modal('show');
                querySearch.setCustomValidity("error");
                event.preventDefault();
                event.stopPropagation();
                return false;
            } else {
                querySearch.setCustomValidity("");
            }

            // if (querySearch.value === "") {
            //     $('#queryNavError')[0].style.display = 'inherit';
            //     querySearch.setCustomValidity("error");
            //     event.preventDefault();
            //     event.stopPropagation();
            //     return false;
            // } else {
            //     $('#queryNavError')[0].style.display = 'none';
            //     querySearch.setCustomValidity("");
            // }

            $("#search-button").attr("disabled", true);
        })
    }

    function hideNavErrorMsg() {
        $('#queryNavError')[0].style.display = 'none';
    }

</script>
</body>
</html>