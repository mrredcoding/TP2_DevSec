package persistence.dao;

import exceptions.AccountNotFoundException;
import exceptions.BaseException;
import exceptions.TransactionFailedException;
import model.Account;
import org.hibernate.Session;
import org.hibernate.Transaction;
import persistence.hibernate.HibernateUtil;

import java.util.List;

public class AccountDAO {

    public Account save(Account account) throws BaseException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(account);
            transaction.commit();
            return account;
        } catch (Exception exception) {
            if (transaction != null) transaction.rollback();
            throw new TransactionFailedException(exception.getMessage());
        }
    }

    public Account findById(int id) throws BaseException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Account account = session.get(Account.class, id);

            if (account == null)
                throw new AccountNotFoundException("with id = " + id);

            return account;

        } catch (Exception exception) {
            throw new TransactionFailedException(exception.getMessage());
        }
    }

    public List<Account> findByOwnerEmail(String ownerEmail) throws BaseException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            List<Account> result = session.createQuery(
                            "FROM Account a WHERE a.owner.email = :email", Account.class)
                    .setParameter("email", ownerEmail)
                    .list();

            if (result == null || result.isEmpty())
                throw new AccountNotFoundException("for owner email = " + ownerEmail);

            return result;
        } catch (Exception exception) {
            throw new TransactionFailedException(exception.getMessage());
        }
    }

    public void updateBalance(int accountId, double amount) throws BaseException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Account account = session.get(Account.class, accountId);
            account.credit(amount);
            session.update(account);
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null) transaction.rollback();
            throw new TransactionFailedException(exception.getMessage());
        }
    }
}