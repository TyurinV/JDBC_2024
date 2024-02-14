package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private Session currentSession;

    private static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration().addAnnotatedClass(User.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        return sessionFactory;
    }

    public Session openCurrentSession() {
        currentSession = getSessionFactory().openSession();
        return currentSession;
    }

    public void closeCurrentSession() {
        currentSession.close();
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try {
            transaction = openCurrentSession().beginTransaction();
            String sql = """
                    CREATE TABLE IF NOT EXISTS USERS (
                                id BIGINT PRIMARY KEY  NOT NULL AUTO_INCREMENT,
                                name VARCHAR(255) NOT NULL,
                                lastName VARCHAR(255) NOT NULL,
                                age TINYINT UNSIGNED);""";
            getCurrentSession().createSQLQuery(sql).executeUpdate();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            closeCurrentSession();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try {
            openCurrentSession().beginTransaction();
            String sql = "DROP TABLE IF EXISTS USERS;";
            getCurrentSession().createSQLQuery(sql).executeUpdate();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            closeCurrentSession();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try {
            openCurrentSession().beginTransaction();
            getCurrentSession().save(new User(name, lastName, age));
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            closeCurrentSession();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try {
            openCurrentSession().beginTransaction();
            User user = getCurrentSession().get(User.class, id);
            getCurrentSession().delete(user);
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            closeCurrentSession();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try {
            openCurrentSession().beginTransaction();
            List<User> users = getCurrentSession().createQuery("from User").getResultList();
            getCurrentSession().getTransaction().commit();
            return users;
        } finally {
            closeCurrentSession();
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try {
            openCurrentSession().beginTransaction();
            String hql = "delete from User";
            getCurrentSession().createQuery(hql).executeUpdate();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            closeCurrentSession();
        }
    }
}
