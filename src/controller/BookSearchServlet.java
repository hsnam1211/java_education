package controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.LibBookVO;
import model.LibDAO;

/**
 * Servlet implementation class BookSearchServlet
 */
@WebServlet("/00Hoos/booksearch")
public class BookSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bookname = request.getParameter("bookname");
		LibDAO dao = new LibDAO();
		List<LibBookVO> book = dao.getBook(2, bookname);
		request.setAttribute("book1", book);
		System.out.println(book);
		// 서블릿이 받은 요청을 JSP에 넘기기 (위임)
		RequestDispatcher rd = request.getRequestDispatcher("bookSearch.jsp");
		rd.forward(request, response);
	}
}
