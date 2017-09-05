package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import Util.TransferExcel;
import dto.BlockUsers;
import dto.Users;

/**
 * userdb에 접속하기위한 userdao클래스
 * @author kosta
 *
 */
public class UserDao {
	private static UserDao instance = new UserDao();
	private FactoryUser factory = FactoryUser.getInstance();
	TransferExcel te = new TransferExcel();
	BlockUsers bu;
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
	
	public boolean checkDDos(String userid) {
		Connection connDDos =null;
		PreparedStatement pstmtDDos = null;
		ResultSet rsDDos = null;
		int count = 0;
		ArrayList<String> ddosarr = new ArrayList<>();
		String sql = " select 0||substr(user_history,7,1)||0"
				+ "||substr(user_history,10,1)||0||substr(user_history,20,1)"
				+ "||substr(user_history,23,2)||substr(user_history,27,2) loglist "
				+ "from logs where user_id = ? order by user_history";
		try {
			connDDos = factory.getConnection();
			pstmtDDos = connDDos.prepareStatement(sql);
			pstmtDDos.setString(1, userid);
			rsDDos=pstmtDDos.executeQuery();
			while(rsDDos.next()) {
				System.out.println(rsDDos.getString("loglist"));
				ddosarr.add(rsDDos.getString("loglist"));
			}
			Calendar cal = Calendar.getInstance();
			for (int i = 0; i < ddosarr.size(); i++) {
				if ((cal.get(Calendar.MONTH)+1)<10) {
					if (cal.get(Calendar.DATE)<10) {
						if (ddosarr.get(i).substring(0, 4).equals("0"+(cal.get((Calendar.MONTH))+1)+"0"+cal.get(Calendar.DATE))) {
							System.out.println("값이 같다");
							count++;
							
						}		
					}else {
						if (ddosarr.get(i).substring(0, 4).equals("0"+(cal.get((Calendar.MONTH))+1)+cal.get(Calendar.DATE))) {
							System.out.println("값이 같다");
							count++;
						}
					}
				}else {
					if (ddosarr.get(i).substring(0, 4).equals((cal.get((Calendar.MONTH))+1)+"0"+cal.get(Calendar.DATE))) {
						System.out.println("값이 같다");
						count++;
					}
				}
			}
			if (count > 5) {
				blockUser(userid,ddosarr);
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			factory.close(connDDos,pstmtDDos,rsDDos);
		}
		return false;
	}
	
	public boolean blockUser(String userid,ArrayList<String> blocktime) {
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		ArrayList<String> btime = new ArrayList<>();
		for (int i = 0; i < blocktime.size()-1; i++) {
			if(blocktime.get(i).substring(4,6).equals(blocktime.get(i+1).substring(4,6))) {
				System.out.println("동일시간에 접속" +blocktime.get(i).substring(4,6) );
				count++;
			}
		}
		
		if (count > 5) {
			Calendar cal = Calendar.getInstance();
	//		bu = new BlockUsers(userid, "block",);
			String sql = "insert into BlockUsers values(?,?,?)";
			try {
				conn = factory.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userid);
				pstmt.setString(2, "block");
				//pstmt.setString(3, cal.);
				rs=pstmt.executeQuery();
				return rs.next();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				factory.close(conn,pstmt,rs);
				}	
			}
		
//		String sql = "SELECT USER_ID FROM USERS WHERE USER_ID=?";
//		
		return true;
	}
	
	public boolean failCount(String userid) {
		Connection failconn =null;
		PreparedStatement failpstmt = null;
		ResultSet failrs = null;
		int failcount = 0;
		String sql = "SELECT COUNT(PERMISSION) FROM LOGS WHERE USER_ID=? AND PERMISSION = 'f' GROUP BY PERMISSION";
		try {
			failconn = factory.getConnection();
			failpstmt = failconn.prepareStatement(sql);
			failpstmt.setString(1, userid);
			failrs=failpstmt.executeQuery();
			failrs.next();
			failcount = failrs.getInt("COUNT(PERMISSION)");
			if (failcount < 5) {
				System.out.println(failcount);
				return true;
			}else {
				checkDDos(userid);
			}
			System.out.println(failcount);
			return false;
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			factory.close(failconn,failpstmt,failrs);
		}
		return false;
	}
	
	public boolean selectUser(String userid, String userpwd) {
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn2 =null;
		PreparedStatement pstmt2 = null;
		userpwd = te.getEncrypt(userpwd);
		String sql = "SELECT USER_PWD FROM USERS WHERE USER_ID = ?";
		String logp = "INSERT INTO LOGS VALUES (?,?,?)";
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,userid);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(userpwd.equals(rs.getString("user_pwd"))){
					conn2 = factory.getConnection();
					pstmt2 = conn2.prepareStatement(logp);
					pstmt2.setString(1,userid);
					df = DateFormat.getDateTimeInstance(0,1,Locale.KOREA);
					pstmt2.setString(2,df.format(new Date()));
					pstmt2.setString(3,"p");
					pstmt2.executeUpdate();
					return true;
				}
				conn2 = factory.getConnection();
				pstmt2 = conn2.prepareStatement(logp);
				pstmt2.setString(1,userid);
				df = DateFormat.getDateTimeInstance(0,1, Locale.KOREA);
				pstmt2.setString(2,df.format(new Date()));
				pstmt2.setString(3,"f");
				pstmt2.executeUpdate();
				//System.out.println(failCount(userid));

			}
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
		username = te.getEncrypt(username);
		String sql = "SELECT USER_ID FROM USERS WHERE USER_NAME=?";
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			rs=pstmt.executeQuery();
			rs.next();
			return rs.getString("USER_ID");
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			factory.close(conn,pstmt,rs);
		}
		return null;
	}
	public boolean deleteLog(String deleteid) {
		
		Connection connlog =null;
		PreparedStatement pstmtlog = null;
		String deletesql = "DELETE FROM LOGS WHERE USER_ID=?";
		try {
			connlog = factory.getConnection();
			pstmtlog = connlog.prepareStatement(deletesql);
			pstmtlog.setString(1, deleteid);
			System.out.println(pstmtlog.executeUpdate());
			return true;
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			factory.close(connlog,pstmtlog);
		}
		return false;
		
	}
	public boolean deleteUser(String deleteid, String deletepwd) {
		Connection conn =null;
		PreparedStatement pstmt = null;
		Connection conn2 =null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		deletepwd = te.getEncrypt(deletepwd);
		String sql = "SELECT USER_PWD FROM USERS WHERE USER_ID=?";
		String deletesql = "DELETE FROM USERS WHERE USER_ID=?";

		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, deleteid);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if(deletepwd.equals(rs.getString("user_pwd"))) {
					if(deleteLog(deleteid)) {
						conn2 = factory.getConnection();
						pstmt2 = conn2.prepareStatement(deletesql);
						pstmt2.setString(1, deleteid);
						if (pstmt2.executeUpdate() == 1) {
							return true;
						}
						System.out.println("삭제과정에서 오류가 있었습니다.");
						return false;
					}
				}
				return false;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			factory.close(conn,pstmt);
		}

	}
	public boolean selectUserPWD(String userid, String username) {
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		username = te.getEncrypt(username);
		String sql = "SELECT USER_NAME FROM USERS WHERE USER_ID=?";
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString("user_name").equals(username)) {
					return true;
				}
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			factory.close(conn,pstmt,rs);
		}
		return false;
	}
	public boolean modifyUserPWD(String userid, String modipwd) {
		Connection conn =null;
		PreparedStatement pstmt = null;
		modipwd = te.getEncrypt(modipwd);
		String sql = "UPDATE USERS SET USER_PWD = ? WHERE USER_ID=?";
		try {
			conn = factory.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, modipwd);
			pstmt.setString(2, userid);			
			if (pstmt.executeUpdate() != 0) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			factory.close(conn,pstmt);
		}
		return false;
	}
}
