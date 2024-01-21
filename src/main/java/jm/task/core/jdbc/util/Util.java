package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    private final String URL = "jdbc:mysql://localhost:3306/tst";
    private final String USERNAME = "root";
    private final String PASSWORD = "1234";

    private Connection connection;

    public Util() {
        try {
            connection = DriverManager
                    .getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Соеденились");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

}
