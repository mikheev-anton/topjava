<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<html>
<head>
    <title>Meals List</title>
</head>
<body>
<table border="1">

    <tr style="color: black">
        <td>ID</td>
        <td>Описание</td>
        <td>Калории</td>
        <td>Время</td>
        <td>Действия</td>
    </tr>
    <c:forEach items="${mealList}" var="meal">
        <c:set var="clin_date" value="${fn:replace(meal.dateTime,'T', ' ')}"/>
        <c:if test="${not meal.exceed}">
            <tr style="color:  green">
                <td>${meal.id}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td>${clin_date}</td>
                <td>
                    <a  href="?action=edit&id=${meal.id}">[Редактировать]</a>
                    <a href="?action=delete&id=${meal.id}">[Удалить]</a>
                </td>
            </tr>
        </c:if>
        <c:if test="${meal.exceed}">
            <tr style="color:red">
                <td>${meal.id}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td>${clin_date}</td>
                <td>
                    <a  href="?action=edit&id=${meal.id}">[Редактировать]</a>
                    <a href="?action=delete&id=${meal.id}">[Удалить]</a>
                </td>
            </tr>
        </c:if>
    </c:forEach>
</table>
<h3>Добавление еды</h3>
<div>
    <form method="post" action="?action=add">
        <p>
            <input type="text"
                   placeholder="Описание"
                   autofocus
                   required
                   name="desc">  <input type="number"
                                        placeholder="Калории"
                                        name="cal"
                                        required>
        </p>
        <p>
            <input type="datetime-local"
                   placeholder="Дата"
                   name="date"
                   required
            >
        </p>
        <p>
            <input value="Добавить" type="submit"> <input value="Отчистить" type="reset">
        </p>
    </form>
</div>

</body>
</html>
