public abstract class User {
    protected String username;
    protected String password;
    protected String fullName;

    public User(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public boolean login(String inputPass) {
        return this.password.equals(inputPass);
    }

    public String getFullName() {
        return fullName;
    }
}