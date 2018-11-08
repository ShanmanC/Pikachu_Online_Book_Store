package ctrl;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.AddressBean;
import bean.PoBean;
import bean.PoItemBean;
import bean.UserInfoBean;
import model.OnlineBookStore;

/**
 * Servlet implementation class Payment
 */
@WebServlet("/Payment")
public class Payment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String MODEL = "model";
	
	private static final String SHOPPING_CART_PAGE="/Shopping-Cart.jspx";
	private static final String HOME_PAGE = "/Home.jspx";
	private static final String LOGIN_PAGE = "/Login.jspx";
	private static final String PAGE_ERROR_PAGE = "PageError.jspx";
	
	private static final String GO_TO_CART = "go_to_cart";
	private static final String GO_TO_LOGIN = "go_to_login";
	private static final String GO_TO_LOGOUT = "go_to_logout";
	private static final String GO_TO_HOME = "go_to_home";
	private static final String PAGE_BEFORE_LOGIN = "page_before_login";
	private static final String SHOPPING_CART="cart";
	private static final String TOTAL_PRICE = "total_price";
	private static final String ORDER_TOTAL_PRICE = "order_total_price";
	
	private static final String USER = "user";
	private static final String PURCHASE_ORDER = "po";
	private static final String COMFIRM_PAYMENT = "confirm_payment";
	private static final String RECEIVER = "receiver";
	private static final String PAYER = "payer";
	private static final String SHIPPING_ADDRESS = "shipping_address";
	private static final String BILLING_ADDRESS = "billing_address";
	private static final String CHANGE_SHIPPING_ADDRESS = "yesship";
	private static final String CHANGE_BILLING_ADDRESS = "yesbill";
	
	private static final String NEW_SHIPPING_FNAME = "sfname";
	private static final String NEW_SHIPPING_LNAME = "slname";
	private static final String NEW_SHIPPING_PHONE = "sphone";
	private static final String NEW_SHIPPING_STREET = "sstreet";
	private static final String NEW_SHIPPING_COUNTRY = "scountry";
	private static final String NEW_SHIPPING_PROVINCE = "sstate";
	private static final String NEW_SHIPPING_ZIP = "szip";
	
	
	private static final String NEW_BILLING_FNAME = "cfname";
	private static final String NEW_BILLING_LNAME = "clname";
	private static final String NEW_BILLING_PHONE = "cphone";
	private static final String NEW_BILLING_STREET = "cstreet";
	private static final String NEW_BILLING_COUNTRY = "ccountry";
	private static final String NEW_BILLING_PROVINCE = "cstate";
	private static final String NEW_BILLING_ZIP = "szip";
	
	private static final String CARD_TYPE = "cardType";
	private static final String CARD_NUMBER = "cardNumber";
	private static final String CARD_EXPIRE_MONTH = "expireM";
	private static final String CARD_EXPIRE_YEAR = "expireY";
	
	private static final String ORDER_ID = "order_id";
	private static final String POITEM_LIST = "poitems";
	
	private static final String DENIED = "denied";
	private static final String DENIED_FLIAG = "denied_flag";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Payment() {
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
		
		PoBean po = (PoBean) request.getSession().getAttribute(PURCHASE_ORDER);
		String target = "OrderDetail.jspx";
		if (request.getParameter(GO_TO_CART) != null) { // Header - go to cart
			target = SHOPPING_CART_PAGE;
		} else if (request.getParameter(GO_TO_LOGIN) != null)  { // Header - go to login
			request.getSession().setAttribute(PAGE_BEFORE_LOGIN, SHOPPING_CART_PAGE);
			target = LOGIN_PAGE;
		} else if (request.getParameter(GO_TO_LOGOUT) != null){ // Header - go to logout
			request.getSession().removeAttribute(USER);
			target = SHOPPING_CART_PAGE;
		} else if (request.getParameter(GO_TO_HOME) != null) { // Header - go to home
			target = HOME_PAGE;
		} else if (request.getParameter(COMFIRM_PAYMENT) != null) { // Submit the payment info, go to OrderDetail page.
			

			
			if (request.getSession().getAttribute(DENIED_FLIAG) == null) {
				request.getSession().setAttribute(DENIED_FLIAG, 1);
				request.getSession().setAttribute(DENIED, "false");
				request.getSession().removeAttribute(SHOPPING_CART);
				request.getSession().setAttribute(ORDER_TOTAL_PRICE, request.getSession().getAttribute(TOTAL_PRICE));
				request.getSession().setAttribute(TOTAL_PRICE, 0);
			} else {
				int denied_flag = (Integer) request.getSession().getAttribute(DENIED_FLIAG);
				denied_flag = denied_flag + 1;
				request.getSession().setAttribute(DENIED_FLIAG, denied_flag);
				if (denied_flag % 3 == 0) {
					request.getSession().setAttribute(DENIED, "true");
				} else {
					request.getSession().setAttribute(DENIED, "false");
					request.getSession().removeAttribute(SHOPPING_CART);
				}
			}

			if (request.getParameter(CHANGE_SHIPPING_ADDRESS) != null) { // User want to use another shipping address.
				String sfname = request.getParameter(NEW_SHIPPING_FNAME);
				String slname = request.getParameter(NEW_SHIPPING_LNAME);
				String sphone = request.getParameter(NEW_SHIPPING_PHONE);
				String sstreet = request.getParameter(NEW_SHIPPING_STREET);
				String scountry = request.getParameter(NEW_SHIPPING_COUNTRY);
				String sstate = request.getParameter(NEW_SHIPPING_PROVINCE);
				String szip = request.getParameter(NEW_SHIPPING_ZIP);
				
				request.getSession().setAttribute(RECEIVER, sfname + " " + slname);
				
				try {
					AddressBean newAdd = model.insertAddr(sstreet, sstate, scountry, szip, sphone);
					UserInfoBean user = (UserInfoBean) request.getSession().getAttribute(USER);
					user.setFname(sfname);
					user.setLname(slname);
					user.setAid(newAdd.getId());
					model.updateuser(user);
					model.updatePo(po, newAdd.getId(), sfname, slname, "ORDERED");
					request.getSession().setAttribute(SHIPPING_ADDRESS, sstreet + "<br />" + sstate + " " + szip + "<br />" + scountry + "<br />" + sphone);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			request.getSession().setAttribute(PAYER, request.getSession().getAttribute(RECEIVER));
			request.getSession().setAttribute(BILLING_ADDRESS, request.getSession().getAttribute(SHIPPING_ADDRESS));
			if (request.getParameter(CHANGE_BILLING_ADDRESS) != null) { // Billing address is different from shipping address.
				String cfname = request.getParameter(NEW_BILLING_FNAME);
				String clname = request.getParameter(NEW_BILLING_LNAME);
				String cphone = request.getParameter(NEW_BILLING_PHONE);
				String cstreet = request.getParameter(NEW_BILLING_STREET);
				String ccountry = request.getParameter(NEW_BILLING_COUNTRY);
				String cstate = request.getParameter(NEW_BILLING_PROVINCE);
				String czip = request.getParameter(NEW_BILLING_ZIP);
				
				request.getSession().setAttribute(PAYER, cfname + " " + clname);
				request.getSession().setAttribute(BILLING_ADDRESS, cstreet + "<br />" + cstate + " " + czip + "<br />" + ccountry + "<br />" + cphone);
			} 
			
			request.getSession().setAttribute(CARD_TYPE, request.getParameter(CARD_TYPE));
			request.getSession().setAttribute(CARD_EXPIRE_MONTH, request.getParameter(CARD_EXPIRE_MONTH));
			request.getSession().setAttribute(CARD_EXPIRE_YEAR, request.getParameter(CARD_EXPIRE_YEAR));
			String cardNumber = request.getParameter(CARD_NUMBER);
			String cardHide = cardNumber.substring(0, cardNumber.length() - 4);
			String cardTail = cardNumber.substring(cardNumber.length() - 4);
			cardNumber = cardHide.replaceAll(".", "*") + cardTail;
			request.getSession().setAttribute(CARD_NUMBER, cardNumber);
			request.getSession().setAttribute(ORDER_ID, po.getId());
			
			try {
				if (request.getSession().getAttribute(DENIED).equals("true")) {
					model.updatePo(po, po.getAddress(), po.getFname(), po.getLname(), "DENIED");
				} else {
					model.updatePo(po, po.getAddress(), po.getFname(), po.getLname(), "PROCESSED");
					Map<PoItemBean, String> poItems = (Map<PoItemBean, String>) model.getPoItems(po.getId());
					request.getSession().setAttribute(PURCHASE_ORDER, po);
					request.getSession().setAttribute(POITEM_LIST, poItems);
					
					for (PoItemBean poItem : poItems.keySet()) {
						model.addVisitEvent(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), poItem.getBid(), "PURCHASE");
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
