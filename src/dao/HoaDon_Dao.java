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
import entity.HoaDon;
import entity.KhachHang;
import entity.LoaiPhong;
import entity.NhanVien;
import entity.Phong;

public class HoaDon_Dao {
	private List<HoaDon> dsHoaDon;
	
	/**
	 * Phương thức getAllHoaDon() trả về danh sách tất cả các hóa đơn từ cơ sở dữ liệu.
	 *
	 * @return Danh sách hóa đơn
	 */
	public List<HoaDon> getAllHoaDon() {
	    String sql = "SELECT HoaDon.maHoaDon, HoaDon.ngayLapHD, HoaDon.gioVao, HoaDon.gioRa, HoaDon.tinhTrangHD, HoaDon.giaPhong, HoaDon.maNhanVien, NhanVien.tenNhanVien, HoaDon.maPhong, "
	    		+ "Phong.tenPhong, HoaDon.maKhachHang, KhachHang.hoTen, KhachHang.soDT FROM HoaDon INNER JOIN KhachHang ON HoaDon.maKhachHang = KhachHang.maKhachHang INNER JOIN NhanVien ON HoaDon.maNhanVien = NhanVien.maNhanVien INNER JOIN Phong ON HoaDon.maPhong = Phong.maPhong";
	    List<HoaDon> list = new ArrayList<HoaDon>();
	    try (Connection connection = ConnectDB.getConnection();
	         PreparedStatement statement = connection.prepareStatement(sql);
	         ResultSet rs = statement.executeQuery()) {
	        while (rs.next()) {
	            HoaDon hd = new HoaDon(
	                rs.getString("maHoaDon"),
	                rs.getDate("ngayLapHD"),
	                rs.getTime("gioVao"),
	                rs.getTime("gioRa"),
	                rs.getBoolean("tinhTrangHD"),
	                rs.getDouble("giaPhong"),
	                new NhanVien(rs.getString("maNhanVien")),
	                new Phong(rs.getString("maPhong")),
	                new KhachHang(rs.getString("maKhachHang")));
	            list.add(hd);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return list;
	}
	
	/**
	 * Phương thức addHoaDonKCoNVVaGR(HoaDon hd) thêm một hóa đơn mới vào cơ sở dữ liệu với thông tin hóa đơn, không có nhân viên và giờ ra được chỉ định sẵn.
	 *
	 * @param hd Hóa đơn cần thêm
	 */
	public void addHoaDonKCoNVVaGR(HoaDon hd) {
	    ConnectDB.getInstance();
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement statement = null;
	    try {
	        if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
	        }
	        statement = con.prepareStatement("INSERT INTO HoaDon (maHoaDon, ngayLapHD, gioVao, gioRa, tinhTrangHD, giaPhong, maNhanVien, maPhong, maKhachHang) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
	        statement.setString(1, hd.getMaHoaDon());
	        statement.setDate(2, hd.getNgayLapHD());
	        statement.setTime(3, hd.getGioVao());
	        statement.setTime(4, Time.valueOf("00:00:59")); // Gio ra = 00:00:59
	        statement.setBoolean(5, false); // Tinh trang HD = 0 (false)
	        statement.setDouble(6, 0); // Gia phong = 0
	        statement.setString(7, hd.getNhanVien().getMaNhanVien());
	        statement.setString(8, hd.getPhong().getMaPhong());
	        statement.setString(9, hd.getKhachHang().getMaKhachHang());
	        statement.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (statement != null) {
	                statement.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	/**
	 * Phương thức getMaHoaDon() trả về mã hóa đơn mới để thêm vào cơ sở dữ liệu.
	 *
	 * @return Mã hóa đơn mới
	 */
	public String getMaHoaDon() {
		String maHD="";
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select CONCAT('HD', RIGHT(CONCAT('000',ISNULL(right(max(maHoaDon),3),0) + 1),3)) from [dbo].[HoaDon] where maHoaDon like 'HD%'";
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				maHD = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return maHD;
	}
	
	/**
	 * Phương thức updateGioRa(Time gioRa, double tongTienPhong, String maHD) cập nhật giờ ra và tổng tiền phòng của hóa đơn.
	 *
	 * @param gioRa          Giờ ra
	 * @param tongTienPhong  Tổng tiền phòng
	 * @param maHD           Mã hóa đơn
	 */
	public void updateGioRa(Time gioRa, double tongTienPhong, String maHD) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
				statement = con.prepareStatement("update HoaDon set gioRa=?, giaPhong=? where maHoaDon=?");
				statement.setTime(1, gioRa);
				statement.setDouble(2, tongTienPhong);
				statement.setString(3, maHD);
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
	 * Phương thức getNVTheoHD(String ma) trả về thông tin nhân viên liên quan đến một hóa đơn dựa trên mã hóa đơn.
	 *
	 * @param ma Mã hóa đơn
	 * @return Nhân viên liên quan đến hóa đơn
	 */
	public NhanVien getNVTheoHD(String ma) {
	    NhanVien nv = null;
	    String sql = "SELECT HoaDon.maNhanVien, NhanVien.tenNhanVien FROM HoaDon INNER JOIN NhanVien ON HoaDon.maNhanVien = NhanVien.maNhanVien WHERE maHoaDon = ?";
	    try (Connection connection = ConnectDB.getConnection();
	         PreparedStatement statement = connection.prepareStatement(sql)) {
	        statement.setString(1, ma);
	        try (ResultSet r = statement.executeQuery()) {
	            if (r.next()) {
	                nv = new NhanVien(r.getString(1), r.getString(2));
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return nv;
	}
	
	/**
	 * Phương thức getNgayLapHD(String ma) trả về ngày lập hóa đơn dựa trên mã hóa đơn.
	 *
	 * @param ma Mã hóa đơn
	 * @return Ngày lập hóa đơn
	 */
	public Date getNgayLapHD(String ma) {
	    Date ngay = null;
	    String sql = "SELECT ngayLapHD FROM HoaDon WHERE maHoaDon = ?";
	    try (Connection connection = ConnectDB.getConnection();
	         PreparedStatement statement = connection.prepareStatement(sql)) {
	        statement.setString(1, ma);
	        try (ResultSet r = statement.executeQuery()) {
	            if (r.next()) {
	                ngay = r.getDate("ngayLapHD");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return ngay;
	}
	
	/**
	 * Phương thức getKHTheoHD(String ma) trả về thông tin khách hàng liên quan đến một hóa đơn dựa trên mã hóa đơn.
	 *
	 * @param ma Mã hóa đơn
	 * @return Khách hàng liên quan đến hóa đơn
	 */
	public KhachHang getKHTheoHD(String ma) {
	    KhachHang kh = null;
	    String sql = "SELECT HoaDon.maKhachHang, KhachHang.hoTen FROM HoaDon INNER JOIN KhachHang ON HoaDon.maKhachHang = KhachHang.maKhachHang WHERE maHoaDon = ?";
	    Connection connection = null;
	    PreparedStatement statement = null;
	    try {
	        connection = ConnectDB.getConnection();
	        if (connection != null && !connection.isClosed()) {
	            statement = connection.prepareStatement(sql);
	            statement.setString(1, ma);
	            ResultSet r = statement.executeQuery();
	            if (r.next()) {
	                kh = new KhachHang(r.getString(1), r.getString(2)); 
	            }
	        } else {
	            // Kết nối đã đóng, thực hiện tái kết nối
	            connection = ConnectDB.getInstance().reconnect();
	            statement = connection.prepareStatement(sql);
	            statement.setString(1, ma);
	            ResultSet r = statement.executeQuery();
	            if (r.next()) {
	                kh = new KhachHang(r.getString(1), r.getString(2)); 
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (statement != null) {
	                statement.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return kh;
	}
	
	/**
	 * Phương thức getGioVao(String ma) trả về giờ vào của một hóa đơn dựa trên mã hóa đơn.
	 *
	 * @param ma Mã hóa đơn
	 * @return Giờ vào của hóa đơn
	 */
	public Time getGioVao(String ma) {
	    Time gio = null;
	    String sql = "SELECT gioVao FROM HoaDon WHERE maHoaDon = ?";
	    Connection connection = null;
	    PreparedStatement statement = null;
	    try {
	        connection = ConnectDB.getConnection();
	        if (connection != null && !connection.isClosed()) {
	            statement = connection.prepareStatement(sql);
	            statement.setString(1, ma);
	            ResultSet r = statement.executeQuery();
	            if (r.next()) {
	                gio = r.getTime(1);
	            }
	        } else {
	            // Kết nối đã đóng, thực hiện tái kết nối
	            connection = ConnectDB.getInstance().reconnect();
	            statement = connection.prepareStatement(sql);
	            statement.setString(1, ma);
	            ResultSet r = statement.executeQuery();
	            if (r.next()) {
	                gio = r.getTime(1);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (statement != null) {
	                statement.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return gio;
	}
	
	/**
	 * Phương thức getGioRa(String ma) trả về giờ ra của một hóa đơn dựa trên mã hóa đơn.
	 *
	 * @param ma Mã hóa đơn
	 * @return Giờ ra của hóa đơn
	 */
	public Time getGioRa(String ma) {
	    Time gio = null;
	    String sql = "SELECT gioRa FROM HoaDon WHERE maHoaDon = ?";
	    Connection connection = null;
	    PreparedStatement statement = null;
	    try {
	        connection = ConnectDB.getConnection();
	        if (connection != null && !connection.isClosed()) {
	            statement = connection.prepareStatement(sql);
	            statement.setString(1, ma);
	            ResultSet r = statement.executeQuery();
	            if (r.next()) {
	                gio = r.getTime(1);
	            }
	        } else {
	            // Kết nối đã đóng, thực hiện tái kết nối
	            connection = ConnectDB.getInstance().reconnect();
	            statement = connection.prepareStatement(sql);
	            statement.setString(1, ma);
	            ResultSet r = statement.executeQuery();
	            if (r.next()) {
	                gio = r.getTime(1);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (statement != null) {
	                statement.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return gio;
	}
	
	/**
	 * Phương thức getTienPhong(String ma) trả về số tiền phòng của một hóa đơn dựa trên mã hóa đơn.
	 *
	 * @param ma Mã hóa đơn
	 * @return Số tiền phòng của hóa đơn
	 */
	public double getTienPhong(String ma) {
	    double tienPhong = 0;
	    String sql = "SELECT giaPhong FROM HoaDon WHERE maHoaDon = ?";
	    Connection connection = null;
	    PreparedStatement statement = null;
	    try {
	        connection = ConnectDB.getConnection();
	        if (connection != null && !connection.isClosed()) {
	            statement = connection.prepareStatement(sql);
	            statement.setString(1, ma);
	            ResultSet r = statement.executeQuery();
	            if (r.next()) {
	                tienPhong = r.getDouble(1);
	            }
	        } else {
	            // Kết nối đã đóng, thực hiện tái kết nối
	            connection = ConnectDB.getInstance().reconnect();
	            statement = connection.prepareStatement(sql);
	            statement.setString(1, ma);
	            ResultSet r = statement.executeQuery();
	            if (r.next()) {
	                tienPhong = r.getDouble(1);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (statement != null) {
	                statement.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return tienPhong;
	}
	
	/**
	 * Phương thức updateTTHD(boolean tt, String maHD) cập nhật trạng thái của một hóa đơn dựa trên mã hóa đơn.
	 *
	 * @param tt    Trạng thái hóa đơn (true nếu hóa đơn đã thanh toán, false nếu chưa thanh toán)
	 * @param maHD  Mã hóa đơn
	 */
	public void updateTTHD(boolean tt, String maHD) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
				statement = con.prepareStatement("update HoaDon set tinhTrangHD=?  where maHoaDon=?");
				statement.setBoolean(1, tt);
				statement.setString(2, maHD);
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
	 * Phương thức getHDTheoNgay(Date ngay) trả về danh sách hóa đơn dựa trên ngày lập hóa đơn.
	 *
	 * @param ngay Ngày lập hóa đơn
	 * @return Danh sách hóa đơn trong ngày
	 */
	public ArrayList<HoaDon> getHDTheoNgay(Date ngay) {
		
		ArrayList<HoaDon> dsHD = new ArrayList<HoaDon>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
		
			String sql = "SELECT HoaDon.maHoaDon, HoaDon.ngayLapHD, HoaDon.gioVao, HoaDon.gioRa, HoaDon.tinhTrangHD, HoaDon.giaPhong, HoaDon.maNhanVien, NhanVien.tenNhanVien, HoaDon.maPhong, Phong.tenPhong, HoaDon.maKhachHang, KhachHang.hoTen, KhachHang.soDT FROM HoaDon INNER JOIN KhachHang ON HoaDon.maKhachHang = KhachHang.maKhachHang "
					+ "INNER JOIN NhanVien ON HoaDon.maNhanVien = NhanVien.maNhanVien INNER JOIN Phong ON HoaDon.maPhong = Phong.maPhong WHERE ngayLapHD like ? and HoaDon.tinhTrangHD=1";
			statement=con.prepareStatement(sql);
			statement.setString(1, "%"+ngay+"%");
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()){
				HoaDon hd = new HoaDon(
		                rs.getString("maHoaDon"),
		                rs.getDate("ngayLapHD"),
		                rs.getTime("gioVao"),
		                rs.getTime("gioRa"),
		                rs.getBoolean("tinhTrangHD"),
		                rs.getDouble("giaPhong"),
		                new NhanVien(rs.getString("maNhanVien")),
		                new Phong(rs.getString("maPhong")),
		                new KhachHang(rs.getString("maKhachHang")));
				dsHD.add(hd);
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
		return dsHD;
	}

	/**
	 * Phương thức getHDTheoThang(int mounth, int year, int day) trả về danh sách hóa đơn dựa trên tháng, năm và ngày lập hóa đơn.
	 *
	 * @param mounth Tháng lập hóa đơn
	 * @param year Năm lập hóa đơn
	 * @param day Ngày lập hóa đơn
	 * @return Danh sách hóa đơn trong tháng, năm và ngày lập hóa đơn
	 */
	public ArrayList<HoaDon> getHDTheoThang(int mounth, int year, int day) {
		ArrayList<HoaDon> dsHD = new ArrayList<HoaDon>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
		
			String sql = "SELECT HoaDon.maHoaDon, HoaDon.ngayLapHD, HoaDon.gioVao, HoaDon.gioRa, HoaDon.tinhTrangHD, HoaDon.giaPhong, HoaDon.maNhanVien, NhanVien.tenNhanVien, HoaDon.maPhong, Phong.tenPhong, HoaDon.maKhachHang, KhachHang.hoTen, KhachHang.soDT FROM HoaDon INNER JOIN KhachHang ON HoaDon.maKhachHang = KhachHang.maKhachHang INNER JOIN NhanVien ON HoaDon.maNhanVien = NhanVien.maNhanVien INNER JOIN Phong ON HoaDon.maPhong = Phong.maPhong WHERE MONTH(ngayLapHD)='"+mounth+"' and YEAR(ngayLapHD)='"+year+"' and DAY(ngayLapHD)='"+day+"' and HoaDon.tinhTrangHD=1";
			statement=con.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()){
				HoaDon hd = new HoaDon(
		                rs.getString("maHoaDon"),
		                rs.getDate("ngayLapHD"),
		                rs.getTime("gioVao"),
		                rs.getTime("gioRa"),
		                rs.getBoolean("tinhTrangHD"),
		                rs.getDouble("giaPhong"),
		                new NhanVien(rs.getString("maNhanVien")),
		                new Phong(rs.getString("maPhong")),
		                new KhachHang(rs.getString("maKhachHang")));
				dsHD.add(hd);
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
		return dsHD;
	}
	
	/**
	 * Phương thức getLocNgayLapHD(Date tuNgay, Date denNgay) trả về danh sách hóa đơn trong khoảng thời gian từ ngày đến ngày.
	 *
	 * @param tuNgay Ngày bắt đầu
	 * @param denNgay Ngày kết thúc
	 * @return Danh sách hóa đơn trong khoảng thời gian
	 */
	public List<HoaDon> getLocNgayLapHD(Date tuNgay, Date denNgay) {
	    try {
	        Connection con = ConnectDB.getConnection();
	        String query = "SELECT * FROM HoaDon WHERE ngayLapHD >= ? AND ngayLapHD <= ?";
	        PreparedStatement stmt = con.prepareStatement(query);
	        stmt.setDate(1, tuNgay);
	        stmt.setDate(2, denNgay);
	        ResultSet rs = stmt.executeQuery();

	        List<HoaDon> hoaDonList = new ArrayList<>();

	        while (rs.next()) {
	        	HoaDon hd = new HoaDon(
		                rs.getString("maHoaDon"),
		                rs.getDate("ngayLapHD"),
		                rs.getTime("gioVao"),
		                rs.getTime("gioRa"),
		                rs.getBoolean("tinhTrangHD"),
		                rs.getDouble("giaPhong"),
		                new NhanVien(rs.getString("maNhanVien")),
		                new Phong(rs.getString("maPhong")),
		                new KhachHang(rs.getString("maKhachHang")));

	            hoaDonList.add(hd);
	        }

	        rs.close();
	        stmt.close();
	        con.close();

	        return hoaDonList;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	
}
