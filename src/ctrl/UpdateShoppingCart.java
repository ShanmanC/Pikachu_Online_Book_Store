package ctrl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.AddressBean;
import bean.BookBean;
import bean.PoBean;
import bean.UserInfoBean;
import model.OnlineBookStore;

/**
 * Servlet implementation class UpdateShoppingCart
 */
@WebServlet("/UpdateShoppingCart")
public class UpdateShoppingCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String MODEL = "model";
	
	private static final String SHOPPING_CART_PAGE="/Shopping-Cart.jspx";
	private static final String HOME_PAGE = "/Home.jspx";
	private static final String PAYMENT_PAGE = "/Payment.jspx";
	private static final String LOGIN_PAGE = "/Login.jspx";
	
	private static final String GO_TO_CART = "go_to_cart";
	private static final String GO_TO_LOGIN = "go_to_login";
	private static final String GO_TO_LOGOUT = "go_to_logout";
	private static final String GO_TO_HOME = "go_to_home";	
	
	private static final String USER = "user";
	private static final String SHOPPING_CART="cart";
	private static final String KEEP_SHOPPING="keep_shopping";
	private static final String CHECK_OUT = "check_out";
	private static final String TOTAL_PRICE = "total_price";
	private static final String EMPTY_CART = "empty_cart";
	
	private static final String PAGE_BEFORE_LOGIN = "page_before_login";
	
	private static final String RECEIVER = "receiver";
	private static final String SHIPPING_ADDRESS = "shipping_address";
	private static final String PURCHASE_ORDER = "po";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateShoppingCart() {
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
		
		String keep_shopping = request.getParameter(KEEP_SHOPPING);
		String check_out = request.getParameter(CHECK_OUT);
		@SuppressWarnings("unchecked")
		Map<BookBean, Integer> cart = (Map<BookBean, Integer>) request.getSession().getAttribute(SHOPPING_CART);

		String target = SHOPPING_CART_PAGE;
		
		if (keep_shopping != null) {	// Go back to home page
			target = HOME_PAGE;
		}else if (request.getParameter(GO_TO_CART) != null) { // Header - go to cart
			target = SHOPPING_CART_PAGE;
		} else if (request.getParameter(GO_TO_LOGIN) != null)  { // Header - go to login
			request.getSession().setAttribute(PAGE_BEFORE_LOGIN, SHOPPING_CART_PAGE);
			target = LOGIN_PAGE;
		} else if (request.getParameter(GO_TO_LOGOUT) != null){ // Header - go to logout
			request.getSession().removeAttribute(USER);
			target = SHOPPING_CART_PAGE;
		} else if (request.getParameter(GO_TO_HOME) != null) { // Header - go to home
			target = HOME_PAGE;
		} else if (check_out != null) { // Go to payment page. If visitor did not login yet, go to login page.
			UserInfoBean user = (UserInfoBean) request.getSession().getAttribute(USER);
			
			if (user == null) { // User must log in before go to payment page.
				request.getSession().setAttribute(PAGE_BEFORE_LOGIN, SHOPPING_CART_PAGE);
				target = LOGIN_PAGE;
			} else if (cart.isEmpty()) { // User has logged in but shopping-cart is empty.
				request.setAttribute(EMPTY_CART, "true");
			} else {
				request.getSession().setAttribute(RECEIVER, user.getFname() + " " + user.getLname());
				String addressString = "Empty Address";
				try {
					AddressBean address = model.getAddressById(user.getAid());
					if (address != null) {
						addressString = address.getStreet() + "<br />" + address.getProvince() + " " + address.getZip() + "<br />" + address.getCountry() + "<br />" + address.getPhone();
					}
					request.getSession().setAttribute(SHIPPING_ADDRESS, addressString);
					
					PoBean po = model.createNewPo(user.getAid(), user.getFname(), user.getLname(), "ORDERED", user.getUid()); 
					for (BookBean b : cart.keySet()) {
						model.addPoItemToPo(po, b.getBid(), b.getPrice(), cart.get(b));
					}
					request.getSession().setAttribute(PURCHASE_ORDER, po);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				target = PAYMENT_PAGE;
			}
			
		} else { // Update shopping cart
			if (!cart.isEmpty()) {
				for (BookBean b : cart.keySet()) {
					String updateCount = request.getParameter(b.getBid() + "_updateCount");
					String deleted = request.getParameter(b.getBid() +"_delete");
					
					if (updateCount != null) {
						String newCount = request.getParameter(b.getBid() + "_quantity");
						cart.replace(b, Integer.parseInt(newCount));
						break;
					}
					if (deleted != null) {
						cart.remove(b);
						break;
					}
				}
			}
			
			// Re-calculate the total price
			Double totalPrice = new Double(0);
			for (BookBean b : cart.keySet()) {
				int price = b.getPrice();
				int quantity = cart.get(b);
				totalPrice = totalPrice + price * quantity;
			}
			
			request.getSession().setAttribute(TOTAL_PRICE, totalPrice);
			request.getSession().setAttribute(SHOPPING_CART, cart);
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
