import model.Employee;
import service.EmployeeService;
import service.LogService;
import service.ReportService;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        EmployeeService employeeService = new EmployeeService();
        LogService logService = new LogService();
        ReportService reportService = new ReportService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Thêm nhân viên");
            System.out.println("2. Cập nhật nhân viên");
            System.out.println("3. Xóa nhân viên");
            System.out.println("4. Xem danh sách nhân viên");
            System.out.println("5. Xem nhật ký hoạt động");
            System.out.println("6. Xem báo cáo thống kê");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.print("Nhập tên đầy đủ: ");
                    String fullName = scanner.nextLine();
                    System.out.print("Nhập username: ");
                    String username = scanner.nextLine();
                    System.out.print("Nhập mật khẩu: ");
                    String password = scanner.nextLine();
                    System.out.print("Nhập vai trò (admin/staff): ");
                    String role = scanner.nextLine();

                    Employee newEmp = new Employee(UUID.randomUUID().toString(), fullName, username, password, role,
                            LocalDateTime.now());
                    employeeService.addEmployee(newEmp);
                    break;

                case 2:
                    System.out.print("Nhập ID nhân viên cần cập nhật: ");
                    String updateId = scanner.nextLine();
                    System.out.print("Nhập tên mới: ");
                    String newName = scanner.nextLine();
                    System.out.print("Nhập username mới: ");
                    String newUser = scanner.nextLine();
                    System.out.print("Nhập mật khẩu mới: ");
                    String newPass = scanner.nextLine();
                    System.out.print("Nhập vai trò mới: ");
                    String newRole = scanner.nextLine();
                    System.out.print("Nhập mật khẩu hiện tại của nhân viên: ");
                    String currentPassword = scanner.nextLine();

                    Employee updatedEmp = new Employee(updateId, newName, newUser, newPass, newRole,
                            LocalDateTime.now());
                    employeeService.updateEmployee(updatedEmp, currentPassword);
                    break;

                case 3:
                    System.out.print("Nhập ID nhân viên cần xóa: ");
                    String delId = scanner.nextLine();
                    System.out.print("Nhập mật khẩu của nhân viên: ");
                    String delPass = scanner.nextLine();
                    employeeService.deleteEmployee(delId, delPass);
                    break;

                case 4:
                    System.out.println("--- Danh sách nhân viên ---");
                    employeeService.getAllEmployees().forEach(emp -> {
                        System.out.println(emp.getEmployeeId() + " | " + emp.getFullName() + " | " + emp.getUsername()
                                + " | " + emp.getRole());
                    });
                    break;

                case 5:
                    System.out.println("(Tạm thời chưa hoàn thiện logService.getAllLogs())");
                    break;

                case 6:
                    System.out.println("--- BÁO CÁO ---");
                    System.out.println("Tổng khách hàng: " + reportService.getTotalCustomers());
                    System.out.println("Tổng giao dịch: " + reportService.getTotalTransactions());
                    System.out.println("Tổng khoản vay: " + reportService.getTotalLoans());
                    break;

                case 0:
                    System.out.println("Thoát chương trình.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
}
