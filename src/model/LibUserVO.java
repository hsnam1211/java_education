package model;

import java.sql.Date;

public class LibUserVO {
	private String user_id;
	private String user_pass;
	private String user_name;
	private Date join_date;
	private String admin_id;
	
	public LibUserVO(String user_id, String user_pass, String user_name, Date join_date, String admin_id) {
		super();
		this.user_id = user_id;
		this.user_pass = user_pass;
		this.user_name = user_name;
		this.join_date = join_date;
		this.admin_id = admin_id;
	}

	public LibUserVO() {
		super();
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_pass() {
		return user_pass;
	}

	public void setUser_pass(String user_pass) {
		this.user_pass = user_pass;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public Date getJoin_date() {
		return join_date;
	}

	public void setJoin_date(Date join_date) {
		this.join_date = join_date;
	}

	public String getAdmin_id() {
		return admin_id;
	}

	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("user_id=").append(user_id).append(", user_pass=").append(user_pass)
				.append(", user_name=").append(user_name).append(", join_date=").append(join_date).append(", admin_id=")
				.append(admin_id);
		return builder.toString();
	}
	
	
}
