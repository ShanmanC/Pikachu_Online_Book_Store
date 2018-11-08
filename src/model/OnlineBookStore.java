package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import bean.*;

public class OnlineBookStore {

	private BookDAO bd;
	private AddressDAO ad;
	private PoDAO pd;
	private PoItemDAO pid;
	private VisitEventDAO ved;
	private ReviewDAO rd;
	private UserInfoDAO uid;
	
	public OnlineBookStore() {
		try {
			bd = new BookDAO();
			ad = new AddressDAO();
			pd = new PoDAO();
			pid = new PoItemDAO();
			ved = new VisitEventDAO();
			rd = new ReviewDAO();
			uid = new UserInfoDAO();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public Map<Integer, BookBean> retriveBook() throws SQLException {
		return bd.retrieve();
	}
	
	// Create new purchase order
	public PoBean createNewPo(int aid, String fname, String lname, String status, String uid) throws SQLException {
		return pd.createNewPo(aid, fname, lname, status, uid);
	}
	
	public void updatePo(PoBean po, int aid, String fname, String lname, String status) throws SQLException {
		pd.updatePo(po, aid, fname, lname, status);
	}
	
	public Map<PoItemBean, String> getPoItems(int poId) throws SQLException{
		return pid.getPoItems(poId);
	}

	// Add poItem to current purchase order
	public void addPoItemToPo(PoBean po, String bid, int price, int count) throws SQLException {
		pid.addPoItemToPo(po, bid, price, count);
	}
	
	public void addNewReview(UserInfoBean user, BookBean bookReviewed, int rating, String review) throws SQLException {
		rd.addNewReview(user, bookReviewed, rating, review);
	}
	
	public ArrayList<ReviewBean> retriAllReviewForBook(String bookID) throws SQLException {
		return rd.retriAllReviewForBook(bookID);
	}
	
	public boolean updateuser(UserInfoBean user) throws SQLException {
		return uid.updateuser(user);
	}
	
	public AddressBean insertAddr(String street, String province, String country, String zip, String phone) throws SQLException {
		return ad.insertAddr(street, province, country, zip, phone);
	}
	
	public UserInfoBean loginUser(String userid, String password) throws SQLException {
		return uid.loginUser(userid, password); 
	}
	
	public UserInfoBean checkUserInformation(String userid, String email, String fname, String lname) throws SQLException {
		return uid.checkUserInformation(userid, email, fname, lname);
	}
	
	public void updatePassword(String userid, String password) throws SQLException {
		uid.updatePassword(userid, password);
	}
	
	public AddressBean getAddressById(int aid) throws SQLException {
		return ad.getAddressById(aid);
	}
	
	public void addVisitEvent(String date, String bookId, String eventType) throws SQLException {
		ved.addVisitEvent(date, bookId, "VIEW");
	}
	
	public boolean checkPurchaseHistory(String bid, String uid) throws SQLException {
		return rd.checkPurchaseHistory(bid, uid);
	}
	
	public Map<String, Integer> getBookReport(String year, String month) throws SQLException {
		return pid.getBookReport(year, month);
	}
	
	public Map<Integer, PoItemBean> getTop3() throws SQLException {
		return pid.getTop3();
	}
	
	public ArrayList<StatisticsBean> getStatistics() throws SQLException {
		return uid.getStatistics();
	}

}
