package jm.task.core.jdbc.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Util {

    private Connection connection;

    private String url;
    private String username;
    private String password;

    FileInputStream fis;
    Properties properties = new Properties();

    public Util() {
        try {
            fis = new FileInputStream("src/main/resources/config.properties");
            properties.load(fis);

            url = properties.getProperty("db.host");
            username = properties.getProperty("db.user");
            password = properties.getProperty("db.pass");

            connection = DriverManager
                    .getConnection(url, username, password);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
