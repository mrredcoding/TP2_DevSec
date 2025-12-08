package security.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class LogoutFilter implements Filter {
    public void init(FilterConfig cfg) {}
    public void destroy() {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (request instanceof HttpServletRequest req &&
                response instanceof HttpServletResponse res &&
                req.getRequestURI().endsWith("/logout")) {

            HttpSession session = req.getSession(false);
            if (session != null) session.invalidate();

            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    c.setValue("");
                    c.setPath("/");
                    c.setMaxAge(0);
                    res.addCookie(c);
                }
            }
        }
        chain.doFilter(request, response);
    }
}