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

    // cập nhật thông tin nhân viên 
    public void updateEmployee(Employee employee) {
        String sql = "UPDATE Employee SET full_name=?, username=?, password=?, role=? WHERE employee_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, employee.getFullName());
            stmt.setString(2, employee.getUsername());
            stmt.setString(3, hashPassword(employee.getPassword()));
            stmt.setString(4, employee.getRole());
            stmt.setString(5, employee.getEmployeeId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // xoá nhân viên
    public void deleteEmployee(String employeeId) {
        String sql = "DELETE FROM Employee WHERE employee_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, employeeId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // in ra danh sách nhân viên
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

//     private String hashPassword(String plainPassword) {
//         return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
//     }
// private List<Employee> mockEmployees = new ArrayList<>();

// public void addEmployee(Employee employee) {
//     // Giả lập thêm vào danh sách
//     mockEmployees.add(employee);
//     System.out.println("Employee added: " + employee.getFullName());
// }

// public void updateEmployee(Employee employee) {
//     for (int i = 0; i < mockEmployees.size(); i++) {
//         if (mockEmployees.get(i).getEmployeeId().equals(employee.getEmployeeId())) {
//             mockEmployees.set(i, employee);
//             System.out.println("Employee updated: " + employee.getFullName());
//             return;
//         }
//     }
//     System.out.println("Employee not found for update.");
// }

// public void deleteEmployee(String employeeId) {
//     mockEmployees.removeIf(emp -> emp.getEmployeeId().equals(employeeId));
//     System.out.println("Employee deleted: " + employeeId);
// }

// public List<Employee> getAllEmployees() {
//     if (mockEmployees.isEmpty()) {
//         mockEmployees.add(new Employee("e001", "Nguyen Van A", "nva", "123", "admin", LocalDateTime.now()));
//         mockEmployees.add(new Employee("e002", "Tran Thi B", "ttb", "123", "staff", LocalDateTime.now()));
//     }
//     return mockEmployees;
// }
}
