// service/LogService.java
// xuất ra toàn bộ lịch sử thoa tác của nhân viên
package service;

import model.Log;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LogService {

    // Lọc lịch sử hoạt động của nhân viên theo thời gian
    public List<Log> getLogsByTimeRange(LocalDateTime start, LocalDateTime end) {
        List<Log> logs = new ArrayList<>();
        String sql = "SELECT * FROM Log WHERE action_time BETWEEN ? AND ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(start));
            stmt.setTimestamp(2, Timestamp.valueOf(end));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // Khởi tạo và thêm Log vào danh sách
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }
    
    // Lọc lịch sử hoạt động của nhân viên theo log ID
    public Log getLogById(String logId) {
        String sql = "SELECT * FROM Log WHERE log_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, logId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Khởi tạo và trả về Log
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    //  Lọc lịch sử hoạt động của nhân viên theo mã nhân viên
    public List<Log> getLogsByEmployeeId(String employeeId) {
        List<Log> logs = new ArrayList<>();
        String sql = "SELECT * FROM Log WHERE employee_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, employeeId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // Khởi tạo và thêm Log vào danh sách
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }
    
}