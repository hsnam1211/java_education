package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BorrowListVO;
import model.LibBookVO;
import model.LibDAO;

/**
 * Servlet implementation class BookReturnServlet
 */
@WebServlet("/00Hoos/bookborrowlist")
public class BookBorrowListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userid = request.getParameter("userid");
		LibDAO dao = new LibDAO();
		List<BorrowListVO> booklist = dao.getBorrowList(userid);
		
		request.setAttribute("book", booklist);
		
		// 서블릿이 받은 요청을 JSP에 넘기기 (위임)
		RequestDispatcher rd = request.getRequestDispatcher("bookBorrowList.jsp");
		rd.forward(request, response);
	}
}
