<%-- 
    Document   : 403
    Created on : 23/07/2015, 06:04:02 PM
    Author     : cesardiaz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>403 Page</title>
    </head>
    <body>
        <h2>HTTP Status 403 - Access is denied</h2>

        <c:choose>
            <c:when test="${empty username}">
                <h3>You do not have permission to access this page!</h3>
            </c:when>
            <c:otherwise>
                <h3>Username : ${username} <br/>
                    You do not have permission to access this page!</h3>
            </c:otherwise>
        </c:choose>
    </body>
</html>
