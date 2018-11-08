package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.VisitEventBean;

public class VisitEventDAO {
	DataSource ds;
	
	public VisitEventDAO () throws  ClassNotFoundException {
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS"); 
		} catch (NamingException e) {
			e.printStackTrace();
		} 
	}
	
	public VisitEventBean retrieve(String visitDay, String bookID) throws SQLException {
		String query = "select V.DAY, V.BID, V.EVENTTYPE from VISITEVENT V where V.DAY = '" + visitDay + "' and V.BID = '" + bookID + "'";
		VisitEventBean ve =	null;
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		while (r.next()) {
			String day = r.getString("DAY");
			String bid = r.getString("BID");
			String eventtype = r.getString("EVENTTYPE");
			ve = new VisitEventBean(day, bid, eventtype);
		}
		r.close();
		p.close();
		con.close();
		return ve;
	}
	
	public VisitEventBean retrieveByDay(String visitDay) throws SQLException {
		String query = "select V.DAY, V.BID, V.EVENTTYPE from VISITEVENT V where V.DAY = '" + visitDay + "'";
		VisitEventBean ve =	null;
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		while (r.next()) {
			String day = r.getString("DAY");
			String bid = r.getString("BID");
			String eventtype = r.getString("EVENTTYPE");
			ve = new VisitEventBean(day, bid, eventtype);
		}
		r.close();
		p.close();
		con.close();
		return ve;
	}
	
	public VisitEventBean retrieveByBid(String bookID) throws SQLException {
		String query = "select V.DAY, V.BID, V.EVENTTYPE from VISITEVENT V where V.BID = '" + bookID + "'";
		VisitEventBean ve =	null;
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		while (r.next()) {
			String day = r.getString("DAY");
			String bid = r.getString("BID");
			String eventtype = r.getString("EVENTTYPE");
			ve = new VisitEventBean(day, bid, eventtype);
		}
		r.close();
		p.close();
		con.close();
		return ve;
	}
	
	public void addVisitEvent(String date, String bookId, String eventType) throws SQLException {
		String query = "INSERT INTO VISITEVENT (DAY, BID, EVENTTYPE) VALUES ('" + date + "', '" + bookId + "', '" + eventType + "')";
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		p.executeUpdate();
		
		p.close();
		con.close();
	}
}
