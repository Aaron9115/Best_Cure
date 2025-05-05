package com.islington.util;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Utility class for handling redirections and setting messages.
 * Updated by Anish
 */
public class RedirectionUtil {

    private static final String baseUrl = "/WEB-INF/pages/";

    // Constants for internal JSP paths (used with forwarding)
    public static final String registerUrl = baseUrl + "register.jsp";
    public static final String loginUrl = baseUrl + "login.jsp";
    public static final String homeUrl = baseUrl + "home.jsp";

    // Set message attribute
    public void setMsgAttribute(HttpServletRequest req, String msgType, String msg) {
        req.setAttribute(msgType, msg);
    }

    // Forward to a JSP page
    public void forwardToPage(HttpServletRequest req, HttpServletResponse resp, String jspPage)
            throws ServletException, IOException {
        req.getRequestDispatcher(jspPage).forward(req, resp);
    }

    // Set message and forward to a page
    public void setMsgAndForward(HttpServletRequest req, HttpServletResponse resp, String msgType, String msg,
            String jspPage) throws ServletException, IOException {
        setMsgAttribute(req, msgType, msg);
        forwardToPage(req, resp, jspPage);
    }

    // Send a redirect to a new URL (outside /WEB-INF)
    public void redirectToUrl(HttpServletRequest req, HttpServletResponse resp, String url)
            throws IOException {
        resp.sendRedirect(req.getContextPath() + url);
    }

    // Set a session message (optional) and redirect to a new URL
    public void setSessionMsgAndRedirect(HttpServletRequest req, HttpServletResponse resp,
            String msgType, String msg, String url) throws IOException {
        req.getSession().setAttribute(msgType, msg);
        redirectToUrl(req, resp, url);
    }
}
