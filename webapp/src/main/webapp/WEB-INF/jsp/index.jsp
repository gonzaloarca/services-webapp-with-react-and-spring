<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/resources/css/styles.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/resources/css/index.css" rel="stylesheet"/>
    <link rel="shortcut icon" href="#">
</head>
<body>
<%@ include file="customNavBar.jsp" %>
<img class="homeBanner" src='<c:url value="${pageContext.request.contextPath}/resources/images/banner1.jpg" />'/>
</body>
</html>