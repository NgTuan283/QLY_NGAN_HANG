
public class BankingSystem1 {
    public static void main(String[] args) {
        KhoanVayService bankService = new KhoanVayService();
        bankService.khoiTaoDuLieuMau();
        khoiDongHeThong(bankService);
    }

    public static void khoiDongHeThong(KhoanVayService bankService) {
        while (true) {
            System.out.println("\n=== HE THONG NGAN HANG ===");
            System.out.println("1. Xem lich su giao dich");
            System.out.println("2. Quan ly khoan vay");
            System.out.println("0. Thoat");
            System.out.print("Lua chon: ");

            String luaChon = bankService.getScanner().nextLine();

            switch (luaChon) {
                case "1":
                    bankService.xemLichSuGiaoDich();
                    break;
                case "2":
                    bankService.quanLyKhoanVay();
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
