package exceptions;

@ResponseStatus(status=400, reason="Bad Request")
public class TransactionFailedException extends BaseException {
    public TransactionFailedException(String message) {
        super(message);
    }
}
