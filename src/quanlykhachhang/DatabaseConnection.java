package quanlykhachhang;

import java.sql.*;
<<<<<<< HEAD:src/quanlykhachhang/DatabaseHelper.java
import java.util.ArrayList;
import java.util.List;
=======
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
>>>>>>> 3e746b1e365039f6023c7e756c0acf9c0bc03171:src/quanlykhachhang/DatabaseConnection.java

public class DatabaseConnection {

    // Cau hinh ket noi voi SQL Server
    private static final String URL = "jdbc:sqlserver://NGUYEN-TUAN\\NGUYENTUAN:1433;databaseName=BankDB;encrypt=true;trustServerCertificate=true";
    private static final String USER = "admin";
    private static final String PASSWORD = "020803Qt";

    // Ket noi den co so du lieu
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Them khach hang vao CSDL
    public static void insertCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO Customer (userName, password, fullName, phoneNumber, email, address, createdTime) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, customer.getUsername());
            ps.setString(2, customer.getPassword());  // Ban can thay doi cach luu mat khau (khong nen luu mat khau tho)
            ps.setString(3, customer.getFullName());
            ps.setString(4, customer.getPhoneNumber());
            ps.setString(5, customer.getEmail());
            ps.setString(6, customer.getAddress());
            ps.setTimestamp(7, Timestamp.valueOf(customer.getCreatedTime())); // Luu thoi gian tao
            ps.executeUpdate();
        }
    }

    // Lay thong tin khach hang theo username
    public static Customer getCustomerByUsername(String userName) throws SQLException {
        String sql = "SELECT * FROM Customer WHERE userName = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Lay thong tin khach hang tu ket qua truy van va tra ve doi tuong Customer
                return new Customer(
                        rs.getString("userName"),
                        rs.getString("password"),
                        rs.getString("fullName"),
                        rs.getString("phoneNumber"),
                        rs.getString("email"),
                        rs.getString("address")
                );
            }
        }
        return null;
    }

    // Cap nhat thong tin khach hang (vi du: so dien thoai, email, dia chi)
    public static void updateCustomer(Customer customer) throws SQLException {
        String sql = "UPDATE Customer SET phoneNumber = ?, email = ?, address = ? WHERE customerID = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, customer.getPhoneNumber());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getAddress());
            ps.setInt(4, customer.getCustomerID());
            ps.executeUpdate();
        }
    }

    // Xoa khach hang khoi co so du lieu
    public static void deleteCustomer(String userName) throws SQLException {
        String sql = "DELETE FROM Customer WHERE userName = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userName);
            ps.executeUpdate();
        }
    }

    // Lay danh sach tat ca khach hang
    public static List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM Customer";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getString("userName"),
                        rs.getString("password"),
                        rs.getString("fullName"),
                        rs.getString("phoneNumber"),
                        rs.getString("email"),
                        rs.getString("address")
                );
                customers.add(customer);
            }
        }
        return customers;
    }
}
