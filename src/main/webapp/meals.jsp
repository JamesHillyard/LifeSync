<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>Meals</title>
    </head>

    <body>
        Hello ${user.firstName}

        <ul>
            <li><a href="${pageContext.request.contextPath}/dashboard">Dashboard</a></li>
            <li><a href="${pageContext.request.contextPath}/exercise">Exercise</a></li>
            <li><a href="${pageContext.request.contextPath}/sleep">Sleep</a></li>
        </ul>

        <form action="${pageContext.request.contextPath}/logout" method="get">
            <input type="submit" value="Logout"/>
        </form>
    </body>
</html>