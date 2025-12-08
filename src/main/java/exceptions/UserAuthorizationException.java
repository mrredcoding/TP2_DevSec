package exceptions;

import exceptions.BaseException;
import exceptions.ResponseStatus;
import model.User;

/**
*
 * @author Alonso Cédric
 * @author Gomez Guillaume
 * @version 1.0
*/

@ResponseStatus(status=401, reason="Unauthorized")
public class UserAuthorizationException extends BaseException {

    public UserAuthorizationException() {
        super("You are not authorized to perform this operation.");
    }
}