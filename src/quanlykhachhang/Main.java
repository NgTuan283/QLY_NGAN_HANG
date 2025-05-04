package quanlykhachhang;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static CustomerManager customerManager = new CustomerManager();
    static Employee defaultEmp = new Employee("admin", "123", "Admin User");

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== HỆ THỐNG NGÂN HÀNG =====");
            System.out.println("1. Đăng nhập");
            System.out.println("2. Đăng ký (Khách hàng)");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");
            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1 -> loginMenu();
                case 2 -> customerRegister();
                case 0 -> {
                    System.out.println("Thoát chương trình.");
                    System.exit(0);
                }
                default -> System.out.println("Lựa chọn không hợp lệ.");
            }
        }
    }

    static void loginMenu() {
        System.out.println("\n1. Nhân viên đăng nhập");
        System.out.println("2. Khách hàng đăng nhập");
        System.out.print("Chọn: ");
        int type = sc.nextInt();
        sc.nextLine();

        if (type == 1)
            employeeLogin();
        else if (type == 2)
            customerLogin();
        else
            System.out.println("Lựa chọn không hợp lệ.");
    }

    static void employeeLogin() {
        System.out.print("Username: ");
        String u = sc.nextLine();
        System.out.print("Password: ");
        String p = sc.nextLine();

        if (u.equals(defaultEmp.getUsername()) && defaultEmp.login(p)) {
            System.out.println("✅ Đăng nhập thành công: " + defaultEmp.getFullName());
            employeeMenu();
        } else {
            System.out.println("❌ Đăng nhập thất bại.");
        }
    }

    static void employeeMenu() {
        while (true) {
            System.out.println("\n--- MENU NHÂN VIÊN ---");
            System.out.println("1. Thêm khách hàng");
            System.out.println("2. Xem danh sách khách hàng");
            System.out.println("3. Sửa khách hàng");
            System.out.println("4. Xóa khách hàng");
            System.out.println("0. Đăng xuất");
            System.out.print("Chọn: ");
            int c = sc.nextInt();
            sc.nextLine();

            switch (c) {
                case 1 -> addCustomerByStaff();
                case 2 -> customerManager.listCustomers();
                case 3 -> {
                    System.out.print("Nhập username khách cần sửa: ");
                    String u = sc.nextLine();
                    customerManager.editCustomerInteractive(sc, u);
                }
                case 4 -> {
                    System.out.print("Nhập username khách cần xóa: ");
                    String u = sc.nextLine();
                    customerManager.removeCustomer(u);
                }
                case 0 -> {
                    System.out.println("Đã đăng xuất.");
                    return;
                }
                default -> System.out.println("Lựa chọn không hợp lệ.");
            }
        }
    }

    static void addCustomerByStaff() {
        System.out.print("Full Name: ");
        String name = sc.nextLine();
        System.out.print("Username: ");
        String u = sc.nextLine();
        if (customerManager.exists(u)) {
            System.out.println("❌ Username đã tồn tại.");
            return;
        }
        System.out.print("Password: ");
        String p = sc.nextLine();
        System.out.print("Phone: ");
        String phone = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Address: ");
        String addr = sc.nextLine();

        // Tạo khách hàng mới
        Customer newCustomer = new Customer(u, p, name, phone, email, addr);
        customerManager.addCustomer(newCustomer);

        // Hiển thị ID và giờ tạo tài khoản của khách hàng mới
        System.out.println("✅ Thêm khách hàng thành công!");
        System.out.println("ID khách hàng: " + newCustomer.getId());
        System.out.println("Thời gian tạo tài khoản: " + newCustomer.getCreatedTime());
    }

    static void customerRegister() {
        System.out.println("\n--- ĐĂNG KÝ KHÁCH HÀNG ---");
        System.out.print("Full Name: ");
        String name = sc.nextLine();
        System.out.print("Username: ");
        String u = sc.nextLine();
        if (customerManager.exists(u)) {
            System.out.println("❌ Username đã tồn tại.");
            return;
        }
        System.out.print("Password: ");
        String p = sc.nextLine();
        System.out.print("Phone: ");
        String phone = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Address: ");
        String addr = sc.nextLine();

        // Tạo khách hàng mới
        Customer newCustomer = new Customer(u, p, name, phone, email, addr);
        customerManager.addCustomer(newCustomer);

        // Hiển thị ID của khách hàng mới đăng ký
        System.out.println("✅ Đăng ký thành công! ID của bạn là: " + newCustomer.getId());
        System.out.println("Thời gian tạo tài khoản: " + newCustomer.getCreatedTime());
    }

    static void customerLogin() {
        System.out.println("\n--- KHÁCH HÀNG ĐĂNG NHẬP ---");
        System.out.print("Username: ");
        String u = sc.nextLine();
        Customer c = customerManager.getCustomer(u);
        if (c == null) {
            System.out.println("❌ Không tìm thấy khách hàng.");
            return;
        }
        System.out.print("Password: ");
        String p = sc.nextLine();
        if (c.login(p)) {
            System.out.println("✅ Đăng nhập thành công!\n");
            customerMenu(c); // Gọi menu sau khi đăng nhập thành công
        } else {
            System.out.println("❌ Sai mật khẩu.");
        }
    }

    static void customerMenu(Customer c) {
        while (true) {
            System.out.println("\n--- MENU KHÁCH HÀNG ---");
            System.out.println("1. Xem thông tin cá nhân");
            System.out.println("2. Đăng xuất");
            System.out.print("Chọn: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Đọc ký tự thừa sau khi đọc số nguyên

            switch (choice) {
                case 1:
                    c.displayInfo(); // Hiển thị thông tin cá nhân
                    break;
                case 2:
                    System.out.println("✅ Đăng xuất thành công.");
                    return; // Thoát khỏi menu và quay lại đăng nhập
                default:
                    System.out.println("❌ Lựa chọn không hợp lệ.");
            }
        }
    }
}
