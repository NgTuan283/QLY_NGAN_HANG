// service/ReportService.java
// 
package service;

import java.sql.*;



public class ReportService {
    // Truy xuất 
    // public Customer getCustomerById(String customerId) {
    //     String sql = "SELECT * FROM Customer WHERE customer_id = ?";
    //     try (Connection conn = DatabaseConnection.getConnection();
    //          PreparedStatement stmt = conn.prepareStatement(sql)) {
    //         stmt.setString(1, customerId);
    //         ResultSet rs = stmt.executeQuery();
    //         if (rs.next()) {
    //             // Khởi tạo và trả về đối tượng Customer
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    //     return null;
    // }
    
    // public int getTotalCustomers() {
    //     return count("Customer");
    // }
    // public int getTotalCustomers() {
    //     return 100;
    // }
    

    public int getTotalTransactions() {
        return count("Transaction");
    }

    public int getTotalLoans() {
        return count("Loan");
    }

    private int count(String table) {
        String sql = "SELECT COUNT(*) FROM " + table;
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
}