package QuanLyNhanVien;

import java.util.Scanner;

public abstract class Menu {
    protected static final Scanner sc = new Scanner(System.in);

    public abstract void displayMenu();

    public abstract void execute(int choice);

    public void run() {
        int choice = -1;
        do {
            displayMenu();
            choice = Integer.parseInt(sc.nextLine());
            execute(choice);
        } while (choice != 0);
    }
}
