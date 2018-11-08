package ctrl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.BookBean;
import bean.ReviewBean;
import bean.UserInfoBean;
import model.OnlineBookStore;

/**
 * Servlet implementation class CheckBookDetails
 */
@WebServlet("/CheckBookDetails")
public class CheckBookDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String HOME_PAGE = "/Home.jspx";
	private static final String LOGIN_PAGE = "/Login.jspx";
	private static final String SHOPPING_CART_PAGE="/Shopping-Cart.jspx";
	private static final String BOOK_DETAIL_PAGE = "/bookDetail.jspx";
	
	private static final String GO_TO_CART = "go_to_cart";
	private static final String GO_TO_LOGIN = "go_to_login";
	private static final String GO_TO_LOGOUT = "go_to_logout";
	private static final String GO_TO_HOME = "go_to_home";	
	
	private static final String BOOK_REVIEWED = "book_reviewed";
	private static final String QUANTITY = "quantity";
	private static final String ADD_TO_CART = "addToCart";
	private static final String MODEL = "model";
	private static final String SHOPPING_CART = "cart";
	private static final String TOTAL_PRICE = "total_price";
	private static final String SUBMIT_REVIEW = "submit_review";
	private static final String RATING = "rating";
	private static final String CUSTOMER_REVIEW = "customer_review";
	private static final String USER = "user";
	private static final String REVIEW_LIST = "reviews";
	private static final String PAGE_BEFORE_LOGIN = "page_before_login";
	private static final String NO_PURCHASE = "no_purchase";
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckBookDetails() {
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
		BookBean bookReviewed = (BookBean) request.getSession().getAttribute(BOOK_REVIEWED);
		String target = BOOK_DETAIL_PAGE;
		
		if (request.getParameter(GO_TO_CART) != null) { // Header - go to cart
			target = SHOPPING_CART_PAGE;
		} else if (request.getParameter(GO_TO_LOGIN) != null)  { // Header - go to login
			request.getSession().setAttribute(PAGE_BEFORE_LOGIN, BOOK_DETAIL_PAGE);
			target = LOGIN_PAGE;
		} else if (request.getParameter(GO_TO_LOGOUT) != null){ // Header - go to logout
			request.getSession().removeAttribute(USER);
			target = BOOK_DETAIL_PAGE;
		} else if (request.getParameter(GO_TO_HOME) != null) { // Header - go to home
			target = HOME_PAGE;
		} else if (request.getParameter(ADD_TO_CART) != null) {
			int quantity = Integer.parseInt(request.getParameter(QUANTITY));
			
			@SuppressWarnings("unchecked")
			Map<BookBean, Integer> cart = (Map<BookBean, Integer>) request.getSession().getAttribute(SHOPPING_CART);
			Double totalPrice = (Double) request.getSession().getAttribute(TOTAL_PRICE);
			if (cart == null) {
				cart = new HashMap<BookBean, Integer>();
			}
			if (totalPrice == null) {
				totalPrice = 0.0;
			}
			
			if (cart.containsKey(bookReviewed)) {
				cart.replace(bookReviewed, cart.get(bookReviewed) + quantity);
			} else {
				cart.put(bookReviewed,quantity);
			}
			totalPrice = totalPrice + quantity * bookReviewed.getPrice();
			
			request.getSession().setAttribute(TOTAL_PRICE, totalPrice);
			request.getSession().setAttribute(SHOPPING_CART, cart);
			
		} else if (request.getParameter(SUBMIT_REVIEW) != null) {
			UserInfoBean user = (UserInfoBean) request.getSession().getAttribute(USER);
			if (user == null) {
				target = LOGIN_PAGE;
				request.getSession().setAttribute(PAGE_BEFORE_LOGIN, "/bookDetail.jspx");
			} else {
				try {
					if (model.checkPurchaseHistory(bookReviewed.getBid(), user.getUid())) {
						int rating = Integer.parseInt(request.getParameter(RATING));
						String review = request.getParameter(CUSTOMER_REVIEW);
						try {
							model.addNewReview(user, bookReviewed, rating, review);
							ArrayList<ReviewBean> reviews = model.retriAllReviewForBook(bookReviewed.getBid());
							request.getSession().setAttribute(REVIEW_LIST, reviews);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					} else {
						request.setAttribute(NO_PURCHASE, "true");
					}
				} catch (NumberFormatException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			// update and show all comments;
			try {
				ArrayList<ReviewBean> reviews = model.retriAllReviewForBook(bookReviewed.getBid());
				request.getSession().setAttribute(REVIEW_LIST, reviews);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
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
