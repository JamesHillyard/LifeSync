<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
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

    <title>LifeSync | Password Reset</title>
    <!-- Add Bootstrap CSS & JS link -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="/css/login.css">
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <!-- Left colorful section -->
        <div class="col-lg-7 colorful-section full-height">

        </div>
        <!-- Right password reset form -->
        <div class="col-lg-5 full-height">
            <div class="float-left">
                <a href="/login" class="back-link gray-text">
                    &#8592; Back
                </a>
            </div>
            <div class="center-vertically full-height">
                <div class="text-center mt-5">
                    <h2 class="title-colour">LifeSync Password Reset</h2>
                </div>
                <form class="mt-4 form-padding needs-validation" novalidate action="${pageContext.request.contextPath}/passwordreset" method="post" id="passwordResetForm">
                    <div class="form-floating mb-3">
                        <input type="email" class="form-control" id="email" name="email" placeholder="" required>
                        <label for="email">Email address</label>
                        <div class="invalid-feedback">
                            Please enter your email address.
                        </div>
                    </div>
                    <div class="form-floating">
                        <input type="password" class="form-control" id="password" name="password" placeholder="" required>
                        <label for="password">New Password</label>
                        <div class="invalid-feedback">
                            Please enter a new password.
                        </div>
                    </div>

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger mt-3" role="alert">
                                ${error}
                        </div>
                    </c:if>
                    <c:if test="${not empty successMessage}">
                        <div class="alert alert-success mt-3" role="alert">
                                ${successMessage}
                        </div>
                    </c:if>

                    <div class="d-grid gap-2 col-5 mx-auto">
                        <button type="submit" class="btn btn-primary btn-block small-rounded-btn">Reset Password</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

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
