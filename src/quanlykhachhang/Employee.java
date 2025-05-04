package quanlykhachhang;
public class Employee extends User {
    public Employee(String username, String password, String fullName) {
        super(username, password, fullName);
    }

    @Override
    public String toString() {
        return "Employee: " + fullName + " (" + userName + ")";
    }
}