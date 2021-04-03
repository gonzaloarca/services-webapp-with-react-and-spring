<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
        <div class="container" style="width: 60%">

            <!-- Navigation -->
            <div class="row">
                <!-- TODO: hacer la navegación posta -->
                <p class="navigation-text">Inicio / Fontaneria</p>
            </div>

            <!-- Title Header -->
            <div class="row top-row">
                    <div class="header-container">
                        <!--div class="header-back-button">
                            <a class="header-back-icon">
                                <i class="fas fa-arrow-left fa-2x"></i>
                            </a>
                        </div-->
                        <h2 class="header-title">Solicitar Servicio</h2>
                    </div>
                    <!--TODO: alt posta -->
                    <img src="${postImage}" alt="" class="header-img"/>
            </div>

            <div class="row bottom-row">

                <!-- Contract Form -->
                <div class="first-col">
                    <div class="contract-form">
                        <!-- Title -->
                        <h3 style="font-weight: bold">Datos de Contacto</h3>
                        <p style="margin: 0">* campo obligatorio</p>
                        <hr class="divider-bar"/>
                        <!-- Form Entries -->
                        <form:form class="contract-input" modelAttribute="contractForm"
                                    action="/contract/package/${packId}" method="post"
                                   enctype="multipart/form-data">

                            <!-- Name -->
                            <div class="form-row">
                                <div class="blue-circle">
                                    <p class="circle-text">1</p>
                                </div>
                                <div class="col-10 label-and-input">
                                    <form:label path="name" class="form-text">
                                        Introduzca su nombre*
                                    </form:label>
                                    <form:input type="text" path="name"
                                                class="form-control text-input"
                                                placeholder="Nombre"/>
                                    <form:errors path="name" cssClass="form-error" element="p"/>
                                </div>
                            </div>

                            <!-- Email -->
                            <div class="form-row">
                                <div class="yellow-circle">
                                    <p class="circle-text">2</p>
                                </div>
                                <div class="col-10 label-and-input">
                                    <form:label path="email" class="form-text">
                                        Introduzca su dirección de correo electrónico*
                                    </form:label>
                                    <form:input type="email" class="form-control text-input" path="email"
                                           placeholder="Dirección de correo electrónico"/>
                                    <form:errors path="email" cssClass="form-error" element="p"/>
                                </div>
                            </div>

                            <!-- Phone Number -->
                            <div class="form-row">
                                <div class="orange-circle">
                                    <p class="circle-text">3</p>
                                </div>
                                <div class="col-7 label-and-input">
                                    <form:label path="phone" class="form-text">
                                        Introduzca su número de teléfono*
                                    </form:label>
                                    <form:input type="text" class="form-control text-input" path="phone"
                                           placeholder="Número de teléfono"/>
                                    <form:errors path="phone" cssClass="form-error" element="p"/>
                                </div>
                            </div>

                            <!-- Description -->
                            <div class="form-row">
                                <div class="blue-circle">
                                    <p class="circle-text">4</p>
                                </div>
                                <div class="col-10 label-and-input">
                                    <form:label path="description" class="form-text">
                                        Introduzca una descripción del trabajo que precisa*
                                    </form:label>
                                    <form:textarea class="form-control text-input" rows="6" path="description"
                                              placeholder="Descripción del trabajo" />
                                    <form:errors path="description" cssClass="form-error" element="p"/>
                                </div>
                            </div>

                            <!-- Image -->
                            <div class="form-row">
                                <div class="yellow-circle">
                                    <p class="circle-text">5</p>
                                </div>
                                <div class="col-10 label-and-input">
                                    <form:label path="image">Seleccione archivo para subir</form:label>
                                    <input type="file" name="image" />
                                </div>
                            </div>

                            <!-- Submit Button -->
                            <div class="submit-button">
                                <button class="btn btn-primary" type="submit">SOLICITAR SERVICIO</button>
                            </div>

                        </form:form>
                    </div>
                </div>

                <!-- Job Detail -->
                <div class="second-col">
                    <div class="job-info">
                        <h5 class="info-title">Detalles del Servicio</h5>
                        <!-- TODO: alt posta -->
                        <img src="${postImage}" alt="" class="info-img"/>
                        <div class="container">
                            <!-- Job Type -->
                            <div class="row info-row">
                                <div class="info-left-col">
                                    <i class="fas fa-briefcase fa-2x"></i>
                                </div>
                                <p class="info-right-col">
                                    Servicio de Fontanería Basico (instalacion fdasfasdfasdfadsfasf)
                                </p>
                            </div>
                            <hr class="divider-bar-info"/>
                            <!-- Package Name -->
                            <div class="row info-row">
                                <div class="info-left-col">
                                    <i class="fas fa-box-open fa-2x"></i>
                                </div>
                                <p class="info-right-col">
                                    Destape de retrete
                                </p>
                            </div>
                            <hr class="divider-bar-info"/>
                            <!-- Location -->
                            <div class="row info-row">
                                <div class="info-left-col">
                                    <i class="fas fa-map-marker-alt fa-2x"></i>
                                </div>
                                <p class="info-right-col">
                                    Recoleta
                                </p>
                            </div>
                            <hr class="divider-bar-info"/>
                            <!-- Professional -->
                            <div class="row info-row">
                                <div class="info-left-col">
                                    <!-- TODO: cambiar este icono por imagen de perfil -->
                                    <i class="fas fa-user fa-2x"></i>
                                </div>
                                <p class="info-right-col">
                                    Rodrigo
                                </p>
                            </div>
                            <hr class="divider-bar-info"/>
                            <!-- Hours -->
                            <div class="row info-row">
                                <div class="info-left-col">
                                    <i class="far fa-clock fa-2x"></i>
                                </div>
                                <p class="info-right-col">
                                    Lunes - Jueves 9:00hs a 18:00hs
                                    Viernes - Sábados 10:00hs a 15:00hs
                                </p>
                            </div>
                            <hr class="divider-bar-info"/>
                            <!-- Price -->
                            <div class="row info-row">
                                <div class="info-left-col">
                                    <i class="fas fa-dollar-sign fa-2x"></i>
                                </div>
                                <div class="info-right-col">
                                    <p class="price-tag">
                                        $69/Hora
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>

            </div>
        </div>
</body>
</html>
