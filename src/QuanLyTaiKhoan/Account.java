// === Account.java ===
package QuanLyTaiKhoan;

import java.util.List;
import java.util.Scanner;


public class Account extends Menu {
    private int accountID;
    private String pin;
    private Double balance;
    private static int countAccountID = 10000;
    static Scanner sc = new Scanner(System.in);

    public Account() {}

    public Account(int id, String pin, double balance) {
        this.accountID = id;
        this.pin = pin;
        this.balance = balance;
    }

    public Account(double balance, String pin) {
        this.accountID = countAccountID++;
        this.pin = pin;
        this.balance = balance;
    }

    public void doAddAccount() {
        try {
            System.out.print("Nhap PIN (6 chu so): ");
            String pin = sc.nextLine();
            System.out.print("Nhap so du ban dau: ");
            double balance = Double.parseDouble(sc.nextLine());

            if (pin.length() != 6 || !pin.matches("\\d{6}")) {
                System.out.println("PIN phai co 6 chu so!");
                return;
            }

            Account acc = new Account(balance, pin);
            DatabaseHelper.insertAccount(acc);
            System.out.println(">>> Tao tai khoan thanh cong! Ma tai khoan: " + acc.getAccountID());
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }

    public void doChangePin() {
        try {
            System.out.print("Nhap ma tai khoan can doi PIN: ");
            int id = Integer.parseInt(sc.nextLine());
            Account acc = DatabaseHelper.getAccountByID(id);

            if (acc == null) {
                System.out.println("Khong tim thay tai khoan!");
                return;
            }

            System.out.print("Nhap PIN moi: ");
            String newPin = sc.nextLine();
            System.out.print("Nhap lai PIN moi: ");
            String confirmPin = sc.nextLine();

            if (!newPin.equals(confirmPin) || !newPin.matches("\\d{6}")) {
                System.out.println("PIN moi khong hop le hoac khong trung khop!");
                return;
            }

            acc.setPin(newPin);
            DatabaseHelper.updateAccount(acc);
            DatabaseHelper.insertTransaction(new Transaction(acc, 0, "Doi PIN", "Doi PIN thanh cong"));
            System.out.println(">>> Doi PIN thanh cong!");
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }

    public void doDeleteAccount() {
        try {
            System.out.print("Nhap ma tai khoan can xoa: ");
            int id = Integer.parseInt(sc.nextLine());
            DatabaseHelper.deleteAccount(id);
            System.out.println(">>> Xoa tai khoan thanh cong!");
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }

    public void doViewAccountList() {
        try {
            List<Account> list = DatabaseHelper.getAllAccounts();
            System.out.printf("%-10s| %-10s| %-10s\n", "ID", "PIN", "So du");
            for (Account acc : list) {
                System.out.printf("%-10d| %-10s| %-10.2f\n", acc.getAccountID(), acc.getPin(), acc.getBalance());
            }
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }

    public void doDepositToAccount() {
        try {
            System.out.print("Nhap ma tai khoan: ");
            int id = Integer.parseInt(sc.nextLine());
            Account acc = DatabaseHelper.getAccountByID(id);
            if (acc == null) {
                System.out.println("Tai khoan khong ton tai!");
                return;
            }

            System.out.print("Nhap so tien can nap: ");
            double amount = Double.parseDouble(sc.nextLine());
            if (amount <= 0) {
                System.out.println("So tien phai lon hon 0!");
                return;
            }

            acc.setBalance(acc.getBalance() + amount);
            DatabaseHelper.updateAccount(acc);
            DatabaseHelper.insertTransaction(new Transaction(acc, amount, "Nap tien", "Nap tien vao tai khoan"));
            System.out.println(">>> Nap tien thanh cong!");
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }

    public void doWithdraw() {
        try {
            System.out.print("Nhap ma tai khoan: ");
            int id = Integer.parseInt(sc.nextLine());
            Account acc = DatabaseHelper.getAccountByID(id);
            if (acc == null) {
                System.out.println("Tai khoan khong ton tai!");
                return;
            }

            System.out.print("Nhap so tien can rut: ");
            double amount = Double.parseDouble(sc.nextLine());
            if (amount <= 0 || amount > acc.getBalance()) {
                System.out.println("So tien khong hop le hoac vuot qua so du!");
                return;
            }

            acc.setBalance(acc.getBalance() - amount);
            DatabaseHelper.updateAccount(acc);
            DatabaseHelper.insertTransaction(new Transaction(acc, amount, "Rut tien", "Rut tien tu tai khoan"));
            System.out.println(">>> Rut tien thanh cong!");
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }

    public void doTransferMoney() {
        try {
            System.out.print("Ma tai khoan chuyen: ");
            int fromID = Integer.parseInt(sc.nextLine());
            Account from = DatabaseHelper.getAccountByID(fromID);

            System.out.print("Ma tai khoan nhan: ");
            int toID = Integer.parseInt(sc.nextLine());
            Account to = DatabaseHelper.getAccountByID(toID);

            if (from == null || to == null || fromID == toID) {
                System.out.println("Tai khoan khong hop le!");
                return;
            }

            System.out.print("So tien can chuyen: ");
            double amount = Double.parseDouble(sc.nextLine());
            System.out.print("Noi dung: ");
            String note = sc.nextLine();

            if (amount <= 0 || amount > from.getBalance()) {
                System.out.println("So tien khong hop le hoac khong du!");
                return;
            }

            from.setBalance(from.getBalance() - amount);
            to.setBalance(to.getBalance() + amount);

            DatabaseHelper.updateAccount(from);
            DatabaseHelper.updateAccount(to);

            DatabaseHelper.insertTransaction(new Transaction(from, amount, "Chuyen khoan", note));
            DatabaseHelper.insertTransaction(new Transaction(to, amount, "Nhan chuyen khoan", note));
            System.out.println(">>> Chuyen tien thanh cong!");
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }

    public void doCheckbalance() {
        try {
            System.out.print("Nhap ma tai khoan: ");
            int id = Integer.parseInt(sc.nextLine());
            Account acc = DatabaseHelper.getAccountByID(id);
            if (acc == null) {
                System.out.println("Khong tim thay tai khoan!");
                return;
            }
            System.out.printf("%-10s| %-10.2f\n", "So du", acc.getBalance());
            DatabaseHelper.insertTransaction(new Transaction(acc, 0, "KTSD", "Kiem tra so du"));
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }

    public void doViewTransactionDiary() {
        try {
            System.out.print("Nhap ma tai khoan: ");
            int id = Integer.parseInt(sc.nextLine());
            List<Transaction> list = DatabaseHelper.getTransactionsByAccountID(id);
            System.out.printf("%-5s| %-28s| %-8s| %-15s| %-15s| %-25s\n", "ID", "Thoi gian", "ID Tai khoan", "So tien", "Loai GD", "Mo ta");
            for (Transaction t : list) {
                System.out.print(t);
            }
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }

    @Override
    public void execute(int n) {
        switch (n) {
            case 1: doAddAccount(); break;
            case 2: doChangePin(); break;
            case 3: doDeleteAccount(); break;
            case 4: doViewAccountList(); break;
            case 5: doDepositToAccount(); break;
            case 6: doWithdraw(); break;
            case 7: doTransferMoney(); break;
            case 8: doCheckbalance(); break;
            case 9: doViewTransactionDiary(); break;
            case 10: System.out.println("Hen gap lai quy khach!"); System.exit(0);
        }
    }

    public int getAccountID() { return accountID; }
    public void setAccountID(int accountID) { this.accountID = accountID; }
    public String getPin() { return pin; }
    public void setPin(String pin) { this.pin = pin; }
    public Double getBalance() { return balance; }
    public void setBalance(Double balance) { this.balance = balance; }
}
