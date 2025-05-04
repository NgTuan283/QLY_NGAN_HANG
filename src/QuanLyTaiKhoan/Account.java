// === Account.java ===
package QuanLyTaiKhoan;

import java.util.List;
import java.util.Scanner;

public class Account extends Menu {
    private int accountID;
    private String pin;
    private Double balance;
    private String userName; // Thêm biến userName

    static Scanner sc = new Scanner(System.in);

    public Account() {
        super();
    }

    public Account(int accountID, String pin, double balance, String userName) {
        this.accountID = accountID;
        this.pin = pin;
        this.balance = balance;
        this.userName = userName;
    }

    public Account(String pin, double balance, String userName) {
        this.pin = pin;
        this.balance = balance;
        this.userName = userName;
    }

    //1. Them tai khoan
    public void doAddAccount() {
        while (true) {
            try {
                System.out.print("Nhap ten tai khoan (UserName): ");
                String userName = sc.nextLine(); // Nhập tên người dùng
                System.out.print("Nhap PIN (6 chu so): ");
                String pin = sc.nextLine();
                System.out.print("Nhap so du ban dau: ");
                double balance = Double.parseDouble(sc.nextLine());
                if (!pin.matches("\\d{6}")) {
                    System.out.println("PIN phai co 6 chu so!");
                    throw new IllegalArgumentException("PIN khong hop le");
                }
                Account acc = new Account(pin, balance, userName); // Gọi constructor mới với userName
                int generatedID = DatabaseHelper.insertAccount(acc);
                acc.setAccountID(generatedID);
                System.out.println(">>> Tao tai khoan thanh cong! Ma tai khoan: " + acc.getAccountID());
                break;
            } catch (Exception e) {
                System.out.println("Loi: " + e.getMessage());
                if (!retryPrompt()) return;
            }
        }
    }

    //2.Thay doi ma PIN tai khoan
    public void doChangePin() {
        while (true) {
            try {
                System.out.print("Nhap ma tai khoan can doi PIN: ");
                int id = Integer.parseInt(sc.nextLine());
                Account acc = DatabaseHelper.getAccountByID(id);
                if (acc == null) throw new IllegalArgumentException("Khong tim thay tai khoan!");
                System.out.print("Nhap PIN moi: ");
                String newPin = sc.nextLine();
                System.out.print("Nhap lai PIN moi: ");
                String confirmPin = sc.nextLine();
                if (!newPin.equals(confirmPin) || !newPin.matches("\\d{6}")) {
                    throw new IllegalArgumentException("PIN moi khong hop le hoac khong trung khop!");
                }
                acc.setPin(newPin);
                DatabaseHelper.updateAccount(acc);
                DatabaseHelper.insertTransaction(new Transaction(acc, 0, "Doi PIN", "Doi PIN thanh cong"));
                System.out.println(">>> Doi PIN thanh cong!");
                break;
            } catch (Exception e) {
                System.out.println("Loi: " + e.getMessage());
                if (!retryPrompt()) return;
            }
        }
    }

    //3. Xoa tai khoan khoi danh sach
    public void doDeleteAccount() {
        while (true) {
            try {
                System.out.print("Nhap ma tai khoan: ");
                int id = Integer.parseInt(sc.nextLine());
                Account acc = DatabaseHelper.getAccountByID(id);
                if (acc == null) throw new IllegalArgumentException("Khong tim thay tai khoan!");
                DatabaseHelper.deleteAccount(id);
                System.out.println(">>> Xoa tai khoan thanh cong!");
                break;
            } catch (Exception e) {
                System.out.println("Loi: " + e.getMessage());
                if (!retryPrompt()) return;
            }
        }
    }

    //4. Xem danh sach tai khoan
    public void doViewAccountList() {
        while (true) {
            try {
                List<Account> list = DatabaseHelper.getAllAccounts();
                System.out.printf("%-10s| %-10s| %-15s| %-10s\n", "ID", "PIN", "UserName", "So du");
                for (Account acc : list) {
                    System.out.printf("%-10d| %-10s| %-15s| %-10.2f\n", acc.getAccountID(), acc.getPin(), acc.getUserName(), acc.getBalance());
                }
                break;
            } catch (Exception e) {
                System.out.println("Loi: " + e.getMessage());
                if (!retryPrompt()) return;
            }
        }
    }

    //5. Nap tien
    public void doDepositToAccount() {
        while (true) {
            try {
                System.out.print("Nhap ma tai khoan: ");
                int id = Integer.parseInt(sc.nextLine());
                Account acc = DatabaseHelper.getAccountByID(id);
                if (acc == null) throw new IllegalArgumentException("Khong tim thay tai khoan!");
                System.out.print("Nhap so tien can nap: ");
                double amount = Double.parseDouble(sc.nextLine());
                if (amount <= 0) throw new IllegalArgumentException("So tien phai lon hon 0!");
                acc.setBalance(acc.getBalance() + amount);
                DatabaseHelper.updateAccount(acc);
                DatabaseHelper.insertTransaction(new Transaction(acc, amount, "Nap tien", "Nap tien vao tai khoan"));
                System.out.println(">>> Nap tien thanh cong!");
                break;
            } catch (Exception e) {
                System.out.println("Loi: " + e.getMessage());
                if (!retryPrompt()) return;
            }
        }
    }

    //6. Rut tien
    public void doWithdraw() {
        while (true) {
            try {
                System.out.print("Nhap ma tai khoan: ");
                int id = Integer.parseInt(sc.nextLine());
                Account acc = DatabaseHelper.getAccountByID(id);
                if (acc == null) throw new IllegalArgumentException("Khong tim thay tai khoan!");
                System.out.print("Nhap so tien can rut: ");
                double amount = Double.parseDouble(sc.nextLine());
                if (amount <= 0 || amount > acc.getBalance()) {
                    throw new IllegalArgumentException("So tien khong hop le hoac khong du!");
                }
                acc.setBalance(acc.getBalance() - amount);
                DatabaseHelper.updateAccount(acc);
                DatabaseHelper.insertTransaction(new Transaction(acc, amount, "Rut tien", "Rut tien tu tai khoan"));
                System.out.println(">>> Rut tien thanh cong!");
                break;
            } catch (Exception e) {
                System.out.println("Loi: " + e.getMessage());
                if (!retryPrompt()) return;
            }
        }
    }

    //7. Chuyen tien
    public void doTransferMoney() {
        while (true) {
            try {
                System.out.print("Ma tai khoan chuyen: ");
                int fromID = Integer.parseInt(sc.nextLine());
                Account from = DatabaseHelper.getAccountByID(fromID);
                System.out.print("Ma tai khoan nhan: ");
                int toID = Integer.parseInt(sc.nextLine());
                Account to = DatabaseHelper.getAccountByID(toID);
                if (from == null || to == null || fromID == toID) {
                    throw new IllegalArgumentException("Tai khoan khong hop le!");
                }
                System.out.print("So tien can chuyen: ");
                double amount = Double.parseDouble(sc.nextLine());
                System.out.print("Noi dung: ");
                String note = sc.nextLine();
                if (amount <= 0 || amount > from.getBalance()) {
                    throw new IllegalArgumentException("So tien khong hop le hoac khong du!");
                }
                from.setBalance(from.getBalance() - amount);
                to.setBalance(to.getBalance() + amount);
                DatabaseHelper.updateAccount(from);
                DatabaseHelper.updateAccount(to);
                DatabaseHelper.insertTransaction(new Transaction(from, amount, "Chuyen khoan", note));
                DatabaseHelper.insertTransaction(new Transaction(to, amount, "Nhan chuyen khoan", note));
                System.out.println(">>> Chuyen tien thanh cong!");
                break;
            } catch (Exception e) {
                System.out.println("Loi: " + e.getMessage());
                if (!retryPrompt()) return;
            }
        }
    }

    //8. Kiem tra so du
    public void doCheckbalance() {
        while (true) {
            try {
                System.out.print("Nhap ma tai khoan: ");
                int id = Integer.parseInt(sc.nextLine());
                Account acc = DatabaseHelper.getAccountByID(id);
                if (acc == null) throw new IllegalArgumentException("Khong tim thay tai khoan!");
                System.out.printf("%-10s| %-10.2f\n", "So du", acc.getBalance());
                DatabaseHelper.insertTransaction(new Transaction(acc, 0, "KTSD", "Kiem tra so du"));
                break;
            } catch (Exception e) {
                System.out.println("Loi: " + e.getMessage());
                if (!retryPrompt()) return;
            }
        }
    }

    //9. Xem lich su giao dich
    public void doViewTransactionDiary() {
        while (true) {
            try {
                System.out.print("Nhap ma tai khoan: ");
                int id = Integer.parseInt(sc.nextLine());
                List<Transaction> list = DatabaseHelper.getTransactionsByAccountID(id);

                if (list.isEmpty()) {
                    System.out.println("Tai khoan chua co giao dich nao.");
                    return;
                }

                System.out.printf("%-5s| %-28s| %-8s| %-15s| %-15s| %-25s\n", "ID", "Thoi gian", "ID Tai khoan", "So tien", "Loai GD", "Mo ta");
                for (Transaction t : list) {
                    System.out.print(t);
                }
                break;
            } catch (Exception e) {
                System.out.println("Loi: " + e.getMessage());
                if (!retryPrompt()) return;
            }
        }
    }

    //Phuong thuc kiem tra khi nhap sai du lieu
    private boolean retryPrompt() {
        while (true) {
            System.out.println("1. Nhap lai thong tin");
            System.out.println("2. Thoat ve menu chinh");
            System.out.print("Chon: ");
            String opt = sc.nextLine();
            if (opt.equals("1")) return true;
            if (opt.equals("2")) return false;
            System.out.println("Lua chon khong hop le, xin moi chon lai.");
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
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public Double getBalance() { return balance; }
    public void setBalance(Double balance) { this.balance = balance; }
}
