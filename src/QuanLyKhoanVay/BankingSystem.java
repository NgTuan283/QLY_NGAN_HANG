package QuanLyKhoanVay;
import java.util.Scanner;

public class BankingSystem {
    public static void main(String[] args) {
        TransactionHistory transactionService = new TransactionHistory();
        LoanManager loanManager = new LoanManager(transactionService);
        
        // Khoi dong he thong
        startSystem(transactionService, loanManager);
    }

    public static void startSystem(TransactionHistory transactionService, LoanManager loanManager) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n=== HE THONG NGAN HANG ===");
            System.out.println("1. Xem lich su giao dich");
            System.out.println("2. Quan ly khoan vay");
            System.out.println("0. Thoat");
            System.out.print("Lua chon: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    transactionService.viewTransactionHistory();
                    break;
                case "2":
                    loanManager.manageLoans();
                    break;
                case "0":
                    System.out.println("Cam on ban da su dung dich vu");
                    return;
                default:
                    System.out.println("Lua chon khong hop le");
            }
        }
    }
}