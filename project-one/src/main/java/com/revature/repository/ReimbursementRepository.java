package com.revature.repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.revature.model.Reimbursement;
import com.revature.model.User;

public class ReimbursementRepository {
    public List<Reimbursement> getAllReimbursements() throws SQLException {
        try (Connection connectionObject = ConnectionFactory.createConnection()) {

            List<Reimbursement> reimbursements = new ArrayList<>();

            String sql = "SELECT * FROM reimbursements";

            Statement stmt = connectionObject.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            // ResultSet represents the temporary table of query results

            // Iterating through the ResultSet
            while (rs.next()) {
                int id = rs.getInt("id");
                double amount = rs.getDouble("amount");
                String description = rs.getString("description");
                String status = rs.getString("status");
                int userId = rs.getInt("userId");

                Reimbursement reimbursement = new Reimbursement(id, amount, description, status, userId);

                reimbursements.add(reimbursement); // add assignment to List
            }

            return reimbursements;
        }
    }

    public List<Reimbursement> getPendingReimbursements() throws SQLException {
        try (Connection connectionObject = ConnectionFactory.createConnection()) {

            List<Reimbursement> reimbursements = new ArrayList<>();

            String sql = "SELECT * FROM reimbursements WHERE status = 'Pending'";

            PreparedStatement pstmt = connectionObject.prepareStatement(sql);



            ResultSet rs = pstmt.executeQuery();
            // ResultSet represents the temporary table of query results

            // Iterating through the ResultSet
            while (rs.next()) {
                int id = rs.getInt("id");
                double amount = rs.getDouble("amount");
                String description = rs.getString("description");
                String status = rs.getString("status");
                int userId = rs.getInt("userId");

                Reimbursement reimbursement = new Reimbursement(id, amount, description, status, userId);

                reimbursements.add(reimbursement); // add assignment to List
            }

            return reimbursements;
        }
    }

    public List<Reimbursement> getAllReimbursementsForUser(int userId) throws SQLException {
        try (Connection connectionObject = ConnectionFactory.createConnection()) {

            List<Reimbursement> reimbursements = new ArrayList<>();

            String sql = "SELECT * FROM reimbursements WHERE userId = ? ORDER BY id ASC";

            PreparedStatement pstmt = connectionObject.prepareStatement(sql);

            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();
            // ResultSet represents the temporary table of query results

            // Iterating through the ResultSet
            while (rs.next()) {
                int id = rs.getInt("id");
                double amount = rs.getDouble("amount");
                String description = rs.getString("description");
                String status = rs.getString("status");
                int uId = rs.getInt("userId");

                Reimbursement reimbursement = new Reimbursement(id, amount, description, status, uId);

                reimbursements.add(reimbursement); // add assignment to List
            }

            return reimbursements;
        }
    }

    public boolean changeReimbursementStatus(int reimbursementId,String status) throws SQLException {
        try (Connection connectionObj = ConnectionFactory.createConnection()) {
            String sql = "UPDATE reimbursements SET status = ? WHERE id = ?";

            PreparedStatement pstmt = connectionObj.prepareStatement(sql);
            pstmt.setString(1, status);
            pstmt.setInt(2, reimbursementId);

            int numberOfRecordsUpdated = pstmt.executeUpdate();

            return numberOfRecordsUpdated == 1;
        }
    }

    // Returns either
    // 1. An assignment object
    // 2. null
    public Reimbursement getReimbursementById(int id) throws SQLException {
        try (Connection connectionObj = ConnectionFactory.createConnection()) {
            String sql = "SELECT * FROM reimbursements WHERE id = ?";

            PreparedStatement pstmt = connectionObj.prepareStatement(sql);

            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery(); // 0 rows or 1 row

            if (rs.next()) {
                int reimbursementId = rs.getInt("id");
                String description = rs.getString("description");
                double amount = rs.getDouble("amount");
                String status = rs.getString("status");
                int userId = rs.getInt("userid");

                return new Reimbursement(reimbursementId, amount, description, status, userId);
            } else {
                return null;
            }
        }
    }

    public Reimbursement submitReimbursement(Reimbursement reimbursement, User user) throws SQLException {
        try (Connection connectionObject = ConnectionFactory.createConnection()){
            String sql = "insert into reimbursements (amount, description, status, userId) values (?,?,?,?)";

            PreparedStatement pstmt = connectionObject.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setDouble(1,reimbursement.getAmount());
            pstmt.setString(2, reimbursement.getDescription());
            pstmt.setString(3, "Pending");
            pstmt.setInt(4, user.getId());

            int numberOfRecordsAdded = pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();

            rs.next(); // retrieve the first row in the temporary table
            int id = rs.getInt(1); // Get 1st column from temporary table

            return new Reimbursement(id, reimbursement.getAmount(), reimbursement.getDescription(), reimbursement.getStatus(), reimbursement.getUserId());
        }
    }
}
