<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Contact - HireNet</title>    <!-- TODO: poner titulo correcto -->
    <link href="${pageContext.request.contextPath}/resources/css/styles.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/resources/css/createcontract.css" rel="stylesheet"/>
    <link rel="shortcut icon" href="#">

    <!-- Bootstrap 4.5.2 CSS minified -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <!-- jQuery 3.6.0 minified dependency for Bootstrap JS libraries -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"
            integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <!-- Bootstrap 4.5.2 JS libraries minified -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
            integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
            crossorigin="anonymous"></script>
    <script src="https://kit.fontawesome.com/108cc44da7.js" crossorigin="anonymous"></script>
</head>
<body class="body">
    <%@ include file="customNavBar.jsp" %>
    <div class="container">
        <div class="row">
            <p class="navigation-text">Inicio / Fontaneria</p>
        </div>
        <div class="row top-row">
                <div class="header-container">
                    <a class="header-back-icon">
                        <i class="fas fa-arrow-left fa-2x"></i>
                    </a>
                    <h2 class="header-title">Solicitar Servicio</h2>
                </div>
                <!--TODO: alt posta -->
                <img src="${postImage}" alt="" class="header-img"/>
        </div>
        <div class="row bottom-row">
            <div class="col-7 first-col">
                <h2 class="contract-form">FORMULARIO</h2>
            </div>
            <div class="col-5 second-col">
                <h2 class="job-info">DETALLE</h2>
            </div>
        </div>
    </div>
</body>
</html>
