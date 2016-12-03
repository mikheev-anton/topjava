
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<li class="dropdown">
    <a href="#" class="dropdown-toggle" data-toggle="dropdown">${pageContext.response.locale}<b class="caret"></b></a>
    <ul class="dropdown-menu">
        <li><a onclick="show('en')">English</a></li>
        <li><a onclick="show('ru')">Русский</a></li>
    </ul>
</li>
<script type="text/javascript">
    var localeCode="${pageContext.response.locale}";
    function show(lang) {
        window.location.href = window.location.href.split('?')[0] + '?lang=' + lang
    }
</script>
