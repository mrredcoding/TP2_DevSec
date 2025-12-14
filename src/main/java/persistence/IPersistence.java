package persistence;

import exceptions.BaseException;
import model.Account;
import model.User;

import java.util.List;

/**
 *
 * @author Alonso Cédric
 * @author Gomez Guillaume
 * @version 1.0
 */

public interface IPersistence {

    Account save(Account account) throws BaseException;
    Account findAccountById (int accountId) throws BaseException;
    void updateBalance(int accountId, double newBalance) throws BaseException;
    List<Account> findAccountByOwnerEmail(String ownerEmail) throws BaseException;
    User findUserByEmail(String userEmail) throws BaseException;
    User validateUserByEmailPassword(String ownerEmail, String pwd) throws BaseException;

}