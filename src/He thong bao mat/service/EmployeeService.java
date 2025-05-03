package service;

import model.Employee;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

public class EmployeeService {

    // Hash mật khẩu sử dụng BCrypt
    private String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // Thêm nhân viên mới
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

    // Cập nhật thông tin nhân viên
    public void updateEmployee(Employee employee, String currentPlainPassword) {
        String sqlCheck = "SELECT password FROM Employee WHERE employee_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(sqlCheck)) {

            checkStmt.setString(1, employee.getEmployeeId());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("password");

                if (BCrypt.checkpw(currentPlainPassword, hashedPassword)) {
                    String sqlUpdate = "UPDATE Employee SET full_name=?, username=?, password=?, role=? WHERE employee_id=?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(sqlUpdate)) {
                        updateStmt.setString(1, employee.getFullName());
                        updateStmt.setString(2, employee.getUsername());
                        updateStmt.setString(3, hashPassword(employee.getPassword()));
                        updateStmt.setString(4, employee.getRole());
                        updateStmt.setString(5, employee.getEmployeeId());

                        updateStmt.executeUpdate();
                        System.out.println("Cap nhat nhan vien thanh cong.");
                    }
                } else {
                    System.out.println("Mat khau hien tai sai. Khong the cap nhat.");
                }
            } else {
                System.out.println("Khong tim thay nhan vien.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xoa nhan vien
    public void deleteEmployee(String employeeId, String plainPassword) {
        String sqlCheck = "SELECT password FROM Employee WHERE employee_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(sqlCheck)) {

            checkStmt.setString(1, employeeId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("password");

                if (BCrypt.checkpw(plainPassword, hashedPassword)) {
                    String sqlDelete = "DELETE FROM Employee WHERE employee_id = ?";
                    try (PreparedStatement deleteStmt = conn.prepareStatement(sqlDelete)) {
                        deleteStmt.setString(1, employeeId);
                        deleteStmt.executeUpdate();
                        System.out.println("Da xoa nhan vien.");
                    }
                } else {
                    System.out.println("Mat khau sai. Khong the xoa nhan vien.");
                }
            } else {
                System.out.println("Khong tim thay nhan vien.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lay danh sach tat ca nhan vien
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM Employee";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Employee emp = new Employee();
                emp.setEmployeeId(rs.getString("employee_id"));
                emp.setFullName(rs.getString("full_name"));
                emp.setUsername(rs.getString("username"));
                emp.setPassword(rs.getString("password"));
                emp.setRole(rs.getString("role"));
                emp.setCreatedTime(rs.getTimestamp("created_time").toLocalDateTime());
                employees.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    // Tim nhan vien theo ten
    public List<Employee> searchByName(String name) {
        List<Employee> results = new ArrayList<>();
        String sql = "SELECT * FROM Employee WHERE full_name LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Employee emp = new Employee();
                emp.setEmployeeId(rs.getString("employee_id"));
                emp.setFullName(rs.getString("full_name"));
                emp.setUsername(rs.getString("username"));
                emp.setPassword(rs.getString("password"));
                emp.setRole(rs.getString("role"));
                emp.setCreatedTime(rs.getTimestamp("created_time").toLocalDateTime());
                results.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    // Dem tong so nhan vien
    public int countEmployees() {
        String sql = "SELECT COUNT(*) FROM Employee";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
