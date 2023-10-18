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
    </body>
</html>