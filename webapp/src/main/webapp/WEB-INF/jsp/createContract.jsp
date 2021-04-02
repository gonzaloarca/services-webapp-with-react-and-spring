<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

        <!-- Navigation -->
        <div class="row">
            <!-- TODO: hacer la navegación posta -->
            <p class="navigation-text">Inicio / Fontaneria</p>
        </div>

        <!-- Title Header -->
        <div class="row top-row">
                <div class="header-container">
                    <div class="header-back-button">
                        <a class="header-back-icon">
                            <i class="fas fa-arrow-left fa-2x"></i>
                        </a>
                    </div>
                    <h2 class="header-title">Solicitar Servicio</h2>
                </div>
                <!--TODO: alt posta -->
                <img src="${postImage}" alt="" class="header-img"/>
        </div>

        <div class="row bottom-row">

            <!-- Contract Form -->
            <div class="col-7 first-col">
                <div class="contract-form">
                    <!-- Title -->
                    <h3 style="font-weight: bold">Datos de Contacto</h3>
                    <p style="margin: 0">* campo obligatorio</p>
                    <hr class="divider-bar"/>
                    <!-- Form Entries -->
                    <form class="contract-input">

                        <!-- Name -->
                        <div class="form-row">
                            <div class="blue-circle">
                                <p class="circle-text">1</p>
                            </div>
                            <div class="col-10 label-and-input">
                                <label for="nameInput" class="form-text">
                                    Introduzca su nombre*
                                </label>
                                <input type="text" class="form-control text-input" id="nameInput"
                                       placeholder="Nombre" required/>
                            </div>
                        </div>

                        <!-- Email -->
                        <div class="form-row">
                            <div class="yellow-circle">
                                <p class="circle-text">2</p>
                            </div>
                            <div class="col-10 label-and-input">
                                <label for="emailInput" class="form-text">
                                    Introduzca su dirección de correo electrónico*
                                </label>
                                <input type="email" class="form-control text-input" id="emailInput"
                                       placeholder="Dirección de correo electrónico" required/>
                            </div>
                        </div>

                        <!-- Phone Number -->
                        <div class="form-row">
                            <div class="orange-circle">
                                <p class="circle-text">3</p>
                            </div>
                            <div class="col-7 label-and-input">
                                <label for="phoneInput" class="form-text">
                                    Introduzca su número de teléfono*
                                </label>
                                <input type="text" class="form-control text-input" id="phoneInput"
                                       placeholder="Número de teléfono" required/>
                            </div>
                        </div>

                        <!-- Description -->
                        <div class="form-row">
                            <div class="blue-circle">
                                <p class="circle-text">4</p>
                            </div>
                            <div class="col-10 label-and-input">
                                <label for="descInput" class="form-text">
                                    Introduzca una descripción del trabajo que precisa*
                                </label>
                                <textarea class="form-control text-input" id="descInput" rows="6"
                                          placeholder="Descripción del trabajo" required></textarea>
                            </div>
                        </div>

                        <!-- Image -->
                        <div class="form-row">
                            <div class="yellow-circle">
                                <p class="circle-text">5</p>
                            </div>
                            <div class="col-10 label-and-input">
                                <label for="imageInput" class="form-text">
                                    Seleccione una imagen de interés para el trabajo
                                </label>
                                <div class="custom-file">
                                    <!-- TODO: encontrar cómo anda el sistema de archivos -->
                                    <input type="file" class="custom-file-input" id="imageInput">
                                    <label class="custom-file-label" for="imageInput">Seleccionar archivo</label>
                                </div>
                            </div>
                        </div>

                        <!-- Submit Button -->
                        <div class="submit-button">
                            <button class="btn btn-primary" type="submit">SOLICITAR SERVICIO</button>
                        </div>

                    </form>
                </div>
            </div>

            <!-- Job Detail -->
            <div class="col-5 second-col">
                <h2 class="job-info">DETALLE</h2>
            </div>

        </div>
    </div>
</body>
</html>
