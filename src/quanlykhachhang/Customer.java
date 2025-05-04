import java.time.LocalDateTime;

public class Customer extends User {
    private static int IDCounter = 1;
    private int ID;
    private String phoneNumber;
    private String email;
    private String address;
    private LocalDateTime createdTime;

    public Customer(String username, String password, String fullName,
            String phoneNumber, String email, String address) {
        super(username, password, fullName);
        this.ID = IDCounter++;
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
        System.out.println("ID: " + ID);
        System.out.println("Full Name: " + fullName);
        System.out.println("Username: " + username);
        System.out.println("Phone: " + phoneNumber);
        System.out.println("Email: " + email);
        System.out.println("Address: " + address);
        System.out.println("Created Time: " + createdTime);
        System.out.println("---------------------------");
    }

    // Getter & Setter
    public String getUsername() {
        return username;
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

    public int getID() {
        return ID;
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