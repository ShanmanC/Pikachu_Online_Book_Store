package listener;

import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import bean.PoItemBean;
import model.OnlineBookStore;

/**
 * Application Lifecycle Listener implementation class MostPopularBook
 *
 */
@WebListener
public class MostPopularBook implements HttpSessionAttributeListener {
	private static final String PURCHASE_ORDER = "po";
	private static final String USER = "user";
	private static final String TOP_THREE = "top_3";
	Map<Integer, PoItemBean> top = new TreeMap<Integer, PoItemBean>();
	OnlineBookStore model = new OnlineBookStore();

    /**
     * Default constructor. 
     */
    public MostPopularBook() {
        
    }

	/**
     * @see ServletRequestAttributeListener#attributeRemoved(ServletRequestAttributeEvent)
     */
    public void attributeRemoved(HttpSessionBindingEvent arg0)  { 
        
    }


	/**
     * @see ServletRequestAttributeListener#attributeAdded(ServletRequestAttributeEvent)
     */
    public void attributeAdded(HttpSessionBindingEvent arg0)  { 
    	try {
			handleEvent(arg0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

	/**
     * @see ServletRequestAttributeListener#attributeReplaced(ServletRequestAttributeEvent)
     */
    public void attributeReplaced(HttpSessionBindingEvent arg0)  { 
    	try {
			handleEvent(arg0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    private void handleEvent(HttpSessionBindingEvent event) throws SQLException {
    	if (event.getName().equals(USER) || event.getName().equals(PURCHASE_ORDER) ) {
    		top = (Map<Integer, PoItemBean>) model.getTop3();
    		event.getSession().setAttribute(TOP_THREE, top);
    	}
    }
	
}
