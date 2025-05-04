package QuanLyNhanVien;

public class ReportService {
    public void displayMenu() {
        System.out.println("\n=========== MENU BAO CÁO ===========");
        System.out.println("1. Thong ke so giao dich");
        System.out.println("2. Thong ke so khoan vay");
        System.out.println("0. Thoat");
        System.out.print("Chon chuc nang: ");
    }

    public void execute(int int1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

    // public void execute(int choice) {
    //     switch (choice) {
    //         case 1:
    //             showTotalTransactions();
    //             break;
    //         case 2:
    //             showTotalLoans();
    //             break;
    //         case 0:
    //             System.out.println("Thoat ra menu chinh.");
    //             break;
    //         default:
    //             System.out.println("Lua chon khong hop le.");
    //     }
    // }

    // private void showTotalTransactions() {
    //     int totalTransactions = count("Transaction");
    //     System.out.println("Tong so giao dich: " + totalTransactions);
    // }

    // private void showTotalLoans() {
    //     int totalLoans = count("Loan");
    //     System.out.println("Tong so khoan vay: " + totalLoans);
    // }

    // private int count(String table) {
    //     if (table.equals("Transaction")) {
    //         return DatabaseConnection.transactions.size(); // Giả sử bạn có danh sách các giao dịch
    //     } else if (table.equals("Loan")) {
    //         return DatabaseConnection.loans.size(); // Giả sử bạn có danh sách các khoản vay
    //     }
    //     return 0;
    // }
}
