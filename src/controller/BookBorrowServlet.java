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
@WebServlet("/00Hoos/bookborrow")
public class BookBorrowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LibDAO dao = new LibDAO();
//		String s_bookno = (String)request.getAttribute("bookno");
//		int bookno = Integer.parseInt(s_bookno);
//		String status = (String)request.getAttribute("status");
//		String userid = (String)request.getAttribute("userid");
		
		
		String s_bookno = request.getParameter("bookno");
		int bookno = Integer.parseInt(s_bookno);
		String status = request.getParameter("status");
		String userid = request.getParameter("userid");
		String message = null;
		
		System.out.println(bookno);
		System.out.println(status);
		System.out.println(userid);
		
		int result = dao.borrowBookStatus1(bookno, "N", userid);
		
		if(result > 0) {
			message = dao.borrowListInsert(userid, bookno)>0?"대출되었습니다.":"대출에 실패했습니다.";
		} else if(result == 0){
			message = "현재 대출 중인 도서입니다.";
		}
	
		System.out.println(message);
		
		request.setAttribute("message",message);
		RequestDispatcher rd = request.getRequestDispatcher("resultInfo.jsp");
		rd.forward(request, response);
	}

}
