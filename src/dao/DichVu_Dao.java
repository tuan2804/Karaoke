package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.DichVu;
import entity.LoaiDichVu;
import entity.LoaiPhong;
import entity.Phong;

public class DichVu_Dao {
	private List<DichVu> dsDichVu;

	public List<DichVu> getDsDichVu() {
		return dsDichVu;
	}

	public String getMaDichVu() {
		String maDV="";
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select CONCAT('DV', RIGHT(CONCAT('000',ISNULL(right(max(maDichVu),3),0) + 1),3)) from [dbo].[DichVu] where maDichVu like 'DV%'";
		try {
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
	
	public List<DichVu> loadDSDichVuFromDatabase(){
		dsDichVu = new ArrayList<DichVu>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			if (con.isClosed()) {
		            con = ConnectDB.getInstance().reconnect();
		    }
			String sql = "Select * from DichVu";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				String maDichVu = rs.getString(1);
				String tenDichVu = rs.getString(2);
				Double giaBan = rs.getDouble(3);
				String donViTinh = rs.getString(4);
				int soLuongTon = rs.getInt(5);
				LoaiDichVu loaiDichVu = new LoaiDichVu(rs.getString(6));
				DichVu dv = new DichVu(maDichVu, tenDichVu, giaBan, donViTinh, soLuongTon, loaiDichVu);
				dsDichVu.add(dv);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsDichVu;
	}
	
	public boolean addDichVu(DichVu dichVu) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("insert into DichVu values(?,?,?,?,?,?)");
			stmt.setString(1, dichVu.getMaDichVu());
			stmt.setString(2, dichVu.getTenDichVu());
			stmt.setDouble(3, dichVu.getGiaBan());
			stmt.setString(4, dichVu.getDonViTinh());
			stmt.setInt(5, dichVu.getSoLuongTon());
			stmt.setString(6, dichVu.getLoaiDV().getMaLoaiDV());
			n = stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return n > 0;
	}
	
	/**
	 * 
	 * @param dv Sửa thông tin dịch vụ theo maDV
	 */
	public boolean updateDichVu(DichVu dv, String maDV) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n=0;
		try { 
			stmt = con.prepareStatement("update DichVu set tenDichVu = ?, giaBan = ?, donViTinh = ?, soLuongTon = ?, maLoaiDV = ? where maDichVu = ?");

			stmt.setString(1, dv.getTenDichVu());
			stmt.setDouble(2, dv.getGiaBan());
			stmt.setString(3, dv.getDonViTinh());
			stmt.setInt(4, dv.getSoLuongTon());
			stmt.setString(5, dv.getLoaiDV().getMaLoaiDV());
			stmt.setString(6, maDV);
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return n>0;
	}
	
	/**
	 * 
	 * @param maDichVu lọc dịch vụ theo mã dịch vụ
	 */
	  public List<DichVu> getLocMaDichVu(String maDichVu) {
	        List<DichVu> dsDichVu = new ArrayList<>();
	        ConnectDB.getInstance();
	        Connection con = ConnectDB.getConnection();
	        try {
	            String sql = "SELECT * FROM DichVu WHERE maDichVu = ?";
	            PreparedStatement statement = con.prepareStatement(sql);
	            statement.setString(1, maDichVu);
	            ResultSet rs = statement.executeQuery();
	            while (rs.next()) {
	                String tenDichVu = rs.getString("tenDichVu");
	                Double giaBan = rs.getDouble("giaBan");
	                String donViTinh = rs.getString("donViTinh");
	                int soLuongTon = rs.getInt("soLuongTon");
	                LoaiDichVu ldv = new LoaiDichVu(rs.getString("maLoaiDV"));
	
	                DichVu dv = new DichVu(maDichVu, tenDichVu, giaBan, donViTinh, soLuongTon, ldv);
	                dsDichVu.add(dv);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return dsDichVu;
	    }
	  
	 /**
	 * 
	 * @param tenDichVu lọc dịch vụ theo tên dịch vụ
	 */
	  public List<DichVu> getLocTenDichVu(String tenDichVu) {
	        List<DichVu> dsDichVu = new ArrayList<>();
	        ConnectDB.getInstance();
	        Connection con = ConnectDB.getConnection();
	        try {
	            String sql = "SELECT * FROM DichVu WHERE tenDichVu = ?";
	            PreparedStatement statement = con.prepareStatement(sql);
	            statement.setString(1, tenDichVu);
	            ResultSet rs = statement.executeQuery();
	            while (rs.next()) {
	                String maDichVu = rs.getString("maDichVu");
	                Double giaBan = rs.getDouble("giaBan");
	                String donViTinh = rs.getString("donViTinh");
	                int soLuongTon = rs.getInt("soLuongTon");
	                LoaiDichVu ldv = new LoaiDichVu(rs.getString("maLoaiDV"));
	
	                DichVu dv = new DichVu(maDichVu, tenDichVu, giaBan, donViTinh, soLuongTon, ldv);
	                dsDichVu.add(dv);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return dsDichVu;
	 }
	  
	  
	  /**
		 * 
		 * @param maLoaiDV lọc loại dịch vụ theo mã loại dịch vụ
		 */
	  public ArrayList<DichVu> getDichVuTheoLoai(String maLoaiDV) {
		    ArrayList<DichVu> dsDichVu = new ArrayList<>();
		    Connection con = null;
		    PreparedStatement ps = null;
		    ResultSet rs = null;

		    try {
		        con = ConnectDB.getInstance().getConnection();
		        String sql = "SELECT * FROM DichVu WHERE maLoaiDV = ?";
		        ps = con.prepareStatement(sql);
		        ps.setString(1, maLoaiDV);
		        rs = ps.executeQuery();

		        while (rs.next()) {
		            DichVu dv = new DichVu();
		            dv.setMaDichVu(rs.getString(1));
		            dv.setTenDichVu(rs.getString(2));
		            dv.setGiaBan(rs.getDouble(3));
		            dv.setDonViTinh(rs.getString(4));
		            dv.setSoLuongTon(rs.getInt(5));
		            dv.setLoaiDV(new LoaiDichVu(rs.getString(6)));
		            dsDichVu.add(dv);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return dsDichVu;
		}
	  
	  public int getSLTheoTen(String ten) {
			int sl = 0;
			String sql = "SELECT soLuongTon FROM DichVu where tenDichVu = N'"+ten+"'";
			PreparedStatement statement = null;
			try {
				statement = ConnectDB.getConnection().prepareStatement(sql);
				ResultSet r = statement.executeQuery();
				r.next();
				sl = r.getInt(1);
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
			return sl;
		}
	  
	  public String getMaTheoTen(String ten) {
			String ma = "";
			String sql = "SELECT maDichVu FROM DichVu where tenDichVu = N'"+ten+"'";
			Connection con = null;
			PreparedStatement statement = null;
			try {
				con = ConnectDB.getInstance().getConnection();
		        if (con.isClosed()) {
		            con = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
		        }
				statement = con.prepareStatement(sql);
				ResultSet r = statement.executeQuery();
				r.next();
				ma = r.getString(1);
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
			return ma;
		}
	  
	  public double getGiaTheoTen(String ten) {
			double gia = 0;
			String sql = "SELECT giaBan FROM DichVu where tenDichVu = N'"+ten+"'";
			PreparedStatement statement = null;
			try {
				statement = ConnectDB.getConnection().prepareStatement(sql);
				ResultSet r = statement.executeQuery();
				r.next();
				gia = r.getDouble(1);
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
			return gia;
		}
	  
	  public DichVu getDichVuTheoMa(String maDichVu) {
		  	DichVu dv = null;
		    Connection connection = ConnectDB.getConnection();
		    PreparedStatement statement = null;
		    
		    try {
		        String query = "SELECT maDichVu, tenDichVu, giaBan, donViTinh, soLuongTon, maLoaiDV FROM DichVu WHERE maDichVu = ?";
		        statement = connection.prepareStatement(query);
		        statement.setString(1, maDichVu);
		        
		        ResultSet resultSet = statement.executeQuery();
		        
		        if (resultSet.next()) {
		            String maDichVuResult = resultSet.getString("maDichVu");
		            String tenDichVu = resultSet.getString("tenDichVu");
		            double giaBan = resultSet.getDouble("giaBan");
		            String donViTinh = resultSet.getString("donViTinh");
		            int soLuongTon = resultSet.getInt("soLuongTon");
		            LoaiDichVu loaiDV = new LoaiDichVu(resultSet.getString("maLoaiDV"));
		            dv = new DichVu(maDichVuResult, tenDichVu, giaBan, donViTinh, soLuongTon, loaiDV);
		        }
		        
		        resultSet.close();
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
		    
		    return dv;
		}
	  
	  public  void updateSoLuong(int sl, String ma) {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			PreparedStatement statement = null;
			try {
					statement = con.prepareStatement("update DichVu set soLuongTon=? where maDichVu=?");
					statement.setInt(1, sl);
					statement.setString(2, ma);
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
	  
	  public String getDVTTheoTen(String ten) {
			String dvt = "";
			String sql = "SELECT donViTinh FROM DichVu where tenDichVu = N'"+ten+"'";
			PreparedStatement statement = null;
			try {
				statement = ConnectDB.getConnection().prepareStatement(sql);
				ResultSet r = statement.executeQuery();
				r.next();
				dvt = r.getString(1);
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
			return dvt;
		}
}
