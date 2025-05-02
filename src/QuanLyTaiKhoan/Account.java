package QuanLyTaiKhoan;

import java.util.Scanner;
import java.util.Vector;

public class Account extends Menu {

    private int accountID;
    private String pin;
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

    public Account(double sd, String pin) {
        this.pin = pin;
        this.balance = sd;
        this.accountID = countAccountID++;
    }

    public Account accountLogIn(int stk, String pin) {
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
            String pin = sc.nextLine();
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
        System.out.print("\nNhap ma tai khoan can doi Pin: ");
        int id = Integer.parseInt(sc.nextLine());
        Account acc = getAccountByID(id);

        if (acc == null) {
            System.out.println("Khong tim thay tai khoan!");
            return;
        }

        String pin;
        String pin2;
        System.out.println("\n\t\t------- Doi PIN -------");
    
        System.out.print("Nhap PIN moi (6 chu so): ");
        pin = sc.nextLine();
    
        System.out.print("Nhap lai PIN moi: ");
        pin2 = sc.nextLine();
    
        // Kiem tra do dai va dinh dang
        if (!isValidPin(pin)) {
            System.out.println("Loi: PIN phai gom dung 6 so!");
            return;
        }
    
        // So sanh noi dung chuoi
        if (!pin.equals(pin2)) {
            System.out.println("Loi: Hai PIN nhap khong khop!");
            return;
        }
    
        String mota = "Doi PIN";
    
        try {
            changePin(pin, mota);
            System.out.println(">>> Doi PIN thanh cong!");
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }
    
    public boolean isValidPin(String pin) {
        if (pin == null || pin.length() != 6) {
            return false;
        }
        for (char c : pin.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
    
    public void changePin(String newpin, String mota) {
        if (newpin.equals(this.pin)) {
            throw new RuntimeException("PIN moi phai khac PIN hien tai!");
        }
    
        // Ghi lai lich su doi PIN
        transactionDiary.add(new Transaction(this, 0, "Doi PIN", mota));
    
        // Cap nhat PIN
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
            System.out.printf("%-10d| %-10s| %-10.2f\n", a.getAccountID(), a.getPin(), a.getBalance());
        }
    }
    
    //5. Nap tien
    public void doDepositToAccount() {
        try {
            System.out.print("\nNhap ma tai khoan : ");
            int id = Integer.parseInt(sc.nextLine());
            Account acc = getAccountByID(id);
    
            while (acc == null) {
                if(acc == null) System.out.println("Khong tim thay tai khoan !");
                System.out.println("1. Nhap lai ma tai khoan");
                System.out.println("2. Thoat");
                System.out.print("Chon: ");
                String option = sc.nextLine();

                if (option.equals("2")) {
                    System.out.println(">>> Huy giao dich nap tien.");
                    return;
                } else if (option.equals("1")) {
                    System.out.print("Nhap ma tai khoan: ");
                    id = Integer.parseInt(sc.nextLine());
                } else {
                    System.out.println("Lua chon khong hop le. Thoat ve menu chinh.");
                    return;
                }
            }
    
            System.out.print("Nhap so tien can nap: ");
            double amount = Double.parseDouble(sc.nextLine());
    
            while (amount <= 0) {
                if (amount <= 0) {
                    System.out.println("So tien phai lon hon 0!");
                }
    
                System.out.println("1. Nhap lai so tien");
                System.out.println("2. Thoat ve menu chinh");
                System.out.print("Chon: ");
                String option = sc.nextLine();
    
                if (option.equals("2")) {
                    System.out.println(">>> Huy giao dich nap tien.");
                    return;
                } else if (option.equals("1")) {
                    System.out.print("Nhap so tien can nap: ");
                    amount = Double.parseDouble(sc.nextLine());
                } else {
                    System.out.println("Lua chon khong hop le. Thoat ve menu chinh.");
                    return;
                }
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
        try {
            System.out.print("\nNhap ma tai khoan : ");
            int id = Integer.parseInt(sc.nextLine());
            Account acc = getAccountByID(id);
    
            while (acc == null) {
                if(acc == null) System.out.println("Khong tim thay tai khoan !");
                System.out.println("1. Nhap lai ma tai khoan");
                System.out.println("2. Thoat");
                System.out.print("Chon: ");
                String option = sc.nextLine();

                if (option.equals("2")) {
                    System.out.println(">>> Huy giao dich rut tien.");
                    return;
                } else if (option.equals("1")) {
                    System.out.print("Nhap ma tai khoan: ");
                    id = Integer.parseInt(sc.nextLine());
                } else {
                    System.out.println("Lua chon khong hop le. Thoat ve menu chinh.");
                    return;
                }
            }
    
            System.out.print("Nhap so tien can rut: ");
            double amount = Double.parseDouble(sc.nextLine());
    
            while (amount <= 0 || amount > acc.getBalance()) {
                if (amount <= 0) {
                    System.out.println("So tien phai lon hon 0!");
                } else {
                    System.out.println("So du tai khoan khong du!");
                }
    
                System.out.println("1. Nhap lai so tien");
                System.out.println("2. Thoat ve menu chinh");
                System.out.print("Chon: ");
                String option = sc.nextLine();
    
                if (option.equals("2")) {
                    System.out.println(">>> Huy giao dich rut tien.");
                    return;
                } else if (option.equals("1")) {
                    System.out.print("Nhap so tien can rut: ");
                    amount = Double.parseDouble(sc.nextLine());
                } else {
                    System.out.println("Lua chon khong hop le. Thoat ve menu chinh.");
                    return;
                }
            }
    
            acc.balance -= amount;
            Account.transactionDiary.add(new Transaction(acc, amount, "Rut tien", "Rut tien tu tai khoan"));
            System.out.println(">>> Rut tien thanh cong! So du moi: " + acc.balance);

        } catch (NumberFormatException e) {
            System.out.println("Loi: Nhap sai kieu du lieu!");
        }
        
    }

    //7. Chuyen tien
    public void doTransferMoney() {
        int stkIn = 0, stkOut = 0;
        double st = 0D;
        System.out.println("\n\t\t------- Chuyen tien -------");
        try {
            System.out.println("So tai khoan nhan tien: ");
            stkIn = Integer.parseInt(sc.nextLine());
            Account in = getAccountByID(stkIn);
            if (in == null) {
                System.out.println ("Khong tim thay tai khoan !");
                while (in == null) {
                    System.out.print("Nhap lai so tai khoan nhan tien: ");
                    stkIn = Integer.parseInt(sc.nextLine());
                    in = getAccountByID(stkIn);
                    if(in == null) System.out.println("Khong tim thay tai khoan !");
                    if(in != null) break;
                }
            }
            System.out.println("So tai khoan chuyen tien");
            stkOut = Integer.parseInt(sc.nextLine());
            Account out = getAccountByID(stkOut);
            if (out == null) {
                System.out.println ("Khong tim thay tai khoan nay!");
                while (out == null) {
                    System.out.print("Nhap lai so tai khoan chuyen tien: ");
                    stkOut = Integer.parseInt(sc.nextLine());
                    out = getAccountByID(stkOut);
                    if(out == null) System.out.println("Khong tim thay so tai khoan nay !");
                    if(out != null) break;
                }
            }
            System.out.println("So tien can chuyen: ");
            st = Double.parseDouble(sc.nextLine());
            System.out.println("Noi dung chuyen tien: ");
            String mt = sc.nextLine();
            transferMoney(in, out, st, out.getAccountID() + " chuyen tien cho " + in.getAccountID() + ", noi dung: " + mt);
            System.out.println(">>> Chuyen khoan thanh cong !");
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }

    public void transferMoney(Account in, Account out, double sotien, String mota) {
        if (in == out) {
            throw new RuntimeException("Tai khoan nhan va chuyen tien phai khac nhau!");
        }
        if (sotien <= 0) {
            throw new RuntimeException("So tien phai lon hon 0!");
        }
        if (out.getBalance() < sotien) {
            throw new RuntimeException("So du tai khoan chuyen khong du!");
        }
    
        // Tao giao dich va cap nhat so du
        transactionDiary.add(new Transaction(out, sotien, "Chuyen khoan", mota));
        transactionDiary.add(new Transaction(in, sotien, "Nhan chuyen khoan", mota));
        out.setBalance(out.getBalance() - sotien);
        in.setBalance(in.getBalance() + sotien);
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
        System.out.printf("\n%-5s| %-28s| %-8s| %-15s| %-15s| %-25s", "ID", "Thoi gian", "ID Tai khoan", "So tien", "Loai GD", "Mo ta");

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

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
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
