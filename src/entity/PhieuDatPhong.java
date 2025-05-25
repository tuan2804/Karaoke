package entity;

import java.sql.Date;
import java.sql.Time;

public class PhieuDatPhong {
	private String maPhieuDatPhong;
	private KhachHang khachhang;
	private Phong phong;
	private Date ngayDatPhong;
	private Time gioDatPhong;
	private String soDT;
	
	public PhieuDatPhong() {
		super();
	}
	
	public String getSdtKhachHang() {
        return soDT;
    }
	
	 public void setSdtKhachHang(String sdtKhachHang) {
	        this.soDT = sdtKhachHang;
	    }
	
	public PhieuDatPhong(String maPhieuDatPhong) {
		super();
		this.maPhieuDatPhong = maPhieuDatPhong;
	}
	
	public PhieuDatPhong(String maPhieuDatPhong, KhachHang khachhang, Phong phong, Date ngayDatPhong,
			Time gioDatPhong) {
		super();
		this.maPhieuDatPhong = maPhieuDatPhong;
		this.khachhang = khachhang;
		this.phong = phong;
		this.ngayDatPhong = ngayDatPhong;
		this.gioDatPhong = gioDatPhong;
	}
	
	public PhieuDatPhong(String maPhieuDatPhong, Phong phong, Date ngayDatPhong) {
		super();
		this.maPhieuDatPhong = maPhieuDatPhong;
		this.phong = phong;
		this.ngayDatPhong = ngayDatPhong;
	}
	
	public String getMaPhieuDatPhong() {
		return maPhieuDatPhong;
	}
	
	public void setMaPhieuDatPhong(String maPhieuDatPhong) {
		this.maPhieuDatPhong = maPhieuDatPhong;
	}
	
	public Date getNgayDatPhong() {
		return ngayDatPhong;
	}
	
	public void setNgayDatPhong(Date ngayDatPhong) {
		this.ngayDatPhong = ngayDatPhong;
	}
	
	public Time getGioDatPhong() {
		return gioDatPhong;
	}
	
	public void setGioDatPhong(Time gioDatPhong) {
		this.gioDatPhong = gioDatPhong;
	}
	
	public KhachHang getKhachhang() {
		return khachhang;
	}
	
	public void setKhachhang(KhachHang khachhang) {
		this.khachhang = khachhang;
	}
	
	public Phong getPhong() {
		return phong;
	}
	
	public void setPhong(Phong phong) {
		this.phong = phong;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((maPhieuDatPhong == null) ? 0 : maPhieuDatPhong.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhieuDatPhong other = (PhieuDatPhong) obj;
		if (maPhieuDatPhong == null) {
			if (other.maPhieuDatPhong != null)
				return false;
		} else if (!maPhieuDatPhong.equals(other.maPhieuDatPhong))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "PhieuDatPhong [maPhieuDatPhong=" + maPhieuDatPhong + ", khachhang=" + khachhang + ", phong=" + phong
				+ ", ngayDatPhong=" + ngayDatPhong + ", gioDatPhong=" + gioDatPhong + "]";
	}
	
	
}