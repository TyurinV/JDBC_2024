package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
        try {
            openCurrentSession().beginTransaction();
            String sql = """
                    CREATE TABLE IF NOT EXISTS USERS (
                                id BIGINT PRIMARY KEY  NOT NULL AUTO_INCREMENT,
                                name VARCHAR(255) NOT NULL,
                                lastName VARCHAR(255) NOT NULL,
                                age TINYINT UNSIGNED);""";
            getCurrentSession().createSQLQuery(sql).executeUpdate();
            getCurrentSession().getTransaction().commit();
        } finally {
            closeCurrentSession();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            openCurrentSession().beginTransaction();
            String sql = "DROP TABLE IF EXISTS USERS;";
            getCurrentSession().createSQLQuery(sql).executeUpdate();
            getCurrentSession().getTransaction().commit();
        } finally {
            closeCurrentSession();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            openCurrentSession().beginTransaction();
            getCurrentSession().save(new User(name, lastName, age));
            getCurrentSession().getTransaction().commit();
        } finally {
            closeCurrentSession();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            openCurrentSession().beginTransaction();
            User user = getCurrentSession().get(User.class, id);
            getCurrentSession().delete(user);
            getCurrentSession().getTransaction().commit();
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
        try {
            openCurrentSession().beginTransaction();
            String hql = "delete from User";
            getCurrentSession().createQuery(hql).executeUpdate();
            getCurrentSession().getTransaction().commit();
        } finally {
            closeCurrentSession();
        }
    }
}
