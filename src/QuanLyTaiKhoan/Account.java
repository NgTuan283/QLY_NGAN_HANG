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
    String menu[] = {"Menu X", "Them moi tai khoan","Doi PIN","Xoa tai khoan","Xem danh sach tai khoan","Nap tien","Rut tien", "Chuyen tien", "Xem so du", "Xem nhat ky giao dich", "Thoat"};

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
    
    // 1. Them moi tai khoan
    public void doAddAccount() {
        System.out.println("\n\t\t------- Them tai khoan -------");
        try {
            System.out.print("Nhap PIN (6 chu so): ");
            int pin = Integer.parseInt(sc.nextLine());
            System.out.print("Nhap so du ban dau: ");
            double balance = Double.parseDouble(sc.nextLine());

            if (String.valueOf(pin).length() != 6) {
                System.out.println("PIN phai co 6 chu so!");
                return;
            }
    
            Account newAccount = new Account(balance, pin);
            accountList.add(newAccount);
            System.out.println(">>> Tao tai khoan thanh cong! Ma tai khoan: " + newAccount.getAccountID());
    
        } catch (NumberFormatException e) {
            System.out.println("Loi: Nhap sai kieu du lieu!");
        }
    }    

    //2. Sua thong tin tai khoan, chi cho phep doi ma pin
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

    //3. Xoa tai khoan khoi danh sach 
    public  void doDeleteAccount() {
        System.out.print("\nNhap ma tai khoan can xoa: ");
        int id = Integer.parseInt(sc.nextLine());
        Account a = getAccountByID(id);
        if (a == null) {
            System.out.println("Khong tim thay tai khoan!");
            return;
        }
        accountList.remove(a);
        System.out.println(">>> Xoa tai khoan thanh cong!");
    }
    
    //4. Xem danh sach tai khoan
    public void doViewAccountList() {
        System.out.println("\n\t\t------- Danh sach tai khoan -------");
        System.out.printf("%-10s| %-10s| %-10s\n", "ID", "PIN", "So du");
        for (Account a : accountList) {
            System.out.printf("%-10d| %-10d| %-10.2f\n", a.getAccountID(), a.getPin(), a.getBalance());
        }
    }
    
    //5. Nap tien
    public void doDepositToAccount() {
        try {
            System.out.print("\nNhap ma tai khoan can nap tien: ");
            int id = Integer.parseInt(sc.nextLine());
            Account acc = getAccountByID(id);
    
            if (acc == null) {
                System.out.println("Khong tim thay tai khoan!");
                return;
            }
    
            System.out.print("Nhap so tien can nap: ");
            double amount = Double.parseDouble(sc.nextLine());
    
            if (amount <= 0) {
                System.out.println("So tien phai lon hon 0!");
                return;
            }
    
            acc.balance += amount;
            Account.transactionDiary.add(new Transaction(acc, amount, "Nap tien", "Nap tien vao tai khoan"));
            System.out.println(">>> Nap tien thanh cong! So du moi: " + acc.balance);
    
        } catch (NumberFormatException e) {
            System.out.println("Loi: Nhap sai kieu du lieu!");
        }
    }    

    //6.Rut tien
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

    //7. Chuyen tien
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

    //8. Kiem tra so du
    public void doCheckbalance() {
        System.out.println("\n\n\t\t------- Kiem tra so du -------");
        System.out.print("Nhap ID tai khoan: ");
        int id;
        try {
            id = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Loi: ID tai khoan khong hop le !");
            return;
        }

        Account acc = getAccountByID(id);
        if (acc == null) {
            System.out.println("Khong tim thay tai khoan voi ID: " + id);
            return;
        }

        System.out.printf("%-10s| %-10s\n", "Tai khoan", "So du");
        System.out.printf("%-10d| %-10.2f\n", acc.getAccountID(), acc.getBalance());
        
        transactionDiary.add(new Transaction(acc, 0, "KTSD", "Kiem tra so du"));
    }

    //9. Xem lich su giao dich
    public void doViewTransactionDiary() {
        System.out.println("\n------- Xem nhat ky giao dich -------");
        System.out.print("Nhap ID tai khoan: ");
        int id;
        try {
            id = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Loi: ID tai khoan khong hop le!");
            return;
        }

        Account acc = getAccountByID(id);
        if (acc == null) {
            System.out.println("Khong tim thay tai khoan voi ID: " + id);
            return;
        }

        System.out.println("\n------- Nhat ky GD cua tai khoan \"" + acc.getAccountID() + "\" -------");
        System.out.printf("\n%-5s| %-28s| %-8s| %-15s| %-15s| %-25s", "ID", "Thoi gian", "So TK", "So tien", "Loai GD", "Mo ta");

        boolean hasTransaction = false;
        for (Transaction t : transactionDiary) {
            if (t.getAcc().getAccountID() == acc.getAccountID()) {
                System.out.print(t.toString());
                hasTransaction = true;
            }
        }

        if (!hasTransaction) {
            System.out.println("\nKhong co giao dich nao cho tai khoan nay.");
        }
    }


    @Override
    public void execute(int n) {
        switch (n) {
            case 1:
                doAddAccount();;
                break;
            case 2:
                doChangePin();;
                break;
            case 3:
                doDeleteAccount();;
                break;
            case 4:
                doViewAccountList();
                break;
            case 5:
                doDepositToAccount();
                break;
            case 6:
                doWithdraw();
                break;
            case 7:
                doTransferMoney();
                break;
            case 8: 
                doCheckbalance();
                break;
            case 9:
                doViewTransactionDiary();
                break;
            case 10:
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

    public Vector<Transaction> getTransactionDiary() {
        return transactionDiary;
    }

    public void setTransactionDiary(Vector<Transaction> transactionDiary) {
        Account.transactionDiary = transactionDiary;
    }

    @Override
    public String toString() {
        return "Account{" + "accountID=" + accountID + ", pin=" + pin + ", balance=" + balance + '}';
    }
}
