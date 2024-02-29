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

    <title>LifeSync | Nutrition</title>
    <!-- Add Bootstrap CSS & JS link -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <!-- Client Side Validation -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.5/dist/jquery.validate.min.js"></script>

    <!-- Chart.js libraries -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js@3.7.0/dist/chart.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/chartjs-plugin-annotation/1.0.2/chartjs-plugin-annotation.min.js"></script>

    <link rel="stylesheet" href="/css/login.css">
    <link rel="stylesheet" href="/css/datapage.css">
    <link rel="stylesheet" href="/css/datainput.css">
</head>

<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#"><img src="/assets/logo.png" alt="Your Logo" width="40" height="40" class="d-inline-block align-top"></a>

    <!-- Navbar Links -->
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="/hlsp/dashboard">Dashboard</a>
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

<!-- Add the rest of your content here -->
<div class="container-fluid">
    <div class="row">
        <!-- Top-left quadrant for the chart -->
        <div class="col-md-6 col-sm-12">
            <div class="quadrant-container quadrant-top">
                <div class="text-center">
                    <h4>Nutrition Data Logged for the Past 7 Days</h4>
                </div>
                <canvas id="nutritionChart" width="300" height="125"></canvas>
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
                        <h4>Average Calorie Intake</h4>
                        <%--
                        Over 2500 Calories - Red
                        2200 - 2500 Calories - Orange
                        1800 - 2200 Calories - Green
                        1600 - 1800 Calories - Orange
                        Under 1600 Calories - Red
                        --%>
                        <c:choose>
                            <c:when test="${averageCalorieIntake >= 2500}">
                                <p class="display-4 text-warning">${averageCalorieIntake} Calories</p>
                            </c:when>
                            <c:when test="${averageCalorieIntake >= 2200}">
                                <p class="display-4 text-warning">${averageCalorieIntake} Calories</p>
                            </c:when>
                            <c:when test="${averageCalorieIntake >= 1800}">
                                <p class="display-4 text-success">${averageCalorieIntake} Calories</p>
                            </c:when>
                            <c:when test="${averageCalorieIntake >= 1600}">
                                <p class="display-4 text-success">${averageCalorieIntake} Calories</p>
                            </c:when>
                            <c:otherwise>
                                <p class="display-4 text-danger">${averageCalorieIntake} Calories</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="col-md-6 text-center">
                        <h4>Average Sugar Intake</h4>
                        <%--
                        Over 38g Sugar - Red
                        32 - 38g Sugar - Orange
                        28 - 32g Sugar - Green
                        22 - 28g Sugar - Orange
                        Under 22g Sugar - Red
                        --%>
                        <c:choose>
                            <c:when test="${averageSugarIntake >= 38}">
                                <p class="display-4 text-warning">${averageSugarIntake} g</p>
                            </c:when>
                            <c:when test="${averageSugarIntake >= 32}">
                                <p class="display-4 text-warning">${averageSugarIntake} g</p>
                            </c:when>
                            <c:when test="${averageSugarIntake >= 28}">
                                <p class="display-4 text-success">${averageSugarIntake} g</p>
                            </c:when>
                            <c:when test="${averageSugarIntake >= 22}">
                                <p class="display-4 text-success">${averageSugarIntake} g</p>
                            </c:when>
                            <c:otherwise>
                                <p class="display-4 text-danger">${averageSugarIntake} g</p>
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
                        <a class="nav-link active" data-bs-toggle="tab" href="#view-nutrition">View Nutrition Data</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" data-bs-toggle="tab" href="#input-nutrition">Input Nutrition Data</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link disabled" data-bs-toggle="tab" href="#edit-nutrition">Edit Nutrition Data</a>
                    </li>
                </ul>


                <div class="tab-content">
                    <!-- View Nutrition Tab Contents -->
                    <div id="view-nutrition" class="tab-pane fade in show active"> <!-- in show active sets this as the default -->
                        <div class="table-responsive" style="max-height: 375px; overflow-y: auto;">
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th>Date</th>
                                    <th>Name</th>
                                    <th>Calories</th>
                                    <th>Fat</th>
                                    <th>Sugar</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="nutritionData" items="${nutritionData}">
                                    <tr>
                                        <td>${nutritionData.getDate()}</td>
                                        <td>${nutritionData.getFoodName()}</td>
                                        <td>${nutritionData.getCalories()}</td>
                                        <td>${nutritionData.getFat()} g</td>
                                        <td>${nutritionData.getSugar()} g</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <!-- Input nutrition Tab Content -->
                    <div id="input-nutrition" class="tab-pane fade">
                        <form class="mt-4 form-padding" id="nutritionInputForm" method="post" action="${pageContext.request.contextPath}/hlsp/nutrition">
                            <div class="form-group">
                                <label for="date">Date:</label>
                                <input type="date" class="form-control custom-input" id="date" name="date">
                            </div>
                            <div class="form-group">
                                <label for="nutritionDetails">Nutrition Details:</label>
                                <input type="text" class="form-control custom-input" id="nutritionDetails" name="nutritionDetails">
                                <div class="text-right mt-2">
                                    <a href="#" data-bs-toggle="modal" data-target="#needHelpModal" class="small text-muted">
                                        Need Help?
                                    </a>
                                </div>
                            </div>
                            <div class="invalid-feedback">This field is required.</div>
                            <c:if test="${not empty dataEntryError}">
                                <div class="alert alert-danger mt-3" role="alert">
                                        ${dataEntryError}
                                </div>
                            </c:if>
                            <button type="submit" class="btn btn-primary btn-block small-rounded-btn">Submit</button>
                        </form>
                    </div>
                    <!-- Edit Sleep Tab Content -->
                    <div id="edit-nutrition" class="tab-pane fade">
                        edit nutrition
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Need Help Modal -->
<div class="modal fade" id="needHelpModal" tabindex="-1" role="dialog" aria-labelledby="needHelpModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-md" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <div style="white-space: normal">
                    LifeSync uses a natural language model for the input of nutritional data. Simply type what you ate!
                    <br>
                    Eg. "For breakfast I had an apple and a cappuccino"
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    // JavaScript code to populate the chart with data
    var exerciseData = [
        <%-- Iterate through your processed exercise data and generate an array --%>
        <c:forEach items="${nutritionDataGrouped}" var="data">
        {
            date: "${data.getDate()}",
            calories: ${data.getCalories()},
            fat: ${data.getFat()},
            sugar: ${data.getSugar()}
        },
        </c:forEach>
    ];

    var ctx = document.getElementById('nutritionChart').getContext('2d');
    var chart = new Chart(ctx, {
        type: 'bar', // You can change the chart type as needed (e.g., 'line')
        data: {
            labels: exerciseData.map(item => item.date),
            datasets: [
                {
                    label: 'Calorie Intake',
                    data: exerciseData.map(item => item.calories),
                    yAxisID: 'highvalue',
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgb(75,126,192)',
                    borderWidth: 1
                },
                {
                    label: 'Fat Intake',
                    data: exerciseData.map(item => item.fat),
                    yAxisID: 'lowvalue',
                    backgroundColor: 'rgba(192,116,75, 0.2)',
                    borderColor: 'rgb(192,75,75)',
                    borderWidth: 1
                },
                {
                    label: 'Sugar Intake',
                    data: exerciseData.map(item => item.sugar),
                    yAxisID: 'lowvalue',
                    backgroundColor: 'rgba(192,116,75, 0.2)',
                    borderColor: 'rgb(192,186,75)',
                    borderWidth: 1
                }
            ]
        },
        options: {
            scales: {
                highvalue: {
                    type: 'linear',
                    position: 'left',
                    beginAtZero: true,
                },
                lowvalue: {
                    type: 'linear',
                    position: 'right',
                    beginAtZero: true,
                },
            },
        },
    });
</script>
</body>
</html>