<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>LifeSync | Sleep</title>

    <!-- Bootstrap CSS and JavaScript -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

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
            <li class="nav-item">
                <a class="nav-link" href="/hlsp/nutrition">Nutrition</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/hlsp/exercise">Exercise</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="/hlsp/sleep">Sleep</a>
            </li>
        </ul>
    </div>

    <ul class="navbar-nav ml-auto">
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="timeRangeDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
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
            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
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
                    <h4>Sleep Duration Chart for the Past 7 Days</h4>
                </div>
                <canvas id="sleepChart" width="300" height="125"></canvas>
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
                        <h4>Recommended Sleep Frequency</h4>
                        <c:choose>
                            <c:when test="${percentageDaysSleepOverRecommended >= 80}">
                                <p class="display-4 text-success">${percentageDaysSleepOverRecommended}%</p>
                            </c:when>
                            <c:when test="${percentageDaysSleepOverRecommended >= 60}">
                                <p class="display-4 text-warning">${percentageDaysSleepOverRecommended}%</p>
                            </c:when>
                            <c:otherwise>
                                <p class="display-4 text-danger">${percentageDaysSleepOverRecommended}%</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="col-md-6 text-center">
                        <h4>Average Sleep Duration</h4>
                        <c:choose>
                            <c:when test="${averageSleepDuration >= 420 && averageSleepDuration <= 540}">
                                <c:set var="hours" value="${Integer.valueOf((averageSleepDuration / 60))}" />
                                <c:set var="minutes" value="${averageSleepDuration % 60}" />
                                <p class="display-4 text-success">${hours} hours<br>${minutes} minutes</p>
                            </c:when>
                            <c:otherwise>
                                <c:set var="hours" value="${Integer.valueOf((averageSleepDuration / 60))}" />
                                <c:set var="minutes" value="${averageSleepDuration % 60}" />
                                <p class="display-4 text-danger">${hours} hours<br>${minutes} minutes</p>
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
                        <a class="nav-link active" data-toggle="tab" href="#view-sleep">View Sleep Data</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" data-toggle="tab" href="#input-sleep">Input Sleep Data</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link disabled" data-toggle="tab" href="#edit-sleep">Edit Sleep Data</a>
                    </li>
                </ul>


                <div class="tab-content">
                    <!-- View Sleep Tab Contents -->
                    <div id="view-sleep" class="tab-pane fade in show active"> <!-- in show active sets this as the default -->
                        <div class="table-responsive" style="max-height: 375px; overflow-y: auto;">
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th>Start Time</th>
                                    <th>End Time</th>
                                    <th>Duration</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="sleepData" items="${sleepData}">
                                    <tr>
                                        <td>${sleepData.starttime}</td>
                                        <td>${sleepData.endtime}</td>
                                        <td>${sleepData.getDurationInHoursAndMinutesHumanReadable()}</td>
                                    </tr>
                                </c:forEach>

                                </tbody>
                            </table>
                        </div>
                    </div>

                    <!-- Input Sleep Tab Content -->
                    <div id="input-sleep" class="tab-pane fade">
                        <form class="mt-4 form-padding" id="sleepInputForm" method="post" action="${pageContext.request.contextPath}/hlsp/sleep">
                            <div class="form-group">
                                <label for="startSleep">Start Sleep:</label>
                                <input type="datetime-local" class="form-control custom-input" id="startSleep" name="starttime">
                            </div>
                            <div class="form-group">
                                <label for="endSleep">End Sleep:</label>
                                <input type="datetime-local" class="form-control custom-input" id="endSleep" name="endtime">
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
                    <div id="edit-sleep" class="tab-pane fade">
                        edit sleep
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    $(document).ready(function() {
        // Add a custom validation method to compare start and end times
        $.validator.addMethod("endTimeLaterThanStartTime", function() {
            var startDateTime = new Date($("#startSleep").val());
            var endDateTime = new Date($("#endSleep").val());

            // Extract date and time components separately
            var startDate = startDateTime.toLocaleDateString();
            var startTime = startDateTime.toLocaleTimeString();

            var endDate = endDateTime.toLocaleDateString();
            var endTime = endDateTime.toLocaleTimeString();

            // Check if end date is later than start date, and if end time is later than start time
            return (endDate > startDate) || (endDate === startDate && endTime > startTime);
        }, "End time must be later than start time");

        $("#sleepInputForm").validate({
            rules: {
                starttime: {
                    required: true
                },
                endtime: {
                    required: true,
                    endTimeLaterThanStartTime: true
                }
            },
            messages: {
                starttime: {
                    required: "Please enter the time you went to sleep"
                },
                endtime: {
                    required: "Please enter the time you woke up"
                }
            },
            errorPlacement: function(error, element) {
                error.addClass("invalid-feedback");
                error.insertAfter(element);
            }
        });
    });
</script>

<script>
    // JavaScript code to populate the chart with data
    var sleepData = [
        <%-- Iterate through your processed sleep data and generate an array --%>
        <c:forEach items="${sleepData}" var="data">
        {
            date: "${data.getEndtime().toLocalDateTime().toLocalDate()}",
            duration: ${data.getDurationInHoursAndMinutes()}
        },
        </c:forEach>
    ];

    var ctx = document.getElementById('sleepChart').getContext('2d');
    var chart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: sleepData.map(item => item.date),
            datasets: [{
                label: 'Sleep Duration (hours)',
                data: sleepData.map(item => item.duration),
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            },
            plugins: {
                annotation: {
                    annotations: [{
                        type: 'line',
                        mode: 'horizontal',
                        scaleID: 'y',
                        value: 8, // Should reflect RECOMMENDED_SLEEP_DURATION in SleepServlet
                        borderColor: 'green',
                        borderWidth: 2,
                        label: {
                            content: 'Recommended Hours',
                            enabled: true,
                        },
                    }],
                },
            },
        },
    });
</script>
</body>
</html>
