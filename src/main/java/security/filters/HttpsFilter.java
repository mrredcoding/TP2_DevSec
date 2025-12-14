package security.filters;

import exceptions.GlobalExceptionHandler;
import exceptions.RequestNotSecureException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class HttpsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (!httpRequest.isSecure()) {
            GlobalExceptionHandler.resolveException(new RequestNotSecureException(), httpResponse);
            return;
        }

        filterChain.doFilter(request, response);
    }
}