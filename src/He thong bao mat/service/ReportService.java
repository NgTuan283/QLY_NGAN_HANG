// service/ReportService.java
// 
package service;

import java.sql.*;



public class ReportService {
    public int getTotalCustomers() {
        return count("Customer");
    }
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
    // public int getTotalCustomers() {
    //     return 42; // giả lập số lượng khách hàng
    // }

    // public int getTotalTransactions() {
    //     return 130; // giả lập số lượng giao dịch
    // }

    // public int getTotalLoans() {
    //     return 27; // giả lập số lượng khoản vay
    // }
}