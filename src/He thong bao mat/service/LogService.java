package service;

import model.Log;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LogService {

    // Ghi một dòng nhật ký mới
    public void logEmployeeAction(String employeeId, String action, String targetId) {
        String sql = "INSERT INTO Log (log_id, employee_id, action, target, target_id, action_time) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, UUID.randomUUID().toString());
            stmt.setString(2, employeeId);
            stmt.setString(3, action);
            stmt.setString(4, "Employee");
            stmt.setString(5, targetId);
            stmt.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy tất cả nhật ký
    public List<Log> getAllLogs() {
        List<Log> logs = new ArrayList<>();
        String sql = "SELECT * FROM Log";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                logs.add(extractLog(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }

    // Tìm log theo mã log
    public Log getLogById(String logId) {
        String sql = "SELECT * FROM Log WHERE log_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, logId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractLog(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Tìm log theo mã nhân viên
    public List<Log> getLogsByEmployeeId(String employeeId) {
        List<Log> logs = new ArrayList<>();
        String sql = "SELECT * FROM Log WHERE employee_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, employeeId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                logs.add(extractLog(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }

    // Tìm log theo khoảng thời gian
    public List<Log> getLogsByTimeRange(LocalDateTime start, LocalDateTime end) {
        List<Log> logs = new ArrayList<>();
        String sql = "SELECT * FROM Log WHERE action_time BETWEEN ? AND ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(start));
            stmt.setTimestamp(2, Timestamp.valueOf(end));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                logs.add(extractLog(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }

    // Helper: Tạo đối tượng Log từ ResultSet
    private Log extractLog(ResultSet rs) throws SQLException {
        return new Log(
            rs.getString("log_id"),
            rs.getString("employee_id"),
            rs.getString("action"),
            rs.getString("target"),
            rs.getString("target_id"),
            rs.getTimestamp("action_time").toLocalDateTime()
        );
    }
}
