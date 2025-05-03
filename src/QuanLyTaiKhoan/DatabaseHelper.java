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

    public static int insertAccount(Account acc) throws SQLException {
        String sql = "INSERT INTO Account (pin, balance) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
    
            ps.setString(1, acc.getPin());
            ps.setDouble(2, acc.getBalance());
            ps.executeUpdate();
    
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // accountID được tạo bởi SQL
            }
        }
        return -1;
    }    

    public static void updateAccount(Account acc) throws SQLException {
        String sql = "UPDATE Account SET pin = ?, balance = ? WHERE accountID = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, acc.getPin());
            ps.setDouble(2, acc.getBalance());
            ps.setInt(3, acc.getAccountID());
            ps.executeUpdate();
        }
    }

    public static Account getAccountByID(int id) throws SQLException {
        String sql = "SELECT * FROM Account WHERE accountID = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Account(rs.getInt("accountID"), rs.getString("pin"), rs.getDouble("balance"));
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

    public static List<Account> getAllAccounts() throws SQLException {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM Account";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Account(rs.getInt("accountID"), rs.getString("pin"), rs.getDouble("balance")));
            }
        }
        return list;
    }

    public static void insertTransaction(Transaction t) throws SQLException {
        String sql = "INSERT INTO Transactions (accountID, amount, thoiGian, transactionType, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, t.getAcc().getAccountID());
            ps.setDouble(2, t.getamount());
            ps.setTimestamp(3, new Timestamp(t.getThoiGian().getTime()));
            ps.setString(4, t.gettransactionType());
            ps.setString(5, t.getstatus());
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
                Transaction t = new Transaction(acc, rs.getDouble("amount"), rs.getString("transactionType"), rs.getString("status"));
                t.settransactionID(rs.getInt("transactionID"));
                t.setThoiGian(rs.getTimestamp("thoiGian"));
                list.add(t);
            }
        }
        return list;
    }
}
