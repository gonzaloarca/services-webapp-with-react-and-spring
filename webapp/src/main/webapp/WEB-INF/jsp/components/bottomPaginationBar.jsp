<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="page" value="${param.page != null ? param.page : 1}"/>
<c:if test="${requestScope.listSize > 0 && requestScope.maxPage > 1}">
    <nav aria-label="...">
        <ul class="pagination justify-content-center">
            <li class='page-item ${page == 1 ? "disabled": "" }'>
                <a class="page-link" href="${pageContext.request.contextPath}?page=${page - 1}${requestScope.parameters}" tabindex="-1"><spring:message code="pagination.previous"/></a>
            </li>
            <c:forEach items="${requestScope.currentPages}" var="pageIndex">
                <c:choose>
                    <c:when test="${page == pageIndex}">
                        <li class="page-item active">
                            <span class="page-link">${pageIndex} <span class="sr-only">(<spring:message code="pagination.current"/>)</span></span>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item">
                            <a class="page-link" href="${pageContext.request.contextPath}?page=${pageIndex}${requestScope.parameters}">${pageIndex}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <li class='page-item ${page == requestScope.maxPage ? "disabled": "" }'>
                <a class="page-link" href="${pageContext.request.contextPath}?page=${page + 1}${requestScope.parameters}"><spring:message code="pagination.next"/></a>
            </li>
        </ul>
    </nav>
</c:if>
