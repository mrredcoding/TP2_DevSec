package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;
import model.Bank;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
*
* @author ouziri
* @version 0.1
*/

@WebServlet("/transaction")
public class TransactionServlet extends HttpServlet {
		
	private final Bank bank = Bank.getInstance();
	
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {    	
    	int accountId= Integer.parseInt(req.getParameter("pAccountId"));
        String amountExpression = req.getParameter("pAmountExpression");        
        try {
            bank.updateBankAccount(accountId, amountExpression);
            req.setAttribute("TransactionOK", true);
            Account a = bank.findAccountById(accountId);
            List<Account> accounts = new ArrayList<Account>();
            accounts.add(a);
            req.setAttribute("accounts", accounts);
            req.getRequestDispatcher("./view/view_accounts.jsp").forward(req, resp);   
        }
        catch (Exception e) {
        	req.setAttribute("TransactionOK", false);
        } 
    }
}
