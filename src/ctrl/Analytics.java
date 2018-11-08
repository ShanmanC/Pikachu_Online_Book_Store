package ctrl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.OnlineBookStore;

/**
 * Servlet implementation class Analytics
 */
@WebServlet("/Analytics")
public class Analytics extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String HOME_PAGE = "/Home.jspx";
	private static final String ANALYSTICS_PAGE = "/Analytics.jspx";
	private static final String PAGE_ERROR_PAGE = "PageError.jspx";
	
	private static final String MODEL = "model";
	private static final String GO_TO_LOGOUT = "go_to_logout";
	private static final String GO_TO_HOME = "go_to_home";
	private static final String USER = "user";
	
	private static final String TABLE_TYPE = "table_type";
	private static final String BOOK_REPORT = "book_report";
	private static final String BOOK_REPORT_YEAR = "year";
	private static final String BOOK_REPORT_MONTH = "month";
	private static final String BOOK_REPORT_RESULT = "book_report_result";
	
	private static final String TOP_REPORT = "top_report";
	
	private static final String STATISTICS_REPORT = "statistics_report";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Analytics() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init() throws ServletException {
		super.init();
		this.getServletContext().setAttribute(MODEL, new OnlineBookStore());
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		OnlineBookStore model = (OnlineBookStore) this.getServletContext().getAttribute(MODEL);
		
		String target = ANALYSTICS_PAGE;
		if (request.getParameter(GO_TO_HOME) != null) { // Header - go to home
			target = HOME_PAGE;
		} else if (request.getParameter(BOOK_REPORT) != null) { // Get book reports in specific month
			request.setAttribute(TABLE_TYPE, BOOK_REPORT);
			String year = request.getParameter(BOOK_REPORT_YEAR);
			String month = request.getParameter(BOOK_REPORT_MONTH);
			try {
				Map<String, Integer>book_report_list = (Map<String, Integer>) model.getBookReport(year, month);
				request.setAttribute(BOOK_REPORT_RESULT, book_report_list);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (request.getParameter(TOP_REPORT) != null) { // Get Top popular books
			request.setAttribute(TABLE_TYPE, TOP_REPORT);
		} else if (request.getParameter(STATISTICS_REPORT) != null) { // Get statistics results
			request.setAttribute(TABLE_TYPE, STATISTICS_REPORT);
		} else if (request.getParameter(GO_TO_LOGOUT) != null) { // User log out, go to home page.
			request.getSession().removeAttribute(USER);
			target = HOME_PAGE;
		} else {
			target = PAGE_ERROR_PAGE;
		}
		
		request.getRequestDispatcher(target).forward(request, response);
 	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
