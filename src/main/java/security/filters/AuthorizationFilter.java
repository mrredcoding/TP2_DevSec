package security.filters;

import exceptions.ForbiddenException;
import exceptions.GlobalExceptionHandler;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

import java.io.IOException;

public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, java.io.IOException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        if (path.startsWith("/admin"))
            checkAdmin(httpRequest, httpResponse);

        chain.doFilter(request, response);
    }

    private void checkAdmin(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
        HttpSession session = httpRequest.getSession(false);
        Object principal = (session != null) ? session.getAttribute("principal") : null;

        if (!(principal instanceof User user) || !user.isAdmin())
            GlobalExceptionHandler.handleException(new ForbiddenException(), httpResponse);
    }
}