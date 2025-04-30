package QuanLyTaiKhoan;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Tạ1100o dữ liệu mẫu
        Account acc1 = new Account(1000000, 123456); // accountID = 10000
        Account acc2 = new Account(500000, 654321);  // accountID = 10001
        Account.accountList.add(acc1);
        Account.accountList.add(acc2);

        Scanner sc = new Scanner(System.in);
        System.out.println("===== DANG NHAP HE THONG =====");

        Account currentAcc = null;

        // Đăng nhập
        while (currentAcc == null) {
            System.out.print("Nhap so tai khoan: ");
            int stk = Integer.parseInt(sc.nextLine());
            System.out.print("Nhap ma PIN: ");
            int pin = Integer.parseInt(sc.nextLine());

            for (Account acc : Account.accountList) {
                currentAcc = acc.accountLogIn(stk, pin);
                if (currentAcc != null) break;
            }

            if (currentAcc == null) {
                System.out.println(">>> Sai thong tin dang nhap, vui long thu lai!\n");
            }
        }

        System.out.println("\n>>> Dang nhap thanh cong!");
        int choice = -1;

        // Hiển thị menu và xử lý lựa chọn
        while (choice != 6) {
            System.out.println("\n======= MENU GIAO DICH =======");
            String[] menu = {"Rut tien", "Chuyen tien", "Doi PIN", "Xem so du", "Xem nhat ky giao dich", "Thoat"};
            for (int i = 0; i < menu.length; i++) {
                System.out.printf("%d. %s\n", i + 1, menu[i]);
            }

            System.out.print("Chon chuc nang: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
                currentAcc.execute(choice);
            } catch (Exception e) {
                System.out.println(">>> Lua chon khong hop le!");
            }
        }

        sc.close();
    }
}
