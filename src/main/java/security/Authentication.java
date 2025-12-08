package security;

import exceptions.UserAuthenticationException;
import exceptions.UserAuthorizationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import model.User;

/**
*
* @author ouziri
* @version 0.1
*/

public class Authentication {

	private static final Authentication _singleton = new Authentication();

	private Authentication() {
	}

	public static Authentication getInstance() {
		return _singleton;
	}	

	public User getAuthenticatedUser(HttpServletRequest request) throws UserAuthenticationException {
		HttpSession session = request.getSession(false);
		if (session == null ||  session.getAttribute("principal") == null)
			throw new UserAuthenticationException ();
		return (User) session.getAttribute("principal");
	}

	public void getAuthorization (User user, String authrizedRoles) throws UserAuthorizationException {
		String [] userRoles = user.getRoles().toUpperCase().split(",");
		for (String userRole : userRoles) {
			if (authrizedRoles.toUpperCase().contains(userRole))
				return ;
		}
		throw new UserAuthorizationException();
	}
}
