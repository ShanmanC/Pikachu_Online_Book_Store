package ctrl;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.AddressBean;
import bean.UserInfoBean;
import model.OnlineBookStore;

/**
 * Servlet implementation class RegisterAndLogin
 */
@WebServlet("/RegisterAndLogin")
public class RegisterAndLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String LOGIN_PAGE = "/Login.jspx";
	private static final String REGISTER_PAGE = "/Register.jspx";
	private static final String FORGET_PASSWORD_CHECK_PAGE = "ForgotPasswordCheck.jspx";
	private static final String FORGET_PASSWORD_SET_PAGE = "ForgotPasswordSet.jspx";
	private static final String SHOPPING_CART_PAGE="/Shopping-Cart.jspx";
	private static final String HOME_PAGE = "/Home.jspx";
	private static final String ANALYTICS_PAGE = "Analytics.jspx";
	private static final String PARTNER_PAGE = "Partner.jspx";
	private static final String PAGE_ERROR_PAGE = "PageError.jspx";
	
	private static final String REGISTER_USERNAME = "register_username";
	private static final String REGISTER_EMAIL = "register_email";
	private static final String REGISTER_PASSWORD = "register_password";
	private static final String SHIPPING_FIRST_NAME = "sfname";
	private static final String SHIPPING_LAST_NAME = "slname";
	private static final String SHIPPING_PHONE = "sphone";
	private static final String SHIPPING_STREET_ADDRESS = "sadrs";
	private static final String SHIPPING_STATE = "sstate";
	private static final String SHIPPING_COUNTRY = "scountry";
	private static final String SHIPPING_ZIP = "szip";
	private static final String LOGIN_USERNAME = "login_userid";
	private static final String LOGIN_PASSWORD = "login_password";
	
	private static final String MODEL = "model";
	private static final String GO_TO_REGISTER = "login_register";
	private static final String GO_TO_LOGIN = "go_to_login";
	private static final String GO_TO_HOME = "go_to_home";
	private static final String GO_TO_CART = "go_to_cart";
	private static final String REGISTER_BUTTON = "register_button";
	private static final String LOGIN_BUTTON = "login_button";
	private static final String FORGET_PASSWORD = "forget_password_button";
	
	private static final String USER_EXIST = "user_exist";
	private static final String LOGIN_ERROR = "login_error";
	private static final String ERROR_MESSAGE = "errorMessage";
	private static final String USER = "user";
	private static final String PAGE_BEFORE_LOGIN = "page_before_login";
	
	private static final String ALLOW_RESET_PASSWORD = "allow_reset_password";
	private static final String RESET_PASSWORD = "reset_password";
	private static final String CONFIRM_NEW_PASSWORD = "confirm_new_password";
	private static final String PASSWORD_UID = "password_uid";
	private static final String PASSWORD_EMAIL = "password_email";
	private static final String PASSWORD_FNAME = "password_fname";
	private static final String PASSWORD_LNAME = "password_lname";
	private static final String CHECK_USER_INFO = "check_user_info";
	private static final String RESET_PWD = "reset_pwd1";
	private static final String USER_FORGET_PASSWORD = "user_forget_password";
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterAndLogin() {
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
		
		String target = REGISTER_PAGE;
		if (request.getParameter(GO_TO_REGISTER) != null) { // Reach register page from login page.
			target = REGISTER_PAGE;
		} else if (request.getParameter(GO_TO_LOGIN) != null) { // Go back to login page.
			target = LOGIN_PAGE;
		} else if (request.getParameter(GO_TO_HOME) != null) {
			target = HOME_PAGE;
		} else if (request.getParameter(GO_TO_CART) != null) {
			target = SHOPPING_CART_PAGE;
		} else if (request.getParameter(REGISTER_BUTTON) != null){ // Register user
			// User Info
			String username = request.getParameter(REGISTER_USERNAME);
			request.getSession().setAttribute(REGISTER_USERNAME, username);
			String email = request.getParameter(REGISTER_EMAIL);
			request.getSession().setAttribute(REGISTER_EMAIL, email);
			String password = request.getParameter(REGISTER_PASSWORD);
			// Shipping Info
			String fname = request.getParameter(SHIPPING_FIRST_NAME);
			request.getSession().setAttribute(SHIPPING_FIRST_NAME, fname);
			String lname = request.getParameter(SHIPPING_LAST_NAME);
			request.getSession().setAttribute(SHIPPING_LAST_NAME, lname);
			String phone = request.getParameter(SHIPPING_PHONE);
			request.getSession().setAttribute(SHIPPING_PHONE, phone);
			String street = request.getParameter(SHIPPING_STREET_ADDRESS);
			request.getSession().setAttribute(SHIPPING_STREET_ADDRESS, street);
			String province = request.getParameter(SHIPPING_STATE);
			String country = request.getParameter(SHIPPING_COUNTRY);
			String zip = request.getParameter(SHIPPING_ZIP);
			request.getSession().setAttribute(SHIPPING_ZIP, zip);

			AddressBean address;
			try {
				address = model.insertAddr(street, province, country, zip, phone);
				UserInfoBean user = new UserInfoBean(username, address.getId(), fname, lname, email, password,
						"Customer");
				if (model.updateuser(user)) {
					request.getSession().setAttribute(USER_EXIST, "false");
					removeCookie(request);
					target = LOGIN_PAGE;
				} else {
					request.getSession().setAttribute(USER_EXIST, "true");
					request.getSession().setAttribute(ERROR_MESSAGE, "* User Name Already Exists!");
					target = REGISTER_PAGE;
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (request.getParameter(LOGIN_BUTTON) != null) { // Login.
			String login_userid = request.getParameter(LOGIN_USERNAME);
			String login_password = request.getParameter(LOGIN_PASSWORD);
			
			try {
				UserInfoBean user = model.loginUser(login_userid, login_password);
				if (user != null) {
					request.getSession().setAttribute(USER, user);
					request.getSession().setAttribute(LOGIN_ERROR, "false");
					if (user.getUserType().equals("Administrator")) { // User is administrator
						target = ANALYTICS_PAGE;
					} else if (user.getUserType().equals("Partner")) { // User is partner
						target = PARTNER_PAGE;
					} else {
						target = (String) request.getSession().getAttribute(PAGE_BEFORE_LOGIN);
					}
				} else {
					request.getSession().setAttribute(LOGIN_ERROR, "true");
					target = LOGIN_PAGE;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (request.getParameter(FORGET_PASSWORD) != null) { // Go to forget-password page.
			target = FORGET_PASSWORD_CHECK_PAGE;
		} else if (request.getParameter(RESET_PASSWORD) != null) { // Check user info, allow user to reset password.
			String password_uid = request.getParameter(PASSWORD_UID);
			String password_email = request.getParameter(PASSWORD_EMAIL);
			String password_fname = request.getParameter(PASSWORD_FNAME);
			String password_lname = request.getParameter(PASSWORD_LNAME);
			
			try {
				UserInfoBean user_forget_pwd = model.checkUserInformation(password_uid, password_email, password_fname, password_lname);
				if (user_forget_pwd == null) { // User enters wrong information
					request.getSession().setAttribute(CHECK_USER_INFO, "false");
					target = FORGET_PASSWORD_CHECK_PAGE;
				} else { // User enters correct information, allow to reset password.
					request.getSession().setAttribute(CHECK_USER_INFO, "true");
					request.getSession().setAttribute(USER_FORGET_PASSWORD, user_forget_pwd);
					target = FORGET_PASSWORD_SET_PAGE;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else if (request.getParameter(CONFIRM_NEW_PASSWORD) != null) {// Confirm new password, go back to login page.
			String reset_password = request.getParameter(RESET_PWD);
			UserInfoBean user_forget_password = (UserInfoBean) request.getSession().getAttribute(USER_FORGET_PASSWORD);
			
			try {
				model.updatePassword(user_forget_password.getUid(), reset_password);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			request.getSession().removeAttribute(ALLOW_RESET_PASSWORD);
			request.getSession().removeAttribute(USER_FORGET_PASSWORD);
			target = LOGIN_PAGE;
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
	
	private void removeCookie(HttpServletRequest request) {
		// User info
		request.getSession().removeAttribute(REGISTER_USERNAME);
		request.getSession().removeAttribute(REGISTER_EMAIL);
		// Shipping Info
		request.getSession().removeAttribute(SHIPPING_FIRST_NAME);
		request.getSession().removeAttribute(SHIPPING_LAST_NAME);
		request.getSession().removeAttribute(SHIPPING_PHONE);
		request.getSession().removeAttribute(SHIPPING_STREET_ADDRESS);
		request.getSession().removeAttribute(SHIPPING_ZIP);
	}

}
