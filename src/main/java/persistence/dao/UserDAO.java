package persistence.dao;

import exceptions.BaseException;
import exceptions.TransactionFailedException;
import exceptions.UserNotFoundException;
import model.User;
import org.hibernate.Session;
import persistence.hibernate.HibernateUtil;

public class UserDAO {

    public User findByEmail(String email) throws BaseException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, email);

            if (user == null)
                throw new UserNotFoundException(email);

            return user;
        } catch (Exception exception) {
            throw new TransactionFailedException(exception.getMessage());
        }
    }

    public User findByEmailAndPwd(String email, String pwd) throws BaseException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            User user = session.createQuery(
                            "FROM User user WHERE user.email = :email AND user.password = :pwd", User.class)
                    .setParameter("email", email)
                    .setParameter("pwd", pwd)
                    .uniqueResult();

            if (user == null)
                throw new UserNotFoundException(email);

            return user;

        }  catch (Exception exception) {
            throw new TransactionFailedException(exception.getMessage());
        }
    }
}