package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import connectDB.ConnectDB;
import entity.KhachHang;
import entity.LoaiDichVu;
import entity.LoaiPhong;
import entity.Phong;

public class LoaiPhong_Dao {
	private List<LoaiPhong> dsLoaiPhong;

	public LoaiPhong_Dao() {
        dsLoaiPhong = new ArrayList<>(); // Khởi tạo danh sách loại phòng trong constructor
    }
	
	public List<LoaiPhong> getDSLoaiPhong(){
		return dsLoaiPhong;
	}
	
	public String getMaLoaiPhong() {
		String maLoaiPhong="";
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select CONCAT('MLP', RIGHT(CONCAT('000',ISNULL(right(max(maLoaiP),3),0) + 1),3)) from [dbo].[LoaiPhong] where maLoaiP like 'MLP%'";
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				maLoaiPhong = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return maLoaiPhong;
	}
	
	// Phần code trong LoaiPhong_Dao
	public ArrayList<LoaiPhong> loadLoaiPhongFromDatabase() {
	    ArrayList<LoaiPhong> dsLoaiPhong = new ArrayList<>();
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "SELECT * FROM LoaiPhong";
	        Statement statement = con.createStatement();
	        ResultSet rs = statement.executeQuery(sql);
	        while (rs.next()) {
	            String maLoaiPhong = rs.getString("maLoaiP");
	            String tenLoaiPhong = rs.getString("tenLoaiP");
	            int sucChua = rs.getInt("sucChua");

	            LoaiPhong loaiPhong = new LoaiPhong(maLoaiPhong, tenLoaiPhong, sucChua);
	            dsLoaiPhong.add(loaiPhong);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return dsLoaiPhong;
	}
	
	public ArrayList<LoaiPhong> getAllLoaiPhong() {
		ArrayList<LoaiPhong> dsLoaiPhong = new ArrayList<LoaiPhong>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from LoaiPhong";
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				String maLoaiP = rs.getString(1);
				String tenLoaiP = rs.getString(2);
				int sucChua = rs.getInt(3);
				LoaiPhong loaiP = new LoaiPhong(maLoaiP, tenLoaiP, sucChua);
				dsLoaiPhong.add(loaiP);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsLoaiPhong;
	}

	public LoaiPhong getLoaiPhongTheoMa(String ma) {
		LoaiPhong lp = new LoaiPhong();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from LoaiPhong where maLoaiP = N'"+ma+"'";
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {

				lp.setMaLoaiP(rs.getNString(1));
				lp.setTenLoaiP(rs.getNString(2));
				lp.setSucChua(rs.getInt(3));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lp;
	}
	

	
	public String getMaLoaiPTheoTen(String tenP) {
		String maLoai = "";
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select maLoaiP from LoaiPhong where tenLoaiP = N'" + tenP +"'";

		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {

				maLoai = rs.getString(1);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return maLoai;

	}
	
	
	
	public boolean addLoaiPhong(String maLoaiPhong, String tenLoaiPhong, int sucChua) {
	    int n = 0;
	    LoaiPhong loaiPhong = new LoaiPhong(maLoaiPhong, tenLoaiPhong, sucChua);
	    if (!dsLoaiPhong.contains(loaiPhong)) {
	        Connection con = ConnectDB.getInstance().getConnection();
	        PreparedStatement st = null;
	        try {
	            st = con.prepareStatement("INSERT INTO LoaiPhong (maLoaiP, tenLoaiP, sucChua) VALUES (?, ?, ?)");
	            st.setString(1, maLoaiPhong);
	            st.setString(2, tenLoaiPhong);
	            st.setInt(3, sucChua);
	            n = st.executeUpdate();

	            dsLoaiPhong.add(loaiPhong);
	            System.out.println(n);
	            System.out.println("Insert success");
	        } catch (SQLException e) {
	            e.printStackTrace();
	            System.out.println("Insert error \n");
	        }
	    }
	    return n > 0;
	}
	
	public boolean deleteLoaiPhong(String maLP) throws SQLException {
		Connection con = ConnectDB.getConnection();
		String sql = "delete from Loaiphong where maLoaiP = ?";
		
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, maLP);
			return stmt.executeUpdate() > 0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		con.close();
		return false;
		
	}
	
	    
	    public boolean updateLoaiPhong(LoaiPhong lp, String maLoaiP) {
	        Connection con = ConnectDB.getInstance().getConnection();
	        PreparedStatement stmt = null;
	        int n = 0;
	        try {
	            stmt = con.prepareStatement("UPDATE LoaiPhong SET tenLoaiP = ?, sucChua = ? WHERE maLoaiP = ?");
	            stmt.setString(1, lp.getTenLoaiP());
	            stmt.setInt(2, lp.getSucChua());
	            stmt.setString(3, maLoaiP);
	            n = stmt.executeUpdate();
	           
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	       return n > 0;
	    }
	    
	    public List<LoaiPhong> getLoc(int sucChua) {
	        List<LoaiPhong> dsLoaiPhong = new ArrayList<>();
	        ConnectDB.getInstance();
	        Connection con = ConnectDB.getConnection();
	        try {
	            String sql = "SELECT * FROM LoaiPhong WHERE sucChua = ?";
	            PreparedStatement statement = con.prepareStatement(sql);
	            statement.setInt(1, sucChua);
	            ResultSet rs = statement.executeQuery();
	            while (rs.next()) {
	                String maLoaiPhong = rs.getString("maLoaiP");
	                String tenLoaiPhong = rs.getString("tenLoaiP");
	                int sucChuaPhong = rs.getInt("sucChua");

	                LoaiPhong loaiPhong = new LoaiPhong(maLoaiPhong, tenLoaiPhong, sucChuaPhong);
	                dsLoaiPhong.add(loaiPhong);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return dsLoaiPhong;
	    }
	   
	    
	    public List<LoaiPhong> getLoc(String tenLoaiPhong) {
	        List<LoaiPhong> dsLoaiPhong = new ArrayList<>();
	        ConnectDB.getInstance();
	        Connection con = ConnectDB.getConnection();
	        try {
	            String sql = "SELECT * FROM LoaiPhong WHERE tenLoaiP = ?";
	            PreparedStatement statement = con.prepareStatement(sql);
	            statement.setString(1, tenLoaiPhong);
	            ResultSet rs = statement.executeQuery();
	            while (rs.next()) {
	                String maLoaiPhong = rs.getString("maLoaiP");
	                int sucChua = rs.getInt("sucChua");

	                LoaiPhong loaiPhong = new LoaiPhong(maLoaiPhong, tenLoaiPhong, sucChua);
	                dsLoaiPhong.add(loaiPhong);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return dsLoaiPhong;
	    }
	    

		
}
