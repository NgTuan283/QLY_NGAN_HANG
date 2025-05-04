package QuanLyKhoanVay;
import java.sql.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    // Cấu hình kết nối dùng SQL Server Authentication với tài khoản 'admin'
    private static final String URL = "jdbc:sqlserver://NGUYEN-TUAN\\NGUYENTUAN:1433;databaseName=BankDB;encrypt=true;trustServerCertificate=true";
    private static final String USER = "admin";
    private static final String PASSWORD = "020803Qt"; // Thay bằng mật khẩu thật

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void insertTransaction(TransactionHistory.Transaction transaction) throws SQLException {
        String sql = "INSERT INTO Transactions (transactionID, accountID, loanID, amount, transactionType, transactionTime, descriptions) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, transaction.getTransactionID());
            ps.setString(2, transaction.getAccountID());
            ps.setString(4, transaction.getLoanID());
            ps.setDouble(5, transaction.getAmount());
            ps.setString(6, transaction.getTranctionType());
            ps.setTimestamp(7, new Timestamp(transaction.gettransactionTime().getTime()));
            ps.setString(8, transaction.getDescription());
            
            ps.executeUpdate();
        }
    }

    public static List<TransactionHistory.Transaction> getTransactionsByAccount(String accountID) throws SQLException {
        List<TransactionHistory.Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM Transactions WHERE sourceAccount = ? OR destinationAccount = ? ORDER BY time DESC";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, accountID);
            ps.setString(2, accountID);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TransactionHistory.Transaction t = new TransactionHistory.Transaction(
                    rs.getString("accountID"),
                    rs.getString("loanID"),
                    rs.getDouble("amount"),
                    rs.getString("transactionType"),
                    new Date(rs.getTimestamp("time").getTime()),
                    rs.getString("descriptions")
                );
                transactions.add(t);
            }
        }
        return transactions;
    }

    public static void insertLoan(LoanManager.Loan loan) throws SQLException {
        String sql = "INSERT INTO Loans (loanID, accountID, loanAmount, interestRate, term, creationDate, remainingAmount) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, loan.getLoanID());
            ps.setString(2, loan.getAccountID());
            ps.setDouble(3, loan.getLoanAmount());
            ps.setDouble(4, loan.getInterestRate());
            ps.setInt(5, loan.getTerm());
            ps.setTimestamp(6, new Timestamp(loan.getCreationDate().getTime()));
            ps.setDouble(7, loan.getRemainingAmount());
            ps.executeUpdate();
        }
    }

    public static void updateLoan(LoanManager.Loan loan) throws SQLException {
        String sql = "UPDATE Loans SET loanAmount = ?, interestRate = ?, term = ?, remainingAmount = ? " +
                     "WHERE loanID = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setDouble(1, loan.getLoanAmount());
            ps.setDouble(2, loan.getInterestRate());
            ps.setInt(3, loan.getTerm());
            ps.setDouble(4, loan.getRemainingAmount());
            ps.setString(5, loan.getLoanID());
            
            ps.executeUpdate();
        }
    }

    public static void deleteLoan(String loanID) throws SQLException {
        String sql = "DELETE FROM Loans WHERE loanID = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, loanID);
            ps.executeUpdate();
        }
    }

    public static List<LoanManager.Loan> getAllLoans() throws SQLException {
        List<LoanManager.Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM Loans";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                LoanManager loanManager = new LoanManager(null);
                LoanManager.Loan loan = loanManager.new Loan(
                    rs.getString("loanID"),
                    rs.getString("accountID"),
                    rs.getDouble("loanAmount"),
                    rs.getDouble("interestRate"),
                    rs.getInt("term")
                );
                loan.setRemainingAmount(rs.getDouble("remainingAmount"));
                loans.add(loan);
            }
        }
        return loans;
    }

    public static LoanManager.Loan getLoanByID(String loanID) throws SQLException {
        String sql = "SELECT * FROM Loans WHERE loanID = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, loanID);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                LoanManager loanManager = new LoanManager(null);
                LoanManager.Loan loan = loanManager.new Loan(
                    rs.getString("loanID"),
                    rs.getString("accountID"),
                    rs.getDouble("loanAmount"),
                    rs.getDouble("interestRate"),
                    rs.getInt("term")
                );
                loan.setRemainingAmount(rs.getDouble("remainingAmount"));
                return loan;
            }
        }
        return null;
    }
}