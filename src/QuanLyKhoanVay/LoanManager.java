import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LoanManager {
    public class Loan {
        private String loanId;
        private String accountNumber;
        private double loanAmount;
        private double interestRate;
        private int term;
        private Date creationDate;
        private double remainingAmount;

        public Loan(String loanId, String accountNumber, double loanAmount, 
                   double interestRate, int term) {
            this.loanId = loanId;
            this.accountNumber = accountNumber;
            this.loanAmount = loanAmount;
            this.interestRate = interestRate;
            this.term = term;
            this.creationDate = new Date();
            this.remainingAmount = loanAmount;
        }

        public String getLoanId() { return loanId; }
        public String getAccountNumber() { return accountNumber; }
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
            return String.format("Khoản vay %s - Tài khoản: %s\nSố tiền: %,.2f - Còn nợ: %,.2f\nLãi suất: %.1f%% - Kỳ hạn: %d tháng",
                    loanId, accountNumber, loanAmount, remainingAmount, interestRate * 100, term);
        }
    }

    private Scanner scanner;
    private TransactionHistory transactionService;

    public LoanManager(TransactionHistory transactionService) {
        this.scanner = new Scanner(System.in);
        this.transactionService = transactionService;
    }

    public void initializeSampleData() {
        try {
            DatabaseHelper.initializeSampleData();
        } catch (SQLException e) {
            System.out.println("Lỗi khi khởi tạo dữ liệu mẫu: " + e.getMessage());
        }
    }

    public void manageLoans() {
        while (true) {
            System.out.println("\n=== QUẢN LÝ KHOẢN VAY ===");
            System.out.println("1. Tạo khoản vay mới");
            System.out.println("2. Cập nhật khoản vay");
            System.out.println("3. Xóa khoản vay");
            System.out.println("4. Xem danh sách khoản vay");
            System.out.println("5. Thanh toán khoản vay");
            System.out.println("0. Quay lại");
            System.out.print("Lựa chọn: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    createNewLoan();
                    break;
                case "2":
                    updateLoan();
                    break;
                case "3":
                    deleteLoan();
                    break;
                case "4":
                    viewLoanList();
                    break;
                case "5":
                    makeLoanPayment();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ");
            }
        }
    }

    private void createNewLoan() {
        System.out.println("\n=== TẠO KHOẢN VAY MỚI ===");
        System.out.print("Nhập số tài khoản: ");
        String accountNumber = scanner.nextLine();

        System.out.print("Nhập số tiền vay (VND): ");
        double amount = Double.parseDouble(scanner.nextLine());

        System.out.print("Nhập lãi suất (%/Tháng): ");
        double interestRate = Double.parseDouble(scanner.nextLine()) / 100;

        System.out.print("Nhập kỳ hạn (tháng): ");
        int term = Integer.parseInt(scanner.nextLine());

        String loanId = "LOAN" + String.format("%03d", getNextLoanId());
        Loan newLoan = new Loan(loanId, accountNumber, amount, interestRate, term);
        
        try {
            DatabaseHelper.insertLoan(newLoan);
            System.out.println("Tạo khoản vay thành công");
            System.out.println(newLoan);
        } catch (SQLException e) {
            System.out.println("Lỗi khi tạo khoản vay: " + e.getMessage());
        }
    }

    private int getNextLoanId() {
        try {
            List<Loan> loans = DatabaseHelper.getAllLoans();
            return loans.size() + 1;
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy danh sách khoản vay: " + e.getMessage());
            return 1;
        }
    }

    private void updateLoan() {
        System.out.println("\n=== CẬP NHẬT KHOẢN VAY ===");
        System.out.print("Nhập mã khoản vay: ");
        String loanId = scanner.nextLine();

        try {
            Loan loan = DatabaseHelper.getLoanById(loanId);
            if (loan == null) {
                System.out.println("Không tìm thấy khoản vay");
                return;
            }

            System.out.println("Thông tin hiện tại:");
            System.out.println(loan);

            System.out.print("Nhập số tiền mới (VND) (bỏ qua để giữ nguyên): ");
            String newAmount = scanner.nextLine();
            if (!newAmount.isEmpty()) {
                double amount = Double.parseDouble(newAmount);
                loan.setLoanAmount(amount);
                loan.setRemainingAmount(amount);
            }

            System.out.print("Nhập lãi suất mới (%/Tháng) (bỏ qua để giữ nguyên): ");
            String newInterestRate = scanner.nextLine();
            if (!newInterestRate.isEmpty()) {
                loan.setInterestRate(Double.parseDouble(newInterestRate) / 100);
            }

            DatabaseHelper.updateLoan(loan);
            System.out.println("Cập nhật thành công");
            System.out.println("Thông tin mới:");
            System.out.println(loan);
        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật khoản vay: " + e.getMessage());
        }
    }

    private void deleteLoan() {
        System.out.println("\n=== XÓA KHOẢN VAY ===");
        System.out.print("Nhập mã khoản vay cần xóa: ");
        String loanId = scanner.nextLine();

        try {
            DatabaseHelper.deleteLoan(loanId);
            System.out.println("Đã xóa khoản vay " + loanId);
        } catch (SQLException e) {
            System.out.println("Lỗi khi xóa khoản vay: " + e.getMessage());
        }
    }

    private void viewLoanList() {
        try {
            List<Loan> loanList = DatabaseHelper.getAllLoans();
            
            if (loanList.isEmpty()) {
                System.out.println("Không có khoản vay nào");
                return;
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            System.out.println("\n=== DANH SÁCH KHOẢN VAY ===");
            for (Loan loan : loanList) {
                System.out.printf("%s - Tài khoản: %s - Số tiền: %,.2f - Còn nợ: %,.2f - Lãi suất: %.1f%% - Kỳ hạn: %d tháng - Ngày tạo: %s\n",
                        loan.getLoanId(), loan.getAccountNumber(), loan.getLoanAmount(), 
                        loan.getRemainingAmount(), loan.getInterestRate() * 100,
                        loan.getTerm(), dateFormat.format(loan.getCreationDate()));
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy danh sách khoản vay: " + e.getMessage());
        }
    }

    private void makeLoanPayment() {
        System.out.println("\n=== THANH TOÁN KHOẢN VAY ===");
        System.out.print("Nhập mã khoản vay: ");
        String loanId = scanner.nextLine();

        try {
            Loan loan = DatabaseHelper.getLoanById(loanId);
            if (loan == null) {
                System.out.println("Không tìm thấy khoản vay");
                return;
            }

            System.out.println("Thông tin khoản vay:");
            System.out.println(loan);

            System.out.print("Nhập số tiền thanh toán (VND): ");
            double amount = Double.parseDouble(scanner.nextLine());

            if (amount <= 0) {
                System.out.println("Số tiền phải lớn hơn 0");
                return;
            }

            if (amount > loan.getRemainingAmount()) {
                System.out.println("Số tiền vượt quá số dư nợ");
                return;
            }

            loan.setRemainingAmount(loan.getRemainingAmount() - amount);
            DatabaseHelper.updateLoan(loan);
            
            System.out.printf("Thanh toán thành công %,.2f VND\n", amount);
            System.out.printf("Số dư nợ còn lại: %,.2f VND\n", loan.getRemainingAmount());

            String transactionId = "TXN" + (transactionService.getTransactions().size() + 1);
            transactionService.addTransaction(new TransactionHistory.Transaction(
                transactionId, loan.getAccountNumber(), "NGÂN HÀNG", 
                loanId, amount, new Date(), 
                "Thanh toán khoản vay " + loanId
            ));
        } catch (SQLException e) {
            System.out.println("Lỗi khi thanh toán khoản vay: " + e.getMessage());
        }
    }
}