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
import entity.CT_HoaDon;
import entity.DichVu;
import entity.HoaDon;
import entity.LoaiPhong;
import entity.Phong;

public class CT_HoaDon_Dao {
	private List<CT_HoaDon> dsCTHoaDon;
	
	/**
	 * Phương thức loadDSCTHoaDonFromDatabase() trả về danh sách các chi tiết hóa đơn từ cơ sở dữ liệu.
	 *
	 * @return Danh sách chi tiết hóa đơn
	 */
	public List<CT_HoaDon> loadDSCTHoaDonFromDatabase(){
		dsCTHoaDon = new ArrayList<CT_HoaDon>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
	        }
			String sql = "Select * from CT_HoaDon";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				HoaDon maHoaDon = new HoaDon(rs.getString(1));
				DichVu maDichVu = new DichVu(rs.getString(2));
				int soLuongDat = rs.getInt(3);
				Double donGia = rs.getDouble(4);
				String donViTinh = rs.getString(5);
				CT_HoaDon ctHoaDon = new CT_HoaDon(maHoaDon, maDichVu, soLuongDat, soLuongDat, donViTinh);
				dsCTHoaDon.add(ctHoaDon);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsCTHoaDon;
	}
	
	/**
	 * Phương thức loadDSCTHoaDonTheoMa(String ma) trả về danh sách các chi tiết hóa đơn dựa trên mã hóa đơn.
	 *
	 * @param ma Mã hóa đơn
	 * @return Danh sách chi tiết hóa đơn
	 */
	public List<CT_HoaDon> loadDSCTHoaDonTheoMa(String ma){
		dsCTHoaDon = new ArrayList<CT_HoaDon>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
	        }
			String sql = "Select * from CT_HoaDon where maHoaDon = N'"+ma+"'";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				HoaDon maHoaDon = new HoaDon(rs.getString(1));
				DichVu maDichVu = new DichVu(rs.getString(2));
				int soLuongDat = rs.getInt(3);
				Double donGia = rs.getDouble(4);
				String donViTinh = rs.getString(5);
				CT_HoaDon ctHoaDon = new CT_HoaDon(maHoaDon, maDichVu, soLuongDat, soLuongDat, donViTinh);
				dsCTHoaDon.add(ctHoaDon);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsCTHoaDon;
	}
	
	/**
	 * Phương thức addCTHoaDon(CT_HoaDon cthd) thêm một chi tiết hóa đơn vào cơ sở dữ liệu.
	 *
	 * @param cthd Chi tiết hóa đơn cần thêm
	 */
	public void addCTHoaDon(CT_HoaDon cthd) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
				statement = con.prepareStatement("insert into CT_HoaDon values(?,?,?,?,?)");
				statement.setString(1, cthd.getHoaDon().getMaHoaDon());
				statement.setString(2, cthd.getDichVu().getMaDichVu());
				statement.setInt(3, cthd.getSoLuongDat());
				statement.setDouble(4, cthd.getDonGia());
				statement.setString(5, cthd.getDonViTinh());
				statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
	}
	
	/**
	 * Phương thức updateCTHoaDon(CT_HoaDon cthd) cập nhật thông tin chi tiết hóa đơn trong cơ sở dữ liệu.
	 *
	 * @param cthd Chi tiết hóa đơn cần cập nhật
	 */
	public void updateCTHoaDon(CT_HoaDon cthd) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
				statement = con.prepareStatement("update CT_HoaDon set soLuongDat=? where maHoaDon=? and maDichVu=?");
				statement.setInt(1, cthd.getSoLuongDat());
				statement.setString(2, cthd.getHoaDon().getMaHoaDon());
				statement.setString(3, cthd.getDichVu().getMaDichVu());
				
				statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
	}
	
	/**
	 * Phương thức deleteCTHD(String maHD, String maDV) xóa một chi tiết hóa đơn khỏi cơ sở dữ liệu dựa trên mã hóa đơn và mã dịch vụ.
	 *
	 * @param maHD Mã hóa đơn
	 * @param maDV Mã dịch vụ
	 */
	public void deleteCTHD(String maHD, String maDV) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		String sql = "delete from CT_HoaDon where maHoaDon=? and maDichVu=?";
		try {
				statement = con.prepareStatement(sql);
				statement.setString(1, maHD);
				statement.setString(2, maDV);
				statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
	}
	
	/**
	 * Phương thức getDanhSachMaKhachHang() trả về danh sách mã khách hàng từ cơ sở dữ liệu.
	 *
	 * @return Danh sách mã khách hàng
	 */
	public List<String> getDanhSachMaKhachHang(){
		List<String> dsMa = new ArrayList<>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
	        }
			String sql = "SELECT DISTINCT maKhachHang FROM HoaDon";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				dsMa.add(rs.getString(1));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsMa;
	}
	
	public int getSoLanXuatHien(String maKH){
		int soLanXuatHien = 0;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
	        }
			String sql = "SELECT COUNT(maKhachHang) AS SoLanXuatHien FROM HoaDon WHERE maKhachHang='"+ maKH + "'";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			if (rs.next()) {
	            soLanXuatHien = rs.getInt(1);
	        }

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return soLanXuatHien;
	}
	
	/**
	 * Phương thức getSoLanXuatHien(String maKH) trả về số lần xuất hiện của một khách hàng dựa trên mã khách hàng.
	 *
	 * @param maKH Mã khách hàng
	 * @return Số lần xuất hiện của khách hàng
	 */
	public String[][] getChiTietTungLanXuatHien(String maKH,int soLan){
		String[][] keyValueArray = new String[soLan][2];
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
	        }
			String sql = "SELECT HD.ngayLapHD, \r\n"
					+ "       COALESCE(SUM(CT.soLuongDat * CT.donGia), 0) + HD.giaPhong AS khachHangChiTra\r\n"
					+ "FROM HoaDon HD\r\n"
					+ "LEFT JOIN CT_HoaDon CT ON HD.maHoaDon = CT.maHoaDon\r\n"
					+ "WHERE HD.maKhachHang = '" + maKH + "'"
					+ "GROUP BY HD.maHoaDon, HD.ngayLapHD, HD.giaPhong\r\n"
					+ "ORDER BY HD.ngayLapHD ASC;\r\n"
					+ "";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			int i = 0;
			while(rs.next()) {
				keyValueArray[i][0] = String.valueOf(rs.getDate(1));
	            keyValueArray[i][1] = String.valueOf(rs.getDouble(2));
	            i++;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return keyValueArray;
	}
	
	public String getMaKhachHangChiLonNhat(){
		String maKHChiLonNhat = "";
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
	        }
			String sql = "USE KaraokeNice\r\n"
					+ "SELECT TOP 1 HD.maKhachHang, SUM(subquery.khachHangChiTra) AS tongTienChiTra\r\n"
					+ "FROM HoaDon HD\r\n"
					+ "LEFT JOIN (\r\n"
					+ "    SELECT HD.maHoaDon, COALESCE(SUM(CT.soLuongDat * CT.donGia), 0) + HD.giaPhong AS khachHangChiTra\r\n"
					+ "    FROM HoaDon HD\r\n"
					+ "    LEFT JOIN CT_HoaDon CT ON HD.maHoaDon = CT.maHoaDon\r\n"
					+ "    GROUP BY HD.maHoaDon, HD.ngayLapHD, HD.giaPhong\r\n"
					+ ") AS subquery ON HD.maHoaDon = subquery.maHoaDon\r\n"
					+ "WHERE HD.maKhachHang IS NOT NULL\r\n"
					+ "GROUP BY HD.maKhachHang\r\n"
					+ "ORDER BY tongTienChiTra DESC;\r\n"
					+ "\r\n"
					+ "";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			if (rs.next()) {
				maKHChiLonNhat = rs.getString(1);
	        }
	        

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return maKHChiLonNhat;
	}
	public String getMaKhachHangChiNhoNhat(){
		String maKHChiNhoNhat = "";
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
	        }
			String sql = "USE KaraokeNice\r\n"
					+ "SELECT TOP 1 HD.maKhachHang, SUM(subquery.khachHangChiTra) AS tongTienChiTra\r\n"
					+ "FROM HoaDon HD\r\n"
					+ "LEFT JOIN (\r\n"
					+ "    SELECT HD.maHoaDon, COALESCE(SUM(CT.soLuongDat * CT.donGia), 0) + HD.giaPhong AS khachHangChiTra\r\n"
					+ "    FROM HoaDon HD\r\n"
					+ "    LEFT JOIN CT_HoaDon CT ON HD.maHoaDon = CT.maHoaDon\r\n"
					+ "    GROUP BY HD.maHoaDon, HD.ngayLapHD, HD.giaPhong\r\n"
					+ ") AS subquery ON HD.maHoaDon = subquery.maHoaDon\r\n"
					+ "WHERE HD.maKhachHang IS NOT NULL\r\n"
					+ "GROUP BY HD.maKhachHang\r\n"
					+ "ORDER BY tongTienChiTra ASC;\r\n"
					+ "\r\n"
					+ "";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			if (rs.next()) {
				maKHChiNhoNhat = rs.getString(1);
	        }
	        

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return maKHChiNhoNhat;
	}
	public String getMaKhachHangChiTrungBinh(){
		String maKHChiTrungBinh = "";
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
	        }
			String sql = "WITH RankedResults AS (\r\n"
					+ "    SELECT \r\n"
					+ "        HD.maKhachHang, \r\n"
					+ "        SUM(subquery.khachHangChiTra) AS tongTienChiTra,\r\n"
					+ "        ROW_NUMBER() OVER (ORDER BY SUM(subquery.khachHangChiTra) DESC) AS RowNum\r\n"
					+ "    FROM HoaDon HD\r\n"
					+ "    LEFT JOIN (\r\n"
					+ "        SELECT \r\n"
					+ "            HD.maHoaDon, \r\n"
					+ "            COALESCE(SUM(CT.soLuongDat * CT.donGia), 0) + HD.giaPhong AS khachHangChiTra\r\n"
					+ "        FROM HoaDon HD\r\n"
					+ "        LEFT JOIN CT_HoaDon CT ON HD.maHoaDon = CT.maHoaDon\r\n"
					+ "        GROUP BY HD.maHoaDon, HD.ngayLapHD, HD.giaPhong\r\n"
					+ "    ) AS subquery ON HD.maHoaDon = subquery.maHoaDon\r\n"
					+ "    WHERE HD.maKhachHang IS NOT NULL\r\n"
					+ "    GROUP BY HD.maKhachHang\r\n"
					+ ")\r\n"
					+ "SELECT maKhachHang, tongTienChiTra\r\n"
					+ "FROM RankedResults\r\n"
					+ "WHERE RowNum = (SELECT CEILING(COUNT(*) / 2.0) FROM RankedResults);\r\n"
					+ "";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			if (rs.next()) {
				maKHChiTrungBinh = rs.getString(1);
	        }
	        

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return maKHChiTrungBinh;
	}
	
	public ArrayList<CT_HoaDon> getCTHDTheoNgay(Date ngay) {
		
		ArrayList<CT_HoaDon> dsCTHD = new ArrayList<CT_HoaDon>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
		
			String sql = "SELECT CT_HoaDon.maHoaDon, CT_HoaDon.maDichVu, CT_HoaDon.soLuongDat, CT_HoaDon.donGia, CT_HoaDon.donViTinh"+"CT_HoaDon.tenDichVu FROM CT_HoaDon INNER JOIN KhachHang ON CT_HoaDon.tenDichVu = DichVu.tenDichVu ";
			statement=con.prepareStatement(sql);
			statement.setString(1, "%"+ngay+"%");
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()){
				CT_HoaDon cthd = new CT_HoaDon(
		                new HoaDon(rs.getString("maHoaDon")),
		                new DichVu(rs.getString("maDichVu")),
		                rs.getInt("soLuongDat"),
		                rs.getDouble("donGia"),
		                rs.getString("donViTinh"));
						new DichVu(rs.getString("tenDichVu"));
				dsCTHD.add(cthd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
		return dsCTHD;
	}

	public int getTongSoLuongDV(String maDichVu) {
	    int TONG = 0;
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
        }
        String sql = "SELECT COUNT(soLuongDat) AS TONG FROM CT_HoaDon WHERE maDichVu=?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, maDichVu);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            TONG = rs.getInt("TONG");
        }
        rs.close();
        statement.close();
        con.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return TONG;
	}
}
