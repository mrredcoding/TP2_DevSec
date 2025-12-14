package exceptions;

/**
 *
 * @author Alonso Cédric
 * @author Gomez Guillaume
 * @version 1.0
 */

@ResponseStatus(status=404, reason="Not Found")
public class UserNotFoundException extends BaseException {

    public UserNotFoundException(String email) {
        super ("No user found with email: " + email);
    }
}