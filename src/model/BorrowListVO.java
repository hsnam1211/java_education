package model;

import java.sql.Date;

public class BorrowListVO {
	private String user_id;
	private int book_no;
	private Date borrow_date;
	private Date return_date;
	private String book_name;
	
	
	public BorrowListVO(String user_id, int book_no, Date borrow_date, Date return_date, String book_name) {
		super();
		this.user_id = user_id;
		this.book_no = book_no;
		this.borrow_date = borrow_date;
		this.return_date = return_date;
		this.book_name = book_name;
	}

	public String getBook_name() {
		return book_name;
	}

	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}


	public BorrowListVO() {
		super();
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public int getBook_no() {
		return book_no;
	}

	public void setBook_no(int book_no) {
		this.book_no = book_no;
	}

	public Date getBorrow_date() {
		return borrow_date;
	}

	public void setBorrow_date(Date borrow_date) {
		this.borrow_date = borrow_date;
	}

	public Date getReturn_date() {
		return return_date;
	}

	public void setReturn_date(Date return_date) {
		this.return_date = return_date;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("user_id=").append(user_id).append(", book_no=").append(book_no)
				.append(", borrow_date=").append(borrow_date).append(", return_date=").append(return_date)
				.append(", book_name=").append(book_name);
		return builder.toString();
	}

	
	
	
}
