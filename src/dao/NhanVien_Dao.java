package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.KhachHang;
import entity.NhanVien;
import entity.TaiKhoan;

public class NhanVien_Dao {
	private List<NhanVien> dsNhanVien;

	public List<NhanVien> getDsNhanVien() {
		return dsNhanVien;
	}

	public NhanVien_Dao() {
		super();
	}
	
	/**
	 * Phương thức getMaNV() trả về mã nhân viên mới.
	 * Mã nhân viên được tạo dựa trên mã nhân viên lớn nhất trong cơ sở dữ liệu và tăng thêm 1.
	 * Mã nhân viên được định dạng theo chuẩn 'NVxxx', trong đó 'xxx' là 3 chữ số cuối cùng của số tiếp theo.
	 * Nếu không có mã nhân viên nào trong cơ sở dữ liệu, mã nhân viên mới sẽ là 'NV001'.
	 *
	 * @return Mã nhân viên mới
	 */
	public String getMaNV() {
	    String maNV = "";
	    ConnectDB.getInstance();
	    Connection con = ConnectDB.getConnection();
	    String sql = "SELECT CONCAT('NV', RIGHT(CONCAT('000', ISNULL(RIGHT(MAX(maNhanVien), 3), 0) + 1), 3)) " +
	                 "FROM [dbo].[NhanVien] WHERE maNhanVien LIKE 'NV%'";
	    try {
	        if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
	        }
	        Statement stm = con.createStatement();
	        ResultSet rs = stm.executeQuery(sql);
	        while (rs.next()) {
	            maNV = rs.getString(1);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return maNV;
	}
	
	/**
	 * Phương thức loadNhanVienFromDatabase() trả về danh sách các nhân viên từ cơ sở dữ liệu.
	 *
	 * @return Danh sách các nhân viên từ cơ sở dữ liệu
	 */
	public List<NhanVien> loadNhanVienFromDatabase() {
	    List<NhanVien> dsNhanVien = new ArrayList<>();
	    Connection con = null;
	    Statement statement = null;
	    ResultSet rs = null;
	    try {
	        con = ConnectDB.getInstance().getConnection();
	        if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
	        }
	        statement = con.createStatement();
	        rs = statement.executeQuery("SELECT * FROM NhanVien");

	        while (rs.next()) {
	            String maNV = rs.getString("maNhanVien");
	            String tenNV = rs.getString("tenNhanVien");
	            String gioiTinh = rs.getString("gioiTinh");
	            Date ngaySinh = rs.getDate("ngaySinh");
	            String cCCD = rs.getString("cCCD");
	            String soDT = rs.getString("soDT");
	            String chucVu = rs.getString("chucVu");
	            Double mucLuong = rs.getDouble("mucLuong");
	            String caLamViec = rs.getString("caLamViec");
	            String tinhTrang = rs.getString("tinhTrang");
	            String taiKhoan = rs.getString("taiKhoan");

	            TaiKhoan tk = new TaiKhoan(taiKhoan);
	            NhanVien nv = new NhanVien(maNV, tenNV, gioiTinh, ngaySinh, cCCD, soDT, chucVu, mucLuong, caLamViec, tinhTrang, tk);
	            dsNhanVien.add(nv);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (rs != null) {
	            try {
	                rs.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (con != null) {
	            try {
	                con.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    return dsNhanVien;
	}

	/**
	 * Phương thức addNhanVien(NhanVien nv) thêm một nhân viên mới vào cơ sở dữ liệu.
	 *
	 * @param nv Nhân viên cần được thêm vào cơ sở dữ liệu
	 * @return True nếu nhân viên được thêm thành công, False nếu không thành công
	 * @throws SQLException Nếu có lỗi xảy ra trong quá trình thao tác với cơ sở dữ liệu
	 */
	public boolean addNhanVien(NhanVien nv) throws SQLException {
	    Connection con = null;
	    PreparedStatement ps = null;
	    int n = 0;
	    try {
	        con = ConnectDB.getInstance().getConnection();
	        if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
	        }
	        String sql = "INSERT INTO NhanVien (maNhanVien, tenNhanVien, gioiTinh, ngaySinh, cCCD, soDT, chucVu, mucLuong, caLamViec, tinhTrang, taiKhoan) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	        ps = con.prepareStatement(sql);
	        ps.setString(1, nv.getMaNhanVien());
	        ps.setString(2, nv.getTenNhanVien());
	        ps.setString(3, nv.getGioiTinh());
	        ps.setDate(4, nv.getNgaySinh());
	        ps.setString(5, nv.getcCCD());
	        ps.setString(6, nv.getSoDT());
	        ps.setString(7, nv.getChucVu());
	        ps.setDouble(8, nv.getMucLuong());
	        ps.setString(9, nv.getCaLamViec());
	        ps.setString(10, nv.getTinhTrang());
	        ps.setString(11, nv.getTaiKhoan().getTaiKhoan());

	        n = ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (ps != null) {
	            ps.close();
	        }
	        if (con != null) {
	            con.close();
	        }
	    }
	    return n > 0;
	}
	
	/**
	 * Phương thức updateNV(NhanVien nv, String maNV) cập nhật thông tin của một nhân viên trong cơ sở dữ liệu dựa trên mã nhân viên.
	 *
	 * @param nv   Thông tin nhân viên mới cần được cập nhật
	 * @param maNV Mã nhân viên của nhân viên cần được cập nhật thông tin
	 * @return True nếu cập nhật thành công, False nếu không thành công
	 */
	public boolean updateNV(NhanVien nv, String maNV) {
	    Connection con = ConnectDB.getInstance().getConnection();
	    PreparedStatement stmt = null;
	    int n = 0;
	    try {
	        if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect();
	        }
	        
	        stmt = con.prepareStatement("UPDATE NhanVien SET tenNhanVien=?, gioiTinh=?, ngaySinh=?, cCCD=?, soDT=?, chucVu=?, mucLuong=?, caLamViec=?, tinhTrang=? WHERE maNhanVien=?");
	        stmt.setString(1, nv.getTenNhanVien());
	        stmt.setString(2, nv.getGioiTinh());
	        stmt.setDate(3, new java.sql.Date(nv.getNgaySinh().getTime()));
	        stmt.setString(4, nv.getcCCD());
	        stmt.setString(5, nv.getSoDT());
	        stmt.setString(6, nv.getChucVu());
	        stmt.setDouble(7, nv.getMucLuong());
	        stmt.setString(8, nv.getCaLamViec());
	        stmt.setString(9, nv.getTinhTrang());
	        stmt.setString(10, maNV);
	        n = stmt.executeUpdate();
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	   return n > 0;
	}
	
	
	/**
	 * Phương thức getNVTheoTK(String maTK) trả về thông tin của một nhân viên dựa trên tài khoản.
	 *
	 * @param maTK Tài khoản của nhân viên cần lấy thông tin
	 * @return Thông tin của nhân viên cần tìm
	 */
	public NhanVien getNVTheoTK(String maTK) { 
		NhanVien nv = new NhanVien();
		Connection con = null;
		String sql = "select * from NhanVien where taiKhoan = '"+maTK+"'";

		try {
			con = ConnectDB.getInstance().getConnection();
	        if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
	        }
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				nv.setMaNhanVien(rs.getString(1));
				nv.setTenNhanVien(rs.getString(2));
				nv.setGioiTinh(rs.getString(3));
				nv.setNgaySinh(rs.getDate(4));
				nv.setcCCD(rs.getString(5));
				nv.setSoDT(rs.getString(6));
				nv.setChucVu(rs.getString(7));
				nv.setMucLuong(rs.getDouble(8));
				nv.setCaLamViec(rs.getString(9));
				nv.setTinhTrang(rs.getString(10));
				nv.setTaiKhoan(new TaiKhoan(rs.getNString(11)));
		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nv;
	}
	
	/**
	 * Phương thức checkmaNV(String maNV) kiểm tra mã nhân viên trong cơ sở dữ liệu.
	 *
	 * @param maNV Mã nhân viên cần kiểm tra
	 * @return True nếu mã nhân viên không tồn tại trong cơ sở dữ liệu, False nếu mã nhân viên đã tồn tại
	 */
	public boolean checkmaNV(String maNV) { 
		NhanVien nv = new NhanVien();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from [dbo].[NhanVien] where maNhanVien = '"+maNV+"'";
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				nv.setMaNhanVien(rs.getString(1));
				nv.setTenNhanVien(rs.getString(2));
				nv.setGioiTinh(rs.getString(3));
				nv.setNgaySinh(rs.getDate(4));
				nv.setcCCD(rs.getString(5));
				nv.setSoDT(rs.getString(6));
				nv.setChucVu(rs.getString(7));
				nv.setMucLuong(rs.getDouble(8));
				nv.setCaLamViec(rs.getString(9));
				nv.setTinhTrang(rs.getString(10));
				nv.setTaiKhoan(new TaiKhoan(rs.getString(11)));
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * Phương thức checkSdtNV(String sdt) kiểm tra số điện thoại của nhân viên trong cơ sở dữ liệu.
	 *
	 * @param sdt Số điện thoại cần kiểm tra
	 * @return True nếu số điện thoại không tồn tại trong cơ sở dữ liệu, False nếu số điện thoại đã tồn tại
	 */
	public boolean checkSdtNV(String sdt) { 
		NhanVien nv = new NhanVien();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from [dbo].[NhanVien] where soDT = '"+sdt+"'";
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				nv.setMaNhanVien(rs.getString(1));
				nv.setTenNhanVien(rs.getString(2));
				nv.setGioiTinh(rs.getString(3));
				nv.setNgaySinh(rs.getDate(4));
				nv.setcCCD(rs.getString(5));
				nv.setSoDT(rs.getString(6));
				nv.setChucVu(rs.getString(7));
				nv.setMucLuong(rs.getDouble(8));
				nv.setCaLamViec(rs.getString(9));
				nv.setTinhTrang(rs.getString(10));
				nv.setTaiKhoan(new TaiKhoan(rs.getString(11)));
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 
	 * @param cccd kiểm tra cccd của nhân viên
	 */
	public boolean checkCccdNV(String cccd) { 
		NhanVien nv = new NhanVien();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from [dbo].[NhanVien] where cccd = '"+cccd+"'";
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				nv.setMaNhanVien(rs.getString(1));
				nv.setTenNhanVien(rs.getString(2));
				nv.setGioiTinh(rs.getString(3));
				nv.setNgaySinh(rs.getDate(4));
				nv.setcCCD(rs.getString(5));
				nv.setSoDT(rs.getString(6));
				nv.setChucVu(rs.getString(7));
				nv.setMucLuong(rs.getDouble(8));
				nv.setCaLamViec(rs.getString(9));
				nv.setTinhTrang(rs.getString(10));
				nv.setTaiKhoan(new TaiKhoan(rs.getString(11)));
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Phương thức checkCccdNV(String cccd) kiểm tra số CCCD của nhân viên trong cơ sở dữ liệu.
	 *
	 * @param cccd Số CCCD cần kiểm tra
	 * @return True nếu số CCCD không tồn tại trong cơ sở dữ liệu, False nếu số CCCD đã tồn tại
	 */
	public List<NhanVien> getLocMaNhanVien(String maNV) {
	    List<NhanVien> dsNhanVien = new ArrayList<>();
	    Connection con = ConnectDB.getInstance().getConnection();
	    try {
	        if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect();
	        }
	        
	        String sql = "SELECT * FROM NhanVien WHERE maNhanVien = ?";
	        PreparedStatement statement = con.prepareStatement(sql);
	        statement.setString(1, maNV);
	        ResultSet rs = statement.executeQuery();
	        while (rs.next()) {
	            String tenNV = rs.getString("tenNhanVien");
	            String gioiTinh = rs.getString("gioiTinh");
	            Date ngaySinh = rs.getDate("ngaySinh");
	            String cCCD = rs.getString("cCCD");
	            String soDT = rs.getString("soDT");
	            String chucVu = rs.getString("chucVu");
	            Double mucLuong = rs.getDouble("mucLuong");
	            String caLamViec = rs.getString("caLamViec");
	            String tinhTrang = rs.getString("tinhTrang");
	            TaiKhoan taiKhoan = new TaiKhoan(rs.getString("taiKhoan"));
	            NhanVien nv = new NhanVien(maNV, tenNV, gioiTinh, ngaySinh, cCCD, soDT, chucVu, mucLuong, caLamViec, tinhTrang, taiKhoan);
	            dsNhanVien.add(nv);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return dsNhanVien;
	}
	
	/**
	 * Phương thức getLocTenNhanvien(String tenNV) trả về danh sách nhân viên được lọc theo tên nhân viên.
	 *
	 * @param tenNV Tên nhân viên cần lọc
	 * @return Danh sách nhân viên được lọc theo tên nhân viên
	 */
	public List<NhanVien> getLocTenNhanvien(String tenNV) {
	    List<NhanVien> dsNhanVien = new ArrayList<>();
	    Connection con = ConnectDB.getInstance().getConnection();
	    try {
	        if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect();
	        }
	        
	        String sql = "SELECT * FROM NhanVien WHERE tenNhanVien = ?";
	        PreparedStatement statement = con.prepareStatement(sql);
	        statement.setString(1, tenNV);
	        ResultSet rs = statement.executeQuery();
	        while (rs.next()) {
	            String maNV = rs.getString("maNhanVien");
	            String gioiTinh = rs.getString("gioiTinh");
	            Date ngaySinh = rs.getDate("ngaySinh");
	            String cCCD = rs.getString("cCCD");
	            String soDT = rs.getString("soDT");
	            String chucVu = rs.getString("chucVu");
	            Double mucLuong = rs.getDouble("mucLuong");
	            String caLamViec = rs.getString("caLamViec");
	            String tinhTrang = rs.getString("tinhTrang");
	            TaiKhoan taiKhoan = new TaiKhoan(rs.getString("taiKhoan"));
	            NhanVien nv = new NhanVien(maNV, tenNV, gioiTinh, ngaySinh, cCCD, soDT, chucVu, mucLuong, caLamViec, tinhTrang, taiKhoan);
	            dsNhanVien.add(nv);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return dsNhanVien;
	}
	
	/**
	 * Phương thức getLocSoDienThoai(String soDT) trả về danh sách nhân viên được lọc theo số điện thoại.
	 *
	 * @param soDT Số điện thoại cần lọc
	 * @return Danh sách nhân viên được lọc theo số điện thoại
	 */
	public List<NhanVien> getLocSoDienThoai(String soDT) {
	    List<NhanVien> dsNhanVien = new ArrayList<>();
	    Connection con = ConnectDB.getInstance().getConnection();
	    try {
	        if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect();
	        }
	        
	        String sql = "SELECT * FROM NhanVien WHERE soDT = ?";
	        PreparedStatement statement = con.prepareStatement(sql);
	        statement.setString(1, soDT);
	        ResultSet rs = statement.executeQuery();
	        while (rs.next()) {
	            String maNV = rs.getString("maNhanVien");
	            String tenNV = rs.getString("tenNhanVien");
	            String gioiTinh = rs.getString("gioiTinh");
	            Date ngaySinh = rs.getDate("ngaySinh");
	            String cCCD = rs.getString("cCCD");
	            String chucVu = rs.getString("chucVu");
	            Double mucLuong = rs.getDouble("mucLuong");
	            String caLamViec = rs.getString("caLamViec");
	            String tinhTrang = rs.getString("tinhTrang");
	            TaiKhoan taiKhoan = new TaiKhoan(rs.getString("taiKhoan"));
	            NhanVien nv = new NhanVien(maNV, tenNV, gioiTinh, ngaySinh, cCCD, soDT, chucVu, mucLuong, caLamViec, tinhTrang, taiKhoan);
	            dsNhanVien.add(nv);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return dsNhanVien;
	}
	
	/**
	 * Phương thức getLocCCCD(String cCCD) trả về danh sách nhân viên được lọc theo số căn cước công dân.
	 *
	 * @param cCCD Số căn cước công dân cần lọc
	 * @return Danh sách nhân viên được lọc theo số căn cước công dân
	 */
	public List<NhanVien> getLocCCCD(String cCCD) {
	    List<NhanVien> dsNhanVien = new ArrayList<>();
	    Connection con = ConnectDB.getInstance().getConnection();
	    try {
	        if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect();
	        }
	        
	        String sql = "SELECT * FROM NhanVien WHERE cCCD = ?";
	        PreparedStatement statement = con.prepareStatement(sql);
	        statement.setString(1, cCCD);
	        ResultSet rs = statement.executeQuery();
	        while (rs.next()) {
	            String maNV = rs.getString("maNhanVien");
	            String tenNV = rs.getString("tenNhanVien");
	            String gioiTinh = rs.getString("gioiTinh");
	            Date ngaySinh = rs.getDate("ngaySinh");
	            String soDT = rs.getString("soDT");
	            String chucVu = rs.getString("chucVu");
	            Double mucLuong = rs.getDouble("mucLuong");
	            String caLamViec = rs.getString("caLamViec");
	            String tinhTrang = rs.getString("tinhTrang");
	            TaiKhoan taiKhoan = new TaiKhoan(rs.getString("taiKhoan"));
	            NhanVien nv = new NhanVien(maNV, tenNV, gioiTinh, ngaySinh, cCCD, soDT, chucVu, mucLuong, caLamViec, tinhTrang, taiKhoan);
	            dsNhanVien.add(nv);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return dsNhanVien;
	}
	
	/**
	 * Phương thức getLocChucVu(String chucVu) trả về danh sách nhân viên được lọc theo chức vụ.
	 *
	 * @param chucVu Chức vụ cần lọc
	 * @return Danh sách nhân viên được lọc theo chức vụ
	 */
	public List<NhanVien> getLocChucVu(String chucVu) {
	    List<NhanVien> dsNhanVien = new ArrayList<>();
	    Connection con = ConnectDB.getInstance().getConnection();
	    try {
	        if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect();
	        }
	        
	        String sql = "SELECT * FROM NhanVien WHERE chucVu = ?";
	        PreparedStatement statement = con.prepareStatement(sql);
	        statement.setString(1, chucVu);
	        ResultSet rs = statement.executeQuery();
	        while (rs.next()) {
	        	String maNV = rs.getString("maNhanVien");
	        	String tenNV = rs.getString("tenNhanVien");
	            String gioiTinh = rs.getString("gioiTinh");
	            Date ngaySinh = rs.getDate("ngaySinh");
	            String cCCD = rs.getString("cCCD");
	            String soDT = rs.getString("soDT");
	            Double mucLuong = rs.getDouble("mucLuong");
	            String caLamViec = rs.getString("caLamViec");
	            String tinhTrang = rs.getString("tinhTrang");
	            TaiKhoan taiKhoan = new TaiKhoan(rs.getString("taiKhoan"));
	            NhanVien nv = new NhanVien(maNV, tenNV, gioiTinh, ngaySinh, cCCD, soDT, chucVu, mucLuong, caLamViec, tinhTrang, taiKhoan);
	            dsNhanVien.add(nv);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return dsNhanVien;
	}
	
	/**
	 * Phương thức getLocCaLamViec(String caLamViec) trả về danh sách nhân viên được lọc theo ca làm việc.
	 *
	 * @param caLamViec Ca làm việc cần lọc
	 * @return Danh sách nhân viên được lọc theo ca làm việc
	 */
	public List<NhanVien> getLocCaLamViec(String caLamViec) {
	    List<NhanVien> dsNhanVien = new ArrayList<>();
	    Connection con = ConnectDB.getInstance().getConnection();
	    try {
	        if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect();
	        }
	        
	        String sql = "SELECT * FROM NhanVien WHERE caLamViec = ?";
	        PreparedStatement statement = con.prepareStatement(sql);
	        statement.setString(1, caLamViec);
	        ResultSet rs = statement.executeQuery();
	        while (rs.next()) {
	        	String maNV = rs.getString("maNhanVien");
	        	String tenNV = rs.getString("tenNhanVien");
	            String gioiTinh = rs.getString("gioiTinh");
	            Date ngaySinh = rs.getDate("ngaySinh");
	            String cCCD = rs.getString("cCCD");
	            String soDT = rs.getString("soDT");
	            String chucVu = rs.getString("chucVu");
	            Double mucLuong = rs.getDouble("mucLuong");
	            String tinhTrang = rs.getString("tinhTrang");
	            TaiKhoan taiKhoan = new TaiKhoan(rs.getString("taiKhoan"));
	            NhanVien nv = new NhanVien(maNV, tenNV, gioiTinh, ngaySinh, cCCD, soDT, chucVu, mucLuong, caLamViec, tinhTrang, taiKhoan);
	            dsNhanVien.add(nv);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return dsNhanVien;
	}
	
	/**
	 * Phương thức getLocTinhTrang(String tinhTrang) trả về danh sách nhân viên được lọc theo tình trạng.
	 *
	 * @param tinhTrang Tình trạng cần lọc
	 * @return Danh sách nhân viên được lọc theo tình trạng
	 */
	public List<NhanVien> getLocTinhTrang(String tinhTrang) {
	    List<NhanVien> dsNhanVien = new ArrayList<>();
	    Connection con = ConnectDB.getInstance().getConnection();
	    try {
	        if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect();
	        }
	        
	        String sql = "SELECT * FROM NhanVien WHERE tinhTrang = ?";
	        PreparedStatement statement = con.prepareStatement(sql);
	        statement.setString(1, tinhTrang);
	        ResultSet rs = statement.executeQuery();
	        while (rs.next()) {
	        	String maNV = rs.getString("maNhanVien");
	        	String tenNV = rs.getString("tenNhanVien");
	            String gioiTinh = rs.getString("gioiTinh");
	            Date ngaySinh = rs.getDate("ngaySinh");
	            String cCCD = rs.getString("cCCD");
	            String soDT = rs.getString("soDT");
	            String chucVu = rs.getString("chucVu");
	            Double mucLuong = rs.getDouble("mucLuong");
	            String caLamViec = rs.getString("caLamViec");
	            TaiKhoan taiKhoan = new TaiKhoan(rs.getString("taiKhoan"));
	            NhanVien nv = new NhanVien(maNV, tenNV, gioiTinh, ngaySinh, cCCD, soDT, chucVu, mucLuong, caLamViec, tinhTrang, taiKhoan);
	            dsNhanVien.add(nv);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return dsNhanVien;
	}
	
	/**
	 * Phương thức getLocGioiTinh(String gioiTinh) trả về danh sách nhân viên được lọc theo giới tính.
	 *
	 * @param gioiTinh Giới tính cần lọc
	 * @return Danh sách nhân viên được lọc theo giới tính
	 */
	public List<NhanVien> getLocGioiTinh(String gioiTinh) {
	    List<NhanVien> dsNhanVien = new ArrayList<>();
	    Connection con = ConnectDB.getInstance().getConnection();
	    try {
	        if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect();
	        }
	        
	        String sql = "SELECT * FROM NhanVien WHERE gioiTinh = ?";
	        PreparedStatement statement = con.prepareStatement(sql);
	        statement.setString(1, gioiTinh);
	        ResultSet rs = statement.executeQuery();
	        while (rs.next()) {
	        	String maNV = rs.getString("maNhanVien");
	        	String tenNV = rs.getString("tenNhanVien");
	            Date ngaySinh = rs.getDate("ngaySinh");
	            String cCCD = rs.getString("cCCD");
	            String soDT = rs.getString("soDT");
	            String chucVu = rs.getString("chucVu");
	            Double mucLuong = rs.getDouble("mucLuong");
	            String tinhTrang = rs.getString("tinhTrang");
	            String caLamViec = rs.getString("caLamViec");
	            TaiKhoan taiKhoan = new TaiKhoan(rs.getString("taiKhoan"));
	            NhanVien nv = new NhanVien(maNV, tenNV, gioiTinh, ngaySinh, cCCD, soDT, chucVu, mucLuong, caLamViec, tinhTrang, taiKhoan);
	            dsNhanVien.add(nv);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return dsNhanVien;
	}
	
	/**
	 * Phương thức getNVtheoMa(String ma) trả về thông tin của nhân viên dựa trên mã nhân viên.
	 *
	 * @param ma Mã nhân viên
	 * @return Thông tin của nhân viên
	 */
	public NhanVien getNVtheoMa(String ma) {
	    NhanVien nv = null;
	    ConnectDB.getInstance();
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement statement = null;
	    try {
	        String sql = "SELECT maNhanVien, tenNhanVien, gioiTinh, ngaySinh, cCCD, soDT, chucVu, mucLuong, caLamViec, tinhTrang, taiKhoan FROM NhanVien WHERE maNhanVien = ?";
	        statement = con.prepareStatement(sql);
	        statement.setString(1, ma);
	        ResultSet rs = statement.executeQuery();
	        if (rs.next()) {
	            nv = new NhanVien(rs.getString("maNhanVien"), rs.getString("tenNhanVien"),
	                    rs.getString("gioiTinh"), rs.getDate("ngaySinh"), rs.getString("cCCD"),
	                    rs.getString("soDT"), rs.getString("chucVu"), rs.getDouble("mucLuong"),
	                    rs.getString("caLamViec"), rs.getString("tinhTrang"),
	                    new TaiKhoan(rs.getString("taiKhoan")));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (statement != null) {
	                statement.close();
	            }
	            if (con != null) {
	                con.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return nv;
	}
	
}
