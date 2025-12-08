package controllers.exceptions;

/**
*
 * @author Alonso Cédric
 * @author Gomez Guillaume
 * @version 1.0
*/

@ResponseStatus(status=429 , reason="Too Many Requests")
public class LimiteNbRequestsExceededException extends BaseException {

    private final Integer LIMIT = 3

	public LimiteNbRequestsExceededException(String message) {
		super ("Request limit has been reached (" + LIMIT + " requests/minute) !");
	}
}