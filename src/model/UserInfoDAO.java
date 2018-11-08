package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.StatisticsBean;
import bean.UserInfoBean;

public class UserInfoDAO {

	DataSource ds;
	
	public UserInfoDAO() throws ClassNotFoundException{ 
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS"); 
		} catch (NamingException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * Giving an userid return a UserInfoBean with the userid and stored
	 * password if username exist. Return null CostomerBean if username is not
	 * exist in database
	 * 
	 * @param username
	 * @return CustomerBean with username
	 * @throws SQLException
	 */
	public UserInfoBean retrieveUser(String uid) throws SQLException {
		String query = "SELECT * FROM USERINFO WHERE uid = '" + uid + "'";
		UserInfoBean user = null;
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		if (r.next())
			user = new UserInfoBean(uid, r.getInt("AID"), r.getString("FNAME"), r.getString("LNAME"), r.getString("EMAIL"), r.getString("PWD"), r.getString("USERTYPE"));
					
		r.close();
		p.close();
		con.close();
		return user;
	}
	
	/**
	 * Check if the userid and password are match
	 * 
	 * @param userid
	 * @param password
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean successLogin(String userid, String password) throws SQLException {
		String query = "SELECT * FROM USERINFO WHERE uid = '" + userid + "'";
		boolean success = false;
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		if (r.next() && r.getString("PWD").equals(password))
			success = true;
		r.close();
		p.close();
		con.close();
		return success;
	}
	
	public UserInfoBean loginUser(String userid, String password) throws SQLException {
		UserInfoBean user = new UserInfoBean();
		if (successLogin(userid, password)) {
			String query = "SELECT * FROM USERINFO WHERE uid='" + userid + "' and pwd='" + password + "'";
			Connection con = this.ds.getConnection();
			PreparedStatement p = con.prepareStatement(query);
			ResultSet r = p.executeQuery();
			if (r.next()) {
				String uid = r.getString("UID");
				String lname = r.getString("LNAME");
				String fname = r.getString("FNAME");
				int aid = r.getInt("AID");
				String email = r.getString("EMAIL");
				String pwd = r.getString("PWD");
				String userType = r.getString("USERTYPE");
				
				user = new UserInfoBean(uid, aid, fname, lname, email, pwd, userType);		
			}
			r.close();
			p.close();
			con.close();
			return user;
		} else {
			return null;
		}
	}
	
	/**
	 * Update a new user or update an exist user's password or email or address id with given
	 * information
	 * 
	 * @param user
	 * @throws SQLException
	 */
	public boolean updateuser(UserInfoBean user) throws SQLException {
		String query = null;
		if (checkUserExist(user) == null) {// user not exist
			query = "INSERT into USERINFO values ('" + user.getUid() + "', '" + user.getLname() + "', '" + user.getFname() +  "', " + user.getAid()  + ", '" + user.getEmail() + "', '" + user.getPassword() + "', '" + user.getUserType() + "')";
			Connection con = this.ds.getConnection();
			PreparedStatement p = con.prepareStatement(query);
			p.executeUpdate();
			p.close();
			con.close();
			
		} else {// update new review
			return false;
		}
		return true;
	}
	/*
	 * return null if the user do not exist, else return the user id
	 */
	public String checkUserExist(UserInfoBean user) throws SQLException {
		String query = "SELECT * FROM USERINFO WHERE uid = '" + user.getUid() + "'";
		String newUser = null;
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		if (r.next())
			newUser = r.getString("UID");
		r.close();
		p.close();
		con.close();
		
		return newUser;
	}
	
	public UserInfoBean checkUserInformation(String uid, String email, String fname, String lname) throws SQLException {
		String query = "SELECT * FROM USERINFO WHERE uid='" + uid + "'AND email='" + email + "'AND fname='" + fname + "'AND lname='" + lname + "'";
		UserInfoBean user = new UserInfoBean();
		
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		if ( r.next()) {
			int aid = r.getInt("AID");
			String pwd = r.getString("PWD");
			String userType = r.getString("USERTYPE");
			
			user = new UserInfoBean(uid, aid, fname, lname, email, pwd, userType);
		} else {
			user = null;
		}
			
		r.close();
		p.close();
		con.close();
		
		return user;
	}
	
	public void updatePassword (String uid, String password) throws SQLException {
		String query = "UPDATE USERINFO SET PWD='" + password + "' WHERE UID='" + uid + "'";
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		p.executeUpdate();
		p.close();
		con.close();
	}
	
	public ArrayList<StatisticsBean> getStatistics() throws SQLException {
		ArrayList<StatisticsBean> result = new ArrayList<StatisticsBean>();
		String query1 = "SELECT U.UID, (I.PRICE * I.QUANTITY) AS SPENT FROM USERINFO U, PO O, POITEM I WHERE O.UID = U.UID AND O.ID = I.ID ORDER BY UID";
		
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query1);
		ResultSet r = p.executeQuery();
		while (r.next()) {
			String uid = r.getString("UID");
			int spent = r.getInt("SPENT");
			StatisticsBean bean = new StatisticsBean(uid, "", spent);
			result.add(bean);
		}
		
		for (StatisticsBean bean : result) {
			String query2 = "SELECT U.UID, A.ZIP FROM USERINFO U, ADDRESS A WHERE U.AID = A.ID AND U.UID='" + bean.getUid() + "'";
			p = con.prepareStatement(query2);
			r = p.executeQuery();
			if (r.next()) {
				String zip = r.getString("ZIP");
				bean.setZip(zip);
			}
		}
		r.close();
		p.close();
		con.close();
	
		return result;
	}
	
}
