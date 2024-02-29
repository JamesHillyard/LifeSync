<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <!-- Google tag (gtag.js) -->
    <script async src="https://www.googletagmanager.com/gtag/js?id=G-8CBWSGTYTV"></script>
    <script>
        window.dataLayer = window.dataLayer || [];
        function gtag(){dataLayer.push(arguments);}
        gtag('js', new Date());

        gtag('config', 'G-8CBWSGTYTV');
    </script>

    <title>LifeSync | Exercise</title>
    <!-- Add Bootstrap CSS & JS link -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <!-- Chart.js libraries -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js@3.7.0/dist/chart.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/chartjs-plugin-annotation/1.0.2/chartjs-plugin-annotation.min.js"></script>

    <link rel="stylesheet" href="/css/login.css">
    <link rel="stylesheet" href="/css/datapage.css">
    <link rel="stylesheet" href="/css/datainput.css">
</head>

<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#"><img src="/assets/logo.png" alt="Your Logo" width="40" height="40" class="d-inline-block align-top navbar-logo-padding"></a>

    <!-- Navbar Links -->
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="/hlsp/dashboard">Dashboard</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/hlsp/nutrition">Nutrition</a>
            </li>
            <li class="nav-item active">
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

<div class="container-fluid">
    <div class="row">
        <!-- Top-left quadrant for the chart -->
        <div class="col-md-6 col-sm-12">
            <div class="quadrant-container quadrant-top">
                <div class="text-center">
                    <h4>Exercise Logged for the Past 7 Days</h4>
                </div>
                <canvas id="exerciseChart" width="300" height="125"></canvas>
            </div>
        </div>
        <!-- Top-right quadrant for articles -->
        <div class="col-md-6 col-sm-12">
            <div class="quadrant-container quadrant-top">
                <table class="table">
                    <thead>
                    <tr>
                        <th>Title</th>
                        <th>Source</th>
                        <th>URL</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="article" items="${articles}">
                        <tr>
                            <td>${article.getName()}</td>
                            <td>${article.getSource()}</td>
                            <td><a href=${article.getUrl()}>Read Here</a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div class="row">
        <!-- Bottom-left quadrant for statistics -->
        <div class="col-md-6 col-sm-12">
            <div class="quadrant-container quadrant-bottom">
                <div class="row">
                    <div class="col-md-6 text-center">
                        <h4>Average Calories Burnt</h4>
                        <c:choose>
                            <c:when test="${averageCaloriesBurnt >= 600}">
                                <p class="display-4 text-success">${averageCaloriesBurnt} Calories</p>
                            </c:when>
                            <c:when test="${averageCaloriesBurnt >= 400}">
                                <p class="display-4 text-warning">${averageCaloriesBurnt} Calories</p>
                            </c:when>
                            <c:otherwise>
                                <p class="display-4 text-danger">${averageCaloriesBurnt} Calories</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="col-md-6 text-center">
                        <h4>Average Exercise Duration</h4>
                        <c:choose>
                            <c:when test="${averageDurationInMinutes >= 90}">
                                <p class="display-4 text-success">${averageDurationFormatted}</p>
                            </c:when>
                            <c:when test="${averageDurationInMinutes >= 60}">
                                <p class="display-4 text-warning">${averageDurationFormatted}</p>
                            </c:when>
                            <c:otherwise>
                                <p class="display-4 text-danger">${averageDurationFormatted}</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-6 col-sm-12">
            <!-- Bottom-right quadrant for the data input form -->
            <div class="quadrant-container quadrant-bottom">
                <ul class="nav nav-underline">
                    <li class="nav-item">
                        <a class="nav-link active" data-bs-toggle="tab" href="#view-exercise">View Exercise Data</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" data-bs-toggle="tab" href="#input-exercise">Input Exercise Data</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link disabled" data-bs-toggle="tab" href="#edit-exercise">Edit Exercise Data</a>
                    </li>
                </ul>


                <div class="tab-content">
                    <!-- View Exercise Tab Contents -->
                    <div id="view-exercise" class="tab-pane fade in show active"> <!-- in show active sets this as the default -->
                        <div class="table-responsive" style="max-height: 375px; overflow-y: auto;">
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th>Date</th>
                                    <th>Activity</th>
                                    <th>Calories Burnt</th>
                                    <th>Duration</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="exerciseData" items="${exerciseData}">
                                    <tr>
                                        <td>${exerciseData.getDate()}</td>
                                        <td>${exerciseData.getActivityName()}</td>
                                        <td>${exerciseData.getCaloriesBurnt()}</td>
                                        <td>${exerciseData.getDurationInHoursAndMinutesHumanReadable()}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <!-- Input exercise Tab Content -->
                    <div id="input-exercise" class="tab-pane fade">
                        <form class="mt-4 form-padding needs-validation" novalidate id="exerciseInputForm" method="post" action="${pageContext.request.contextPath}/hlsp/exercise">
                            <div class="form-group">
                                <div style="text-align: center">
                                    LifeSync uses a natural language model for the input of exercise data. Simply type what you did!<br>
                                    E.g. "A 30-minute walk and 90 minutes rock climbing"
                                </div>
                                <br>
                                <div class="form-floating">
                                    <textarea class="form-control" placeholder="" id="exerciseDetails" name="exerciseDetails" style="height: 100px" cols="4" required></textarea>
                                    <label for="exerciseDetails">Exercise Details</label>
                                    <div class="invalid-feedback">
                                        Please enter exercise details.
                                    </div>
                                </div>
                            </div>
                            <div class="invalid-feedback">This field is required.</div>
                            <c:if test="${not empty dataEntryError}">
                                <div class="alert alert-danger mt-3" role="alert">
                                        ${dataEntryError}
                                </div>
                            </c:if>
                            <div class="d-grid gap-2 col-5 mx-auto">
                                <button type="submit" class="btn btn-primary btn-block small-rounded-btn">Submit</button>
                            </div>
                        </form>
                    </div>
                    <!-- Edit Sleep Tab Content -->
                    <div id="edit-exercise" class="tab-pane fade">
                        edit exercise
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    // JavaScript code to populate the chart with data
    var exerciseData = [
        <%-- Iterate through your processed exercise data and generate an array --%>
        <c:forEach items="${exerciseDataGrouped}" var="data">
        {
            date: "${data.getDate()}",
            duration: ${data.getDuration()},
            calories: ${data.getCaloriesBurnt()}
        },
        </c:forEach>
    ];

    var ctx = document.getElementById('exerciseChart').getContext('2d');
    var chart = new Chart(ctx, {
        type: 'bar', // You can change the chart type as needed (e.g., 'line')
        data: {
            labels: exerciseData.map(item => item.date),
            datasets: [
                {
                    label: 'Duration of Exercise',
                    data: exerciseData.map(item => item.duration),
                    yAxisID: 'duration',
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1
                },
                {
                    label: 'Calories Burnt',
                    data: exerciseData.map(item => item.calories),
                    yAxisID: 'calories',
                    backgroundColor: 'rgba(192,116,75, 0.2)',
                    borderColor: 'rgba(192,116,75, 1)',
                    borderWidth: 1
                }
            ]
        },
        options: {
            scales: {
                duration: {
                    type: 'linear',
                    position: 'left',
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'Duration (minutes)'
                    }
                },
                calories: {
                    type: 'linear',
                    position: 'right',
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'Calories Burnt'
                    }
                }
            },
        },
    });
</script>

<%--
@Author Bootstrap
Source: https://getbootstrap.com/docs/5.3/forms/validation/#custom-styles
 --%>
<script>
    (() => {
        'use strict'

        // Fetch all the forms to apply custom Bootstrap validation styles to
        const forms = document.querySelectorAll('.needs-validation')

        // Loop over them and prevent submission
        Array.from(forms).forEach(form => {
            form.addEventListener('submit', event => {
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }

                form.classList.add('was-validated')
            }, false)
        })
    })()
</script>
</body>
</html>