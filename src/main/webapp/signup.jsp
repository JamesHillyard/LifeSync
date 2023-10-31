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
                    <h2 class="title-colour">Signup to LifeSync</h2>
                </div>
                <form class="mt-4 form-padding" action="${pageContext.request.contextPath}/signup" method="post" id="signupForm">
                    <div class="form-group">
                        <label class="type-name-label title-colour font-weight-bold" for="firstname">
                            First Name:
                        </label>
                        <input type="text" class="form-control custom-input" placeholder="Firstname" id="firstname" name="firstname">
                    </div>
                    <div class="form-group">
                        <label class="type-name-label title-colour font-weight-bold" for="lastname">
                            Last Name:
                        </label>
                        <input type="text" class="form-control custom-input" placeholder="Lastname" id="lastname" name="lastname">
                    </div>
                    <div class="form-group">
                        <label class="type-name-label title-colour font-weight-bold" for="username">
                            Username:
                        </label>
                        <input type="text" class="form-control custom-input" placeholder="Username" id="username" name="username">
                    </div>
                    <div class="form-group">
                        <label class="type-name-label title-colour font-weight-bold" for="password">
                            Password:
                        </label>
                        <label for="password" class="small float-right text-muted">at least 8 characters</label>
                        <input type="password" class="form-control custom-input" placeholder="Password" id="password" name="password">
                    </div>
                    <div class="form-check">
                        <input type="checkbox" class="form-check-input" value="" id="termsAndConditions" name="termsAndConditions">
                        <label class="form-check-label" for="termsAndConditions">
                            I agree to the Terms of Use and the LifeSync Privacy Policy
                        </label>
                    </div>
                    <div class="invalid-feedback">This field is required.</div>
                    <br>
                    <button type="submit" class="btn btn-primary btn-block small-rounded-btn">Signup</button>
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
        $("#signupForm").validate({
            rules: {
                firstname: {
                    required: true
                },
                lastname: {
                    required: true
                },
                username: {
                    required: true
                },
                password: {
                    required: true,
                    minlength: 8
                },
                termsAndConditions: {
                    required: true
                }
            },
            messages: {
                firstname: {
                    required: "Please enter your firstname"
                },
                lastname: {
                    required: "Please enter your lastname"
                },
                username: {
                    required: "Please enter a username"
                },
                password: {
                    required: "Please enter a password",
                    minlength: "Your password must be at least 8 characters"
                },
                termsAndConditions: {
                    required: "You must agree to the terms and conditions"
                }
            },
            errorPlacement: function(error, element) {
                if (element.attr("name") === "termsAndConditions") {
                    // Place the error message next to the checkbox label
                    error.addClass("invalid-feedback");
                    error.insertAfter(element.next("label"));
                } else {
                    error.addClass("invalid-feedback");
                    error.insertAfter(element);
                }
            }
        });
        // Check for a successMessage and disable form elements if there is a successMessage
        <c:if test="${not empty successMessage}">
            $("#signupForm").find(':input').prop('disabled', true);
        </c:if>
    });
</script>

</body>
</html>
