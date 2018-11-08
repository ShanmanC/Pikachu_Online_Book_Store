package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.PoBean;
import bean.PoItemBean;

public class PoItemDAO {
	DataSource ds;
	
	public PoItemDAO () throws  ClassNotFoundException {
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS"); 
		} catch (NamingException e) {
			e.printStackTrace();
		} 
	}
	
	// Add method
	public void addPoItemToPo(PoBean po, String bid, int price, int count) throws SQLException {
		Connection con = this.ds.getConnection(); 
		String query = "INSERT INTO POITEM (ID, BID, PRICE, QUANTITY) VALUES (" + po.getId() + " , '" + bid
				+ "' , " + price + ", " + count + ")";
		
		PreparedStatement p = con.prepareStatement(query);
		p.executeUpdate();
		
		p.close();
		con.close();
	}
	
	public Map<PoItemBean, String> getPoItems(int poId) throws SQLException{
		Map<PoItemBean, String> poItemList = new HashMap<PoItemBean, String>();
		Connection con = this.ds.getConnection(); 
		String query = "select p.id, p.bid, b.title, p.price, p.quantity from poitem p, book b where p.bid = b.bid and p.id = " + poId;
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()) {
			String bid = r.getString("BID"); 
			int price = r.getInt("PRICE");
			int quantity = r.getInt("QUANTITY");
			String title = r.getString("TITLE");
			
			PoItemBean poItem = new PoItemBean(poId, bid, price, quantity);
			poItemList.put(poItem, title);
		}
		  
		r.close();
		p.close();
		con.close();
		return poItemList;
	}
	
	public Map<String, Integer> getBookReport(String year, String month) throws SQLException {
		Map<String, Integer> result = new HashMap<String, Integer>();
		String firstDay = year + "-" + month + "-01";
		
		String nextMonth = "";
		if (Integer.parseInt(month) + 1 < 10) {
			nextMonth = "0" + (Integer.parseInt(month) + 1);
		} else {
			nextMonth = "" + (Integer.parseInt(month) + 1);
		}
		String lastDay = year + "-" + nextMonth + "-01";
		
		String query = "select i.bid, sum(i.quantity) as count from po o, poitem i where o.id = i.id and o.day >= '" + firstDay + "' and o.day < '" + lastDay + "' group by i.bid";
		
		Connection con = this.ds.getConnection(); 
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		while (r.next()) {
			String bid = r.getString("BID");
			int quantity = r.getInt("COUNT");
			result.put(bid, quantity);
		}

		p.close();
		con.close();

		return result;
	}
	
	public Map<Integer, PoItemBean> getTop3() throws SQLException {
		Map<Integer, PoItemBean> top3 = new TreeMap<Integer, PoItemBean>();
		String query = "SELECT I.BID, SUM(I.QUANTITY) AS QUANTITY FROM POITEM I, PO P WHERE I.ID = P.ID AND P.STATUS = 'PROCESSED' GROUP BY BID ORDER BY QUANTITY DESC";
		Connection con = this.ds.getConnection(); 
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		int i = 1;
		while (r.next() && i <= 3) {
			String bid = r.getString("BID");
			int quantity = r.getInt("QUANTITY");
			PoItemBean fakeItem = new PoItemBean(0, bid, 0, quantity);
			top3.put(i, fakeItem);
			i++;
		}
		
		return top3;
	}
}
