import java.util.*;
import java.text.SimpleDateFormat;

public class LichSuGiaoDich {
    // Lớp GiaoDich (inner class)
    public static class GiaoDich {
        private String maGiaoDich;
        private String taiKhoanNguon;
        private String taiKhoanDich;
        private String maKhoanVay;
        private double soTien;
        private Date thoiGian;
        private String moTa;

        public GiaoDich(String maGiaoDich, String taiKhoanNguon, String taiKhoanDich, 
                       String maKhoanVay, double soTien, Date thoiGian, String moTa) {
            this.maGiaoDich = maGiaoDich;
            this.taiKhoanNguon = taiKhoanNguon;
            this.taiKhoanDich = taiKhoanDich;
            this.maKhoanVay = maKhoanVay;
            this.soTien = soTien;
            this.thoiGian = thoiGian;
            this.moTa = moTa;
        }

        public String getMaGiaoDich() { return maGiaoDich; }
        public String getTaiKhoanNguon() { return taiKhoanNguon; }
        public String getTaiKhoanDich() { return taiKhoanDich; }
        public String getMaKhoanVay() { return maKhoanVay; }
        public double getSoTien() { return soTien; }
        public Date getThoiGian() { return thoiGian; }
        public String getMoTa() { return moTa; }
    }

    private List<GiaoDich> danhSachGiaoDich;
    private Scanner scanner;

    public LichSuGiaoDich() {
        this.danhSachGiaoDich = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void khoiTaoDuLieuMau() {
        danhSachGiaoDich.add(new GiaoDich("GD001", "ACC001", "ACC002", null, 1000000, new Date(), "Chuyen tien"));
        danhSachGiaoDich.add(new GiaoDich("GD002", "ACC002", "ACC001", null, 2000000, new Date(), "Nhan tien"));
    }

    public void xemLichSuGiaoDich() {
        System.out.println("\n=== LICH SU GIAO DICH ===");
        System.out.print("Nhap so tai khoan (VD: TK001): ");
        String soTaiKhoan = scanner.nextLine();

        List<GiaoDich> giaoDichTK = new ArrayList<>();
        for (GiaoDich gd : danhSachGiaoDich) {
            if (gd.getTaiKhoanNguon().equals(soTaiKhoan) || gd.getTaiKhoanDich().equals(soTaiKhoan)) {
                giaoDichTK.add(gd);
            }
        }

        if (giaoDichTK.isEmpty()) {
            System.out.println("Khong tim thay giao dich nao");
        } else {
            SimpleDateFormat dinhDangNgay = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            System.out.println("\nLich su giao dich cua " + soTaiKhoan + ":");
            
            for (GiaoDich gd : giaoDichTK) {
                String loai = gd.getTaiKhoanNguon().equals(soTaiKhoan) ? "CHUYEN DI" : "NHAN VE";
                System.out.printf("[%s] %s - So tien: %,.2f - %s -> %s - Ngay: %s - Mo ta: %s\n",
                        gd.getMaGiaoDich(), loai, gd.getSoTien(), 
                        gd.getTaiKhoanNguon(), gd.getTaiKhoanDich(),
                        dinhDangNgay.format(gd.getThoiGian()), gd.getMoTa());
            }
        }
    }

    public void themGiaoDich(GiaoDich giaoDich) {
        danhSachGiaoDich.add(giaoDich);
    }

    public List<GiaoDich> getDanhSachGiaoDich() {
        return danhSachGiaoDich;
    }
}