package exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Map;

/**
 *
 * @author Alonso Cédric
 * @author Gomez Guillaume
 * @version 1.0
 */
public class ExceptionResolver {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void resolve(Exception exception, HttpExchange exchange) throws IOException {
        String message = exception.getMessage();
        Integer status = exception.getClass().getAnnotation(ResponseStatus.class).status();

        Map<String, Object> body = Map.of(
                "message", message,
                "status", status,
                "exception", ex.getClass().getSimpleName()
        );

        byte[] bytes = objectMapper.writeValueAsBytes(body);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.getResponseBody().close();
    }
}