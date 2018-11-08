package ctrl;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.*;
import model.*;

/**
 * Servlet implementation class Start
 */
@WebServlet("/Start")
public class Start extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String HOME_PAGE = "/Home.jspx";
	private static final String LOGIN_PAGE = "/Login.jspx";
	private static final String SHOPPING_CART_PAGE="/Shopping-Cart.jspx";
	private static final String BOOK_DETAIL_PAGE = "/bookDetail.jspx";
	
	private static final String GO_TO_CART = "go_to_cart";
	private static final String GO_TO_LOGIN = "go_to_login";
	private static final String GO_TO_LOGOUT = "go_to_logout";
	private static final String GO_TO_HOME = "go_to_home";	
	
	private static final String APPLICATION_NAME = "applicationName";
	private static final String APPLICATION_ICON = "applicationIcon";
	private static final String MODEL = "model";
	private static final String SHOPPING_CART = "cart";
	private static final String DETAIL = "detail";
	private static final String BOOK_REVIEWED = "book_reviewed";
	
	private static final String ICON_PATH = "/image/Pikachu.png";
	private static final String BOOK_INFO = "bookinfo";
	private static final String TOTAL_PRICE = "total_price";
	private static final String REVIEW_LIST = "reviews";
	private static final String PAGE_BEFORE_LOGIN = "page_before_login";
	private static final String USER = "user";
	
	private static final String SEARCH_BUTTON = "search_button";
	private static final String SEARCH_QUERY = "search_query";
	private static final String SEARCH = "search";
	private static final String SEARCH_RESULT_COUNT = "searchResultCount";
	private static final String SEARCH_RESULT = "searchResult";
	
	private static final String CATEGORY = "category";
	private static final String CATEGORY_ALL = "all";
	private static final String CATEGORY_SELECTED = "selected_category";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Start() {
        super();
    }
    
    public void init() throws ServletException {
    		super.init();
		this.getServletContext().setAttribute(MODEL, new OnlineBookStore());
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		OnlineBookStore model = (OnlineBookStore) this.getServletContext().getAttribute(MODEL);
		
		
		if (request.getParameter(GO_TO_CART) != null) { // Header - go to cart
			request.getRequestDispatcher(SHOPPING_CART_PAGE).forward(request, response);
		} else if (request.getParameter(GO_TO_LOGIN) != null)  { // Header - go to login
			request.getSession().setAttribute(PAGE_BEFORE_LOGIN, "/Home.jspx");
			request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
		} else if (request.getParameter(GO_TO_LOGOUT) != null){ // Header - go to logout
			request.getSession().removeAttribute(USER);
			request.getRequestDispatcher(HOME_PAGE).forward(request, response);
		} else if (request.getParameter(GO_TO_HOME) != null) { // Header - go to home
			request.getRequestDispatcher(HOME_PAGE).forward(request, response);
		} else if (request.getParameter(DETAIL) != null) { // Go to book-details page.
			
			String bookId = request.getParameter(DETAIL);
			@SuppressWarnings("unchecked")
			Map<String, BookBean> bookInfoFromDatabase = (Map<String, BookBean>) request.getSession().getAttribute(BOOK_INFO);
			
			
			BookBean bookReviewed = bookInfoFromDatabase.get(Integer.parseInt(bookId.substring(1)));
			
			try {
				ArrayList<ReviewBean> reviews = model.retriAllReviewForBook(bookReviewed.getBid());
				request.getSession().setAttribute(REVIEW_LIST, reviews);
				
				model.addVisitEvent(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), bookId, "VIEW");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			request.getSession().setAttribute(BOOK_REVIEWED, bookReviewed);
			
			request.getRequestDispatcher(BOOK_DETAIL_PAGE).forward(request, response);
			
		} else if (request.getParameter(SEARCH_BUTTON) != null) { // Show searched table.
			String query = request.getParameter(SEARCH_QUERY);
			request.setAttribute(SEARCH, "true");
			ArrayList<BookBean> search_result = new ArrayList<BookBean>();
			@SuppressWarnings("unchecked")
			Map<String, BookBean> bookInfoFromDatabase = (Map<String, BookBean>) request.getSession().getAttribute(BOOK_INFO);
			for (BookBean b : bookInfoFromDatabase.values()) {
				if (b.getTitle().toLowerCase().contains(query.toLowerCase())) {
					search_result.add(b);
				}
			}
			request.setAttribute(SEARCH_RESULT, search_result);
			request.setAttribute(SEARCH_RESULT_COUNT, search_result.size());
			request.getRequestDispatcher(HOME_PAGE).forward(request, response);
		} else if (request.getParameter(CATEGORY) != null) { // User choose specific category
			String category = request.getParameter(CATEGORY);
			@SuppressWarnings("unchecked")
			Map<Integer, BookBean> bookInfoFromDatabase = (Map<Integer, BookBean>) request.getSession().getAttribute(BOOK_INFO);
			
			if (category.equals(CATEGORY_ALL)) {
				request.setAttribute(CATEGORY_SELECTED, bookInfoFromDatabase);
			} else {
				Map<Integer, BookBean> selected_list = new HashMap<Integer, BookBean>();
				for (Integer i : bookInfoFromDatabase.keySet()) {
					BookBean b = bookInfoFromDatabase.get(i);
					if (b.getCategory().equals(category)) 
						selected_list.put(i, b);
				}
				request.setAttribute(CATEGORY_SELECTED, selected_list);
			}
			request.getRequestDispatcher(HOME_PAGE).forward(request, response);
		}
		
		else { // Add to cart, save attributes.
			Map<Integer, BookBean> bookInfoFromDatabase = null;
			
			String target = HOME_PAGE;
			String applicationName = this.getServletContext().getInitParameter(APPLICATION_NAME);

			try {
				bookInfoFromDatabase = model.retriveBook();
				addToShoppingCart(request, model, bookInfoFromDatabase);

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			request.getSession().setAttribute(BOOK_INFO, bookInfoFromDatabase);
			
			
			request.getSession().setAttribute(APPLICATION_NAME, applicationName);
			request.getSession().setAttribute(APPLICATION_ICON, ICON_PATH);
			
			
			request.getRequestDispatcher(target).forward(request, response);
		} 

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	// Zoe
	private void addToShoppingCart(HttpServletRequest request, OnlineBookStore model, Map<Integer, BookBean> bookInfoFromDatabase) throws Exception {
		@SuppressWarnings("unchecked")
		Map<BookBean, Integer> cart = (Map<BookBean, Integer>) request.getSession().getAttribute(SHOPPING_CART);

		if (cart == null) {
			cart = new HashMap<BookBean, Integer>();
		}
		
		for (BookBean  b : bookInfoFromDatabase.values()) {
			String bookCount = request.getParameter(b.getTitle() + "_count");
			String bookAdd = request.getParameter(b.getTitle() + "_add");
			
			if (bookCount != null && bookAdd != null) {
				if (cart.containsKey(b)) {
					cart.replace(b, cart.get(b) + Integer.parseInt(bookCount));
				} else {
					cart.put(b, Integer.parseInt(bookCount));
				}
				model.addVisitEvent(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), b.getBid(), "CART");
			}
		}
		
		// calculate the total price
		Double totalPrice = new Double(0);
		for (BookBean b : cart.keySet()) {
			int price = b.getPrice();
			int quantity = cart.get(b);
			totalPrice = totalPrice + price * quantity;
		}
		
		request.getSession().setAttribute(TOTAL_PRICE, totalPrice);
		request.getSession().setAttribute(SHOPPING_CART, cart);

	}

}
