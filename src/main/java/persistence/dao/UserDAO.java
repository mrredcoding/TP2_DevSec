package persistence.dao;

import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import persistence.hibernate.HibernateUtil;

public class UserDAO {

    public void save(User u) {
        Transaction transaction;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(u);
            transaction.commit();
        }
    }

    public User findByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(User.class, email);
        }
    }

    public User findByEmailAndPwd(String email, String pwd) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM User user WHERE user.email = :email AND user.password = :pwd", User.class)
                    .setParameter("email", email)
                    .setParameter("pwd", pwd)
                    .uniqueResult();
        }
    }
}
