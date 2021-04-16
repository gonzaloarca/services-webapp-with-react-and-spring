<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: franq
  Date: 15/4/2021
  Time: 21:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
    <form:form modelAttribute="registerForm" action="${pageContext.request.contextPath}/register" method="post">
        <label>Email</label>
        <form:input path="email" type="text" />
        <label>Password</label>
        <form:input path="password" type="password" />
        <label>Username</label>
        <form:input path="username" type="text" />
        <label>Phone</label>
        <form:input path="phone" type="text" />
        <button type="submit">Registrarse</button>
    </form:form>

</body>
</html>
