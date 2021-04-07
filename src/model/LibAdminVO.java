package model;

import java.sql.Date;

public class LibAdminVO {
	private String admin_id;
	private String admin_pass;
	private String admin_name;
	
	public LibAdminVO(String admin_id, String admin_pass, String admin_name) {
		super();
		this.admin_id = admin_id;
		this.admin_pass = admin_pass;
		this.admin_name = admin_name;
	}

	public LibAdminVO() {
		super();
	}

	public String getAdmin_id() {
		return admin_id;
	}

	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}

	public String getAdmin_pass() {
		return admin_pass;
	}

	public void setAdmin_pass(String admin_pass) {
		this.admin_pass = admin_pass;
	}

	public String getAdmin_name() {
		return admin_name;
	}

	public void setAdmin_name(String admin_name) {
		this.admin_name = admin_name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LibAdminVO [admin_id=").append(admin_id).append(", admin_pass=").append(admin_pass)
				.append(", admin_name=").append(admin_name).append("]");
		return builder.toString();
	}
	
	
}
