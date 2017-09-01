package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class FactoryUser {
	private static FactoryUser instance = new FactoryUser();
	/**DB ����̹� ����*/
	private String driverinfo;
	/**DB URL ���*/
	private String url;
	/**DB ���� ID*/
	private String driver_user;
	/**DB ���� PWD*/
	private String driver_pwd;
	private ResourceBundle bundle;
	
	public FactoryUser() {
		bundle = ResourceBundle.getBundle("conf/dbserver");
		driverinfo = bundle.getString("oracle.driver");
		url = bundle.getString("oracle.url");
		driver_user = bundle.getString("oracle.username");
		driver_pwd = bundle.getString("oracle.password");
		try {
			Class.forName(driverinfo);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static FactoryUser getInstance() {
		return instance;
	}
	
	/**
	 * 
	 * @param url
	 * @param user
	 * @param password
	 * @return Connection
	 * DB�� ����
	 */
	public Connection getConnection() {
		try {
			return DriverManager.getConnection(this.url, this.driver_user, this.driver_pwd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * CUD �ڿ����� �޼ҵ�
	 * @param conn
	 * @param stmt
	 */
	public void close(Connection conn,Statement stmt) {
		close(conn,stmt,null);
	}
	
	/**
	 * R �ڿ����� �޼ҵ�
	 * @param conn
	 * @param stmt
	 * @param rs
	 */
	public void close(Connection conn,Statement stmt,ResultSet rs) {
		try {
			if(stmt!=null) {stmt.close();}
			if(conn!=null) {conn.close();}
			if(rs!=null) {rs.close();}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
