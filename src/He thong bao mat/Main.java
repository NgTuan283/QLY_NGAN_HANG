
// DatabaseConnection.java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Main.java
import model.Employee;
import service.EmployeeService;
import service.LogService;
import service.ReportService;

import java.time.LocalDateTime;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        EmployeeService employeeService = new EmployeeService();
        LogService logService = new LogService();
        ReportService reportService = new ReportService();

        // Test thêm nhân viên
        Employee newEmp = new Employee(UUID.randomUUID().toString(), "Nguyen Van A", "nguyenvana", "123456", "admin",
                LocalDateTime.now());
        employeeService.addEmployee(newEmp);

        // Test xem danh sách nhân viên
        employeeService.getAllEmployees().forEach(emp -> {
            System.out.println(emp.getFullName() + " | " + emp.getUsername());
        });

        // Test xem nhật ký
        logService.getAllLogs().forEach(log -> {
            System.out.println(log.getAction() + " on " + log.getTarget());
        });

        // Test thống kê
        System.out.println("Total Customers: " + reportService.getTotalCustomers());
        System.out.println("Total Transactions: " + reportService.getTotalTransactions());
        System.out.println("Total Loans: " + reportService.getTotalLoans());
    }
}
