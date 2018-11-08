package filter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import bean.StatisticsBean;
import model.OnlineBookStore;

/**
 * Servlet Filter implementation class StatisticsFilter
 */
@WebFilter("/Analytics")
public class StatisticsFilter implements Filter {
	private static final String TABLE_TYPE = "table_type";
	private static final String STATISTICS_REPORT = "statistics_report";
	private static final String STATISTICS_RESULT = "statistics_result";
	
	OnlineBookStore model = new OnlineBookStore();
	
	
    /**
     * Default constructor. 
     */
    public StatisticsFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		
		if (request.getParameter(STATISTICS_REPORT) != null) {
			request.setAttribute(TABLE_TYPE, STATISTICS_REPORT);
			
			try {
				ArrayList<StatisticsBean> statistics =  model.getStatistics();
				
				for (StatisticsBean bean : statistics) {
					String uid = bean.getUid();
					String hided = uid.toString().replaceAll(".", "*");
					bean.setUid(hided);
				}
				request.setAttribute(STATISTICS_RESULT, statistics);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
