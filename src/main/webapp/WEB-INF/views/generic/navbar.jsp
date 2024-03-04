<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-- Add Bootstrap CSS & JS link -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light">
        <a class="navbar-brand" href="#"><img src="/assets/logo.png" alt="Your Logo" width="40" height="40" class="d-inline-block align-top navbar-logo-padding"></a>

        <!-- Navbar Links -->
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <%-- Dashboard Functionality is not implemented and should be disabled --%>
                <li class="nav-item">
                    <div class="nav-link gray-text">Dashboard</div>
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href="/hlsp/nutrition">Nutrition</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/hlsp/exercise">Exercise</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/hlsp/sleep">Sleep</a>
                </li>
            </ul>
        </div>

        <ul class="navbar-nav ml-auto">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="timeRangeDropdown" role="button" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <c:choose>
                        <c:when test="${empty cookie.timeRange}">
                            7 Days
                        </c:when>
                        <c:otherwise>
                            ${cookie.timeRange.getValue()} Days
                        </c:otherwise>
                    </c:choose>
                </a>
                <div class="dropdown-menu" aria-labelledby="timeRangeDropdown">
                    <a class="dropdown-item" href="/hlsp/cookie/timeRange?days=7" id="timeRange7">7 Days</a>
                    <a class="dropdown-item disabled" href="/hlsp/cookie/timeRange?days=14" id="timeRange14">14 Days</a>
                    <a class="dropdown-item disabled" href="/hlsp/cookie/timeRange?days=30" id="timeRange30">30 Days</a>
                </div>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <img src="/assets/user-profile.png" alt="Your Profile" width="30" height="30" class="d-inline-block align-top"> ${user.firstname}
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="/hlsp/logout">Log Out</a>
                </div>
            </li>
        </ul>
    </nav>
</body>
</html>
