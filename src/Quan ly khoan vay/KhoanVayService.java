import java.util.*;
import java.text.SimpleDateFormat;


class KhoanVay {
    private String maKhoanVay;
    private String soTaiKhoan;
    private double soTienVay;
    private double laiSuat;
    private int kyHan;
    private Date ngayTao;
    private double soTienConNo;

    public KhoanVay(String maKhoanVay, String soTaiKhoan, double soTienVay, 
                   double laiSuat, int kyHan) {
        this.maKhoanVay = maKhoanVay;
        this.soTaiKhoan = soTaiKhoan;
        this.soTienVay = soTienVay;
        this.laiSuat = laiSuat;
        this.kyHan = kyHan;
        this.ngayTao = new Date();
        this.soTienConNo = soTienVay;
    }

    public String getMaKhoanVay() { return maKhoanVay; }
    public String getSoTaiKhoan() { return soTaiKhoan; }
    public double getSoTienVay() { return soTienVay; }
    public void setSoTienVay(double soTienVay) { this.soTienVay = soTienVay; }
    public double getLaiSuat() { return laiSuat; }
    public void setLaiSuat(double laiSuat) { this.laiSuat = laiSuat; }
    public int getKyHan() { return kyHan; }
    public Date getNgayTao() { return ngayTao; }
    public double getSoTienConNo() { return soTienConNo; }
    public void setSoTienConNo(double soTienConNo) { this.soTienConNo = soTienConNo; }

    @Override
    public String toString() {
        return String.format("Khoan vay %s - Tai khoan: %s\nSo tien: %,.2f - Con no: %,.2f\nLai suat: %.1f%% - Ky han: %d thang",
                maKhoanVay, soTaiKhoan, soTienVay, soTienConNo, laiSuat * 100, kyHan);
    }
}


public class KhoanVayService {
    private List<GiaoDich> danhSachGiaoDich;
    private List<KhoanVay> danhSachKhoanVay;
    private Scanner scanner;

    public KhoanVayService() {
        this.danhSachGiaoDich = new ArrayList<>();
        this.danhSachKhoanVay = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void khoiTaoDuLieuMau() {
        danhSachGiaoDich.add(new GiaoDich("GD001", "ACC001", "ACC002", null, 1000000, new Date(), "Chuyen tien"));
        danhSachGiaoDich.add(new GiaoDich("GD002", "ACC002", "ACC001", null, 2000000, new Date(), "Nhan tien"));
        danhSachKhoanVay.add(new KhoanVay("LOAN001", "ACC001", 5000000, 0.1, 12));
    }

    public void xemLichSuGiaoDich() {
        System.out.println("\n=== LICH SU GIAO DICH ===");
        System.out.print("Nhap so tai khoan (VD: TK001): ");
        String soTaiKhoan = scanner.nextLine();

        List<GiaoDich> giaoDichTK = layLichSuGiaoDich(soTaiKhoan);

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

    private List<GiaoDich> layLichSuGiaoDich(String soTaiKhoan) {
        List<GiaoDich> ketQua = new ArrayList<>();
        for (GiaoDich gd : danhSachGiaoDich) {
            if (gd.getTaiKhoanNguon().equals(soTaiKhoan) || gd.getTaiKhoanDich().equals(soTaiKhoan)) {
                ketQua.add(gd);
            }
        }
        return ketQua;
    }

    public void quanLyKhoanVay() {
        while (true) {
            System.out.println("\n=== QUAN LY KHOAN VAY ===");
            System.out.println("1. Tao khoan vay moi");
            System.out.println("2. Cap nhat khoan vay");
            System.out.println("3. Xoa khoan vay");
            System.out.println("4. Xem danh sach khoan vay");
            System.out.println("5. Thanh toan khoan vay");
            System.out.println("0. Quay lai");
            System.out.print("Lua chon: ");

            String luaChon = scanner.nextLine();

            switch (luaChon) {
                case "1":
                    taoKhoanVayMoi();
                    break;
                case "2":
                    capNhatKhoanVay();
                    break;
                case "3":
                    xoaKhoanVay();
                    break;
                case "4":
                    xemDanhSachKhoanVay();
                    break;
                case "5":
                    thanhToanKhoanVay();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Lua chon khong hop le");
            }
        }
    }

    private void taoKhoanVayMoi() {
        System.out.println("\n=== TAO KHOAN VAY MOI ===");
        System.out.print("Nhap so tai khoan: ");
        String soTaiKhoan = scanner.nextLine();

        System.out.print("Nhap so tien vay (VND): ");
        double soTien = Double.parseDouble(scanner.nextLine());

        System.out.print("Nhap lai suat (%/Thang): ");
        double laiSuat = Double.parseDouble(scanner.nextLine()) / 100;

        System.out.print("Nhap ky han (thang): ");
        int kyHan = Integer.parseInt(scanner.nextLine());

        String maKhoanVay = "LOAN00" + (danhSachKhoanVay.size() + 1);
        KhoanVay khoanVayMoi = new KhoanVay(maKhoanVay, soTaiKhoan, soTien, laiSuat, kyHan);
        khoanVayMoi.setSoTienConNo(soTien);
        danhSachKhoanVay.add(khoanVayMoi);

        System.out.println("Tao khoan vay thanh cong");
        System.out.println(khoanVayMoi);
       
    }

    private void capNhatKhoanVay() {
        System.out.println("\n=== CAP NHAT KHOAN VAY ===");
        System.out.print("Nhap ma khoan vay: ");
        String maKhoanVay = scanner.nextLine();

        KhoanVay khoanVay = timKhoanVayTheoMa(maKhoanVay);
        if (khoanVay == null) {
            System.out.println("Khong tim thay khoan vay");
            return;
        }

        System.out.println("Thong tin hien tai:");
        System.out.println(khoanVay);

        System.out.print("Nhap so tien moi (VND) (bo qua de giu nguyen): ");
        String soTienMoi = scanner.nextLine();
        if (!soTienMoi.isEmpty()) {
            khoanVay.setSoTienVay(Double.parseDouble(soTienMoi));
            khoanVay.setSoTienConNo(Double.parseDouble(soTienMoi));
        }
    

        System.out.print("Nhap lai suat moi (%/Thang) (bo qua de giu nguyen): ");
        String laiSuatMoi = scanner.nextLine();
        if (!laiSuatMoi.isEmpty()) {
            khoanVay.setLaiSuat(Double.parseDouble(laiSuatMoi) / 100);
        }

        System.out.println("Cap nhat thanh cong");
        System.out.println("Thong tin moi:");
        System.out.println(khoanVay);
    }

    private void xoaKhoanVay() {
        System.out.println("\n=== XOA KHOAN VAY ===");
        System.out.print("Nhap ma khoan vay can xoa: ");
        String maKhoanVay = scanner.nextLine();

        boolean daXoa = danhSachKhoanVay.removeIf(kv -> kv.getMaKhoanVay().equals(maKhoanVay));
        if (daXoa) {
            System.out.println("Da xoa khoan vay " + maKhoanVay);
        } else {
            System.out.println("Khong tim thay khoan vay");
        }
    }

    private void xemDanhSachKhoanVay() {
        System.out.println("\n=== DANH SACH KHOAN VAY ===");
        if (danhSachKhoanVay.isEmpty()) {
            System.out.println("Khong co khoan vay nao");
        } else {
            SimpleDateFormat dinhDangNgay = new SimpleDateFormat("dd/MM/yyyy");
            for (KhoanVay kv : danhSachKhoanVay) {
                System.out.printf("%s - Tai khoan: %s - So tien: %,.2f - Con no: %,.2f - Lai suat: %.1f%% - Ky han: %d thang - Ngay tao: %s\n",
                        kv.getMaKhoanVay(), kv.getSoTaiKhoan(), kv.getSoTienVay(), 
                        kv.getSoTienConNo(), kv.getLaiSuat() * 100,
                        kv.getKyHan(), dinhDangNgay.format(kv.getNgayTao()));
            }
        }
    }

    private void thanhToanKhoanVay() {
        System.out.println("\n=== THANH TOAN KHOAN VAY ===");
        System.out.print("Nhap ma khoan vay: ");
        String maKhoanVay = scanner.nextLine();

        KhoanVay khoanVay = timKhoanVayTheoMa(maKhoanVay);
        if (khoanVay == null) {
            System.out.println("Khong tim thay khoan vay");
            return;
        }

        System.out.println("Thong tin khoan vay:");
        System.out.println(khoanVay);

        System.out.print("Nhap so tien thanh toan (VND): ");
        double soTien = Double.parseDouble(scanner.nextLine());

        if (soTien <= 0) {
            System.out.println("So tien phai lon hon 0");
            return;
        }

        if (soTien > khoanVay.getSoTienConNo()) {
            System.out.println("So tien vuot qua so du no");
            return;
        }

        khoanVay.setSoTienConNo(khoanVay.getSoTienConNo() - soTien);
        System.out.println("Thanh toan thanh cong " + String.format("%,.2f", soTien));
        System.out.println("So du no con lai: " + String.format("%,.2f", khoanVay.getSoTienConNo()));

        String maGiaoDich = "GD" + (danhSachGiaoDich.size() + 1);
        danhSachGiaoDich.add(new GiaoDich(maGiaoDich, khoanVay.getSoTaiKhoan(), "NGANHANG", 
                maKhoanVay, soTien, new Date(), "Thanh toan khoan vay " + maKhoanVay));
    }

    private KhoanVay timKhoanVayTheoMa(String maKhoanVay) {
        for (KhoanVay kv : danhSachKhoanVay) {
            if (kv.getMaKhoanVay().equals(maKhoanVay)) {
                return kv;
            }
        }
        return null;
    }
}
