package quanlykhachhang;
import java.time.LocalDateTime;

public class Customer extends User {
    private static int IDCounter = 1;
    private int customerID;
    private String phoneNumber;
    private String email;
    private String address;
    private LocalDateTime createdTime;

    public Customer(String userName, String password, String fullName,
            String phoneNumber, String email, String address) {
        super(userName, password, fullName);
        this.customerID = IDCounter++;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.createdTime = LocalDateTime.now();
    }

    public Customer(int ID, String username, String password, String fullName,
            String phoneNumber, String email, String address, LocalDateTime createdTime) {
        super(username, password, fullName);
        this.ID = ID;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.createdTime = createdTime;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void displayInfo() {
        System.out.println("Ma khach hang: " + customerID);
        System.out.println("Full Name: " + fullName);
        System.out.println("Username: " + userName);
        System.out.println("Phone: " + phoneNumber);
        System.out.println("Email: " + email);
        System.out.println("Address: " + address);
        System.out.println("Created Time: " + createdTime);
        System.out.println("---------------------------");
    }

    // Getter & Setter
    public String getUsername() {
        return userName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }
}