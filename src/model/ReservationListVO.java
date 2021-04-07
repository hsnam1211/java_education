package model;

import java.sql.Date;

public class ReservationListVO {
	private String user_id;
	private int book_no;
	private Date reservation_date;

	public ReservationListVO(String user_id, int book_no, Date reservation_date) {
		super();
		this.user_id = user_id;
		this.book_no = book_no;
		this.reservation_date = reservation_date;
	}

	public ReservationListVO() {
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

	public Date getReservation_date() {
		return reservation_date;
	}

	public void setReservation_date(Date reservation_date) {
		this.reservation_date = reservation_date;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("user_id=").append(user_id).append(", book_no=").append(book_no)
				.append(", reservation_date=").append(reservation_date);
		return builder.toString();
	}
	
	
}
