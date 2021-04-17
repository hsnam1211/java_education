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
 * Servlet implementation class BookReturnServlet
 */
@WebServlet("/00Hoos/bookreturn")
public class BookReturnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		LibDAO dao = new LibDAO();

		String s_bookno = request.getParameter("bookno");
		int bookno = Integer.parseInt(s_bookno);
		String userid = request.getParameter("userid");
		String message = null;
		
		System.out.println(bookno);
		System.out.println(userid);
		
		int result = dao.returnBookStatus(bookno, "Y", userid);
		
		if(result > 0) { message = dao.borrowListDelete(userid, bookno)>0?"반납되었습니다.":"반납에 실패했습니다."; } 
	
		System.out.println(message);
		
		request.setAttribute("message",message);
		RequestDispatcher rd = request.getRequestDispatcher("resultInfo.jsp");
		rd.forward(request, response);
	}

}
