package QuanLyNhanVien;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import QuanLyNhanVien.EmployeeService;

public class DatabaseConnection4 {

    // Cấu hình kết nối với cơ sở dữ liệu
    private static final String URL = "jdbc:mysql://localhost:3306/your_database?useSSL=false"; // Đổi 'your_database' thành tên cơ sở dữ liệu của bạn
    private static final String USERNAME = "root";  // Thay đổi username nếu cần
    private static final String PASSWORD = "123456"; // Thay đổi mật khẩu nếu cần

    // Kết nối tới cơ sở dữ liệu
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

//  kiểm tra username trùng
    public static boolean isUsernameExists(String username) {
        String query = "SELECT COUNT(*) FROM Employee WHERE username = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "root", "123456");
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;  // Trả về true nếu có nhân viên với username đó
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;  // Trả về false nếu không có trùng username
    }

    // lấy thông tin nhân viên theo ID
    public static Employee getEmployeeById(String employeeId) {
        String query = "SELECT * FROM Employee WHERE employee_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, employeeId);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                return new Employee(
                    rs.getString("employee_id"),
                    rs.getString("full_name"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role"),
                    rs.getTimestamp("created_time").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Trả về null nếu không tìm thấy nhân viên
    }

    // Thêm nhân viên vào cơ sở dữ liệu
    public static void addEmployee(Employee employee) {
        String sql = "INSERT INTO Employee (employee_id, full_name, username, password, role, created_time) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, employee.getEmployeeId());
            stmt.setString(2, employee.getFullName());
            stmt.setString(3, employee.getUsername());
            stmt.setString(4, employee.getPassword());
            stmt.setString(5, employee.getRole());
            stmt.setTimestamp(6, Timestamp.valueOf(employee.getCreatedTime()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    



    // Cập nhật thông tin nhân viên
    public static void updateEmployee(Employee employee) {
        String sql = "UPDATE Employee SET full_name = ?, username = ?, password = ?, role = ? WHERE employee_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, employee.getFullName());
            stmt.setString(2, employee.getUsername());
            stmt.setString(3, employee.getPassword());
            stmt.setString(4, employee.getRole());
            stmt.setString(5, employee.getEmployeeId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa nhân viên theo ID
    public static void deleteEmployee(String employeeId) {
        String sql = "DELETE FROM Employee WHERE employee_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, employeeId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Tìm kiếm nhân viên theo tên
    public static List<Employee> searchEmployeeByName(String name) {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM Employee WHERE full_name LIKE ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Employee emp = new Employee(
                        rs.getString("employee_id"),
                        rs.getString("full_name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getTimestamp("created_time").toLocalDateTime()
                );
                employees.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    // Lấy tất cả nhân viên
    public static List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM Employee";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Employee emp = new Employee(
                        rs.getString("employee_id"),
                        rs.getString("full_name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getTimestamp("created_time").toLocalDateTime()
                );
                employees.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    // Tìm kiếm nhật ký theo ID nhân viên
    public static List<Log> getLogsByEmployeeId(String employeeId) {
        List<Log> logs = new ArrayList<>();
        String sql = "SELECT * FROM Log WHERE employee_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, employeeId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Log log = new Log(
                        rs.getString("log_id"),
                        rs.getString("employee_id"),
                        rs.getString("action"),
                        rs.getString("target"),
                        rs.getString("target_id"),
                        rs.getTimestamp("action_time").toLocalDateTime()
                );
                logs.add(log);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }

    // Thêm nhật ký hành động vào cơ sở dữ liệu
    public static void addLog(Log log) {
        String sql = "INSERT INTO Log (log_id, employee_id, action, target, target_id, action_time) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, log.getLogId());
            stmt.setString(2, log.getEmployeeId());
            stmt.setString(3, log.getAction());
            stmt.setString(4, log.getTarget());
            stmt.setString(5, log.getTargetId());
            stmt.setTimestamp(6, Timestamp.valueOf(log.getActionTime()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Tìm kiếm nhật ký theo ngày
    public static List<Log> getLogsByDateRange(java.time.LocalDateTime start, java.time.LocalDateTime end) {
        List<Log> logs = new ArrayList<>();
        String sql = "SELECT * FROM Log WHERE action_time BETWEEN ? AND ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(start));
            stmt.setTimestamp(2, Timestamp.valueOf(end));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Log log = new Log(
                        rs.getString("log_id"),
                        rs.getString("employee_id"),
                        rs.getString("action"),
                        rs.getString("target"),
                        rs.getString("target_id"),
                        rs.getTimestamp("action_time").toLocalDateTime()
                );
                logs.add(log);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }
}


