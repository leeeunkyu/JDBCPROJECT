package dto;

/**
 * 사용자 로그를 정의할 로그테이블 
 * @author kosta
 *
 */
public class Logs {
	
	String user_id;
	String user_history;
	String permission;
	
	public Logs() {
	}
	
	public Logs(String user_id, String user_history, String permission) {
		this.user_id = user_id;
		this.user_history = user_history;
		this.permission = permission;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_history() {
		return user_history;
	}
	public void setUser_history(String user_history) {
		this.user_history = user_history;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(user_id);
		builder.append("  ");
		builder.append(user_history);
		builder.append("  ");
		builder.append(permission);
		builder.append("\n");
		return builder.toString();
	}
}
