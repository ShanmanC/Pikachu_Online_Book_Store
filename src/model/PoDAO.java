package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.PoBean;

public class PoDAO {
	DataSource ds;
	
	public PoDAO () throws  ClassNotFoundException {
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS"); 
		} catch (NamingException e) {
			e.printStackTrace();
		} 
	}
	
	// Create new PO
	public PoBean createNewPo(int aid, String fname, String lname, String status, String uid) throws SQLException {
		String day = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String query1 = "INSERT INTO PO (ADDRESS, STATUS, FNAME, LNAME, UID, DAY) VALUES (" + aid + ", '" + status + "', '" + fname + "', '" + lname + "', '" + uid + "', '" + day +  "')";
		Connection con = this.ds.getConnection(); 
		PreparedStatement p = con.prepareStatement(query1); 
		p.executeUpdate();
		
		String query2 = "SELECT MAX(ID) AS ID FROM PO WHERE ADDRESS = " + aid + " AND STATUS = '" + status + "' AND FNAME = '" + fname + "' AND LNAME = '" + lname + "'";
		p = con.prepareStatement(query2);
		ResultSet r = p.executeQuery();
		int id = 0;
		if (r.next())
			id = r.getInt("ID");
		
		p.close();
		con.close();
		return new PoBean(id, lname, fname, status, aid);
	}
	
	public void updatePo(PoBean po, int aid, String fname, String lname, String status) throws SQLException {
		String query = "UPDATE PO SET fname='" + fname + "', lname='" + lname + "', status='" + status + "', address = " + aid  + "WHERE id = " + po.getId();
		Connection con = this.ds.getConnection(); 
		PreparedStatement p = con.prepareStatement(query); 
		p.executeUpdate();
		p.close();
		con.close();
	}

}
