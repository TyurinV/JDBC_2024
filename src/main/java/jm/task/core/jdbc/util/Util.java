package jm.task.core.jdbc.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Util {

    private Connection connection;

    private final String URL;
    private final String USERNAME;
    private final String PASSWORD;

    FileInputStream fis;
    Properties properties = new Properties();

    public Util() {
        try {
            fis = new FileInputStream("src/main/resources/config.properties");
            properties.load(fis);

            URL = properties.getProperty("db.host");
            USERNAME = properties.getProperty("db.user");
            PASSWORD = properties.getProperty("db.pass");
            System.out.println(URL + " " + USERNAME);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            connection = DriverManager
                    .getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
