package security.filters;

import exceptions.GlobalExceptionHandler;
import exceptions.TooManyRequestsException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import security.services.RateLimiterService;

import java.io.IOException;

public class RateLimitFilter implements Filter {

    private final RateLimiterService limiter = RateLimiterService.getInstance();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException{

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String key = resolveKey(httpRequest);

        if (!limiter.tryConsume(key)) {
            GlobalExceptionHandler.handleException(new TooManyRequestsException(), httpResponse);
            return;
        }

        chain.doFilter(request, response);
    }

    private String resolveKey(HttpServletRequest req) {
        var session = req.getSession(false);
        if (session != null) {
            Object principal = session.getAttribute("principal");
            if (principal instanceof User user) return "USER:" + user.getId();
        }
        return "IP:" + req.getRemoteAddr();
    }
}
