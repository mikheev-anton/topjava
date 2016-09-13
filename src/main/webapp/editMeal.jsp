<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit meal</title>
</head>
<body>
<div>
    <style>
        input{
            width: 180px;
            display: inline-block;
        }

        label {
            width: 100px;
            display: inline-block;
        }
    </style>
    <c:set var="me" value="${mealForEdit}"/>
    <form method="post" action="?action=edit">
        <p>
            <label>Описание: </label><input type="text"
                                            placeholder="Описание"
                                            name="desc"
                                            required
                                            value="${me.description}">
        </p>
        <p>
            <label>Калории: </label><input type="number"
                                           placeholder="Калории"
                                           name="cal"
                                           value="${me.calories}"
                                           required>
        </p>
        <p>
            <label>Дата: </label><input type="datetime-local"
                                        placeholder="dd.MM.yyyy'T'HH:mm"
                                        required
                                        name="date"
                                        value="${me.dateTime}">
        </p>
        <p>
            <input value="Исправить" type="submit">
        </p>
    </form>
</div>
</body>
</html>
