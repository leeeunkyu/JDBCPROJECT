package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import dto.Borders;

public class BorderDao {
	private FactoryUser factory = FactoryUser.getInstance();
	private static BorderDao instance = new BorderDao();

	public static BorderDao getInstance() {
		return instance;
	}
	private BorderDao() {
		// TODO Auto-generated constructor stub
	}
	public int addBorder(Borders bor) {
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "INSERT INTO BORDERS VALUES (?,?,?,?,?,?)";
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bor.getUserid());
			pstmt.setInt(2, bor.getBord_index());
			pstmt.setString(3, bor.getBord_head());
			pstmt.setString(4, bor.getBord_body());
			pstmt.setInt(5, bor.getBord_count());
			pstmt.setString(6, bor.getBord_date());
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			factory.close(conn,pstmt,rs);
		}
		return 0;
	}
	public ArrayList<Borders> selectList() {
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM BORDERS ORDER BY BORD_INDEX DESC";
		ArrayList<Borders> a_bor = new ArrayList<>();
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				a_bor.add(new Borders(rs.getString("user_id"), rs.getInt("bord_index"), 
						rs.getString("bord_head"), rs.getString("bord_body"), 
						rs.getInt("bord_count"),rs.getString("bord_date")));
			}
			return a_bor;
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			factory.close(conn,pstmt,rs);
		}
		return null;
	}
	public int selectIndex() {
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = " SELECT BORD_INDEX FROM BORDERS ORDER BY BORD_INDEX DESC";
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			return rs.getInt("bord_index")+1;
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			factory.close(conn,pstmt,rs);
		}
		return 0;
	}
	public Borders selctOne(int index_num) {
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn2 =null;
		PreparedStatement pstmt2 = null;
		String sql = " SELECT * FROM BORDERS where bord_index= ? ";
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index_num);
			rs = pstmt.executeQuery();
			rs.next();
			return new Borders(rs.getString("user_id"), rs.getInt("bord_index"), 
					rs.getString("bord_head"), rs.getString("bord_body"), 
					rs.getInt("bord_count"),rs.getString("bord_date"));
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			factory.close(conn,pstmt,rs);
		}
		return null;
	}
	public ArrayList<Borders> selectUserBord(String userid) {
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM BORDERS WHERE USER_ID = ? ORDER BY BORD_INDEX DESC";
		ArrayList<Borders> a_bor = new ArrayList<>();
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				a_bor.add(new Borders(rs.getString("user_id"), rs.getInt("bord_index"), 
						rs.getString("bord_head"), rs.getString("bord_body"), 
						rs.getInt("bord_count"),rs.getString("bord_date")));
			}
			return a_bor;
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			factory.close(conn,pstmt,rs);
		}
		return null;
	}
	public boolean deleteOne(int delete_num, String delete_pwd) {
		Connection conn =null;
		PreparedStatement pstmt = null;
		Connection conn2 =null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		String sql = " SELECT USERS.USER_PWD FROM BORDERS,USERS "
				+ "WHERE USERS.USER_ID = BORDERS.USER_ID "
				+ "AND BORDERS.BORD_INDEX=? ";
		String deletesql ="DELETE FROM BORDERS WHERE BORD_INDEX = ?";
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, delete_num);
			rs = pstmt.executeQuery();
			rs.next();
			String str = rs.getString("USER_PWD");
			System.out.println(str);
			if(str.equals(delete_pwd)){
				conn2 = factory.getConnection();
				pstmt2 = conn2.prepareStatement(deletesql);
				pstmt2.setInt(1,delete_num);
				pstmt2.executeUpdate();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			factory.close(conn,pstmt,rs);
		}
		return false;
	}

}
