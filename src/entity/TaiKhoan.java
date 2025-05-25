package entity;

import java.util.Objects;

public class TaiKhoan {
	private String taiKhoan;
	private String matKhau;
	
	public TaiKhoan() {
		super();
	}
	
	public TaiKhoan(String taiKhoan) {
		super();
		this.taiKhoan = taiKhoan;
	}

	public TaiKhoan(String taiKhoan, String matKhau) {
		super();
		this.taiKhoan = taiKhoan;
		this.matKhau = matKhau;
	}

	public String getTaiKhoan() {
		return taiKhoan;
	}

	public void setTaiKhoan(String taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

	public String getMatKhau() {
		return matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	@Override
	public String toString() {
		return "TaiKhoan [taiKhoan=" + taiKhoan + ", matKhau=" + matKhau + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(taiKhoan);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaiKhoan other = (TaiKhoan) obj;
		return Objects.equals(taiKhoan, other.taiKhoan);
	}
	
	
	
	
}
