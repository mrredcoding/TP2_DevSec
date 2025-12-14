package exceptions;

@ResponseStatus(status=403, reason="Forbidden")
public class ForbiddenException extends BaseException {
    public ForbiddenException() {
        super("You are not authorized to access this resource.");
    }
}