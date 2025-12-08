package security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.BaseException;
import exceptions.ResponseStatus;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebFilter("/*")
public class GlobalExceptionFilter implements Filter {

    private ObjectMapper objectMapper;

    @Override
    public void init(FilterConfig filterConfig) {
        objectMapper = new ObjectMapper();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException {

        try {
            chain.doFilter(request, response);
        } catch (Exception ex) {
            handleException(ex, (HttpServletResponse) response);
        }
    }

    private void handleException(Exception exception, HttpServletResponse response)
            throws IOException {

        Throwable rootCause = unwrap(exception);

        if (rootCause instanceof BaseException baseException) {
            writeBaseExceptionResponse(baseException, response);
        } else {
            writeGenericInternalError(response);
        }
    }

    private Throwable unwrap(Throwable exception) {
        while (exception instanceof ServletException && exception.getCause() != null) {
            exception = exception.getCause();
        }
        return exception;
    }

    private void writeBaseExceptionResponse(BaseException ex, HttpServletResponse resp)
            throws IOException {

        ResponseStatus annotation = ex.getClass().getAnnotation(ResponseStatus.class);

        int status = annotation.status();
        String reason = annotation.reason();

        resp.setStatus(status);
        resp.setContentType("application/json");

        Map<String, Object> body = Map.of(
                "message", ex.getMessage(),
                "status", status,
                "reason", reason
        );

        writeJson(resp, body);
    }

    private void writeGenericInternalError(HttpServletResponse resp) throws IOException {
        resp.setStatus(500);
        resp.setContentType("application/json");

        Map<String, Object> body = Map.of(
                "message", "Internal server error",
                "status", 500,
                "reason", "Internal Server Error"
        );

        writeJson(resp, body);
    }

    private void writeJson(HttpServletResponse resp, Map<String, Object> body)
            throws IOException {
        String jsonOutput = objectMapper.writeValueAsString(body);

        resp.getWriter().write(jsonOutput);
    }
}