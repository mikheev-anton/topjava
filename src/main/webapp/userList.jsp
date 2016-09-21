<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User list</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>User list</h2>
<table border="1" cellpadding="8" cellspacing="0">
<tr>
    <th>id</th>
    <th>Name</th>
    <th>Email</th>
    <th>Password</th>
</tr>
<c:forEach items="${userList}" var="user">
    <jsp:useBean id="user" scope="page" type="ru.javawebinar.topjava.model.User"/>
    <tr>
        <td>${user.id}</td>
        <td>${user.name}</td>
        <td>${user.email}</td>
        <td>${user.password}</td>
    </tr>
</c:forEach>
</table>
</body>
</html>
