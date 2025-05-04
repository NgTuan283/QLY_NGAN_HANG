package QuanLyNhanVien;

import java.time.LocalDateTime;
import java.util.List;

 class Log {
    private String logId;
    private String employeeId;
    private String action;
    private String target;
    private String targetId;
    private LocalDateTime actionTime;

    // Constructor
    public Log(String logId, String employeeId, String action, String target, String targetId, LocalDateTime actionTime) {
        this.logId = logId;
        this.employeeId = employeeId;
        this.action = action;
        this.target = target;
        this.targetId = targetId;
        this.actionTime = actionTime;
    }

    // Getter and Setter methods
    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public LocalDateTime getActionTime() {
        return actionTime;
    }

    public void setActionTime(LocalDateTime actionTime) {
        this.actionTime = actionTime;
    }
}

public class LogService {
    public void displayMenu() {
        System.out.println("\n=========== MENU NHAT KY ===========");
        System.out.println("1. Xem toan bo nhat ky");
        System.out.println("2. Xem nhat ky theo ma nhan vien");
        System.out.println("0. Thoat");
        System.out.print("Chon chuc nang: ");
    }

    public void execute(int choice) {
        switch (choice) {
            case 1:
                showAllLogs();
                break;
            case 2:
                showLogsByEmployee();
                break;
            case 0:
                System.out.println("Thoat ra menu chinh.");
                break;
            default:
                System.out.println("Lua chon khong hop le.");
        }
    }

    private void showAllLogs() {
        List<Log> logs = DatabaseConnection4.getLogsByEmployeeId("all");
        logs.forEach(log -> System.out.println(log.getLogId() + " | " + log.getAction() + " | " + log.getTargetId()));
    }

    private void showLogsByEmployee() {
        System.out.print("Nhap ma nhan vien: ");
        String empId = new java.util.Scanner(System.in).nextLine();
        List<Log> logs = getLogsByEmployeeId(empId);
        logs.forEach(log -> System.out.println(log.getLogId() + " | " + log.getAction() + " | " + log.getTargetId()));
    }

    private List<Log> getLogsByEmployeeId(String employeeId) {
        return DatabaseConnection4.getLogsByEmployeeId(employeeId);
    }

    public void logEmployeeAction(String employeeId, String action, String targetId) {
        Log log = new Log("LOG" + System.currentTimeMillis(), employeeId, action, "Employee", targetId, java.time.LocalDateTime.now());
        DatabaseConnection4.addLog(log);
    }
}
