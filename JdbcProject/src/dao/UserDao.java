package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import dto.Users;

public class UserDao {
	private static UserDao instance = new UserDao();
	private FactoryUser factory = FactoryUser.getInstance();
	DateFormat df;
	
	private UserDao() {
		// TODO Auto-generated constructor stub
	}
	public static UserDao getInstance() {
		return instance;
	}
	public boolean isUsers(String user_id) {
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT USER_ID FROM USERS WHERE USER_ID=?";
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user_id);
			rs=pstmt.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			factory.close(conn,pstmt,rs);
		}
		return true;
	}
	public int addUsers(Users usr) {
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "INSERT INTO USERS VALUES (?,?,?,?,?)";
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, usr.getUser_id());
			pstmt.setString(2, usr.getUser_pwd());
			pstmt.setString(3, usr.getUser_name());
			pstmt.setString(4, usr.getGender());
			pstmt.setString(5, usr.getUser_signup());
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			factory.close(conn,pstmt,rs);
		}
		return 0;
	}
	public boolean selectUser(String userid, String userpwd) {
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn2 =null;
		PreparedStatement pstmt2 = null;
		String sql = "SELECT USER_PWD FROM USERS WHERE USER_ID = ?";
		String logp = "INSERT INTO LOGS VALUES (?,?,?)";
		String logf = "INSERT INTO LOGS VALUES (?,?,?)";
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,userid);
			rs = pstmt.executeQuery();
			rs.next();
			if(userpwd.equals(rs.getString("user_pwd"))){
				conn2 = factory.getConnection();
				pstmt2 = conn2.prepareStatement(logp);
				pstmt2.setString(1,userid);
				df = DateFormat.getDateInstance(0, Locale.KOREA);
				pstmt2.setString(2,df.format(new Date()));
				pstmt2.setString(3,"p");
				pstmt2.executeUpdate();
				return true;
			}
			conn2 = factory.getConnection();
			pstmt2 = conn2.prepareStatement(logp);
			pstmt2.setString(1,userid);
			df = DateFormat.getDateInstance(0, Locale.KOREA);
			pstmt2.setString(2,df.format(new Date()));
			pstmt2.setString(3,"f");
			pstmt2.executeUpdate();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			factory.close(conn,pstmt,rs);
			factory.close(conn2,pstmt2);
		}
		return false;
	}
	public String selectID(String username) {
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT USER_ID FROM USERS WHERE USER_NAME=?";
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			rs=pstmt.executeQuery();
			rs.next();
			rs.getString("USER_ID");
			return rs.getString("USER_ID");
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			factory.close(conn,pstmt,rs);
		}
		return null;
	}
}
