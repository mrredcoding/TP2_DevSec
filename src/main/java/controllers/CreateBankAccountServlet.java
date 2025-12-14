package controllers;

import exceptions.BaseException;
import exceptions.GlobalExceptionHandler;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Bank;
import model.User;
import security.services.AuthenticationService;

import java.io.IOException;

@WebServlet("/accounts/create")
public class CreateBankAccountServlet extends HttpServlet {

    private Bank bank;
    private AuthenticationService authenticationService;

    @Override
    public void init() {
        this.bank = Bank.getInstance();
        this.authenticationService = AuthenticationService.getInstance();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            User user = authenticationService.getPrincipal(request);
            bank.createAccount(user.getEmail());

            response.sendRedirect(request.getContextPath()
                    + "/accounts?messageType=success&message=Account+created");
        } catch (BaseException exception) {
            GlobalExceptionHandler.handleException(exception, response);
        }
    }
}