package controllers;

import exceptions.BaseException;
import exceptions.GlobalExceptionHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;
import model.Bank;
import model.User;
import security.services.AuthenticationService;

import java.io.IOException;
import java.util.List;

@WebServlet("/accounts")
public class ViewAccountServlet extends HttpServlet {

    private Bank bank;
    private AuthenticationService authenticationService;

    @Override
    public void init() {
        this.bank = Bank.getInstance();
        this.authenticationService = AuthenticationService.getInstance();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            User user = authenticationService.getPrincipal(request);
            bank.createAccount(user.getEmail());
            List<Account> accounts = bank.findAccountByOwnerEmail(user.getEmail());

            request.setAttribute("accounts", accounts);
            request.getRequestDispatcher("/view/accounts.jsp").forward(request, response);
        } catch (BaseException exception) {
            GlobalExceptionHandler.handleException(exception, response);
        }
    }
}
