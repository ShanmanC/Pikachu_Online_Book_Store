package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;


/**
 * Servlet Filter implementation class BlockJxps
 */
@WebFilter(urlPatterns = { "/Analytics.jspx", "/bookDetail.jspx", "/ForgotPasswordCheck.jspx", "/ForgotPasswordSet.jspx", "/header.jspx", "/Login.jspx", "/OrderDetail.jspx", "/PageError.jspx", "/Partner.jspx", "/Payment.jspx", "/Register.jspx"})
public class BlockJxps implements Filter {
	private static final String PAGE_ERROR_PAGE = "PageError.jspx";

    /**
     * Default constructor. 
     */
    public BlockJxps() {
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
		
		request.getRequestDispatcher(PAGE_ERROR_PAGE).forward(request, response);

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
