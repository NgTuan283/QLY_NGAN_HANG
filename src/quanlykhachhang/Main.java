package quanlykhachhang;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static CustomerManager customerManager = new CustomerManager();
    static Employee defaultEmp = new Employee("admin", "123", "Admin User");

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== HE THONG NGAN HANG =====");
            System.out.println("1. Dang nhap");
            System.out.println("2. Dang ky (Khach hang)");
            System.out.println("0. Thoat");
            System.out.print("Lua chon: ");
            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1 -> loginMenu();
                case 2 -> customerRegister();
                case 0 -> {
                    System.out.println("Thoat chuong trinh.");
                    System.exit(0);
                }
                default -> System.out.println("Lua chon khong hop le.");
            }
        }
    }

    static void loginMenu() {
        System.out.println("\n1. Nhan vien dang nhap");
        System.out.println("2. Khach hang dang nhap");
        System.out.print("Lua Chon: ");
        int type = sc.nextInt();
        sc.nextLine();

        if (type == 1)
            employeeLogin();
        else if (type == 2)
            customerLogin();
        else
            System.out.println("Lua chon khong hop le.");
    }

    static void employeeLogin() {
        System.out.print("Username: ");
        String u = sc.nextLine();
        System.out.print("Password: ");
        String p = sc.nextLine();

        if (u.equals(defaultEmp.getUserName()) && defaultEmp.login(p)) {
            System.out.println("Dang nhap thanh cong: " + defaultEmp.getFullName());
            employeeMenu();
        } else {
            System.out.println("Dang nhap that bai.");
        }
    }

    static void employeeMenu() {
        while (true) {
            System.out.println("\n--- MENU NHAN VIEN ---");
            System.out.println("1. Them khach hang");
            System.out.println("2. Xem danh sach khach hang");
            System.out.println("3. Sua khach hang");
            System.out.println("4. Xoa khach hang");
            System.out.println("0. Dang xuat");
            System.out.print("Lua chon: ");
            int c = sc.nextInt();
            sc.nextLine();

            switch (c) {
                case 1 -> addCustomerByStaff();
                case 2 -> customerManager.listCustomers();
                case 3 -> {
                    System.out.print("Nhap username khach can sua: ");
                    String u = sc.nextLine();
                    customerManager.editCustomerInteractive(sc, u);
                }
                case 4 -> {
                    System.out.print("Nhap username khach can xoa: ");
                    String u = sc.nextLine();
                    customerManager.removeCustomer(u);
                }
                case 0 -> {
                    System.out.println("Da dang xuat.");
                    return;
                }
                default -> System.out.println("Lua chon khong hop le.");
            }
        }
    }

    static void addCustomerByStaff() {
        System.out.print("Full Name: ");
        String name = sc.nextLine();
        System.out.print("Username: ");
        String u = sc.nextLine();
        if (customerManager.exists(u)) {
            System.out.println("Username da ton tai.");
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
        System.out.println("Them khach hang thanh cong!");
        System.out.println("ID khach hang: " + newCustomer.getCustomerID());
        System.out.println("Thoi gian tao tai khoan: " + newCustomer.getCreatedTime());
    }

    static void customerRegister() {
        System.out.println("\n--- ĐANG KY KHACH HANG ---");
        System.out.print("Full Name: ");
        String name = sc.nextLine();
        System.out.print("Username: ");
        String u = sc.nextLine();
        if (customerManager.exists(u)) {
            System.out.println("Username da ton tai.");
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
        System.out.println("Dang ky thanh cong! ID cua ban la: " + newCustomer.getCustomerID());
        System.out.println("Thoi gian tao tai khoan: " + newCustomer.getCreatedTime());
    }

    static void customerLogin() {
        System.out.println("\n--- KHACH HANG DANG NHAP ---");
        System.out.print("Username: ");
        String u = sc.nextLine();
        Customer c = customerManager.getCustomer(u);
        if (c == null) {
            System.out.println("Khong tim thay khach hang.");
            return;
        }
        System.out.print("Password: ");
        String p = sc.nextLine();
        if (c.login(p)) {
            System.out.println("Dang nhap thanh cong!\n");
            customerMenu(c); // Gọi menu sau khi đăng nhập thành công
        } else {
            System.out.println("Sai mat khau.");
        }
    }

    static void customerMenu(Customer c) {
        while (true) {
            System.out.println("\n--- MENU KHACH HANG ---");
            System.out.println("1. Xem thong tin ca nhan");
            System.out.println("2. Dang xuat");
            System.out.print("Lua chon: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Đọc ký tự thừa sau khi đọc số nguyên

            switch (choice) {
                case 1:
                    c.displayInfo(); // Hiển thị thông tin cá nhân
                    break;
                case 2:
                    System.out.println("Dang xuat thanh cong.");
                    return; // Thoát khỏi menu và quay lại đăng nhập
                default:
                    System.out.println("Lua chon khong hop le.");
            }
        }
    }
}