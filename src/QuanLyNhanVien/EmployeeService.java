package QuanLyNhanVien;

import java.util.List;
import java.util.Scanner;


import java.time.LocalDateTime;

 class Employee {
    private String employeeId;
    private String fullName;
    private String username;
    private String password;
    private String role;
    private LocalDateTime createdTime;

    // Constructor
    public Employee(String employeeId, String fullName, String username, String password, String role, LocalDateTime createdTime) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.role = role;
        this.createdTime = createdTime;
    }

    // Getter and Setter methods
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
}


public class EmployeeService {

    private static final Scanner scanner = new Scanner(System.in);

    private LogService logService = new LogService();

    public void displayMenu() {
        System.out.println("\n=========== MENU QUAN LY NHAN VIEN ===========");
        System.out.println("1. Them nhan vien");
        System.out.println("2. Cap nhat thong tin nhan vien");
        System.out.println("3. Xoa nhan vien");
        System.out.println("4. Hien thi danh sach nhan vien");
        System.out.println("0. Thoat");
        System.out.print("Chon chuc nang: ");
    }

    public void execute(int choice) {
        switch (choice) {
            case 1:
                addEmployee();
                break;
            case 2:
                updateEmployee();
                break;
            case 3:
                deleteEmployee();
                break;
            case 4:
                showAllEmployees();
                break;
            case 0:
                System.out.println("Thoat ra menu chinh.");
                break;
            default:
                System.out.println("Lua chon khong hop le.");
        }
    }

    private void addEmployee() {
        System.out.print("Ho ten: ");
        String name = scanner.nextLine();
        System.out.print("Ten dang nhap: ");
        String username = scanner.nextLine();

        // Kiểm tra xem username đã tồn tại chưa
        if (DatabaseConnection.isUsernameExists(username)) {
            System.out.println("Username da ton tai, vui long chon username khac.");
            return;
        }

        System.out.print("Mat khau (6 ký tự): ");
        String password = scanner.nextLine();
        while (password.length() != 6) {
            System.out.print("Mat khau phai co 6 ky tu. Nhap lai: ");
            password = scanner.nextLine();
        }

        System.out.print("Vai tro: ");
        String role = scanner.nextLine();
        String employeeId = "EMP" + System.currentTimeMillis();
        Employee employee = new Employee(employeeId, name, username, password, role, java.time.LocalDateTime.now());
        DatabaseConnection.employees.add(employee);
        logService.logEmployeeAction(employeeId, "Them", employeeId);
        System.out.println("Nhan vien da duoc them.");
    }

    private void updateEmployee() {
        System.out.print("Nhap ma nhan vien can cap nhat: ");
        String employeeId = scanner.nextLine();
        
        // Kiểm tra nhân viên có tồn tại không
        Employee employeeToUpdate = null;
        for (Employee emp : DatabaseConnection.employees) {
            if (emp.getEmployeeId().equals(employeeId)) {
                employeeToUpdate = emp;
                break;
            }
        }
        
        if (employeeToUpdate == null) {
            System.out.println("Khong tim thay nhan vien voi ma nhan vien: " + employeeId);
            return;
        }
    
        System.out.print("Nhap mat khau nhan vien de cap nhat: ");
        String password = scanner.nextLine();
    
        // Kiểm tra mật khẩu đúng không
        if (!employeeToUpdate.getPassword().equals(password)) {
            System.out.println("Mat khau khong dung. Khong the cap nhat.");
            return;
        }
    
        // Cập nhật thông tin
        String newUsername;
        while (true) {
            System.out.print("Nhap username moi: ");
            newUsername = scanner.nextLine();
    
            // Kiểm tra username có trùng với username cũ của nhân viên không
            if (newUsername.equals(employeeToUpdate.getUsername())) {
                System.out.println("Username moi khong the trung voi username cu.");
                continue;
            }
    
            // Kiểm tra xem username này đã tồn tại chưa trong hệ thống
            if (DatabaseConnection.isUsernameExists(newUsername)) {
                System.out.println("Username da ton tai, vui long chon username khac.");
            } else {
                break;  // Nếu username không trùng, thoát khỏi vòng lặp
            }
        }
    
        // Kiểm tra mật khẩu mới có đúng 6 ký tự không
        String newPassword;
        while (true) {
            System.out.print("Nhap mat khau moi (6 ky tu): ");
            newPassword = scanner.nextLine();
            if (newPassword.length() == 6) {
                break;  // Nếu mật khẩu hợp lệ, thoát khỏi vòng lặp
            } else {
                System.out.println("Mat khau phai co 6 ky tu. Nhap lai.");
            }
        }
    
        // Cập nhật thông tin khác như tên đầy đủ và vai trò
        System.out.print("Nhap ten moi: ");
        String newFullName = scanner.nextLine();
        System.out.print("Nhap vai tro moi: ");
        String newRole = scanner.nextLine();
    
        // Cập nhật lại thông tin
        employeeToUpdate.setUsername(newUsername);
        employeeToUpdate.setPassword(newPassword);
        employeeToUpdate.setFullName(newFullName);
        employeeToUpdate.setRole(newRole);
    
        // Lưu lại danh sách đã cập nhật
        DatabaseConnection.updateEmployee(employeeToUpdate);
        System.out.println("Thong tin nhan vien da duoc cap nhat.");
    }

    private void deleteEmployee() {
        System.out.print("Nhap ma nhan vien can xoa: ");
        String employeeId = scanner.nextLine();
        
        // Kiểm tra nhân viên có tồn tại không
        Employee employeeToDelete = null;
        for (Employee emp : DatabaseConnection.employees) {
            if (emp.getEmployeeId().equals(employeeId)) {
                employeeToDelete = emp;
                break;
            }
        }
        
        if (employeeToDelete == null) {
            System.out.println("Khong tim thay nhan vien voi ma nhan vien: " + employeeId);
            return;
        }
    
        System.out.print("Nhap mat khau nhan vien de xoa: ");
        String password = scanner.nextLine();
    
        // Kiểm tra mật khẩu đúng không
        if (!employeeToDelete.getPassword().equals(password)) {
            System.out.println("Mat khau khong dung. Khong the xoa.");
            return;
        }
    
        // Xóa nhân viên
        DatabaseConnection.deleteEmployee(employeeId);
        System.out.println("Nhan vien da duoc xoa.");
    }

    private void showAllEmployees() {
        // Gọi phương thức loadEmployees() để đảm bảo danh sách nhân viên được cập nhật
    
        List<Employee> employees = DatabaseConnection.getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("Danh sach nhan vien hien tai rong.");
        } else {
            System.out.println("\n=========== Danh Sach Nhan Vien ===========");
            // Duyệt qua danh sách nhân viên và in ra thông tin
            for (Employee e : employees) {
                System.out.println(e.getEmployeeId() + " | " + e.getFullName() + " | " + e.getUsername() + " | " + e.getRole());
            }
        }
    }
}
