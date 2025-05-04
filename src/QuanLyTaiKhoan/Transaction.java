package QuanLyTaiKhoan;

import java.util.Date;

public class Transaction {


    private int transactionID;
    private Account acc;
    private String loanID;  // Bổ sung loanID
    private double amount;
    private Date transactionTime;
    private String transactionType, description;
    private static int count = 1;


    public Transaction() {
    }


    public Transaction(Account a, double amount, String transactionType, String description) {
        this.acc = a;
        this.transactionID = count++;
        this.amount = amount;
        this.transactionTime = new Date();
        this.transactionType = transactionType;
        this.description = description;
    }
    public Transaction(Account a, double amount, String transactionType, String description, String loanID) {
        this.transactionID = count++;
        this.acc = a;
        this.amount = amount;
        this.transactionTime = new Date();
        this.transactionType = transactionType;
        this.description = description;
        this.loanID = loanID;
    }

    public Transaction(int accountID, String loanID, double amount, String transactionType, String description) {
        this.transactionID = count++;
        this.acc = new Account();  // Giả sử bạn muốn lấy Account từ ID
        this.acc.setAccountID(accountID);
        this.loanID = loanID;
        this.amount = amount;
        this.transactionTime = new Date();
        this.transactionType = transactionType;
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("\n%-5d| %-28s| %-8d| %-15f| %-15s| %-25s",transactionID, transactionTime.toString(), acc.getAccountID(), amount, transactionType, description);
    }


    public int gettransactionID() {
        return transactionID;
    }


    public void settransactionID(int transactionID) {
        this.transactionID = transactionID;
    }


    public Account getAcc() {
        return this.acc;
    }


    public void setAcc(Account acc) {
        this.acc = acc;
    }


    public double getamount() {
        return amount;
    }


    public void setamount(double amount) {
        this.amount = amount;
    }


    public Date gettransactionTime() {
        return transactionTime;
    }


    public void settransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }


    public String gettransactionType() {
        return transactionType;
    }


    public void settransactionType(String transactionType) {
        this.transactionType = transactionType;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public static int getCount() {
        return count;
    }


    public static void setCount(int count) {
        Transaction.count = count;
    }

    public String getLoanID() {
        return loanID;
    }

    public void setLoanID(String loanID) {
        this.loanID = loanID;
    }
}