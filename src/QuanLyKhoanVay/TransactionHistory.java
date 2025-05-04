package QuanLyKhoanVay;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TransactionHistory {
    public static class Transaction {
        private int transactionID;
        private String accountID;
        private String loanID;
        private double amount;
        private Date transactionTime;
        private String transactionType;
        private String description;

        public Transaction( String accountID,String loanID, double amount, String transactionType, Date transactionTime, String description) {

            this.accountID = accountID;
            this.loanID = loanID;
            this.amount = amount;
            this.transactionType = transactionType;
            this.transactionTime = transactionTime;
            this.description = description;
        }

        public int getTransactionID() { return transactionID; }
        public String getAccountID() { return accountID; }
        public String getLoanID() { return loanID; }
        public double getAmount() { return amount; }
        public String getTranctionType(){ return transactionType; }
        public Date gettransactionTime() { return transactionTime; }
        public String getDescription() { return description; }
    }

    private Scanner scanner;

    public TransactionHistory() {
        this.scanner = new Scanner(System.in);
    }


    public void viewTransactionHistory() {
        System.out.println("\n=== LICH SU GIAO DICH ===");
        System.out.print("Nhap so tai khoan: ");
        String accountID = scanner.nextLine();

        try {
            List<Transaction> accountTransactions = DatabaseHelper.getTransactionsByAccount(accountID);

            if (accountTransactions.isEmpty()) {
                System.out.println("Khong tim thay giao dich nao");
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                System.out.println("\nLich su giao dich cua " + accountID + ":");
                
                for (Transaction t : accountTransactions) {
                    String type = t.getAccountID().equals(accountID) ? "CHUYEN DI" : "NHAN VE";
                    System.out.printf("[%s] %s - So tien: %,.2f - %s -> %s - Ngay: %s - Mo ta: %s\n",
                            t.getTransactionID(), type, t.getAmount(), 
                            t.getAccountID(),
                            dateFormat.format(t.gettransactionTime()), t.getDescription());
                }
            }
        } catch (SQLException e) {
            System.out.println("Loi khi truy van CSDL: " + e.getMessage());
        }
    }

    public void addTransaction(Transaction transaction) {
        try {
            DatabaseHelper.insertTransaction(transaction);
        } catch (SQLException e) {
            System.out.println("Loi khi luu giao dich: " + e.getMessage());
        }
    }

    public List<Transaction> getTransactions() {
        try {
            return DatabaseHelper.getTransactionsByAccount(null);
        } catch (SQLException e) {
            System.out.println("Loi khi lay danh sach giao dich: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
