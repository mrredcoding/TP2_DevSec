package controllers;

import exceptions.BaseException;
import exceptions.GlobalExceptionHandler;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Bank;

import java.io.IOException;

@WebServlet("/accounts/transfer")
public class TransactionServlet extends HttpServlet {

    private Bank bank;

    @Override
    public void init() {
        this.bank = Bank.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            int accountId = Integer.parseInt(request.getParameter("pAccountId"));
            String amountExpression = request.getParameter("pAmountExpression");

            bank.updateBankAccount(accountId, amountExpression);

            response.sendRedirect(request.getContextPath()
                    + "/accounts?messageType=success&message=Transfer+completed");
        } catch (BaseException exception) {
            GlobalExceptionHandler.resolveException(exception, response);
        }
    }
}