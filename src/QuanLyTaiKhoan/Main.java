package QuanLyTaiKhoan;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Account temp = new Account();
        int choice = -1;

        // Hien thi menu va xu ly chon
        while (choice != 10) {
            System.out.println("\n======= MENU GIAO DICH TAI KHOAN =======");
            String menu[] = {"Them moi tai khoan","Doi ten tai khoan","Xoa tai khoan","Xem danh sach tai khoan","Nap tien","Rut tien", "Chuyen tien", "Xem so du", "Xem nhat ky giao dich", "Thoat"};
            for (int i = 0; i < menu.length; i++) {
                System.out.printf("%d. %s\n", i + 1, menu[i]);
            }

            System.out.print("Chon chuc nang: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
                temp.execute(choice);
            } catch (Exception e) {
                System.out.println(">>> Lua chon khong hop le!");
            }
        }
        sc.close();
    }
}