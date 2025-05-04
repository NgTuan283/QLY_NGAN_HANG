import java.util.Scanner;

public class BankingSystem {
    public static void main(String[] args) {
        LichSuGiaoDich giaoDichService = new LichSuGiaoDich();
        QuanLyKhoanVay quanLyKhoanVay = new QuanLyKhoanVay(giaoDichService);
        
        // Khởi tạo dữ liệu mẫu
        giaoDichService.khoiTaoDuLieuMau();
        quanLyKhoanVay.khoiTaoDuLieuMau();
        
        // Khởi động hệ thống
        khoiDongHeThong(giaoDichService, quanLyKhoanVay);
    }

    public static void khoiDongHeThong(LichSuGiaoDich giaoDichService, QuanLyKhoanVay quanLyKhoanVay) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n=== HE THONG NGAN HANG ===");
            System.out.println("1. Xem lich su giao dich");
            System.out.println("2. Quan ly khoan vay");
            System.out.println("0. Thoat");
            System.out.print("Lua chon: ");

            String luaChon = scanner.nextLine();

            switch (luaChon) {
                case "1":
                    giaoDichService.xemLichSuGiaoDich();
                    break;
                case "2":
                    quanLyKhoanVay.quanLyKhoanVay();
                    break;
                case "0":
                    System.out.println("Cam on ban da su dung dich vu");
                    return;
                default:
                    System.out.println("Lua chon khong hop le");
            }
        }
    }
}