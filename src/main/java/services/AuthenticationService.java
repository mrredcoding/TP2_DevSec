package services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Bank;
import model.User;

public class AuthenticationService {

    private final Bank bank = Bank.getInstance();

    public void login(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String email = req.getParameter("pUserEmail");
        String pwd = req.getParameter("pUserPwd");

        User user = bank.validateUserByEmailPassword(email, pwd);

        HttpSession session = req.getSession(true);
        session.setAttribute("principal", user);

        resp.sendRedirect("./index.jsp?messageType=success&message=Login successful");
    }

    public void logout(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession(false);
        if (session != null) session.invalidate();

        resp.sendRedirect("./index.jsp?messageType=success&message=Logout successful");
    }
}