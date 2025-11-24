package persistence.dao;

import model.Account;
import org.hibernate.Session;
import org.hibernate.Transaction;
import persistence.hibernate.HibernateUtil;

import java.util.List;

public class AccountDAO {

    public Account save(Account account) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(account);
            transaction.commit();
            return account;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public Account findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Account.class, id);
        }
    }

    public List<Account> findByOwnerEmail(String ownerEmail) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Account account WHERE account.owner.email = :email", Account.class)
                    .setParameter("email", ownerEmail)
                    .list();
        }
    }

    public void updateBalance(int accountId, double amount) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Account account = session.get(Account.class, accountId);
            account.credit(amount);
            session.update(account);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
}
