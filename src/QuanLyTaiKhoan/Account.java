package QuanLyTaiKhoan;

import java.util.Scanner;
import java.util.Vector;

public class Account extends Menu {

    private int accountID, pin;
    private Double balance;
    private static int countAccountID = 10000;
    // tao ra mot danh sach luu cac tai khoan giao dich trong lop
    static Vector<Account> accountList = new Vector<Account>(10, 5);
    static Vector<Transaction> transactionDiary = new Vector<Transaction>(10, 5);
    static Scanner sc = new Scanner(System.in); // Scanner dùng chung
    String menu[] = {"Menu X", "Rut tien", "Chuyen tien", "Doi PIN", "Xem so du", "Xem nhat ky giao dich", "Thoat"};

    public Account() {
        super();
    }

    public Account(double sd, int pin) {
        this.pin = pin;
        this.balance = sd;
        this.accountID = countAccountID++;
    }

    public Account accountLogIn(int stk, int pin) {
        if (this.accountID == stk && this.pin == pin) {
            return this;
        }
        return null;
    }

    public static Account getAccountByID(int accountID) {
        for (Account account : accountList) {
            if (account.getAccountID() == accountID) {
                return account;
            }
        }
        return null;
    }

    public void doWithdraw() {
        System.out.println("\n\t\t------- Rut tien -------");
        System.out.println("So tien can rut: ");
        double st = 0D;
        try {
            st = Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Loi nhap sai kieu so !");
            return;
        }
        String mt = "Rut tien";
        try {
            withdraw(st, mt);
            System.out.println(">>> Rut tien thanh cong !");
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }

    public double withdraw(double sotien, String mota) {
        if (sotien > this.balance) {
            throw new RuntimeException("So du khong du de rut tien");
        }
        transactionDiary.add(new Transaction(this, sotien, "Rut tien", mota));
        return this.balance -= sotien;
    }

    public void doTransferMoney() {
        int stk = 0;
        double st = 0D;
        System.out.println("\n\t\t------- Chuyen tien -------");
        try {
            System.out.println("So tai khoan nhan tien: ");
            stk = Integer.parseInt(sc.nextLine());
            System.out.println("So tien can chuyen: ");
            st = Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Loi: nhap sai kieu so !");
            return;
        }
        System.out.println("Noi dung chuyen tien: ");
        String mt = sc.nextLine();
        Account a = getAccountByID(stk);
        try {
            transferMoney(a, st, this.getAccountID() + " chuyen tien cho " + a.getAccountID() + ", noi dung: " + mt);
            System.out.println(">>> Chuyen khoan thanh cong !");
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }

    public void transferMoney(Account a, double sotien, String mota) {
        if (a == null) {
            throw new RuntimeException("Khong tim thay tai khoan nhan tien !");
        }
        if (a == this) {
            throw new RuntimeException("Tai khoan nhan tien phai khac tai khoan chuyen tien !");
        }
        if (sotien > balance) {
            throw new RuntimeException("So du khong du de chuyen tien !");
        }

        transactionDiary.add(new Transaction(this, sotien, "Chuyen Khoan", mota));
        transactionDiary.add(new Transaction(a, sotien, "Nhan Chuyen Khoan", mota));
        this.balance -= sotien;
        a.balance += sotien;
    }

    public void doChangePin() {
        int pin = 0;
        int pin2 = 0;
        System.out.println("\n\t\t------- Doi Pin -------");
        try {
            System.out.println("Nhap pin moi: ");
            pin = Integer.parseInt(sc.nextLine());
            System.out.println("Nhap lai pin moi: ");
            pin2 = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Loi: Nhap sai kieu so !");
            return;
        }
        String mota = "Doi pin";
        if (pin < 100000) {
            System.out.println("Pin phai co 6 chu so !");
            return;
        }
        if (pin != pin2) {
            System.out.println("Pin nhap lai khong chinh xac !");
            return;
        }
        try {
            changePin(pin, mota);
            System.out.println(">>> Doi pin thanh cong !");
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
            return;
        }
    }

    public void changePin(int newpin, String mota) {
        if (newpin == this.pin) {
            throw new RuntimeException("Pin moi phai khac pin cu !");
        }
        transactionDiary.add(new Transaction(this, 0, "Doi Pin", mota));
        this.pin = newpin;
    }

    public void checkbalance() {
        System.out.println("\n\n\t\t------- Kiem tra so du -------");
        System.out.printf("%-10s| %-10s", "Tai khoan", "So du");
        System.out.printf("\n%-10d| %-10f", this.getAccountID(), this.getBalance());
        transactionDiary.add(new Transaction(this, 0, "KTSD", "Kiem tra so du tai ATM"));
    }

    public void viewTransactionDiary() {
        System.out.println("\n------- Nhat ky GD cua tai khoan \"" + this.accountID + "\" -------");
        System.out.printf("\n%-5s| %-28s| %-8s| %-15s| %-15s| %-25s", "ID", "Thoi gian", "So TK", "So tien", "Loai GD", "Mo ta");
        for (Transaction t : transactionDiary) {
            if (t.getAcc().getAccountID() == this.getAccountID()) {
                System.out.print(t.toString());
            }
        }
    }

    @Override
    public void execute(int n) {
        switch (n) {
            case 1:
                doWithdraw();
                break;
            case 2:
                doTransferMoney();
                break;
            case 3:
                doChangePin();
                break;
            case 4:
                checkbalance();
                break;
            case 5:
                viewTransactionDiary();
                break;
            case 6:
                System.out.println("\t\t Hen gap lai quy khach !");
                System.exit(0);
        }
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public static int getCountAccountID() {
        return countAccountID;
    }

    public static void setCountAccountID(int countAccountID) {
        Account.countAccountID = countAccountID;
    }

    public static Vector<Transaction> getTransactionDiary() {
        return transactionDiary;
    }

    public static void setTransactionDiary(Vector<Transaction> transactionDiary) {
        Account.transactionDiary = transactionDiary;
    }

    @Override
    public String toString() {
        return "Account{" + "accountID=" + accountID + ", pin=" + pin + ", balance=" + balance + '}';
    }
}
