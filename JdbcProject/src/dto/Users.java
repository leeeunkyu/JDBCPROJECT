package dto;

/**
 * 사용자를 정의할 유저 테이블
 * @author kosta
 *
 */
public class Users {
	private String user_id;
	private String user_pwd;
	private String user_name;
	private String gender;
	private String user_signup;
	public Users(String user_id, String user_pwd, String user_name, String gender, String user_signup) {
		this.user_id = user_id;
		this.user_pwd = user_pwd;
		this.user_name = user_name;
		this.gender = gender;
		this.user_signup = user_signup;
	}
	public Users() {
		// TODO Auto-generated constructor stub
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_pwd() {
		return user_pwd;
	}
	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getUser_signup() {
		return user_signup;
	}
	public void setUser_signup(String user_signup) {
		user_signup = user_signup;
	}

}
