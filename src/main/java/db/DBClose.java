package db;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBClose {
	
	public static void close(Connection conn, Statement psmt, ResultSet rs) {
		// 접속해제 하는 곳
		try {
			if(conn != null) {	// db의 후처리
				conn.close();
			}
			if(psmt != null) {
				psmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {				
			e.printStackTrace();
		}			
	}
}
