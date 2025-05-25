package entity;

import java.util.Objects;

public class DichVu {
	private String maDichVu;
	private String tenDichVu;
	private Double giaBan;
	private String donViTinh;
	private int soLuongTon;
	private LoaiDichVu loaiDV;
	
	public DichVu() {
		super();
	}

	public DichVu(String maDichVu) {
		super();
		this.maDichVu = maDichVu;
	}

	public DichVu(String maDichVu, String tenDichVu, Double giaBan, String donViTinh, int soLuongTon,
			LoaiDichVu loaiDV) {
		super();
		this.maDichVu = maDichVu;
		this.tenDichVu = tenDichVu;
		this.giaBan = giaBan;
		this.donViTinh = donViTinh;
		this.soLuongTon = soLuongTon;
		this.loaiDV = loaiDV;
	}

	public String getMaDichVu() {
		return maDichVu;
	}

	public void setMaDichVu(String maDichVu) {
		this.maDichVu = maDichVu;
	}

	public String getTenDichVu() {
		return tenDichVu;
	}

	public void setTenDichVu(String tenDichVu) {
		this.tenDichVu = tenDichVu;
	}

	public Double getGiaBan() {
		return giaBan;
	}

	public void setGiaBan(Double giaBan) {
		this.giaBan = giaBan;
	}

	public String getDonViTinh() {
		return donViTinh;
	}

	public void setDonViTinh(String donViTinh) {
		this.donViTinh = donViTinh;
	}
	
	public int getSoLuongTon() {
		return soLuongTon;
	}

	public void setSoLuongTon(int soLuongTon) {
		this.soLuongTon = soLuongTon;
	}

	public LoaiDichVu getLoaiDV() {
		return loaiDV;
	}

	public void setLoaiDV(LoaiDichVu loaiDV) {
		this.loaiDV = loaiDV;
	}

	@Override
	public String toString() {
		return "DichVu [maDichVu=" + maDichVu + ", tenDichVu=" + tenDichVu + ", giaBan=" + giaBan + ", soLuongTon="
				+ soLuongTon + ", loaiDV=" + loaiDV + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(maDichVu);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DichVu other = (DichVu) obj;
		return Objects.equals(maDichVu, other.maDichVu);
	}
	
	
}
