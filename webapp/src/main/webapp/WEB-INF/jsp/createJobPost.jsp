<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>
        <spring:message code="jobPost.create.title" var="text"/>
        <spring:message code="title.name" arguments="${text}"/>
    </title>
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

    <!-- FontAwesome 5 -->
    <script src="https://kit.fontawesome.com/108cc44da7.js" crossorigin="anonymous"></script>

    <link href="${pageContext.request.contextPath}/resources/css/styles.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/resources/css/createjobpost.css" rel="stylesheet"/>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/icon.svg">
    <link rel="apple-touch-icon" href="${pageContext.request.contextPath}/resources/images/apple-touch-icon.png">

</head>
<body>
<c:set var="path" value="/create-job-post" scope="request"/>
<c:set var="zoneValues" value="${zoneValues}" scope="request"/>
<%@include file="components/customNavBar.jsp" %>
<div class="content-container-transparent">
    <jsp:include page="components/siteHeader.jsp">
        <jsp:param name="code" value="jobPost.create.title"/>
    </jsp:include>
    <div class="content-container">
        <c:url value="/create-job-post" var="postPath"/>
        <form:form modelAttribute="createJobPostForm" action="${postPath}" method="post" class="create-job-post-form">
            <div class="form-header">
                <h3>
                    <spring:message code="jobPost.create.service"/>
                </h3>
                <p>
                    <spring:message code="jobPost.create.required"/>
                </p>
                <hr class="hr1 form-hr">
                <svg xmlns="http://www.w3.org/2000/svg"
                     xmlns:xlink="http://www.w3.org/1999/xlink" width="680"
                     height="676" viewBox="0 0 680 676">
                    <image width="680" height="676"
                           xlink:href="data:img/png;base64,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"></image>
                </svg>
            </div>

            <div class="form-step">
                <div class="blue-circle">
                    <h4 class="circle-text">1</h4>
                </div>
                <div class="form-step-input">
                    <form:label path="jobType" class="header-label"
                                for="serviceTypeSelect">
                        <spring:message code="jobPost.create.service.select"/>
                    </form:label>
                    <spring:message code="jobPost.create.service.type" var="serviceType"/>
                    <form:select path="jobType" class="form-control w-75" id="serviceTypeSelect">
                        <form:option value="" label="${serviceType}"/>
                        <c:forEach items="${jobTypes}" var="type">
                            <form:option value="${type.value}">
                                <spring:message code="${type.stringCode}"/>
                            </form:option>
                        </c:forEach>
                    </form:select>
                    <form:errors path="jobType" class="form-error" element="p"/>
                </div>
            </div>

            <div class="form-step">
                <div class="yellow-circle">
                    <h4 class="circle-text">2</h4>
                </div>
                <div class="form-step-input">
                    <form:label path="title" for="jobTitle"
                                class="header-label">
                        <spring:message code="jobPost.create.publication.title"/>
                    </form:label>
                    <spring:message code="jobPost.create.publication.placeholder" var="titlePlaceholder"/>
                    <form:input path="title" id="jobTitle" type="text" class="form-control"
                                placeholder="${titlePlaceholder}" maxlength="100"/>
                    <form:errors path="title" class="form-error" element="p"/>
                </div>
            </div>

            <div class="form-step" style="width: 100%; margin-bottom: 0">
                <div class="orange-circle">
                    <h4 class="circle-text">3</h4>
                </div>
                <div class="form-step-input" style="width: 100%">
                    <label class="header-label">
                        <spring:message code="jobPost.create.package.required"/>
                    </label>
                    <hr class="hr1" style="margin-top: 0">
                </div>
            </div>

            <div class="package-container" id="navTabsContent">
                <div class="package-header">
                    <i class="fas fa-cube mr-3"></i>
                    <spring:message code="jobPost.create.package.header"/>
                </div>
                <div id="navPackage1" class="package-form">
                    <div class="package-input">
                        <form:label path="jobPackage.title" for="packageTitle1">
                            <spring:message code="jobPost.create.package.title"/>
                        </form:label>
                        <spring:message code="jobPost.create.package.title" var="pTitlePlaceholder"/>
                        <form:input path="jobPackage.title" id="packageTitle1" type="text" class="form-control"
                                    placeholder="${pTitlePlaceholder}" maxlength="100"/>
                        <form:errors path="jobPackage.title" class="form-error" element="p"/>
                    </div>
                    <div class="package-input">
                        <form:label path="jobPackage.description"
                                    for="packageDescription1">
                            <spring:message code="jobPost.create.package.description"/>
                        </form:label>
                        <spring:message code="jobPost.create.package.descriptionPlaceholder"
                                        var="descriptionPlaceholder"/>
                        <form:textarea path="jobPackage.description" id="packageDescription1" class="form-control"
                                       placeholder="${descriptionPlaceholder}" maxlength="100"
                                       rows="3"/>
                        <form:errors path="jobPackage.description" class="form-error" element="p"/>
                    </div>

                    <div class="package-input">
                        <form:label path="jobPackage.rateType"
                                    style="display: block; margin-bottom: 15px">
                            <spring:message code="jobPost.create.package.rateType"/>
                        </form:label>
                        <div class="center">
                            <div class="form-check form-check-inline">
                                <form:radiobutton path="jobPackage.rateType" id="hourlyRadio1"
                                                  class="form-check-input" name="inlineRadioOptions"
                                                  value="0"/>
                                <form:label path="jobPackage.rateType" for="hourlyRadio1"
                                            class="form-check-label">
                                    <spring:message code="jobPost.create.package.hourly"/>
                                </form:label>
                            </div>
                            <div class="form-check form-check-inline">
                                <form:radiobutton path="jobPackage.rateType" id="oneTimeRadio1"
                                                  class="form-check-input" name="inlineRadioOptions"
                                                  value="1"/>
                                <form:label path="jobPackage.rateType" for="oneTimeRadio1"
                                            class="form-check-label">
                                    <spring:message code="jobPost.create.package.oneTime"/>
                                </form:label>
                            </div>
                            <div class="form-check form-check-inline">
                                <form:radiobutton path="jobPackage.rateType" id="tbdRadio1"
                                                  class="form-check-input"
                                                  name="inlineRadioOptions"
                                                  value="2"/>
                                <form:label path="jobPackage.rateType" for="tbdRadio1"
                                            class="form-check-label">
                                    <spring:message code="jobPost.create.package.tbd"/>
                                </form:label>
                            </div>
                        </div>
                        <form:errors path="jobPackage.rateType" class="form-error" element="p"/>
                    </div>
                        <%-- TODO: Deshabilitar si es "A acordar"--%>
                    <div class="package-input">
                        <form:label path="jobPackage.price" for="packagePrice1">
                            <spring:message code="jobPost.create.package.price"/>
                        </form:label>
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text">
                                    <spring:message code="jobPost.create.package.argentinePeso"/>
                                </span>
                            </div>
                            <spring:message code="jobPost.create.package.price" var="pricePlaceholder"/>
                            <form:input path="jobPackage.price" id="packagePrice1" type="number" step="any"
                                        class="form-control" min="0" max="99999999999"
                                        placeholder="${pricePlaceholder}"/>
                        </div>
                        <form:errors path="jobPackage" class="form-error" element="p"/>
                    </div>

                </div>
                    <%--                TODO: Proximo sprint: multiples paquetes--%>
                    <%--                <c:forEach begin="2" end="5" var="i">--%>
                    <%--                    <div id="packageCont${i}" style="display: none">--%>
                    <%--                        <div class="package-header">--%>
                    <%--                            <i class="fas fa-cube"></i>--%>
                    <%--                            <span class="ml-5">Paquete</span>--%>
                    <%--                        </div>--%>
                    <%--                        <div id="navPackage${i}" class="package-form">--%>
                    <%--                            <div class="package-input">--%>
                    <%--                                <form:label path="packages[${i-1}].title"--%>
                    <%--                                            for="packageTitle${i}">Nombre del paquete*</form:label>--%>
                    <%--                                <form:input path="packages[${i-1}].title" id="packageTitle${i}" type="text"--%>
                    <%--                                            class="form-control"--%>
                    <%--                                            placeholder="Nombre del paquete"/>--%>
                    <%--                                <form:errors path="packages[${i-1}].title" class="form-error" element="p"/>--%>
                    <%--                            </div>--%>
                    <%--                            <div class="package-input">--%>
                    <%--                                <form:label path="packages[${i-1}].description"--%>
                    <%--                                            for="packageDescription${i}">Descripción del paquete*</form:label>--%>
                    <%--                                <form:textarea path="packages[${i-1}].description" id="packageDescription${i}"--%>
                    <%--                                               class="form-control"--%>
                    <%--                                               placeholder="Descripción del paquete"--%>
                    <%--                                               rows="3"/>--%>
                    <%--                                <form:errors path="packages[${i-1}].description" class="form-error" element="p"/>--%>
                    <%--                            </div>--%>

                    <%--                            <div class="package-input">--%>
                    <%--                                <form:label path="packages[${i-1}].rateType"--%>
                    <%--                                            style="display: block; margin-bottom: 15px">¿Cómo cobrará el paquete?*</form:label>--%>
                    <%--                                <div class="center">--%>
                    <%--                                    <div class="form-check form-check-inline">--%>
                    <%--                                        <form:radiobutton path="packages[${i-1}].rateType" id="hourlyRadio${i}"--%>
                    <%--                                                          class="form-check-input" name="inlineRadioOptions"--%>
                    <%--                                                          value="0"/>--%>
                    <%--                                        <form:label path="packages[${i-1}].rateType" for="hourlyRadio${i}"--%>
                    <%--                                                    class="form-check-label">Por hora</form:label>--%>
                    <%--                                    </div>--%>
                    <%--                                    <div class="form-check form-check-inline">--%>
                    <%--                                        <form:radiobutton path="packages[${i-1}].rateType" id="oneTimeRadio${i}"--%>
                    <%--                                                          class="form-check-input" name="inlineRadioOptions"--%>
                    <%--                                                          value="1"/>--%>
                    <%--                                        <form:label path="packages[${i-1}].rateType" for="oneTimeRadio${i}"--%>
                    <%--                                                    class="form-check-label">Por trabajo puntual</form:label>--%>
                    <%--                                    </div>--%>
                    <%--                                    <div class="form-check form-check-inline">--%>
                    <%--                                        <form:radiobutton path="packages[${i-1}].rateType" id="tbdRadio${i}"--%>
                    <%--                                                          class="form-check-input"--%>
                    <%--                                                          name="inlineRadioOptions"--%>
                    <%--                                                          value="2"/>--%>
                    <%--                                        <form:label path="packages[${i-1}].rateType" for="tbdRadio${i}"--%>
                    <%--                                                    class="form-check-label">A acordar con el cliente</form:label>--%>
                    <%--                                    </div>--%>
                    <%--                                </div>--%>
                    <%--                                <form:errors path="packages[${i-1}].rateType" class="form-error" element="p"/>--%>
                    <%--                            </div>--%>
                    <%--                            <div class="package-input">--%>
                    <%--                                <form:label path="packages[${i-1}].price" for="packagePrice${i}">Precio*</form:label>--%>
                    <%--                                <div class="input-group mb-3">--%>
                    <%--                                    <div class="input-group-prepend">--%>
                    <%--                                        <span class="input-group-text">ARS</span>--%>
                    <%--                                    </div>--%>
                    <%--                                    <form:input path="packages[${i-1}].price" id="packagePrice${i}" type="number"--%>
                    <%--                                                class="form-control"--%>
                    <%--                                                placeholder="Precio"/>--%>
                    <%--                                </div>--%>
                    <%--                                <form:errors path="packages[${i-1}]" class="form-error" element="p"/>--%>
                    <%--                            </div>--%>
                    <%--
                                                    <%--                            <button id="deletePackage${i}" type="button" class="btn btn-block btn-danger btn-lg">--%>
                    <%--                                <i class="fas fa-trash-alt"></i> ELIMINAR PAQUETE--%>
                    <%--                            </button>--%>
                    <%--                        </div>--%>
                    <%--                    </div>--%>
                    <%--                </c:forEach>--%>
            </div>

            <%--            <button id="addPackage" type="button" class="btn btn-block btn-light btn-lg text-uppercase"--%>
            <%--                    style="margin-top: 20px">--%>
            <%--                <span class="badge badge-primary">+</span>--%>
            <%--                <span style="font-size: 16px; font-weight: bold">Añadir otro paquete</span>--%>
            <%--            </button>--%>

            <hr class="hr1" style="margin: 30px 0">

            <%--            TODO: PROXIMO SPRINT--%>
            <%--            &lt;%&ndash;TODO: Implementar file browser con JS/Java&ndash;%&gt;--%>
            <%--            <div class="form-step">--%>
            <%--                <div class="blue-circle">--%>
            <%--                    <h4 class="circle-text">4</h4>--%>
            <%--                </div>--%>
            <%--                <div class="form-step-input">--%>
            <%--                    <form:label path="servicePics"--%>
            <%--                                class="header-label">Seleccione al menos 1 imagen para su servicio*</form:label>--%>
            <%--                    <div class="custom-file">--%>
            <%--                        <form:input path="servicePics" type="file" class="custom-file-input" id="jobImageInput"--%>
            <%--                                    multiple="multiple"/>--%>
            <%--                        <form:label path="servicePics" class="custom-file-label"--%>
            <%--                                    for="jobImageInput">Seleccionar archivo(s)</form:label>--%>
            <%--                        <small class="form-text text-muted">Restricciones de archivo</small>--%>
            <%--                        <form:errors path="servicePics" class="form-error" element="p"/>--%>
            <%--                    </div>--%>
            <%--                </div>--%>
            <%--            </div>--%>

            <div class="form-step">
                <div class="blue-circle">
                    <h4 class="circle-text">4</h4>
                </div>
                <div class="form-step-input">
                    <form:label path="availableHours" for="availableHoursInput"
                                class="header-label">
                        <spring:message code="jobPost.create.availableHours"/>
                    </form:label>
                    <spring:message code="jobPost.create.availableHours.placeholder" var="hoursPlaceholder"/>
                    <form:textarea path="availableHours" id="availableHoursInput" class="form-control"
                                   placeholder="${hoursPlaceholder}"
                                   rows="5" maxlength="100"/>
                    <form:errors path="availableHours" class="form-error" element="p"/>

                </div>
            </div>

            <div class="form-step">
                <div class="yellow-circle">
                    <h4 class="circle-text">5</h4>
                </div>
                <div class="form-step-input">
                    <form:label path="zones"
                                class="header-label">
                        <spring:message code="jobPost.create.zones"/>
                    </form:label>
                        <%--                    TODO: Filtrado de ubicaciones para proximo sprint--%>
                        <%--                    <div class="form-group has-search">--%>
                        <%--                        <span class="fa fa-search form-control-feedback"></span>--%>
                        <%--                        <input type="text" class="form-control"--%>
                        <%--                               placeholder="<spring:message code="jobPost.create.zones.placeholder"/>"/>--%>
                        <%--                    </div>--%>
                    <form:errors path="zones" class="form-error" element="p"/>
                    <div class="list-group location-list">
                        <c:forEach items="${zoneValues}" var="zone">
                            <label class="list-group-item">
                                <form:checkbox path="zones" class="form-check-input" value="${zone.value}"/>
                                <span class="location-name">
                                    <spring:message code="${zone.stringCode}"/>
                                </span>
                            </label>
                        </c:forEach>
                    </div>
                </div>
            </div>

            <%--            TODO: PROXIMO SPRINT--%>
            <%--            <div class="form-step">--%>
            <%--                <div class="blue-circle">--%>
            <%--                    <h4 class="circle-text">4</h4>--%>
            <%--                </div>--%>
            <%--                <div class="form-step-input mb-5">--%>
            <%--                    <label class="header-label">Seleccione una imagen de perfil</label>--%>
            <%--                    <div class="custom-file">--%>
            <%--                        <form:input path="profilePic" type="file" class="custom-file-input" id="profilePic"--%>
            <%--                                    multiple="multiple"/>--%>
            <%--                        <form:label path="profilePic" class="custom-file-label  w-100"--%>
            <%--                                    for="profilePic">Seleccionar archivo</form:label>--%>
            <%--                        <small class="form-text text-muted">Restricciones de archivo</small>--%>
            <%--                        <form:errors path="profilePic" class="form-error" element="p"/>--%>
            <%--                    </div>--%>
            <%--                </div>--%>
            <%--            </div>--%>
            <button type="submit" class="btn btn-primary btn-lg btn-block text-uppercase w-50 mx-auto ">
                <spring:message code="jobPost.create.submit"/>
            </button>
        </form:form>
    </div>
</div>
<jsp:include page="components/footer.jsp"/>


<script>
    const tbdRadio = $("#tbdRadio1");

    tbdRadio.on('click', function () {
        $("#packagePrice1").prop('readonly', true).val('');
    })

    if (tbdRadio.is(':checked')) {
        $("#packagePrice1").prop('readonly', true);
    }

    $("#oneTimeRadio1").on('click', function () {
        $("#packagePrice1").prop('readonly', false);
    });

    $("#hourlyRadio1").on('click', function () {
        $("#packagePrice1").prop('readonly', false);
    });
    //TODO: Proximo sprint paquetes dinmaicos
    // let packageCount = 1;
    //
    // for (let i = 1; i <= 5; i++) {
    //     $("#tbdRadio" + i).on('click', function () {
    //         $("#packagePrice" + i).prop('readonly', true);
    //     });
    //
    //     $("#oneTimeRadio" + i).on('click', function () {
    //         $("#packagePrice" + i).prop('readonly', false);
    //     });
    //
    //     $("#hourlyRadio" + i).on('click', function () {
    //         $("#packagePrice" + i).prop('readonly', false);
    //     });
    //
    //     // if (i >= 2) {
    //     //     $("#deletePackage" + packageCount).on('click', function () {
    //     //         $("#packageCont" + packageCount).css("display", "none");
    //     //         //vaciar los inputs
    //     //         packageCount--;
    //     //     })
    //     // }
    // }
    //
    // $("#addPackage").on('click', function () {
    //     if (packageCount < 5) {
    //         packageCount++;
    //         $("#packageCont" + packageCount).css("display", "block");
    //     }
    //     if (packageCount === 5)
    //         $("#packageCont" + packageCount).prop('disabled', true);
    // });


</script>

</body>
</html>
