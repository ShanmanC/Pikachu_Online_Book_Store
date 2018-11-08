package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.BookBean;
import bean.ReviewBean;
import bean.UserInfoBean;

public class ReviewDAO {

	DataSource ds;
	
	public ReviewDAO() throws ClassNotFoundException{ 
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS"); 
		} catch (NamingException e) {
			e.printStackTrace();
		} 
	}
	
	/*
	 * Giving an bookID return a list with all ReviewBean of this book
	 * if book not exist or no review on this book, list will be null
	 */
	public ArrayList<ReviewBean> retriAllReviewForBook(String bookID) throws SQLException {
		String query = "SELECT * FROM Review WHERE bid = '" + bookID + "'";
		ArrayList<ReviewBean> list = new ArrayList<ReviewBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		while (r.next()) {
			String bid = r.getString("BID");
			String uid = r.getString("UID");
			int rating = r.getInt("RATING");
			String review = r.getString("CONTENT");
			list.add(new ReviewBean(uid, bid, rating, review));
		}
		r.close();
		p.close();
		con.close();
		return list;
	}
	

	public void addNewReview(UserInfoBean user, BookBean bookReviewed, int rating, String review) throws SQLException {
		ReviewBean newReview = new ReviewBean(user.getUid(), bookReviewed.getBid(), rating, review);
		addReview(newReview);
	}
	/*
	 * add a new customer review, if the customer have already submit there review they can not add another time
	 */
	public void addReview(ReviewBean review) throws SQLException {
		String query = null;
		if (checkReviewExist(review) == null) {// review not exist
			query = "INSERT INTO Review VALUES ('" + review.getUid() + "', '" + review.getBid() + "', " + review.getRating() + ", '" + review.getContent() + "')";
			
		} else {// update new review

			query = "UPDATE Review SET \"RATING\"=" + review.getRating() + ", \"CONTENT\"='"
						+ review.getContent() + "' WHERE \"BID\"='" + review.getBid() + "' AND \"UID\"='"
						+ review.getUid() + "'";
			
		}
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		p.executeUpdate();
		p.close();
		con.close();
	

	}
	
	/*
	 * return null if the review do not exist, else retrun the review
	 */
	public String checkReviewExist(ReviewBean review) throws SQLException {
		String query = "SELECT * FROM Review WHERE bid = '" + review.getBid() + "' AND uid = '" + review.getUid() + "'";
		String newReview = null;
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		if (r.next())
			newReview = r.getString("CONTENT");
		r.close();
		p.close();
		con.close();
		
		return newReview;
	}
	
	public boolean checkPurchaseHistory(String bid, String uid) throws SQLException {
		boolean result = false; 
		String query = "select * from po o, poitem i where o.uid = '" + uid + "' and i.bid='" + bid + "' and o.status='PROCESSED'";
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		if (r.next())
			result = true;
		r.close();
		p.close();
		con.close();
		
		return result;
	}
	
	
}
