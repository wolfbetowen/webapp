<%--
  Created by IntelliJ IDEA.
  User: alex
  Date: 22.11.2019
  Time: 2:29
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/currentDate.tld" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:bundle basename="local" prefix="label.">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script src="https://unpkg.com/gijgo@1.9.13/js/gijgo.min.js" type="text/javascript"></script>
    <link href="https://unpkg.com/gijgo@1.9.13/css/gijgo.min.css" rel="stylesheet" type="text/css"/>
    <head><title><fmt:message key="feedBack"/></title></head>
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="controller?command=main_page">
                    Trainings Center
                </a>
            </div>
            <ul class="nav navbar-nav">
                <c:if test="${user.type == null}">
                    <li>
                        <a href="controller?command=log_in_page">
                            <fmt:message key="login"/>
                        </a>
                    </li>
                    <li>
                        <a href="controller?command=registration_page">
                            <fmt:message key="signUp"/>
                        </a>
                    </li>
                </c:if>
                <li>
                    <a href="controller?command=trainings_page">
                        <fmt:message key="currentTrainings"/>
                    </a>
                    <c:if test="${user.type != null}">
                <li class="active">
                    <a href="controller?command=cabinet">
                        <fmt:message key="cabinet"/>
                    </a>
                </li>
                <li>
                    <a href="controller?command=log_out">
                        <fmt:message key="logout"/>
                    </a>
                </li>
                </c:if>
                </li>
            </ul>
            <form id="xxx" method="post" action="controller">
                <input type="hidden" name="command" value="set_local"/>
                <button form="xxx" name="local" value="${local == 'en' ? 'ru' : 'en'}"
                        class="btn-link" type="submit">
                        ${local == 'en' ? 'Ru' : 'En'}
                </button>
            </form>
        </div>
    </nav>
    <body>


    </body>
    </html>
</fmt:bundle>