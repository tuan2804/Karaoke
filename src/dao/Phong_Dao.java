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
import entity.LoaiDichVu;
import entity.LoaiPhong;
import entity.Phong;

public class Phong_Dao {
	private List<Phong> dsPhong;
	
	public Phong_Dao() {
		dsPhong = new ArrayList<>();
	}


	public List<Phong> getDSPhong(){
		return dsPhong;
	}
	
	
	
	public Phong getPhongByIndex(int index) {
	    if (index >= 0 && index < dsPhong.size()) {
	        return dsPhong.get(index);
	    }
	    return null;
	}
	
	public String getMaPhong() {
		String maPhong="";
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select CONCAT('P', RIGHT(CONCAT('000',ISNULL(right(max(maPhong),3),0) + 1),3)) from [dbo].[Phong] where maPhong like 'P%'";
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				maPhong = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return maPhong;
	}
	
	/**
	 * 
	 * @param load danh sách khách phòng
	 */
	public List<Phong> loadDSPhongFromDatabase(){
		dsPhong = new ArrayList<Phong>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
	        }
			String sql = "Select * from Phong";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				String maPhong = rs.getString(1);
				String tenPhong = rs.getString(2);
				String tinhTrangPhong = rs.getString(3);
				Double giaPhong = rs.getDouble(4);
				LoaiPhong lp = new LoaiPhong(rs.getString(5)); 
				Phong p = new Phong(maPhong, tenPhong, tinhTrangPhong, giaPhong, lp);
				dsPhong.add(p);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsPhong;
	}
	
	/**
	 * 
	 * @param Lấy thông tin phòng
	 */
	public ArrayList<Phong> getRoomList() {
	    ArrayList<Phong> dataList = new ArrayList<>();
	    try {
	        ConnectDB.getInstance();
	        Connection con = ConnectDB.getConnection();
	        if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
	        }
	        String sql = "SELECT * FROM Phong";
	        Statement statement = con.createStatement();
	        ResultSet rs = statement.executeQuery(sql);
	        while (rs.next()) {
	            String maPhong = rs.getString(1);
	            String tenPhong = rs.getString(2);
	            String tinhTrangPhong = rs.getString(3);
	            Double giaPhong = rs.getDouble(4);
	            LoaiPhong lp = new LoaiPhong(rs.getString(5));
	            Phong p = new Phong(maPhong, tenPhong, tinhTrangPhong, giaPhong, lp);
	            dataList.add(p);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return dataList;
	}
	
	/**
	 * 
	 * @param Lấy danh sách phòng
	 */
	public ArrayList<Phong> getDanhSachPhong() {
		ArrayList< Phong> lsPhong = new ArrayList<Phong>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from Phong";

		try {
			if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
	        }
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				Phong p = new Phong();

				p.setMaPhong(rs.getNString(1));
				p.setTenPhong(rs.getNString(2));
				p.setTinhTrangPhong(rs.getNString(3));
				p.setLoaiPhong(new LoaiPhong(rs.getNString(4)));

				lsPhong.add(p);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lsPhong;
	}
	
	/**
	 * 
	 * @param phong Thêm phòng
	 */
	public boolean addPhong(Phong phong) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
	        }
			stmt = con.prepareStatement("insert into Phong values(?,?,?,?,?)");
			stmt.setString(1, phong.getMaPhong());
			stmt.setString(2, phong.getTenPhong());
			stmt.setString(3, phong.getTinhTrangPhong());
			stmt.setDouble(4, phong.getGiaPhong());
			stmt.setString(5, phong.getLoaiPhong().getMaLoaiP());
			n = stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return n > 0;
	}
	
	/**
	 * 
	 * @param p Sửa thông tin phòng theo maPhong
	 */
	public boolean updatePhong(Phong p, String maPhong) {
	    ConnectDB.getInstance();
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    int n = 0;
	    try {
	        if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
	        }
	        stmt = con.prepareStatement("update Phong set tenPhong = ?, tinhTrangPhong = ?, giaPhong = ?, loaiPhong = ? where maPhong = ?");

	        stmt.setString(1, p.getTenPhong());
	        stmt.setString(2, p.getTinhTrangPhong());
	        stmt.setDouble(3, p.getGiaPhong() != null ? p.getGiaPhong() : 0); // Sử dụng giá trị mặc định 0 nếu giá trị là null
	        stmt.setString(4, p.getLoaiPhong().getMaLoaiP()); // Sử dụng trường `tenLoaiP` thay vì `maLoaiP`
	        stmt.setString(5, maPhong);
	        n = stmt.executeUpdate();
	    } catch (SQLException e) {
	        // TODO: handle exception
	        e.printStackTrace();
	    }
	    return n > 0;
	}
	
	public  void updatePhong(Phong nv) {
		 ConnectDB.getInstance();
		 Connection con = ConnectDB.getConnection();
		 PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("update Phong set tenPhong=?, tinhTrangPhong=?, loaiPhong=? where maPhong=?");
			stmt.setString(1, nv.getTenPhong());
			stmt.setString(2, nv.getTinhTrangPhong());
			stmt.setString(3, nv.getLoaiPhong().getMaLoaiP());
			stmt.setString(4, nv.getMaPhong());
			stmt.executeUpdate(); 

		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
	}	
	
	/**
	 * 
	 * 
	 * 
	 * @param load danh sách phòng
	 */
	private void loadDanhSachPhong() {
	    Connection con = null;
	    PreparedStatement st = null;
	    ResultSet rs = null;

	    try {
	        con = ConnectDB.getInstance().getConnection();
	        if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
	        }
	        st = con.prepareStatement("SELECT * FROM Phong");
	        rs = st.executeQuery();

	        while (rs.next()) {
	            String maPhong = rs.getString("maPhong");
	            String tenPhong = rs.getString("tenPhong");
	            String tinhTrang = rs.getString("tinhTrang");
	            double giaPhong = rs.getDouble("giaPhong");
	            String maLoaiPhong = rs.getString("maLoaiPhong");
	            
	            // Lấy thông tin loại phòng từ mã loại phòng
	            String tenLoaiPhong = getTenLoaiPhongTheoMaLoaiPhong(maLoaiPhong);

	            // Tạo đối tượng Phong và thêm vào danh sách
	            LoaiPhong loaiPhong = new LoaiPhong(maLoaiPhong, tenLoaiPhong);
	            Phong phong = new Phong(maPhong, tenPhong, tinhTrang, giaPhong, loaiPhong);
	            dsPhong.add(phong);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        // Đảm bảo đóng ResultSet, PreparedStatement và Connection
	        if (rs != null) {
	            try {
	                rs.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (st != null) {
	            try {
	                st.close();
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
	}
	
	

	/**
	 * 
	 * @param maLoaiPhong lấy tên loại phòng theo mã loại phòng
	 */
	public String getTenLoaiPhongTheoMaLoaiPhong(String maLoaiPhong) {
	    Connection con = null;
	    PreparedStatement st = null;
	    ResultSet rs = null;
	    String tenLoaiPhong = null;

	    try{
	    	
	        con = ConnectDB.getInstance().getConnection();
	        if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
	        }
	        st = con.prepareStatement("SELECT tenLoaiP FROM LoaiPhong WHERE maLoaiP = ?");
	        st.setString(1, maLoaiPhong);
	        rs = st.executeQuery();

	        if (rs.next()) {
	            tenLoaiPhong = rs.getString("tenLoaiP");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        // Đảm bảo đóng ResultSet, PreparedStatement và Connection
	        if (rs != null) {
	            try {
	                rs.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (st != null) {
	            try {
	                st.close();
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

	    return tenLoaiPhong;
	}
	
	/**
	 * 
	 * @param maP xóa một phòng
	 */
	public boolean deletePhong(String maP) throws SQLException {
		Connection con = ConnectDB.getConnection();
		String sql = "delete from Phong where maPhong = ?";
		
		try {
			if (con.isClosed()) {
	            con = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
	        }
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, maP);
			return stmt.executeUpdate() > 0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		con.close();
		return false;
	}
	
	/**
	 * 
	 * @param maPhong lọc phòng theo mã phòng
	 */
	  public List<Phong> getLocMaPhong(String maPhong) {
	        List<Phong> dsPhong = new ArrayList<>();
	        ConnectDB.getInstance();
	        Connection con = ConnectDB.getConnection();
	        try {
	        	if (con.isClosed()) {
		            con = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
		        }
	            String sql = "SELECT * FROM Phong WHERE maPhong = ?";
	            PreparedStatement statement = con.prepareStatement(sql);
	            statement.setString(1, maPhong);
	            ResultSet rs = statement.executeQuery();
	            while (rs.next()) {
	                String tenPhong = rs.getString("tenPhong");
	                String tinhTrang = rs.getString("tinhTrangPhong");
	                Double giaPhong = rs.getDouble("giaPhong");
	                LoaiPhong loaiPhong = new LoaiPhong(rs.getString("loaiPhong"));
	
	                Phong phong = new Phong(maPhong, tenPhong, tinhTrang, giaPhong, loaiPhong);
	                dsPhong.add(phong);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return dsPhong;
	    }
	    
	  /**
		 * 
		 * @param tenPhong Lọc thông tin phòng theo tên phòng
		 */
	  public List<Phong> getLocTenPhong(String tenPhong) {
	        List<Phong> dsPhong = new ArrayList<>();
	        ConnectDB.getInstance();
	        Connection con = ConnectDB.getConnection();
	        try {
	        	if (con.isClosed()) {
		            con = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
		        }
	            String sql = "SELECT * FROM Phong WHERE tenPhong = ?";
	            PreparedStatement statement = con.prepareStatement(sql);
	            statement.setString(1, tenPhong);
	            ResultSet rs = statement.executeQuery();
	            while (rs.next()) {
	                String maPhong = rs.getString("maPhong");
	                String tinhTrang = rs.getString("tinhTrangPhong");
	                Double giaPhong = rs.getDouble("giaPhong");
	                LoaiPhong loaiPhong = new LoaiPhong(rs.getString("loaiPhong"));
	
	                Phong phong = new Phong(maPhong, tenPhong, tinhTrang, giaPhong, loaiPhong);
	                dsPhong.add(phong);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return dsPhong;
	    }
	  
	  /**
		 * 
		 * @param tinhTrang lọc thonong tin phòng theo tình trạng
		 */
	  public List<Phong> getLocTinhTrang(String tinhTrang) {
	        List<Phong> dsPhong = new ArrayList<>();
	        ConnectDB.getInstance();
	        Connection con = ConnectDB.getConnection();
	        try {
	        	if (con.isClosed()) {
		            con = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
		        }
	            String sql = "SELECT * FROM Phong WHERE tinhTrangPhong = ?";
	            PreparedStatement statement = con.prepareStatement(sql);
	            statement.setString(1, tinhTrang);
	            ResultSet rs = statement.executeQuery();
	            while (rs.next()) {
	                String maPhong = rs.getString("maPhong");
	                String tenPhong = rs.getString("tenPhong");
	                Double giaPhong = rs.getDouble("giaPhong");
	                LoaiPhong loaiPhong = new LoaiPhong(rs.getString("loaiPhong"));
	
	                Phong phong = new Phong(maPhong, tenPhong, tinhTrang, giaPhong, loaiPhong);
	                dsPhong.add(phong);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return dsPhong;
	    }
	  
	  /**
		 * 
		 * @param maLoaiP lọc loại phòng theo mã loại phòng
		 */
	  public ArrayList<Phong> getPhongTheoLoai(String maLoaiP) {
		    ArrayList<Phong> lsP = new ArrayList<>();
		    Connection con = null;
		    PreparedStatement ps = null;
		    ResultSet rs = null;

		    try {
		        con = ConnectDB.getInstance().getConnection();
		        if (con.isClosed()) {
		            con = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
		        }
		        String sql = "SELECT * FROM Phong WHERE loaiPhong = ?";
		        ps = con.prepareStatement(sql);
		        ps.setString(1, maLoaiP);
		        rs = ps.executeQuery();

		        while (rs.next()) {
		            Phong p = new Phong();
		            p.setMaPhong(rs.getString(1));
		            p.setTenPhong(rs.getString(2));
		            p.setTinhTrangPhong(rs.getString(3));
		            p.setGiaPhong(rs.getDouble(4));
		            p.setLoaiPhong(new LoaiPhong(rs.getString(5)));
		            lsP.add(p);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return lsP;
		}
	  
	  public Phong getPhongTheoMa(String maPhong) {
		    Phong phong = null;
		    Connection connection = ConnectDB.getConnection();
		    PreparedStatement statement = null;
		    
		    try {
		    	if (connection.isClosed()) {
		    		connection = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
		        }
		        String query = "SELECT maPhong, tenPhong, tinhTrangPhong, giaPhong, loaiPhong FROM Phong WHERE maPhong = ?";
		        statement = connection.prepareStatement(query);
		        statement.setString(1, maPhong);
		        
		        ResultSet resultSet = statement.executeQuery();
		        
		        if (resultSet.next()) {
		            String maPhongResult = resultSet.getString("maPhong");
		            String tenPhong = resultSet.getString("tenPhong");
		            String tinhTrangPhong = resultSet.getString("tinhTrangPhong");
		            double giaPhong = resultSet.getDouble("giaPhong");
		            LoaiPhong loaiPhong = new LoaiPhong(resultSet.getString("loaiPhong"));
		            
		            phong = new Phong(maPhongResult, tenPhong, tinhTrangPhong, giaPhong, loaiPhong);
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
		    
		    return phong;
		}
	  
	  public double getGiaTheoTenP(String ten) {
			double gia = 0;
			String sql = "SELECT giaPhong FROM Phong Where maPhong = N'"+ten+"'";
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
	  
	  public String getMaTheoTenP(String ten) {
		    String ma = "";
		    String sql = "SELECT maPhong FROM Phong WHERE tenPhong = ?";
		    Connection connection = null;
		    PreparedStatement statement = null;
		    try {
		        connection = ConnectDB.getConnection();
		        if (connection != null && !connection.isClosed()) {
		            statement = connection.prepareStatement(sql);
		            statement.setString(1, ten);
		            ResultSet r = statement.executeQuery();
		            if (r.next()) {
		                ma = r.getString(1);
		            }
		        } else {
		            connection = ConnectDB.getInstance().reconnect();
		            statement = connection.prepareStatement(sql);
		            statement.setString(1, ten);
		            ResultSet r = statement.executeQuery();
		            if (r.next()) {
		                ma = r.getString(1);
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
		    return ma;
		}
	  
	  
	  public void updateTTP(String tt, String tenP) {
		    Connection con = null;
		    PreparedStatement statement = null;
		    try {
		    	
		        con = ConnectDB.getConnection();
		        if (con.isClosed()) {
		            con = ConnectDB.getInstance().reconnect(); // Kết nối lại nếu đã đóng
		        }
		        statement = con.prepareStatement("UPDATE Phong SET tinhTrangPhong = ? WHERE tenPhong = ?");
		        statement.setString(1, tt);
		        statement.setString(2, tenP);
		        statement.executeUpdate();
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
		}
}