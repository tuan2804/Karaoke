package entity;

import java.sql.Date;
import java.util.Objects;

public class NhanVien {
	private String maNhanVien;
	private String tenNhanVien;
	private String gioiTinh;
	private Date ngaySinh;
	private String cCCD;
	private String soDT;
	private String chucVu;
	private Double mucLuong;
	private String caLamViec;
	private String tinhTrang;
	private TaiKhoan taiKhoan;
	
	public NhanVien() {
		super();
	}

	public NhanVien(String maNhanVien) {
		super();
		this.maNhanVien = maNhanVien;
	}
	
	

	public NhanVien(String tenNhanVien, String chucVu) {
		super();
		this.tenNhanVien = tenNhanVien;
		this.chucVu = chucVu;
	}

	public NhanVien(String maNhanVien, String tenNhanVien, String gioiTinh, Date ngaySinh, String cCCD, String soDT,
			String chucVu, Double mucLuong, String caLamViec, String tinhTrang) {
		super();
		this.maNhanVien = maNhanVien;
		this.tenNhanVien = tenNhanVien;
		this.gioiTinh = gioiTinh;
		this.ngaySinh = ngaySinh;
		this.cCCD = cCCD;
		this.soDT = soDT;
		this.chucVu = chucVu;
		this.mucLuong = mucLuong;
		this.caLamViec = caLamViec;
		this.tinhTrang = tinhTrang;
	}

	public NhanVien(String maNhanVien, String tenNhanVien, String gioiTinh, Date ngaySinh, String cCCD, String soDT,
			String chucVu, Double mucLuong, String caLamViec, String tinhTrang, TaiKhoan taiKhoan) {
		super();
		this.maNhanVien = maNhanVien;
		this.tenNhanVien = tenNhanVien;
		this.gioiTinh = gioiTinh;
		this.ngaySinh = ngaySinh;
		this.cCCD = cCCD;
		this.soDT = soDT;
		this.chucVu = chucVu;
		this.mucLuong = mucLuong;
		this.caLamViec = caLamViec;
		this.tinhTrang = tinhTrang;
		this.taiKhoan = taiKhoan;
	}

	public String getMaNhanVien() {
		return maNhanVien;
	}

	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	public String getTenNhanVien() {
		return tenNhanVien;
	}

	public void setTenNhanVien(String tenNhanVien) {
		this.tenNhanVien = tenNhanVien;
	}

	public String getGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public String getcCCD() {
		return cCCD;
	}

	public void setcCCD(String cCCD) {
		this.cCCD = cCCD;
	}

	public String getSoDT() {
		return soDT;
	}

	public void setSoDT(String soDT) {
		this.soDT = soDT;
	}

	public String getChucVu() {
		return chucVu;
	}

	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}

	public Double getMucLuong() {
		return mucLuong;
	}

	public void setMucLuong(Double mucLuong) {
		this.mucLuong = mucLuong;
	}

	public String getCaLamViec() {
		return caLamViec;
	}

	public void setCaLamViec(String caLamViec) {
		this.caLamViec = caLamViec;
	}
	
	public String getTinhTrang() {
		return tinhTrang;
	}

	public void setTinhTrang(String tinhTrang) {
		this.tinhTrang = tinhTrang;
	}

	public TaiKhoan getTaiKhoan() {
		return taiKhoan;
	}

	public void setTaiKhoan(TaiKhoan taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

	@Override
	public String toString() {
		return "NhanVien [maNhanVien=" + maNhanVien + ", tenNhanVien=" + tenNhanVien + ", gioiTinh=" + gioiTinh
				+ ", ngaySinh=" + ngaySinh + ", cCCD=" + cCCD + ", soDT=" + soDT + ", chucVu=" + chucVu + ", mucLuong="
				+ mucLuong + ", caLamViec=" + caLamViec + ", tinhTrang=" + tinhTrang + ", taiKhoan=" + taiKhoan + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(maNhanVien);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NhanVien other = (NhanVien) obj;
		return Objects.equals(maNhanVien, other.maNhanVien);
	}
	
	
	
}
