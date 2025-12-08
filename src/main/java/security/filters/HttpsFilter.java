package security.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class HttpsFilter implements Filter {
    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (request instanceof HttpServletRequest req &&
                response instanceof HttpServletResponse res) {

            if (!req.isSecure()) {
                res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                res.setContentType("text/plain;charset=UTF-8");
                res.getWriter().write("HTTPS required. Use https://");
                return;
            }
        }
        chain.doFilter(request, response);
    }
}