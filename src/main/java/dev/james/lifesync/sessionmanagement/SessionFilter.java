package dev.james.lifesync.sessionmanagement;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * @author James Hillyard
 * <p>
 * This Filter is used to ensure a user has a session when trying to access pages in the LifeSync application.
 * <p>
 * This filter applies login verification to all pages under `/hlsp` as these pages require user data.
 * <p>
 * Any pages that do not require user data should be placed at the context root '/'. Frontend assets in
 * `src/main/resources` are also served at the context root. Filtering the context root will block the frontend
 * accessing these compoenents
 */
@WebFilter(filterName = "SessionFilter", urlPatterns = "/hlsp/*")
public class SessionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        HttpSession session = httpRequest.getSession(false); // Do not create a new session if it doesn't exist
        if (session != null && session.getAttribute("user") != null) {
            // User has a session, proceed with the request
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            // No session, redirect to the login servlet
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        }
    }
}
