package filters;

import java.io.IOException; 
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import domeinklassen.User;

public class GeenGaragebeheerderFilter implements Filter{
	public void init(FilterConfig arg0) throws ServletException {
		/* Filter is being placed into service, do nothing. */
	}
	public void doFilter(ServletRequest req, ServletResponse resp,
		FilterChain chain) throws IOException, ServletException {
		HttpServletRequest r2 = (HttpServletRequest)req;
		User u = (User)r2.getSession().getAttribute("user");
		if (u.getType() == 2) {
			r2.setAttribute("msg", "Log eerst in met een gebruikersaccount die toestemming heeft om op deze pagina te komen!");
			r2.getRequestDispatcher("/index.jsp").forward(req, resp);
		} 
		else {
			chain.doFilter(req, resp);
		}
	}
	public void destroy() {
		/* Filter is being taken out of service, do nothing. */
	}
}