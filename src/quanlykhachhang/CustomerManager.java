package quanlykhachhang;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CustomerManager {

    static Scanner scanner = new Scanner(System.in);

    // Thêm khách hàng vào cơ sở dữ liệu
    public void addCustomer(Customer customer) {
        try {
            DatabaseHelper.insertCustomer(customer);
            System.out.println("Tao khach hang thanh cong");
        } catch (SQLException e) {
            System.out.println("Loi khi tao khach hang: " + e.getMessage());
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
        try {
            List<Customer> customers = DatabaseHelper.getAllCustomers();
            for (Customer customer : customers) {
                System.out.println(customer.getFullName() + " - " + customer.getEmail());
            }
        } catch (SQLException e) {
            System.out.println("Loi khi lay danh sach khach hang: " + e.getMessage());
        }
    }

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
