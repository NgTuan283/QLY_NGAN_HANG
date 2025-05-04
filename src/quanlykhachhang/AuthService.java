public class AuthService {
    public static boolean login(User user, String password) {
        return user.login(password);
    }

    public static void logout(User user) {
        System.out.println(user.getFullName() + " logged out.");
    }
}
