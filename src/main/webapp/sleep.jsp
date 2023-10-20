<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>Sleep</title>
    </head>

    <body>
        Hello ${user.firstname}

        <ul>
            <li><a href="${pageContext.request.contextPath}/dashboard">Dashboard</a></li>
            <li><a href="${pageContext.request.contextPath}/meals">Meals</a></li>
            <li><a href="${pageContext.request.contextPath}/exercise">Exercise</a></li>
        </ul>
    </body>
</html>