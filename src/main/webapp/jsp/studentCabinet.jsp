<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="ru" scope="session"/>
<fmt:bundle basename="local" prefix="label.">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <html><head><title>Student cabinet</title></head>
    </head>
    <body>
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="controller?command=main_page">Trainings Center</a>
            </div>
            <ul class="nav navbar-nav">
                <li class="active"><a href="controller?command=cabinet"><fmt:message key="cabinet"/></a></li>
                <li><a href="controller?command=trainings_page"><fmt:message key="currentTrainings"/></a></li>
                <li><a href="controller?command=log_out"><fmt:message key="logout"/></a></li>
            </ul>
        </div>
    </nav>
    <br/>
    <fmt:message key="hello" var="hello"/>
    <c:set var="user" value="${sessionScope.user}"/>
    <c:out value=" ${hello} ${user.surname} ${user.name}"/>
    <br/>
    <jsp:useBean id="gettrainings" class="com.epam.webapp.service.TrainingsService"/>
    <jsp:useBean id="getCompletedTrinings" class="com.epam.webapp.service.TrainingsService"/>
    <c:set var="count" value="1" scope="page"/>
    <div class="container">
        <h1><fmt:message key="studentstrainings"/></h1>
        <table class="table">
            <thead>
            <tr>
                <th>No</th>
                <th><fmt:message key="name"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="training" items="${gettrainings.getCompletedTrainingForStudent(user.id)}">
                <c:choose>
                    <c:when test="${training.grade == 0 }">
                        <tr>
                            <td>${count}</td>
                            <td>${training.name}</td>
                        </tr>
                        <c:set var="count" value="${count + 1}" scope="page"/>
                    </c:when>
                </c:choose>
            </c:forEach>
            <c:if test="${count == 1 }">
                <tr>
                    <td>
                        <fmt:message key="noEnrolledTraining"/>
                    </td>
                </tr>
            </c:if>
            </tbody>
        </table>
    </div>

    <div class="container">
        <c:set var="count" value="1"/>
        <h1><fmt:message key="completedTraining"/></h1>
        <table class="table">
            <thead>
            <tr>
                <th>No</th>
                <th><fmt:message key="name"/></th>
                <th><fmt:message key="assessment"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="training" items="${gettrainings.getCompletedTrainingForStudent(user.id)}">
                <c:choose>
                    <c:when test="${training.grade > 0 }">
                        <tr>
                            <td>${count}"</td>
                            <td>${training.name}</td>
                            <td>${training.grade}</td>
                        </tr>
                        <c:set var="count" value="${count + 1}" scope="page"/>
                    </c:when>
                </c:choose>
            </c:forEach>
            <c:if test="${count == 1 }">
                <tr>
                    <td>
                        <fmt:message key="noCompletedTraining"/>
                    </td>
                </tr>
            </c:if>
            </tbody>
        </table>
    </div>

    <br/>
    <c:import url="footer.jsp"/></body>
    </html>
</fmt:bundle>