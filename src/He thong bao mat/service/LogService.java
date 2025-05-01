// service/LogService.java
package service;

import model.Log;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LogService {
    // public List<Log> getAllLogs() {
    //     List<Log> logs = new ArrayList<>();
    //     String sql = "SELECT * FROM Log";
    //     try (Connection conn = DatabaseConnection.getConnection();
    //          Statement stmt = conn.createStatement();
    //          ResultSet rs = stmt.executeQuery(sql)) {
    //         while (rs.next()) {
    //             Log log = new Log();
    //             log.setLogId(rs.getString("log_id"));
    //             log.setEmployeeId(rs.getString("employee_id"));
    //             log.setAction(rs.getString("action"));
    //             log.setTarget(rs.getString("target"));
    //             log.setTargetId(rs.getString("target_id"));
    //             log.setActionTime(rs.getTimestamp("action_time").toLocalDateTime());
    //             logs.add(log);
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    //     return logs;
    // }
     public List<Log> getAllLogs() {
        List<Log> logs = new ArrayList<>();
        logs.add(mockLog("e001", "Thêm", "Employee", "e001"));
        logs.add(mockLog("e002", "Xem", "Customer", "c123"));
        return logs;
    }

    private Log mockLog(String empId, String action, String target, String targetId) {
        Log log = new Log();
        log.setLogId("log_" + System.currentTimeMillis());
        log.setEmployeeId(empId);
        log.setAction(action);
        log.setTarget(target);
        log.setTargetId(targetId);
        log.setActionTime(LocalDateTime.now());
        return log;
    }
}