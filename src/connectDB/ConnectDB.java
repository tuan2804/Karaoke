package connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
	public static Connection con = null;
	private static ConnectDB instance = new ConnectDB();
	private String url = "jdbc:sqlserver://localhost:1433;databaseName=KaraokeNice";
    private String username = "sa";
    private String password = "sa123456";
    
	public static ConnectDB getInstance() {
		return instance;
	}
	
	public void connect() throws SQLException {
		String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=KaraokeNice;user=sa;password=sa123456";
		con = DriverManager.getConnection(connectionUrl);
	}
	
	public void disconnect() {
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

	public static Connection getConnection() {
		return con;
	}
	
	public Connection reconnect() {
        try {
        	con = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}
