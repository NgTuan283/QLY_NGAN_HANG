// service/EmployeeService.java
// Quản lý các thao tác liên quan đến nhân viên, bao gồm thêm, cập nhật, xóa và lấy danh sách nhân viên
package service;

import model.Employee;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;



public class EmployeeService {
    private String hashPassword(String plainPassword) {
    return org.mindrot.jbcrypt.BCrypt.hashpw(plainPassword, org.mindrot.jbcrypt.BCrypt.gensalt());
}

    // thêm nhân viên mới
    public void addEmployee(Employee employee) {
        String sql = "INSERT INTO Employee (employee_id, full_name, username, password, role, created_time) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, employee.getEmployeeId());
            stmt.setString(2, employee.getFullName());
            stmt.setString(3, employee.getUsername());
            stmt.setString(4, hashPassword(employee.getPassword()));
            stmt.setString(5, employee.getRole());
            stmt.setTimestamp(6, Timestamp.valueOf(employee.getCreatedTime()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // cập nhật thông tin nhân viên ( yêu cầu đúng mật khẩu mởi có thể cập nhật)
    public void updateEmployee(Employee employee, String currentPlainPassword) {
        // Lấy mật khẩu đã hash từ DB
        String sqlCheck = "SELECT password FROM Employee WHERE employee_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(sqlCheck)) {
            checkStmt.setString(1, employee.getEmployeeId());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                String hashedPassword = rs.getString("password");
    
                // Kiểm tra mật khẩu hiện tại
                if (BCrypt.checkpw(currentPlainPassword, hashedPassword)) {
                    // Mật khẩu đúng → cập nhật
                    String sqlUpdate = "UPDATE Employee SET full_name=?, username=?, password=?, role=? WHERE employee_id=?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(sqlUpdate)) {
                        updateStmt.setString(1, employee.getFullName());
                        updateStmt.setString(2, employee.getUsername());
                        updateStmt.setString(3, hashPassword(employee.getPassword())); // mật khẩu mới
                        updateStmt.setString(4, employee.getRole());
                        updateStmt.setString(5, employee.getEmployeeId());
                        updateStmt.executeUpdate();
                        System.out.println("Cập nhật nhân viên thành công.");
                    }
                } else {
                    System.out.println("Sai mật khẩu hiện tại. Không thể cập nhật.");
                }
            } else {
                System.out.println("Không tìm thấy nhân viên với ID: " + employee.getEmployeeId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    // xoá nhân viên( yêu cầu nhập đúng mật khẩu của nhân viên bị xoá)
    public void deleteEmployee(String employeeId, String plainPassword) {
        String sqlCheck = "SELECT password FROM Employee WHERE employee_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(sqlCheck)) {
    
            checkStmt.setString(1, employeeId);
            ResultSet rs = checkStmt.executeQuery();
    
            if (rs.next()) {
                String hashedPassword = rs.getString("password");
    
                // So sánh mật khẩu nhập với mật khẩu đã hash trong DB
                if (BCrypt.checkpw(plainPassword, hashedPassword)) {
                    // Nếu đúng, tiến hành xóa
                    String sqlDelete = "DELETE FROM Employee WHERE employee_id = ?";
                    try (PreparedStatement deleteStmt = conn.prepareStatement(sqlDelete)) {
                        deleteStmt.setString(1, employeeId);
                        deleteStmt.executeUpdate();
                        System.out.println("Đã xóa nhân viên: " + employeeId);
                    }
                } else {
                    System.out.println("Sai mật khẩu. Không thể xóa nhân viên.");
                }
            } else {
                System.out.println("Không tìm thấy nhân viên với ID: " + employeeId);
            }
        }
    
    // xem danh sách nhân viên
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM Employee";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getString("employee_id"));
                employee.setFullName(rs.getString("full_name"));
                employee.setUsername(rs.getString("username"));
                employee.setPassword(rs.getString("password"));
                employee.setRole(rs.getString("role"));
                employee.setCreatedTime(rs.getTimestamp("created_time").toLocalDateTime());
                employees.add(employee);

                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }


}
