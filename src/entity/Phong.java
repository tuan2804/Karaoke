package entity;

import java.util.Objects;

public class Phong {
	private static final long serialVersionUID = 1L;
	private String maPhong;
	private String tenPhong;
	private String tinhTrangPhong;
	private Double giaPhong;
	private LoaiPhong loaiPhong;
	
	public Phong() {
		super();
	}

	public Phong(String maPhong) {
		super();
		this.maPhong = maPhong;
	}

	
	
	public Phong(String maPhong, String tenPhong, Double giaPhong) {
		super();
		this.maPhong = maPhong;
		this.tenPhong = tenPhong;
		this.giaPhong = giaPhong;
	}

	public Phong(String maPhong, String tenPhong, String tinhTrangPhong, Double giaPhong, LoaiPhong loaiPhong) {
		super();
		this.maPhong = maPhong;
		this.tenPhong = tenPhong;
		this.tinhTrangPhong = tinhTrangPhong;
		this.giaPhong = giaPhong;
		this.loaiPhong = loaiPhong;
	}

	public Double getGiaPhong() {
		return giaPhong;
	}

	public void setGiaPhong(Double giaPhong) {
		this.giaPhong = giaPhong;
	}

	public String getMaPhong() {
		return maPhong;
	}

	public void setMaPhong(String maPhong) {
		this.maPhong = maPhong;
	}

	public String getTenPhong() {
		return tenPhong;
	}

	public void setTenPhong(String tenPhong) {
		this.tenPhong = tenPhong;
	}

	public String getTinhTrangPhong() {
		return tinhTrangPhong;
	}

	public void setTinhTrangPhong(String tinhTrangPhong) {
		this.tinhTrangPhong = tinhTrangPhong;
	}

	public LoaiPhong getLoaiPhong() {
		return loaiPhong;
	}

	public void setLoaiPhong(LoaiPhong loaiPhong) {
		this.loaiPhong = loaiPhong;
	}

	@Override
	public String toString() {
		return "Phong [maPhong=" + maPhong + ", tenPhong=" + tenPhong + ", tinhTrangPhong=" + tinhTrangPhong
				+ ", giaPhong=" + giaPhong + ", loaiPhong=" + loaiPhong + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(maPhong);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Phong other = (Phong) obj;
		return Objects.equals(maPhong, other.maPhong);
	}
	
	
}
