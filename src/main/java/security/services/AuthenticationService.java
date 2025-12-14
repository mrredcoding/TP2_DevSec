package security.services;

import exceptions.BaseException;
import exceptions.UnauthorizedException;
import exceptions.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import model.User;
import persistence.dao.UserDAO;

public class AuthenticationService {

    private static final AuthenticationService INSTANCE = new AuthenticationService();
    private final UserDAO userDAO = new UserDAO();

    private AuthenticationService() {}

    public static AuthenticationService getInstance() {
        return INSTANCE;
    }

    /**
     * Authenticates a user by email and password.
     * @throws UserNotFoundException if credentials are invalid.
     */
    public User authenticate(String email, String password) throws BaseException {
        return userDAO.findByEmailAndPwd(email, password);
    }

    public User getPrincipal(HttpServletRequest request) throws BaseException {
        HttpSession session = request.getSession(false);
        if (session == null ||  session.getAttribute("principal") == null)
            throw new UnauthorizedException ();
        return (User) session.getAttribute("principal");
    }
}