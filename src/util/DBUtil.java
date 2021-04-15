package util;

import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBUtil {
	
	// Connecting
//	public static Connection getConnection() {
//		
//		Connection conn = null;
//		String driverName = "oracle.jdbc.OracleDriver";
//		String userid = "LIBRARY", password = "1234";
//		String url = "jdbc:oracle:thin:@localhost:1521:xe";
//		
//		try {
//			Class.forName(driverName);
//			conn = DriverManager.getConnection(url, userid, password);
//			//System.out.println("Driver Connect Success");
//		} catch (ClassNotFoundException | SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//		return conn;
//	}
	
	public static Connection getConnection() {
		Connection conn = null;
		Context initContext;
		try {
			initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup("jdbc/myoracle");
			conn = ds.getConnection();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
	}
	
	
	// Close
	public static void close(ResultSet rs, Statement st, Connection conn) {
		try {
			if(rs != null) rs.close();
			if(st != null) st.close();
			if(conn != null) conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
