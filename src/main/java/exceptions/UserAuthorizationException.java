package security.exceptions;

import model.User;

/**
*
 * @author Alonso Cédric
 * @author Gomez Guillaume
 * @version 1.0
*/

@ResponseStatus(status=403, reason="Forbidden")
public class UserAuthorizationException extends BaseException {
	
	private User user;

	public UserAthorizationException(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}

}
