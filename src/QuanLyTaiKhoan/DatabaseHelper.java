// === DatabaseHelper.java ===
package QuanLyTaiKhoan;

import java.sql.*;
import java.util.*;

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

    // Cập nhật phương thức insertAccount để thêm userName
    public static int insertAccount(Account acc) throws SQLException {
        String sql = "INSERT INTO Account (pin, balance, userName) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
    
            ps.setString(1, acc.getPin());
            ps.setDouble(2, acc.getBalance());
            ps.setString(3, acc.getUserName()); // Thêm userName vào cơ sở dữ liệu
            ps.executeUpdate();
    
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // accountID được tạo bởi SQL
            }
        }
        return -1;
    }    


    // Cập nhật phương thức updateAccount để thêm userName
    public static void updateAccount(Account acc) throws SQLException {
        String sql = "UPDATE Account SET pin = ?, balance = ?, userName = ? WHERE accountID = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, acc.getPin());
            ps.setDouble(2, acc.getBalance());
            ps.setString(3, acc.getUserName()); // Cập nhật userName
            ps.setInt(4, acc.getAccountID());
            ps.executeUpdate();
        }
    }

    public static Account getAccountByID(int id) throws SQLException {
        String sql = "SELECT * FROM Account WHERE accountID = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Trả về Account với userName
                return new Account(rs.getInt("accountID"), rs.getString("pin"), rs.getDouble("balance"), rs.getString("userName"));
            }
        }
        return null;
    }

    public static void deleteAccount(int id) throws SQLException {
        String sql = "DELETE FROM Account WHERE accountID = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // Cập nhật phương thức getAllAccounts để bao gồm userName
    public static List<Account> getAllAccounts() throws SQLException {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM Account";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                // Thêm userName khi khởi tạo Account
                list.add(new Account(rs.getInt("accountID"), rs.getString("pin"), rs.getDouble("balance"), rs.getString("userName")));
            }
        }
        return list;
    }

    public static void insertTransaction(Transaction t) throws SQLException {
        String sql = "INSERT INTO Transactions ( accountID, loanID, amount, transactionType, transactionTime, descriptions) VALUES ( ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, t.getAcc().getAccountID());
            ps.setString(2, t.getLoanID());
            ps.setDouble(3, t.getamount());
            ps.setString(4, t.gettransactionType());
            ps.setTimestamp(5, new Timestamp(t.gettransactionTime().getTime()));     
            ps.setString(6, t.getDescription());
            ps.executeUpdate();
        }
    }

    public static List<Transaction> getTransactionsByAccountID(int id) throws SQLException {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM Transactions WHERE accountID = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Account acc = getAccountByID(id);
                Transaction t = new Transaction(acc, rs.getDouble("amount"), rs.getString("transactionType"), rs.getString("descriptions"));
                t.settransactionID(rs.getInt("transactionID"));
                t.settransactionTime(rs.getTimestamp("transactionTime"));
                list.add(t);
            }
        }
        return list;
    }
   
}