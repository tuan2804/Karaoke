package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import connectDB.ConnectDB;
import entity.TaiKhoan;

public class TaiKhoan_Dao{
	private List<TaiKhoan> dsTaiKhoan;

	public List<TaiKhoan> getDsTaiKhoan() {
		return dsTaiKhoan;
	}

	public TaiKhoan_Dao() {
		super();
	}
	
	/**
	 * Phương thức addTaiKhoan(TaiKhoan tk) thêm một tài khoản vào cơ sở dữ liệu.
	 *
	 * @param tk Tài khoản cần thêm
	 * @return True nếu thêm thành công, False nếu thất bại
	 * @throws SQLException Nếu xảy ra lỗi khi thao tác với cơ sở dữ liệu
	 */
	public boolean addTaiKhoan(TaiKhoan tk) throws SQLException {
	    Connection con = null;
	    PreparedStatement stmt = null;
	    int n = 0;
	    try {
	        con = ConnectDB.getInstance().getConnection();
	        if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
	        }
	        stmt = con.prepareStatement("INSERT INTO TaiKhoan (taiKhoan, matKhau) VALUES (?, ?)");
	        stmt.setString(1, tk.getTaiKhoan());
	        stmt.setString(2, tk.getMatKhau());

	        n = stmt.executeUpdate();
	        con.commit(); // Commit dữ liệu vào cơ sở dữ liệu
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (stmt != null) {
	                stmt.close();
	            }
	            if (con != null) {
	                con.close();
	            }
	        } catch (SQLException e2) {
	            e2.printStackTrace();
	        }
	    }
	    return n > 0;
	}
	
	/**
	 * Phương thức suaTK(TaiKhoan tk) cập nhật thông tin mật khẩu của một tài khoản.
	 *
	 * @param tk Tài khoản cần cập nhật
	 * @return True nếu cập nhật thành công, False nếu thất bại
	 */
	public boolean suaTK(TaiKhoan tk) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n=0;
		try {
			if (con.isClosed()) {
		            con = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
		    }
			stmt = con.prepareStatement("update TaiKhoan set matKhau = ? where taiKhoan = ?");
			stmt.setString(2, tk.getTaiKhoan());
			stmt.setString(1, tk.getMatKhau());
			n = stmt.executeUpdate();
			} catch (SQLException e) {
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
		return n>0;
	}
	
	/**
	 * Phương thức getDanhSachTK() trả về danh sách các tài khoản.
	 *
	 * @return Danh sách các tài khoản
	 */
	public ArrayList<TaiKhoan> getDanhSachTK(){
		ArrayList<TaiKhoan> lstTK=new ArrayList<TaiKhoan>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("select * from TaiKhoan");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				TaiKhoan tk=new TaiKhoan();
				tk.setTaiKhoan(rs.getString(1));
				tk.setMatKhau(rs.getString(2));
				lstTK.add(tk);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lstTK;
	}

	/**
	 * Phương thức getTaiKhoanTheoMa(String maTK) trả về thông tin của một tài khoản dựa trên mã tài khoản.
	 *
	 * @param maTK Mã tài khoản
	 * @return Thông tin của tài khoản
	 */
	public TaiKhoan getTaiKhoanTheoMa(String maTK) {
	    TaiKhoan tk = new TaiKhoan();
	    ConnectDB.getInstance();
	    Connection con = ConnectDB.getConnection();

	    if (con != null) {
	        try {
	        	if (con.isClosed()) {
	 	            con = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
	 	        }
	            String sql = "SELECT * FROM TaiKhoan WHERE taiKhoan = '" + maTK + "'";
	            Statement stm = con.createStatement();
	            ResultSet rs = stm.executeQuery(sql);
	            while (rs.next()) {
	                tk.setTaiKhoan(rs.getString(1));
	                tk.setMatKhau(rs.getString(2));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                con.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    } else {
	        
	    }

	    return tk;
	}
	
	/**
	 * Phương thức getMatKhauTheoMaNV(String ma) trả về thông tin mật khẩu của một nhân viên dựa trên mã nhân viên.
	 *
	 * @param ma Mã nhân viên
	 * @return Thông tin mật khẩu của nhân viên
	 */
	public TaiKhoan getMatKhauTheoMaNV(String ma) {
		TaiKhoan tk=new TaiKhoan();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from TaiKhoan where maTK = '"+ma+"'"; 
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				tk.setTaiKhoan(rs.getString(1));
				tk.setMatKhau(rs.getString(2));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return tk;
	}
	
}
