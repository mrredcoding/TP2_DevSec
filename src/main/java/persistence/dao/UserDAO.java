package persistence.dao;

import exceptions.BaseException;
import exceptions.TransactionFailedException;
import exceptions.UserNotFoundException;
import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import persistence.hibernate.HibernateUtil;

public class UserDAO {

    public User save(User user) throws BaseException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            return user;
        } catch (Exception exception) {
            if (transaction != null) transaction.rollback();
            throw new TransactionFailedException(exception.getMessage());
        }
    }

    public User findByEmail(String email) throws BaseException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, email);

            if (user == null)
                throw new UserNotFoundException("with email = " + email);

            return user;
        } catch (Exception exception) {
            throw new TransactionFailedException(exception.getMessage());
        }
    }

    public User findByEmailAndPwd(String email, String pwd) throws BaseException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            User user = session.createQuery(
                            "FROM User u WHERE u.email = :email AND u.password = :pwd", User.class)
                    .setParameter("email", email)
                    .setParameter("pwd", pwd)
                    .uniqueResult();

            if (user == null)
                throw new UserNotFoundException("with email = " + email);

            return user;

        }  catch (Exception exception) {
            throw new TransactionFailedException(exception.getMessage());
        }
    }
}