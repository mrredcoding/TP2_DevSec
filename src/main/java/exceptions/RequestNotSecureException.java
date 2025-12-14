package exceptions;

@ResponseStatus(status=400, reason="Bad Request")
public class RequestNotSecureException extends BaseException {
    public RequestNotSecureException() {
        super("HTTPS is required.");
    }
}