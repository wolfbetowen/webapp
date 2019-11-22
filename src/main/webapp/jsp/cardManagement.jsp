<%--
  Created by IntelliJ IDEA.
  User: alex
  Date: 19.11.2019
  Time: 14:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:bundle basename="local" prefix="label.">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <html>
    <head>
        <title><fmt:message key="cardManagement"/></title>
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
                </li>
                <li><a href="controller?command=log_out"><fmt:message key="logout"/></a></li>
            </ul>
            <form id="xxx" method="post" action="controller">
                <input type="hidden" name="command" value="set_local"/>
                <input type="hidden" name="redirectTo" value="true"/>
                <button form="xxx" name="local" value="${local == 'en' ? 'ru' : 'en'}"
                        class="btn-link" type="submit">
                        ${local == 'en' ? 'Ru' : 'En'}
                </button>
            </form>
        </div>
    </nav>
    <br/>

    <jsp:useBean id="cardService" class="com.epam.webapp.service.impl.PaymentCardServiceImpl"/>

    <div class="container-fluid">


        <c:if test="${messageOperation != null}">
            <div class="alert alert-danger" role="alert">
                    ${messageOperation}
                <c:set var="messageOperation" value="${null}"/>
            </div>
        </c:if>


        <c:if test="${editor == null}">
            <div class="container">
                <h2><fmt:message key="yoursCards"/></h2>
                <table class="table">
                    <c:set var="count" value="${1}"/>
                    <thead>
                    <tr>
                        <th>No</th>
                        <th><fmt:message key="number"/></th>
                        <th><fmt:message key="score"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="card" items="${cardService.findUsersCard(user.id)}">
                        <tr>
                            <td><a href="controller?command=card_management&editor=true&cardId=${card.id}">
                                    ${count}
                            </a></td>
                            <td><a href="controller?command=card_management&editor=true&cardId=${card.id}">
                                    ${card.number}
                            </a></td>
                            <td><a href="controller?command=card_management&editor=true&cardId=${card.id}">
                                    ${card.score}
                            </a></td>
                        </tr>
                        <c:set var="count"
                               value="${count + 1}" scope="page"/>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
        <c:if test="${editor != null}">
            <div class="container">
                <h2><fmt:message key="yoursCards"/></h2>
                <table class="table">
                    <thead>
                    </thead>
                    <tbody>
                    <tr>
                        <td>
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text">$</span>
                                </div>
                                <form name="replenish" method="POST" action="controller">
                                    <input type="hidden" name="redirectTo" value="true"/>
                                    <input type="hidden" name="cardId" value="${cardId}"/>
<%--                                    <input type="hidden" name="editor" value=""/>--%>
                                    <input type="hidden" name="command" value="replenish_card"/>
                                    <input type="text"
                                           name="sum"
                                           pattern="(0\.((0[1-9]{1})|([1-9]{1}([0-9]{1})?)))|(([1-9]+[0-9]*)(\.([0-9]{1,2}))?)"
                                           maxlength="6"
                                           class="form-control" id="inputTwitter" placeholder="100.00" required>
                                    <button class="btn-success" type="submit">пополнить</button>
                                </form>
                            </div>
                        </td>
                        <td>
                            <form name="transfer" method="POST" action="controller">
                                <input type="hidden" name="redirectTo" value="true"/>
                                <input type="hidden" name="command" value="transfer_money"/>
                                <input type="hidden" name="cardDonor" value="${cardId}"/>
                                перевести на
                                <select class="selectpicker show-tick" name="cardRecipient" required>
                                   <c:forEach var="card" items="${cardService.findUsersCard(user.id)}">
                                       <c:if test="${card.id != cardId}">
                                           <option value="${card.id}">${card.number}</option>
                                       </c:if>
                                    </c:forEach>
                                </select>
                                сумма

                                <input type="text" name="sum" class="form-control" maxlength="6"
                                       placeholder="100.00" required
                                       pattern="(0\.((0[1-9]{1})|([1-9]{1}([0-9]{1})?)))|(([1-9]+[0-9]*)(\.([0-9]{1,2}))?)">
                                <button type="submit" class="btn-success">перевести</button>
                            </form>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <c:set var="editor" value="${null}"/>
        </c:if>
    </div>

    </body>
    </html>
</fmt:bundle>