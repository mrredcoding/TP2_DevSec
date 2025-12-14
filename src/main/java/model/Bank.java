package model;

import exceptions.BaseException;
import exceptions.TransactionFailedException;
import exceptions.UserNotFoundException;
import persistence.IPersistence;
import persistence.hibernate.HibernateMainPersistence;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
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

    public void createAccount(String ownerEmail) throws BaseException {
        if (ownerEmail == null || ownerEmail.trim().isEmpty())
            throw new TransactionFailedException("Owner email is empty");

        User owner = persistence.findUserByEmail(ownerEmail);
        if (owner == null)
            throw new UserNotFoundException(ownerEmail);

        persistence.save(new Account(owner));
    }

    public Account findAccountById(int accountId) throws BaseException {
        return persistence.findAccountById(accountId);
    }

    public List<Account> findAccountByOwnerEmail(String email) throws BaseException {
        return persistence.findAccountByOwnerEmail(email);
    }

    public User validateUserByEmailPassword(String email, String pwd) throws BaseException {
        User user = persistence.validateUserByEmailPassword(email, pwd);
        if (user == null)
            throw new UserNotFoundException(email);
        return user;
    }

    public void updateBankAccount(int accountId, String amountExpression) throws BaseException {
        double amount;
        try {
            amount = evaluate(amountExpression);
        } catch (ScriptException exception) {
            throw new TransactionFailedException(exception.getMessage());
        }
        persistence.updateBalance(accountId, amount);
    }

    private double evaluate(String expr) throws ScriptException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("js");
        Number result = (Number) engine.eval(expr);
        return result.doubleValue();
    }
}