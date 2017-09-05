package dto;

/**
 * 사용자 댓글을 정의할 코맨트 테이블
 * @author kosta
 *
 */
public class Comments {
	private String userId;
	private int bordIndex;
	private String content;
	private String commentHistory;

	public Comments() {

	}
	
	public Comments(String userId, int indexNum, String content, String commentHistory) {
		this.userId = userId;
		this.bordIndex = indexNum;
		this.content = content;
		this.commentHistory = commentHistory;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("작성자: "+userId);
		builder.append(" ["+content+"]");
		builder.append("  "+commentHistory);
		return builder.toString();
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getBordIndex() {
		return bordIndex;
	}
	public void setBordIndex(int bordIndex) {
		this.bordIndex = bordIndex;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCommentHistory() {
		return commentHistory;
	}
	public void setCommentHistory(String commentHistory) {
		this.commentHistory = commentHistory;
	}
	
}
