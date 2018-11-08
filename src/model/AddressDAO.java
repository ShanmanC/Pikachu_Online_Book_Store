package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.AddressBean;

public class AddressDAO {
	
	DataSource ds;
	
	public AddressDAO () throws  ClassNotFoundException {
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS"); 
		} catch (NamingException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * Return the address of this customer
	 * 
	 * @param address id
	 * @return the address of this customer that store in the database
	 * @throws SQLException
	 */
	public AddressBean retrieveAddress(int id) throws SQLException{
		String query = "SELECT * from ADDRESS where ID = '" + id;
		AddressBean address = null;
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		if (r.next()) {
			int aid = r.getInt("ID");
			String street = r.getString("STREET");
			String province = r.getString("PROVINCE");
			String country = r.getString("COUNTRY");
			String zip = r.getString("ZIP");
			String phone = r.getString("PHONE");
			address = new AddressBean(aid, street, province, country, zip, phone);
		}
		r.close();
		p.close();
		con.close();
		return address;
	}
	
	/**
	 * Return the addr's unique int id, if address not exist, return 0
	 * 
	 * @param addr
	 * @return int id or 0
	 * @throws SQLException
	 */
	public int checkAddreExistence(AddressBean addr) throws SQLException {
		String query = "SELECT ID FROM Address WHERE STREET = '" + addr.getStreet() + "' AND PROVINCE = '" + addr.getProvince() + "' AND COUNTRY = '" + addr.getCountry()
				+ "' AND ZIP = '" + addr.getZip() + "' AND PHONE = '" + addr.getPhone() + "'";
		int id = 0;
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		if (r.next())
			id = r.getInt("ID");
		r.close();
		p.close();
		con.close();
		return id;
	}
	
	public int checkAddressExistence(String street, String province, String country, String zip, String phone) throws SQLException {
		int id = 0;
		String query = "SELECT ID FROM Address WHERE STREET = '" + street + "' AND PROVINCE = '" + province + "' AND COUNTRY = '" + country
		+ "' AND ZIP = '" + zip + "' AND PHONE = '" + phone + "'";
		
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		if (r.next())
			id = r.getInt("ID");
		r.close();
		p.close();
		con.close();
		return id;
	}
	
	/**
	 * insert a new customer' address return the address id
	 * 
	 * @param addr
	 * @throws SQLException
	 */
	public int insertAddr(AddressBean addr) throws SQLException {
		String query = "INSERT INTO Address (street, province, country, zip, phone) VALUES ('"
						+ addr.getStreet() + "', '" + addr.getProvince() + "', '"
						+ addr.getCountry() + "', '" + addr.getZip() + "', '" + addr.getPhone() + "')";
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		p.executeUpdate();
		p.close();
		con.close();
		return checkAddreExistence(addr);
		
	}
	
	public AddressBean insertAddr(String street, String province, String country, String zip, String phone) throws SQLException {
		int aid = checkAddressExistence(street, province, country, zip, phone);
		if (aid == 0) {
			Connection con = this.ds.getConnection();
			String query1 = "INSERT INTO Address (street, province, country, zip, phone) VALUES ('"
					+ street + "', '" + province + "', '"
					+ country + "', '" + zip + "', '" + phone + "')";
			PreparedStatement p = con.prepareStatement(query1);
			p.executeUpdate();
			
			String query2 = "SELECT MAX(ID) AS AID FROM ADDRESS";
			p = con.prepareStatement(query2);
			ResultSet r = p.executeQuery();
			if (r.next())
				aid = r.getInt("AID");
			p.close();
			con.close();
		}
		
		AddressBean add = new AddressBean(aid, street, province, country, zip, phone);
		return add;
	}
	
	public AddressBean getAddressById(int aid) throws SQLException {
		AddressBean address = new AddressBean();
		String query = "SELECT * FROM Address WHERE ID = " + aid;
		
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		if (r.next()) {
			String street = r.getString("STREET");
			String province = r.getString("PROVINCE");
			String country = r.getString("COUNTRY");
			String zip = r.getString("ZIP");
			String phone = r.getString("PHONE");
			
			address = new AddressBean(aid, street, province, country, zip, phone);
		} else {
			address = null;
		}
			
		r.close();
		p.close();
		con.close();
		return address;
	}

}
