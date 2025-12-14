package exceptions;

@ResponseStatus(status=404, reason="Not Found")
public class AccountNotFoundException extends BaseException {
    public AccountNotFoundException(String cause) {
        super("No account found " + cause);
    }
}