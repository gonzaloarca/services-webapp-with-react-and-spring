<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/resources/css/customnavbar.css" rel="stylesheet"/>

</head>
<body>
<!-- Sacado de https://getbootstrap.com/docs/4.6/components/navbar/ -->
<nav class="navbar navbar-expand-lg navbar-dark">
    <a class="navbar-brand" href="/">
        <img src='<c:url value="../../resources/images/hirenet-logo-v3-invert.png" />' alt="HireNet Logo"/>
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item ${requestScope['javax.servlet.forward.request_uri'] == "/" ? 'active': ''}">
                <a class="nav-link" href="${pageContext.request.contextPath}/">Inicio</a>
            </li>
            <li class="nav-item ${requestScope['javax.servlet.forward.request_uri'] == "/create-job-post" ? 'active': ''}">
                <a class="nav-link" href="${pageContext.request.contextPath}/create-job-post">Publicar servicio</a>
            </li>
        </ul>
        <form class="form-inline my-2 my-lg-0">
            <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
            <button class="btn btn-outline-light my-2 my-sm-0" type="submit">Search</button>
        </form>
    </div>
</nav>

</body>
</html>
