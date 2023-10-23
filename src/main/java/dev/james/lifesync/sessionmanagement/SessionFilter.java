package dev.james.lifesync.sessionmanagement;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

/**
 * @author James Hillyard
 * <p>
 * This Filter is used to ensure a user has a session when trying to access pages in the LifeSync application.
 * <p>
 * This filter excludes `/login` and `/login.jsp` to prevent a too many redirects error, as these pages don't require
 * a session to be accessed. This is hardcoded and would need expanding if there are more pages that do not require a
 * session
 */
@WebFilter(filterName = "SessionFilter", urlPatterns = "/*")
public class SessionFilter implements Filter {

    private final List<String> excludedPaths = List.of("/login", "/login.jsp", ".css", ".jpg");


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        // Don't session check anything in the excluded paths.
        String requestURI = httpRequest.getRequestURI();
        for (String excludedPath : excludedPaths) {
            if (requestURI.endsWith(excludedPath)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

        HttpSession session = httpRequest.getSession(false); // Do not create a new session if it doesn't exist
        if (session != null && session.getAttribute("user") != null) {
            // User has a session, proceed with the request
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            // No session, redirect to the login servlet
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        }
    }

    @Override
    public void destroy() {
        // No cleanup required
    }
}
