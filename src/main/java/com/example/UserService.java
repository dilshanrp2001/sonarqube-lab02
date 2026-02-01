package main.java.com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserService {

    private String getPassword() {
        return System.getenv("DB_PASSWORD");
    }

    // VULNERABILITY: SQL Injection
    public void findUser(String username) throws SQLException {

        try (Connection conn =
            DriverManager.getConnection("jdbc:mysql://localhost/db",
                    "root", getPassword());
             PreparedStatement st = conn.prepareStatement("SELECT * FROM users WHERE name = ?")) {

            st.setString(1, username);
            st.executeQuery();
        }
    }

    // SMELL: Unused method
    public void notUsed() {
        System.out.println("I am never called");
    }
    // EVEN WORSE: another SQL injection
    public void deleteUser(String username) throws SQLException {
        try (Connection conn =
            DriverManager.getConnection("jdbc:mysql://localhost/db",
                    "root", getPassword());
             PreparedStatement st = conn.prepareStatement("DELETE FROM users WHERE name = ?")) {
            st.setString(1, username);
            st.execute();
        }
    }


}
