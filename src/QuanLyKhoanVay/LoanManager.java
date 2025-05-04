

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LoanManager extends Menu {
    public class Loan {
        private String loanID;
        private String accountID;
        private String customerID;
        private double loanAmount;
        private double interestRate;
        private int term;
        private Date creationDate;
        private double remainingAmount;

        public Loan(String loanID, String accountID, double loanAmount, 
                   double interestRate, int term) {
            this.loanID = loanID;
            this.accountID = accountID;
            this.loanAmount = loanAmount;
            this.interestRate = interestRate;
            this.term = term;
            this.creationDate = new Date();
            this.remainingAmount = loanAmount;
        }

        public Loan(String loanID, String accountID, String customerID, double loanAmount, 
                   double interestRate, int term) {
            this.loanID = loanID;
            this.accountID = accountID;
            this.customerID = customerID;
            this.loanAmount = loanAmount;
            this.interestRate = interestRate;
            this.term = term;
            this.creationDate = new Date();
            this.remainingAmount = loanAmount;
        }

        public String getLoanID() { return loanID; }
        public String getAccountID() { return accountID; }
        public String getCustomerID(){ return customerID; }
        public double getLoanAmount() { return loanAmount; }
        public void setLoanAmount(double loanAmount) { this.loanAmount = loanAmount; }
        public double getInterestRate() { return interestRate; }
        public void setInterestRate(double interestRate) { this.interestRate = interestRate; }
        public int getTerm() { return term; }
        public Date getCreationDate() { return creationDate; }
        public double getRemainingAmount() { return remainingAmount; }
        public void setRemainingAmount(double remainingAmount) { this.remainingAmount = remainingAmount; }

        @Override
        public String toString() {
            return String.format("Khoan vay %s - Tai khoan: %s\nSo tien: %,.2f - Con no: %,.2f\nLai suat: %.1f%% - Ky han: %d thang",
                    loanID, accountID, loanAmount, remainingAmount, interestRate * 100, term);
        }
    }

    private TransactionHistory transactionService;

    public LoanManager(TransactionHistory transactionService) {
        super(new String[] {
            "QUAN LY KHOAN VAY",
            "CHUC NANG KHOAN VAY",
            "Tao khoan vay moi",
            "Cap nhat khoan vay",
            "Xoa khoan vay",
            "Xem danh sach khoan vay",
            "Thanh toan khoan vay",
            "Quay lai"
        });
        this.transactionService = transactionService;
    }

    @Override
    public void execute(int n) {
        switch (n) {
            case 1:
                createNewLoan();
                break;
            case 2:
                updateLoan();
                break;
            case 3:
                deleteLoan();
                break;
            case 4:
                viewLoanList();
                break;
            case 5:
                makeLoanPayment();
                break;
            case 6:
            throw new MenuExitException();
        }
    }

    private void createNewLoan() {
        System.out.println("\n=== TAO KHOAN VAY MOI ===");
        System.out.print("Nhap so tai khoan: ");
        String accountID = sc.nextLine();

        System.out.print("Nhap so tien vay (VND): ");
        double amount = Double.parseDouble(sc.nextLine());

        System.out.print("Nhap lai suat (%/Thang): ");
        double interestRate = Double.parseDouble(sc.nextLine()) / 100;

        System.out.print("Nhap ky han (thang): ");
        int term = Integer.parseInt(sc.nextLine());

        String loanID = "LOAN" + String.format("%03d", getNextLoanID());
        Loan newLoan = new Loan(loanID, accountID, amount, interestRate, term);
        
        try {
            DatabaseHelper.insertLoan(newLoan);
            System.out.println("Tao khoan vay thanh cong");
            System.out.println(newLoan);
        } catch (SQLException e) {
            System.out.println("Loi khi tao khoan vay: " + e.getMessage());
        }
    }

    private int getNextLoanID() {
        try {
            List<Loan> loans = DatabaseHelper.getAllLoan();
            return loans.size() + 1;
        } catch (SQLException e) {
            System.out.println("Loi khi lay danh sach khoan vay: " + e.getMessage());
            return 1;
        }
    }

    private void updateLoan() {
        System.out.println("\n=== CAP NHAT KHOAN VAY ===");
        System.out.print("Nhap ma khoan vay: ");
        String loanID = sc.nextLine();

        try {
            Loan loan = DatabaseHelper.getLoanByID(loanID);
            if (loan == null) {
                System.out.println("Khong tim thay khoan vay");
                return;
            }

            System.out.println("Thong tin hien tai:");
            System.out.println(loan);

            System.out.print("Nhap so tien moi (VND) (bo qua de giu nguyen): ");
            String newAmount = sc.nextLine();
            if (!newAmount.isEmpty()) {
                double amount = Double.parseDouble(newAmount);
                loan.setLoanAmount(amount);
                loan.setRemainingAmount(amount);
            }

            System.out.print("Nhap lai suat moi (%/Thang) (bo qua de giu nguyen): ");
            String newInterestRate = sc.nextLine();
            if (!newInterestRate.isEmpty()) {
                loan.setInterestRate(Double.parseDouble(newInterestRate) / 100);
            }

            DatabaseHelper.updateLoan(loan);
            System.out.println("Cap nhat thanh cong");
            System.out.println("Thong tin moi:");
            System.out.println(loan);
        } catch (SQLException e) {
            System.out.println("Loi khi cap nhat khoan vay: " + e.getMessage());
        }
    }

    private void deleteLoan() {
        System.out.println("\n=== XOA KHOAN VAY ===");
        System.out.print("Nhap ma khoan vay can xoa: ");
        String loanID = sc.nextLine();

        try {
            DatabaseHelper.deleteLoan(loanID);
            System.out.println("Da xoa khoan vay " + loanID);
        } catch (SQLException e) {
            System.out.println("Loi khi xoa khoan vay: " + e.getMessage());
        }
    }

    private void viewLoanList() {
        try {
            List<Loan> loanList = DatabaseHelper.getAllLoan();
            
            if (loanList.isEmpty()) {
                System.out.println("Khong co khoan vay nao");
                return;
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            System.out.println("\n=== DANH SACH KHOAN VAY ===");
            for (Loan loan : loanList) {
                System.out.printf("%s - Tai khoan: %s - So tien: %,.2f - Con no: %,.2f - Lai suat: %.1f%% - Ky han: %d thang - Ngay tao: %s\n",
                        loan.getLoanID(), loan.getAccountID(), loan.getLoanAmount(), 
                        loan.getRemainingAmount(), loan.getInterestRate() * 100,
                        loan.getTerm(), dateFormat.format(loan.getCreationDate()));
            }
        } catch (SQLException e) {
            System.out.println("Loi khi lay danh sach khoan vay: " + e.getMessage());
        }
    }

    public void makeLoanPayment() {
        System.out.println("\n=== THANH TOAN KHOAN VAY ===");
        System.out.print("Nhap ma khoan vay: ");
        String loanId = sc.nextLine();
    
        try {
            Loan loan = DatabaseHelper.getLoanByID(loanId);
            if (loan == null) {
                System.out.println("Khong tim thay khoan vay");
                return;
            }
    
            System.out.println("Thong tin khoan vay:");
            System.out.println(loan);
    
            System.out.print("Nhap so tien thanh toan (VND): ");
            double amount = Double.parseDouble(sc.nextLine());
    
            if (amount <= 0) {
                System.out.println("So tien phai lon hon 0");
                return;
            }
    
            if (amount > loan.getRemainingAmount()) {
                System.out.println("So tien vuot qua so du no");
                return;
            }
    
            loan.setRemainingAmount(loan.getRemainingAmount() - amount);
            DatabaseHelper.updateLoan(loan);
            
            System.out.printf("Thanh toan thanh cong %,.2f VND\n", amount);
            System.out.printf("So du no con lai: %,.2f VND\n", loan.getRemainingAmount());
    
            String transactionType = "Thanh toan khoan vay " + loanId;
            
            TransactionHistory.Transaction transaction = new TransactionHistory.Transaction(
                loan.getAccountID(),
                loan.getLoanID(),
                amount,
                transactionType,
                new Date(),
                "Thanh toan khoan vay thanh cong"
            );
    
            transactionService.addTransaction(transaction);
        } catch (SQLException e) {
            System.out.println("Loi khi thanh toan khoan vay: " + e.getMessage());
        }
    }
}