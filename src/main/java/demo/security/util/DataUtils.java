package demo.security.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataUtils {

    public static ResultSet findUser(Connection connection, String username) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery("SELECT * FROM users WHERE username = '" + username + "'");
    }
}
