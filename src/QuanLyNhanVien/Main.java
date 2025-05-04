package QuanLyNhanVien;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        EmployeeService employeeService = new EmployeeService();
        LogService logService = new LogService();
        ReportService reportService = new ReportService();

        int choice;
        do {
            System.out.println("\n=========== MENU CHINH ===========");
            System.out.println("1. Quan ly nhan vien");
            System.out.println("2. Quan ly nhat ky");
            System.out.println("3. Bao cao");
            System.out.println("0. Thoat");
            System.out.print("Chon chuc nang: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    employeeService.displayMenu();
                    employeeService.execute(Integer.parseInt(scanner.nextLine()));
                    break;
                case 2:
                    logService.displayMenu();
                    logService.execute(Integer.parseInt(scanner.nextLine()));
                    break;
                case 3:
                    reportService.displayMenu();
                    reportService.execute(Integer.parseInt(scanner.nextLine()));
                    break;
                case 0:
                    System.out.println("Thoat chuong trinh.");
                    break;
                default:
                    System.out.println("Lua chon khong hop le.");
            }
        } while (choice != 0);
    }
}
