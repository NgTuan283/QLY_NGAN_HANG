package QuanLyTaiKhoan;

import java.util.Date;

public class Transaction {


    private int transactionID;
    private Account acc;
    private String loanID;  // Bổ sung loanID
    private double amount;
    private Date transactionTime;
    private String transactionType, status;
    private static int count = 1;


    public Transaction() {


    }


    public Transaction(Account a, double amount, String transactionType, String status) {
        this.transactionID = count++;
        this.amount = amount;
        this.transactionTime = new Date();
        this.transactionType = transactionType;
        this.status = status;
    }
    public Transaction(Account a, double amount, String transactionType, String status, String loanID) {
        this.transactionID = count++;
        this.acc = a;
        this.amount = amount;
        this.transactionTime = new Date();
        this.transactionType = transactionType;
        this.status = status;
        this.loanID = loanID;
    }

    public Transaction(int accountID, String loanID, double amount, String transactionType, String status) {
        this.transactionID = count++;
        this.acc = new Account();  // Giả sử bạn muốn lấy Account từ ID
        this.acc.setAccountID(accountID);
        this.loanID = loanID;
        this.amount = amount;
        this.transactionTime = new Date();
        this.transactionType = transactionType;
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("\n%-5d| %-28s| %-8d| %-15f| %-15s| %-25s",transactionID, transactionTime.toString(), acc.getAccountID(), amount, transactionType, status);
    }


    public int gettransactionID() {
        return transactionID;
    }


    public void settransactionID(int transactionID) {
        this.transactionID = transactionID;
    }


    public Account getAcc() {
        return acc;
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


    public String getstatus() {
        return status;
    }


    public void setstatus(String status) {
        this.status = status;
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