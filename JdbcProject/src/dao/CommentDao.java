package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.Comments;
import dto.Logs;

/**
 * 코맨트 db접근을 위한 코맨트 dao클래스
 * @author kosta
 *
 */
public class CommentDao {
	private static CommentDao instance = new CommentDao();
	private FactoryUser factory = FactoryUser.getInstance();

	private CommentDao() {
		// TODO Auto-generated constructor stub
	}
	
	public static CommentDao getInstance() {
		return instance;
	}

	public ArrayList<Comments> selectCommentList(int indexNum) {
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Comments> al = new ArrayList<Comments>();
		String sql = " SELECT * FROM COMMENTS WHERE BORD_INDEX = ?";
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, indexNum);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				al.add(new Comments(rs.getString("USER_ID"), rs.getInt("BORD_INDEX"), rs.getString("COMMENT_CONTENT"), rs.getString("COMMENT_HISTORY")));
			}
			
			return al;
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			factory.close(conn,pstmt,rs);
		}
		return al;
	}

	public int insertComment(Comments com) {
		Connection conn =null;
		PreparedStatement pstmt = null;
		String sql = " INSERT INTO COMMENTS VALUES (?,?,?,?)";
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, com.getUserId());
			pstmt.setInt(2, com.getBordIndex());
			pstmt.setString(3, com.getContent());
			pstmt.setString(4, com.getCommentHistory());
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			factory.close(conn,pstmt);
		}
		return 0;
	}
	
}
