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
        <img src="${pageContext.request.contextPath}/resources/images/hirenet-logo-nav-1.svg"
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
            <%--            <li class="nav-item ${requestScope.path == "/categories" ? 'active': ''}">--%>
            <%--                <a class="nav-link" href="${pageContext.request.contextPath}/categories">--%>
            <%--                    <spring:message code="navigation.categories"/>--%>
            <%--                </a>--%>
            <%--            </li>--%>

            <c:if test="${requestScope.path != '/' && requestScope.path != '/login' && requestScope.path != '/register'
            && requestScope.path != '/error'}">
                <%--@elvariable id="searchForm" type="ar.edu.itba.paw.webapp.form.SearchForm"--%>
                <form:form class="form-inline ml-4 my-auto flex-grow-1"
                           action="${pageContext.request.contextPath}/search"
                           method="get" modelAttribute="searchForm" acceptCharset="utf-8">
                    <spring:message code="navigation.search" var="queryPlaceholder"/>
                    <form:input class="form-control mr-sm-2" type="search" path="query"
                                placeholder="${queryPlaceholder}" aria-label="Search"/>
                    <button class="btn btn-outline-light my-2 my-sm-0" type="submit">
                        <spring:message code="navigation.search"/>
                    </button>
                    <a type="button" class="btn btn-link navbar-link-button ml-auto" data-toggle="modal"
                       data-target="#zonesModal">
                        <i class="fas fa-map-marker-alt fa-lg mr-2"></i>
                        <c:if test="${pickedZone == null}">
                            <spring:message code="navigation.picklocation"/>
                        </c:if>
                        <c:if test="${pickedZone != null}">
                            <spring:message code="${pickedZone.stringCode}"/>
                        </c:if>
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
                                        <h4 class="my-3">
                                            <spring:message code="navigation.modal.title"/>
                                        </h4>
                                        <div class="has-search">
                                            <span class="fa fa-search form-control-feedback"></span>
                                            <input id="locationFilter" type="text" class="form-control"
                                                   placeholder="<spring:message code="jobPost.create.zones.placeholder"/>"/>
                                        </div>
                                        <div class="navbar-location-list-group">
                                            <c:forEach items="${requestScope.zoneValues}" var="zone">
                                                <label class="navbar-location-list-group-item">
                                                    <form:radiobutton path="zone" class="form-check-input"
                                                                      value="${zone.value}"/>
                                                        <%--                                                TODO: CAMBIAR A CHECKBUTTON?--%>
                                                    <span class="location-name"><spring:message
                                                            code="${zone.stringCode}"/></span>
                                                </label>
                                            </c:forEach>
                                        </div>
                                    </div>
                                    <div class="d-flex">
                                        <button type="button" class="btn btn-danger ml-auto mr-4" data-dismiss="modal">
                                            <spring:message code="navigation.modal.close"/></button>
                                        <button class="btn btn-success" type="submit">
                                            <spring:message code="navigation.modal.confirm"/></button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </form:form>
            </c:if>
            <c:if test="${requestScope.path == '/' || requestScope.path == '/login' || requestScope.path == '/register'
            || requestScope.path == '/error'}">
                <span class="ml-auto"></span>
            </c:if>

            <sec:authorize access="isAnonymous()">
                <a type="button" class="btn btn-link navbar-link-button"
                   href="${pageContext.request.contextPath}/login"><spring:message
                        code="navigation.login"/></a>
                <a type="button" class="btn btn-light"
                   href="${pageContext.request.contextPath}/register"><spring:message
                        code="navigation.register"/></a>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <li class="nav-item ${requestScope.path == "/my-contracts" ? 'active': ''}">
                    <a class="nav-link"
                       href="${pageContext.request.contextPath}/my-contracts">
                        <spring:message code="navigation.mycontracts"/>
                    </a>
                </li>
                <button type="button" class="btn dropdown-toggle navbar-dropdown-toggle" data-toggle="dropdown"
                        aria-haspopup="true"
                        aria-expanded="false" id="navbarDropdown">
                    <c:choose>
                        <c:when test="${currentUser.image.string == null}">
                            <img class="navbar-user-img"
                                 src="${pageContext.request.contextPath}/resources/images/defaultavatar.svg"
                                 alt="avatar">
                        </c:when>
                        <c:otherwise>
                            <img class="navbar-user-img"
                                 src="data:${currentUser.image.type};base64,${currentUser.image.string}" alt="avatar">
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
                    <a class="dropdown-item mt-3" href="${pageContext.request.contextPath}/logout"><spring:message
                            code="navigation.dropdowon.logout"/></a>
                </div>
            </sec:authorize>
        </ul>
    </div>
</nav>
<script>
    // Para buscar una locacion
    $('#locationFilter').on('keyup', function () {
        const filter = $(this)[0].value.toUpperCase();
        const list = $('.navbar-location-list-group');
        const listElems = list[0].getElementsByTagName('label');

        // Iterar por la lista y esconder los elementos que no matcheen
        for (let i = 0; i < listElems.length; i++) {
            let a = listElems[i].getElementsByTagName("span")[0];
            let txtValue = a.textContent || a.innerText;
            if (txtValue.toUpperCase().indexOf(filter) > -1) {
                listElems[i].style.display = "";
            } else {
                listElems[i].style.display = "none";
            }
        }
    });
</script>
</body>
</html>