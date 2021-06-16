<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<style>
    .footer-css p {
        margin-bottom: 4px;
        text-align: left;
    }

    .title {
        font-size: 1rem;
        font-weight: bold;
    }

    .hirenet-copyright {
        text-align: center !important;
        color: #7e7e7e;
        margin: 4vh 0 !important;
        font-size: 0.8rem;
    }
</style>

<footer class="d-flex justify-content-center" style="margin-top: 5vh">
    <div class="footer-css">
        <div class="row mb-3"  style="font-size: 0.9rem">
            <div class="col-8">
                <p class="title">
                    <spring:message code="footer.creators"/>
                </p>
                <div class="row">
                    <div class="col">
                        <p>Gonzalo Arca</p>
                        <p>Manuel Félix Parma</p>
                    </div>
                    <div class="col">
                        <p>Francisco Quesada</p>
                        <p>Manuel Joaquín Rodriguez</p>
                    </div>
                </div>
            </div>
            <div class="col-4">
                <p class="title">
                    <spring:message code="footer.contact"/>
                </p>
                <a href="mailto: paw.hirenet@gmail.com">paw.hirenet@gmail.com</a>
            </div>
        </div>
        <div>
            <p class="hirenet-copyright">© 2021 HireNet</p>
        </div>
    </div>
</footer>
