package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.*;

public class BookDAO {

	DataSource ds;
	
	public BookDAO() throws ClassNotFoundException{ 
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS"); 
		} catch (NamingException e) {
			e.printStackTrace();
		} 
	}
	
	public Map<Integer, BookBean> retrieve() throws SQLException{
		String query = "SELECT * FROM BOOK";
		Map<Integer, BookBean> rv = new HashMap<Integer, BookBean>();
		Connection con = this.ds.getConnection(); 
		PreparedStatement p = con.prepareStatement(query); 
		ResultSet r = p.executeQuery();
		
		
		while (r.next()) {
			String bid = r.getString("BID"); 
			String title = r.getString("TITLE");
			int price = r.getInt("PRICE");
			String category = r.getString("CATEGORY");
			String artwork_url = r.getString("ARTWORK_URL");
			
			int i = Integer.parseInt(bid.substring(1));
			
			BookBean bb = new BookBean(bid, title, price, category, artwork_url);
			rv.put(i, bb);
			i++;
		}
		  
		r.close();
		p.close();
		con.close();
		
		return rv;
	}

}
