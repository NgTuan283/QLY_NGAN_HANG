// package QLY_NGAN_HANG.QuanLyNhanVien;

// import java.sql.*;
// import java.util.ArrayList;
// import java.util.List;

// import QLY_NGAN_HANG.QuanLyNhanVien.EmployeeService;

// public class DatabaseConnection {

//     // Cấu hình kết nối với cơ sở dữ liệu
//     private static final String URL = "jdbc:mysql://localhost:3306/your_database?useSSL=false"; // Đổi 'your_database' thành tên cơ sở dữ liệu của bạn
//     private static final String USERNAME = "root";  // Thay đổi username nếu cần
//     private static final String PASSWORD = "123456"; // Thay đổi mật khẩu nếu cần

//     // Kết nối tới cơ sở dữ liệu
//     public static Connection getConnection() throws SQLException {
//         return DriverManager.getConnection(URL, USERNAME, PASSWORD);
//     }

//     public static List<Employee> employees = new ArrayList<>();

//     // Thêm nhân viên vào cơ sở dữ liệu
//     public static void addEmployee(Employee employee) {
//         String sql = "INSERT INTO Employee (employee_id, full_name, username, password, role, created_time) VALUES (?, ?, ?, ?, ?, ?)";
//         try (Connection conn = getConnection();
//              PreparedStatement stmt = conn.prepareStatement(sql)) {
//             stmt.setString(1, employee.getEmployeeId());
//             stmt.setString(2, employee.getFullName());
//             stmt.setString(3, employee.getUsername());
//             stmt.setString(4, employee.getPassword());
//             stmt.setString(5, employee.getRole());
//             stmt.setTimestamp(6, Timestamp.valueOf(employee.getCreatedTime()));
//             stmt.executeUpdate();
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//     }



//     // Cập nhật thông tin nhân viên
//     public static void updateEmployee(Employee employee) {
//         String sql = "UPDATE Employee SET full_name = ?, username = ?, password = ?, role = ? WHERE employee_id = ?";
//         try (Connection conn = getConnection();
//              PreparedStatement stmt = conn.prepareStatement(sql)) {
//             stmt.setString(1, employee.getFullName());
//             stmt.setString(2, employee.getUsername());
//             stmt.setString(3, employee.getPassword());
//             stmt.setString(4, employee.getRole());
//             stmt.setString(5, employee.getEmployeeId());
//             stmt.executeUpdate();
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//     }

//     // Xóa nhân viên theo ID
//     public static void deleteEmployee(String employeeId) {
//         String sql = "DELETE FROM Employee WHERE employee_id = ?";
//         try (Connection conn = getConnection();
//              PreparedStatement stmt = conn.prepareStatement(sql)) {
//             stmt.setString(1, employeeId);
//             stmt.executeUpdate();
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//     }

//     // Tìm kiếm nhân viên theo tên
//     public static List<Employee> searchEmployeeByName(String name) {
//         List<Employee> employees = new ArrayList<>();
//         String sql = "SELECT * FROM Employee WHERE full_name LIKE ?";
//         try (Connection conn = getConnection();
//              PreparedStatement stmt = conn.prepareStatement(sql)) {
//             stmt.setString(1, "%" + name + "%");
//             ResultSet rs = stmt.executeQuery();
//             while (rs.next()) {
//                 Employee emp = new Employee(
//                         rs.getString("employee_id"),
//                         rs.getString("full_name"),
//                         rs.getString("username"),
//                         rs.getString("password"),
//                         rs.getString("role"),
//                         rs.getTimestamp("created_time").toLocalDateTime()
//                 );
//                 employees.add(emp);
//             }
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//         return employees;
//     }

//     // Lấy tất cả nhân viên
//     public static List<Employee> getAllEmployees() {
//         List<Employee> employees = new ArrayList<>();
//         String sql = "SELECT * FROM Employee";
//         try (Connection conn = getConnection();
//              Statement stmt = conn.createStatement();
//              ResultSet rs = stmt.executeQuery(sql)) {
//             while (rs.next()) {
//                 Employee emp = new Employee(
//                         rs.getString("employee_id"),
//                         rs.getString("full_name"),
//                         rs.getString("username"),
//                         rs.getString("password"),
//                         rs.getString("role"),
//                         rs.getTimestamp("created_time").toLocalDateTime()
//                 );
//                 employees.add(emp);
//             }
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//         return employees;
//     }

//     // Tìm kiếm nhật ký theo ID nhân viên
//     public static List<Log> getLogsByEmployeeId(String employeeId) {
//         List<Log> logs = new ArrayList<>();
//         String sql = "SELECT * FROM Log WHERE employee_id = ?";
//         try (Connection conn = getConnection();
//              PreparedStatement stmt = conn.prepareStatement(sql)) {
//             stmt.setString(1, employeeId);
//             ResultSet rs = stmt.executeQuery();
//             while (rs.next()) {
//                 Log log = new Log(
//                         rs.getString("log_id"),
//                         rs.getString("employee_id"),
//                         rs.getString("action"),
//                         rs.getString("target"),
//                         rs.getString("target_id"),
//                         rs.getTimestamp("action_time").toLocalDateTime()
//                 );
//                 logs.add(log);
//             }
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//         return logs;
//     }

//     // Thêm nhật ký hành động vào cơ sở dữ liệu
//     public static void addLog(Log log) {
//         String sql = "INSERT INTO Log (log_id, employee_id, action, target, target_id, action_time) VALUES (?, ?, ?, ?, ?, ?)";
//         try (Connection conn = getConnection();
//              PreparedStatement stmt = conn.prepareStatement(sql)) {
//             stmt.setString(1, log.getLogId());
//             stmt.setString(2, log.getEmployeeId());
//             stmt.setString(3, log.getAction());
//             stmt.setString(4, log.getTarget());
//             stmt.setString(5, log.getTargetId());
//             stmt.setTimestamp(6, Timestamp.valueOf(log.getActionTime()));
//             stmt.executeUpdate();
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//     }

//     // Tìm kiếm nhật ký theo ngày
//     public static List<Log> getLogsByDateRange(java.time.LocalDateTime start, java.time.LocalDateTime end) {
//         List<Log> logs = new ArrayList<>();
//         String sql = "SELECT * FROM Log WHERE action_time BETWEEN ? AND ?";
//         try (Connection conn = getConnection();
//              PreparedStatement stmt = conn.prepareStatement(sql)) {
//             stmt.setTimestamp(1, Timestamp.valueOf(start));
//             stmt.setTimestamp(2, Timestamp.valueOf(end));
//             ResultSet rs = stmt.executeQuery();
//             while (rs.next()) {
//                 Log log = new Log(
//                         rs.getString("log_id"),
//                         rs.getString("employee_id"),
//                         rs.getString("action"),
//                         rs.getString("target"),
//                         rs.getString("target_id"),
//                         rs.getTimestamp("action_time").toLocalDateTime()
//                 );
//                 logs.add(log);
//             }
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//         return logs;
//     }
// }


package QuanLyNhanVien;

import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {

    // Danh sách ảo để chứa nhân viên và nhật ký
    public static List<Employee> employees = new ArrayList<>();
    public static List<Log> logs = new ArrayList<>();

    // Thêm nhân viên vào CSDL ảo
    public static void addEmployee(Employee employee) {
        employees.add(employee);
    }

    // Cập nhật thông tin nhân viên
    public static void updateEmployee(Employee employee) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getEmployeeId().equals(employee.getEmployeeId())) {
                employees.set(i, employee);
                break;
            }
        }
    }

    // Xóa nhân viên
    public static void deleteEmployee(String employeeId) {
        employees.removeIf(employee -> employee.getEmployeeId().equals(employeeId));
    }

    // Lấy tất cả nhân viên
    public static List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }

    public static boolean isUsernameExists(String username) {
        for (Employee emp : employees) {
            if (emp.getUsername().equals(username)) {
                return true;  // Nếu tìm thấy username trùng
            }
        }
        return false;  // Nếu không tìm thấy username trùng
    }

    // Tìm kiếm nhân viên theo tên
    public static List<Employee> searchEmployeeByName(String name) {
        List<Employee> result = new ArrayList<>();
        for (Employee emp : employees) {
            if (emp.getFullName().contains(name)) {
                result.add(emp);
            }
        }
        return result;
    }

    // Thêm nhật ký vào CSDL ảo
    public static void addLog(Log log) {
        logs.add(log);
    }

    // Lấy nhật ký theo ID nhân viên
    public static List<Log> getLogsByEmployeeId(String employeeId) {
        List<Log> result = new ArrayList<>();
        for (Log log : logs) {
            if (log.getEmployeeId().equals(employeeId)) {
                result.add(log);
            }
        }
        return result;
    }

    // Lấy nhật ký theo ngày
    public static List<Log> getLogsByDateRange(java.time.LocalDateTime start, java.time.LocalDateTime end) {
        List<Log> result = new ArrayList<>();
        for (Log log : logs) {
            if (log.getActionTime().isAfter(start) && log.getActionTime().isBefore(end)) {
                result.add(log);
            }
        }
        return result;
    }
}
