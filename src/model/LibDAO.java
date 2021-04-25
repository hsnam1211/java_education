package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import logic.ReservationMessage;
import util.DBUtil;
import util.LoggableStatement;


public class LibDAO {
	
	// 1.로그인 하기 
	public String login(String input_id, String input_pass, int num) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = null;
		String id = null;
		String pass = null;
		String admin = null;
		
		if(num == 1) {

			sql = "select user_id, user_pass, admin_id from lib_user where user_id = ?";
			
			try {
				st = conn.prepareStatement(sql);
				st.setString(1, input_id);
				rs = st.executeQuery();
				
				while(rs.next()) {
					id = rs.getString("user_id");
					pass = rs.getString("user_pass");
					admin = rs.getString("admin_id");
				
				}
				if(input_id == null || input_pass == null || id == null ||(id.equals(input_id) && !pass.equals(input_pass)) || (!id.equals(input_id) && pass.equals(input_pass))) {
					System.out.println("계정이 올바르지 않습니다.");
					return "no_id";
				} else if(id.equals(input_id) && pass.equals(input_pass) && admin!=null) {
					System.out.println("관리자 승인 확인");
					return id;
				} else if(id.equals(input_id) && pass.equals(input_pass) && admin==null) {					
					System.out.println("관리자 승인이 필요합니다.");
					return "no_admin";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				DBUtil.close(rs, st, conn);
			}
			
		} else if(num == 2) {
			sql = "select admin_id, admin_pass from lib_admin where admin_id = ?";
			
			try {
				st = conn.prepareStatement(sql);
				st.setString(1, input_id);
				rs = st.executeQuery();
				
				while(rs.next()) {
					id = rs.getString("admin_id");
					pass = rs.getString("admin_pass");
				
				}
				
				if(input_id == null || input_pass == null || id == null ||(id.equals(input_id) && !pass.equals(input_pass)) || (!id.equals(input_id) && pass.equals(input_pass))) {
					System.out.println("계정이 올바르지 않습니다.");
					return "no_id";
				} else if(id.equals(input_id) && pass.equals(input_pass)) {
					
					System.out.println("관리자 입니다.");
					return id;	
				} 
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				DBUtil.close(rs, st, conn);
			}
		}
		return null;
	}

	
	// 2.도서검색(이용자 or 관리자)
	public List<LibBookVO> getBook(int num, String bookname) {
		List<LibBookVO> bookList = new ArrayList<>();
		String sql = null;
		
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			if(num == 1) {
				sql = "select * from book_list order by book_no";	
				st = conn.prepareStatement(sql);
			} else if(num == 2) {
//				Scanner sc = new Scanner(System.in);
				sql = "select * from book_list where book_name = ? order by book_no";
				
//				System.out.print("조회하실 도서 제목을 입력해주세요. >>");
				String book_name = bookname;
//				String book_name = sc.nextLine();
				st = conn.prepareStatement(sql);
				st.setString(1, book_name);
			} else if(num == 3) {
				sql = "select * from book_list where book_borrow_status = 'Y' order by book_no";
				st = conn.prepareStatement(sql);
			}
		
			rs = st.executeQuery();
			
			while(rs.next()) {
				bookList.add(makeBookList(rs));
			}
			if(bookList.isEmpty()) {
				System.out.println("없는 도서입니다.");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally { 
			DBUtil.close(rs, st, conn);
		}
		return bookList;
	}
	
	public LibBookVO makeBookList(ResultSet rs) throws SQLException { 
		LibBookVO libBook = new LibBookVO();
		
		libBook.setBook_name(rs.getString("book_name"));
		libBook.setBook_no(rs.getInt("book_no"));
		libBook.setBook_borrow_status(rs.getString("book_borrow_status"));    
		libBook.setBook_writer(rs.getString("book_writer"));     
		
		
		return libBook;
	};
	
	// 3.도서대출, 반납(이용자)

	public int borrowBookStatus(int book_no, String status, String user_id) { // 선택한 책 row 한 줄을 가져오고, status엔 대여가능 여부(Y,N)를 가져온다.
		Scanner sc = new Scanner(System.in);
		int result = 0;
		int num = 0;
		String borrow_status = null;
		String sql1 = "select book_borrow_status from book_list where book_no = ?";
		String sql2 = " update book_list"
				+ " set"
				+ " book_borrow_status = ?"
				+ " where book_no = ?";
				
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(sql1);
			st.setInt(1, book_no);
			rs = st.executeQuery();
			while(rs.next()) {
				borrow_status = rs.getString("book_borrow_status");
			}
			
			if(borrow_status == null) { // 없는 도서일 때
				System.out.println("없는 데이터 입니다.");
				return -1;
			} else if(!borrow_status.equals(status)) {
				
				System.out.println("\n");
				System.out.println("1.예 | 2.아니오");
				if(status.equals("N")) {
					System.out.print("도서를 대출하시겠습니까? >>");	
					num = sc.nextInt();
				} else { //반납
					for(int i=0;i<borrowUserList(user_id).size();i++) { 
						if(borrowUserList(user_id).get(i).getBook_no() == book_no) {
							System.out.print("도서를 반납하시겠습니까? >>");					
							
							num = sc.nextInt();
							if(num == 1) {
								st = conn.prepareStatement(sql2);
								
								st.setString(1, status);
								st.setInt(2, book_no);
								result = st.executeUpdate();
								// 여기에서 예약리스트 조회해서 있으면 로그인 메시지 메서드로 전달 
								if(reservationTop1(user_id, book_no).getBook_no() != 0) { //반납하기 전 예약순번 1번이 있는지 확인한다. 있으면 if문 실행
									reservationSave(reservationTop1(user_id, book_no).getUser_id(), reservationTop1(user_id, book_no).getBook_no());
								
								}
								return result;
							} else if(num == 2) {
								return -1;
							} else {
								System.out.println("잘못입력하셨습니다.");
								return -1;
							}
						}
					}
					// 리스트에 도서가 있지만 자신이 대출한 도서가 아닐 때
					return 0;
				}
				
				
				if(num == 1) { //대출
					
					if(reservationTop1(user_id, book_no).getBook_no() == 0) { //대출하기 전 예약순번 1번이 있는지 확인한다. 있다면 else로 
						st = conn.prepareStatement(sql2);
						
						st.setString(1, status);
						st.setInt(2, book_no);
						result = st.executeUpdate(); //없다면 지금 대출하기 
					} else {												  //예약순번 1번이 있다면 그게 나인지 확인한다. 
						if(reservationTop1(user_id, book_no).getBook_no() == book_no && reservationTop1(user_id, book_no).getUser_id().equals(user_id)) {
							st = conn.prepareStatement(sql2);
							
							st.setString(1, status);
							st.setInt(2, book_no);
							result = st.executeUpdate();
							
							// 예약 저장 딜리트
							reservationSaveDelete(user_id, book_no); // 1번이었던 내 순번 지우기
							// 예약 대기 명단 딜리트 메서드 
							reservationListDelete(book_no); // 예약 전체명단에서 내 이름 지우기
						} else {
							System.out.println("예약 대기 중인 도서입니다.");
							return -1;
						}
						
					}
				} else if(num == 2) {
					return -1;
				} else {
					System.out.println("잘못입력하셨습니다.");
					return -1;
				}
			}	
		} 

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, st, conn);
		}
		
		return result;
	}
	
	//web용 반납함수
	public int returnBookStatus(int book_no, String status, String user_id) { // 선택한 책 row 한 줄을 가져오고, status엔 대여가능 여부(Y,N)를 가져온다.
		Scanner sc = new Scanner(System.in);
		int result = 0;
		int num = 0;
		String borrow_status = null;
		String sql1 = "select book_borrow_status from book_list where book_no = ?";
		String sql2 = " update book_list"
				+ " set"
				+ " book_borrow_status = ?"
				+ " where book_no = ?";
		
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(sql1);
			st.setInt(1, book_no);
			rs = st.executeQuery();
			while(rs.next()) {
				borrow_status = rs.getString("book_borrow_status");
			}
			
			if(!borrow_status.equals(status)) {
				if(status.equals("Y")) {
					for(int i=0;i<borrowUserList(user_id).size();i++) { 
						if(borrowUserList(user_id).get(i).getBook_no() == book_no) {
							st = conn.prepareStatement(sql2);
							
							st.setString(1, status);
							st.setInt(2, book_no);
							result = st.executeUpdate();
							// 여기에서 예약리스트 조회해서 있으면 로그인 메시지 메서드로 전달 
							if(reservationTop1(user_id, book_no).getBook_no() != 0) {
								reservationSave(reservationTop1(user_id, book_no).getUser_id(), reservationTop1(user_id, book_no).getBook_no());	
							}
							return result;
						}
					}
					// 리스트에 도서가 있지만 자신이 대출한 도서가 아닐 때
					return 0;
				}
			}	
		} 
		
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, st, conn);
		}
		
		return result;
	}

	// web용 대출함수
	public int borrowBookStatus1(int book_no, String status, String user_id) { // 선택한 책 row 한 줄을 가져오고, status엔 대여가능 여부(Y,N)를 가져온다.
		int result = 0;
		int num = 0;
		String borrow_status = null;
		String sql1 = "select book_borrow_status from book_list where book_no = ?";
		String sql2 = " update book_list"
				+ " set"
				+ " book_borrow_status = ?"
				+ " where book_no = ?";
				
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(sql1);
			st.setInt(1, book_no);
			rs = st.executeQuery();
			while(rs.next()) {
				borrow_status = rs.getString("book_borrow_status");
			}
			
			if(borrow_status == null) { // 없는 도서일 때
				System.out.println("없는 데이터 입니다.");
				return -1;
			} else if(!borrow_status.equals(status)) {
				if(status.equals("N")) { // 빌릴 수 있을 때
					if(reservationTop1(user_id, book_no).getBook_no() == 0) {
						st = conn.prepareStatement(sql2);
						
						st.setString(1, status);
						st.setInt(2, book_no);
						result = st.executeUpdate();		
					} else {
						if(reservationTop1(user_id, book_no).getBook_no() == book_no && reservationTop1(user_id, book_no).getUser_id().equals(user_id)) {
							st = conn.prepareStatement(sql2);
							
							st.setString(1, status);
							st.setInt(2, book_no);
							result = st.executeUpdate();
							
							// 예약 저장 딜리트
							reservationSaveDelete(user_id, book_no);
							// 예약 대기 명단 딜리트 메서드 
							reservationListDelete(book_no);
							
						} else {
							System.out.println("예약 대기 중인 도서입니다.");
							return -1;
						}
					}
				} else {
					for(int i=0;i<borrowUserList(user_id).size();i++) { 
						if(borrowUserList(user_id).get(i).getBook_no() == book_no) {
							st = conn.prepareStatement(sql2);
							
							st.setString(1, status);
							st.setInt(2, book_no);
							result = st.executeUpdate();
							// 여기에서 예약리스트 조회해서 있으면 로그인 메시지 메서드로 전달 
							if(reservationTop1(user_id, book_no).getBook_no() != 0) {
								reservationSave(reservationTop1(user_id, book_no).getUser_id(), reservationTop1(user_id, book_no).getBook_no());
								
							}
							return result;
						}
					}
					// 리스트에 도서가 있지만 자신이 대출한 도서가 아닐 때
					return 0;
				}
			}	
		} 

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, st, conn);
		}
		
		return result;
	}

	

	
	// insert 도서 대출 명단
	public int borrowListInsert(String user_id, int book_no) { 
		int result = 0;
		String sql1 = "select book_name from book_list where book_no = ?"; // book_name 가져오는 쿼리
		String sql2 = " insert into book_borrow_list(user_id, book_no, book_name) values (?,?,?)"; // book_borrow_list insert query
		String sql3 = " insert into book_history_list(user_id, book_no, book_name) values (?,?,?)"; // 가져온 book_name를 포함한 book_history_list를 insert
		String book_name = null;
	
		Connection conn = DBUtil.getConnection();
		PreparedStatement st;
		ResultSet rs;
		
		try {
			
			// book_name 가져오기 
			st = conn.prepareStatement(sql1);
			st.setInt(1, book_no);
			rs = st.executeQuery();
			while(rs.next()) {
				book_name = rs.getString("book_name");
			}
			
			// book_borrow_list insert query
			st = conn.prepareStatement(sql2);
			st.setString(1, user_id);
			st.setInt(2, book_no);
			st.setString(3, book_name);
			result = st.executeUpdate();
			
			// 가져온 book_name를 포함한 book_history_list를 insert
			st = conn.prepareStatement(sql3);
			st.setString(1, user_id);
			st.setInt(2, book_no);
			st.setString(3, book_name);
			result = st.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	};
	
	// 도서 대출 명단
	public List<BorrowListVO> borrowUserList() {
		List<BorrowListVO> bookList = new ArrayList<>();
		String sql = "select * from book_list";
		Connection conn = DBUtil.getConnection();
		Statement st = null;
		ResultSet rs = null;
		
	
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			
			while(rs.next()) {
				bookList.add(makeBorrowUserList(rs));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, st, conn);
		}
		return bookList;
	}
	public BorrowListVO makeBorrowUserList(ResultSet rs) throws SQLException { 
		BorrowListVO borrowList = new BorrowListVO();
		
		borrowList.setUser_id(rs.getString("user_id"));
		borrowList.setBook_no(rs.getInt("book_no"));
		borrowList.setBook_name(rs.getString("book_name"));
		borrowList.setBorrow_date(rs.getDate("borrow_date"));
		borrowList.setReturn_date(rs.getDate("return_date"));
		
		return borrowList;
	};
	
	// 도서 대출 명단 select
	public List<BorrowListVO> borrowUserList(String user_id) {
		List<BorrowListVO> bookList = new ArrayList<>();
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			if(user_id.equals("admin1")) {
				sql = "select * from book_borrow_list";
				st = conn.prepareStatement(sql);
			} else {
				sql = "select * from book_borrow_list where user_id = ?";
				st = conn.prepareStatement(sql);
				st.setString(1, user_id);
			}
			rs = st.executeQuery();
			
			while(rs.next()) {
				bookList.add(makeBorrowUserList(rs));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, st, conn);
		}
		return bookList;
	}
	
	// 5.도서연장(이용자)
	public int extendBook(int book_no, String user_id) {
		int result = 0;
		int j = 0;
		String sql = " update book_borrow_list"
				+ " set"
				+ " return_date = return_date + 7"
				+ " where book_no = ?";
		
		Connection conn = DBUtil.getConnection();
		PreparedStatement st;
		
		try {
			for(int i=0;i<getBook(1,"").size();i++) { 
				if(getBook(1,"").get(i).getBook_no() == book_no) {
					for(j=0;j<borrowUserList(user_id).size();j++) { // 도서가 있지만 자신이 대출한 도서가 아닐 때
						if(borrowUserList(user_id).get(j).getBook_no() == book_no) {
							st = conn.prepareStatement(sql);
							st.setInt(1, book_no);
							result = st.executeUpdate();
							return result;
						}
					}
					if(j == borrowUserList(user_id).size()) {
						System.out.println(user_id+"님이 대출한 도서가 아닙니다.");
						return result;
					}
				}
			}
			System.out.println("없는 데이터 입니다.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	// 6.도서예약(이용자)
	public int reservationBook(String user_id, int book_no) {
		int result = 0;
		List<LibBookVO> bookList = new ArrayList<>();
		List<BorrowListVO> bookBorrowList = getBorrowList(user_id);
		List<ReservationListVO> bookReservationList = getReservationList(user_id);
		String sql = null;
		
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		sql = "select * from book_list where book_borrow_status = 'N' and book_no = ? order by book_no";
		
		// 도서 예약자 리스트(getBorrowList())에서 user_id와 book_no조회해서 있을 시 "현재 대출중인 도서입니다." 띄우고 예약불가
		if(!bookBorrowList.isEmpty()) {
			for(BorrowListVO bookBorrow:bookBorrowList) {
				if(bookBorrow.getBook_no() == book_no) {
					return 2; // "현재 대출중인 도서입니다."
				}
			}			
		}
		// 없을 시 예약자 명단에서 user_id와 book_no조회해서 있을 시 "현재 예약중인 도서입니다." 띄우고 예약불가
		if(!bookReservationList.isEmpty()) {
			for(ReservationListVO bookReservation:bookReservationList) {
				if(bookReservation.getBook_no() == book_no) {
					return 3; // "이미 예약하신 도서입니다."
				}
			}			
		}
		
		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, book_no);
			rs = st.executeQuery();
			
			while(rs.next()) {
				bookList.add(makeBookList(rs));
			}
			if(bookList.isEmpty()) {
				return 0; // "예약 불가능한 도서입니다."
			} else {
				sql = "insert into book_reservation_list(user_id, book_no) values(?, ?)";
				st = conn.prepareStatement(sql);
				st.setString(1, user_id);
				st.setInt(2, book_no);
				result = st.executeUpdate();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, st, conn);
		}
		return result;
	}
	
	// 도서 예약 명단 리스트 
	public List<ReservationListVO> getReservationList(String user_id) {
		List<ReservationListVO> bookReservationList = new ArrayList<>();
		String sql = "select * from book_reservation_list where user_id = ?";
		
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
				st = conn.prepareStatement(sql);
				st.setString(1, user_id);
				rs = st.executeQuery();
				while(rs.next()) {
					bookReservationList.add(makeBookReservationList(rs));					
				}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally { 
			DBUtil.close(rs, st, conn);
		}
		return bookReservationList;
	}
	
	public ReservationListVO makeBookReservationList(ResultSet rs) throws SQLException { 
		ReservationListVO bookReservationList = new ReservationListVO();
		
		bookReservationList.setUser_id(rs.getString("user_id"));
		bookReservationList.setBook_no(rs.getInt("book_no"));
		bookReservationList.setReservation_date(rs.getDate("reservation_date"));    
		     
		
		
		return bookReservationList;
	};
	
	
	// 예약 대기명단 1순위 
	public ReservationListVO reservationTop1(String user_id, int book_no) { 
		ReservationListVO resListVO = new ReservationListVO();
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = " select * "
				+ "from (select rownum top, aa.* "
				+ "        from (select * "
				+ "                from book_reservation_list "
				+ "                order by reservation_date) aa "
				+ "        where book_no = ?) "
				+ " where top = 1 ";
		
		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, book_no);
			rs = st.executeQuery();
			
			while(rs.next()) {
				resListVO.setUser_id(rs.getString("user_id"));
				resListVO.setBook_no(rs.getInt("book_no"));
				resListVO.setReservation_date(rs.getDate("reservation_date"));
			}
			
		} catch (NullPointerException e){
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resListVO;
	}
	
	// 대출 후 예약 명단 삭제 
	public int reservationListDelete(int book_no) {
		int result = 0;
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql1 = "delete "
				+ "from book_reservation_list "
				+ "where (book_no, user_id) in ( "
				+ "select book_no, user_id "
				+ "from (select rownum top, aa.* "
				+ "        from (select  * "
				+ "                from book_reservation_list "
				+ "                order by reservation_date) aa "
				+ "        where book_no = ?) "
				+ "where top = 1)";
		
		
		
			try {
				st = conn.prepareStatement(sql1);
				st.setInt(1, book_no);
				result = st.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return result;
	}
	
	
	// 7.대출이력조회(이용자)
	public List<BorrowListVO> getBorrowHistory(String user_id) {
		List<BorrowListVO> borrowList = new ArrayList<>();
		String sql = "select * "
				+ " from book_history_list "
				+ " where user_id = ?";
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(sql);
			st.setString(1, user_id);
			rs = st.executeQuery();
			
			while(rs.next()) {
				borrowList.add(makeBorrowHistroyList(rs));
			} 
			if(borrowList.isEmpty()) {
				return null;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, st, conn);
		}
		return borrowList;
	}
	public BorrowListVO makeBorrowHistroyList(ResultSet rs) throws SQLException { 
		BorrowListVO borrowHistory = new BorrowListVO();
		
		borrowHistory.setBook_name(rs.getString("book_name"));
		borrowHistory.setBook_no(rs.getInt("book_no"));
		borrowHistory.setUser_id(rs.getString("user_id")); 
		borrowHistory.setBorrow_date(rs.getDate("borrow_date"));
		borrowHistory.setReturn_date(rs.getDate("return_date"));
		     
		return borrowHistory;
	};
	
	// 8.회원승인(관리자)
	public int memberApproval(String user_id , String admin_id) {
		int result = 0;
		List<LibUserVO> userList = new ArrayList<LibUserVO>(); 
		userList = getLibUser(user_id);
		
		if(userList == null) { // 없는 회원 조회 시 null값을 리턴받고 0을 리턴.
			return result;
		}
		else if(null != userList.get(0).getAdmin_id()) { // 이용객 조회 시 admin_id 컬럼이 null이 아니라면 -1을 리턴해서 관리자 승인을 받은 회원임을 알림.
			return -1;
		}
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		String sql = "update lib_user"
				+ " set "
				+ " admin_id = ?"
				+ " where user_id = ?";
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(sql);
			st.setString(1, admin_id);
			st.setString(2, user_id);
			result = st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, st, conn);
		}
		return result;
	}
	
	// 9.회원삭제(관리자)
	public int memberDelete(String user_id) {
		Scanner sc = new Scanner(System.in);
		int result = 0;
		String id = null;
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		String sql1 = "select * from book_borrow_list where user_id = ?";
		String sql2 = "delete from lib_user where user_id = ?"; 
		ResultSet rs = null;
		try {
			if(!getLibUser(user_id).isEmpty()) {
				
				st = conn.prepareStatement(sql1);
				st.setString(1, user_id);
				rs = st.executeQuery();
				while(rs.next()) {
					id = rs.getString("user_id");
				}
				if(!user_id.equals(id)) {
					st = conn.prepareStatement(sql2);
					st.setString(1, user_id);
					
					System.out.println("1.예 | 2.아니오");
					System.out.print(user_id+"님을 삭제 하시겠습니까?. >>");
					int num = sc.nextInt(); 
					if(num == 1) {
						result = st.executeUpdate();						
					} else if(num == 2) {
						System.out.println("취소하셨습니다.");
						return -1;
					}
				} else {
					System.out.println("현재 대출 중인 도서가 있는 회원입니다.");
				}
			}
				
	
		} catch (NullPointerException e){
			System.out.println("없는 회원입니다.");
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, st, conn);
		}
		return result;
	}
	
	// 10.도서업로드(관리자)
	public int bookInsert(LibBookVO book) {
		int result = 0;
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		String sql = "insert into book_list(book_no, book_name, book_writer) values(book_no_sequence.nextval, ?, ?)";
		try {
			st = conn.prepareStatement(sql);
			st.setString(1, book.getBook_name());
			st.setString(2, book.getBook_writer());
			result = st.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(null, st, conn);
		}
		return result;
	}
	public LibBookVO makeBook() { 
		LibBookVO book = new LibBookVO();
		
		Scanner sc = new Scanner(System.in);
		System.out.print("도서 제목을 입력해주세요. >>");
		String book_name = sc.nextLine();
		book.setBook_name(book_name);
		System.out.print("저자를 입력해주세요. >>");
		String book_writer = sc.nextLine();
		book.setBook_writer(book_writer);
		
		return book;
	}
	
	// 11.도서삭제 (관리자)
	public int bookDelete(int book_no) {
		Scanner sc = new Scanner(System.in);
		int result = 0;
		int book_num = 0;
		String borrow_status = null;
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql1 = "select * from book_list where book_no = ?";
		String sql2 = "delete from book_list where book_no = ?";
		
		try {
			st = conn.prepareStatement(sql1);
			st.setInt(1, book_no);
			rs = st.executeQuery();
			while(rs.next()) {
				book_num = rs.getInt("book_no");
				borrow_status = rs.getString("book_borrow_status");
			}
			if((book_num == book_no)) {
				//st = conn.prepareStatement(sql);
				if(borrow_status.equals("Y")) {
					st = new LoggableStatement(conn, sql2);
					st.setInt(1, book_no);
					System.out.println("SQL문 확인:" +
							((LoggableStatement) st).getQueryString());
					System.out.println("1.예 | 2.아니오");
					System.out.print("BOOK_NO."+book_no+" 을(를) 목록에서 삭제하시겠습니까?. >>");
					int num = sc.nextInt(); 
					if(num == 1) {
						result = st.executeUpdate();	
					} else if(num == 2){
						System.out.println("취소하셨습니다.");
						return result;
					}
				} else {
					System.out.println("현재 대출중인 도서입니다. 삭제할 수 없습니다.");
				}
				
			}
			
		} catch (NullPointerException e) {
			System.out.println("없는 데이터입니다.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, st, conn);
		}
		return result;
	}
	
	// 12.회원가입 (이용자)
	public int joinMembership(LibUserVO joinUser) {
		int result = 0;
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "insert into lib_user(user_id, user_pass , user_name) values(?,?,?)";
		
		try {
			st = conn.prepareStatement(sql);
			st.setString(1, joinUser.getUser_id());
			st.setString(2, joinUser.getUser_pass());
			st.setString(3, joinUser.getUser_name());
			result = st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, st, conn);
		}
		return result;
	}
	public LibUserVO makeJoinMembership() {
		LibUserVO joinUser = new LibUserVO();
		
		Scanner sc = new Scanner(System.in);
		System.out.print("(회원가입) 사용하실 id를 입력해주세요. >>");
		String user_id = sc.next();
		joinUser.setUser_id(user_id);
		sc.nextLine();
		System.out.print("(회원가입) 사용하실 password 입력해주세요. >>");
		String user_pass = sc.nextLine();
		joinUser.setUser_pass(user_pass);
		System.out.print("(회원가입) 이름을 입력해주세요. >>");
		String user_name = sc.nextLine();
		joinUser.setUser_name(user_name);
		
		return joinUser;
	}
	
	// 반납 시 대출 명단에서 삭제 
	public int borrowListDelete(String user_id, int book_no) {
		int result = 0;
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		String sql = "delete from book_borrow_list where user_id = ? and book_no = ?"; 
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(sql);
			st.setString(1, user_id);
			st.setInt(2, book_no);
			
			result = st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, st, conn);
		}
		return result;
	}
	// 이용자 리스트 셀렉 
	public List<LibUserVO> getLibUser() {
		List<LibUserVO> userList = new ArrayList<LibUserVO>();
		String sql = "select * "
				+ " from lib_user";

		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			
			while(rs.next()) {
				userList.add(makeLibUserList(rs));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, st, conn);
		}
		return userList;
	}
	public List<LibUserVO> getLibUser(String user_id) {
		List<LibUserVO> userList = new ArrayList<LibUserVO>();
		String sql = "select * "
				+ " from lib_user"
				+ " where user_id = ?";

		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(sql);
			st.setString(1, user_id);
			rs = st.executeQuery();
			
			while(rs.next()) {
				userList.add(makeLibUserList(rs));
			}
			
			if(userList.isEmpty()) { // 없는 회원을 승인했을 때 list가 비어있다면 null을 리턴.
				return null;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, st, conn);
		}
		return userList;
	}
	public List<LibUserVO> getLibUser(int num) {
		List<LibUserVO> userList = new ArrayList<LibUserVO>();
		String sql = "select * "
				+ " from lib_user"
				+ " where admin_id is null";

		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			
			while(rs.next()) {
				userList.add(makeLibUserList(rs));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, st, conn);
		}
		return userList;
	}
	public LibUserVO makeLibUserList(ResultSet rs) throws SQLException { 
		LibUserVO userList = new LibUserVO();
		
		userList.setUser_id(rs.getString("user_id")); 
		userList.setUser_pass(rs.getString("user_pass"));
		userList.setUser_name(rs.getString("user_name"));
		userList.setJoin_date(rs.getDate("join_date"));
		userList.setAdmin_id(rs.getString("admin_id"));
		     
		return userList;
	}

	public List<BorrowListVO> getBorrowList(String user_id) {
		List<BorrowListVO> borrowList = new ArrayList<>();
		String sql = "select * "
				+ " from book_borrow_list "
				+ " where user_id = ? order by borrow_date";
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(sql);
			st.setString(1, user_id);
			rs = st.executeQuery();
			
			while(rs.next()) {
				borrowList.add(makeBorrowHistroyList(rs));
			} 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, st, conn);
		}
		return borrowList;
	}

	// 예약자 저장 
	public int reservationSave(String user_id, int book_no) {
		int result = 0;
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "insert into reservation_save values(?,?)";
		
		try {
			st = conn.prepareStatement(sql);
			st.setString(1, user_id);
			st.setInt(2, book_no);
			
			result = st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, st, conn);
		}
		return result;
	};
	
	// 예약자 가져오기
	public List<ReservationListVO> getReservationSave(String user_id) {
		List<ReservationListVO> resListVO = new ArrayList<>();
		String sql = "select * "
				+ " from reservation_save "
				+ " where send_user_id = ?";
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(sql);
			st.setString(1, user_id);
			rs = st.executeQuery();
			
			while(rs.next()) {
				resListVO.add(makeReservationSave(rs));
			} 
			if(resListVO.isEmpty()) {
				return null;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, st, conn);
		}
		return resListVO;
	}
	
	public int reservationSaveDelete(String user_id, int book_no) {
		int result = 0;
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql1 = "delete "
				+ "from reservation_save "
				+ "where send_user_id = ? and send_book_no = ?";
	
			try {
				st = conn.prepareStatement(sql1);
				st.setString(1, user_id);
				st.setInt(2, book_no);
				result = st.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return result;

	}
	
	public ReservationListVO makeReservationSave(ResultSet rs) throws SQLException { 
		ReservationListVO resListVO = new ReservationListVO();
		
		resListVO.setUser_id(rs.getString("send_user_id")); 
		resListVO.setBook_no(rs.getInt("send_book_no"));
		
		     
		return resListVO;
	}
}
	

