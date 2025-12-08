package security.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AuthenticationFilter implements Filter {
    public void init(FilterConfig cfg) {}
    public void destroy() {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        Object user = (session != null) ? session.getAttribute("user") : null;

        if (user == null) {
            res.sendRedirect(req.getContextPath() +
                    "/login?msg=Authentication+required");
            return;
        }

        chain.doFilter(request, response);
    }
}