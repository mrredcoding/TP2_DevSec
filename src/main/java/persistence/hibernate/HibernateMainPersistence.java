package persistence.hibernate;

import exceptions.BaseException;
import model.Account;
import model.User;
import persistence.IPersistence;
import persistence.dao.AccountDAO;
import persistence.dao.UserDAO;

import java.util.List;

public class HibernateMainPersistence implements IPersistence {

    private final AccountDAO accountDAO = new AccountDAO();
    private final UserDAO userDAO = new UserDAO();

    @Override
    public Account save(Account account) throws BaseException {
        return accountDAO.save(account);
    }

    @Override
    public Account findAccountById(int accountId) throws BaseException {
        return accountDAO.findById(accountId);
    }

    @Override
    public List<Account> findAccountByOwnerEmail(String ownerEmail) throws BaseException {
        return accountDAO.findByOwnerEmail(ownerEmail);
    }

    @Override
    public void updateBalance(int accountId, double newBalance) throws BaseException {
        accountDAO.updateBalance(accountId, newBalance);
    }

    @Override
    public User findUserByEmail(String email) throws BaseException {
        return userDAO.findByEmail(email);
    }

    @Override
    public User validateUserByEmailPassword(String email, String pwd) throws BaseException {
        return userDAO.findByEmailAndPwd(email, pwd);
    }
}
