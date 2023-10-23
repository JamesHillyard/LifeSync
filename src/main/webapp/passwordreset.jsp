<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Add Bootstrap JS scripts -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.5/dist/jquery.validate.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.17.0/font/bootstrap-icons.css"></script>
<!DOCTYPE html>
<html>
<head>
    <title>LifeSync | Password Reset</title>
    <!-- Add Bootstrap CSS link -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/login.css">
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <!-- Left colorful section -->
        <div class="col-7 colorful-section">

        </div>
        <!-- Right password reset form -->
        <div class="col-md-5">
            <div class="float-left">
                <a href="/login" class="back-link gray-text">
                    &#8592; Back
                </a>
            </div>
            <div class="center-vertically">
                <div class="text-center mt-5">
                    <h2 class="title-colour">LifeSync Password Reset</h2>
                </div>
                <form class="mt-4 form-padding" action="${pageContext.request.contextPath}/passwordreset" method="post" id="passwordResetForm">
                    <div class="form-group">
                        <input type="text" class="form-control custom-input" placeholder="Username" name="username">
                    </div>
                    <div class="form-group">
                        <input type="password" class="form-control custom-input" placeholder="New Password" name="newPassword">
                    </div>
                    <div class="invalid-feedback">This field is required.</div>
                    <button type="submit" class="btn btn-primary btn-block small-rounded-btn">Reset Password</button>
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger mt-3" role="alert">
                                ${error}
                        </div>
                    </c:if>
                    <c:if test="${not empty successMessage}">
                        <div class="alert alert-success mt-3" role="alert">
                                ${successMessage}
                        </div>
                        <a href="/login" class="btn btn-primary btn-block small-rounded-btn">Return To Login</a>
                    </c:if>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    $(document).ready(function() {
        $("#passwordResetForm").validate({
            rules: {
                username: {
                    required: true
                },
                newPassword: {
                    required: true
                }
            },
            messages: {
                username: {
                    required: "Please enter your username"
                },
                newPassword: {
                    required: "Please enter your new password"
                }
            },
            errorPlacement: function(error, element) {
                error.addClass("invalid-feedback");
                error.insertAfter(element);
            }
        });
        // Check for a successMessage and disable form elements if there is a successMessage
        <c:if test="${not empty successMessage}">
            $("#passwordResetForm").find(':input').prop('disabled', true);
        </c:if>
    });
</script>

</body>
</html>
