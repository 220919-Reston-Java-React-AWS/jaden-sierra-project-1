package com.revature.repository;

import com.revature.model.User;

import java.sql.*;

public class UserRepository {

    //registration
    public User addUser(User user) throws SQLException { // Create in CRUD

        try (Connection connectionObject = ConnectionFactory.createConnection()) {
            String sql = "insert into users (username, password, role) values (?, ?, ?)";

            // Create PreparedStatement object using a pre-defined template (? are used as placeholders for values)
            PreparedStatement pstmt = connectionObject.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, "Employee");

            int numberOfRecordsAdded = pstmt.executeUpdate(); // applies to INSERT, UPDATE, and DELETE

            // Get automatically generated ID
            ResultSet rs = pstmt.getGeneratedKeys(); // produce a temporary table that contains 1 column and 1 row
            // representing the automatically generated primary key
            rs.next(); // retrieve the first row in the temporary table
            int id = rs.getInt(1); // Get 1st column from temporary table

            return new User(id, user.getUsername(), user.getPassword(), user.getRole());
        }
    }

    //Login
    public User getUserByUsernameAndPassword(String username, String password) throws SQLException {

        try (Connection connectionObj = ConnectionFactory.createConnection()) {
            String sql = "SELECT * FROM users as u WHERE u.username = ? AND u.password = ?";

            PreparedStatement pstmt = connectionObj.prepareStatement(sql);

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String un = rs.getString("username");
                String pw = rs.getString("password");
                String role = rs.getString("role");

                return new User(id, un, pw, role);
            } else {
                return null;
            }

        }
    }

    public User getUserByUsername(String username) throws SQLException {
        try (Connection connectionObj = ConnectionFactory.createConnection()) {
            String sql = "SELECT * FROM users as u WHERE u.username = ?";
            PreparedStatement pstmt = connectionObj.prepareStatement(sql);

            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery(); // ResultSet represents a temporary table that contains all data that we have
            // queried for

            if (rs.next()) { // returns a boolean indicating whether there is a record or not for the "next" row AND iterates to the next row
                int id = rs.getInt("id");
                String un = rs.getString("username");
                String pw = rs.getString("password");
                String role = rs.getString("role");

                return new User(id, un, pw, role);
            } else {
                return null;
            }

        }
    }


}

