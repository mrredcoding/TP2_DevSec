package security.filters;

import exceptions.BaseException;
import exceptions.GlobalExceptionHandler;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import security.services.AuthenticationService;
import utils.HttpMethod;

import java.io.IOException;

public class LoginFilter implements Filter {

    private final AuthenticationService authenticationService = AuthenticationService.getInstance();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();

        if (HttpMethod.POST.getValue().equalsIgnoreCase(method) && path.equals(httpRequest.getContextPath() + "/auth/login")) {
            String username = httpRequest.getParameter("pUserEmail");
            String password = httpRequest.getParameter("pUserPwd");

            try {
                User user = authenticationService.authenticate(username, password);
                httpRequest.getSession(true).setAttribute("principal", user);
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/index.jsp?messageType=success&message=Successful+connection");
            } catch (BaseException exception) {
                GlobalExceptionHandler.handleException(exception, httpResponse);
            }
            return;
        }

        chain.doFilter(request, response);
    }
}