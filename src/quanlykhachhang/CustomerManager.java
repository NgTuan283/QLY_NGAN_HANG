package quanlykhachhang;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomerManager {
    private ArrayList<Customer> customers = new ArrayList<>();

    public void addCustomer(Customer c) {
        customers.add(c);
        System.out.println("Khách hàng đã được thêm thành công.");
    }

    public void listCustomers() {
        if (customers.isEmpty()) {
            System.out.println("Không có khách hàng.");
            return;
        }
        for (Customer c : customers) {
            c.displayInfo();
        }
    }

    public void removeCustomer(String username) {
        boolean removed = customers.removeIf(c -> c.getUsername().equals(username));
        if (removed) {
            System.out.println("Khách hàng đã bị xóa.");
        } else {
            System.out.println("Không tìm thấy khách hàng.");
        }
    }

    public void editCustomerInteractive(Scanner sc, String username) {
        Customer c = getCustomer(username);
        if (c == null) {
            System.out.println("Không tìm thấy khách hàng.");
            return;
        }

        System.out.print("Tên mới (enter để giữ nguyên): ");
        String name = sc.nextLine();
        if (!name.isEmpty())
            c.setFullName(name);

        System.out.print("SĐT mới (enter để giữ nguyên): ");
        String phone = sc.nextLine();
        if (!phone.isEmpty())
            c.setPhoneNumber(phone);

        System.out.print("Email mới (enter để giữ nguyên): ");
        String email = sc.nextLine();
        if (!email.isEmpty())
            c.setEmail(email);

        System.out.print("Địa chỉ mới (enter để giữ nguyên): ");
        String address = sc.nextLine();
        if (!address.isEmpty())
            c.setAddress(address);

        System.out.println("Cập nhật thông tin thành công.");
    }

    public boolean exists(String username) {
        for (Customer c : customers) {
            if (c.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public Customer getCustomer(String username) {
        for (Customer c : customers) {
            if (c.getUsername().equals(username)) {
                return c;
            }
        }
        return null;
    }
}
