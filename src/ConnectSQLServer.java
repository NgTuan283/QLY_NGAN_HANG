/*import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectSQLServer {
    public static void main(String[] args) {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=BankDB;encrypt=true;trustServerCertificate=true";
        String user = "admin";
        String password = "020803Qt";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println(" Ket noi thanh cong");

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Account");

            while (rs.next()) {
                System.out.println(rs.getString(1));
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}*/
