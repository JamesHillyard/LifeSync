<%@ page import="dev.james.lifesync.cookie.ThemeUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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

    <title>LifeSync | Nutrition</title>
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
                        <form class="mt-4 form-padding needs-validation" novalidate id="nutritionInputForm" method="post" action="${pageContext.request.contextPath}/hlsp/nutrition">
                            <div class="form-group">
                                <div style="text-align: center">
                                    LifeSync uses a natural language model for the input of nutritional data. Simply type what you ate!
                                    <br>
                                    E.g. "For breakfast I had an apple and a cappuccino"
                                </div>
                            </div>
                            <br>
                            <div class="form-floating">
                                <textarea class="form-control" placeholder="" id="nutritionDetails" name="nutritionDetails" style="height: 100px" cols="4" required></textarea>
                                <label for="nutritionDetails">Nutrition Details</label>
                                <div class="invalid-feedback">
                                    Please enter nutrition details.
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
                    <div id="edit-nutrition" class="tab-pane fade">
                        edit nutrition
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../generic/footer.jsp"/>

<script>
    // JavaScript code to populate the chart with data
    var nutritionData = [
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
            labels: nutritionData.map(item => item.date),
            datasets: [
                {
                    label: 'Calorie Intake',
                    data: nutritionData.map(item => item.calories),
                    yAxisID: 'highvalue',
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgb(75,126,192)',
                    borderWidth: 1
                },
                {
                    label: 'Fat Intake',
                    data: nutritionData.map(item => item.fat),
                    yAxisID: 'lowvalue',
                    backgroundColor: 'rgba(192,116,75, 0.2)',
                    borderColor: 'rgb(192,75,75)',
                    borderWidth: 1
                },
                {
                    label: 'Sugar Intake',
                    data: nutritionData.map(item => item.sugar),
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