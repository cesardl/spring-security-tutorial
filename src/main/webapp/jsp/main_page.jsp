<%-- 
    Document   : main_page
    Created on : 21/07/2015, 02:55:32 PM
    Author     : cesardiaz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Main Page</title>
        <script type="text/javascript">
            function formSubmit() {
                document.getElementById("logoutForm").submit();
            }
        </script>
    </head>
    <body>
        <h2>Title : ${title}</h2>
        <h2>Message : ${message}</h2>

        <sec:authorize access="hasRole('ROLE_REGULAR_USER')">
            <!-- For logout user -->
            <c:url value="/j_spring_security_logout" var="logoutUrl" />

            <!-- csrf for log out-->
            <form id='logoutForm' action='${logoutUrl}' method='POST'>
                <input type="hidden"
                       name="${_csrf.parameterName}"
                       value="${_csrf.token}" />
            </form>

            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <h3>Welcome : ${pageContext.request.userPrincipal.name}, you have successfully logged in.</h3>

                <br/>

                <a href="<c:url value="/admin"/>">Admin Page</a>

                <br/>
                <br/>
                <br/>
                <a href="<c:url value="/common"/>">Commom Page</a>

                <br/>
                <br/>
                <br/>
                <a href="javascript:formSubmit()">Logout</a>
            </c:if>
        </sec:authorize>

        <sec:authorize access="isRememberMe()">
            <h2># This user is login by "Remember Me Cookies".</h2>
        </sec:authorize>

        <sec:authorize access="isFullyAuthenticated()">
            <h2># This user is login by username / password.</h2>
        </sec:authorize>
    </body>
</html>
