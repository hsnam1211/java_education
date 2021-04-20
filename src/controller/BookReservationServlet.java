package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.LibDAO;


/**
 * Servlet implementation class BookBorrowServlet
 */
@WebServlet("/00Hoos/bookreservation")
public class BookReservationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LibDAO dao = new LibDAO();
		
		
		String s_bookno = request.getParameter("bookno");
		int bookno = Integer.parseInt(s_bookno);
		String userid = request.getParameter("userid");
		String message = null;
		
		System.out.println(bookno);
		System.out.println(userid);
		
		int result = dao.reservationBook(userid, bookno);
		
		if(result == 1) {
			message = "예약되었습니다.";
		} else if(result == 2){
			message = "현재 대출중인 도서입니다.";
		} else if(result == 3) {
			message = "이미 예약된 도서입니다.";			
		} else if(result == 0) {
			message = "예약에 실패했습니다.";
		}
	
		System.out.println(message);
		
		request.setAttribute("message",message);
		RequestDispatcher rd = request.getRequestDispatcher("resultInfo.jsp");
		rd.forward(request, response);
	}

}
