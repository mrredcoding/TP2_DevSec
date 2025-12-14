package security.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.HttpMethod;

import java.io.IOException;

public class LogoutFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();

        if (HttpMethod.POST.getValue().equalsIgnoreCase(method) && path.equals(httpRequest.getContextPath() + "/auth/logout")) {
            var session = httpRequest.getSession(false);
            if (session != null) session.invalidate();
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/index.jsp?messageType=success&message=Successful+logout");
            return;
        }

        chain.doFilter(request, response);
    }
}