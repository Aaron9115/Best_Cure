package com.islington.filter;

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

import com.islington.util.CookieUtil;

@WebFilter(asyncSupported = true, urlPatterns = "/*")
public class AuthenticationFilter implements Filter {

    private static final String LOGIN = "/login";
    private static final String REGISTER = "/register";
    private static final String DASHBOARD = "/dashboard";
    private static final String HOME = "/home";
    private static final String PROFILE = "/profile";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        // Allow static resources without filtering
        if (uri.endsWith(".png") || uri.endsWith(".jpg") || uri.endsWith(".css") || uri.endsWith(".js")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        String userRole = null;

        if (session != null) {
            userRole = (String) session.getAttribute("role");
        }

        if (userRole == null) {
            userRole = CookieUtil.getCookie(req, "role") != null ? CookieUtil.getCookie(req, "role").getValue() : null;
        }

       
        String path = uri.substring(req.getContextPath().length());

        // Admin access logic
        if ("admin".equals(userRole)) {
            if (path.equals(LOGIN) || path.equals(REGISTER)) {
                res.sendRedirect(req.getContextPath() + DASHBOARD);
                return;
            }
            chain.doFilter(request, response);
        }
        // User access logic
        else if ("user".equals(userRole)) {
            if (path.equals(LOGIN) || path.equals(REGISTER)) {
                res.sendRedirect(req.getContextPath() + HOME);
                return;
            }
            chain.doFilter(request, response);
        }
        // Guest (not logged in)
        else {
            // Only restrict /profile or /profile.jsp
            if (path.equals(PROFILE) || path.equals(PROFILE + ".jsp")) {
                res.sendRedirect(req.getContextPath() + LOGIN);
            } else {
                chain.doFilter(request, response); // Allow all other pages
            }
        }
    }

    @Override
    public void destroy() {
        // Cleanup logic, if required
    }
}
