package entity;

import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

public class HoaDon {
	private String maHoaDon;
	private Date ngayLapHD;
	private Time gioVao;
	private Time gioRa;
	private boolean tinhTrangHD;
	private Double giaPhong;
	private NhanVien nhanVien;
	private Phong phong;
	private KhachHang khachHang;
	public HoaDon() {
		super();
	}
	
	
	public HoaDon(String maHoaDon) {
		super();
		this.maHoaDon = maHoaDon;
	}


	public HoaDon(String maHoaDon, Date ngayLapHD, Time gioVao, boolean tinhTrangHD, NhanVien nhanVien, Phong phong,
			KhachHang khachHang) {
		super();
		this.maHoaDon = maHoaDon;
		this.ngayLapHD = ngayLapHD;
		this.gioVao = gioVao;
		this.tinhTrangHD = tinhTrangHD;
		this.nhanVien = nhanVien;
		this.phong = phong;
		this.khachHang = khachHang;
	}


	public HoaDon(String maHoaDon, Date ngayLapHD, Time gioVao, Time gioRa, boolean tinhTrangHD, Double giaPhong,
			NhanVien nhanVien, Phong phong, KhachHang khachHang) {
		super();
		this.maHoaDon = maHoaDon;
		this.ngayLapHD = ngayLapHD;
		this.gioVao = gioVao;
		this.gioRa = gioRa;
		this.tinhTrangHD = tinhTrangHD;
		this.giaPhong = giaPhong;
		this.nhanVien = nhanVien;
		this.phong = phong;
		this.khachHang = khachHang;
	}
	public String getMaHoaDon() {
		return maHoaDon;
	}
	public void setMaHoaDon(String maHoaDon) {
		this.maHoaDon = maHoaDon;
	}
	public Date getNgayLapHD() {
		return ngayLapHD;
	}
	public void setNgayLapHD(Date ngayLapHD) {
		this.ngayLapHD = ngayLapHD;
	}
	public Time getGioVao() {
		return gioVao;
	}
	public void setGioVao(Time gioVao) {
		this.gioVao = gioVao;
	}
	public Time getGioRa() {
		return gioRa;
	}
	public void setGioRa(Time gioRa) {
		this.gioRa = gioRa;
	}
	public boolean isTinhTrangHD() {
		return tinhTrangHD;
	}
	public void setTinhTrangHD(boolean tinhTrangHD) {
		this.tinhTrangHD = tinhTrangHD;
	}
	public Double getGiaPhong() {
		return giaPhong;
	}
	public void setGiaPhong(Double giaPhong) {
		this.giaPhong = giaPhong;
	}
	public NhanVien getNhanVien() {
		return nhanVien;
	}
	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}
	public Phong getPhong() {
		return phong;
	}
	public void setPhong(Phong phong) {
		this.phong = phong;
	}
	public KhachHang getKhachHang() {
		return khachHang;
	}
	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}
	@Override
	public String toString() {
		return "HoaDon [maHoaDon=" + maHoaDon + ", ngayLapHD=" + ngayLapHD + ", gioVao=" + gioVao + ", gioRa=" + gioRa
				+ ", tinhTrangHD=" + tinhTrangHD + ", giaPhong=" + giaPhong + ", nhanVien=" + nhanVien + ", phong="
				+ phong + ", khachHang=" + khachHang + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(maHoaDon);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HoaDon other = (HoaDon) obj;
		return Objects.equals(maHoaDon, other.maHoaDon);
	}
	
	
}