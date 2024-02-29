<%@ page import="dev.james.lifesync.cookie.ThemeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <!-- Add Bootstrap CSS & JS link -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <!-- Add Bootstrap Icons link -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
    <link rel="stylesheet" href="/css/footer.css">
</head>
<body>
<div class="container">
    <footer class="d-flex flex-wrap justify-content-between align-items-center py-3 my-4 border-top footer">
        <div class="col-md-4 d-flex">
            <span class="mb-3 mb-md-0">© 2024 LifeSync</span>
        </div>

        <div class="col-md-4 d-flex align-items-center justify-content-center">
            <img src="/assets/logo.png" width="40" height="40" alt="Logo">
        </div>

        <div class="col-md-4 d-flex justify-content-end align-items-center">
            <a href="/hlsp/cookie/theme" onclick="changeIcon(this)"><i id="icon" class="bi bi-brightness-high-fill"></i></a>
        </div>
    </footer>
</div>

<%-- Used to set the Icon to Sun / Moon based on the cookie when the page loads --%>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        var theme = '<%= ThemeUtil.getTheme(request) %>';
        var icon = document.getElementById('icon');
        if (theme === 'light') {
            icon.className = 'bi bi-brightness-high-fill';
        } else if (theme === 'dark') {
            icon.className = 'bi bi-moon-fill';
        }
    });
</script>
<script>
    function changeIcon(element) {
        var icon = document.getElementById('icon');
        var htmlTag = document.getElementsByTagName('html')[0];

        var theme = '<%= ThemeUtil.getTheme(request) %>';
        if (theme === 'light') {
            icon.className = 'bi bi-brightness-high-fill';
            htmlTag.setAttribute('data-bs-theme', 'light');
        } else if (theme === 'dark') {
            icon.className = 'bi bi-moon-fill';
            htmlTag.setAttribute('data-bs-theme', 'dark');
        }
    }
</script>


</body>
</html>


