package filters;

import java.io.IOException; import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

public class IngelogdFilter implements Filter{
	public void init(FilterConfig arg0) throws ServletException {
		/* Filter is being placed into service, do nothing. */
	}
	public void doFilter(ServletRequest req, ServletResponse resp,
		FilterChain chain) throws IOException, ServletException {
		HttpServletRequest r = (HttpServletRequest)req;
		if (r.getSession().getAttribute("gebruiker") == null) {
			r.setAttribute("msg", "Welkom! Log in met uw gebruikersnaam en wachtwoord");
			r.getRequestDispatcher("/loginpage.jsp").forward(req, resp);
		} 
		else {
			chain.doFilter(req, resp);
		}
	}
	public void destroy() {
		/* Filter is being taken out of service, do nothing. */
	}
}