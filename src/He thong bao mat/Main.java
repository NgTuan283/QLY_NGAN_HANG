import model.Employee;
import model.Log;
import service.EmployeeService;
import service.LogService;
import service.ReportService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EmployeeService employeeService = new EmployeeService();
        LogService logService = new LogService();
        ReportService reportService = new ReportService();

        int choice;
        do {
            System.out.println("\n=========== MENU HE THONG ===========");
            System.out.println("1. Them nhan vien");
            System.out.println("2. Cap nhat thong tin nhan vien");
            System.out.println("3. Xoa nhan vien");
            System.out.println("4. Hien thi danh sach nhan vien");
            System.out.println("5. Tim kiem nhan vien theo ten");
            System.out.println("6. Xem toan bo nhat ky");
            System.out.println("7. Xem nhat ky theo ma nhan vien");
            System.out.println("8. Xem nhat ky theo khoang thoi gian");
            System.out.println("9. Tim nhat ky theo ma log");
            System.out.println("10. Tong so nhan vien");
            System.out.println("11. Thong ke so giao dich");
            System.out.println("12. Thong ke so khoan vay");
            System.out.println("0. Thoat");
            System.out.print("Chon chuc nang: ");

            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> {
                    System.out.print("Ho ten: ");
                    String name = scanner.nextLine();
                    System.out.print("Ten dang nhap: ");
                    String username = scanner.nextLine();
                    System.out.print("Mat khau: ");
                    String password = scanner.nextLine();
                    System.out.print("Vai tro: ");
                    String role = scanner.nextLine();
                    String id = UUID.randomUUID().toString();
                    Employee emp = new Employee(id, name, username, password, role, LocalDateTime.now());
                    employeeService.addEmployee(emp);
                    logService.logEmployeeAction(id, "Them", id);
                    System.out.println("Da them nhan vien.");
                }
                case 2 -> {
                    System.out.print("Ma nhan vien: ");
                    String id = scanner.nextLine();
                    System.out.print("Ten moi: ");
                    String newName = scanner.nextLine();
                    System.out.print("Ten dang nhap moi: ");
                    String newUsername = scanner.nextLine();
                    System.out.print("Mat khau moi: ");
                    String newPassword = scanner.nextLine();
                    System.out.print("Vai tro moi: ");
                    String newRole = scanner.nextLine();
                    System.out.print("Mat khau cu: ");
                    String oldPassword = scanner.nextLine();
                    Employee updatedEmp = new Employee(id, newName, newUsername, newPassword, newRole, LocalDateTime.now());
                    employeeService.updateEmployee(updatedEmp, oldPassword);
                    logService.logEmployeeAction(id, "Cap nhat", id);
                }
                case 3 -> {
                    System.out.print("Ma nhan vien: ");
                    String delId = scanner.nextLine();
                    System.out.print("Mat khau: ");
                    String delPass = scanner.nextLine();
                    employeeService.deleteEmployee(delId, delPass);
                    logService.logEmployeeAction(delId, "Xoa", delId);
                }
                case 4 -> {
                    List<Employee> all = employeeService.getAllEmployees();
                    all.forEach(e -> System.out.println(e.getEmployeeId() + " | " + e.getFullName() + " | " + e.getUsername() + " | " + e.getRole()));
                }
                case 5 -> {
                    System.out.print("Nhap ten nhan vien can tim: ");
                    String keyword = scanner.nextLine();
                    List<Employee> found = employeeService.searchByName(keyword);
                    found.forEach(e -> System.out.println(e.getEmployeeId() + " | " + e.getFullName()));
                }
                case 6 -> {
                    List<Log> allLogs = logService.getAllLogs();
                    allLogs.forEach(log -> System.out.println(log.getLogId() + " | " + log.getAction() + " | " + log.getTargetId()));
                }
                case 7 -> {
                    System.out.print("Nhap ma nhan vien: ");
                    String empId = scanner.nextLine();
                    List<Log> logs = logService.getLogsByEmployeeId(empId);
                    logs.forEach(log -> System.out.println(log.getLogId() + " | " + log.getAction() + " | " + log.getTargetId()));
                }
                case 8 -> {
                    System.out.print("Tu ngay (yyyy-MM-dd): ");
                    LocalDateTime from = LocalDateTime.parse(scanner.nextLine() + "T00:00:00");
                    System.out.print("Den ngay (yyyy-MM-dd): ");
                    LocalDateTime to = LocalDateTime.parse(scanner.nextLine() + "T23:59:59");
                    List<Log> timeLogs = logService.getLogsByTimeRange(from, to);
                    timeLogs.forEach(log -> System.out.println(log.getLogId() + " | " + log.getAction() + " | " + log.getActionTime()));
                }
                case 9 -> {
                    System.out.print("Nhap ma log: ");
                    String logId = scanner.nextLine();
                    Log log = logService.getLogById(logId);
                    if (log != null) System.out.println(log.getLogId() + " | " + log.getAction() + " | " + log.getActionTime());
                    else System.out.println("Khong tim thay log.");
                }
                case 10 -> System.out.println("Tong so nhan vien: " + employeeService.countEmployees());
                case 11 -> System.out.println("Tong so giao dich: " + reportService.getTotalTransactions());
                case 12 -> System.out.println("Tong so khoan vay: " + reportService.getTotalLoans());
                case 0 -> System.out.println("Thoat chuong trinh.");
                default -> System.out.println("Lua chon khong hop le.");
            }
        } while (choice != 0);
    }
}
