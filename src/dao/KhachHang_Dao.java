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
import entity.LoaiPhong;
import entity.NhanVien;
import entity.TaiKhoan;

public class KhachHang_Dao {
	private List<KhachHang> dsKhachHang;

	public List<KhachHang> getDsKhachHang() {
		return dsKhachHang;
	}

	public KhachHang_Dao() {
		super();
	}
	
	public String getMaKhachHang() {
		String maKhachHang="";
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select CONCAT('KH', RIGHT(CONCAT('000',ISNULL(right(max(maKhachHang),3),0) + 1),3)) from [dbo].[KhachHang] where maKhachHang like 'KH%'";
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				maKhachHang = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return maKhachHang;
	}
	
	/**
	 * 
	 * @param Load dữ liệu khách hàng
	 */
	public List<KhachHang> loadKhachHangFromDatabase() {
	    List<KhachHang> dsKhachHang = new ArrayList<>();
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "SELECT * FROM KhachHang";
	        Statement statement = con.createStatement();
	        ResultSet rs = statement.executeQuery(sql);
	        while (rs.next()) {
	            String maKH = rs.getString(1);
	            String hoTen = rs.getString(2);
	            String gioiTinh = rs.getString(3);
	            String soDT = rs.getString(4);
	            String cCCD = rs.getString(5);
	            Date ngaySinh = rs.getDate(6);
	            KhachHang kh = new KhachHang(maKH, hoTen, gioiTinh, soDT, cCCD, ngaySinh);
	            dsKhachHang.add(kh);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return dsKhachHang;
	}

	/**
	 * 
	 * @param kh thêm thông tin khách hàng
	 */
	public boolean addKhachHang(KhachHang kh) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("insert into KhachHang values (?, ?, ?, ?, ?, ?)");
			stmt.setString(1, kh.getMaKhachHang());
			stmt.setString(2, kh.getHoTen());
			stmt.setString(3, kh.getGioiTinh());
			stmt.setString(4, kh.getSoDT());
			stmt.setString(5, kh.getcCCD());
			stmt.setDate(6, kh.getNgaySinh());
			n = stmt.executeUpdate();
		}  catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}

		}
		return n > 0;	
	}
	
	/**
	 * 
	 * @param Lấy danh sách khách hàng
	 */
	public ArrayList<KhachHang> getDanhSachKH() {
		ArrayList<KhachHang> lsKH = new ArrayList<KhachHang>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from KhachHang";

		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				KhachHang kh = new KhachHang();

				kh.setMaKhachHang(rs.getString(1));
				kh.setHoTen(rs.getString(2));
				kh.setGioiTinh(rs.getString(3));
				kh.setSoDT(rs.getString(4));
				kh.setcCCD(rs.getString(5));
				kh.setNgaySinh(rs.getDate(6));
				lsKH.add(kh);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lsKH;
	}
	
	

	/**
	 * 
	 * @param Lấy thông tin kh cần sửa theo maKH
	 */
	
	public boolean updateKH(KhachHang kh, String maKH) {
	    Connection con = ConnectDB.getInstance().getConnection();
	    PreparedStatement stmt = null;
	    int n = 0;
	    try {
	        stmt = con.prepareStatement("UPDATE KhachHang SET hoTen=?, gioiTinh=?, soDT=?, cCCD=?, ngaySinh=? WHERE maKhachHang=?");
	        stmt.setString(1, kh.getHoTen());
	        stmt.setString(2, kh.getGioiTinh());
	        stmt.setString(3, kh.getSoDT());
	        stmt.setString(4, kh.getcCCD());
	        stmt.setDate(5, new java.sql.Date(kh.getNgaySinh().getTime()));
	        stmt.setString(6, maKH);
	        n = stmt.executeUpdate();
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	   return n > 0;
	}
	
	/**
	 * 
	 * @param maKH Lọc theo mã khách hàng
	 */
	public List<KhachHang> getLocMaKhachHang(String maKH) {
        List<KhachHang> dsKhachHang = new ArrayList<>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        try {
            String sql = "SELECT * FROM KhachHang WHERE maKhachHang = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, maKH);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String hoTen = rs.getString("hoTen");
                String gioiTinh = rs.getString("gioiTinh");
                String sdt = rs.getString("soDT");
                String cCCD =  rs.getString("cCCD");
                Date ngaySinh = rs.getDate("ngaySinh");
                
                KhachHang khachHang = new KhachHang(maKH, hoTen, gioiTinh, sdt, cCCD, ngaySinh);
                dsKhachHang.add(khachHang);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsKhachHang;
    }
	
	/**
	 * 
	 * @param tenKH Lọc theo tên khách hàng
	 */
	public List<KhachHang> getLocTenKhachHang(String tenKH) {
        List<KhachHang> dsKhachHang = new ArrayList<>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        try {
            String sql = "SELECT * FROM KhachHang WHERE hoTen = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, tenKH);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String maKH = rs.getString("maKhachHang");
                String gioiTinh = rs.getString("gioiTinh");
                String sdt = rs.getString("soDT");
                String cCCD =  rs.getString("cCCD");
                Date ngaySinh = rs.getDate("ngaySinh");
                
                KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinh, sdt, cCCD, ngaySinh);
                dsKhachHang.add(khachHang);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsKhachHang;
    }
	
	/**
	 * 
	 * @param gioiTinh Lọc khách hàng theo giới tính
	 */
	public List<KhachHang> getLocGioiTinh(String gioiTinh) {
        List<KhachHang> dsKhachHang = new ArrayList<>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        try {
            String sql = "SELECT * FROM KhachHang WHERE gioiTinh = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, gioiTinh);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String maKH = rs.getString("maKhachHang");
                String hoTen = rs.getString("hoTen");
                String sdt = rs.getString("soDT");
                String cCCD =  rs.getString("cCCD");
                Date ngaySinh = rs.getDate("ngaySinh");
                
                KhachHang khachHang = new KhachHang(maKH, hoTen, gioiTinh, sdt, cCCD, ngaySinh);
                dsKhachHang.add(khachHang);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsKhachHang;
    }
	
	/**
	 * 
	 * @param sdt Lọc theo số điện thoại của khách hàng
	 */
	public List<KhachHang> getLocSDT(String sdt) {
        List<KhachHang> dsKhachHang = new ArrayList<>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        try {
            String sql = "SELECT * FROM KhachHang WHERE soDT = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, sdt);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String maKH = rs.getString("maKhachHang");
                String hoTen = rs.getString("hoTen");
                String gioiTinh = rs.getString("gioiTinh");
                String cCCD =  rs.getString("cCCD");
                Date ngaySinh = rs.getDate("ngaySinh");
                
                KhachHang khachHang = new KhachHang(maKH, hoTen, gioiTinh, sdt, cCCD, ngaySinh);
                dsKhachHang.add(khachHang);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsKhachHang;
    }
	
	/**
	 * 
	 * @param cCCD Lọc theo căn cước công dân của khách hàng
	 */
	public List<KhachHang> getLocCCCD(String cCCD) {
        List<KhachHang> dsKhachHang = new ArrayList<>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        try {
            String sql = "SELECT * FROM KhachHang WHERE cCCD = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, cCCD);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String maKH = rs.getString("maKhachHang");
                String hoTen = rs.getString("hoTen");
                String gioiTinh = rs.getString("gioiTinh");
                String sdt =  rs.getString("soDT");
                Date ngaySinh = rs.getDate("ngaySinh");
                
                KhachHang khachHang = new KhachHang(maKH, hoTen, gioiTinh, sdt, cCCD, ngaySinh);
                dsKhachHang.add(khachHang);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsKhachHang;
    }
	
	
	
	public String getMaKhachHangTheoTen(String tenKH) {
		String maKH = "";
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select maKhachHang from KhachHang where hoTen = N'" + tenKH +"'";

		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {

				maKH = rs.getString(1);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return maKH;

	}
	
	public String getMaKhachHangTheoSDT(String sdtKH) {
		String maKH = "";
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select maKhachHang from KhachHang where soDT = N'" + sdtKH +"'";

		try {
			if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
	        }
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {

				maKH = rs.getString(1);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return maKH;

	}
	
	public String getSDTTheoMaHD(String maHoaDon) {
		String sdt = "";
		String sql = "SELECT KhachHang.soDT FROM HoaDon INNER JOIN KhachHang ON HoaDon.maKhachHang = KhachHang.maKhachHang INNER JOIN NhanVien ON HoaDon.maNhanVien = NhanVien.maNhanVien INNER JOIN Phong ON HoaDon.maPhong = Phong.maPhong where maHoaDon = N'"+maHoaDon+"'";
		PreparedStatement statement = null;
		try {
			statement = ConnectDB.getConnection().prepareStatement(sql);
			ResultSet r = statement.executeQuery();
			r.next();
			sdt = r.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sdt;
	}
	
	public KhachHang getMaKhachHangTheoMa(String ma) {
		KhachHang kh = new KhachHang();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from KhachHang where maKhachHang = N'"+ma+"'";
		try {
			if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
	        }
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				kh.setMaKhachHang(rs.getString(1));
				kh.setHoTen(rs.getString(2));
				kh.setGioiTinh(rs.getString(3));
				kh.setSoDT(rs.getString(4));
				kh.setcCCD(rs.getString(5));
				kh.setNgaySinh(rs.getDate(6));
				

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return kh;
	}
	
	
	
}
