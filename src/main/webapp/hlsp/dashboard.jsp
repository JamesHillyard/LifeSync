<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>Dashboard</title>
    </head>

    <body>
        Hello ${user.firstname}

        <ul>
            <li><a href="${pageContext.request.contextPath}/hlsp/meals">Meals</a></li>
            <li><a href="${pageContext.request.contextPath}/hlsp/exercise">Exercise</a></li>
            <li><a href="${pageContext.request.contextPath}/hlsp/sleep">Sleep</a></li>
        </ul>
    </body>
</html>