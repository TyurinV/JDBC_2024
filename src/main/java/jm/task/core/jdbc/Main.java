package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class Main {
    public static void main(String[] args) {

//        UserServiceImpl usi = new UserServiceImpl();
//        usi.createUsersTable();
//////        usi.dropUsersTable();
//        usi.saveUser("Василий", "Иванов", (byte) 33);
////        usi.removeUserById(1);
//        usi.cleanUsersTable();
        Configuration configuration = new Configuration().addAnnotatedClass(User.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        User user = session.get(User.class, 1L);
        System.out.println(user);
        session.getTransaction().commit();

        sessionFactory.close();

    }
}
