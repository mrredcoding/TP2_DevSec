package security.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class LoginSessionFilter implements Filter {
    public void init(FilterConfig cfg) {}
    public void destroy() {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (request instanceof HttpServletRequest req) {
            if (req.getRequestURI().endsWith("/login")) {
                HttpSession session = req.getSession(false);
                if (session != null) session.invalidate();
                req.getSession(true);
            }
        }
        chain.doFilter(request, response);
    }
}