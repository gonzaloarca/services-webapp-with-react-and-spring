<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cotacto enviado</title>
    <link href="${pageContext.request.contextPath}/resources/css/styles.css" rel="stylesheet"/>

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
<body>
    <%@ include file="customNavBar.jsp" %>
    <!-- TODO: definir cómo va a ser esta página -->
    <div class="content-container-transparent page">
        <h1 class="message">¡Información Confirmada!</h1>
    </div>
</body>
</html>

<style>
.page {
    display: flex;
    height: 80%;
}

.message {
    margin: auto;
    padding: 50px;
    background-color: white;
    box-shadow: 0 3px 6px rgba(0, 0, 0, 0.16);
    text-align: center;
}
</style>
