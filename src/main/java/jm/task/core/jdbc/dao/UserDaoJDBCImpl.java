package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Util util = new Util();
        String str = """
                CREATE TABLE IF NOT EXISTS USERS (
                            id BIGINT PRIMARY KEY  NOT NULL AUTO_INCREMENT,
                            name VARCHAR(255) NOT NULL,
                            lastName VARCHAR(255) NOT NULL,
                            age TINYINT UNSIGNED);""";

        try (Statement statement = util.getConnection().createStatement()) {
            statement.executeUpdate(str);
            util.getConnection().close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void dropUsersTable() {
        Util util = new Util();
        String sql = "DROP TABLE IF EXISTS USERS;";

        try (Statement statement = util.getConnection().createStatement()) {
            statement.executeUpdate(sql);
            util.getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Util util = new Util();
        String sql = "INSERT INTO USERS (name, lastName, age) values(?,?,?)";

        try {
            PreparedStatement preparedStatement = util.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            util.getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void removeUserById(long id) {
        Util util = new Util();
        String sql = "DELETE FROM USERS WHERE id = ?";

        try {
            PreparedStatement preparedStatement = util.getConnection().prepareStatement(sql);
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            util.getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        Util util = new Util();
        String sql = "SELECT * FROM USERS";
        List<User> list = new ArrayList<>();


        try (Statement statement = util.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));

                list.add(user);
            }
            util.getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void cleanUsersTable() {
        Util util = new Util();
        String query = "TRUNCATE TABLE USERS";

        try (Statement statement = util.getConnection().createStatement()) {
            statement.executeUpdate(query);

            statement.close();
            util.getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

