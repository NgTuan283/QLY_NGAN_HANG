<<<<<<< HEAD
package quanlykhachhang;

import java.sql.SQLException;
import java.util.List;
=======
>>>>>>> 3e746b1e365039f6023c7e756c0acf9c0bc03171
import java.util.Scanner;
import java.sql.*;

public class CustomerManager {

<<<<<<< HEAD
    static Scanner scanner = new Scanner(System.in);

    // Thêm khách hàng vào cơ sở dữ liệu
    public void addCustomer(Customer customer) {
        try {
            DatabaseHelper.insertCustomer(customer);
            System.out.println("Tao khach hang thanh cong");
        } catch (SQLException e) {
            System.out.println("Loi khi tao khach hang: " + e.getMessage());
=======
    public void addCustomer(Customer c) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Customer (username, password, fullName, phoneNumber, email, address, createdTime) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, c.getUsername());
            stmt.setString(2, c.getPassword());
            stmt.setString(3, c.getFullName());
            stmt.setString(4, c.getPhoneNumber());
            stmt.setString(5, c.getEmail());
            stmt.setString(6, c.getAddress());
            stmt.setTimestamp(7, Timestamp.valueOf(c.getCreatedTime()));

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                System.out.println("ID khach hang: " + rs.getInt(1));
            }
            System.out.println("Khach hang da duoc them vao CSDL.");
        } catch (SQLException e) {
            e.printStackTrace();
>>>>>>> 3e746b1e365039f6023c7e756c0acf9c0bc03171
        }
    }

    // Kiểm tra sự tồn tại của tên đăng nhập
    public boolean exists(String userName) {
        try {
            return DatabaseHelper.getCustomerByUsername(userName) != null;
        } catch (SQLException e) {
            System.out.println("Loi khi kiem tra khach hang: " + e.getMessage());
            return false;
        }
    }

    // Lấy tất cả khách hàng
    public void listCustomers() {
<<<<<<< HEAD
        try {
            List<Customer> customers = DatabaseHelper.getAllCustomers();
            for (Customer customer : customers) {
                System.out.println(customer.getFullName() + " - " + customer.getEmail());
            }
        } catch (SQLException e) {
            System.out.println("Loi khi lay danh sach khach hang: " + e.getMessage());
=======
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Customer";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Customer c = new Customer(
                        rs.getInt("ID"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("fullName"),
                        rs.getString("phoneNumber"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getTimestamp("createdTime").toLocalDateTime());
                c.displayInfo();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeCustomer(String username) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM Customer WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Khach hang da bi xoa.");
            } else {
                System.out.println("Khong tim thay khach hang.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editCustomerInteractive(Scanner sc, String username) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            Customer c = getCustomer(username);
            if (c == null) {
                System.out.println("Khong tim thay khach hang.");
                return;
            }

            System.out.print("Ten moi (enter de giu nguyen): ");
            String name = sc.nextLine();
            if (!name.isEmpty())
                c.setFullName(name);

            System.out.print("SDT moi (enter de giu nguyen): ");
            String phone = sc.nextLine();
            if (!phone.isEmpty())
                c.setPhoneNumber(phone);

            System.out.print("Email moi (enter de giu nguyen): ");
            String email = sc.nextLine();
            if (!email.isEmpty())
                c.setEmail(email);

            System.out.print("Dia chi moi (enter de giu nguyen): ");
            String address = sc.nextLine();
            if (!address.isEmpty())
                c.setAddress(address);

            String sql = "UPDATE Customer SET fullName = ?, phoneNumber = ?, email = ?, address = ? WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, c.getFullName());
            stmt.setString(2, c.getPhoneNumber());
            stmt.setString(3, c.getEmail());
            stmt.setString(4, c.getAddress());
            stmt.setString(5, c.getUsername());
            stmt.executeUpdate();

            System.out.println("Cap nhat thong tin thanh cong.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean exists(String username) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT COUNT(*) FROM Customer WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
>>>>>>> 3e746b1e365039f6023c7e756c0acf9c0bc03171
        }
    }

<<<<<<< HEAD
    // Chỉnh sửa thông tin khách hàng (tương tác với người dùng)
    public void editCustomerInteractive(Scanner sc, String userName) {
        try {
            Customer customer = DatabaseHelper.getCustomerByUsername(userName);
            if (customer != null) {
                System.out.println("Nhap so dien thoai moi: ");
                String phoneNumber = sc.nextLine();
                System.out.println("Nhap email moi: ");
                String email = sc.nextLine();
                System.out.println("Nhap dia chi moi: ");
                String address = sc.nextLine();

                customer.setPhoneNumber(phoneNumber);
                customer.setEmail(email);
                customer.setAddress(address);

                DatabaseHelper.updateCustomer(customer);
                System.out.println("Cap nhat thong tin khach hang thanh cong.");
            } else {
                System.out.println("Khach hang khong tim thay.");
            }
        } catch (SQLException e) {
            System.out.println("Loi khi cap nhat thong tin khach hang: " + e.getMessage());
=======
    public Customer getCustomer(String username) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Customer WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Customer(
                        rs.getInt("ID"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("fullName"),
                        rs.getString("phoneNumber"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getTimestamp("createdTime").toLocalDateTime());
            }
        } catch (SQLException e) {
            e.printStackTrace();
>>>>>>> 3e746b1e365039f6023c7e756c0acf9c0bc03171
        }
    }

    // Xóa khách hàng
    public void removeCustomer(String userName) {
        try {
            DatabaseHelper.deleteCustomer(userName);
            System.out.println("Khach hang da duoc xoa thanh cong.");
        } catch (SQLException e) {
            System.out.println("Loi khi xoa khach hang: " + e.getMessage());
        }
    }

    // Lấy thông tin khách hàng từ CSDL
    public Customer getCustomer(String userName) {
        try {
            return DatabaseHelper.getCustomerByUsername(userName);
        } catch (SQLException e) {
            System.out.println("Loi khi lay thong tin khach hang: " + e.getMessage());
            return null;
        }
    }
}
