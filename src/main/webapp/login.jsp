<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title>Login</title>
    </head>

    <body>
        <form action="${pageContext.request.contextPath}/login" method="post">
            <label>
                Username
                <input type="text" name="username"/>
            </label>

            <br/><br/>
            <label>
                Password
                <input type="text" name="password"/>
            </label>
            <br/><br/>
            <input type="submit" value="login"/>
        </form>
        <c:if test="${not empty loginError}">
            <c:out value="${loginError}"/>
        </c:if>

        <form action="${pageContext.request.contextPath}/logout" method="get">
            <input type="submit" value="Logout"/>
        </form>
    </body>
</html>