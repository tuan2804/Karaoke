package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.PhieuDatPhong;
import entity.Phong;
import entity.KhachHang;
import entity.LoaiPhong;

public class PhieuDatPhong_Dao {
	private List<PhieuDatPhong> dsPhieuDatPhong;
	
	public PhieuDatPhong_Dao() {
	}

	public List<PhieuDatPhong> getDsPhieuDatPhong() {
		return dsPhieuDatPhong;
	}
    public int getDsPhieuDatPhongSize() {
        return dsPhieuDatPhong.size();
    }
	public List<PhieuDatPhong> loadDSPhieuDatPhongFromDatabase(){
	    dsPhieuDatPhong = new ArrayList<>();
	    try {
	        ConnectDB.getInstance();
	        Connection con = ConnectDB.getConnection();
	        if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
	        }
	        String sql = "SELECT PDP.*, KH.soDT FROM PhieuDatPhong PDP JOIN KhachHang KH ON PDP.maKhachHang = KH.maKhachHang";
	        Statement statement = con.createStatement();
	        ResultSet rs = statement.executeQuery(sql);
	        while (rs.next()) {
	            String maPDPhong = rs.getString(1);
	            String maKhachHang = rs.getString(2);
	            String maPhong = rs.getString(3);
	            Date ngayDat = rs.getDate(4);
	            Time gioDat = rs.getTime(5);
	            String sdtKhachHang = rs.getString("soDT"); // Lấy thông tin số điện thoại từ cột "soDT" trong bảng KhachHang
	            KhachHang khachHang = new KhachHang(maKhachHang);
	            Phong phong = new Phong(maPhong);
	            PhieuDatPhong pdp = new PhieuDatPhong(maPDPhong, khachHang, phong, ngayDat, gioDat);
	            pdp.setSdtKhachHang(sdtKhachHang);
	            dsPhieuDatPhong.add(pdp);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return dsPhieuDatPhong;
	}
	
	public boolean addPhieuDatPhong(PhieuDatPhong phieuDatPhong) {
	    Connection con = ConnectDB.getInstance().getConnection();
	    PreparedStatement stmt = null;
	    int n = 0;
	    try {
	        if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
	        }
	        stmt = con.prepareStatement("insert into PhieuDatPhong (maPhieuDatPhong, maKhachHang, maPhong, ngayDatPhong, gioDatPhong) values(?,?,?,?,?)");
	        stmt.setString(1, phieuDatPhong.getMaPhieuDatPhong());
	        stmt.setString(2, phieuDatPhong.getKhachhang().getMaKhachHang());
	        stmt.setString(3, phieuDatPhong.getPhong().getMaPhong());
	        stmt.setDate(4, phieuDatPhong.getNgayDatPhong());
	        stmt.setTime(5, phieuDatPhong.getGioDatPhong());
	        n = stmt.executeUpdate();
	    } catch (Exception e) {
	        // TODO: handle exception
	        e.printStackTrace();
	    }
	    return n > 0;
	}
	
	public void deletePhieuDatPhong(String maPDP) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		String sql = "delete from PhieuDatPhong where maPhieuDatPhong=?";
		try {
				statement = con.prepareStatement(sql);
				statement.setString(1, maPDP);
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
	public boolean updatePhieuDatPhong(Date date,Time time, String maPDP) {
	    ConnectDB.getInstance();
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    int n = 0;
	    try {
	        if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
	        }
	        stmt = con.prepareStatement("update PhieuDatPhong set ngayDatPhong = ?, gioDatPhong = ? where maPhieuDatPhong = ?");

	        stmt.setDate(1, date);
	        stmt.setTime(2, time);
	        stmt.setString(3, maPDP);
	        n = stmt.executeUpdate();
	    } catch (SQLException e) {
	        // TODO: handle exception
	        e.printStackTrace();
	    }
	    return n > 0;
	}
	public List<Object[]> getChiTietDanhSachCacNgayDaDuocDat(String maPhong) {
	    List<Object[]> keyValueList = new ArrayList<>();
	    try {
	        ConnectDB.getInstance();
	        Connection con = ConnectDB.getConnection();
	        if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect(); // Reconnect if closed
	        }
	        String sql = "SELECT pdp.ngayDatPhong, kh.hoTen\r\n"
	        		+ "FROM PhieuDatPhong pdp\r\n"
	        		+ "INNER JOIN KhachHang kh ON pdp.maKhachHang = kh.maKhachHang\r\n"
	        		+ "WHERE pdp.maPhong = '" + maPhong + "'\r\n"
	        		+ "ORDER BY pdp.ngayDatPhong ASC;\r\n"
	        		+ "";

	        Statement statement = con.createStatement();
	        ResultSet rs = statement.executeQuery(sql);
	        
	        while (rs.next()) {
	            Object[] data = {rs.getDate(1), rs.getString(2)};
	            keyValueList.add(data);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return keyValueList;
	}
}
	
