
import java.util.*;

class GiaoDich {
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
    public double getSoTien() { return soTien; }
    public Date getThoiGian() { return thoiGian; }
    public String getMoTa() { return moTa; }
}
