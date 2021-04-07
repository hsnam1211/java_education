package model;

public class LibBookVO {
	private int book_no;
	private String book_name;
	private String book_writer;
	private String book_borrow_status;
	
	public LibBookVO(int book_no, String book_name, String book_writer, String book_borrow_status) {
		super();
		this.book_no = book_no;
		this.book_name = book_name;
		this.book_writer = book_writer;
		this.book_borrow_status = book_borrow_status;
	}
	
	public LibBookVO() {
		super();
	}

	public int getBook_no() {
		return book_no;
	}

	public void setBook_no(int book_no) {
		this.book_no = book_no;
	}

	public String getBook_name() {
		return book_name;
	}

	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}

	public String getBook_writer() {
		return book_writer;
	}

	public void setBook_writer(String book_writer) {
		this.book_writer = book_writer;
	}

	public String getBook_borrow_status() {
		return book_borrow_status;
	}

	public void setBook_borrow_status(String book_borrow_status) {
		this.book_borrow_status = book_borrow_status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("book_no=").append(book_no).append(", book_name=").append(book_name)
				.append(", book_writer=").append(book_writer).append(", book_borrow_status=").append(book_borrow_status);
		return builder.toString();
	}
	
	
}
