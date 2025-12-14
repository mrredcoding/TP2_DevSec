package exceptions;
/**
*
 * @author Alonso Cédric
 * @author Gomez Guillaume
 * @version 1.0
*/

@ResponseStatus(status=401, reason="Unauthorized")
public class UnauthorizedException extends BaseException {
    public UnauthorizedException() {
        super("You are not authorized to access this resource.");
    }
}
