package security.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CookieSecurityFilter implements Filter {
    public void init(FilterConfig cfg) {}
    public void destroy() {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (response instanceof HttpServletResponse res) {
            res.setHeader("X-Content-Type-Options", "nosniff");
            res.setHeader("X-Frame-Options", "DENY");
            res.setHeader("Referrer-Policy", "no-referrer");
            res.setHeader("X-XSS-Protection", "1; mode=block");
        }
        chain.doFilter(request, response);
    }
}