package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;


public class Main {
    public static void main(String[] args) {

        UserServiceImpl usi = new UserServiceImpl();
        usi.createUsersTable();
        usi.saveUser("Василий", "Иванов", (byte) 33);
        usi.removeUserById(1);

        List<User> users = usi.getAllUsers();
        for(User user : users){
            System.out.println(user);
        }

        usi.cleanUsersTable();
        usi.dropUsersTable();
    }
}
