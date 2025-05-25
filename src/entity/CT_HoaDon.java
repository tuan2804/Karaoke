package entity;

public class CT_HoaDon {
	private HoaDon hoaDon;
	private DichVu dichVu;
	private int soLuongDat;
	private double donGia;
	private String donViTinh;
	
	
	
	public CT_HoaDon(HoaDon hoaDon) {
		super();
		this.hoaDon = hoaDon;
	}

	public CT_HoaDon(HoaDon hoaDon, DichVu dichVu, int soLuongDat, double donGia, String donViTinh) {
		super();
		this.hoaDon = hoaDon;
		this.dichVu = dichVu;
		this.soLuongDat = soLuongDat;
		this.donGia = donGia;
		this.donViTinh = donViTinh;
	}

	public CT_HoaDon() {
		super();
	}

	public HoaDon getHoaDon() {
		return hoaDon;
	}

	public void setHoaDon(HoaDon hoaDon) {
		this.hoaDon = hoaDon;
	}

	public DichVu getDichVu() {
		return dichVu;
	}

	public void setDichVu(DichVu dichVu) {
		this.dichVu = dichVu;
	}

	public int getSoLuongDat() {
		return soLuongDat;
	}

	public void setSoLuongDat(int soLuongDat) {
		this.soLuongDat = soLuongDat;
	}

	public double getDonGia() {
		return donGia;
	}

	public void setDonGia(double donGia) {
		this.donGia = donGia;
	}

	public String getDonViTinh() {
		return donViTinh;
	}

	public void setDonViTinh(String donViTinh) {
		this.donViTinh = donViTinh;
	}

	@Override
	public String toString() {
		return "CT_HoaDon [hoaDon=" + hoaDon + ", dichVu=" + dichVu + ", soLuongDat=" + soLuongDat + ", donGia="
				+ donGia + "]";
	}
	
	
}
