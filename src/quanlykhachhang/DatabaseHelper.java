import java.sql.*;
import java.util.*;

public class DatabaseHelper {

    // Cấu hình kết nối với SQL Server
    private static final String URL = "jdbc:sqlserver://NGUYEN-TUAN\\\\NGUYENTUAN:1433;databaseName=BankDB;encrypt=true;trustServerCertificate=true";
    private static final String USER = "admin";
    private static final String PASSWORD = "020803Qt";

    // Kết nối đến cơ sở dữ liệu
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Thêm khách hàng mới
    public static void insertCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO Customer (username, password, fullName, phoneNumber, email, address, createdTime) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, customer.getUsername());
            ps.setString(2, customer.password);
            ps.setString(3, customer.getFullName());
            ps.setString(4, customer.getPhoneNumber());
            ps.setString(5, customer.getEmail());
            ps.setString(6, customer.getAddress());
            ps.setTimestamp(7, Timestamp.valueOf(customer.getCreatedTime()));
            ps.executeUpdate();
        }
    }

    // Lấy thông tin khách hàng theo username
    public static Customer getCustomerByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM Customer WHERE username = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Customer(rs.getString("username"), rs.getString("password"), rs.getString("fullName"),
                        rs.getString("phoneNumber"), rs.getString("email"), rs.getString("address"));
            }
        }
        return null;
    }
}