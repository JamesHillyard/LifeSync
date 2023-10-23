<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Add Bootstrap JS scripts -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.5/dist/jquery.validate.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<!DOCTYPE html>
<html>
<head>
    <title>LifeSync | Login</title>
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
        <!-- Right login form -->
        <div class="col-md-5">
            <div class="center-vertically">
                <div class="text-center mt-5">
                    <img src="https://raw.githubusercontent.com/JamesHillyard/LifeSync/main/logo.png" alt="LifeSync Logo" width="200" class="logo-padding">
                    <h2 class="title-colour">LifeSync</h2>
                </div>
                <form class="mt-4 form-padding" action="${pageContext.request.contextPath}/login" method="post" id="loginForm">
                    <div class="form-group">
                        <input type="text" class="form-control custom-input" placeholder="Username" name="username">
                    </div>
                    <div class="form-group">
                        <input type="password" class="form-control custom-input" placeholder="Password" name="password">
                        <div class="text-right mt-2">
                            <a href="#" class="gray-text">Forgot Password?</a>
                        </div>
                    </div>
                    <div class="invalid-feedback">This field is required.</div>
                    <c:if test="${not empty loginError}">
                        <div class="alert alert-danger mt-3" role="alert">
                                ${loginError}
                        </div>
                    </c:if>
                    <button type="submit" class="btn btn-primary btn-block small-rounded-btn">Login</button>
                    <div class="mt-3 text-center">
                        <div class="line"></div>
                        <div class="small text-muted">
                            <a href="#" data-toggle="modal" data-target="#privacyPolicyModal" class="gray-text">
                                Privacy Policy
                            </a>
                            <span class="mx-2">|</span>
                            <a href="#" data-toggle="modal" data-target="#termsOfUseModal" class="gray-text">
                                Terms of Use
                            </a>
                        </div>
                    </div>
                </form>
                <div class="text-center mt-3 gray-text">
                    <a href="register.html" class="btn btn-link gray-text">Sign Up</a>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Terms of Use Modal -->
<div class="modal fade" id="termsOfUseModal" tabindex="-1" role="dialog" aria-labelledby="termsOfUseModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-xl" role="document">
        <div class="modal-content">
            <div class="modal-body" style="font-size: 12px; line-height: 1.4;">
                <div style="white-space: normal">
                    <b>LifeSync Terms of Service</b>
                    <br><br>
                    Effective Date: 23/10/2023
                    <br><br>

                    Please read these Terms of Service ("Terms") carefully before using the services provided by LifeSync, a company dedicated to enhancing your life's synchronization. By accessing or using our services, you agree to be bound by these Terms, including our Privacy Policy, and any additional terms or conditions that may be referenced herein. If you do not agree with these Terms, please do not use our services.

                    1. Acceptance of Terms
                    <br><br>
                    By using LifeSync's services, you acknowledge that you have read and understood these Terms and agree to be bound by them. You must be at least 18 years old or have the legal capacity to enter into these Terms on behalf of yourself or an organization to use our services.
                    <br><br>
                    2. Service Description
                    <br><br>
                    LifeSync provides various services designed to help individuals improve their lifestyle.
                    <br><br>
                    3. User Accounts
                    <br><br>
                    You may need to create a user account to access certain features of our services. You are responsible for maintaining the confidentiality of your account information and for all activities that occur under your account. You agree to provide accurate, current, and complete information during the registration process and to update such information to keep it accurate, current, and complete. You are responsible for any activity that occurs on your account.
                    <br><br>
                    4. Privacy
                    <br><br>
                    Your use of our services is also governed by our Privacy Policy, which can be found on our website. By using our services, you consent to the collection and use of your information as described in the Privacy Policy.
                    <br><br>
                    5. Intellectual Property
                    <br><br>
                    All content provided by LifeSync, including text, graphics, logos, and software, is the property of LifeSync and is protected by applicable copyright and trademark laws. You agree not to use our intellectual property without our express written consent.
                    <br><br>
                    6. Termination
                    <br><br>
                    We reserve the right to terminate or suspend your access to our services at our sole discretion, without notice, for any violation of these Terms or for any other reason we deem appropriate.
                    <br><br>
                    7. Limitation of Liability
                    <br><br>
                    To the fullest extent permitted by applicable law, LifeSync, its officers, employees, agents, and affiliates will not be liable for any direct, indirect, incidental, consequential, or punitive damages arising out of or in connection with your use of our services.
                    <br><br>
                    8. Changes to Terms
                    <br><br>
                    LifeSync reserves the right to update or modify these Terms at any time. We will notify you of any changes by posting the new Terms on our website. You are responsible for regularly reviewing these Terms, and your continued use of our services constitutes your agreement to be bound by the updated Terms.

                    By using LifeSync's services, you acknowledge that you have read, understood, and agreed to these Terms of Service.
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Privacy Policy Modal -->
<div class="modal fade" id="privacyPolicyModal" tabindex="-1" role="dialog" aria-labelledby="privacyPolicyModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-body" style="font-size: 12px; line-height: 1.4;">
                <div style="white-space: normal;">
                    <b>Privacy Policy</b>
                    <br><br>
                    Last Updated: 23/10/2023
                    <br><br>

                    1. Introduction
                    <br><br>
                    At LifeSync, we value your privacy and are committed to protecting your personal information. This Privacy Policy outlines our practices regarding the collection, use, and disclosure of your information when you use our website.
                    <br><br>

                    By using our website, you agree to the terms and conditions outlined in this Privacy Policy. If you do not agree with our practices, please do not use our website.
                    <br><br>

                    2. Information We Collect
                    <br><br>
                    2.1. Personal Information
                    <br><br>
                    We may collect the following personal information from you:
                    <br><br>
                    <ul>
                        <li>Name</li>
                        <li>Email Address</li>
                    </ul>
                    <br><br>
                    2.2. Non-Personal Information
                    <br><br>
                    We may collect non-personal information, such as your browser type, IP address, and the date and time of your visit.
                    <br><br>

                    3. How We Use Your Information
                    <br><br>
                    3.1 We may use your information for the following purposes:
                    <br><br>
                    - Provide a tailored experience based on your provided fitness data.
                    - To improve our website and user experience.
                    <br><br>

                    4. Security
                    <br><br>
                    We take reasonable steps to protect your information from unauthorized access, disclosure, alteration, or destruction. However, no data transmission or storage can be guaranteed to be 100% secure, so we cannot ensure or warrant the security of any information you provide to us.
                    <br><br>

                    5. Cookies
                    <br><br>
                    We may use cookies to collect information about your use of our website. You can manage your cookie preferences through your browser settings.
                    <br><br>

                    6. Changes to This Privacy Policy
                    <br><br>
                    We may update this Privacy Policy from time to time. Any changes will be posted on our website, and the "Last Updated" date will be revised.
                    <br><br>

                    Thank you for using LifeSync. Your privacy is important to us.
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    $(document).ready(function() {
        $("#loginForm").validate({
            rules: {
                username: {
                    required: true
                },
                password: {
                    required: true
                }
            },
            messages: {
                username: {
                    required: "Please enter your username"
                },
                password: {
                    required: "Please enter your password"
                }
            },
            errorPlacement: function(error, element) {
                error.addClass("invalid-feedback");
                error.insertAfter(element);
            }
        });
    });
</script>

</body>
</html>
