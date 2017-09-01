package dto;

public class Borders {
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("번호["+bord_index+"]");
		builder.append("  ");
		builder.append("작성자:"+userid);
		builder.append("  ");
		builder.append("제목:"+bord_head);
		builder.append("  ");
		builder.append("조회수:"+bord_count);
		builder.append("  ");
		builder.append("작성일:"+bord_date);
		builder.append("\n");
		return builder.toString();
	}

	private String userid;
	private int bord_index;
	private String bord_head;
	private String bord_body;
	private int bord_count;
	private String bord_date;
	
	public Borders() {
		
	}
	
	public Borders(String userid, int bord_index, String bord_head, String bord_body, int bord_count,
			String bord_date) {
		this.userid = userid;
		this.bord_index = bord_index;
		this.bord_head = bord_head;
		this.bord_body = bord_body;
		this.bord_count = bord_count;
		this.bord_date = bord_date;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public int getBord_index() {
		return bord_index;
	}

	public void setBord_index(int bord_index) {
		this.bord_index = bord_index;
	}

	public String getBord_head() {
		return bord_head;
	}

	public void setBord_head(String bord_head) {
		this.bord_head = bord_head;
	}

	public String getBord_body() {
		return bord_body;
	}

	public void setBord_body(String bord_body) {
		this.bord_body = bord_body;
	}

	public int getBord_count() {
		return bord_count;
	}

	public void setBord_count(int bord_count) {
		this.bord_count = bord_count;
	}

	public String getBord_date() {
		return bord_date;
	}

	public void setBord_date(String bord_date) {
		this.bord_date = bord_date;
	}

	

}
