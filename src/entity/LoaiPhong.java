package entity;

import java.util.Objects;

public class LoaiPhong {
	private static final long serialVersionUID = 1L;
	private String maLoaiP;
	private String tenLoaiP;
	private int sucChua;
	private int stt;
	
	public LoaiPhong() {
		super();
	}

	 public void setStt(int stt) {
	        this.stt = stt;
	}
	 
	public LoaiPhong(String maLoaiP) {
		super();
		this.maLoaiP = maLoaiP;
	}

	public LoaiPhong(String maLoaiP, String tenLoaiP) {
		super();
		this.maLoaiP = maLoaiP;
		this.tenLoaiP = tenLoaiP;
	}
	
	public LoaiPhong(String maLoaiP, String tenLoaiP, int sucChua) {
		super();
		this.maLoaiP = maLoaiP;
		this.tenLoaiP = tenLoaiP;
		this.sucChua = sucChua;
	}

	public String getMaLoaiP() {
		return maLoaiP;
	}

	public void setMaLoaiP(String maLoaiP) {
		this.maLoaiP = maLoaiP;
	}

	public String getTenLoaiP() {
		return tenLoaiP;
	}

	public void setTenLoaiP(String tenLoaiP) {
		this.tenLoaiP = tenLoaiP;
	}

	public int getSucChua() {
		return sucChua;
	}

	public void setSucChua(int sucChua) {
		this.sucChua = sucChua;
	}

	@Override
	public String toString() {
		return "LoaiPhong [maLoaiP=" + maLoaiP + ", tenLoaiP=" + tenLoaiP + ", sucChua=" + sucChua + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(maLoaiP);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoaiPhong other = (LoaiPhong) obj;
		return Objects.equals(maLoaiP, other.maLoaiP);
	}
	
	
}
