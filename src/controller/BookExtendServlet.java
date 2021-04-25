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
 * Servlet implementation class BookExtendServlet
 */
@WebServlet("/00Hoos/bookextend")
public class BookExtendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookExtendServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		LibDAO dao = new LibDAO();
		
		String s_bookno = request.getParameter("bookno");
		int bookno = Integer.parseInt(s_bookno);
		String userid = request.getParameter("userid");
		String message = null;

		int result = dao.extendBook(bookno, userid);
		message = result > 0?"연장되었습니다.":"연장에 실패했습니다.";
		
		request.setAttribute("message", message);
		RequestDispatcher rd = request.getRequestDispatcher("resultInfo.jsp");
		rd.forward(request, response);
	}
}
