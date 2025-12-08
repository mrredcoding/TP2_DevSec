package exceptions;

/**
*
 * @author Alonso Cédric
 * @author Gomez Guillaume
 * @version 1.0
*/

@ResponseStatus(status=429 , reason="Too Many Requests")
public class LimitNbRequestsExceededException extends BaseException {

    private static final Integer LIMIT = 3;

	public LimitNbRequestsExceededException() {
		super ("Request limit has been reached (" + LIMIT + " requests/minute) !");
	}
}