<%@ page import="dev.james.lifesync.cookie.ThemeUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!DOCTYPE html>
<html data-bs-theme="<%= ThemeUtil.getTheme(request) %>">
<head>
    <!-- Google tag (gtag.js) -->
    <script async src="https://www.googletagmanager.com/gtag/js?id=G-8CBWSGTYTV"></script>
    <script>
        window.dataLayer = window.dataLayer || [];
        function gtag(){dataLayer.push(arguments);}
        gtag('js', new Date());

        gtag('config', 'G-8CBWSGTYTV');
    </script>

    <title>LifeSync | Sleep</title>

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

<jsp:include page="../generic/navbar.jsp"/>

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
                        <a class="nav-link active" data-bs-toggle="tab" href="#view-sleep">View Sleep Data</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" data-bs-toggle="tab" href="#input-sleep">Input Sleep Data</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link disabled" data-bs-toggle="tab" href="#edit-sleep">Edit Sleep Data</a>
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
                            <div class="d-grid gap-2 col-5 mx-auto">
                                <button type="submit" class="btn btn-primary btn-block small-rounded-btn">Submit</button>
                            </div>
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

<jsp:include page="../generic/footer.jsp"/>

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
