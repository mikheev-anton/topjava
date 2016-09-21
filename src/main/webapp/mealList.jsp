<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<head>
    <title>Meal</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }
        div{
            display: inline-block;
        }
        .ram{
            position: absolute;
            margin-left: 20px;
        }
        .table{
            position: relative;
        }
        .btn{
            margin-top: 20px;
        }
    </style>
</head>
<body>
<p>
    <h2><a href="index.html">Home</a></h2>
    <h3>Meal of ${userN}</h3>

    <a href="meals?action=create">Add Meal</a>
    <hr>
<div class="table">
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${mealList}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.to.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</div>

<div class="ram">
    <form method="post" action="meals?action=filter">
        <h4>Фильтровать по дням</h4>
        <div>
            <label for="startDate">От: </label>
            <div >
                <input class="left" type="date" value="${requestScope.get("dateS")}" name="startDate" id="startDate">
            </div>
            <label  for="endDate">До: </label>
            <div >
                <input type="date" value="${requestScope.get("dateE")}" name="endDate" id="endDate">
            </div>
        </div>
        <h4>Фильтровать по времени</h4>
        <div>
            <label for="startTime">От: </label>
            <div>
                <input class="left" type="time" value="${requestScope.get("timeS")}" name="startTime" id="startTime">
            </div>

            <label for="endTime">До: </label>
            <div>
                <input type="time" value="${requestScope.get("timeE")}" name="endTime" id="endTime">
            </div>
        </div>
        <p class="btn">
            <p >
                <button  type="submit">Отфильтровать</button>
            </p>
        </p>
    </form>
    <p class="btn">
        <form style="bottom: 100px" method="get" action="meals">
            <input type="submit" value="Сброс поиска">
        </form>
    </p>
</div>

</body>
</html>