package exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

public class GlobalExceptionHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void handleException(BaseException exception, HttpServletResponse response)
            throws IOException {

        writeBaseExceptionResponse(exception, response);
    }

    private static void writeBaseExceptionResponse(BaseException exception, HttpServletResponse response)
            throws IOException {

        ResponseStatus annotation = exception.getClass().getAnnotation(ResponseStatus.class);

        int status = annotation.status();
        String reason = annotation.reason();

        response.setStatus(status);
        response.setContentType("application/json");

        Map<String, Object> body = Map.of(
                "message", exception.getMessage(),
                "status", status,
                "reason", reason
        );

        writeJson(response, body);
    }

    private static void writeJson(HttpServletResponse resp, Map<String, Object> body)
            throws IOException {
        String jsonOutput = objectMapper.writeValueAsString(body);

        resp.getWriter().write(jsonOutput);
    }
}