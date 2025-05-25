package entity;

import java.sql.Date;
import java.util.Objects;

public class KhachHang {
	private String maKhachHang;
	private String hoTen;
	private String gioiTinh;
	private String soDT;
	private String cCCD;
	private Date ngaySinh;
	
	public KhachHang() {
		super();
	}

	
	public KhachHang(String maKhachHang) {
		super();
		this.maKhachHang = maKhachHang;
	}



	public KhachHang(String maKhachHang, String hoTen) {
		super();
		this.maKhachHang = maKhachHang;
		this.hoTen = hoTen;
	}


	public KhachHang(String maKhachHang, String hoTen, String gioiTinh, String soDT, String cCCD, Date ngaySinh) {
		super();
		this.maKhachHang = maKhachHang;
		this.hoTen = hoTen;
		this.gioiTinh = gioiTinh;
		this.soDT = soDT;
		this.cCCD = cCCD;
		this.ngaySinh = ngaySinh;
	}

	public String getMaKhachHang() {
		return maKhachHang;
	}

	public void setMaKhachHang(String maKhachHang) {
		this.maKhachHang = maKhachHang;
	}

	public String getHoTen() {
		return hoTen;
	}

	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}

	public String getGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public String getSoDT() {
		return soDT;
	}

	public void setSoDT(String soDT) {
		this.soDT = soDT;
	}

	public String getcCCD() {
		return cCCD;
	}

	public void setcCCD(String cCCD) {
		this.cCCD = cCCD;
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	@Override
	public String toString() {
		return "KhachHang [maKhachHang=" + maKhachHang + ", hoTen=" + hoTen + ", gioiTinh=" + gioiTinh + ", soDT="
				+ soDT + ", cCCD=" + cCCD + ", ngaySinh=" + ngaySinh + "]";
	}

	@Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        KhachHang other = (KhachHang) obj;
        return Objects.equals(maKhachHang, other.maKhachHang);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maKhachHang);
    }
	
	
}
