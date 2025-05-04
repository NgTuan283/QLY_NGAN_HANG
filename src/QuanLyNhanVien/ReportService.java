package QuanLyNhanVien;

import java.sql.*;

public class ReportService {

    // Hiển thị menu báo cáo
    public void displayMenu() {
        System.out.println("\n=========== MENU BAO CÁO ===========");
        System.out.println("1. Thong ke so giao dich");
        System.out.println("2. Thong ke so khoan vay");
        System.out.println("0. Thoat");
        System.out.print("Chon chuc nang: ");
    }

    // Thực thi chức năng báo cáo dựa trên lựa chọn
    public void execute(int choice) {
        switch (choice) {
            case 1:
                showTotalTransactions();
                break;
            case 2:
                showTotalLoans();
                break;
            case 0:
                System.out.println("Thoat ra menu chinh.");
                break;
            default:
                System.out.println("Lua chon khong hop le.");
        }
    }

    // Báo cáo tổng số giao dịch
    private void showTotalTransactions() {
        int totalTransactions = count("Transaction");
        System.out.println("Tong so giao dich: " + totalTransactions);
    }

    // Báo cáo tổng số khoản vay
    private void showTotalLoans() {
        int totalLoans = count("Loan");
        System.out.println("Tong so khoan vay: " + totalLoans);
    }

    // Đếm số lượng giao dịch hoặc khoản vay từ cơ sở dữ liệu
    private int count(String table) {
        String query = "";
        if (table.equals("Transaction")) {
            query = "SELECT COUNT(*) FROM Transaction";
        } else if (table.equals("Loan")) {
            query = "SELECT COUNT(*) FROM Loan";
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "root", "123456");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getInt(1);  // Trả về số lượng
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;  // Trả về 0 nếu không tìm thấy dữ liệu
    }
}
