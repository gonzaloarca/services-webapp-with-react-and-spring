<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<span class="custom-row rating align-items-center">
    <c:forEach var="i" begin="1" end="5">
        <%--  > 0.8 se rendondea para arriba, < 0.2 para abajo, el resto queda en 0.5--%>
        <c:choose>
            <c:when test="${i <= param.rate + 0.2}">
                <i class="bi bi-star-fill star"></i>
            </c:when>
            <c:when test="${i-param.rate <= 0.8 && i-param.rate >= 0.2}">
                <i class="bi bi-star-half star"></i>
            </c:when>
            <c:otherwise>
                <i class="bi bi-star star"></i>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</span>
