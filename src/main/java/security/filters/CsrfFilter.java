package security.filters;

import exceptions.ForbiddenException;
import exceptions.GlobalExceptionHandler;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.HttpMethod;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class CsrfFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(true);


        if (HttpMethod.GET.getValue().equalsIgnoreCase(httpRequest.getMethod())) {
            session.setAttribute("CSRF_TOKEN", UUID.randomUUID().toString());
            chain.doFilter(request, response);
            return;
        }

        String token = httpRequest.getParameter("_csrf");
        String expected = (String) session.getAttribute("CSRF_TOKEN");

        if (!Objects.equals(token, expected)) {
            GlobalExceptionHandler.handleException(new ForbiddenException(), httpResponse);
            return;
        }

        chain.doFilter(request, response);
    }
}