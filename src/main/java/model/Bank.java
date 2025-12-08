package model;

import exceptions.UserNotFoundException;
import persistence.IPersistence;
import persistence.hibernate.HibernateMainPersistence;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.List;

/**
*
* @author ouziri
* @version 0.1
*/

public class Bank {

    private final IPersistence persistence = new HibernateMainPersistence();
    private static final Bank instance = new Bank();

    private Bank() {}

    public static Bank getInstance() {
        return instance;
    }

    public Account createAccount(String ownerEmail) throws Exception {
        if (ownerEmail == null || ownerEmail.trim().isEmpty())
            throw new IllegalArgumentException("Owner email is empty");

        User owner = persistence.findUserByEmail(ownerEmail);
        if (owner == null)
            throw new UserNotFoundException(ownerEmail);

        return persistence.save(new Account(owner));
    }

    public Account findAccountById(int accountId) throws Exception {
        return persistence.findAccountById(accountId);
    }

    public List<Account> findAccountByOwnerEmail(String email) throws Exception {
        return persistence.findAccountByOwnerEmail(email);
    }

    public User validateUserByEmailPassword(String email, String pwd) throws Exception {
        User user = persistence.validateUserByEmailPassword(email, pwd);
        if (user == null)
            throw new UserNotFoundException(email);
        return user;
    }

    public void updateBankAccount(int accountId, String amountExpression) throws Exception {
        double amount = evaluate(amountExpression);
        persistence.updateBalance(accountId, amount);
    }

    private double evaluate(String expr) throws Exception {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("js");
        Number result = (Number) engine.eval(expr);
        return result.doubleValue();
    }
}