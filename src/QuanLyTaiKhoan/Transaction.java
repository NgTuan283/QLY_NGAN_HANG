package QuanLyTaiKhoan;

import java.util.Date;

public class Transaction {


    private int transactionID;
    private Account acc;
    private double amount;
    private Date thoiGian;
    private String transactionType, status;
    private static int count = 1;


    public Transaction() {


    }


    public Transaction(Account a, double amount, String transactionType, String status) {
        this.transactionID = count++;
        this.acc = a;
        this.amount = amount;
        this.thoiGian = new Date();
        this.transactionType = transactionType;
        this.status = status;
    }


    @Override
    public String toString() {
        return String.format("\n%-5d| %-28s| %-8d| %-15f| %-15s| %-25s",transactionID, thoiGian.toString(), acc.getAccountID(), amount, transactionType, status);
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


    public Date getThoiGian() {
        return thoiGian;
    }


    public void setThoiGian(Date thoiGian) {
        this.thoiGian = thoiGian;
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


}


