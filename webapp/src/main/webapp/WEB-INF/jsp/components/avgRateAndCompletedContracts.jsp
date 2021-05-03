<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <style>
        .avgRate-subtitle {
            color: gray;
            margin: 0;
        }

        .completed-works-outline, .completed-works {
            border-radius: 50%;
        }

        .completed-works {
            width: 50px;
            height: 50px;
            line-height: 50px;
            font-size: 20px;
            color: white;
            text-align: center;
            font-weight: bold;
            background: #485696;
        }

        .completed-works-outline {
            width: 70px;
            height: 70px;
            background: #fcb839;
            display: flex;
            justify-content: center;
            align-items: center;
        }
    </style>
</head>

<body>
<div class="card custom-card">
    <div class="card-body">
        <p class="avgRate-subtitle"><spring:message code="highlight.reviews.average"/></p>
        <span class="custom-row rating align-items-center justify-content-center">
            <h1 class="mr-3">
                ${param.avgRate}
            </h1>
            <jsp:include page="rateStars.jsp">
                <jsp:param name="rate" value="${param.avgRate}"/>
            </jsp:include>
            <p class="ml-1 mb-0">
                (${param.totalReviewsSize})
            </p>
        </span>
    </div>
</div>
<div class="card custom-card mt-4">
    <div class="card-body">
        <div class="row align-items-center justify-content-center">
            <div class="completed-works-outline">
                <div class="completed-works">${param.totalContractsCompleted}</div>
            </div>
            <h5 class="mb-0 ml-3">
                <c:choose>
                    <c:when test="${param.totalContractsCompleted == 1}">
                        <spring:message code="highlight.completed.works.one.time"/>
                    </c:when>
                    <c:otherwise>
                        <spring:message code="highlight.completed.works"/>
                    </c:otherwise>
                </c:choose>
            </h5>
        </div>
    </div>
</div>
</body>
</html>
