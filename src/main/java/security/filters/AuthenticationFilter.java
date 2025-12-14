package security.filters;

import exceptions.GlobalExceptionHandler;
import exceptions.UnauthorizedException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, java.io.IOException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        if (isPublic(path)) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = httpRequest.getSession(false);
        Object principal = (session != null) ? session.getAttribute("principal") : null;

        if (!(principal instanceof User)) {
            GlobalExceptionHandler.resolveException(new UnauthorizedException(), httpResponse);
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean isPublic(String path) {
        return path.startsWith("/auth")
                || path.startsWith("/view")
                || path.endsWith(".jsp")
                || path.startsWith("/css")
                || path.startsWith("/js")
                || path.startsWith("/images");
    }
}