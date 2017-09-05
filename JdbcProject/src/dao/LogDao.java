package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.Logs;

/**
 * log db에 들어가기 위한 logdao클래스 
 * @author kosta
 *
 */
public class LogDao {
	private FactoryUser factory = FactoryUser.getInstance();
	private static LogDao instance = new LogDao();
	
	public static LogDao getInstance() {
		// TODO Auto-generated method stub
		return instance;
	}
	private LogDao() {
		// TODO Auto-generated constructor stub
	}
	public ArrayList<Logs> selectLog(String userid) {
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Logs> al = new ArrayList<Logs>();
		String sql = " SELECT * FROM LOGS WHERE USER_ID = ?";
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				al.add(new Logs(userid, rs.getString("user_history"),rs.getString("permission")));
			}
			
			return al;
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			factory.close(conn,pstmt,rs);
		}
		return al;
	}

}
