package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {

        UserServiceImpl usi = new UserServiceImpl();
        usi.createUsersTable();
//        usi.dropUsersTable();
        usi.saveUser("Василий", "Иванов", (byte) 33);
//        usi.removeUserById(1);
//        usi.cleanUsersTable();
        System.out.println(usi.getAllUsers());
    }
}
