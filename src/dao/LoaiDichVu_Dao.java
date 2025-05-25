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
import entity.LoaiDichVu;
import entity.LoaiPhong;
import entity.NhanVien;

public class LoaiDichVu_Dao {
	private List<LoaiDichVu_Dao> dsLoaiDichVu;

	public List<LoaiDichVu_Dao> getDsLoaiDichVu() {
		return dsLoaiDichVu;
	}
	
	public String getMaLoaiDichVu() {
		String maLoaiDV="";
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select CONCAT('LDV', RIGHT(CONCAT('000',ISNULL(right(max(maLoaiDV),3),0) + 1),3)) from [dbo].[LoaiDichVu] where maLoaiDV like 'LDV%'";
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				maLoaiDV = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return maLoaiDV;
	}
	
	public ArrayList<LoaiDichVu> loadLoaiDichVuFromDatabase() {
	    ArrayList<LoaiDichVu> dsLoaiDichVu = new ArrayList<>();
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "SELECT * FROM LoaiDichVu";
	        Statement statement = con.createStatement();
	        ResultSet rs = statement.executeQuery(sql);
	        while (rs.next()) {
	            String maLoaiDV = rs.getString(1);
	            String tenDV = rs.getString(2);

	            LoaiDichVu loaiDV = new LoaiDichVu(maLoaiDV, tenDV);
	            dsLoaiDichVu.add(loaiDV);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return dsLoaiDichVu;
	}
	
	public boolean addLoaiDV(LoaiDichVu loaiDV) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("insert into LoaiDichVu values (?, ?)");
			stmt.setString(1, loaiDV.getMaLoaiDV());
			stmt.setString(2, loaiDV.getTenLoaiDV());
			
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
	
	public List<LoaiDichVu> getLocTheoMa(String maLoaiDV) {
        List<LoaiDichVu> dsLoaiDichVu = new ArrayList<>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        try {
            String sql = "SELECT * FROM LoaiDichVu WHERE maLoaiDV = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, maLoaiDV);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String tenLoaiDV = rs.getString("tenLoaiDV");
                
                
                LoaiDichVu loaiDV = new LoaiDichVu(maLoaiDV, tenLoaiDV);
                dsLoaiDichVu.add(loaiDV);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsLoaiDichVu;
    }
	
	public List<LoaiDichVu> getLocTheoTen(String tenLoaiDV) {
        List<LoaiDichVu> dsLoaiDichVu = new ArrayList<>();
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        try {
            String sql = "SELECT * FROM LoaiDichVu WHERE tenLoaiDV = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, tenLoaiDV);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String maLoaiDV = rs.getString("maLoaiDV");
                
                
                LoaiDichVu loaiDV = new LoaiDichVu(maLoaiDV, tenLoaiDV);
                dsLoaiDichVu.add(loaiDV);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsLoaiDichVu;
    }
	
    public boolean updateLoaiDV(LoaiDichVu loaiDV, String maLoaiDV) {
	    Connection con = ConnectDB.getInstance().getConnection();
	    PreparedStatement stmt = null;
	    int n = 0;
	    try {
	        stmt = con.prepareStatement("UPDATE LoaiDichVu SET tenLoaiDV=? WHERE maLoaiDV=?");
	        stmt.setString(1, loaiDV.getTenLoaiDV());
	        stmt.setString(2, maLoaiDV);
	        n = stmt.executeUpdate();
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	   return n > 0;
	}
    
    public ArrayList<LoaiDichVu> getAllLoaiDichVu() {
		ArrayList<LoaiDichVu> dsLoaiDichVu = new ArrayList<LoaiDichVu>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from LoaiDichVu";
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				String maLoaiDV = rs.getString(1);
				String tenLoaiDV = rs.getString(2);
				
				LoaiDichVu ldv = new LoaiDichVu(maLoaiDV, tenLoaiDV);
				dsLoaiDichVu.add(ldv);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsLoaiDichVu;
	}
    
    public LoaiDichVu getLoaiDichVuTheoMa(String ma) {
        LoaiDichVu ldv = null;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            con = ConnectDB.getInstance().getConnection();
            String sql = "SELECT * FROM LoaiDichVu WHERE maLoaiDV = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, ma);
            rs = stmt.executeQuery();

            if (rs.next()) {
                ldv = new LoaiDichVu();
                ldv.setMaLoaiDV(rs.getString("maLoaiDV"));
                ldv.setTenLoaiDV(rs.getString("tenLoaiDV"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ldv;
    }
    
    public String getMaLoaiDVTheoTen(String tenDV) {
		String maDV = "";
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select maLoaiDV from LoaiDichVu where tenLoaiDV = N'" + tenDV +"'";

		try {
			if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
	        }
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				maDV = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return maDV;

	}
    
    
}
