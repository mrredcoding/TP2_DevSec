package utils;

public enum HttpMethod {

    POST("POST"),
    GET("GET"),
    DELETE("DELETE"),
    PUT("PUT"),
    PATCH("PATCH");

    private final String value;

    HttpMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}