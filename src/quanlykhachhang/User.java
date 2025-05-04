package quanlykhachhang;
public abstract class User {
    protected String userName;
    protected String password;
    protected String fullName;

    public User(String userName, String password, String fullName) {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword(){
        return password;
    }

    public boolean login(String inputPass) {
        return this.password.equals(inputPass);
    }

    public String getFullName() {
        return fullName;
    }
}