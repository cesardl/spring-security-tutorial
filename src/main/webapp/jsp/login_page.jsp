<%-- 
    Document   : login_page
    Created on : 21/07/2015, 10:50:35 AM
    Author     : cesardiaz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <style>
            .error {
                padding: 15px;
                margin-bottom: 20px;
                border: 1px solid transparent;
                border-radius: 4px;
                color: #a94442;
                background-color: #f2dede;
                border-color: #ebccd1;
            }

            .msg {
                padding: 15px;
                margin-bottom: 20px;
                border: 1px solid transparent;
                border-radius: 4px;
                color: #31708f;
                background-color: #d9edf7;
                border-color: #bce8f1;
            }
        </style>
    </head>
    <body onload='document.loginForm.username.focus();'>
        <h3>Spring Security Custom Login Form (XML)</h3>
        <h4>Custom Login Page with Username and Password</h4>

        <c:if test="${not empty error}">
            <div class="error">
                Your login attempt was unsuccessful. ${error}
            </div>
        </c:if>
        <c:if test="${not empty msg}">
            <div class="msg">${msg}</div>
        </c:if>

        <form name='loginForm'
              action="<c:url value='/auth/login_check?targetUrl=${targetUrl}' />"
              method='POST'>
            <table>
                <tr>
                    <td>User:</td>
                    <td><input type='text' name='username' value=''></td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><input type='password' name='password' /></td>
                </tr>
                <!-- if this is login for update, ignore remember me check -->
                <c:if test="${empty loginUpdate}">
                    <tr>
                        <td></td>
                        <td>Remember Me: <input type="checkbox" name="remember-me" /></td>
                    </tr>
                </c:if>
                <tr>
                    <td colspan='2' align='right'>
                        <input name="submit" type="submit" value="submit" />
                    </td>
                </tr>
                <tr>
                    <td colspan='2' align='right'>
                        <input name="reset" type="reset" />
                    </td>
                </tr>
            </table>

            <input type="hidden" name="${_csrf.parameterName}"
                   value="${_csrf.token}" />

        </form>
    </body>
</html>
